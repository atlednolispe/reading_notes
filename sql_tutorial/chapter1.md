chapter1-数据库和SQL
===================


## 1.2 数据库的结构

根据SQL语句的内容返回的数据同样必须是二维表的形式

关系数据库必须以行为单位进行数据读写,一个单元格中只能输入一个数据

## 1.3 SQL概要

### SQL语句种类

DDL - Data Definition Language

1. CREATE
2. DROP
3. ALTER

DML - Data Manipulation Language

1. SELECT
2. INSERT
3. UPDATE
4. DELETE

DCL - Data Control Language

1. COMMIT
2. ROLLBACK
3. GRANT
4. REVOKE

### SQL的基本书写规则

1. SQL语句要以分号(;)结尾
2. SQL语句不区分大小写,插入单元格的数据区分大小写,建议:关键字大写,表名的首字母大写,其余(列名等)小写,数据库中实际存储的表名是小写的
3. 常数规则: 字符串和日期常数需要使用单引号(')括起来,数字常数无需加注单引号(直接书写数字即可)
4. 单词需要用半角空格或者换行来分隔

## 1.4 表的创建

### 创建数据库

‵‵‵sql
CREATE DATABASE shop;
‵‵‵

### 创建表

```sql
CREATE TABLE Product
(product_id      CHAR(4)      NOT NULL,
 product_name    VARCHAR(100) NOT NULL,
 product_type    VARCHAR(32)  NOT NULL,
 sale_price      INTEGER ,
 purchase_price  INTEGER ,
 regist_date     DATE ,
 PRIMARY KEY (product_id));
```

### 命名规则

只能使用半角英文字母、数字、下划线(_)作为数据库、表和列的名称,且必须以半角英文字母开头。另外,在同一个数据库中不能创建两个相同名称的表,在同一个表中也不能创建两个名称相同的列。

### 数据类型的指定

所有的列都必须指定数据类型

四种基本的数据类型：

1. INTEGER
2. CHAR(定长字符串): CHAR(8)类型的列存储'abc',实际存储的为'abc[5 space]'
3. VARCHAR(可变长字符串): 'abc' -> 'abc',Oracle中使用VARCHAR2型
4. DATE(年月日): Oracle还包含时分秒

### 约束设置

1. NOT NULL: 必须输入数据的约束
2. 主键: PRIMARY KEY (product_id),可以唯一确定一行数据的列。"键"就是在指定特定数据时使用的列的组合。

## 1.5 表的删除和更新

```sql
DROP TABLE Product;
```

```sql
-- 添加列
ALTER TABLE Product ADD COLUMN product_name_pinyin VARCHAR(100);

-- 删除列
ALTER TABLE Product DROP COLUMN product_name_pinyin;

-- 插入数据
INSERT INTO Product VALUES (4000, 2800, NULL);

-- 重命名表名
-- 1. PostgreSQL
ALTER TABLE Poduct RENAME TO Product;
-- 2. MySQL
RENAME TABLE Poduct to Product;
```

## Ex

```sql
-- 1
CREATE TABLE Addressbook
(regist_no      INTEGER         NOT NULL,
 name           VARCHAR(128)    NOT NULL,  
 address        VARCHAR(256)    NOT NULL,
 tel_no         CHAR(10),
 mail_address   CHAR(20),
 PRIMARY KEY (regist_no)
);

-- 2
ALTER TABLE Addressbook ADD COLUMN postal_code CHAR(8) NOT NULL;

-- 3
DROP TABLE Addressbook;

-- DATAGRIP 切换PostgreSQL数据库
-- 右上角切换search path
```

[search path](https://www.jetbrains.com/help/datagrip/writing-and-executing-sql-commands.html#controlling_search_path)
