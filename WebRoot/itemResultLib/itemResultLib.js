$(document).ready(function () {
	getItemResultLib();
	getDepartment_dep();
	getEaxmintion_item();
});
//----------------------------------------搜索框--------------------------------------------------------------------
/**
 * 清空查询 
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#exam_num').val(""),
	 $('#user_name').val(""),
	 $('#phone').val(""),
	 $('#id_num').val(""),
	 $('#com_Name').textbox('setValue',"")
	 getuser();
}
/**
 * 查询--科室下拉框
 */
function getDepartment_dep(){
	$('#dep_id').combobox({    
	    url:'getDepartment_dep.action',    
	    valueField:'id',    
	    textField:'dep_name',
	    onSelect: function(rec){
	    	$('#item_name').combobox({    
	    	    url:'getExaminationItem.action?dep_id='+rec.id,    
	    	    valueField:'id',    
	    	    textField:'item_name',
	    	    onLoadSuccess:function(){
	    	    	var zhi = $('#item_name').combobox('getData');
	    	    	if(zhi.length>0){
	    	    		$('#item_name').combobox('select',zhi[0].id);
	    	    	}
	    	    }
	    	});  
	    }
	});  

}
/**
 * 查询--检查项目下拉框
 */
function getEaxmintion_item()
{
	$.ajax
	({
		url:'getExaminationItem.action',
		type:'post',
		success:function(data)
		{
			var objet = eval("("+data+")");
			var qb = [{id:'',text:'全部'}]
			$.each(objet,function(k,v){
				qb.push({"id":v.id,"text":v.item_name});
			})
			$('#item_name').combobox("loadData",qb)
		}
	})
}
 //-------------------------------显示项目结果------------------------------------
/**
 * 显示项目结果
 */
 function getItemResultLib(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#itemResultLibShow").datagrid({
		 url:'queryItemResultLib.action',
		 queryParams:{
			 dep_id:$('#dep_id').combobox('getValue'),
			 exam_item_id:$('#item_name').combobox('getValue'),
			 exam_result:$('#exam_result_s').val()
		 },
		 rownumbers:false,
		 toolbar:toolbar,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	{field:'ck',checkbox:true },
	           	{align:'center',field:'id',title:'id',width:15,},	
	            {align:'center',field:'exam_item_id',title:'ID编码',width:15,},	
	            {align:'center',field:'item_num',title:'项目编码',width:15},	
			    {align:'center',field:'item_name',title:'项目名称',width:15},
			 	{align:'left',field:'exam_result',title:'结果描述',width:25},
			 	{align:'left',field:'exam_conclusion',title:'结论描述',width:25},
			 	{align:'center',field:'dep_name',title:'所属科室',width:15},
			 	{align:'center',field:'seq_codes',title:'顺序码',width:15},
			 	{align:'center',field:'default_value_s',title:'默认值',width:15,'hidden':false},
			 	/*{align:'center',field:'create_time',title:'创建时间',width:15},
			 	{align:'center',field:'updaters',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:15},*/
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
		    striped:true,
		    rowStyler: function(index,row){
		    	if(row.default_value_s == '是'){
    				return 'color:#ff0000;';
    			}
			}

	});
}
/**
 * 根据默认值修改列表颜色
 */
function xg_colu(value,row,index){
	if(row.default_value_s == '是'){
		return 'background-color:#6293BB;color:#fff;font-weight:bold;';
	}
}
/**
 * 修改页面
 */
function update(val,row){
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function updateSampleDemo(id){
	$("#dlg-custedit").dialog({
	title:'修改项目结果知识库',
	width : 700,
	height: 550,
	href:'updateItemResultLibPage.action?id='+id
});
	$("#dlg-custedit").dialog('open');
}
/**
 * 删除知识库
 */
function f_sc(val, row) {
	return '<a href=\"javascript:pl_del(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
//---------------------------------------------头部工具栏------------------------------------
var toolbar=[{
	text : '新增项目结果',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增项目结果',
			width : 700,
			height: 550,
			href : 'addItemResultLib.action?dep_id='+$('#dep_id').combobox('getValue')
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
				url:'delItemResultLib.action',
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
