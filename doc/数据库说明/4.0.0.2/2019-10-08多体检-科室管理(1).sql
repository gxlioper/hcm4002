INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2511','centerDepartmentDep.action','���������-���ҹ���',1,null,'2511',1,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2514','updateCenterDepPage.action','���������-�޸Ŀ���ҳ��',2,null,'2511',1,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2512','batchCenterDep.action','���������-��ǰ�������������ӿ���',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2513','deleteCenterDep.action','���������-ɾ������',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2515','updateCenterDep.action','���������-�޸Ŀ���',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2516','centerupdatedepRate.action','���������-�޸Ŀ���������',2,null,'2511',2,1,0);

alter table department_vs_center add dep_address varchar(100) --���ҵ�ַ
alter table department_vs_center add dep_inter_num varchar(50) --���ҵ���������
alter table department_vs_center add isprint_barcode char(1) --�Ƿ��ӡ���� 0 �� 1��
alter table department_vs_center add synchro char(1) --�Ƿ��ϴ�΢��
alter table department_vs_center add synchrodate datetime --�Ƿ��ϴ�΢��
alter table department_vs_center add calculation_rate int not null default(0) --���������� ���100����С0 
alter table department_vs_center add view_result_type int not null default(0) -- Ӱ����ұ��������ͣ�0��ʾ������pacs_id���桢1��ʾ�����ұ���dep_num���棬�ڲ�ѯ��ʱ��ᰴ�մ�������ʾӰ��ͼƬ

alter table department_vs_center add remark1 varchar(50)
alter table department_vs_center add remark2 varchar(50)