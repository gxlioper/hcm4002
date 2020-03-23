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
<script type="text/javascript" src="<%=request.getContextPath()%>/scaleManager/scale_answer_img.js?randomId=<%=Math.random()%>"></script>
<style>
.bkground {
	background-color:#eef
}
</style>
<script type="text/javascript">
$(function() {
	
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
	getcustomerInfo();
	//回车查询
	$("#exam_num").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			chaxun();
		}
	});
})
</script>
</head>
<body>
<div class="formdiv">
	<div class="formdiv fomr3" style="padding-top:20px;">
	<fieldset style="margin:5px;padding-right:0;">
 		<legend><strong>信息检索</strong></legend>
		<div class="user-query">
			<dl>
				<dd>体检号：<input type="text" style="width:85px" id="exam_num" value="<s:property value="exam_num"/>" class="textinput" /></dd>
				<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:50px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
				<dt style="width:60px;">档案号：</dt><dd id="arch_num"></dd>
				<dt style="width:60px;">姓名：</dt><dd id="user_name"></dd>
				<dt style="width:60px;">性别：</dt><dd id="sex"></dd>
				<dt style="width:60px;">年龄：</dt><dd id="age"></dd>
				<dt style="width:60px;">单位：</dt><dd id="company"></dd>
				<dt style="width:80px;">人员类型：</dt><dd id="customer_type"></dt>
				<dt style="width:80px;">体检套餐：</dt><dd id="set_name"></dd>
			</dl>
		</div>
	</fieldset>
	<fieldset style="margin:5px;padding-right:0;" id="answer_fs">
 		<legend><strong>问卷答题</strong></legend>
		<div class="user-query">
			<br/>
			<input type="hidden" id="question_num" value="<s:property value="questionOptionList.size"/>"/>
		<s:iterator value="questionOptionList" status="st1">
		  <img src="<%=request.getContextPath()%>/images/moca/<s:property value="question.imgName"/>" />
		  <dl class="bkground">
			  	<input type="hidden" id="question<s:property value="#st1.index"/>" value="<s:property value="question.id"/>"/>
		  	<dd>
			  <s:iterator value="options" status="st2">
				  <input type="radio" <s:if test="selected == 1">checked</s:if> id='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>' optionId='<s:property value="id"/>' name='question<s:property value="#st1.index"/>' optionValue='<s:property value="value"/>'/>
				  <!-- <s:if test="options.size == 1">checked</s:if> -->
				  <label for='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>'>&nbsp;&nbsp;<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				  <!-- style="width: 150px;display:inline-block;" -->
			  </s:iterator>
		  	</dd>
		  </dl>
		  <dl class="bkground"></dl>
		</s:iterator>
		<div class="dialog-button-box" style="position: inherit">
		<div class="inner-button-box">
	    	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:submit();">提交</a>
		</div>
		</div>
		</div>
		</fieldset>
	</div>
</div>
<input type="hidden" id="scale_code" value="<s:property value="scale_code"/>"/>
</body>
</html>