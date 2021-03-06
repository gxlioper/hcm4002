INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'822', N'managersignshow.action', N'用户签名页面', N'2', NULL, N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'823', N'managersignupdate.action', N'用户签名保存', N'2', NULL, N'1', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'824', N'getuserPhoto.action', N'获取用户签名图片', N'2', NULL, N'1', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'825', N'examinfosignshow.action', N'体检用户签名页面', N'2', NULL, N'9', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'826', N'examsignupdate.action', N'体检用户签名保存', N'2', NULL, N'9', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'827', N'getexamPhotosgin.action', N'获取用户签名图片', N'2', NULL, N'9', N'2', N'1')
Alter table user_usr Alter column user_signature ntext
alter table user_usr add signpicpath varchar(500);
alter table batch add exam_date_end datetime null;

CREATE TABLE [dbo].[exam_info_sign](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,
	[examsign] [ntext] NOT NULL,
	[examsignpicpath] [varchar](500) null,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,

 CONSTRAINT [PK_exam_info_sign] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

