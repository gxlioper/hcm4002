##2017-12-04_主程序3.0_放射性人员导入.sql

------------------------------------------------------------------------------------------------------------------------2017-12-04

ALTER TABLE dbo.impcustomerInfo ADD	info_type int NOT NULL CONSTRAINT DF_impcustomerInfo_info_type DEFAULT 0

ALTER TABLE dbo.impcustomerInfo ADD born_address varchar(100) NULL

ALTER TABLE dbo.impcustomerInfo ADD nation varchar(10) NULL

ALTER TABLE dbo.impcustomerInfo ADD nation varchar(10) NULL

ALTER TABLE dbo.impcustomerInfo ADD	zip varchar(6) NULL

ALTER TABLE dbo.impcustomerInfo ADD	degreeOfedu varchar(20) NULL

ALTER TABLE dbo.impcustomerInfo ADD	sc_class varchar(200) NULL

------------------------------------------------------------------------------------------------------------------------2017-12-07

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

ALTER TABLE dbo.company_info ADD
	com_phone varchar(50) NULL,
	com_zip varchar(50) NULL
	
------------------------------------------------------------------------------------------------------------------------2017-12-21

INSERT INTO [peis_kaifa3.0].[dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype])
 VALUES ('zyb512','fsxzybimpusershow.action','导入临时表操作界面（放射性职业病）',2,null,'zyb9',1,1,2);
INSERT INTO [peis_kaifa3.0].[dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype])
 VALUES ('zyb514','fsxzybimpuserfile.action','文件上传界面（放射性职业病）',2,null,'zyb9',1,1,2);
INSERT INTO [peis_kaifa3.0].[dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype])
 VALUES ('zyb513','fsxzybimpusershowlist.action','放射性职业病导入表查询列表',2,null,'zyb9',2,1,2);
INSERT INTO [peis_kaifa3.0].[dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype])
 VALUES ('zyb515','fsxzybsaveCustomerTmplist.action','放射性职业病单条数据修改',2,null,'zyb9',2,1,2);
INSERT INTO [peis_kaifa3.0].[dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype])
 VALUES ('zyb516','fsxzybimpuserToExaminfodo.action','放射性职业病部分进入正式库',2,null,'zyb9',2,1,2);