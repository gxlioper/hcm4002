alter table charging_summary_single add merge_charge int not null default(0) --收费类型 0 表示普通收费  1表示合并收费 合并收费不能退费