--附件表
DROP TABLE ATTACHMENT;
CREATE TABLE ATTACHMENT(
    ID          VARCHAR(25)            , -- ID主键
    MODULENAME  VARCHAR(10)            , -- 模块名称
    TABLENAME   VARCHAR(40)            , -- 表名
    PKID        VARCHAR(20)            , -- 表名中对应的记录ID
    LX          VARCHAR(10)            , -- 类型
    DX          INT                    , -- 附件大小
    DPI         VARCHAR(50)            , -- 分辨率
    ORDERNUM    VARCHAR(10)            , -- 排序号
    FJNAME      VARCHAR(60)            , -- 附件名称
    FJPATH      VARCHAR(255)           , -- 附件路径
    FJO         VARCHAR(20)            , -- 创建人
    FJT         VARCHAR(14)            , -- 创建时间
    SFSX        VARCHAR(1)  DEFAULT '0', -- 是否生效 0:生效 1:无效
    PRIMARY KEY (ID)
);

--码表
DROP TABLE DICT;
CREATE TABLE DICT(
    DICT_ID     VARCHAR(20)            , -- ID主键
    PARENT_ID   VARCHAR(20)            , -- 父ID
    DICT_CODE   VARCHAR(20)            , -- 类型
    DICT_VALUE  VARCHAR(20)            , -- 码值
    DICT_NAME   VARCHAR(30)            , -- 名称
    ABR         VARCHAR(20)            , -- 简称   未使用
    HLP         VARCHAR(20)            , -- 助记码 未使用
    SEQ_NUM     VARCHAR(5)             , -- 排序
    DICT_LEVEL  VARCHAR(5)             , -- 级别 从1开始 多层级时，用来使用
    STATUS      CHAR(1)      DEFAULT '1', -- 是否可用 0:不可  1:可用
    PRIMARY KEY (DICT_ID)
);

delete from dict;

insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000001', '0'       , 'SEX', null, '性别', '1', '1');
insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000002', '10000001', 'SEX', '0' , '女'  , '1', '2');
insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000003', '10000001', 'SEX', '1' , '男'  , '2', '2');

insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000004', '0'       , 'CERTTYPE', null, '证件类型', '2', '1');
insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000005', '10000004', 'CERTTYPE', '0' , '身份证'  , '1', '2');
insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000006', '10000004', 'CERTTYPE', '1' , '军官证'  , '2', '2');
insert into dict (DICT_ID, PARENT_ID, DICT_CODE, DICT_VALUE, DICT_NAME, SEQ_NUM, DICT_LEVEL)
values ('10000007', '10000004', 'CERTTYPE', '2' , '学生证'  , '3', '2');

commit;

--平台公共加锁记录表
DROP TABLE PUBLCKREC;
CREATE TABLE PUBLCKREC ( 
RECKEY VARCHAR(80)     NOT NULL,       --锁键值
LCKTIM VARCHAR(10)     NOT NULL,       --加锁时间
TIMOUT VARCHAR(6)      NOT NULL,       --失效时间
UPDFLG CHAR(1)          NOT NULL,       --更新标志
PRIMARY KEY (RECKEY) );

--平台公共信息表
CREATE TABLE PUBPLTINF ( 
SYSID CHARACTER(6) NOT NULL,         --系统ID
LOGNO CHARACTER(14),                 --当前前置流水号  YYMMDD+8流水号
BLOGNO CHARACTER(12),                --当前批量流水号
ACTDAT CHARACTER(8) NOT NULL,        --会计日期
DATEND CHARACTER(2),                 --日终状态
ELEBK  CHARACTER(6),                 --电子联行号
PRIMARY KEY (SYSID) 
);


--公共序号表
CREATE TABLE PUB_SEQREC_INF ( 
         KEYNAM VARCHAR(30 ) NOT NULL,           --键名
         KEYVAL VARCHAR(10 ) NOT NULL,           --键值
         PRIMARY KEY (KEYNAM)
);

--冲正表
DROP TABLE PUBIGTJNL;
CREATE TABLE PUBIGTJNL (
TRANID VARCHAR(100) NOT NULL,      --交易唯一性ID 
TLV INT,                     --离散值
APPNAME CHAR(100) NOT NULL,         --应用名
OPRCLK INT NOT NULL,         --每次冲正发起时间
FALTMS INT,                   --失败次数
TIMEOUT INT NOT NULL,         --冲正超时时间
MAXTMS INT NOT NULL,          --最大可发起冲正次数
DATA VARCHAR(3072),                --发起冲正所需数据
STS CHAR(1) NOT NULL,               --状态
TXNCODE VARCHAR(50),               --冲正交易码
LOGLEVEL VARCHAR(10),              --日志级别
LOGNO VARCHAR(50),                 --冲正流水号（支付订单流水号）
STARTTIME VARCHAR(14) NOT NULL,    --第一次冲正发起时间
PRIMARY KEY (TRANID, APPNAME, STARTTIME) );


insert into PUBPLTINF(SYSID,LOGNO,BLOGNO,ACTDAT,DATEND,ELEBK) values(
'001','14070900000000','','20140709','',''
);

--序号表
CREATE TABLE POSSEQREC
(	KEYNAM VARCHAR(30), 
	KEYVAL VARCHAR(10)
) 


-- ----------------------------
-- Table structure for CNAPS
-- ----------------------------
DROP TABLE IF EXISTS CNAPS;
CREATE TABLE CNAPS (
  CNAPS_CODE varchar(20)  NULL COMMENT '联行号',
  BANK_NAME varchar(255)  NULL COMMENT '银行名称',
  SUB_BRANCH varchar(255)  NULL COMMENT '支行',
  BANK_PRO varchar(50)  NULL COMMENT '所在省',
  BANK_CITY varchar(50)  NULL COMMENT '所在地市'
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='银行网点 CNAPS(联行号)';

-- ----------------------------
-- Table structure for UNIONFIT
-- ----------------------------
DROP TABLE IF EXISTS UNIONFIT;
CREATE TABLE UNIONFIT (
  ISSNAM varchar(100)  NULL COMMENT '发卡行名称',
  ISSNO varchar(10)  NULL COMMENT '发卡行代码',
  CRDNAM varchar(100)  NULL COMMENT '卡名称',
  FITCRK varchar(3)  NULL COMMENT 'Fit所在磁道',
  FITOFF varchar(3)  NULL COMMENT 'Fit起始字节',
  FITLEN varchar(7)  NULL COMMENT 'Fit长度',
  CRDOFF varchar(3)  NULL COMMENT '主账号起始字节',
  CRDLEN varchar(2)  NULL COMMENT '主账号长度',
  CRDCTT varchar(20)  NULL COMMENT '主账号',
  CRDCRK varchar(3)  NULL COMMENT '主账号所在磁道',
  BINOFF varchar(3)  NULL COMMENT '发卡行标识起始字节',
  BINLEN varchar(2)  NULL COMMENT '发卡行标识长度',
  BINCTT varchar(13)  NULL COMMENT '发卡行标识取值',
  BINCRK varchar(3)  NULL COMMENT '发卡行标识读取磁道',
  DCFLAG varchar(2)  NULL COMMENT '卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡'
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='卡BIN';


/*==============================================================*/
/* Table: MSGCODE  消息码维护表表                                  */
/*==============================================================*/
DROP TABLE IF EXISTS MSGCODE;
CREATE TABLE MSGCODE (
  MSG_ID varchar(6)  NULL COMMENT '消息码',
  MSG_CONTENT varchar(200)  NULL COMMENT '消息描述',
  MSG_FLAG char(1)  default '0' NULL COMMENT '消息标记 0 不显示给前端 1显示给前端',
   PRIMARY KEY (MSGCODE);
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='消息码维护表';

/*==============================================================*/
/* Table: TRAN_ERROR_CODE  渠道错误代码维护表                                        */
/*==============================================================*/
DROP TABLE IF EXISTS TRAN_ERROR_CODE;
CREATE TABLE TRAN_ERROR_CODE (
  MSG_SYS_ID   varchar(6)  NULL COMMENT '系统错误码',
  MSG_THIRD_ID varchar(6)  NULL COMMENT '第三方错误码',
  MSG_CONTENT varchar(200)  NULL COMMENT '错误码描述',
  PRIMARY KEY (MSG_SYS_ID);
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='渠道错误代码维护表';

