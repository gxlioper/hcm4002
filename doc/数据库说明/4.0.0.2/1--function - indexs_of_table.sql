-- 查询表中所有索引
if exists (select * from dbo.sysobjects 
	where id = object_id(N'indexs_of_table') and xtype in (N'FN', N'IF', N'TF'))
drop function indexs_of_table
go

create function indexs_of_table
(
	@table_name varchar( 1000 )
)
returns @indexs table
(
	index_name varchar( 8000 ),
	table_name varchar( 1000 ),
	[index_columns] varchar( 8000 )
)
with encryption
as
begin
	insert into @indexs
	(
		index_name,
		table_name,
		[index_columns]
	)
	select
		a.name as indexname,
		c.name as tablename,
		d.name as indexcolumns
		-- a .indid
	from sysindexes a
	join sysindexkeys b on a.id = b.id and a.indid = b.indid
	join sysobjects c on b.id = c.id
	join syscolumns d on b.id = d.id and b.colid = d.colid
	where a.indid not in ( 0 , 255 )
	-- 查所有用户表
	-- and c.xtype = 'U'
	-- and c.status > 0
	-- 查指定表
	and c.name = @table_name
	order by c.name, a.name, d.name
	
	return
end
go
