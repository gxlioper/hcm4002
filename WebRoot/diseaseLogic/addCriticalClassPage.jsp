<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function (){
})
function savendl(){
	if(!$('#add_critical_class_name').val()){
		$('#add_critical_class_name').textbox().next('span').find('input').focus();
		return;
	}
	var url = "";
	var id = '<s:property value="id" />';
	var critical_class_level = '<s:property value="critical_class_level" />';
	var parent_id = '<s:property value="parent_id" />';
	if(id>0){
		url = "updateCriticalClass.action"
	} else {
		url = 'saveCriticalClass.action';
	}
	$.ajax({
		url:url,
		data:{
			id:id,
		 	critical_class_level:critical_class_level,
			critical_class_name:$('#add_critical_class_name').val(),
			parent_id:parent_id,
			seq_code:$('#seq_code').val()
		},
		type:'post',
		success:function(data){
			$("#examCriticalClass").treegrid('reload');
			$.messager.alert("提示信息",data,"info");
			$('#dlg-edit').dialog('close');
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}
</script>
	<div class="formDiv" style=" width:540px; margin-top: 15px">
		<dl>
			<dt style="margin-left:-25px">
				<font color="#FF0000">*</font> 类别名称:
			</dt>
			<dd>
				<input type="text" class="easyui-textbox" data-options="required:true" style="height: 26px;width:350px" id="add_critical_class_name" value="<s:property  value='critical_class_name'/>"   />
			</dd>
		</dl>
		<dl>
			<dt style="margin-left:-25px">
				顺序码:
			</dt>
			<dd>
				<input type="text" class="easyui-numberbox" style="height: 26px;width:350px" id="seq_code" value="<s:property  value='seq_code'/>"   />
			</dd>
		</dl>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:savendl();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>