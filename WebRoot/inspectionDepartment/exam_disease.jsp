<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	var disease_id = '<s:property value="disease_id"/>';
	getSugGrid(disease_id);
});

function getSugGrid(id){
	 var model={disease_id:id,age:$("#age").html(),sex:$("#sex").html()};
	$("#sug_list").datagrid({
			 url:'serchDiseaseSuggestionList.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:70}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    onDblClickRow:function(rowIndex, rowData){
			    	rowData.data_source = 1;
			    	appendDisease(rowData);
			    	$('#dlg-edit').dialog('close');
			    }
	});
}
//确定选择一条健康建议
function f_save_addsug(){
	var row = $("#sug_list").datagrid('getSelected');
//	if(row == 'null' || row == null){
//		$.messager.alert("操作提示","请选择一条健康建议!", "error");
//		return;
//	}
	row.data_source = 1;
	appendDisease(row);
	$('#dlg-edit').dialog('close');
}

</script>
<div class="easyui-layout" style="height:315px;">
	<table id="sug_list"></table>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_save_addsug();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>