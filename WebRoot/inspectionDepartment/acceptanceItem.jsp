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
<title>核收</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/acceptanceItem.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
	$('#ser_num').css('ime-mode','disabled');
	$('#ser_num').focus();
})
</script>

<style type="text/css">
</style>
</head>
<body>
<input type="hidden" id="report_print_type" value="<s:property value="model.report_print_type"/>">
<input type="hidden" id="zyb_report_print_type" value="<s:property value="model.zyb_report_print_type"/>">
<div class="easyui-layout" fit="true">
	<div data-options="region:'west'" style="width:40%;">
		<table id="item_result"></table>
	</div>
    <div data-options="region:'center'">
    	<div class="easyui-layout" data-options="fit:true" border="false" style="margin-left:10px;">
    		<div data-options="region:'north'" border="false" style="height:94px;">
    			<fieldset>
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px;ime-mode: disabled;" id="ser_num" name="ser_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;height:27px;" onclick="javascript:reporbaogao1();">普通报告预览</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;height:27px;" onclick="javascript:reporbaogao2();">职业病报告预览</a></dd>
							<dd><a id="hs_button" href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;display:none;" onclick="javascript:f_heshou(1);">核收</a>
	    					<a id="qx_button" href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;display:none;" onclick="javascript:f_heshou(0);">取消核收</a></dd>
							<dt style="width:70px;margin-left:0px;">检查状态：</dt><dt style="width:40px;" id="exam_status"></dt>
							<dt style="width:70px;">总检医生：</dt><dt id="final_doctor" style="width:60px;font-size: 16px;font-weight: bold;"></dt>
							<dt style="width:70px;">审核时间：</dt><dt id="check_time"></dt>
						</dl>
						<dl>
							<dt style="width:50px;">姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name" style="width:70px;"></dt>
							<dt style="width:50px;">性别：</dt><dt id="sex" style="width:30px;"></dt>
							<dt style="width:50px;">年龄：</dt><dt id="age" style="width:30px;"></dt>
							<dt style="width:70px;">体检套餐：</dt><dt id="set_name"></dt>
							<dt style="width:70px;">体检日期：</dt><dt id="join_date"></dt>
							<dt style="width:70px;">单位：</dt><dt id="company"></dt>
						</dl>
					</div>
 				</fieldset>
			</div>
			<div data-options="region:'center'" style="position:relative">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        			<div title="总检结论" style="padding:5px;" >
        				<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 100%;font-size:14px;" id="zongjianjielun"></textarea>
        			</div>
       				<div title="总检建议" style="padding:5px;">
       					<table id="exam_disease">
	      				</table>
       				</div>
       				<div title="导检单" style="padding:5px;">
       					<div id="disease_tijianbaogao"  style="vertical-align: middle; text-align: center;"></div>
       				</div>
       			</div>
       			<div style="width: 120px; height: 20px; position:absolute;top:0px;right:100px;" onclick="javascript:exam_suggestion();">
       				<span class="easyui-linkbutton c6" style="width:120px;height:27px;" >报告预览意见</span>
       				<div id="dlg-report" class="easyui-dialog" data-options="width: 850,height: 552,closed: true,cache: false,modal: true,top:50">
       				</div>
			   </div>
			   <div style="width: 80px; height: 20px; position:absolute;top:0px;right:250px;" onclick="javascript:reportReject();">
       				<span class="easyui-linkbutton c6" style="width:80px;height:27px;" >报告驳回</span>
       				<div id="dlg-reject" class="easyui-dialog" data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50">
       				</div>
			   </div>
    	</div>
	</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 900,height: 450,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>