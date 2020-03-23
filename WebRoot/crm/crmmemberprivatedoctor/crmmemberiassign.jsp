 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmmemberprivatedoctor/crmmemberprivatedoctorassign.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>';
$(function(){
	$('#arch_num').css('ime-mode','disabled');
	$('#arch_num').focus();
})
</script>
<style>
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:2px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
</style>
</head>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:100%;">
<input type="hidden" id="report_print_type" value="<s:property value="report_print_type"/>">
<input type="hidden" id="zyb_report_print_type" value="<s:property value="zyb_report_print_type"/>">
<input  id="flagsss"  value="<s:property value="flag"/>"  hidden="true" style="height:16px;width:140px;" />
<input type="hidden" id="plan_visit_date" value="<s:property value="model.plan_visit_date"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>我的客户查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:70px;"><s:text name="tjhname"/></dt>
					<dd>
						<input  type="text" class="textinput" id="arch_num"    style="height:16px;width:140px;" />
					</dd>
					<dt style="width:70px;"><s:text name="dahname"/></dt>
					<dd>
						<input  type="text" class="textinput" id="exam_num"    style="height:16px;width:140px;" />
					</dd>
					<dt style="width:70px;">姓名</dt>
					<dd>
						<input  type="text" class="textinput" id="user_name"    style="height:16px;width:140px;" />
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_allot_date" type="checkbox" checked />分配日期</dt>
					<dd>
					<input  id="allot_dates"  value="<s:property value="model.plan_visit_date"/>"  hidden="true" style="height:16px;width:140px;" />
						<input  type="text" class="easyui-datebox" id="allot_date"    style="height:23px;width:140px;" data-options="prompt:'请选择结束查询日期'"/>
					</dd>					
					<dd><a href="javascript:getCrmMemberPrivateDoctorAssign();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>

 <fieldset style="margin:5px;padding-right:0;height:80%;">
 <legend><strong>我的客户列表</strong></legend> 
      <table id="CrmMemberPrivateDoctorAssignshow">
      </table>	
 </fieldset>
  </div>
 </div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50">
	<legend><strong>回访记录列表</strong></legend> 
      <table id="CrmVisitRecordshow">
      </table>	
 </fieldset>
</div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="results_contrast" class="pop_up_box_lis">
    <div id="ls_title" class="title"><span>策略明细</span></div>
    <table id="examitem_div">
    </table>
</div>
</body>
</html>