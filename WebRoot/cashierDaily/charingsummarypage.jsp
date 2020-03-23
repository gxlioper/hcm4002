<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财务部门日结</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cashierDaily/charingsummarypage.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
      	 <div class="easyui-layout" fit="true">
      	 	<div data-options="region:'north'" style="height:70px;" border="false">
      	 				<fieldset>
    						<legend><strong>信息检索</strong></legend>
    						<div class="user-query">
								<dl>
									<dt style="width:70px;">日结日期：</dt>
									<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dt style="height:26px;width:20px;">至</dt>
                     				<dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dt style="width:70px;">收费员：</dt>
                     				<dd><select id="user_id" class="easyui-combobox" data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
                     				<dt style="width:70px;">日结状态:</dt>
                     				<dd><select id="daily_status" class="easyui-combobox" data-options="height:26,width:80,panelHeight:'auto'">
                     					<option value="">全部</option>
                     					<option value="0">未日结</option>
                     					<option value="1">已日结</option>
                     				</select></dd>
                     				<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
								</dl>
							</div>
    					</fieldset>
      	 	</div>
      	 	<div data-options="region:'west',title:'收费金额汇总'" style="width:35%;">
      	 		<table id="huizonglist"></table>
      	 	</div>
      	 	<div data-options="region:'center'">
      	 		<div id="aa" class="easyui-accordion" fit="true">
		 			<div title="个人收费明细列表">
		            	<table id="gcharginglist"></table>
		            </div>
		            <div title="团体收费明细列表">
		            	<table id="tcharginglist"></table>
		            </div>
		            <div title="已开发票信息列表">
		            	<table id="invoicelist"></table>
		            </div>
	            </div>
      	 	</div>
      	 </div>
</body>
</html>