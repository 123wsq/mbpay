/*==============================================================*/
/* Table: MAPAPP_VALIDATE_CODE   验证码信息表                                       */
/*==============================================================*/
drop table if exists MPAPP_VALIDATE_CODE_INF;
CREATE TABLE MPAPP_VALIDATE_CODE(
	MOBILE_NO    CHAR(11) NOT NULL     COMMENT '手机号码',
	SEND_TIME    CHAR(14) NOT NULL     COMMENT '发送时间',
	CODE 	     CHAR(6)  NOT NULL     COMMENT '验证码',
	CODE_TYPE    char(2)  DEFAULT ''   COMMENT '验证码类型：01:注册验证码 02登陆密码修改或找回  03支付密码修改或召回',
    CODE_STATUS  char(1)  DEFAULT '0'  COMMENT '验证码状态：0 未使用  1  已使用',
	primary key (SEND_TIME)
)ENGINE=InnoDB  CHARSET=utf8 COMMENT='短信验证码表';

/*==============================================================*/
/* Table: MPAPP_ESIGN_INF_TEMP    电子签名临时表                                 */
/*==============================================================*/
drop table if exists MPAPP_ESIGN_INF_TEMP;
CREATE TABLE MPAPP_ESIGN_INF_TEMP(
	PRDORDNO     VARCHAR(25) NOT NULL  COMMENT '订单号',
	ESIGN_ID     VARCHAR(25) NOT NULL  COMMENT '电子签名ID',
	primary key (PRDORDNO)
)ENGINE=InnoDB  CHARSET=utf8 COMMENT='电子签名临时表';


/*==============================================================*/
/* Table: MPAPP_LOGIN_INF         APP登陆信息表                                  */
/*==============================================================*/
drop table if exists MPAPP_LOGIN_INF;
CREATE TABLE MPAPP_LOGIN_INF(
	CUST_ID          VARCHAR(14)   NOT NULL  COMMENT '客户ID（唯一标识）',
	LOGIN_TIME       VARCHAR(12)   NOT NULL  COMMENT '客户端登陆时间单位:秒',
	IP               VARCHAR(25)   NOT NULL  COMMENT '客户端IP地址',
	MAC              VARCHAR(100)  NOT NULL  COMMENT '客户端MAC地址',
	SYS_TYPE         VARCHAR(1)    NOT NULL  COMMENT '客户端类型',
	TOKEN            VARCHAR(50)   NOT NULL  COMMENT 'TOKEN',
	LAST_LOGIN_DATE  VARCHAR(8)    NOT NULL  COMMENT '最后登陆日期',
	LAST_LOGIN_TIME  VARCHAR(14)   NOT NULL  COMMENT '最后登陆时间',
	primary key (CUST_ID,SYS_TYPE,LAST_LOGIN_DATE,LAST_LOGIN_TIME)
)ENGINE=InnoDB  CHARSET=utf8 COMMENT='APP登陆信息表';

