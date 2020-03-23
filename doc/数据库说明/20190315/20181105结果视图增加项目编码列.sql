
/****** Object:  View [dbo].[v_exam_result]    Script Date: 11/05/2018 15:17:03 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




ALTER view [dbo].[v_exam_result]
/********************
  体检结果视图
*********************/
as
    select e.exam_num, com.exam_info_id, eci.charge_item_id as charging_id, com.exam_item_id, com.exam_result, com.exam_date, eci.exam_doctor_name,
    '' as ref_value,ei.item_num
	from common_exam_detail com, examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where com.exam_info_id=e.id and eci.examinfo_id=e.id and eci.charge_item_id=ci.id and cei.charging_item_id=ci.id 
	  and cei.exam_item_id=ei.id and com.exam_item_id=ei.id and e.is_Active='Y' and eci.isActive='Y'
    union all
	select e.exam_num, ed.exam_info_id, eci.charge_item_id as charging_id, ed.exam_item_id, ed.exam_result, ed.exam_date, 
	eci.exam_doctor_name,ed.ref_value,ei.item_num
	from exam_result_detail ed,  examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where ed.exam_info_id=e.id and eci.examinfo_id=e.id and eci.charge_item_id=ci.id and cei.charging_item_id=ci.id 
	  and cei.exam_item_id=ei.id and ed.exam_item_id=ei.id and e.is_Active='Y' and eci.isActive='Y'
    union all
	select e.exam_num, v.exam_info_id, 0 as charging_id, v.exam_item_id, v.exam_result, 
	v.exam_date, v.exam_doctor,'' as ref_value,d.chargingItem_num
	from  view_exam_detail v, exam_info e,pacs_summary p,pacs_detail d
	where e.id = v.exam_info_id and v.pacs_id = p.id and p.id = d.summary_id 
	




GO


