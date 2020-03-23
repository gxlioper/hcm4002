<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		 
		 $("#dustitem_name").validatebox({
			required:true,
		 });
		
		 $("#option_value").validatebox({
			required:true,
		 });
		 
		 loadcombox();
	});
		
	function loadcombox(){
		 $("#dustitem_name").combobox({
			url :'getdustitemList.action',
			editable : false, //不可编辑状态
			panelHeight : '300',//自动高度适合 
			valueField : 'dustitem_id',
			textField : 'dustitem_name'
		}); 
 	   
	}
		
	function f_save(){
		 if($("#dustitem_name").combobox('getValue').trim()==''){
				$('#dustitem_name').combobox().next('span').find('input').focus();
				return;
		}else if(document.getElementById("option_value").value ==""){
			$("#option_value").focus();
			return;
		}
		$.ajax({
			url:'saveDustitemoption.action', 
			type: "post",
			data:{
				optionID:$("#optionID").val(),
				dustID:$("#dustitem_name").combobox('getValue').trim(),
				option_value:$("#option_value").val(),
				showorder:$("#showorder").val()
				},          
			success: function(data){
				 $.messager.alert('提示信息', data);
				 if($("#add").is(':checked')){
					 $("#dustitem_name").combobox('setValue',''),
					 $("#option_value").val(''),
					 $("#showorder").val('')
					 getData();
				 }else{
					 $("#edit_dlg").dialog("close");
					 getData(); 
				 }
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		
	}
	function continuityadd(){
		if($("#add").is(":checked")){
			$("#add").attr('checked',false);
		}else{
			$("#add").attr('checked',true);
		}
	}
</script>
<%-- <fieldset style="margin:5px; height:90%;">
		<legend><strong>职业危害相关信息编辑</strong></legend> --%>
			<div class="formdiv">
			<input type="hidden"  id="optionID" value="<s:property value='optionID'/>"   />
				<dl>
	    	       <dt>参数名称</dt>
	    	       <dd><input id="dustitem_name"  style="width:240px;" value="<s:property value='dustitem_name'/>" required='true'  ></dd>
	    	    </dl>
				<dl>
	    	       <dt>参数值</dt>
	    	       <dd><input  id="option_value" style="width:240px;" value="<s:property value='option_value'/>"  class="textinput" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>显示顺序</dt>
	    	       <dd><input  id="showorder" type="number" style="width:240px;" value="<s:property value='showorder'/>"  ></dd>
	    	    </dl>
	    	    
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if test="optionID==null">
			<div style="float:left;padding-left:20px;font-size:16px;cursor:pointer;" onclick="continuityadd();">
				<input type="checkbox"  id="add" onclick="continuityadd()"  style="cursor:pointer;"/>连续添加
			</div>
		</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	<!--  </fieldset> -->