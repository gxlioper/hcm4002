alter table exam_Critical_detail add data_source int not null default(0) --数据来源 0表示系统自动生成、1表示手动插入
                                     ,creater int,create_time datetime  --新增人、新增时间