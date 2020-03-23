<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------保存体检类别-----------------------------
$(function(){
	$('#occuphyexaclass_name').validatebox({    
	    required: true 
	});  
	$('#order').validatebox({
		validType:'IsNumber'
	})
	document.getElementById("occuphyexaclass_name").focus();
})
function saveOccuphyexaclass(){
	if($('#occuphyexaclass_name').val()==""||$('#occuphyexaclass_name').val()==null||$('#occuphyexaclass_name').val().length<1){
		$('#occuphyexaclass_name').focus();
		return;
	}
	if(!/^[0-9]{1,20}$/.test($('#order').val()) && $('#order').val()!=""){
		$('#order').focus();
		return;
	}
 	var model={
		'occuphyexaclassID':$('#occuphyexaclassID').val(),
		'occuphyexaclass_name':$("#occuphyexaclass_name").val(),     
		'order':$("#order").val(),     
		'remark':$("#remark").val()
 	} 
 	
	$.ajax({
		url:"addZYB_Occuphyexa.action",
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#occuphyexaclassID').val(''),
				$("#occuphyexaclass_name").val(''),     
				$("#order").val(''),     
				$("#remark").val('')
			}
			$('#groupusershow').datagrid('reload');
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
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
<input type="hidden"  value="<s:property value='occuphyexaclassID'/>"  name=""   id="occuphyexaclassID" />
 <fieldset style=" margin:10px;margin-bottom:0px;margin-top:5px;height:285px">
	<legend><strong>职业体检类别编辑</strong></legend> 
	<div class="formDiv"  style="padding-top: 50px;" >
		<dl>
			<dt>类别名称</dt>
			<dd>
				<input type="text"   maxlength="50" class="textinput" value="<s:property value='occuphyexaclass_name'/>"     id="occuphyexaclass_name"    style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt>显示顺序</dt>
			<dd><input type="text" maxlength="4"	 class="textinput"	 value="<s:property  value='order'/>"   id="order"   style="width: 260px;height: 26px"  /> </dd>
		</dl>
		<dl>
			<dt>备注</dt>
			<dd><input type="text"	 maxlength="200"  class="textinput" value="<s:property  value='remark'/>"   id="remark"   style="width: 260px;height:26px"  /> </dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="occuphyexaclassID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuphyexaclass();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>