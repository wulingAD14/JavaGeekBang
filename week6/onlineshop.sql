use onlineshop;
create table customers   #客户表
(
   custid  int,               #客户id
   custtype int,              #客户类型
   custaccount varchar(50),   #客户账号
   custpassword varchar(50),  #密码
   custname varchar(50),      #客户名字
   birthday date,			  #生日
   areaid int,                #区域
   tel varchar(50),           #电话
   address varchar(500)       #地址
);
create table goods       #商品表
(
   goodid int,                #商品id
   goodtype int,              #商品类型
   goodname varchar(100),     #商品名称
   price  decimal(10,2),      #商品价格
   sellerid int,              #卖方id
   details varchar(2000)      #商品详情
);
create table orders      #订单表
(
   orderid int,               #订单id
   orderno varchar(50),       #订单编号
   ordertype int,             #订单类型
   ordertime datetime,        #订单时间
   buyerid  int,              #买方id
   sellerid int,              #卖方id
   goodid int,                #商品id
   price  decimal(10,2),      #单价
   qty    decimal(10,2),      #数量
   amount decimal(19,2),      #总价
   remark varchar(1000)       #备注
)