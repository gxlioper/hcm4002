$(document).ready(function () {
	//题目类型下拉框getSysSurveyQuestionDTO
	getqust_type_id();
	getgroupuserGrid();
});
//--------------------------------------题目类型下拉框-------------------------------
function  getqust_type_id(){
	$('#c_qust_type_id').combobox({
		  url:'getDatadisKongGe.action?com_Type=WJTMLX', 
		  editable:false,
		  panelHeight:'auto',
		  valueField:'id',    
		  textField:'name',
		  required: true
	})
}
/**
 * 显示题目列表
 */
 function getgroupuserGrid(){
	     var  model = {
	    		 	code:$('#c_code').val(),
	    		 	name:$('#c_name').val(),
	    		 	qust_type_id:$('#c_qust_type_id').combobox('getValue')
	     			};
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'getSysSurveyList.action',
		 queryParams:model,
		 toolbar:toolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
	            {align:'center',field:'code',title:'题目编码',width:18},
			 	{align:'center',field:'name',title:'题目名称',width:18},
			 	{align:'center',field:'qust_type_id',title:'题目类型',width:18},
			 	{align:'center',field:'sexs',title:'适用性别',width:18},
			 	{align:'center',field:'ages',title:'适用年龄',width:18},
			 	{align:'center',field:'marriageStates',title:'婚姻状况',width:15},
			 	{align:'center',field:'sss',title:'修改',width:10,formatter:f_xg},
			 	{align:'center',field:'ss',width:10,title:'删除',formatter:s_sc}
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
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].objectId;
	        	xg_f_tj(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}
 //----------------------删除问题------------------
 function s_sc(val, row) {
		return '<a href=\"javascript:s_ssc(\''+ row.objectId+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
function s_ssc(id){
	$.ajax({
		url:'deleteSysSurvey.action',
		data:{ids:id},
		type:'post',
		success:function(data){
			$("#groupusershow").datagrid("reload")
			$.messager.alert("提示信息",data,"info");
		},
		error:function(){
			$.messager.alert("警告信息","操作失败！","error");
		}
	})
}
/**
 * 修改页面
 */
function f_xg(val,row){
	return '<a href=\"javascript:xg_f_tj(\''+row.objectId+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"添加\" /></a>';
}

function xg_f_tj(id){
	$("#dlg-custedit").dialog({
		title : '修改题目',
		width :900,
		height:620,
		href :'getSysSurveyQuestionDTO.action?objectId='+id
	});
	$("#dlg-custedit").dialog('open');
	$("#dlg-custedit").dialog('center');
}

//----------------------------------定义工具栏---------------------------
 var w="";
 var h="";
var toolbar = [ {
	text : '新增题目',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		
		
		$("#dlg-custedit").dialog({
			title : '新增题目',
			width :900,
			height:620,
			href :'addSysSurveyPage.action'
		});
		$("#dlg-custedit").dialog('open');
		$("#dlg-custedit").dialog('center');
		
		// var url='addSysSurveyPage.action'; 
		// window.open(url,"新增题目", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		        ids.push(item.objectId);
		    }); 
		    s_ssc(ids.toString());
	    }
}];
