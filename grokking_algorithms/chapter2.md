chapter2-选择排序
================

## 2.3 选择查找

```python3
"""
选择排序
"""


def find_smallest(arr):
    """
    从头到尾检查min。
    """
    smallest = arr[0]
    smallest_index = 0

    for i in range(1, len(arr)):
        if arr[i] < smallest:
            smallest = arr[i]
            smallest_index = i

    return smallest_index


def selection_sort(arr):
    """
    如果传入数组非空,则每次取出数组最小数加入结果数组。
    """
    result = []

    while len(arr):
        smallest_index = find_smallest(arr)
        result.append(arr.pop(smallest_index))

    return result


if __name__ == '__main__':
    print(selection_sort([5, 3, 6, 2, 10]))

```

## 对于插入的一点简单注解

对于插入到某个指定元素后,对于无序的数组而言要找到指定元素也需要和链表一样从头遍历数组,而两者比较的插入O(n), O(1)的时间复杂度是对插入的操作而言而不包含查找指定的元素。