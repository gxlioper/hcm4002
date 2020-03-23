--增加是否体检车登记配置
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'健康体检中心', N'IS_OUT_REG', N'N', N'Y', N'是否为体检车部署，N表示院内程序，Y表示体检车程序', NULL, N'000')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'健康管理中心', N'IS_OUT_DIRECTLY', N'Y', N'Y', N'体检车与主系统数据互通是否为直接连接，N表示通过文件导入导出，Y表示体检车与院内网络连通，直接连接数据库', NULL, N'000')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'健康管理中心', N'SQLITE_PATH', N'D:', N'Y', N'体检车导入导出数据库生成/上传路径', NULL, N'000')

create table out_data_exp( --导出记录表
  exam_num varchar(20) primary key,
  exp_date datetime
);

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[data_migration_company]( --单位、体检任务、分组导入信息对照表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[com_name] [varchar](100) NULL,
	[com_id] [int] NULL,           --导入信息本系统单位ID
	[com_id_car] [int] NULL,       --被导入信息原系统单位ID
	[batch_id] [int] NULL,         --导入信息本系统体检任务ID
	[batch_id_car] [int] NULL,     --被导入信息原系统体检任务ID
	[group_id] [int] NULL,         --导入信息本系统分组ID
	[group_id_car] [int] NULL,     --被导入信息原系统分组ID
 CONSTRAINT [PK_data_migration_company] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[data_migration_exam]    Script Date: 02/05/2020 11:02:53 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[data_migration_exam]( --体检信息导入对照表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NULL,        --导入信息本系统体检编号
	[exam_num_bar] [varchar](50) NULL,    --被导入信息原系统体检编号
	[arch_num] [varchar](50) NULL,        --导入信息本系统档案号
	[arch_num_bar] [varchar](50) NULL,    --被导入信息原系统档案号
	[create_time] [datetime] NULL,
	[creater] [int] NULL,
 CONSTRAINT [PK_data_migration_exam] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


--系统功能
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1762', N'getDataMigrationPage.action', N'获取数据导入导出页面', N'1', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1763', N'getDataMigrationList.action', N'获取需要导出的数据列表', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1764', N'importDataMigrationDirectlyConnected.action', N'直连方式数据导入导出', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1765', N'getDataBaseConfigPage.action', N'数据库/ftp 配置页面', N'2', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1766', N'getDataBaseConfigList.action', N'获取数据库/ftp 配置信息', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1767', N'getDataBaseConfigEditPage.action', N'获取数据库/ftp配置编辑页面', N'2', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1768', N'saveDataBaseConfig.action', N'保存数据库/ftp配置信息', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1769', N'exportDataMigrationDb.action', N'导出数据到数据库文件', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1770', N'importDataMigrationDb.action', N'导入上传数据库文件', N'2', NULL, N'1762', N'2', N'1', N'0')