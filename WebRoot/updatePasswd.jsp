
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
 <script type="text/javascript">
 	$(document).ready(function(){
  	 $(this).keypress( function(e) {
  		 var key = window.event ? e.keyCode : e.which;
   		if(key.toString() == "13"){
     		 return false;
    	 }
  	 });
   
	}) 
     
     
function f_savePerson(){
 		if (document.getElementById("username").value == ''){
	  		//alert('姓名不能为空！');
	  		$.messager.alert("提示","旧密码不能为空！", "warning");
			document.getElementById("username").focus();
			return;
		}else if (document.getElementById("passwd").value == ''){
		  		//alert('密码不能为空！');
		  		$.messager.alert("提示","新密码不能为空！", "warning");
				document.getElementById("passwd").focus();
				return;
			}else if (document.getElementById("passwd").value !=  document.getElementById("passwd1").value){
		  		//alert('两次输入的密码不一致！');
		  		$.messager.alert("提示","两次输入的新密码不一致！", "warning");
				document.getElementById("passwd").focus();
				return;
			}
			

	 $.ajax({
        url:'updatePasswddo.action',  
        data:{
          id:$("#id").val(),
          username:$("#username").val(),
          passwd:$("#passwd").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){  
          	$.messager.confirm('提示信息', data,function(strtext){
     			 if (strtext) {
       				$("#dlg-edit_heder").dialog("close");
       	           	 //window.location.href='person.jsp';
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
	$('input').attr("maxlength","20");
})
</script>
<div class="formdiv">
	<br/>
			<dl>
				<dt>登录名称：</dt>
				<dd><s:property value="#session.username.username"/></dd>
				
			</dl>
			
			<dl>
				<dt>原   密   码：</dt>
				<dd><input type="password"  name="username" id="username" class="textinput"  style="width:244px;"/> <strong class="red">*</strong></dd>
			</dl>
			
			<dl>
				<dt>登录密码：</dt>
				<dd><input type="password"   name="passwd" id="passwd" class="textinput"  style="width:244px; maxlength=10 "/> <strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>密码确认：</dt>
				<dd><input type="password"   name="passwd1" id="passwd1"  class="textinput" style="width:244px; maxlength=10 "/> <strong class="red">*</strong></dd>
			</dl>
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="f_savePerson();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit_heder').dialog('close')">关闭</a>
	</div>
	
</div>