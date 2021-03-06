USE [PEIS_DBGJ]
GO
/****** Object:  Table [dbo].[customer_member_info]    Script Date: 03/21/2017 10:02:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[customer_member_info](
	[id] [varchar](50) NOT NULL,
	[arch_num] [varchar](50) NOT NULL,
	[level] [int] NOT NULL,
	[integral] [int] NOT NULL,
	[totalamt] [decimal](8, 2) NULL,
	[totaltimes] [int] NULL,
	[prelevel] [int] NULL,
	[preintegral] [int] NULL,
	[leveltime] [datetime] NULL,
	[integeraltime] [datetime] NULL,
 CONSTRAINT [PK_customer_member_info] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
