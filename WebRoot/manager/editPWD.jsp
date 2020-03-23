<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

	$(function(){
		$('#passwd,#identify1').validatebox({   
   		 	required: true    	
		})
		$("#passwd").validatebox({
			required:true,
			validType:'maxLength[10]'
		});
		$("#identify1").validatebox({
			required:true,
			validType:'equals["#passwd"]'
		});
		
	})
	function f_savepwd(){
		if (document.getElementById("passwd").value == ''){
	  		alert('密码不能为空！');
			document.getElementById("passwd").focus();
			return;
		}else if (document.getElementById("passwd").value !=  document.getElementById("identify1").value){
	  		alert('两次输入的密码不一致！');
			document.getElementById("identify1").focus();
			return;
		}
		
	$.ajax({
	url:'usereditpwd.action?language='+$("#language").val(),  
	data:{
	  id:$("#id").val(),
	  passwd:$("#passwd").val(),
	  },          
	type: "post",//数据发送方式   
	  success: function(text){ 
		  var obj=eval("("+text+")");
		  $('#editpwd').dialog('close');
			getGrid();
		  $.messager.alert('提示信息', obj,function(){});
	    }     
	});              
	}
</script>
<fieldset style="margin:10px;padding-top:20px;height:70%;">
   <div class="formdiv">
		<dl>
	      <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>登录密码：</dt>
		  <dd><input type="password" name="passwd" id="passwd" value="<s:property value="passwd"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>密码确认：</dt>
		  <dd><input type="password" name="identify1" id="identify1" value="<s:property value="passwd"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_savepwd()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#editpwd').dialog('close')">关闭</a>
	</div></div>
</fieldset>