chapter5-复杂查询
================

## 5.1 视图

```sql
-- 视图类似于表,在更新时存在一些不同
-- 视图时并不会将数据保存到存储设备,实际保存的是SELECT语句,视图会在内部执行SELECT语句并创建出一张临时表

-- 1. 视图无需保存数据,因此可以节省存储设备的容量
-- 2. 便于重用,简化复杂查询语句,并且视图中的数据会随着原表的变化自动更新
-- 应该将经常使用的SELECT语句做成视图

CREATE VIEW 视图名称 (<视图列名 1>, <视图列名 2>, ...)
AS
<SELECT 语句>

CREATE VIEW ProductSum (product_type, cnt_product)
AS
SELECT product_type, COUNT(*)
  FROM Product
 GROUP BY product_type;

-- 应该尽量避免在视图的基础上创建视图,多重视图会降低SQL的性能

-- 视图限制
-- 1. 定义视图时不能使用ORDER BY子句,和表一样,数据行都是没有顺序的
-- 2. 若定义视图的SELECT满足特定条件,视图也可以进行更新操作
--        - SELECT子句中未使用DISTINCT
--        - FROM子句中只有一张表
--        - 未使用GROUP BY子句
--        - 未使用HAVING 子句

-- 因为聚合的视图是从表派生的,如果视图发生了改变,而原表没有进行相应更新的话,就无法保证数据的一致性,所以聚合的视图不能做更新操作

-- PostgreSQL中的视图会被初始设定为只读,需要如下修改可写
CREATE OR REPLACE RULE insert_rule
AS ON INSERT
TO ProductJim DO INSTEAD
INSERT INTO Product VALUES (
                    new.product_id,
                    new.product_name,
                    new.product_type,
                    new.sale_price,
                    new.purchase_price,
                    new.regist_date);

-- 删除视图
DROP VIEW ViewName;

-- 删除关联的多重视图
DROP VIEW ViewName CASCADE;
```

## 5.2 子查询

```sql
-- 子查询就是将用来定义视图的SELECT语句直接用于FROM子句当中
-- 子查询就是一次性的视图
SELECT product_type, cnt_product
  FROM (SELECT product_type, COUNT(*) AS cnt_product
          FROM Product
         GROUP BY product_type) AS ProductSum;
-- Oracle子查询别名不能使用AS

-- 为了可读性和性能,尽量避免使用多层嵌套的子查询

-- 原则上子查询必须设定名称

-- 标量子查询就是返回单一值的子查询
SELECT product_id, product_name, sale_price
  FROM Product
 WHERE sale_price > (SELECT AVG(sale_price)
                       FROM Product);

-- 通常任何可以使用((单一值))的位置都可以使用标量子查询
-- 能够使用常数或者列名的地方,无论是SELECT子句、GROUP BY子句、HAVING子句,还是ORDER BY子句,几乎所有的地方都可以使用
```

## 5.3 关联子查询

```sql
-- 在细分的组内进行比较时,需要使用关联子查询
-- 对每一行sale_price判断时对关联子查询通过WHERE进行了筛选
SELECT product_type, product_name, sale_price
  FROM Product P1
 WHERE sale_price > (SELECT AVG(sale_price)
                       FROM Product P2
                      WHERE P1.product_type = P2.product_type);

-- 结合条件一定要写在子查询中
-- 内部可以看到外部,而外部看不到内部
-- 先执行P2,P2结束后P1看不到P2了
SELECT product_type, product_name, sale_price
  FROM Product AS P1
 WHERE P1.product_type = P2.product_type
   AND sale_price > (SELECT AVG(sale_price)
                       FROM Product AS P2
                      GROUP BY product_type);
```

## Ex

```sql
-- 1
CREATE VIEW ViewPractice5_1
AS
SELECT product_name, sale_price, regist_date
  FROM Product
 WHERE sale_price >= 1000 AND regist_date = '2009-09-20';

SELECT * FROM ViewPractice5_1;

-- 3
SELECT product_id,
       product_name,
       product_type,
       sale_price,
       (SELECT AVG(sale_price) AS p FROM Product AS p_avg) AS sale_price_all
  FROM Product;

-- 4
CREATE VIEW AvgPriceByType
AS
SELECT product_id,
       product_name,
       sale_price,
       product_type,
       (SELECT AVG(sale_price)
          FROM Product AS P2
         WHERE P2.product_type = P1.product_type) AS avg_sale_price
  FROM Product AS P1;
```