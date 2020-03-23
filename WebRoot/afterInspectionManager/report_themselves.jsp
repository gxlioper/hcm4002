<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("#receive_name1").validatebox({
		required: true,
	});
	$('#exam_num1').css('ime-mode','disabled');
	$('#exam_num1').focus();
});
function f_saveReportThemes(){
	if($("#receive_name1").val() == ''){
		$("#receive_name1").focus();
		return;
	}
//	else if($("#receive_phone1").val() == ''){
//		$("#receive_phone1").focus();
//		return;
//	}
	else if($("#receive_date1").datebox('getValue') == ''){
		$("#receive_date1").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveManagerReportReceive.action',
		data:{
			exam_num:$("#exam_num1").val(),
			receive_type:$("#receive_type1").val(),
			receive_name:$("#receive_name1").val(),
			receive_phone:$("#receive_phone1").val(),
			receive_date:$("#receive_date1").datebox('getValue'),
			receive_remark:$("#receive_remark1").val()
		},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', data,'info');
	    	$("#dlg-edit-thems").dialog("close");
	    	if($("#ser_exam_num").val() != ''){
	    		getGrid($("#ser_exam_num").val());
	    	}else{
	    		getGrid();
	    	}
		},
		error:function(data){
			$(".loading_div").remove();
			$.messager.alert('提示信息', '操作失败','error');
	    	$("#dlg-edit-thems").dialog("close");
		}
	});
	
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl style="display:none;">
				<dt>体检人姓名：</dt>
				<dd><input type="text" id="user_name1" name="user_name1" value="<s:property value="user_name"/>"  disabled="disabled" class="textinput"/><strong class="red">*</strong></dd>
				<dt><s:text name="tjhname"/>：</dt>
				<dd><input type="text" id="exam_num1" name="exam_num1" value="<s:property value="exam_num"/>"  disabled="disabled" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt><input type="hidden" name="receive_type1" id="receive_type1" value="<s:property value="receive_type"/>"/>领取人：</dt>
				<dd><input type="text" id="receive_name1" name="receive_name1" value="<s:property value="receive_name"/>" class="textinput"/><strong class="red">*</strong></dd>
				<dt>电话：</dt>
				<dd><input type="text" id="receive_phone1" name="receive_phone1" value="<s:property value="receive_phone"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>领取日期：</dt>
				<dd><input type="text" id="receive_date1" name="receive_date1" value="<s:property value="join_date"/>" class="easyui-datebox" required="required"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><input type="text"  style="width:450px;" id="receive_remark1" name="receive_remark1" value="<s:property value="receive_remark"/>" class="textinput"/></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveReportThemes();">确认领取</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit-thems').dialog('close')">关闭</a>
	</div>
</div>
</form>
