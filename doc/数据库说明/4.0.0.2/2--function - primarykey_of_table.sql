-- 查询表中主键
if exists (select * from dbo.sysobjects 
	where id = object_id(N'primarykey_of_table') and xtype in (N'FN', N'IF', N'TF'))
drop function primarykey_of_table
go

create function primarykey_of_table
(
	@table_name nvarchar( 1000 )
)
returns @pkey table
(
	pkname varchar( 8000 ),
	table_name varchar( 1000 ),
	pk_columns varchar( 8000 )
)
with encryption
as
begin
	insert into @pkey
	(
		pkname,
		table_name,
		pk_columns
	)
	select
		p.name as primary_key_name,
		t.name as table_name,
		c.name as pkcolumns
	from sys.objects t
	inner join sys.objects p on t.object_id = p.parent_object_id and t.type = 'U' and p.type = 'PK'
	inner join sys.SysColumns c on c.id = t.object_id 
	inner join sysindexes i on i.name = p.name
	inner join sysindexkeys k on k.id = c.id and k.colid = c.colid and k.indid = i.indid
	where t.object_id = object_id( @table_name )
	order by t.name, p.name, c.name
	
	return
end
go
