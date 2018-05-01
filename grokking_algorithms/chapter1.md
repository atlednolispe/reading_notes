chapter1-算法简介
================

## 1.2 二分查找

二分查找的前提条件: 有序!

n个数字通过二分查找得到结果最多需要[log<sub>2</sub><sup>n</sup>]+1次,书中P6写的是log<sub>2</sub><sup>n</sup>是不对的,用于解决练习1.1 & 1.2

```python3
"""
二分查找
"""


def binary_search(array, item):
    """
    :param array: 必须有序
    """
    low = 0
    hi = len(array) - 1

    while low <= hi:
        mid = (low + hi) // 2
        guess = array[mid]

        if guess == item:
            return mid
        elif guess > item:
            hi = mid - 1
        else:
            low = mid + 1
    return None


if __name__ == '__main__':
    my_list = list(range(1, 10, 2))

    print(binary_search(my_list, 3))
    print(binary_search(my_list, -1))

```

## 1.3 大O表示法

同阶无穷大代表运行*时间*的增大速率,大O代表时间的上限

练习: 使用大O表示法给出下述各种情形的运行时间
    1.3 在电话簿中根据名字查找电话号码。 O(log<sup>n</sup>)
    1.4 在电话簿中根据电话号码找人。 O(n)
    1.5 阅读电话簿中每个人的电话号码。 O(n)
    1.6 阅读电话簿中姓名以A打头的人的电话号码。这个问题比较棘手，它涉及第4章的概念。答案可能让你感到惊讶! O(n)
