alter table exam_summary add censoring_status varchar(1) not null default(0)-- 0��ʾδ���󣬱�ʾ�Ѹ���
                            ,censoring_doc int --����ҽ��
	                        ,censoring_time datetime --��������