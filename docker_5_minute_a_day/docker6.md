chapter6-Docker存储
===================

## 6.1 storage driver

新数据在容器分层结构的最上方的可写容器层

## 6.2 Data Volumn

Docker host文件系统的目录/文件 -> mount到容器文件系统

### 6.2.1 bind mount

host上存在的dir/file -> mount到容器,若容器中存在文件则会隐藏,可以设置权限

host-path指定的文件需要已经存在否则当作新目录绑定给容器

由于需要绑定数据限制了容器的可移植性

```
$ docker run -d [-v host-path:container-path:chmod]
```

### 6.2.2 docker managed volumn

[/var/lib/docker on mac](https://stackoverflow.com/questions/38532483/where-is-var-lib-docker-on-mac-os-x)

只能绑定到目录,且只能是读写权限

```
# 只要指明mount point
$ docker run -d [-v :container-path]

$ screen ~/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/tty
$ cd /var/lib/docker

# 不要用ctrl+a, d detach退出,会导致之后进入无法正常使用..(不明真相)
$ ctrl + a, :quit

# 若mount point指明的容器目录已经存在原有数据会被复制到volume,再将volume mount到mount point
linuxkit-025000000001:/var/lib/docker/volumes/4181b2927ea7945e32266cc0b8d5b5c138c44aa34dc0cb76c7d19c47fcc43d0b/_data# cat index.html
<html><body><h1>It works!</h1></body></html>
linuxkit-025000000001:/var/lib/docker/volumes/4181b2927ea7945e32266cc0b8d5b5c138c44aa34dc0cb76c7d19c47fcc43d0b/_data#

# 删除容器同时删除volume
$ docker rm -v container_name

# 删除所有未挂载的volume
$ docker volume rm $(docker volume ls -qf dangling=true)

docker volume只能查看managed volume,不能看bind mount,也无法知道volume对应容器,需要docker inspect
```

## 6.3 数据共享

### 6.3.1 container & host

```
# docker cp实现与/var/lib/docker/volumes/xxx之间的复制
$ docker cp container:path host-path
```

### 6.3.2 container & container

```
# bind mount
$ docker run --name web[i] -d -p 80 -v ~/htdocs:/usr/local/apache2/htdocs httpd
```

## 6.4 volume container

volume container: 专门为其他容器提供volume的容器,可以是bind mount/managed volume,但数据还是在host中

```
# 创建mount point要使用host的绝对路径否则会绑定到/var/lib/docker/volumes/xxx
$ docker create --name vc -v ~/htdocs:/usr/local/apache2/htdocs -v /other/useful/tools busybox

# 共享相同的volume,不同的容器只需要mount一次
$ docker run -d --name web[i] -d -p 80 --volumes-from vc httpd
```

## data-packed volume container

把数据打包到镜像后使用managed volume共享,不用依赖host提供数据

```
# VOLUME = -v
# Dockerfile
FROM busybox:latest
ADD htdocs /usr/local/apache2/htdocs
VOLUME /usr/local/apache2/htdocs

$ docker build -t datapacked .

$ docker create --name vc datapacked

$ docker run -d -p 80:80 --volumes-from vc httpd
```

## 6.6 data volume 生命周期

备份和恢复: filesystem的备份

迁移: 将host上的文件mount到新容器

删除: bind mount只能由host删除,managed volume使用-v删除,见6.2.2
