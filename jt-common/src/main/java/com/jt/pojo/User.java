package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
/**
create table tb_user
(
   id                   bigint not null auto_increment,
   username             varchar(50),
   password             varchar(32) comment 'MD5加密',
   phone                varchar(20),
   email                varchar(50),
   created              datetime,
   updated              datetime,
   primary key (id)
);

 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User extends BasePojo{
	@TableId(type = IdType.AUTO)
	private Long id;
	private String  username;
	private String  password;
	private String  phone;
	private String  email;
}
