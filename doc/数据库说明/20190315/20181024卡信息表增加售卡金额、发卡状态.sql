alter table card_info add sale_amount decimal(8,2) -- �ۿ����
                          ,hair_card_status int not null default(0) --����״̬ 0��ʾδ������1��ʾ���ѷ���
                          ,hair_card_creater int  --������
                          ,hair_card_create_time datetime --����ʱ��