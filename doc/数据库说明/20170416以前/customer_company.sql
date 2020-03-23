CREATE TABLE [dbo].[customer_company](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[batchcom_id] [int] NOT NULL,--对应批次任务里面的单位id
	[center_id] [int] NOT NULL,
	[com_id] [int] NOT NULL,--上级单位id
	[creater] [int] NOT NULL,
	[create_time] [datetime] NOT NULL,
	[updater] [int] NOT NULL,
	[update_time] [datetime] NOT NULL,
	[remark] [varchar](50) NULL,
	[remark2] [varchar](50) NULL,
 CONSTRAINT [PK_customer_company] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
