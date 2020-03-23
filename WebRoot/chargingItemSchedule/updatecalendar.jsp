<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<script type="text/javascript">
$(function(){
	
})
function updateyuyue(){
	if($('#dd').val()==""){
		$.messager.alert("提示信息","日期为必填项！","error");
		return;
	}
	if($('#yuyueshuliang').val()==""){
		$.messager.alert("提示信息","数量不能为空！","error");
		return;
	}
	$.ajax({
		url:'updateItemschdeul.action',
		data:{
			id:$('#up_id').val(),
			schedule_time:$('#dd').datebox('getValue'),
			schedule_number:$('#yuyueshuliang').val()
		},
		type:'post',
		success:function(data){
			$('#edit_dlg').dialog('close')
			$.messager.alert("提示信息",data,"info");
			$("#itemscheduleList").datagrid('reload')
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}
</script>
<input type="hidden"  id = "up_id" value="<s:property value='id'/>" />

<div style="margin-top:80px;margin-left:100px">
	<div>
		<div>
					日期<input  id="dd" style="height: 26px;" value="<s:property value='schedule_time'/>"  type= "text" class= "easyui-datebox" required ="required"> </input>   
	    </div>
	    <div>
					数量<input type="text" id = "yuyueshuliang" value="<s:property value='schedule_number'/>"  class="textinput easyui-validatebox" data-options="required:true" style="height: 20px;margin-top: 10px;" id = "up_piliangshuliang" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	    </div>
    </div>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:updateyuyue()" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveReportMail();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
