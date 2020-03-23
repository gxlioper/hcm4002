---------------------------------------------------------------------------
--<1>
--
--作者：jdf
--
--存储过程：P_GetSysParam
--
--功能概述：
--		获取参数值，如体检编号等
--
--输入参数：
-- @ParamId varchar( 20 ) exam_no - 体检号；barcode - 条码号；vipno - 档案号；lis_order_no - 117lis条码
--						  pacs -- 117PACS序号；studyid - 117医院pacs库studyid；code - 字典表编码；arch_num_rz - 省公务员入职体检档案号
--						  contract - 合同编号；rcpt_num - 收费申请单号(用于HIS接口发起缴费申请)
--						  sample_no - 项目样本编码；report_item_code - 报告项目编码；exam_item_code - 检查项目编码
--						  charge_item_code - 收费项目编码；disease_no - 疾病库知识库编码
--						  pacs_req_num - pacs申请单号；account_num - 缴费收据号
--						  set_num - 套餐编码；balance_num - 团体结算单号
--						  daily_acc_num - 收费员日结号；fd_acc_num - 财务部门日结号；print_task_no - 打印任务号
--						  com_num:单位编码；batch_num - 批次编码；group_num - 分组编码；icd_id - ICD10编码
--
--输出参数：
-- @ParamValue varchar( 20 ) 
--
-- V1.0
--
-- V1.1 sumrain 2018/07/01 整理
--      LIS条码号 pre_code( 默认为8 ) + yymmdd + 顺序号( 长度由param_len指定，默认为3 )
--		体检号 pre_code( 默认为'' ) + yymmdd + 顺序号( 长度由param_len指定，默认为4 )
--		PACS申请单及条码定长 pre_code( 默认为'0' ) + yymmdd + 顺序号( 长度由param_len指定，默认为4 )
--		体检档案号 pre_code( 默认为'TJ' ) + 顺序号( 长度由param_len指定，默认为8 )
--
--
-- V1.2 sumrain 2018/11/11
-- 将单位编码(com_num)、批次编码(batch_num)格式改为：yyyy+四位顺序号
--
-- V1.3 sumrain 2019/04/06
-- 增加收据号rcpt_no
--
-- V1.4 sumrain 2019/05/24
-- 增加售卡交易流水号card_sale_num
--
-- V1.5 sumrain 2019/06/17
-- 修改团体结算单号 TJ190617001
--
-- V1.5 sumrain 2019/07/05
-- 增加 daily_acc_num_class，收费员日结类型流水号
--
-- V1.6 sumrain 2019/08/30
-- 增加参数@center_num，多体检中心参数
--
-- V1.7 sumrain 2019/09/09
-- 体检编号、检验条码、PACS申请单号、收费申请单号区分院区、单位编码、批次编码
-- 'exam_no', 'barcode', 'pacs_req_num', 'rcpt_num', 'com_num', 'batch_num'
-- 区分体检中心，其他不区分体检中心
--
-----------------------------------------------------------------------------
if exists (select * from dbo.sysobjects 
	where id = object_id(N'P_GetSysParam') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure P_GetSysParam
go

create procedure P_GetSysParam
	-- 参数名
	@ParamId varchar( 20 ) = 'exam_no',
	-- 输出参数值
	@ParamValue varchar( 20 ) out,
	@center_num varchar( 50 ) = '001'
with encryption
as
begin

	declare @curday varchar( 8 ),
		@fcurday varchar( 8 ),
		@curValue varchar( 20 ),
		@tmpValue int,
		@tmpStr varchar( 10 ),
		@head varchar( 10 ) = 'TM',
		@date datetime,
		@param_value varchar( 20 ),
		@pre_code varchar( 6 ),
		@param_len int,
		@year varchar( 2 ),
		@isExists int,
		@max varchar( 20 ),
		@min varchar( 20 )

	select @max = '99999999999999999999',
			@min = '00000000000000000000'
			
	if( @center_num is null or LTRIM( rtrim( @center_num ) ) = '' )
	begin
		select @center_num = '001'
	end
	
	-- 体检编号、检验条码、PACS申请单号、收费申请单号区分院区、单位编码、批次编码
	if @ParamId not in ( 'exam_no', 'barcode', 'pacs_req_num', 'rcpt_num', 'com_num', 'batch_num' )
	begin
		select @center_num = '001'
	end
	
	select @year = substring( CONVERT( varchar( 10 ), getdate( ), 112 ), 1, 4 )
	-- yyMMdd
	select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
	-- yyyyMMdd
	select @fcurday = CONVERT( varchar( 10 ), getdate( ), 112 )
	
	select @isExists = 0
	declare cur_id cursor SCROLL_LOCKS for select param_value, pre_code, param_len from sys_param1 where param_id = @ParamId and center_num = @center_num for update
	open cur_id
	fetch next from cur_id into @param_value, @pre_code, @param_len
	if( @@FETCH_STATUS = 0 )
	begin
		select @isExists = 1
	end
	
	if( @pre_code is null )
	begin
		select @pre_code = ''
	end
	
	 -- 打印任务号
	if @ParamId = 'print_task_no'
	begin
		select @curday = cast( cast( CONVERT( varchar( 10 ), GETDATE( ),20 ) as datetime ) as int )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = @curday + '00001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'print_task_no',
				'打印任务号',
				@curValue,
				'打印任务号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 5 ) != @curday )
			begin
				select @curValue = @curday + '00001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 6, 5 ) ) + 1
				select @tmpStr = right( '00000' + cast( @tmpValue as varchar( 5 ) ), 5 )
				select @curValue = @curday + @tmpStr
			end
		end
	end
	-- ICD10编码
	else if @ParamId = 'icd_id'
	begin
		select @head = 'icd_'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = @head + '00001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'icd_id',
				'ICD编码',
				@curValue,
				'ICD编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 5 , 5 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 5 , 5 )
			if( @curValue is null )
			begin
				select @curValue = @head + '00001'
			end
			else
			begin
				select @tmpValue =  CONVERT( integer, @curValue ) + 1
				select @curValue = right( '00000' + cast( @tmpValue as varchar( 5 ) ), 5 )
				select @curValue = @head + @curValue
			end
		end
	end
	-- 单位编码
	else if @ParamId = 'com_num'
	begin
		select @head = SUBSTRING( @fcurday, 1, 4 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @param_len = 4, @curValue = @head + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = ''
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'com_num',
				'单位编码',
				@curValue,
				'单位编码',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 3, 5 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 5, LEN( @param_value ) - 4 )
			if( @curValue is null or substring( @param_value, 1, 4 ) < @head )
			begin
				-- select @curValue = @head + '00001'
				select @curValue = @head + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @curValue = CAST( @tmpValue as varchar( 20 ) )
				end
				else
				begin
					select @curValue = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
				end

				select @curValue = @head + @curValue
			end
		end
	end
	-- 任务编码(批次编码)
	else if @ParamId = 'batch_num'
	begin
		-- select @head = 'B' + cast( cast( cast( CONVERT( varchar( 10 ), getdate( ), 12 ) as datetime ) as int ) as varchar( 5 ) )
		select @head = SUBSTRING( @fcurday, 1, 4 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			-- select @curValue = @head + '001'
			select @param_len = 4, @curValue = @head + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = ''
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'batch_num',
				'批次编码',
				@curValue,
				'任务编码(批次编码)',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			-- select @curValue = @param_value
			select @curValue = substring( @param_value, 5, LEN( @param_value ) - 4 )
			if( ( @curValue is null ) or ( substring( @param_value, 1, 4 ) < @head ) )
			begin
				-- select @curValue = @head + '001'
				select @curValue = @head + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @curValue = CAST( @tmpValue as varchar( 20 ) )
				end
				else
				begin
					select @curValue = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
				end
							
				-- select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, 3 ) ) + 1
				-- select @curValue = right( '000' + cast( @tmpValue as varchar( 3 ) ), 3 )
				select @curValue = @head + @curValue
			end
		end
	end
	-- 分组编码
	else if @ParamId = 'group_num'
	begin
		select @head = 'G' + cast( cast( cast( CONVERT( varchar( 10 ), getdate( ), 12 ) as datetime ) as int ) as varchar( 5 ) )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = @head + '001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'group_num',
				'分组编码',
				@curValue,
				'分组编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) != @head )
			begin
				select @curValue = @head + '001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, 3 ) ) + 1
				select @curValue = right( '000' + cast( @tmpValue as varchar( 3 ) ), 3 )
				select @curValue = @head + @curValue
			end
		end
	end
	-- 团体结算单号
	else if @ParamId = 'balance_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		if( @isExists = 0 )
		begin
			select @param_len = 3, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = 'J'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'balance_num',
				'团体结算单号',
				@curValue,
				'用于团体结算',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 收费员日结号
	else if @ParamId = 'daily_acc_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		select @curValue = 'C' + @curday + '000001'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'daily_acc_num',
				'收费员日结号',
				@curValue,
				'收费员日结号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 2, 6 )!= @curday )
			begin
				select @curValue = 'C' + @curday + '000001'
			end
			else
			begin
				select @tmpValue = CONVERT(integer, SUBSTRING(@curValue, 8, 6 ) ) + 1
				select @tmpStr = right( '000000' + cast( @tmpValue as varchar( 10 ) ), 6 )
				select @curValue = 'C' + @curday + @tmpStr
		end
		end
	end
	-- 财务部门日结号
	else if @ParamId = 'fd_acc_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		select @curValue = 'F' + @curday + '000001'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'fd_acc_num',
				'财务部门日结号',
				@curValue,
				'财务部门日结号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 2, 6 ) != @curday )
			begin
				select @curValue = 'F' + @curday + '000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 8, 6 ) ) + 1
				select @tmpStr = right( '000000' + cast( @tmpValue as varchar( 10 ) ), 6 )
				select @curValue = 'F' + @curday + @tmpStr
			end
		end
	end
	-- 缴费申请单号
	else if @ParamId = 'rcpt_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		if( @isExists = 0 )
		begin
			select @param_len = 3, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = 'J'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'rcpt_num',
				'缴费申请单号',
				@curValue,
				'用于个人缴费申请',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 收据号
	else if @ParamId = 'rcpt_no'
	begin
		if( @isExists = 0 )
		begin
			select @param_len = 6, @curValue = SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = '13'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'rcpt_no',
				'收据号',
				@curValue,
				'收据号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			select @curValue = @param_value
			if( @curValue is null )
			begin
				select @curValue = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @curValue = CAST( @tmpValue as varchar( 20 ) )
				end
				else
				begin
					select @curValue = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
				end
			end
		end
	end	
	-- 健康计划编号
	else if @ParamId = 'visit_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- if not exists (select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = @curday + '_0001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'visit_num',
				'健康计划编号',
				@curValue,
				'健康计划编号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) != @curday )
			begin
				select @curValue = @curday + '_0001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 8, 4 ) ) + 1
				select @tmpStr = right( '0000' + cast( @tmpValue as varchar( 11 ) ), 4 )
				select @curValue = @curday + '_' + @tmpStr
			end
		end
	end
	-- 签单计划编号
	else if @ParamId = 'sign_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = @curday + '_0001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'sign_num',
				'签单计划编号',
				@curValue,
				'签单计划编号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) != @curday )
			begin
				select @curValue = @curday + '_0001'
			end
			else 
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 8, 4 ) ) + 1
				select @tmpStr = right( '0000' + cast( @tmpValue as varchar( 11 ) ), 4 )
				select @curValue = @curday + '_' + @tmpStr
			end
		end
	end
	--缴费收据号
	else if @ParamId = 'account_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		select @curValue = @curday + '00001'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'account_num',
				'缴费收据号',
				@curValue,
				'发票表缴费收据号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) != @curday )
			begin
				select @curValue = @curday + '00001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, 5 ) ) + 1
				select @tmpStr = right( '00000' + cast( @tmpValue as varchar( 11 ) ), 5 )
				select @curValue = @curday + @tmpStr
			end
		end
	end
	-- PACS申请单号 
	else if @ParamId = 'pacs_req_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @param_len = 4, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = '0'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'pacs_req_num',
				'PACS申请单号',
				@curValue,
				'PACS申请单及条码定长',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				-- select @curValue = @curday + '0001'
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				-- select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, 5 ) ) + 1
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 售卡交易流水号
	else if @ParamId = 'card_sale_num'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		if( @isExists = 0 )
		begin
			select @param_len = 6, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = ''
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'card_sale_num',
				'售卡交易流水号',
				@curValue,
				'售卡交易流水号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 收费员日结类型流水号
	else if @ParamId = 'daily_acc_num_class'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		if( @isExists = 0 )
		begin
			select @param_len = 4, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = ''
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'daily_acc_num_class',
				'收费员日结类型流水号',
				@curValue,
				'收费员日结类型流水号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 体检编号
	else if @ParamId = 'exam_no' 
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- select @curValue = @curday + '0001'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @param_len = 4, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = ''
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'exam_no',
				'体检号',
				@curValue,
				'体检中心(1)体检号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				-- select @curValue = @curday + '00001'
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				-- select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, 5 ) ) + 1
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end
				else
				begin
					-- select @tmpStr = right( '00000' + cast( @tmpValue as varchar( 11 ) ), 5 )
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					-- select @curValue = @curday + @tmpStr
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- LIS条码号
	else if @ParamId = 'barcode'   
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			-- set @curValue = '09' + @curday + '0001'
			select @param_len = 3, @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = '8'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'barcode',
				'检验条码',
				@curValue,
				'LIS条码号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = ( select param_value from sys_param1 where param_id = @ParamId )
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) < @curday )
			begin
				-- select @curValue = '09' + @curday + '0001'
				select @curValue = @curday + SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				-- select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 11, 4 ) ) + 1
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 7, @param_len ) ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @tmpStr = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
					select @curValue = convert( varchar( 10 ), cast( SUBSTRING( @year, 1, 2 ) + SUBSTRING( @curValue, 1, 6 ) as datetime ) + 1, 12 ) + @tmpStr
				end				
				else
				begin
					-- select @tmpStr = right( '0000' + cast( @tmpValue as varchar( 8 ) ), 4 )
					select @tmpStr = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
					-- select @curValue = '09' + @curday + @tmpStr
					select @curValue = SUBSTRING( @curValue, 1, 6 ) + @tmpStr
				end
			end
		end
	end
	-- 体检档案号
	else if @ParamId = 'vipno'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			-- set @curValue = 'TJ0000001'
			select @param_len = 8, @curValue = SUBSTRING( @min, 1, @param_len - 1 ) + '1', @pre_code = 'TJ'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				pre_code,
				param_len,
				center_num
			)
			values
			(
				'vipno',
				'档案号',
				@curValue,
				'体检档案号',
				@pre_code,
				@param_len,
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null )
			begin
				select @curValue = SUBSTRING( @min, 1, @param_len - 1 ) + '1'
			end
			else
			begin
				-- select @tmpValue = CONVERT( integer, substring( @curValue, 3, 7 ) ) + 1
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				-- 序号超出最大值
				if( @tmpValue > CONVERT( integer, SUBSTRING( @max, 1, @param_len ) ) )
				begin
					select @curValue = CAST( @tmpValue as varchar( 20 ) )
				end
				else
				begin
					-- select @curValue = 'TJ' + right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
					select @curValue = right( SUBSTRING( @min, 1, @param_len ) + cast( @tmpValue as varchar( 20 ) ), @param_len )
				end
			end
		end
	end
	-- 合同编号
	else if @ParamId = 'contract'
	begin
		-- yyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 12 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			set @curValue = @curday + '_0001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'contract',
				'合同编号',
				@curValue,
				'合同编号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 6 ) != @curday )
			begin
				select @curValue = @curday + '_0001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 8, 4 ) ) + 1
				select @tmpStr = right( '0000' + cast( @tmpValue as varchar( 11 ) ), 4 )
				select @curValue = @curday + '_' + @tmpStr
			end
		end
	end
	-- 省公务员入职体检
	else if @ParamId = 'arch_num_rz'
	begin
		-- yy
		select @curday = substring( CONVERT( varchar( 10 ), getdate( ), 120 ), 3, 2 )
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = '0' + @curday + '00001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'arch_num_rz',
				'档案号（入职）',
				@curValue,
				'省公务员入职体检',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 2, 2 ) != @curday )
			begin
				select @curValue = '0' + @curday + '00001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, substring( @curValue, 4, 5 ) ) + 1
				select @curValue = '0' + @curday + right( '00000' + cast( @tmpValue as varchar( 5 ) ), 5 )
			end
		end
	end
	-- 117lis条码
	else if @ParamId = 'lis_order_no'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			set @curValue = 'T000000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'lis_order_no',
				'117 LIS',
				@curValue,
				'LIS库order_no',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 1 ) != 'T' )
			begin
				select @curValue = 'T000000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, substring( @curValue, 2, 9 ) ) + 1
				select @curValue = 'T' + right( '000000000' + cast( @tmpValue as varchar( 9 ) ), 9 )
			end
		end
	end
	-- 117 PACS接口申请序号
	else if @ParamId = 'pacs'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
				select @curValue = '00000001'
				insert into sys_param1
				(
					param_id,
					param_name,
					param_value,
					note,
					center_num
				)
				values
				(
					'pacs',
					'117 PACS申请序号',
					@curValue,
					'117 PACS接口申请序号',
					@center_num
				)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null )
			begin
				select @curValue = '00000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '00000000' + cast( @tmpValue as varchar( 8 ) ), 8 )
			end
		end
	end
	-- 117医院PACS表studyid
	else if @ParamId = 'studyid'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
				set @curValue = '000000001'
				insert into sys_param1
				(
					param_id,
					param_name,
					param_value,
					note,
					center_num
				)
				values
				(
					'studyid',
					'PACS表studyid',
					@curValue,
					'117医院PACS表studyid',
					@center_num
				)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null )
			begin
				select @curValue = '000000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '000000000' + cast( @tmpValue as varchar( 9 ) ), 9 )
			end
		end
	end
	-- 字典表编码
	else if @ParamId = 'code'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = '000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'code',
				'字典表编码',
				@curValue,
				'字典表编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null )
			begin
				select @curValue = '000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '000000' + cast( @tmpValue as varchar( 6 ) ), 6 )
			end
		end
	end
	-- 项目样本编码
	else if @ParamId = 'sample_no'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'S00001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'sample_no',
				'项目样本编码',
				@curValue,
				'项目样本编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 2, 5 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 5 )
			if( @curValue is null )
			begin
				select @curValue = 'S00001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '00000' + cast( @tmpValue as varchar( 5 ) ), 5 )
				select @curValue = 'S' + @curValue
			end
		end
	end
	-- 套餐编码
	else if @ParamId = 'set_num'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'S0000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'set_num',
				'套餐编码',
				@curValue,
				'套餐编码',
				@center_num
			)
			end
		else
		begin
			-- select @curValue = substring( param_value, 2, 7 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 7 )
			if( @curValue is null )
			begin
				select @curValue = 'S0000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
				select @curValue = 'S' + @curValue
			end
		end
	end
	-- 报告项目编码
	else if @ParamId = 'report_item_code'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'R0000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'report_item_code',
				'报告项目编码',
				@curValue,
				'报告项目编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 2, 7 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 7 )
			if( @curValue is null )
			begin
				select @curValue = 'R0000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
				select @curValue = 'R' + @curValue
			end
		end
	end
	-- 检查项目编码
	else if @ParamId = 'exam_item_code'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'E0000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'exam_item_code',
				'检查项目编码',
				@curValue,
				'检查项目编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 2, 7 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 7 )
			if( @curValue is null )
			begin
				select @curValue = 'E0000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
				select @curValue = 'E' + @curValue
			end
		end
	end
	-- 收费项目编码
	else if @ParamId = 'charge_item_code'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'C0000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'charge_item_code',
				'收费项目编码',
				@curValue,
				'收费项目编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 2, 7 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 7 )
			if( @curValue is null )
			begin
				select @curValue = 'C0000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
				select @curValue = 'C' + @curValue
			end
		end
	end
	-- 疾病库知识库编码
	else if @ParamId = 'disease_no'
	begin
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			select @curValue = 'D0000001'
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'disease_no',
				'疾病库知识库编码',
				@curValue,
				'疾病库知识库编码',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = substring( param_value, 2, 7 ) from sys_param1 where param_id = @ParamId
			select @curValue = substring( @param_value, 2, 7 )
			if( @curValue is null )
			begin
				select @curValue = 'D00000001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, @curValue ) + 1
				select @curValue = right( '0000000' + cast( @tmpValue as varchar( 7 ) ), 7 )
				select @curValue = 'D' + @curValue
			end
		end
	end
	-- 科室采样打印流水号
	else if @ParamId = 'dep_sample_num'
	begin
		-- yyyyMMdd
		select @curday = CONVERT( varchar( 10 ), getdate( ), 112 )
		select @curValue = @curday + '0001'
		-- if not exists ( select param_id from sys_param1 where param_id = @ParamId )
		if( @isExists = 0 )
		begin
			insert into sys_param1
			(
				param_id,
				param_name,
				param_value,
				note,
				center_num
			)
			values
			(
				'dep_sample_num',
				'科室采样打印流水号',
				@curValue,
				'科室采样打印流水号',
				@center_num
			)
		end
		else
		begin
			-- select @curValue = param_value from sys_param1 where param_id = @ParamId
			select @curValue = @param_value
			if( @curValue is null ) or ( substring( @curValue, 1, 8 ) != @curday )
			begin
				select @curValue = @curday + '0001'
			end
			else
			begin
				select @tmpValue = CONVERT( integer, SUBSTRING( @curValue, 9, 4 ) ) + 1
				select @tmpStr = right( '0000' + cast( @tmpValue as varchar( 10 ) ), 4 )
				select @curValue = @curday + @tmpStr
			end
		end
	end

	-- update sys_param1
	-- set param_value = @curValue
	-- where param_id = @ParamId
	
	if( @isExists = 1 )
	begin
		update sys_param1 set param_value = @curValue where current of cur_id
	end
	
	close cur_id
	deallocate cur_id
	
	select @ParamValue = @pre_code + @curValue

end
go
