
alter table examinfo_disease add disease_description varchar(3000) --增加病理解释字段


ALTER TABLE [dbo].[exam_summary] ADD [report_class] [int] NOT NULL default(0);
ALTER TABLE [dbo].[exam_summary] ADD [report_class_user] [int];
ALTER TABLE [dbo].[exam_summary] ADD [report_class_date] [datetime] ;



INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'体检中心', N'EXAM_SUMMARY_STYLE', N'2', N'Y', N'总检室总监模式，1 普通总检模式，2 180总检模式')




/****** Object:  Table [dbo].[health_risk_morbidity]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_morbidity](  --健康风险评估疾病总分对应的实际发病率
	[id] [int] IDENTITY(1,1) NOT NULL,
	[health_risk_id] [int] NOT NULL,         --评估疾病ID
	[points] [int] NOT NULL,                 --总分数
	[sex] [varchar](50) NOT NULL,            --性别
	[morbidity] [int] NULL,                  --发病率
	[condition] [varchar](10) NULL,          --判断条件
 CONSTRAINT [PK_health_risk_morbidity] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_morbidity] ON
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (2, 1, 1, N'男', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (3, 1, 2, N'男', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (4, 1, 3, N'男', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (5, 1, 4, N'男', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (6, 1, 5, N'男', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (7, 1, 6, N'男', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (8, 1, 7, N'男', 6, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (9, 1, 8, N'男', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (10, 1, 9, N'男', 8, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (11, 1, 10, N'男', 10, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (12, 1, 11, N'男', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (13, 1, 12, N'男', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (14, 1, 13, N'男', 15, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (15, 1, 14, N'男', 17, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (16, 1, 15, N'男', 20, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (17, 1, 16, N'男', 22, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (18, 1, 17, N'男', 26, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (19, 1, 18, N'男', 29, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (20, 1, 19, N'男', 33, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (21, 1, 20, N'男', 37, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (22, 1, 21, N'男', 42, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (23, 1, 22, N'男', 47, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (24, 1, 23, N'男', 52, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (25, 1, 24, N'男', 57, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (26, 1, 25, N'男', 63, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (27, 1, 26, N'男', 68, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (28, 1, 27, N'男', 74, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (29, 1, 28, N'男', 79, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (30, 1, 29, N'男', 84, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (31, 1, 30, N'男', 88, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (32, 1, 1, N'女', 1, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (33, 1, 2, N'女', 1, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (34, 1, 3, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (35, 1, 4, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (36, 1, 5, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (38, 1, 6, N'女', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (39, 1, 7, N'女', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (40, 1, 8, N'女', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (41, 1, 9, N'女', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (42, 1, 10, N'女', 6, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (43, 1, 11, N'女', 8, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (44, 1, 12, N'女', 9, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (45, 1, 13, N'女', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (46, 1, 14, N'女', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (47, 1, 15, N'女', 16, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (48, 1, 16, N'女', 18, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (49, 1, 17, N'女', 23, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (50, 1, 18, N'女', 27, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (51, 1, 19, N'女', 32, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (52, 1, 20, N'女', 37, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (53, 1, 21, N'女', 43, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (54, 1, 22, N'女', 50, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (55, 1, 23, N'女', 57, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (56, 1, 24, N'女', 64, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (57, 1, 25, N'女', 71, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (58, 1, 26, N'女', 78, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (60, 1, 27, N'女', 84, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (61, 2, -1, N'男', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (62, 2, 0, N'男', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (63, 2, 1, N'男', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (65, 2, 2, N'男', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (66, 2, 3, N'男', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (67, 2, 4, N'男', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (68, 2, 5, N'男', 8, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (69, 2, 6, N'男', 10, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (70, 2, 7, N'男', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (71, 2, 8, N'男', 16, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (72, 2, 9, N'男', 20, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (73, 2, 10, N'男', 25, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (74, 2, 11, N'男', 31, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (75, 2, 12, N'男', 37, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (76, 2, 13, N'男', 45, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (77, 2, 14, N'男', 53, N'>=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (78, 2, -2, N'女', 1, N'<=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (79, 2, -1, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (80, 2, 0, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (81, 2, 1, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (82, 2, 2, N'女', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (83, 2, 3, N'女', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (84, 2, 4, N'女', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (86, 2, 5, N'女', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (87, 2, 6, N'女', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (88, 2, 7, N'女', 6, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (89, 2, 8, N'女', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (90, 2, 9, N'女', 8, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (91, 2, 10, N'女', 10, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (92, 2, 11, N'女', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (93, 2, 12, N'女', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (94, 2, 13, N'女', 15, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (95, 2, 14, N'女', 18, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (96, 2, 15, N'女', 20, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (97, 2, 16, N'女', 24, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (98, 2, 17, N'女', 27, N'>=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (99, 3, -3, N'男', 1, N'<=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (100, 3, -2, N'男', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (101, 3, -1, N'男', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (102, 3, 0, N'男', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (103, 3, 1, N'男', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (104, 3, 2, N'男', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (105, 3, 3, N'男', 6, N'=')
GO
print 'Processed 100 total records'
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (106, 3, 4, N'男', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (107, 3, 5, N'男', 9, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (108, 3, 6, N'男', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (109, 3, 7, N'男', 14, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (110, 3, 8, N'男', 18, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (111, 3, 9, N'男', 22, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (112, 3, 10, N'男', 27, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (113, 3, 11, N'男', 33, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (114, 3, 12, N'男', 40, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (115, 3, 13, N'男', 45, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (116, 3, 14, N'男', 56, N'>=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (117, 3, -2, N'女', 1, N'<=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (118, 3, -1, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (119, 3, 0, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (120, 3, 1, N'女', 2, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (121, 3, 2, N'女', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (122, 3, 3, N'女', 3, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (123, 3, 4, N'女', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (124, 3, 5, N'女', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (125, 3, 6, N'女', 6, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (126, 3, 7, N'女', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (127, 3, 8, N'女', 8, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (128, 3, 9, N'女', 9, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (129, 3, 10, N'女', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (130, 3, 11, N'女', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (131, 3, 12, N'女', 15, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (132, 3, 13, N'女', 17, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (133, 3, 14, N'女', 20, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (134, 3, 15, N'女', 24, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (135, 3, 16, N'女', 27, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (136, 3, 17, N'女', 32, N'>=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (137, 4, 10, N'全部', 3, N'<=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (138, 4, 11, N'全部', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (139, 4, 12, N'全部', 4, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (140, 4, 13, N'全部', 5, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (141, 4, 14, N'全部', 6, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (142, 4, 15, N'全部', 7, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (143, 4, 16, N'全部', 9, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (144, 4, 17, N'全部', 11, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (145, 4, 18, N'全部', 13, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (146, 4, 19, N'全部', 15, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (147, 4, 20, N'全部', 18, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (148, 4, 21, N'全部', 21, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (149, 4, 22, N'全部', 25, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (150, 4, 23, N'全部', 29, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (151, 4, 24, N'全部', 33, N'=')
INSERT [dbo].[health_risk_morbidity] ([id], [health_risk_id], [points], [sex], [morbidity], [condition]) VALUES (152, 4, 25, N'全部', 35, N'>=')
SET IDENTITY_INSERT [dbo].[health_risk_morbidity] OFF
/****** Object:  Table [dbo].[health_risk_item_examresult]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_item_examresult](  --健康风险评估所需要提取的项目检查结果表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,                     --体检号
	[risk_item_code] [varchar](50) NOT NULL,       --健康风险评估项目编码
	[exam_result] [varchar](500) NOT NULL,         --检查结果
 CONSTRAINT [PK_health_risk_item_examresult] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[health_risk_item_contrast]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_item_contrast](  --健康风险评估与体检系统项目关系对照表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[risk_item_code] [varchar](50) NOT NULL,     --健康风险评估项目编码
	[item_code] [varchar](50) NOT NULL,          --影像项目对应收费项目编码，普通和检验对应检查项目编码
	[item_name] [varchar](50) NOT NULL,          --项目名称
 CONSTRAINT [PK_health_risk_item_contrast] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_item_contrast] ON
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (4, N'LDL-C', N'JC000158', N'胆固醇')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (5, N'PDM', N'JC000433', N'父母糖尿病史')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (6, N'HDL-C', N'JC000156', N'高密度脂蛋白')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (7, N'TG', N'JC000155', N'甘油三酯')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (8, N'GLU', N'JC000149', N'空腹血糖')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (9, N'BMI', N'WL003', N'体重指数')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (10, N'SBP', N'WL004', N'收缩压')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (11, N'DBP', N'WL005', N'舒张压')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (12, N'DM', N'JC000443', N'糖尿病史')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (13, N'AF', N'SF000090', N'心房颤动')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (14, N'CVD', N'JC000443', N'心血管疾病史')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (15, N'CIGS', N'E0000003', N'吸烟史')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (17, N'CHOL', N'JC000154', N'总胆固醇')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (18, N'LVH', N'SF000090', N'左心室肥厚')
INSERT [dbo].[health_risk_item_contrast] ([id], [risk_item_code], [item_code], [item_name]) VALUES (19, N'HTM', N'E0000003', N'服药史')
SET IDENTITY_INSERT [dbo].[health_risk_item_contrast] OFF
/****** Object:  Table [dbo].[health_risk_examinfo_item]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_examinfo_item](  --健康风险评估项目得分
	[id] [int] IDENTITY(1,1) NOT NULL,
	[risk_examinfo_id] [int] NOT NULL,           --健康风险评估结果ID
	[risk_item_code] [varchar](50) NOT NULL,     --健康风险评估项目编码
	[point] [int] NOT NULL,                      --分数
 CONSTRAINT [PK_health_risk_examinfo_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[health_risk_examinfo]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_examinfo](--健康风险评估疾病风险结果表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_num] [varchar](50) NOT NULL,    --体检号
	[health_risk_id] [int] NOT NULL,      --评估疾病ID
	[points] [int] NOT NULL,              --分数
	[picture_path] [varchar](500) NULL,   --生成柱状图路径
	[is_success] [int] NOT NULL,          --疾病评估状态  0表示成功，1表示失败
	[cause_failure] [varchar](500) NULL,  --失败原因
	[creater] [int] NULL,                 --创建人
	[create_time] [datetime] NULL,        --创建时间
	[reality_morbidity] [int] NULL,       --实际发病率
	[average_morbidity] [decimal](18, 1) NULL,  --平均发病率
	[hard_morbidity] [decimal](18, 1) NULL,     --硬终点发病率（心血管）
	[low_morbidity] [decimal](18, 1) NULL,      --低风险发病率（心血管）
	[is_active] [varchar](10) NOT NULL,         --是否有效
 CONSTRAINT [PK_health_risk_examinfo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[health_risk_assessment_morbidity]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_assessment_morbidity](  --健康风险评估疾病对应的平均发病率
	[id] [int] IDENTITY(1,1) NOT NULL,
	[health_risk_id] [int] NOT NULL,                  --评估疾病ID
	[sex] [varchar](50) NOT NULL,                     --性别
	[min_age] [int] NOT NULL,                         --最小年龄
	[max_age] [int] NOT NULL,                         --最大年龄
	[average_morbidity] [decimal](18, 1) NOT NULL,    --平均发病率
	[hard_morbidity] [decimal](18, 1) NULL,           --硬终点发病率（心血管）
	[low_morbidity] [decimal](18, 1) NULL,            --低风险发病率（心血管）
 CONSTRAINT [PK_health_risk_assessment_morbidity] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_assessment_morbidity] ON
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (1, 1, N'男', 0, 59, CAST(5.9 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (2, 1, N'男', 60, 64, CAST(7.8 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (3, 1, N'男', 65, 69, CAST(11.0 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (4, 1, N'男', 70, 74, CAST(13.7 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (5, 1, N'男', 75, 79, CAST(18.0 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (6, 1, N'男', 80, 200, CAST(22.3 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (7, 1, N'女', 0, 59, CAST(3.0 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (8, 1, N'女', 60, 64, CAST(4.7 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (10, 1, N'女', 65, 69, CAST(7.2 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (11, 1, N'女', 70, 74, CAST(10.9 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (12, 1, N'女', 75, 79, CAST(15.5 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (13, 1, N'女', 80, 200, CAST(23.9 AS Decimal(18, 1)), NULL, NULL)
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (14, 2, N'男', 0, 34, CAST(3.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (15, 2, N'男', 35, 39, CAST(5.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (16, 2, N'男', 40, 44, CAST(7.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (17, 2, N'男', 45, 49, CAST(11.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (18, 2, N'男', 50, 54, CAST(14.0 AS Decimal(18, 1)), CAST(10.0 AS Decimal(18, 1)), CAST(6.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (19, 2, N'男', 55, 59, CAST(16.0 AS Decimal(18, 1)), CAST(13.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (20, 2, N'男', 60, 64, CAST(21.0 AS Decimal(18, 1)), CAST(20.0 AS Decimal(18, 1)), CAST(9.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (21, 2, N'男', 65, 69, CAST(25.0 AS Decimal(18, 1)), CAST(22.0 AS Decimal(18, 1)), CAST(11.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (22, 2, N'男', 70, 200, CAST(30.0 AS Decimal(18, 1)), CAST(25.0 AS Decimal(18, 1)), CAST(14.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (23, 2, N'女', 0, 34, CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (24, 2, N'女', 35, 39, CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (25, 2, N'女', 40, 44, CAST(2.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (26, 2, N'女', 45, 49, CAST(5.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (27, 2, N'女', 50, 54, CAST(8.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)), CAST(5.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (28, 2, N'女', 55, 59, CAST(12.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (29, 2, N'女', 60, 64, CAST(12.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (30, 2, N'女', 65, 69, CAST(13.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (31, 2, N'女', 70, 200, CAST(14.0 AS Decimal(18, 1)), CAST(11.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (32, 3, N'男', 0, 34, CAST(3.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (33, 3, N'男', 35, 39, CAST(5.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (34, 3, N'男', 40, 44, CAST(7.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (35, 3, N'男', 45, 49, CAST(11.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(4.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (36, 3, N'男', 50, 54, CAST(14.0 AS Decimal(18, 1)), CAST(10.0 AS Decimal(18, 1)), CAST(6.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (37, 3, N'男', 55, 59, CAST(16.0 AS Decimal(18, 1)), CAST(13.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (38, 3, N'男', 60, 64, CAST(21.0 AS Decimal(18, 1)), CAST(20.0 AS Decimal(18, 1)), CAST(9.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (39, 3, N'男', 65, 69, CAST(25.0 AS Decimal(18, 1)), CAST(20.0 AS Decimal(18, 1)), CAST(11.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (40, 3, N'男', 70, 200, CAST(30.0 AS Decimal(18, 1)), CAST(25.0 AS Decimal(18, 1)), CAST(14.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (41, 3, N'女', 0, 34, CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (42, 3, N'女', 35, 39, CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (43, 3, N'女', 40, 44, CAST(2.0 AS Decimal(18, 1)), CAST(1.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (44, 3, N'女', 45, 49, CAST(5.0 AS Decimal(18, 1)), CAST(2.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (45, 3, N'女', 50, 54, CAST(8.0 AS Decimal(18, 1)), CAST(3.0 AS Decimal(18, 1)), CAST(5.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (46, 3, N'女', 55, 59, CAST(12.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)), CAST(7.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (47, 3, N'女', 60, 64, CAST(12.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (48, 3, N'女', 65, 69, CAST(13.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
INSERT [dbo].[health_risk_assessment_morbidity] ([id], [health_risk_id], [sex], [min_age], [max_age], [average_morbidity], [hard_morbidity], [low_morbidity]) VALUES (49, 3, N'女', 70, 200, CAST(14.0 AS Decimal(18, 1)), CAST(11.0 AS Decimal(18, 1)), CAST(8.0 AS Decimal(18, 1)))
SET IDENTITY_INSERT [dbo].[health_risk_assessment_morbidity] OFF
/****** Object:  Table [dbo].[health_risk_assessment_item_condition]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_assessment_item_condition]( --健康风险评估疾病项目对应判断条件分数
	[id] [int] IDENTITY(1,1) NOT NULL,
	[health_item_id] [int] NOT NULL,            --评估疾病项目ID
	[points] [int] NOT NULL,                    --分数
	[min_value] [int] NULL,                     --最小值 >=
	[max_value] [int] NULL,                     --最大值 <=
	[yes_or_not] [varchar](50) NULL,            --文字包含关键字 in
	[min_value1] [int] NULL,                    --最小值1 >=
	[max_value1] [int] NULL,                    --最大值1 <=
 CONSTRAINT [PK_health_risk_assessment_item_condition] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_assessment_item_condition] ON
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (1, 1, 0, 0, 56, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (2, 1, 1, 57, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (3, 1, 2, 60, 62, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (4, 1, 3, 63, 65, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (5, 1, 4, 66, 68, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (6, 1, 5, 69, 72, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (7, 1, 6, 73, 75, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (8, 1, 7, 76, 78, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (9, 1, 8, 79, 81, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (10, 1, 9, 82, 84, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (11, 1, 10, 85, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (12, 2, 0, 97, 105, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (13, 2, 1, 106, 115, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (14, 2, 2, 116, 125, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (15, 2, 3, 126, 135, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (16, 2, 4, 136, 145, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (17, 2, 5, 146, 155, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (18, 2, 6, 156, 165, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (19, 2, 7, 166, 175, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (20, 2, 8, 176, 185, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (21, 2, 9, 186, 195, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (22, 2, 10, 196, 205, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (23, 3, 0, 97, 105, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (24, 3, 1, 106, 112, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (25, 3, 2, 113, 117, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (26, 3, 3, 118, 123, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (27, 3, 4, 124, 129, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (28, 3, 5, 130, 135, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (29, 3, 6, 136, 142, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (30, 3, 7, 143, 150, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (31, 3, 8, 151, 161, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (32, 3, 9, 162, 176, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (33, 3, 10, 177, 205, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (35, 4, 2, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (37, 5, 4, NULL, NULL, N'心血管', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (39, 6, 3, NULL, NULL, N'吸烟,是', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (41, 7, 4, NULL, NULL, N'心房颤动', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (43, 8, 5, NULL, NULL, N'左心室肥厚', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (44, 9, 0, 0, 56, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (45, 9, 1, 57, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (46, 9, 2, 60, 62, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (47, 9, 3, 63, 64, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (48, 9, 4, 65, 67, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (49, 9, 5, 68, 70, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (50, 9, 6, 71, 73, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (51, 9, 7, 74, 76, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (52, 9, 8, 77, 78, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (53, 9, 9, 79, 81, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (54, 9, 10, 82, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (56, 10, 1, 95, 106, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (57, 10, 2, 107, 118, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (58, 10, 3, 119, 130, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (59, 10, 4, 131, 143, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (60, 10, 5, 144, 155, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (61, 10, 6, 156, 167, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (62, 10, 7, 168, 180, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (63, 10, 8, 181, 192, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (64, 10, 9, 193, 204, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (65, 10, 10, 205, 216, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (66, 11, 1, 95, 106, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (67, 11, 2, 107, 113, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (68, 11, 3, 114, 119, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (69, 11, 4, 120, 125, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (70, 11, 5, 126, 131, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (71, 11, 6, 132, 139, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (72, 11, 7, 140, 148, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (73, 11, 8, 149, 160, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (74, 11, 9, 161, 204, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (75, 11, 10, 205, 216, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (77, 12, 3, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (79, 13, 2, NULL, NULL, N'心血管', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (81, 14, 3, NULL, NULL, N'吸烟', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (83, 15, 6, NULL, NULL, N'心房颤动', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (85, 16, 4, NULL, NULL, N'左心室肥厚', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (86, 17, -1, 0, 34, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (87, 17, 0, 35, 39, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (88, 17, 1, 40, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (89, 17, 2, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (90, 17, 3, 50, 54, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (91, 17, 4, 55, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (92, 17, 5, 60, 64, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (93, 17, 6, 65, 69, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (94, 17, 7, 70, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (95, 20, -3, NULL, 99, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (96, 20, 0, 100, 199, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (97, 20, 1, 200, 239, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (98, 20, 2, 240, 279, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (99, 20, 3, 280, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (100, 21, 2, NULL, 34, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (101, 21, 1, 35, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (102, 21, 0, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (103, 21, 0, 50, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (104, 21, -2, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (105, 23, 0, 0, 119, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (106, 23, 0, 120, 129, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (107, 23, 1, 130, 139, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (108, 23, 2, 140, 159, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (109, 23, 3, 160, 999, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (110, 23, 0, 0, 119, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (111, 23, 0, 120, 129, NULL, 80, 84)
GO
print 'Processed 100 total records'
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (112, 23, 1, 130, 139, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (113, 23, 2, 140, 159, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (114, 23, 3, 160, 999, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (115, 23, 1, 0, 119, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (116, 23, 1, 120, 129, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (117, 23, 1, 130, 139, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (118, 23, 2, 140, 159, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (119, 23, 3, 160, 999, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (120, 23, 2, 0, 119, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (121, 23, 2, 120, 129, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (122, 23, 2, 130, 139, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (123, 23, 2, 140, 159, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (124, 23, 3, 160, 999, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (125, 23, 3, 0, 119, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (126, 23, 3, 120, 129, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (127, 23, 3, 130, 139, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (128, 23, 3, 140, 159, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (129, 23, 3, 160, 999, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (131, 24, 2, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (133, 25, 2, NULL, NULL, N'吸烟', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (134, 26, -9, 0, 34, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (135, 26, -4, 35, 39, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (136, 26, 0, 40, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (137, 26, 3, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (138, 26, 6, 50, 54, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (139, 26, 7, 55, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (140, 26, 8, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (141, 27, -2, NULL, 99, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (142, 27, 0, 100, 199, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (143, 27, 1, 200, 279, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (144, 27, 3, 280, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (145, 28, 6, NULL, 35, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (146, 28, 2, 36, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (147, 28, 1, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (148, 28, 0, 50, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (149, 28, -3, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (150, 29, -3, 0, 119, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (151, 29, 0, 120, 129, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (152, 29, 0, 130, 139, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (153, 29, 2, 140, 159, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (154, 29, 3, 160, 999, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (155, 29, 0, 0, 119, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (156, 29, 0, 120, 129, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (157, 29, 0, 130, 139, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (158, 29, 2, 140, 159, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (159, 29, 3, 160, 999, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (160, 29, 0, 0, 119, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (161, 29, 0, 120, 129, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (162, 29, 0, 130, 139, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (163, 29, 2, 140, 159, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (164, 29, 3, 160, 999, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (165, 29, 2, 0, 119, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (166, 29, 2, 120, 129, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (167, 29, 2, 130, 139, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (168, 29, 2, 140, 159, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (169, 29, 3, 160, 999, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (170, 29, 3, 0, 119, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (171, 29, 3, 120, 129, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (172, 29, 3, 130, 139, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (173, 29, 3, 140, 159, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (174, 29, 3, 160, 999, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (176, 30, 4, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (178, 31, 2, NULL, NULL, N'吸烟', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (179, 33, -1, 0, 34, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (180, 33, 0, 35, 39, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (181, 33, 1, 40, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (182, 33, 2, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (183, 33, 3, 50, 54, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (184, 33, 4, 55, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (185, 33, 5, 60, 64, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (186, 33, 6, 65, 69, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (187, 33, 7, 70, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (188, 34, -3, NULL, 99, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (190, 34, 0, 100, 159, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (192, 34, 1, 160, 189, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (193, 34, 2, 190, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (194, 35, 2, NULL, 35, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (195, 35, 1, 36, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (196, 35, 0, 45, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (197, 35, -2, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (198, 36, 0, 0, 119, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (199, 36, 0, 120, 129, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (200, 36, 1, 130, 139, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (201, 36, 2, 140, 159, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (202, 36, 3, 160, 999, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (203, 36, 0, 0, 119, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (204, 36, 0, 120, 129, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (205, 36, 1, 130, 139, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (206, 36, 2, 140, 159, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (207, 36, 3, 160, 999, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (208, 36, 1, 0, 119, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (209, 36, 1, 120, 129, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (210, 36, 1, 130, 139, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (211, 36, 2, 140, 159, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (212, 36, 3, 160, 999, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (213, 36, 2, 0, 119, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (214, 36, 2, 120, 129, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (215, 36, 2, 130, 139, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (216, 36, 2, 140, 159, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (217, 36, 3, 160, 999, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (218, 36, 3, 0, 119, NULL, 100, 999)
GO
print 'Processed 200 total records'
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (219, 36, 3, 120, 129, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (220, 36, 3, 130, 139, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (221, 36, 3, 140, 159, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (222, 36, 3, 160, 999, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (224, 37, 2, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (226, 38, 2, NULL, NULL, N'吸烟', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (227, 39, -9, 0, 34, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (228, 39, -4, 35, 39, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (229, 39, 0, 40, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (230, 39, 3, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (231, 39, 6, 50, 54, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (232, 39, 7, 55, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (233, 39, 8, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (234, 40, -2, NULL, 99, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (235, 40, 0, 100, 159, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (236, 40, 2, 160, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (237, 41, 6, NULL, 35, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (238, 41, 2, 36, 44, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (239, 41, 1, 45, 49, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (240, 41, 0, 50, 59, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (241, 41, -3, 60, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (242, 42, -3, 0, 119, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (243, 42, 0, 120, 129, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (244, 42, 0, 130, 139, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (245, 42, 2, 140, 159, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (246, 42, 3, 160, 999, NULL, 0, 79)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (247, 42, 0, 0, 119, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (248, 42, 0, 120, 129, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (249, 42, 0, 130, 139, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (250, 42, 2, 140, 159, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (251, 42, 3, 160, 999, NULL, 80, 84)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (252, 42, 0, 0, 119, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (253, 42, 0, 120, 129, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (254, 42, 0, 130, 139, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (255, 42, 2, 140, 159, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (256, 42, 3, 160, 999, NULL, 85, 89)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (257, 42, 2, 0, 119, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (258, 42, 2, 120, 129, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (259, 42, 2, 130, 139, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (260, 42, 2, 140, 159, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (261, 42, 3, 160, 999, NULL, 90, 99)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (262, 42, 3, 0, 119, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (263, 42, 3, 120, 129, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (264, 42, 3, 130, 139, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (265, 42, 3, 140, 159, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (266, 42, 3, 160, 999, NULL, 100, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (268, 43, 4, NULL, NULL, N'糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (270, 44, 2, NULL, NULL, N'吸烟', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (271, 45, 100, 127, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (272, 45, 10, 100, 126, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (273, 46, 2, 25, 30, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (274, 46, 5, 31, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (276, 47, 5, NULL, 40, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (277, 48, 5, NULL, 50, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (279, 49, 3, NULL, NULL, N'父母糖尿病', NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (280, 50, 2, 150, NULL, NULL, NULL, NULL)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (281, 51, 2, 130, 999, NULL, 85, 999)
INSERT [dbo].[health_risk_assessment_item_condition] ([id], [health_item_id], [points], [min_value], [max_value], [yes_or_not], [min_value1], [max_value1]) VALUES (283, 52, 2, NULL, NULL, N'降压药', NULL, NULL)
SET IDENTITY_INSERT [dbo].[health_risk_assessment_item_condition] OFF
/****** Object:  Table [dbo].[health_risk_assessment_item]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_assessment_item](  --健康风险评估疾病对应项目
	[id] [int] IDENTITY(1,1) NOT NULL,
	[health_risk_id] [int] NOT NULL,            --评估疾病ID
	[item_code] [varchar](50) NOT NULL,         --健康风险评估项目编码
	[item_name] [varchar](50) NOT NULL,         --项目名称
	[item_type] [int] NOT NULL,                 --判断类型  1表示按最大（max_value）和最小值（min_value）判断、2表示按包含（in）关系判断 yes_or_not、3表示按组合条件判断 max_value、min_value、max_value1、min_value1 (特殊项目血压)
	[sex] [varchar](10) NOT NULL,               --适用性别
	[item_seq] [int] NOT NULL,                  --顺序码
 CONSTRAINT [PK_health_risk_assessment_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_assessment_item] ON
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (1, 1, N'AGE', N'年龄', 1, N'男', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (2, 1, N'DBP', N'舒张压', 1, N'男', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (3, 1, N'SBP', N'收缩压', 1, N'男', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (4, 1, N'DM', N'糖尿病史', 2, N'男', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (5, 1, N'CVD', N'心血管疾病史', 2, N'男', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (6, 1, N'CIGS', N'吸烟史', 2, N'男', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (7, 1, N'AF', N'心房颤动', 2, N'男', 7)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (8, 1, N'LVH', N'左心室肥厚', 2, N'男', 8)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (9, 1, N'AGE', N'年龄', 1, N'女', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (10, 1, N'DBP', N'舒张压', 1, N'女', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (11, 1, N'SBP', N'收缩压', 1, N'女', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (12, 1, N'DM', N'糖尿病史', 2, N'女', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (13, 1, N'CVD', N'心血管疾病史', 2, N'女', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (14, 1, N'CIGS', N'吸烟史', 2, N'女', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (15, 1, N'AF', N'心房颤动', 2, N'女', 7)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (16, 1, N'LVH', N'左心室肥厚', 2, N'女', 8)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (17, 2, N'AGE', N'年龄', 1, N'男', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (20, 2, N'CHOL', N'总胆固醇', 1, N'男', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (21, 2, N'HDL-C', N'高密度脂蛋白', 1, N'男', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (23, 2, N'BP', N'血压', 3, N'男', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (24, 2, N'DM', N'糖尿病史', 2, N'男', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (25, 2, N'CIGS', N'吸烟史', 2, N'男', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (26, 2, N'AGE', N'年龄', 1, N'女', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (27, 2, N'CHOL', N'总胆固醇', 1, N'女', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (28, 2, N'HDL-C', N'高密度脂蛋白', 1, N'女', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (29, 2, N'BP', N'血压', 3, N'女', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (30, 2, N'DM', N'糖尿病史', 2, N'女', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (31, 2, N'CIGS', N'吸烟史', 2, N'女', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (33, 3, N'AGE', N'年龄', 1, N'男', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (34, 3, N'LDL-C', N'胆固醇', 1, N'男', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (35, 3, N'HDL-C', N'高密度脂蛋白', 1, N'男', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (36, 3, N'BP', N'血压', 3, N'男', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (37, 3, N'DM', N'糖尿病史', 2, N'男', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (38, 3, N'CIGS', N'吸烟史', 2, N'男', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (39, 3, N'AGE', N'年龄', 1, N'女', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (40, 3, N'LDL-C', N'胆固醇', 1, N'女', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (41, 3, N'HDL-C', N'高密度脂蛋白', 1, N'女', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (42, 3, N'BP', N'血压', 3, N'女', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (43, 3, N'DM', N'糖尿病史', 2, N'女', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (44, 3, N'CIGS', N'吸烟史', 2, N'女', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (45, 4, N'GLU', N'空腹血糖', 1, N'全部', 1)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (46, 4, N'BMI', N'体重指数', 1, N'全部', 2)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (47, 4, N'HDL-C', N'高密度脂蛋白', 1, N'男', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (48, 4, N'HDL-C', N'高密度脂蛋白', 1, N'女', 3)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (49, 4, N'PDM', N'父母糖尿病史', 2, N'全部', 4)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (50, 4, N'TG', N'甘油三酸脂', 1, N'全部', 5)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (51, 4, N'BP', N'血压', 3, N'全部', 6)
INSERT [dbo].[health_risk_assessment_item] ([id], [health_risk_id], [item_code], [item_name], [item_type], [sex], [item_seq]) VALUES (52, 4, N'HTM', N'服药史', 2, N'全部', 7)
SET IDENTITY_INSERT [dbo].[health_risk_assessment_item] OFF
/****** Object:  Table [dbo].[health_risk_assessment]    Script Date: 11/12/2018 14:22:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[health_risk_assessment](  --健康风险评估疾病表
	[id] [int] IDENTITY(1,1) NOT NULL,
	[disease_name] [varchar](50) NOT NULL,     --疾病名称
	[disease_type] [varchar](10) NOT NULL,     --疾病类型
	[disease_type_name] [varchar](50) NOT NULL,--类型名称
	[bar_graph_titel] [varchar](50) NULL,      --生成柱状图标题名称
 CONSTRAINT [PK_health_risk_assessment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[health_risk_assessment] ON
INSERT [dbo].[health_risk_assessment] ([id], [disease_name], [disease_type], [disease_type_name], [bar_graph_titel]) VALUES (1, N'卒中', N'STROKE', N'卒中', N'十年卒中风险评估对比图')
INSERT [dbo].[health_risk_assessment] ([id], [disease_name], [disease_type], [disease_type_name], [bar_graph_titel]) VALUES (2, N'心血管病-总胆固醇', N'CHD', N'心血管病', N'十年心血管风险评估对比图')
INSERT [dbo].[health_risk_assessment] ([id], [disease_name], [disease_type], [disease_type_name], [bar_graph_titel]) VALUES (3, N'心血管病-胆固醇', N'CHD', N'心血管病', N'十年心血管风险评估对比图')
INSERT [dbo].[health_risk_assessment] ([id], [disease_name], [disease_type], [disease_type_name], [bar_graph_titel]) VALUES (4, N'糖尿病', N'DM', N'糖尿病', N'二型糖尿病八年风险评估对比图')
SET IDENTITY_INSERT [dbo].[health_risk_assessment] OFF
