
GO
/****** Object:  Table [dbo].[crm_sign_tracking]    Script Date: 06/22/2017 23:18:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[crm_sign_tracking](
	[id] [varchar](50) NOT NULL,
	[sign_num] [varchar](50) NOT NULL,
	[tracking_date] [datetime] NOT NULL,
	[contact_name] [varchar](50) NOT NULL,
	[phone] [varchar](50) NOT NULL,
	[tracking_content] [varchar](500) NOT NULL,
	[remark] [varchar](500) NULL,
 CONSTRAINT [PK_crm_sign_ tracking] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签单计划编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'sign_num'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'跟踪日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'tracking_date'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'目标联系人姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'contact_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'目标联系人电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'phone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'沟通内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'tracking_content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'crm_sign_tracking', @level2type=N'COLUMN',@level2name=N'remark'
GO
