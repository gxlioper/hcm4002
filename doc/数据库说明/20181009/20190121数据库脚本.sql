alter table examinfo_charging_item add inputter int not null default(0)

go
alter table sample_exam_detail add exam_num varchar(16)  --体检号
go
alter table common_exam_detail add exam_num varchar(16)
go
alter table exam_result_detail add exam_num varchar(16)
go
alter table view_exam_detail add exam_num varchar(16)
go
alter table exam_summary add exam_num varchar(16)
go
alter table examinfo_disease add exam_num varchar(16)
go

alter table pacs_detail add pacs_req_code varchar(20)   --申请单号

go

alter table examResult_chargingItem add bar_code varchar(20)  --条码号
go
alter table department_dep add teshu varchar(20)
go
alter table examination_item add rz_seq int
go
alter table examination_item add dept_id int



----------------------------yangm 20190122 增加exam_num-----------------------------
--卡流水表
alter table card_deal add exam_num varchar(20)
--个人收费表
alter table charging_summary_single  add exam_num varchar(20)
--普通科室检查
alter table common_exam_detail add exam_num varchar(20)
--危机值表
alter table exam_Critical_detail add exam_num varchar(20)
--部门结果表
alter table exam_dep_result add exam_num varchar(20)
--流程表
alter table exam_flow add exam_num varchar(20)
--导检单回收
alter table exam_info_recycling_guid add exam_num varchar(20)
--复查登记表
alter table exam_info_repeat add exam_num varchar(20)
--检验结果表
alter table exam_result_detail add exam_num varchar(20)
--总检结论表
alter table exam_summary add exam_num varchar(20)
--体检者对应批次表    此表是否可以去掉
alter table examinfo_batch add exam_num varchar(20)
--体检对应收费项目表
alter table examinfo_charging_item add exam_num varchar(20)
--科室疾病库
alter table examInfo_dept_disease add exam_num varchar(20)
--体检者对应疾病表
alter table examinfo_disease add exam_num varchar(20)
--检查医生表
alter table examinfo_doctor add exam_num varchar(20)
--检查报告打印流水表
alter table examinfo_print_report add exam_num varchar(20)
--体检者对应套餐关系表
alter table examinfo_set add exam_num varchar(20)

alter table generate_Reports add exam_num varchar(20)
--人员分组日志表
alter table log_exam_group add exam_num varchar(20)
--lis与第三方对接流水表
alter table zl_req_item add exam_num varchar(20)
--pacs与第三方对接流水表
alter table zl_req_pacs_item add exam_num varchar(20)
--职业病总检结果表
alter table zyb_exam_summary_result add exam_num varchar(20)
--职业病问诊结果表
alter table zyb_inquisition_result add exam_num varchar(20)

------------------------yangm 20190124--------------eci表  
alter table examinfo_charging_item add paytype int not null default(0) --付费方式0体检付费1 第三方付费 2混合付费

alter table examinfo_charging_item add paypeis decimal(10, 4) not null default(0) --体检付费金额
alter table examinfo_charging_item add paypeisstatus int not null default(0) --体检付费状态

alter table examinfo_charging_item add paythird decimal(10, 4) not null default(0) --第三方付费金额
alter table examinfo_charging_item add paythirdstatus int not null default(0) --第三方付费状态

------------------------yangm 20190124---------------------------
--团体结算收费项目明细表，暂时无用
alter table charging_detail_group add charging_item_code varchar(50) 
--个人结算收费项目明细表
alter table charging_detail_single add charging_item_code varchar(50)
--检查项目收费项目关系表
alter table charging_item_exam_item add charging_item_code varchar(50)
--检查项目排期表
alter table charging_item_schedule add charging_item_code varchar(50)
--普通科室
alter table common_exam_detail add charging_item_id int not null default(0)

alter table common_exam_detail add charging_item_code varchar(50)
--体检疾病库
alter table disease_knowloedge_lib add charging_item_code varchar(50)
--体检疾病与收费项目、检查项目关系表
alter table disease_logic_exam_item add charging_item_code varchar(50)
--危机值表
alter table exam_Critical_detail add charging_item_code varchar(50)
--检验结果表
alter table exam_result_detail add charging_item_code varchar(50)
--收费项目  每天检查上限设置表
alter table limit_charging_item add charging_item_code varchar(50)
--套餐对应收费项目关系表
alter table set_charging_item add charging_item_code varchar(50)
--团体结算人员对应收费项目表
alter table team_account_item_list add charging_item_code varchar(50)
--体检者样本与收费项目关系表
alter table examResult_chargingItem add charging_item_code varchar(50)
--样本对应耗材类收费项目表，样本自动带出耗材
alter table sample_demo_charging_item add charging_item_code varchar(50)

------------------------------检查项目-------------------------------------
 
--检查项目对应收费项目关系表
alter table charging_item_exam_item     add item_code varchar(50)  
--普通科室检查结果表
alter table common_exam_detail          add item_code varchar(50)  
--科室逻辑表
alter table dep_logic_exam_item         add item_code varchar(50)  
--疾病逻辑表
alter table disease_logic_exam_item     add item_code varchar(50)  
--危机值表
alter table exam_Critical_detail        add item_code varchar(50)  
--检查项目文本型参考值和危机值
alter table exam_item_RefandDang        add item_code varchar(50)
--体检检验项目明细表
alter table exam_result_detail          add item_code varchar(50)  
--普通检查项目结果词库
alter table item_result_lib             add item_code varchar(50)
--pacs 检查结果表
alter table view_exam_detail            add item_code varchar(16)
--pacs 检查细项结果表
alter table view_exam_item              add item_code varchar(50)  