
/****** Object:  Table [dbo].[exam_flow_config]    Script Date: 03/16/2019 13:13:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_flow_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,
	[h0] [int] NOT NULL,
	[h0creater] [int] NULL,
	[h0date] [datetime] NULL,
	[h1] [int] NOT NULL,
	[h1creater] [int] NULL,
	[h1date] [datetime] NULL,
	[s0] [int] NOT NULL,
	[s0creater] [int] NULL,
	[s0date] [datetime] NULL,
	[s1] [int] NOT NULL,
	[s1creater] [int] NULL,
	[s1date] [datetime] NULL,
	[z] [int] NOT NULL,
	[zcreater] [int] NULL,
	[zdate] [datetime] NULL,
	[z0] [int] NOT NULL,
	[z0creater] [int] NULL,
	[z0date] [datetime] NULL,
	[z1] [int] NOT NULL,
	[z1creater] [int] NULL,
	[z1date] [datetime] NULL,
	[c0] [int] NOT NULL,
	[c0creater] [int] NULL,
	[c0date] [datetime] NULL,
	[c] [int] NOT NULL,
	[ccreater] [int] NULL,
	[cdate] [datetime] NULL,
	[f] [int] NOT NULL,
	[fcreater] [int] NULL,
	[fdate] [datetime] NULL,
	[p0] [int] NOT NULL,
	[p0creater] [int] NULL,
	[p0date] [datetime] NULL,
	[p1] [int] NOT NULL,
	[p1creater] [int] NULL,
	[p1date] [datetime] NULL,
	[e0] [int] NOT NULL,
	[e0creater] [int] NULL,
	[e0date] [datetime] NULL,
	[e1] [int] NOT NULL,
	[e1creater] [int] NULL,
	[e1date] [datetime] NULL,
	[m] [int] NOT NULL,
	[mcreater] [int] NULL,
	[mdate] [datetime] NULL,
	[v] [int] NOT NULL,
	[vcreater] [int] NULL,
	[vdate] [datetime] NULL,
	[vtcreater] [int] NULL,
	[vtdate] [datetime] NULL,
	[center_num] [varchar](20) NOT NULL,
	[f0] [int] NOT NULL,
	[f0creater] [int] NULL,
	[f0date] [datetime] NULL,
	[s] [int] NOT NULL,
	[sdate] [datetime] NULL,
	[t] [int] NOT NULL,
	[tcreater] [int] NULL,
	[tdate] [datetime] NULL,
	[edesc] [varchar](200) NULL,
	[c1] [int] NOT NULL,
	[c1creater] [int] NOT NULL,
	[c1date] [datetime] NULL,
 CONSTRAINT [PK_exam_flow_config] PRIMARY KEY CLUSTERED 
(
	[exam_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_h0]  DEFAULT ((0)) FOR [h0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_h1]  DEFAULT ((0)) FOR [h1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_s0]  DEFAULT ((0)) FOR [s0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_s1]  DEFAULT ((0)) FOR [s1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z_1]  DEFAULT ((0)) FOR [z]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z]  DEFAULT ((0)) FOR [z0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z1]  DEFAULT ((0)) FOR [z1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_c0]  DEFAULT ((0)) FOR [c0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_c]  DEFAULT ((0)) FOR [c]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_f]  DEFAULT ((0)) FOR [f]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_p0]  DEFAULT ((0)) FOR [p0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_p1]  DEFAULT ((0)) FOR [p1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_e0]  DEFAULT ((0)) FOR [e0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_e1]  DEFAULT ((0)) FOR [e1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_m]  DEFAULT ((0)) FOR [m]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_v]  DEFAULT ((0)) FOR [v]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_co__f0__5A5A5133]  DEFAULT ((0)) FOR [f0]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_con__s__5B4E756C]  DEFAULT ((0)) FOR [s]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_con__t__5F1F0650]  DEFAULT ((0)) FOR [t]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  DEFAULT ((0)) FOR [c1]
GO

ALTER TABLE [dbo].[exam_flow_config] ADD  DEFAULT ((0)) FOR [c1creater]
GO



/****** Object:  Table [dbo].[flow_config]    Script Date: 03/16/2019 13:11:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[flow_config](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[flow_code] [varchar](10) NOT NULL,
	[after_flow_code] [varchar](50) NULL,
	[flow_name] [varchar](50) NOT NULL,
	[is_active] [char](1) NOT NULL,
	[seqNum] [int] NOT NULL,
 CONSTRAINT [PK_flow_config] PRIMARY KEY CLUSTERED 
(
	[flow_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'任务编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'flow_config', @level2type=N'COLUMN',@level2name=N'flow_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'后置任务编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'flow_config', @level2type=N'COLUMN',@level2name=N'after_flow_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'对应体检信息状态码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'flow_config', @level2type=N'COLUMN',@level2name=N'flow_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否有效 Y有效 N无效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'flow_config', @level2type=N'COLUMN',@level2name=N'is_active'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顺序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'flow_config', @level2type=N'COLUMN',@level2name=N'seqNum'
GO
SET IDENTITY_INSERT [dbo].[flow_config] ON
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (11, N'c', N'f', N'总检审核', N'Y', 8)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (22, N'-c', N'c', N'取消审核', N'Y', 8)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (30, N'c0', N'c', N'报告发送审核', N'Y', 19)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (31, N'-c0', N'c0', N'报告发送审核撤销', N'Y', 20)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (52, N'c1', NULL, N'准备审核', N'Y', 8)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (49, N'dy', NULL, N'打印室核收记录', N'Y', 7)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (16, N'e0', N'e1', N'报告领取阶段', N'Y', 12)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (28, N'-e0', N'e0', N'报告领取阶段撤销', N'Y', 17)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (17, N'e1', NULL, N'报告领取完成', N'Y', 13)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (37, N'-e1', N'e1', N'报告领取完成撤销', N'Y', 26)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (12, N'f', N'p0', N'总检复核', N'Y', 9)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (23, N'-f', N'f', N'取消终审', N'Y', 9)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (45, N'f0', N'f', N'准备复审', N'Y', 9)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (34, N'fe0', N'fe1', N'胶片发放阶段', N'Y', 23)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (35, N'fe1', NULL, N'胶片发放完成', N'Y', 24)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (38, N'-fe1', N'fe1', N'胶片发放完成撤销', N'Y', 27)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (32, N'fs0', N'fs1', N'胶片核收阶段', N'Y', 21)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (33, N'fs1', N'fe0', N'胶片核收完成', N'Y', 22)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (36, N'-fs1', N'fs1', N'胶片核收完成撤销', N'Y', 25)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (1, N'h0', N'h1', N'导检单核收阶段', N'Y', 1)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (4, N'h1', N's0', N'导检单上传完成', N'Y', 2)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (41, N'-h1', N'h1', N'导检单核收完成撤销', N'Y', 30)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (25, N'm', NULL, N'报告解读', N'Y', 14)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (14, N'p0', N'p1', N'报告打印', N'Y', 10)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (29, N'-p0', N'p0', N'报告打印撤销', N'Y', 18)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (15, N'p1', N'e0', N'报告打印完成', N'Y', 11)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (39, N'-p1', N'p1', N'报告打印完成撤销', N'Y', 28)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (8, N's0', N's1', N'整单核收阶段', N'Y', 3)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (27, N'-s0', N's0', N'整单处理阶段撤销', N'Y', 16)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (10, N's1', N'c', N'整单上传完成', N'Y', 4)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (40, N'-s1', N's1', N'整单处理完成撤销', N'Y', 29)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (46, N't', N'', N'设置特殊通知', N'Y', 6)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (47, N'-t', N'', N'取消特殊通知', N'Y', 6)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (26, N'v', NULL, N'回访', N'Y', 15)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (44, N'vt', N'v', N'分配回访', N'Y', 33)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (43, N'-vt', N'vt', N'撤销分配回访', N'Y', 32)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (24, N'z', N'h1', N'报告室核收', N'Y', 5)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (42, N'-z', N'z', N'报告室核收撤销', N'Y', 31)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (19, N'z0', N'h1', N'准备总检', N'Y', 6)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (20, N'z1', N'z0', N'总检完成', N'Y', 7)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (21, N'-z1', N'z1', N'取消总检', N'Y', 7)
INSERT [dbo].[flow_config] ([id], [flow_code], [after_flow_code], [flow_name], [is_active], [seqNum]) VALUES (48, N'zd', NULL, N'整单室核收记录', N'Y', 5)
SET IDENTITY_INSERT [dbo].[flow_config] OFF
/****** Object:  Table [dbo].[exam_flow_remak]    Script Date: 03/16/2019 13:11:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[exam_flow_remak](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,
	[dep_id] [int] NOT NULL,
	[remark] [varchar](500) NOT NULL,
	[remark_user] [int] NOT NULL,
	[remark_time] [datetime] NOT NULL,
 CONSTRAINT [PK_exam_flow_remak] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[exam_flow_log]    Script Date: 03/16/2019 13:11:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[exam_flow_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](20) NOT NULL,
	[flow_code] [varchar](10) NOT NULL,
	[sendcreater] [int] NOT NULL,
	[senddate] [datetime] NOT NULL,
	[acccreater] [int] NULL,
	[flow_type] [int] NOT NULL,
	[remark] [varchar](500) NULL,
	[notes] [varchar](500) NULL,
	[center_num] [varchar](20) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[exam_flow_log] ON

/****** Object:  Default [DF_exam_flow_config_h0]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_h0]  DEFAULT ((0)) FOR [h0]
GO
/****** Object:  Default [DF_exam_flow_config_h1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_h1]  DEFAULT ((0)) FOR [h1]
GO
/****** Object:  Default [DF_exam_flow_config_s0]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_s0]  DEFAULT ((0)) FOR [s0]
GO
/****** Object:  Default [DF_exam_flow_config_s1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_s1]  DEFAULT ((0)) FOR [s1]
GO
/****** Object:  Default [DF_exam_flow_config_z_1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z_1]  DEFAULT ((0)) FOR [z]
GO
/****** Object:  Default [DF_exam_flow_config_z]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z]  DEFAULT ((0)) FOR [z0]
GO
/****** Object:  Default [DF_exam_flow_config_z1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_z1]  DEFAULT ((0)) FOR [z1]
GO
/****** Object:  Default [DF_exam_flow_config_c0]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_c0]  DEFAULT ((0)) FOR [c0]
GO
/****** Object:  Default [DF_exam_flow_config_c]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_c]  DEFAULT ((0)) FOR [c]
GO
/****** Object:  Default [DF_exam_flow_config_f]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_f]  DEFAULT ((0)) FOR [f]
GO
/****** Object:  Default [DF_exam_flow_config_p0]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_p0]  DEFAULT ((0)) FOR [p0]
GO
/****** Object:  Default [DF_exam_flow_config_p1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_p1]  DEFAULT ((0)) FOR [p1]
GO
/****** Object:  Default [DF_exam_flow_config_e0]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_e0]  DEFAULT ((0)) FOR [e0]
GO
/****** Object:  Default [DF_exam_flow_config_e1]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_e1]  DEFAULT ((0)) FOR [e1]
GO
/****** Object:  Default [DF_exam_flow_config_m]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_m]  DEFAULT ((0)) FOR [m]
GO
/****** Object:  Default [DF_exam_flow_config_v]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF_exam_flow_config_v]  DEFAULT ((0)) FOR [v]
GO
/****** Object:  Default [DF__exam_flow_co__f0__5A5A5133]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_co__f0__5A5A5133]  DEFAULT ((0)) FOR [f0]
GO
/****** Object:  Default [DF__exam_flow_con__s__5B4E756C]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_con__s__5B4E756C]  DEFAULT ((0)) FOR [s]
GO
/****** Object:  Default [DF__exam_flow_con__t__5F1F0650]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  CONSTRAINT [DF__exam_flow_con__t__5F1F0650]  DEFAULT ((0)) FOR [t]
GO
/****** Object:  Default [DF__exam_flow_co__c1__09D45A2B]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  DEFAULT ((0)) FOR [c1]
GO
/****** Object:  Default [DF__exam_flow__c1cre__0AC87E64]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_config] ADD  DEFAULT ((0)) FOR [c1creater]
GO
/****** Object:  Default [DF_exam_flow_log_flow_type]    Script Date: 03/16/2019 13:11:34 ******/
ALTER TABLE [dbo].[exam_flow_log] ADD  CONSTRAINT [DF_exam_flow_log_flow_type]  DEFAULT ((1)) FOR [flow_type]
GO
