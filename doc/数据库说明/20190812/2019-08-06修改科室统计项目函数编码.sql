
/****** Object:  UserDefinedFunction [dbo].[GetNECIByExamIdAndDepid]    Script Date: 08/06/2019 14:54:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER function [dbo].[GetNECIByExamIdAndDepid](--根据科室统计未检查项目数
  @exam_num varchar(50),
  @DEP_ID int
)
returns int
begin
   declare @counts int
  select @counts=sum((case when(eci.exam_status='N') then 1 else 0 end)) from examinfo_charging_item eci,charging_item c where eci.exam_num= @exam_num
  and eci.pay_status<>'M'
  and eci.isActive='Y'
  and c.item_code=eci.charging_item_code
  and c.item_category != '耗材类型'
  and c.dep_id=@DEP_ID
   return @counts
end
GO


