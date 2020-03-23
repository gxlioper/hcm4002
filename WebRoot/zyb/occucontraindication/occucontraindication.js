$(function () {
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			getOccuhazardfactorsList();
		}
	}
	getOccuhazardfactorsList();
	
});
/**
 * 显示职业禁忌证列表
 */
 function getOccuhazardfactorsList(){
   /*  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("table").prepend(str);*/
	     $("#hazardfactorsShow").datagrid({
		 url:'queryOccucontraindicationList.action',
		 toolbar:toolbar,
		 rownumbers:true,
		// height:'',
	     pageSize:15,
	     pageList:[15,30,40,50],//可以设置每页记录条数的列表 
	     nowrap:false,//内容显示不下换行
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'left',field:'contraindication_name',title:'禁忌证名称',width:30},
			 	{align:'left',field:'tremexplain',title:'相关术语解释',width:30},
			 	{align:'center',field:'sss',title:'修改',width:5,"formatter":f_xg},
			 	{align:'center',field:'ss',width:5,title:'删除',"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
    	  //  fit:true,
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
        	var row_id = $('#hazardfactorsShow').datagrid('getRows')[index].contraindicationID;
	        	updateZYB_OccuphyexaPage(row_id);
	        }
	       
	     
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.contraindicationID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害因素
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业禁忌证',
			width :795,
			height:385,
			center:'center',
			href:'updateOccucontraindicationList.action?contraindicationID='+id,
		});
}
//------------------------------------删除职业禁忌证----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.contraindicationID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业禁忌证
 * @param id
 */
function f_deluser(id) {
	if(id==""){
		$.messager.alert('警告信息', '请选择要删除的记录！', 'error');
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteOccucontraindicationList.action?ids='+id,
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
	    		url : 'deleteOccucontraindicationList.action?ids=\''+id+'\'',
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
	text : '新增职业禁忌证',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业禁忌证',
			width :795,
			height:385,
			center:'center',
			href :'addOccucontraindicationList.action'
		});
 	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#hazardfactorsShow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.contraindicationID+"'");
		        //ids.push(1);
		    }); 
		    f_deluser(ids.toString());
	    }
}];
