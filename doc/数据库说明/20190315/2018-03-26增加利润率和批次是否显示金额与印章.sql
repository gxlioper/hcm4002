ALTER TABLE [dbo].[batch] ADD [is_showamount] [int] NOT NULL default(0);--�Ƿ���ʾ���
ALTER TABLE [dbo].[batch] ADD [is_showseal] [int] NOT NULL default(0);--�Ƿ���ʾӡ��

--ALTER TABLE [dbo].[charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--�����ʣ���λ%
ALTER TABLE [dbo].[department_dep] ADD [calculation_rate] [int] NOT NULL default(0);--�����ʣ���λ%
ALTER TABLE [dbo].[examinfo_charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--�����ʣ���λ%

ALTER TABLE [dbo].[exam_flow_config] ADD [s] [int] NOT NULL default(0);--�Ƿ�����ϴ���־��1��ʾ�����ϴ�