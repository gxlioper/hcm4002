$(document).ready(function () {
	getOccuhazardClassList();
	
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
	   //var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		// $("body").prepend(str);
	     $("#OccuhazardClassshow").datagrid({
		 url:'getZybdiseaseclassresultList.action',
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'left',field:'diseaseclass_name',title:'疾病分类名称',width:28},
	            {align:'left',field:'result_name',title:'结论',width:28},
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
		    onDblClickRow:function(index, row){
	        	var row_id = $('#OccuhazardClassshow').datagrid('getRows')[index].diseaseclassresultID;
	        	updateZYB_OccuhazardClassPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

//-------------------------------------修改职业危害类别页面---------------------
 function g_wlb(val,row){	
	return '<a href=\"javascript:updateZYB_OccuhazardClassPage(\''+row.diseaseclassresultID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害类别页面
 * @param id
 */
 function updateZYB_OccuhazardClassPage(id){
			$("#dlg-custedit").dialog({
			title:'修改疾病分类对应体检结论',
			width :500,
			height:260,
			center:'center',
			href:'updateZybdiseaseclassresult.action?diseaseclassresultID='+id,
		});
		//$("#dlg-custedit").dialog('open');
}
//------------------------------------删除体检类别----------------------------------
function s_wlb(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.diseaseclassresultID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业危害类别
 * @param id
 */
function s_s_wlb(id) {
	if(id==""){
		$.messager.alert('警告信息', '请选择需要删除的记录', 'error');
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZybdiseaseclassresult.action?ids='+id,
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
	    		url : 'deleteZybdiseaseclassresult.action?ids=\''+id+'\'',
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
	text : '新增疾病分类对应体检结论',
	iconCls : 'icon-add',
	width : 200,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增疾病分类对应体检结论',
			width :500,
			height:260,
			center:'center',
			href :'addZybdiseaseclassresultPage.action'
		});
		//$("#dlg-custedit").dialog('open');
 	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#OccuhazardClassshow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.diseaseclassresultID+"'");
		    }); 
		    s_s_wlb(ids.toString());
	    }
}];
