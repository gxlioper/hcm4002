alter table examinfo_disease add diagnosis_source varchar(50),--诊断来源
  item_source varchar(50),--来源组合项目
  exam_result varchar(100),--个人体检结论
  career_hazards varchar(500),--职业危害
  career_suggest varchar(500)--职业建议
 alter table  exam_summary add
  final_status char(1), --总检状态
  final_time datetime   --总检时间