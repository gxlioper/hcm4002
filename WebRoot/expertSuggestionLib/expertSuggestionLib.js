$(document).ready(function () {
	getItemResultLib();
	getDepartment_dep();
});
//----------------------------------------搜索框--------------------------------------------------------------------
/**
 * 查询--科室下拉框
 */
function getDepartment_dep(){
	$.ajax({
		url:'getDepartment_dep.action',
		type:'post',
		success:function(data){
			var themecombo2 =[{'text':'全部','id':''}];
			var objet = eval("("+data+")");
			$.each(objet,function(k,v){
				themecombo2.push({'text':v.dep_name,'id':v.id});
			})
			$("#dep_id").combobox("loadData",themecombo2);
	   }
	})
}
 //-------------------------------专科建议列表------------------------------------
/**
 * 显示专科建议列表
 */
 function getItemResultLib(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#itemResultLibShow").datagrid({
		 url:'getExpertSuggestionLib.action',
		 queryParams:{
			 sugg_word:$('#item_name').val(),
			 dep_id:$('#dep_id').combobox('getValue')
		 },
		 rownumbers:false,
		 toolbar:toolbar,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	{field:'ck',checkbox:true },
	            {align:'center',field:'id',title:'ID编码',width:15,},	
	            {align:'center',field:'sugg_word',title:'建议名词',width:15},	
			    {align:'center',field:'sugg_content',title:'建议内容',width:30},
			 	{align:'center',field:'dep_name',title:'所属科室',width:15},
			 	{align:'center',field:'chi_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:15},
			 	{align:'center',field:'caozuo',title:'修改',width:15,"formatter":update},
			 	{align:'center',field:'shanchu',title:'删除',width:15,"formatter":f_sc}
		 	]],   	
		 	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}
/**
 * 专科建议修改页面
 */
function update(val,row){
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function updateSampleDemo(id){
	$("#dlg-custedit").dialog({
	title:'修改专科建议',
	width : 600,
	height: 330,
	href:'updateExpertSuggestionLibPage.action?id='+id
});
	$("#dlg-custedit").dialog('open');
}
/**
 * 删除专科建议
 */
function f_sc(val, row) {
	return '<a href=\"javascript:pl_del(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
//---------------------------------------------头部工具栏------------------------------------
var toolbar=[{
	text : '新增专科建议',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增专科建议',
			width : 600,
			height:330,
			href : 'addExpertSuggestionLibPage.action'
		});
		$("#dlg-custedit").dialog('open');
	}
},{
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#itemResultLibShow').datagrid('getChecked');
	    $.each(checkedItems,function(index,item){
	        ids.push(item.id);
	    }); 
	    pl_del(ids.toString());
    }
}]
//---------------------------------------------删除项目结果知识库----------------------------------------------
function pl_del(ids){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	$.ajax({
				url:'deletExpertSuggestionLib.action',
				type:'post',
				data:{ids:ids},
				success:function(data){
					$.messager.alert("提示信息",data,"info");
					getItemResultLib();
				},
				error:function(){
					$.messager.alert("警告信息","操作失败",'error');
				}
			})    
	    }    
	});  
}
