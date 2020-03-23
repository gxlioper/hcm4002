/****** Object:  UserDefinedFunction [dbo].[fun_GetExaminfoChargingItemCount]    Script Date: 03/17/2019 13:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--查询团体或者个人缴费总数
ALTER function [dbo].[fun_GetExaminfoChargingItemCount](
  @exam_id varchar(20),
  @exam_num varchar(20),
  @isnotpayflag int --0 表示不含弃检的所有项目 1表示包含弃检的所有项目 2表示已检项 3表示包含弃检+已检
  ) returns int
begin
   declare @doc int
   if @isnotpayflag=0 --不含弃检项目
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
	       and a.exam_status<>'G'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   )
  else if @isnotpayflag=1 --包含弃检的所有项
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   )
  else if @isnotpayflag=2 --已检项
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
	       and a.exam_status='Y'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   ) 
   else if @isnotpayflag=3 --表示包含弃检+已检
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
	       and a.exam_status in ('Y','G')
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   ) 
   return @doc
 end