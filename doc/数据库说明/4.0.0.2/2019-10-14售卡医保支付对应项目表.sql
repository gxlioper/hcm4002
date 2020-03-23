
CREATE TABLE [dbo].[card_sale_medical_item](   --�ۿ�ʡ��ҽ��֧����Ӧ�����Ŀ��
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](50) NOT NULL,   --�ۿ�������ˮ��
	[charging_item_code] [varchar](50) NOT NULL, --�շ���Ŀ����
	[itemnum] [int] NOT NULL,                    --��Ŀ����
	[item_amount] [decimal](10, 4) NOT NULL,     --��Ŀԭ����
	[discount] [decimal](5, 2) NOT NULL,         --�ۿ�
	[amount] [decimal](10, 4) NOT NULL,          --�ۺ��ܽ��
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_sale_medical_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


