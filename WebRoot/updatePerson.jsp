
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
 $.extend($.fn.validatebox.defaults.rules, {    
	    minLength: {    
	        validator: function(value, param){    
	            return value.length >= param[0];    
	        },    
	        message: 'Please enter at least {0} characters.'   
	    }    
	});
 	$(document).ready(function(){
  	 $(this).keypress( function(e) {
  		 var key = window.event ? e.keyCode : e.which;
   		if(key.toString() == "13"){
     		 return false;
    	 }
  	 });
   
	}) 
function f_savePerson(){
	if ($("#remark1").val() == ''){
		$.messager.alert("提示","显示姓名不能为空！", "warning");
		return;
	}	
	 $.ajax({
        url:'updatePersondo.action',  
        data:{
        	remark1:$("#remark1").val(),
            tel1:$("#tel1").val(),
            email:$("#email").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){  
          		$.messager.confirm('提示信息', data,function(strtext){
          			 if (strtext) {
          				$("#dlg-edit_heder").dialog("close");
          			 }
          			
          		});
            },
            error:function(){
            	 $("#dlg-edit_heder").dialog("close");
            	//alert("用户操作失败！");
            	$.messager.alert('提示信息', "用户操作失败！",function(){});
            }  
    });
}
$(function(){
	$("input").attr("maxlength","15");
})
</script>
<div class="formdiv">
	<br />
	<dl>
		<dt>登录名称：</dt>
		<dd>
			<s:property value="#session.username.username" />
		</dd>
	</dl>
	<dl>
		<dt>姓名：</dt>
		<dd>
			<input id="remark1" type="text"  class="textinput" style="width: 244px;"
				value="<s:property value="remark1"/>"/> <strong
				class="red">*</strong>
		</dd>
	</dl>

	<dl>
		<dt>联系电话：</dt>
		<dd>
			<input type="text"   class="easyui-validatebox"
				 style="width: 244px;"
				value="<s:property value="tel1"/>" id="tel1" />
		</dd>
	</dl>

	<dl>
		<dt>Email：</dt>
		<dd>
			<input type="text"  class="textinput" style="width: 244px;"
				value="<s:property value="email"/>" id="email" />
		</dd>
	</dl>
	<input type="hidden" name="usertype" id="usertype"
		value="<s:property value="usertype"/>" />
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width: 80px;" onclick="f_savePerson();">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 80px;"	onclick="javascript:$('#dlg-edit_heder').dialog('close')">关闭</a>
	</div>
</div>