--������ͨ������ʽ����
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'������ԷҽԺ', N'IS_DEPT_TYPE', N'XYYY', N'Y', N'��ͨ���Ҽ����ʽ��COMM��ʾ��ͨ��XYYY��ʾ��ԷҽԺ')


/****** Object:  Table [dbo].[exam_summary_reject]    Script Date: 01/18/2018 16:58:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[exam_summary_reject](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[examinfo_id] [int] NOT NULL,  -- ���ID
	[reject_doctor] [int] NOT NULL, --����ҽ��
	[reject_time] [datetime] NOT NULL, --����ʱ��
	[reject_context] [varchar](500) NULL, -- ����ԭ��
	[done_status] [int] NOT NULL, --����״̬
	[done_doctor] [int] NULL,     --����ҽ��
	[done_time] [datetime] NULL,  --����ʱ��
 CONSTRAINT [PK_exam_summary_reject] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO