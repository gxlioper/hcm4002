
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



create view [dbo].[v_daily_cashier_acc_way]
--�շ�Ա���ս��շѷ�ʽ��ͼ
as
  select w.daily_acc_num,w.charging_way,sum(w.amonut) as amount from cashier_daily_acc_payway w,cashier_daily_acc c 
  where w.daily_acc_num = c.daily_acc_num group by w.daily_acc_num,w.charging_way
GO


