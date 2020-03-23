<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>经济类型管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript">
	$(document).ready(function () { 
		enter ();
		getData();
	});
	
	function enter (){
		document.onkeydown=function(e){
			var ev=document.all ? window.event:e;
			if(ev.keyCode==13){
				getData();
			}
		}
	};
	
	function getData(){
		$('#list').treegrid({
		    url:'geteconomicclassList.action', 
		    queryParams:{ economicclass_code:$('#economicclass_code_s').textbox('getValue'),
		    	          economicclass_name:$('#economicclass_name_s').textbox('getValue'),
		    },
		    dataType: 'json',
		    idField:'economicID', //根据那个字段判断树节点关系   
		    treeField:'economicclass_name',//根据那个列展现树
		    pageSize: 3,//每页显示的记录条数，默认为10 
		    pageList:[3,20,30,60,75],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:"center",field:'economicID',title:'ID',hidden:true},
		        {align:"center",field:'parentID',title:'父级id',hidden:true},
		        {align:"center",field:'economicclass_code',title:'类型编码',width:50},
		        {align:"left",field:'economicclass_name',title:'类型名称',width:50},
		        {align:"center",field:'update',title:'修改',width:30,"formatter":f_update},
		        {align:"center",field:'edit_del',title:'删除',width:50,"formatter":f_delete},
		        
		    ]] ,
			collapsible:true,//是否可折叠
       		pagination:true,//分页控件
        	rownumbers:false,//行号
        	fitColumns:true,
        	displayMsg:'',
        	//showFooter:true,//是否使用页脚
        	singleSelect:true,
		    fit:true,
        	toolbar:toolbar,
        	/* onClickRow:function(index,row){
	        } */
		}); 
	};
	/**
	  * 定义工具栏
	  */
	  var toolbar = [{
		    text:'父级新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#editf_add").dialog({
		    		title:'父级新增',
		    		href:'f_addEcoclass.action'
		    	});
		    	$("#editf_add").dialog("open");
		    }
		},{
		    text:'子级新增',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#edit_insert").dialog({
		    		title:'子级新增',
		    		href:'addEcoclass.action'
		    	});
		    	$("#edit_insert").dialog("open");
		    }
		}];
	
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:updateEco(\''+row.economicID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_delete(val,row){
			return '<a href=\"javascript:deleteEco(\''+row.economicID+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	
	function updateEco(id){
	    $("#edit_dlg").dialog({
			title:'修改',
			href:'updateEcoclass.action?economicID='+id
		});
		$("#edit_dlg").dialog('open'); 
	}
	//删除
	function deleteEco(id){
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deleteEcoclass.action?economicID='+id,
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
		$("#economicclass_name_s").textbox('setValue',"");
		$("#economicclass_code_s").textbox('setValue',"");
		getData();
	}
	function searchFun(){
		getData();
	}
</script>
</head>
<body class="easyui-layout"> 
    <div  data-options="region:'north'" style="height:80px;">
		<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
			&nbsp;&nbsp;&nbsp;&nbsp;父级类型名称: <input id="economicclass_name_s"  class="easyui-textbox"  data-options="prompt:'类型名称'" />
			&nbsp;&nbsp;&nbsp;&nbsp;父级类型编码: <input id="economicclass_code_s"  class="easyui-textbox" data-options="prompt:'类型编码'"/>
			
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
		</div>
		 </fieldset>
		  </div>
    <div data-options="region:'center'" style="padding:5px;background:#FFFFFF;width:90%;">
    	<div  id="list"></div>
    </div> 
    <div id="editf_add" class="easyui-dialog"  data-options="width: 500,height: 370,closed: true,cache: false,modal: true,top:50"></div>  
    <div id="edit_insert" class="easyui-dialog"  data-options="width: 500,height: 370,closed: true,cache: false,modal: true,top:50"></div>  
    <div id="edit_dlg" class="easyui-dialog"  data-options="width: 500,height: 370,closed: true,cache: false,modal: true,top:50"></div>  
</body> 
</html>