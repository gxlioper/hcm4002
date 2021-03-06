
/****** Object:  Table [dbo].[config_exam_vip]    Script Date: 03/15/2019 09:04:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[config_exam_vip](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[vip_code] [varchar](20) NULL,
	[vip_name] [varchar](50) NOT NULL,
	[amt_lower] [numeric](8, 2) NULL,
	[amt_upper] [numeric](10, 0) NULL,
	[isActive] [varchar](10) NOT NULL,
	[req_no] [int] NOT NULL,
	[remark1] [varchar](50) NULL,
	[remark2] [varchar](50) NULL,
	[remark3] [varchar](50) NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK_config_exam_vip] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[config_exam_vip] ON
INSERT [dbo].[config_exam_vip] ([id], [vip_code], [vip_name], [amt_lower], [amt_upper], [isActive], [req_no], [remark1], [remark2], [remark3], [creater], [create_time], [updater], [update_time]) VALUES (1, N'PTVIP', N'普通人员', CAST(0.00 AS Numeric(8, 2)), CAST(2000 AS Numeric(10, 0)), N'Y', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[config_exam_vip] ([id], [vip_code], [vip_name], [amt_lower], [amt_upper], [isActive], [req_no], [remark1], [remark2], [remark3], [creater], [create_time], [updater], [update_time]) VALUES (2, N'YBVIP', N'一般vip', CAST(2000.00 AS Numeric(8, 2)), CAST(4000 AS Numeric(10, 0)), N'Y', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
INSERT [dbo].[config_exam_vip] ([id], [vip_code], [vip_name], [amt_lower], [amt_upper], [isActive], [req_no], [remark1], [remark2], [remark3], [creater], [create_time], [updater], [update_time]) VALUES (3, N'TJVIP', N'特级vip', CAST(4000.00 AS Numeric(8, 2)), CAST(100000 AS Numeric(10, 0)), N'Y', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[config_exam_vip] OFF
