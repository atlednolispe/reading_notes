chapter6-函数、谓词、CASE表达式
============================

## 6.1 各种各样的函数

```sql
-- 算术函数
ABS()
MOD(被除数, 除数) (SQL SERVER = %)
-- 四舍五入
ROUND(对象数值, 保留小数的位数)

-- 字符串函数
-- 拼接
|| (SQL SERVER/MySQL CONCAT(str1, str2, str3))
LENGTH(字符串) (SQL Server LEN(str1))
LOWER()
UPPER()
REPLACE(对象字符串, 替换前的字符串, 替换后的字符串)
SUBSTRING (对象字符串 FROM 截取的起始位置 FOR 截取的字符数) (SQL SERVER SUBSTRING(对象字符串,截取的起始位置,截取的字符数), Oracle/DB2 SUBSTR(对象字符串,截取的起始位置,截取的字符数))

-- 日期函数
-- 没有参数,不需要()
CURRENT_DATE
CURRENT_TIME
-- DATE + TIME
CURRENT_TIMESTAMP
EXTRACT(日期元素 FROM 日期)
-- PostgreSQL/MySQL
SELECT CURRENT_TIMESTAMP,
       EXTRACT(YEAR   FROM CURRENT_TIMESTAMP) AS year,
       EXTRACT(MONTH  FROM CURRENT_TIMESTAMP) AS month,
       EXTRACT(DAY    FROM CURRENT_TIMESTAMP) AS day,
       EXTRACT(HOUR   FROM CURRENT_TIMESTAMP) AS hour,
       EXTRACT(MINUTE FROM CURRENT_TIMESTAMP) AS minute,
       EXTRACT(SECOND FROM CURRENT_TIMESTAMP) AS second;

-- 转换函数
CAST(转换前的值 AS 想要转换的数据类型)
-- 返回可变参数中左侧开始第1个不是NULL的值
COALESCE(数据1, 数据2, 数据3 ...)
```

## 6.2 谓词

```sql
-- 谓词: 返回值是布尔值,用于判断是否存在满足某种条件的记录

-- %: 0字符以上的任意字符串
-- _: 任意1个字符
LIKE

-- 包含区间端点
BETWEEN ... AND ...

IS NULL/IS NOT NULL

-- 都不包含NULL
IN/NOT IN (值1, 值2, ...)

-- 可以使用子查询作为IN谓词的参数

-- EXIST
-- EXIST谓词的主语是“记录”,是否存在这样的行
-- 通常指定关联子查询作为EXIST的参数
-- 由于EXIST只关心记录是否存在,因此返回哪些列都没有关系,所以作为EXIST参数的子查询中经常会使用SELECT *
SELECT product_name, sale_price
  FROM Product AS P
 WHERE EXISTS (SELECT *
                 FROM ShopProduct AS SP
                WHERE SP.shop_id = '000C'
                  AND SP.product_id = P.product_id);
```

## 6.3 CASE表达式

```sql
-- <求值表达式>是一个boolean
-- 表达式最终返回值是一个单值
CASE WHEN <求值表达式> THEN <表达式>
     WHEN <求值表达式> THEN <表达式>
     ...
     ELSE <表达式>
END

-- 默认为ELSE NULL,END不能省略
SELECT product_name,
       CASE WHEN product_type = '衣服'    THEN 'A: '
            WHEN product_type = '办公用品' THEN 'B: '
            WHEN product_type = '厨房用具' THEN 'C: '
            ELSE NULL
       END
       || product_type AS abc_product_type
  FROM Product;

-- 表达式可以书写在任意位置

-- 利用CASE表达式将SELECT语句结果中的行和列进行互换
SELECT product_type,
       SUM(sale_price) AS sum_price
  FROM Product
 GROUP BY product_type;

SELECT SUM(CASE WHEN product_type = '衣服'
                THEN sale_price ELSE 0 END) AS sum_price_clothes,
       SUM(CASE WHEN product_type = '厨房用具'
                THEN sale_price ELSE 0 END) AS sum_price_kitchen,
       SUM(CASE WHEN product_type = '办公用品'
                THEN sale_price ELSE 0 END) AS sum_price_office
  FROM Product;

-- 简单CASE表达式,WHEN只能指定一列
SELECT product_name,
       CASE product_type
            WHEN '衣服'    THEN 'A: ' || product_type
            WHEN '厨房用具' THEN 'B: ' || product_type
            WHEN '办公用品' THEN 'C: ' || product_type
            ELSE NULL
        END AS abc_product_type
  FROM Product;

SELECT product_name,
       CASE WHEN product_type = '衣服'    THEN 'A: ' || product_type
            WHEN product_type = '厨房用具' THEN 'B: ' || product_type
            WHEN product_type = '办公用品' THEN 'C: ' || product_type
            ELSE NULL
       END AS abc_product_type
  FROM Product;
```

## Ex

```sql
-- 1
-- SQL中最危险的陷阱,NOT IN的参数中包含NULL时结果通常会为空
-- 子查询的返回值千万注意不能含NULL
SELECT product_name, purchase_price
  FROM Product
 WHERE purchase_price NOT IN (500, 2800, 5000, NULL);

-- 2
SELECT SUM(CASE WHEN sale_price <= 1000
                THEN 1 ELSE 0 END) AS low_price,
       SUM(CASE WHEN sale_price > 1000 AND sale_price <= 3000 -- BETWEEN AND
                THEN 1 ELSE 0 END) AS mid_price,
       SUM(CASE WHEN sale_price > 3000
                THEN 1 ELSE 0 END) AS high_price
  FROM Product;
```