
alter table cashier_daily_acc_invoice add invoice_amount decimal(20, 2)
			
update cashier_daily_acc_invoice set invoice_amount = 0
update cashier_daily_acc_invoice set invoice_amount = (select c.invoice_amount from charging_invoice_single c where c.invoice_class = cashier_daily_acc_invoice.charging_way_id and c.invoice_num = cashier_daily_acc_invoice.invoice_num) where invoice_status <> 'B'