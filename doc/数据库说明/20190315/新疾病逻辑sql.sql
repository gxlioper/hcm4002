--�����Ŀ�������ֶ�  ������� 0��ʾ����� 1��ʾ���ۺ������ͣ�Ϊ�ɰ汾������ 347�����Ŀ��
alter table examination_item add item_result_type int not null default(0)

--����֪ʶ��������ֶ�
alter table disease_knowloedge_lib add disease_system varchar(10) --����ϵͳ���� �����ֵ�ά������ϵͳ���ࣨ�磺��Ѫ��ϵͳ������ϵͳ���ڷ���ϵͳ�ȣ�
                                      ,disease_statistics varchar(10) -- ��ͳ�Ʒ��� �����ֵ�ά����ͳ�Ʒ��ࣨ��������磺����ѹƫ�ߡ�����ѹƫ��ͳ��ΪѪѹƫ�ߣ�
                                      ,disease_report int not null default(0)   --�Ƿ��ϱ� 0�ϱ���1���ϱ�
                                      ,disease_team_show int not null default(0) --�ű��Ƿ���ʾ 0��ʾ��1����ʾ
                                      
create table disease_logic_single( --������߼�����
	id varchar(50) not null,            
	disease_num varchar(50) not null,   --��������
	logic_name varchar(50) not null,    --�����߼�����
	item_code varchar(50) not null,      --�����Ŀ����
	isActive varchar(10) not null,
	sex varchar(10) not null,           --�����Ա�
	age_max int not null,               --�����������
	age_min int not null,                --������С����
	logic_class int not null,            --���������߼����� 0�������Ŀ�����1����ͨ����С���Ӱ���鰴�տ��ұ���Ľ��
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_single_item (--������߼�����
	id varchar(50) not null,
	logic_single_id varchar(50) not null,   --��������ID
	logic_item_name varchar(50) not null,   --��������
	logic_index int not null,               --��������
	critical_flag int not null,
	isActive varchar(10) not null,
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_single_item_condition (--������߼�����ֵ
	id varchar(50) not null,
	logic_single_item_id varchar(50) not null,   --�����߼�����ID
	logic_index int not null,                    --����ֵ����
	condition_value varchar(50) not null,        --����ֵ
	condition varchar(10) not null               --����
)
 
create table disease_logic_composite( --���ϼ����߼�����
	id varchar(50) not null,
	disease_num varchar(50) not null,   --��������
	logic_name varchar(50) not null,    --�����߼�����
	isActive varchar(10) not null,
	sex varchar(10) not null,           --�����Ա�
	age_max int not null,               --�����������
	age_min int not null,                --������С����
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_composite_item (--���ϼ����߼�����
	id varchar(50) not null,
	logic_composite_id varchar(50) not null,   --��������ID
	logic_item_name varchar(50) not null,   --��������
	logic_index int not null,               --��������
	critical_flag int not null,
	isActive varchar(10) not null,
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_composite_item_condition (--���ϼ����߼�����ֵ
	id varchar(50) not null,
	logic_composite_item_id varchar(50) not null,   --�����߼�����ID
	diseaseOrItem_num varchar(50) not null,         --�������Ա��������Ŀ����
	condition_type int not null,                 --0��ʾ�������ԡ�1��ʾ�����Ŀ
	logic_index int not null,                    --����ֵ����
	condition_value varchar(50) not null,        --����ֵ
	condition varchar(10) not null               --����
)

create table examinfo_disease_single(  --���¼���Ӧ�������Ա�
	id varchar(50) not null,
	exam_num varchar(50) not null,      --�����
	dep_num varchar(50) not null,       --���ұ���
	charging_item_code varchar(50) not null,     --�շ���Ŀ����
	disease_num varchar(50) not null,   --��������
	item_code varchar(50) not null,      --�����Ŀ����
	disease_name varchar(50),           --��������
	creater int,
	create_time datetime
)
