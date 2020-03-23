<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	if($("#type").val() == 0){
		$("#dl_dbname").show();
	}else{
		$("#dl_dbname").hide();
	}
});

function isValidIP(ip) {
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    return reg.test(ip);
} 
function f_saveViewWords(){
	var reg =/^[0-9]*$/;
	if($("#database_url").val() == ''){
		$.messager.alert('提示信息', '请输入IP地址!','error');
		$("#database_url").focus();
		return;
	}else if(!isValidIP($("#database_url").val())){
		$.messager.alert('提示信息', '请输入正确的IP地址!','error');
		$("#database_url").focus();
		return;
	}else if($("#database_port").val() == ''){
		$.messager.alert('提示信息', '请输入端口号!','error');
		$("#database_port").focus();
		return;
	}else if(!reg.test($("#database_port").val())){
		$.messager.alert('提示信息', '请输入正确端口号!','error');
		$("#database_port").focus();
		return;
	}else if($("#type").val() == 0 && $("#database_uame").val() == ''){
		$.messager.alert('提示信息', '请输入数据库名称!','error');
		$("#database_uame").focus();
		return;
	}else if($("#username").val() == ''){
		$.messager.alert('提示信息', '请输入登录名称!','error');
		$("#username").focus();
		return;
	}else if($("#password").val() == ''){
		$.messager.alert('提示信息', '请输入登录密码!','error');
		$("#password").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveDataBaseConfig.action',
		data:{"type":$("#type").val(),
			  "database_url":$("#database_url").val(),
			  "database_port":$("#database_port").val(),
			  "database_uame":$("#database_uame").val(),
			  "username":$("#username").val(),
			  "password":$("#password").val()
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert('提示信息', obj[1],function(){});
				$('#dlg-edit').dialog('close');
				getGrid();
			}else{
				$.messager.alert('提示信息', obj[1],function(){});
			}
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,function(){});
		}
	});
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" id="type" value="<s:property value="type"/>"/>IP地址：</dt>
				<dd><input type="text" id="database_url" value="<s:property value="database_url"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>端口号：</dt>
				<dd><input type="text" id="database_port" value="<s:property value="database_port"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl id="dl_dbname">
				<dt>数据库名称：</dt>
				<dd><input type="text" id="database_uame" value="<s:property value="database_uame"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>登录名称：</dt>
				<dd><input type="text" id="username" value="<s:property value="username"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>登录密码：</dt>
				<dd><input type="password" id="password" value="<s:property value="password"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveViewWords();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
