--售卡交易主表
CREATE TABLE [dbo].[card_sale_summary](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL, --售卡交易流水号
	[sale_status] [int] NOT NULL,            --交易状态 1售卡、0预售卡 注：预售卡在操作售卡后修改此状态为1
	[invoice_id] [int] NULL,                 --所开发票ID
	[is_print_recepit] [char](1) NOT NULL,   --是否开发票 N未开、Y已开
	[amount] [decimal](18, 2) NOT NULL,      --卡内总金额
	[sale_amount] [decimal](18, 2) NOT NULL, --售卡总金额
	[sale_type] [int] NOT NULL,              --交易类型 1售卡、0预售卡 注：预售卡在操作售卡后不修改此状态
	[sale_user] [int] NULL,                  --售卡人
	[sale_time] [datetime] NULL,             --售卡时间
	[advance_sale_user] [int] NULL,          --预售卡人  直接售卡操作不需要填写
	[advance_sale_time] [datetime] NULL,     --预售卡时间
	[daily_status] [int] NOT NULL default(0),--日结状态 0未日结、1已日结
 CONSTRAINT [PK_card_sale_summary] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


--售卡交易明细表
CREATE TABLE [dbo].[card_sale_detail](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL,  --售卡交易主表流水号
	[card_num] [varchar](50) NOT NULL,        --卡号
	[amount] [decimal](18, 2) NOT NULL,       --卡内金额
	[sale_amount] [decimal](18, 2) NOT NULL,  --售卡金额
	[creater] [int] NULL,                     --创建人
	[create_time] [datetime] NULL,            --创建时间
 CONSTRAINT [PK_card_sale_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

--售卡收费方式表
CREATE TABLE [dbo].[card_sale_way](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL, --售卡交易主表流水号
	[charging_way] [varchar](45) NOT NULL,   --收费方式ID
	[amount] [decimal](8, 2) NOT NULL,       --金额
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_sale_way] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

