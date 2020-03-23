<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"	%>
<script type="text/javascript">
$(function(){
	//$('#contraindication_name').textbox("textbox").focus();
})
////////////////////////////////////////////////保存职业禁忌证///////////////////////////////////////////////
function saveOccuhazardfactors(){
	if($('#contraindication_name').textbox('getValue')==""){
		$('#contraindication_name').textbox('textbox').focus();
		return;
	}
	if($('#contraindication_name').textbox('getValue').length>40){
		$('#contraindication_name').textbox('textbox').focus();
		return;
	}
	var model = {
			contraindicationID:$('#contraindicationID').val(),
			contraindication_name:$('#contraindication_name').val(),
			tremexplain:$('#tremexplain').textbox('getValue')
	}
	$.ajax({
		url:'saveOccucontraindicationList.action',
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
<input type="hidden"  value="<s:property value='contraindicationID'/>"  name=""   id="contraindicationID" /> 
<div  style="padding: 5px 5px 5px 5px">
	<div id="tt" class="easyui-tabs"  data-options="" style="width:779px;height:293px;background:f7f4f5;">   
	    <div title="职业禁忌证名称"	data-options="tabWidth:255" style="padding:0px;font-size: 200px;">   
	            <input class="easyui-textbox" data-options="required:'true',missingMessage:'该项为必填项',multiline:'true',validType:'maxLength[40]',invalidMessage:'最多可输入40个字符'"  id="contraindication_name"   value="<s:property value='contraindication_name'/>"	  style="width:100%;height: 100%;"> 
	    </div>   
	    <div title="相关术语解释"	data-options="tabWidth:255" style="padding:0px;font-size: 200px;">   
	            <input class="easyui-textbox"   id="tremexplain"   data-options="multiline:'true'" value="<s:property value='tremexplain'/>"	 	 style="width:100%;height: 100%;"> 
	    </div>   
	</div> 
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box"   >
		<div >
			<s:if  test="contraindicationID==null">
				<div  style="float: left;padding-left: 10px;cursor: pointer;"   onclick="dianji();"  ><input type="checkbox"   onclick="dianji();"    id="lianxu"   style="cursor: pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
		    <a href="javascript:saveOccuhazardfactors();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
		</div>
	</div>
</div>