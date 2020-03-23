INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb607','zybUploadData.action','职业病上传数据主界面',1,null,'zyb607',1,1,2);

INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb608','getZybUserList.action','职业病数据上传-人员列表',2,null,'zyb607',1,1,2);
INSERT INTO [dbo].[WEB_XTGNB] ([ID],[URL],[NAME],[TYPE],[REMARK],[FATHERACTION],[ACTIONTYPE],[ADMINTYPE],[apptype]) VALUES ('zyb609','uploadZybData.action','数据上传-批量上传',2,null,'zyb607',1,1,2);

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) VALUES (N'xxxxxx', N'ZYB_UPLOAD_DATA_PAGE_STYLE', N'ww', N'Y', N'职业病上传数据页面样式:ww武威jq健桥')

ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_indicator] varchar(45);--职业病付费类别：个费，团费

ALTER TABLE [dbo].[impcustomerInfo] ADD [exam_class] varchar(45);--体检类型：对应数据字典