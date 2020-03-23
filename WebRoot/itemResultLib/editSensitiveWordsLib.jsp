<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$("#sensitive_content").validatebox({
		required: true
	});
});

function f_saveViewWords(){
	if($("#sensitive_content").val() == ''){
		$("#sensitive_content").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'editSensitiveWordsLib.action',
		data:{"id":$("#id").val(),
			  "sensitive_content":$("#sensitive_content").val(),
			  "sensitive_type":$("#sensitive_type").combobox('getValue'),
			  "sensitive_sex":$("#sensitive_sex").combobox('getValue')
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,'info');
			$('#dlg-edit').dialog('close');
			getGrid();
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,'error');
		}
	});
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" id="id" value="<s:property value="id"/>"/>敏感词类型：</dt>
				<dd><select style="width:350px;height:26px;" id="sensitive_type" class="easyui-combobox" data-options="panelHeight:'auto',onLoadSuccess:function(){$('#sensitive_type').combobox('setValue','<s:property value="sensitive_type"/>');}">
					<option value="1">性别敏感</option>
					<option value="2">特殊词敏感</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>敏感词性别：</dt>
				<dd><select style="width:350px;height:26px;" id="sensitive_sex" class="easyui-combobox" data-options="panelHeight:'auto',onLoadSuccess:function(){$('#sensitive_sex').combobox('setValue','<s:property value="sensitive_sex"/>');}">
					<option value="全部">全部</option>
					<option value="男">男</option>
					<option value="女">女</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>敏感词内容：</dt>
				<dd><input type="text" style="width:345px;" id="sensitive_content" value="<s:property value="sensitive_content"/>" class="textinput"/><strong class="red">*</strong></dd>
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
