--增加普通科室样式配置
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'北京西苑医院', N'IS_DEPT_TYPE', N'XYYY', N'Y', N'普通科室检查样式：COMM表示普通、XYYY表示西苑医院')


/****** Object:  Table [dbo].[exam_summary_reject]    Script Date: 01/18/2018 16:58:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[exam_summary_reject](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[examinfo_id] [int] NOT NULL,  -- 体检ID
	[reject_doctor] [int] NOT NULL, --驳回医生
	[reject_time] [datetime] NOT NULL, --驳回时间
	[reject_context] [varchar](500) NULL, -- 驳回原因
	[done_status] [int] NOT NULL, --处理状态
	[done_doctor] [int] NULL,     --处理医生
	[done_time] [datetime] NULL,  --处理时间
 CONSTRAINT [PK_exam_summary_reject] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO