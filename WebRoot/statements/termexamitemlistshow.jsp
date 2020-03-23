<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/sxtCutPic.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termexamitemlistshow.js?randomId=<%=Math.random()%>"></script>

<body>
<input type="hidden" id="acc_num" value="<s:property value="model.acc_num"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
  <fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人信息</strong>
	</legend>	
	     <div class="user-query">
				<dl>
					<dd style="height:20px;width:80px;">姓名 </dd>
					<dd style="height:20px;width:100px;" ><s:property value="examInfoUser.user_name"/></dd>
					<dd style="height:20px;width:100px;">证件号 </dd>
					<dd style="height:20px;width:280px;" ><s:property value="examInfoUser.id_num"/></dd>
					<dd style="height:20px;width:80px;">性别 </dd>
					<dd style="height:20px;width:40px;" ><s:property value="examInfoUser.sex"/></dd>
				</dl>
				<dl>
					<dd style="height:20px;width:80px;">年龄 </dd>
					<dd style="height:20px;width:100px;" ><s:property value="examInfoUser.age"/></dd>
					<dd style="height:20px;width:100px;" >单位</dd>
					<dd style="height:20px;width:400px;" ><s:property value="examInfoUser.company"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input type="button" name="Submit2" onclick="javascript:window.close();" value="关闭窗口" /></dd>
				</dl>
			</div>
	</fieldset>
	<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>项目结算明细</strong>
	</legend>
			&nbsp;&nbsp;&nbsp;&nbsp;结算项目数：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>项
			&nbsp;&nbsp;项目金额：<font id="tyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;结算金额：<font id="gyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人付费金额：<font id="gsjje" style="color:blue;font-weight:bold;font-style:italic;">0</font>元
			<div id="djtGselectItemcharginglist"></div>
	</fieldset>
  </body>
