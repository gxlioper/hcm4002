<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">

$(function(){
	$('#add_exam_set_name').validatebox({    
	    required: true
	}); 
	if($('#add_id').val()>0){
		var zhi = "<s:property value='set_class'/>";
		$('#add_set_class').attr('value',zhi);

	}
})
function saveExamType(){
	if($('#add_exam_set_name').val()==""){
		$('#add_exam_set_name').focus();
		return;
	}
	if($('#add_set_class').combobox('getValue')<=0){
		$('#add_set_class').textbox('textbox').focus();
		return;
	}
	$.ajax({
		url:'saveExamSetType.action',
		data:{
			id:$('#add_id').val(),
			set_type_name:$('#add_exam_set_name').val(),
			set_class:$('#add_set_class').combobox('getValue')
		},
		type:'post',
		success:function(data){
			 $("#typeShow").datagrid('reload');
			$('#dlg-exam_set_type').dialog('close')
			$.messager.alert('提示信息',data,"info");
		},
		erorr:function(){
			$.messager.alert('警告信息',"操作失败","error");
		}
	})
}
</script>
	<input type = "hidden"  id = "add_id"  value = "<s:property value='id'/>"/>
	<div class="formDiv"  style="margin-top: 50px;margin-left:50px;">
		<dl>
			<dt style="width:100px">类别名称</dt>
			<dd>
				<input class="textinput"  id="add_exam_set_name"   name="add_exam_set_name"   style="width:150px;height:26px" value="<s:property value='set_type_name'/>" />
			</dd>
		</dl>
		<dl>
			<dt style="width:100px">类型</dt>
			<dd>
				<input type = "hidden" id = "get_set_class" value="<s:property value='set_class'/>" />
				<select id="add_set_class" class="easyui-combobox" name="add_set_class"  data-options="panelHeight:'auto',editable:false,required: true"  style="width:150px;height:28px">   
					    <option value='1'>常规体检</option>   
					    <option value='2'>微信</option>   
					    <option value='1001'>放射体检</option>   
					    <option value='1002'>职业病体检</option>   
				</select>  
			</dd>
		</dl>
	</div>
<!-- </fieldset> -->

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<div>
		    <a href="javascript:saveExamType();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-exam_set_type').dialog('close')">关闭</a>
		</div>
	</div>
</div>