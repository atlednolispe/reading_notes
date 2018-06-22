chapter7-多主机管理
===================

## 7.2 Docker Machine

[install docker machine](https://docs.docker.com/machine/install-machine/)

```
# 安装docker machine
# Linux
$ base=https://github.com/docker/machine/releases/download/v0.14.0 &&
  curl -L $base/docker-machine-$(uname -s)-$(uname -m) >/tmp/docker-machine &&
  sudo install /tmp/docker-machine /usr/local/bin/docker-machine

# 验证docker machine版本
$ docker-machine version
docker-machine version 0.14.0, build 89b8332

# 安装补全脚本
$ base=https://raw.githubusercontent.com/docker/machine/v0.14.0
for i in docker-machine-prompt.bash docker-machine-wrapper.bash docker-machine.bash
do
  sudo wget "$base/contrib/completion/bash/${i}" -P /etc/bash_completion.d
done

# terminal载入prompt
$ source /etc/bash_completion.d/docker-machine-prompt.bash

# 但这种提示符只能在bash使用,zsh中这样配置无法正确显示
[user@ubuntu ~ [host1]]$ eval $(docker-machine env host1)
```

## 7.3 创建machine

首先需要daemon可以免密登录主机:
[免密登录](https://github.com/atlednolispe/dwm/blob/master/Linux/aliyun_pem.md#linux-%E5%85%8D%E5%AF%86%E7%99%BB%E5%BD%95)

之后在/etc/sudoers添加以下设置,[sudo: no tty present and no askpass program
specified](https://github.com/docker/machine/issues/3554)

username ALL=(ALL) NOPASSWD: ALL

```
# 创建machine,并修改远程主机hostname
$ docker-machine create --driver generic --generic-ip-address=host_ip --generic-ssh-user username host1

# 远程主机建立tcp连接接受daemon控制
$ cat /etc/systemd/system/docker.service.d/10-machine.conf 
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2376 -H unix:///var/run/docker.sock
--storage-driver aufs --tlsverify --tlscacert /etc/docker/ca.pem --tlscert
/etc/docker/server.pem --tlskey /etc/docker/server-key.pem --label
provider=generic 
Environment=

$ netstat -tnlp
(Not all processes could be identified, non-owned process info
 will not be shown, you would have to be root to see it all.)
 Active Internet connections (only servers)
 Proto Recv-Q Send-Q Local Address           Foreign Address         State
 PID/Program name    
 ...
 tcp6       0      0 :::2376                 :::*                    LISTEN
 ...
 -  
```

## 7.4 管理machine

```
# 执行后所有执行的docker命令都在host1执行
$ eval $(docker-machine env host1)


# 可以切换docker主机
$ eval $(docker-machine env host2)

# 重启host操作系统
$ docker-machine start/stop/restart host1

$ docker-machine scp host1:/tmp/a host2:/tmp/b
```
