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
<title>问诊项目模板维护</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/askDiagnosisSample/askDiagnosisSample.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>模板类型：<select id="ser_type" class="easyui-combobox" data-options="panelHeight:'auto'">
					<option value="">全部</option>
					<option value="1">职业健康检查</option>
					<option value="2">放射健康检查</option>
					</select></dd>
				<!-- 	<dd>性别&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value='0'  name="sexx"  id="quanbu" checked="checked"  />全部&nbsp;&nbsp;&nbsp;
					<input type="radio" value='1'  name="sexx"  id="nan" />男&nbsp;&nbsp;&nbsp;
					<input type="radio" value='2'  name="sexx"  id="nv" />女 -->
					</dd>
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0; height:95%;">
<legend><strong>问诊项目模板列表</strong></legend>
<table id="askDiagnoList"> 
</table>
</fieldset>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 880,height:500,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>