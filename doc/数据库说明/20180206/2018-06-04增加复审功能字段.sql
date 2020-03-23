alter table exam_summary add censoring_status varchar(1) not null default(0)-- 0表示未复审，表示已复审
                            ,censoring_doc int --复审医生
	                        ,censoring_time datetime --复审日期