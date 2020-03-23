<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职业病团报模板管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript">
	$(document).ready(function () { 
		enter();
		getData();
	});
	function enter(){
		document.onkeydown = function(e){
			var ev = document.all ? window.event : e;
			if(ev.keyCode==13) {
				getData();
			}
		}
	};
	function getData(){
		var model={"occuphyexaclass_name":$('#occuphyexaclass_name_s').textbox('getValue'),"exam_type_name":$('#exam_type_name_s').textbox('getValue')
		};
		$('#occtemplate').datagrid({
		    url:'getOcctemplateList.action', 
		    queryParams:model,
		    dataType: 'json',
		    pageSize: 20,//每页显示的记录条数，默认为10 
		    pageList:[20,30,50,70,100],//可以设置每页记录条数的列表 
		    columns:[[
		        {align:"center",field:'id',title:'id',hidden:true},
		        {align:"center",field:'exam_type_name',title:'职业体检大类别',width:50},
		        {align:"center",field:'occuphyexaclass_name',title:'职业体检类别',width:50},
		        {align:"center",field:'template',title:'模板名称',width:50},
		        {align:"center",field:'remark',title:'备注',width:50},
		        {align:"center",field:'update',title:'修改',width:30,"formatter":f_update},
		        {align:"center",field:'edit_del',title:'删除',width:30,"formatter":f_delete},
		        
		    ]] ,
       		pagination:true,//分页控件
        	rownumbers:true,//行号
        	fitColumns:true,
        	fit:true,
        	singleSelect:true,
        	toolbar:toolbar,
        	onDblClickRow:function(index, row){
          	var row_id = $('#occtemplate').datagrid('getRows')[index].id;
          	        updateTofW(row_id);
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
		    	$("#edit").dialog({
		    		title:'新增',
		    		href:'addOcctemplate.action'
		    	});
		    	$("#edit").dialog("open");
		    }
		}];
	
	//修改按钮
	function f_update(val,row){	
			return '<a href=\"javascript:updateTofW(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_delete(val,row){
			return '<a href=\"javascript:deleteTofW(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	//更新
	
	function updateTofW(id){
	    $("#edit").dialog({
			title:'修改',
			href:'updateOcctemplate.action?id='+id
		});
		$("#edit").dialog('open'); 
	}
	//删除
	function deleteTofW(id){
		$.messager.confirm('提示信息','是否确定删除？',function(r){
			if(r){
				$.ajax({
		   		url:'deleteOcctemplate.action?id='+id,
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
		$("#exam_type_name_s").textbox('setValue',"");
		$("#occuphyexaclass_name_s").textbox('setValue',"");
		getData();
	}
	function searchFun(){
		getData();
	}
</script>
</head>
<body class="easyui-layout"> 
    <div data-options="region:'north'," style="height:80px;">
    	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
		<legend><strong>信息检索</strong></legend>
		<div id="tb" style="padding-right:20px;padding-left:50px;">
    	职业体检大类别: <input id="exam_type_name_s" name="exam_type_name_s" class="easyui-textbox"  data-options="prompt:'职业体检大类别'" />
		职业体检类别: <input id="occuphyexaclass_name_s" name="occuphyexaclass_name_s" class="easyui-textbox" data-options="prompt:'职业体检类别'"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>&nbsp;&nbsp; 
		 	       
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a><br />
		</div>
		</fieldset>
    </div> 
    <div data-options="region:'center'" style="padding:5px;background:#eee;">
    	<div id="occtemplate"></div>
    </div>
    <div id="edit" class="easyui-dialog"  data-options="width: 500,height: 400,closed: true,cache: false,modal: true,top:50"></div>  
</body> 
</html>