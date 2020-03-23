IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[log_exam_group]') AND type in (N'U'))
  drop table dbo.log_exam_group
create table log_exam_group(
  /*****分组日志表*****/
  id int IDENTITY(1,1) primary key,
  group_id int,
  log_type varchar(10), --自动分组
  exam_info_id int,
  exam_num varchar(20),
  tbl_name  varchar(60),
  note      varchar(4000),
  create_time datetime default(getdate())
)
