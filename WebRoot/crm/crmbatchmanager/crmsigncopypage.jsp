<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function savebatch_name(){
	if($('#batch_name').val().trim()==''){
		$.messager.alert('提示信息', '填写的体检任务名称为空！', 'error');
		return;
	}else{
		$.ajax({
			url : 'copyCrmBatch.action',
			type : 'post',
			data : {
				"id" : $('#batch_id').val(),
				"batch_name" : $('#batch_name').val(),
				"sign_num": $('#sign_numcopy').val()
			},
			success : function(data) {
				$.messager.alert('提示信息',data);
				$('#dlg-copy').dialog('close');
				$("#fanganlist").datagrid('load',{"sign_num":$("#sign_numcopy").val()})
			},
			error : function() {
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		});
	}
}
</script>
<fieldset style="margin:5px">
 <legend><strong>所选体检任务基本信息</strong></legend>
	    <div class="formdiv">
    	<dl>
    		<dt>体检任务名称：<s:property value="batch_name"/></dt>
    		<dt>签单计划名称：<s:property value="sign_name"/></dt>
    	</dl>
    	<dl>
    		<dt>单位名称：<s:property value="comname"/></dt>
    		<dt>创建时间：<s:property value="create_time"/></dt>
    	</dl>
    </div>
</fieldset>
<fieldset style="margin:5px;">
    <legend><strong>请填写新的体检任务名称</strong></legend>
    <input class="textinput" id="batch_id" style="width:150px;height:26" value='<s:property value="id"/>' hidden="true">
    <input class="textinput" id="sign_numcopy" style="width:150px;height:26" value='<s:property value="sign_num"/>' hidden="true">
  	体检任务名称:<input class="textinput" id="batch_name" style="width:150px;height:26">
	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:26px;" onclick="javascript:savebatch_name();">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-copy').dialog('close')">关闭</a>
</fieldset>


