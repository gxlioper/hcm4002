ALTER TABLE  examination_item  add item_class_id int 
alter table charging_item_exam_item add item_class_id int  NOT NULL  default(0); --添加排序字段

CREATE TABLE [dbo].[charging_item_class](
	[item_class_id] [int] IDENTITY(1,1) NOT NULL,
	[item_class_name] [varchar](50) NOT NULL,
	[charging_item_id] [int] NOT NULL,
 CONSTRAINT [PK_examination_item_class] PRIMARY KEY CLUSTERED 
(
	[item_class_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
