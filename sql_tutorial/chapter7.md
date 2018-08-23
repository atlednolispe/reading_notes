chapter7-集合运算
================

## 7.1 表的加减法

```sql 
UNION/UNION ALL
-- 1. 运算对象的记录的列数必须相同
-- 2. 运算对象的记录中列的类型必须一致
-- 3. 可以使用任何SELECT语句,但ORDER BY子句只能在最后使用一次

-- ALL: 保留重复行

INTERSECT/INTERSECT ALL

EXCEPT = MINUS(Oracle)

-- 优先级 EXCEPT > INTERSECT > UNION
```

## 7.2 联结(以列为单位对表进行联结)

```sql
-- INNER JOIN
-- 进行内联结时必须使用ON子句,并且要书写在FROM和WHERE之间
-- 使用联结时SELECT子句中的列需要按照“<表的别名>.<列名>”的格式进行书写,同时存在于两张表中的列必须这样写
-- 联结的表只在SELECT语句执行期间存在
SELECT SP.shop_id, 
       SP.shop_name, 
       SP.product_id, 
       P.product_name,
       P.sale_price
  FROM ShopProduct AS SP INNER JOIN Product AS P
    ON SP.product_id = P.product_id;
 WHERE SP.shop_id = '000A';

-- OUTER JOIN
-- RIGHT LEFT决定主表
-- 选取出单张表中全部的信息
SELECT SP.shop_id, SP.shop_name, SP.product_id, P.product_name, P.sale_price
  FROM ShopProduct AS SP RIGHT OUTER JOIN Product AS P
    ON SP.product_id = P.product_id;

-- 多表联结
SELECT SP.shop_id, SP.shop_name, SP.product_id, P.product_name, P.sale_price, IP.inventory_quantity
  FROM ShopProduct AS SP INNER JOIN Product AS P
    ON SP.product_id = P.product_id
           INNER JOIN InventoryProduct AS IP
              ON SP.product_id = IP.product_id
WHERE IP.inventory_id = 'S001';

-- CROSS JOIN
SELECT SP.shop_id, SP.shop_name, SP.product_id, P.product_name
  FROM ShopProduct AS SP CROSS JOIN Product AS P;

-- 关系除法就是CROSS JOIN的逆运算
SELECT DISTINCT emp
  FROM EmpSkills ES1
 WHERE NOT EXISTS
           (SELECT skill
              FROM Skills
            EXCEPT
            SELECT skill
              FROM EmpSkills ES2
             WHERE ES1.emp = ES2.emp);
```

## Ex

```sql
-- 2
SELECT COALESCE(SP.shop_id, '不确定') AS shop_id,
       COALESCE(SP.product_id, '不确定') AS product_id,
       P.product_name,
       P.sale_price
  FROM ShopProduct AS SP RIGHT JOIN Product AS P
    ON SP.product_id = P.product_id;
```