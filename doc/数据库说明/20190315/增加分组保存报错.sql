ALTER TABLE [dbo].[charging_item] ADD [hiscodeClass] [char](1) NULL;
alter table his_price_list add is_active  varchar(1) not null default('Y') 
alter table his_price_list add expand1  varchar(100)
alter table his_clinic_item add is_active  varchar(1) not null default('Y') 




--导检单回收
alter table exam_info_recycling_guid add exam_num varchar(20)