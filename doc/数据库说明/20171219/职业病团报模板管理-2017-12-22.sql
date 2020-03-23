
 CREATE TABLE dbo.occtemplate
	(
	Id varchar(50) NOT NULL,
	exam_type int NOT NULL,
	occuphyexaclassid varchar(50) NOT NULL,
	template varchar(200) NOT NULL,
	remark varchar(200) NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.occtemplate ADD CONSTRAINT
	PK_occtemplate PRIMARY KEY CLUSTERED 
	(
	Id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb501','occtemplateManager.action','职业病团报模板管理',1,null,'zyb501',1,1,2);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb502','addOcctemplate.action','职业病团报模板新增',2,null,'zyb501',1,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb503','updateOcctemplate.action','职业病团报模板编辑',2,null,'zyb501',1,1,2);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb504','getOcctemplateList.action','职业病团报模板getList',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb505','saveOcctemplate.action','职业病团报模板保存',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb506','deleteOcctemplate.action','职业病团报模板删除',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb507','validateOcctemplate.action','职业病团报模板类别验证',2,null,'zyb501',2,1,2);