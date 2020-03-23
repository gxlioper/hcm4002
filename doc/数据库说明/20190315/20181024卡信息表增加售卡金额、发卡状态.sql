alter table card_info add sale_amount decimal(8,2) -- 售卡金额
                          ,hair_card_status int not null default(0) --发卡状态 0表示未发卡、1表示是已发卡
                          ,hair_card_creater int  --发卡人
                          ,hair_card_create_time datetime --发卡时间