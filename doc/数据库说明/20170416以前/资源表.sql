USE [PEIS]
GO
/****** Object:  Table [dbo].[WEB_RESRELATIONSHIP]    Script Date: 12/12/2016 17:17:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WEB_RESRELATIONSHIP](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[res_code] [varchar](10) NOT NULL,
	[res_type] [varchar](10) NOT NULL,
	[datavalue] [varchar](500) NOT NULL,
	[remark] [varchar](100) NULL,
	[create_time] [datetime] NOT NULL,
	[creater] [int] NOT NULL,
	[updater] [int] NOT NULL,
	[update_time] [datetime] NOT NULL,
	[userorroleid] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源表code' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESRELATIONSHIP', @level2type=N'COLUMN',@level2name=N'res_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型：1表示角色类，2表示用户类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESRELATIONSHIP', @level2type=N'COLUMN',@level2name=N'res_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源值' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESRELATIONSHIP', @level2type=N'COLUMN',@level2name=N'datavalue'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'角色id或者人员id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESRELATIONSHIP', @level2type=N'COLUMN',@level2name=N'userorroleid'
GO
/****** Object:  Table [dbo].[WEB_RESOURCE]    Script Date: 12/12/2016 17:17:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WEB_RESOURCE](
	[code] [varchar](10) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[type] [varchar](10) NOT NULL,
	[data_type] [varchar](10) NOT NULL,
	[notice] [ntext] NOT NULL,
	[example] [varchar](100) NULL,
	[examplenote] [ntext] NULL,
	[remark] [varchar](100) NULL,
	[remark1] [varchar](100) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'适用类型 1表示角色类，2表示用户类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据类型：1 数值；2字符' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'data_type'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源定义' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'notice'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源例子' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'example'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'资源例子说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_RESOURCE', @level2type=N'COLUMN',@level2name=N'examplenote'
GO
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS001', N'总检人员审核权限', N'2', N'1', N'赋予人员是否有总检审核的资源，数值型。此资源需要给人员赋予总检权限才有作用。1表示拥有审核的权限。其他设置均无效。设置时候需要输入数字1.', N'输入：1', N'表示拥有总检审核资源', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS002', N'报告打印次数限制', N'1', N'1', N'赋予角色是否有打印报表的资源，数值型。0表示无打印权限，1表示有一次打印报告权限，2表示有多次打印权限', N'输入：2', N'表示角色有多次能打印报告的权限', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS003', N'登记台加项可输入最小折扣率', N'2', N'1', N'赋予操作人员在登记台可输入最小的折扣率，数值型。最大为10，最小为0.输入值可以是整数或者可带两位小数', N'输入：7.90', N'表示可打折的范围为7.90-10', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS004', N'套餐维护里可输入最小折扣率', N'2', N'1', N'赋予操作人员在套餐维护里面可输入最小的折扣率，数值型。最大为10，最小为0.输入值可以是整数或者可带两位小数', N'输入：5', N'表示可打折的范围为5-10', NULL, NULL)
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS005', N'赋予操作人员可查询特殊人员完整信息', N'2', N'1', N'赋予操作员可查询特殊人员完整信息的资源，数值型。1表示有权限查询特殊人员的完整信息。', N'输入：1', N'表示拥有查询特殊人员完整信息的权限', NULL, NULL)
