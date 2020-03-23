--套餐维护 添加应用类型     1普通项目    2职业病
ALTER TABLE set_charging_item ADD apptype char(1)   NOT NULL DEFAULT 1  