if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_name') 
begin
alter table exam_ext_info add ti_name varchar(20);  --新增体检者姓名
end


if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_sex') 
begin
alter table exam_ext_info add ti_sex varchar(4);--新增体检者性别
end


if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_age') 
begin
alter table exam_ext_info add ti_age int NOT NULL  default(0);--新增体检者年龄
end

if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_id_num') 
begin
alter table exam_ext_info add ti_id_num varchar(20);--新增体检者身份证
end