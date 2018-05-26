chapter6-广度优先搜索
===================

## 6.3 BFS

key: 需要按序检查

解决两类问题:

1. 是否存在路径
2. 最短路径

```python3
from collections import deque


graph = dict()
graph["you"] = ["alice", "bob", "claire"]
graph["bob"] = ["anuj", "peggy"]
graph["alice"] = ["peggy"]
graph["claire"] = ["thom", "jonny"]
graph["anuj"] = []
graph["peggy"] = []
graph["thom"] = []
graph["jonny"] = []


def person_is_seller(person):
    return person[-1] == 'm'


def search(person):
    """
    使用双端队列实现广度优先算法来检查邻居

    检查好友是否是seller,若不是将好友对好友全部添加到队列尾部
    """
    search_queue = deque()
    search_queue.extend(graph[person])
    searched = []  # 检查过的对象不再检查否则死循环

    while search_queue:
        person = search_queue.popleft()

        if person not in searched:
            if person_is_seller(person):
                print(person + ' is a mango seller!')
                return True
            else:
                search_queue.extend(graph[person])
                searched.append(person)

    return False


if __name__ == '__main__':
    search("you")

```

## 6.5

树: 其中任意两个顶点间存在唯一路径