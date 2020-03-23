
/****** Object:  Table [dbo].[exam_summary_review_item]    Script Date: 05/28/2018 08:52:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_summary_review_item]( --设定复查项目表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,   --体检号
	[charging_item_id] [int] NOT NULL,   --收费项目ID
	[creater] [int] NULL,                --创建人
	[create_time] [datetime] NULL,       --创建时间
 CONSTRAINT [PK_exam_summary_review_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO






/****** Object:  Table [dbo].[exam_summary_review]    Script Date: 12/27/2019 16:43:05 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_summary_review](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,
	[review_status] [char](1) NOT NULL,
	[review_title] [varchar](100) NOT NULL,
	[review_date] [datetime] NOT NULL,
	[review_user] [int] NOT NULL,
	[review_time] [datetime] NOT NULL,
	[notice_user] [int] NULL,
	[notice_time] [datetime] NULL,
	[notice_type] [char](1) NULL,
 CONSTRAINT [PK_exam_summary_review] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


