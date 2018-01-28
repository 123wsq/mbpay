/*==============================================================*/
/* Table: MPCOOP_COOPORG_INF  便民业务供应商信息表                               */
/*==============================================================*/
drop table if exists MPCOOPSUB_SUPPLIER_INF;
CREATE TABLE MPCOOPSUB_SUPPLIER_INF (
  SUP_ID       varchar(16) NOT NULL     COMMENT '供应商编号',
  SUP_ORG_ID   varchar(15) NOT NULL     COMMENT '供应商机构号',
  SUP_MER_ID   varchar(15) NOT NULL     COMMENT '供应商商户号',
  SUP_NAME     varchar(50)     NULL     COMMENT '供应商名称',
  SUP_TER_ID   varchar(8)      NULL     COMMENT '终端号',
  SUP_IN_MOD   char(1)         NULL     COMMENT '接入模式 1非备付金模式 2备付金模式',
  SUP_IS_SIGN  char(1)         NULL     COMMENT '是否需要签到 1否 2是',
  SUP_MK       varchar(32)     NULL     COMMENT '主密钥',
  SUP_PINKEY   varchar(32)     NULL     COMMENT 'PIN密钥',
  SUP_MACKEY   varchar(32)     NULL     COMMENT 'MAC密钥',
  SUP_MK_CV       varchar(32)     NULL     COMMENT '主密钥校验值',
  SUP_PINKEY_CV   varchar(32)     NULL     COMMENT 'PIN密钥校验值',
  SUP_MACKEY_CV   varchar(32)     NULL     COMMENT 'MAC密钥校验值',
  SIGN_TIME    varchar(14)   null     COMMENT '上次签到时间',
  PRIMARY KEY (SUP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='便民业务供应商信息表';

/*==============================================================*/
/* Table: MPCOOPSUB_SUPPLIER_ROUTE_INF  供应商路由信息表                  */
/*==============================================================*/
drop table if exists MPCOOPSUB_SUPPLIER_ROUTE_INF;
create table MPCOOPSUB_SUPPLIER_ROUTE_INF 
(
   RTRID                char(10)                   not null COMMENT '路由编号',
   SUP_ID               varchar(16)                NOT null COMMENT '供应商编号',
   TXNCD                varchar(6)                 not null COMMENT '交易码',
   RTRSVR               varchar(32)                    null COMMENT '路由服务名',
   RTRCOD               char(6)                        null COMMENT '路由交易码',
   STATUS               char(2)                        null COMMENT '路由状态',
   EDIT_DATE            varchar(4)                     null COMMENT '最后编辑时间',
   EDIT_USER_ID         varchar(14)                    null COMMENT '最后编辑人',
   constraint MPCOOPSUB_SUPPLIER_ROUTE_INF primary key clustered (RTRID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商路由信息表';



/*==============================================================*/
/* Table: MPCOOPSUB_SUPPLIER_ROUTE_REL_INF  供应商业务类型信息表   */
/*==============================================================*/
drop table if exists MPCOOPSUB_SUPPLIER_ROUTE_REL_INF;
create table MPCOOPSUB_SUPPLIER_ROUTE_REL_INF 
(
   SUP_ID               varchar(16)                   not null COMMENT '供应商编号',
   SUB_BIZ_TYPE         char(6)                       not null COMMENT '子业务类型',
   IS_USE               char(1)                       not null COMMENT '0 启用 1停用',
   constraint MPCOOPSUB_SUPPLIER_ROUTE_REL_INF primary key clustered (SUP_ID,SUB_BIZ_TYPE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商业务关系信息表';


/*==============================================================*/
/* Table: MPAPPSUB_PRD_PHONE_INF  手机充值详细信息表                           */
/*==============================================================*/
drop table if exists MPAPPSUB_PRD_PHONE_INF;
create table MPAPPSUB_PRD_PHONE_INF 
(
   PRDORDNO             varchar(25)                   not null COMMENT '商品订单表',
   CUST_ID              varchar(15)                   not null COMMENT '商户编号',
   PHONE_NO             char(11)                      not null COMMENT '充值手机号',
   T_MOBILE             varchar(50)                   not null COMMENT '运营商',
   LOCATION             varchar(50)                   not null COMMENT '地区',
   constraint MPAPPSUB_PRD_PHONE_INF primary key clustered (PRDORDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机充值详细信息表';

/*==============================================================*/
/* Table: MPAPPSUB_PRD_CREDIT_CARD_INF  信用卡还款详细信息表          */
/*==============================================================*/
drop table if exists MPAPPSUB_PRD_CREDIT_CARD_INF;
create table MPAPPSUB_PRD_CREDIT_CARD_INF 
(
   PRDORDNO            varchar(25)                      not null COMMENT '商品订单表',
   CUST_ID              varchar(15)                   not null COMMENT '商户编号',
   CARD_IN             varchar(19)                      not null COMMENT '转入卡号',
   ISSNAM              varchar(50)                      not null COMMENT '发卡行名称',
   CRDNAM              varchar(50)                      not null COMMENT '卡名称',
   CARD_OUT            varchar(19)                      not null COMMENT '转出卡号',
   constraint MPAPPSUB_PRD_CREDIT_CARD_INF primary key clustered (PRDORDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信用卡还款详细信息表';

/*==============================================================*/
/* Table: MPAPPSUB_PRD_CARDTOCARD_INF  卡卡转账详细信息表            */
/*==============================================================*/
drop table if exists MPAPPSUB_PRD_CARDTOCARD_INF;
create table MPAPPSUB_PRD_CARDTOCARD_INF 
(
   PRDORDNO            varchar(25)                      not null COMMENT '商品订单表',
   CUST_ID              varchar(15)                     not null COMMENT '商户编号',
   CARD_IN             varchar(19)                      not null COMMENT '转入卡号',
   ISSNAM              varchar(50)                      not null COMMENT '发卡行名称',
   CRDNAM              varchar(50)                      not null COMMENT '卡名称',
   CARD_OUT            varchar(19)                      not null COMMENT '转出卡号',
   FEE                 varchar(12)                      not null COMMENT '手续费',
   constraint MPAPPSUB_PRD_CARDTOCARD_INF primary key clustered (PRDORDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡卡转账细信息表';





