/*****************收费员日结收费类型表****************/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cashier_daily_acc_class](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[daily_acc_num] [varchar](20) NOT NULL,            --日结主表流水号
	[daily_acc_class_num] [varchar](20) NOT NULL,      --日结收费类型流水号
	[daily_acc_class_amount] [decimal](20, 2) NOT NULL,--收费类型总金额
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[daily_acc_class] [char](3) NOT NULL,              --收费类型   001 个人收费、002 团体结账、 003 会员卡储值 、004单位储值
	[tran_code] [char](3) NOT NULL,                    --交易类型   101 收费、102 消费
 CONSTRAINT [PK_cashier_daily_acc_class] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
-- daily_acc_num 字段创建索引
CREATE NONCLUSTERED INDEX [IX_cashier_daily_acc_num] ON [dbo].[cashier_daily_acc_class] 
(
	[daily_acc_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

--日结收费方式表 增加收费类型流水号
alter table cashier_daily_acc_payway add daily_acc_class_num varchar(20) 

--增加索引
CREATE NONCLUSTERED INDEX [IX_cashier_daily_acc_payway_class_num] ON [dbo].[cashier_daily_acc_payway] 
(
	[daily_acc_class_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

--日结收费明细表 增加收费类型流水号
alter table cashier_daily_acc_list add daily_acc_class_num varchar(20),amount decimal(20,2)

--修改字段长度
alter table cashier_daily_acc_list alter column team_acc_num varchar(50)

--增加索引
CREATE NONCLUSTERED INDEX [IX_cashier_daily_acc_list_class_num] ON [dbo].[cashier_daily_acc_list] 
(
	[daily_acc_class_num] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]

--团体账号充值流水记录表 增加日结字段
alter table company_account_detail add daily_status int not null default(0) 

