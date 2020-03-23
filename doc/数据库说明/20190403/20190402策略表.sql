CREATE TABLE crm_plan_tactics     ----	健康计划策略表crm_plan_tactics
(
	[id] [int]  identity(1,1) primary key not null,
    [tactics_num] [varchar](50) unique not NULL,     ---- 策略编码
	[notices] [varchar](500)  NULL, 	 ----策略描述
	[tactics_type] int NULL,       ---- 策略类型
	[rmark] [varchar](500)  NULL,      -----对应策略说明
	[creater] [int] NULL,                ---- 创建者
	[create_date] [datetime] NULL,       -----创建时间
	[updater] [int] NULL,                ---- 修改者
	[update_date] [datetime] NULL,       -----修改时间
	         
)

---tactics_type 表示：1 慢病、2复查、3危机值、4vip回访，5特殊回访


 CREATE TABLE crm_plan_tactics_detail    ------	健康计划策略表明细crm_plan_tactics_detail
(
	[id] [int]  identity(1,1) primary key not null,
    	[tactics_num] [varchar](50)  not NULL,     ---- 策略编码
	[notices] [varchar](500)  NULL, 	 ----回访内容描述
	[plan_doctor_id] int NULL,       ---- 回访医生
	[distancedate] int  NULL,      -----距离体检几天回访
	[creater] [int] NULL,                ---- 创建者
	[create_date] [datetime] NULL,       -----创建时间
	[updater] [int] NULL,                ---- 修改者
	[update_date] [datetime] NULL,       -----修改时间
	         
)


alter  table crm_visit_plan  add  tactics_num  varchar(50) null    ----编码
alter  table  crm_visit_record add tactics_detail_id  int null          --------策略子id
alter  table   crm_visit_record add  visit_notices varchar(500) null   -----------回访内容
alter table  crm_visit_lost add  cvr_id varchar(50)              --------失访表关联回访子表id

 
