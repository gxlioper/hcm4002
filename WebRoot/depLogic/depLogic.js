$(document).ready(function () {
	getgroupuserGrid();

});
 //-------------------------------显示疾病逻辑列表------------------------------------
/**
 * 显示疾病逻辑列表
 */
 function getgroupuserGrid(){	
	     var  model={
	    		 conclusion_word:$('#c_conclusion_word').val(),
	    		 dep_id:$('#c_dep').combobox('getValue')
	     }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'getDepLogic.action',
		 toolbar:'#toolbar',
		 queryParams:model,
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'id',title:'ID编码',width:7},	
		    {align:'left',field:'dep_name',title:'科室',width:10,},
		 	{align:'left',field:'conclusion_word',title:'结果词',width:20},
		 	{align:'left',field:'sex',title:'适用性别',width:10},
		 	{align:'left',field:'tiaojian',title:'条件描述',width:50},
		 	{align:'center',field:'sss',title:'修改',"formatter":f_xg},
		 	{align:'center',field:'ss',title:'删除',"formatter":f_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updataSampleReportDemoPage(row_id);
	        }
	});
}


//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function updataSampleReportDemoPage(id){
			$("#dlg-custedit").dialog({
			title:'修改科室逻辑自动生成设置',
			href:'updateDepLogicPage.action?id='+id+'&ran='+ Math.random()
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('hcenter');
}
//-------------------------------------新增报疾病逻辑修改疾病逻辑----------------------------

//------------------------------------删除疾病逻辑----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除科室逻辑提示信息
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '确定删除科室逻辑？', function(r) {
		if (r) {
			$.ajax({
				url : 'deleteDepLogic.action?ids='+ id,
				type : 'post',
				success : function(data) {
					$('#groupusershow').datagrid('reload')
					$.messager.alert('提示信息', data);
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 删除科室逻辑
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的科室逻辑!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除科室逻辑？',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteDepLogic.action?ids='+ids,
				type : "post",
				success : function(data) {
					$('#groupusershow').datagrid('reload');
						$.messager.alert("操作提示",data);
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增科室逻辑',
	iconCls : 'icon-add',
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增科室逻辑自动生成设置',
			href : 'addDepLogicPage.action'
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('hcenter');
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
} ];
