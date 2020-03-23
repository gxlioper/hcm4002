---------------------------------------------------------------------------
--<1>
--
--���ߣ�jdf
--
--�洢���̣�P_GetSysParam
--
--���ܸ�����
--		��ȡ����ֵ��������ŵ�
--
--���������
-- @ParamId varchar( 20 ) exam_no - ���ţ�barcode - ����ţ�vipno - �����ţ�lis_order_no - 117lis����
--						  pacs -- 117PACS��ţ�studyid - 117ҽԺpacs��studyid��code - �ֵ����룻arch_num_rz - ʡ����Ա��ְ��쵵����
--						  contract - ��ͬ��ţ�rcpt_num - �շ����뵥��(����HIS�ӿڷ���ɷ�����)
--						  sample_no - ��Ŀ�������룻report_item_code - ������Ŀ���룻exam_item_code - �����Ŀ����
--						  charge_item_code - �շ���Ŀ���룻disease_no - ������֪ʶ�����
--						  pacs_req_num - pacs���뵥�ţ�account_num - �ɷ��վݺ�
--						  set_num - �ײͱ��룻balance_num - ������㵥��
--						  daily_acc_num - �շ�Ա�ս�ţ�fd_acc_num - �������ս�ţ�print_task_no - ��ӡ�����
--						  com_num:��λ���룻batch_num - ���α��룻group_num - ������룻icd_id - ICD10����
--
--���������
-- @ParamValue varchar( 20 ) 
--
-- V1.0
--
-- V1.1 sumrain 2018/07/01 ����
--      LIS����� pre_code( Ĭ��Ϊ8 ) + yymmdd + ˳���( ������param_lenָ����Ĭ��Ϊ3 )
--		���� pre_code( Ĭ��Ϊ'' ) + yymmdd + ˳���( ������param_lenָ����Ĭ��Ϊ4 )
--		PACS���뵥�����붨�� pre_code( Ĭ��Ϊ'0' ) + yymmdd + ˳���( ������param_lenָ����Ĭ��Ϊ4 )
--		��쵵���� pre_code( Ĭ��Ϊ'TJ' ) + ˳���( ������param_lenָ����Ĭ��Ϊ8 )
--
--
-- V1.2 sumrain 2018/11/11
-- ����λ����(com_num)�����α���(batch_num)��ʽ��Ϊ��yyyy+��λ˳���
--
-- V1.3 sumrain 2019/04/06
-- �����վݺ�rcpt_no
--
-- V1.4 sumrain 2019/05/24
-- �����ۿ�������ˮ��card_sale_num
--
-- V1.5 sumrain 2019/06/17
-- �޸�������㵥�� TJ190617001
--
-- V1.5 sumrain 2019/07/05
-- ���� daily_acc_num_class���շ�Ա�ս�������ˮ��
--
-- V1.6 sumrain 2019/08/30
-- ���Ӳ���@center_num����������Ĳ���
--
-- V1.7 sumrain 2019/09/09
-- ����š��������롢PACS���뵥�š��շ����뵥������Ժ������λ���롢���α���
-- 'exam_no', 'barcode', 'pacs_req_num', 'rcpt_num', 'com_num', 'batch_num'
-- ����������ģ������������������
--
-----------------------------------------------------------------------------
if exists (select * from dbo.sysobjects 
	where id = object_id(N'P_GetSysParam') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure P_GetSysParam
go

create procedure P_GetSysParam
	-- ������
	@ParamId varchar( 20 ) = 'exam_no',
	-- �������ֵ
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
	
	-- ����š��������롢PACS���뵥�š��շ����뵥������Ժ������λ���롢���α���
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
	
	 -- ��ӡ�����
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
				'��ӡ�����',
				@curValue,
				'��ӡ�����',
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
	-- ICD10����
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
				'ICD����',
				@curValue,
				'ICD����',
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
	-- ��λ����
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
				'��λ����',
				@curValue,
				'��λ����',
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
				-- ��ų������ֵ
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
	-- �������(���α���)
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
				'���α���',
				@curValue,
				'�������(���α���)',
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
				-- ��ų������ֵ
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
	-- �������
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
				'�������',
				@curValue,
				'�������',
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
	-- ������㵥��
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
				'������㵥��',
				@curValue,
				'�����������',
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
				-- ��ų������ֵ
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
	-- �շ�Ա�ս��
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
				'�շ�Ա�ս��',
				@curValue,
				'�շ�Ա�ս��',
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
	-- �������ս��
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
				'�������ս��',
				@curValue,
				'�������ս��',
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
	-- �ɷ����뵥��
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
				'�ɷ����뵥��',
				@curValue,
				'���ڸ��˽ɷ�����',
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
				-- ��ų������ֵ
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
	-- �վݺ�
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
				'�վݺ�',
				@curValue,
				'�վݺ�',
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
				-- ��ų������ֵ
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
	-- �����ƻ����
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
				'�����ƻ����',
				@curValue,
				'�����ƻ����',
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
	-- ǩ���ƻ����
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
				'ǩ���ƻ����',
				@curValue,
				'ǩ���ƻ����',
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
	--�ɷ��վݺ�
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
				'�ɷ��վݺ�',
				@curValue,
				'��Ʊ��ɷ��վݺ�',
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
	-- PACS���뵥�� 
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
				'PACS���뵥��',
				@curValue,
				'PACS���뵥�����붨��',
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
				-- ��ų������ֵ
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
	-- �ۿ�������ˮ��
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
				'�ۿ�������ˮ��',
				@curValue,
				'�ۿ�������ˮ��',
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
				-- ��ų������ֵ
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
	-- �շ�Ա�ս�������ˮ��
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
				'�շ�Ա�ս�������ˮ��',
				@curValue,
				'�շ�Ա�ս�������ˮ��',
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
				-- ��ų������ֵ
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
	-- �����
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
				'����',
				@curValue,
				'�������(1)����',
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
				-- ��ų������ֵ
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
	-- LIS�����
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
				'��������',
				@curValue,
				'LIS�����',
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
				-- ��ų������ֵ
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
	-- ��쵵����
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
				'������',
				@curValue,
				'��쵵����',
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
				-- ��ų������ֵ
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
	-- ��ͬ���
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
				'��ͬ���',
				@curValue,
				'��ͬ���',
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
	-- ʡ����Ա��ְ���
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
				'�����ţ���ְ��',
				@curValue,
				'ʡ����Ա��ְ���',
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
	-- 117lis����
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
				'LIS��order_no',
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
	-- 117 PACS�ӿ��������
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
					'117 PACS�������',
					@curValue,
					'117 PACS�ӿ��������',
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
	-- 117ҽԺPACS��studyid
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
					'PACS��studyid',
					@curValue,
					'117ҽԺPACS��studyid',
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
	-- �ֵ�����
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
				'�ֵ�����',
				@curValue,
				'�ֵ�����',
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
	-- ��Ŀ��������
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
				'��Ŀ��������',
				@curValue,
				'��Ŀ��������',
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
	-- �ײͱ���
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
				'�ײͱ���',
				@curValue,
				'�ײͱ���',
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
	-- ������Ŀ����
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
				'������Ŀ����',
				@curValue,
				'������Ŀ����',
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
	-- �����Ŀ����
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
				'�����Ŀ����',
				@curValue,
				'�����Ŀ����',
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
	-- �շ���Ŀ����
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
				'�շ���Ŀ����',
				@curValue,
				'�շ���Ŀ����',
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
	-- ������֪ʶ�����
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
				'������֪ʶ�����',
				@curValue,
				'������֪ʶ�����',
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
	-- ���Ҳ�����ӡ��ˮ��
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
				'���Ҳ�����ӡ��ˮ��',
				@curValue,
				'���Ҳ�����ӡ��ˮ��',
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
