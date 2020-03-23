alter table dep_user add apptype char(1)  NOT NULL  default('1');

alter table web_role add apptype char(1)  NOT NULL  default('1');

alter table examinatioin_center add parent_id char(1);

INSERT [dbo].[WEB_RESOURCE] ([code], [name], [type], [data_type], [notice], [example], [examplenote], [remark], [remark1]) VALUES (N'crm001', N'体检任务分组加项可输入最小折扣率', N'2', N'1', N'赋予操作人员在体检任务新增分组中加项的最小折扣率,数值型,最大为10，最小为0.输入值可以是整数或者可带两位小数', N'输入：7.90', N'表示可打折的范围为7.90-10', NULL, NULL)

alter table zyb_areacode add adminarea_code varchar(50);

update center_configuration set config_value='0' where config_key='IS_CONTRACT_CHECK'

