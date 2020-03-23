

ALTER procedure [dbo].[P_GetSysParam](  --参数读取存储过程
   @ParamId varchar(20)='exam_no', /*sys_param表中的param_id的值(exam_no:体检号；barcode：条码号；vipno：档案号; lis_order_no:lis用117);
                            pacs:117PACS序号; studyid: 117医院pacs库studyid; code:字典表编码; arch_num_rz：省公务员入职体检档案号
                            contract:合同编号；rcpt_num:收费申请单号(用于HIS接口发起缴费申请)
							sample_no:项目样本编码、report_item_code:报告项目编码、exam_item_code:检查项目编码、
                            charge_item_code:收费项目编码、disease_no:疾病库知识库编码
                            pacs_req_num:pacs申请单号; account_num : 缴费收据号
                            set_num:套餐编码*/
   @ParamValue varchar(20) out     --输出参数值
) 
as
  --set nocount on
  declare @curday varchar(8),
          @curValue varchar(20),
          @tmpValue int,
          @tmpStr varchar(10),
          @head varchar(2)='TM',
          @date datetime,
          @month varchar(2)

  set @curday=CONVERT(varchar(10), getdate(), 12)
  --select @curday
  if @ParamId='rcpt_num'  --缴费申请单号
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '000001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('rcpt_num', '缴费申请单号', @curValue, '用于个人缴费申请')
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
  else if @ParamId='account_num'  --缴费收据号
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '00001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('account_num', '缴费收据号', @curValue, '发票表缴费收据号')
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
  else if @ParamId='pacs_req_num'  --PACS申请单号
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '00001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('pacs_req_num', 'PACS申请单号', @curValue, 'PACS申请单及条码定长')
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
     --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
      set @month=SUBSTRING(@curday, 3, 2)
     -- set @curday='T' + cast(cast(cast(CONVERT(varchar(10), getdate(),20) as datetime) as int) as varchar(5))
      if @month='10' 
        set @month='A'
      else if @month='11' 
        set @month='B'
      else if @month='12' 
        set @month='C'
      else
        set @month= convert(varchar(1),CAST(@month as int))
        
      set @curday='T' + SUBSTRING(@curday,1,2) + @month + SUBSTRING(@curday,5,2)
	  set @curValue=@curday + '0001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('exam_no', '体检号', @curValue, '体检中心(1)体检号')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,6)!=@curday)
		  set @curValue=@curday + '0001'
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 7, 4)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(10)), 4)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday + @tmpStr
		end
	  end
  end
  else if @ParamId='barcode'   
  begin
     set @curday=CONVERT(varchar(10), getdate(), 112)
     set @date=CONVERT(datetime, @curday)
     set @curday=CAST(convert(int, @date) as varchar(5))
     if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'00001'
		insert into sys_param1(param_id, param_name, param_value, note)
		values('barcode', '检验条码', @curValue, 'LIS条码号')
	  end
	  else begin
		set @curValue=(select param_value from sys_param1 where param_id=@ParamId)
		if (@curValue=NULL) or (substring(@curValue,1,5)!=@curday)
		  set @curValue=@curday+'00001' 
		else begin
		  set @tmpValue=CONVERT(integer, SUBSTRING(@curValue, 6, 5)) + 1
		  set @tmpStr=right('0000'+ cast(@tmpValue as varchar(10)), 5)
		  --select '@tmpStr', @tmpStr
		  set @curValue=@curday+@tmpStr
		end
	  end
  end
  else if @ParamId='vipno'
  begin
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue='TJ0000001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('vipno', '档案号', @curValue, '体检档案号')
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
  else if @ParamId='contract' --合同编号
  begin
    set @curday=CONVERT(varchar(10), getdate(), 12)
    if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
	    set @curValue=@curday+'_0001'
	    insert into sys_param1(param_id, param_name, param_value, note)
        values('contract', '合同编号', @curValue, '合同编号')
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
        values('arch_num_rz', '档案号（入职）', @curValue, '省公务员入职体检')
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
        values('lis_order_no', '117 LIS', @curValue, 'LIS库order_no')
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
        values('pacs', '117 PACS申请序号', @curValue, '117 PACS接口申请序号')
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
        values('studyid', 'PACS表studyid', @curValue, '117医院PACS表studyid')
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
        values('code', '字典表编码', @curValue, '字典表编码')
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
        values('sample_no', '项目样本编码', @curValue, '项目样本编码')
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
        values('set_num', '套餐编码', @curValue, '套餐编码')
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
        values('report_item_code', '报告项目编码', @curValue, '报告项目编码')
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
        values('exam_item_code', '检查项目编码', @curValue, '检查项目编码')
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
        values('charge_item_code', '收费项目编码', @curValue, '收费项目编码')
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
        values('report_item_code', '疾病库知识库编码', @curValue, '疾病库知识库编码')
	  end
	  else begin
	    set @curValue=(select substring(param_value,2,7) from sys_param1 where param_id=@ParamId)
	    if (@curValue=NULL)
	      set @curValue='D0000001'
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
  */
