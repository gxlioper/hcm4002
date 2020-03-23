alter table department_dep add dict_num varchar(50) --ִ�п��ұ���
alter table department_vs_center add dict_num varchar(50) --ִ�п��ұ���
alter table user_usr add dict_num varchar(50) --ִ�п��ұ���

/****** Object:  Table [dbo].[department_dict]    Script Date: 11/08/2019 14:52:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[department_dict](    --ִ�п��ұ�
	[id] [int] IDENTITY(1,1) NOT NULL,   
	[dept_code] [varchar](20) NOT NULL,  --���ұ���
	[dept_name] [varchar](50) NOT NULL,  --��������
	[dept_class] [varchar](10) NULL,     --�������
	[input_code] [varchar](20) NULL,     --���Ƿ�
	[seq_code] [int] NULL,               --˳����
	[is_active] [varchar](10) NOT NULL,  --�Ƿ���Ч
	[center_num] [varchar](50) NOT NULL, --������ı���
	[remark] [varchar](100) NULL,        --��ע
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK_department_dict] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


