
--处理旧数据卡有效期日期数据问题
update zkhyk set NCard_Validity = null where NCard_Validity = 'NULL'

--卡信息入库
insert into card_info(id,card_num,status,deadline,card_type,amount,face_amount,card_level,card_count,limit_card_count,company,creater,create_time,hair_card_status,discount,sale_status,center_num)
(  select (select NEWID()) id,c.NCard_ID card_num,'1' status,c.NCard_Validity deadline, 
2 card_type,
(select case when NCard_Fee is null then 0 else Convert(decimal(10,2),NCard_Fee) end from zkhykxx                                     --不记名卡最终余额
where ID = (select MAX(ID) as ID from dbo.zkhykxx where NCard_ID = c.NCard_ID
group by NCard_ID)) amount,
(select case when NCard_FillUseFee is null then 0 else Convert(decimal(10,2),NCard_FillUseFee) end from zkhykxx                                     --不记名卡最终余额
where ID = (select min(ID) as ID from dbo.zkhykxx where NCard_ID = c.NCard_ID
group by NCard_ID)) face_amount,
case when c.NCard_Style_ID = '000007' then 193 when c.NCard_Style_ID = '000008' then 194
when c.NCard_Style_ID = '000009' then 195 when c.NCard_Style_ID = '000010' then 196 else 194 end as card_level,
(select COUNT(ID) from zkhykxx where Fee_Flag = 1 and NCard_ID = c.NCard_ID) as card_count,
99 as limit_card_count,
case when NCard_Have_XM = 'NULL' then null else NCard_Have_XM end company,
case when c.NCard_Distributor = '运静' then 656 
when c.NCard_Distributor = '张丽' then 677
when c.NCard_Distributor = '褚金艳' then 693
when c.NCard_Distributor = '徐婷婷' then 685
when c.NCard_Distributor = '徐雪梅' then 660
when c.NCard_Distributor = '张倩' then 661
when c.NCard_Distributor = '骆玲' then 659
else 14 end as creater
,c.NCard_Distribute_Date as create_time,
'1' as hair_card_status,
'10' as discount,
'1' as sale_status,
'20190900002001' as center_num
from dbo.zkhyk c )



--卡交易明细
insert into card_deal(id,card_num,trancode,deal_type,old_amount,amount,deal_time,deal_date,remark,creater)
(select (select NEWID()) id,c.NCard_ID card_num,'001' trancode,
case when Fee_Flag = 0 then 5 when Fee_Flag = 1 then 6 when Fee_Flag = 2 then 7 end deal_type, 
(Convert(decimal(10,2),Ncard_fee) - Convert(decimal(10,2),NCard_FillUseFee)) old_amount,Convert(decimal(10,2),NCard_FillUseFee) amount,NCard_FillOrConsumer_Date deal_time,
convert(varchar(100),NCard_FillOrConsumer_Date,23) deal_date,
case when NCard_FillOrConsumer_XM = 'NULL' then null else NCard_FillOrConsumer_XM end remark,
case when c.NCard_Operator = '运静' then 656
when c.NCard_Operator = '张丽' then 677
when c.NCard_Operator = 'admin' then 14
when c.NCard_Operator = '褚金艳' then 693
when c.NCard_Operator = '徐婷婷' then 685
when c.NCard_Operator = '徐雪梅' then 660
when c.NCard_Operator = '张倩' then 661
when c.NCard_Operator = '骆玲' then 659
else 14 end creater
from zkhykxx c left join zkhyk n on c.NCard_ID = n.NCard_ID) 





