<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>粉尘胸片参数管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript">
	$(document).ready(function () { 
		shuxing();
		getData();
		enter();
	});
	
	function enter(){
		document.onkeydown = function(e){//回车查询
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	getData();
		     }
		}
	}
	
	 function shuxing(){
		$('#h_tree').tree({    
			 url:'getdustitemList.action',
			 dataType:'json',
			 loadFilter :function(data,parent){
					 var obj = data;
					 var newData = new Array();
					 for(var i=0;i<obj.length;i++){
						 newData.push({id:obj[i].dustitem_id,text:obj[i].dustitem_name});
					 }
					 var newDate2 = [{id:'',text:'粉尘胸片类别',children:newData}];
					 return newDate2;
			 },
			 onClick:function(node){
				 getData(node.id);
			 },
			 onLoadSuccess:function(node,data){  
		           $("#h_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
		           var n = $("#h_tree").tree("getSelected");  
		           if(n!=null){  
		                $("#h_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
		           }  
	        },
		});  
	};
	
	function getData(id){
		var model={ "dustID":id };
		$('#list').datagrid({
		    url:'getdustitemoptionList.action', 
		    queryParams:model,
		    dataType: 'json',
		    pageSize: 15,//每页显示的记录条数，默认为10 
		    pageList:[15,20,30,60,75],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:'center',field:'ck',checkbox:true}   ,   
		        {align:"center",field:'optionID',title:'ID',hidden:true},
		        {align:"center",field:'dustitem_name',title:'参数名称',width:50},
		        {align:"center",field:'option_value',title:'参数值',width:50},
		        {align:"center",field:'showorder',title:'显示顺序',width:50},
		        {align:"center",field:'upd',title:'修改',width:15,"formatter":f_update},
		        {align:"center",field:'del',title:'删除',width:15,"formatter":f_delete},
		       
		        
		    ]] ,
       		pagination:true,//分页控件
        	rownumbers:true,//行号
        	fitColumns:true,
        	singleSelect:false,
		    fit:true,
        	toolbar:toolbar,
        	 onDblClickRow:function(index, row){
        	   	 var row_id = $('#list').datagrid('getRows')[index].optionID;
        	   			update_itemoption(row_id);
        	       }
		}); 
	};
	/**
	  * 定义工具栏
	  */
	  var toolbar = [{
		    text:'新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#edit_dlg").dialog({
		    		title:'新增',
		    		href:'addDustitemoption.action'
		    	});
		    	$("#edit_dlg").dialog("open");
		    }
		},{
		    text:'批量删除',
		    iconCls:'icon-cancel',
		    handler:function(){
		    	var ids = new Array();
		    	var checkeds = $('#list').datagrid('getChecked');
			    $.each(checkeds, function(index, row){
			       ids.push("'"+row.optionID+"'");
			    }); 
			    delete_dip(ids.toString());
		    }
		}];
	
	//批量删除
	function delete_dip(ids){

		if(ids==""){
			$.messager.alert('提示信息','请选择要删除的行!')
			return;
		}
		$.messager.confirm('提示信息','是否确定删除选中的行',function(r){
		 	if(r){
				 $.ajax({
					url : 'deletesDustitemoption.action',
					data : {ids:ids},
					type : "post",
					success : function(data) {
							$.messager.alert("操作提示",data);
							getData();
					},
					error : function() {
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				 });
		 	 }
		 });
	
	}
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:update_itemoption(\''+row.optionID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_delete(val,row){
			return '<a href=\"javascript:delete_itemoption(\''+row.optionID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	
	function update_itemoption(id){
	    $("#edit_dlg").dialog({
			title:'修改',
			href:'updDustitemoption.action?optionID='+id
		});
		$("#edit_dlg").dialog('open'); 
	}
	//删除
	function delete_itemoption(id){
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deletedustitemoption.action?optionID='+id,
		   		type:'post',
		   		success:function(data){
	   			$.messager.alert('提示信息',data);
	   			getData();
	   		    },
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
	   		});
			}
		}) 
	}
	function cleanFun(){
		dustitem_name_s='';
		getData();
	}
</script>
</head>
<body class="easyui-layout">
 	<div data-options="region:'west',title:'粉尘胸片类别',split:true" style="width:20%;padding-top:10px;">
 		<div id="h_tree"></div>
    </div> 
   
    <div data-options="region:'center'" style="padding:5px;background:#FFFFFF;width:90%;">
    	<div  id="list"></div>
    </div> 
    <div id="edit_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50"></div>
</body> 
</html>