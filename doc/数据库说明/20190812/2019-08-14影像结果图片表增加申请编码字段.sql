alter table view_exam_image add exam_num varchar(50)
alter table view_exam_image add pacs_req_code varchar(20)
alter table view_exam_image add seq_no int not null default(0)
