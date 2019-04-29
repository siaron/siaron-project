hazelcast-demo
- https://github.com/hazelcast/hazelcast
- https://hazelcast.com/hazelcast-backbone-iot-internet-things/
- https://hazelcast.com/hazelcast-low-latency-datastores-iot/

docker
- pull
```
$ docker run hazelcast/hazelcast:$HAZELCAST_VERSION
```
- run
```
$ docker run -e JAVA_OPTS="-Dhazelcast.local.publicAddress=<host_ip>:5701" -p 5701:5701 hazelcast/hazelcast
$ docker run -e JAVA_OPTS="-Dhazelcast.local.publicAddress=<host_ip>:5702" -p 5702:5701 hazelcast/hazelcast 
```
- 内存计算
 - Hazelcast的拓扑结构
   - 内嵌模式
   - 客户端/服务器部署模式