<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		
		$("#criterion_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
	});
		
	
		
	function f_saveche_c(){
		if (document.getElementById("criterion_name").value ==""){
			$("#criterion_name").focus();
			return;
		}
		$.ajax({
			url:'saveCheckcriterion.action', 
			type: "post",
			data:{
				criterionID:$("#criterionID").val(),
				criterion_name:$("#criterion_name").val(),
				occuphyexaclassID:$('#occuphyexaclassID').val(),
				hazardfactorsID:$('#hazardfactorsID').val()
				},          
			success: function(data){
				 $.messager.alert('提示信息', data);
				 if(!$('#add').is(':checked')){
			  		 $("#edit_dlg").dialog("close");
			  	 }else{
			  		$("#criterion_name").val('')
			  	 };
				 getData();
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		
	};
	function continuityadd(){
		 if($('#add').is(':checked')){
			$('#add').attr('checked',false)
		}else{
			$('#add').attr('checked',true)
		} 
	} 
</script>
<input type="hidden"  id='occuphyexaclassID'   value="<s:property  value='occuphyexaclassID'/>"  />
<input type="hidden"  id='hazardfactorsID'   value="<s:property  value='hazardfactorsID'/>"  />
<fieldset style="margin:5px; height:80%;">
		<legend><strong>检查依据信息编辑</strong></legend>
			<div class="formdiv"  style="margin-top:100px;" >
				<dl>
	    	       <dt><input id="criterionID" type="hidden" value="<s:property value='criterionID'/>" />
	    	       	依据内容</dt>
	    	       <dd><input id="criterion_name" style="width:240px;" value="<s:property value='criterion_name'/>"  class="textinput"  ></dd>
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if  test="criterionID==null">
				<div style="float:left;padding-left:20px;font-size:16px;cursor:pointer;" onclick="continuityadd();"><input type="checkbox" onclick="continuityadd();" id="add" style="cursor:pointer;" />&nbsp;&nbsp;连续添加</div>
		</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_saveche_c();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>