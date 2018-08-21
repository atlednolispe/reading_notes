chapter4-数据更新
================

## 4.1 数据的插入(INSERT语句的使用方法)

```sql
INSERT INTO <表名> (列1, 列2, 列3, ...) VALUES (值1, 值2, 值3, ...);

-- 逗号隔开,分别括在()内,这种形式称为清单

-- 多行INSERT(Oracle除外)
INSERT INTO ProductIns VALUES ('0002', '打孔器', '办公用品', 500, 320, '2009-09-11'),
                              ('0003', '运动T-shirt', '衣服', 4000, 2800, NULL),
                              ('0003', '菜刀', '厨房用具', 4000, 2800, NULL);

-- Oracle中的多行INSERT
INSERT ALL INTO ProductIns VALUES ('0002', '打孔器', '办公用品', 500, 320, '2009-09-11')
           INTO ProductIns VALUES ('0003', '运动T恤', '衣服', 4000, 2800, NULL)
           INTO ProductIns VALUES ('0004', '菜刀', '厨房用具', 3000, 2800, '2009-09-20')

-- 全列INSERT时,可以省略表名后的列清单

-- 创建表时设定默认值
sale_price INTEGER DEFAULT 0,

-- 插入默认值,若未设定默认值且未设置NOT NULL则会默认插入NULL
('0007', '擦菜板', '厨房用具', DEFAULT, 790, '2009-04-28')
-- 或者列清单省略field以及值清单省略数据

-- INSERT ... SELECT用于备份表
INSERT INTO ProductCopy (
       product_id, product_name, product_type, sale_price, purchase_price, regist_date)
SELECT product_id, product_name, product_type, sale_price, purchase_price, regist_date
  FROM Product;

-- INSERT ... SELECT使用ORDER BY子句并不会产生任何效果
```

## 4.2 数据的删除(DELETE 语句的使用方法)

```sql
DELETE FROM <表名>
 WHERE <条件>;

-- DELETE语句中不能使用GROUP BY、HAVING和ORDER BY三类子句,而只能使用WHERE子句
-- DELETE处理比较慢,TRUNCATE速度更快但只能清除整个表
```

## 4.3 数据的更新(UPDATE 语句的使用方法)

```sql
UPDATE <表名>
   SET <列名> = <表达式>
 WHERE <条件>;

-- 多列更新
UPDATE Product
   SET sale_price = sale_price * 10,
       purchase_price = purchase_price / 2
 WHERE product_type = '厨房用具';

-- 不是所有DBMS都可以用
UPDATE Product
   SET (sale_price, purchase_price) = (sale_price * 10, purchase_price / 2)
 WHERE product_type = ' 厨房用具 ';
```

## 4.4 事务

```sql
-- 事务
-- 需要在同一个处理单元中执行的一系列更新处理的集合
事务开始语句;
-- SQL Server、PostgreSQL: BEGIN TRANSACTION
-- MySQL: START TRANSACTION
-- Oracle、DB2: 无
DML 语句1;
DML 语句2;
DML 语句3;
...
事务结束语句(COMMIT或者ROLLBACK) ;


BEGIN TRANSACTION;
    -- 将运动T恤的销售单价降低1000日元
    UPDATE Product
       SET sale_price = sale_price - 1000
     WHERE product_name = '运动 T 恤';
    -- 将T恤衫的销售单价上浮1000日元
    UPDATE Product
       SET sale_price = sale_price + 1000
     WHERE product_name = 'T 恤衫';
    COMMIT;

-- 虽然我们可以不清楚事务开始的时间点,但是在事务结束时一定要仔细进行确认

-- 大部分情况下,事务在数据库连接建立时就已经悄悄开始了
-- A. 每条SQL语句就是一个事务(自动提交模式) - SQL Server、PostgreSQL和MySQL等
-- B. 直到用户执行COMMIT或者ROLLBACK为止算作一个事务 - Oracle

-- DELETE前记得开始事务模式, BEGIN TRANSACTION

-- 事务ACID

-- 原子性(Atomicity)
-- 其中所包含的更新处理要么全部执行,要么完全不执行

-- 一致性(Consistency)
-- 一致性指的是事务中包含的处理要满足数据库提前设置的约束,事务中不合法的SQL则ROLLBACK,其余成功操作

-- 隔离性(Isolation)
-- 在没有提交之前,其他事务也是看不到新添加的记录的

-- 持久性(Durability)
-- 事务(不论是提交还是回滚)结束后,DBMS能够保证该时间点的数据状态会被保存,如果不能保证持久性,即使是正常提交结束的事务,一旦发生了系统故障,也会导致数据丢失,一切都需要从头再来
```

## Ex

```sql
-- 3
INSERT INTO ProductMargin(product_id, product_name, sale_price, purchase_price, margin)
SELECT product_id, product_name, sale_price, purchase_price, sale_price - purchase_price
  FROM Product;

-- 4
-- 两个SET写在一个UPDATE会导致margin没有变化仍为1200
BEGIN TRANSACTION;
    UPDATE ProductMargin
       SET sale_price = 3000
     WHERE product_name = '运动T恤';
     
    UPDATE ProductMargin
       SET margin = sale_price - purchase_price
     WHERE product_name = '运动T恤';
COMMIT;
```