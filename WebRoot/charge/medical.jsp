<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>医保项目管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />


<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/medical.js?randomId=<%=Math.random()%>"></script>

<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:250px; top:120px; width:300px;height:130px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input[type=text]{ width:180px;}
</style>
</head>
<body>
<!-- <a href="javascript:history.back(-1)">返回上一页</a> -->
 <a href="javascript:history.back(-1)"   class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:110px;margin: 5px">返回</a>
<fieldset>
 				<legend><strong>信息检索</strong></legend>
<div class="user-query">
	<dl>
	    <dt style="height:26px;width:70px;">项目编号</dt>
		<dd><input class="easyui-textbox"  type="text" id="item_code_city" value="" style="height:26px;width:100px;"/></dd> 
		<dt style="height:26px;width:60px;">项目名称</dt>
		<dd><input class="easyui-textbox"  type="text" id="item_name_city" value="" style="height:26px;width:135px;"/></dd>
<%-- 		<dt style="height:26px;width:110px;padding-left:10px;">是否关联市医保</dt>					
		<dd><select class="easyui-combobox" id="is_bind_city" data-options="height:26,width:100,panelHeight:'auto'">
		<option value="">全部</option>
		<option value="Y">是</option>
		<option value="N">否</option>
		</select></dd> --%>
		<dd><a href="javascript:getChargItemList_city();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
     </dl>
</div>
</fieldset>
<div style="width:60%;float: left;" >
	<fieldset>
		<legend><strong>价表</strong></legend>
		<table id="chargItemList_city"  class="easyui-datagrid"></table>
	</fieldset>
</div>
<div style="width:39%;float: right">
	<!-- 省医保 -->
	<fieldset>
	<legend><strong>已关联省医保项目列表</strong></legend>
		<table id="binded_city" style="height:240px;" ></table>
	</fieldset>
	<!-- 市医保 -->
	<fieldset>
	<legend><strong>已关联市医保项目列表</strong></legend>
	<table id="binded_city_shi" style="height:240px;" ></table>
	</fieldset>
</div>
<div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height:400,closed: true,cache: false,modal: true,top:50"></div>
<div id="clinic_prov" class="easyui-dialog" data-options="width: 1000,height: 500,closed: true,cache: false,modal: true"></div>
<div id="clinic_city" class="easyui-dialog" data-options="width: 1000,height: 500,closed: true,cache: false,modal: true"></div>
</body>
</html>