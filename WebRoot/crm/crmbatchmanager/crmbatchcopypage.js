
$(document).ready(function () {
	pgetcompanyinfo();
});

//获取单位联系人列表
function pgetcompanyinfo(){
	$("#company_info_list").treegrid({
		 url:'getSignBillPlan.action',
		 dataType: 'json',
		 queryParams:{sign_name:$("#serch_comname").val()},
		 height:290,
		 idField:'id',
		 treeField:'sign_name',
		 columns:[[{field:'sign_name',title:'签单计划与体检任务',width:25},
			 {align:'center',field:'sign_num',title:'签单计划编码与体检任务编码',width:35},
			 {align:'center',field:'com_name',title:'单位名称',width:25},
			 {align:'center',field:'create_time',title:'创建时间',width:25},
			 {align:'center',field:'name',title:'创建人',width:15},
			 {align:'center',field:'flag',hidden:true},
		]],
   		onLoadSuccess:function(){
   			$("#serch_comname").focus();
   		},
   		collapsible:true,//是否可折叠
    	rownumbers:true,//行号
    	fitColumns:true,
    	//showFooter:true,//是否使用页脚
    	singleSelect:true,
	    toolbar:'#sousuo_input'
    });
}

function pcompanyReload(){
	$("#company_info_list").treegrid('load',{"sign_name":$("#serch_comname").val()})
}

function savePlan(){
	var row = $("#company_info_list").treegrid('getSelected');
	if(row == null){
		$.messager.alert("操作提示", "请选择一个体检任务!", "error");
		return;
	}else if(row.flag=='1'){
		$.messager.alert("操作提示", "您选择的是签单计划，请选择体检任务!", "error");
		return;
	}else{
		$("#dlg-edit").dialog('close');
		$("#dlg-copy").dialog({
			title : '复制体检任务',
			width:500,
			 height:250,
			href : 'getSignPlanPage.action?id='+row.id+'&sign_num='+$("#sign_numes").val()
		});
		$("#dlg-copy").dialog('open');
	}
}
