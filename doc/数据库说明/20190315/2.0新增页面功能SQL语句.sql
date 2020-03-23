alter table group_info add group_order int not null default(0) --增加排序字段

INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1545', N'djtTpanel.action', N'团体控制台页面 ', N'1', null, N'1545', N'2', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1546', N'getExamInfoYditemAddList.action', N'人员异动新增项目', N'2', null, N'1545', N'1', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1547', N'ydcustdelchangitemlist.action', N'登记台获取 人员收费项目列表', N'2', null, N'1545', N'2', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1548', N'getExamInfoYdUserList.action', N'异动体检人员显示', N'2', null, N'1545', N'2', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1549', N'getExamInfoYditemdelList.action', N'异动体检人删除项目', N'2', null, N'1545', N'1', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1550', N'getBatchForGroupTwo.action', N'批次信息', N'2', null, N'1545', N'2', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1551', N'cusbatchedit.action', N'新增体检任务', N'2', null, N'1545', N'1', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1552', N'docoptbatchto.action', N'复制批次-复制到目标单位下面', N'2', null, N'1545', N'1', N'1', N'1');
INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1553', N'saveGroupOrder.action', N'保存排序 ', N'2', null, N'1545', N'1', N'1', N'1');
