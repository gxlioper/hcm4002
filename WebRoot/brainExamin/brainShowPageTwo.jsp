<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健康体检</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/plugins/easyui.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/icon.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/brainExamin/brainShowPageTwo.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/brainExamin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/brainExamin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<style type="">
	#xiaLa .panel-title{
		font-size: 13px;
		margin-top: 8px;
	}
	#xiaLa .accordion-collapse{
		width: 18px;
		display: none;
	}
	
	#title_biao{
		margin-left:10%;
	}
	
	.ulOne{
		margin: 5px 5px 5px 5px;
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
	.panel-header{
		height: 37px;
	}
	.panel-title {
		margin-top: 7px;
	}
	.easyui-fluid{
		margin-left: 5px;
		margin-top: 4px;
		float: left;
	}
	.easyui-layout .panel-title{
		line-height: 6px;
		font-size: 14px;
	}
	.panel-header{
		height: 20px;
	}
</style>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
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

<%-- <fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
	
</fieldset> --%>

 <fieldset style="margin:5px; padding-right:0;height: 820px;">	
 	<fieldset style="margin:0px 15px 0px 5px ;padding-right:0;">
		<legend><strong>信息检索</strong>
			<div style="display: none;">
				<input id="dep_id" type="text" value="<s:property value="dep_id"/>"/>
				<input id="item_id" type="text" value=""/>
				<input id="red_id" type="text" value=""/>
				<input id="set_num" type="text" value=""/>
	    		<input id="start_quest" type="text" value=""/>
	    		<input id="first_maoHeight" type="text" value=""/>
	    		<input id="quest_sub_code" type="text" value="<s:property value="quest_sub_code"/>"/>
	    	</div>
		</legend>
		<div class="user-query">
			<dl>
				<dd><s:text name="tjhname"/>：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width:102px" id="exam_num" value="<s:property value="exam_num"/>" class="textinput"/></dd>
				<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:50px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
				<dt style="width:60px;"><s:text name="dahname"/>：</dt><dd id="arch_num"></dd>
				<dt style="width:60px;">姓名：</dt><dd id="user_name"></dd>
				<dt style="width:60px;">性别：</dt><dd id="sex"></dd>
				<dt style="width:60px;">年龄：</dt><dd id="age"></dd>
				<dt style="width:60px;">单位：</dt><dd id="company"></dd>
				<dt style="width:80px;">人员类型：</dt><dd id="customer_type"></dd>
				<dt style="width:80px;">体检套餐：</dt><dd id="set_name"></dd>
				<dd><a href="javascript:saveEndRecord()" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; border-radius: 5px; margin-top: 10px;">保存</a></dd>
			</dl>
		</div>
		
		<!--  <div class="user-query">
			<dl>
				<dd>名称检索：<input class="easyui-textbox"  type="text" id="quest_name"  value=""  style="width:110px;height:27px;"/></dd>
				<dd><a href="javascript:queryQuestNameFixed();"  class="easyui-linkbutton c6" style="width:50px;height:27px;">查询</a></dd>
				<dd><a href="javascript:queryQuestNameEmpty();"  class="easyui-linkbutton" style="width:50px;height:27px;">清空</a></dd>
			</dl>
		 </div> -->
		
	</fieldset>
 	
 	<fieldset style="margin:5px ;padding-right:0; height: 720px; border: 0px;">
		<div class="easyui-layout" fit="true" style="margin: 1px 0px 1px -10px;">
		    <div id="center"  data-options="region:'center',title:''">
				 
			 	<div id="index_text" style=" width: 100%; letter-spacing:2px;">
				 	<p><h2 style="text-align: center; margin-top: 134px;"><span id="titleText2"></span></h2></p>
				 	<p style="font-size: 18px; margin: 40px 0px 0px 75px;line-height:40px">
				 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据中华人民共和国《统计法》第三章第十五条规定，
						“属于私人、家庭的单项调查资料，非经本人同意，不得外泄”。</p>
				 </div>
					 
				 <div id="questionTitle" style="margin-left: 15px;" ></div>
				
				 
				 <br style="clear: both;"/>
				 <div id="end_msg" style=" width: 100%; letter-spacing:2px; margin: 10px 0px 20px 0px; text-align: center; display: none;">
				 	<a href="javascript:saveEndRecord()" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; border-radius: 5px; margin-top: 10px;">保存</a>
				 </div>
				 	
		    </div>
		</div>
	</fieldset>
</fieldset>
</body>
</html>