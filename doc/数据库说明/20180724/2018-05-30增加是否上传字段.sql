alter table exam_info add is_upload int not null default(0) ---- 是否上传 0表示不上传， 1表示上传
alter table exam_info add is_report_upload int not null default(0) --是否上传报告  0 不上传 1 上传
alter table exam_info add zyb_set_source int not null default(0) --//职业病体检套餐来源，0表示按职业危害因素关联套餐，1表示关联自选套餐
alter table exam_set add isSynchro int not null default(0) --是否提交同步 0 否 1是