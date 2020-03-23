 insert  into  WEB_XTGNB  values(991,'getPricing.action','划价页面','1','','325',1,1,1)

 insert  into  WEB_XTGNB  values(998,'getTeamPrepayment.action','团体预付费页面','1','','998',1,1,1)

insert  into  WEB_XTGNB  values(
'1730',	'getExamInfoAuditPageLog.action'	,'检查结果、总检结果审计页面',	'1'	,NULL	,1730	,1,	1,	1)

alter table exam_info add ziqu_report_time datetime; --增加自取报告时间


alter  table  examinfo_charging_item   add  tj_charge_amount decimal(10, 4)   		 --- 体检收费金额
				,tj_charge_status varchar(45)         --- 体检收费状态
				,his_charge_amount  decimal(10, 4)                            --- his收费金额
				,his_charge_status  varchar(45)      --- his收费状态
				,pay_mode      varchar(45)  			 --- 0 体检收费，1 his收费 ，2 混合收费

alter  table charging_summary_single add pay_mode varchar(45)  null      ----结算总表付费方式  1 体检收费,2 his收费


------团体预付费 --- 商户表
CREATE TABLE company_account
(
	[id] [int]  identity(1,1) primary key not null,
   	 [center_num] [varchar](50) NULL,     ---- 体检中心编号
	[com_num] [varchar](50)unique not NULL, 	 ----单位编号
	[com_code] [varchar](50) NULL,       ---- 企业代码
	[balance] decimal(10, 4)  NULL,      -----账户余额
	[com_type] [int] NULL,               -----商户状态：0_正常 1_冻结 2_已销户
	[creater] [int] NULL,                ---- 创建者
	[create_date] [datetime] NULL,       -----创建时间
	[updater] [int] NULL,                ---- 修改者
	[update_date] [datetime] NULL,       -----修改时间
	[digitalSign] [varchar](40) NULL     -----数字签名           
)


------商户消费充值流水表

CREATE TABLE companyAccountDetail
(
	[id] [int]  identity(1,1) primary key not null,
	[logicdate] [datetime] NULL,        -----入帐日期
	[com_num] [varchar](50) not NULL, 	 ----单位编号
	[jnnumber] [varchar](50)unique not NULL,       -----交易流水号
	[prejnnumber] [varchar](50) NULL,    -----上笔流水号
	[account_num] [varchar](50) NULL,    -----结帐号
	[creater] [int] NULL,                ---- 操作员
	[trancode] [varchar](50) NULL,       ----交易类型代码   1充值  2消费  
	[jnstatus] [int] NULL,               ----流水状态       1 有效、2作废，3撤销
	[jndatetime] [datetime] NULL,        ---- 流水发生日期时间
	[balance] decimal(10, 4)  NULL,      -----账户余额
	[oldbalance] decimal(10, 4)  NULL,    -----账户原金额
	[usednum] [int] NULL,                ---- 累计使用次数 
	[trantmt] decimal(10, 4)  NULL,      -----交易额
	[chargingway] [varchar](50) NULL,    ----支付方式
	[invaioce_type] [int] null  ,         ----开票状态 0 未开  1 已开
   	 [digitalSign] [varchar](40) NULL ,    -----数字签名    
	[resume] [varchar](100) NULL     ,     -----摘要
	[invoice_id] [int] null                -----发票id
) 