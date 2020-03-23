<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息修改</title>
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/tree.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/admin.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>

</head>

<body>
<!--div class="head white">
<div>个人基本信息</div>
</div-->
<div class="head white"><div><span class="head-em"><span>个人基本信息</span></span></div></div>
<div class="formWidget">
	<div class="up"><div></div></div>
	<div class="box">
		<div class="form rechargeForm">
		<br/>
		<dl>
	      <dt>登录名称：</dt>
		  <dd><s:property value="username"/></dd>
		</dl>
		<!--dl>
	      <dt>用户类型：</dt>
		  <dd><s:property value="susertype"/></dd>
		</dl-->
		<!--dl>
	      <dt>证件号码：</dt>
		  <dd><s:property value="pid"/></dd>
		</dl-->
		<dl>
	      <dt>姓名：</dt>
		  <dd><s:property value="name"/></dd>
		</dl>
		<dl>
	      <dt>性别：</dt>
		  <dd><s:if test="sex==1">男</s:if>
		  <s:if test="sex==2">女</s:if>
		  </dd>
		</dl>
	
		<dl>
	      <dt>联系电话1：</dt>
		  <dd><s:property value="tel1"/></dd>
		</dl>
		<dl>
	      <dt>联系电话2：</dt>
		  <dd><s:property value="tel2"/></dd>
		</dl>
		<dl>
	      <dt>Email地址：</dt>
		  <dd><s:property value="email"/></dd>
		</dl>		

		</div>
	</div>
	<div class="down"><div></div></div>
</div>
</body>
</html>
