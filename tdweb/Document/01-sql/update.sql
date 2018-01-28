--hg add 20160406 代理商表添加字段
--MAX_TCAS 提现单笔收费金额  单位元
alter table MPAMNG_AGENT_INF add MAX_TCAS varchar(10) default '0';
alter table MPAMNG_AGENT_INF_TEMP add MAX_TCAS varchar(10) default '0';
--hg add 20160407 商户表添加字段
alter table MPOMNG_MOBILE_MER_INF add MAX_TCAS varchar(10) default '0';
--hg add 20160408 终端表添加字段
alter table MPAMNG_TERMINAL_INF add RATE_TCAS varchar(4) default '0';
alter table MPAMNG_TERMINAL_INF add MAX_TCAS varchar(10) default '0';