
/****** Object:  Table [dbo].[zyb_inquisition_result]    Script Date: 12/19/2017 10:06:38 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[zyb_inquisition_result](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[examinfo_id] [int] NOT NULL,
	[item_id] [varchar](50) NOT NULL,
	[result] [varchar](500) NOT NULL,
	[item_type] [varchar](1) NOT NULL,
	[exam_date] [datetime] NOT NULL,
	[exam_doctor] [varchar](50) NOT NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_zyb_inquisition_result] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


