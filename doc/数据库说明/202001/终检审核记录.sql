if exists(select * from sys.triggers where name='trig_insert_exam_summary')
drop trigger trig_insert_exam_summary;
go

if exists(select * from sys.triggers where name='trig_update_exam_summary')
drop trigger trig_update_exam_summary;
go


--ɾ������
alter table report_pdf drop constraint PK__report_p__C60997C804859529
--��������
alter table report_pdf add id int not null identity(1,1)  PRIMARY KEY 

--
INSERT INTO center_configuration(center_name,config_key,config_value,is_active,common,center_num)
VALUES('�������','INSERT_report_pdf','N','Y','   ZJ:�ռ�д���¼��ZJ_SH:���ʱ�ռ�д���¼,N�ر�','000')