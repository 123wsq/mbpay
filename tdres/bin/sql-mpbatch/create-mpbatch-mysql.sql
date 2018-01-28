-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_BANK_INF   对账-银行记录信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_BANK_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_BANK_INF (
  BANKCODE    varchar(20)   NOT NULL      COMMENT '银行编码',
  ACTDATE     varchar(8)    NOT NULL      COMMENT '交易日期',
  BANKLOGNO   varchar(30)   NOT NULL      COMMENT '银行流水号',
  CHKTYPE     varchar(2)    DEFAULT NULL  COMMENT '明细类型  01 消费  02 消费撤销',
  BANKDATE    varchar(8)    DEFAULT NULL  COMMENT '结算日期',
  CCYCOD      varchar(3)    DEFAULT NULL  COMMENT '交易货币',
  TXNAMT      varchar(12)   DEFAULT NULL  COMMENT '交易金额   分',
  FEE         varchar(12)   DEFAULT NULL  COMMENT '手续费',
  INAMT       varchar(12)   DEFAULT NULL  COMMENT '实收净额',
  CUST_ID     varchar(20)   DEFAULT NULL  COMMENT '商户号',
  PAYNO       varchar(30)   DEFAULT NULL  COMMENT '支付订单号',
  CHECKDATE   varchar(8)    DEFAULT NULL  COMMENT '对账日期',
  CHECKTIME   varchar(8)    DEFAULT NULL  COMMENT '对账时间',
  PRIMARY KEY (BANKCODE,ACTDATE,BANKLOGNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-银行记录信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_DOUBT_INF   对账-存疑信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_DOUBT_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_DOUBT_INF (
  BANKCODE       varchar(20)   NOT NULL     COMMENT '银行编码',
  ACTDATE        varchar(8)    NOT NULL     COMMENT '交易日期',
  BANKLOGNO      varchar(30)   NOT NULL     COMMENT '银行流水号',
  CHKTYPE        varchar(2)    DEFAULT NULL COMMENT '明细类型  01 消费  02 消费撤销',
  BANKDATE       varchar(8)    DEFAULT NULL COMMENT '结算日期',
  CCYCOD         varchar(3)    DEFAULT NULL COMMENT '交易货币',
  TXNAMT         varchar(12)   DEFAULT NULL COMMENT '交易金额   分',
  FEE            varchar(12)   DEFAULT NULL COMMENT '手续费',
  INAMT          varchar(12)   DEFAULT NULL COMMENT '实收净额',
  CUST_ID        varchar(20)   DEFAULT NULL COMMENT '商户号',
  PAYNO          varchar(30)   DEFAULT NULL COMMENT '支付订单号',
  CHECKDATE      varchar(8)    DEFAULT NULL COMMENT '对账日期',
  CHECKTIME      varchar(8)    DEFAULT NULL COMMENT '对账时间',
  CHECKDOUBTTYPE varchar(2)    DEFAULT NULL COMMENT '类型   01 银行有-系统无 02 银行无-系统有',
  CHECKNUM       varchar(2)    DEFAULT '0'  COMMENT '对账次数',
  PRIMARY KEY (BANKCODE,ACTDATE,BANKLOGNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-存疑信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_ERROR_INF  对账-错账信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_ERROR_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_ERROR_INF (
  BANKCODE       varchar(20)   NOT NULL     COMMENT '银行编码',
  ACTDATE        varchar(8)    NOT NULL     COMMENT '交易日期',
  BANKLOGNO      varchar(30)   NOT NULL     COMMENT '银行流水号',
  CHKTYPE        varchar(2)    DEFAULT NULL COMMENT '明细类型  01 消费  02 消费撤销',
  BANKDATE       varchar(8)    DEFAULT NULL COMMENT '结算日期',
  CCYCOD         varchar(3)    DEFAULT NULL COMMENT '交易货币',
  TXNAMT         varchar(12)   DEFAULT NULL COMMENT '交易金额   分',
  FEE            varchar(12)   DEFAULT NULL COMMENT '手续费',
  INAMT          varchar(12)   DEFAULT NULL COMMENT '实收净额',
  CUST_ID        varchar(20)   DEFAULT NULL COMMENT '商户号',
  PAYNO          varchar(30)   DEFAULT NULL COMMENT '支付订单号',
  CHECKDATE      varchar(8)    DEFAULT NULL COMMENT '对账日期',
  CHECKTIME      varchar(8)    DEFAULT NULL COMMENT '对账时间',
  CHECKDOUBTTYPE varchar(2)    DEFAULT NULL COMMENT '类型   01 银行有-系统无 02 银行无-系统有',
  CHECKNUM       varchar(2)    DEFAULT '0'  COMMENT '对账次数',
  PRIMARY KEY (BANKCODE,ACTDATE,BANKLOGNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-错账信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_INF  对账-成功记录信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_INF (
  BANKCODE   varchar(20)   NOT NULL     COMMENT '银行编码',
  ACTDATE    varchar(8)    NOT NULL     COMMENT '交易日期',
  BANKLOGNO  varchar(30)   NOT NULL     COMMENT '银行流水号',
  CHKTYPE    varchar(2)    DEFAULT NULL COMMENT '明细类型  01 消费  02 消费撤销',
  BANKDATE   varchar(8)    DEFAULT NULL COMMENT '结算日期',
  CCYCOD     varchar(3)    DEFAULT NULL COMMENT '交易货币',
  TXNAMT     varchar(12)   DEFAULT NULL COMMENT '交易金额   分',
  FEE        varchar(12)   DEFAULT NULL COMMENT '手续费',
  INAMT      varchar(12)   DEFAULT NULL COMMENT '实收净额',
  CUST_ID    varchar(20)   DEFAULT NULL COMMENT '商户号',
  PAYNO      varchar(30)   DEFAULT NULL COMMENT '支付订单号',
  CHECKDATE  varchar(8)    DEFAULT NULL COMMENT '对账日期',
  CHECKTIME  varchar(8)    DEFAULT NULL COMMENT '对账时间',
  PRIMARY KEY (BANKCODE,ACTDATE,BANKLOGNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-成功记录信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_PAY_INF  对账-交易记录信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_PAY_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_PAY_INF (
  BANKCODE   varchar(20)   NOT NULL     COMMENT '银行编码',
  ACTDATE    varchar(8)    NOT NULL     COMMENT '交易日期',
  BANKLOGNO  varchar(30)   NOT NULL     COMMENT '银行流水号',
  CHKTYPE    varchar(2)    DEFAULT NULL COMMENT '明细类型  01 消费  02 消费撤销',
  BANKDATE   varchar(8)    DEFAULT NULL COMMENT '结算日期',
  CCYCOD     varchar(3)    DEFAULT NULL COMMENT '交易货币',
  TXNAMT     varchar(12)   DEFAULT NULL COMMENT '交易金额   分',
  FEE        varchar(12)   DEFAULT NULL COMMENT '手续费',
  INAMT      varchar(12)   DEFAULT NULL COMMENT '实收净额',
  CUST_ID    varchar(20)   DEFAULT NULL COMMENT '商户号',
  PAYNO      varchar(30)   DEFAULT NULL COMMENT '支付订单号',
  CHECKDATE  varchar(8)    DEFAULT NULL COMMENT '对账日期',
  CHECKTIME  varchar(8)    DEFAULT NULL COMMENT '对账时间',
  PRIMARY KEY (BANKCODE,ACTDATE,BANKLOGNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-交易记录信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_CHECKACCOUNT_STATUS_INF  对账-对账状态表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_CHECKACCOUNT_STATUS_INF;
CREATE TABLE MPBATCH_CHECKACCOUNT_STATUS_INF (
  BANKCODE      varchar(255)   DEFAULT NULL   COMMENT '银行编码',
  CHKTYPE       varchar(2)     DEFAULT NULL   COMMENT '对账类型  01 消费  02 退货',
  ACTDATE       varchar(20)    DEFAULT NULL   COMMENT '交易日期',
  BANKFILSTS    varchar(255)   DEFAULT NULL   COMMENT '银行对账文件状态',
  BKGFILSTS     varchar(255)   DEFAULT NULL   COMMENT '账务对账信息状态',
  CHKPROSTS     varchar(2)     DEFAULT NULL   COMMENT '对账处理状态  01 对账中 02 对账完成 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账-对账状态表';



-- ------------------------------------------------------------
-- Table structure for MPBATCH_PROFITSHARING_INF 分润信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_PROFITSHARING_INF;
CREATE TABLE MPBATCH_PROFITSHARING_INF (
  SHAR_DATE             varchar(8)      NOT NULL      COMMENT '分润计算日(年月日)',
  AGENT_ID              varchar(20)     NOT NULL      COMMENT '代理商ID',
  PAYNO                 varchar(25)     NOT NULL      COMMENT '支付订单号',
  PAYAMT                varchar(12)     DEFAULT NULL  COMMENT '订单金额',
  PAY_RATE              varchar(10)     DEFAULT NULL  COMMENT '交易费率',
  PAY_FEE               varchar(12)     DEFAULT NULL  COMMENT '手续费',
  FATH_AGENT_ID         varchar(20)     DEFAULT NULL  COMMENT '上级代理商',
  AGENT_DGR             varchar(20)     DEFAULT NULL  COMMENT '代理商等级',
  AGENT_RATE            varchar(10)     DEFAULT NULL  COMMENT '代理商费率',
  AGENT_FEE             varchar(12)     DEFAULT NULL  COMMENT '代理商手续费',
  SHAR_PROFIT_RATIO     varchar(4)      DEFAULT NULL  COMMENT '分润比例（十分之几）',
  SHARAMT               varchar(12)     DEFAULT NULL  COMMENT '分润金额',
  SHAR_STATUS           varchar(2)      DEFAULT NULL  COMMENT '状态',
  SHAR_TIME             varchar(20)     DEFAULT NULL  COMMENT '时间(年月日时分秒)',
  PRIMARY KEY (SHAR_DATE,AGENT_ID,PAYNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分润信息表';


-- ------------------------------------------------------------
-- Table structure for MPBATCH_SETTLEACCOUNTS_INF 商户清算信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPBATCH_SETTLEACCOUNTS_INF;
CREATE TABLE MPBATCH_SETTLEACCOUNTS_INF (
  SEDATE       varchar(20)     NOT NULL        COMMENT '清算日期',
  SESTATUS     varchar(2)      DEFAULT NULL    COMMENT '清算状态 01:未结算,02结算中,03:结算成功,04:结算失败',
  CUST_ID      varchar(25)     NOT NULL        COMMENT '商户ID',
  BANK_NAME    varchar(255)    DEFAULT NULL    COMMENT '开户行',
  CNAPS_CODE   varchar(255)    DEFAULT NULL    COMMENT '联行号(支行信息)',
  CARD_NO      varchar(11)     DEFAULT NULL    COMMENT '银行卡号',
  AMT         varchar(255)     DEFAULT NULL    COMMENT '结算金额',
  PRIMARY KEY (SEDATE,CUST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户清算信息表';

