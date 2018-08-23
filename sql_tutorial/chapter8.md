chapter8-SQL高级处理
===================

## 8.1 窗口函数

```sql
-- 窗口函数,即OLAP,用于进行实时分析 = OnLine Analytical Processing

<窗口函数> OVER ([PARTITION BY <列清单>]
                     ORDER BY <排序用列清单>)

-- 窗口函数大体可以分为两种
-- 1. 聚合函数(SUM/AVG/COUNT/MAX/MIN)
-- 2. RANK/DENSE_RANK/ROW_NUMBER等专用窗口函数

SELECT P1.product_name, P1.product_type, P1.sale_price, COUNT(*) AS ranking
  FROM Product AS P1 LEFT JOIN Product AS P2
    ON P1.product_type = P2.product_type
   AND P1.sale_price >= P2.sale_price
 GROUP BY P1.product_id, P1.product_type, P1.sale_price
 ORDER BY P1.product_type, ranking;

-- 窗口函数
-- PARTITION BY能够设定排序的对象范围
-- ORDER BY能够指定按照哪一列,何种顺序进行排序
-- 通过PARTTION BY分组后的记录的集合可以称为窗口
-- 窗口函数兼具分组和排序两种功能,但没有聚合的功能
SELECT product_name, product_type, sale_price,
       RANK () OVER (PARTITION BY product_type
                         ORDER BY sale_price) AS ranking
  FROM Product;

-- RANK
-- 计算排序时,如果存在相同位次的记录,则会跳过之后的位次。有3条记录排在第1位时:1 1 1 4...

-- DENSE_RANK
-- 即使存在相同位次的记录,也不会跳过之后的位次。有3条记录排在第1位时:1 1 1 2...

-- ROW_NUMBER
-- 赋予唯一的连续位次。有3条记录排在第1位时:1 2 3 4...

SELECT product_name, product_type, sale_price, 
       RANK () OVER (ORDER BY sale_price) AS ranking,
       DENSE_RANK () OVER (ORDER BY sale_price) AS dense_ranking,
       ROW_NUMBER () OVER (ORDER BY sale_price) AS row_num
  FROM Product;

-- 由于专用窗口函数无需参数,因此通常括号中都是空的
-- 原则上窗口函数只能在SELECT子句中使用,(ORDER BY子句或者UPDATE语句的SET子句中也可以使用),因为ORDER BY是在SELECT后执行

-- 作为窗口函数使用的聚合函数
SELECT product_id, product_name, sale_price,
       SUM (sale_price) OVER (ORDER BY product_id) AS current_sum
  FROM Product;

-- 窗口中指定更加详细的汇总范围
-- 移动平均
-- 当前行开始截止到之前2行
-- PRECEDING/FOLLOWING
SELECT product_id, product_name, sale_price,
       AVG (sale_price) OVER (ORDER BY product_id
                               ROWS 2 PRECEDING) AS moving_avg
  FROM Product;

SELECT product_id, product_name, sale_price,
       AVG (sale_price) OVER (ORDER BY product_id
                               ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING) AS moving_avg
  FROM Product;

-- OVER子句中的ORDER BY只是用来决定窗口函数按照什么样的顺序进行计算的,对结果的排列顺序并没有影响
``` 

## 8.2 GROUPING运算符

```sql
-- 聚合同时得到合计行

-- UNION ALL和UNION 的不同之处在于它不会对结果进行排序,因此比UNION的性能更好

-- GROUPING包含: ROLLUP/CUBE/GROUPING SETS

-- 使用ROLLUP同时得出合计和小计
-- 就是将GROUP BY子句中的聚合键清单ROLLUP(<列 1>, <列 2>, ...)使用
-- 相当于汇总GROUP BY () + GROUP BY (product_type)
SELECT product_type, SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY ROLLUP(product_type);
-- MySQL
SELECT product_type, SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY product_type WITH ROLLUP;

-- GROUP BY () + GROUP BY (product_type) + GROUP BY (product_type, regist_date)
SELECT product_type, regist_date, SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY ROLLUP(product_type, regist_date);

-- GROUPING: 用来判断超级分组记录所产生的NULL时返回1,其他情况返回0
SELECT CASE WHEN GROUPING(product_type) = 1
            THEN '商品种类 合计'
            ELSE product_type END AS product_type,
       CASE WHEN GROUPING(regist_date) = 1
            THEN '登记日期 合计'
            ELSE CAST(regist_date AS VARCHAR(16)) END AS regist_date,  -- 日期和GROUPING返回相同数据类型
       SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY ROLLUP(product_type, regist_date);

-- CUBE
-- GROUP BY子句中聚合键的所有可能的组合
-- GROUP BY () + GROUP BY (product_type) + GROUP BY (regist_date) + GROUP BY (product_type, regist_date)
SELECT CASE WHEN GROUPING(product_type) = 1 
            THEN '商品种类 合计'
            ELSE product_type END AS product_type,
       CASE WHEN GROUPING(regist_date) = 1 
            THEN '登记日期 合计'
            ELSE CAST(regist_date AS VARCHAR(16)) END AS regist_date,
       SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY CUBE(product_type, regist_date);

-- GROUPING SETS
-- 只有关于两个键单独的合计
SELECT CASE WHEN GROUPING(product_type) = 1
            THEN '商品种类 合计'
            ELSE product_type END AS product_type,
       CASE WHEN GROUPING(regist_date) = 1
            THEN '登记日期 合计'
            ELSE CAST(regist_date AS VARCHAR(16)) END AS regist_date,
       SUM(sale_price) AS sum_tanka
  FROM Product
 GROUP BY GROUPING SETS (product_type, regist_date);
```

## Ex

```sql
-- 2
SELECT regist_date, 
       product_name, 
       sale_price, 
       SUM (sale_price) OVER (ORDER BY CASE WHEN regist_date IS NULL
                                            THEN CAST('0001-01-01' AS DATE)
                                            ELSE regist_date
                                            END)
  FROM Product;

-- 对NULL的转换应该优先想到COALESCE
-- 时间最早为'0001-01-01'
SELECT regist_date,
       product_name,
       sale_price,
       SUM (sale_price) OVER (ORDER BY COALESCE(regist_date, CAST('0001-01-01' AS DATE))) AS current_sum_price
FROM Product;

-- NULLS FIRST/LAST
SELECT regist_date,
       product_name,
       sale_price,
       SUM (sale_price) OVER (ORDER BY regist_date NULLS FIRST) AS current_sum_price -- 不是所有DBMS支持
FROM Product;
```