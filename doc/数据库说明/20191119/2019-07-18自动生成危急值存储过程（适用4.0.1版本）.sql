---------------------------------------------------------------------------
--<1>
--
--作者：dangqi，2019/7/18，xian
--
--存储过程：pro_exam_critical
--
--功能概述：
--
-- 根据危急值逻辑自动生成危机值存储过程
-- 
--输入参数：
--
--输出参数：
--
-- V1.0
--
-----------------------------------------------------------------------------
if exists (select * from dbo.sysobjects 
	where id = object_id(N'pro_exam_critical_num') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure pro_exam_critical_num
go

create procedure pro_exam_critical_num(
	@exam_num varchar(50),            --体检号
	@charging_item_num varchar(50),   --收费项目编码
	@item_num varchar(50)             --检查项目编码
)
as 
begin
	declare 
		@logic_id varchar(50),
		@critical_class_parent_id int,
		@critical_class_id int,
		@critical_class_level int,
		@exam_info_id int,
		@disease_id int,
		@disease_num varchar(50),
		@critical_suggestion varchar(3000),
		@exam_result varchar(5000),
		@flag int,
		@sex varchar(10),                --性别
		@age int                         --年龄

	--根据收费项目编码、检查项目编码查询危机值逻辑
	select @exam_info_id=e.id,@sex=c.sex,@age=e.age from exam_info e,customer_info c where e.customer_id = c.id and exam_num = @exam_num

	declare cri_logic_cursor cursor for 
		select distinct l.id,l.critical_class_parent_id,l.critical_class_id,l.critical_class_level,l.critical_suggestion,l.disease_num,k.id as disease_id 
		from exam_Critical_logic l left join disease_knowloedge_lib k on l.disease_num = k.disease_num,exam_Critical_logic_item i,exam_Critical_logic_item_condition c
		where l.id = i.logic_id and i.id = c.logic_item_id and c.item_num = @item_num 
		and c.charging_item_code = @charging_item_num and l.sex in(@sex,'全部') and @age > l.age_min and @age < l.age_max
	open cri_logic_cursor
	fetch next from cri_logic_cursor into @logic_id,@critical_class_parent_id,@critical_class_id,@critical_class_level,@critical_suggestion,@disease_num,@disease_id
	
	while @@FETCH_STATUS = 0
	begin

		set @flag = 0
		declare @logic_item_id varchar(50)
		
		declare cri_item_cursor cursor for 
			select c.id from exam_Critical_logic_item c where c.logic_id = @logic_id order by c.logic_index
		open cri_item_cursor
		fetch next from cri_item_cursor into @logic_item_id
		
		while @@FETCH_STATUS = 0
		begin
			declare @iResult int,
				    @strSQL nvarchar(4000),
                    @condition varchar(10),
                    @condition_value varchar(100),
                    @con_charging_num varchar(50),
                    @con_charging_id int,
                    @con_item_num varchar(50),
                    @con_item_id int
                    
            set @iResult = 0
			set @strSQL = 'select @iResult=COUNT(*) where 1=1 '
			set @exam_result = ''
			
			declare cri_con_cursor cursor for 
				select c.condition,c.condition_value,c.charging_item_code,c.item_num from exam_Critical_logic_item_condition c
				where c.logic_item_id = @logic_item_id order by c.logic_index
			open cri_con_cursor
			
			fetch next from cri_con_cursor into @condition,@condition_value,@con_charging_num,@con_item_num
			while @@FETCH_STATUS = 0
			begin
				declare 
					@result varchar(500),
					@ref_value varchar(100),
					@item_name varchar(100),
					@temp_result varchar(500),
					@item_unit varchar(50)
				
				select @result=c.exam_result,@item_name=c.item_name,@ref_value=c.ref_value,@item_unit=c.item_unit from v_critical_exam_result_num c 
				where c.item_num = @con_item_num and c.item_code = @con_charging_num and c.exam_num = @exam_num
				
				if @result is null  --检查结果为null，跳出本次循环
					break
				--将项目名称+检查结果+参考值
				if @ref_value = ''
					set @temp_result = @item_name + ':' + @result + @item_unit + '  '
				else
					set @temp_result = @item_name + ':' + @result + @item_unit + '('+@ref_value+')  '
				if (charindex(@temp_result,@exam_result) = 0)
					set @exam_result = @exam_result + @temp_result
				
				--判断逻辑条件组装	
				if (@condition in ('>', '>=', '<', '<=')) and (ISNUMERIC(@result)>0) and (ISNUMERIC(@condition_value)>0)
					set @strSQL = @strSQL + ' and ' + @result + @condition + @condition_value
				else if @condition='in'
					set @strSQL = @strSQL + ' and charindex('''+@condition_value+''','''+@result+''')>0 ' 
				else if @condition='not in'
					set @strSQL = @strSQL + ' and not charindex('''+@condition_value+''', ''' +@result+''')>0 '
				
				set @result = null
				
				fetch next from cri_con_cursor into @condition,@condition_value,@con_charging_num,@con_item_num
			end
			close cri_con_cursor
			deallocate cri_con_cursor
			
			print @exam_result
			print @strSQL
			if LEN(@strSQL)>LEN('select @iResult=COUNT(*) where 1=1 ')
			begin
				exec sp_executesql @strSQL,N'@iResult int output',@iResult output
			end
			if @iResult > 0
			begin
				set @flag = 1
				break
			end
			fetch next from cri_item_cursor into @logic_item_id
		end
		close cri_item_cursor
		deallocate cri_item_cursor
		
		declare @critical_id int,@done_flag int
		
		select @critical_id = c.id, @done_flag=c.done_flag from exam_Critical_detail c 
		where c.exam_info_id = @exam_info_id and c.critical_class_parent_id = @critical_class_parent_id 
		and c.critical_class_id = @critical_class_id
		
		begin try
		if @flag = 1
		begin
			if @critical_id is null  --危急值不存在，新增插入危急值
				insert into exam_Critical_detail(exam_info_id,
												 exam_result,
												 disease_id,
												 done_flag,
												 is_active,
												 data_source,
												 exam_num,
												 disease_num,
												 critical_class_parent_id,
												 critical_class_id,
												 critical_class_level,
												 critical_suggestion) 
										  values(@exam_info_id,
										         @exam_result,
										         @disease_id,
										         '0',
										         'Y',
										         '0',
										         @exam_num,
										         @disease_num,
										         @critical_class_parent_id,
										         @critical_class_id,
										         @critical_class_level,
										         @critical_suggestion)
			else
				update exam_Critical_detail set exam_result = @exam_result where id = @critical_id	
		end
		else if @flag = 0
			if @critical_id > 0
				delete exam_Critical_detail where id = @critical_id
		end try
		begin catch
	    PRINT '出现异常，错误编号：' + convert(varchar,error_number()) + ',错误消息：' + error_message()
	    end catch
		fetch next from cri_logic_cursor into @logic_id,@critical_class_parent_id,@critical_class_id,@critical_class_level,@critical_suggestion,@disease_num,@disease_id
	end     
	close cri_logic_cursor
	deallocate cri_logic_cursor
end

/*********************
exec pro_exam_critical 'T178230039','C0000054','WL004'
**********************/