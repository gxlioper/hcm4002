<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>科室采样</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/depprintsample.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	var dahname ='<s:text name="dahname"/>';  
	var tjhname ='<s:text name="tjhname"/>'; 
	$("input").attr("maxlength","20");
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
});
</script>
<style type="text/css">
.jre{}
</style>
</head>
<body>
<input type="hidden" id="dep_id" value="<s:property value="#session.username.dep_id"/>"/>
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:20%;">
		<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px;ime-mode: disabled;" id="ser_num" name="ser_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:50px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
						</dl>
					</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/><input type="hidden" id="exam_num"/></dt><dt id="user_name"></dt></dl>
						<dl><dt>性别：</dt><dt id="sex"></dt></dl>
						<dl><dt>年龄：</dt><dt id="age"></dt></dl>
						<dl><dt>单位：</dt><dt id="company"></dt></dl>
						<dl><dt>人员类型：</dt><dt id="customer_type"></dt></dl>
						<dl><dt>体检套餐：</dt><dt id="set_name"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center'">
    	<table id="cyitemList">
      	</table>
	</div>
</div>
<div id="toolbar">
		<div style="margin-left:140px;">
		<a href="javascript:void(0)" id="save_exam" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_sampling_barcode();">确定保存</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:send_application();">补发申请</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:print_barcode();">打印条码</a>
		</div>	
</div>
<div id="dlg-print" class="easyui-dialog" data-options="width: 400,height: 208,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>