chapter8-贪婪算法
================

## 贪婪算法

每步都选择局部最优解

##  8.3 covered set

```python3
stations = dict()
stations["kone"] = set(["id", "nv", "ut"])
stations["ktwo"] = set(["wa", "id", "mt"])
stations["kthree"] = set(["or", "nv", "ca"])
stations["kfour"] = set(["nv", "ut"])
stations["kfive"] = set(["ca", "az"])

states_needed = set(["mt", "wa", "or", "id", "nv", "ut", "ca", "az"])


def calculate_stations(stations, states_needed):
    """
    集合覆盖问题: 选择尽可能少的集合覆盖
    """
    final_stations = set()

    while states_needed:
        best_station = None
        states_covered = set()

        for station in stations:
            covered = stations[station] & states_needed
            if len(covered) > len(states_covered):
                best_station = station
                states_covered = covered

        final_stations.add(best_station)
        states_needed -= states_covered

    return final_stations


if __name__ == '__main__':
    print(calculate_stations(stations, states_needed))

```

## 8.4 NP完全问题

1. 元素较少时算法的运行速度非常快,但随着元素数量的增加,速度会变得非常慢。
2. 涉及“所有组合”的问题通常是NP完全问题。
3. 不能将问题分成小问题,必须考虑各种可能的情况。这可能是NP完全问题。
4. 如果问题涉及序列(如旅行商问题中的城市序列)且难以解决,它可能就是NP完全问题。
5. 如果问题涉及集合(如广播台集合)且难以解决,它可能就是NP完全问题。
6. 如果问题可转换为集合覆盖问题或旅行商问题,那它肯定是NP完全问题。

## 8.5

1. 贪婪算法寻找局部最优解，企图以这种方式获得全局最优解
2. 贪婪算法易于实现、运行速度快，是不错的近似算法