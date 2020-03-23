/*
  declare @status  int
  exec pro_exam_enforceGroup '455', '170746', '14', '001', @status out
  select @status
*/
--select * from exam_info where exam_num = '1607201043'
ALTER procedure [dbo].[pro_exam_enforceGroup](
  /**************���屸��ǿ�Ʒ���******************/
  @group_id varchar(10),       --����ID
  @exam_info_ids varchar(max),  --���ID����������� ';'�ָ�  
  @user_id varchar(10),        --�û�ID
  @center_id varchar(10),       --������ı���
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
           @charging_item_num varchar(20),
           @item_name  varchar(100),
           @dep_num    varchar(20),
           @dep_name   varchar(100)
           
   
   declare @charge_item_id varchar(10),
           @item_amount varchar(10),
           @discount varchar(10),
           @amount varchar(10),
           @id int,
           @pacs_id int,
           @tran_error int,
           @exam_indicator varchar(10)
   
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
      
      update exam_info set group_id=@group_id where id=@exam_info_id
      
	  --������ײ�
	  delete from examinfo_set where examinfo_id=@exam_info_id
	  insert into examinfo_set(examinfo_id, exam_set_id, exam_indicator, discount, amount, is_new_added, isActive, creater, create_time, final_exam_date)
	  select @exam_info_id, exam_set_id, 'T', s.set_discount, s.set_amount, 0, 'Y', @user_id, GETDATE(), GETDATE() 
	  from group_set gs, exam_set s 
	  where gs.exam_set_id=s.id and gs.isActive='Y' and s.is_Active='Y' and gs.group_id=@group_id
		--��־
	 -- insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
	 -- values(@group_id, 'ǿ�Ʒ���', @exam_info_id, @exam_num, 'examinfo_set', 'д��ɹ�')
	  
	  --����������Ŀ
      declare group_item_cursor cursor for
          select charge_item_id, item_amount, discount, amount from group_charging_item where group_id=@group_id and isActive='Y'
        
      open group_item_cursor
        
      fetch next from group_item_cursor into @charge_item_id, @item_amount, @discount, @amount
	  while @@FETCH_STATUS=0
	  begin
	    update exam_info set exam_indicator='T' where id=@exam_info_id
	    if @exam_indicator='T'
	      insert into examinfo_charging_item(examinfo_id, charge_item_id, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date)
          values(@exam_info_id, @charge_item_id, 'T', @item_amount, @discount, @amount, 'R', 'N',
               'Y',  0, 0, @amount, 0, 'N', 'N', @user_id, GETDATE(), GETDATE())
        else
          insert into examinfo_charging_item(examinfo_id, charge_item_id, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                 isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date)
          values(@exam_info_id, @charge_item_id, 'G', @item_amount, @discount, @amount, 'N', 'N',
               'Y',  0, 0, 0, @amount, 'N', 'N', @user_id, GETDATE(), GETDATE())
                 
        --��־
      --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
      --  values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examinfo_charging_item', 'д��examinfo_charging_item�ɹ��� ��ĿID=' + @charge_item_id)
          

          
        --select top 10 * from charging_item
        select @sam_demo_id=sam_demo_id, @dep_category=ci.dep_category, @charging_item_num=item_code, @item_name=item_name, 
            @dep_num=d.dep_num, @dep_name=d.dep_name
        from charging_item ci, department_dep d 
        where ci.dep_id=d.id and ci.id=@charge_item_id
          
          if @dep_category=131 --������Ŀ
          begin
              set @id = null
			  select @id=id from sample_exam_detail where exam_info_id=@exam_info_id and sample_id=@sam_demo_id
			  if @id is null
			  begin
			             --��������
				declare @ParamValue varchar(20),
				  @BarCode_Class int
				select @BarCode_Class=BarCode_Class from sample_demo where id=@sam_demo_id
				
				if @BarCode_Class=2 --Ԥӡ����
				  set @ParamValue=''
				else
				  exec P_GetSysParam 'BARCODE', @ParamValue out
				--select @ParamValue
        
				 insert into sample_exam_detail(exam_info_id, sample_id, sample_barcode, pic_path, status, center_num, creater, create_time)
				 values(@exam_info_id, @sam_demo_id, @ParamValue, 'images/person.jpg', 'W', @center_id, @user_id, GETDATE())

				 set @id = IDENT_CURRENT('sample_exam_detail')
	             --��־
           --      insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
           --      values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'sample_exam_detail', 
           --        'д��sample_exam_detail�ɹ��������='+@ParamValue +'��ID=' + @id)
			  end
			  --select top 1 * from examResult_chargingItem
			  insert into examResult_chargingItem(charging_id, exam_id, result_type, isActive, creater, create_time)
			  values(@charge_item_id, @id, 'sample', 'Y', @user_id, GETDATE())
			  
			  --��־
              --   insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examResult_chargingItem', 
              --     'д��examResult_chargingItem�ɹ���ID=' + @id)
          end
          --pacs����
          if @dep_category=21 --PACS��Ŀ
          begin
			  --select top 2 * from pacs_summary
			  set @pacs_id = null
			  select @pacs_id=ID from pacs_summary where examinfo_num=@exam_num and examinfo_sampleId=@sam_demo_id
			  if @pacs_id is null
			  begin 
			       declare @ParamValue_Pacs varchar(20)
                   exec P_GetSysParam 'pacs_req_num', @ParamValue_Pacs out
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
			  insert into pacs_detail(summary_id, examinfo_num, chargingItem_num, chargingItem_name, dep_num, dep_name, examinfo_sampleId, 
				is_need_return, creater, create_time)
			  values(@pacs_id, @exam_num, @charging_item_num, @item_name, @dep_num, @dep_name, @sam_demo_id,
			    'Y', @user_id, GETDATE())
			    
			   --��־
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
                -- values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'pacs_detail', 
                --   'д��pacs_detail�ɹ�����Ŀ����=' + @charging_item_num +', ��Ŀ����=' + @item_name)
          end
            
	    fetch next from group_item_cursor into @charge_item_id, @item_amount, @discount, @amount
	  end
	  close group_item_cursor
	  deallocate group_item_cursor
	  
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
	 if @@trancount > 0   
      commit tran
	 
	 fetch next from exam_info_cursor into @exam_info_id
   end
   close exam_info_cursor
   deallocate exam_info_cursor
   
   set @status=1
end