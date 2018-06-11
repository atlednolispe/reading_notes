chapter5-Docker网络
===================

## 5.1 none

```
# 隔离环境
$ docker run -it --network=none busybox
```

## 5.2 host

```
# ubuntu上显示是host的网卡
# ? mac上面显示的不是mac的hostname和eth
$ docker run -it --network=host
```

## 5.3 bridge

```
# 默认创建的network就是bridge
$ docker run -d httpd
```

半路出家对于网络真的是一点都不懂啊,之后找别的东西了解一下。

docker安装时会创建一个docker0的linux bridge,也就是docker bridge的网关172.17.0.1,brctl show可以查看bridge配置,docker0上面挂载的虚拟网卡vethxxx和容器内的eth0@xxx网卡是一对veth pair。

## 5.4 user-defined

```
# 创建bridge网桥
# 默认subnet 172.18.0.0/24, gateway 172.18.0.1/16
$ docker network create --driver bridge my_net

$ docker network create --driver bridge --subnet 172.22.16.0/24 --gateway 172.22.16.1 mynet

# 指定容器ip
# 只有使用--subnet创建的网络才可以指定静态ip
$ docker run -it --network=mynet --ip 172.22.16.8 busybox

# 同一网络的容器和网关之间可以通信

# 不同网络的容器间的通信
# 给容器container_id添加了一块网卡连接到another network的网关
$ docker network connect [another network] container_id

# 不同容器网络间由于配置了防火墙DOCKER-ISOLATION做到隔离
# 我的mac上没有配置connect不同bridge也可以通信..好像是没有配置iptables的
```

## 5.5 容器间通信

```
# 有同属于一个网络的网卡容期间便可以通信
# 1. --network
# 2. connect

# docker dns server
# 必须为user-defined不能是默认的bridge
$ docker run -d --network=mynet httpd

# joined
# 多个容器共享网络栈,共享网卡和配置信息,可以通过127.0.0.1直接通信
# 常用于容器间通过lo快速通信,或是监控其他容器网络流量
$ docker run -it --network=container:[container_id] httpd
```

## 5.6 容器和外部连接

```
# 1. container访问外部
# 将容器的外出包经过iptables的NAT处理将源地址转换为host再发送

# 2. 外部访问container
# 通过端口映射,每个映射端口都会启动一个docker-proxy
$ docker run -d -p [host port]:[container port] httpd
```