<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#addsales_id').combobox({
	url : 'getCrmUserCenterList.action',
	editable : false, //不可编辑状态
	cache : false,
	height:26,
	width:100,
	panelHeight : 'auto',//自动高度适合
	valueField : 'userid',
	textField : 'name',
	required:true, 
	onLoadSuccess : function(data) {
	}
});
	$('#addsales_id').combobox('setValue', '<s:property value='creater'/>');
});

function f_Examsavecreater(){
	if($('#addsales_id').combobox('getValue')!=''){
		$.ajax({
		    url:'editCrmSignBillPlanEditCreater.action', 
		    type: "post",
			data:{
				id:$("#idcreater").val(),
				creater:$('#addsales_id').combobox('getValue'),
			  },          
			success: function(data){
				 $(".loading_div").remove();
			  	 $.messager.alert('提示信息', data);
			  	 $("#dlg-show").dialog("close");
			  	chaxunsingn();
				
			    },
			 error:function(){
				 	$(".loading_div").remove();
			    	$("#dlg-show").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }  
		});
	}else{
		$('#addsales_id').textbox('textbox').focus();
		return;
	}
}

</script>	
<input   id="idcreater"   value="<s:property  value="id"/>" hidden="true"/>
	<fieldset style="margin:5px; height:90%;">
		<legend><strong>修改负责人信息</strong></legend>
			<div class="formdiv">
			 <dl>
	    	       <dt style="height: 26px; width:140px;">名称： <s:property  value="sign_name"/></dt>
					<dt style="height: 26px; width:140px;">单位：<s:property  value="com_name"/></dt>
	    	    </dl>
	    	   <dl>
	    	       <dt style="height: 26px; width:280px;">该签单计划现负责人为：<s:property value='creater'/>,修改负责人请选择</dt>
	    	       <dd><input  class="easyui-combobox"  id="addsales_id"  
					 class="easyui-validatebox"  value="<s:property  value="sales_id"/>"
					style="height: 26px; width: 140px;"/></dd>
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_Examsavecreater();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

