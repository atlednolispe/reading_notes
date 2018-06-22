chapter8-容器网络
=================

## 8.2 overlay

```
# docker overlay需要k-v数据库,在daemon中以容器运行consul
$ docker run -d -p 8500:8500 -h consul --name consul progrium/consul -server
-bootstrap

# 修改cluster配置文件
$ docker-machine ssh xmubuntu1804
$ sudo vim /etc/systemd/system/docker.service.d/10-machine.conf
...provider=generic --cluster-store=consul://daemon_ip:8500 --cluster-advertise=cluster_ip:2376

# 重启cluster的服务
$ systemctl daemon-reload
$ systemctl restart docker.service

可以在daemon:8500的key-value中看见新注册的nodes
```
