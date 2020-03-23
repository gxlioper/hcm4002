
create table SYS_LOG
(
  ID        VARCHAR2(50) not null,
  RY        VARCHAR2(50) not null,
  BIZ_TYPE  VARCHAR2(1) not null,
  OPER_TYPE VARCHAR2(1) not null,
  DATETIME  VARCHAR2(20) not null,
  OPER_ID   VARCHAR2(50),
  EXPLAIN   VARCHAR2(200),
  REMARK    VARCHAR2(200),
  REMARK1   VARCHAR2(200),
  REMARK2   VARCHAR2(200)
);

create table WEB_CONFIG
(
  CODE   VARCHAR(20) not null,
  NAME   VARCHAR(50) not null,
  TYPES  VARCHAR(50) not null,
  REMARK VARCHAR(100),
  MEMO   VARCHAR(50) not null
);

create table WEB_ROLE
(
  ID        VARCHAR(50) not null,
  ROLENAME VARCHAR(100) not null,
  DATETIME VARCHAR(20) not null,
  RY       VARCHAR(50),
  REMARK   VARCHAR(200)
);


create table WEB_ROLEMENU
(
  ID      VARCHAR(50) not null,
  ROLE_ID VARCHAR(50) not null,
  GNCD_ID VARCHAR(50),
  REMARK  VARCHAR(200)
);
create table WEB_XTGNB
(
  ID           VARCHAR(50) not null,
  URL          VARCHAR(200),
  NAME         VARCHAR(100),
  TYPE         CHAR(1),
  REMARK       VARCHAR(200),
  FATHERACTION VARCHAR(50),
  ACTIONTYPE   CHAR(1),
  ADMINTYPE    CHAR(1)
);

create table Web_Userjsb
(
  ID        VARCHAR(50) not null,
  role_id   VARCHAR(50) not null,
  user_id   int not null,
  remark    VARCHAR(100),
)

create table WEB_XTGNCD
(
  ID        VARCHAR(50) not null,
  XTGN_ID   VARCHAR(50) not null,
  NAME      VARCHAR(100) not null,
  FATHER_ID VARCHAR(50) not null,
  URLTYPE   CHAR(1) not null,
  ICON_URL  VARCHAR(100),
  DATETIME  VARCHAR(50) not null,
  RY        VARCHAR(50) not null,
  REMARK    VARCHAR(100),
  LEVELS    numeric(6) not null,
  USERTYPE  CHAR(1),
  OTHER_URL VARCHAR(100),
  INDEXID   VARCHAR(3),
  ISPOP     numeric(6)
);

prompt 1 records loaded
prompt Loading WEB_ROLE...
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40283f814c113a9f014c113bf31c0000', 'yyyy', '2015-03-13 11:44:43', null, null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('ff80808138323f2a013832768cce0061', '系统管理员', '2013-04-28 12:41:22', null, null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40283f814c2705d3014c270acc710002', '6666', '2015-09-28 09:21:54', '1', null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40283f814c1123de014c112906330000', 'tttt', '2015-03-13 11:24:03', null, null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40288aca4e6afdb2014e6b2a81d60000', 'Alex', '2015-07-08 08:57:04', null, null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40288aca4e8f44ca014e8f4b64620000', '11', '2015-09-28 09:22:04', '1', null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40288a234b4e0d4c014b4e206b000002', '管理员', '2015-02-03 14:28:42', null, null);
insert into WEB_ROLE (ID, ROLENAME, DATETIME, RY, REMARK)
values ('40288a234b4e0d4c014b4e20b97a0004', '分管员', '2015-02-03 14:29:02', null, null);
commit;
prompt 8 records loaded
prompt Loading WEB_ROLEMENU...
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1c9e0004', 'ff80808138323f2a013832768cce0061', '40288a493e171a5b013e171af6270000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1ca70006', 'ff80808138323f2a013832768cce0061', '2c9c49815219d6cc01521b4b3e710001', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cb20008', 'ff80808138323f2a013832768cce0061', '2c9ba3814b5ccdb4014b5d6997dd0004', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cc1000c', 'ff80808138323f2a013832768cce0061', '4028d5813ea5af64013ea61ec1e70187', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1ccc000f', 'ff80808138323f2a013832768cce0061', '4028d5813ea5af64013ea61f681f0189', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1ccf0010', 'ff80808138323f2a013832768cce0061', '40288a244ef1f4ac014ef1f9f5480000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1ce10013', 'ff80808138323f2a013832768cce0061', '40288a244ef24207014ef25d6be68888', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cf80015', 'ff80808138323f2a013832768cce0061', '40288a2435c2e2040135c317808e0003', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cfb0016', 'ff80808138323f2a013832768cce0061', '40288a0f36f1cc570136f1e0bc490001', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cff0017', 'ff80808138323f2a013832768cce0061', '40288a2435c2e2040135c317c4d40004', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1c790002', 'ff80808138323f2a013832768cce0061', '4028d5813ea5af64013ea622c0810250', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1c9b0003', 'ff80808138323f2a013832768cce0061', '40288a493e34bc3a013e34bdfff40000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288a234b70eded014b70fcd61c0003', '40288a234b4e0d4c014b4e20b97a0004', '4028d5813ea5af64013ea622c0810250', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54ebd9720014ebda7d2420000', '40288aca4e8f44ca014e8f4b64620000', '4028d5813ea5af64013ea622c0810250', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489500002', '40288aca4e6afdb2014e6b2a81d60000', '40288a2435c2e2040135c31742a80002', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489500003', '40288aca4e6afdb2014e6b2a81d60000', '40288a0f36f1cc570136f1e0bc490001', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489600005', '40288aca4e6afdb2014e6b2a81d60000', '40288a2435c2e2040135c317c4d40004', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489600006', '40288aca4e6afdb2014e6b2a81d60000', 'ff80808139d85e4f0139d86731b6009d', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489310000', '40288aca4e6afdb2014e6b2a81d60000', '2c9ba3814b5ccdb4014b5d6a9b430006', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489500001', '40288aca4e6afdb2014e6b2a81d60000', '2c9ba3814b5ccdb4014b5d6afc2c0008', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cb60009', 'ff80808138323f2a013832768cce0061', '40288ac54eae6598014eae6bf16e0001', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cba000a', 'ff80808138323f2a013832768cce0061', '40288acc3e3f98f3013e3f9ed4e50002', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cbe000b', 'ff80808138323f2a013832768cce0061', '4028d5813ea5af64013ea61f681f0190', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cc5000d', 'ff80808138323f2a013832768cce0061', '4028d5813ea5af64013ea61fb375018b', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cc8000e', 'ff80808138323f2a013832768cce0061', '4028d5813ebff5a3013ebff8ffcc0000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cd40011', 'ff80808138323f2a013832768cce0061', '40288a49407d917701407da9e3f40003', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cd80012', 'ff80808138323f2a013832768cce0061', '40288a244ef24207014ef25d6be80000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cf40014', 'ff80808138323f2a013832768cce0061', '2c9ba3814b5ccdb4014b5d6afc2c0008', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1ca10005', 'ff80808138323f2a013832768cce0061', '402809813e490407013e4909c0050000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1cad0007', 'ff80808138323f2a013832768cce0061', '40288a234c49515e014c4961b0200000', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('2c9c49815219d6cc01521b4c1d020018', 'ff80808138323f2a013832768cce0061', 'ff80808139d85e4f0139d86731b6009d', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288a234b70eded014b70fcd5f10000', '40288a234b4e0d4c014b4e20b97a0004', '2c9ba3814b5ccdb4014b5d6a9b430006', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288a234b70eded014b70fcd6150001', '40288a234b4e0d4c014b4e20b97a0004', '2c9ba3814b5ccdb4014b5d6afc2c0008', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288a234b70eded014b70fcd6180002', '40288a234b4e0d4c014b4e20b97a0004', '4028d5813ea5af64013ea621ee99024e', null);
insert into WEB_ROLEMENU (ID, ROLE_ID, GNCD_ID, REMARK)
values ('40288ac54e6b6ea8014e6b7489600004', '40288aca4e6afdb2014e6b2a81d60000', '40288a2435c2e2040135c317808e0003', null);
commit;

prompt 14 records loaded
prompt Loading WEB_USERINFO...
insert into WEB_USERINFO (ID, USERNAME, PASSWD, USERLOCK, LASTIP, LASTDATE, LOGINNUM, STATUS, CDATE, USERTYPE, NAME, PID, TEL1, TEL2, REMARK1, REMARK2, SEX, EMAIL)
values ('40288aca4e70e55c014e70f34a660000', 'dasdasdadasdad', '123456', 0, null, null, 0, null, '20150709115429', 0, '231231313131231231', null, null, null, null, null, '2', null);
insert into WEB_USERINFO (ID, USERNAME, PASSWD, USERLOCK, LASTIP, LASTDATE, LOGINNUM, STATUS, CDATE, USERTYPE, NAME, PID, TEL1, TEL2, REMARK1, REMARK2, SEX, EMAIL)
values ('1', 'admin', '888888', 0, '0:0:0:0:0:0:0:1', '20150708104455', 3820, 1, '20120303', 1, '系统管理员', '2344567890', '010-16899168', '010-88877886', null, null, '1', 'syn@gmail.com');
insert into WEB_USERINFO (ID, USERNAME, PASSWD, USERLOCK, LASTIP, LASTDATE, LOGINNUM, STATUS, CDATE, USERTYPE, NAME, PID, TEL1, TEL2, REMARK1, REMARK2, SEX, EMAIL)
values ('40288a4941560f6501415657ff340000', 'luzhiyou', '123123', 0, '0:0:0:0:0:0:0:1', '20150202143535', 1, null, '20130925111829', 2, 'luzhiyou', '130182198705284417', null, null, null, null, '1', null);
insert into WEB_USERINFO (ID, USERNAME, PASSWD, USERLOCK, LASTIP, LASTDATE, LOGINNUM, STATUS, CDATE, USERTYPE, NAME, PID, TEL1, TEL2, REMARK1, REMARK2, SEX, EMAIL)
values ('40288aca4e8b6426014e8b7c4ac30000', '21312312', '123456', 0, null, null, 0, null, '20150714153415', 0, '3132321', null, '312312321', '31221', null, null, '1', null);
commit;

prompt Loading WEB_XTGNB...
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('69', 'notice/noticeAdd.action', '添加公告', '2', null, '59', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('70', 'notice/noticeUpdate.action', '修改公告', '2', null, '59', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('71', 'notice/delNotice.action', '删除公告', '2', null, '59', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('114', 'terminfotermmainshow.action', '终端信息管理加载数据', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('115', 'terminfotermBinding.action', '终端信息管理跳转绑定', '2', null, '54', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('116', 'terminfoupdateTermInfoBinding.action', '终端信息管理保存绑定', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('117', 'terminfofindByTermId.action', '终端信息管理查询id重复', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('128', 'termmenuaddMenuzbOne.action', '终端菜单组管理跳转到增加菜单', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('77', 'addhelp.action', '添加帮助信息', '2', null, '55', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('78', 'third/thirdUpdate.action', '修改第三方信息', '2', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('79', 'third/delete.action', '删除第三方信息', '2', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('80', 'third/savebankConfig.action', '添加第三方', '2', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('81', 'third/updateTermConfig.action', '修改第三方配置信息', '2', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('126', 'termmenutermMenumainshow.action', '终端菜单组管理加载数据', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('127', 'termmenuoneMenuGroupTree.action', '终端菜单组管理加载修改菜单树', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('129', 'termmenuaddmenut.action', '终端菜单组管理增加菜单', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('85', 'monitor/monitortoOperEraMonitor.action', '操作时时监控', '1', null, '85', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('92', 'watercontrollist.action', '水控管理', '1', null, '92', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('94', 'webConfigtermConfigmainshow.action', '加载配置管理数据', '2', null, '89', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('95', 'webConfigtoChangeType.action', '跳转修改配置管理数据', '2', null, '89', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('96', 'webConfigupdateConfig.action', '修改配置管理数据', '2', null, '89', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('97', 'webConfigNamewebconfignamemainshow.action', '加载配置管理名称数据', '2', null, '90', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('98', 'webConfigNametoEditConfigName.action', '跳转修改配置管理名称数据', '2', null, '90', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('99', 'webConfigNameupdateConfigName.action', '修改配置管理名称数据', '2', null, '90', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('100', 'webConfigNotewebconfignotemainshow.action', '加载配置管理备注数据', '2', null, '91', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('101', 'webConfigNotetoEditConfigNote.action', '跳转修改配置管理备注数据', '2', null, '91', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('102', 'webConfigNoteupdateConfigNote.action', '修改配置管理备注数据', '2', null, '91', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('103', 'monitor/monitortermoperinfoshow.action', '操作时时监控加载数据', '2', null, '85', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('104', 'monitor/monitortermOperListShow.action', '终端操作监控加载数据', '2', null, '86', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('105', 'monitor/monitortermOperTimeListShow.action', '终端操作分时加载数据', '2', null, '93', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('106', 'watercontrolmainshow.action', '水控管理加载数据', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('107', 'watercontroltoadd.action', '水控管理跳转增加', '2', null, '92', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('108', 'watercontrolinsertWaterControl.action', '水控管理增加', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('109', 'watercontrolcheckById.action', '水控管理查询id重复', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('110', 'watercontrolcheckByName.action', '水控管理查询name重复', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('111', 'watercontroldeleteWaterControl.action', '水控管理删除', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('112', 'watercontroltoUpdate.action', '水控管理跳转更新', '2', null, '92', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('113', 'watercontrolupdateWaterControl.action', '水控管理更新', '2', null, '92', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('118', 'terminfofindByTermIp.action', '终端信息管理查询ip重复', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('119', 'terminfotoSaveTermInfo.action', '终端信息管理跳转新增', '2', null, '54', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('120', 'terminfosaveTermInfo.action', '终端信息管理保存新增', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('121', 'terminfodelTermInfos.action', '终端信息管理批量删除', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('122', 'terminfoupdateTerm.action', '终端信息管理跳转更新', '2', null, '54', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('123', 'terminfoupdateTermInfo.action', '终端信息管理保存更新', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('124', 'terminfoopenOrCloseTerm.action', '终端信息管理启用停用', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('125', 'terminfogetteAll.action', '终端信息管理加载分配终端数据', '2', null, '54', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('130', 'termmenudelmenut.action', '终端菜单组管理删除菜单', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('131', 'termmenudelMenuGAll.action', '终端菜单组管理删除菜单组', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('132', 'termmenugetMenuzbOne.action', '终端菜单组管理跳转到修改菜单', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('133', 'termmenutoTermUpdateCd.action', '终端菜单组管理修改页', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('134', 'termmenuaddNewMenug.action', '终端菜单组管理增加菜单组', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('135', 'termmenutoaddMenu.action', '终端菜单组管理跳转到增加菜单组', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('136', 'termmenueditmenut.action', '终端菜单组管理修改菜单', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('137', 'termmenuhelp.action', '终端菜单组管理跳转到帮助', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('138', 'termmenutoaddHelp.action', '终端菜单组管理跳转到增加帮助', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('139', 'termmenuaddhelp.action', '终端菜单组管理增加帮助', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('140', 'termmenutoTermInfoUpdate.action', '终端菜单组管理跳转到修改菜单组名称', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('141', 'termmenuupdateTermInfoDo.action', '终端菜单组管理修改菜单组名称', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('142', 'termmenufindSameLevelMenuCount.action', '终端菜单组管理同一个级别下有多少菜单', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('143', 'termmenufindChildrensCountById.action', '终端菜单组管理查询下面子菜单的个数', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('144', 'termmenuselpic.action', '终端菜单组管理跳转到选择第一级图片', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('145', 'termmenuselpic2.action', '终端菜单组管理跳转到选择第二级图片', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('146', 'termmenuselFirstLevelFunction.action', '终端菜单组管理选择第一级图片', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('147', 'termmenushowPic.action', '终端菜单组管理跳转到图片展示', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('148', 'termmenuinsertMenuGroupByExistedMenuGroupId.action', '终端菜单组管理复制菜单组', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('149', 'termmenufindByMenuGroupName.action', '终端菜单组管理菜单组名称是否重复', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('150', 'termmenutoaddvalue.action', '终端菜单组管理跳转到增加最值', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('151', 'termmenusaveValue.action', '终端菜单组管理增加最值', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('152', 'termmenutoaddPassword.action', '终端菜单组管理跳转到增加密码', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('55', 'toPlTermCd.action', '终端菜单天假', '1', null, '55', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('153', 'termmenuaddPassword.action', '终端菜单组管理增加密码', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('74', 'update.action', '修改终端信息', '2', null, '54', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('156', 'termmenutotransMethod.action', '终端菜单组管理跳转到增加转账', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('157', 'termmenusavetranMethod.action', '终端菜单组管理增加转账', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('158', 'termmenutoaddlost.action', '终端菜单组管理跳转到增加挂失', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('159', 'termmenusavelostMethod.action', '终端菜单组管理增加挂失', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('160', 'termmenugetMenuFlag.action', '终端菜单组管理获取右键显示标示', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('161', 'termmenutoupdateCd.action', '终端菜单组管理跳转到分配终端', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('162', 'termmenutoTermCdDetail.action', '终端菜单组管理跳转到查看终端', '2', null, '58', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('163', 'termmenutermAllshow.action', '终端菜单组管理加载分配终端', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('164', 'termmenuCdtermsave.action', '终端菜单组管理保存分配终端', '2', null, '58', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('56', 'toTermCdList.action', '终端菜单组管理', '1', null, '56', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('83', 'termMenu/editmenu.action', '修改终端菜单信息', '2', null, '55', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('84', 'termMenu/delmenu.action', '删除终端菜单', '2', null, '55', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('82', 'termMenu/addmenu.action', '添加终端菜单信息', '2', null, '55', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('88', 'report/toTranAmt.action', '终端交易金额监控', '1', null, '87', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('14', 'createMenu.action', '初始化菜单', '1', null, '14', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('1', 'usermanager.action', '用户管理', '1', null, '1', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('39', 'treeShow.action', '显示菜单树', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('40', 'getGnMenuOne.action', '得到一个功能菜单', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('41', 'getGnList.action', '得到功能列表', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('42', 'addmenu.action', '增加菜单', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('43', 'getMenuzbOne.action', '得到菜单主表', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('44', 'delmenu.action', '删除一级菜单', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('45', 'rolegnshow.action', '显示所有角色功能', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('46', 'rolegnshowone.action', '显示一个角色功能', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('47', 'rolemainshow.action', '显示所有角色', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('48', 'rolegn.action', '获取角色对应功能', '2', null, '15', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('49', 'delrole.action', '删除角色', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('50', 'rolemainheader.action', '获取角色列表头文件', '2', null, '15', '2', '1');
commit;
prompt 100 records committed...
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('51', 'roleedit.action', '编辑角色', '2', null, '15', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('52', 'roleeditdo.action', '新增或修改角色', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('53', 'rolegnsave.action', '保存角色功能', '2', null, '15', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('15', 'rolemain.action', '角色管理', '1', null, '15', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('21', 'getJob.action', '岗位授权管理', '1', null, '21', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('86', 'monitor/monitortoTermLogList.action', '终端操作监控', '1', null, '86', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('59', 'notice/toNoticeList.action', '公告管理', '1', null, '59', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('68', 'third/toThirdList.action', '第三方管理', '1', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('90', 'webConfigNametoInfoConfigName.action', '配置名称管理', '1', null, '90', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('93', 'monitor/monitortoOperTimerList.action', '终端操作分时', '1', null, '93', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('57', 'toBaseInfo.action', '平台基本信息', '1', null, '56', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('87', 'report/toTermControl.action', '到终端状态监控', '1', null, '87', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('89', 'webConfigtoInfoConfig.action', '配置管理', '1', null, '89', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('16', 'toAlink.action', '链路管理', '1', null, '16', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('91', 'webConfigNotetoInfoConfigNote.action', '配置备注管理', '1', null, '91', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('54', 'terminfoterminfolist.action', '终端信息管理', '1', null, '54', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('165', 'feemanager.action', '缴费管理', '1', null, '165', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('166', 'feeeditdo.action', '新增或修改缴费信息', '2', null, '165', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('58', 'termmenutoTermMenu.action', '终端菜单', '1', null, '57', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('60', 'sysLogMain.action', '系统日志管理', '1', null, '60', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('61', 'editmenu.action', '修改菜单', '2', null, '14', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('62', 'usereditdo.action', '新增或修改用户', '2', null, '1', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('63', 'deluser.action', '删除用户', '2', null, '1', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('64', 'gweditdo.action', '新增或修改岗位', '2', null, '21', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('65', 'gwdel.action', '删除岗位', '2', null, '21', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('66', 'sysLogToExcel.action', '转存日志', '2', null, '60', '2', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('67', 'third/toSavaBankConfigPage.action', '添加第三方', '2', null, '68', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('17', 'toApp.action', '应用列表管理', '1', null, '17', '1', '1');
insert into WEB_XTGNB (ID, URL, NAME, TYPE, REMARK, FATHERACTION, ACTIONTYPE, ADMINTYPE)
values ('167', 'delfee.action', '删除缴费信息', '2', null, '165', '2', '1');
commit;

prompt Loading WEB_XTGNCD...
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a49407d917701407da904f66666', '0', '水控设置', '40288a2435c2e2040135c316df1b0001', '1', 'ico-prive', '2015-11-03 11:27:20', '1', null, 2, '1', null, '15', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a493e34bc3a013e34bdfff41111', '133', '修改菜单组中菜单', '40288a493e16f796013e170c27060000', '1', null, '2013-04-23 16:58:40', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a244ef24207014ef25d6be68888', '92', '水控管理', '40288a49407d917701407da904f66666', '1', null, '2015-11-03 11:27:25', '1', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('2c9c49815219d6cc01521b4b3e710001', '165', '缴费管理', '2c9c49815219d6cc01521adea2540000', '1', null, '2016-01-07 16:54:22', '1', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a234c49515e014c4961b0200000', '57', '平台信息管理', '4028d5813ea5af64013ea6189aad00bf', '1', 'ico-dataport', '2015-03-24 09:24:41', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('2c9c49815219d6cc01521adea2540000', '0', '缴费管理', '40288a2435c2e2040135c316df1b0001', '1', null, '2016-01-07 14:55:44', '1', null, 2, '1', null, '10', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a244ef1f4ac014ef1f9f5480000', '90', '配置名称管理', '40288a49407d917701407da904f50000', '1', null, '2015-08-03 13:12:47', 'admin', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a244ef24207014ef25d6be80000', '91', '配置备注管理', '40288a49407d917701407da904f50000', '1', null, '2015-08-03 15:01:25', 'admin', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a49407d917701407da9e3f40003', '89', '配置管理', '40288a49407d917701407da904f50000', '1', null, '2013-08-27 09:32:28', 'admin', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4090098150cb47aa0150cb5ef74c0002', '92', '水控管理', '40288a49407d917701407da904f58888', '1', null, '2015-11-03 11:23:29', '1', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea61f681f0190', '93', '终端操作分时', '4028d5813ea5af64013ea61dfb910185', '1', null, '2013-05-15 10:57:56', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a493e16f796013e170c27060000', '0', '终端管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-aca', '2013-07-17 17:25:35', 'admin', null, 2, '1', null, '10', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288ac54eae6598014eae6bf16e0001', '17', '应用列表管理', '4028d5813ea5af64013ea6189aad00bf', '1', null, '2015-07-21 10:23:06', '1', null, 3, '1', null, '12', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea6189aad00bf', '0', '平台管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-dataport', '2013-07-17 17:26:24', 'admin', null, 2, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea61ec1e70187', '87', '终端状态监控', '4028d5813ea5af64013ea61dfb910185', '1', null, '2013-05-15 10:57:13', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea61f681f0189', '86', '终端操作监控', '4028d5813ea5af64013ea61dfb910185', '1', null, '2013-05-15 10:57:56', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea621ee99024e', '0', '公告管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-30-1', '2013-07-17 17:25:19', 'admin', null, 2, '1', null, '10', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ebff5a3013ebff8ffcc0000', '88', '终端交易监控', '4028d5813ea5af64013ea61dfb910185', '1', null, '2013-05-20 11:26:06', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('ff80808139d85e4f0139d86731b6009d', '1', '用户管理', '40288a2435c2e2040135c31742a80002', '1', 'ico-profile', '2013-07-18 13:48:16', 'admin', null, 3, '1', null, '8', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a493e171a5b013e171af6270000', '54', '终端信息管理', '40288a493e16f796013e170c27060000', '1', null, '2013-04-17 16:27:23', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea622c0810250', '59', '公告设置', '4028d5813ea5af64013ea621ee99024e', '1', null, '2013-05-15 11:01:35', 'admin', null, 3, '1', null, '10', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('402809813e490407013e4909c0050000', '68', '第三方管理', '40288a493e16f796013e170c27060000', '1', null, '2013-04-27 09:09:35', 'admin', null, 3, '1', null, '12', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a493e34bc3a013e34bdfff40000', '58', '终端菜单组管理', '40288a493e16f796013e170c27060000', '1', null, '2013-04-23 16:58:40', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288acc3e3f98f3013e3f9a24eb0000', '0', '系统管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-password', '2013-07-17 17:26:35', 'admin', null, 2, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288acc3e3f98f3013e3f9ed4e50002', '60', '系统日志管理', '40288acc3e3f98f3013e3f9a24eb0000', '1', null, '2013-04-25 13:16:49', 'admin', null, 3, '1', null, '111', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea61dfb910185', '0', '终端监控', '40288a2435c2e2040135c316df1b0001', '1', 'ico-inspection', '2013-07-17 17:26:07', 'admin', null, 2, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a2435c2e2040135c317808e0003', '15', '角色管理', '40288a2435c2e2040135c31742a80002', '1', 'ico-password', '2013-07-18 14:13:33', 'admin', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a2435c2e2040135c317c4d40004', '14', '初始化菜单', '40288a2435c2e2040135c31742a80002', '1', null, '2012-12-03 10:10:56', 'admin', null, 3, '1', null, '6', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a2435c2e2040135c316df1b0001', '0', '后台管理', '0', '1', null, '2012-02-28 16:32:30', 'admin', null, 1, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a2435c2e2040135c31742a80002', '0', '授权管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-prive', '2013-07-17 17:25:01', 'admin', null, 2, '1', null, '9', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a0f36f1cc570136f1e0bc490001', '21', '岗位授权管理', '40288a2435c2e2040135c31742a80002', '1', 'ico-return', '2013-07-17 17:51:45', 'admin', null, 3, '1', null, '1', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('4028d5813ea5af64013ea61fb375018b', '85', '操作时时监控', '4028d5813ea5af64013ea61dfb910185', '1', null, '2013-05-15 10:58:15', 'admin', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('40288a49407d917701407da904f50000', '0', '配置管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-prive', '2013-08-27 09:32:17', 'admin', null, 2, '1', null, '13', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('2c9ba3814b5ccdb4014b5d6997dd0004', '16', '链路管理', '4028d5813ea5af64013ea6189aad00bf', '1', 'ico-dataport', '2015-07-20 14:23:20', '1', null, 3, '1', null, '11', 1);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('2c9ba3814b5ccdb4014b5d6a9b430006', '0', '个人信息管理', '40288a2435c2e2040135c316df1b0001', '1', 'ico-user', '2015-02-06 13:44:02', 'admin', null, 2, '1', null, '9', 2);
insert into WEB_XTGNCD (ID, XTGN_ID, NAME, FATHER_ID, URLTYPE, ICON_URL, DATETIME, RY, REMARK, LEVELS, USERTYPE, OTHER_URL, INDEXID, ISPOP)
values ('2c9ba3814b5ccdb4014b5d6afc2c0008', '0', '个人信息', '2c9ba3814b5ccdb4014b5d6a9b430006', '1', 'ico-user', '2015-02-06 13:46:56', 'admin', null, 3, '1', null, '9', 1);
commit;
