/*==============================================================*/
/* Table: MPSMS_MESSAGE_TEMPLATE_INF     短信模版表                            */
/*==============================================================*/
drop table if exists MPSMS_MESSAGE_TEMPLATE_INF;
create table MPSMS_MESSAGE_TEMPLATE_INF 
(
   MESSAGE_CODE         varchar(10)                       not null COMMENT '短信模版类型 01注册 02 找回密码 03 消费',
   MESSAGE_CONTENT      varchar(500)                      not null COMMENT '短信模版内容',
   constraint PK_MPSMS_MESSAGE_TEMPLATE_INFF primary key clustered (MESSAGE_CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信模版表';


/*==============================================================*/
/* Table: MPSMS_MESSAGE_INF     短信发送记录表                            */
/*==============================================================*/
drop table if exists MPSMS_MESSAGE_INF;
CREATE TABLE MPSMS_MESSAGE_INF (
  SMS_ID         varchar(9)            NOT NULL     COMMENT 'ID',
  SMS_MOBLIE     varchar(11)           DEFAULT NULL COMMENT '发送手机号',
  SMS_BODY       varchar(500)          DEFAULT NULL COMMENT '发送内容',
  SMS_STATUS     varchar(1)            DEFAULT NULL COMMENT '消息发送状态 0 成功 1失败',
  SMS_DATE       varchar(14)           DEFAULT NULL COMMENT '发送时间',
  SMS_TYPE       varchar(2)            DEFAULT '' COMMENT '短信类型',
  PRIMARY KEY (SMS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送记录表';