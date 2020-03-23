
/****** Object:  Table [dbo].[charging_invoice_relation]    Script Date: 12/06/2019 14:43:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[charging_invoice_relation](  --收费记录与发票关系表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[account_num] [varchar](50) NOT NULL,    --结算单号
	[invoice_id] [int] NOT NULL,             --发票ID
	[exam_type] [varchar](10) NOT NULL,      --结算类型  G个人收费、T团检结账、C会员卡销售、R单位账户充值
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_charging_invoice_relation] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


