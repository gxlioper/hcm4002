create function [dbo].[GetItemNameByExamIdAndDepid](--根据科室体检号获取当前科室的项目名称
  @examinfo_id int,
  @DEP_NUM varchar(30)
)
returns varchar(200)
begin
   declare @item_names varchar(200)
   set @item_names=''
   declare @item_name varchar(200)
   DECLARE order_cursor CURSOR FAST_FORWARD FOR
   ( select ci.item_name from examinfo_charging_item eci,charging_item ci,department_dep dd where ci.id=eci.charge_item_id
and dd.id=ci.dep_id
and eci.isActive='Y'
and eci.pay_status<>'M'
and dd.dep_num=@DEP_NUM
and eci.examinfo_id=@examinfo_id
  and ci.item_category != '耗材类型')
  
  OPEN order_cursor;
  FETCH NEXT FROM order_cursor INTO @item_name;

WHILE @@FETCH_STATUS=0
  begin
     set @item_names=@item_names+','+@item_name;
      -- 取下一条记录
      FETCH NEXT FROM order_cursor INTO @item_name;
    END
-- 关闭游标
CLOSE order_cursor;

-- 释放游标
DEALLOCATE order_cursor;
    
   return @item_names
end