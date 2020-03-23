<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收费员日结</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cashierDaily/cashierdailyacc.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden" id="user_ids" value="<s:property value="model.userId"/>"/>
<div class="easyui-layout" border="false" fit="true">
	<div data-options="region:'north'" style="height:70px;">
		<fieldset>
    		<legend><strong>信息检索</strong></legend>
    		<div class="user-query">
				<dl>
					<dt style="width:70px;">日结日期：</dt>
					<dd><input class="easyui-datebox" id="daily_acc_date1" value="<s:property value="model.daily_acc_date1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="daily_acc_date2" value="<s:property value="model.daily_acc_date2"/>" style="width:100px;height:26px;"/></dd>
                    <dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
				</dl>
			</div>
    	</fieldset>
	</div>
    <div data-options="region:'center'" border="false">
      	<table id="dailyacclist"></table>
    </div>
</div>
<div id="dlg-sfmx" class="easyui-dialog"  data-options="width: 1300,height: 550,closed: true,cache: false,modal: true,title:'收费员日结收费明细'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
		<div style="height:450px;width:1260px;margin-left:20px;">
			<div class="easyui-layout" border="false" fit="true">
				<div data-options="region:'west'" title="收费类型列表" style="width:550px;">
					<table id = "dailyacclistclass"></table>
				</div>
				<div data-options="region:'center'" title="收费类型明细列表">
					<table id = "dailyacclistclassmx"></table>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-sfmx').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-fpmx" class="easyui-dialog"  data-options="width: 1000,height: 550,closed: true,cache: false,modal: true,title:'收费员日结发票明细'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
		<div style="height:450px;width:950px;margin-left:20px;">
			<div class="easyui-layout" border="false" fit="true">
				<div data-options="region:'center'" title="发票明细列表">
					<table id = "dailyaccinvoicelist"></table>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fpmx').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>