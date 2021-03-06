USE [2020peis4002]
GO
/****** Object:  UserDefinedFunction [dbo].[GetNECIByExamId]    Script Date: 03/05/2020 21:18:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER function [dbo].[GetNECIByExamId](--未检查项目数
  @EXAM_NUM varchar(50)
)
returns int
begin
   declare @counts int
  select @counts=sum((case when(eci.exam_status='N') then 1 else 0 end)) from examinfo_charging_item eci,charging_item c where eci.exam_num= @EXAM_NUM
  and eci.pay_status<>'M'
  and eci.isActive='Y'
  and c.id=eci.charge_item_id
  and c.item_category != '耗材类型'
   return @counts
end