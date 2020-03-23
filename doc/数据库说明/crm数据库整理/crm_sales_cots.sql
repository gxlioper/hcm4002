
GO
/****** Object:  Table [dbo].[crm_sales_cost]    Script Date: 06/22/2017 23:15:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[crm_sales_cost](
	[id] [varchar](50) NOT NULL,
	[sales_id] [int] NOT NULL,
	[cost_amount] [decimal](8, 2) NOT NULL,
	[cost_date] [datetime] NOT NULL,
	[cost_type] [char](1) NOT NULL,
	[batch_num] [varchar](50) NULL,
	[payment_type] [varchar](10) NULL,
	[remark] [varchar](500) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
