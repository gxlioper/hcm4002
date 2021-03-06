alter table exam_ext_info add out_reg_flag int --外检报到标识(0:体检中心报到;1:外检车报到)

alter table exam_ext_info add out_reg_deviceCode varchar(10) --体检报到外检车编码

alter table sample_exam_detail add out_deviceCode varchar(10)  
--外检车编码(用于外检车样本核收时记录当前外检车编码，通过核收存储过程修改)


create table dict_out_bus( --外检车字典维护
  deviceCode     varchar(10) primary key, --外检车编号
  deviceName     varchar(100), --外检车名称
  is_used        int not null default(0), --启用(0:不启用;1:启用) 
  note           varchar(200)  --备注
)

--增加是否体检车登记配置
INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common], [center_type], [center_num]) VALUES (N'XXX', N'IS_OUT_REG', N'N', N'Y', N'是否为体检车部署，N表示院内程序，Y表示体检车程序', NULL, N'000')