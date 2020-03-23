ALTER procedure [dbo].[pro_exam_autoGroup](
  /************�Զ�����洢����*****************/
  @batch_id  varchar(10), --����ID
  @user_id   varchar(10),   --�û�ID
  @center_id varchar(10),  --�������ID
  @status  int out         --1��ִ�н���
) 
as
begin
  /*
  declare @tmp_table table(
		exam_info_id int,
		group_id int,
		exam_num varchar(20),
		name varchar(100),
		sex  varchar(10),
		age  int,
		is_marrige varchar(20),
		position  varchar(100),
		other  varchar(100)
	  )  */
      create table #tmp_table(
		exam_info_id int,
		group_id int,
		exam_num varchar(20),
		name varchar(100),
		sex  varchar(10),
		age  int,
		is_marrige varchar(20),
		position  varchar(100),
		other  varchar(100)
	  )
	  
  declare @group_id int,
      @sex varchar(4),
      @min_age int,
      @max_age int,
      @is_marrige varchar(10),
      @postion varchar(100),
      @other   varchar(100)  --���� 
   
   declare 
      @exam_info_id int,
      @exam_num varchar(20),
      @name varchar(20),
      @exam_sex varchar(4),
      @dep_category varchar(20),
      @charging_item_num varchar(20),
      @item_name varchar(200),
      @dep_num varchar(10),
      @dep_name varchar(40),
      @tran_error int
      
   
  declare @strSQL varchar(8000),
      @sam_demo_id int,
      @id int, 
      @pacs_id int,
      @exam_indicator varchar(2),
      @pay_Status varchar(2)
	  
  set @status=0
  declare group_cursor cursor for
    select a.id, a.sex, a.min_age, a.max_age, a.is_Marriage, a.posttion, a.group_index, a.exam_indicator 
    from group_info as a, group_charging_item as b
    where a.id=b.group_id and a.batch_id=@batch_id and a.isActive='Y' and b.isActive='Y'
  
  open group_cursor
  fetch next from group_cursor into @group_id, @sex, @min_age, @max_age, @is_marrige, @postion, @other, @exam_indicator 
  while @@FETCH_STATUS=0
  begin
    print @is_marrige
    truncate table #tmp_table
    --���ݷ���������ѯ���ʵ����ͻ�
    set @strSQL = 'insert into #tmp_table(exam_info_id, group_id, exam_num, name, sex, age, is_marrige, position, other) '
                + 'select eb.examinfo_id, ' + cast(@group_id as varchar(10)) + ' as group_id, e.exam_num, c.user_name, c.sex, e.age, e.is_marriage, e.position, e.group_index '
	            + 'from examinfo_batch eb '
	            + 'inner join exam_info e on e.id=eb.examinfo_id '
	            + 'inner join customer_info c on e.customer_id=c.id '
	            + 'where eb.batch_id='+ @batch_id +' and c.is_Active=''Y'' and e.is_Active=''Y'' and (e.group_id is null or e.group_id=0) '
	
	if (@min_age>=0) and (@max_age>0)
	  set @strSQL = @strSQL + ' and e.age>=' + cast(@min_age as varchar(10))
	     + ' and e.age<' + cast(@max_age as varchar(10))
	--print @strSQL
		  
	if ((@sex is not null) and (@sex!='')) 
	  set @strSQL = @strSQL + ' and c.sex=''' + @sex + ''''
	    
	if ((@is_marrige is not null) and (@is_marrige!='')) 
	  set @strSQL = @strSQL + ' and e.is_marriage=''' + @is_marrige + ''''
	  
	if ((@postion is not null) and (@postion!=''))
	  set @strSQL = @strSQL + ' and e.position=''' + @postion + ''''
	  
	if ((@other is not null) and (@other!=''))
	  set @strSQL = @strSQL + ' and e.group_index=''' + @other + ''''
	  
	print @strSQL
	--exec sp_executesql @strSQL
	exec(@strSQL)
	
	declare exam_cursor cursor for
	  select exam_info_id, exam_num, name, sex from #tmp_table
	  
	open exam_cursor
	fetch next from exam_cursor into @exam_info_id, @exam_num, @name, @exam_sex
	
	while @@FETCH_STATUS=0
	begin
	  BEGIN TRAN Tran_auto
	  begin try
	    --������ײ�
        delete from examinfo_set where examinfo_id=@exam_info_id
		insert into examinfo_set(examinfo_id, exam_set_id, exam_indicator, discount, amount, is_new_added, isActive, creater, create_time, final_exam_date)
		select @exam_info_id, exam_set_id, 'T', s.set_discount, s.set_amount, 0, 'Y', @user_id, GETDATE(), GETDATE() 
		from group_set gs, exam_set s 
		where gs.exam_set_id=s.id and gs.isActive='Y' and s.is_Active='Y' and gs.group_id=@group_id
		--��־
		insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
		values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examinfo_set', 'д��ɹ�')
        
        update exam_info set group_id=@group_id where id=@exam_info_id
        --����������Ŀ
        declare @charge_item_id varchar(10),
           @item_amount varchar(10),
           @discount varchar(10),
           @amount varchar(10)
        
        declare group_item_cursor cursor for
          select charge_item_id, item_amount, discount, amount from group_charging_item where group_id=@group_id and isActive='Y'
        
        open group_item_cursor
        
        fetch next from group_item_cursor into @charge_item_id, @item_amount, @discount, @amount
        while @@FETCH_STATUS=0
        begin
          --�������Ŀ
		  update exam_info set exam_indicator='T' where id=@exam_info_id
          --select top 10 * from examinfo_charging_item 
          if @exam_indicator='T'  --�������
		  	    
			insert into examinfo_charging_item(examinfo_id, charge_item_id, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                  isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date)
            values(@exam_info_id, @charge_item_id, 'T', @item_amount, @discount, @amount, 'R', 'N',
                  'Y',   0, 0, @amount, 0, 'N', 'N', @user_id, GETDATE(), GETDATE())
		 
          else
            insert into examinfo_charging_item(examinfo_id, charge_item_id, exam_indicator, item_amount, discount, amount, pay_status, exam_status, 
                  isActive, is_new_added, check_status, team_pay, personal_pay, is_application, change_item, creater, create_time, final_exam_date)
            values(@exam_info_id, @charge_item_id, 'G', @item_amount, @discount, @amount, 'N', 'N',
                  'Y',   0, 0, 0, @amount, 'N', 'N', @user_id, GETDATE(), GETDATE())
                 
          --��־
         -- insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
         -- values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examinfo_charging_item', 'д��examinfo_charging_item�ɹ��� ��ĿID=' + cast(@charge_item_id as varchar(20)))
          
         
          --select top 10 * from charging_item
          select @sam_demo_id=sam_demo_id, @dep_category=ci.dep_category, @charging_item_num=item_code, @item_name=item_name, 
            @dep_num=d.dep_num, @dep_name=d.dep_name
          from charging_item ci, department_dep d 
          where ci.dep_id=d.id and ci.id=@charge_item_id
          
          if @dep_category=131 --������Ŀ
          begin
              set @id=null
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
			     -- select top 1 * from sample_exam_detail
				 insert into sample_exam_detail(exam_info_id, sample_id, sample_barcode, pic_path, status, center_num, creater, create_time)
				 values(@exam_info_id, @sam_demo_id, @ParamValue, 'images/person.jpg', 'W', @center_id, @user_id, GETDATE())

				 set @id = IDENT_CURRENT('sample_exam_detail')
	             --��־
              --   insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'sample_exam_detail', 
                 --  'д��sample_exam_detail�ɹ��������='+@ParamValue +'��ID=' + cast(@id as varchar(10)))
			  end
			  --select top 1 * from examResult_chargingItem
			  insert into examResult_chargingItem(charging_id, exam_id, result_type, isActive, creater, create_time)
			  values(@charge_item_id, @id, 'sample', 'Y', @user_id, GETDATE())
			  
			  --��־
              --   insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'examResult_chargingItem', 
              --     'д��examResult_chargingItem�ɹ���ID=' + cast(@id as varchar(20)))
          end
          --pacs����
          if @dep_category=21 --PACS��Ŀ
          begin
			  --select top 2 * from pacs_summary
			  set @pacs_id=null
			  select @pacs_id=ID from pacs_summary where examinfo_num=@exam_num and examinfo_sampleId=@sam_demo_id
			  if @pacs_id is null
			  begin 
			      declare @ParamValue_Pacs varchar(20)
                   exec P_GetSysParam 'pacs_req_num', @ParamValue_Pacs out
                   --select @ParamValue_Pacs
                   
				  insert into pacs_summary(examinfo_num, examinfo_name, examinfo_sex, exam_status, examinfo_sampleId, 
				   apply_person, apply_date, creater, create_time, pacs_req_code)
				  values(@exam_num, @name, @exam_sex, 'N', @sam_demo_id, 'admin', GETDATE(), @user_id, GETDATE(), @ParamValue_Pacs) 
				  
				  set @pacs_id = IDENT_CURRENT('pacs_summary')
				  
				  --��־
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
               --  values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'pacs_summary', 
               --    'д��pacs_summary�ɹ���ID=' + cast(@id as varchar(10)))
	          end
			  --select top 1 * from pacs_detail
			  insert into pacs_detail(summary_id, examinfo_num, chargingItem_num, chargingItem_name, dep_num, dep_name, examinfo_sampleId, 
				is_need_return, creater, create_time)
			  values(@pacs_id, @exam_num, @charging_item_num, @item_name, @dep_num, @dep_name, @sam_demo_id,
			    'Y', @user_id, GETDATE())
			    
			   --��־
               --  insert into log_exam_group(group_id, log_type, exam_info_id, exam_num, tbl_name, note)
              --   values(@group_id, '�Զ�����', @exam_info_id, @exam_num, 'pacs_detail', 
              --     'д��pacs_detail�ɹ�����Ŀ����=' + @charging_item_num +', ��Ŀ����=' + @item_name)
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
     /* IF(@tran_error > 0)
	  BEGIN
		--ִ�г����ع�����
		--set @status=0
		ROLLBACK TRAN;
		--PRINT 'ʧ��!';
	  END
	  ELSE
	  BEGIN
		--û���쳣���ύ����
		COMMIT TRAN;
		--set @status=1
		PRINT '�ɹ�!';
	  END
       */
      fetch next from exam_cursor into @exam_info_id, @exam_num, @name, @exam_sex
	end
	close exam_cursor
	deallocate exam_cursor
	
    fetch next from group_cursor into @group_id, @sex, @min_age, @max_age, @is_marrige, @postion, @other, @exam_indicator 
  end
  close group_cursor
  deallocate group_cursor
  
  set @status=1
end
