<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="nameEn"/>(<s:property value="nameCn"/>)</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<style>
.bkground {
	background-color:#eef
}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<div class="formdiv">
	<div class="formdiv fomr3" style="padding-top:20px;">
	<s:if test="scale_code == 'QS09'">
		<fieldset style="margin:5px;padding-right:0;">
			<legend><strong>结果评估</strong></legend>
			<div class="user-query">
				<dl class="bkground">
					<dd style="width:25%;">
					</dd>
					<dd>
						<s:property value="appraise"/>
					</dd>
				</dl>
			</div>
		</fieldset>
	</s:if>
	<fieldset style="margin:5px;padding-right:0;">
 		<legend><strong><s:property value="nameEn"/>(<s:property value="nameCn"/>)</strong></legend>
		<div class="user-query">
			<input type="hidden" id="question_num" value="<s:property value="questionOptionList.size"/>"/>
			<dl><s:property value="content"/></dl>
		<s:iterator value="questionOptionList" status="st1">
		  <dl class="<s:if test="#st1.odd">bkground</s:if>">
		  	<dd style="width:40%;">
		  		<s:property value="question.name"/>
			  	<input type="hidden" id="question<s:property value="#st1.index"/>" value="<s:property value="question.id"/>"/>
		  	</dd>
		  	<dd>
			  <s:iterator value="options" status="st2">
			  		<s:if test="selected == 1">
						<input type="radio" checked id='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>' optionId='<s:property value="id"/>' name='question<s:property value="#st1.index"/>' optionValue='<s:property value="value"/>'/>
						&nbsp;&nbsp;<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:else>
						<input type="radio" disabled id='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>' optionId='<s:property value="id"/>' name='question<s:property value="#st1.index"/>' optionValue='<s:property value="value"/>'/>
						<span style="color: gray;">&nbsp;&nbsp;<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</s:else>
			  </s:iterator>
		  	</dd>
		  </dl>
		</s:iterator>
		</div>
		</fieldset>
		<fieldset style="margin:5px;padding-right:0;">
				<legend><strong>结果总计</strong></legend>
				<div class="user-query" style="font-size: 20px;">
					<dl><dt style="width: 50%">总分：</dt><dd><s:property value="score"/>分（满分<s:property value="full_score"/>分）</dd><dt style="width: 12%">医生：</dt><dd><s:property value="creater"/></dd></dl>
				</div>
		</fieldset>
	</div>
</div>
<input type="hidden" id="scale_code" value="<s:property value="scale_code"/>"/>
</body>
</html>