alter table exam_set add hazardfactorsid varchar(50);		
alter table exam_set add app_type int not null DEFAULT (1)
alter table exam_set add occuphyexaclassid varchar(50);
alter table exam_set add hazard_code varchar(50);	
alter table  set_charging_item add ischosen char(1) not null DEFAULT ('0')
insert INTO  WEB_XTGNB(ID,URL,NAME,TYPE,REMARK,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb373','getZybExamSetPage.action','职业病套餐管理页面','1','','zyb373',1,1,2)


insert INTO  WEB_XTGNB(ID,URL,NAME,TYPE,REMARK,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb374','getZybExamSetTree.action','职业危害因素树','1','','zyb373',2,1,2)


insert INTO  WEB_XTGNB(ID,URL,NAME,TYPE,REMARK,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('zyb375','getZybExamSetList.action','职业病套餐列表','1','','zyb373',2,1,2)
update WEB_XTGNB set URL='getLbOccucontraindicationPage.action' where ID='zyb376'
