

/****** Object:  UserDefinedFunction [dbo].[GetExamDoctorNameByExamId]    Script Date: 10/29/2019 16:52:49 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


ALTER function [dbo].[GetExamDoctorNameByExamId](
   @exam_num varchar(45),
   @DepId int
 ) returns varchar(100)
 begin
   declare @examDoctorName varchar(100)
    
    set @examDoctorName = ''
   select @examDoctorName=@examDoctorName+RTRIM(a.exam_doctor_name+'('+a.exam_date+')')+',' from (select distinct ec.exam_doctor_name,CONVERT(varchar(50),ec.exam_date,23) as exam_date from examinfo_charging_item ec,charging_item c
    where ec.charge_item_id = c.id and ec.exam_status='Y' and ec.isActive = 'Y' and ec.exam_num =@exam_num and c.dep_id = @DepId) as a
     if LEN(@examDoctorName) > 0
          begin
          set @examDoctorName=left(@examDoctorName,LEN(@examDoctorName)-1)
          end
   return @examDoctorName
 end  


GO


