--增加资源
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'RS055', N'判断用户是否有销毁功能', N'2', N'1', N'赋予操作人员有销毁权限。', N'输入：1', N'表示拥有销毁功能', NULL, NULL)
--增加配置
INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_OPERATION_CONTROL', N'Y', N'Y', N'体检人员操作控制, Y 不可以同事操作、N 可以同事操作', N'XXXXX');




