INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXX', N'IS_SEARCHTOTAL_TYPE', N'1', N'Y', N'1、老的数据结构，按照id查询，2、按照数据项编号查询')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1754', N'getResearchDataPage.action', N'获取科研数据页面', N'1', NULL, N'1754', N'1', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1950', N'showesearchDataMongo.action', N'mongo获取科研数据页面', N'1', NULL, N'1950', N'1', N'1', N'1');

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1951', N'mongoDataList.action', N'mongo根据查询条件查询科研数据', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1952', N'mongoChargingItemListByq.action', N'mongo查询收费项目列表', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1953', N'mongoExaminationItemListByq.action', N'mongo查询检查项目列表', N'1', NULL, N'1950', N'1', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1954', N'mongocompanychangshow.action', N'mongo查询单位', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1955', N'expmongoDataListall.action', N'mongo导出全部', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1956', N'expmongoDatasave.action', N'mongo保存查询条件', N'2', NULL, N'1950', N'2', N'1', N'1');

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1957', N'researchDataconditions.action', N'mongo读取查询条件列表', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1958', N'expmongoDatadel.action', N'mongo删除一条查询条件', N'2', NULL, N'1950', N'2', N'1', N'1');
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1959', N'getResearchDataconditionsId.action', N'mongo获取一条查询条件', N'2', NULL, N'1950', N'2', N'1', N'1');