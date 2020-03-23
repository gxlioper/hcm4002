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
<title>清除结果</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/updateExamstatusN/updateExamstatusN.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
	
	$("input").attr("maxlength","20");
	$("#ser_num").focus(function(){
        $(this).select();
	});
});
</script>
<style type="text/css">
</style>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:20%;">
		<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px" id="ser_num" name="ser_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
						</dl>
					</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt style="width: 30%;">姓名：<input type="hidden" name="exam_id" id="exam_id"/><input type="hidden" name="status" id="status"/></dt><dt style="width: 70%;" id="user_name"></dt></dl>
						<dl><dt style="width: 30%;">性别：</dt><dt style="width: 70%;" id="sex"></dt></dl>
						<dl><dt style="width: 30%;">年龄：</dt><dt style="width: 70%;" id="age"></dt></dl>
						<dl><dt style="width: 30%;">单位：</dt><dt style="width: 70%;" id="company"></dt></dl>
						<dl><dt style="width: 30%;">人员类型：</dt><dt style="width: 70%;" id="customer_type"></dt></dl>
						<dl><dt style="width: 30%;">体检套餐：</dt><dt style="width: 70%;" id="set_name"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center'">
    	<table id="cyitemList">
      	</table>
	</div>
</div>
<div id="toolbar">
		<div style="margin-left:20px;">
		<a href="javascript:void(0)"  class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save();">取消登记</a>
		</div>	
</div>
</body>
</html>