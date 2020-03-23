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
		 url:'getExaminationresult.action',
		 queryParams:{
			 result_name:zhi
		 },
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].resultID,text:obj[i].result_name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].resultID,text:obj[i].result_name,});
				 }
				 var newDate2 = [{id:'',text:'所有结论',children:newData}];
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
 * 显示建议词列表
 */
 function getOccuhazardfactorsList(id){
    /* var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("table").prepend(str);*/
	     $("#hazardfactorsShow").datagrid({
		 url:'queryZybProposetemplatePage.action',
		 queryParams:{
			 OPTIONID:id
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 fitColumns:true,//自适应
		// height:'',
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'left',field:'OCCUPECLASSID',title:'结论',width:30},
			 	{align:'left',field:'TEMPLATENAME',title:'建议词名称',width:30},
			 	{align:'center',field:'ISDEFAULT',title:'是否默认',width:10,"formatter":sf},
			 	{align:'center',field:'ENABLE',title:'是否有效',width:10,"formatter":youxiao},
			 	{align:'center',field:'user_name',title:'最后修改人',width:10},
			 	{align:'center',field:'UPDATETIME',title:'最后修改时间',width:10},
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
    	   
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
        	/*var row_id = $('#hazardfactorsShow').datagrid('getRows')[index].hazardfactorsID;
	        	updateZYB_OccuphyexaPage(row_id);*/
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}
 //列表复选框 
 function sf(val,row){
		if(val){	
			return '<input type="checkbox"   id="isshow"  data="ISDEFAULT"  onclick="sfxg(this);"      value='+row.TEMPLATEID+'  style="cursor: pointer;"  checked="checked"   />';
		} else{
			return '<input type="checkbox"   id="isshow"   data="ISDEFAULT"   value='+row.TEMPLATEID+'  onclick ="sfxg(this);"    style="cursor: pointer;"    />';
		}
}
function youxiao(val,row){
	if(val){	
		return '<input type="checkbox"   id="youx"  data="ENABLE"  onclick="sfxg(this);"      value='+row.TEMPLATEID+'  style="cursor: pointer;"  checked="checked"   />';
	} else{
		return '<input type="checkbox"   id="youx"  data="ENABLE"  value='+row.TEMPLATEID+'  onclick ="sfxg(this);"    style="cursor: pointer;"    />';
	}
}
function sfxg(obj){
	var is=false;
	if($(obj).is(":checked")){
		is=true;
	}
	var zhi = $(obj).attr('data')
	if(zhi=='ENABLE'){
		var model = {
				ENABLE:is,
				chebox:zhi,
				TEMPLATEID:$(obj).val()
		}
	}else{
		var model = {
				ISDEFAULT:is,
				chebox:zhi,
				TEMPLATEID:$(obj).val()
		}
	}
	
	$.post('updateProposetemplateListchebox.action',model,function(){
		$('#hazardfactorsShow').datagrid('reload');
	})
}
//-------------------------------------修改职业建议词页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateZYB_OccuphyexaPage(\''+row.TEMPLATEID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改职业危害因素
 * @param id
 */
 function updateZYB_OccuphyexaPage(id){
			$("#dlg-custedit").dialog({
			title:'修改职业建议词',
			width :500,
			height:415,
			center:'center',
			href:'updateZybProposetemplatePage.action?TEMPLATEID='+id
		});
}
//------------------------------------删除体检类别----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:delete_lb(\''+row.TEMPLATEID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 *批量删除职业因素
 * @param id
 */
function f_deluser(id) {
	if(id==""){
		$.messager.alert("提示信息","请选择需要删除的记录","error");
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZybProposetemplatePage.action?ids='+id,
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
	if(id==""){
		$.messager.alert("提示信息","请选择需要删除的记录","error");
		return;
	}
	$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteZybProposetemplatePage.action?ids=\''+id+'\'',
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
	text : '新增职业建议词',
	iconCls : 'icon-add',
	width : 150,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增职业建议词',
			width :500,
			height:415,
			center:'center',
			href :'addZybProposetemplatePage.action'
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
		       ids.push("'"+item.TEMPLATEID+"'");
		        //ids.push(1);
		    }); 
		    f_deluser(ids.toString());
	    }
}];
