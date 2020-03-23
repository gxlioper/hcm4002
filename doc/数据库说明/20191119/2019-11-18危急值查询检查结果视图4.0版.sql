
/****** Object:  View [dbo].[v_critical_exam_result]    Script Date: 11/18/2019 16:40:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



create VIEW [dbo].[v_critical_exam_result_num]
AS
	select e.exam_num,ei.item_name,com.exam_result,ei.item_num,ci.item_code,'' as ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from common_exam_detail com, examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where com.exam_num=e.exam_num and eci.exam_num=e.exam_num and eci.charging_item_code=ci.item_code and cei.charging_item_code=ci.item_code 
	and cei.item_code=ei.item_num and com.item_code=ei.item_num and e.is_Active='Y' and eci.isActive='Y'
	union all
	select e.exam_num,ei.item_name,ed.exam_result,ei.item_num,ci.item_code,ed.ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from exam_result_detail ed,examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where ed.exam_num=e.exam_num and eci.exam_num=e.exam_num and eci.charging_item_code=ci.item_code and cei.charging_item_code=ci.item_code 
	and cei.item_code=ei.item_num and ed.item_code=ei.item_num and e.is_Active='Y' and eci.isActive='Y'
	union all
	select p.examinfo_num,ci.item_name,v.exam_result,ei.item_num,ci.item_code,'' as ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from pacs_detail p, view_exam_detail v, charging_item ci,charging_item_exam_item cei,examination_item ei
	where p.pacs_req_code=v.pacs_req_code and p.chargingItem_num=ci.item_code and ci.item_code = cei.charging_item_code
	and cei.item_code = ei.item_num and ei.item_result_type = '1' 


GO


