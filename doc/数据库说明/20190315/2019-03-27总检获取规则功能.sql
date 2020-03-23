
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS050', N'总检医生总检权限', N'2', N'1', N'赋予总检医生总检贵宾客户权限输入1、赋予总检医生总检包含TTM项目客户权限输入2、赋予总检医生总检个人客户权限输入3', N'输入：1或2或3', N'1表示总检医生拥有总检贵宾客户权限、2表示总检医生拥有总检包含TTM项目客户权限、3表示总检医生拥有总检个人客户权限', NULL, NULL, N'Y')

update center_configuration set config_value =1,common = '总检获取模式：1、180医院总检获取规则，2、交大二院总检获取规则，3、东北国际医院总检获取规则' where config_key = 'IS_EXAM_RESULT_CANFINAL'