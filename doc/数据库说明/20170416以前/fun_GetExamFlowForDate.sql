create function [dbo].[fun_GetExamFlowForDate](
  @exam_id varchar(20),
  @typess varchar(20)
  ) returns varchar(50)
as
begin
   declare @doc varchar(50)
    if(@typess='0')
	    set @doc=(select top(1) CONVERT(varchar(50),s.fromacc_date,120) from exam_flow s where s.types='0' and s.exam_id=@exam_id order by s.fromacc_date desc)
	else
	    set @doc=(select top(1) CONVERT(varchar(50),s.toacc_date,120) from exam_flow s where s.types='1' and s.exam_id=@exam_id order by s.toacc_date desc) 
   return @doc
 end