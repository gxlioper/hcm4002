--4.0删除配置
delete center_configuration where config_key = 'IS_DISEASE_KNOW_TYPE'

--疾病知识库建议表增加疾病编码字段
alter table disease_suggestion_lib add disease_num varchar(50)