chapter2-查询基础
================

## 2.1 SELECT语句基础

```sql
-- SELECT & FROM clause
SELECT <列名>, ...
FROM <表名>;

-- SELECT * 会使用CREATE TABLE顺序对结果的列排序

-- 原则以子句为单位进行换行, 相同clause的内容均在关键字右侧, 且最好右对齐

-- 别名,中文需要""，英文不需要
SELECT product_id AS id,
       product_name AS "商品名称",
       purchase_price AS "进货单价"
  FROM Product;

-- 常数,字符串&日期需要''，数字不需要
SELECT '商品' AS string, 38 AS number, '2009-02-24' AS date,
       product_id, product_name
FROM Product;

-- 对列的行去重, DISTINCT关键字只能用在第一个列名之前,且NULL也被视为一种类型
SELECT DISTINCT product_type, regist_date
  FROM Product;

-- 条件查询,首先通过WHERE子句查询出符合指定条件的记录,然后再选取出SELECT语句指定的列
-- SQL中子句的书写顺序是固定的， WHERE在FROM后面
SELECT product_name, product_type
  FROM Product
 WHERE product_type = '衣服';

-- 注释
-- 1. 单行--， 2. 多行/* */
```

## 2.2 算术运算符和比较运算符

```sql
-- 算术运算
SELECT product_name, sale_price,
       sale_price * 2 AS "sale_price_x2"
  FROM Product;

-- 所有包含NULL的计算,结果肯定是NULL

-- 只获取一行零时数据,Oracle使用Dual零时表
SELECT (100 + 200) * 3 AS calculation;

-- 比较运算
-- =， <>

-- 字符串类型的数据原则上按照字典顺序进行排序

-- 不能对NULL使用比较运算符，下面的结果不会显示含有NULL的行
-- IS NULL， IS NOT NULL
SELECT product_name, purchase_price
  FROM Product
 WHERE purchase_price = 2800;
```

## 2.3 逻辑运算符

```sql
-- NOT
-- 不能单独使用,必须和其他查询条件组合起来使用, NOT > AND > OR
SELECT product_name, product_type, sale_price
  FROM Product
 WHERE NOT sale_price >= 1000;

SELECT product_name, product_type, regist_date
  FROM Product
 WHERE product_type = '办公用品'
   AND (   regist_date = '2009-09-11'
        OR regist_date = '2009-09-20');

-- 通过创建真值表,无论多复杂的条件,都会更容易理解
-- P Q R  Q OR R  P AND ( Q OR R )
-- T T T    T       T

-- 含NULL的比较运算结果UNKNOWN
-- TRUE, FALSE, UNKNOWN
```

## Ex

```sql
-- 1
SELECT product_name, regist_date
  FROM Product
 WHERE regist_date > '2009-04-28';

-- 2
-- 均为空

-- 3
SELECT product_name, sale_price, purchase_price
  FROM Product
 WHERE sale_price > purchase_price + 500;

-- 4
SELECT product_name, product_type, 0.9 * sale_price - purchase_price AS profit
  FROM Product
 WHERE (   product_type = '办公用品'
        OR product_type = '厨房用具')
   AND 0.9 * sale_price - purchase_price >= 100;
```