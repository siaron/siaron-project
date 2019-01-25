# kafka
- 默认端口 `9092`
## 安装
- `wget http://ftp.riken.jp/net/apache/kafka/2.1.0/kafka_2.11-2.1.0.tgz`
- `tar -xzf kafka_2.11-2.1.0.tgz`
- `cd kafka_2.11-2.1.0`
## 启动
- kafka是依赖zookeeper的,但是自己带了一个单机的zk
- 启动zk
    - `bin/zookeeper-server-start.sh config/zookeeper.properties`
- 启动kafka
    - `bin/kafka-server-start.sh config/server.properties`
## 使用
- 创建一个topic
    - 
- 发送一些消息
- 启动消费者
## 安装kafka-manager
## 安装kafka-monitor
