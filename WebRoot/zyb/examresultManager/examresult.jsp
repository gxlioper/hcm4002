<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>体检结论管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/occudisease/occudisease.js"></script>
<script type="text/javascript">
$(document).ready(function () { 
	enter();
	getResultList();
});
function enter(){
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			getResultList();
		}
	}
};

	//工具栏
	var toolbar = [ {
		text : '新增',
		iconCls : 'icon-add',
		width : 150,
		handler : function() {
			$("#dlg-edit").dialog({
				title : '新增',
				width :660,
				height:340,
				center:'center',
				href :'addZybExamresult.action'
			});
	 	}
	},{
			text:'批量删除',
			width:120,
			iconCls:'icon-cancel',
		    handler:function(){
		    	var ids = new Array();
		    	var checkedItems = $('#list').datagrid('getChecked');
			    $.each(checkedItems, function(index, item){
			       ids.push("'"+item.resultID+"'");
			    }); 
			    f_deletes(ids.toString());
		    }
	}];
	
//数据加载
function getResultList(){
     $("#list").datagrid({
		 url:'getZybexamresultList.action',
		 queryParams:{result_name:$("#result_name_s").val()},
		 toolbar:toolbar,
		 rownumbers:true,//行号
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
			 	{align:'center',field:'resultID',hidden:true},
			 	{align:'center',field:'result_name',title:'结论',width:30},
			 	{align:'center',field:'seq_code',title:'顺序码',width:10},
			 	{align:'center',field:'xg',title:'修改',width:15,"formatter":f_xg},
			 	{align:'center',field:'sc',title:'删除',width:15,"formatter":f_sc}
		 	]],	    	
	   
		 fitColumns:true,//自适应
		 pagination:true,//分页控件
		 striped:true,
		 fit:true,
		 onDblClickRow:function(index, row){
	   	 var row_id = $('#list').datagrid('getRows')[index].resultID;
	   		updatExamresult(row_id);
	       } 
      
	});
} 

	//修改
    function f_xg(val,row){	
    		return '<a href=\"javascript:updatExamresult(\''+row.resultID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
    	}
    	
    function updatExamresult(id){
    	$("#dlg-edit").dialog({
    		title:'修改职业病',
    	    width :660,
    		height:340,
    		center:'center',
    		href:'updateZybExamresult.action?resultID='+id,
    	});
    }
    
    //删除
    function f_sc(val, row) {
    	return '<a href=\"javascript:deleteExamresult(\''+row.resultID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
    }
    
    function deleteExamresult(id){
		$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
		    if (r){ 
		    	$.ajax({
		    		url : 'deleteExamresultone.action?resultID='+id,
		    		type : 'post',
		    		success : function(data) {
		    			$.messager.alert('提示信息',data,'info');
		    			$('#list').datagrid('reload');
		    		},
		    		error : function() {
		    			$.messager.alert('提示信息', '操作失败！', 'error');
		    		}
		    	});
		    }    
		});
	}
    
    //批量删除
    function f_deletes(id) {
    		if(id==null || id==""){
    			$.messager.alert('警告信息',"请选择要删除的记录",'error');
    			return;
    		}
    		$.messager.confirm('确认','您确认要删除记录吗？',function(r){    
    		    if (r){ 
    		    	$.ajax({
    		    		url : 'deleteZybExamresult.action?ids='+id,
    		    		type : 'post',
    		    		success : function(data) {
    		    			$.messager.alert('提示信息',data,'info');
    		    			$('#list').datagrid('reload');
    		    		},
    		    		error : function() {
    		    			$.messager.alert('提示信息', '操作失败！', 'error');
    		    		}
    		    	});
    		    }    
    		});
    	}
    	
function searchFun(){
	getResultList();
}
function cleanFun(){
	$("#result_name_s").textbox('setValue',"");
	getResultList();
}

</script>
<body class="easyui-layout">
	 <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
		  	&nbsp;&nbsp;&nbsp;&nbsp;模糊查询: <input id="result_name_s"  class="easyui-textbox" />
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	 	</div>
		</fieldset>
	</div>
	<div data-options="region:'center'" >
	  	<div id='list'></div>
	</div>   
<div id="dlg-edit"></div>
</body>
</html>