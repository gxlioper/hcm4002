INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'体检中心', N'EXAM_SUMMARY_STYLE', N'1', N'Y', N'总检室总监模式，1 普通总检模式，2 180总检模式')

INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS021', N'未处理危急值提醒权限', N'2', N'1', N'赋予操作人员存在未处理危急值提醒的权限。', N'输入：1', N'表示拥有未处理危急值提醒的权限', NULL, NULL)