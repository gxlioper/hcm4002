 insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','A类','Y','1','A','0')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','B类','Y','2','B','0')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('危急值等级','WJZDJ','回访类','Y','3','C','0')
 
 insert into WEB_RESOURCE values ('RS054','判断用户是否有删除修改手动添加的危急值的权限','2','1','判断用户是否有删除修改手动添加的危急值的权限','输入：1','表示操作员有此功能','','','Y')
 
 
 
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'IS_SHOW_ZJORKS', N'KS', N'Y', N'手动增加危急值页是普通科室还是主检室，ZJ表示主检室，KS表示科室 ')

INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'2309', N'criticalhandle.action', N'危急值处理情况统计', N'1', null, N'', N'1', N'1', N'1');

alter table exam_Critical_detail add critical_class_parent_id int,  --大类
          critical_class_id int,  --子类
          critical_class_level int, --危急值等级 
          critical_suggestion varchar(3000)--危急值建议


