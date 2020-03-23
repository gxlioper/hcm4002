$(document).ready(function () {
	list();
});


 //-------------------------------显示列表------------------------------------
/**
 * 列表
 */
 function list(){	
	    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#examCriticalClass").treegrid({
		 url:'queryCriticalClass.action',
		 queryParams:{
			 critical_class_name:$('#critical_class_name').val()
		 },
		 idField : 'id',
		 treeField : 'critical_class_name',
		 parentField : 'parent_id',
		 rownumbers:false,
	     pageSize:15,//
	     fit:true,
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'left',field:'critical_class_name',title:'类别名称',width:15},
		    {align:'center',field:'seq_code',title:'顺序',width:15},	
		 	{align:'center',field:'sss',title:'修改',width:15,"formatter":f_xg},
		 	{align:'center',field:'ss',title:'删除',width:15,"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    	$(".loading_div").remove();
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar,
	        onClickRow:function(row){
	        	//$("#examCriticalClass").treegrid('collapseAll');
	        	$("#examCriticalClass").treegrid('expand',row.id);
	        }
	});
}

function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 
 * 
 */
 function updataSampleReportDemoPage(id){
			$("#dlg-edit").dialog({
			title:'修改类别',
			width : 550,
			height : 320,
			href:'editexamCriticalClassPage.action?id='+id
		});
		$("#dlg-edit").dialog('open');
}
//------------------------------------删除----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\',\''+row.critical_class_level+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除
 * 
 */
function f_deluser(id,critical_class_level) {
	$.messager.confirm('提示信息', '是否删除', function(r) {
		if (r) {
			$.ajax({
				url : 'removeCriticalClass.action?id='+ id+'&critical_class_level='+critical_class_level,
				type : 'post',
				success : function(data) {
					$("#examCriticalClass").treegrid('reload');
					$.messager.alert('提示信息', data);
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增大类',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-edit").dialog({
			title : '新增大类',
			width : 600,
			height : 320,
			href : 'addexamCriticalClassPage.action?critical_class_level=1'
		});
		$("#dlg-edit").dialog('open');
	}
}, {
	text:'新增子类',
	width:120,
	iconCls:'icon-add',
    handler:function(){
    	var l = $("#examCriticalClass").treegrid('getSelections');
		if(l == ''){
			$.messager.alert("提示信息","请选择大类","error");
			return;
		}
		console.log(l);
    	$("#dlg-edit").dialog({
			title : '新增子类',
			width : 600,
			height : 320,
			href : 'addexamCriticalClassPage.action?critical_class_level=2&parent_id='+l[0].id
		});
		$("#dlg-edit").dialog('open');
    }
} ];
