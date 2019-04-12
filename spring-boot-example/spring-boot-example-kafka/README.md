# kafka
- 默认端口 `9092`
- mac 配置文件路径为: /usr/local/etc/kafka
- 启动: kafka-server-start server.properties
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
- 查看多少个topic
   - kafka-topics --zookeeper 127.0.0.1:2181 --list
- 查看topic下有多少的partition
   - kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic topic1
- 查看多少个 consumer-group ,详情
   - kafka-consumer-groups --bootstrap-server 127.0.0.1:9092 --list
   - kafka-consumer-groups --bootstrap-server 127.0.0.1:9092 --group topicGroupId --describe 
- 查看group 下多少个 consumer
   - 
   
- delete topic
    - kafka-topics  --delete --zookeeper 127.0.0.1:2181  --topic topic1
   
## 主要概念
kafka对保存消息是依据topic进行分类, kafka集群由多个kafka实例组成. 其依赖于zookeeper来保证系统可用性集群保存一些meta信息.

## Topics & Log
 一个topic 认为是一类消息, 每个topic 将会被分成多个partition(区),每个partition在存储层面都为一个append log 文件.任何发布到此partition的消息会被
 追加到log文件的尾部,每条消息在文件中都有一个offset (偏移量),offset 为一个long类型的数字,它唯一标识了一条消息,kafka并没有额外的索引来存储offset,因为在
 kafka中几乎不需要对消息进行 "随机读取"
 
 kafka和jms不同的是即使消息被消费,也不会被立刻删除,日志文件会根据broker的配置要求保留一段时间以后删除, 如文件保留两天, 那么两个天后会被删除, 无论是否被消费.
 kafka通过这种简单的手段来释放磁盘空间,以及减少消息消费后对文件内容改动磁盘的io开支
 
 对于消费者而言. 需要保存消费消息的offset,对于offset的保存和使用是有消费者来控制的.当正产消费时,offset会将线性向前驱动.消息被顺序消息.事实上消费者可以使用任意的
 顺序消费消息.它只需要重置offset即可. offset 会保存在zookeeper中.
 
 kafka本身不维护消费者和生产的状态信息,因为这些都是存在zookeeper中.因为消费者和生产者显得很轻量.
 
 partition目的有多个.最根本的原因是kafka是基于文件存储的.通过磁盘分区可以将日志内容分散在不同的磁盘上. 来避免文件尺寸达到单机磁盘的限制.没个partition都会被
 当前的kafka保存.可以将一个topic切分为任意的partition来保存消息/消费频率.此外多个partition意味着可以容纳更多的消费者, 来提高并发消费的能力
 
## Distribution 
 一个topic的过个partition可以被分散到多个server上. 每个server负责partition中消息的读写操作.此外kafka还可以配置partition的副本. 每个partition被北非到多台机器上,已提高可用性.
 
 基于 复制 的方案.那么一位置需要对个多备份进行调度,多个partition中都有一个leader,leader负责所有的读写操作, 如果leader失效会有其他的flower成为leader.
 
## Producers
 生产者将消息发送到指定的topic上. 同时Producer也能决定将此消息归属于哪个partition;比如基于”round-robin”方式或者通过其他的一些算法等.
 
## Consumers
 本质上kafka只支持topic, 每个消费者属于一个消费者组,反过来说.每个消费者组中可以有多个消费者.发送到topic的消息会被订阅此topic的每个group中的一个消费者消费
 
 如果所有的consumer都具有相同的group,这种情况和queue模式很像;消息将会在consumers之间负载均衡. 
 如果所有的consumer都具有不同的group,那这就是”发布-订阅”;消息将会广播给所有的消费者. 
 
 在kafka中,一个partition中的消息只会被group中的一个consumer消费;每个group中consumer消息消费互相独立;我们可以认为一个group是一个”订阅”者,一个Topic中的每个partions,只会被一个”订阅者”中的一个consumer消费,不过一个consumer可以消费多个partitions中的消息.kafka只能保证一个partition中的消息被某个consumer消费时,消息是顺序的.事实上,从Topic角度来说,消息仍不是有序的.
 
 kafka的原理决定,对于一个topic,同一个group中不能有多于partitions个数的consumer同时消费,否则将意味着某些consumer将无法得到消息.
##Guarantees
发送到partitions中的消息将会按照它接收的顺序追加到日志中

对于消费者而言,它们消费消息的顺序和日志中消息顺序一致.

如果Topic的”replicationfactor”为N,那么允许N-1个kafka实例失效. 

## 设计原理

1. 持久性
    因为kafka的日志为append操作,因为磁盘检索开销是小的.同时为了减少磁盘写入的次数.broker 会将消息暂存到buffer中. 等一定个数或者尺寸在flush到磁盘.减少io调用次数
 
2. 性能
    需要考虑的影响性能点很多,除磁盘IO之外,我们还需要考虑网络IO,这直接关系到kafka的吞吐量问题.kafka并没有提供太多高超的技巧;对于producer端,可以将消息buffer起来,当消息的条数达到一定阀值时,批量发送给broker;对于consumer端也是一样,批量fetch多条消息.不过消息量的大小可以通过配置文件来指定.对于kafka broker端,似乎有个sendfile系统调用可以潜在的提升网络IO的性能:将文件的数据映射到系统内存中,socket直接读取相应的内存区域即可,而无需进程再次copy和交换. 其实对于producer/consumer/broker三者而言,CPU的开支应该都不大,因此启用消息压缩机制是一个良好的策略;压缩需要消耗少量的CPU资源,不过对于kafka而言,网络IO更应该需要考虑.可以将任何在网络上传输的消息都经过压缩.kafka支持gzip/snappy等多种压缩方式.

3. 生产者
    负载均衡: producer将会和Topic下所有partition leader保持socket连接;消息由producer直接通过socket发送到broker,中间不会经过任何"路由层".事实上,消息被路由到哪个partition上,有producer决定.比如可以采用"random""key-hash""轮询"等,如果一个topic中有多个partitions,那么在producer端实现"消息均衡分发"是必要的.
 
    其中partition leader的位置(host:port)注册在zookeeper中,producer作为zookeeper client,已经注册了watch用来监听partition leader的变更事件.
    异步发送：将多条消息暂且在客户端buffer起来，并将他们批量的发送到broker，小数据IO太多，会拖慢整体的网络延迟，批量延迟发送事实上提升了网络效率。不过这也有一定的隐患，比如说当producer失效时，那些尚未发送的消息将会丢失。
4. 消费者
    consumer端向broker发送"fetch"请求,并告知其获取消息的offset;此后consumer将会获得一定条数的消息;consumer端也可以重置offset来重新消费消息.
     
    在JMS实现中,Topic模型基于push方式,即broker将消息推送给consumer端.不过在kafka中,采用了pull方式,即consumer在和broker建立连接之后,主动去pull(或者说fetch)消息;这中模式有些优点,首先consumer端可以根据自己的消费能力适时的去fetch消息并处理,且可以控制消息消费的进度(offset);此外,消费者可以良好的控制消息消费的数量,batch fetch.
 

### kafka Stream Example