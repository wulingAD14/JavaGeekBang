## 启动3台redis服务器  
redis-server.exe redis.windows-6379.conf  
redis-server.exe redis.windows-6380.conf  
redis-server.exe redis.windows-6381.conf  
  
## 启动3个哨兵  
redis-server.exe sentinel26379.conf --sentinel  
redis-server.exe sentinel26380.conf --sentinel  
redis-server.exe sentinel26381.conf --sentinel  
  
## 查看redis状态  
redis-cli.exe -h 127.0.0.1 -p 6379  
info replication  
redis-cli.exe -h 127.0.0.1 -p 6380  
info replication  
redis-cli.exe -h 127.0.0.1 -p 6381  
info replication  
  
## 查看sentinel状态  
redis-cli.exe -h 127.0.0.1 -p 26378  
info sentinel  
  
## 测试  
set aaa 111  
get aaa  
  
## 启动集群  
redis-server.exe redis.windows-service-6379.conf  
redis-server.exe redis.windows-service-6380.conf  
redis-server.exe redis.windows-service-6381.conf  
redis-server.exe redis.windows-service-6382.conf  
redis-server.exe redis.windows-service-6383.conf  
redis-server.exe redis.windows-service-6384.conf  
redis-trib.rb create --replicas 1 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384  
  
## 查看集群  
redis-trib.rb check 127.0.0.1:6379  
redis-cli.exe -c -h 127.0.0.1 -p 6379  
