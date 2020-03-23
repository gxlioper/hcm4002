USE [hjwpeis]
GO

/****** Object:  StoredProcedure [dbo].[pro_exam_enforceGroup6]    Script Date: 09/26/2019 08:40:56 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




/*
  declare @status  int
  exec pro_exam_enforceGroup6 '455', '170746', '14', '001', @status out
  select @status
*/
--select * from exam_info where exam_num = '1607201043'
ALTER procedure [dbo].[pro_exam_enforceGroup6](
  /**************���屸��ǿ�Ʒ���(��������ϵͳV4.0.0.2�汾����)******************/
  @group_id varchar(10),       --����ID
  @exam_info_nums varchar(max),  --���Ŵ���������� ';'�ָ�  
  @user_id varchar(10),        --�û�ID
  @center_num varchar(50),       --������ı���
  @status  int out         --1��ִ�н���
)
as
begin
   declare @exam_info_id int, --���ID
           @exam_num varchar(20), --����
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
   
   --select @pay_Status=config_value from dbo.center_configuration where config_key='IS_AFTER_PAY' and center_num=@center_num
   set @pay_Status=dbo.fun_GetCenterConfigVaule(@center_num, 'IS_AFTER_PAY')
   select @exam_indicator=exam_indicator from group_info where id=@group_id
   
   declare exam_info_cursor cursor for
     select value from StrSplit(@exam_info_nums, ';')
   
   open exam_info_cursor 
   fetch next from exam_info_cursor into @exam_num
   while @@FETCH_STATUS=0
   begin
     BEGIN TRAN Tran_auto
     begin try
      select @name=c.user_name, @sex = c.sex, @exam_info_id=e.id
      from exam_info e, customer_info c
      where e.customer_id=c.id and e.exam_num=@exam_num
      
      delete from pacs_detail where examinfo_num=@exam_num
      delete from pacs_summary where examinfo_num=@exam_num
      delete from examResult_chargingItem where bar_code in (select sample_barcode from sample_exam_detail where exam_num=@exam_num) and result_type='sample'
	  delete from sample_exam_detail where exam_num=@exam_num
      delete from examinfo_charging_item where exam_num=@exam_num
      
      update exam_info set group_id=@group_id, is_after_pay=@pay_Status where exam_num=@exam_num
      
	  --������ײ�
	  delete from examinfo_set where exam_num=@exam_num
	  insert into examinfo_set(exam_num, examinfo_id, exam_set_id, exam_indicator, discount, amount, is_new_added, isActive, creater, create_time, final_exam_date)
	  select @exam_num, 0, exam_set_id, 'T', s.set_discount, s.set_amount, 0, 'Y', @user_id, GETDATE(), GETDATE() 
	  from group_set gs, exam_set s 
	  where gs.exam_set_id=s.id and gs.isActive='Y' and s.is_Active='Y' and gs.group_id=@group_id
		--��־
	 -- insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
	 -- values(@group_id, 'ǿ�Ʒ���', @exam_info_id, @exam_num, 'examinfo_set', 'д��ɹ�')
	  
	  --����������Ŀ
      declare group_item_cursor cursor for
         -- select charge_item_id, item_amount, discount, amount from group_charging_item where group_id=@group_id and isActive='Y'
        select gci.charge_item_id ,ci.item_code, gci.item_amount, gci.discount, gci.amount,ci.calculation_rate,ci.calculation_amount
              from group_charging_item gci, charging_item ci
      where gci.group_id=@group_id and gci.isActive='Y' and gci.charge_item_id=ci.id
      open group_item_cursor
        
      fetch next from group_item_cursor into @charge_item_id, @charging_item_code, @item_amount, @discount, @amount,@calculation_rate,@calculation_amount
	  while @@FETCH_STATUS=0
	  begin
	    update exam_info set exam_indicator='T' where exam_num=@exam_num
	    if @exam_indicator='T'
	      insert into examinfo_charging_item(exam_num, center_num, examinfo_id, charge_item_id,charging_item_code, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date,calculation_rate,calculation_amount)
          values(@exam_num, @center_num, @exam_info_id, @charge_item_id,@charging_item_code, 'T', @item_amount, @discount, @amount, 'R', 'N',
               'Y',  0, 0, @amount, 0, 'N', 'N', @user_id, GETDATE(), GETDATE(),@calculation_rate,@calculation_amount)
        else
          insert into examinfo_charging_item(exam_num, center_num, examinfo_id, charge_item_id,charging_item_code, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date,calculation_rate,calculation_amount)
          values(@exam_num, @center_num, @exam_info_id, @charge_item_id,@charging_item_code, 'G', @item_amount, @discount, @amount, 'N', 'N',
               'Y',  0, 0, 0, @amount, 'N', 'N', @user_id, GETDATE(), GETDATE(),@calculation_rate,@calculation_amount)
                 
        --��־
      --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
      --  values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examinfo_charging_item', 'д��examinfo_charging_item�ɹ��� ��ĿID=' + @charge_item_id)
          

          
        --select top 10 * from charging_item
        select @sam_demo_id=sam_demo_id, @dep_category=ci.dep_category, @charging_item_code=item_code, @item_name=item_name, 
            @dep_num=d.dep_num, @dep_name=d.dep_name
        from charging_item ci, department_dep d 
        where ci.dep_id=d.id and ci.id=@charge_item_id
          
          if @dep_category=131 --������Ŀ
          begin
		      declare @ParamValue varchar(20),
				  @BarCode_Class int
              set @id = null
			  select @id=id,@ParamValue=sample_barcode from sample_exam_detail where exam_num=@exam_num and sample_id=@sam_demo_id
			  if @id is null
			  begin
			             --��������
				
				--select @BarCode_Class=BarCode_Class from sample_demo where id=@sam_demo_id
				
				--if @BarCode_Class=2 --Ԥӡ����
				--  set @ParamValue=''
				--else
				  exec P_GetSysParam 'BARCODE', @ParamValue out, @center_num
				--select @ParamValue
        
				 insert into sample_exam_detail(exam_info_id, exam_num, sample_id, sample_barcode, pic_path, status, center_num, creater, create_time)
				 values(@exam_info_id, @exam_num, @sam_demo_id, @ParamValue, 'images/person.jpg', 'W', @center_num, @user_id, GETDATE())

				 set @id = IDENT_CURRENT('sample_exam_detail')
	             --��־
           --      insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
           --      values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'sample_exam_detail', 
           --        'д��sample_exam_detail�ɹ��������='+@ParamValue +'��ID=' + @id)
			  end
			  --select top 1 * from examResult_chargingItem
			  insert into examResult_chargingItem(charging_id, charging_item_code, bar_code, exam_id, result_type, isActive, creater, create_time)
			  values(@charge_item_id, @charging_item_code, @ParamValue, @id, 'sample', 'Y', @user_id, GETDATE())
			  
			  --��־
              --   insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examResult_chargingItem', 
              --     'д��examResult_chargingItem�ɹ���ID=' + @id)
          end
          --pacs����
          if @dep_category=21 --PACS��Ŀ
          begin
		   declare @ParamValue_Pacs varchar(20)
			  --select top 2 * from pacs_summary
			  set @pacs_id = null
			  select @pacs_id=ID,@ParamValue_Pacs=pacs_req_code from pacs_summary where examinfo_num=@exam_num and examinfo_sampleId=@sam_demo_id
			  if @pacs_id is null
			  begin 
			      
                   exec P_GetSysParam 'pacs_req_num', @ParamValue_Pacs out,@center_num
                 --select @ParamValue_Pacs
				  insert into pacs_summary(examinfo_num, examinfo_name, examinfo_sex, exam_status, examinfo_sampleId, 
				   apply_person, apply_date, creater, create_time, pacs_req_code)
				  values(@exam_num, @name, @sex, 'N', @sam_demo_id, 'admin', GETDATE(), @user_id, GETDATE(), @ParamValue_Pacs) 
				  
				  set @pacs_id = IDENT_CURRENT('pacs_summary')
				  
				  --��־
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
               --  values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'pacs_summary', 
               --    'д��pacs_summary�ɹ���ID=' + @id)
	          end
			 -- select top 1 * from pacs_detail
			  insert into pacs_detail(summary_id, examinfo_num, chargingItem_num, chargingItem_name, dep_num, dep_name, examinfo_sampleId,pacs_req_code, 
				is_need_return, creater, create_time)
			  values(@pacs_id, @exam_num, @charging_item_code, @item_name, @dep_num, @dep_name, @sam_demo_id,@ParamValue_Pacs,
			    'Y', @user_id, GETDATE())
			    
			   --��־
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
                -- values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'pacs_detail', 
                --   'д��pacs_detail�ɹ�����Ŀ����=' + @charging_item_code +', ��Ŀ����=' + @item_name)
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
		PRINT '�����쳣�������ţ�' + convert(varchar,error_number()) + ',������Ϣ��' + error_message()
		--��־
        insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
        values(@group_id, '�Զ�����', @exam_info_id, @exam_num, '�쳣', 
                   '�����쳣�������ţ�' + convert(varchar,error_number()) + ',������Ϣ��' + error_message())
		SET @tran_error = @tran_error + 1
		
		
	 END CATCH    
	 --if @@trancount > 0   
  --    commit tran
	 
	 fetch next from exam_info_cursor into @exam_num
   end
   close exam_info_cursor
   deallocate exam_info_cursor
   
   set @status=1
end



GO


