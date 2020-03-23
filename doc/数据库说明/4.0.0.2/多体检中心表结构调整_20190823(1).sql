-- ϵͳ�û���������Ĺ�ϵ��
--if exists (select * from dbo.sysobjects where id = object_id(N'user_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
--drop table user_vs_center
--go

--create table user_vs_center
--(
--	userid int not null,					-- �û�ID
--	center_num varchar( 50 ) not null,		-- ������ı���
--	is_default char( 1 ) not null			-- �Ƿ�Ĭ��������ģ�1-Ĭ�ϣ�0-��һ���û�ֻ����һ��Ĭ���������

--	primary key( userid, center_num )
--)
--go

-- ϵͳ�û���������Ĺ�ϵ������������ı���
if( COL_LENGTH( 'exam_user', 'center_num' ) is null )
begin
	alter table exam_user add center_num varchar( 50 ) not null
end
go

-- ϵͳ�û���������Ĺ�ϵ�����ӡ��Ƿ�Ĭ��������ģ�1-Ĭ�ϣ�0-��һ���û�ֻ����һ��Ĭ��������ġ�
if( COL_LENGTH( 'exam_user', 'is_default' ) is null )
begin
	alter table exam_user add is_default char( 1 ) not null
end
go

if exists (select * from sysindexes where name='ix_exam_user_user_id_center_num')
begin
	drop index exam_user.ix_exam_user_user_id_center_num
end
go

create unique index ix_exam_user_user_id_center_num on exam_user( user_id, center_num )
go

--ɾ���û����ű�������Ա��¼
delete from dep_user where user_id=14
-- department_dep ���ӿ��ұ��룬���ұ�����ϵͳ�Զ�����
if( COL_LENGTH( 'dep_user', 'center_num' ) is null )
begin
	alter table dep_user add center_num varchar( 50 ) not null  default('001')
end
go

--��ӡ��������������ı���
if( COL_LENGTH( 'dep_user_print', 'center_num' ) is null )
begin
	alter table dep_user_print add center_num varchar( 50 ) not null  default('001')
end
go

-- ������Ŀ��ҹ�ϵ��
if exists (select * from dbo.sysobjects where id = object_id(N'department_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table department_vs_center
go

create table department_vs_center
(
	dep_id int not null,						-- ����ID
	center_num varchar( 50 ) not null,			-- ������ı���
	creater int,								-- ������
	create_time datetime						-- ����ʱ��	
	
	primary key( dep_id, center_num )
)
go

-- ��������շ���Ŀ��ϵ��
if exists (select * from dbo.sysobjects where id = object_id(N'charging_item_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table charging_item_vs_center
go

create table charging_item_vs_center
(
 center_num varchar( 50 ) not null,      -- ������ı���
 charging_item_code varchar( 50 ) not null,    -- �շ���Ŀ����
 price numeric( 8, 2 ) not null,       -- ͳһ�۸�ԭ��
 calculation_amount numeric( 8, 2 )  null,             -- ������
 calculation_rate int not null,       -- ������
 charging_item_number int null,       -- ���ƴ���  
 item_discount int null,         -- ��Ŀ�ۿ���
 limit_num int null,          -- ÿ���������  
 charge_inter_num varchar(20) null,      -- ϵͳ�����
 interface_flag varchar(45) null,      -- �ӿڱ�ʾ
 center_price numeric( 8, 2 ) null,      -- ��������ļ۸�
 his_num varchar( 50 ) null,        -- ���������his������
 exam_num varchar( 50 ) null,       -- ���������lis������
 view_num varchar( 50 ) null,       -- ���������pacs������
 perform_dept varchar( 20 ) null,      -- ���������ִ�п��ұ���
 creater int,           -- ������
 cerate_time datetime,         -- ����ʱ��
 updater int,           -- ������ 
 update_time datetime         -- ����ʱ��

 primary key( charging_item_code, center_num )
)
go

-- examinfo_charging_item ���ӡ���Ŀ����(����)������ı��롱������Ŀ���������ı��롱
if( COL_LENGTH( 'examinfo_charging_item', 'center_num' ) is null )
begin
	alter table examinfo_charging_item add center_num  varchar( 50 )		-- ��Ŀ����(����)������ı���
end
go

if( COL_LENGTH( 'examinfo_charging_item', 'exam_center_num' ) is null )
begin
	alter table examinfo_charging_item add exam_center_num  varchar( 50 )		-- ��Ŀ������ı���
end
go

-- ���������ӡ�������ı��롱
if( COL_LENGTH( 'customer_info', 'center_num' ) is null )
begin
	alter table customer_info add center_num varchar( 50 )		-- ������ı���
end
go

-- exam_info ���ӡ�����������ı��롱
if( COL_LENGTH( 'exam_info', 'exam_center_num' ) is null )
begin
	alter table exam_info add exam_center_num  varchar( 50 )		-- ����������ı���
end
go

-- ���������Ȩ���ҵ��
if exists (select * from dbo.sysobjects where id = object_id(N'customer_exam_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table customer_exam_center
go

create table customer_exam_center
(
	center_num varchar( 50 ) not null,						-- ������ı���
	exam_num varchar( 50 ) not null,						-- �����
	charging_item_code varchar( 50 ) not null,				-- �շ���Ŀ����
	creater int,											-- ������
	create_time datetime									-- ����ʱ��

	primary key( center_num, exam_num, charging_item_code )
)
go

-- ���α�batch����������ı���
if( COL_LENGTH( 'batch', 'center_num' ) is null )
begin
	alter table batch add center_num varchar( 50 )		-- ������ı���
end
go

-- ����ײͱ�exam_set����������ı���
if( COL_LENGTH( 'exam_set', 'center_num' ) is null )
begin
	alter table exam_set add center_num varchar( 50 )		-- ������ı���
end
go

-- �����շ�����charging_summary_single�����շ�������ı���
if( COL_LENGTH( 'charging_summary_single', 'center_num' ) is null )
begin
	alter table charging_summary_single add center_num varchar( 50 )		-- ������ı���
end
go

-- ��������շ�����charging_summary_group����������ı���
if( COL_LENGTH( 'charging_summary_group', 'center_num' ) is null )
begin
	alter table charging_summary_group add center_num varchar( 50 )		-- ������ı���
end
go

-- �շ�Ա�ս�����cashier_daily_acc_class����������ı���
if( COL_LENGTH( 'cashier_daily_acc_class', 'center_num' ) is null )
begin
	alter table cashier_daily_acc_class add center_num varchar( 50 )		-- ������ı���
end
go

-- �������ս�����Finance_Dep_acc����������ı���
if( COL_LENGTH( 'Finance_Dep_acc', 'center_num' ) is null )
begin
	alter table Finance_Dep_acc add center_num varchar( 50 )		-- ������ı���
end
go

-- �ܼ���۱�exam_summary����������ı���
if( COL_LENGTH( 'exam_summary', 'center_num' ) is null )
begin
	alter table exam_summary add center_num varchar( 50 )		-- ������ı���
end
go

-- ���ñ����� center_configuration ������ı���
if( COL_LENGTH( 'center_configuration', 'center_num' ) is null )
begin
	alter table center_configuration add center_num varchar( 50 )		-- ������ı���
end
go

-- ���ñ�webservice_configuration����������ı���
if( COL_LENGTH( 'webservice_configuration', 'center_num' ) is null )
begin
	alter table webservice_configuration add center_num varchar( 50 )		-- ������ı���
end
go

-- ��������sys_param1����������ı���
if( COL_LENGTH( 'sys_param1', 'center_num' ) is null )
begin
	alter table sys_param1 add center_num varchar( 50 )		-- ������ı���
end
go

-- ������ĵ�λ��Ϣ��ϵ��
if exists (select * from dbo.sysobjects where id = object_id(N'company_info_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table company_info_vs_center
go

create table company_info_vs_center
(
	company_id int not null,				-- ��λID
	center_num varchar( 50 ) not null,		-- ������ı���
	is_owner char( 1 ) not null,			-- ����������Ƿ�Ϊ��λ��Ϣ�ĳ�ʼ�����ߣ�1-�ǣ�0-��һ����λֻ����һ��������
	creater int,							-- ������
	create_time datetime					-- ����ʱ��

	primary key( center_num, company_id )
)
go

-- lis�������examination_item_vs_lis���Ӽ����Ŀ���뼰������ı���
if( COL_LENGTH( 'examination_item_vs_lis', 'item_code' ) is null )
begin
	alter table examination_item_vs_lis add item_code varchar( 50 )		-- �����Ŀ����
end
go

if( COL_LENGTH( 'examination_item_vs_lis', 'center_num' ) is null )
begin
	alter table examination_item_vs_lis add center_num varchar( 50 )	-- ������ı���
end
go

-- ������Ŀ���Ϣ��ϵ��card_info_center
--if exists (select * from dbo.sysobjects where id = object_id(N'card_info_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
--drop table card_info_center
--go

--create table card_info_center
--(
--	card_num varchar( 50 ) not null,		-- ����
--	center_num varchar( 50 ) not null,		-- ������ı���
--	is_owner char( 1 ) not null,			-- �Ƿ񴴽��ߣ�1-�ǣ�0-��һ����Ա��ֻ����һ��������

--	primary key( center_num, card_num )
--)
--go

-- ����Ϣ������������ı���
if( COL_LENGTH( 'card_info', 'center_num' ) is null )
begin
	alter table card_info add center_num varchar( 50 ) not null			-- ������ı���
end
go

-- ��λ������ˮ������������ı���
if( COL_LENGTH( 'company_account_detail', 'center_num' ) is null )
begin
	alter table company_account_detail add center_num varchar( 50 ) not null	-- ������ı���
end
go

-- �ۿ�������������������ı���
if( COL_LENGTH( 'card_sale_summary', 'center_num' ) is null )
begin
	alter table card_sale_summary add center_num varchar( 50 ) not null		-- ������ı���
end
go


-- �շ�Ա�ս���������������ı���
if( COL_LENGTH( 'cashier_daily_acc', 'center_num' ) is null )
begin
 alter table cashier_daily_acc add center_num varchar( 50 )
end
go

-- ���Ϸ�Ʊ������������ı���
if( COL_LENGTH( 'nullify_invoice', 'center_num' ) is null )
begin
 alter table nullify_invoice add center_num varchar( 50 )
end
go

--������������ֶ�
if( COL_LENGTH( 'card_info', 'company_id' ) is null )
begin
	alter table card_info add company_id int not null default(0)
end
go

--ȡ��charging_detail_single ���item_code 
if( COL_LENGTH( 'charging_detail_single', 'item_code' ) is not null )
begin
	alter table charging_detail_single drop column item_code
end
go


