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
<title>疾病知识库管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DiseaseKnowloedgeManage/DiseaseKnowloedge.js?randomId=<%=Math.random()%>"></script>
</head>
<body class="easyui-layout" >
<input type="hidden" id="dept_id" value="">
<input type="hidden" id="IS_DISEASE_KNOW_TYPE" value="<s:property value="IS_DISEASE_KNOW_TYPE" />">
	<div  data-options="region:'west',title:'科室',split:true" style="width:15%;">
		<div id='some_tree' data-options="animate:true"></div>
	</div>  
	<div  data-options="region:'center'">
			<fieldset style="margin:5px;padding-right:20px;padding-left:20px;">
			<legend><strong>疾病信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:80px;">疾病类型：</dt>
					<dd><select id="disease_type_s"></select></dd>
					<dt style="width:80px;">疾病名称：</dt>
					<dd><input id="disease_name_s" class="easyui-textbox" data-options="height:27,prompt:'疾病名称'" size="26"/></dd>
					<dt style="width:80px;">疾病级别：</dt>
					<dd><input id="disease_level_z" class="easyui-combobox" data-options="prompt:'疾病级别'" size="26"/></dd>
					<dd><input id="qiyong" checked="checked" value="Y" type="checkbox" onclick="searchFun();">启用</dd>
					<dd><input id="tingyong" value="N" type="checkbox" onclick="searchFun();">停用</dd>
					<dd><a href="javascript:searchFun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:70px;">查询</a></dd>
					<dd><a href="javascript:cleanFun();" class="easyui-linkbutton" style="width:70px;">清空</a></dd>
				</dl>
			</div>
			</fieldset>
			  <div id="centerLeft" style="height:98%;">
				<fieldset style="margin-right:0px;padding-right:10px;height:95%;Float:left;width:55%;">
					<legend><strong>疾病信息列表</strong></legend>
					   <div id="diseaseKnowloedgeList"> </div>
				</fieldset>
				<fieldset style="margin-left:0px;padding-right:10px;height:95%;Float:left;width:41%;">
					<legend><strong>健康建议</strong></legend>
					   <div id="suggestionList"> </div> 
				</fieldset>
			 </div>
		 
		<div id="dlg-look" class="easyui-dialog" data-options="width: 830,height: 520,closed: true,cache: false,modal: true,top:5"></div>
		<div id="dlg-add" class="easyui-dialog" data-options="width: 800,height: 340,closed: true,cache: false,modal: true,top:5"></div>
		<div id="dlg-gxwh" class="easyui-dialog" data-options="width: 800,height: 500,closed: true,cache: false,modal: true,top:5"></div>
	 </div>	
</body>
</html>