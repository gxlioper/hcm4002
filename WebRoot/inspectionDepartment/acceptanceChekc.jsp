<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>体检花名册</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/acceptanceCheck.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
})
</script>
<style type="text/css">
.taw{width:70px;}
</style>
</head>
<body style="height:100%;">

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:70px;"><input id="ck_exam_num"  type="checkbox"/><s:text name="tjhname"/></dt>
					<dd><input type="text" style="ime-mode: disabled;" id="exam_num" class="textinput"/></dd>
					<dt style="width:50px;"><input id="ck_user_name" type="checkbox"/>姓名</dt>
					<dd><input type="text" id="user_name" class="textinput"/></dd>
					<dt style="width:80px;"><input id="ck_join_date" type="checkbox"/>体检日期</dt>
					<dd><input type="text" id="s_join_date" class="easyui-datebox" data-options="height:26,width:100"/></dd>
					<dd style="width:30px;">至</dd>
					<dd><input type="text" id="e_join_date" class="easyui-datebox" data-options="height:26,width:100"/></dd>
					<dt style="width:70px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select id="exam_type" class="easyui-combobox" data-options="height:26,width:140,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
						</select></dd>
					<dt style="width:42px;"><input id="ck_sex" type="checkbox"/>性别</dt>
					<dd><select id="sex" class="easyui-combobox" data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="男">男</option>
						<option value="女">女</option>
						</select></dd>
				</dl>
				<dl>
					<dt style="width:70px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>
					<dd><input type="text" id="arch_num" class="textinput"/></dd>
					<dt style="width:50px;"><input id="ck_phone" type="checkbox"/>电话</dt>
					<dd><input type="text" id="phone" class="textinput"/></dd>
					<dt style="width:80px;"><input id="ck_company_id" type="checkbox"/>选择单位<input type="hidden" id="company_id" /></dt>
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:250px;"/>
						<div id="com_name_list_div" style="left:552px;display:none"
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
					</dd>
					<dt class="taw"   style="width:70px;text-align: right;">体检任务</dt>
					<dd><select class="easyui-combobox" id="batch_id" name="batch_id"	data-options="height:26,width:140,panelHeight:'auto'">
					<option value="">不限</option>
					</select></dd>
                    <dt style="width:42px;text-align: right;">分组</dt>
					<dd><select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:100,panelHeight:'auto'">
					<option value="">不限</option>
					</select></dd>
                   
				</dl>
				<dl>
					<dt style="height:26px;width:70px;"><input id="ck_tjlx" type="checkbox" />体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:145,panelHeight:'auto'"></select>
					</dd>	
					 <dd><a href="javascript:getExamInfoGrid();" class="easyui-linkbutton c6" style="width:80px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员信息列表</strong></legend>
      <table id="contractlist">
      </table>	
 </fieldset>
 <div id="dlg-edit"></div>
 <div id="combine-edit" class="easyui-dialog" data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50" ></div>
 </body>
 </html>