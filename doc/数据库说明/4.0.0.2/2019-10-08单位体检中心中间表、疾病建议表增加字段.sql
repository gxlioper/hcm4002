alter table company_info_vs_center add contact_phone varchar(45) --��ϵ�绰
,contact_name varchar(45)  --��ϵ��
,com_phone varchar(45) --��λ�绰
,com_jianjie varchar(200) --��λ���
,com_fax varchar(50) --��λ����
,com_type varchar(45) --��λ����
,keShi_Name varchar(20) -- ������ϵ��
,email varchar(45) --����
,address varchar(200) --��ַ
,remark varchar(500)  --��ע



alter table disease_suggestion_lib add center_num varchar(45) --������ı���

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'����ܽ�����������', N'IS_DISEASE_SUG_CENTER', N'N', N'Y', N'���������ģʽ�����������Ƿ�����������֡�Y��ʾ���֡�N��ʾ������', NULL, N'000')