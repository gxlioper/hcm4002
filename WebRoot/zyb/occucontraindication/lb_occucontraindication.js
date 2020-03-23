$(function () {
	shu();
	/*document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			getOccuhazardfactorsList();
		}
	}
	getOccuhazardfactorsList();*/
	
});
/**
 * 显示职业禁忌证列表
 */
 function getOccuhazardfactorsList(){
	    var f = "";
	    var z = "";
	    var node = $("#some_tree").tree('getSelected');
	    if(node.lx=='lx'){//职业危害体检类别
 	    	f = '\''+$('#some_tree').tree("getParent",node.target).id+'\''; 
 	    	z = node.id;
	    } else if(node.ys == 'ys'){//因素
	    	f ='\''+ node.id+'\'';
	    	//z = $("#some_tree").tree("getChildren",node.target);
	    } else {
	    	var zi = $('#some_tree').tree("getChildren",node.target)
	    	f = $('#some_tree').tree('getSelected');
	    	var arra = new Array();
	    	for(var i = 0 ; i < zi.length ; i++){
	    		console.log(zi[i].text);
	    		if(zi[i].ys=='ys'){
	    			arra.push('\''+zi[i].id+'\'');
	    		}
	    	}
	    	f = arra.toString();
	    	if(f==""){
	    		f='\'\'';
	    	}
	    }
	 
   /*  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("table").prepend(str);*/
	     $("#hazardfactorsShow").datagrid({
		 url:'getLbOccucontraindicationList.action',
		 queryParams:{
			 contraindication_name:$('#contraindication_name').val(),
			 occuphyexaclassID:z,
			 hazardfactorsID:f
		 },
		 toolbar:toolbar,
		 rownumbers:true,
		// height:'',
	     pageSize:15,
	     pageList:[15,30,40,50],//可以设置每页记录条数的列表 
	     nowrap:false,//内容显示不下换行
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'left',field:'contraindication_name',title:'禁忌证名称',width:30},
			 	{align:'left',field:'tremexplain',title:'相关术语解释',width:40},
			 	{align:'center',field:'DISORDER',title:'顺序码',width:20},
			 	/*{align:'left',field:'hazard_name',title:'危害因素',width:40},*/
			 	{align:'center',field:'occuphyexaclass_name',title:'职业体检类别',width:40},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',title:'删除',"formatter":f_sc}
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
	        	updateZYB_OccuphyexaPage(row.RID);
	        }
	       
	     
	});
}

//-------------------------------------修改职业体检类别页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.RID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
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
			href:'getupdateLbOccucontraindicationPage.action?RID='+id
		});
			$("#dlg-custedit").dialog('open');
}
//------------------------------------删除职业禁忌证----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.RID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
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
	    		url : 'deleteOccuhazardfactorsOccucontraindication.action?RID='+id,
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
		var node = $("#some_tree").tree('getSelected');
		var lx = node.lx;
		var f_lx = $("#some_tree").tree("getParent",node.target);
		//alert(f_lx.id);
		if(lx=="lx"){
			$("#dlg-custedit").dialog({
				title : '新增职业禁忌证',
				center:'center',
				href :'getLbAddOccucontraindicationPage.action?occuphyexaclassID='+node.id+'&hazardfactorsID='+f_lx.id
			});
			$("#dlg-custedit").dialog('open');
		} else {
			$.messager.alert("提示信息","请选择危害因素和类别","error");
			return;
		}
	
 	}
}];
/**
--------------------------------------------------树-----------------------------

**/
/**
 * 树
 */
function shu(){
	/* var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);*/
	$("#some_tree").tree({
		 url:'getZybExamSetTree.action',
		 onClick:function(node){
		 },
		 onLoadSuccess:function(node){  
		 		$(".loading_div").remove();
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	getOccuhazardfactorsList();
        }
	});
}
