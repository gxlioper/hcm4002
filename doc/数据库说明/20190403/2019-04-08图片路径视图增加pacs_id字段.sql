ALTER view [dbo].[v_examinfo_image_path] 
as
select v.exam_info_id,v.exam_num,v.report_picture_path,ci.dep_id,d.dep_name,d.seq_code,v.pacs_id
from view_exam_detail v,pacs_detail pd,charging_item ci,department_dep d 
where v.pacs_req_code = pd.pacs_req_code and pd.chargingItem_num = ci.item_code and ci.dep_id = d.id
and v.report_picture_path is not null and v.report_picture_path <> '' and d.view_result_type = 0
union all
select v.exam_info_id,v.exam_num,vi.image_path as report_picture_path,ci.dep_id,d.dep_name,d.seq_code,v.pacs_id 
from view_exam_image vi,view_exam_detail v,pacs_detail pd,charging_item ci,department_dep d 
where v.pacs_req_code = pd.pacs_req_code and pd.chargingItem_num = ci.item_code and ci.dep_id = d.id
and vi.pacs_req_code = v.pacs_req_code and d.view_result_type = 0
union all 
select v.exam_info_id,v.exam_num,v.report_picture_path,d.id as dep_id,d.dep_name,d.seq_code,0 as pacs_id
from view_exam_detail v,department_dep d 
where v.dept_num = d.dep_num and v.report_picture_path is not null 
and v.report_picture_path <> '' and d.view_result_type = 1
union all
select v.exam_info_id,v.exam_num,vi.image_path as report_picture_path,d.id as dep_id,d.dep_name,d.seq_code,0 as pacs_id 
from view_exam_image vi,view_exam_detail v,department_dep d 
where v.dept_num = d.dep_num 
and vi.pacs_req_code = v.pacs_req_code and d.view_result_type = 1
union all 
select e.id,e.DJD_path,e.exam_num,'0' as dep_id,'µ¼¼ìµ¥' as dep_name,99999,0 as pacs_id from exam_info e 
where e.is_Active= 'Y' and e.DJD_path is not null and e.DJD_path <> ''