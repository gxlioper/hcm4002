ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_indicator] varchar(45);--职业病付费类别：个费，团费

ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_class] varchar(45);--体检类型：对应数据字典

alter table zyb_exam_occuhazardfactors add hazard_year int --危害年限

/****** Object:  Table [dbo].[set_occuhazardfactors]    Script Date: 12/09/2019 14:10:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[set_occuhazardfactors]( --体检套餐对应职业危害因素表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[set_id] [int] NOT NULL,                  --套餐ID
	[hazard_code] [varchar](50) NOT NULL,     --危害因素编码
	[occuphyexaclassID] [varchar](50) NOT NULL, --职业体检类别id
	[hazard_year] [int] NULL,                  --危害年限
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


