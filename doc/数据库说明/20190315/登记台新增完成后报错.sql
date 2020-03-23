--体检者对应批次表    此表是否可以去掉
alter table examinfo_batch add exam_num varchar(20)

ALTER  TABLE  exam_info  ADD   wuxuzongjian int not null  default 1

alter table exam_ext_info add political_status int NULL