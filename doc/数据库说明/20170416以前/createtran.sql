alter table group_info add exam_indicator varchar(45) default('T');

alter table exam_info add order_id varchar(50);

alter table charging_item add notices varchar(1000);

alter table exam_info add exam_times  varchar(100);

alter table exam_info add is_report_print char(1)  NOT NULL  default('T');

alter table exam_info add patient_id  varchar(20);--体检人id his使用

alter table exam_info add exam_indicator  char(1);--团体付费状态 T团体结算 G 自费结算
alter table exam_info add clinic_no varchar(20);--门诊号 his使用
alter table exam_info add employeeID  varchar(20);--工号 
alter table exam_info add mc_no  varchar(20); --就诊卡号 his使用
alter table exam_info add visit_date  varchar(20);--就诊日期 his使用
alter table exam_info add visit_no  varchar(20);--就诊号 his使用
alter table impcustomerInfo add employeeID varchar(20);--工号

alter table impcustomerInfo add exam_type varchar(50);

alter table company_info add name_pinyin varchar(500);

alter table sample_demo add isPrint_BarCode int default(0)  --是否打印条码（0：不打印；1：打印）

alter table sample_demo add BarCode_Class int default(0)    --条码类型（0：系统条码；1：预印条码；2：其他条码）

alter table exam_set add price  decimal(8,2);    --套餐表添加金额原价price

alter table exam_inf add company_id int NOT NULL;
alter table exam_inf add batch_id int  NOT NULL;
ALTER TABLE exam_info ADD  CONSTRAINT DF_exam_info_com_id  DEFAULT ((0)) FOR company_id;
ALTER TABLE exam_info ADD  CONSTRAINT DF_exam_info_bantch_id  DEFAULT ((0)) FOR batch_id;

alter table batch add checktype int NOT NULL default(0);
alter table batch add checkuser int NULL;
alter table batch add checkdate varchar(20) NULL;
alter table batch add checknotice varchar(500) NULL;
alter table charging_item add item_note varchar(1500)  --项目意义
alter table charging_item add perform_dept  varchar(20) --执行科室编码（来自HIS科室字典表）

alter table sample_exam_detail add check_id int --采样医生ID

alter table sample_exam_detail add check_doctor varchar(20) --采样医生姓名

alter table sample_exam_detail add check_date datetime --采样时间

alter table batch add overflag char(1) not null default('0') -- 0表示未封帐，1表示已经封帐

alter table his_clinic_item add create_date datetime not null default (getdate())  --创建时间
alter table his_clinic_item add update_date datetime not null default (getdate())  --修改时间

alter table his_clinic_item_v_price_list add create_date datetime  not null default (getdate()) --创建时间
alter table his_clinic_item_v_price_list add update_date datetime  not null default (getdate())  --修改时间

alter table his_price_list add create_date datetime not null default (getdate())  --创建时间
alter table his_price_list add update_date datetime not null default (getdate()) --修改时间

create table his_dict_dept(
  dept_code   varchar(20) primary key,  --科室编码
  dept_name   varchar(40) not null,     --科室名称
  dept_class  varchar(10),               --科室类别 
  input_code  varchar(20)                --助记符
)

CREATE TABLE [dbo].[batch_Per_plan](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[plandate] [varchar](50) NOT NULL,
	[batch_id] [int] NOT NULL,
	[per_num] [int] NOT NULL,
	[creater] [int] NOT NULL,
	[creater_date] [varchar](20) NOT NULL,
	[remark] [varchar](100) NULL,
 CONSTRAINT [PK_batch_Per_plan] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


--导检单流转日志表
CREATE TABLE [dbo].[exam_flow](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_id] [int] NOT NULL,--体检id	
	[fromacc] [int],--发起人id
	[fromacc_date] [datetime],--发起时间
	[toacc] [int],--接收人id
	[toacc_date] [datetime],--接收时间
	[types] [varchar](2) NOT NULL,--类型：0表示发起，标识接收
	[remark] [varchar](2000),--说明1
	[remark1] [varchar](2000),--说明2
 CONSTRAINT [PK_exam_flow] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


/****** Object:  Table [dbo].[WEB_CONFIG]    Script Date: 07/26/2016 22:51:23 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[WEB_CONFIG](
	[CODE] [varchar](20) NOT NULL,
	[NAME] [varchar](50) NOT NULL,
	[TYPES] [varchar](50) NOT NULL,
	[REMARK] [varchar](100) NULL,
	[MEMO] [varchar](50) NOT NULL,
 CONSTRAINT [PK_WEB_CONFIG] PRIMARY KEY CLUSTERED 
(
	[CODE] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'编码说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_CONFIG', @level2type=N'COLUMN',@level2name=N'NAME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型定义' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_CONFIG', @level2type=N'COLUMN',@level2name=N'TYPES'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1 表示字符型，2表示数字型（不带小数点），3表示带小数点' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'WEB_CONFIG', @level2type=N'COLUMN',@level2name=N'MEMO'
GO


CREATE TABLE [dbo].[contract](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[company_id] [int] NOT NULL,
	[company_name] [varchar](500) NOT NULL,
	[batch_id] [int] NOT NULL,
	[batch_name] [varchar](500) NOT NULL,
	[types] [int] NOT NULL,
	[contract_num] [varchar](50) NOT NULL,
	[validity_date] [varchar](20) NOT NULL,
	[creater] [int] NOT NULL,
	[create_time] [datetime] NOT NULL,
	[updater] [int] NOT NULL,
	[update_time] [datetime] NOT NULL,
	[remark] [varchar](100) NULL,
	[linkman] [varchar](100) NOT NULL,
	[tel] [varchar](50) NOT NULL,
	[checknotice] [varchar](100) NULL,
	[checkuser] [int] NULL,
	[checkdate] [varchar](20) NULL,
 CONSTRAINT [PK_contract] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单位id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'company_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单位名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'company_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'方案id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'batch_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'方案名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'batch_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类型1：审核未通过；2 审核通过；0合同未审核 9合同无效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'types'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'合同编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'contract_num'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'合同有效期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'validity_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'creater'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'create_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'updater'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后修改日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'update_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'remark'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'linkman'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系人电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'tel'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'checknotice'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核人id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'checkuser'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'审核日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'contract', @level2type=N'COLUMN',@level2name=N'checkdate'
GO


CREATE TABLE [dbo].[impcustomerInfo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[company_id] [int] NULL,
	[batch_id] [int] NULL,
	[contract_id] [int] NULL,
	[arch_num] [varchar](50) NULL,
	[id_num] [varchar](50) NULL,
	[sex] [varchar](50) NULL,
	[birthday] [varchar](50) NULL,
	[custname] [varchar](50) NULL,
	[age] [int] NULL,
	[is_marriage] [varchar](50) NULL,
	[position] [varchar](200) NULL,
	[_level] [varchar](100) NULL,
	[tel] [varchar](50) NULL,
	[remark] [varchar](200) NULL,
	[customer_type] [varchar](50) NULL,
	[others] [varchar](500) NULL,
	[flags] [int] NOT NULL,
	[notices] [varchar](100) NULL,
	[creater] [int] NOT NULL,
	[create_time] [varchar](20) NOT NULL,
 CONSTRAINT [PK_impcustomerInfo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 职务' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impcustomerInfo', @level2type=N'COLUMN',@level2name=N'position'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'部门' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impcustomerInfo', @level2type=N'COLUMN',@level2name=N'_level'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0 表示导入不成功，1表示导入成功' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impcustomerInfo', @level2type=N'COLUMN',@level2name=N'flags'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'导入失败说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'impcustomerInfo', @level2type=N'COLUMN',@level2name=N'notices'
GO

ALTER TABLE [dbo].[impcustomerInfo] ADD  CONSTRAINT [DF_impcustomerInfo_flags]  DEFAULT ((0)) FOR [flags]
GO


CREATE TABLE [dbo].[driver_info](
	[code] [varchar](20) NOT NULL,
	[com_name] [varchar](100) NOT NULL,
	[com_type] [varchar](4) NOT NULL,
	[com_type_name] [varchar](50) NOT NULL,
	[com_ocx_name] [varchar](500) NOT NULL,
	[is_active] [char](1) NOT NULL,
	[remark] [varchar](500) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'读卡器设备编号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'设备厂家' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'com_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'设备类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'com_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'设备名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'com_type_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ocx 加载字符串' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'com_ocx_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Y 有效，N 无效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'is_active'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'说明' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'driver_info', @level2type=N'COLUMN',@level2name=N'remark'
GO




