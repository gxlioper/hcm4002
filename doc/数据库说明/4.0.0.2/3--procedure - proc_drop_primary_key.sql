-- É¾³ý±íÖ÷¼ü
if exists (select * from dbo.sysobjects 
	where id = object_id(N'proc_drop_primary_key') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure proc_drop_primary_key
go

create procedure proc_drop_primary_key
	@table_name varchar( 1000 )
with encryption
as
begin
	declare
		@pkname varchar( 8000 ),
		@sql varchar( 8000 )
	
	declare cur cursor for select distinct pkname from dbo.primarykey_of_table( @table_name )
	open cur
	fetch next from cur into @pkname

	while @@FETCH_STATUS = 0
	begin
		select @sql = 'alter table ' + @table_name + ' drop ' + @pkname
		exec( @sql )
		
		fetch next from cur into @pkname
	end

	close cur
	deallocate cur
end
go

-- É¾³ýË÷Òý
if exists (select * from dbo.sysobjects 
	where id = object_id(N'proc_drop_index') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure proc_drop_index
go

create procedure proc_drop_index
	@table_name varchar( 1000 )
with encryption
as
begin
	declare
		@index_name varchar( 8000 ),
		@sql varchar( 8000 )
	
	declare cur cursor for select distinct index_name from dbo.indexs_of_table( @table_name )
	open cur
	fetch next from cur into @index_name

	while @@FETCH_STATUS = 0
	begin
		begin try
			select @sql = 'drop index ' + @index_name + ' on ' + @table_name
			exec( @sql )
		end try
		begin catch
			select @sql = 'alter table ' + @table_name + ' drop ' + @index_name
			exec( @sql )		
		end catch
		
		fetch next from cur into @index_name
	end

	close cur
	deallocate cur
end
go
