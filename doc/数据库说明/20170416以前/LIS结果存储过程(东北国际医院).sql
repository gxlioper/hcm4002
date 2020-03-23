alter procedure proc_Lis_result(
/*******ƽ̨LIS�ӿڽ��д��������������ҽԺ��*******/
  @bar_code           varchar(20), --�����
  @exam_num           varchar(20), --����
  @lis_item_code      varchar(20), --LIS�շ���Ŀ������        
  @lis_rep_item_code       varchar(20), --LISϸ�����
  @exam_doctor        varchar(20),      --���飨���棩ҽ��
  @exam_date          varchar(20),      --��������
  @exam_result        varchar(100),     --������
  @ref_value            varchar(400),   --�ο���Χ
  @item_unit          varchar(80),      --��λ
  @ref_indicator      varchar(4),       --�ߵͱ�ʶ��0��������1���ߣ�2���ͣ�3�����ԣ�4��Σ����
  @approver           varchar(20),      --���ҽ��
  @approve_date       varchar(20),      --�������
  @error              int out           --����ֵ(0:�ɹ�)
 )
 as
 begin
  declare @tran_error int,
    @exam_category   varchar(100),
    @exam_item_category varchar(100),
    @exam_id int,
    @exam_info_id       int,         --���ID
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
		
		--����LIS����������Σ��ֵ���Ѵ洢����		
		exec proc_exam_critical_lis  @exam_info_id,   --���ID
                                     @exam_item_id,   --ϸ��ID
                                     @exam_result,  --ϸ����
                                     @exam_id,
                                     @exam_date     --�������
	
	  end
	  else begin
	    
	    select @exam_id=ID from exam_result_detail where exam_info_id=@exam_info_id and exam_item_id=@exam_item_id
		update exam_result_detail set exam_result=@exam_result, ref_value=@ref_value, ref_indicator=@ref_indicator,
		  item_unit=@item_unit, exam_doctor=@exam_doctor, update_time=@exam_date, approve_date=@approve_date
		where exam_info_id=@exam_info_id and exam_item_id=@exam_item_id
		
		select @exam_num=exam_num from exam_info where id=@exam_info_id
		update generate_Reports set is_printed=0 where exam_info_id=@exam_num
		--����LIS����������Σ��ֵ���Ѵ洢����		
		exec proc_exam_critical_lis  @exam_info_id,   --���ID
                                     @exam_item_id,   --ϸ��ID
                                     @exam_result,  --ϸ����
                                     @exam_id,
                                     @exam_date     --�������
	  end 
	end
  end try
  BEGIN CATCH
	PRINT '�����쳣�������ţ�' + convert(varchar,error_number()) + ',������Ϣ��' + error_message()
	SET @tran_error = @tran_error + 1
  END CATCH
  
  IF(@tran_error > 0)
  BEGIN
    --ִ�г����ع�����
    set @error=0
    ROLLBACK TRAN;
    PRINT 'д������LIS�����ʧ��!';
  END
  ELSE
  BEGIN
    --û���쳣���ύ����
    COMMIT TRAN;
      set @error=1
    PRINT 'д������LIS������ɹ�!';
  END
end