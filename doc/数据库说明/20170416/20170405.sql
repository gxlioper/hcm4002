INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'813', N'regetDjtRegisterGEdit.action', N'登记台个人复检', N'2', NULL, N'3', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'814', N'regetDjtRegisterTEdit.action', N'登记台团体复检', N'2', NULL, N'4', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'815', N'updateTeamAccountForIddo.action', N'团体结算修改说明', N'2', NULL, N'10', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE]) VALUES (N'816', N'termExamInfoUserListts.action', N'团体特殊结算获取体检人员信息', N'2', NULL, N'10', N'2', N'1')

alter table team_account add acc_name  varchar(100);--结算单说明
alter table team_account add remark  varchar(500);--结算单备注