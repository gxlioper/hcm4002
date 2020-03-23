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
<title>影像科室常用词列表</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/itemResultLib/examSummaryGuidanceLib.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>体检综述名称：<input maxlength="20" type="text" id="ser_word" class="textinput"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0; height:95%;">
<legend><strong>体检综述列表</strong></legend>
<table id="SummaryGuidanceList"> 
</table>
</fieldset>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 950,height: 380,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>