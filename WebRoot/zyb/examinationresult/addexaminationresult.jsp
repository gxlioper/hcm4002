<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
//---------------------保存体检类别-----------------------------
$(document).ready(function (){
	$('#diseaseclassID').combobox({
		url:'getDiseaseclassSelect.action',
		valueField:'diseaseclassID',
		textField:'diseaseclass_name',
		panelHeight:'auto',
		editable:false,
		onLoadSuccess:function(){
			var eo = "<s:property value='diseaseclassID'/>";
			if(eo!=""){
				$('#diseaseclassID').combobox('setValue',eo);
			} else{
				var did = $('#diseaseclassID').combobox('getData')[0].diseaseclassID;
				$('#diseaseclassID').combobox('setValue',did);
			}
		}
	})
	
	$('#resultID').combobox({
		url:'getExaminationresultSelect.action',
		valueField:'resultID',
		textField:'result_name',
		panelHeight:'auto',
		editable:false,
		onLoadSuccess:function(){
			var of ="<s:property value='resultID'/>";
			if(of>0){
				$('#resultID').combobox('setValue',of);
			} else{
				var rid = $('#resultID').combobox('getData')[0].resultID;
				$('#resultID').combobox('setValue',rid);
			} 
		}
	})

})
function saveoccuhazardclass(){
 	var  model = {
 			diseaseclassID:$('#diseaseclassID').combobox('getValue'),
			resultID:$('#resultID').combobox('getValue'),
			diseaseclassresultID:$('#diseaseclassresultID').val()
 	}
	$.ajax({
		url:"saveZybdiseaseclassresult.action",
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert("提示信息",data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
			}
			$('#OccuhazardClassshow').datagrid('reload');
			
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
<input type="hidden"  value="<s:property value='diseaseclassresultID'/>"  name=""   id="diseaseclassresultID" />

 <fieldset style=" margin:5px;margin-bottom:3px;margin-top:3px;padding-top:30px;padding-bottom: 30px;padding-left:10px">
	<legend><strong>职业危害类别编辑</strong></legend> 
	<div class=formDiv   >
		<dl>
			<dt  style="width: 100px;">疾病分类</dt>
			<dd>
				<input type="text"    id="diseaseclassID"       style="width: 260px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt  style="width: 100px;">体检结论</dt>
			<dd>
				<input type="text"    id="resultID"      style="width: 260px;height:26px"  />
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="diseaseclassresultID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveoccuhazardclass();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>