if exists (select * from dbo.sysobjects 
	where id = object_id(N'pro_cashier_daily') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure pro_cashier_daily
go

create procedure pro_cashier_daily(
  /****************收费员日结存储过程*****************/
  @userid int,     --收费员id
  @center_num varchar(50), --体检中心编码
  @status int out,  --执行结束 0成功，1失败
  @error_text varchar(500) out --失败原因
)
as 
begin

	declare @daily_acc_num varchar(20),
	        @daily_acc_amount decimal(20,2),
	        @daily_class int
	
	declare @daily_acc_class_num varchar(20),
			@daily_acc_class_amount decimal(20,2),
			@charging_way int,
			@way_amonut decimal(20,2),
			@data_code_children varchar(50)
	
	declare @exam_num varchar(20),
	        @summary_id int,
	        @team_acc_num varchar(20),
	        @amount decimal(20,2)
	        
	declare @invoice_class int,
			@invoice_num varchar(20),
			@invoice_status varchar(1),
			@invoice_amount decimal(20,2),
			@invoice_id int
			
	declare @daily_acc_class_num2 varchar(20),
			@daily_acc_class_amount2 decimal(20,2),
			@deal_id varchar(50),
			@deal_amount decimal(20,2)
	set @status = 0
	set @error_text = '收费员日结成功'
	
	BEGIN TRAN Tran_auto
	  begin try
	  
		set @daily_acc_amount = 0
		
		/***********************************************
		**************收费处个人收费日结****************
		***********************************************/
		set @daily_acc_class_amount = 0
		set @daily_acc_class_amount2 = 0
		
		--保存个人收费方式汇总
		declare charging_way_single_cursor cursor for
		select w.charging_way,sum(case charging_status when 'Y' then w.amount else -1*amount end) as amount
		from charging_summary_single c,charging_way_single w 
		where c.id = w.summary_id and c.daily_status = 0 and c.is_active <> 'C' and c.cashier = @userid and c.center_num=@center_num group by w.charging_way   
        
        open charging_way_single_cursor
        fetch next from charging_way_single_cursor into @charging_way,@way_amonut
        while @@FETCH_STATUS = 0
        begin
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			if @daily_acc_class_num is null
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num out
			insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
			values(@daily_acc_num,@daily_acc_class_num,@charging_way,@way_amonut)
			
			if @charging_way = '122' --判断是否存在会员卡收费
			begin
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num2 out
				insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
				values(@daily_acc_num,@daily_acc_class_num2,@charging_way,-1*@way_amonut)
			end
			
			fetch next from charging_way_single_cursor into @charging_way,@way_amonut
        end
        close charging_way_single_cursor
        deallocate charging_way_single_cursor
		
		--保存个人收费明细
	    declare charging_single_cursor cursor for
		select e.exam_num,c.id as summary_id,(case charging_status when 'Y' then amount2 else -1*amount2 end) as amount,c.req_num
		from charging_summary_single c,exam_info e 
		where c.daily_status = 0 and c.is_active <> 'C' and c.cashier = @userid and c.center_num=@center_num and c.exam_id = e.id
	
		open charging_single_cursor 
		fetch next from charging_single_cursor into @exam_num,@summary_id,@amount,@team_acc_num
		while @@FETCH_STATUS = 0	
		begin 
		  set @daily_acc_class_amount = @daily_acc_class_amount + @amount
		  
		  insert into cashier_daily_acc_list(daily_acc_num,exam_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
		  values(@daily_acc_num,@exam_num,@summary_id,'G',@team_acc_num,GETDATE(),@userid,@daily_acc_class_num,@amount)
		  
		  update charging_summary_single set daily_status = '1' where id = @summary_id
		  
		  if @daily_acc_class_num2 is not null
		  begin
			declare card_deal_cursor cursor for
			select c.id,-1*c.amount from card_deal c where c.summary_id = @summary_id and c.trancode = '001' and c.deal_type in ('6','7')
			
			open card_deal_cursor
			fetch next from card_deal_cursor into @deal_id,@deal_amount
			while @@FETCH_STATUS = 0
			begin
				set @daily_acc_class_amount2 = @daily_acc_class_amount2 + @deal_amount
				insert into cashier_daily_acc_list(daily_acc_num,exam_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
				values(@daily_acc_num,@exam_num,0,'X',@deal_id,GETDATE(),@userid,@daily_acc_class_num2,@deal_amount)
				
				fetch next from card_deal_cursor into @deal_id,@deal_amount
			end
			
			close card_deal_cursor
			deallocate card_deal_cursor
			
		  end
		  
		  fetch next from charging_single_cursor into @exam_num,@summary_id,@amount,@team_acc_num
		end
		close charging_single_cursor
		deallocate charging_single_cursor
		
		if @daily_acc_class_num is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num,@daily_acc_class_amount,@userid,GETDATE(),'001','101',@center_num)
			
			set @daily_acc_class_num = null
		end
		
		if @daily_acc_class_num2 is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount2
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num2,@daily_acc_class_amount2,@userid,GETDATE(),'003','102',@center_num)
			
			set @daily_acc_class_num2 = null
		end
		
		/**************************************************
		******************团体结账日结*********************
		**************************************************/
		set @daily_acc_class_amount = 0
		set @daily_acc_class_amount2 = 0
		
		--保存收费方式汇总
		declare charging_way_group_cursor cursor for
		select w.charging_way,sum(w.amount) as amount from charging_summary_group cs,charging_way_group w 
        where cs.id = w.summary_id and cs.charging_status = 'Y' and cs.daily_status = '0' and 
        cs.receiv_status = '1' and cs.is_active = 'Y' and cs.cashier = @userid and cs.center_num=@center_num group by w.charging_way
        
        open charging_way_group_cursor
        fetch next from charging_way_group_cursor into @charging_way,@way_amonut
        while @@FETCH_STATUS = 0
        begin
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			if @daily_acc_class_num is null
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num out
				
			insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
			values(@daily_acc_num,@daily_acc_class_num,@charging_way,@way_amonut)
			
			select @data_code_children = d.data_code_children from data_dictionary d where d.id = @charging_way
			if @data_code_children = '372' --判断是否存在团体划账收费方式
			begin
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num2 out
				insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
				values(@daily_acc_num,@daily_acc_class_num2,@charging_way,-1*@way_amonut)
			end
			
			fetch next from charging_way_group_cursor into @charging_way,@way_amonut
        end
        close charging_way_group_cursor
        deallocate charging_way_group_cursor
        
        --保存团体结账收费明细
		declare charging_group_cursor cursor for
		select cs.account_num,cs.id as summary_id,cs.amount2 from charging_summary_group cs 
        where cs.charging_status = 'Y' and cs.daily_status = '0' and cs.receiv_status = '1' 
        and cs.is_active = 'Y' and cs.cashier = @userid and cs.center_num=@center_num
        
        open charging_group_cursor
        fetch next from charging_group_cursor into @team_acc_num,@summary_id,@amount
		while @@FETCH_STATUS = 0
		begin 
		  set @daily_acc_class_amount = @daily_acc_class_amount + @amount
		  
		  insert into cashier_daily_acc_list(daily_acc_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
		  values(@daily_acc_num,@summary_id,'T',@team_acc_num,GETDATE(),@userid,@daily_acc_class_num,@amount)
		  
		  update charging_summary_group set daily_status = '1' where id = @summary_id
		  
		  if @daily_acc_class_num2 is not null
		  begin
			
			declare com_acc_remit_cursor cursor for
			select c.jnnumber,case when c.trancode = '002' then -1*c.trantmt else trantmt end amount from 
			company_account_detail c where c.trancode in ('002','004') and account_num = @team_acc_num
			
			open com_acc_remit_cursor
			fetch next from com_acc_remit_cursor into @deal_id,@deal_amount
			while @@FETCH_STATUS = 0
			begin
				set @daily_acc_class_amount2 = @daily_acc_class_amount2 + @deal_amount
				insert into cashier_daily_acc_list(daily_acc_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
				values(@daily_acc_num,0,'H',@deal_id,GETDATE(),@userid,@daily_acc_class_num2,@deal_amount)
				
				update company_account_detail set daily_status = '1' where jnnumber = @deal_id
				fetch next from com_acc_remit_cursor into @deal_id,@deal_amount
			end
		  end 
		  fetch next from charging_group_cursor into @team_acc_num,@summary_id,@amount
		end
		close charging_group_cursor
		deallocate charging_group_cursor
		
		if @daily_acc_class_num is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num,@daily_acc_class_amount,@userid,GETDATE(),'002','101',@center_num)
			
			set @daily_acc_class_num = null
		end
		if @daily_acc_class_num2 is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount2
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num2,@daily_acc_class_amount2,@userid,GETDATE(),'004','102',@center_num)
			
			set @daily_acc_class_num2 = null
		end
		/*******************************************************
		******************售卡交易收费员日结********************
		*******************************************************/
		set @daily_acc_class_amount = 0
		
		--保存收费方式
		declare card_sale_way_cursor cursor for
		select w.charging_way,sum(w.amount) amount from card_sale_summary c,card_sale_way w 
		where c.sale_trade_num = w.sale_trade_num and sale_status = 1 and daily_status = 0
		and sale_user = @userid and c.center_num=@center_num group by w.charging_way
		
		open card_sale_way_cursor 
		fetch next from card_sale_way_cursor into @charging_way,@way_amonut
		while @@FETCH_STATUS = 0
		begin
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			if @daily_acc_class_num is null
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num out
				
			insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
			values(@daily_acc_num,@daily_acc_class_num,@charging_way,@way_amonut)
			fetch next from card_sale_way_cursor into @charging_way,@way_amonut
		end
		
		close card_sale_way_cursor
		deallocate card_sale_way_cursor
		
		--保存售卡明细
		declare card_sale_cursor cursor for
		select c.sale_trade_num,c.sale_amount from card_sale_summary c where 
		c.sale_status = 1 and c.daily_status = 0 and c.sale_user = @userid and c.center_num=@center_num
		
		open card_sale_cursor
		fetch next from card_sale_cursor into @team_acc_num,@amount
		while @@FETCH_STATUS = 0
		begin
		  set @daily_acc_class_amount = @daily_acc_class_amount + @amount
		  
		  insert into cashier_daily_acc_list(daily_acc_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
		  values(@daily_acc_num,0,'C',@team_acc_num,GETDATE(),@userid,@daily_acc_class_num,@amount)
		  
		  update card_sale_summary set daily_status = '1' where sale_trade_num = @team_acc_num
		  fetch next from card_sale_cursor into @team_acc_num,@amount
		end
		
		close card_sale_cursor
		deallocate card_sale_cursor
		
		if @daily_acc_class_num is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num,@daily_acc_class_amount,@userid,GETDATE(),'003','101',@center_num)
		
			set @daily_acc_class_num = null
		end
		
		/****************************************************
		*****************团体账户充值日结********************
		****************************************************/
		set @daily_acc_class_amount = 0
		
		--保存充值收费方式
		declare com_detail_way_cursor cursor for 
		select c.chargingway,sum(case when c.trancode = '001' then c.trantmt else -1*c.trantmt end) amount 
		from company_account_detail c where c.trancode in ('001','003') and c.daily_status = 0 
		and c.creater = @userid and c.center_num=@center_num group by c.chargingway
		
		open com_detail_way_cursor
		fetch next from com_detail_way_cursor into @charging_way,@way_amonut
		while @@FETCH_STATUS = 0
		begin 
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			if @daily_acc_class_num is null
				exec P_GetSysParam 'daily_acc_num_class',@daily_acc_class_num out
						
			insert into cashier_daily_acc_payway(daily_acc_num,daily_acc_class_num,charging_way,amonut)
			values(@daily_acc_num,@daily_acc_class_num,@charging_way,@way_amonut)
			fetch next from com_detail_way_cursor into @charging_way,@way_amonut
		end
		close com_detail_way_cursor
		deallocate com_detail_way_cursor
		
		--保存团体账户充值明细
		declare com_detail_cursor cursor for
		select c.jnnumber,case when c.trancode = '001' then c.trantmt else -1*c.trantmt end as amount
		from company_account_detail c where c.trancode in ('001','003') and c.daily_status = 0 and c.creater = @userid and c.center_num=@center_num
		
		open com_detail_cursor
		fetch next from com_detail_cursor into  @team_acc_num,@amount
		while @@FETCH_STATUS = 0
		begin
			set @daily_acc_class_amount = @daily_acc_class_amount + @amount
		  
			insert into cashier_daily_acc_list(daily_acc_num,summary_id,exam_type,team_acc_num,create_time,creater,daily_acc_class_num,amount)
			values(@daily_acc_num,0,'Z',@team_acc_num,GETDATE(),@userid,@daily_acc_class_num,@amount)
		  
			update company_account_detail set daily_status = '1' where jnnumber = @team_acc_num
			fetch next from com_detail_cursor into  @team_acc_num,@amount
		end
		close com_detail_cursor
		deallocate com_detail_cursor
		
		if @daily_acc_class_num is not null
		begin
			set @daily_acc_amount = @daily_acc_amount + @daily_acc_class_amount
			insert into cashier_daily_acc_class(daily_acc_num,daily_acc_class_num,daily_acc_class_amount,creater,create_time,daily_acc_class,tran_code,center_num)
			values(@daily_acc_num,@daily_acc_class_num,@daily_acc_class_amount,@userid,GETDATE(),'004','101',@center_num)
			
			set @daily_acc_class_num = null
		end
		
		/*****************收费员发票日结*********************/
		declare invoice_cursor cursor for
		select c.invoice_class,c.invoice_num,c.invoice_status,c.invoice_amount,c.id 
		from charging_invoice_single c where c.daily_status = 0 and c.invoice_maker = @userid and c.center_num = @center_num
	
		open invoice_cursor
		fetch next from invoice_cursor into @invoice_class,@invoice_num,@invoice_status,@invoice_amount,@invoice_id
		while @@FETCH_STATUS = 0
		begin
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			insert into cashier_daily_acc_invoice(daily_acc_num,charging_way_id,invoice_num,invoice_status,create_date,userId,invoice_amount)
			values(@daily_acc_num,@invoice_class,@invoice_num,@invoice_status,GETDATE(),@userid,@invoice_amount)
			
			update charging_invoice_single set daily_status = '1' where id = @invoice_id
			
			fetch next from invoice_cursor into @invoice_class,@invoice_num,@invoice_status,@invoice_amount,@invoice_id
		end
		close invoice_cursor
		deallocate invoice_cursor
		
		declare invoice_null_cursor cursor for
		select n.invoice_class,n.invoice_num,n.invoice_status, 
		(case when invoice_status = 'N' then (select -1*c.invoice_amount from charging_invoice_single c 
		where c.invoice_num = n.invoice_num and c.invoice_class = n.invoice_class) else 0 end) invoice_amount,n.id
		from nullify_invoice n where n.daily_status = 0 and n.creater = @userid and n.center_num=@center_num
		
		open invoice_null_cursor
		fetch next from invoice_null_cursor into @invoice_class,@invoice_num,@invoice_status,@invoice_amount,@invoice_id
		while @@FETCH_STATUS = 0
		begin
			if @daily_acc_num is null
				exec P_GetSysParam 'daily_acc_num', @daily_acc_num out
			insert into cashier_daily_acc_invoice(daily_acc_num,charging_way_id,invoice_num,invoice_status,create_date,userId,invoice_amount)
			values(@daily_acc_num,@invoice_class,@invoice_num,@invoice_status,GETDATE(),@userid,@invoice_id)
			
			update nullify_invoice set daily_status = '1' where id = @invoice_id
			fetch next from invoice_null_cursor into @invoice_class,@invoice_num,@invoice_status,@invoice_amount,@invoice_id
		end
		close invoice_null_cursor
		deallocate invoice_null_cursor
		
		/****************收费员日结主表保存*****************/
		print @daily_acc_num
		if @daily_acc_num is not null
		begin 
			insert into cashier_daily_acc(daily_acc_num,userId,daily_acc_date,daily_acc_amount,is_Active,daily_status,center_num)
			values(@daily_acc_num,@userid,GETDATE(),@daily_acc_amount,'Y','0',@center_num)
		end
		else
		begin
			set @status = 1
			set @error_text = '没有收费记录信息，不需要收费日结。'
		end
		commit tran
      end try
	BEGIN CATCH
	  if @@trancount > 0    
        rollback tran
        
        set @status = 1
        set @error_text = error_message()  
	END CATCH
end


/**
执行语句
declare @status int,@error_text varchar(50)
exec pro_cashier_daily 14, @status out,@error_text out 
select @status,@error_text

close charging_way_single_cursor
deallocate charging_way_single_cursor

select CURSOR_STATUS('LOCAL','invoice_null_cursor')
**/

