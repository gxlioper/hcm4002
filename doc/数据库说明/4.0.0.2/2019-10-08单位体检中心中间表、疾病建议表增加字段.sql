alter table company_info_vs_center add contact_phone varchar(45) --联系电话
,contact_name varchar(45)  --联系人
,com_phone varchar(45) --单位电话
,com_jianjie varchar(200) --单位简称
,com_fax varchar(50) --单位传真
,com_type varchar(45) --单位类型
,keShi_Name varchar(20) -- 科室联系人
,email varchar(45) --邮箱
,address varchar(200) --地址
,remark varchar(500)  --备注



alter table disease_suggestion_lib add center_num varchar(45) --体检中心编码

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'火箭蛙健康管理中心', N'IS_DISEASE_SUG_CENTER', N'N', N'Y', N'多添加中心模式，疾病建议是否按体检中心区分。Y表示区分、N表示不区分', NULL, N'000')