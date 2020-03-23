<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	$('#result_name').validatebox({    
	    required: true  
	});  
})

function save(){
	if($('#result_name').val()==""){
		$('#result_name').focus();
		return;
	}
	var model = {
			resultID:$('#resultID').val(),
			result_name:$('#result_name').val(),
			seq_code:$("#seq_code").val()
	}
	$.ajax({
		url:'saveZybExamresult.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data,"info");
			if(!$('#add').is(':checked')){
				$('#dlg-edit').dialog('close')
			}else{
				$('#result_name').val('');
			}
			$('#list').datagrid('reload');
		},
		error:function(){
			$.messager.alert('警告信息',"操作失败","error");
		}
	})
}
function dianji(){
	if($('#add').is(':checked')){
		$('#add').attr('checked',false)
	}else{
		$('#add').attr('checked',true)
	}
}
</script>
<input type="hidden"  value="<s:property value='resultID'/>"  id="resultID" /> 
 <fieldset  style="margin: 5px 5px 5px 5px;padding-top:20px;padding-bottom: 20px;height:80%;" >
	<legend  ><strong>体检结论编辑</strong></legend> 
	<div class="formDiv"  >
		<dl>
			<dt >结论</dt>
			<dd><textarea style="width:320px;resize:vertical;" cols="45" rows="3"  id="result_name" ><s:property value="result_name"/></textarea></dd>
			<dt >顺序码</dt>
			<dd><input style="width:320px;height:27px;" class="textinput" type="text"  id="seq_code" value="<s:property value="seq_code"/>"></dd>
		</dl>
		
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="resultID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="add"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:save();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
		</div>
	</div>
</div>