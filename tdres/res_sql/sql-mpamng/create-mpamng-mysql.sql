
-- ------------------------------------------------------------
-- Table structure for MPAMNG_TERMINAL_INF   终端信息表
-- -------------------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_TERMINAL_INF;
CREATE TABLE MPAMNG_TERMINAL_INF (
  TERMINAL_ID            varchar(20)    NOT  NULL     COMMENT '终端ID(序列号)',
  TERMINAL_SEQ           varchar(50)         NULL     COMMENT '终端号',
  TERMINAL_NO            varchar(50)         NULL     COMMENT '终端物理号',
  AGENT_ID               varchar(14) DEFAULT NULL     COMMENT '代理商ID',
  AGENT_ID               varchar(20)         NULL     COMMENT '商户ID',
  TERMINAL_TYPE          varchar(20)         NULL     COMMENT '终端类型',
  TERMINAL_COM           varchar(20)         NULL     COMMENT '终端厂商',
  TERMINAL_STATUS        varchar(1)  '0'              COMMENT '终端状态 0 入库 1下拨 2绑定',
  RATE_LIVELIHOOD        varchar(4) DEFAULT  NULL     COMMENT '民生类   0.38%',
  RATE_GENERAL           varchar(4) DEFAULT  NULL     COMMENT '一般类  0.78%',
  RATE_GENERAL_TOP       varchar(4) DEFAULT  NULL     COMMENT '批发类  0.78% 封顶',
  RATE_GENERAL_MAXIMUN   varchar(12)DEFAULT  NULL     COMMENT '批发类  0.78% 封顶金额 分',
  RATE_ENTERTAIN         varchar(4) DEFAULT  NULL     COMMENT '餐娱类  1.25%',
  RATE_ENTERTAIN_TOP     varchar(4) DEFAULT  NULL     COMMENT '房产汽车类   1.25% 封顶',
  RATE_ENTERTAIN_MAXIMUN varchar(12)DEFAULT  NULL     COMMENT '房产汽车类   1.25% 封顶金额  分',
  CREATE_USER_ID         varchar(20)         NULL     COMMENT '创建人',
  CREATE_DATE            varchar(14)         NULL     COMMENT '创建时间',
  EDIT_USER_ID           varchar(20)         NULL     COMMENT '修改人',
  EDIT_DATE              varchar(14)         NULL     COMMENT '修改时间',
  PRIMARY KEY (TERMINAL_ID)
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='终端信息表';

-- -----------------------------------------------------
-- Table structure for MPAMNG_TERMINAL_KEY_INF  终端密钥表
-- ----------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_TERMINAL_KEY_INF;
CREATE TABLE MPAMNG_TERMINAL_KEY_INF (
  TERMINAL_ID       varchar(20)  NOT NULL COMMENT '终端ID',
  TERMINAL_LMK      varchar(33)      NULL COMMENT '终端LMK',
  TERMINAL_LMK_CV   varchar(16)      NULL COMMENT '终端LMK Check value',
  TERMINAL_ZMK      varchar(33)      NULL COMMENT '终端ZMK',
  TERMINAL_ZMK_CV   varchar(16)      NULL COMMENT '终端ZMK Check value',
  TERMINAL_TTK      varchar(32)      NULL COMMENT '终端密钥(解密磁道信息)',
  TERMINAL_TTK_CV   varchar(16)      NULL COMMENT '终端TTK Check value',
  TERMINAL_PIK      varchar(33)      NULL COMMENT '终端PIN KEY',
  TERMINAL_PIK_CV   varchar(16)      NULL COMMENT '终端PIK Check value',
  TERMINAL_TDK      varchar(33)      NULL COMMENT '终端磁道密钥',
  TERMINAL_TDK_CV   varchar(16)      NULL COMMENT '终端TDK Check value',
  TERMINAL_MAK      varchar(16)      NULL COMMENT '终端 MAC KEY',
  TERMINAL_MAK_CV   varchar(16)      NULL COMMENT '终端MAK Check value',
  PRIMARY KEY (TERMINAL_ID)
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='终端密钥表';

-- --------------------------------------------------------
-- Table structure for MPAMNG_TERMINALCOMPANY_INF  终端厂商表
-- ---------------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_TERMINALCOMPANY_INF;
CREATE TABLE MPAMNG_TERMINALCOMPANY_INF (
  TERM_ID         varchar(9)   NOT NULL  COMMENT 'ID',
  TERMCOM_CODE    varchar(20)      NULL  COMMENT '厂商简写',
  TERMCOM_NAME    varchar(20)      NULL  COMMENT '厂商名称',
  TERMTYPE        varchar(20)      NULL  COMMENT '终端类型',
  TERMTYPE_NAME   varchar(20)      NULL  COMMENT '终端类型名',
  PRIMARY KEY (TERM_ID)
) ENGINE=InnoDB  CHARSET=utf8 COMMENT='终端厂商表';

----------------
------附件表
------------------
DROP TABLE MPOMNG_MOBILE_MER_INF;
CREATE TABLE MPOMNG_MOBILE_MER_INF(
    AGENT_ID    		VARCHAR(14)	   NOT NULL COMMENT '代理商登陆账户',
    CUST_ID     		VARCHAR(14)	   NOT NULL COMMENT '代理商编号', 
    AGENT_DGR   		VARCHAR(14)				COMMENT '代理商等级',  
    FATH_CUST_ID		VARCHAR(14)				COMMENT '父代理商编号',
    FIRST_CUST_ID		VARCHAR(14)				COMMENT '一级代理商编号',
    AGENT_NAME			VARCHAR(60)				COMMENT '代理商名称',
    LEGAL_NAME			VARCHAR(60)				COMMENT '法人姓名',
    LEGAL_IDENTITY_NO 	VARCHAR(20)				COMMENT '法人身份证号码', 
    LIC_NO      		VARCHAR(40)				COMMENT '税务登记号',
    TAX_NO      		VARCHAR(40)				COMMENT '法人营业执照号',
    ADDRESS      		VARCHAR(100)			COMMENT '联系地址',
    TEL      			VARCHAR(20)				COMMENT '联系电话',
    MOBLIE_NO      		VARCHAR(20)				COMMENT '手机号码',
    EMAIL      			VARCHAR(60)				COMMENT '邮箱', 
    FROZ_STATE      	VARCHAR(1) DEFAULT '0'	COMMENT '冻结状态 0：未冻结；1：已冻结；', 
    CONTRACT_STR_DATE   VARCHAR(10)				COMMENT '合同开始日期', 
    CONTRACT_END_DATE   VARCHAR(10)				COMMENT '合同结束日期', 
    AGE_STATUS      	VARCHAR(2)				COMMENT '代理商启用： 01正常 02停用',
    REMARK      		VARCHAR(200)			COMMENT '代理商说明信息', 
    FILED1	      		VARCHAR(20)				COMMENT '备用字段1', 
    FILED2	      		VARCHAR(20)				COMMENT '备用字段2',
    TECHCONTACT      	VARCHAR(30)				COMMENT '技术联系人', 
    TECHTELNO	      	VARCHAR(15)				COMMENT '技术联系电话',
    TECHEMAIL	      	VARCHAR(60)				COMMENT '技术联系邮箱',
    TECHMOBNO	      	VARCHAR(20)				COMMENT '技术联系手机',
    CITY		      	VARCHAR(50)				COMMENT '城市',
    AGE_TYPE	      	VARCHAR(4)				COMMENT '代理商类型',
    BIZCONTACT      	VARCHAR(60)				COMMENT '业务联系人',
    BIZMOBNO        	VARCHAR(20)				COMMENT '业务联系手机', 
    SERVCONTACT      	VARCHAR(60)				COMMENT '客服联系人',
    SERVMOBNO	      	VARCHAR(20)				COMMENT '客服联系手机',
    OEM_STATE	      	VARCHAR(1) DEFAULT '0'	COMMENT 'OEM标识 0：否  1：是', 
    FEE_CODE	      	VARCHAR(10)				COMMENT '费率代码',
    SLTTYP		      	VARCHAR(1)				COMMENT '结算方式： 0日结 1月结',
    EFFTIM		      	VARCHAR(8)				COMMENT '', 
    TIFLG		      	VARCHAR(1)				COMMENT 'T+0提现标志，0 否  1是',
    SINGLECHANNEL		VARCHAR(2)				COMMENT '单通道',  
    MULTICHANNEL		VARCHAR(2)				COMMENT '多通道',  
    BANKPAYACNO		    VARCHAR(20)				COMMENT '代理商结算账户银行账号', 
    BANKPAYUSERNM		VARCHAR(40)				COMMENT '代理商结算账户用户名',
    BANKCODE		    VARCHAR(50)				COMMENT '开户行', 
    CREATE_DATE		    VARCHAR(14)				COMMENT '创建时间', 
    CREATE_USER_ID		VARCHAR(14)				COMMENT '创建人',
    EDIT_DATE		    VARCHAR(14)				COMMENT '编辑时间', 
    EDIT_USER_ID		VARCHAR(14)				COMMENT '编辑人' 
);


-- ----------------------------------------------------
-- Table structure for MPAMNG_AGENT_INF   代理商信息表
-- -----------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_AGENT_INF;
CREATE TABLE  MPAMNG_AGENT_INF  (
   AGENT_ID                varchar(14)   NOT NULL         COMMENT '代理商ID',
   LOGON_NAME              varchar(14)   NOT NULL         COMMENT '代理商登陆账户',
   AGENT_DGR               varchar(2)    NOT NULL         COMMENT '代理商等级',
   FATH_AGENT_ID           varchar(14)   NOT NULL         COMMENT '父代理商用户ID',
   FIRST_AGENT_ID          varchar(14)   NOT NULL         COMMENT '一级代理商用户ID',
   AGENT_NAME              varchar(60)   DEFAULT NULL     COMMENT '代理商名称',
   LEGAL_NAME              varchar(60)   DEFAULT NULL     COMMENT '法人姓名',
   LEGAL_IDENTITY_NO       varchar(20)   NOT NULL         COMMENT '法人身份证号码',
   LIC_NO                  varchar(40)   DEFAULT NULL     COMMENT '法人营业执照号',
   TAX_NO                  varchar(40)   DEFAULT NULL     COMMENT '税务登记号',
   ADDRESS                 varchar(100)  DEFAULT NULL     COMMENT '联系地址',
   TEL                     varchar(20)   DEFAULT NULL     COMMENT '联系电话',
   MOBLIE_NO               varchar(20)   DEFAULT NULL     COMMENT '手机号码',
   EMAIL                   varchar(60)   DEFAULT NULL     COMMENT '邮箱',
   FROZ_STATE              varchar(1)    DEFAULT '0'      COMMENT '是否冻结 0：否；1：是；',
   MARGIN                  varchar(15)   NOT NULL         COMMENT '保证金',
   CONTRACT_STR_DATE       varchar(14)   DEFAULT NULL     COMMENT '合同开始日期',
   CONTRACT_END_DATE       varchar(14)   DEFAULT NULL     COMMENT '合同结束日期',
   AGE_STATUS              varchar(1)    DEFAULT '0'      COMMENT '代理商禁用： 0否 1是',
   REMARK                  varchar(200)  DEFAULT NULL     COMMENT '代理商说明信息', 
   FILED1                  varchar(20)   DEFAULT NULL     COMMENT '备用字段1',
   FILED2                  varchar(20)   DEFAULT NULL     COMMENT '备用字段2',
   TECHCONTACT             varchar(30)   DEFAULT NULL     COMMENT '技术联系人',
   TECHTELNO               varchar(15)   DEFAULT NULL     COMMENT '技术联系电话',
   TECHEMAIL               varchar(60)   DEFAULT NULL     COMMENT '技术联系邮箱',
   TECHMOBNO               varchar(20)   DEFAULT NULL     COMMENT '技术联系手机',
   CITY                    varchar(50)   DEFAULT NULL     COMMENT '城市',
   PROVINCE                varchar(10)   DEFAULT NULL     COMMENT '省份',
   AGE_TYPE                varchar(4)    DEFAULT NULL     COMMENT '代理商类型',
   BIZCONTACT              varchar(60)   DEFAULT NULL     COMMENT '业务联系人',
   BIZMOBNO                varchar(20)   DEFAULT NULL     COMMENT '业务联系手机',
   SERVCONTACT             varchar(60)   DEFAULT NULL     COMMENT '客服联系人',
   SERVMOBNO               varchar(20)   DEFAULT NULL     COMMENT '客服联系手机',
   OEM_STATE               char(1)       DEFAULT '0'      COMMENT 'OEM标识 0：否  1：是',
   RATE_LIVELIHOOD         varchar(4)    DEFAULT NULL     COMMENT '民生类   0.38%',
   RATE_GENERAL            varchar(4)    DEFAULT NULL     COMMENT '一般类  0.78%',
   RATE_GENERAL_TOP        varchar(4)    DEFAULT NULL     COMMENT '批发类  0.78% 封顶',
   RATE_GENERAL_MAXIMUN    varchar(12)   DEFAULT NULL     COMMENT '批发类  0.78% 封顶金额 分',
   RATE_ENTERTAIN          varchar(4)    DEFAULT NULL     COMMENT '餐娱类  1.25%',
   RATE_ENTERTAIN_TOP      varchar(4)    DEFAULT NULL     COMMENT '房产汽车类   1.25% 封顶',
   RATE_ENTERTAIN_MAXIMUN  varchar(12)   DEFAULT NULL     COMMENT '房产汽车类   1.25% 封顶金额  分',
   SLTTYP                  char(1)       DEFAULT '0'      COMMENT '结算方式： 0日结 1月结',
   EFFTIM                  char(8)       DEFAULT '20140701',COMMENT '日结月结的时间',
   TIFLG                   char(1)       DEFAULT '0'      COMMENT 'T+0提现标志，0 否  1是',
   SINGLECHANNEL           varchar(2)    DEFAULT NULL     COMMENT '单通道',
   MULTICHANNEL            varchar(2)    DEFAULT NULL     COMMENT '多通道',
   BANKPAYACNO             varchar(20)   DEFAULT NULL     COMMENT '代理商结算账户银行账号',
   BANKPAYUSERNM           varchar(40)   DEFAULT NULL     COMMENT '代理商结算账户用户名',
   BANKCODE                varchar(50)   DEFAULT NULL     COMMENT '开户行',
   CREATE_DATE             varchar(14)   DEFAULT NULL     COMMENT '创建时间',
   CREATE_USER_ID          varchar(20)   DEFAULT NULL     COMMENT '创建人',
   EDIT_DATE               varchar(14)   DEFAULT NULL     COMMENT '编辑时间',
   EDIT_USER_ID            varchar(20)   DEFAULT NULL     COMMENT '编辑人',
   AGENT_CODE              varchar(100)  DEFAULT NULL     COMMENT '代理商标识码',
   FATHER_CODE             varchar(100)  DEFAULT NULL     COMMENT '父代理商标识码',
   AUDIT_AGENTID           varchar(14)   DEFAULT '0'      COMMENT '审核代理商ID'
   PROFIT_RATIO            varchar(4)    DEFAULT '10'     COMMENT '分润比例（十分之几）',
   OEM_PIC_ID              varchar(25)   DEFAULT NULL     COMMENT 'oem图片(图片id)',
   OPENING_LICENSE_PIC_ID  varchar(25)   DEFAULT NULL     COMMENT '开户许可证(图片id)',
   BUSINESS_LICENSE_PIC_ID varchar(25)   DEFAULT NULL     COMMENT '营业执照(图片id)',
   LEGAL_IDENTITY_PIC_ID   varchar(25)   DEFAULT NULL     COMMENT '法人身份证(图片id)',
   TAX_NO_PIC_ID           varchar(25)   DEFAULT NULL     COMMENT ' 税务登记证(图片id)'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商信息表';



-- ---------------------------------------------------------
-- Table structure for MPAMNG_AGENT_INF_TEMP   代理商信息表-临时表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_AGENT_INF_TEMP;
CREATE TABLE  MPAMNG_AGENT_INF_TEMP  (
   AGENT_ID                varchar(14)   NOT NULL         COMMENT '代理商ID',
   LOGON_NAME              varchar(14)   NOT NULL         COMMENT '代理商登陆账户',
   AGENT_DGR               varchar(2)    NOT NULL         COMMENT '代理商等级',
   FATH_AGENT_ID           varchar(14)   NOT NULL         COMMENT '父代理商用户ID',
   FIRST_AGENT_ID          varchar(14)   NOT NULL         COMMENT '一级代理商用户ID',
   AGENT_NAME              varchar(60)   DEFAULT NULL     COMMENT '代理商名称',
   LEGAL_NAME              varchar(60)   DEFAULT NULL     COMMENT '法人姓名',
   LEGAL_IDENTITY_NO       varchar(20)   NOT NULL         COMMENT '法人身份证号码',
   LIC_NO                  varchar(40)   DEFAULT NULL     COMMENT '法人营业执照号',
   TAX_NO                  varchar(40)   DEFAULT NULL     COMMENT '税务登记号',
   ADDRESS                 varchar(100)  DEFAULT NULL     COMMENT '联系地址',
   TEL                     varchar(20)   DEFAULT NULL     COMMENT '联系电话',
   MOBLIE_NO               varchar(20)   DEFAULT NULL     COMMENT '手机号码',
   EMAIL                   varchar(60)   DEFAULT NULL     COMMENT '邮箱',
   FROZ_STATE              varchar(1)    DEFAULT '0'      COMMENT '是否冻结 0：否；1：是；',
   MARGIN                  varchar(15)   NOT NULL         COMMENT '保证金',
   CONTRACT_STR_DATE       varchar(14)   DEFAULT NULL     COMMENT '合同开始日期',
   CONTRACT_END_DATE       varchar(14)   DEFAULT NULL     COMMENT '合同结束日期',
   AGE_STATUS              varchar(1)    DEFAULT '0'      COMMENT '代理商禁用： 0否 1是',
   REMARK                  varchar(200)  DEFAULT NULL     COMMENT '代理商说明信息', 
   FILED1                  varchar(20)   DEFAULT NULL     COMMENT '备用字段1',
   FILED2                  varchar(20)   DEFAULT NULL     COMMENT '备用字段2',
   TECHCONTACT             varchar(30)   DEFAULT NULL     COMMENT '技术联系人',
   TECHTELNO               varchar(15)   DEFAULT NULL     COMMENT '技术联系电话',
   TECHEMAIL               varchar(60)   DEFAULT NULL     COMMENT '技术联系邮箱',
   TECHMOBNO               varchar(20)   DEFAULT NULL     COMMENT '技术联系手机',
   CITY                    varchar(50)   DEFAULT NULL     COMMENT '城市',
   PROVINCE                varchar(10)   DEFAULT NULL     COMMENT '省份',
   AGE_TYPE                varchar(4)    DEFAULT NULL     COMMENT '代理商类型',
   BIZCONTACT              varchar(60)   DEFAULT NULL     COMMENT '业务联系人',
   BIZMOBNO                varchar(20)   DEFAULT NULL     COMMENT '业务联系手机',
   SERVCONTACT             varchar(60)   DEFAULT NULL     COMMENT '客服联系人',
   SERVMOBNO               varchar(20)   DEFAULT NULL     COMMENT '客服联系手机',
   OEM_STATE               char(1)       DEFAULT '0'      COMMENT 'OEM标识 0：否  1：是',
   RATE_LIVELIHOOD         varchar(4)    DEFAULT NULL     COMMENT '民生类   0.38%',
   RATE_GENERAL            varchar(4)    DEFAULT NULL     COMMENT '一般类  0.78%',
   RATE_GENERAL_TOP        varchar(4)    DEFAULT NULL     COMMENT '批发类  0.78% 封顶',
   RATE_GENERAL_MAXIMUN    varchar(12)   DEFAULT NULL     COMMENT '批发类  0.78% 封顶金额 分',
   RATE_ENTERTAIN          varchar(4)    DEFAULT NULL     COMMENT '餐娱类  1.25%',
   RATE_ENTERTAIN_TOP      varchar(4)    DEFAULT NULL     COMMENT '房产汽车类   1.25% 封顶',
   RATE_ENTERTAIN_MAXIMUN  varchar(12)   DEFAULT NULL     COMMENT '房产汽车类   1.25% 封顶金额  分',
   SLTTYP                  char(1)       DEFAULT '0'      COMMENT '结算方式： 0日结 1月结',
   EFFTIM                  char(8)       DEFAULT '20140701',COMMENT '日结月结的时间',
   TIFLG                   char(1)       DEFAULT '0'      COMMENT 'T+0提现标志，0 否  1是',
   SINGLECHANNEL           varchar(2)    DEFAULT NULL     COMMENT '单通道',
   MULTICHANNEL            varchar(2)    DEFAULT NULL     COMMENT '多通道',
   BANKPAYACNO             varchar(20)   DEFAULT NULL     COMMENT '代理商结算账户银行账号',
   BANKPAYUSERNM           varchar(40)   DEFAULT NULL     COMMENT '代理商结算账户用户名',
   BANKCODE                varchar(50)   DEFAULT NULL     COMMENT '开户行',
   CREATE_DATE             varchar(14)   DEFAULT NULL     COMMENT '创建时间',
   CREATE_USER_ID          varchar(20)   DEFAULT NULL     COMMENT '创建人',
   EDIT_DATE               varchar(14)   DEFAULT NULL     COMMENT '编辑时间',
   EDIT_USER_ID            varchar(20)   DEFAULT NULL     COMMENT '编辑人',
   AGENT_CODE              varchar(100)  DEFAULT NULL     COMMENT '代理商标识码',
   FATHER_CODE             varchar(100)  DEFAULT NULL     COMMENT '父代理商标识码',
   AUDIT_AGENTID           varchar(14)   DEFAULT '0'      COMMENT '审核代理商ID'
   PROFIT_RATIO            varchar(4)    DEFAULT '10'     COMMENT '分润比例（十分之几）',
   OEM_PIC_ID              varchar(25)   DEFAULT NULL     COMMENT 'oem图片(图片id)',
   OPENING_LICENSE_PIC_ID  varchar(25)   DEFAULT NULL     COMMENT '开户许可证(图片id)',
   BUSINESS_LICENSE_PIC_ID varchar(25)   DEFAULT NULL     COMMENT '营业执照(图片id)',
   LEGAL_IDENTITY_PIC_ID   varchar(25)   DEFAULT NULL     COMMENT '法人身份证(图片id)',
   TAX_NO_PIC_ID           varchar(25)   DEFAULT NULL     COMMENT '税务登记证(图片id)'
   AUDIT_STATUS             varchar(2)   DEFAULT NULL     COMMENT '审核状态：0审核中，1审核通过，2审核不通过',
   AUDIT_FAIL_REASON       varchar(255)  DEFAULT NULL     COMMENT '审核不通过原因'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商信息表-临时表';


-- ---------------------------------------------------------
-- Table structure for MPAMNG_AGENT_ORG_INF   代理商    大商户配置
-- -----------------------------------------------------------
DROP TABLE IF EXISTS MPAMNG_AGENT_ORG_INF;
CREATE TABLE  MPAMNG_AGENT_ORG_INF (
  AGEORG_ID    varchar(9) NOT NULL,
  AGENT_ID     varchar(14) DEFAULT NULL COMMENT '代理商ID',
  COOPORG_NO   varchar(30) DEFAULT NULL COMMENT '合作机构编号',
  MER_NO       varchar(255) DEFAULT NULL COMMENT '合作机构大商户号',
  PRIMARY KEY (AGEORG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商    大商户配置';

