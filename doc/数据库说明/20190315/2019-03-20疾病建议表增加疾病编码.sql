--4.0ɾ������
delete center_configuration where config_key = 'IS_DISEASE_KNOW_TYPE'

--����֪ʶ�⽨������Ӽ��������ֶ�
alter table disease_suggestion_lib add disease_num varchar(50)