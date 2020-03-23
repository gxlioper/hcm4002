alter table exam_info add card_num varchar(50)  --体检信息表增加会员卡号，适用于会员卡登记是写入
alter table card_info add is_set_status int not null default(0) --卡信息表增加是否绑定套餐字段，默认0未绑定、1绑定

/****** Object:  Table [dbo].[card_exam_set] 卡信息对应套餐信息表   Script Date: 05/06/2019 09:11:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[card_exam_set](
	[id] [varchar](50) NOT NULL,
	[card_num] [varchar](50) NOT NULL,     --卡号
	[set_num] [varchar](50) NOT NULL,      --体检套餐编码
	[set_amount] [decimal](8, 2) NOT NULL, --套餐金额
	[set_name] [varchar](50) NOT NULL,     --套餐名称
	[set_discount] [decimal](5, 2) NOT NULL,--套餐折扣
	[center_num] [varchar](50) NULL,        --体检中心编码
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_exam_set] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

CREATE NONCLUSTERED INDEX [IX_card_exam_set_card_num] ON [dbo].[card_exam_set] 
(
	[card_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_card_exam_set_num] ON [dbo].[card_exam_set] 
(
	[set_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[card_exam_set_item]卡信息对应套餐项目信息表    Script Date: 05/06/2019 09:12:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[card_exam_set_item](
	[id] [varchar](50) NOT NULL,
	[card_num] [varchar](50) NOT NULL,          --卡号
	[charging_item_code] [varchar](50) NOT NULL,--收费项目编码
	[set_num] [varchar](50) NOT NULL,           --体检套餐编码
	[itemnum] [int] NOT NULL,                   --项目数量
	[discount] [decimal](5, 2) NULL,            --项目折扣
	[amount] [decimal](8, 2) NOT NULL,          --金额
	[item_amount] [decimal](8, 2) NOT NULL,     --原金额
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_exam_set_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_card_exam_set_item_card_num] ON [dbo].[card_exam_set_item] 
(
	[card_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_card_exam_set_item_code] ON [dbo].[card_exam_set_item] 
(
	[charging_item_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_card_exam_set_item_num] ON [dbo].[card_exam_set_item] 
(
	[set_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

ALTER TABLE [dbo].[card_exam_set_item] ADD  CONSTRAINT [DF_card_exam_set_item_itemnum]  DEFAULT ((1)) FOR [itemnum]
GO
