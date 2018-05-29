chapter9-动态规划
================

## 9.1 背包问题

# 如果不选当前行商品则最大价值为CELL[i-1][j],选择了当前行物品则只能增加剩余容量的最优解

CELL[i][j] = max{CELL[i-1][j], 当前行商品的价值 + CELL[i-1][j-当前行商品重量]}


## 9.2.3

逐列填充网格为什么可能有影响?

## 9.2.7

动态规划只能解决子问题互斥的问题

## 9.3.3 最长公共子串

```
# i,j equal -> i-1,j-1 equal
if word_a[i] == word_b[j]: 
    cell[i][j] = cell[i-1][j-1] + 1
else:
    cell[i][j] = 0
```

## 9.3.5 LCS

最长公共子序列的算法

```
if word_a[i] == word_b[j]:
    cell[i][j] = cell[i-1][j-1] + 1
else:
    cell[i][j] = max(cell[i-1][j], cell[i][j-1])
```