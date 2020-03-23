--�����ֵ�����Σ��ֵ�ȼ�
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('Σ��ֵ�ȼ�','WJZDJ','A��','Y','1','A')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('Σ��ֵ�ȼ�','WJZDJ','B��','Y','2','B')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children) values ('Σ��ֵ�ȼ�','WJZDJ','�ط���','Y','3','C')

alter table exam_Critical_detail add critical_class_parent_id int,  --����
									 critical_class_id int,  --����
									 critical_class_level int, --Σ��ֵ�ȼ� 
									 critical_suggestion varchar(3000),--Σ��ֵ����
									 disease_num varchar(45) --��������
									 
/****** Object:  Table [dbo].[exam_Critical_class]    Script Date: 07/11/2019 13:48:17 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_Critical_class](   --Σ��ֵ����
	[id] [int] IDENTITY(1,1) NOT NULL,
	[critical_class_name] [varchar](50) NOT NULL,  --�������
	[critical_class_level] [int] NOT NULL,         --���ȼ�  1 ���࣬2����
	[parent_id] [int] NULL,                        --�����Ӧ���� id
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

CREATE TABLE [dbo].[exam_Critical_logic](  --Σ��ֵ�߼�����
	[id] [varchar](50) NOT NULL,
	[critical_class_parent_id] [int] NOT NULL,  --Σ��ֵ����id
	[critical_class_id] [int] NOT NULL,         --Σ��ֵ����id
	[critical_class_level] [int] NOT NULL,      --Σ��ֵ�ȼ� 
	[isActive] [varchar](10) NOT NULL,          --�Ƿ�����
	[critical_suggestion] [varchar](3000) NULL, --Σ��ֵ����
	[disease_num] [varchar](45) NULL,           --��������
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

CREATE TABLE [dbo].[exam_Critical_logic_item](  --Σ��ֵ�߼�____������
	[id] [varchar](50) NOT NULL,
	[logic_id] [varchar](50) NOT NULL,          --Σ��ֵ�߼�����ID
	[logic_item_name] [varchar](50) NOT NULL,   --Σ��ֵ�߼���������
	[logic_index] [int] NOT NULL,               --Σ��ֵ�߼���������
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

CREATE TABLE [dbo].[exam_Critical_logic_item_condition]( --Σ��ֵ�߼�____����ֵ��
	[id] [varchar](50) NOT NULL,
	[logic_item_id] [varchar](50) NOT NULL,              --Σ��ֵ�߼�����ID
	[item_num] [varchar](50) NOT NULL,                   --�����Ŀ����
	[charging_item_code] [varchar](50) NOT NULL,         --�շ���Ŀ����
	[condition_value] [varchar](50) NOT NULL,            --����ֵ
	[condition] [varchar](10) NOT NULL,                  --����
	[logic_index] [int] NOT NULL,                        --����ֵ����
 CONSTRAINT [PK_exam_Critical_logic_item_condition] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
--�˵�
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2400','examCriticalClassPage.action','Σ��ֵ������',1,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2046','addexamCriticalClassPage.action','Σ��ֵ�������ҳ��',2,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2047','editexamCriticalClassPage.action','Σ��ֵ����޸�ҳ��',2,null,'2400',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2045','saveCriticalClass.action','Σ��ֵ�������',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2041','updateCriticalClass.action','Σ��ֵ����޸�',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2042','removeCriticalClass.action','Σ��ֵ���ɾ��',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2043','queryCriticalClass.action','Σ��ֵ����ҳ��ѯ',2,null,'2400',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2044','criticalClasslist.action','Σ��ֵ����б�',2,null,'2400',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2048','examCriticalLog.action','��𼶱�Σ��ֵ�߼�����',1,null,'2048',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2049','examCriticalLogicadd.action','��𼶱�Σ��ֵ�߼��༭ҳ��',2,null,'2048',1,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2050','saveExamCriticalLogic.action','��𼶱�Σ��ֵ�߼�����',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2051','queryExamCriticalLogic.action','��𼶱�Σ��ֵ�߼���ҳ��ѯ',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2052','removeExamCriticalLogic.action','��𼶱�Σ��ֵ�߼�ɾ��',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2053','updateExamCriticalLogicIsAcive.action','��𼶱�Σ��ֵ�߼��༭',2,null,'2048',2,1,0);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('2054','updateExamCriticalLogicIsAcive.action','��ȡ��𼶱�Σ��ֵ�߼������Լ�����ֵ',2,null,'2048',2,1,0);