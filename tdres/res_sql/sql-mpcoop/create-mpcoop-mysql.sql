/*==============================================================*/
/* Table: MPCOOP_COOPORG_INF  合作机构表信息表                                  */
/*==============================================================*/
drop table if exists MPCOOP_COOPORG_INF;
CREATE TABLE MPCOOP_COOPORG_INF (
  COOPORG_NO     varchar(30) NOT NULL     COMMENT '合作机构编号',
  COOPNAME       varchar(40)     NULL     COMMENT '合作机构名称',
  AREACD         varchar(6)      NULL     COMMENT '合作机构地区码',
  CONTACT        varchar(40)     NULL     COMMENT '联系人',
  PHONE          varchar(40)     NULL     COMMENT '联系电话',
  COOP_TYPE      varchar(1)  DEFAULT '0'  COMMENT '合作机构类型：0-收单，1-快捷',
  SVRSTS         char(1)         NULL     COMMENT '服务开通状态',
  EDIT_DATE      varchar(14)     null     COMMENT '编辑时间',
  EDIT_USER_ID   varchar(4)      null     COMMENT '编辑员',
  PRIMARY KEY (COOPORG_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构信息表';


/*==============================================================*/
/* Table: MPCOOP_COOP_TERMKEY_INF  合作机构终端工作密钥表                               */
/*==============================================================*/
drop table if exists MPCOOP_COOPORG_TERMKEY_INF;
create table MPCOOP_COOPORG_TERMKEY_INF 
(
   COOPORG_NO           char(8)                    not null COMMENT '合作机构编号',
   MER_NO               varchar(15)                not null COMMENT '第三方机构商户号',
   TER_NO               varchar(8)                 not null COMMENT '终端号',
   PINKEY               varchar(32)                    null COMMENT 'PIN密钥',
   MACKEY               varchar(32)                    null COMMENT 'MAC密钥',
   TDKEY                varchar(32)                    null COMMENT 'TDK密钥',
   TK_CHECK_VALUE       varchar(16)                    null COMMENT 'TDK效验值',
   PK_CHECK_VALUE       varchar(16)                    null COMMENT 'PIN校验值',
   MK_CHECK_VALUE       varchar(16)                    null COMMENT 'MAC校验值',
   RES_FIELD3           varchar(32)                    null COMMENT '预留字段',
   RES_FIELD1           varchar(32)                    null COMMENT '预留字段',
   RES_FIELD2           varchar(32)                    null COMMENT '预留字段',
   constraint MPCOOP_COOPORG_TERMKEY_INF primary key clustered (COOPORG_NO,MER_NO, TER_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构终端密钥表';

/*==============================================================*/
/* Table: MPCOOP_COOPORG_MER_INF     合作机构商户终端表                            */
/*==============================================================*/
drop table if exists MPCOOP_COOPORG_MER_INF;
create table MPCOOP_COOPORG_MER_INF 
(
   COOPORG_NO           char(8)                        not null COMMENT '合作机构编号',
   MER_NO               varchar(15)                    not null COMMENT '商户编号',
   TER_NO               varchar(8)                     not null COMMENT '终端编号',
   MER_NAME             varchar(50)                        null COMMENT '商户名称',
   MER_KEY              varchar(32)                        null COMMENT '商户密钥(快捷通道/终端主密钥)',
   CHECK_VALUE          varchar(16)                        null COMMENT '主密钥校验值',
   RATE_T0              varchar(4)                 DEFAULT NULL COMMENT '大商户T0刷卡成本费率(%)',
   RATE_T1              varchar(4)                 DEFAULT NULL COMMENT '大商户T1刷卡成本费率(%)',
   RATE_TYPE            varchar(1)                 DEFAULT NULL COMMENT '费率类型 1民生类 2一般类 3餐娱类 4批发类  5房产类',
   RATE_TOP             varchar(12)                DEFAULT NULL COMMENT '费率值(0.38 ... 1.25)',
   RATE_TYPE_TOP        varchar(12)                DEFAULT NULL COMMENT '费率类型   封顶金额',
   MER_STATUS           char(2)                    DEFAULT NULL COMMENT '商户状态:0-正常，1-关闭',
   DT_LIMIT             varchar(12)                DEFAULT NULL COMMENT '日交易限额',
   MT_LIMIT             varchar(12)                DEFAULT NULL COMMENT '月交易限额',
   LOW_LIMIT            varchar(12)                DEFAULT NULL COMMENT '单笔最低交易额',
   HIGH_LIMIT           varchar(12)                DEFAULT NULL COMMENT '单笔最高交易额',
   BATCH_NO             varchar(6)                 DEFAULT NULL COMMENT '第三方批次号',
   SIGN_TIME            varchar(14)                DEFAULT NULL COMMENT '上次签到时间',
   EDIT_DATE            varchar(14)                DEFAULT NULL COMMENT '编辑时间',
   EDIT_USER_ID         varchar(4)                 DEFAULT NULL COMMENT '编辑员',
   SIGNCOUNT            int(11)                    DEFAULT '0' COMMENT '签到次数',
   PRIMARY KEY (COOPORG_NO,MER_NO,TER_NO)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构商户终端表';
 

/*==============================================================*/
/* Table: MPCOOP_COOPORG_ROUTE_INF  合作机构路由信息表                       */
/*==============================================================*/
drop table if exists MPCOOP_COOPORG_ROUTE_INF;
create table MPCOOP_COOPORG_ROUTE_INF 
(
   RTRID                char(4)                    not null COMMENT '路由编号',
   COOPORG_NO           varchar(30)                NOT null COMMENT '合作结构编号',
   TXNCD                varchar(6)                 not null COMMENT '交易码',
   RTRSVR               varchar(32)                    null COMMENT '路由服务名',
   RTRCOD               char(6)                        null COMMENT '路由交易码',
   STATUS               char(2)                        null COMMENT '路由状态',
   EDIT_DATE            varchar(4)                     null COMMENT '最后编辑时间',
   EDIT_USER_ID         varchar(14)                    null COMMENT '最后编辑人',
   constraint MPCOOP_COOPORG_ROUTE_INF primary key clustered (RTRID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构路由信息表';

/*==============================================================*/
/* Table: MPCOOP_COOPORG_ROUTE_REL_INF  合作机构路由关系信息表      */
/*==============================================================*/
drop table if exists MPCOOP_COOPORG_ROUTE_REL_INF;
create table MPCOOP_COOPORG_ROUTE_REL_INF 
(
   RTRID                char(4)                    not null COMMENT '路由编号',
   COOPORG_NO           char(8)                    not null COMMENT '合作机构编号',
   MER_NO               varchar(15)                not null COMMENT '商户编号',
   TER_NO               varchar(8)                 not null COMMENT '终端编号',
   PRIORITY             CHAR(1)           DEFAULT '1'  null COMMENT '优先级',
   STIME                CHAR(2)           DEFAULT '00' null COMMENT '开始时间00',
   ETIME                CHAR(2)           DEFAULT '99' null COMMENT '结束时间99',
   constraint MPCOOP_COOPORG_ROUTE_REL_INF primary key clustered (RTRID,COOPORG_NO,MER_NO,TER_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构路由关系信息表';
