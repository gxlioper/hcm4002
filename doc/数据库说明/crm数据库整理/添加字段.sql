alter table dep_user add apptype char(1)  NOT NULL  default('1');

alter table web_role add apptype char(1)  NOT NULL  default('1');

alter table examinatioin_center add parent_id char(1);

INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'crm001', N'��������������������С�ۿ���', N'2', N'1', N'���������Ա������������������м������С�ۿ���,��ֵ��,���Ϊ10����СΪ0.����ֵ�������������߿ɴ���λС��', N'���룺7.90', N'��ʾ�ɴ��۵ķ�ΧΪ7.90-10', NULL, NULL)

alter table zyb_areacode add adminarea_code varchar(50);

update center_configuration set config_value='0' where config_key='IS_CONTRACT_CHECK'

