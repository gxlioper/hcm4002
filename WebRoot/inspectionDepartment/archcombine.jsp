<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/acceptanceCheck.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	$(document).ready(function () {
		
		var strids=$("#arch_com_ids").val();
		getselections(strids);
		
	});
	
	function getselections(strids){
		$("#archcombine").datagrid({
			url:'getselectedarch.action?arch_com_ids='+strids,
			dataType: 'json',
			//queryParams:model,
			rownumbers:false,
			columns:[[
			          {align:'center',field:'ch',checkbox:true},
			          {align:'center',field:'customer_id',hidden:true},
			          {align:'center',field:'arch_num',title:dahname,width:10,sortable:true},
			          {align:'center',field:'exam_num',title:tjhname,width:15,sortable:true},
			          {align:'center',field:'user_name',title:'姓名',width:10,sortable:true},
			          {align:'center',field:'create_time',title:'创建日期',width:15,sortable:true},
			]],	    	
			singleSelect:true,
			collapsible:true,
			fitColumns:true,
			striped:true,
		});
	}
	
	//保存合并信息
	function f_saveHebin(){
		var rows=$("#archcombine").datagrid('getSelected');
		if(rows.length<=0){
			$.messager.alert("提示信息","无效选择");
			return;
		}else{
			$.ajax({
				url:'updateArchCombine.action',
				data:{
					"customer_id":rows.customer_id,
					"arch_num":rows.arch_num,
					"arch_com_ids":$("#arch_com_ids").val()
				},
				success:function(){
					$.messager.alert("提示信息","档案合并成功");
					$('#combine-edit').dialog('close');
					$("#contractlist").datagrid('reload');
				},
				error:function(){
					$.messager.alert("提示信息","档案合并失败！");
				}
			});
		}
		
		
		
	};
</script>
<input type="hidden" name="arch_com_ids" id="arch_com_ids" value="<s:property value="arch_com_ids"/>"/>
<fieldset style="margin:5px;padding-right:20px;padding-left:30px;font-size:14px;">
	<legend><strong>选择基准档案</strong></legend>
	<div id="archcombine"></div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_saveHebin();">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#combine-edit').dialog('close')">关闭</a>
		</div>
	</div>
</fieldset>