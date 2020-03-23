-- 系统用户与体检中心关系表
--if exists (select * from dbo.sysobjects where id = object_id(N'user_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
--drop table user_vs_center
--go

--create table user_vs_center
--(
--	userid int not null,					-- 用户ID
--	center_num varchar( 50 ) not null,		-- 体检中心编码
--	is_default char( 1 ) not null			-- 是否默认体检中心，1-默认，0-否，一个用户只能有一个默认体检中心

--	primary key( userid, center_num )
--)
--go

-- 系统用户与体检中心关系表增加体检中心编码
if( COL_LENGTH( 'exam_user', 'center_num' ) is null )
begin
	alter table exam_user add center_num varchar( 50 ) not null
end
go

-- 系统用户与体检中心关系表增加“是否默认体检中心，1-默认，0-否，一个用户只能有一个默认体检中心”
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

--删除用户部门表超级管理员记录
delete from dep_user where user_id=14
-- department_dep 增加科室编码，科室编码由系统自动生成
if( COL_LENGTH( 'dep_user', 'center_num' ) is null )
begin
	alter table dep_user add center_num varchar( 50 ) not null  default('001')
end
go

--打印科室增加体检中心编码
if( COL_LENGTH( 'dep_user_print', 'center_num' ) is null )
begin
	alter table dep_user_print add center_num varchar( 50 ) not null  default('001')
end
go

-- 体检中心科室关系表
if exists (select * from dbo.sysobjects where id = object_id(N'department_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table department_vs_center
go

create table department_vs_center
(
	dep_id int not null,						-- 科室ID
	center_num varchar( 50 ) not null,			-- 体检中心编码
	creater int,								-- 创建者
	create_time datetime						-- 创建时间	
	
	primary key( dep_id, center_num )
)
go

-- 体检中心收费项目关系表
if exists (select * from dbo.sysobjects where id = object_id(N'charging_item_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table charging_item_vs_center
go

create table charging_item_vs_center
(
 center_num varchar( 50 ) not null,      -- 体检中心编码
 charging_item_code varchar( 50 ) not null,    -- 收费项目编码
 price numeric( 8, 2 ) not null,       -- 统一价格即原价
 calculation_amount numeric( 8, 2 )  null,             -- 核算金额
 calculation_rate int not null,       -- 利润率
 charging_item_number int null,       -- 限制次数  
 item_discount int null,         -- 项目折扣率
 limit_num int null,          -- 每日体检人数  
 charge_inter_num varchar(20) null,      -- 系统外编码
 interface_flag varchar(45) null,      -- 接口标示
 center_price numeric( 8, 2 ) null,      -- 本体检中心价格
 his_num varchar( 50 ) null,        -- 本体检中心his关联码
 exam_num varchar( 50 ) null,       -- 本体检中心lis关联码
 view_num varchar( 50 ) null,       -- 本体检中心pacs关联码
 perform_dept varchar( 20 ) null,      -- 本体检中心执行科室编码
 creater int,           -- 创建者
 cerate_time datetime,         -- 创建时间
 updater int,           -- 更新者 
 update_time datetime         -- 更新时间

 primary key( charging_item_code, center_num )
)
go

-- examinfo_charging_item 增加“项目申请(备单)体检中心编码”、“项目检查体检中心编码”
if( COL_LENGTH( 'examinfo_charging_item', 'center_num' ) is null )
begin
	alter table examinfo_charging_item add center_num  varchar( 50 )		-- 项目申请(备单)体检中心编码
end
go

if( COL_LENGTH( 'examinfo_charging_item', 'exam_center_num' ) is null )
begin
	alter table examinfo_charging_item add exam_center_num  varchar( 50 )		-- 项目检查中心编码
end
go

-- 档案表增加“体检中心编码”
if( COL_LENGTH( 'customer_info', 'center_num' ) is null )
begin
	alter table customer_info add center_num varchar( 50 )		-- 体检中心编码
end
go

-- exam_info 增加“报到体检中心编码”
if( COL_LENGTH( 'exam_info', 'exam_center_num' ) is null )
begin
	alter table exam_info add exam_center_num  varchar( 50 )		-- 报到体检中心编码
end
go

-- 体检中心授权体检业务
if exists (select * from dbo.sysobjects where id = object_id(N'customer_exam_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table customer_exam_center
go

create table customer_exam_center
(
	center_num varchar( 50 ) not null,						-- 体检中心编码
	exam_num varchar( 50 ) not null,						-- 体检编号
	charging_item_code varchar( 50 ) not null,				-- 收费项目编码
	creater int,											-- 创建者
	create_time datetime									-- 创建时间

	primary key( center_num, exam_num, charging_item_code )
)
go

-- 批次表batch增加体检中心编码
if( COL_LENGTH( 'batch', 'center_num' ) is null )
begin
	alter table batch add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 体检套餐表exam_set增加体检中心编码
if( COL_LENGTH( 'exam_set', 'center_num' ) is null )
begin
	alter table exam_set add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 个人收费主表charging_summary_single增加收费体检中心编码
if( COL_LENGTH( 'charging_summary_single', 'center_num' ) is null )
begin
	alter table charging_summary_single add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 团体结账收费主表charging_summary_group增加体检中心编码
if( COL_LENGTH( 'charging_summary_group', 'center_num' ) is null )
begin
	alter table charging_summary_group add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 收费员日结主表cashier_daily_acc_class增加体检中心编码
if( COL_LENGTH( 'cashier_daily_acc_class', 'center_num' ) is null )
begin
	alter table cashier_daily_acc_class add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 财务部门日结主表Finance_Dep_acc增加体检中心编码
if( COL_LENGTH( 'Finance_Dep_acc', 'center_num' ) is null )
begin
	alter table Finance_Dep_acc add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 总检结论表exam_summary增加体检中心编码
if( COL_LENGTH( 'exam_summary', 'center_num' ) is null )
begin
	alter table exam_summary add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 配置表增加 center_configuration 体检中心编码
if( COL_LENGTH( 'center_configuration', 'center_num' ) is null )
begin
	alter table center_configuration add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 配置表webservice_configuration增加体检中心编码
if( COL_LENGTH( 'webservice_configuration', 'center_num' ) is null )
begin
	alter table webservice_configuration add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 编码规则表sys_param1增加体检中心编码
if( COL_LENGTH( 'sys_param1', 'center_num' ) is null )
begin
	alter table sys_param1 add center_num varchar( 50 )		-- 体检中心编码
end
go

-- 体检中心单位信息关系表
if exists (select * from dbo.sysobjects where id = object_id(N'company_info_vs_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table company_info_vs_center
go

create table company_info_vs_center
(
	company_id int not null,				-- 单位ID
	center_num varchar( 50 ) not null,		-- 体检中心编码
	is_owner char( 1 ) not null,			-- 该体检中心是否为单位信息的初始创建者，1-是，0-否，一个单位只能有一个创建者
	creater int,							-- 创建者
	create_time datetime					-- 创建时间

	primary key( center_num, company_id )
)
go

-- lis关联码表examination_item_vs_lis增加检查项目编码及体检中心编码
if( COL_LENGTH( 'examination_item_vs_lis', 'item_code' ) is null )
begin
	alter table examination_item_vs_lis add item_code varchar( 50 )		-- 检查项目编码
end
go

if( COL_LENGTH( 'examination_item_vs_lis', 'center_num' ) is null )
begin
	alter table examination_item_vs_lis add center_num varchar( 50 )	-- 体检中心编码
end
go

-- 体检中心卡信息关系表card_info_center
--if exists (select * from dbo.sysobjects where id = object_id(N'card_info_center') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
--drop table card_info_center
--go

--create table card_info_center
--(
--	card_num varchar( 50 ) not null,		-- 卡号
--	center_num varchar( 50 ) not null,		-- 体检中心编码
--	is_owner char( 1 ) not null,			-- 是否创建者，1-是，0-否，一个会员卡只能有一个创建者

--	primary key( center_num, card_num )
--)
--go

-- 卡信息表增加体检中心编码
if( COL_LENGTH( 'card_info', 'center_num' ) is null )
begin
	alter table card_info add center_num varchar( 50 ) not null			-- 体检中心编码
end
go

-- 单位帐务流水表增加体检中心编码
if( COL_LENGTH( 'company_account_detail', 'center_num' ) is null )
begin
	alter table company_account_detail add center_num varchar( 50 ) not null	-- 体检中心编码
end
go

-- 售卡交易主表增加体检中心编码
if( COL_LENGTH( 'card_sale_summary', 'center_num' ) is null )
begin
	alter table card_sale_summary add center_num varchar( 50 ) not null		-- 体检中心编码
end
go


-- 收费员日结主表增加体检中心编码
if( COL_LENGTH( 'cashier_daily_acc', 'center_num' ) is null )
begin
 alter table cashier_daily_acc add center_num varchar( 50 )
end
go

-- 作废发票表增加体检中心编码
if( COL_LENGTH( 'nullify_invoice', 'center_num' ) is null )
begin
 alter table nullify_invoice add center_num varchar( 50 )
end
go

--体检中心增加字段
if( COL_LENGTH( 'card_info', 'company_id' ) is null )
begin
	alter table card_info add company_id int not null default(0)
end
go

--取消charging_detail_single 表的item_code 
if( COL_LENGTH( 'charging_detail_single', 'item_code' ) is not null )
begin
	alter table charging_detail_single drop column item_code
end
go


