---- 菜单信息表
Drop table AUTH_MENU_INF;
create table AUTH_MENU_INF(
	MENU_ID VARCHAR2(20) primary key NOT NULL ,
	MENU_PAR_ID VARCHAR2(20) ,
	MENU_NAME VARCHAR2(40) NOT NULL,
	MENU_URL VARCHAR2(100) ,
	MENU_STATUS int ,
	MENU_TYPE int not null,
	MENU_CODE VARCHAR2(20),
	SYS_ID VARCHAR2(6),
	MENU_IS_LEAF INT
);

comment on table AUTH_MENU_INF IS '菜单信息表';

comment on column AUTH_MENU_INF.MENU_ID 		is '菜单编号' ;
comment on column AUTH_MENU_INF.MENU_PAR_ID 	is '菜单父编号' ;
comment on column AUTH_MENU_INF.MENU_NAME 		is '菜单名称' ;
comment on column AUTH_MENU_INF.MENU_URL 		is '菜单URL' ;
comment on column AUTH_MENU_INF.MENU_STATUS 	is '菜单状态' ;
comment on column AUTH_MENU_INF.MENU_TYPE 		is '菜单类型' ;
comment on column AUTH_MENU_INF.MENU_CODE 		is '菜单英文代码' ;
comment on column AUTH_MENU_INF.SYS_ID 			is '系统编号' ;
comment on column AUTH_MENU_INF.MENU_IS_LEAF 	is '是否叶子节点' ;


---- 角色信息表需要的主键序列
Drop sequence ROLE_ID_SEQ;
create sequence ROLE_ID_SEQ increment by 1 start with 1;

---- 角色信息表
Drop table AUTH_ROLE_INF;
create table AUTH_ROLE_INF(
	ROLE_ID VARCHAR2(40) not null,
	ORG_ID VARCHAR2(40) not null,
	ROLE_NAME VARCHAR2(80),
	ROLE_DESC VARCHAR2(100),
	ROLE_STATUS int 
);

comment on table AUTH_ROLE_INF 					IS '角色信息表';

comment on column AUTH_ROLE_INF.ROLE_ID 		is '角色编号' ;
comment on column AUTH_ROLE_INF.ORG_ID 			is '机构编号' ;
comment on column AUTH_ROLE_INF.ROLE_NAME 		is '角色名称' ;
comment on column AUTH_ROLE_INF.ROLE_DESC 		is '角色描述' ;
comment on column AUTH_ROLE_INF.ROLE_STATUS 	is '角色状态  1:禁用；0:启动' ;


---- 角色信息表创建的触发器,用于主键的自增长
create or replace trigger trg_roles before insert on AUTH_ROLE_INF for each row 
begin 
select ROLE_ID_SEQ.nextval into :new.ID from dual;
end trg_roles;

---- 角色信息表需要的主键序列
drop sequence USER_ID_SEQ;
create sequence USER_ID_SEQ increment by 1 start with 1;

---- 用户信息表
drop table AUTH_USER_INF;
create table AUTH_USER_INF(
	ID NUMBER(10) primary key not null ,
	USER_ID varchar2(20) not null ,
	ORG_ID varchar2(40) not null,
	USER_NAME varchar2(40) ,
	USER_PWD varchar2(100),
	USER_RANDOM varchar2(15),
	USER_STATUS int 
);

comment on table AUTH_USER_INF 					IS '用户信息表';

comment on column AUTH_USER_INF.ID 				is '编号' ;
comment on column AUTH_USER_INF.USER_ID 		is '用户编号' ;
comment on column AUTH_USER_INF.ORG_ID 			is '机构编号' ;
comment on column AUTH_USER_INF.USER_NAME 		is '用户名' ;
comment on column AUTH_USER_INF.USER_PWD 		is '用户密码' ;
comment on column AUTH_USER_INF.USER_RANDOM 	is '用户随机码' ;
comment on column AUTH_USER_INF.USER_STATUS 	is '用户状态' ;

---- 用户信息表创建的触发器,用于主键的自增长
create or replace trigger trg_users before insert on AUTH_USER_INF for each row 
begin 
select USER_ID_SEQ.nextval into :new.ID from dual;
end trg_users;


---- 机构信息表
drop table AUTH_ORG_INF;
create table AUTH_ORG_INF(
	ORG_ID varchar2(40) primary key not null,
	ORG_NAME VARCHAR2(160) ,
	ORG_PAR_ID VARCHAR2(40) ,
	ORG_DESC VARCHAR2(200) ,
	ORG_LEVEL VARCHAR2(2),
	ORG_STATUS int,
	ORG_EMAIL VARCHAR2(50),
	ORG_MOBILE VARCHAR2(20),
	ORG_ADDRESS VARCHAR2(200),
	ORG_LOGAL  VARCHAR2(100)
);

comment on table AUTH_ORG_INF 					IS '机构信息表';

comment on column AUTH_ORG_INF.ORG_ID 			is '机构编号' ;
comment on column AUTH_ORG_INF.ORG_NAME 		is '机构名称' ;
comment on column AUTH_ORG_INF.ORG_PAR_ID 		is '机构父编号' ;
comment on column AUTH_ORG_INF.ORG_DESC 		is '机构描述' ;
comment on column AUTH_ORG_INF.ORG_LEVEL 		is '机构级别' ;
comment on column AUTH_ORG_INF.ORG_STATUS 		is '机构状态' ;
comment on column AUTH_ORG_INF.ORG_LOGAL 		is '机构联系人' ;
comment on column AUTH_ORG_INF.ORG_EMAIL 		is '机构邮箱' ;
comment on column AUTH_ORG_INF.ORG_MOBILE 		is '机构手机号' ;
comment on column AUTH_ORG_INF.ORG_ADDRESS 		is '机构地址' ;

---- 用户角色关联表
drop table AUTH_USER_ROLE_REL_INF;
create table AUTH_USER_ROLE_REL_INF (
	U_ID int not null,
	R_ID VARCHAR2(40) not null,
	primary key (U_ID,R_ID)
);

comment on table AUTH_USER_ROLE_REL_INF 		IS '用户角色关联表';

comment on column AUTH_USER_ROLE_REL_INF.U_ID 	is '用户编号' ;
comment on column AUTH_USER_ROLE_REL_INF.R_ID 	is '角色编号' ;


---- 菜单按钮关联表
drop table AUTH_MENU_BUTTON_REL_INF;

---- 机构角色关联表
drop table AUTH_ORG_ROLE_REL_INF;

---- 角色菜单按钮关联表
drop table AUTH_ROLE_MENU_BUTTON_REL_INF;

---- 按钮信息表
drop table AUTH_BUTTON_INF;

---- 角色菜单关联表
drop table AUTH_ROLE_MENU_REL_INF;
create table AUTH_ROLE_MENU_REL_INF(
	R_ID VARCHAR2(40) not null,
	MENU_ID VARCHAR2(20) not null ,
	primary key(R_ID,MENU_ID)
);

comment on table AUTH_ROLE_MENU_REL_INF 			IS '角色菜单关联表';

comment on column AUTH_ROLE_MENU_REL_INF.R_ID 		is '角色编号' ;
comment on column AUTH_ROLE_MENU_REL_INF.MENU_ID 	is '菜单编号' ;

---- 审计菜单表==预留多级别审计
drop table AUTH_AUDIT_MENU_REL_INF;
create table AUTH_AUDIT_MENU_REL_INF(
	AUDIT_ID varchar2(40)  not null,
	MENU_ID VARCHAR2(20)  not null,
	primary key (AUDIT_ID,MENU_ID)
);

comment on table AUTH_AUDIT_MENU_REL_INF IS '审计菜单表';

comment on column AUTH_AUDIT_MENU_REL_INF.AUDIT_ID 	is '审计编号' ;
comment on column AUTH_AUDIT_MENU_REL_INF.MENU_ID 	is '审计菜单' ;


---- 审计日志表
drop table AUTH_AUDITLOG_INF;
create table AUTH_AUDITLOG_INF(
	AUDITLOG_ID varchar2(100) not null,
	IP 			VARCHAR2(20)  not null,
	MENU_NAME 	VARCHAR2(40)  not null,
	U_ID 		VARCHAR2(20)  not null,
	USER_ID 	VARCHAR2(20)  not null,
	OPER_DATE 	VARCHAR2(14)  not null,
	primary key (AUDITLOG_ID)
);

comment on table AUTH_AUDITLOG_INF 					IS '审计日志表';

comment on column AUTH_AUDITLOG_INF.AUDITLOG_ID 	is '审计日志编号' ;
comment on column AUTH_AUDITLOG_INF.IP				is '请求客户端ip' ;
comment on column AUTH_AUDITLOG_INF.MENU_NAME 		is '操作的菜单' ;
comment on column AUTH_AUDITLOG_INF.U_ID 			is '用户唯一值' ;
comment on column AUTH_AUDITLOG_INF.USER_ID 		is '用户登陆名' ;
comment on column AUTH_AUDITLOG_INF.OPER_DATE 		is '操作时间' ;
