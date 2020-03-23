ALTER function [dbo].[GetTeamPayByExamId](--通过体检id获取体检者团体金额
  @ID varchar(20)
)
returns decimal(16, 4)
begin
   declare @team_pay decimal(16, 4)
   select @team_pay = sum(ec.team_pay)from examinfo_charging_item ec where ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_num = @ID
   return @team_pay
end
go

ALTER function [dbo].[GetPersonalPayByExamId](--通过体检id获取体检者个人金额
  @ID varchar(20)
)
returns decimal(16, 4)
begin
   declare @personal_pay decimal(16, 4)
   select @personal_pay = sum(ec.personal_pay)from examinfo_charging_item ec where ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_num = @ID
   return @personal_pay
end
go

create index idx_examinfo_charging_item_001 on examinfo_charging_item (exam_num,pay_status,isActive )
go