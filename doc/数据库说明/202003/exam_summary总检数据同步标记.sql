--��ȡ������ȡʱ�䣬��ȡ���Ĭ��Ϊ0���ܼ������ʱ����ȡ����޸�Ϊ0.
ALTER TABLE exam_summary ADD read_status1 char(1) DEFAULT 0  NOT NULL
ALTER TABLE exam_summary ADD read_status2 char(1) DEFAULT 0  NOT NULL
ALTER TABLE exam_summary ADD read_status3 char(1) DEFAULT 0  NOT NULL
ALTER TABLE exam_summary ADD read_time1 datetime 
ALTER TABLE exam_summary ADD read_time2 datetime 
ALTER TABLE exam_summary ADD read_time3 datetime 