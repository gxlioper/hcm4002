CREATE INDEX index_examinfo_djd_path ON exam_info (DJD_path) --exam_info表增加导检单路径索引
--新增图片路径视图
create view v_examinfo_image_path 
as
select v.exam_info_id,v.report_picture_path,ci.dep_id,d.dep_name,d.seq_code 
from view_exam_detail v,pacs_detail pd,charging_item ci,department_dep d 
where v.pacs_id = pd.summary_id and pd.chargingItem_num = ci.item_code and ci.dep_id = d.id
and v.report_picture_path is not null and v.report_picture_path <> '' and d.view_result_type = 0
union all
select v.exam_info_id,vi.image_path as report_picture_path,ci.dep_id,d.dep_name,d.seq_code 
from view_exam_image vi,view_exam_detail v,pacs_detail pd,charging_item ci,department_dep d 
where v.pacs_id = pd.summary_id and pd.chargingItem_num = ci.item_code and ci.dep_id = d.id
and vi.view_exam_id = v.id and d.view_result_type = 0
union all 
select v.exam_info_id,v.report_picture_path,d.id as dep_id,d.dep_name,d.seq_code 
from view_exam_detail v,department_dep d 
where v.dept_num = d.dep_num and v.report_picture_path is not null 
and v.report_picture_path <> '' and d.view_result_type = 1
union all
select v.exam_info_id,vi.image_path as report_picture_path,d.id as dep_id,d.dep_name,d.seq_code 
from view_exam_image vi,view_exam_detail v,department_dep d 
where v.dept_num = d.dep_num 
and vi.view_exam_id = v.id and d.view_result_type = 1
union all 
select e.id,e.DJD_path,'0' as dep_id,'导检单' as dep_name,99999 from exam_info e 
where e.is_Active= 'Y' and e.DJD_path is not null and e.DJD_path <> ''
go