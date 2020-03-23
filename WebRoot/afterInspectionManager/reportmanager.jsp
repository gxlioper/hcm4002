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
<title>报告列表</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/afterInspectionManager/reportmanager.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
.ser_width{width:40px;}
</style>
</head>
<body>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>扫码确认报告已整理</strong></legend>
			<div class="user-query">
				<dl>
					<dd><s:text name="tjhname"/>：&nbsp;&nbsp;<input maxlength="20" type="text" id="ser_exam_num" name="ser_exam_num"  class="textinput"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:confirm_print()">确认整理报告</a></dd>
					<dt  class="autoWidth"><span  id="message_examinfo" class="red"></span></dt>
				</dl>
			</div>
 </fieldset>

<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:60px;" class="ser_width"><s:text name="tjhname"/>：</dt>
					<dd><input style="width:100px;" maxlength="20" type="text" id="ser_exam_num_c" name="ser_exam_num_c"  class="textinput"/></dd>
					<!--<dt style="width:60px;" class="ser_width"><s:text name="dahname"/>：</dt>
					<dd><input style="width:100px;" maxlength="20" type="text" id="ser_arch_num" name="ser_arch_num"  class="textinput"/></dd>-->
					
					<dt style="width:80px;" class="ser_width">单位：</dt>
					<dd><input type="hidden" id="company_id"/><input class="textinput"  type="text" id="com_Name"/>
					  <div id="com_name_list_div" style="display:none;margin-left:220px;position:fixed;max-height:300px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="width:100px;">邮寄/自取日期：</dt>
					<dd><input style="width:105px;height:26px" type="text" id="create_time" class="easyui-datebox" /> 至</dd>  
					<dd><input style="width:105px;height:26px" type="text" id="create_time1" class="easyui-datebox" /></dd>  
                    <dt style="width:80px;" class="ser_width">打印报告：</dt>
                    <dd><select class="easyui-combobox" id="is_report_print" data-options="height:26,width:80,panelHeight:'auto',editable:false">
						<option value="">全部</option>
						<option value="N">未打印</option>
						<option value="Y">已打印</option>
					</select></dd>
					<dt style="width:80px;" class="ser_width">整理日期：</dt>
					<dd><input style="width:105px;height:26px" type="text" id="report_tidy_time" name="report_tidy_time" class="easyui-datebox" /> 至</dd>
					<dd><input style="width:105px;height:26px" type="text" id="report_tidy_time1" name="report_tidy_time1" class="easyui-datebox" /></dd>
					
				</dl>
				<dl>
					<dt style="width:60px;" class="ser_width">姓名：</dt>
					<dd><input style="width:100px;" maxlength="20" type="text" id="ser_name" name="ser_name" class="textinput"/></dd>
					<!--<dt  style="width:60px;" class="ser_width">电话：</dt>
					<dd><input style="width:100px;" maxlength="11" type="text" id="ser_phone" name="ser_phone" class="textinput"/></dd>-->
					<dt style="width:80px;" class="ser_width">身份证号：</dt>
					<dd><input style="width:140px;" maxlength="18" type="text" id="ser_id_num" name="ser_id_num" class="textinput"/></dd>
					
					
					<dt style="width:100px;" class="ser_width">体检日期：</dt>
					<dd><input style="width:105px;height:26px" type="text" id="join_date" name="join_date" class="easyui-datebox" /> 至</dd>
					<dd><input style="width:105px;height:26px" type="text" id="join_date1" name="join_date1" class="easyui-datebox" /></dd>
					
					<dt style="width:80px;" class="ser_width">报告领取：</dt>
					<dd><select class="easyui-combobox" id="ser_receive_type"
					data-options="height:26,width:80,panelHeight:'auto',editable:false">
						<option value="">全部</option>
						<option value="0">未邮寄,未自取</option>
						<option value="1">已邮寄</option>
						<option value="2">已自取</option>
					</select></dd>
					<dt style="width:80px;" class="ser_width">整理报告：</dt>
					<dd><select class="easyui-combobox" id="is_report_tidy" data-options="height:26,width:80,panelHeight:'auto',editable:false">
						<option value="">全部</option>
						<option value="N">未整理</option>
						<option value="Y" selected="true">已整理</option>
					</select></dd>  
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c3" style="width:80px;height:27px;" onclick="javascript:delAll();">清空</a>
					</dd>
					
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
<table id="examinfolist"> 
</table>
</fieldset>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 780,height: 400,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-edit-thems" class="easyui-dialog" data-options="width: 780,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 600,height: 350,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>