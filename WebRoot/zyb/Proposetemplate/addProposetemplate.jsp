<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	getoccuphyexaclass();
	$('#TEMPLATENAME').validatebox({
		  required: true  
	});
})
/////////////////////////////////////////////////////获取结论////////////////////////////////////////////////
function getoccuphyexaclass(){
	$('#OPTIONID').combobox({
		url:'getExaminationresult.action',
		valueField: 'resultID',    
        textField: 'result_name',
        panelHeight:200,
        required: true,
        editable:false,
        onLoadSuccess:function(){
            var resultID = "<s:property value='OPTIONID'/>";
        	if(resultID!=''){
        		$('#OPTIONID').combobox('setValue', resultID);
        	}else{
	        	var oo = $('#OPTIONID').combobox('getData');
	        	$('#OPTIONID').combobox('setValue', oo[0].resultID);
        	}
        }
	})
}
////////////////////////////////////////////////保存职业建议词///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#TEMPLATENAME').val()==''){
		$('#TEMPLATENAME').focus();
		return;
	}
	
	if($('#CONTEXT').val().length>250){
		$.messsager.alert("警告信息","建议内容不能超过250个字符");
		return;
	}
	var ISDEFAULT_s = false;
	
	if($("#ISDEFAULT").is(":checked")){
		ISDEFAULT_s=true;
	}
	
	var ENABLE_s=false;
	if($('#ENABLE').is(":checked")){
		ENABLE_s=true;
	}
	
	var model = {
			TEMPLATEID:$('#TEMPLATEID').val(),
			OPTIONID:$('#OPTIONID').combobox('getValue'),
			TEMPLATENAME:$('#TEMPLATENAME').val(),
			CONTEXT:$('#CONTEXT').val(),
			ISDEFAULT:ISDEFAULT_s,
			ENABLE:ENABLE_s
	}
	$.ajax({
		url:'saveZybProposetemplatePage.action',
		data:model,
		type:'post',
		success:function(data){
		 $.messager.alert('提示信息',data,"info");
			if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#TEMPLATENAME').val('');
				$('#CONTEXT').val('');
				$('#ISDEFAULT').attr("checked",false);
				$('#ENABLE').attr("checked",false);
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
<input type="hidden"  value="<s:property value='TEMPLATEID'/>"  name=""   id="TEMPLATEID" /> 
 <fieldset  style="margin: 10px 10px">
	<legend  ><strong>职业建议词</strong></legend> 
	<div class="formDiv"   style="" >
		<dl>
			<dt		style="width:100px;"	>体检结论</dt>
			<dd>
				<input type="text"   class="textinput"       id="OPTIONID"       style="width: 220px;height:26px"  />
			</dd>
		</dl>
		<dl>
			<dt  style="width:100px;">建议词名称</dt>
			<dd>
				<input type="text"    class="textinput"   maxlength="25"   id="TEMPLATENAME"    value="<s:property value='TEMPLATENAME'/>"  style="width: 220px;height:26px"  />
			</dd>
		</dl>
		<dl  style="height: 26px;">
			<dt  style="width:100px;"  ></dt>
			<dd>
				<s:if test='ISDEFAULT==true'>
					<input type="checkbox"  id="ISDEFAULT"   style="cursor: pointer" checked="checked" />&nbsp;是否默认
				</s:if>
				<s:else>
					<input type="checkbox"  id="ISDEFAULT"    style="cursor: pointer" />&nbsp;是否默认
				</s:else>
			</dd>
			<dd>
				<s:if test='ENABLE==true'>
					<input type="checkbox"  id="ENABLE"   style="cursor: pointer" checked="checked" />&nbsp;是否有效
				</s:if>
				<s:else>
					<input type="checkbox"    style="cursor: pointer" id="ENABLE" />&nbsp;是否有效
				</s:else>
			</dd>
		</dl>
		<dl>
			<dt  style="width: 100px;">建议内容</dt>
			<dd>
				<textarea  style="width:220px;height: 150px;"   id="CONTEXT"  maxlength="250" rows="" cols=""><s:property value='CONTEXT'/></textarea>
			</dd>
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="TEMPLATEID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>