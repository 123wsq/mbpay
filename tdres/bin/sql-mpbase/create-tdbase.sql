--附件表
DROP TABLE ATTACHMENT;
CREATE TABLE ATTACHMENT(
    ID          VARCHAR2(25)            , -- ID主键
    MODULENAME  VARCHAR2(10)            , -- 模块名称
    TABLENAME   VARCHAR2(40)            , -- 表名
    PKID        VARCHAR2(20)            , -- 表名中对应的记录ID
    LX          VARCHAR2(10)            , -- 类型
    DX          NUMBER(12)              , -- 附件大小
    DPI         VARCHAR2(50)            , -- 分辨率
    ORDERNUM    VARCHAR2(10)            , -- 排序号
    FJNAME      VARCHAR2(60)            , -- 附件名称
    FJPATH      VARCHAR2(255)           , -- 附件路径
    FJO         VARCHAR2(20)            , -- 创建人
    FJT         VARCHAR2(14)            , -- 创建时间
    SFSX        VARCHAR2(1)  DEFAULT '0', -- 是否生效 0:生效 1:无效
    PRIMARY KEY (ID)
);

--码表
DROP TABLE DICT;
CREATE TABLE DICT(
    DICT_ID     VARCHAR2(20)            , -- ID主键
    PARENT_ID   VARCHAR2(20)            , -- 父ID
    DICT_CODE   VARCHAR2(20)            , -- 类型
    DICT_VALUE  VARCHAR2(20)            , -- 码值
    DICT_NAME   VARCHAR2(30)            , -- 名称
    ABR         VARCHAR2(20)            , -- 简称   未使用
    HLP         VARCHAR2(20)            , -- 助记码 未使用
    SEQ_NUM     VARCHAR2(5)             , -- 排序
    DICT_LEVEL  VARCHAR2(5)             , -- 级别 从1开始 多层级时，用来使用
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
RECKEY VARCHAR2(80)     NOT NULL,       --锁键值
LCKTIM VARCHAR2(10)     NOT NULL,       --加锁时间
TIMOUT VARCHAR2(6)      NOT NULL,       --失效时间
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
TRANID VARCHAR2(100) NOT NULL,      --交易唯一性ID 
TLV NUMBER(20),                     --离散值
APPNAME CHAR(100) NOT NULL,         --应用名
OPRCLK NUMBER(14) NOT NULL,         --每次冲正发起时间
FALTMS NUMBER(4),                   --失败次数
TIMEOUT NUMBER(4) NOT NULL,         --冲正超时时间
MAXTMS NUMBER(4) NOT NULL,          --最大可发起冲正次数
DATA VARCHAR2(3072),                --发起冲正所需数据
STS CHAR(1) NOT NULL,               --状态
TXNCODE VARCHAR2(50),               --冲正交易码
LOGLEVEL VARCHAR2(10),              --日志级别
LOGNO VARCHAR2(50),                 --冲正流水号（支付订单流水号）
STARTTIME VARCHAR2(14) NOT NULL,    --第一次冲正发起时间
PRIMARY KEY (TRANID, APPNAME, STARTTIME) );
DROP INDEX PUBIGTJNL_INDEX1;
CREATE INDEX PUBIGTJNL_INDEX1 ON PUBIGTJNL (APPNAME, STS);


insert into PUBPLTINF(SYSID,LOGNO,BLOGNO,ACTDAT,DATEND,ELEBK) values(
'001','14070900000000','','20140709','',''
);