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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/companyManager/zybdeptmanager.js"></script>
</head>
<body style="height:500px;">

<div id="cont-page" class="include-page" >

<input id="id" name="id" value="" type="hidden"/>
<div class="easyui-layout" id="commanagerbar">
    <div data-options="region:'west',split:true" title="单位列表" style="width:230px;">
    &nbsp;
    <input id="com_name" onkeyup="reopen();"  maxlength="20" style="background-position:right; background-repeat:no-repeat;height:15px; width: 100px;" />
    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="height:20px;">单位查询</a>  
    <div id="append" style="position: absolute; background-color: white;"></div>  
    	 <div id="depttree"></div>
    </div>
    <div  data-options="region:'center'">    
    <div id="compantmanager" class="easyui-tabs" data-options="tabHeight:20" border="false"></div>    
	
    </div>
</div>

</div>


</body>
</html>