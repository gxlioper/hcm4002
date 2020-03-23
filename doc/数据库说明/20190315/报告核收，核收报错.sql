alter table exam_summary add cancel_type int not null default(0)  --操作类型 1 一键取消，0 一键恢复


--总检结论表
alter table exam_summary add exam_num varchar(20)