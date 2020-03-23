INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS037', N'登记台可以调用收费页面', N'2', N'1', N'赋予操作人员拥有登记台可以调用收费页面功能', N'输入：1', N'表示操作人员拥有登记台可以调用收费页面的功能', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS038', N'人员可以查询所有单位信息', N'2', N'1', N'赋予操作人员拥有查询所有单位信息功能', N'输入：1', N'表示操作人员拥有查询所有单位信息的功能', NULL, NULL)
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXX', N'IS_COMPANY_SHOW_POW', N'Y', N'Y', N'单位查询是否关联创建人')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'北京天坛医院', N'IS_DEP_EDIT_QUESTIONNAIRE', N'Y', N'Y', N'是否启用普通科室检查页面具有修改问卷内容的功能')
--科室逻辑表
alter table dep_logic_exam_item         add item_code varchar(50)  
 alter table exam_result_detail add charging_item_id int  NOT NULL  default(0); --关联收费项目ID
alter table exam_info add isvisit int not null default(1)--1表示要回访-0表示无需回访
insert into WEB_RESOURCE values('RS023','人员设置后付费的资源','2','1','赋予人员后付费的资源','输入：1','表示操作员有此功能','','','Y')
insert into WEB_RESOURCE values('RS024','人员设置团体结算有减免的功能','2','1','赋予人员可以对团体进行减免','输入：1','表示操作员有此功能','','','Y')
insert into WEB_RESOURCE values('RS026','人员设置对批次进行封帐功能','2','1','赋予人员可以批次进行封帐','输入：1','表示操作员有此功能','','','Y')
insert into WEB_RESOURCE values('RS027','人员设置对批次进行审核功能','2','1','赋予人员可以批次进行审核','输入：1','表示操作员有此功能','','','Y')
INSERT INTO [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS036', N'销售签单计划是否分配排除撞单的功能', N'2', N'1', N'赋予人员具有排除撞单的权限。', N'输入：1', N'表示操作员有此功能', null, null, N'Y')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxx', N'IS_CHECK_NO_SUMMARY', N'N', N'Y', N'是否启用通过体检类型设置无需总检')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxx', N'IS_CHECK_NOSUMMARY_EXAMTYPE', N'1,2', N'Y', N'设置无需总检的体检类型，以逗号隔开')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS037', N'登记台可以调用收费页面', N'2', N'1', N'赋予操作人员拥有登记台可以调用收费页面功能', N'输入：1', N'表示操作人员拥有登记台可以调用收费页面的功能', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS038', N'人员可以查询所有单位信息', N'2', N'1', N'赋予操作人员拥有查询所有单位信息功能', N'输入：1', N'表示操作人员拥有查询所有单位信息的功能', NULL, NULL)
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXX', N'IS_COMPANY_SHOW_POW', N'N', N'Y', N'单位查询是否关联创建人')
delete from WEB_RESOURCE where code='RS034'
INSERT INTO [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS036', N'销售签单计划是否分配排除撞单的功能', N'2', N'1', N'赋予人员具有排除撞单的权限。', N'输入：1', N'表示操作员有此功能', null, null, N'Y')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS012', N'条码打印次数限制', N'2', N'1', N'赋予操作人员打印条码的最大次数，最小值为1，最大值不限（值为正整数）', N'输入：2', N'表示可以打印两次条码', NULL, NULL)
insert  into center_configuration ([config_key], [config_value], [is_active], [common],[center_name])  values('IS_REPORT_ROOM','N','Y','是否需要报告室流程，Y需要,N不需要','XXXXX') --报告室配置Y表示走报告室流程 N表示不走报告室
insert  into center_configuration ([config_key], [config_value], [is_active], [common],[center_name])  values('UPLOAD_FLOW','Y','Y','导检单回收上传流程, Y:表示在回收页面可以接上传','xxxx')--导检单是否可以在回收页面上传
insert  into center_configuration ([config_key], [config_value], [is_active], [common],[center_name])  values('IS_NEEDALERT_WJITEM','','Y','整单室缺项目时不弹出配置科室ID下的所有项目,ID 以,号隔开 12,23,24','XXXXX')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'体检中心', N'HEALTH_GET_DEP', N'123,345', N'Y', N'拥有健康管理项目的部门，以小写的逗号隔开')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'体检中心', N'HEALTH_GET_IS', N'Y', N'Y', N' 是否在打印室整单时候判断健康管理报告')insert  into WEB_RESOURCE values('RS041' ,'卡信息修改权限' ,'2' ,'1','赋予操作人员卡信息修改权限', '输入：1'	,'表示操作人员可以修改'  ,'','','Y')
INSERT [dbo].[data_dictionary] ([data_type], [data_code], [data_name], [remark], [isActive], [creater], [create_time], [updater], [update_time], [seq_code], [data_code_children]) VALUES ( N'体检类型', N'TJLX', N'招工体检', N'exam_report.fr3', N'Y', 14,'2018-12-23', 14, '2018-12-23', 7, N'ZGTJ')
--增加资源
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS011', N'医生拥有提示VIP客户到检功能', N'1', N'1', N'赋予操作人员VIP客户提示权限，数值型。1表示拥有VIP客户到检提示功能。', N'输入：1', N'表示拥有VIP客户到检提示功能', NULL, NULL)
insert into WEB_RESOURCE  ([code], [name], [type], [data_type], [notice], [example], [examplenote]) values('RS025','赋予总检医生一键取消\恢复功能','2','1','总检医生一键取消\恢复功能','','')
INSERT INTO [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS034', N'销售签单计划是否分配排除撞单的功能', N'2', N'1', N'赋予人员具有排除撞单的权限。', N'输入：1', N'表示操作员有此功能', null, null, N'Y')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS043', N'团体被单团体项目转个人项目资源', N'2', N'1', N'赋予操作员：团体被单团体项目转个人项目资源', N'输入：1', N'表示操作员可以团体被单团体项目转个人项目', NULL, NULL,'Y')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS042', N'团体被单个人项目转团体项目资源', N'2', N'1', N'赋予操作员：团体被单个人项目转团体项目资源', N'输入：1', N'表示操作员可以团体被单个人项目转团体项目', NULL, NULL,'Y')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS044', N'登记台首页团体体检转个人体检资源', N'2', N'1', N'赋予操作员：登记台首页团体体检转个人体检资源', N'输入：1', N'表示操作员可以登记台首页团体体检转个人体检', NULL, NULL,'Y')
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS045', N'登记台首页个人体检转团体体检资源', N'2', N'1', N'赋予操作员：登记台首页个人体检转团体体检资源', N'输入：1或2', N'表示操作员可以登记台首页个人体检转团体体检,1表示体检类型和项目类型都转为团体,2表示将体检类型转为团体', NULL, NULL,'Y')
INSERT INTO center_configuration VALUES ('体检中心', 'IS_PRIVATE','Y','Y','是否开启隐私保护功能','1')

INSERT INTO WEB_RESOURCE VALUES ('RS048', '操作人员隐私保护权限','2','1','赋予操作人员隐私保护权限','输入：1','表示拥有隐私权限','','','Y')

﻿INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_DJT_SHOW_WENJUAN', N'N', N'Y', N'登记台是否显示问卷结果页面，Y,显示N,不显示')

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'WXQZJ_URL', N'http://192.168.0.0:8080', N'Y', N'微信前置机ip端口，示例：http://192.168.0.0:8080')

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_CUSTOM_IDENTIFICATION', N'5,6', N'Y', N'定义哪些人员类别的体检者需要在页面有加重标识提醒')

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('1365','questionNaireShow.action','问卷地址',1,null,'3',1,1,0);
update WEB_XTGNB set URL='doaccountbatchto.action' where ID='1267'
update WEB_XTGNB set URL='doaccountbatchtoren.action' where ID='1268'
insert into WEB_RESOURCE values('RS034','人员设置对批次进行结算功能','2','1','赋予人员可以批次进行结帐','输入：1','表示操作员有此功能','','','Y')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'1800', N'dooverbatchto.action', N'批次进行结帐', N'2', NULL, N'1240', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'1801', N'dooverbatchtoren.action', N'批次解除结帐', N'2', NULL, N'1240', N'2', N'1')

INSERT [dbo].[data_dictionary] ([data_type], [data_code], [data_name], [remark], [isActive], [creater], [create_time], [updater], [update_time], [seq_code], [data_code_children]) VALUES ( N'样本分类', N'YBFL', N'影像', N'', N'Y', 14, CAST(0x0000A93700E681C4 AS DateTime), 0, NULL, 1, N'')
INSERT [dbo].[data_dictionary] ([data_type], [data_code], [data_name], [remark], [isActive], [creater], [create_time], [updater], [update_time], [seq_code], [data_code_children]) VALUES ( N'样本分类', N'YBFL', N'检查', N'', N'Y', 14, CAST(0x0000A93700E6D624 AS DateTime), 0, NULL, 2, N'')

alter table batch add is_separate_account int not null default(0) --

alter table exam_summary add cancel_type int not null default(0)  --
insert into WEB_RESOURCE values('RS025','赋予总检医生一键取消\恢复功能','2','1','总检医生一键取消\恢复功能','','')

/****** Object:  Table [dbo].[exam_summary_cancel]    Script Date: 05/25/2018 21:20:47 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_summary_cancel](  -- 总检一键取消恢复表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,     --体检号
	[final_status] [varchar](10) NOT NULL, --取消时总检状态
	[approve_status] [varchar](10) NOT NULL,--取消时审核状态
	[censoring_status] [varchar](10) NOT NULL,--取消时复审状态 
	[creater] [int] NOT NULL,                 --操作人
	[create_time] [datetime] NOT NULL,        --操作时间
	[cancel_type] [int] NOT NULL,             --操作类型 1 一键取消，0 一键恢复
 CONSTRAINT [PK_exam_summary_cancel] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


--------增加系统功能数据
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1702', N'getExamInfoAuditPageLog.action', N'检查结果、总检结果审计页面', N'1', NULL, N'1702', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1703', N'getExamInfoAuditListLog.action', N'审计查询体检信息列表', N'2', NULL, N'1702', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1704', N'getExamDepResultPageLog.action', N'审计查看普通科室日志页面', N'2', NULL, N'1702', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1705', N'getExamDepResultCountLog.action', N'审计查看科室检查次数日志树', N'2', NULL, N'1702', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1706', N'getCommonExamDetailListLog.action', N'审计查询科室检查历次结果日志列表', N'2', NULL, N'1702', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1707', N'getExamSummaryPageLog.action', N'审计查询总检审核复审日志页面', N'2', NULL, N'1702', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1708', N'getExamSummaryCountLog.action', N'审计查询总检审核复审次数日志树', N'2', NULL, N'1702', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1709', N'getExamExamDiseaseListLog.action', N'审计查询总检审核复审阳性发现日志列表', N'2', NULL, N'1702', N'2', N'1', N'1')

--------------------
/****** Object:  Table [dbo].[exam_dep_result_log]    Script Date: 06/09/2018 11:11:26 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_dep_result_log]( --普通科室检查结论日志主表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_info_id] [int] NOT NULL,
	[dep_id] [int] NOT NULL,
	[exam_doctor] [varchar](50) NOT NULL,
	[exam_date] [datetime] NOT NULL,
	[exam_result_summary] [varchar](500) NOT NULL,
	[suggestion] [varchar](500) NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_exam_dep_result_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'体检信息id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'exam_dep_result_log', @level2type=N'COLUMN',@level2name=N'exam_info_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'科室id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'exam_dep_result_log', @level2type=N'COLUMN',@level2name=N'dep_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查医生' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'exam_dep_result_log', @level2type=N'COLUMN',@level2name=N'exam_doctor'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'检查时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'exam_dep_result_log', @level2type=N'COLUMN',@level2name=N'exam_date'
GO


/****** Object:  Table [dbo].[common_exam_detail_log]    Script Date: 06/09/2018 11:12:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[common_exam_detail_log]( -- 普通科室项目检查结果日志表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dep_result_id] [int] NOT NULL,
	[exam_item_id] [int] NOT NULL,
	[health_level] [varchar](50) NOT NULL,
	[exam_result] [varchar](500) NOT NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_common_exam_detail_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


/****** Object:  Table [dbo].[exam_summary_log]    Script Date: 06/09/2018 11:14:01 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[exam_summary_log]( -- 总检、审核、复审 结论日志主表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_info_id] [int] NOT NULL,
	[exam_doctor_id] [int] NOT NULL,
	[exam_date] [datetime] NOT NULL,
	[final_exam_result] [text] NOT NULL,
	[operation_type] [int] NOT NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_exam_summary_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'操作类型 1总检保存 2审核保存 3复审保存 4一键取消恢复保存' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'exam_summary_log', @level2type=N'COLUMN',@level2name=N'operation_type'
GO


/****** Object:  Table [dbo].[examinfo_disease_log]    Script Date: 06/09/2018 11:15:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[examinfo_disease_log]( -- 阳性发现信息日志表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[summary_id] [int] NOT NULL,
	[disease_id] [int] NOT NULL,
	[disease_name] [varchar](500) NOT NULL,
	[suggest] [varchar](max) NOT NULL,
	[disease_index] [int] NOT NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[disease_type] [nchar](30) NULL,
	[icd_10] [nchar](30) NULL,
	[disease_class] [varchar](10) NULL,
	[remarke] [varchar](500) NULL,
 CONSTRAINT [PK_examinfo_disease_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


/****** Object:  Table [dbo].[barcode_print]    Script Date: 02/25/2019 15:26:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[barcode_print]( --条码打印记录表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,  --体检号
	[printer] [int] NOT NULL,           --打印人
	[print_time] [datetime] NULL,       --打印时间
 CONSTRAINT [PK_barcode_print] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO



