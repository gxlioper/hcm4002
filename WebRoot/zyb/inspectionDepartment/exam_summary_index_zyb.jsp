<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>总检室首页</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/inspectionDepartment/exam_summary_index_zyb.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		$("input").attr("maxlength","20");
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	})
</script>
</head> 
<body>
<input type="hidden" id = "app_type"  value="<s:property value="model.app_type"/>" />
<input type="hidden" id = "is_batch"  value="<s:property value="is_batch_examine_s"/>" />
<input type="hidden" id = "examSummaryCheckDefault"  value="<s:property value="examSummaryCheckDefault"/>" />
<input type="hidden" id="teamAmountViewFlag" value="<s:property value="teamAmountViewFlag"/>">
<input type="hidden" id="report_print_type" value="<s:property value="report_print_type"/>">
<input type="hidden" id="zyb_report_print_type" value="<s:property value="zyb_report_print_type"/>">
<div id="tt" class="easyui-tabs">
<div title="体检人员列表" style="">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:70px;"><input id="ck_exam_num" type="checkbox"/>体检号</dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:20px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:80px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="exam_status"
					data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
			        <dt style="height:26px;width:80px;"><input id=tijianleixin_f type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="tijianleixin"
					data-options="height:26,width:65,panelHeight:'auto'">
					<option value="G">个人</option>
					<option value="T">团体</option>
					</select></dd>
                  </dl>
				<dl>
				   <dt style="height:26px;width:70px;"><input id="ck_arch_num" type="checkbox"/>档案号</dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:60px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_final_date" type="checkbox"/>总检日期</dt>
					<dd><input class="easyui-datebox" id="final_time1" value="<s:property value="model.final_time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:20px;">至</dt>
                     <dd><input class="easyui-datebox" id="final_time2" value="<s:property value="model.final_time2"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:80px;"><input id="ck_doctor_name" type="checkbox"/>总检医生</dt>					
					<dd><select class="easyui-combobox" id="doctor_name"
					data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
                     <dt style="height:26px;width:80px;"><input id="ck_appstatus" type="checkbox"/>审核状态</dt>					
					<dd><select class="easyui-combobox" id="appstatus"
					data-options="height:26,width:65,panelHeight:'auto'">
					<option value="">所有</option>
					<option value="B">未审核</option>
					<option value="A">已审核</option>
					</select></dd>
				 </dl>
				 <dl>
                  	<dt style="height:26px;width:70px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:305px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-10px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    	<dt style="height:26px;width:90px;"><input id="ck_is_guide_back" type="checkbox">导检单回收</dt>
					<dd>
						<select class="easyui-combobox" id="is_guide_back"data-options="height:26,width:65,panelHeight:'auto'">
							<!-- <option value="">所有</option> -->
							<option value="N">未回收</option>
							<option value="Y">已回收</option>
						</select>
					</dd>
					 <dt style="height:26px;width:80px;"><input id="ck_customer_type" type="checkbox"/>人员类型</dt>					
					<dd><select class="easyui-combobox" id="customer_type"
					data-options="height:26,width:65,panelHeight:'auto'">
					</select></dd>
					<dt style="height:26px;width:120px;"><input id="ck_tjlx" type="checkbox" />职业体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:140,panelHeight:'auto'"></select>
					</dd>	
					<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong>&nbsp;&nbsp;
	<span style="background-color:#FF0000;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">阳性 &nbsp;&nbsp;</span>
    <span style="background-color:#0000FF;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">已打印报告&nbsp;&nbsp;</span>
    <span style="font-weight:bold;">&nbsp;加粗</span>
    <span style="font-size:14px;">取报告方式为邮寄&nbsp;&nbsp;</span>
</legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 </div>
 <div title="可以总检人员列表" style="">
 	 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:80px;">体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date1" value="<s:property value="model.seven_time"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date1" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:60px;">体检号</dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num1" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:40px;">姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname1" value="" style="height:26px;width:80px;"/></dd>
					<dd><a href="javascript:chaxun2();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong>&nbsp;&nbsp;
	<span style="background-color:#FF0000;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">阳性 &nbsp;&nbsp;</span>
    <span style="background-color:#0000FF;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">已打印报告&nbsp;&nbsp;</span>
    <span style="font-weight:bold;">&nbsp;加粗</span>
    <span style="font-size:14px;">取报告方式为邮寄&nbsp;&nbsp;</span>
</legend>
      <table id="getexamInfoList">
      </table>	
 </fieldset>
 	
 </div>
<%-- <div title="被驳回人员列表" style="">
 	 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:60px;"><input id="ck_exam_num2" checked="checked" type="checkbox"/>体检号</dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num2" value="" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:80px;">处理状态</dt>					
					<dd><select class="easyui-combobox" id="done_status" data-options="height:26,width:65,panelHeight:'auto'">
						<option value="0">未处理</option>
						<option value="1">已处理</option>
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_data2" type="checkbox"/>驳回日期</dt>
					<dd><input class="easyui-datebox" id="start_date2" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:30px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_date2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
					<dt style="height:26px;width:60px;"><input id="ck_custname2" type="checkbox"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname2" value="" style="height:26px;width:80px;"/></dd>
					<dd><a href="javascript:chaxun3();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="getexamreject">
      </table>	
 </fieldset>
 </div> --%>
 </div>
 </body>
 </html>