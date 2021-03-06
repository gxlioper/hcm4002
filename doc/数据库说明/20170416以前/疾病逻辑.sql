
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'536', N'getDiseaseLogicPage.action', N'疾病逻辑管理页面', N'1', NULL, N'536', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'537', N'getDiseaseLogic.action', N'疾病逻辑列表', N'2', NULL, N'537', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'538', N'deletDiseaseLogic.action', N'删除疾病逻辑', N'2', NULL, N'538', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'539', N'addDiseaseLogicPage.action', N'疾病逻辑新增页面', N'2', NULL, N'539', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'540', N'getDiseaseChargingItem.action', N'获取所有收费项目', N'2', NULL, N'540', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'541', N'getDiseaseExaminationItem.action', N'获取所有检查项目', N'2', NULL, N'541', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'542', N'getDiseaseKnowloedge.action', N'获取所有疾病', N'2', NULL, N'542', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'543', N'addDiseaseKnowloedge.action', N'添加疾病逻辑', N'2', NULL, N'543', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'544', N'updateDiseaseLogicPage.action', N'疾病逻辑修改页面', N'2', NULL, N'544', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'545', N'updateDiseaseLogicPage.action', N'疾病逻辑修改获取检查项目', N'2', NULL, N'545', N'2', N'1')



ALTER TABLE disease_logic ADD age_max  VARCHAR(4)    --最大年龄
ALTER TABLE disease_logic ADD age_min VARCHAR(4)     --最小年龄

