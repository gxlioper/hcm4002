alter table department_dep add dict_num varchar(50) --执行科室编码
alter table department_vs_center add dict_num varchar(50) --执行科室编码
alter table user_usr add dict_num varchar(50) --执行科室编码

/****** Object:  Table [dbo].[department_dict]    Script Date: 11/08/2019 14:52:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[department_dict](    --执行科室表
	[id] [int] IDENTITY(1,1) NOT NULL,   
	[dept_code] [varchar](20) NOT NULL,  --科室编码
	[dept_name] [varchar](50) NOT NULL,  --科室名称
	[dept_class] [varchar](10) NULL,     --科室类别
	[input_code] [varchar](20) NULL,     --助记符
	[seq_code] [int] NULL,               --顺序码
	[is_active] [varchar](10) NOT NULL,  --是否有效
	[center_num] [varchar](50) NOT NULL, --体检中心编码
	[remark] [varchar](100) NULL,        --备注
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK_department_dict] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


