INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXXX', N'ZYB_BARCODE_PRINT_TYPE', N'5', N'Y', N'职业病报告预览打印配置')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXX', N'IS_HG_WRITEJIUZHENKA', N'N', N'Y', N'收费写就诊卡')
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'XXXX', N'IS_CHANGE_SET_PRICE', N'Y', N'Y', N'收费项目价格变动时是否联动修改套餐价格')

alter table data_dictionary add data_class varchar(50) not null default(0) 
ALTER  TABLE  company_info  ADD  com_phone varchar(50);--单位电话
ALTER  TABLE  company_info  ADD  com_fax varchar(50);--单位传真
ALTER  TABLE  company_info  ADD  assigned_unit_code varchar(50);--客户合同单位代码


ALTER  TABLE  impcustomerInfo  ADD  address varchar(200);--地址
ALTER  TABLE  impcustomerInfo  ADD  nation varchar(50);--民族
ALTER  TABLE  impcustomerInfo  ADD  chargingType varchar(50);--费别
ALTER  TABLE  impcustomerInfo  ADD  customer_type_id varchar(50);--人员类别
ALTER  TABLE  impcustomerInfo  ADD  medical_insurance_card varchar(50);--社保卡号


ALTER TABLE dbo.customer_info ADD born_address varchar(100) NULL
	
ALTER TABLE dbo.exam_info ADD zip varchar(6) NULL

ALTER TABLE dbo.exam_info ADD degreeOfedu varchar(20) NULL

CREATE TABLE dbo.exam_ext_typeofocc
	(
	id varchar(50) NOT NULL,
	exam_num varchar(50) NOT NULL,
	arch_num varchar(50) NOT NULL,
	sc_classcode varchar(50) NOT NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.exam_ext_typeofocc ADD CONSTRAINT
	PK_exam_ext_typeofocc PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'东北国际健康管理中心', N'IS_QUEUE_CHECK_baodao', N'N', N'Y', N'6-8临时处理')
alter table exam_info add comon_report_type int --职业病打印普通报告调用模板类型（默认健康体检类型模板）
alter table exam_info add report_type int  not null default(0) 
ALTER TABLE dbo.company_info ADD com_phone varchar(50) NULL
ALTER TABLE dbo.company_info ADD com_zip varchar(50) NULL

update exam_info set data_source='001'
ALTER TABLE company_info ADD keShi_Name varchar(20)  -- 增加科室联系人

alter table impcustomerInfo add billdep varchar(100)--发票部门
alter table exam_info add billdep varchar(100)--发票部门

INSERT [dbo].[data_dictionary] ([data_type], [data_code], [data_name], [remark], [isActive], [creater], [create_time], [updater], [update_time], [seq_code], [data_code_children],data_class) VALUES ('数据来源', N'SJLY', N'系统端', NULL, N'Y', 14, '2018-09-09 01:01:01', NULL, NULL, NULL, N'001','0')
INSERT [dbo].[data_dictionary] ([data_type], [data_code], [data_name], [remark], [isActive], [creater], [create_time], [updater], [update_time], [seq_code], [data_code_children],data_class) VALUES ('数据来源', N'SJLY', N'微信端', NULL, N'Y', 14, '2018-09-09 01:01:01', NULL, NULL, NULL, N'002','0')