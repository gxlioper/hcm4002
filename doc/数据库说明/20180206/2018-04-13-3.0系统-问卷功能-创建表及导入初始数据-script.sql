USE [peis_kaifa3.0]
GO
/****** Object:  Table [dbo].[scale_result_list]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[scale_result_list](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](45) NOT NULL,
	[questionID] [int] NOT NULL,
	[answerID] [int] NOT NULL,
	[score] [int] NULL,
	[recordID] [int] NOT NULL,
 CONSTRAINT [PK_scale_result_list] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[scale_question_option]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[scale_question_option](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[questionID] [int] NOT NULL,
	[optionID] [int] NOT NULL,
 CONSTRAINT [PK_scale_question_option] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[scale_dict_question]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[scale_dict_question](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](100) NULL,
	[seqNo] [int] NULL,
	[scale_code] [varchar](20) NOT NULL,
	[imgName] [varchar](255) NULL,
 CONSTRAINT [PK_scale_dict_question] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'对应quest_dict_list表quest_sub_code字段' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'scale_dict_question', @level2type=N'COLUMN',@level2name=N'scale_code'
GO
/****** Object:  Table [dbo].[scale_dict_option]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[scale_dict_option](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](100) NULL,
	[seqNo] [int] NULL,
	[scale_code] [varchar](20) NOT NULL,
	[value] [int] NULL,
 CONSTRAINT [PK_scale_dict_option] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[scale_dict_appraise]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[scale_dict_appraise](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fromPoint] [int] NOT NULL,
	[toPoint] [int] NOT NULL,
	[Content] [varchar](255) NULL,
	[scale_code] [varchar](20) NOT NULL,
 CONSTRAINT [PK_scale_dict_appraise] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quest_exam_Record]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_exam_Record](
	[recId] [int] IDENTITY(1,1) NOT NULL,
	[peId] [varchar](10) NULL,
	[inDate] [datetime] NULL,
	[tranDate] [datetime] NULL,
	[traner] [int] NULL,
	[tranFlag] [char](1) NULL,
	[checkDate] [datetime] NULL,
	[checker] [int] NULL,
	[delFlag] [char](1) NULL,
	[custId] [varchar](10) NULL,
	[downFlag] [int] NULL,
	[quest_sub_code] [varchar](50) NULL,
	[score] [int] NULL,
	[appraise] [varchar](255) SPARSE  NULL,
 CONSTRAINT [PK_OHA_QUESTRECORD] PRIMARY KEY CLUSTERED 
(
	[recId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'记录ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'recId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'体检编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'peId'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开始体检时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'inDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'tranDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'上传人员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'traner'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0：开始答题；
   1：已结束答题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'tranFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'checkDate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'checker'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1：已删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'delFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下载标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'downFlag'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'问卷类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'quest_sub_code'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'总分数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'score'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'评估内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_Record', @level2type=N'COLUMN',@level2name=N'appraise'
GO
/****** Object:  Table [dbo].[quest_exam_List]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_exam_List](
	[questID] [int] IDENTITY(1,1) NOT NULL,
	[recordId] [varchar](10) NULL,
	[peId] [varchar](10) NULL,
	[resultId] [varchar](10) NULL,
	[questResult] [varchar](200) NULL,
	[resultId_itemID] [varchar](50) NULL,
	[resultId_itemID1] [varchar](50) NULL,
	[questResult_itemID] [varchar](50) NULL,
	[itemTitleID] [char](10) NULL,
	[itemIsMulsel] [int] NULL,
	[itemtextUnit] [varchar](200) NULL,
	[resultId_itemName] [varchar](100) NULL,
	[resultId_itemName1] [varchar](50) NULL,
	[questResult_itemName] [varchar](100) NULL,
 CONSTRAINT [PK_OHA_QUESTLIST] PRIMARY KEY CLUSTERED 
(
	[questID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'输入框的内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_exam_List', @level2type=N'COLUMN',@level2name=N'itemtextUnit'
GO
/****** Object:  Table [dbo].[quest_dict_title]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_dict_title](
	[titleID] [int] IDENTITY(1,1) NOT NULL,
	[titleName] [varchar](100) NULL,
	[supID] [varchar](10) NULL,
	[Level] [int] NULL,
	[seqNo] [int] NULL,
	[isVisable] [char](1) NULL,
	[quest_sub_code] [varchar](20) NOT NULL,
 CONSTRAINT [PK_OHA_DIC_TITLE] PRIMARY KEY CLUSTERED 
(
	[titleID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0：不显示
1：显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_title', @level2type=N'COLUMN',@level2name=N'isVisable'
GO
/****** Object:  Table [dbo].[quest_dict_rec]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_dict_rec](
	[quest_code] [varchar](12) NOT NULL,
	[class_name] [varchar](100) NOT NULL,
	[charging_item_code] [varchar](20) NULL,
	[note] [varchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[quest_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quest_dict_list_ext]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_dict_list_ext](
	[scale_code] [varchar](20) NOT NULL,
	[nameCn] [varchar](100) NULL,
	[nameEn] [varchar](20) NULL,
	[full_score] [int] NULL,
	[content] [varchar](255) NULL,
 CONSTRAINT [PK_quest_dict_list_ext] PRIMARY KEY CLUSTERED 
(
	[scale_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quest_dict_list]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_dict_list](
	[quest_sub_code] [varchar](20) NOT NULL,
	[quest_code] [varchar](12) NOT NULL,
	[quest_sub_name] [varchar](100) NOT NULL,
	[seq_no] [int] NULL,
	[note] [varchar](1000) NULL,
	[isVisable] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[quest_sub_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quest_dict_Item]    Script Date: 04/13/2018 19:23:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quest_dict_Item](
	[ItemId] [int] IDENTITY(1,1) NOT NULL,
	[ItemName] [varchar](100) NULL,
	[supItemId] [varchar](10) NULL,
	[Itemlevel] [int] NULL,
	[seqNo] [int] NULL,
	[titleId] [int] NULL,
	[isVisable] [char](1) NULL,
	[isMulSel] [char](1) NULL,
	[linkNo] [varchar](10) NULL,
	[textUnit] [varchar](200) NULL,
	[linkItem] [varchar](10) NULL,
	[linksubItem] [varchar](40) NULL,
	[ItemCode] [varchar](10) NULL,
	[Sex] [varchar](4) NULL,
	[IsDefault] [int] NOT NULL,
	[defaultResult] [varchar](20) NULL,
 CONSTRAINT [PK_OHA_DIC_ITEM] PRIMARY KEY CLUSTERED 
(
	[ItemId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0：不显示；
1：显示' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_Item', @level2type=N'COLUMN',@level2name=N'isVisable'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0：单选
1：多选 2.输入框  3. 文本类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_Item', @level2type=N'COLUMN',@level2name=N'isMulSel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联的问题为： (1)显示 （2）连接跳转 ' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_Item', @level2type=N'COLUMN',@level2name=N'linkItem'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'下级关联的代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_Item', @level2type=N'COLUMN',@level2name=N'linksubItem'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'默认结果' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'quest_dict_Item', @level2type=N'COLUMN',@level2name=N'defaultResult'
GO
/****** Object:  Default [DF__OHA_dic_I__isVis__473C8FC7]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_Item] ADD  CONSTRAINT [DF__OHA_dic_I__isVis__473C8FC7]  DEFAULT ('1') FOR [isVisable]
GO
/****** Object:  Default [DF__OHA_dic_I__isMul__4830B400]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_Item] ADD  CONSTRAINT [DF__OHA_dic_I__isMul__4830B400]  DEFAULT ('0') FOR [isMulSel]
GO
/****** Object:  Default [DF_OHA_dic_Item_linkItem]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_Item] ADD  CONSTRAINT [DF_OHA_dic_Item_linkItem]  DEFAULT ((0)) FOR [linkItem]
GO
/****** Object:  Default [DF_OHA_dic_Item_linksubItem]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_Item] ADD  CONSTRAINT [DF_OHA_dic_Item_linksubItem]  DEFAULT ((0)) FOR [linksubItem]
GO
/****** Object:  Default [DF__quest_dic__IsDef__3B80C458]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_Item] ADD  DEFAULT ((0)) FOR [IsDefault]
GO
/****** Object:  Default [DF_quest_dict_list_ext_full_score]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_list_ext] ADD  CONSTRAINT [DF_quest_dict_list_ext_full_score]  DEFAULT ((0)) FOR [full_score]
GO
/****** Object:  Default [DF__quest_dic__quest__61A66D40]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_dict_title] ADD  CONSTRAINT [DF__quest_dic__quest__61A66D40]  DEFAULT ((0)) FOR [quest_sub_code]
GO
/****** Object:  Default [DF_OHA_QuestList_itemIsMulsel]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_exam_List] ADD  CONSTRAINT [DF_OHA_QuestList_itemIsMulsel]  DEFAULT ((0)) FOR [itemIsMulsel]
GO
/****** Object:  Default [DF__OHA_Quest__tranF__6F1576F7]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_exam_Record] ADD  CONSTRAINT [DF__OHA_Quest__tranF__6F1576F7]  DEFAULT ('0') FOR [tranFlag]
GO
/****** Object:  Default [DF__OHA_Quest__downF__70099B30]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[quest_exam_Record] ADD  CONSTRAINT [DF__OHA_Quest__downF__70099B30]  DEFAULT ((0)) FOR [downFlag]
GO
/****** Object:  Default [DF_scale_dict_option_value]    Script Date: 04/13/2018 19:23:58 ******/
ALTER TABLE [dbo].[scale_dict_option] ADD  CONSTRAINT [DF_scale_dict_option_value]  DEFAULT ((0)) FOR [value]
GO
