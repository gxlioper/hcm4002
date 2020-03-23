--�ۿ���������
CREATE TABLE [dbo].[card_sale_summary](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL, --�ۿ�������ˮ��
	[sale_status] [int] NOT NULL,            --����״̬ 1�ۿ���0Ԥ�ۿ� ע��Ԥ�ۿ��ڲ����ۿ����޸Ĵ�״̬Ϊ1
	[invoice_id] [int] NULL,                 --������ƱID
	[is_print_recepit] [char](1) NOT NULL,   --�Ƿ񿪷�Ʊ Nδ����Y�ѿ�
	[amount] [decimal](18, 2) NOT NULL,      --�����ܽ��
	[sale_amount] [decimal](18, 2) NOT NULL, --�ۿ��ܽ��
	[sale_type] [int] NOT NULL,              --�������� 1�ۿ���0Ԥ�ۿ� ע��Ԥ�ۿ��ڲ����ۿ����޸Ĵ�״̬
	[sale_user] [int] NULL,                  --�ۿ���
	[sale_time] [datetime] NULL,             --�ۿ�ʱ��
	[advance_sale_user] [int] NULL,          --Ԥ�ۿ���  ֱ���ۿ���������Ҫ��д
	[advance_sale_time] [datetime] NULL,     --Ԥ�ۿ�ʱ��
	[daily_status] [int] NOT NULL default(0),--�ս�״̬ 0δ�սᡢ1���ս�
 CONSTRAINT [PK_card_sale_summary] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


--�ۿ�������ϸ��
CREATE TABLE [dbo].[card_sale_detail](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL,  --�ۿ�����������ˮ��
	[card_num] [varchar](50) NOT NULL,        --����
	[amount] [decimal](18, 2) NOT NULL,       --���ڽ��
	[sale_amount] [decimal](18, 2) NOT NULL,  --�ۿ����
	[creater] [int] NULL,                     --������
	[create_time] [datetime] NULL,            --����ʱ��
 CONSTRAINT [PK_card_sale_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

--�ۿ��շѷ�ʽ��
CREATE TABLE [dbo].[card_sale_way](
	[id] [varchar](50) NOT NULL,
	[sale_trade_num] [varchar](20) NOT NULL, --�ۿ�����������ˮ��
	[charging_way] [varchar](45) NOT NULL,   --�շѷ�ʽID
	[amount] [decimal](8, 2) NOT NULL,       --���
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
 CONSTRAINT [PK_card_sale_way] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

