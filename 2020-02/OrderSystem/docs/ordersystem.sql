
drop database if exists ordering_system;

CREATE DATABASE `ordering_system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use ordering_system;

-- 菜单表
create table `t_menu` (
                        `id` int not null auto_increment,
                        `name` varchar(64) not null comment '菜单名字',
                        `price` varchar(64) not null comment '价格',
                        `flavor` varchar(64) not null comment '口味',
                        `tid` varchar(64) not null comment '类目id',
                        `create_time` timestamp not null default current_timestamp comment '创建时间',
                        `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                        primary key (`id`)
) comment '菜单表';


-- 类型表
create table `t_type` (
                        `id` int not null auto_increment,
                        `name` varchar(64) not null comment '类目名字',
                        `create_time` timestamp not null default current_timestamp comment '创建时间',
                        `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                        primary key (`id`)
) comment '类型表';

-- 账户表
create table `t_user` (
                        `id` int not null auto_increment,
                        `username` varchar(64) not null comment '姓名',
                        `password` varchar(64) not null comment '密码',
                        `nickname` varchar(64) not null comment '别名',
                        `gender` varchar(64) not null comment '性别',
                        `telephone` varchar(64) not null comment '手机号',
                        `address` varchar(64) not null comment '地址',
                        `registerdate` varchar(64) not null comment '注册日期',
                        `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                        primary key (`id`)
) comment '账户表';

-- 订单表
create table `t_order` (
                         `id` int not null auto_increment,
                         `uid` varchar(64) not null comment '用户id',
                         `mid` varchar(64) not null comment '菜单id',
                         `aid` varchar(64) not null comment '管理者id',
                         `state` varchar(64) not null comment '状态',
                         `date` varchar(64) not null comment '日期',
                         `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                         primary key (`id`)
) comment '订单表';


-- 管理员表
create table `t_admin` (
                         `id` int not null auto_increment,
                         `username` varchar(64) not null comment '用户名',
                         `password` varchar(64) not null comment '密码',
                         `create_time` timestamp not null default current_timestamp comment '创建时间',
                         `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                         primary key (`id`)
) comment '管理员表';

-- 分类表
create table `product_category` (
                                  `category_id` int not null auto_increment,
                                  `category_name` varchar(64) not null comment '类目名字',
                                  `category_type` int not null comment '类目编号',
                                  `create_time` timestamp not null default current_timestamp comment '创建时间',
                                  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                                  primary key (`category_id`)
) comment '分类表';

-- 商品
create table `product_info` (
                              `product_id` varchar(32) not null,
                              `product_name` varchar(64) not null comment '商品名称',
                              `product_price` decimal(8,2) not null comment '单价',
                              `product_stock` int not null comment '库存',
                              `product_description` varchar(64) comment '描述',
                              `product_icon` varchar(512) comment '小图',
                              `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
                              `category_type` int not null comment '类目编号',
                              `create_time` timestamp not null default current_timestamp comment '创建时间',
                              `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                              primary key (`product_id`)
) comment '商品表';

-- 订单
create table `order_master` (
                              `order_id` varchar(32) not null,
                              `buyer_name` varchar(32) not null comment '买家名字',
                              `buyer_phone` varchar(32) not null comment '买家电话',
                              `buyer_address` varchar(128) not null comment '买家地址',
                              `buyer_openid` varchar(64) not null comment '买家微信openid',
                              `order_amount` decimal(8,2) not null comment '订单总金额',
                              `order_status` tinyint(3) not null default '0' comment '订单状态, 默认为新下单',
                              `pay_status` tinyint(3) not null default '0' comment '支付状态, 默认未支付',
                              `create_time` timestamp not null default current_timestamp comment '创建时间',
                              `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                              primary key (`order_id`),
                              key `idx_buyer_openid` (`buyer_openid`)
) comment '订单表';

-- 订单商品
create table `order_detail` (
                              `detail_id` varchar(32) not null,
                              `order_id` varchar(32) not null,
                              `product_id` varchar(32) not null,
                              `product_name` varchar(64) not null comment '商品名称',
                              `product_price` decimal(8,2) not null comment '当前价格,单位分',
                              `product_quantity` int not null comment '数量',
                              `product_icon` varchar(512) comment '小图',
                              `create_time` timestamp not null default current_timestamp comment '创建时间',
                              `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                              primary key (`detail_id`),
                              key `idx_order_id` (`order_id`)
) comment '订单详情表';

-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
                             `id` varchar(32) not null,
                             `username` varchar(32) not null,
                             `password` varchar(32) not null,
                             `openid` varchar(64) not null comment '微信openid',
                             `create_time` timestamp not null default current_timestamp comment '创建时间',
                             `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
                             primary key (`id`)
) comment '卖家信息表';


insert into t_user (id, username, password, nickname, gender, telephone, address, registerdate, update_time)
VALUES (1,'user1','userpasswd1','user1','w','11111111111','沈河区','2020-02-02 15:58:00','2020-02-02 15:58:00');

insert into t_admin (id, username, password, create_time, update_time)
values (1,'user1','userpasswd1','2020-02-02 15:58:00','2020-02-02 15:58:00');


insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(1,'黄冠梨','6.90','甜','1','2020-02-02 15:58:00','2020-02-02 15:58:00');

insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(2,'芒果','9.90','甜','1','2020-02-02 15:58:00','2020-02-02 15:58:00');

insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(3,'红提','16.90','甜','1','2020-02-02 15:58:00','2020-02-02 15:58:00');

insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(4,'青岛啤酒','4.90','甜','2','2020-02-02 15:58:00','2020-02-02 15:58:00');

insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(5,'雪花纯生','4.90','甜','2','2020-02-02 15:58:00','2020-02-02 15:58:00');
insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(6,'娃哈哈AD钙奶','8.90','甜','3','2020-02-02 15:58:00','2020-02-02 15:58:00');
insert into t_menu (id, name, price, flavor, tid, create_time, update_time) values
(7,'蒙牛利乐包','2.56','甜','3','2020-02-02 15:58:00','2020-02-02 15:58:00');



insert into t_type (id, name, create_time, update_time)  values (1,'水果','2020-02-02 15:58:00','2020-02-02 15:58:00');
insert into t_type (id, name, create_time, update_time)  values (2,'酒水','2020-02-02 15:58:00','2020-02-02 15:58:00');
insert into t_type (id, name, create_time, update_time)  values (3,'乳类制品','2020-02-02 15:58:00','2020-02-02 15:58:00');


select * from t_menu;