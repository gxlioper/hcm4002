alter table exam_summary add itemupload_status int default(0) not null  -- 0表示未读取、1表示已读取
alter table exam_summary add itemupload_time datetime   -- 读取时间