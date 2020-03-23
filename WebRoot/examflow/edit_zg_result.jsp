<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function () {
		$('#zg_exam_result').combobox({
			data:[{
				id:'Y',name:'合格'
			},{
				id:'R',name:'复查'
			},{
				id:'N',name:'不合格'
			},{
				id:'O',name:'其他'
			}],
	        editable:false, //不可编辑状态
	        cache: false,
	        panelHeight: 'auto',//自动高度适合
	        valueField:'id',   
	        textField:'name',
	        onLoadSuccess: function (data){
	        	$('#zg_exam_result').combobox('setValue',data[0].id);
	        }
		});
		
		$("#count").html($('#flowexampListzg').datagrid('getChecked').length);
	});
	
	function save(){
		var exam_numList= new Array();
		var checkedItems = $('#flowexampListzg').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        exam_numList.push(item.exam_num);
	    });
		$.ajax({
			url:'saveZgExamResult.action', 
		    type: "post",
			data:{
				exam_num : exam_numList.toString(),
				exam_result : $("#zg_exam_result").combobox("getValue").trim(),
				exam_doctor : $("#zg_exam_doctor").val(),
				remark : $("#zg_remark").val(),
			},          
			success: function(data){
			  	$.messager.alert('提示信息', data.split("-")[2]);
			 	$("#dlg-edit-zg-result").dialog("close");
			 	chaxun();
				chaxun_report();
			 },
			 error:function(){
		    	$("#dlg-edit-zg-result").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }
		})
	};
</script>
<div class="formdiv">
	<dl>本次共录入<span id='count'></span>人</dl>
	<dl>
		<dt style="height:26px;width:100px;">体检结果：</dt>
		<dd><input required='true' class="easyui-combobox" id="zg_exam_result"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 200px;" /></dd>
	</dl>
	<dl>
		<dt style="height:26px;width:100px;">检查医生：</dt>
		<dd><input type="text" style="width:200px" id="zg_exam_doctor" class="textinput"/></dd>
	</dl>
	<dl>
		<dt style="height:26px;width:100px;">备注：</dt>
		<dd><input type="text" style="width:200px" id="zg_remark" class="textinput"/></dd>
	</dl>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit-zg-result').dialog('close')">关闭</a>
	</div>
</div>