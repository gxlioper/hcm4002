<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>配置管理</title>	
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/plugins/upload/uploadify.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/plugins/tree.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tree.js"></script>
<script type="text/javascript">
	$(function(){
		getGrid();
	});
	function getGrid()
	{
	       var model = {};
			$("#termconfiglist").datagrid({
			url: '<%=request.getContextPath()%>/webConfigtermConfigmainshow.action',
			queryParams: model,
			rownumbers:true,
	        //toolbar: '#toolbar',
	        sortName: 'code',
			sortOrder: 'desc',
			remoteSort:true,
	   		collapsible:true,
	   		rownumbers:true,
	        height: 325,
	        columns:[[{align:"center",field:"code",title:"编码",width:10},
	                  {align:"center",field:"name",title:"名称",width:30},
	        		  {align:"center",field:"types",title:"类型",width:30},
	        		  {align:"center",field:"remark",title:"标记",width:30},
	        		  {align:"center",field:"editConfig",title:"修改",width:10,formatter:editConfig}
	        		  ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	$("#rowTotal").val(value.total);
		    },
		    //singleSelect:true,
		    collapsible:true,
			pagination: true,
	        fitColumns:true,
	        fit:true,
	        striped:true
	        //toolbar:toolbar
		});
	}
	function editConfig(val,row)
	{
		return '<a href=\"javascript:goEditConfig(\''+row.code+'\')\"><img src=\"<%=request.getContextPath()%>/themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
	function goEditConfig(val)
	{
		if($('#dd').length>0)
		{
			$('#dd').remove();
		}
		$('body').append("<div id='dd'></div>");
		$('#dd').dialog({
			title: '修改配置信息',
			iconCls: 'icon-edit',
			closed: false,
			showType: 'show',
			//left:100,
			//top:30,
			height:300,
			width:600,
			href: '<%=request.getContextPath()%>/webConfigtoChangeType.action?code='+val,
			modal: true 
		});
	}
</script>
</head>
<body>
		<div class="easyui-layout" fit="true" style="margin:5px;">
		<div data-options="region:'north'" border="false" style="height:42px" >
			<div class="location">
				<div class="repeat textbox-div"><img src="<%=request.getContextPath()%>/themes/default/images/blank.gif" id="father_iconurl" class="icon-24 <s:property value="#session.menufather_iconurl"/>" alt="" /><s:property value="#session.menufather_name"/> <font face="simsun">&gt;</font> <s:property value="#session.menu_now"/></div>
				<s class="sprite"></s><i class="sprite"></i>
			</div>
		</div>
		<div data-options="region:'center'" border="false">
			<table id="termconfiglist"></table>
		</div>
		</div>
</body>
</html>
