
/****** Object:  Table [dbo].[data_base_config]    Script Date: 01/02/2020 11:50:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[data_base_config](  --体检车与主系统数据导入导出配置表
	[database_url] [varchar](50) NOT NULL,  --ip地址
	[database_port] [varchar](50) NOT NULL, --端口号
	[database_uame] [varchar](50) NOT NULL, --数据库名称
	[username] [varchar](50) NOT NULL,      --登录名称
	[password] [varchar](50) NOT NULL,      --登录密码
	[type] [int] NOT NULL,                   --配置类型 0表示数据库配置、1表示ftp配置
  CONSTRAINT [PK_data_base_config] PRIMARY KEY CLUSTERED 
(
	[type] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[data_base_config] ([database_url], [database_port], [database_uame], [username], [password], [type]) VALUES (N'127.0.0.1', N'1433', N'PEIS', N'sa', N'123456', 0)
INSERT [dbo].[data_base_config] ([database_url], [database_port], [database_uame], [username], [password], [type]) VALUES (N'127.0.0.1', N'21', N'', N'pacs', N'pacs', 1)
