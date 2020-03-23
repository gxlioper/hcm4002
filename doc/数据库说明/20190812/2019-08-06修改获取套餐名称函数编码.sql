
/****** Object:  UserDefinedFunction [dbo].[GetExamSetNameByExamID]    Script Date: 08/06/2019 15:02:50 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER function [dbo].[GetExamSetNameByExamID](

   @ExamID int
 ) returns varchar(100)
 begin
   declare @setName varchar(100)
    
    set @setName = ''
   select @setName=@setName+RTRIM(s.set_name) from examinfo_set es,exam_set s,exam_info e
    where es.exam_set_id = s.id and es.isActive = 'Y' and e.exam_num = es.exam_num and e.id =@ExamID
    set @setName =case when LEN(@setName) > 0 then STUFF(@setName,1,0,'') else @setName end
   return @setName
 end  

