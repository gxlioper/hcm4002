INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_SHENQINGDAN', N'N', N'Y', N'登记台是否展示打申请单', N'XXXXX');


INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_SELECT_SHENQINGDAN', N'N', N'Y', N'登记台是否展示选择打申请单', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_PAIDUIHAO', N'N', N'Y', N'登记台是否展示打排队号', N'XXXXX');


INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_DAOJIANDAN_SHENGQINGDAN', N'N', N'Y', N'登记台是否展示打导检单申请单', N'XXXXX');

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_PACS_CUSINFO_UP', N'N', N'Y', N'是否给pacs接口同步档案');

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'COLLECT_FEES_WHOLE', N'10', N'Y', N'收费处凑整可使用最大金额');

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_HIS_UNRELATED_SHOW', N'N', N'Y', N'收费项目管理his价表和诊疗项目时采用模式:Y 关联价表和诊疗项目关系，N 不关联关系');

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'IS_TBAODAO_QUEUE_SEND', N'Y', N'Y', N'团检报道是否发送排队申请');
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'IS_TEAM_AMOUNT_VIEW', N'N', N'Y', N'控制团体金额、折扣率等是否可见的资源RS039是否生效');
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'体检中心', N'IS_PRINT_BARCODE_ITEM_PAY', N'N', N'Y', N'登记台打印条码是否判断项目是否付费，N表示不用判断，Y表示需要判断项目已收费');