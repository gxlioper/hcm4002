<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问卷结果展示</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/plugins/easyui.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/icon.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scaleManager/quest_show.js?randomId=<%=Math.random()%>"></script>
<style type="">
	#xiaLa .panel-title{
		font-size: 13px;
	}
	#xiaLa .accordion-collapse{
		width: 18px;
		display: none;
	}
	
	#title_biao{
		margin-left:22%;
	}
	
	.ulOne{
		margin: 5px 10px 5px 5px;
	}
	.ulOne a li{
		height: 26px;
		line-height: 26px;
		padding: 5px 2px;
		position: relative;
		vertical-align: middle;
		border-bottom: 1px solid #fff;
		background-image: none;
		text-align: left;
		background-color: #F7F4F0;
	}
	.ulOne a{
		color: #000;
		text-decoration: none;
		text-align: left;
		font-size: 12px;
	}
	.ulOne a:hover { 
		font-size: 12px; 
		color:#0b90da;
	}
	#twoMenuList{
		padding: 5px;
	}
	.easyui-fluid{
		margin-left: 10px;
	}
</style>
<script type="text/javascript">
$(function() {
	//加载右侧菜单信息
	chaxun();
	//回车查询
	$("#exam_num").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			chaxun();
		}
	});
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
})
</script>
</head>
<body>
	<fieldset style="margin:5px;padding-right:0;">
 		<legend><strong>信息检索</strong></legend>
		<div class="user-query">
			<dl>
				<dd>体检号：<input type="text" style="width:85px" id="exam_num" value="<s:property value="exam_num"/>" class="textinput"/></dd>
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
	<fieldset style="margin:5px;padding-right:0;height: 520px;">
 		<legend><strong>问卷展示</strong></legend>
		<div class="easyui-layout" fit="true" style="margin:5px;">
		    <div data-options="region:'west',split:true" title="<a id='title_biao' onClick='javaScript:showRoot()'>问卷类型</a>" style="width:165px;">
		    	<div id="xiaLa"></div>
			</div>
		    <div data-options="region:'center',title:''">
		    	<p> 
					<h3 style="margin:16px 0px 15px 13px;">当前位置    >>  
				 		<span id="titleText" style="color:red;"></span>
					</h3>
				</p>
				<div id="questionTitle"></div>
		    </div>
		</div>
	</fieldset>
<input type="hidden" id="quest_code" value="<s:property value="quest_code"/>"/>
</body>
</html>