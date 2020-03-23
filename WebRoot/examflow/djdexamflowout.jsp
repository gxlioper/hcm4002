<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/djdexamflowout.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人</strong>
	</legend>
	<div class="user-query">
		    <dl>		  
			<dt>体检编号</dt>
			<dd>
				<input type="text" class="easyui-textbox" id="exam_num" style="height: 26px; width: 85px;ime-mode: disabled;" />
			</dd>
			</dl>
			<dl>
			<dt>说明</dt>			
			<dd>
				<input class="easyui-textbox" type="text" maxlength="50"  id="remark"
					style="height: 26px; width: 200px;"  value="<s:property value="model.remark" />"/>
					<!--a href="javascript:insertexam();" class="easyui-linkbutton" style="width:100px;">查询</a-->
				<a href="javascript:flowcustadd();" class="easyui-linkbutton c6" style="width:100px;">导检单签收</a>
				<a href="javascript:deletechargitemall();" class="easyui-linkbutton" style="width:100px;">清除表格</a>
			</dd>			
		    </dl>

	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员列表</strong>
	</legend>
			<div id="selectexamflowlist"></div>
	</div>
</fieldset>
</body>
</html>