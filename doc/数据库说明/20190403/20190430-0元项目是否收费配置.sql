
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) 
VALUES (N'XXXXXX', N'IS_CHARGINGWAY_ZERO', N'Y', N'Y', N'�շѴ�ѡ�е��շѷ�ʽ���Ϊ0ʱ,���շѷ�ʽ�Ƿ�д�����ݿ�')

UPDATE center_configuration  SET  common  = '�շѴ�ѡ�е��շѷ�ʽ���Ϊ0ʱ,���շѷ�ʽ�Ƿ�д�����ݿ�,N �����շ�����Ĭ��Ϊ���շ� ��ֱ�Ӽ��Y ���շ����̣����˷�'    
WHERE config_key = 'IS_CHARGINGWAY_ZERO'