
GO
/****** Object:  Table [dbo].[crm_sign_bill_plan]    Script Date: 06/22/2017 21:12:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[crm_sign_bill_plan](
	[id] [varchar](50) NOT NULL,
	[company_id] [int] NOT NULL,
	[sign_num] [varchar](50) NOT NULL,
	[sign_name] [varchar](50) NOT NULL,
	[sign_year] [varchar](4) NOT NULL,
	[sign_type] [varchar](10) NOT NULL,
	[old_new_type] [varchar](10) NOT NULL,
	[customer_type] [varchar](10) NOT NULL,
	[sign_count] [int] NOT NULL,
	[sign_date] [datetime] NULL,
	[sign_persion] [int] NULL,
	[sign_amount] [decimal](8, 2) NULL,
	[end_date] [datetime] NULL,
	[track_progress] [char](1) NOT NULL,
	[sign_status] [char](1) NOT NULL,
	[protect_date] [datetime] NULL,
	[abort_date] [datetime] NULL,
	[create_time] [datetime] NOT NULL,
	[creater] [int] NOT NULL,
	[track_time] [datetime] NOT NULL,
	[sign_pingying] [varchar](50) NULL,
 CONSTRAINT [PK_crm_sign_bill_plan] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单位id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'company_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单计划编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单计划名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'年度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_year'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'新旧分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'old_new_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户分类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'customer_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_count'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计签单日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计体检人数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_persion'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计体检金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_amount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计体检结束日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'end_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'跟踪进度' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'track_progress'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单计划状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'sign_status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'保护日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'protect_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'保护截止日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'abort_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'create_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'申请人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'creater'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'跟踪进度变化时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_bill_plan', @level2type=N'COLUMN',@level2name=N'track_time'
GO
