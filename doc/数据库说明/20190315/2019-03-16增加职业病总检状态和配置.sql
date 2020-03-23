update center_configuration set common = '总检室疾病逻辑使用类型：N表示旧疾病逻辑、Y表示旧关联科室疾病逻辑、X表示新疾病逻辑' where config_key = 'IS_DISEASE_LOGIC_DEP'
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_USE_COMPOSITE_LOGIC', N'N', N'Y', N'总检室是否启用复合逻辑判断疾病。注：为Y时复合逻辑出的疾病按疾病等级排序，为N时单项阳性按照科室、收费项目、检查项目排序')

alter table exam_info add zyb_final_status varchar(10) not null default('N'),--职业病总检状态 N未总检、Z已总检
						  zyb_final_time datetime,                           --职业病总检时间					   
                          zyb_final_doctor varchar(50)                       --职业病总检医生
                          

alter table examinfo_disease add exam_num varchar(50),
                                 disease_num varchar(50),
                                 data_source int,        --数据来源 0表示自动生成、1表示手工添加、2表示手动合并
                                 data_source_json varchar(max) --数据来源条件，data_source为0或2时保存json格式数据
