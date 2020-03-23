$(document).ready(function () {
	$('#exam_set_name').focus();
	dgridType();
	
});




/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 
/**
 * 显示体检套餐
 */
 function dgridType(){
	 	 var exam_set_name = "";
	 	if($('#ck_exam_set_name').is(":checked")){
	 		 exam_set_name = $('#exam_set_name').val();
	 	}
	 	var set_class = "";
	 	if($('#ck_set_class').is(":checked")){
	 		set_class = $('#set_class').combobox('getValue') 
	 	}

	 	 var model = {
	 			set_class:set_class,
	 			set_type_name:exam_set_name
	 	 }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#typeShow").datagrid({
		 url:'getqueryExamSetTypeList.action',
		 queryParams:model,
		 toolbar:toolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'center',field:'id',title:'ID',width:20},
	            {align:'center',field:'set_type_name',title:'类别名称',width:20},
			 	{align:'center',field:'set_class_s',title:'类别类型',width:20},
			 	{align:'center',field:'sss',title:'修改',width:20,"formatter":l_xg},
			 	{align:'center',field:'ss',width:20,title:'删除',"formatter":l_sc}
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
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}
//-------------------------------------修改体检项目---------------------
 function l_xg(val,row){	
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改体检
 * @param id
 */
 function updateSampleDemo(id){
			$("#dlg-exam_set_type").dialog({
			title:'修改类别',
			href:'saveSetTypePage.action?id='+id
		});
		$("#dlg-exam_set_type").dialog('open');
}
//------------------------------------删除体检套餐----------------------------------
function l_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除体检套餐方法
 * @param id
 */
function f_deluser(id) {
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deleteExamSetTypeList.action?id='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$("#typeShow").datagrid('reload');
	    			$.messager.alert('提示信息',data);
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
	text : '新增类别',
	iconCls : 'icon-add',
	width:120,
	handler : function() {
		$("#dlg-exam_set_type").dialog({
			center:'center',
			title : '新增类别',
			href :'saveSetTypePage.action'
		});
		$("#dlg-exam_set_type").dialog('open');
	}
}];

var set_ids;
function setweixinadddo(set_ids){
	 $.messager.confirm('提示信息','执行此操作，将对所选套餐同步到微信端，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'setweixinadddo.action',
				data : {
					    ids:set_ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);															
							} else {
								$.messager.alert("操作提示", text, "error");
							}							
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
}

//启停修改
function f_qt(val,row){
 var html='';
    if(val=="Y"){
      return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
    }else if(val=='N') {
       return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
     }
}


//启（停）修改
function f_startorblock(id,html){
	$.messager.confirm('提示信息','是否确认'+html+'该套餐？',function(r){
		if(r){
		$.ajax({
     	url:'getstartorblock.action?id='+id,
     	type:'post',
     	success:function(data){
     		var obj=eval("("+data+")");
     		if(obj=='success'){
     			$.messager.alert('提示信息',html+"该套餐成功！");
     			$('#groupusershow').datagrid('reload');
     		}else if(obj=='error'){
     			$.messager.alert('提示信息',html+"该套餐失败！",'error');
     		}else{
     			$.messager.alert('提示信息',obj);
     		}
     	},
     	error:function(){
     		$.messager.alert('提示信息','操作失败！','error');
     			}
			});
		}
	})
}

