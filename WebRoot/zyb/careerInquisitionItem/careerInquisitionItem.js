$(document).ready(function () {
	$('#item_name').focus();
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
 * 显示体检问诊项目
 */
 function getOccuhazardClassList(){
	     $("#OccuhazardClassshow").datagrid({
		 url:'getZybCareerInquisitionItemList.action',
		 queryParams:{
			 item_code:$('#item_code_c').val(),
			 item_name:$('#item_name_c').val()
		 },
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'center',field:'item_code',title:'职业体检问诊项目编码',width:28},
	            {align:'left',field:'item_name',title:'职业体检问诊项目名称',width:28},
			 	{align:'center',field:'sex_s',title:'适用性别',width:28},
			 	{align:'center',field:'aa',title:'是否显示在体检列表',width:28,'formatter':sf},
			 	{align:'center',field:'order',title:'显示顺序',width:28},
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
    	    fit:true,
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
	        	var row_id = $('#OccuhazardClassshow').datagrid('getRows')[index].item_id;
	        	updateZYB_OccuhazardClassPage(row_id);
	        },
			rowStyler:function(index,row){//改变行颜色
				if (row.isshow=='0'){
					return 'color:ff0000;';
				}
			}
	       
	       // nowrap:false,//内容显示不下换行
	});
}
function sf(val,row){
	if(row.isshow=='0'){	
	return '<input type="checkbox"   id="isshow"  onclick="sfxg(this);"     value='+row.item_id+'  style="cursor: pointer;"  checked="checked"   />';
	} else{
	return '<input type="checkbox"   id="isshow"  value='+row.item_id+'  onclick ="sfxg(this);"    style="cursor: pointer;"    />';
	}
}
function sfxg(obj){
	var iss="1";
	if( $(obj).is(':checked') ){
		iss="0";
	}
	var model={
	 		'item_id':$(obj).val(),
			'isshow':iss,
			'zt':'1'
	 	} 
	$.ajax({
		url:"saveZybCareerInquisitionItemCode.action",
		data:model,
		type:'post',
		success:function(data){
			$('#OccuhazardClassshow').datagrid('reload');
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	}) 
}
function ys(val,row){
	return 'background-color:#ffee00;color:red;';

}
//-------------------------------------修改职业体检问诊项目页面---------------------
 function g_wlb(val,row){	
	return '<a href=\"javascript:updateZYB_OccuhazardClassPage(\''+row.item_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业体检问诊项目
 * @param id
 */
 function updateZYB_OccuhazardClassPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业体检问诊项目',
			width :650,
			height:410,
			center:'center',
			href:'getUpdateZybCareerInquisitionItemPage.action?item_id='+id,
		});
		$("#dlg-custedit").dialog('open');
}
//------------------------------------删除体检类别----------------------------------
function s_wlb(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.item_id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业危害类别
 * @param id
 */
function s_s_wlb(id) {
	if(id==""){
		$.messager.alert("提示信息","请选择要删除的记录","error");
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZybCareerInquisitionItem.action?ids='+id,
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
	    		url : 'deleteZybCareerInquisitionItem.action?ids=\''+id+'\'',
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
	text : '新增职业体检问诊项目',
	iconCls : 'icon-add',
	width : 170,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业体检问诊项目',
			width :650,
			height:410,
			center:'center',
			href :'addZybCareerInquisitionItemPage.action'
		});
		$("#dlg-custedit").dialog('open');
 	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#OccuhazardClassshow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		       ids.push("'"+item.item_id+"'");
		    }); 
		    s_s_wlb(ids.toString());
	    }
}];
