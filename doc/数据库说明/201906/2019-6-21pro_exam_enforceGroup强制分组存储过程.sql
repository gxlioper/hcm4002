
if exists(select * from sys.procedures where name='pro_exam_enforceGroup')
drop procedure dbo.pro_exam_enforceGroup;
go

/*
  declare @status  int
  exec pro_exam_enforceGroup '455', '170746', '14', '001', @status out
  select @status
*/
--select * from exam_info where exam_num = '1607201043'
CREATE procedure [dbo].[pro_exam_enforceGroup](
  /**************团体备单强制分组******************/
  @group_id varchar(10),       --分组ID
  @exam_info_ids varchar(max),  --体检ID串，多个人用 ';'分割  
  @user_id varchar(10),        --用户ID
  @center_id varchar(10),       --体检中心编码
  @status  int out         --1：执行结束
)
as
begin
   declare @exam_info_id int, --体检ID
           @exam_num varchar(20), --体检号
           @name     varchar(30),
           @sex      varchar(6),
           @sam_demo_id int,
           @dep_category int,
           @item_name  varchar(100),
           @dep_num    varchar(20),
           @dep_name   varchar(100),
           @pay_Status varchar(2),
           @calculation_rate int,
           @calculation_amount decimal(8,4)
           
   
   declare @charge_item_id varchar(10),
           @charging_item_code varchar(50),
           @item_amount varchar(10),
           @discount varchar(10),
           @amount varchar(10),
           @id int,
           @pacs_id int,
           @tran_error int,
           @exam_indicator varchar(10)
   
   select @pay_Status=config_value from dbo.center_configuration where config_key='IS_AFTER_PAY'
   select @exam_indicator=exam_indicator from group_info where id=@group_id
   
   declare exam_info_cursor cursor for
     select value from StrSplit(@exam_info_ids, ';')
   
   open exam_info_cursor 
   fetch next from exam_info_cursor into @exam_info_id
   while @@FETCH_STATUS=0
   begin
     BEGIN TRAN Tran_auto
     begin try
      select @exam_num= e.exam_num, @name=c.user_name, @sex = c.sex
      from exam_info e, customer_info c
      where e.customer_id=c.id and e.id=@exam_info_id
      
      delete from pacs_detail where examinfo_num=@exam_num
      delete from pacs_summary where examinfo_num=@exam_num
      delete from examResult_chargingItem where exam_id in (select id from sample_exam_detail where exam_info_id=@exam_info_id) and result_type='sample'
	  delete from sample_exam_detail where exam_info_id=@exam_info_id
      delete from examinfo_charging_item where examinfo_id=@exam_info_id
      
      update exam_info set group_id=@group_id, is_after_pay=@pay_Status where id=@exam_info_id
      
	  --体检者套餐
	  delete from examinfo_set where examinfo_id=@exam_info_id
	  insert into examinfo_set(examinfo_id, exam_set_id, exam_indicator, discount, amount, is_new_added, isActive, creater, create_time, final_exam_date)
	  select @exam_info_id, exam_set_id, 'T', s.set_discount, s.set_amount, 0, 'Y', @user_id, GETDATE(), GETDATE() 
	  from group_set gs, exam_set s 
	  where gs.exam_set_id=s.id and gs.isActive='Y' and s.is_Active='Y' and gs.group_id=@group_id
		--日志
	 -- insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
	 -- values(@group_id, '强制分组', @exam_info_id, @exam_num, 'examinfo_set', '写入成功')
	  
	  --遍历分组项目
      declare group_item_cursor cursor for
         -- select charge_item_id, item_amount, discount, amount from group_charging_item where group_id=@group_id and isActive='Y'
        select gci.charge_item_id ,ci.item_code, gci.item_amount, gci.discount, gci.amount,ci.calculation_rate,ci.calculation_amount
              from group_charging_item gci,charging_item ci
      where gci.group_id=@group_id and gci.isActive='Y' and gci.charge_item_id=ci.id
      open group_item_cursor
        
      fetch next from group_item_cursor into @charge_item_id,@charging_item_code, @item_amount, @discount, @amount,@calculation_rate,@calculation_amount
	  while @@FETCH_STATUS=0
	  begin
	    update exam_info set exam_indicator='T' where id=@exam_info_id
	    if @exam_indicator='T'
	      insert into examinfo_charging_item(examinfo_id,exam_num, charge_item_id,charging_item_code, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date,calculation_rate,calculation_amount)
          values(@exam_info_id,@exam_num, @charge_item_id,@charging_item_code, 'T', @item_amount, @discount, @amount, 'R', 'N',
               'Y',  0, 0, @amount, 0, 'N', 'N', @user_id, GETDATE(), GETDATE(),@calculation_rate,@calculation_amount)
        else
          insert into examinfo_charging_item(examinfo_id,exam_num, charge_item_id,charging_item_code, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date,calculation_rate,calculation_amount)
          values(@exam_info_id,@exam_num, @charge_item_id,@charging_item_code, 'G', @item_amount, @discount, @amount, 'N', 'N',
               'Y',  0, 0, 0, @amount, 'N', 'N', @user_id, GETDATE(), GETDATE(),@calculation_rate,@calculation_amount)
                 
        --日志
      --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
      --  values(@group_id, '自动分组', @exam_info_id, @exam_num, 'examinfo_charging_item', '写入examinfo_charging_item成功， 项目ID=' + @charge_item_id)
          

          
        --select top 10 * from charging_item
        select @sam_demo_id=sam_demo_id, @dep_category=ci.dep_category, @charging_item_code=item_code, @item_name=item_name, 
            @dep_num=d.dep_num, @dep_name=d.dep_name
        from charging_item ci, department_dep d 
        where ci.dep_id=d.id and ci.id=@charge_item_id
          
          if @dep_category=131 --检验项目
          begin
		      declare @ParamValue varchar(20),
				  @BarCode_Class int
              set @id = null
			  select @id=id from sample_exam_detail where exam_info_id=@exam_info_id and sample_id=@sam_demo_id
			  if @id is null
			  begin
			             --检验条码
				
				select @BarCode_Class=BarCode_Class from sample_demo where id=@sam_demo_id
				
				if @BarCode_Class=2 --预印条码
				  set @ParamValue=''
				else
				  exec P_GetSysParam 'BARCODE', @ParamValue out
				--select @ParamValue
        
				 insert into sample_exam_detail(exam_info_id,exam_num, sample_id, sample_barcode, pic_path, status, center_num, creater, create_time)
				 values(@exam_info_id,@exam_num, @sam_demo_id, @ParamValue, 'images/person.jpg', 'W', @center_id, @user_id, GETDATE())

				 set @id = IDENT_CURRENT('sample_exam_detail')
	             --日志
           --      insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
           --      values(@group_id, '自动分组', @exam_info_id, @exam_num, 'sample_exam_detail', 
           --        '写入sample_exam_detail成功，条码号='+@ParamValue +'；ID=' + @id)
			  end
			  --select top 1 * from examResult_chargingItem
			  insert into examResult_chargingItem(charging_id,charging_item_code,bar_code, exam_id, result_type, isActive, creater, create_time)
			  values(@charge_item_id,@charging_item_code, @ParamValue, @id, 'sample', 'Y', @user_id, GETDATE())
			  
			  --日志
              --   insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '自动分组', @exam_info_id, @exam_num, 'examResult_chargingItem', 
              --     '写入examResult_chargingItem成功，ID=' + @id)
          end
          --pacs申请
          if @dep_category=21 --PACS项目
          begin
		   declare @ParamValue_Pacs varchar(20)
			  --select top 2 * from pacs_summary
			  set @pacs_id = null
			  select @pacs_id=ID from pacs_summary where examinfo_num=@exam_num and examinfo_sampleId=@sam_demo_id
			  if @pacs_id is null
			  begin 
			      
                   exec P_GetSysParam 'pacs_req_num', @ParamValue_Pacs out
                 --select @ParamValue_Pacs
				  insert into pacs_summary(examinfo_num, examinfo_name, examinfo_sex, exam_status, examinfo_sampleId, 
				   apply_person, apply_date, creater, create_time, pacs_req_code)
				  values(@exam_num, @name, @sex, 'N', @sam_demo_id, 'admin', GETDATE(), @user_id, GETDATE(), @ParamValue_Pacs) 
				  
				  set @pacs_id = IDENT_CURRENT('pacs_summary')
				  
				  --日志
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
               --  values(@group_id, '自动分组', @exam_info_id, @exam_num, 'pacs_summary', 
               --    '写入pacs_summary成功，ID=' + @id)
	          end
			 -- select top 1 * from pacs_detail
			  insert into pacs_detail(summary_id, examinfo_num, chargingItem_num, chargingItem_name, dep_num, dep_name, examinfo_sampleId,pacs_req_code, 
				is_need_return, creater, create_time)
			  values(@pacs_id, @exam_num, @charging_item_code, @item_name, @dep_num, @dep_name, @sam_demo_id,@ParamValue_Pacs,
			    'Y', @user_id, GETDATE())
			    
			   --日志
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
                -- values(@group_id, '自动分组', @exam_info_id, @exam_num, 'pacs_detail', 
                --   '写入pacs_detail成功，项目编码=' + @charging_item_code +', 项目名称=' + @item_name)
          end
            
	    fetch next from group_item_cursor into @charge_item_id,@charging_item_code, @item_amount, @discount, @amount,@calculation_rate,@calculation_amount
	  end
	  close group_item_cursor
	  deallocate group_item_cursor
	  
	  commit tran
	 end try
	 BEGIN CATCH
	    if @@trancount > 0   
          rollback tran
		PRINT '出现异常，错误编号：' + convert(varchar,error_number()) + ',错误消息：' + error_message()
		--日志
        insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
        values(@group_id, '自动分组', @exam_info_id, @exam_num, '异常', 
                   '出现异常，错误编号：' + convert(varchar,error_number()) + ',错误消息：' + error_message())
		SET @tran_error = @tran_error + 1
		
		
	 END CATCH    
	 --if @@trancount > 0   
  --    commit tran
	 
	 fetch next from exam_info_cursor into @exam_info_id
   end
   close exam_info_cursor
   deallocate exam_info_cursor
   
   set @status=1
end
GO


