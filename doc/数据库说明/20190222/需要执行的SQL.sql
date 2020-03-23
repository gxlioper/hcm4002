INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_DJT_EXAMINFO_NO_SUMMARY', N'N', N'Y', N'登记台是否判断上次体检未总检时不让新增体检', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_DEFAULT_TEN', N'Y', N'Y', N'登记台加项是否默认为10折 默认10折：Y，自动计算折扣：N', N'XXXX');

alter table exam_info add ziqu_report_time datetime; --增加自取报告时间


if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_name') 
begin
alter table exam_ext_info add ti_name varchar(20);  --新增体检者姓名
end

if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_sex') 
begin
alter table exam_ext_info add ti_sex varchar(4);--新增体检者性别
end

if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_age') 
begin
alter table exam_ext_info add ti_age int NOT NULL  default(0);--新增体检者年龄
end

if not exists
(select * from syscolumns  where id=object_id('exam_ext_info') and name='ti_id_num') 
begin
alter table exam_ext_info add ti_id_num varchar(20);--新增体检者身份证
end

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_DAOJIANDAN_SHENGQINGDAN', N'N', N'Y', N'登记台是否展示打导检单申请单', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_PAIDUIHAO', N'N', N'Y', N'登记台是否展示打排队号', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_SELECT_SHENQINGDAN', N'N', N'Y', N'登记台是否展示选择打申请单', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_SHENQINGDAN', N'N', N'Y', N'登记台是否展示打申请单', N'XXXXX');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_PRINT_BARCODE_ITEM_PAY', N'Y', N'Y', N'登记台打印条码是否判断项目是否付费，N表示不用判断，Y表示需要判断项目已收费', N'体检中心');

INSERT INTO [dbo].[center_configuration] ([config_key], [config_value], [is_active], [common], [center_name]) VALUES (N'IS_SHOW_HH_PAY', N'N', N'Y', N'登记台团体加项 是否显示混合付费', N'XXXX')



CREATE TABLE [dbo].[thrid_interface_log] (
[id] varchar(32) NOT NULL ,
[req_no] varchar(50) NULL ,
[exam_no] varchar(20) NULL ,
[message_id] varchar(50) NULL ,
[message_name] varchar(30) NOT NULL ,
[message_date] datetime NOT NULL ,
[message_type] varchar(50) NOT NULL ,
[sender] varchar(50) NOT NULL ,
[receiver] varchar(50) NOT NULL ,
[message_request] nvarchar(MAX) NOT NULL ,
[message_response] nvarchar(MAX) NOT NULL ,
[flag] int NOT NULL ,
[sys_request] nvarchar(MAX) NOT NULL ,
[sys_respones] nvarchar(MAX) NOT NULL ,
[xtgnb_id] varchar(50) NOT NULL ,
[message_inout] int NOT NULL 
)




CREATE TABLE [dbo].[thrid_interface_message_log] (
[id] varchar(32) NOT NULL ,
[til_id] varchar(32) NOT NULL ,
[ldate] datetime NULL ,
[lmessage] nvarchar(MAX) NULL ,
[seq_code] int NULL 
)