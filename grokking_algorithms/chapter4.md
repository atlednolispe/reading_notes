chapter4-快速排序
================

## 4.1 Divide & Conquer

```python3
"""
1. find base case
2. divide to base case
"""


def sum_of_array(arr):
    """
    4.1 通过递归对数组求和
    """
    if not len(arr):
        return 0
    else:
        return arr[0] + sum_of_array(arr[1:])


def length_of_array(arr):
    """
    4.2 通过递归计算列表包含元素个数
    """
    if not len(arr):
        return 0
    else:
        return 1 + length_of_array(arr[1:])


def biggest(arr):
    """
    4.3 通过递归求列表最大值
    """
    if not len(arr):
        return
    elif arr[0] and biggest(arr[1:]):
        return arr[0] if arr[0] > biggest(arr[1:]) else biggest(arr[1:])
    else:
        return arr[0]


"""
4.4 二分查找

base case: 
if l > r:
    return

if l == r == num:
    return l
    
recursive case:
if l < r 
"""


if __name__ == '__main__':
    print(sum_of_array([2, 4, 6]))
    print(length_of_array([2, 4, 6]))
    print(biggest([2, 4, 6]))
    print(biggest([]))
```

## 4.2 快速排序

```python3
def quick_sort(array):
    """
    快速排序: 通过基准值将数组分为小于基准值,基准值,大于基准值三部分,递归排序

    每次随机选择pivot以达到最佳状况

    运行时间O(n*log(n)): 每层的操作数都为O(n)*O(log(n))层
    O(n):
          第一层 对n-1个数进行比较操作
          第二层 对n-2/n-3个数进行比较操作
          ...
          所以每层都是O(n)次操作
    """
    if len(array) < 2:
        return array
    else:
        pivot = array[0]
        less = [i for i in array[1:] if i <= pivot]
        greater = [i for i in array[1:] if i > pivot]

        return quick_sort(less) + [pivot] + quick_sort(greater)


if __name__ == '__main__':
    print(quick_sort([10, 5, 2, 3]))

```