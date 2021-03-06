/****
说明：
    1、添加了字段的备注说明
	2、增加了trancode 此字段默认 为001 不能为空,如果原来创建了此表，并且里面有记录，请慎重手工修改
****/
CREATE TABLE [dbo].[card_deal](
	[id] [varchar](50) NOT NULL,	
	[card_num] [varchar](50) NOT NULL,
	[trancode] [varchar](3) NOT NULL DEFAULT '001' , 
	[deal_type] [char](1) NOT NULL,
	[examinfo_id] [int] NULL,	
	[old_amount] [decimal](8, 2) NULL,
	[amount] [decimal](8, 2) NULL,
	[creater] [int] NOT NULL,
	[deal_time] [datetime] not NULL,
	[deal_date] [varchar](50) NOT NULL,
	[card_count] [int] NULL,
	[remark] [varchar](500) NULL,
	[summary_id] [int] NULL,

 CONSTRAINT [PK_card_consumer] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'票据编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'card_num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'体检编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'examinfo_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'deal_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易原金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'old_amount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'amount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易创建者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'creater'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'deal_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'deal_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易类型 001 表示卡操作类 002 表示发票交易类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'card_deal', @level2type=N'COLUMN',@level2name=N'trancode'
GO
/****** Object:  Default [DF_card_deal_trancode]    Script Date: 04/18/2017 10:04:57 ******/
GO
