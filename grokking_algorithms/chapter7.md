chapter7-狄克斯特拉算法
=====================

```
Dijkstra’s algorithm
1. 找出最便宜节点
2. 更新该节点的邻居的开销,记录父节点
3. 对下一个最便宜节点重复过程1&2,直到每个节点都做了操作
4. 计算最终路径

* 不能将狄克斯特拉算法用于包含负权边的图,可使用另一种算法——贝尔曼-福德算法(Bellman-Ford algorithm)
```

```python3
inf = float("inf")


def generate_graph1():
    graph1 = dict()

    graph1["start"] = {}
    graph1["start"]["a"] = 6
    graph1["start"]["b"] = 2

    graph1["a"] = {}
    graph1["a"]["fin"] = 1

    graph1["b"] = {}
    graph1["b"]["a"] = 3
    graph1["b"]["fin"] = 5

    graph1["fin"] = {}

    costs1 = {'a': 6, 'b': 2, 'fin': inf}
    parents1 = {'a': 'start', 'b': 'start'}

    return graph1, costs1, parents1


def generate_graph2():
    graph2 = dict()

    graph2["start"] = {}
    graph2["start"]["a"] = 5
    graph2["start"]["b"] = 2

    graph2["a"] = {}
    graph2["a"]["c"] = 4
    graph2["a"]["d"] = 2
    graph2["b"] = {}
    graph2["b"]["a"] = 8
    graph2["b"]["d"] = 7

    graph2["c"] = {}
    graph2["c"]["d"] = 6
    graph2["c"]["fin"] = 3
    graph2["d"] = {}
    graph2["d"]["fin"] = 1

    graph2["fin"] = {}

    costs2 = {'a': 5, 'b': 2, 'c': inf, 'd': inf, 'fin': inf}
    parents2 = {'a': 'start', 'b': 'start'}

    return graph2, costs2, parents2


def generate_graph3():
    graph3 = dict()

    graph3["start"] = {}
    graph3["start"]["a"] = 10

    graph3["a"] = {}
    graph3["a"]["b"] = 20
    graph3["b"] = {}
    graph3["b"]["c"] = 1
    graph3["b"]["fin"] = 30
    graph3["c"] = {}
    graph3["c"]["a"] = 1

    graph3["fin"] = {}

    costs3 = {'a': 10, 'b': inf, 'c': inf, 'fin': inf}
    parents3 = {'a': 'start'}

    return graph3, costs3, parents3


def generate_graph4():
    """
    不能将狄克斯特拉算法用于包含负权边的图
    """
    return None


def find_lowest_cost_node(costs, processed):
    lowest_cost = inf
    lowest_cost_node = None

    for node in costs:
        cost = costs[node]
        if node not in processed and cost < lowest_cost:
            lowest_cost = cost
            lowest_cost_node = node

    return lowest_cost_node


def dijkstra(graph: dict, costs: dict, parents: dict):
    """
    :param graph: 节点权重
    :param costs: 所有节点的费用
    :param parents: 父节点为起始节点的所有节点
    """
    processed = []
    node = find_lowest_cost_node(costs, processed)

    while node is not None:
        cost = costs[node]
        neighbors = graph[node]
        for neighbor in neighbors:
            new_cost = cost + neighbors[neighbor]
            if new_cost < costs[neighbor]:
                costs[neighbor] = new_cost
                parents[neighbor] = node

        processed.append(node)
        node = find_lowest_cost_node(costs, processed)

    return costs


if __name__ == '__main__':
    graph1, costs1, parents1 = generate_graph1()
    graph2, costs2, parents2 = generate_graph2()
    graph3, costs3, parents3 = generate_graph3()

    costs1 = dijkstra(graph1, costs1, parents1)
    costs2 = dijkstra(graph2, costs2, parents2)
    costs3 = dijkstra(graph3, costs3, parents3)

    print(costs1)
    print(costs2)
    print(costs3)

```

