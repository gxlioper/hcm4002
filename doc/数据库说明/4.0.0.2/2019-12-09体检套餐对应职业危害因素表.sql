ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_indicator] varchar(45);--ְҵ��������𣺸��ѣ��ŷ�

ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_class] varchar(45);--������ͣ���Ӧ�����ֵ�

alter table zyb_exam_occuhazardfactors add hazard_year int --Σ������

/****** Object:  Table [dbo].[set_occuhazardfactors]    Script Date: 12/09/2019 14:10:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[set_occuhazardfactors]( --����ײͶ�ӦְҵΣ�����ر�
	[id] [int] IDENTITY(1,1) NOT NULL,
	[set_id] [int] NOT NULL,                  --�ײ�ID
	[hazard_code] [varchar](50) NOT NULL,     --Σ�����ر���
	[occuphyexaclassID] [varchar](50) NOT NULL, --ְҵ������id
	[hazard_year] [int] NULL,                  --Σ������
	[creater] [int] NULL,
	[create_time] [datetime](10) NULL,
 CONSTRAINT [PK_set_occuhazardfactors] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


