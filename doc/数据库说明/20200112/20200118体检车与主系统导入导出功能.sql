--�����Ƿ���쳵�Ǽ�����
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'�����������', N'IS_OUT_REG', N'N', N'Y', N'�Ƿ�Ϊ��쳵����N��ʾԺ�ڳ���Y��ʾ��쳵����', NULL, N'000')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'������������', N'IS_OUT_DIRECTLY', N'Y', N'Y', N'��쳵����ϵͳ���ݻ�ͨ�Ƿ�Ϊֱ�����ӣ�N��ʾͨ���ļ����뵼����Y��ʾ��쳵��Ժ��������ͨ��ֱ���������ݿ�', NULL, N'000')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'������������', N'SQLITE_PATH', N'D:', N'Y', N'��쳵���뵼�����ݿ�����/�ϴ�·��', NULL, N'000')

create table out_data_exp( --������¼��
  exam_num varchar(20) primary key,
  exp_date datetime
);

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[data_migration_company]( --��λ��������񡢷��鵼����Ϣ���ձ�
	[id] [int] IDENTITY(1,1) NOT NULL,
	[com_name] [varchar](100) NULL,
	[com_id] [int] NULL,           --������Ϣ��ϵͳ��λID
	[com_id_car] [int] NULL,       --��������Ϣԭϵͳ��λID
	[batch_id] [int] NULL,         --������Ϣ��ϵͳ�������ID
	[batch_id_car] [int] NULL,     --��������Ϣԭϵͳ�������ID
	[group_id] [int] NULL,         --������Ϣ��ϵͳ����ID
	[group_id_car] [int] NULL,     --��������Ϣԭϵͳ����ID
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

CREATE TABLE [dbo].[data_migration_exam]( --�����Ϣ������ձ�
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NULL,        --������Ϣ��ϵͳ�����
	[exam_num_bar] [varchar](50) NULL,    --��������Ϣԭϵͳ�����
	[arch_num] [varchar](50) NULL,        --������Ϣ��ϵͳ������
	[arch_num_bar] [varchar](50) NULL,    --��������Ϣԭϵͳ������
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


--ϵͳ����
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1762', N'getDataMigrationPage.action', N'��ȡ���ݵ��뵼��ҳ��', N'1', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1763', N'getDataMigrationList.action', N'��ȡ��Ҫ�����������б�', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1764', N'importDataMigrationDirectlyConnected.action', N'ֱ����ʽ���ݵ��뵼��', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1765', N'getDataBaseConfigPage.action', N'���ݿ�/ftp ����ҳ��', N'2', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1766', N'getDataBaseConfigList.action', N'��ȡ���ݿ�/ftp ������Ϣ', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1767', N'getDataBaseConfigEditPage.action', N'��ȡ���ݿ�/ftp���ñ༭ҳ��', N'2', NULL, N'1762', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1768', N'saveDataBaseConfig.action', N'�������ݿ�/ftp������Ϣ', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1769', N'exportDataMigrationDb.action', N'�������ݵ����ݿ��ļ�', N'2', NULL, N'1762', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1770', N'importDataMigrationDb.action', N'�����ϴ����ݿ��ļ�', N'2', NULL, N'1762', N'2', N'1', N'0')