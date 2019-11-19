package com.jt.pojo;
/**
create table tb_cart
(
   id                   bigint(20) not null auto_increment,
   user_id              bigint(20),
   item_id              bigint(20),
   item_title           varchar(100),
   item_image           varchar(200),
   item_price           bigint(20) comment '单位：分',
   num                  int(10),
   created              datetime,
   updated              datetime,
   primary key (id),
   key AK_user_itemId (user_id, item_id)
);
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@TableName("tb_cart")
@Accessors(chain = true)
public class Cart extends BasePojo{
	@TableId
	private Long id;
	private Long userId;
	private Long itemId;
	private String  itemTitle;
	private String  itemImage ;
	private Long  itemPrice ;
	private Integer num;

}
