if exists(select * from sys.triggers where name='trig_insert_exam_summary')
drop trigger trig_insert_exam_summary;
go

if exists(select * from sys.triggers where name='trig_update_exam_summary')
drop trigger trig_update_exam_summary;
go


--删除主键
alter table report_pdf drop constraint PK__report_p__C60997C804859529
--新增主键
alter table report_pdf add id int not null identity(1,1)  PRIMARY KEY 

--
INSERT INTO center_configuration(center_name,config_key,config_value,is_active,common,center_num)
VALUES('体检中心','INSERT_report_pdf','N','Y','   ZJ:终检写入记录，ZJ_SH:审核时终检写入记录,N关闭','000')