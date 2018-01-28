/*==============================================================*/
/* Table: MPOMNG_MOBILE_MER_INF   商户登录账户信息表                              */
/*==============================================================*/
drop table if exists MPOMNG_MOBILE_MER_INF;
create table MPOMNG_MOBILE_MER_INF 
(
   CUST_ID              char(14)                   NOT null COMMENT '商户编号',
   CUST_TYPE            char(1)                        null COMMENT '商户类型', 
   CUST_NAME            varchar(50)                    null COMMENT '商户名称',
   CUST_PWD             varchar(32)                    null COMMENT '密码',
   PAY_PWD              varchar(32)            DEFAULT null COMMENT '支付密码',
   PAYPWD_STRENGTH      char(1)                        null COMMENT '密码强度',
   USR_EMAIL            varchar(50)                    null COMMENT '电子邮件',
   ACTIV_CODE           varchar(20)            DEFAULT null COMMENT '激活码，如果有激活码，表示已激活',
   PROVINCE_ID          varchar(7)             DEFAULT null COMMENT '所属省份Id',
   CITY_ID              varchar(7)             DEFAULT null COMMENT '所在城市ID',
   REGION_ID            varchar(20)            DEFAULT null COMMENT '所在地区ID',
   USR_ADDRESS          varchar(200)           DEFAULT null COMMENT '街道信息等详细地址补充',
   USR_GENDER           char(1)                        null COMMENT '性别',
   USR_ZIP              varchar(6)                     null COMMENT '邮编',
   USR_TEL              varchar(20)                    null COMMENT '固定电话',
   USR_MOBILE           varchar(20)                    null COMMENT '手机',
   USR_BIRTHDAY         char(8)                        null COMMENT '生日',
   USR_RANDOM           char(6)                        null COMMENT '随机码',
   IS_LOGIN_FLAG        char(2)                DEFAULT null COMMENT '冻结状态',
   LAST_OPER_TIME       varchar(14)                    null COMMENT '上次登录时间',
   CUST_REG_DATETIME    varchar(14)                    null COMMENT '注册时间',
   CUST_PWD_NUM         int                            null COMMENT '密码效验次数',
   MERCLASS             varchar(2)                     null COMMENT '商户等级',
   RES_FIELD1           varchar(50)                    null COMMENT '备用字段1',
   RES_FIELD2           varchar(50)                    null COMMENT '备用字段2',
   RES_FIELD3           varchar(200)                   null COMMENT '备用字段3',
   CUST_LOGIN           varchar(50)            DEFAULT null COMMENT '登陆手机号',
   USR_BIRTHDAY         varchar(30)            DEFAULT null COMMENT '生日',
   CUST_STATUS          varchar(1)             DEFAULT null COMMENT '商户状态 : 0 正常  1 禁用',
   AUTH_STATUS          varchar(1)             DEFAULT '0' COMMENT '认证状态（0未认证，1审核中，2审核通过，3审核不通过）',
   REFERRER             varchar(14)            DEFAULT null COMMENT '推荐人',
   PRIMARY KEY (CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户登录账户信息表';

/*==============================================================*/
/* Table: MPOMNG_FILE_DOWNLOAD_INF   文件下载信息表                           */
/*==============================================================*/
drop table if exists MPOMNG_FILE_DOWNLOAD_INF;
CREATE TABLE MPOMNG_FILE_DOWNLOAD_INF(
	DID            VARCHAR(15)     NOT null COMMENT '文件编号',
	DNAME          VARCHAR(100)            COMMENT '文件编号文件名',
	DFILENAME      VARCHAR(100)            COMMENT '文件编号物理名称',
	DPATH          VARCHAR(500)            COMMENT '文件编号文件路径',
	DSIZE          VARCHAR(50)             COMMENT '文件编号文件大小',
	DTYPE          VARCHAR(15)             COMMENT '文件编号文件类型',
	DTIME          VARCHAR(10)             COMMENT '文件编号文件生成时长',
	DSTIME         VARCHAR(14)             COMMENT '文件编号文件生成开始时间',
	DETIME         VARCHAR(14)             COMMENT '文件编号文件生成结束时间',
	STATUS         CHAR(1)                 COMMENT '文件编号状态（1 制作中  2失败 0成功）',
	RPTTYPE        VARCHAR(2)              COMMENT '文件编号报表类型',
	ORGNO		   VARCHAR(40)   		   COMMENT '机构号',
	UID  		   VARCHAR(4)              COMMENT '创建人',
	PRIMARY KEY (DID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件下载信息表';


/*==============================================================*/
/* Table: MPOMNG_PAY_FEERATE_INF     费率表                                   */
/*==============================================================*/
drop table if exists MPOMNG_PAY_RATE_INF;
create table MPOMNG_PAY_RATE_INF
(
   FEE_ID               char(10)                   NOT null COMMENT '费率编号',
   FEE_CODE             char(4)                        null COMMENT '费率代码',
   REL_TYPE             char(2)                        null COMMENT '关联类型  00 平台成本费率 01 代理商费成本率  02代理商下的商户费率 03 终端费率 04 商户费率',
   REL_ID               varchar(50)                    null COMMENT '关联编码',
   CCY                  char(3)                        null COMMENT '货币类型',
   FEE_NAME             varchar(50)                    null COMMENT '费率名称',
   FEE_TYPE             varchar(4)                     null COMMENT '费率类型  CH01 一般类 , CH02 民生类, CH03 批发类封顶手续费 CH04 餐娱类费率',
   PRICING              varchar(12)                    null COMMENT '定额',
   PERCENT              varchar(12)                    null COMMENT '比例',
   FEE_LOW              varchar(12)                    null COMMENT '最低',
   FEE_TOP              varchar(12)                    null COMMENT '封顶',
   FEE_STATUS           varchar(2)                     null COMMENT '费率状态 00 正常 01 处理中(或审核中) 02 历史',
   CREATE_DATE          varchar(14)                    null COMMENT '',
   CREATE_USER_ID       char(4)                        null COMMENT '',
   EDIT_DATE            varchar(14)                    null COMMENT '',
   EDIT_USER_ID         char(4)                        null COMMENT '',
   PRIMARY KEY (FEE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费率表';





/*==============================================================*/
/* Table: MPOMNG_CUST_ACCOUNT  商户账户信息表                                        */
/*==============================================================*/
drop table if exists MPOMNG_CUST_ACCOUNT_INF;
CREATE TABLE MPOMNG_CUST_ACCOUNT_INF (
  CUST_ID         varchar(20)        NOT NULL COMMENT '商户号',
  ACCOUNT         varchar(100)   DEFAULT NULL COMMENT '账户编号',
  AC_TYPE         varchar(2)     DEFAULT '01' COMMENT '账户类型  01 余额账户',
  CCY             varchar(3)     DEFAULT '156' COMMENT '货币类型',
  AC_BAL          varchar(12)    DEFAULT NULL COMMENT '总余额',
  AC_T0           varchar(12)    DEFAULT NULL COMMENT 'T+0',
  AC_T1           varchar(12)    DEFAULT NULL COMMENT 'T+1',
  AC_T1_Y         varchar(12)    DEFAULT NULL COMMENT 'T+1未提',
  FROZ_BALANCE    varchar(12)    DEFAULT NULL COMMENT '冻结金额',
  LST_TX_DATETIME varchar(14)    DEFAULT NULL COMMENT '最后交易时间',
  RES_FIELD1      varchar(12)    DEFAULT NULL COMMENT '预留字段1',
  RES_FIELD2      varchar(12)    DEFAULT NULL COMMENT '预留字段1',
  RES_FIELD3      varchar(12)    DEFAULT NULL COMMENT '预留字段1',
  ACCOUNT_STATUS  varchar(1)     DEFAULT NULL COMMENT '是否冻结：0正常 1冻结',
  PRIMARY KEY (CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户账户信息表';

/*==============================================================*/
/* Table: MPOMNG_CUST_ACCOUNT_HIS  商户账户变动历史详情表                       */
/*==============================================================*/
drop table if exists MPOMNG_CUST_ACCOUNT_HIS_INF;
CREATE TABLE MPOMNG_CUST_ACCOUNT_HIS_INF (
  ACCOUNT_LOG_ID        varchar(20)    NOT NULL       COMMENT 'ID',
  CUST_ID               varchar(20)    NOT NULL       COMMENT '商户号',
  ACCOUNT               varchar(100)   DEFAULT NULL   COMMENT '账户编号',
  AC_TYPE               varchar(2)     DEFAULT NULL   COMMENT '账户类型',
  CCY                   varchar(3)     DEFAULT '156'  COMMENT '货币类型',
  OLD_AC_BAL            varchar(12)    DEFAULT NULL   COMMENT '总余额',
  OLD_AC_T0             varchar(12)    DEFAULT NULL   COMMENT 'T+0',
  OLD_AC_T1             varchar(12)    DEFAULT NULL   COMMENT 'T+1',
  OLD_AC_T1_Y           varchar(12)    DEFAULT NULL   COMMENT 'T+1未提',
  AC_BAL                varchar(12)    DEFAULT NULL   COMMENT '总余额',
  AC_T0                 varchar(12)    DEFAULT NULL   COMMENT 'T+0',
  AC_T1                 varchar(12)    DEFAULT NULL   COMMENT 'T+1',
  AC_T1_Y               varchar(12)    DEFAULT NULL   COMMENT 'T+1未提',
  FROZ_BALANCE          varchar(12)    DEFAULT NULL   COMMENT '冻结金额',
  OLD_FROZ_BALANCE      varchar(12)    DEFAULT NULL   COMMENT '冻结金额',
  LST_TX_DATETIME       varchar(14)    DEFAULT NULL   COMMENT '最后交易时间',
  CHANGE_TYPE           varchar(10)    DEFAULT NULL   COMMENT '改变类型：1001-收款；1002-其他虚拟账户转入；1003-退款；2001-提现；2002-虚拟账户转出；2003-虚拟账号支付；',
  CHANGE_DESC           varchar(200)   DEFAULT NULL   COMMENT '变动描述',
  RES_FIELD1            varchar(12)    DEFAULT NULL   COMMENT '预留字段1',
  RES_FIELD2            varchar(12)    DEFAULT NULL   COMMENT '预留字段1',
  RES_FIELD3            varchar(12)    DEFAULT NULL   COMMENT '预留字段1',
  PRIMARY KEY (ACCOUNT_LOG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户账户变动历史详情表';



/*==============================================================*/
/* Table: MPOMNG_AC_FREEZE_DETAILS_INF  账户金额冻结明细表                */
/*==============================================================*/
drop table if exists MPOMNG_AC_FREEZE_DETAILS_INF;
create table MPOMNG_AC_FREEZE_DETAILS_INF 
(
   FREEZE_NO            varchar(12)                NOT null COMMENT '冻结编号',
   AC_NO                varchar(20)                    null COMMENT '支付账户',
   FREEZE_WAY           varchar(2)                     null COMMENT '01=账户冻结 02=现金冻结',
   CCY                  char(1)                        null COMMENT '货币类型',
   FREEZE_AMT           varchar(12)                    null COMMENT '冻结金额',
   BEGIN_DATE           varchar(8)                     null COMMENT '开始日期',
   END_DATE             varchar(8)                     null COMMENT '结束日期',
   FREEZE_REASON        varchar(200)                   null COMMENT '冻结原因',
   BIZ_TYPE             varchar(6)                     null COMMENT '业务类型',
   BIZ_CODE             varchar(25)                    null COMMENT '业务订单编码',
   LST_UPT_DATE         varchar(14)                    null COMMENT '操作时间',
   U_ID                 int(10)                        null COMMENT '操作员',
   constraint PK_MPOMNG_AC_FREEZE_DETAILS_INF primary key clustered (FREEZE_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户金额冻结明细表';




/*==============================================================*/
/* Table: MPOMNG_AGE_CUST_REF_INF     商户代理商关系表                         */
/*==============================================================*/
drop table if exists MPOMNG_AGE_CUST_REF_INF_BACK;
create table MPOMNG_AGE_CUST_REF_INF_BACK 
(
   CUST_ID              varchar(14)                       not null COMMENT '商户编号',
   AGE_ID               varchar(14)                       not null COMMENT '代理商编号',
   TERMINAL_ID          varchar(20)                       not null COMMENT '终端编号',
   constraint PK_MPOMNG_AGE_CUST_REF_INF primary key clustered (CUST_ID, AGE_ID,TERMINAL_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户代理商关系表';


/*==============================================================*/
/* Table: MPOMNG_TD_RATE_INF     平台费率信息表                                   */
/*==============================================================*/
drop table if exists MPOMNG_TD_RATE_INF;
create table MPOMNG_TD_RATE_INF 
(
   RATE_CODE            varchar(10)                       not null COMMENT '费率代码',
   RATE_NAME            varchar(25)                       not null COMMENT '费率名称',
   RATE_TYPE            varchar(2)                        not null COMMENT '收费类型  01 定额收费 02 百分比收费', 
   RATE_VAL             varchar(20)                       not null COMMENT '费率值',
   constraint PK_MPOMNG_TD_RATE_INF primary key clustered (RATE_CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台费率信息表';


/*==============================================================*/
/* Table: MPOMNG_APP_INF     APP 信息 表                                  */
/*==============================================================*/
drop table if exists MPOMNG_APP_INF;
CREATE TABLE MPOMNG_APP_INF (
  APP_ID           varchar(9)     NOT NULL       COMMENT 'ID 主键',
  APP_NAME         varchar(20)    DEFAULT NULL   COMMENT 'APP 名称',
  APP_VERSION      varchar(4)     DEFAULT NULL   COMMENT 'APP 版本号',
  APP_PLATFORM     varchar(1)     DEFAULT NULL   COMMENT 'APP 平台 1 IOS 2ANDROID',
  APP_AUTO_UPDATE  varchar(1)     DEFAULT NULL   COMMENT 'APP 是否自动更新  1 是 2否 3强制更新',
  APP_DESC         varchar(500)   DEFAULT NULL   COMMENT 'APP 说明',
  APP_ISSUE_DATE   varchar(14)    DEFAULT NULL   COMMENT 'APP 发布时间',
  APP_FILE_SZIE    varchar(5)     DEFAULT NULL   COMMENT 'APP 文件 大小  单位:MB',
  APP_FILE_ID      varchar(25)    DEFAULT NULL   COMMENT 'APP文件ID',
  APP_FILE_NAME    varchar(100)   DEFAULT NULL   COMMENT 'APP文件路径',
  APP_FILE_PATH    varchar(255)   DEFAULT NULL,
  CREATE_USER_ID   varchar(20)    DEFAULT NULL   COMMENT '创建人',
  CREATE_DATE      varchar(14)    DEFAULT NULL   COMMENT '创建时间',
  PRIMARY KEY (APP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP 信息 表';


/*==============================================================*/
/* Table: MPOMNG_APPIMG_INF     APP 轮播图                                  */
/*==============================================================*/
drop table if exists MPOMNG_APPIMG_INF;
CREATE TABLE MPOMNG_APPIMG_INF (
  APPIMG_ID       varchar(9)    NOT NULL,
  APPIMG_PATH     varchar(255)  DEFAULT NULL COMMENT 'APP 轮播图 路径',
  APPIMG_DESC     varchar(255)  DEFAULT NULL COMMENT 'APP 轮播图 描述',
  CREATE_USER_ID  varchar(20)   DEFAULT NULL,
  CREATE_DATE     varchar(14)   DEFAULT NULL,
  PRIMARY KEY (APPIMG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP 轮播图';


/*==============================================================*/
/* Table: MPOMNG_CUST_BANK_INF     商户结算信息表                                  */
/*==============================================================*/
drop table if exists MPOMNG_CUST_BANK_INF;
CREATE TABLE MPOMNG_CUST_BANK_INF (
  BANK_CARD_ID       varchar(20)    DEFAULT NULL COMMENT '银行卡记录ID',
  CUST_ID            varchar(14)    DEFAULT NULL COMMENT '商户编号',
  ISSNO              varchar(10)    DEFAULT NULL COMMENT '发卡行代码',
  ISSNAM             varchar(100)   DEFAULT NULL COMMENT '发卡行名称',
  PROVINCE_ID        varchar(7)     DEFAULT NULL COMMENT '开户省份ID',
  CITY_ID            varchar(7)     DEFAULT NULL COMMENT '开户城市ID',
  CNAPS_CODE         varchar(20)    DEFAULT NULL COMMENT '联行号',
  CARD_TYPE          varchar(10)    DEFAULT NULL COMMENT '卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡',
  CARD_STATE         varchar(2)     DEFAULT NULL COMMENT '卡片状态：1-默认使用；0-正常；-1：不可使用',
  CARD_NO            varchar(50)    DEFAULT NULL COMMENT '银行卡号',
  MOBILE_NO          varchar(20)    DEFAULT NULL COMMENT '银行预留手机号',
  BINDING_TIME       varchar(50)    DEFAULT NULL COMMENT '  绑定时间',
  UNBUNDLING_TIME    varchar(50)    DEFAULT NULL COMMENT '解绑时间',
  BANKCARD_FRONT     varchar(255)   DEFAULT NULL COMMENT '银行卡正面照',
  BANKCARD_BACK      varchar(255)   DEFAULT NULL COMMENT '银行卡反面照'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户结算信息表';


/*==============================================================*/
/* Table: MPOMNG_CUST_BANK_INF_TEMP     商户结算信息临时表                                  */
/*==============================================================*/
drop table if exists MPOMNG_CUST_BANK_INF_TEMP;
CREATE TABLE MPOMNG_CUST_BANK_INF_TEMP (
  BANK_CARD_ID       varchar(20)    DEFAULT NULL COMMENT '银行卡记录ID',
  CUST_ID            varchar(14)    DEFAULT NULL COMMENT '商户编号',
  ISSNO              varchar(10)    DEFAULT NULL COMMENT '发卡行代码',
  ISSNAM             varchar(100)   DEFAULT NULL COMMENT '发卡行名称',
  PROVINCE_ID        varchar(7)     DEFAULT NULL COMMENT '开户省份ID',
  CITY_ID            varchar(7)     DEFAULT NULL COMMENT '开户城市ID',
  CNAPS_CODE         varchar(20)    DEFAULT NULL COMMENT '联行号',
  CARD_TYPE          varchar(10)    DEFAULT NULL COMMENT '卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡',
  CARD_STATE         varchar(2)     DEFAULT NULL COMMENT '卡片状态：1-默认使用；0-正常；-1：不可使用',
  OLD_CARD_NO        varchar(50)    DEFAULT NULL COMMENT '当前卡号（当用户发起银行卡号变更时存改变前的卡号）',
  CARD_NO            varchar(50)    DEFAULT NULL COMMENT '银行卡号',
  MOBILE_NO          varchar(20)    DEFAULT NULL COMMENT '银行预留手机号',
  BINDING_TIME       varchar(50)    DEFAULT NULL COMMENT '  绑定时间',
  UNBUNDLING_TIME    varchar(50)    DEFAULT NULL COMMENT '解绑时间',
  BANKCARD_FRONT     varchar(255)   DEFAULT NULL COMMENT '银行卡正面照',
  BANKCARD_BACK      varchar(255)   DEFAULT NULL COMMENT '银行卡反面照'
  UPDATE_DESC        varchar(255)   DEFAULT NULL COMMENT '修改原因描述信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户结算信息临时表';


/*==============================================================*/
/* Table: MPOMNG_MERIDENTIFY_INF     商户实名认证信息表                                  */
/*==============================================================*/
drop table if exists MPOMNG_MERIDENTIFY_INF;
CREATE TABLE MPOMNG_MERIDENTIFY_INF (
  CUST_ID                  char(14)       NOT NULL     COMMENT '商户编号(主键)',
  CUST_NAME                varchar(50)    DEFAULT NULL COMMENT '商户姓名',
  CERTIFICATE_TYPE         varchar(2)     DEFAULT NULL COMMENT '证件类型:1-身份证',
  CERTIFICATE_NO           varchar(50)    DEFAULT NULL COMMENT '证件号码',
  IDCARD_HANDHELD          varchar(255)   DEFAULT NULL COMMENT '手持身份证照片',
  IDCARD_FRONT             varchar(200)   DEFAULT NULL COMMENT '身份证正面',
  IDCARD_BACK              varchar(200)   DEFAULT NULL COMMENT '身份证反面',
  CUST_STATUS              char(1)        DEFAULT '0'  COMMENT '认证状态（0未认证，1审核中，2审核通过，3审核不通过）',
  IDENTIFY_TIME            varchar(14)    DEFAULT NULL COMMENT '认证时间',
  AUDIT_IDEA               varchar(200)   DEFAULT NULL COMMENT '认证审核意见',
  IDENTIFY_USER            varchar(20)    DEFAULT NULL COMMENT '审核人',
  POLICE_IDENTIFYSTATUS    varchar(2)     DEFAULT '0'  COMMENT '公安部身份认证状态:0-未认证；1-认证通过；2-认证不通过',
  POLICE_IDENTIFYPIC       varchar(200)   DEFAULT NULL COMMENT '公安部身份认证图片',
  RES_FIELD1               varchar(50)    DEFAULT NULL COMMENT '预留字段1',
  RES_FIELD2               varchar(50)    DEFAULT NULL COMMENT '预留字段2',
  RES_FIELD3               varchar(50)    DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户实名认证信息表';



/*==============================================================*/
/* Table: MPOMNG_NOTICE_INF     公告表                                  */
/*==============================================================*/
drop table if exists MPOMNG_NOTICE_INF;
CREATE TABLE MPOMNG_NOTICE_INF (
  NOTICE_ID         varchar(9)    NOT NULL COMMENT '公告ID',
  NOTICE_PLATFORM   varchar(1)    DEFAULT NULL COMMENT '公告平台   1代理商 2商户(手机APP)',
  NOTICE_TITLE      varchar(255)  DEFAULT NULL COMMENT '公告标题',
  NOTICE_BODY       varchar(1000) DEFAULT NULL COMMENT '公告内容',
  NOTICE_ISSUE      varchar(10)   DEFAULT NULL COMMENT '公告发布人',
  NOTICE_ISSUE_DATE varchar(14)   DEFAULT NULL COMMENT '公告发布时间',
  CREATE_USER_ID    varchar(20)   DEFAULT NULL,
  CREATE_DATE       varchar(14)   DEFAULT NULL,
  PRIMARY KEY (NOTICE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告表';



/*==============================================================*/
/* Table: MPOMNG_PAY_INF     支付订单表                                  */
/*==============================================================*/
drop table if exists MPOMNG_PAY_INF;
CREATE TABLE MPOMNG_PAY_INF (
  PAYORDNO      varchar(50)    NOT NULL     COMMENT '支付订单号',
  CUST_ID       varchar(50)    DEFAULT NULL COMMENT '商户ID',
  PAYSTATUS     varchar(10)    DEFAULT '00' COMMENT '订单状态，00:未支付  01:支付成功  02:支付失败  03:退款审核中 04:退款处理中 05:退款成功 06:退款失败 07:撤销审核中 08：同意撤销  09：撤销成功 10:撤销失败 11：订单作废  21：支付处理中 99:超时',
  PAYORDTYPE    varchar(10)    DEFAULT NULL COMMENT '支付订单类型',
  PAYTYPE       varchar(10)    DEFAULT NULL COMMENT '支付方式：01 支付账户   02 终端   03 快捷支付',
  TXAMT         varchar(12)    DEFAULT NULL COMMENT '订单金额，单位：分',
  RATE          varchar(10)    DEFAULT NULL COMMENT '费率',
  FEE           varchar(12)    DEFAULT NULL COMMENT '手续费，单位：分',
  NETRECAMT     varchar(12)    DEFAULT NULL COMMENT '实际金额，单位：分',
  PAY_CHANNEL   varchar(100)   DEFAULT NULL COMMENT '支付渠道编号',
  PAY_DATE      varchar(12)    DEFAULT NULL COMMENT '支付日期',
  PAY_TIME      varchar(12)    DEFAULT NULL COMMENT '支付时间',
  PAYORDTIME    varchar(20)    DEFAULT NULL COMMENT '支付完整时间（年月日时分秒）',
  MODIFY_TIME   varchar(20)    DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (PAYORDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付订单表';


/*==============================================================*/
/* Table: MPOMNG_PRD_INF     商品订单表                                  */
/*==============================================================*/
drop table if exists MPOMNG_PRD_INF;
CREATE TABLE MPOMNG_PRD_INF (
  PRDORDNO         varchar(25)   NOT NULL     COMMENT '商品订单号',
  PRDORDTYPE       varchar(2)    DEFAULT NULL COMMENT '订单类型 01收款 02商品',
  BIZTYPE          varchar(10)   DEFAULT NULL COMMENT '子业务类型，01：手机充值 02 彩票',
  ORDSTATUS        varchar(10)   DEFAULT '00' COMMENT '订单状态 00:未交易 01:成功 02:失败 03:可疑 04:处理中 05:已取消 06:未支付 07:已退货 08:退货中 09:部分退货',
  ORDAMT           varchar(12)   DEFAULT NULL COMMENT '订单金额，单位：分',
  PAYORDNO         varchar(25)   DEFAULT NULL COMMENT '支付订单号(最后一次发起的支付订单)',
  PRICE            varchar(12)   DEFAULT NULL COMMENT '商品单价，单位：分',
  GOODS_NAME       varchar(200)  DEFAULT NULL COMMENT '商品名称',
  GOODS_NAME_SHORT varchar(255)  DEFAULT NULL COMMENT '商品简称',
  CUST_ID          varchar(25)   DEFAULT NULL COMMENT '发起商户ID',
  PRDDATE          varchar(20)   DEFAULT NULL COMMENT '订单日期',
  PRDTIME          varchar(20)   DEFAULT NULL COMMENT '订单时间',
  ORDTIME          varchar(20)   DEFAULT NULL COMMENT '订单时间（年月日时分秒）',
  MODIFY_TIME      varchar(20)   DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (PRDORDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';


/*==============================================================*/
/* Table: MPOMNG_PAYMENT_JOURNAL_INF      收款支付流水表-交易流水                                 */
/*==============================================================*/
drop table if exists MPOMNG_PAYMENT_JOURNAL_INF ;
CREATE TABLE MPOMNG_PAYMENT_JOURNAL_INF (
  PAYMENT_ID      varchar(20)      NOT NULL COMMENT 'id',
  PAYORDNO        varchar(25)      DEFAULT NULL COMMENT '支付订单号',
  TXN_DATE        varchar(10)      DEFAULT NULL COMMENT '交易日期',
  TXN_TIME        varchar(10)      DEFAULT NULL COMMENT '交易时间',
  TXN_TYPE        varchar(2)       DEFAULT '01' COMMENT '交易类型 01 消费 02 消费撤销...',
  TXN_SREFNO      varchar(20)      DEFAULT NULL COMMENT '批次号',
  TXN_CRDFLG      varchar(2)       DEFAULT NULL COMMENT '卡类型',
  TXN_ACTNO       varchar(19)      DEFAULT NULL COMMENT '交易卡号',
  TXN_AMT         varchar(12)      DEFAULT NULL COMMENT '交易金额',
  TXN_FEE         varchar(12)      DEFAULT NULL COMMENT '手续费',
  TXN_STATUS      varchar(1)       DEFAULT '0'  COMMENT '交易状态 0 预计 S成功 F失败 C冲正 R撤销 T超时 E完成',
  INMOD           varchar(5)       DEFAULT NULL COMMENT '接入方式',
  TTXNDT          varchar(10)      DEFAULT NULL COMMENT '第三方交易日期',
  TTXNTM          varchar(10)      DEFAULT NULL COMMENT '第三方交易时间',
  TMERCID         varchar(20)      DEFAULT NULL COMMENT '第三方商户号',
  TTERMID         varchar(20)      DEFAULT NULL COMMENT '第三方终端号',
  TLOGNO          varchar(30)      DEFAULT NULL COMMENT '第三方交易流水号',
  TBATNO          varchar(10)      DEFAULT NULL COMMENT '第三方批次号',
  TAUTCOD         varchar(10)      DEFAULT NULL COMMENT '第三方授权码',
  TOPRID          varchar(10)      DEFAULT NULL COMMENT '第三方操作员',
  TCPSCOD         varchar(10)      DEFAULT NULL COMMENT '第三方返回码',
  TSREFNO         varchar(20)      DEFAULT NULL COMMENT '第三方系统参考号',
  PRIMARY KEY (PAYMENT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收款支付流水表-交易流水';


/*==============================================================*/
/* Table: MPOMNG_CAS_INF     提现订单表                                 */
/*==============================================================*/
drop table if exists  MPOMNG_CAS_INF ;
CREATE TABLE MPOMNG_CAS_INF (
  CAS_ORD_NO         varchar(25)     NOT NULL       COMMENT '提现订单号',
  CAS_TYPE           varchar(10)     NOT NULL       COMMENT '提现类型，0：T0，1：T1',
  CAS_DATE           varchar(20)     DEFAULT NULL   COMMENT '订单创建时间',
  MODIFY_DATE        varchar(20)     DEFAULT NULL   COMMENT '最后操作时间',
  SUC_DATE           varchar(20)     DEFAULT NULL   COMMENT '订单完成时间',
  ORDSTATUS          varchar(10)     DEFAULT NULL   COMMENT '订单状态 00:未处理 01:成功 02:失败 03:可疑 04:处理中 05:已取消 ',
  TXCCY              varchar(10)     DEFAULT 156    COMMENT '货币类型',
  CUST_ID            varchar(50)     DEFAULT NULL   COMMENT '所属商户编号',
  CUST_NAME          varchar(100)    DEFAULT NULL   COMMENT '提现商户户名',
  TXAMT              varchar(12)     DEFAULT NULL   COMMENT '订单金额',
  RATE               varchar(10)     DEFAULT NULL   COMMENT '费率',
  FEE                varchar(12)     DEFAULT NULL   COMMENT '手续费,单位：分',
  NETRECAMT          varchar(12)     DEFAULT NULL   COMMENT '实际到账金额，单位：分',
  ISSNO              varchar(10)     DEFAULT NULL   COMMENT '发卡行代码',
  ISSNAM             varchar(100)    DEFAULT NULL   COMMENT '发卡行名称',
  CARD_NO            varchar(50)     DEFAULT NULL   COMMENT '提现银行卡号',
  PROVINCE_ID        varchar(7)      DEFAULT NULL,
  CAS_DESC           varchar(100)    DEFAULT NULL   COMMENT '提现描述，如提现成功，或通道返回的错误信息等可以存入该字段',
  CAS_AUDIT          varchar(10)     DEFAULT 1      COMMENT '提现审核状态，0：不通过，1：审核通过',
  AUDIT_DESC         varchar(100)    DEFAULT NULL   COMMENT '审核描述',
  CAS_REL            varchar(255)    DEFAULT NULL   COMMENT '提现参照其他表中的关联字段，如T+0可能需要关联充值订单，此处可存放充值订单号',
  FILED1             varchar(255)    DEFAULT NULL,
  FILED2             varchar(255)    DEFAULT NULL,
  FILED3             varchar(255)    DEFAULT NULL,
  PRIMARY KEY (CAS_ORD_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现订单表';




/*==============================================================*/
/* Table: MPOMNG_MER_PROFIT_INF     商户利润信息表                             */
/*==============================================================*/
drop table if exists  MPOMNG_MER_PROFIT_INF ;
CREATE TABLE MPOMNG_MER_PROFIT_INF (
  PROFIT_ID          varchar(25)     NOT NULL       COMMENT '分润流水',
  PAYNO              varchar(25)     NOT NULL       COMMENT '支付流水',
  CUST_ID            varchar(14)     NOT NULL       COMMENT '商户编号',
  CUST_CLASS         varchar(2)      NOT NULL       COMMENT '商户等级',
  PROFIT_DATE        varchar(8)      DEFAULT NULL   COMMENT '分润日期',
  TXN_AMT            varchar(12)     DEFAULT NULL   COMMENT '交易分润金额',
  MNG_AMT            varchar(12)     DEFAULT NULL   COMMENT '管理分润金额',
  REFRENCE           varchar(14)     NOT NULL       COMMENT '推荐人',
  PRIMARY KEY (PROFIT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户利润信息表';

/*==============================================================*/
/* Table: MPOMNG_MER_PROFIT_RULE_INF     商户分润规则表                    */
/*==============================================================*/
drop table if exists  MPOMNG_MER_PROFIT_RULE_INF ;
CREATE TABLE MPOMNG_MER_PROFIT_RULE_INF (
  PROFIT_TYPE        varchar(2)      NOT NULL       COMMENT '分润类型 01交易分润 02管理分润',
  CUST_CLASS         varchar(2)      NOT NULL       COMMENT '商户等级',
  RATE_PERCENT       varchar(8)      DEFAULT NULL   COMMENT '分润比',
  RATE_TOP           varchar(12)     DEFAULT NULL   COMMENT '封顶'
  PRIMARY KEY (PROFIT_TYPE,CUST_CLASS)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户分润规则表';

