<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<link type="text/css" href="/scripts/jquery-ui.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.validatebox.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/chargingItemSchedule/ItemSchedule.js"></script>
	
	
	 --%>
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<link type="text/css" href="/scripts/jquery-ui.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.validatebox.js"></script>
<script type="text/javascript" src="/scripts/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  

<script type="text/javascript"
	src="<%=request.getContextPath()%>/chargingItemSchedule/ItemSchedule.js?randomId=<%=Math.random()%>"></script>

<title>项目排期</title>


<style type="text/css">
</style>
</head>
<body class="easyui-layout" >
		<div id='genzongrili' data-options="region:'west',split:true" style="width: 20%;height:80%">
			<div id="genzongrili" class="easyui-calendar"
				style="width:100%; height:250px;"></div>
		</div>
		<div data-options="region:'center'" style="height: 100%">
			<fieldset style="margin: 10px; padding-right: 0;">
				<legend>
					<strong>收费项目查询</strong>
				</legend>
				<div class="user-query">
					<dl>
						<dt style="width:60px;">项目编码:</dt>
						<dd>
							<input class="textinput" type="text" id="pq_item_code"
								style="height:22px; width: 140px;" class="easyui-validatebox" />
						</dd>
						<dt style="width: 60px">项目名称:</dt>
						<dd>
							<input class="textinput" type="text" id="pq_item_name"
								style="height:22px; width: 140px;" />
						</dd>
						<dt style="width:40px">科室</dt>
						<dd>
							<input  class="easyui-combobox" type="text" id="pq_dep_id" style="height:30px; width: 140px;" />
						</dd>
						<dd>
							<a href="javascript:datagridSchedule();"
								class="easyui-linkbutton c6"
								style="width: 100px;">查询</a>
						</dd>
					</dl>
				</div>
			</fieldset>
			<fieldset style="margin: 5px; padding-right: 0; height:84%">
				<legend>
					<strong>收费项目列表</strong>
				</legend>
				<table id="itemscheduleList">
				</table>
			</fieldset>
		</div>
		<div id="tb">
			<a href="#" value="排期"  onclick="paiqile();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,text:'排期'">批量排期</a>
	 </div>
		
        <div id="edit_dlg" class="easyui-dialog"  data-options="width:400,height:370,closed: true,cache: false,modal: true,top:50,left:300"></div>
</div>  
		
</body>
</html>