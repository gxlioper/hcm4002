alter table exam_info add zyb_set_source int not null default(0) --职业病体检套餐来源，0表示按职业危害因素关联套餐，1表示关联自选套餐

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb508','zybimpusershow_set.action','导入临时表操作界面(关联自选套餐)',2,null,'zyb9',1,1,2);