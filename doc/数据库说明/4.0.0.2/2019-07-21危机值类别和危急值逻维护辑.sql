--数据字典增加危急值等级
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('危急值等级','WJZDJ','A类','Y','1','A')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('危急值等级','WJZDJ','B类','Y','2','B')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('危急值等级','WJZDJ','回访类','Y','3','C')

alter table exam_Critical_detail add critical_class_parent_id int,  --大类
									 critical_class_id int,  --子类
									 critical_class_level int, --危急值等级 
									 critical_suggestion varchar(3000),--危急值建议
									 disease_num varchar(45) --疾病编码
									 
/****** Object:  Table [dbo].[exam_Critical_class]    Script Date: 07/11/2019 13:48:17 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_class](   --危机值类别表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[critical_class_name] [varchar](50) NOT NULL,  --类别名称
	[critical_class_level] [int] NOT NULL,         --类别等级  1 大类，2子类
	[parent_id] [int] NULL,                        --子类对应大类 id
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[seq_code][int] NULL,
	[remark][varchar](50)NULL
 CONSTRAINT [PK_exam_Critical_class] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_logic](  --危急值逻辑主表
	[id] [varchar](50) NOT NULL,
	[critical_class_parent_id] [int] NOT NULL,  --危急值大类id
	[critical_class_id] [int] NOT NULL,         --危急值子类id
	[critical_class_level] [int] NOT NULL,      --危急值等级 
	[isActive] [varchar](10) NOT NULL,          --是否启用
	[critical_suggestion] [varchar](3000) NULL, --危急值建议
	[disease_num] [varchar](45) NULL,           --疾病编码
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL
	
 CONSTRAINT [PK_exam_Critical_logic] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_logic_item](  --危急值逻辑____条件表
	[id] [varchar](50) NOT NULL,
	[logic_id] [varchar](50) NOT NULL,          --危急值逻辑主表ID
	[logic_item_name] [varchar](50) NOT NULL,   --危机值逻辑条件名称
	[logic_index] [int] NOT NULL,               --危机值逻辑条件索引
 CONSTRAINT [PK_exam_Critical_logic_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_logic_item_condition]( --危急值逻辑____条件值表
	[id] [varchar](50) NOT NULL,
	[logic_item_id] [varchar](50) NOT NULL,              --危急值逻辑条件ID
	[item_num] [varchar](50) NOT NULL,                   --检查项目编码
	[charging_item_code] [varchar](50) NOT NULL,         --收费项目编码
	[condition_value] [varchar](50) NOT NULL,            --条件值
	[condition] [varchar](10) NOT NULL,                  --条件
	[logic_index] [int] NOT NULL,                        --条件值索引
 CONSTRAINT [PK_exam_Critical_logic_item_condition] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
--菜单
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2400','examCriticalClassPage.action','危急值类别管理',1,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2046','addexamCriticalClassPage.action','危急值类别新增页面',2,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2047','editexamCriticalClassPage.action','危急值类别修改页面',2,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2045','saveCriticalClass.action','危急值类别新增',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2041','updateCriticalClass.action','危急值类别修改',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2042','removeCriticalClass.action','危急值类别删除',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2043','queryCriticalClass.action','危急值类别分页查询',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2044','criticalClasslist.action','危急值类别列表',2,null,'2400',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2048','examCriticalLog.action','类别级别危急值逻辑管理',1,null,'2048',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2049','examCriticalLogicadd.action','类别级别危急值逻辑编辑页面',2,null,'2048',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2050','saveExamCriticalLogic.action','类别级别危急值逻辑新增',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2051','queryExamCriticalLogic.action','类别级别危急值逻辑分页查询',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2052','removeExamCriticalLogic.action','类别级别危急值逻辑删除',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2053','updateExamCriticalLogicIsAcive.action','类别级别危急值逻辑编辑',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2054','updateExamCriticalLogicIsAcive.action','获取类别级别危急值逻辑条件以及条件值',2,null,'2048',2,1,0);