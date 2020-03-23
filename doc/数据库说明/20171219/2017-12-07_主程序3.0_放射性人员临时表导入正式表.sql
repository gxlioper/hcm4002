
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