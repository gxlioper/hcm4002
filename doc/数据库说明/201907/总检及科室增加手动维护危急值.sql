 insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('Σ��ֵ�ȼ�','WJZDJ','A��','Y','1','A','0')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('Σ��ֵ�ȼ�','WJZDJ','B��','Y','2','B','0')
insert into data_dictionary(data_type,data_code,data_name,isActive,seq_code,data_code_children,data_class) values ('Σ��ֵ�ȼ�','WJZDJ','�ط���','Y','3','C','0')
 
 insert into WEB_RESOURCE values ('RS054','�ж��û��Ƿ���ɾ���޸��ֶ���ӵ�Σ��ֵ��Ȩ��','2','1','�ж��û��Ƿ���ɾ���޸��ֶ���ӵ�Σ��ֵ��Ȩ��','���룺1','��ʾ����Ա�д˹���','','','Y')
 
 
 
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'IS_SHOW_ZJORKS', N'KS', N'Y', N'�ֶ�����Σ��ֵҳ����ͨ���һ��������ң�ZJ��ʾ�����ң�KS��ʾ���� ')

INSERT INTO [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'2309', N'criticalhandle.action', N'Σ��ֵ�������ͳ��', N'1', null, N'', N'1', N'1', N'1');

alter table exam_Critical_detail add critical_class_parent_id int,  --����
          critical_class_id int,  --����
          critical_class_level int, --Σ��ֵ�ȼ� 
          critical_suggestion varchar(3000)--Σ��ֵ����


