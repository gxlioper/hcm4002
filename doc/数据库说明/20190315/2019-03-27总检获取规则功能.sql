
INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1], [is_active]) VALUES (N'RS050', N'�ܼ�ҽ���ܼ�Ȩ��', N'2', N'1', N'�����ܼ�ҽ���ܼ����ͻ�Ȩ������1�������ܼ�ҽ���ܼ����TTM��Ŀ�ͻ�Ȩ������2�������ܼ�ҽ���ܼ���˿ͻ�Ȩ������3', N'���룺1��2��3', N'1��ʾ�ܼ�ҽ��ӵ���ܼ����ͻ�Ȩ�ޡ�2��ʾ�ܼ�ҽ��ӵ���ܼ����TTM��Ŀ�ͻ�Ȩ�ޡ�3��ʾ�ܼ�ҽ��ӵ���ܼ���˿ͻ�Ȩ��', NULL, NULL, N'Y')

update center_configuration set config_value =1,common = '�ܼ��ȡģʽ��1��180ҽԺ�ܼ��ȡ����2�������Ժ�ܼ��ȡ����3����������ҽԺ�ܼ��ȡ����' where config_key = 'IS_EXAM_RESULT_CANFINAL'