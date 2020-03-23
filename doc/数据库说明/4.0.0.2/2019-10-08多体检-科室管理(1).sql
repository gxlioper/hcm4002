INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2511','centerDepartmentDep.action','多体检中心-科室管理',1,null,'2511',1,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2514','updateCenterDepPage.action','多体检中心-修改科室页面',2,null,'2511',1,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2512','batchCenterDep.action','多体检中心-当前体检中心批量添加科室',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2513','deleteCenterDep.action','多体检中心-删除科室',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2515','updateCenterDep.action','多体检中心-修改科室',2,null,'2511',2,1,0);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) 
VALUES ('2516','centerupdatedepRate.action','多体检中心-修改科室利润率',2,null,'2511',2,1,0);

alter table department_vs_center add dep_address varchar(100) --科室地址
alter table department_vs_center add dep_inter_num varchar(50) --科室第三方编码
alter table department_vs_center add isprint_barcode char(1) --是否打印条码 0 否 1是
alter table department_vs_center add synchro char(1) --是否上传微信
alter table department_vs_center add synchrodate datetime --是否上传微信
alter table department_vs_center add calculation_rate int not null default(0) --科室利润率 最大100，最小0 
alter table department_vs_center add view_result_type int not null default(0) -- 影像科室保存结果类型，0表示按样本pacs_id保存、1表示按科室编码dep_num保存，在查询的时候会按照此类型显示影像图片

alter table department_vs_center add remark1 varchar(50)
alter table department_vs_center add remark2 varchar(50)