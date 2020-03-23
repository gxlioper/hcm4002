alter table exam_info add card_num varchar(50)  --�����Ϣ�����ӻ�Ա���ţ������ڻ�Ա���Ǽ���д��
alter table card_info add is_set_status int not null default(0) --����Ϣ�������Ƿ���ײ��ֶΣ�Ĭ��0δ�󶨡�1��

/****** Object:  Table [dbo].[card_exam_set] ����Ϣ��Ӧ�ײ���Ϣ��   Script Date: 05/06/2019 09:11:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[card_exam_set](
	[id] [varchar](50) NOT NULL,
	[card_num] [varchar](50) NOT NULL,     --����
	[set_num] [varchar](50) NOT NULL,      --����ײͱ���
	[set_amount] [decimal](8, 2) NOT NULL, --�ײͽ��
	[set_name] [varchar](50) NOT NULL,     --�ײ�����
	[set_discount] [decimal](5, 2) NOT NULL,--�ײ��ۿ�
	[center_num] [varchar](50) NULL,        --������ı���
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

/****** Object:  Table [dbo].[card_exam_set_item]����Ϣ��Ӧ�ײ���Ŀ��Ϣ��    Script Date: 05/06/2019 09:12:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[card_exam_set_item](
	[id] [varchar](50) NOT NULL,
	[card_num] [varchar](50) NOT NULL,          --����
	[charging_item_code] [varchar](50) NOT NULL,--�շ���Ŀ����
	[set_num] [varchar](50) NOT NULL,           --����ײͱ���
	[itemnum] [int] NOT NULL,                   --��Ŀ����
	[discount] [decimal](5, 2) NULL,            --��Ŀ�ۿ�
	[amount] [decimal](8, 2) NOT NULL,          --���
	[item_amount] [decimal](8, 2) NOT NULL,     --ԭ���
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
