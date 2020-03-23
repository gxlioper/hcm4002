
/****** Object:  Table [dbo].[charging_invoice_relation]    Script Date: 12/06/2019 14:43:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[charging_invoice_relation](  --�շѼ�¼�뷢Ʊ��ϵ��
	[id] [int] IDENTITY(1,1) NOT NULL,
	[account_num] [varchar](50) NOT NULL,    --���㵥��
	[invoice_id] [int] NOT NULL,             --��ƱID
	[exam_type] [varchar](10) NOT NULL,      --��������  G�����շѡ�T�ż���ˡ�C��Ա�����ۡ�R��λ�˻���ֵ
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


