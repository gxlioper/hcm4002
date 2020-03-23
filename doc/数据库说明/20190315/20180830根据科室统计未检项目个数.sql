GO
create function [dbo].[GetNECIByExamIdAndDepid](--根据科室统计未检查项目数
  @ID int,
  @DEP_ID int
)
returns int
begin
   declare @counts int
  select @counts=sum((case when(eci.exam_status='N') then 1 else 0 end)) from examinfo_charging_item eci,charging_item c where eci.examinfo_id= @ID
  and eci.pay_status<>'M'
  and eci.isActive='Y'
  and c.id=eci.charge_item_id
  and c.item_category != '耗材类型'
  and c.dep_id=@DEP_ID
   return @counts
end