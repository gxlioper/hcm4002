-- ����������
if( COL_LENGTH( 'exam_info', 'join_operator' ) is null )
begin
	alter table exam_info add join_operator int not null default( 0 )
end
go

-- ¼����
if( COL_LENGTH( 'examinfo_charging_item', 'inputter' ) is null )
begin
	alter table examinfo_charging_item add inputter int not null default( 0 )
end
go


