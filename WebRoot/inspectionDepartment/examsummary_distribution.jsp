<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主检主动分配页面</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/examsummary_distribution.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
</script>
</head>
<body>
<div id="tt" class="easyui-tabs" fit="true">
<div title="按主检医生派发" style="">
	<div style="margin: 10px;">
		<div class="user-query">
			<dl>
				<dd>
					未获取的人数为：<font style="color: red;font-weight:bold;" id="wd_count">0</font>人，
					未主检的人数为：<font style="color: red;font-weight:bold;" id="wz_count">0</font>人。
				</dd>
				<dt style="height:26px;width:80px;">派发数量：</dt>
				<dd><input class="easyui-numberbox" type="text" id="final_can_count" value="10" style="height:26px;width:60px;"/></dd>
				<dd><a href="javascript:final_can_distribution();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">确认派发</a></dd>
				<dd><a href="javascript:getfinalCount();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">刷新</a></dd>
			</dl>
			
		</div>
		<fieldset style="margin:5px;padding-right:0;">
		<legend><strong>主检医生列表</strong></legend>
			<div class="user-query" id="doctor_list">
 			</div>
 		</fieldset>
	</div>
</div>
<div title="按未获取体检人员派发" style="">
	<table id="examinfoList"> 
	</table>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 450,height: 180,title:'派发主检医生',closed: true,cache: false,modal: true,top:50">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 60px;" >派发主检医生：</dd>
				<dd><select style="width:150px;height:26px;" id="exam_doctor"></select><strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_examdistribution();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
		</div>
	</div>
	</form>
</div>
</body>
</html>