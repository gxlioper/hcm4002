alter procedure proc_pacs_report_dbgj(
/*********************************************
    ��������ҽԺ��ƽ̨PACS�ӿڽ���ش���
 *********************************************/
     @pacs_req_code   varchar(20),  --���뵥��ţ����
     @exam_num      varchar(20),  --����
     @check_date    varchar(20),  --���ʱ�䣨���
     @check_doct    varchar(20),  --���ҽ�������
     @audit_date    varchar(20),  --���ʱ�䣨���
     @audit_doct    varchar(20),  --���ҽ�������
     @exam_result      varchar(1000), --��Ͻ�������
     @exam_desc   varchar(2000),    --Ӱ�����(�ɿ�)
     @BodyPart   varchar(200),     --��鲿λ(�ɿ�)
     @ExamMethod varchar(500),     --��鷽��(�ɿ�)
     @img_file   varchar(2000),     --ͼ���ļ�(�����;�������ļ���ʽ��/pacs_img/ET/2016-10-27/T16A270010/T16A270010.jpg)
     @error              int out,           --����ֵ(0:�ɹ���1��ʧ��)
     @error_msg          varchar(200) out           --������Ϣ(�ɹ���ʧ��ԭ��)
 )
 as
 begin
   declare @pacs_id int,
     @exam_info_id  int,
     @charge_item_id int, 
     @tran_error int
    
   
   if exists (select s.id as pacs_id from pacs_summary s where s.pacs_req_code=@pacs_req_code)
   begin      
	   select @pacs_id=s.id, @charge_item_id=ci.id from pacs_summary s, pacs_detail l, charging_item ci, department_dep d 
       where s.id=l.summary_id and l.chargingItem_num=ci.item_code and s.pacs_req_code=@pacs_req_code
	   
	   select @exam_info_id=ID from exam_info where exam_num=@exam_num
	   
	  set @tran_error=0
	  BEGIN TRAN Tran_report
	  
	  declare @view_exam_id int
	  
	  if not exists (select * from view_exam_detail where pacs_id=@pacs_id) 
	  begin
		begin try
			insert into view_exam_detail(pacs_id, exam_info_id, exam_item_id, exam_doctor, exam_result, exam_desc, exam_date, 
				center_num, approver, approve_date)
			values(@pacs_id, @exam_info_id, 347, @check_doct, @exam_result, @exam_desc, @check_date,
				'001', @audit_doct, @audit_date)
			SET @tran_error = @tran_error + @@ERROR;
            
            set @view_exam_id=IDENT_CURRENT('view_exam_detail')
            
            insert into view_exam_image(view_exam_id, image_path, create_time)
            select @view_exam_id, value, GETDATE() from dbo.StrSplit(@img_file,';')
            
			update examinfo_charging_item set exam_date=@check_date 
			where examinfo_id=@exam_info_id and charge_item_id=@charge_item_id and isActive='Y' and exam_date is null
			SET @tran_error = @tran_error + @@ERROR;
			
			update examinfo_charging_item set exam_doctor_name=@check_doct, exam_status='Y' 
			where examinfo_id=@exam_info_id and charge_item_id=@charge_item_id and isActive='Y' 
			SET @tran_error = @tran_error + @@ERROR;
			
			exec proc_exam_critical_pacs --����PACS��Ŀ��������Σ��ֵ���Ѵ洢����
			   @exam_info_id=@exam_info_id,   --���ID
			   @exam_item_id=347,   --ϸ��ID
			   @exam_result=@exam_result,  --ϸ����
			   @check_date=@check_date,    --�������
			   @pacs_id=@pacs_id         --PACS����ID
		  SET @tran_error = @tran_error + @@ERROR;
		end try
		BEGIN CATCH
			PRINT '�����쳣�������ţ�' + convert(varchar,error_number()) + ',������Ϣ��' + error_message()
			SET @tran_error = @tran_error + 1
		END CATCH
	  end
	  else begin
	    select @view_exam_id=ID from view_exam_detail where pacs_id=@pacs_id
	    
		update view_exam_detail set exam_result=@exam_result, exam_desc=@exam_desc, exam_doctor=@check_doct, approver=@audit_doct,
		approve_date=@audit_date
		where exam_info_id=@exam_info_id and exam_item_id=347 and pacs_id=@pacs_id

		SET @tran_error = @tran_error + @@ERROR;
		
		delete from view_exam_image where view_exam_id=@view_exam_id
		
		 insert into view_exam_image(view_exam_id, image_path, create_time)
         select @view_exam_id, value, GETDATE() from dbo.StrSplit(@img_file,';')
	    
		update examinfo_charging_item set exam_doctor_name=@check_doct, exam_status='Y' 
			where examinfo_id=@exam_info_id and charge_item_id=@charge_item_id  and isActive='Y' 
		SET @tran_error = @tran_error + @@ERROR;
		
	    select @exam_num=exam_num from exam_info where id=@exam_info_id
		update generate_Reports set is_printed=0 where exam_info_id=@exam_num
		
		exec proc_exam_critical_pacs --����PACS��Ŀ��������Σ��ֵ���Ѵ洢����
			   @exam_info_id=@exam_info_id,   --���ID
			   @exam_item_id=347,   --ϸ��ID
			   @exam_result=@exam_result,  --ϸ����
			   @check_date=@check_date,    --�������
			   @pacs_id=@pacs_id         --PACS����ID
	  end
	   
	  IF(@tran_error > 0)
	  BEGIN
			--ִ�г����ع�����
			ROLLBACK TRAN;
			set @error=1
			set @error_msg='������Ϣ��' + error_message()
			PRINT 'д������PACS�����ʧ��!';
	  END
	  ELSE
	  BEGIN
			--û���쳣���ύ����
			COMMIT TRAN;
			set @error=0
			PRINT 'д������PACS������ɹ�!';
	  END
  end
  else begin
    set @error=1
    set @error_msg='���뵥�š�'+ @pacs_req_code + '�������ڡ�'
  end
 end					

