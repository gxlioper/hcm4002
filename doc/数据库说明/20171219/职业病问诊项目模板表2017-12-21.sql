
/****** Object:  Table [dbo].[zyb_ask_diagnosis_sample]    Script Date: 12/19/2017 10:05:58 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[zyb_ask_diagnosis_sample](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[sub_name] [varchar](100) NULL,
	[seq_code] [int] NULL,
	[type] [varchar](1) NOT NULL,
	[temp_content] [varchar](500) NULL,
	[is_active] [varchar](10) NOT NULL,
	[creater] [int] NULL,
	[creare_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK_ask_diagnosis_sample] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'问诊项目名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'zyb_ask_diagnosis_sample', @level2type=N'COLUMN',@level2name=N'name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'问题描述' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'zyb_ask_diagnosis_sample', @level2type=N'COLUMN',@level2name=N'sub_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顺序码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'zyb_ask_diagnosis_sample', @level2type=N'COLUMN',@level2name=N'seq_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'问诊项目类型 1表示职业病问诊项目 2表示放射职业病问诊项目' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'zyb_ask_diagnosis_sample', @level2type=N'COLUMN',@level2name=N'type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'问诊项目模板内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'zyb_ask_diagnosis_sample', @level2type=N'COLUMN',@level2name=N'temp_content'
GO


