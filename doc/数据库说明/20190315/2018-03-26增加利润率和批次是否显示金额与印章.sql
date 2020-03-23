ALTER TABLE [dbo].[batch] ADD [is_showamount] [int] NOT NULL default(0);--是否显示金额
ALTER TABLE [dbo].[batch] ADD [is_showseal] [int] NOT NULL default(0);--是否显示印章

--ALTER TABLE [dbo].[charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%
ALTER TABLE [dbo].[department_dep] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%
ALTER TABLE [dbo].[examinfo_charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%

ALTER TABLE [dbo].[exam_flow_config] ADD [s] [int] NOT NULL default(0);--是否可以上传标志，1表示可以上传