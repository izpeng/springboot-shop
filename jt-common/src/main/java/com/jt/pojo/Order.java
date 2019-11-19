package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
/**
create table tb_order
(
   order_id             varchar(50) not null comment '订单号：登录用户id+当前时间戳',
   payment              varchar(50) comment '精确到2位小数；单位：元。如：200.09，表示：200元7分。',
   payment_type         int(2) comment '1、在线支付，2、货到付款',
   post_fee             varchar(50) comment '邮费。精确到2位小数；单位：元。如：200.09',
   status               int comment '状态：1、未付款2、已付款3、未发货4、已发货5、交易成功6、交易关闭',
   payment_time         datetime,
   consign_time         datetime,
   end_time             datetime,
   close_time           datetime,
   shipping_name        varchar(20),
   shipping_code        varchar(20),
   user_id              bigint(20),
   buyer_message        varchar(100),
   buyer_nick           varchar(50),
   buyer_rate           int(2),
   created              datetime,
   updated              datetime,
   primary key (order_id)
);
 */
@Data
@TableName("tb_order")
@Accessors(chain = true)
public class Order {

}
