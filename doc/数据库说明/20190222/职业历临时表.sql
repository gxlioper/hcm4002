CREATE TABLE [dbo].[impoccuhis](
	[id] [varchar](50) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,
	[id_num] [varchar](50) NOT NULL,
	[arch_num] [varchar](50) NOT NULL,
	[company] [varchar](200) NULL,
	[workshop] [varchar](200) NULL,
	[worktype] [varchar](200) NULL,
	[startdate] [varchar](50) NULL,
	[enddate] [varchar](50) NULL,
	[harmname] [varchar](100) NULL,
	[concentrations] [varchar](100) NULL,
	[measure] [varchar](500) NULL,
	[creater] [int] NOT NULL,
	[create_time] [datetime] NOT NULL,
	[radiation] [varchar](200) NULL,
	[man_haur] [varchar](200) NULL,
	[cumulative_exposure] [varchar](200) NULL,
	[history_excessive] [varchar](200) NULL,
	[diseases] [varchar](100) NULL,
	[diagnosisdate] [varchar](50) NULL,
	[diagnosiscom] [varchar](100) NULL,
	[diagnosisnotice] [varchar](1000) NULL,
	[diseasereturn] [varchar](1000) NULL,
	[occuType] [int] NOT NULL,
	[histype] [int] NOT NULL,
 CONSTRAINT [PK_impoccuhis] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'company'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'车间部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'workshop'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工种' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'worktype'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'入厂时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'startdate'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'离厂时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'enddate'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'有害因素种类、名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'harmname'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'有害因素浓度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'concentrations'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'防护措施' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'measure'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'记录创建者' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'creater'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'create_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'放射线种类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'radiation'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'每日工时数或工作量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'man_haur'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'累积受照射剂量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'cumulative_exposure'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'过量照射史' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'history_excessive'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'疾病名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'diseases'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'诊断日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'diagnosisdate'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'诊断单位' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'diagnosiscom'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'治疗经过' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'diagnosisnotice'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'转归' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'diseasereturn'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型：0 健康体检、1职业病 2 放射性职业病' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'occuType'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'职业史类型：0职业历史、1既往史' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impoccuhis', @level2type=N'COLUMN',@level2name=N'histype'
GO

ALTER TABLE [dbo].[impoccuhis] ADD  CONSTRAINT [DF_impoccuhis_occuType]  DEFAULT ((0)) FOR [occuType]
GO

ALTER TABLE [dbo].[impoccuhis] ADD  CONSTRAINT [DF_impoccuhis_histype]  DEFAULT ((0)) FOR [histype]
GO


