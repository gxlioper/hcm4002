
/****** Object:  Table [dbo].[exam_summary_reject_lib]    Script Date: 04/28/2019 12:30:26 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[exam_summary_reject_lib](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[reject_context] [varchar](500) NOT NULL,
	[is_active] [char](1) NOT NULL,
	[creater] [int] NULL,
	[create_time] [datetime] NULL,
	[updater] [int] NULL,
	[update_time] [datetime] NULL,
 CONSTRAINT [PK_exam_summary_reject_lib] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO



insert into WEB_XTGNB values ('2112','finalRejection.action','�����������','1','','2112','1','1','1')

insert into WEB_XTGNB values ('2113','finalRejectionList.action','������������б��ҳ','2','','2112','2','1','1')

insert into WEB_XTGNB values ('2114','rejectadd.action','���������������','2','','2112','2','1','1')

insert into WEB_XTGNB values ('2115','rejectudater.action','������������޸�','2','','2112','2','1','1')

insert into WEB_XTGNB values ('2116','saveRejection.action','��������������޸�','2','','2112','2','1','1')

insert into WEB_XTGNB values ('2117','deletereject.action','�����������ɾ��','2','','2112','2','1','1')

insert into WEB_XTGNB values ('2118','getRejectionlistshow.action','�����Ҳ�ѯ�������','2','','2112','2','1','1')