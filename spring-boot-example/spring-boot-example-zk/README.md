# zk
- 默认端口 `2181`
    
## 安装
- mac 启动: zkServer start / zkServer stop

## 配置
- dataDir 元数据数据目录
- clientPort 当前服务器服务器的端口
- server.1/server.2/server.3 指定服务器的集群情况（有几台服务器，master是服务器地址）
- server.1=master:2888:3888
  `server.1=master:2888(通讯端口):3888（选举端口）`
  
## 集群
- 伪分布式集群
  - 配置文件`/conf/zoo1.cfg zoo2.cfg zoo3.cfg`
  - zoo1.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zoo1
      clientPort=2181
      server.1=127.0.0.1:2888:3888
      server.2=127.0.0.1:2889:3889
      server.3=127.0.0.1:2890:3890
      ```
      
   - zoo2.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zk2
      clientPort=2182
      server.1=127.0.0.1:2888:3888
      server.2=127.0.0.1:2889:3889
      server.3=127.0.0.1:2890:3890
      ```
   - zoo3.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zk3
      clientPort=2183
      server.1=127.0.0.1:2888:3888
      server.2=127.0.0.1:2889:3889
      server.3=127.0.0.1:2890:3890
      ```
  - 创建myid
    - `cd /opt/zookeeper`
    - `mkdir {zoo1,zoo2,zoo3}`
    - ```
      echo 1 > zoo1/myid
      echo 2 > zoo2/myid
      echo 3 > zoo3/myid
      ```
- 全分布式集群
  - 配置文件`/conf/zoo1.cfg zoo2.cfg zoo3.cfg`
  - zoo1.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zk1
      clientPort=2181
      server.1=master:2888(通讯端口):3888（选举端口）
      server.2=slave1:2889:3889
      server.3=slave1:2890:3890
      ```
   - zoo2.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zk1
      clientPort=2181
      server.1=master:2888:3888
      server.2=slave1:2889:3889
      server.3=slave1:2890:3890
      ```
   - zoo3.cfg
      ```
      tickTime=2000
      initLimit=10
      syncLimit=5
      dataDir=/opt/zookeeper/zk1
      clientPort=2181
      server.1=master:2888:3888
      server.2=slave1:2889:3889
      server.3=slave1:2890:3890
      ```
## 权限划分
  - CREATE: 创建子节点
  - READ: 获取节点数据以及当前节点的子节点列表
  - WRITE: 为节点设置数据
  - DELETE: 删除子节点
  - ADMIN: 节点设置权限
- 认证方式
  - world : 默认方式，相当于全世界都能访问
  - auth : 代表已经认证通过的用户(cli中可以通过addauth digest user:pwd 来添加当前上下文中的授权用户)
  - digest : 即用户名:密码这种方式认证，这也是业务系统中最常用的
  - ip : 使用Ip地址认证
- 设置访问控制
  - 方式一：
    1. 增加用户
        1. addauth digest 用户名:密码明文 `addauth digest user1:password1`
    2. 设置权限
        1. setAcl /path auth:用户名:密码明文:权限 `setAcl /test auth:user1:password1:cdrwa`
    3. 查看Acl
        1. getAcl /path
  - 方式二
    1. `setAcl /path digest`:用户名:密码密文:权限
- 超级用户
  - 生成用户
    ```
    String m = DigestAuthenticationProvider.generateDigest("super:admin");
    ```
  - 设置
    ```
     "-Dzookeeper.DigestAuthenticationProvider.superDigest=super:xQJmxLMiHGwaqBvst5y6rkB6HQs="
    ```
  - maven
    ```
    <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.13</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-api</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>log4j</groupId>
                    <artifactId>log4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    ```
  - 使用
    ```
    addauth digest super:admin
    ```
- example
  - 添加一个example-user用户
    `addauth digest example-user:12345`
  - 创建一个example-node 节点.
    `create /example-node 'example-node-data'` 
  - 把example-node节点的CREATE,READ,WRITE,DELETE,ADMIN权限赋予example-user用户
    `setAcl /example-node auth:example-user:12345:rwadc`
- 多层节点的权限问题
  - 多级节点之间的ACL，并非继承关系，但是也有些一联系
  - `create -e /test/test-1 t1`
  - 

## 基本命令
- 查看所有节点
  - `ls / `
- 查看某个节点
  - `ls /a`
- 创建一个节点
  - `create test 'test-data'`
  - `-e` 临时节点
  - `-s` 顺序节点
- 删除节点
  - `delete /test`
- 节点状态
  - `stat /test`
- 删除节点
  - `rmr /test`
- 连接集群
  ```
  zkCli -server 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
  ```
- 启动集群
  - zkServer start zoo1.cfg
  - zkServer start zoo2.cfg
  - zkServer start zoo3.cfg

# java client
- maven example
    ```
    <properties>
            <zk-version>3.4.13</zk-version>
            <zkClient-version>0.11</zkClient-version>
            <curator-version>4.1.0</curator-version>
        </properties>
    
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.apache.zookeeper</groupId>
                <artifactId>zookeeper</artifactId>
                <version>${zk-version}</version>
                <exclusions>
                    <exclusion>
                        <groupId>org.slf4j</groupId>
                        <artifactId>slf4j-api</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>org.slf4j</groupId>
                        <artifactId>slf4j-log4j12</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>log4j</groupId>
                        <artifactId>log4j</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>com.101tec</groupId>
                <artifactId>zkclient</artifactId>
                <version>${zkClient-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-recipes</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-framework</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-x-async</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-examples</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-x-discovery</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-x-discovery-server</artifactId>
                <version>${curator-version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.curator</groupId>
                <artifactId>curator-test</artifactId>
                <version>${curator-version}</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    ```