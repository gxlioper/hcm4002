
/****** Object:  UserDefinedFunction [dbo].[GetNECIByExamIdAndDepid]    Script Date: 11/22/2019 09:37:42 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER function [dbo].[GetNECIByExamIdAndDepid](--根据科室统计未检查项目数
  @EXAM_NUM varchar(50),
  @DEP_ID int
)
returns int
begin
   declare @counts int
  select @counts=sum((case when(eci.exam_status='N') then 1 else 0 end)) from examinfo_charging_item eci,charging_item c where eci.exam_num= @EXAM_NUM
  and eci.pay_status<>'M'
  and eci.isActive='Y'
  and c.item_code=eci.charging_item_code
  and c.item_category != '耗材类型'
  and c.dep_id=@DEP_ID
   return @counts
end
GO


