 insert  into  WEB_XTGNB  values(991,'getPricing.action','����ҳ��','1','','325',1,1,1)

 insert  into  WEB_XTGNB  values(998,'getTeamPrepayment.action','����Ԥ����ҳ��','1','','998',1,1,1)

insert  into  WEB_XTGNB  values(
'1730',	'getExamInfoAuditPageLog.action'	,'��������ܼ������ҳ��',	'1'	,NULL	,1730	,1,	1,	1)

alter table exam_info add ziqu_report_time datetime; --������ȡ����ʱ��


alter  table  examinfo_charging_item   add  tj_charge_amount decimal(10, 4)   		 --- ����շѽ��
				,tj_charge_status varchar(45)         --- ����շ�״̬
				,his_charge_amount  decimal(10, 4)                            --- his�շѽ��
				,his_charge_status  varchar(45)      --- his�շ�״̬
				,pay_mode      varchar(45)  			 --- 0 ����շѣ�1 his�շ� ��2 ����շ�

alter  table charging_summary_single add pay_mode varchar(45)  null      ----�����ܱ��ѷ�ʽ  1 ����շ�,2 his�շ�


------����Ԥ���� --- �̻���
CREATE TABLE company_account
(
	[id] [int]  identity(1,1) primary key not null,
   	 [center_num] [varchar](50) NULL,     ---- ������ı��
	[com_num] [varchar](50)unique not NULL, 	 ----��λ���
	[com_code] [varchar](50) NULL,       ---- ��ҵ����
	[balance] decimal(10, 4)  NULL,      -----�˻����
	[com_type] [int] NULL,               -----�̻�״̬��0_���� 1_���� 2_������
	[creater] [int] NULL,                ---- ������
	[create_date] [datetime] NULL,       -----����ʱ��
	[updater] [int] NULL,                ---- �޸���
	[update_date] [datetime] NULL,       -----�޸�ʱ��
	[digitalSign] [varchar](40) NULL     -----����ǩ��           
)


------�̻����ѳ�ֵ��ˮ��

CREATE TABLE companyAccountDetail
(
	[id] [int]  identity(1,1) primary key not null,
	[logicdate] [datetime] NULL,        -----��������
	[com_num] [varchar](50) not NULL, 	 ----��λ���
	[jnnumber] [varchar](50)unique not NULL,       -----������ˮ��
	[prejnnumber] [varchar](50) NULL,    -----�ϱ���ˮ��
	[account_num] [varchar](50) NULL,    -----���ʺ�
	[creater] [int] NULL,                ---- ����Ա
	[trancode] [varchar](50) NULL,       ----�������ʹ���   1��ֵ  2����  
	[jnstatus] [int] NULL,               ----��ˮ״̬       1 ��Ч��2���ϣ�3����
	[jndatetime] [datetime] NULL,        ---- ��ˮ��������ʱ��
	[balance] decimal(10, 4)  NULL,      -----�˻����
	[oldbalance] decimal(10, 4)  NULL,    -----�˻�ԭ���
	[usednum] [int] NULL,                ---- �ۼ�ʹ�ô��� 
	[trantmt] decimal(10, 4)  NULL,      -----���׶�
	[chargingway] [varchar](50) NULL,    ----֧����ʽ
	[invaioce_type] [int] null  ,         ----��Ʊ״̬ 0 δ��  1 �ѿ�
   	 [digitalSign] [varchar](40) NULL ,    -----����ǩ��    
	[resume] [varchar](100) NULL     ,     -----ժҪ
	[invoice_id] [int] null                -----��Ʊid
) 