update center_configuration set common = '�ܼ��Ҽ����߼�ʹ�����ͣ�N��ʾ�ɼ����߼���Y��ʾ�ɹ������Ҽ����߼���X��ʾ�¼����߼�' where config_key = 'IS_DISEASE_LOGIC_DEP'
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'IS_USE_COMPOSITE_LOGIC', N'N', N'Y', N'�ܼ����Ƿ����ø����߼��жϼ�����ע��ΪYʱ�����߼����ļ����������ȼ�����ΪNʱ�������԰��տ��ҡ��շ���Ŀ�������Ŀ����')

alter table exam_info add zyb_final_status varchar(10) not null default('N'),--ְҵ���ܼ�״̬ Nδ�ܼ졢Z���ܼ�
						  zyb_final_time datetime,                           --ְҵ���ܼ�ʱ��					   
                          zyb_final_doctor varchar(50)                       --ְҵ���ܼ�ҽ��
                          

alter table examinfo_disease add exam_num varchar(50),
                                 disease_num varchar(50),
                                 data_source int,        --������Դ 0��ʾ�Զ����ɡ�1��ʾ�ֹ���ӡ�2��ʾ�ֶ��ϲ�
                                 data_source_json varchar(max) --������Դ������data_sourceΪ0��2ʱ����json��ʽ����
