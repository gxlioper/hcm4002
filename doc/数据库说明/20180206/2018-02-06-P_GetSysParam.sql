
ALTER procedure [dbo].[P_GetSysParam](  --������ȡ�洢����
   @ParamId varchar(20)='exam_no', /*sys_param���е�param_id��ֵ(exam_no:���ţ�barcode������ţ�vipno��������; lis_order_no:lis��117);
                            pacs:117PACS���; studyid: 117ҽԺpacs��studyid; code:�ֵ�����; arch_num_rz��ʡ����Ա��ְ��쵵����
                            contract:��ͬ��ţ�rcpt_num:�շ����뵥��(����HIS�ӿڷ���ɷ�����)
							sample_no:��Ŀ�������롢report_item_code:������Ŀ���롢exam_item_code:�����Ŀ���롢
                            charge_item_code:�շ���Ŀ���롢disease_no:������֪ʶ�����
                            pacs_req_num:pacs���뵥��; account_num : �ɷ��վݺ�
                            set_num:�ײͱ��룻balance_num : ������㵥�ţ�
                            daily_acc_num ���շ�Ա�ս�ţ�
                            fd_acc_num ���������ս��, print_task_no:��ӡ�����
                            com_num:��λ���룻batch_num:���α��룻group_num:�������, icd_id��ICD10����*/
   @ParamValue varchar(20) out     --�������ֵ
) 
as
  --set nocount on
  declare @curday varchar(8),
          @curValue varchar(20),
          @tmpValue int,
          @tmpStr varchar(10),
          @head varchar(10)='TM',
          @date datetime,
          @month varchar(2)

  set @curday=CONVERT(varchar(10), getdate(), 12)
  --select @curday
  if @ParamId='print_task_no' --��ӡ�����
  begin
    set @curday=cast(cast(CONVERT(varchar(10), GETDATE(),20) as datetime) as int)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'00001'
		insert into sys_param1(param_id, param_name, param_value, note)
		values('print_task_no', '��ӡ�����', @curValue, '��ӡ�����')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,5)!=@curday)
		  set @curValue=@curday + '00001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 6, 5)) + 1
		  set @tmpStr=right('00000'+ cast(@tmpValue as varchar(5)), 5)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='icd_id'    --ICD10����
  begin
    set @head='icd_'
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@head+'00001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('icd_id', 'ICD����', @curValue, 'ICD����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,5,5) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue=@head+'00001'
	    else begin
	      set @tmpValue= CONVERT(integer, @curValue) + 1
	      set @curValue=right('00000'+ cast(@tmpValue as varchar(5)), 5)
	      set @curValue=@head+@curValue
	    end
	  end
  end
  else if @ParamId='com_num'    --��λ����
  begin
    set @head=SUBSTRING(@curday,1,2) 
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@head+'00001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('com_num', '��λ����', @curValue, '��λ����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,3,5) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue=@head+'00001'
	    else begin
	      set @tmpValue= CONVERT(integer, @curValue) + 1
	      set @curValue=right('00000'+ cast(@tmpValue as varchar(5)), 5)
	      set @curValue=@head+@curValue
	    end
	  end
  end
  else if @ParamId='batch_num'    --�������(���α���)
  begin
    set @head='B'+cast(cast(cast(CONVERT(varchar(10), getdate(), 12) as datetime) as int) as varchar(5))
    
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@head+'001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('batch_num', '���α���', @curValue, '�������(���α���)')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL) or (substring(@curValue,1,6)!=@head)
	      set @curValue=@head+'001'
	    else begin
	      set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 3)) + 1
	      set @curValue=right('000'+ cast(@tmpValue as varchar(3)), 3)
	      set @curValue=@head+@curValue
	    end
	  end
  end
  else if @ParamId='group_num'    --�������
  begin
    set @head='G'+cast(cast(cast(CONVERT(varchar(10), getdate(), 12) as datetime) as int) as varchar(5))
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@head+'001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('group_num', '�������', @curValue, '�������')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL) or (substring(@curValue,1,6)!=@head)
	      set @curValue=@head+'001'
	    else begin
	      set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 3)) + 1
	      set @curValue=right('000'+ cast(@tmpValue as varchar(3)), 3)
	      set @curValue=@head+@curValue
	    end
	  end
  end
  else if @ParamId='balance_num' --������㵥��
  begin
      set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      --set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('balance_num', '������㵥��', @curValue, '�����������')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,8)!=@curday)
		  set @curValue=@curday + '001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 9, 3)) + 1
		  set @tmpStr=right('00000000'+ cast(@tmpValue as varchar(9)), 3)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='daily_acc_num'  --�շ�Ա�ս��
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue='C'+@curday + '000001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('daily_acc_num', '�շ�Ա�ս��', @curValue, '�շ�Ա�ս��')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,2,6)!=@curday)
		  set @curValue='C'+@curday + '000001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 8, 6)) + 1
		  set @tmpStr=right('000000'+ cast(@tmpValue as varchar(10)), 6)
		  --select '@tmpStr', @tmpStr
		  set @curValue='C'+@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='fd_acc_num'  --�������ս��
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue='F'+@curday + '000001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('fd_acc_num', '�������ս��', @curValue, '�������ս��')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,2,6)!=@curday)
		  set @curValue='F'+@curday + '000001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 8, 6)) + 1
		  set @tmpStr=right('000000'+ cast(@tmpValue as varchar(10)), 6)
		  --select '@tmpStr', @tmpStr
		  set @curValue='F'+@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='rcpt_num'  --�ɷ����뵥��
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '000001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('rcpt_num', '�ɷ����뵥��', @curValue, '���ڸ��˽ɷ�����')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '000001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 6)) + 1
		  set @tmpStr=right('000000'+ cast(@tmpValue as varchar(10)), 6)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end 
    else if @ParamId='visit_num' --�����ƻ����
  begin
    set @curday=CONVERT(varchar(10), getdate(), 12)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'_0001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('visit_num', '�����ƻ����', @curValue, '�����ƻ����')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '_0001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 8, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(11)), 4)
		  set @curValue=@curday + '_' + @tmpStr
		end
	  end
  end
  else if @ParamId='sign_num' --ǩ���ƻ����
  begin
    set @curday=CONVERT(varchar(10), getdate(), 12)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'_0001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('sign_num', 'ǩ���ƻ����', @curValue, 'ǩ���ƻ����')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '_0001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 8, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(11)), 4)
		  set @curValue=@curday + '_' + @tmpStr
		end
	  end
  end
  else if @ParamId='account_num'  --�ɷ��վݺ�
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '00001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('account_num', '�ɷ��վݺ�', @curValue, '��Ʊ��ɷ��վݺ�')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '00001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 5)) + 1
		  set @tmpStr=right('00000'+ cast(@tmpValue as varchar(11)), 5)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end 
  else if @ParamId='pacs_req_num'  --PACS���뵥��
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '00001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('pacs_req_num', 'PACS���뵥��', @curValue, 'PACS���뵥�����붨��')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '00001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 5)) + 1
		  set @tmpStr=right('00000'+ cast(@tmpValue as varchar(11)), 5)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end 
  else if @ParamId='exam_no' 
  begin
         --  set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
       set @curday=CONVERT(varchar(10), getdate(), 112)
      --set @month=SUBSTRING(@curday, 3, 2)
     -- set @curday='T' + cast(cast(cast(CONVERT(varchar(10), getdate(),20) as datetime) as int) as varchar(5))
      --if @month='10' 
        --set @month='A'
      --else if @month='11' 
        --set @month='B'
      --else if @month='12' 
        --set @month='C'
      --else
        set @month= convert(varchar(1),CAST(@month as int))
        
      --set @curday='T' + SUBSTRING(@curday,1,2) + @month + SUBSTRING(@curday,5,2)
	  set @curValue=@curday + '0001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('exam_no', '����', @curValue, '�������(1)����')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,8)!=@curday)
		  set @curValue=@curday + '0001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 9, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(10)), 4)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='barcode'   
  begin
     set @curday=CONVERT(varchar(10), getdate(), 112)
     if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='09'+@curday+'0001'
		insert into sys_param1(param_id, param_name, param_value, note)
		values('barcode', '��������', @curValue, 'LIS�����')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,3,8)!=@curday)
		  set @curValue='09'+@curday+'0001' 
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 11, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(8)), 4)
		  --select '@tmpStr', @tmpStr
		  set @curValue='09'+@curday+@tmpStr
		end
	  end
  end

  else if @ParamId='vipno'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='TJ0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('vipno', '������', @curValue, '��쵵����')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL) or (substring(@curValue,1,2)!='TJ')
	      set @curValue='TJ0000001'
	    else begin
	      set @tmpValue=CONVERT(integer, substring(@curValue,3,7)) + 1
	      set @curValue='TJ'+right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	    end
	  end
  end
  else if @ParamId='contract' --��ͬ���
  begin
    set @curday=CONVERT(varchar(10), getdate(), 12)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'_0001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('contract', '��ͬ���', @curValue, '��ͬ���')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '_0001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 8, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(11)), 4)
		  set @curValue=@curday + '_' + @tmpStr
		end
	  end
  end
  else if @ParamId='arch_num_rz'
  begin
    --select substring(CONVERT(varchar(10), getdate(), 120),3,2)
    set @curday=substring(CONVERT(varchar(10), getdate(), 120),3,2)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='0'+@curday+'00001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('arch_num_rz', '�����ţ���ְ��', @curValue, 'ʡ����Ա��ְ���')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL) or (substring(@curValue,2,2)!=@curday)
	      set @curValue='0'+@curday+'00001'
	    else begin
	      set @tmpValue=CONVERT(integer, substring(@curValue,4,5)) + 1
	      set @curValue='0'+@curday+right('00000'+ cast(@tmpValue as varchar(5)), 5)
	    end
	  end
  end
  else if @ParamId='lis_order_no'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='T000000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('lis_order_no', '117 LIS', @curValue, 'LIS��order_no')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL) or (substring(@curValue,1,1)!='T')
	      set @curValue='T000000001'
	    else begin
	      set @tmpValue=CONVERT(integer, substring(@curValue,2,9)) + 1
	      set @curValue='T'+right('000000000'+ cast(@tmpValue as varchar(9)), 9)
	    end
	  end
  end
  else if @ParamId='pacs'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='00000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('pacs', '117 PACS�������', @curValue, '117 PACS�ӿ��������')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='00000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('00000000'+ cast(@tmpValue as varchar(8)), 8)
	    end
	  end
  end
  else if @ParamId='studyid'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='000000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('studyid', 'PACS��studyid', @curValue, '117ҽԺPACS��studyid')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='000000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('000000000'+ cast(@tmpValue as varchar(9)), 9)
	    end
	  end
  end
  else if @ParamId='code'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('code', '�ֵ�����', @curValue, '�ֵ�����')
	  end
	  else begin
	    set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('000000'+ cast(@tmpValue as varchar(6)), 6)
	    end
	  end
  end
  
  else if @ParamId='sample_no'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='S00001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('sample_no', '��Ŀ��������', @curValue, '��Ŀ��������')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,5) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='S00001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('00000'+ cast(@tmpValue as varchar(5)), 5)
	      set @curValue='S'+@curValue
	    end
	  end
  end
  else if @ParamId='set_num'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='S0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('set_num', '�ײͱ���', @curValue, '�ײͱ���')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='S0000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	      set @curValue='S'+@curValue
	    end
	  end
  end
  else if @ParamId='report_item_code'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='R0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('report_item_code', '������Ŀ����', @curValue, '������Ŀ����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='R0000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	      set @curValue='R'+@curValue
	    end
	  end
  end
  else if @ParamId='exam_item_code'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='E0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('exam_item_code', '�����Ŀ����', @curValue, '�����Ŀ����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='E0000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	      set @curValue='E'+@curValue
	    end
	  end
  end
  else if @ParamId='charge_item_code'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='C0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('charge_item_code', '�շ���Ŀ����', @curValue, '�շ���Ŀ����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='C0000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	      set @curValue='C'+@curValue
	    end
	  end
  end
  else if @ParamId='disease_no'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='D0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('disease_no', '������֪ʶ�����', @curValue, '������֪ʶ�����')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='D00000001'
	    else begin
	      set @tmpValue=CONVERT(integer, @curValue) + 1
	      set @curValue=right('0000000'+ cast(@tmpValue as varchar(7)), 7)
	      set @curValue='D'+@curValue
	    end
	  end
  end
  
  update sys_param1 set param_value=@curValue where param_id=@ParamId
  set @ParamValue=@curValue
  --set nocount on

  /*
    declare @ParamValue varchar(20)
  exec P_GetSysParam 'balance_num', @ParamValue out
  select @ParamValue
  
    declare @ParamValue varchar(20)
  exec P_GetSysParam 'disease_no', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'pacs_req_num', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'account_num', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'rcpt_num', @ParamValue out
  select @ParamValue
    
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'contract', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'exam_no', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'BARCODE', @ParamValue out
  select @ParamValue

  declare @ParamValue varchar(20)
  exec P_GetSysParam 'vipno', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'arch_num_rz', @ParamValue out
  select @ParamValue
  
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'lis_order_no', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'pacs', @ParamValue out
  select @ParamValue
  
   declare @ParamValue varchar(20)
  exec P_GetSysParam 'studyid', @ParamValue out
  select @ParamValue
  
   declare @ParamValue varchar(20)
  exec P_GetSysParam 'code', @ParamValue out
  select @ParamValue
  
   declare @ParamValue varchar(20)
  exec P_GetSysParam 'set_num', @ParamValue out
  select @ParamValue
  
  declare  @ParamValue  varchar(20)
  
  exec P_GetSysParam 'daily_acc_num', @ParamValue out
  select @ParamValue
  
  declare  @ParamValue  varchar(20)
  
  exec P_GetSysParam 'print_task_no', @ParamValue out
  select @ParamValue
  
   declare @ParamValue varchar(20)
  exec P_GetSysParam 'com_num', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'batch_num', @ParamValue out
  select @ParamValue
  
  declare @ParamValue varchar(20)
  exec P_GetSysParam 'group_num', @ParamValue out
  select @ParamValue
  
   declare @ParamValue varchar(20)
  exec P_GetSysParam 'icd_id', @ParamValue out
  select @ParamValue
  */




