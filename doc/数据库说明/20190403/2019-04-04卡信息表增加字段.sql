alter table card_info add sale_status int not null default(0) --卡信息表增加售卡状态字段0未售卡、1已售卡


--生成号码存储过程增加售卡交易流水号
else if @ParamId='card_sale_num'  --售卡交易流水号
  begin
      --set @curday=CONVERT(varchar(10), getdate(), 112)  --yyyymmdd
      set @curday=CONVERT(varchar(10), getdate(), 12)
	  set @curValue=@curday + '00001'
	  if not exists (select param_id from sys_param1 where param_id=@ParamId)
	  begin
		insert into sys_param1(param_id, param_name, param_value, note)
		values('card_sale_num', '售卡交易流水号', @curValue, '售卡交易流水号')
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