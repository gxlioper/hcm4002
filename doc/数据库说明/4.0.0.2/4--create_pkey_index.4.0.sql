-- 重建主键与索引
-- 1)exam_info
exec proc_drop_primary_key 'exam_info'
exec proc_drop_index 'exam_info'

alter table exam_info add constraint pk_exam_info_exam_num primary key( exam_num )
create unique index idx_exam_info_id on exam_info ( id )
create index idx_exam_info_customer_id on exam_info ( customer_id )
create index idx_exam_info_company_id on exam_info ( company_id )
create index idx_exam_info_batch_id_group_id on exam_info ( batch_id, group_id )
create index idx_exam_info_center_num_is_Active on exam_info ( center_num, is_Active )
create index idx_exam_info_center_num_join_date on exam_info ( center_num, join_date )
create index idx_exam_info_center_num_status on exam_info ( center_num, status )
go

-- 2)customer_info
exec proc_drop_primary_key 'customer_info'
exec proc_drop_index 'customer_info'

alter table customer_info add constraint pk_customer_info_id primary key( id )
create index idx_customer_info_id_num on customer_info ( id_num )
create index idx_customer_info_user_name on customer_info ( [user_name] )
create index idx_customer_info_center_num on customer_info ( center_num )
go

-- 3)sample_exam_detail
exec proc_drop_primary_key 'sample_exam_detail'
exec proc_drop_index 'sample_exam_detail'

update sed
set sed.exam_num = e.exam_num
from sample_exam_detail sed, exam_info e
where sed.exam_info_id = e.id
and sed.exam_num is null

alter table sample_exam_detail alter column sample_barcode varchar( 45 ) not null
go

alter table sample_exam_detail alter column exam_num varchar( 45 ) not null
go

alter table sample_exam_detail add constraint pk_sample_exam_detail_exam_num_sample_barcode primary key( exam_num, sample_barcode )
create unique index idx_sample_exam_detail_id on sample_exam_detail ( id )
create unique index idx_sample_exam_detail_sample_barcode on sample_exam_detail ( sample_barcode )
go

-- 4)examResult_chargingItem
exec proc_drop_primary_key 'examResult_chargingItem'
exec proc_drop_index 'examResult_chargingItem'

alter table examResult_chargingItem alter column bar_code varchar( 45 ) not null
go

alter table examResult_chargingItem alter column charging_item_code varchar( 50 ) not null
go

alter table examResult_chargingItem add constraint pk_examResult_chargingItem_bar_code_charging_item_code_isActive primary key( bar_code, charging_item_code, isActive )
create unique index idx_examResult_chargingItem_id on examResult_chargingItem ( id )
go

-- 5)charging_item
exec proc_drop_primary_key 'charging_item'
exec proc_drop_index 'charging_item'

alter table charging_item add constraint pk_charging_item_item_code primary key( item_code )
create unique index idx_charging_item_id on charging_item( id )
go

-- 6)examinfo_charging_item
exec proc_drop_primary_key 'examinfo_charging_item'
exec proc_drop_index 'examinfo_charging_item'

alter table examinfo_charging_item add constraint pk_examinfo_charging_item_id primary key( id )
create index idx_examinfo_charging_item_exam_num_charging_item_code_isActive_pay_status on examinfo_charging_item ( exam_num, charging_item_code, isActive, pay_status )
create index idx_examinfo_charging_item_exam_date on examinfo_charging_item ( exam_date )
go

-- 7)sample_demo
exec proc_drop_primary_key 'sample_demo'
exec proc_drop_index 'sample_demo'

alter table sample_demo add constraint pk_sample_demo_id primary key( id )
go

-- 8)data_dictionary
exec proc_drop_primary_key 'data_dictionary'
exec proc_drop_index 'data_dictionary'

alter table data_dictionary add constraint pk_data_dictionary_id primary key( id )
go

-- 9)charging_item_exam_item
exec proc_drop_primary_key 'charging_item_exam_item'
exec proc_drop_index 'charging_item_exam_item'

alter table charging_item_exam_item alter column charging_item_code varchar( 50 ) not null
alter table charging_item_exam_item alter column item_code varchar( 50 ) not null
go

alter table charging_item_exam_item add constraint pk_charging_item_exam_item_charging_item_code_item_code primary key( charging_item_code, item_code )
create index idx_charging_item_exam_item_id on charging_item_exam_item ( id )
go

-- 10)examination_item
exec proc_drop_primary_key 'examination_item'
exec proc_drop_index 'examination_item'

alter table examination_item add constraint pk_examination_item_item_num primary key( item_num )
create index idx_examination_item_id on examination_item( id )
go

-- 11)examination_item_vs_lis
exec proc_drop_primary_key 'examination_item_vs_lis'
exec proc_drop_index 'examination_item_vs_lis'

update eivl
set eivl.item_code = ei.item_num
from examination_item_vs_lis eivl, examination_item ei
where eivl.exam_item_id = ei.id
and eivl.item_code is null
go

delete from examination_item_vs_lis where item_code is null
go

alter table examination_item_vs_lis alter column item_code varchar( 50 ) not null
go

alter table examination_item_vs_lis alter column center_num varchar( 50 ) not null
go

alter table examination_item_vs_lis add constraint pk_examination_item_vs_lis_center_num_item_code_lis_item_id primary key( center_num, item_code, lis_item_id )
go

-- 12)exam_result_detail
exec proc_drop_primary_key 'exam_result_detail'
exec proc_drop_index 'exam_result_detail'

alter table exam_result_detail alter column exam_num varchar( 45 ) not null
alter table exam_result_detail alter column charging_item_code varchar( 50 ) not null
alter table exam_result_detail alter column item_code varchar( 50 ) not null
go

alter table exam_result_detail add constraint pk_exam_result_detail_exam_num_charging_item_code_item_code primary key( exam_num, charging_item_code, item_code )
create unique index idx_exam_result_detail_id on exam_result_detail ( id )
go

-- 13)pacs_summary
exec proc_drop_primary_key 'pacs_summary'
exec proc_drop_index 'pacs_summary'

alter table pacs_summary alter column pacs_req_code varchar( 20 ) not null
go

alter table pacs_summary add constraint pk_pacs_summary_examinfo_num_pacs_req_code primary key( examinfo_num, pacs_req_code )
create unique index idx_pacs_summary_id on pacs_summary ( id )
create unique index idx_pacs_summary_pacs_req_code on pacs_summary ( pacs_req_code )
go

-- 14)pacs_detail
exec proc_drop_primary_key 'pacs_detail'
exec proc_drop_index 'pacs_detail'

alter table pacs_detail alter column pacs_req_code varchar( 20 ) not null
go

alter table pacs_detail add constraint pk_pacs_detail_examinfo_num_pacs_req_code_chargingItem_num primary key( examinfo_num, pacs_req_code, chargingItem_num )
create unique index idx_pacs_detail_id on pacs_detail ( id )
create index idx_pacs_detail_pacs_req_code_chargingItem_num on pacs_detail ( pacs_req_code, chargingItem_num )
go

-- 15)view_exam_detail
exec proc_drop_primary_key 'view_exam_detail'
exec proc_drop_index 'view_exam_detail'

alter table view_exam_detail alter column exam_num varchar( 45 ) not null
alter table view_exam_detail alter column pacs_req_code varchar( 20 ) not null
go

alter table view_exam_detail add constraint pk_view_exam_detail_exam_num_pacs_req_code primary key( exam_num, pacs_req_code )
create unique index idx_view_exam_detail_id on view_exam_detail ( id )
go

-- 16)view_exam_image
exec proc_drop_primary_key 'view_exam_image'
exec proc_drop_index 'view_exam_image'

delete from view_exam_image where ltrim( rtrim ( image_path ) ) = ''

alter table view_exam_image alter column exam_num varchar( 45 ) not null
alter table view_exam_image alter column pacs_req_code varchar( 20 ) not null
go

alter table view_exam_image add constraint pk_view_exam_image_exam_num_pacs_req_code_image_path primary key( exam_num, pacs_req_code, image_path )
create unique index idx_view_exam_image_id on view_exam_image ( id )
go

-- 17)batch
exec proc_drop_primary_key 'batch'
exec proc_drop_index 'batch'

alter table batch add constraint pk_batch_batch_num primary key( batch_num )
create unique index idx_batch_company_id_batch_num on batch( company_id, batch_num )
create unique index idx_batch_id on batch ( id )
create index idx_batch_batch_name on batch ( batch_name )
go

-- 18)company_info
exec proc_drop_primary_key 'company_info'
exec proc_drop_index 'company_info'

alter table company_info alter column com_num varchar( 60 ) not null
go

alter table company_info add constraint pk_company_info_com_num primary key( com_num )
create unique index idx_company_info_id on company_info ( id )
create index idx_company_info_com_name on company_info ( com_name )
go

-- 19)group_info
exec proc_drop_primary_key 'group_info'
exec proc_drop_index 'group_info'

alter table group_info add constraint pk_group_info_batch_id_id_isActive primary key( batch_id, id, isActive )
go

-- 20)group_set
exec proc_drop_primary_key 'group_set'
exec proc_drop_index 'group_set'

alter table group_set add constraint pk_group_set_id primary key( id )
create index idx_group_set_group_id_exam_set_id on group_set ( group_id, exam_set_id, isActive )
go

-- 21)group_charging_item
exec proc_drop_primary_key 'group_charging_item'
exec proc_drop_index 'group_charging_item'

alter table group_charging_item add constraint pk_group_charging_item_id primary key( id )
create index idx_group_charging_item_group_id_charge_item_id_isActive on group_charging_item ( group_id, charge_item_id, isActive )
go

-- 22)examinfo_batch
exec proc_drop_primary_key 'examinfo_batch'
exec proc_drop_index 'examinfo_batch'

alter table examinfo_batch alter column exam_num varchar( 45 ) not null
go

alter table examinfo_batch add constraint pk_examinfo_batch_batch_id_exam_num primary key( batch_id, exam_num )
create unique index idx_examinfo_batch_id on examinfo_batch ( id )
go

-- 23)examinfo_disease
--exec proc_drop_primary_key 'examinfo_disease'
--exec proc_drop_index 'examinfo_disease'

alter table examinfo_disease alter column exam_num varchar( 45 ) not null
go

alter table examinfo_disease alter column app_type varchar( 1 ) not null
go

--alter table examinfo_disease add constraint pk_examinfo_disease_exam_num_app_type_disease_index primary key( exam_num, app_type, disease_index )
--create index idx_examinfo_disease_disease_id on examinfo_disease ( disease_id )
--create unique index idx_examinfo_disease_id on examinfo_disease( id )
--go

-- 24)department_dep
exec proc_drop_primary_key 'department_dep'
exec proc_drop_index 'department_dep'

alter table department_dep add constraint pk_department_dep_id primary key( id )
go

-- 25)common_exam_detail
exec proc_drop_primary_key 'common_exam_detail'
exec proc_drop_index 'common_exam_detail'

alter table common_exam_detail alter column exam_num varchar( 45 ) not null
alter table common_exam_detail alter column charging_item_code varchar( 50 ) not null
alter table common_exam_detail alter column item_code varchar( 50 ) not null
go

alter table common_exam_detail add constraint pk_common_exam_detail_exam_num_charging_item_code_item_code primary key( exam_num, charging_item_code, item_code )
create unique index idx_common_exam_detail_id on common_exam_detail ( id )
go

-- 26)exam_dep_result
exec proc_drop_primary_key 'exam_dep_result'
exec proc_drop_index 'exam_dep_result'

alter table exam_dep_result alter column exam_num varchar( 45 ) not null
alter table exam_dep_result alter column app_type varchar( 1 ) not null
go

alter table exam_dep_result add constraint pk_exam_dep_result_exam_num_dep_id_app_type primary key( exam_num, dep_id, app_type )
create unique index idx_exam_dep_result_id on exam_dep_result ( id )
go

-- 27)exam_summary
exec proc_drop_primary_key 'exam_summary'
exec proc_drop_index 'exam_summary'

alter table exam_summary alter column exam_num varchar( 45 ) not null
alter table exam_summary alter column app_type varchar( 1 ) not null
go

alter table exam_summary add constraint pk_exam_summary_exam_num_app_type primary key( exam_num, app_type )
create unique index idx_exam_summary_id on exam_summary( id )
go

-- 28)examinfo_set
exec proc_drop_primary_key 'examinfo_set'
exec proc_drop_index 'examinfo_set'

alter table examinfo_set add constraint pk_examinfo_set_id primary key( id )
create index idx_examinfo_set_exam_num_exam_set_id_app_type_isActive on examinfo_set( exam_num, exam_set_id, app_type, isActive )
go
