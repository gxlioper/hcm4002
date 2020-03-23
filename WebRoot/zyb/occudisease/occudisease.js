$(function () {
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			shu($('#cx').val());
		}
	}
	shu("");
	
});
/**
 * 树
 */
function shu(zhi){
	$("#some_tree").tree({
		 url:'getZyboccudiseaseClassList.action',
		 queryParams:{
			 diseaseclass_name:zhi
		 },
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].diseaseclassID,text:obj[i].diseaseclass_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].diseaseclassID,text:obj[i].diseaseclass_name});
				 }
				 var newDate2 = [{id:'',text:'所有职业病类别',children:newData}];
				 return newDate2;
			 }
		 },
		 onClick:function(node){
			 getOccuhazardfactorsList( node.id );
		 },
		 onLoadSuccess:function(node,data){  
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	 getOccuhazardfactorsList( node.id );
        }
	});
}

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
 * 显示职业病列表
 */
 function getOccuhazardfactorsList(hazardclassID_c){
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("table").prepend(str);
	     $("#hazardfactorsShow").datagrid({
		 url:'getZyboccudiseaseList.action',
		 queryParams:{
			 diseaseclassID:hazardclassID_c
		 },
		 toolbar:toolbar,
		 rownumbers:true,//行号
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
			 	{align:'left',field:'occudisease_name',title:'职业病名称',width:30},
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
    	    fit:true,
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
        	var row_id = $('#hazardfactorsShow').datagrid('getRows')[index].occudiseaseID;
	        	updateZYB_OccuphyexaPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.occudiseaseID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害因素
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业病',
			width :420,
			height:240,
			center:'center',
			href:'updateZyboccudiseasePage.action?occudiseaseID='+id,
		});
}
//------------------------------------删除职业病----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.occudiseaseID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业因素
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
	    		url : 'deleteZyboccudisease.action?ids='+id,
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
	    		url : 'deleteZyboccudisease.action?ids=\''+id+'\'',
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
	text : '新增职业病',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业病',
			width :420,
			height:240,
			center:'center',
			href :'addZyboccudiseasePage.action'
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
		       ids.push("'"+item.occudiseaseID+"'");
		    }); 
		    f_deluser(ids.toString());
	    }
}];
