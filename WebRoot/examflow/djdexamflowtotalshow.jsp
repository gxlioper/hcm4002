<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/djdexamflowtotalshow.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>统计条件</strong>
	</legend>
	<div class="user-query">
		    <dl>		  
			<dt style="width:60px;height:26px;">类型</dt>
			<dd><select class="easyui-combobox" id="actiontype" name="actiontype"
					data-options="height:26,width:80,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '分发',  
                   value: 0,  
                   selected:true},   //把日计量做为默认值  
                   {label: '签收',  
                   value: 1},]"></select>
			</dd>		  
			<dt style="width:80px;height:26px;">查询日期</dt>
			<dd>
				<input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                      至
			    <input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input>
				<a href="javascript:searchexamflowsholist();" class="easyui-linkbutton" style="width:100px;height:26px;">查询</a>
			</dd>
			</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员列表</strong>
	</legend>
	         &nbsp;&nbsp;&nbsp;&nbsp;合计总人数：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>
			<div id="selectexamflowsholist"></div>
</fieldset>
</body>
</html>