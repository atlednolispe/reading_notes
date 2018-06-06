chapter4-Docker容器
===================

## 4.1 运行容器

### 4.1.2 进入容器

```
# 好像只能进入running的容器

# 1. 直接进入启动命令的终端,不启动新进程
# ctrl+p & ctrl+q退出
$ docker attach [CONTAINER ID]

# 2. 容器中打开xin终端,并且可以启动新进程
$ docker exec -it [CONTAINER ID] bash
$ exit

# 查看容器启动命令输出 -f: tail -f
$ docker logs -f [CONTAINER ID]
```

1. CMD/ENTRYPOINT/DOCKER RUN指定的命令结束后容器停止
2. -d: 后台启动容器
3. run -it: 容器启动后直接进入
4. exec -it: 进入容器并执行命令

### 4.2 - 4.4 容器常用操作

```
$ docker start
$ docker stop
# restart = stop + start
$ docker restart

# 重启参数
# --restart=always
# --restart=on-failure:3

$ docker pause/unpause

$ docker rm [container1] [container2]
$ docker rm -v $(docker ps -aq -f status=exited)
```

## 4.5 State Machine

docker stop/kill 不会判断是否需要自动重启

## 4.6 资源限制

```
# 1. memory

# 最多200M内存,100Mswap
$ docker run -m 200M --memory-swap 300M ubuntu

# 2. CPU

# 分配的是权重,只有在CPU资源紧张时生效
$ docker run -c 1024 ubuntu

# 3. Block IO

# --blkio-weight: 优先级
# bps: 每秒读写数据量  iops: 每秒IO次数
# --device-read-bps, --device-write-bps
# --device-read-iops, --device-write-iops
```

## 4.7 实现容器的底层技术

cgroup: 资源限额;  namespace: 资源隔离;

```
# ubuntu
# cgroup
/sys/fs/cgroup/[cpu, memory, blkio]/docker/

# namespace
mount(/)/UTS(hostname)/IPC/PID/NETWORK/USER
```