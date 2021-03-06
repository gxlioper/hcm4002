
GO
/****** Object:  Table [dbo].[sys_survey_question_relation]    Script Date: 02/20/2017 09:34:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sys_survey_question_relation](
	[objectId] [int] IDENTITY(1,1) NOT NULL,
	[survey_id] [varchar](32) NULL,
	[question_id] [varchar](32) NULL,
	[code] [varchar](200) NULL,
 CONSTRAINT [PK_sys_survey_question_relation] PRIMARY KEY CLUSTERED 
(
	[objectId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sys_survey_question]    Script Date: 02/20/2017 09:34:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sys_survey_question](
	[name] [varchar](32) NULL,
	[objectId] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](10) NOT NULL,
	[qust_type_id] [varchar](32) NOT NULL,
	[sex] [varchar](1) NOT NULL,
	[age] [int] NOT NULL,
	[marriageState] [varchar](1) NOT NULL,
	[answer_type] [varchar](1) NULL,
	[delete_flg] [varchar](1) NULL,
	[create_date] [datetime] NULL,
	[creater_id] [varchar](32) NULL,
	[update_date] [datetime] NULL,
	[updater_id] [varchar](32) NULL,
	[delete_date] [datetime] NULL,
	[deleter_id] [varchar](32) NULL,
	[content] [varchar](200) NULL,
	[exam_center_id] [varchar](32) NULL,
	[age_to] [int] NULL,
	[question_level] [varchar](1) NULL,
 CONSTRAINT [PK_sys_survey_question] PRIMARY KEY CLUSTERED 
(
	[objectId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sys_survey]    Script Date: 02/20/2017 09:34:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sys_survey](
	[objectId] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](10) NULL,
	[name] [varchar](20) NULL,
	[enable] [varchar](1) NOT NULL,
	[delete_flg] [varchar](1) NULL,
	[create_date] [datetime] NULL,
	[creater_id] [varchar](32) NULL,
	[update_date] [datetime] NULL,
	[updater_id] [varchar](32) NULL,
	[delete_date] [datetime] NULL,
	[deleter_id] [varchar](32) NULL,
	[exam_center_id] [varchar](32) NULL,
	[user_id] [varchar](32) NULL,
	[person_name] [varchar](15) NULL,
	[sex] [varchar](2) NOT NULL,
	[age] [int] NOT NULL,
 CONSTRAINT [PK_sys_survey] PRIMARY KEY CLUSTERED 
(
	[objectId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sys_question_options]    Script Date: 02/20/2017 09:34:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sys_question_options](
	[objectId] [int] IDENTITY(1,1) NOT NULL,
	[quest_id] [varchar](32) NOT NULL,
	[code] [varchar](10) NOT NULL,
	[content] [varchar](60) NULL,
	[delete_flg] [varchar](32) NULL,
	[create_date] [datetime] NULL,
	[creater_id] [varchar](50) NULL,
	[update_date] [datetime] NULL,
	[updater_id] [varchar](50) NULL,
	[delete_date] [datetime] NULL,
	[deleter_id] [varchar](50) NULL,
	[next_quest_code] [varchar](32) NULL,
	[sub_quest_id] [varchar](32) NULL,
 CONSTRAINT [PK_sys_question_options] PRIMARY KEY CLUSTERED 
(
	[objectId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sys_question_items]    Script Date: 02/20/2017 09:34:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sys_question_items](
	[objectId] [int] IDENTITY(1,1) NOT NULL,
	[quest_option_id] [varchar](32) NOT NULL,
	[charge_items_id] [varchar](32) NOT NULL,
	[weight_value] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
