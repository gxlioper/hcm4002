USE [hjwpeis4.0.0.2]
GO
/****** Object:  UserDefinedFunction [dbo].[fun_CharToStar]    Script Date: 03/01/2020 21:43:16 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER function [dbo].[fun_CharToStar](
  /*************将明文字符转成加密符号**************/
  @exam_info_num  varchar(50),
  @param_name  varchar(60), --参数名(如：姓名、电话、身份证号)
  @param_value varchar(200), --传入参数值
  @flag varchar(2) --是否有资源权限(Y:有资源；N:没有资源权限)
) returns varchar(20)
as
begin
  declare 
    @is_encrypt int,   --是否加密(1:加密；0:不加密)
    @strResult varchar(20),
    @dest_char varchar(10), --替换成的字符
    @ids varchar( 50 )
    select @ids = config_value from center_configuration where config_key = 'CUSTOMER_TYPE_SPECIAL'
  --判断体检者是否需要加密的人员类型  
  -- if (exists (select e.id from exam_info e where e.exam_num=@exam_info_num and e.customer_type_id in (5,6)))
  if (exists (select e.id from exam_info e where e.exam_num=@exam_info_num and e.customer_type_id in (select value from dbo.StrSplit( @ids, ',' ))))
    set @is_encrypt=1
  else
    set @is_encrypt=0
  
  if (@flag='N') or (@is_encrypt=0) --不需要加密
    set @strResult=@param_value 
  else begin
     if @flag='Y'
     begin 
		  if @param_name='phone' --电话
		  begin
			set @dest_char='****'
			set @strResult=STUFF(@param_value, 4, 4, @dest_char)
		  end
		  else if @param_name='name' --姓名
		  begin
			set @dest_char='**'
			set @strResult=STUFF(@param_value, 2,3, @dest_char)
		  end
		  else if @param_name='id_num' --身份证号
		  begin
			set @dest_char='****'
			set @strResult=STUFF(@param_value, 15, 4, @dest_char)
		  end
	 end --if @flag='N'
  end
  return @strResult
end