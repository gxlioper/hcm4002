$(function () {
	getOccuhazardfactorsList();
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			shu($('#cx').val());
		}
	}
	
});

/**
 * 显示复查项目排除目标疾病
 */
 function getOccuhazardfactorsList(){
	/*   var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("table").prepend(str);*/
	     $("#hazardfactorsShow").datagrid({
		 url:'getRecheckExclDiseaseList.action',
		 toolbar:toolbar,
		 rownumbers:true,//行号
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
			 	{align:'left',field:'check_disease_name',title:'复查项目排除目标疾病',width:30},
			 	{align:'center',field:'sss',title:'修改',width:15,"formatter":f_xg},
			 	{align:'center',field:'ss',width:15,title:'删除',"formatter":f_sc}
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
        	var row_id = $('#hazardfactorsShow').datagrid('getRows')[index].check_disease_id;
	        	updateZYB_OccuphyexaPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.check_disease_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害因素
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改复查项目排除目标疾病',
			width :450,
			height:240,
			center:'center',
			href:'updateRecheckExclDiseasePage.action?check_disease_id='+id,
		});
}
//------------------------------------删除职业病----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.check_disease_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除复查项目及要求
 * @param id
 */
function f_deluser(id) {
	if(id==null || id==""){
		$.messager.alert('警告信息',"请选择要删除的记录",'error');
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteRecheckExclDiseaseList.action?ids='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#hazardfactorsShow').datagrid('reload');
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
	    		url : 'deleteRecheckExclDiseaseList.action?ids=\''+id+'\'',
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#hazardfactorsShow').datagrid('reload');
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
	text : '新增',
	iconCls : 'icon-add',
	width : 60,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增复查项目及要求',
			width :450,
			height:240,
			center:'center',
			href :'addRecheckExclDiseasePage.action'
		});
 	}
},{
		text:'批量删除',
		width:100,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#hazardfactorsShow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.check_disease_id+"'");
		    }); 
		    f_deluser(ids.toString());
	    }
}];
