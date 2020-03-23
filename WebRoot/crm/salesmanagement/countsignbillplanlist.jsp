<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>签单计划中心</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/countsignbillplanlist.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
</head>
<body>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>申请人：&nbsp;&nbsp;<select id="serect_creater">
					</select></dd>
					<dt style="width:60px;">开始日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="signstartTime"    style="height:23px;width:150px;" data-options="prompt:'请选择开始查询日期'"/>
					</dd>
					<dt style="width:70px;">结束日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="signendTime"    style="height:23px;width:150px;" data-options="prompt:'请选择结束查询日期'"/>
					</dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getCountSignBillPlanList();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>签单计划统计列表</strong></legend>
<table id="sign_bill_span_count_list"> 
</table>
</fieldset>
<div id="dlg-edit"></div>
<div id="dlg-show"></div>
</body>
</html>