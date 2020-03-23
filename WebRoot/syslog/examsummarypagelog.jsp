<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	getExamdepGrid();
});
function getExamdepGrid(){
	$("#dep_list").datagrid({
		 url:'getExamSummaryCountLog.action?exam_num='+$("#exam_num").val(),
		 dataType: 'json',
		 columns:[[
		    {align:'center',field:'operation_types',title:'操作类型',width:10},
		 	{align:'center',field:'exam_doctor',title:'医生',width:15},
		 	{align:'center',field:'exam_date',title:'时间',width:25}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    onDblClickRow : function(rowIndex, rowData){
		    	getptGrid(rowData.id);
		    }
	});
}
//获取
function getptGrid(id){
	 var model={'id':id};
	 $("#resultlist").datagrid({
		 url:'getExamExamDiseaseListLog.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:true,
		 border:false,
		 columns:[[
		           {align:'',field:'disease_name',title:'阳性/疾病发现',width:15},
			 	   {align:'',field:'suggest',title:'阳性/疾病建议',width:25},
		           {align:'',field:"final_remarke","title":"备注","width":10}
		 ]],	    	
		 onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
		 },
		 singleSelect:true,
		 collapsible:true,
		 pagination: false,
		 fitColumns:true,
		 striped:true,
		 fit:true,
		 nowrap:false
 });
}

</script>
<form id="add1Form">
<input id="examinfo_id" type="hidden" value="<s:property value="model.id"/>"/>
	<div class="easyui-layout" style="height:410px;width:1200px;">
		<div data-options="region:'west',title:'操作信息',split:true" style="width:300px;">
			<table id="dep_list"></table>
		</div>
		<div data-options="region:'center',title:'阳性发现信息'">
			<table id="resultlist"></table>
		</div> 
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">关闭</a>
	</div>
</div>
</form>