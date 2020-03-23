

/****** Object:  View [dbo].[v_Result]    Script Date: 09/20/2019 14:27:10 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


create VIEW [dbo].[v_critical_exam_result]
AS
	select e.exam_num,ei.item_name,com.exam_result,ei.item_num,ci.item_code,'' as ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from common_exam_detail com, examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where com.exam_info_id=e.id and eci.examinfo_id=e.id and eci.charge_item_id=ci.id and cei.charging_item_id=ci.id 
	and cei.exam_item_id=ei.id and com.exam_item_id=ei.id and e.is_Active='Y' and eci.isActive='Y'
	union all
	select e.exam_num,ei.item_name,ed.exam_result,ei.item_num,ci.item_code,ed.ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from exam_result_detail ed,examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, examination_item ei, exam_info e
	where ed.exam_info_id=e.id and eci.examinfo_id=e.id and eci.charge_item_id=ci.id and cei.charging_item_id=ci.id 
	and cei.exam_item_id=ei.id and ed.exam_item_id=ei.id and e.is_Active='Y' and eci.isActive='Y'
	union all
	select p.examinfo_num,ci.item_name,v.exam_result,ei.item_num,ci.item_code,'' as ref_value,(case when ei.item_unit is null then '' else ei.item_unit end) item_unit
	from pacs_detail p, view_exam_detail v, charging_item ci,charging_item_exam_item cei,examination_item ei
	where p.summary_id=v.pacs_id and p.chargingItem_num=ci.item_code and ci.id = cei.charging_item_id
	and cei.exam_item_id = ei.id and ei.id = 347 

GO

