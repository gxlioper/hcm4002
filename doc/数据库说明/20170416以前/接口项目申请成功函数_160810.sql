create function fun_GetSuccReqItem(
  /**************
    获取接口项目申请成功的项目列表
   **************/
  @exam_info_id int,  --体检ID
  @dep_category int   --项目类型ID (131：检验；21：pacs项目)
)
 returns @UnReqTable table(
   item_id int,
   item_code varchar(20),
   item_name varchar(200)
 )
as
begin
  insert into @UnReqTable(item_id, item_code, item_name)
  select eci.charge_item_id, ci.item_code, ci.item_name
  from examinfo_charging_item eci, charging_item ci
  where eci.charge_item_id=ci.id and ci.isActive='Y' and ci.interface_flag=2 
    and dep_category=@dep_category and eci.examinfo_id=@exam_info_id and eci.is_application='Y' 
  
  return;
end