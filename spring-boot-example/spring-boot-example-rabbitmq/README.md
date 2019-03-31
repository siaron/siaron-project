# rabbtimq
- 默认端口 `5672`,web端口`15672`,mqtt端口`1883`,stomp端口`6372`
## 安装
- `yum -y install make gcc gcc-c++ kernel-devel m4 ncurses-devel openssl-devel unixODBC unixODBC-devel httpd python-simplejson`
- `wget --content-disposition https://packagecloud.io/rabbitmq/erlang/packages/el/7/erlang-21.1-1.el7.centos.x86_64.rpm/download.rpm`
- `rpm -ivh erlang-21.1-1.el7.centos.x86_64.rpm`
- `wget https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.8/rabbitmq-server-3.7.8-1.el7.noarch.rpm`
- `yum install rabbitmq-server-3.7.8-1.el7.noarch.rpm`
## 启动
- `rabbitmq-server -detached`
## 启用web管理插件
- `rabbitmq-plugins enable rabbitmq_management`
## 访问限制
- `rabbitmqctl add_user admin admin@mqtt`
- `rabbitmqctl set_user_tags admin administrator`
## 启用mqtt,stomp插件
- `rabbitmq-plugins enable rabbitmq_mqtt`
- `rabbitmq-plugins enable rabbitmq_stomp`
- `rabbitmq-plugins enable rabbitmq_sharding` 消息sharing插件,消息到exchange后会经过这个插件分布到不同queue
- `rabbitmq-plugins enable rabbitmq_event_exchange` 可以得到vhost,exchange等的全生命周期的监控,create,delete

#docker rabbitmq
- `docker pull rabbitmq`
- `docker network create -d bridge rabbit-net`
-  运行
```
docker run -d --hostname my-rabbit --network rabbit-net --name rabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15676:15672 -p 5674:5672 -p 61615:61613 -p 1885:1883 -p 15694:15674 rabbitmq:management
```