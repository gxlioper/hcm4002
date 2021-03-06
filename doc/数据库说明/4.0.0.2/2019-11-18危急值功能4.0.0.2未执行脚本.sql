 --数据字典增加危急值等级
delete data_dictionary where data_code = 'WJZDJ' and data_class is null
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','A类','Y','1','A',0)
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','B类','Y','2','B',0)
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','回访类','Y','3','C',0)

alter table exam_Critical_logic add critical_suggestion varchar(3000) --危急值建议
								   ,disease_num varchar(45)  --疾病编码
								   ,sex varchar(10)          --适用性别
								   ,age_min int              --适用最小年龄
								   ,age_max int              --适用最大年龄

alter table exam_Critical_detail add give_notice_type int

/****** Object:  Table [dbo].[exam_Critical_log]    Script Date: 11/19/2019 11:11:06 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_Critical_id] [int] NOT NULL,
	[create_time] [datetime] NULL,
	[creater] [int] NULL,
	[note] [varchar](200) NULL,
	[isActive] [varchar](10) NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

alter table crm_plan_tactics add level int
								   
insert into WEB_RESOURCE values ('RS054','判断用户是否有删除修改手动添加的危急值的权限','2','1','判断用户是否有删除修改手动添加的危急值的权限','输入：1','表示操作员有此功能','','','Y')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'IS_SHOW_ZJORKS', N'KS', N'Y', N'手动增加危急值页是普通科室还是主检室，ZJ表示主检室，KS表示科室 ')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'crm325', N'getCrmVisitPlanNewPage.action', N'新健康回访首页', N'1', NULL, N'crm325', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'crm326', N'getCrmVisitPlanNewExamInfoList.action', N'健康计划查询人员信息', N'2', NULL, N'crm325', N'2', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'crm327', N'getVisitPlanRecordPage.action', N'健康计划回访记录页面', N'2', NULL, N'crm325', N'2', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'crm328', N'getCrmVisitPlanListByExamNum.action', N'体检号获取已回访全部记录', N'2', NULL, N'crm325', N'2', N'2', N'1')
insert into WEB_XTGNB values ('2301','getExamCriticalList.action','主检室跟科室显示危急值列表','2','','','2','1','1')
insert into WEB_XTGNB values ('2302','showNewExamCritical.action','总检显示新增危急值','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2303','getCriticalClassD.action','获取危急值大类名称','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2304','getCriticalLevel.action','获取危急值等级','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2305','saveCriticalDetail.action','主检室及科室手动保存危急值','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2306','toSelectResult.action','跳转选择检查项目','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2307','delCriticalDetail.action','总检及科室手动删除危急值','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2308','getDisease.action','获取疾病','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2309','criticalhandle.action','危急值处理情况统计','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2310','getCriticalClass.action','获取危急值子类名称','2','','2301','2','1','1')
insert into WEB_XTGNB values ('2311','criticalhandleShow.action','危急值处理情况统计查询','2','','2301','2','1','1')
