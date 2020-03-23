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
<script type="text/javascript" src="<%=request.getContextPath()%>/cashierDaily/financefepacc.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden" id="user_ids" value="<s:property value="model.user_id"/>"/>
<div id="tt" class="easyui-tabs" fit="true">
	<div title="未日结列表" style="padding:5px;">
	   <div class="easyui-layout" border="false" fit="true">
		   	<div data-options="region:'north'" style="height:185px;">
		   		<table id="huizongjine"></table>
		   	</div>
	 		<div data-options="region:'center',title:'收费员日结总记录列表'">
	 			<table id="witemlist"></table>
	      	</div>
	      	<div data-options="region:'east',title:'收费员日结发票列表'" style="width:35%;">
	 			<table id="winvoicelist"></table>
	      	</div>
      </div>
      </div>
      <div title="已日结列表" style="padding:5px;">
      	 <div class="easyui-layout" border="false" fit="true">
      	 	<div data-options="region:'west',title:'财务部门日结总记录列表'" style="width:35%;" border="false">
      	 		<div class="easyui-layout" border="false" fit="true">
      	 			<div data-options="region:'north'" style="height:70px;">
      	 				<fieldset>
    						<legend><strong>信息检索</strong></legend>
    						<div class="user-query">
								<dl>
									<dt style="width:70px;">日结日期：</dt>
									<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dt style="height:26px;width:20px;">至</dt>
                     				<dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dd><a href="javascript:chaxun(1);"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
								</dl>
							</div>
    					</fieldset>
      	 			</div>
      	 			<div data-options="region:'center'" border="false">
      	 				<table id="finacclist"></table>
      	 			</div>
      	 		</div>
      	 	</div>
      	 	<div data-options="region:'center'" border="false">
      	 		<div class="easyui-layout" border="false" fit="true">
      	 			<div data-options="region:'center',title:'财务部门日结收费方式明细列表'" border="true">
      	 				<table id="waylist"></table>
      	 			</div>
      	 			<div data-options="region:'east',title:'收费员日结发票列表'" style="width:50%;" border="true">
      	 				<table id="yinvoicelist"></table>
      	 			</div>
      	 			<div data-options="region:'south',title:'收费员日结总记录列表'" style="height:60%;" border="true">
      	 				<table id="dailylist"></table>
      	 			</div>
      	 		</div>
      	 	</div>
      	 </div>
      </div>
  </div>
</body>
</html>