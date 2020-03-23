
GO
/****** Object:  Table [dbo].[crm_batch_check]    Script Date: 06/22/2017 21:33:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[crm_batch_check](
	[id] [varchar](50) NOT NULL,
	[batch_id] [varchar](50) NOT NULL,
	[check_type] [char](1) NOT NULL,
	[check_status] [char](1) NOT NULL,
	[checkuser] [int] NULL,
	[checkdate] [datetime] NULL,
	[checknotice] [varchar](500) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
