ALTER TABLE [dbo].[charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%
ALTER TABLE [dbo].[department_dep] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%
ALTER TABLE [dbo].[examinfo_charging_item] ADD [calculation_rate] [int] NOT NULL default(0);--利润率，单位%



if( COL_LENGTH( 'examinatioin_center', 'notices' ) is null )--系统提示
begin
	alter table examinatioin_center add notices varchar(500)
end
go