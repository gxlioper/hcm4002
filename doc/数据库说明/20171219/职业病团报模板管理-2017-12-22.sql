
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

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb501','occtemplateManager.action','ְҵ���ű�ģ�����',1,null,'zyb501',1,1,2);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb502','addOcctemplate.action','ְҵ���ű�ģ������',2,null,'zyb501',1,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb503','updateOcctemplate.action','ְҵ���ű�ģ��༭',2,null,'zyb501',1,1,2);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb504','getOcctemplateList.action','ְҵ���ű�ģ��getList',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb505','saveOcctemplate.action','ְҵ���ű�ģ�屣��',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb506','deleteOcctemplate.action','ְҵ���ű�ģ��ɾ��',2,null,'zyb501',2,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb507','validateOcctemplate.action','ְҵ���ű�ģ�������֤',2,null,'zyb501',2,1,2);