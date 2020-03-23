--����Ӧ�շ���Ŀ��
alter table examinfo_charging_item add exam_num varchar(20)


alter table examinfo_charging_item add inputter int not null default(0)


alter table examinfo_charging_item add introducer int  not null default(0)--��Ŀ������


alter table examinfo_charging_item add tixing_flag int not null default(0)--0��ʾδ����-1��ʾ������


alter table sample_exam_detail add exam_num varchar(16)  --����


alter table examResult_chargingItem add bar_code varchar(20)  --�����


alter table pacs_detail add pacs_req_code varchar(20)   --���뵥��

ALTER TABLE [dbo].[WEB_RESOURCE] ADD [is_active] [varchar](1) NOT NULL DEFAULT ('Y')
