# Environment
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