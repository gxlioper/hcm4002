$(document).ready(function () {
	getOccuhazardClassList();
	//回车事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	getOccuhazardClassList();
	     }
	}
	$('#hazardclass_name_c').focus();
	
});

/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 

/**
 * 显示职业危害类别
 */
 function getOccuhazardClassList(){
	     $("#OccuhazardClassshow").datagrid({
		 url:'getZYB_OccuhazardClassList.action',
		 queryParams:{
			 hazardclass_code:$('#hazardclass_code_c').val(),
			 hazardclass_name:$('#hazardclass_name_c').val()
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 height:($(window).height()),
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'center',field:'hazardclass_code',title:'职业危害类别编码',width:28},
	            {align:'left',field:'hazardclass_name',title:'职业危害类别名称',width:28},
			 	{align:'center',field:'order',title:'显示顺序',width:28},
			 	{align:'left',field:'remark',title:'备注',width:28},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":g_wlb},
			 	{align:'center',field:'ss',width:10,title:'删除',"formatter":s_wlb}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    fit:true,
		    onDblClickRow:function(index, row){
	        	var row_id = $('#OccuhazardClassshow').datagrid('getRows')[index].hazardclassID;
	        	updateZYB_OccuhazardClassPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

//-------------------------------------修改职业危害类别页面---------------------
 function g_wlb(val,row){	
	return '<a href=\"javascript:updateZYB_OccuhazardClassPage(\''+row.hazardclassID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害类别页面
 * @param id
 */
 function updateZYB_OccuhazardClassPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业危害类别',
			width :650,
			height:370,
			href:'updateZYB_OccuhazardClassPage.action?hazardclassID='+id,
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('center');
}
//------------------------------------删除体检类别----------------------------------
function s_wlb(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.hazardclassID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业危害类别
 * @param id
 */
function s_s_wlb(id) {
	if(id==""){
		$.messager.alert("提示信息","请选择要删除的记录","error");
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZYB_OccuhazardClass.action?ids='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#OccuhazardClassshow').datagrid('reload');
	    		},
	    		error : function() {
	    			$.messager.alert('提示信息', '操作失败！', 'error');
	    		}
	    	});
	    }    
	});
}
function delete_lb(id){
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZYB_OccuhazardClass.action?ids=\''+id+'\'',
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#OccuhazardClassshow').datagrid('reload');
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
	text : '新增职业危害类别',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业危害类别',
			width :650,
			height:370,
			href :'addZYB_OccuhazardClassPage.action'
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('center');
 	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#OccuhazardClassshow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.hazardclassID+"'");
		    }); 
		    s_s_wlb(ids.toString());
	    }
}];
