<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	$('#check_item_ask').focus();
})
////////////////////////////////////////////////保存复查项目及要求///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#check_disease_name').val()==""){
		$.messager.alert("提示信息","内容不能为空","error");
		return;
	}
	if($('#check_disease_name').val().length>100){
		$.messager.alert("提示信息","内容最多可输入100个字符","error");
		return;
	}
	var model = {
			check_disease_id:$('#check_disease_id').val(),
			check_disease_name:$('#check_disease_name').val()
	}
	$.ajax({
		url:'saveRecheckExclDisease.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#check_disease_name').val('');
			}
			$('#hazardfactorsShow').datagrid('reload');
		},
		error:function(){
			$.messager.alert('警告信息',"操作失败","error");
		}
	})
}
function dianji(){
	if($('#lianxu').is(':checked')){
		$('#lianxu').attr('checked',false)
	}else{
		$('#lianxu').attr('checked',true)
	}
}
</script>
<input type="hidden"  value="<s:property value='check_disease_id'/>"  name=""   id="check_disease_id" /> 
	<textarea rows=""  id="check_disease_name"  maxlength="100"  style="width:434px;height:142px;margin: 5px 5px" cols=""><s:property value='check_disease_name'/></textarea>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="check_item_id==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>