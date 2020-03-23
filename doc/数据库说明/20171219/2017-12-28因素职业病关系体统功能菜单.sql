

DELETE  FROM   WEB_XTGNB   where  id  IN('zyb397','zyb601','zyb6012','zyb398','zyb399','zyb600','zyb603')
INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb397','getZyboccudiseaseYinsuPage.action','因素职业病关系管理',1,'zyb397',1,1,2)


INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb601','getAddZyboccudiseaseYinsuPage.action','因素职业病关系新增页面',2,'zyb397',1,1,2)


INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb602','getupdateZyboccudiseaseYinsuPage.action','因素职业病关系修改页面',2,'zyb397',1,1,2)


INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb398','getZyboccudiseaseYinsuTable.action','因素职业病关系列表',2,'zyb397',2,1,2)


INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb399','getDeleteZyboccudiseaseYinsu.action','删除因素职业病关系',2,'zyb397',2,1,2)

INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb600','getSaveZyboccudiseaseYinsu.action','保存因素职业病关系',2,'zyb397',2,1,2)


INSERT INTO WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb603','getYinsuList.action','因素列表',2,'zyb397',2,1,2)