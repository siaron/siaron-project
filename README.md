# Environment
- Java8
- Spring Boot  2.1.1
- Spring Cloud Finchley.SR2

# siaron-project
 - other-example   
 - spring-boot-example   
 - spring-cloud-example
 - spring-example

# 示例教程 

# Docker
## 安装docker
###  卸载旧docker
```
 sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```
### 安装docker
```
sudo yum install -y yum-utils \
           device-mapper-persistent-data \
           lvm2
```
### docker 源
```
sudo yum-config-manager \
    --add-repo \
    https://mirrors.ustc.edu.cn/docker-ce/linux/centos/docker-ce.repo
```
### 安装 Docker CE (docker)
```
sudo yum makecache fast
sudo yum install docker-ce
```
### 启动 Docker CE
```
sudo systemctl enable docker
sudo systemctl start docker
```
## 基本命令
```
docker pull nginx
docker run -p 8088:80 --name mynginx -d nginx
docker exec -it mynginx bash
docker stop mynginx
docker rm mynginx
docker ps -a 
```
## docker 安装redis
```
docker pull redis
```
### docker redis 使用
```
cd ~
mkdir redis
cp /opt/redis-5.3/redis.conf ~/redis/
修改redis.conf 以下三项
# bind 127.0.0.1
protected-mode no
requirepass redis 

cd ~

启动
docker run -p 127.0.0.1:6389:6379  --name myRedis -v $PWD/redis/redis.conf:/etc/redis/redis.conf -v $PWD/redis:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes

停止和删除
docker stop myRedis && docker rm myRedis
```