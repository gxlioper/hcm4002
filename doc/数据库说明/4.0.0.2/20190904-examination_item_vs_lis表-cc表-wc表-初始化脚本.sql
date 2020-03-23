--检查项目关系表初始化语句

drop trigger [trig_update_exam_item]

drop trigger [trig_insert_exam_item]

update vs set vs.center_num = '001', vs.item_code = ei.item_num
from examination_item_vs_lis vs,examination_item ei
where vs.exam_item_id = ei.id 

-- 配置表增加 center_configuration 体检中心编码
if( COL_LENGTH( 'center_configuration', 'center_num' ) is null )
begin
	alter table center_configuration add center_num varchar( 50 ) not null default '000'		-- 体检中心编码
end
go

-- 配置表webservice_configuration增加体检中心编码
if( COL_LENGTH( 'webservice_configuration', 'center_num' ) is null )
begin
	alter table webservice_configuration add center_num varchar( 50 ) not null default '000'		-- 体检中心编码
end
go