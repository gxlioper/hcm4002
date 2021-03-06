drop table insure_account
CREATE TABLE [dbo].[insure_account](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[flag]	[varchar](5),   ----操作成功标志	number(1)	y	（0-失败 1-成功）
	[cause]	[varchar](500) ,   ----失败原因说明	varchar2(500)	n	
	[medical_req_num]	[varchar](20),   ----医保流水号	varchar2(20)	y	医保门诊挂号流水号/住院登记流水号
	[medical_charge_req_num]	[varchar](20),   ----医保收费流水号	      varchar2(20)	y	
	[his_req_num]	[varchar](50),   ---- varchar2(50)	y	收费流水号
	[medical_treatment_mode]		[varchar](3),     ---- varchar2(3)	y	医疗就诊方式 参见编码附件
	[medical_category] [varchar](3),		   ----varchar2(3)	y 	医疗类别参见编码附件
	[pat_no]	[varchar](20),    ---- 社会保障卡号	varchar2(20)	y	
	[management_code]	[varchar](20),      ----个人管理码	varchar2(20)	n	地市唯一标识码（id0000）
	[name]	[varchar](50)    NOT NULL,    ----姓名	varchar2(50)	y	 
	[sex]	[varchar](3),    ----性别	varchar2(3)	y	
	[sex_name]	[varchar](10),    ---- 性别名称	varchar2(10)	y	
	[date_birth]	[varchar](20),     ----出生日期	number(8)	y	
	[diseases_coding]	[varchar](20),      ----病种编码	varchar2(16)	n	
	[treatment_personnel]	[varchar](3),   ----   人员待遇类别	varchar2(3)	n	
	[treatment_personnel_name]	[varchar](20) ,   ----  人员待遇类别名称	varchar2(30)	n	
	[treatment_medicall]	[varchar](3),    ---- 医疗待遇状态	varchar2(3)	y	参见编码附件
	[treatment_medical_name]	[varchar](30),    ---- 医疗待遇状态名称	varchar2(30)	y	
	[site_medical_type]	[varchar](3),   ----   异地就医类型	varchar2(3)	n	
	[site_medical_type_name]	[varchar](30),   ----   异地就医类型名称	varchar2(30)	n	
	[administrative_divisions]	[varchar](6),   ----   参保地行政区划	varchar2(6)	n	 
	[overall_payment_standard]	[numeric](12, 4),   ----统筹支付医保费用起付标准	number(12,4)	n	
	[amount_medical]	[numeric](12, 4),   ----医疗费总金额	number(12,4)	y	医疗费总金额=个人现金支付金额+个人账户支付金额+基金支付总额
	[personal_cash]		[numeric](12, 4),   ----个人现金支付金额（实付现金）	number(12,4)	y	发票显示金额
	[individual_account]		[numeric](12, 4),   ----个人账户支付金额	number(12,4)	y	发票显示金额
	[fund]		[numeric](12, 4),   ----基金支付总额	number(12,4)	y	发票显示金额 医保基金支付总额=统筹基金支付+商保基金支付+公务员医疗补助+精准扶贫医疗叠加+医疗救助基金+其他基金支付+企业补充
	[whole_fund]		[numeric](12, 4),   ----其中：统筹基金支付	number(12,4)	n	
	[commercial_fund]		[numeric](12, 4),   ----其中：商保基金支付（大额补充）	number(12,4)	n	大病基金
	[civil_servants]		[numeric](12, 4),   ----其中：公务员医疗补助	number(12,4)	n	明细见“补助明细”列表（以下类同）bkc059=bkc059_1+bkc059_2+bkc059_3
	[poverty_alleviation]		[numeric](12, 4),   ----其中：精准扶贫医疗叠加	number(12,4)	n	明细见“补助明细”列表
	[bailout_fund]		[numeric](12, 4),   ----其中：医疗救助基金	number(12,4)	n	明细见“补助明细”列表
	[other_fund]		[numeric](12, 4),   ----其中：其他基金支付	number(12,4)	n	
	[enterprise_complement]		[numeric](12, 4),   ----其中：企业补充	number(12,4)	n	
	[family_account]		[numeric](12, 4),   ----家庭共济账户支付	number(12,4)	y	通用
	[deductionser]	[varchar](40),   ----(家庭健康)共济账户扣款人	varchar2(40)	y	(例:张三;李四;)
	[deductions_amount]	[varchar](40),   ----(家庭健康)共济账户扣款人金额	varchar2(40)	y	(例:100;200;)
	[general_amount_compensation]		[numeric](12, 4),   ----（其中）一般诊疗费补偿金额	number(12,4)	y	
	[general_personal_amount]		[numeric](12, 4),   ----（其中）一般诊疗费个人支付金额	number(12,4)	y	
	[individual_pocket]	[numeric](12, 4),   ----个人自费（非医保费用）	number(12,4)	y	
	[personal_account_balance]	[numeric](16, 2),   ----	个人账户余额	number(16,2)	y	通用
	[family_balance]		[numeric](16, 2),   ----家庭共济账户余额	number(16,2)	n	通用
	[number_hospita]	[varchar](3),   ----本年度住院次数	number(3)	y	
	[actual_visit_date]	[varchar](10),   ---- 实际就诊日期	number(8)	y	门诊为实际就诊日期，住院为实际出院日期(或中途结算日期)
	[actual_time_visit]	[varchar](10),   ----实际就诊时间	number(6)	n	住院指病人实际出院时间
	[settlement_date]	[varchar](10),   ----费用发生日期（结算日期）	number(8)	y	医保系统结算时间
	[clearing_time]	[varchar](10),   ----费用发生时间（结算时间）	number(6)	y	医保系统结算时间
	[collector]	[varchar](50),   ----  收费人	varchar2(50)	n	
	[surgical_category]	[varchar](3),   ----   计划生育手术类别	varchar2(3)	n	
	[fertility_category]	[varchar](3),   ----   生育类别	varchar2(3)	n	
	[fetus_number]	[varchar](3),    ---- 胎儿数	number(3)	n	
	[family_planning]	[varchar](10),   ---- 计划生育手术或生育日期	number(8)	n	
	[days_pregnancy]	[varchar](3),   ---- 怀孕天数	number(3)	n	
	[disease_settlement]	[varchar](2),   ----是否进入单病种结算  	varchar（2）	y	Y-是  N-否	@Column(name="medical_type")
	[medical_type]	[varchar](20),-----01收费    02退费
	[center_num]	[varchar](20),
	[sterilisation_req_num]	[varchar](20),
	[creater] [int],
	[create_time] [datetime]
	----[subsidies_indicators]	[varchar](8)     NOT NULL,   ---- 补助指标	varchar2(8)	y	参见补助编码表
	----[subsidies_indicators_name]	[varchar](50)      NOT NULL,   ----补助指标名称	varchar2(50)	y	各地补助名称
	----[subsidies_amount]	[numeric](12, 4)    NOT NULL,   ----补助金额	number(12,4)	y	
	----[invoice_category]  	[varchar](3)     NOT NULL,    ----发票项目类别	varchar2(3)	y	参见编码附件
	----[invoice_category_name]  [varchar](50)    NOT NULL,    ---- 	发票项目类别名称	varchar2(50)	y	
	----[invoice_cost]	[numeric](12, 4),   ---- 发票项目费用                                   	number(12,4)	y	
	----[medical_invoice_cost]	[numeric](12, 4)    NOT NULL,   ----医保发票费用             	number(12,4)	y	(完全进入三段的费用)
	----[medical_personal_cost]	[numeric](12, 4)    NOT NULL,   ----医保个人费用             	number(12,4)	y	(乙类中个人支付费用)
	----[special_invoice_cost]	[numeric](12, 4)    NOT NULL,   ----特殊项目发票费用	number(12,4)	y	(门诊三段费用或门诊基金费用)
	----[non_invoice_cost]	[numeric](12, 4)    NOT NULL   ----非医保发票费用            	number(12,4)	y	(不进入三段的费用)
 CONSTRAINT [PK_INSURE_ACCOUNT] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]


CREATE TABLE [dbo].[identity_authentication](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[flag] [varchar](2)	,----校验标志（0-未通过 1-已通过）	number(1)	y	
	[cause]    [varchar](500),	----未通过原因说明	varchar2(500)	n	
	[ginseng_administrative]	   [varchar](6) NOT NULL,----参保地行政区划	varchar2(6)	y	
	[ginseng_administrative_name]	   [varchar](50) NOT NULL,----参保地行政区划名称	varchar2(50)	y	
	[nam_entity]	   [varchar](100),----单位名称	varchar2(100)	n	
	[pat_no]	   [varchar](20) NOT NULL,----社会保障卡号	varchar2(20)	y	
	[document_type]	   [varchar](3) NOT NULL,----证件类型	varchar2(3)	y	参见编码附件
	[document_name]	   [varchar](50) NOT NULL,----证件类型名称	varchar2(50)	y	
	[document_number]	   [varchar](18) NOT NULL,----证件号码（社会保障号）	varchar2(18)	y	
	[personal_code]	   [varchar](20) NOT NULL,----个人管理码	varchar2(20)	y	地市唯一标识码（id0000）
	[name]	   [varchar](50) NOT NULL,----姓名	varchar2(50)	y	
	[sex]	   [varchar](3) NOT NULL,----性别	varchar2(3)	y	
	[sex_name]	   [varchar](10) NOT NULL,----性别名称	varchar2(10)	y	
	[date_birth]   [varchar](8) NOT NULL,----出生日期	number(8)	y	
	[medical_identification]	   [varchar](3),----医疗救助认定身份	varchar2(3)	n	
	[medical_identification_name]	   [varchar](50),----医疗救助认定身份名称	varchar2(50)	n	
	[personal_account_balance]	  [numeric](16, 2) NOT NULL,   ----个人账户余额	number(16,2)	y
	[center_num]	[varchar](20),
	[creater] [int],
	[create_time] [datetime]
	----[plant_type]	   [varchar](3) NOT NULL,----险种类型	varchar2(3)	y	参见编码附件
	----[plant_type_name]	   [varchar](50) NOT NULL,----险种类型名称	varchar2(50)	y	
	----[agency_code]	   [varchar](10) NOT NULL,----所属经办机构编码（分中心）	varchar2(8)	y	
	----[agency_code_name]	   [varchar](50) NOT NULL,----所属经办机构编码名称	varchar2(50)	y	
	----[treatment_personnel]	   [varchar](3) NOT NULL,----人员待遇类别	varchar2(3)	y	
	----[treatment_personnel_name]	   [varchar](30) NOT NULL,----人员待遇类别名称	varchar2(30)	y	
	----[treatment_medicall]	   [varchar](3) NOT NULL,----医疗待遇状态	varchar2(3)	y	参见编码附件
	----[treatment_medicall_name]	   [varchar](30) NOT NULL,----医疗待遇状态名称	varchar2(30)	y	
	----[ginseng_protect_date]	   [varchar](10) NOT NULL,----参保日期（享受待遇日期）	number(8)	y	
	----[status_indicators]	   [varchar](10) NOT NULL,----身份指标	varchar2(10)	y	参见编码身份指标附件说明
	----[status_indicators_name]	   [varchar](50) NOT NULL,----身份指标名称	varchar2(50)	y	
	----[identity]	   [varchar](3) NOT NULL----身份标志	varchar2(3)	y	y 是，n 否
	 CONSTRAINT [PK_IDENTITY_AUTHENTICATION] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

create table dict_Insurance_clinic_item(
  --市医保诊疗项目
  item_code            VARCHAR(20) not null,   --1.诊疗项目编码	
  item_name            VARCHAR(100) not null,  --2.项目名称	
  short_code           VARCHAR(50),            --3.拼音助记码	
  item_place	       VARCHAR(100),	        --5.产地(材料)
  item_unit            VARCHAR(20),            --16.单位	  
  spec                 VARCHAR(200),          --19.规格
  price                decimal(18, 4),            --20.价格
  invoice_item_name    VARCHAR(100),				--发票项目名称
  fee_class            VARCHAR(50),             --14.医保类别
  is_medical		   VARCHAR(3),  -----是否医保项目
  dosage_form          VARCHAR(16),--剂型
  note                 VARCHAR(1000),           --23.备注
  is_effective         VARCHAR(3),  ---是否有效
  top_fee              VARCHAR(20)             --30.最高限价
 );

alter table charging_summary_single add pos_voucher_no varchar(50),city_cycle_code  varchar(50),prov_cycle_code varchar(50),old_req_num varchar(50)

ALTER TABLE card_sale_summary ADD  medical_insurance_card  varchar(50)  
ALTER TABLE charging_invoice_single ADD  tax_invoices_num  varchar(50)  
ALTER TABLE charging_invoice_single ADD  bill_type  char(1)  
ALTER TABLE nullify_invoice ADD  tax_invoices_num  varchar(50)  
ALTER TABLE nullify_invoice ADD  bill_type  char(1)   

ALTER TABLE user_invoice ADD  bill_type  char(1)  not null default('1')  










CREATE TABLE [dbo].[health_care_coding](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[index_items] [varchar](100)	,----指标项
	[indicators_show]    [varchar](500),	----指标说明
	[c_value]	   [varchar](10) NOT NULL,----值
	[specifies]	   [varchar](100) NOT NULL,----定义说明
	
	 CONSTRAINT [PK_HEALTH_CARE_CODING] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
INSERT  INTO   WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('2221'	,'getHealthCareManual.action'	,'手动医保退费'	,'1'	,'2221'	,'1'	,'1',	'1')
INSERT  INTO   WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('2222'	,'posDailyPageCharge.action'	,'pos日结'	,'1'	,'2222'	,'1'	,'1',	'1')

INSERT [dbo].[center_configuration] ([center_name], [config_key], [config_value], [is_active], [common])
 VALUES (N'漳州第三医院', N'IS_MEDICAL_INSURANCE_CHARGING', N'2', N'Y', N'1.普通模式，2.医保或pos使用模式')
 
 
INSERT  INTO   WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('2020'	,'cityReconciliationPageCharge.action'	,'医保对账'	,'1'	,'2020'	,'1'	,'1',	'1')

INSERT  INTO   WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('2501'	,'getitemBindInsurancePage.action'	,'医保项目管理'	,'1'	,'2501'	,'1'	,'1',	'1')

			
ALTER TABLE user_usr ADD  is_prescription  varchar(10) not null   DEFAULT ('N')   
ALTER TABLE user_usr ADD  prescription_num  varchar(50)  
alter table user_usr  add  work_other_num  varchar(50) 

ALTER TABLE charging_Summary_Single ADD  prescription_name  varchar(20)   
ALTER TABLE charging_Summary_Single ADD  prescription_num  varchar(50)  

ALTER TABLE card_sale_summary ADD  prescription_name  varchar(20)   
ALTER TABLE card_sale_summary ADD  prescription_num  varchar(50)  


ALTER TABLE charging_summary_single ADD  health_type  varchar(20)     
ALTER TABLE charging_summary_single ADD  medical_insurance_card  varchar(50)  

alter table insure_account add reconciliation_state varchar(4) not null 

INSERT  INTO   WEB_XTGNB(ID,URL,NAME,TYPE,FATHERACTION,ACTIONTYPE,ADMINTYPE,apptype)
VALUES('2223'	,'getitemBindInsurancePageCharge.action'	,'医保项目管理'	,'1'	,'2223'	,'1'	,'1',	'1')

CREATE TABLE [dbo].[dict_prov_clinic_item_peis](
	[peis_item_code] [varchar](16) NOT NULL,
	[clinic_item_code] [varchar](20) NOT NULL,
	[item_num] [int] NULL,
	[item_price] [decimal](8, 2) NULL,
 CONSTRAINT [PK_DICT_PROV_CLINIC_ITEM_PEIS] PRIMARY KEY CLUSTERED 
(
	[peis_item_code] ASC,
	[clinic_item_code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO


CREATE TABLE [dbo].[charging_item_medical_price](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[charge_item_code] [varchar](20) NOT NULL,    --体检收费项目编码
	[medical_price_id] [int] NOT NULL,   --医保价表收费项目id
	[item_num] [int] NOT NULL default(1),---个数
	[creater] [int] NOT NULL,
	[create_date] [datetime] NOT NULL,
	[updater] [int] NULL,
	[update_date] [datetime] NULL,
 CONSTRAINT [PK_charging_item_medical_price] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO



SET ANSI_PADDING OFF
GO


--医保缴费对应医保项目明细表
CREATE TABLE [dbo].[Insurance_fee_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[req_num] [varchar](20) NOT NULL,    --对应体检申请单号
	[charging_item_code] [varchar](50) NOT NULL,    --收费项目编号
	[charging_item_amount] decimal(8,2) NOT NULL,    --收费项目金额 
	[medical_price_id] [int] NOT NULL,   --火箭蛙价格项目id 保存medical_price_list表id
	[medical_price_num] [int] NOT NULL default(1),---火箭蛙价格项目个数 
	[medical_old_price] decimal(8,2) NOT NULL,    --价表单价
	[medical_new_price] decimal(8,2) NOT NULL,    --项目实际单价
	[medical_item_code] [varchar](20) NOT NULL,   ---医保项目编码
	[medical_item_num] [int] NOT NULL default(1), --医保项目个数
	[medical_type] [varchar](10) NOT NULL,        ---医保类型  02市医保 03 省医保
	[business_type] [varchar](10) NOT NULL,        ---业务类型  1表示个人收费 2表示售卡
	[creater] [int] NOT NULL,
	[create_date] [datetime] NOT NULL,
	
 CONSTRAINT [PK_Insurance_fee_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


--医保价表
CREATE TABLE [dbo].[medical_price_list](
	[id] [int] IDENTITY(1,1) NOT NULL, --自增id
	[item_class] [varchar](10) NOT NULL, --财务类型 
    [item_code] [varchar](20) NOT NULL, --项目编码
	[item_name] [varchar](100) NOT NULL,--价表名称
	[item_spec] [varchar](100) NOT NULL,--规格
	[units] [varchar](20) NOT NULL,--单位
	[price] [decimal](8, 2) NULL,--金额
	[prefer_price] [decimal](8, 2) NULL,--默认金额，无效
	[performed_by] [varchar](20) NULL,--无效
	[input_code] [varchar](40) NULL,--助记码
	[class_on_inp_rcpt] [varchar](20) NULL,--无效
	[class_on_outp_rcpt] [varchar](20) NULL,--无效
	[class_on_reckoning] [varchar](20) NULL,--无效
	[subj_code] [varchar](20) NULL,--无效
	[memo] [varchar](40) NULL,--备注
	[start_date] [datetime] NOT NULL,--有效开始时间
	[stop_date] [datetime] NULL,--有效截至时间
	[creater] [int] NOT NULL,
	[create_date] [datetime] NOT NULL,
	[updater] [int] NULL,
	[update_date] [datetime] NULL,
	[is_active] [varchar](1) NOT NULL,--Y表示有效，N表示无效
	[expand1] [varchar](100) NULL,--扩展说明
 CONSTRAINT [PK_medical_price_list] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[medical_price_list] ADD  CONSTRAINT [DF_medical_price_list_is_active]  DEFAULT ('Y') FOR [is_active]
GO


CREATE TABLE [dbo].[medical_price_medical_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[medical_price_id] [int] NOT NULL,   -- 医保价表id
	[medical_item_code] [varchar](20) NOT NULL,   ---医保项目编码
	[item_num] [int] NOT NULL default(1),                    ---个数
	[medical_type] [varchar](10) NOT NULL,        ---医保类型  02市医保 03 省医保
	[creater] [int] NOT NULL,
	[create_date] [datetime] NOT NULL,
	[updater] [int] NULL,
	[update_date] [datetime] NULL,
 CONSTRAINT [PK_medical_item_medical_price] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO




CREATE TABLE [dbo].[insurance_payer_info](
	[id] [int] IDENTITY(1,1) NOT NULL,  
	[patNo] [varchar](50) NOT NULL, /**个人社保编号  **/
	[id_num] [varchar](50) NOT NULL, /**身份证号  **/
	[name] [varchar](50) NOT NULL,  /**姓名  **/
	[sex] [varchar](50) NOT NULL,  /**性别  **/
	[birthday] [varchar](50) NOT NULL,  /**出生日期  **/
	[nation] [varchar](50) NOT NULL, /**民族  **/
	[acc_balance] [decimal](12, 2) NOT NULL, /**账户余额  **/
	[insurance_status] [varchar](50) NOT NULL, /**参保状态  **/
	[ic] [varchar](50) NOT NULL, /**社会保障卡卡号   **/
	[medical_type] [varchar](50) NOT NULL, /**医保状态  02市医保   03省医保   **/
	[business_type] [varchar](50) NOT NULL, /**个人收费 1      卡收费 2   **/
	[creater] [int] NOT NULL, 
	[create_date] [datetime] NOT NULL

) ON [PRIMARY]
SET ANSI_PADDING OFF

ALTER TABLE [dbo].[insurance_payer_info] ADD  CONSTRAINT [PK_insurance_payer_info] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]




CREATE TABLE [dbo].[invoice_information](
	[id] [int] IDENTITY(1,1) NOT NULL,  
	[infoClientName] [varchar](50) NOT NULL, /**购方名称  **/
	[infoClientTaxCode] [varchar](50)  , /**购方税号   **/
	[infoClientBankAccount] [varchar](50)  ,  /**购方开户行及账号  **/
	[infoClientAddressPhone] [varchar](50)  ,  /**购方地址电话  **/
	[infoKind] [varchar](50) NOT NULL,  /**发票类型(0:增值税发票;2:增值税普通发票)  **/
	[creater] [int]  , 
	[create_time] [datetime]  

) ON [PRIMARY]
SET ANSI_PADDING OFF

ALTER TABLE [dbo].[invoice_information] ADD  CONSTRAINT [PK_invoice_information] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]


