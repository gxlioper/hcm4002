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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scaleManager/scale_answer.js"></script>
<style>
.bkground {
	background-color:#eef
}
</style>
</head>
<body>
<div class="formdiv">
	<div class="formdiv fomr3" style="padding-top:20px;">
	<fieldset style="margin:5px;padding-right:0;">
 		<legend><strong><s:property value="nameEn"/>(<s:property value="nameCn"/>)</strong></legend>
		<div class="user-query">
			<dl><s:property value="content"/></dl>
			<input type="hidden" id="question_num" value="<s:property value="questionOptionList.size"/>"/>
		<s:iterator value="questionOptionList" status="st1">
		  <s:if test="question.imgName != ''"><img src="<%=request.getContextPath()%>/images/moca/<s:property value="question.imgName"/>" /></s:if>
		  <dl class="<s:if test="#st1.odd || question.imgName != ''">bkground</s:if>">
		  	<dd style="width:50%;">
		  		<s:if test="question.imgName == ''"><s:property value="question.name"/></s:if>
		  		<!--<s:else></s:else>-->
			  	<input type="hidden" id="question<s:property value="#st1.index"/>" value="<s:property value="question.id"/>"/>
		  	</dd>
		  	<dd>
			  <s:iterator value="options" status="st2">
				  <input type="radio" <s:if test="selected == 1">checked</s:if> id='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>' optionId='<s:property value="id"/>' name='question<s:property value="#st1.index"/>' optionValue='<s:property value="value"/>'/>
				  <!-- <s:if test="options.size == 1">checked</s:if> -->
				  <label for='<s:property value="#st1.index"/>_<s:property value="#st2.index"/>'>&nbsp;&nbsp;<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;</label>
				  <!-- style="width: 150px;display:inline-block;" -->
			  </s:iterator>
		  	</dd>
		  </dl>
		</s:iterator>
		<div class="dialog-button-box" style="position: inherit">
		<div class="inner-button-box">
	    	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:submit();">提交</a>
		</div>
		</div>
		</div>
		</fieldset>
		<s:if test="!appraiseList.empty">
			<fieldset style="margin:5px;padding-right:0;">
				<legend><strong>评估标准</strong></legend>
				<div class="user-query">
			<s:iterator value="appraiseList" status="st">
				<dl class="<s:if test="#st.even">bkground</s:if>">
					<dd style="width:5%;">
						<s:property value="fromPoint"/>-<s:property value="toPoint"/>分
					</dd>
					<dd>
						<s:property value="content"/>
					</dd>
				</dl>
			</s:iterator>
				</div>
			</fieldset>
		</s:if>
	</div>
</div>
<input type="hidden" id="scale_code" value="<s:property value="scale_code"/>"/>
</body>
</html>