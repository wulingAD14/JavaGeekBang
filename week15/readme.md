# 课程总结
## 1、JVM
java虚拟机，是用来执行java字节码(二进制的形式)的虚拟计算机。主要包括：
1. 类加载器(ClassLoader)
2. 执行引擎：解释器(Interpreter)、JIT编译器(JIT Compiler)、垃圾回收(GC)
3. 本地方法接口
3. 运行时数据区。内存结构：
    1). 程序计数器(Program Counter Register)
    2). 虚拟机栈(VM Stack)
    3). 本地方法栈(Native Method Stack)
    4). 方法区(Method Area)
    5). 堆(Heap)：垃圾回收机制主要管理的就是此区域，各种GC策略，内存调优。

## 2、NIO（Nonblocking IO，非阻塞IO）
传统IO是面向流的，基于字节流和字符流进行操作，阻塞模式；NIO是面向缓冲区的，基于Channel和Buffer(缓冲区)进行操作，非阻塞模式。  
NIO的特点：事件驱动模型、单线程处理多任务、非阻塞I/O，I/O读写不再阻塞，而是返回0、基于block的传输比基于流的传输更高效、更高级的IO函数zero-copy、IO多路复用大大提高了Java网络应用的可伸缩性和实用性。基于Reactor线程模型。  
Netty是一款基于NIO开发的网络应用开发框架，具有高并发、传输快、封装好、高性能等特点。  

## 3、并发编程
1. 多线程：
    1). 实现类：Thread、Runnable、Callable、线程池Executors
    2). 线程的生命周期：
        <1>. 6种状态：new、runnable、blocked、waiting、time_waiting、terminated
        <2>. 启动停止：start、stop、interrupt
    3). 性能指标：延迟、吞吐量、最佳线程数
2. 线程安全：
    1). 原子性：
        <1>. 互斥锁sychronized：等待通知机制wait、notify、notifyAll
        <2>. 锁Lock：可重入锁、公平锁、读写锁。三个用锁的最佳实践，最小使用锁（降低锁范围、细分锁粒度）
    2). 可见性：volatile、synchronized
    3). 有序性：happens-before原则（先行发生原则）
3. 常用的并发工具与集合类：原子类工具包Atomic、Semaphore、CountDownLatch、CyclicBarrier、Exchanger、List、Map


## 4、Spring框架
框架是基于类库和工具的开放式结构，具有支撑性+扩展性、聚合性+约束性的特征。  
Java三大框架：SSH（Spring、Struts、Hibernate）、SSM（Spring、SpringMVC、MyBatis）  
Spring框架特征：轻量、IOC控制反转（DI依赖注入，降低耦合）、AOP面向切面、Bean容器（对象的配置管理）、框架（组件的配置组合）  
Spring Boot：自动化配置、spring-boot-starter  
Spring ORM：对象关系映射，实现java对象和关系数据库的映射转换，及持久化支持。常用框架包括 Hibernate、MyBatis、JPA等  


## 5、MySQL数据库和SQL
MySQL是常用的开源关系型数据库，有4种存储引擎：myisam、innodb（默认，支持事务I，默认隔离级别为可重复读）、memory、archive。  
SQL结构化查询语言包含6个部分：DQL、DML、TCL、DCL、DDL、CCL  
性能相关：SQL执行顺序、索引原理（innodb使用B+树实现聚集索引，一般单表数据不超过2000万）  
最佳实践：选择引擎、库表命名、拆分宽表、选择数据类型、选择索引、SQL设计等。  


## 6、分库分表
1. 主从复制：异步复制、半同步复制、组复制
2. 读写分离的3种方式：
    1). 基于spring/spring boot，配置多个数据源
    2). ShardingSphere-jdbc的Master-Slave功能，自动实现读写分离
    3). MyCar/ShardingSphere-Proxy的Master-Slave功能，部署中间件配置规则
3. failover故障转移的5种方式：
    1). 手动修改配置切换
    2). LVS+Keepalived，手动修改配置切换
    3). MHA：至少需要3台，一般能在30s内实现主从切换
    4). MGR：基于组复制，自动选择某个从改成主
    5). MySQL Cluster：负载均衡、故障转移
    6). Orchestrator：自动发现MySQL复制拓扑，图形化展示及管理
4. 数据库拆分
    1). 垂直拆分：按业务分类数据，实现分布式服务化、微服务
    2). 水平拆分（分库、分表、分库分表）：解决容量/性能问题，实现分布式结构，利于扩容，主要中间件有ShardingSphere-Proxy
    3). 数据迁移的方式
5. 分布式事务：实现多个节点操作的整体事务一致性
    1). 强一致：XA分布式事务协议
    2). 弱一致：BASE柔性事务：二阶段提交
        <1>. TCC模式：代码有侵入，三段逻辑 try、confirm、cancel。防止空回滚、防悬挂控制、幂等校验。常用框架有hmily
        <2>. AT：代码无侵入，常用框架有阿里seata


## 7、RPC和微服务
1. RPC：RPC是基于接口的远程服务调用，核心是代理机制。  
    在一个典型RPC的使用场景中，包含了服务发现、负载、容错、网络传输、序列化等组件。  
    一个RPC的核心功能主要有5个部分组成，分别是：客户端、客户端Stub、网络传输模块、服务端Stub、服务端。  
    常用的RPC框架有Dubbo、gRPC、Hessian、Thrift。  
2. 分布式服务化：偏向于业务与系统的集成。直连调用，侧边增强。不同于SOA/ESB（代理调用，直接增强）。有状态的部分，放到xx中心；无状态的部分，放到应用侧。
    1). 架构：配置中心、注册中心、元数据中心
    2). 服务：注册、发现、集群、路由、负载均衡、过滤、流控。
3. 微服务：
    1). 发展历程：响应式微服务 -> 服务网格与云原生 -> 数据库网格 ->  单元化架构  
    2).  微服务应用场景：大规模复杂业务系统及中台建设
    3). 微服务架构六大最佳实践：遗留系统改造、适当粒度拆分、扩展立方体AFK、自动化管理（测试/部署/运维）、分布式事务、完善监控体系
    4). 微服务解决方案：Spring Cloud

## 8、分布式缓存
缓存是提升系统性能的有效方法，让分布式应用程序加速的重要技术之一。分布式缓存是降低分布式应用程序延迟、提高并发性和可伸缩性的一种重要策略。
1. 缓存加载时机：全量加载、懒加载
2. 缓存有效性：读写比、命中率。不适用变动频率大、一致性要求高的数据
3. 本地缓存：Maps、Guava Cache、Spring Cache、JCache。
4. 远程缓存：Redis、Memcached、Hazelcast/Ignite内存网格
5. 缓存策略（基于容量考虑）：FIFO/LRU、按固定时间过期、按业务时间加权
6. 缓存问题：缓存穿透、缓存击穿、缓存雪崩
7. Redis：
    1). 六大使用场景：业务数据缓存、业务数据处理、全局一致计数、高效统计计数、发布订阅与Stream、分布式锁
    2). 组件库：Redission，基于Netty NIO
    3). 集群与高可用：主从复制、sentinel高可用、cluster集群

## 9、分布式消息队列
1. 场景：异步处理、应用解耦、削峰平谷、可靠通信。实现高性能，高可用，可伸缩和最终一致性架构。  
2. 3个角色：队列服务端，队列的生产者，队列的消费者。
3. 消息处理模式：点对点（Queue）、发布订阅（Topic）
4. 消息协议：JMS、STOMP、AMOP、MQTT、XMPP、Open Messaging
4. MQ应用思考点：生产端可靠性投递、消费端幂等（消息只能消费一次）、高可用/低延迟/可靠性、消息堆积能力、可扩展性、消息有序性。
5. 业界主流MQ：ActiveMQ、RabbitMQ、RocketMQ、Kafka









