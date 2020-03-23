<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () {
		getToUserList();
		$("#user").validatebox({
			required:true,
			validType:'maxLength[10]'
		});
	});
	
	function getToUserList(){
		$("#user").combobox({
			url :'getDatadis.action?com_Type=CHECKERS',
			editable : true, //不可编辑状态
			panelHeight : 'auto',//设置固定高度以显示滚动条
			valueField : 'id',
			textField : 'name'
		})
	}
	function save(){
		if ($("#user").combobox('getValue').trim()==''){
			$('#user').combobox().next('span').find('input').focus();
			return;
		}
		var exam_numList= new Array();
		var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        exam_numList.push(item.exam_num);
	    });
		
		$.ajax({
			url:'flowexamsendchk.action', 
		    type: "post",
			data:{
				ids:exam_numList.toString(),
				doctor_id:$("#user").combobox('getValue').trim(),
			},
			success: function(text){
			  	if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示",text.split("-")[1],"");
				} else {
					$.messager.alert("警告信息",text,"error");
				}
			 	$("#sendchk_dlg").dialog("close");
			 	shangchuanchaxun();
			 },
			 error:function(){
			    	$("#sendchk_dlg").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	};
</script>
<fieldset style="margin:5px; height:80%;padding-top:20px;">
		<legend><strong>导检单发送审核</strong></legend>
			<div class="formdiv">
				<dl></dl>
				<dl>
	    	       <dt>发送给</dt>
	    	       <dd><input id="user" required='true'  >&nbsp;&nbsp;医生进行审核</dd>
	    	    </dl>
	    	</div>

<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="save();">发送</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#sendchk_dlg').dialog('close')">关闭</a>
	</div>
</div>
</fieldset> 