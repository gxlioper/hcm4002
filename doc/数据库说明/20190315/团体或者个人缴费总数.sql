/****** Object:  UserDefinedFunction [dbo].[fun_GetExaminfoChargingItemCount]    Script Date: 03/17/2019 13:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--��ѯ������߸��˽ɷ�����
ALTER function [dbo].[fun_GetExaminfoChargingItemCount](
  @exam_id varchar(20),
  @exam_num varchar(20),
  @isnotpayflag int --0 ��ʾ���������������Ŀ 1��ʾ���������������Ŀ 2��ʾ�Ѽ��� 3��ʾ��������+�Ѽ�
  ) returns int
begin
   declare @doc int
   if @isnotpayflag=0 --����������Ŀ
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
	       and a.exam_status<>'G'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   )
  else if @isnotpayflag=1 --���������������
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   )
  else if @isnotpayflag=2 --�Ѽ���
     set @doc=(select COUNT(*) as counts from examinfo_charging_item a 
	    where  a.isActive='Y' and a.examinfo_id=@exam_id
	       and a.exam_indicator in ('T','GT')
	       and a.pay_status<>'M'
	       and a.exam_status='Y'
           and not exists (select * from team_account_item_list l where l.exam_num=@exam_num 
           and a.charge_item_id=l.charging_item_id) 
	   ) 
   else if @isnotpayflag=3 --��ʾ��������+�Ѽ�
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