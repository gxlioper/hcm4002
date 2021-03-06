
/****** Object:  Table [dbo].[zyb_exam_summary_result_occid]    Script Date: 04/12/2018 20:58:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[zyb_exam_summary_result_occid]( -- 检出职业病，疑似职业病等ID表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[result_id] [int] NOT NULL,                     --职业病总检检查结果表
	[occudiseaseIDorcontraindicationID] [varchar](50) NOT NULL, --职业病ID
 CONSTRAINT [PK_exam_summary_result_occid] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[zyb_exam_summary_result]    Script Date: 04/12/2018 20:58:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[zyb_exam_summary_result]( -- 职业病总检检查结果表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_info_id] [int] NOT NULL,           --体检ID
	[resultID] [varchar](50) NOT NULL,       --检查结果ID
	[exam_result] [varchar](100) NOT NULL,   --检查结果
	[remark] [varchar](500) NULL,            --备注
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_exam_summary_result] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
