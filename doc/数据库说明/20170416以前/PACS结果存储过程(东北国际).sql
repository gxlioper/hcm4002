alter procedure proc_pacs_report_dbgj(
/*********************************************
    东北国际医院（平台PACS接口结果回传）
 *********************************************/
     @pacs_req_code   varchar(20),  --申请单编号（必填）
     @exam_num      varchar(20),  --体检号
     @check_date    varchar(20),  --检查时间（必填）
     @check_doct    varchar(20),  --检查医生（必填）
     @audit_date    varchar(20),  --审核时间（必填）
     @audit_doct    varchar(20),  --审核医生（必填）
     @exam_result      varchar(1000), --诊断结果（必填）
     @exam_desc   varchar(2000),    --影像表现(可空)
     @BodyPart   varchar(200),     --检查部位(可空)
     @ExamMethod varchar(500),     --检查方法(可空)
     @img_file   varchar(2000),     --图像文件(多个用;隔开，文件格式：/pacs_img/ET/2016-10-27/T16A270010/T16A270010.jpg)
     @error              int out,           --返回值(0:成功；1：失败)
     @error_msg          varchar(200) out           --返回消息(成功；失败原因)
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
			
			exec proc_exam_critical_pacs --根据PACS项目结论生成危急值提醒存储过程
			   @exam_info_id=@exam_info_id,   --体检ID
			   @exam_item_id=347,   --细项ID
			   @exam_result=@exam_result,  --细项结果
			   @check_date=@check_date,    --检查日期
			   @pacs_id=@pacs_id         --PACS申请ID
		  SET @tran_error = @tran_error + @@ERROR;
		end try
		BEGIN CATCH
			PRINT '出现异常，错误编号：' + convert(varchar,error_number()) + ',错误消息：' + error_message()
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
		
		exec proc_exam_critical_pacs --根据PACS项目结论生成危急值提醒存储过程
			   @exam_info_id=@exam_info_id,   --体检ID
			   @exam_item_id=347,   --细项ID
			   @exam_result=@exam_result,  --细项结果
			   @check_date=@check_date,    --检查日期
			   @pacs_id=@pacs_id         --PACS申请ID
	  end
	   
	  IF(@tran_error > 0)
	  BEGIN
			--执行出错，回滚事务
			ROLLBACK TRAN;
			set @error=1
			set @error_msg='错误消息：' + error_message()
			PRINT '写入或更新PACS检查结果失败!';
	  END
	  ELSE
	  BEGIN
			--没有异常，提交事务
			COMMIT TRAN;
			set @error=0
			PRINT '写入或更新PACS检查结果成功!';
	  END
  end
  else begin
    set @error=1
    set @error_msg='申请单号【'+ @pacs_req_code + '】不存在。'
  end
 end					

