CREATE TABLE exam_suggestion_log    ------ 报告预览意见表exam_suggestion_log    
(
	 [id] [int]  identity(1,1) primary key not null,
     	 [exam_num] [varchar](30)  not NULL,     ---- 体检编号
	 [notices] [varchar](1000)  NULL,   ----意见建议
	 [creater] [int] NULL default 0,                ---- 创建者
	 [create_date] [datetime] not NULL,       -----创建时间
	 [updater] [int] not NULL default 0,                ---- 修改者
	 [update_date] [datetime] NULL,       -----修改时间
	 [resource] [varchar](3) not NULL default '001',   ----来源
	 [apptype] [int] not NULL default 1,   ----职业体检类型
	   
)