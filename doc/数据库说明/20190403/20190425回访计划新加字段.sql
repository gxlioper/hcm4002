
     
     
     alter table  crm_visit_record  add actual_doctor_id int ---实际回访医生
     alter table  crm_visit_record  add actual_date datetime  ----实际回访时间
      alter table  crm_visit_record  add record_status varchar(10) not null default(0)  --- 状态 0 计划未执行 1 计划已执行



update  crm_visit_record  set actual_date = visit_date ,actual_doctor_id =  visit_doctor_id ,record_status = '1' where    customer_feedback <> '' and customer_feedback is not null  ---初始化之前数据