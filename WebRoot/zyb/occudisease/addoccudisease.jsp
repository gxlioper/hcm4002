<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	getoccuphyexaclass();
	$('#add_diseaseclass_name').validatebox({    
	    required: true  
	});  
})
/////////////////////////////////////////////////////获取职业病类别////////////////////////////////////////////////
function getoccuphyexaclass(){
	$('#add_diseaseclassID').combobox({
		url:'getZyboccudiseaseClassList.action',
		valueField:'diseaseclassID',    
        textField:'diseaseclass_name',
        panelHeight:'auto',
        required: true,
        onLoadSuccess:function(){
       
         	var s = "<s:property value='diseaseclassID'/>";
         	if(s!=''&&s>0){
        		$('#add_diseaseclassID').combobox('setValue',s);
        	}else{
        	 	var cc = $('#add_diseaseclassID').combobox('getData');
            	$('#add_diseaseclassID').combobox('setValue',cc[0].diseaseclassID);
        	}  
        }
	})
}
////////////////////////////////////////////////保存职业病///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#add_diseaseclass_name').val()==""){
		$('#add_diseaseclass_name').focus();
		return;
	}
	var model = {
			occudiseaseID:$('#add_occudiseaseID').val(),
			diseaseclassID:$('#add_diseaseclassID').combobox('getValue'),
			occudisease_name:$('#add_diseaseclass_name').val()
	}
	$.ajax({
		url:'saveZyboccudisease.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#add_diseaseclass_name').val('');
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
<input type="hidden"  value="<s:property value='occudiseaseID'/>"  name=""   id="add_occudiseaseID" /> 
 <fieldset  style="margin: 5px 5px 5px 5px;padding-top:20px;padding-bottom: 20px;" >
	<legend  ><strong>职业危病编辑</strong></legend> 
	<div class="formDiv"  >
		<dl>
			<dt  style="width:100px;">职业病类别</dt>
			<dd>
				<input type="text"      id="add_diseaseclassID"    style="width: 200px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt	style="width:100px;"	>职业病名称</dt>
			<dd>
				<input type="text"  	maxlength="100"   class="textinput"     id="add_diseaseclass_name" 	value="<s:property value='occudisease_name'/>"	   style="width: 200px;height:26px"  />
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="occudiseaseID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>