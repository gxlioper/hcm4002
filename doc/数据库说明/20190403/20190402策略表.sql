CREATE TABLE crm_plan_tactics     ----	�����ƻ����Ա�crm_plan_tactics
(
	[id] [int]  identity(1,1) primary key not null,
    [tactics_num] [varchar](50) unique not NULL,     ---- ���Ա���
	[notices] [varchar](500)  NULL, 	 ----��������
	[tactics_type] int NULL,       ---- ��������
	[rmark] [varchar](500)  NULL,      -----��Ӧ����˵��
	[creater] [int] NULL,                ---- ������
	[create_date] [datetime] NULL,       -----����ʱ��
	[updater] [int] NULL,                ---- �޸���
	[update_date] [datetime] NULL,       -----�޸�ʱ��
	         
)

---tactics_type ��ʾ��1 ������2���顢3Σ��ֵ��4vip�طã�5����ط�


 CREATE TABLE crm_plan_tactics_detail    ------	�����ƻ����Ա���ϸcrm_plan_tactics_detail
(
	[id] [int]  identity(1,1) primary key not null,
    	[tactics_num] [varchar](50)  not NULL,     ---- ���Ա���
	[notices] [varchar](500)  NULL, 	 ----�ط���������
	[plan_doctor_id] int NULL,       ---- �ط�ҽ��
	[distancedate] int  NULL,      -----������켸��ط�
	[creater] [int] NULL,                ---- ������
	[create_date] [datetime] NULL,       -----����ʱ��
	[updater] [int] NULL,                ---- �޸���
	[update_date] [datetime] NULL,       -----�޸�ʱ��
	         
)


alter  table crm_visit_plan  add  tactics_num  varchar(50) null    ----����
alter  table  crm_visit_record add tactics_detail_id  int null          --------������id
alter  table   crm_visit_record add  visit_notices varchar(500) null   -----------�ط�����
alter table  crm_visit_lost add  cvr_id varchar(50)              --------ʧ�ñ�����ط��ӱ�id

 
