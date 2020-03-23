<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>采样室</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
});
</script>
<style type="text/css">
.jre{}
.jre1{}
</style>
</head>
<body>
<input type="hidden" id="dep_id" value="<s:property value="#session.username.dep_id"/>"/>
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>"/>
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:25%;background-color: #F4F4F4;">
		<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query" style="font-size:15px;">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px;ime-mode: disabled;" id="ser_num" name="ser_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:50px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
						</dl>
						<dl>
							<dd>条码号：<input type="text" style="width:85px" id="barcode" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:50px;height:27px;" onclick="javascript:chaxun_barcode();">查询</a></dd>
						</dl>
					</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
    				<div style="position: absolute;margin-left: 60%;"><img id="exampic" name="exampic" style="height:120px;width:100px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /></div>
					<div class="user-query" style="font-size:15px;">
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/><input type="hidden" id="exam_num"/></dt><dt id="user_name"></dt></dl>
						<dl><dt>性别：</dt><dt id="sex"></dt></dl>
						<dl><dt>年龄：</dt><dt id="age"></dt></dl>
						<dl><dt>人员类型：</dt><dt id="customer_type"></dt></dl>
						<dl><dt>电话：</dt><dd id="phone"></dd></dl>
						<dl><dt>体检类型：</dt><dt id="exam_types"></dt></dl>
						<dl><dt>体检套餐：</dt><dd id="set_name"></dd></dl>
						<dl><dt>单位：</dt><dt id="company"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center'" onkeydown="keyDown(event)">
    	<table id="cyitemList">
      	</table>
	</div>
</div>
<div id="toolbar">
		<div style="float:left;margin-left:20px">
		预印条码号：<input type="text" style="width:150px" id="ser_barcode" name="ser_barcode" class="textinput"/>
		<font id="erro" style="color:red;"></font>
		</div>
		<div style="margin-left:440px;">
		<a href="javascript:void(0)" id="save_exam" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_sampling_barcode();">确定保存</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:send_application();">补发申请</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:print_barcode();">打印条码</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:merge_sample();">合并样本</a>
		<a href="javascript:void(0)" id="" class="easyui-linkbutton c6" style="width:80px;margin-left:10px;" onclick="javascript:split_up_sample();">拆分样本</a>
		</div>	
</div>
<div id="dlg-print" class="easyui-dialog" data-options="width: 400,height: 208,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-hbyb" class="easyui-dialog"  data-options="width: 300,height: 400,closed: true,cache: false,modal: true,title:'强制合并样本'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<div style="margin-left:20px;">请选择合并目标样本:</div>
			<div style="height:260px;width:260px;margin-left:20px;">
				<table id="qzhbyb_list"></table>
			</div>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_hebin_sample();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-hbyb').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-cfyb" class="easyui-dialog"  data-options="width: 400,height: 450,closed: true,cache: false,modal: true,title:'拆分样本'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<div style="margin-left:20px;">请选择需要拆分的项目:</div>
			<div style="height:310px;width:360px;margin-left:20px;">
				<table id="cfyb_list"></table>
			</div>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_split_sample();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-cfyb').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/samplingDeparment.js?randomId=<%=Math.random()%>"></script>