<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>签单计划</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/sign_bill_plan.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>签单计划名称:&nbsp;&nbsp;<input type="text" id="ser_sign_name" class="textinput"/></dd>
					<dd>申请人：&nbsp;&nbsp;<select id="ser_creater">
					</select></dd>
					<dd>年度：&nbsp;&nbsp;<input type="text" id="ser_sign_year" value="<s:property value="sign_year"/>" class="textinput"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:chaxunsingn();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0;height:95%;">
<legend><strong>签单计划列表</strong></legend>
<table id="sign_bill_span_list"> 
</table>
</fieldset>
</div>
</div>
<div id="dlg-edit"></div>
<div id="dlg-show"></div>
</body>
</html>