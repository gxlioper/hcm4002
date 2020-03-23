$(function () {
//	document.onkeydown = function(e){
//		var ev = document.all ? window.event : e;
//		if(ev.keyCode==13) {
//			shu($('#cx').val());
//		}
//	}
	shu("");
	
});
function chaxun(){
	var n = $("#some_tree").tree("getSelected");
	getOccuhazardfactorsList(n.id);
}
/**
 * 树
 */
function shu(zhi){
	$("#some_tree").tree({
		 url:'getOccuhazardfactorsList.action',
		 queryParams:{
			 hazardclass_name:zhi
		 },
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].hazardclassID,text:obj[i].hazardclass_name,});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].hazardclassID,text:obj[i].hazardclass_name,});
				 }
				 var newDate2 = [{id:'',text:'所有职业危害类别',children:newData}];
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
//	var obj = $("#some_tree").tree('getRoots');
//	$("#some_tree").tree('select',obj[0].id);
//	getOccuhazardfactorsList();
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
 * 显示职业危害因素列表
 */
 function getOccuhazardfactorsList(hazardclassID_c){
//	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
//		 $("table").prepend(str);
	     $("#hazardfactorsShow").datagrid({
		 url:'getOccuHazardFactorsList.action',
		 queryParams:{
			 hazardclassID:hazardclassID_c,
			 hazard_name : $("#ser_hazard_name").textbox('getValue')
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		// height:'',
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'center',field:'hazard_code',title:'职业危害编码',width:15},
			 	{align:'left',field:'hazard_name',title:'职业危害名称',width:15},
			 	{align:'center',field:'hazard_year',title:'危害年限',width:15},
			 	{align:'center',field:'order',title:'显示顺序',width:15},
			 	{align:'left',field:'hazard_desc',title:'职业危害描述',width:15},
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
        	var row_id = $('#hazardfactorsShow').datagrid('getRows')[index].hazardfactorsID;
	        	updateZYB_OccuphyexaPage(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.hazardfactorsID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害因素
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业危害因素',
			width :800,
			height:550,
			center:'center',
			href:'updateOccuHazardFactorsPage.action?hazardfactorsID=\''+id+'\'',
		});
}
//------------------------------------删除体检类别----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.hazardfactorsID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业因素
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
	    		url : 'deledtOccuHazardFactors.action?ids='+id,
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
	    		url : 'deledtOccuHazardFactors.action?ids=\''+id+'\'',
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
	text : '新增职业危害因素',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业危害因素',
			width :800,
			height:550,
			center:'center',
			href :'addOccuHazardFactorsPage.action'
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
		       ids.push("'"+item.hazardfactorsID+"'");
		        //ids.push(1);
		    }); 
		    f_deluser(ids.toString());
	    }
}];
