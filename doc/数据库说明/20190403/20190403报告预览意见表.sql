CREATE TABLE exam_suggestion_log    ------ ����Ԥ�������exam_suggestion_log    
(
	 [id] [int]  identity(1,1) primary key not null,
     	 [exam_num] [varchar](30)  not NULL,     ---- �����
	 [notices] [varchar](1000)  NULL,   ----�������
	 [creater] [int] NULL default 0,                ---- ������
	 [create_date] [datetime] not NULL,       -----����ʱ��
	 [updater] [int] not NULL default 0,                ---- �޸���
	 [update_date] [datetime] NULL,       -----�޸�ʱ��
	 [resource] [varchar](3) not NULL default '001',   ----��Դ
	 [apptype] [int] not NULL default 1,   ----ְҵ�������
	   
)