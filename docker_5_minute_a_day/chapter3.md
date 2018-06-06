chapter3-Docker镜像
===================

## 3.1 镜像内部结构

### 3.1.1 hello-world

[hello-world's Dockerfile](https://github.com/docker-library/hello-world/blob/b0a34596994b120f5456f08992ef9a75ed56f34e/amd64/hello-world/Dockerfile)

```
# 下载image
$ docker pull hello-world

# Dockerfile
# 定义如何构建image
# hello-world's Dockerfile
FROM scratch
COPY hello /
CMD ["/hello"]
```

### 3.1.2 base image

1. 从scratch构建
2. 用于拓展其他image
3. 一般为Linux发行版的docker image

container只能公用host的kernel无法对kernel升级,若对kernel有要求的项目可能更适合虚拟机

image: read only; container: r & w, 保存image的变化部分

## 3.2 构建镜像

### 3.2.1 docker commit

```
# 1. run container
# -it: 交互模式进入容器并打开终端
$ docker run -it ubuntu

# 2. modify container
root@d08e11aad92f:/# apt update && apt install -y vim

$ docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
d08e11aad92f        ubuntu              "/bin/bash"         6 minutes ago       Up 6 minutes                            nervous_dijkstra

# 3. save container to a new image
$ docker commit nervous_dijkstra ubuntu1804_vim

$ docker images
REPOSITORY             TAG                 IMAGE ID            CREATED             SIZE
ubuntu1804_vim         latest              961079755480        46 seconds ago      180MB
ubuntu                 latest              452a96d81c30        4 weeks ago         79.6MB

# 4. create a new container from new image
$ docker run -it ubuntu1804_vim
```

不推荐使用commit创建image:

1. 手工创建易出错,复用性差
2. 使用者无法得知image创建过程,无法进行安全审计
3. Dockerfile底层也是使用commit一层层提交

### 3.2.2 Dockerfile

```
# ./Dockerfile
FROM ubuntu
RUN apt update && apt install vim -y

# 通过Dockerfile构建image
$ docker build -t ubuntu1804_vim .
# 发送上下文但不会把当前目录下的文件直接复制到新container
Sending build context to Docker daemon  3.072kB
Step 1/2 : From ubuntu
 ---> 452a96d81c30
Step 2/2 : RUN apt update && apt install vim -y
 ---> Running in e66d1155f1b9

...
install vim
...

Removing intermediate container e66d1155f1b9
 ---> 731a95e4080d
Successfully built 731a95e4080d
Successfully tagged ubuntu1804_vim:latest
```

docker image分层结构,Dockerfile任一指令都会创建一个镜像层,上层依赖下层,只要一层变化则上面到缓存都失效。即改变指令顺序或者修改添加指令缓存都会失效。

docker history: 查看image分层结构

#### 3.2.2.4

Dockerfile -> image: 失败前一个image可用于调试

1. base image -> a new container
2. CMD -> modify container
3. docker commit -> a new image
4. new image -> next new container
5. repeat 2->4

#### 3.2.2.5 常用Dockerfile命令

```
1. FROM:
指定base image

2. MAINTAINER
设置作者

3. COPY
从build context复制文件到image
COPY src dest / COPY ["src", "dest"]
src只能为context中的内容

4. ADD
和COPY相似,若src是archive,自动解压到dest

5. ENV
设置环境变量,环境变量可以被后面的指令使用
栗子: ENV MY_VERSION 1.3 RUN apt install mypackage=$MY_VERSION -y

6. EXPOSE
指定容器中的进程会监听某个端口,docker可以将该端口暴露,wait until network

7. VOLUMN
将文件或者目录声明为volumn,wait until storage

8. WORKDIR
为后面的RUN, CMD, ENTRYPOINT, ADD, COPY设置镜像的当前工作目录,若不存在会创建目录

9. RUN
容器中运行指定命令

10. CMD
容器启动时运行指定命令,创建新的image
Dockerfile可以有多个CMD但只有最后一个生效,并且可以被docker run后的参数替换

11. ENTRYPOINT
设置容器启动运行的命令
Dockerfile可以有多个ENTRYPOINT但只有最后一个生效,CMD或docker run后的参数会被当作参数传递给ENTRYPOINT

12. #
注释
```

## 3.3 RUN, CMD, ENTRYPOINT

```
shell/exec

# shell
<docker instruction> <command>
# 暂时理解为shell bash -c 做bash的变量$VAR替换

# exec
# 将"executable", "param1", "param2", ... 直接拼接不做变量替换
<docker instruction> ["executable", "param1", "param2", ...]
# 如果要使用环境变量
ENV var world ENTRYPOINT ["/bin/sh", "-c", "echo hello $var"]
```

### 3.3.2

RUN: 安装应用或者软件包,创建新image

RUN apt update && apt install -y ...  # 如果单独行apt update会创建新image可能是之前的cache

### 3.3.3

CMD: 容器启动且docker run没有指定其他命令时运行

docker run若指定其他命令则CMD被忽略,多个CMD只有最后的生效

1. CMD ["executable", "param1", "param2"]
2. CMD ["param1", "param2"]  # 为ENTRYPOINT提供额外参数,ENTRYPOINT必须使用exec格式,不管出现在ENTRYPOINT的前后
3. CMD param1 param2

### 3.3.4

ENTRYPOINT: 容器以应用或者服务形式运行,不会被忽略一定会被执行,即使docker run指定了其他命令

1. ENTRYPOINT ["executable", "param1", "param2"]
2. ENTRYPOINT param1 param2  # 忽略任何CMD或者docker run提供的参数

ENTRYPOINT的exec可以用CMD提供*额外*默认参数,ENTRYPOINT中的参数始终会被使用,CMD提供的默认参数可以在启动时替换

栗子: ENTRYPOINT ["/bin/echo", "hello"] CMD ["world"]

docker run -it [image] / docker run -it [image] everybody

**CMD && ENTRYPOINT 都只有最后一个生效**

### 3.3.5

优先使用exec格式ENTRYPOINT,CMD提供默认参数,docker run可替换默认参数

## 3.4 分发镜像

### 3.4.1 镜像命名

```
[image name] = [repository]:[tag]

使用镜像时最好避免使用latest,使用指定tag

$ docker tag ubuntu [repository]:[tag]

默认规定:
image:1 分支1中最新
iamge:1.7 1.7.x最新
```

### 3.4.2 docker hub

```
$ docker login -u username

# 若username和登陆用户不同无法上传
$ docker tag ubuntu username/ubuntu:v1
$ docker push username/ubuntu:v1
```

### 3.4.3 本地registry

```
$ docker run -d -p 5000:5000 /myregistry:/var/lib/registry registry:2

$ docker tag username/ubuntu:v1 registry_host:port/username/ubuntu:v1
# registry_host:port 运行了registry的主机及端口
$ docker push registry_host:port/username/ubuntu:v1

$ docker pull registry_host:port/username/ubuntu:v1
```
