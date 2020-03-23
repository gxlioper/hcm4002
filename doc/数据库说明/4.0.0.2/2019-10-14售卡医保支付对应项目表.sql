
CREATE TABLE [dbo].[card_sale_medical_item](   --售卡省市医保支付对应体检项目表
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](50) NOT NULL,   --售卡交易流水号
	[charging_item_code] [varchar](50) NOT NULL, --收费项目编码
	[itemnum] [int] NOT NULL,                    --项目个数
	[item_amount] [decimal](10, 4) NOT NULL,     --项目原单价
	[discount] [decimal](5, 2) NOT NULL,         --折扣
	[amount] [decimal](10, 4) NOT NULL,          --折后总金额
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_sale_medical_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


