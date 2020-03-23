
GO
/****** Object:  Table [dbo].[crm_company_contacts]    Script Date: 06/22/2017 21:18:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[crm_company_contacts](
	[id] [varchar](50) NOT NULL,
	[company_id] [int] NOT NULL,
	[contacts_name] [varchar](50) NOT NULL,
	[position] [varchar](10) NOT NULL,
	[important_level] [varchar](10) NOT NULL,
	[phone] [varchar](11) NULL,
	[telephone] [varchar](15) NULL,
	[email] [varchar](50) NULL,
	[id_num] [varchar](50) NULL,
	[personal_interests] [varchar](500) NULL,
	[remarke] [varchar](500) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
