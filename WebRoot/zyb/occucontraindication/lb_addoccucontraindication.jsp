<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	$('#jinjizheng').combobox({
		url:'getLbcriterionManagerList.action',
	    valueField:'contraindicationID',    
	    textField:'contraindication_name',
	    onLoadSuccess:function(){
	    	var sa = $('#jinjizheng').combobox('getData');
	    	for(var i = 0 ; i < sa.length ; i++){
	    		if($('#contraindicationID').val()==sa[i].contraindicationID){
	    			$('#jinjizheng').combobox('setValue',$('#contraindicationID').val());
	    			break;
	    		}
	    	}
	    }
	})
	$('#DISORDER').validatebox({    
	    required: true
	});  
})

////////////////////////////////////////////////保存职业禁忌证///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#jinjizheng').combobox('getValue')=="" || $('#jinjizheng').combobox('getText')==""){
		$.messager.alert("提示信息","请选择禁忌证！","error");
		return;
	};
	if($('#DISORDER').val()==""){
		$('#DISORDER').focus();
		return;
	}
	var model = {
			contraindicationID:$('#jinjizheng').combobox('getValue'),
			occuphyexaclassID:$('#occuphyexaclassID').val(),
			hazardfactorsID:$('#hazardfactorsID').val(),
			DISORDER:$('#DISORDER').val(),
			RID:$('#RID').val()
	}
	$.ajax({
		url:'saveLbOccucontraindication.action',
		data:model,
		type:'post',
		success:function(data){
			$.messager.alert('提示信息',data,"info");
			 if(!$('#lianxu').is(':checked')){
				$('#dlg-custedit').dialog('close')
			}else{
				$('#contraindication_name').textbox('setValue',"");
				$('#tremexplain').textbox('setValue',"");
				$("#tt").tabs("select",0);
			} 
			$('#dlg-custedit').dialog('close')
			$('#hazardfactorsShow').datagrid('reload');
		},
		error:function(){
			$.messager.alert('警告信息',"操作失败","error");
		}
	})
}
 
</script>
<input type="hidden"  value="<s:property value='contraindicationID'/>"  name="contraindicationID"   id="contraindicationID" /> 
<input type="hidden"  value="<s:property value='occuphyexaclassID'/>"  name="occuphyexaclassID"   id="occuphyexaclassID" /> 
<input type="hidden"  value="<s:property value='hazardfactorsID'/>"  name="hazardfactorsID"   id="hazardfactorsID" /> 
<input type="hidden"  value="<s:property value='RID'/>"  name="RID"   id="RID" /> 
<div  style="padding: 5px 10px 5px 5px;margin-top: 20px;" class="formDiv" >
<%-- 	<div id="tt" class="easyui-tabs"  data-options="" style="width:779px;height:293px;background:f7f4f5;">   
	    <div title="职业禁忌证名称"	data-options="tabWidth:255" style="padding:0px;font-size: 200px;">   
	            <input class="easyui-textbox" data-options="required:'true',missingMessage:'该项为必填项',multiline:'true',validType:'maxLength[40]',invalidMessage:'最多可输入40个字符'"  id="l_contraindication_name"  value="<s:property value='contraindication_name'/>"   	  style="width:100%;height: 100%;"> 
	    </div>   
	    <div title="相关术语解释"	data-options="tabWidth:255" style="padding:0px;font-size: 200px;">   
	            <input class="easyui-textbox"   id="tremexplain"   data-options="multiline:'true'" value="<s:property value='tremexplain'/>"	 	 style="width:100%;height: 100%;"> 
	    </div>   
	</div>  --%>
	<dl>
		<dt>禁忌证</dt>
		<dd>
			<input type = "text"  id ="jinjizheng"   style="height: 26px;width:500px;"     />
		</dd>
	</dl>
	<dl>
		<dt>顺序码</dt>
		<dd><input type="text"  id = 'DISORDER'   style="height: 26px;width: 100px;" class="textinput"  value = "<s:property value = 'DISORDER'/>"   /></dd>
	</dl>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
	<%-- 		<s:if  test="contraindicationID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if> --%>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>