alter table batch add accountcreater int 
alter table batch add accountdate datetime
alter table batch add overcreater int
alter table batch add overdate datetime
alter table batch add accountflag int not null default(0)--1表示结帐-0表示未封帐
alter table batch add report_sms_notice int not null default(0) -- 批次领取报告短信通知标志， 0表示不通知， 1表示通知


/****** Object:  Table [dbo].[batch_flow_log]    Script Date: 06/02/2018 20:20:55 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[batch_flow_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[batch_id] [int] NOT NULL,
	[group_id] [int] NULL,
	[flow_type] [int] NOT NULL,
	[creater] [int] NOT NULL,
	[createdate] [datetime] NOT NULL,
	[remark] [varchar](500) NULL,
	[notes] [varchar](500) NULL,
	[center_num] [varchar](20) NOT NULL,
 CONSTRAINT [PK_batch_flow_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 审核2取消审核，3封帐4取消封帐5 结帐' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'batch_flow_log', @level2type=N'COLUMN',@level2name=N'flow_type'
GO