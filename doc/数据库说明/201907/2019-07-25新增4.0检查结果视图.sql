
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create view [dbo].[v_exam_result_new]
/********************
 检查结果视图4.0版本
*********************/
as
	select e.exam_num,ei.item_name,com.exam_result,ei.item_num,ci.item_code,'' as ref_value,com.exam_date, eci.exam_doctor_name,ei.seq_code,ci.item_seq,dd.seq_code as dep_seq
	from common_exam_detail com, examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e,department_dep dd
	where com.exam_num=e.exam_num and eci.exam_num=e.exam_num and eci.charging_item_code=ci.item_code and cei.charging_item_code=ci.item_code and ci.dep_id = dd.id
	and cei.item_code=ei.item_num and com.item_code=ei.item_num and e.is_Active='Y' and eci.isActive='Y' 
	union all
	select e.exam_num,ei.item_name,ed.exam_result,ei.item_num,ci.item_code,ed.ref_value,ed.exam_date,eci.exam_doctor_name,ei.seq_code,ci.item_seq,dd.seq_code as dep_seq
	from exam_result_detail ed,examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e,department_dep dd
	where ed.exam_num=e.exam_num and eci.exam_num=e.exam_num and eci.charging_item_code=ci.item_code and cei.charging_item_code=ci.item_code and ci.dep_id = dd.id
	and cei.item_code=ei.item_num and ed.item_code=ei.item_num and e.is_Active='Y' and eci.isActive='Y' 
	union all
	select p.examinfo_num,ci.item_name,v.exam_result,ei.item_num,ci.item_code,'' as ref_value,v.exam_date, v.exam_doctor,ei.seq_code,ci.item_seq,dd.seq_code as dep_seq
	from pacs_detail p, view_exam_detail v, charging_item ci,charging_item_exam_item cei,examination_item ei,department_dep dd
	where p.summary_id=v.pacs_id and p.chargingItem_num=ci.item_code and ci.item_code = cei.charging_item_code and ci.dep_id = dd.id
	and cei.item_code = ei.item_num and ei.item_result_type = 1 
GO


/*****************
select * from v_exam_result_new v where v.exam_num = '1907250002'
*****************/