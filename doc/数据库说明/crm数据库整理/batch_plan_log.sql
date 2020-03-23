
GO
/****** Object:  Table [dbo].[batch_plan_log]    Script Date: 06/22/2017 21:22:28 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[batch_plan_log](
	[id] [varchar](50) NOT NULL,
	[project_id] [varchar](50) NOT NULL,
	[project_status] [varchar](100) NOT NULL,
	[project_reason] [varchar](500) NULL,
	[creater] [varchar](50) NOT NULL,
	[creater_time] [datetime] NOT NULL,
	[project_type] [varchar](50) NULL,
	[project_name] [varchar](100) NULL,
	[type] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
