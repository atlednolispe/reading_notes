chapter3-聚合与排序
==================

## 3.1 对表进行聚合查询

```sql
-- 聚合函数: 将多行汇总为一行
-- COUNT/SUM/AVG/MAX/MIN

-- 包含NULL行
SELECT COUNT(*)
  FROM Product;

-- 非空行总计 
SELECT COUNT(purchase_price)
  FROM Product;

-- 聚合函数以列名为参数,那么在计算之前就已经把NULL排除在外
-- AVG()会计算非NULL的平均值
-- MAX/MIN函数几乎适用于所有数据类型的列,只要能排序
-- SUM/AVG函数只适用于数值类型的列

-- 去重
-- 先去重再计数
SELECT COUNT(DISTINCT product_type)
  FROM Product;

-- 先计数后去重=只计数
SELECT DISTINCT COUNT(product_type)
  FROM Product;

-- 结果包含NULL
SELECT DISTINCT purchase_price
  FROM Product;
```

## 3.2 对表进行分组

```sql
-- GROUP BY就像是切分表的一把刀
-- 先WHERE过滤后聚合
-- FROM -> WHERE -> GROUP BY -> SELECT
SELECT <列名 1>, <列名 2>, <列名 3>, ...
  FROM <表名>
 WHERE ...
 GROUP BY <列名 1>, <列名 2>, <列名 3>, ...;

-- 聚合键包含NULL
-- GROUP BY后会存在NULL项
-- COUNT(*)返回NULL项个数, NULL对应的COUNT(purchase_price)为0
SELECT purchase_price, COUNT(*), COUNT(purchase_price)
FROM Product
GROUP BY purchase_price;

-- GROUP BY常见问题
-- * 聚合函数SELECT只能包含常数、聚合函数、聚合键，有聚合函数出现在SELECT中,则此SELECT中就只能出现聚合键其他键都不行
-- 因为SQL在DBMS内部的执行顺序,GROUP BY子句中不能使用SELECT子句中定义的别名
-- GROUP BY子句的结果顺序随机
-- 只有SELECT子句和HAVING子句以及ORDER BY子句中能够使用聚合函数,WHERE中不能使用
```

## 3.3 为聚合结果指定条件

```sql
-- HAVING子句只能包含常数、聚合函数、聚合键
SELECT <列名 1>, <列名 2>, <列名 3>, ...
FROM <表名>
GROUP BY <列名 1>, <列名 2>, <列名 3>, ...
HAVING <分组结果对应的条件>

-- WHERE子句 = 指定行所对应的条件
-- HAVING子句 = 指定组所对应的条件

-- 速度上WHERE >> HAVING
-- 使用COUNT函数等对表中的数据进行聚合操作时,DBMS内部就会进行排序处理
-- WHERE过滤减少了排序行的数量
-- 并且可以对WHERE子句指定条件所对应的列创建索引大幅提高处理速度
```

## 3.4 对查询结果进行排序

```sql
-- 书写顺序
-- SELECT -> FROM 子句 -> WHERE 子句 -> GROUP BY -> HAVING 子句 -> ORDER BY

-- ASC/DESC
-- 排序键中包含NULL时,会在开头或末尾进行汇总

-- 执行顺序
-- FROM -> WHERE -> GROUP BY -> HAVING -> SELECT -> ORDER BY
-- 因此可以在ORDER BY中使用别名
-- SELECT在GROUP BY子句之后,ORDER BY子句之前!
-- 在ORDER BY子句中可以使用SELECT子句中未使用的列和聚合函数

-- 在ORDER BY子句中不要使用列编号
```

## Ex

```sql
-- 3.2 
-- 勘误应该是超过1.5倍数
SELECT product_type, SUM(sale_price) AS sum_sale, SUM(purchase_price) AS sum_purchase
  FROM Product
 GROUP BY product_type
HAVING SUM(sale_price) > 1.5 * SUM(purchase_price);
```