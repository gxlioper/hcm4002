alter procedure proc_Lis_result(
/*******平台LIS接口结果写入检验表（东北国际医院）*******/
  @bar_code           varchar(20), --条码号
  @exam_num           varchar(20), --体检号
  @lis_item_code      varchar(20), --LIS收费项目关联码        
  @lis_rep_item_code       varchar(20), --LIS细项编码
  @exam_doctor        varchar(20),      --检验（报告）医生
  @exam_date          varchar(20),      --检验日期
  @exam_result        varchar(100),     --检验结果
  @ref_value            varchar(400),   --参考范围
  @item_unit          varchar(80),      --单位
  @ref_indicator      varchar(4),       --高低标识（0：正常；1：高；2：低；3：阳性；4：危急）
  @approver           varchar(20),      --审核医生
  @approve_date       varchar(20),      --审核日期
  @error              int out           --返回值(0:成功)
 )
 as
 begin
  declare @tran_error int,
    @exam_category   varchar(100),
    @exam_item_category varchar(100),
    @exam_id int,
    @exam_info_id       int,         --体检ID
    @exam_item_id int,
    @charging_item_id int
  
  select @exam_info_id=ID from exam_info where exam_num=@exam_num
  set @tran_error=0
  BEGIN TRAN Tran_report
  begin try
    if exists (select ci.id from examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, 
               examination_item ei, examination_item_vs_lis l
           where eci.examinfo_id=@exam_info_id and eci.charge_item_id=ci.id and ci.id=cei.charging_item_id
            and cei.exam_item_id=ei.id and ei.id=l.exam_item_id
            and ci.exam_num=@lis_item_code and l.lis_item_id=@lis_rep_item_code and eci.isActive='Y')
    begin
      select @charging_item_id=ci.id, @exam_item_id=ei.id from examinfo_charging_item eci, charging_item ci, charging_item_exam_item cei, 
               examination_item ei, examination_item_vs_lis l
      where eci.examinfo_id=@exam_info_id and eci.charge_item_id=ci.id and ci.id=cei.charging_item_id
            and cei.exam_item_id=ei.id and ei.id=l.exam_item_id
            and ci.exam_num=@lis_item_code and l.lis_item_id=@lis_rep_item_code and eci.isActive='Y'
            
	  if not exists (select exam_info_id from exam_result_detail where exam_info_id=@exam_info_id and exam_item_id=@exam_item_id)
	  begin        
	    update exam_info set join_date=SUBSTRING(@exam_date, 1, 10) where id=@exam_info_id and join_date is null
	    
		insert into exam_result_detail(exam_info_id, exam_item_id, exam_category, exam_item_category, exam_doctor, exam_date, 
			exam_result, ref_value, item_unit, ref_indicator, center_num, approver, approve_date, ref_min) 
		values(@exam_info_id, @exam_item_id, @exam_category, @exam_item_category, @exam_doctor, @exam_date,
			@exam_result, @ref_value, @item_unit, @ref_indicator, '001', @approver, @approve_date, '')
		SET @tran_error = @tran_error + @@ERROR;
	    
	    set @exam_id = IDENT_CURRENT('exam_result_detail')
		insert into examResult_chargingItem(charging_id, exam_id, result_type, isActive)
		values(@charging_item_id, @exam_id, 'exam', 'Y')
		SET @tran_error = @tran_error + @@ERROR;

		update examinfo_charging_item set exam_doctor_name=@exam_doctor, exam_date=@exam_date, exam_status='Y'
		where examinfo_id=@exam_info_id and charge_item_id=@charging_item_id
		
		--根据LIS单项结果生成危急值提醒存储过程		
		exec proc_exam_critical_lis  @exam_info_id,   --体检ID
                                     @exam_item_id,   --细项ID
                                     @exam_result,  --细项结果
                                     @exam_id,
                                     @exam_date     --检查日期
	
	  end
	  else begin
	    
	    select @exam_id=ID from exam_result_detail where exam_info_id=@exam_info_id and exam_item_id=@exam_item_id
		update exam_result_detail set exam_result=@exam_result, ref_value=@ref_value, ref_indicator=@ref_indicator,
		  item_unit=@item_unit, exam_doctor=@exam_doctor, update_time=@exam_date, approve_date=@approve_date
		where exam_info_id=@exam_info_id and exam_item_id=@exam_item_id
		
		select @exam_num=exam_num from exam_info where id=@exam_info_id
		update generate_Reports set is_printed=0 where exam_info_id=@exam_num
		--根据LIS单项结果生成危急值提醒存储过程		
		exec proc_exam_critical_lis  @exam_info_id,   --体检ID
                                     @exam_item_id,   --细项ID
                                     @exam_result,  --细项结果
                                     @exam_id,
                                     @exam_date     --检查日期
	  end 
	end
  end try
  BEGIN CATCH
	PRINT '出现异常，错误编号：' + convert(varchar,error_number()) + ',错误消息：' + error_message()
	SET @tran_error = @tran_error + 1
  END CATCH
  
  IF(@tran_error > 0)
  BEGIN
    --执行出错，回滚事务
    set @error=0
    ROLLBACK TRAN;
    PRINT '写入或更新LIS检查结果失败!';
  END
  ELSE
  BEGIN
    --没有异常，提交事务
    COMMIT TRAN;
      set @error=1
    PRINT '写入或更新LIS检查结果成功!';
  END
end