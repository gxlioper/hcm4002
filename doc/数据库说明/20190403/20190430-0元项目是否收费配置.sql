
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common]) 
VALUES (N'XXXXXX', N'IS_CHARGINGWAY_ZERO', N'Y', N'Y', N'收费处选中的收费方式金额为0时,该收费方式是否写入数据库')

UPDATE center_configuration  SET  common  = '收费处选中的收费方式金额为0时,该收费方式是否写入数据库,N 不走收费流程默认为已收费 可直接减项，Y 走收费流程，可退费'    
WHERE config_key = 'IS_CHARGINGWAY_ZERO'