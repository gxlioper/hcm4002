<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>   

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驳回意见管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>finalRejection/finalRejectionadd.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript">
 
	function searchFun(){
		getGrid();
	}
	function cleanFun(){
		$('#reject_context').textbox('setValue',"");
		getGrid('reload');
	}
	
 	var toolbar = [{
		    text:'新增驳回意见',
		    iconCls:'icon-add',
		    handler:function(){
		    	$("#reject_edit").dialog({
		    		title:'新增驳回意见',
		    		href:'rejectadd.action'
		    	});
		    	$("#reject_edit").dialog("open");
		    }
		}];
	
	$(document).ready(function () {
	    getGrid();
	});
	
	function getGrid(){
	       var model = {"reject_context": $('#reject_context').textbox('getValue')};
		   $("#rejectlist").datagrid({
			url: 'finalRejectionList.action',
			queryParams: model,
			ctrlSelect:true,
	        pageSize: 15,//每页显示的记录条数，默认为10 
	        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	       //toolbar: '#toolbar',
	       // sortName: 'dep_name',
			sortOrder: 'asc',
			height:400,
	        columns:[[
	        		  {align:"center",field:"reject_context","title":"驳回意见","width":50},
	        		  {align:"center",field:"creater","title":"创建人","width":15},       		  
	        		  {align:"center",field:"create_time","title":"创建时间","width":30},
	        		  {align:"center",field:"updater","title":" 更新者","width":15},
	        		  {align:"center",field:"update_time","title":"更新时间","width":30},
	        		  {align:"center",field:"xg","title":"操作","width":15,"formatter":xg_sc}
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
	 function xg_sc(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:updatereject(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>'
		 		+ '&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deletereject(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>'
		 return str;
	 }
	 function updatereject(id){
					$("#reject_edit").dialog({
					title:'修改驳回意见',
					href:'rejectudater.action?id='+id
				});
				$("#reject_edit").dialog('open');
		}

		function deletereject(id)
		{
			$.messager.confirm('提示信息','是否确定删除该意见？',function(r){
				if(r){
			    $.ajax({
		   		url:'deletereject.action?id='+id,
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
			});
		}

	    </script>
	</head>
	<body>
	<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:80px;">
	<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
	<legend><strong>驳回意见检索</strong></legend>
	<div id="tb" style="padding-right:20px;padding-left:30px;">
		&nbsp;&nbsp;&nbsp;驳回意见: <input id="reject_context" name="reject_context" class="easyui-textbox"  data-options="prompt:'驳回意见'" />
		&nbsp;&nbsp;&nbsp;<a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查&nbsp;&nbsp;询</a>   	       
		&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="cleanFun();">&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空&nbsp;</a>
	</div>
	 </fieldset>
	 </div>
	 <div  data-options="region:'center'">
		<fieldset style="margin:5px;padding-right:20px;height:95%;">
		<legend><strong>驳回意见信息</strong></legend>
		  <div id="rejectlist"></div>
		</fieldset>
	 </div>
	 </div>
	 
	 <div id="reject_edit" class="easyui-dialog" data-options="width: 700,height: 300,closed: true,cache: false,modal: true,top:50" ></div>
	</body>
	</html>