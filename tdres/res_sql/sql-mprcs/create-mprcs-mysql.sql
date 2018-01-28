
-- ------------------------------------------------------------
-- Table structure for MPRCS_BANK_CARD_BLACKLIST_INF   银行卡黑名单表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPRCS_BANK_CARD_BLACKLIST_INF;
CREATE TABLE MPRCS_BANK_CARD_BLACKLIST_INF (
  CARDNO      varchar(50)   NOT NULL COMMENT '银行卡号',
  CARD_TYPE   varchar(10)   DEFAULT NULL   COMMENT '卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡',
  ISSNO       varchar(10)   DEFAULT NULL   COMMENT '发卡行代码',
  ISSNAM      varchar(100)  DEFAULT NULL   COMMENT '发卡行名称',
  IDCARDNO    varchar(100)  DEFAULT NULL   COMMENT '持卡人身份证',
  IDCARD_NAME varchar(100)  DEFAULT NULL   COMMENT '持卡人名称',
  CREATE_DATE varchar(14)   DEFAULT NULL   COMMENT '添加时间',
  SOURCE      varchar(255)  DEFAULT NULL   COMMENT '添加来源说明，比如系统导入、交易返回异常卡',
  CREATE_DESC varchar(255)  DEFAULT NULL   COMMENT '描述信息',
  FILED1      varchar(255)  DEFAULT NULL   COMMENT '备用字段1',
  FILED2      varchar(255)  DEFAULT NULL   COMMENT '备用字段2',
  PRIMARY KEY (CARDNO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡黑名单表';
-- -----------------------------------------------------
-- Table structure for MPRCS_CUST_LEVEL_INF  等级维护表
-- ----------------------------------------------------
DROP TABLE IF EXISTS MPRCS_CUST_LEVEL_INF;
CREATE TABLE MPRCS_CUST_LEVEL_INF (
  LEVEL       varchar(2)    NOT NULL      COMMENT '定义的等级编号',
  LEVEL_NAME  varchar(200)   DEFAULT NULL  COMMENT '等级名称',
  LEVEL_DESC  varchar(200)   DEFAULT NULL  COMMENT '等级描述',
  PRIMARY KEY (`LEVEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='等级维护表';

-- --------------------------------------------------------
-- Table structure for MPRCS_TRAN_SERIAL_RECORD_INF  交易记录表
-- ---------------------------------------------------------
DROP TABLE IF EXISTS MPRCS_TRAN_SERIAL_RECORD_INF;
CREATE TABLE MPRCS_TRAN_SERIAL_RECORD_INF (
  REC_ID       varchar(20)    NOT NULL,
  BUS_TYPE     varchar(10)    DEFAULT NULL COMMENT '业务类型  00：所有，01：收款,02:消费,03:提现',
  SUB_BUS      varchar(10)    DEFAULT NULL COMMENT '子业务类型',
  PAY_WAY      varchar(10)    DEFAULT NULL COMMENT '支付方式：01 支付账户   02 终端   03 快捷支付',
  AGENT_ID     varchar(100)   DEFAULT NULL COMMENT '该笔交易所属代理商编号',
  CUST_ID      varchar(100)   DEFAULT NULL COMMENT '交易用户编号',
  TER_NO       varchar(100)   DEFAULT NULL COMMENT '消费终端号，如果是快捷，可存激活码',
  BANK_CARD_NO varchar(100)   DEFAULT NULL COMMENT '交易银行卡号',
  CARD_TYPE    varchar(10)    DEFAULT NULL COMMENT '卡种 01 借记卡 02 贷记卡 03 准贷记卡 04 预付卡',
  TRAN_AMT     varchar(18)    DEFAULT NULL COMMENT '交易金额，单位：分',
  TARN_YEAR    varchar(10)    DEFAULT NULL COMMENT '交易年份',
  TARN_MONTH   varchar(10)    DEFAULT NULL COMMENT '交易月份',
  TARN_DAY     varchar(10)    DEFAULT NULL COMMENT '交易日',
  TARN_TIME    varchar(20)    DEFAULT NULL COMMENT '交易时间',
  TRAN_CODE    varchar(10)    DEFAULT NULL COMMENT '交易参考编号，收款存支付订单号，提现可存提现订单号',
  TRAN_STATE   varchar(10)    DEFAULT NULL COMMENT '交易状态，0：失败，1：成功，2：退货(交易成功但退款后，应该修改该交易记录状态）',
  PRIMARY KEY (REC_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易记录表';



-- ----------------------------------------------------
-- Table structure for MPRCS_USER_LIMIT_INF   风控记录表
-- -----------------------------------------------------
DROP TABLE IF EXISTS MPRCS_USER_LIMIT_INF;
CREATE TABLE MPRCS_USER_LIMIT_INF (
  LIMIT_ID          varchar(14)    NOT NULL DEFAULT '' COMMENT '限额记录ID',
  LIMIT_TYPE        varchar(10)    DEFAULT '00' COMMENT '限额类型。00：默认限额，10：商户等级限额，20：商户限额，30：银行卡限额',
  LIMIT_CUST_LEVEL  varchar(10)    DEFAULT NULL COMMENT '商户级别，按商户限额时为空',
  LIMIT_CUST_ID     varchar(20)    DEFAULT NULL COMMENT '用户编号,根据等级限额时为空',
  LIMIT_BUS_TYPE    varchar(14)    DEFAULT '00' COMMENT '业务类型 00：所有，01：收款,02:消费,03:提现',
  LIMIT_SUB_BUS     varchar(14)    DEFAULT '00' COMMENT '业务类型下的子业务类型，00：默认所有',
  LIMIT_PAY_WAY     varchar(18)    DEFAULT '00' COMMENT '支付方式：00 所有 01 支付账户   02 终端   03 快捷支付',
  LIMIT_MIN_AMT     varchar(18)    DEFAULT NULL COMMENT '单笔最小金额，单位：分',
  LIMIT_MAX_AMT     varchar(18)    DEFAULT NULL COMMENT '单笔最大金额，单位：分',
  LIMIT_DAY_TIMES   varchar(3)     DEFAULT NULL COMMENT '每日次数',
  LIMIT_DAY_AMT     varchar(18)    DEFAULT NULL COMMENT '每日金额，单位：分',
  LIMIT_MONTH_TIMES varchar(3)     DEFAULT NULL COMMENT '每月交易次数',
  LIMIT_MONTH_AMT   varchar(18)    DEFAULT NULL COMMENT '每月交易金额,单位：分',
  LIMIT_YEAR_TIMES  varchar(4)     DEFAULT NULL COMMENT '每年交易次数',
  LIMIT_YEAR_AMT    varchar(18)    DEFAULT NULL COMMENT '每年交易金额，单位：分',
  LIMIT_START_DATE  varchar(8)     DEFAULT NULL COMMENT '生效开始日期',
  LIMIT_END_DATE    varchar(8)     DEFAULT NULL COMMENT '生效结束日期',
  LIMIT_DESC        varchar(400)   DEFAULT NULL COMMENT '描述信息',
  IS_USE            char(1)        DEFAULT '1'  COMMENT '启用状态， 1：启用 0 ： 关闭',
  CREATE_NAME       varchar(30)    DEFAULT NULL COMMENT '创建人',
  CREATE_DATE       varchar(8)     DEFAULT NULL COMMENT '创建日期',
  CREATE_DATETIME   varchar(14)    DEFAULT NULL COMMENT '创建时间',
  UPDATE_NAME       varchar(30)    DEFAULT NULL COMMENT '修改人',
  UPDATE_DATE       varchar(8)     DEFAULT NULL COMMENT '修改日期',
  UPDATE_DATETIME   varchar(14)    DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (LIMIT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风控记录表';



-- ----------------------------------------------------
-- Table structure for MPRCS_ORG_TERM_LIMIT_INF   合作机构终端风控记录表
-- -----------------------------------------------------
DROP TABLE IF EXISTS MPRCS_ORG_TERM_LIMIT_INF;
CREATE TABLE MPRCS_ORG_TERM_LIMIT_INF (
  LIMIT_ID          varchar(17)    NOT NULL DEFAULT '' COMMENT '限额记录ID',
  LIMIT_ORG_ID      varchar(30)    DEFAULT NULL COMMENT '合作机构编号',
  LIMIT_MER_NO      varchar(15)    DEFAULT NULL COMMENT '商户编号',
  LIMIT_TERM_NO     varchar(8)     DEFAULT NULL COMMENT '终端编号',
  LIMIT_PAY_AMT     varchar(12)    DEFAULT NULL COMMENT '交易金额',
  TXN_DATE          varchar(8)     DEFAULT NULL COMMENT '创建日期'
  PRIMARY KEY (LIMIT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作机构终端风控记录表';


-- ----------------------------------------------------
-- Table structure for MPRCS_DEFAULT_LIMIT_INF   风控默认限额表
-- -----------------------------------------------------
DROP TABLE IF EXISTS MPRCS_DEFAULT_LIMIT_INF;
CREATE TABLE MPRCS_DEFAULT_LIMIT_INF (
  LIMIT_ID             varchar(14)    NOT NULL DEFAULT '' COMMENT '限额记录ID',
  LIMIT_TYPE           varchar(2)    DEFAULT '00' COMMENT '限额类型。00：默认限额',
  LIMIT_CUST_LEVEL     varchar(10)    DEFAULT NULL COMMENT '商户级别',
  LIMIT_MIN_AMT_TP     varchar(12)    DEFAULT NULL COMMENT '终端收款 ,单笔最小金额，单位：分',
  LIMIT_MAX_AMT_TP     varchar(12)    DEFAULT NULL COMMENT '终端收款 ,单笔最大金额，单位：分',
  LIMIT_DAY_TIMES_TP   varchar(10)    DEFAULT NULL COMMENT '终端收款 ,每日次数',
  LIMIT_DAY_AMT_TP     varchar(12)    DEFAULT NULL COMMENT '终端收款 ,每日金额，单位：分',
  LIMIT_MONTH_TIMES_TP varchar(10)    DEFAULT NULL COMMENT '终端收款 ,每月交易次数',
  LIMIT_MONTH_AMT_TP   varchar(12)    DEFAULT NULL COMMENT '终端收款 ,每月交易金额,单位：分',
  LIMIT_MIN_AMT_TC     varchar(12)    DEFAULT NULL COMMENT '终端提现 ,单笔最小金额，单位：分',
  LIMIT_MAX_AMT_TC     varchar(12)    DEFAULT NULL COMMENT '终端提现 ,单笔最大金额，单位：分',
  LIMIT_DAY_TIMES_TC   varchar(10)    DEFAULT NULL COMMENT '终端提现 ,每日次数',
  LIMIT_DAY_AMT_TC     varchar(12)    DEFAULT NULL COMMENT '终端提现 ,每日金额，单位：分',
  LIMIT_MONTH_TIMES_TC varchar(10)    DEFAULT NULL COMMENT '终端提现 ,每月交易次数',
  LIMIT_MONTH_AMT_TC   varchar(12)    DEFAULT NULL COMMENT '终端提现 ,每月交易金额,单位：分',
  LIMIT_MIN_AMT_OP     varchar(12)    DEFAULT NULL COMMENT '快捷收款 ,单笔最小金额，单位：分',
  LIMIT_MAX_AMT_OP     varchar(12)    DEFAULT NULL COMMENT '快捷收款 ,单笔最大金额，单位：分',
  LIMIT_DAY_TIMES_OP   varchar(10)    DEFAULT NULL COMMENT '快捷收款 ,每日次数',
  LIMIT_DAY_AMT_OP     varchar(12)    DEFAULT NULL COMMENT '快捷收款 ,每日金额，单位：分',
  LIMIT_MONTH_TIMES_OP varchar(10)    DEFAULT NULL COMMENT '快捷收款 ,每月交易次数',
  LIMIT_MONTH_AMT_OP   varchar(12)    DEFAULT NULL COMMENT '快捷收款 ,每月交易金额,单位：分',
  LIMIT_MIN_AMT_OC     varchar(12)    DEFAULT NULL COMMENT '快捷提现 ,单笔最小金额，单位：分',
  LIMIT_MAX_AMT_OC     varchar(12)    DEFAULT NULL COMMENT '快捷提现 ,单笔最大金额，单位：分',
  LIMIT_DAY_TIMES_OC   varchar(10)    DEFAULT NULL COMMENT '快捷提现 ,每日次数',
  LIMIT_DAY_AMT_OC     varchar(12)    DEFAULT NULL COMMENT '快捷提现 ,每日金额，单位：分',
  LIMIT_MONTH_TIMES_OC varchar(10)    DEFAULT NULL COMMENT '快捷提现 ,每月交易次数',
  LIMIT_MONTH_AMT_OC   varchar(12)    DEFAULT NULL COMMENT '快捷提现 ,每月交易金额,单位：分',
  LIMIT_START_DATE  varchar(8)     DEFAULT NULL COMMENT '生效开始日期',
  LIMIT_END_DATE    varchar(8)     DEFAULT NULL COMMENT '生效结束日期',
  IS_USE            char(1)        DEFAULT '1'  COMMENT '启用状态， 1：开启 0 ： 关闭',
  CREATE_NAME       varchar(30)    DEFAULT NULL COMMENT '创建人',
  CREATE_DATE       varchar(8)     DEFAULT NULL COMMENT '创建日期',
  CREATE_DATETIME   varchar(14)    DEFAULT NULL COMMENT '创建时间',
  UPDATE_NAME       varchar(30)    DEFAULT NULL COMMENT '修改人',
  UPDATE_DATE       varchar(8)     DEFAULT NULL COMMENT '修改日期',
  UPDATE_DATETIME   varchar(14)    DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (LIMIT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风控默认限额表';





