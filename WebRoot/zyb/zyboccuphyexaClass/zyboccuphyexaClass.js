$(document).ready(function () {
	getoccuphyexaclassList();
	$('#occuphyexaclass_name_c').focus();
	//回车事件
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	getoccuphyexaclassList();
		     }
		}
});

/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 
/**
 * 查询条件加回车事件
 * 
 */
function huiche(event){
	if(event.keyCode == 13){
		getoccuphyexaclassList();
	}
}
/**
 * 显示职业体检类别
 */
 function getoccuphyexaclassList(){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'getZYB_OccuphyexaList.action',
		 queryParams:{
			 occuphyexaclass_name:$('#occuphyexaclass_name_c').val()
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 height:510,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'left',field:'occuphyexaclass_name',title:'类别名称',width:28},
			 	{align:'center',field:'order',title:'显示顺序',width:28},
			 	{align:'left',field:'remark',title:'备注',width:28},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',width:10,title:'删除',"formatter":f_sc}
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
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].occuphyexaclassID;
	        	updateZYB_OccuphyexaPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	     
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.occuphyexaclassID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改体检
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业体检类别',
			width :700,
			height:400,
			center:'center',
			href:'updateZYB_OccuphyexaPage.action?occuphyexaclassID='+id,
		});
}
//------------------------------------删除体检类别----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.occuphyexaclassID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业类别
 * @param id
 */
function f_deluser(id) {
	if(id==""){
		$.messager.alert("提示信息","请选择要删除的记录","error");
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZYB_Occuphyexa.action?ids='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#groupusershow').datagrid('reload');
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
	    		url : 'deleteZYB_Occuphyexa.action?ids=\''+id+'\'',
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data,'info');
	    			$('#groupusershow').datagrid('reload');
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
	text : '新增职业体检类别',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业体检类别',
			width :700,
			height:400,
			center:'center',
			href :'addZYB_OccuphyexaPage.action'
		});
 	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.occuphyexaclassID+"'");
		        //ids.push(1);
		    }); 
		    f_deluser(ids.toString());
	    }
}];
