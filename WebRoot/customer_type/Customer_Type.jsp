<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人员类型管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/customer_type/Customer_Type.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
	 function chaxun(){
		$("#type_name_s").combobox({
			url :'queryAllCustomer.action?',
			//editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'type_name',
			textField : 'type_name'
		})
	} 
	function searchFun(){
		getGrid();
	}
	function cleanFun(){
		$('#type_name_s').textbox('setValue',"");
		$('#type_code_s').textbox('setValue',"");
		getGrid('reload');
	}
	
 	var toolbar = [{
		    text:'新增人员类型',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#ctms_edit").dialog({
		    		title:'新增',
		    		href:'addCustomer.action'
		    	});
		    	$("#ctms_edit").dialog("open");
		    }
		}];
	
	$(document).ready(function () {
	    getGrid();
	   // chaxun();
	});
	
	function getGrid(){
	       var model = {"type_code": $('#type_code_s').textbox('getValue'),"type_name":$("#type_name_s").textbox('getValue')};
		   $("#customerlist").datagrid({
			url:'customertypeList.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	        toolbar: '#toolbar',
	        sortName: 'cdate',
			sortOrder: 'desc',
			height:400,
	        columns:[[
	                  {align:'center',field:"type_name",title:"类型名称",width:20},
	        		  {align:"center",field:"type_code",title:"类型编码","width":20},
	        		  {align:'center',field:"type_comment",title:"类型备注","width":18},
	        		  {align:"center",field:"creater",title:"创建人","width":15},       		  
	        		  {align:"center",field:"create_time",title:"创建时间","width":30},
	        		  {align:"center",field:"updater",title:" 更新者","width":15},
	        		  {align:"center",field:"update_time",title:"更新时间","width":30},
	        		  {align:"center",field:"xiugai",title:"修改",width:15,"formatter":f_xg},
	        		  {align:"center",field:"shanchu",title:"删除",width:15,"formatter":f_sc}
	     	        ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    },
		    singleSelect:true,
		    collapsible:true,
			pagination:true,
	        fitColumns:true,
	        striped:true,
	      	fit:true,
	        toolbar:toolbar
		});
	}
	//修改按钮
	function f_xg(val,row){	
			return '<a href=\"javascript:updateCtms(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	 }
	 //删除按钮
	function f_sc(val,row){
			return '<a href=\"javascript:deleteCtms(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
	}
	 function updateCtms(id){
					$("#ctms_edit").dialog({
					title:'修改',
					href:'updaterCustomer.action?id='+id
				});
				$("#ctms_edit").dialog('open');
		}

		function deleteCtms(id)
		{
			$.messager.confirm('提示信息','是否确定删除？',function(r){
				if(r){
			    $.ajax({
		   		url:'deleteCustomer.action?id='+id,
		   		type:'post',
		   		success:function(data){
		   			$.messager.alert('提示信息',data);
		   			 getGrid();
		   		},
		   		error:function(){
		   			$.messager.alert('提示信息','操作失败！','error');
		   		}
		   		});
				}
			})
		}
	    </script>
	</head>
	<body>
	<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
	<legend><strong>科室信息检索</strong></legend>
	<div id="tb" style="padding-right:20px;padding-left:50px;">
		&nbsp;&nbsp;&nbsp;&nbsp;类型编码: <input id="type_code_s" name="type_code_s" class="easyui-textbox" data-options="prompt:'类型编码'"/>
		&nbsp;&nbsp;&nbsp;&nbsp;类型名称:<input id="type_name_s" name="type_name_s" class="easyui-textbox"  data-options="prompt:'类型名称'" />
		
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>人员类型信息</strong></legend>
		  <div id="customerlist"></div>
		</fieldset>
	 </div>
	 </div>
	 <div id="ctms_edit" class="easyui-dialog" data-options="width: 500,height: 300,closed: true,cache: false,modal: true,top:50" ></div>
	</body>
	</html>