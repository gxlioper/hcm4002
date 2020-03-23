
insert  into  center_configuration  values('体检中心','CRITICAL_TACTICS_NUM','WJZ_0001','Y','策略值编码 用于处理手动添加危急值记录回访策略 ','1')

alter   table exam_Critical_detail  add  critical_type  varchar(10)     ---- 类型 0 重大阳性 1 危机



 insert  into  WEB_XTGNB  values('2000','getCriticalDBGJPage.action','危急值添加首页','1','','2000','1','1','1')	
 insert  into  WEB_XTGNB  values('2005','getCriticalHandlePage.action','危急值处理首页','1','','2005','1','1','1')	