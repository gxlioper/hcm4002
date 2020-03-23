<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function () {
		getVisitUserList();
		$("#user").validatebox({
			required:true,
			validType:'maxLength[10]'
		});
	});
	
	function getVisitUserList(){
		$("#user").combobox({
			url :'getVisitUserList.action',
			panelHeight : 'auto',//设置固定高度以显示滚动条
			valueField : 'id',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight:200,
			textField : 'chi_name'
		})
	}
	function save(){
		if ($("#user").combobox('getValue').trim()==''){
			$('#user').combobox().next('span').find('input').focus();
			return;
		}
		var exam_numList= new Array();
		var arch_numList= new Array();
		var checkedItems = $('#flowexampListv').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        exam_numList.push(item.exam_num);
	        arch_numList.push(item.arch_num);
	    });
		$.ajax({
			url:'batchAllotDoctor.action', 
		    type: "post",
			data:{
				exam_num:exam_numList.toString(),
				arch_num:arch_numList.toString(),
				doctor_id:$("#user").combobox('getValue').trim(),
			},          
			success: function(data){
			  	$.messager.alert('提示信息', data);
			 	$("#allot_dlg").dialog("close");
				chaxun();
			 },
			 error:function(){
			    	$("#allot_dlg").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	};
</script>
			<div class="formdiv">
				<dl></dl>
				<dl>
	    	       <dt>分配给</dt>
	    	       <dd><input id="user" required='true'  >&nbsp;&nbsp;医生</dd>
	    	    </dl>
	    	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="save();">分配</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#allot_dlg').dialog('close')">关闭</a>
	</div>
</div>