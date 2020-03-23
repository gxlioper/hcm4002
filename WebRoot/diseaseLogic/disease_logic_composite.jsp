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
<!--不保存缓存  -->
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<title>复合疾病逻辑维护</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/diseaseLogic/disease_logic_composite.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	$(function() {
		$("input").attr("maxlength", "45");
	})
</script>
</head>
<body>
<input type="hidden"  id='web_Resource'  value='<s:property value="web_Resource"/>' />
	<div class="easyui-layout" fit="true">
	<div data-options="region:'west',title:'科室',split:true"
		style="width: 15%;">
		<div id='some_tree'></div>
	</div>
	<div data-options="region:'center'">
		<fieldset style="margin: 5px;">
			<legend>
				<strong>复合疾病逻辑查询</strong>
			</legend>
			<div class="user-query">
				<dl>
					<dt style="width: 140px; text-align: right; margin-right: 5px">阳性指标/疾病名称</dt>
					<dd>
						<input class="textinput" type="text" id="c_logic_name" />
					</dd>
					<dt style="width: 140px; text-align: right; margin-right: 5px">检查项目名称</dt>
					<dd>
						<input class="textinput" type="text" id="e_item_name" />
					</dd>
					<dt style="text-align: right; padding-right: 10px">启停:</dt>
					<dd>
						<select id="c_isActive" class="easyui-combobox"
							style="width: 100px; height: 26px;" data-options='panelHeight:80'>
							<option value=''>全部</option>
							<option value='Y' selected="selected">启用</option>
							<option value='N'>停用</option>
						</select>
					</dd>
					<dd>
						<a href="javascript:chaxun();"
							class="easyui-linkbutton c6 l-btn l-btn-small"
							style="width: 100px;">查询</a>
					</dd>
				</dl>
			</div>
		</fieldset>
		<fieldset style="margin: 5px; padding-right: 0;" id="danxiang">
			<legend>
				<strong>复合疾病逻辑列表</strong>
			</legend>
			<table id="groupusershow" class="easyui-datagrid">
			</table>
		</fieldset>
	</div>
	</div>
	<div id="dlg-custedit" class="easyui-dialog" data-options="width:'1100',closed: true,cache: false,modal:true,top:50" style="min-height: 500px"></div>
</body>
</html>