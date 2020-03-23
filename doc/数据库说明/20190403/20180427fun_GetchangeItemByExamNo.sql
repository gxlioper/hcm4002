USE [peis_180_0220]
GO

/****** Object:  UserDefinedFunction [dbo].[fun_GetchangeItemByExamNo]    Script Date: 04/27/2019 11:25:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

create  function [dbo].[fun_GetchangeItemByExamNo](
  @exam_num varchar(20)
)
returns varchar(2000)
begin
 declare @s varchar(2000)
 set @s=''
 select @s=@s + item_name +'¡¢' from v_examinfo_charging_item 
 where exam_num=@exam_num and change_item = 'C'
 if LEN(@s)>1 
   set @s=SUBSTRING(@s,1,len(@s)-1)
 return @s
end;

GO


