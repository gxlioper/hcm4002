<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>终审室首页</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/exam_summary_censoring.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" >
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
</script>
</head> 
<body>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>"/>
<input type="hidden" id="is_exam_result_canfinal" value="<s:property value="model.is_exam_result_canfinal"/>"/>
<div id="tt" class="easyui-tabs">
<div title="未复审人员列表" style="">
	<fieldset style="margin:5px;padding-right:0;">
	<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:110px;"><input id="ck_exam_num0" type="checkbox"/><s:text name="dahname"/>\<s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num0" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="ck_custname0" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname0" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date0" type="checkbox"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date0" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_date0" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
                  </dl>
				<dl>
					<dt style="height:26px;width:110px;"><input id="ck_exam_doctor0" type="checkbox"/>审核医生</dt>					
					<dd><select class="easyui-combobox" id="exam_doctor0"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
                    <dt style="height:26px;width:60px;"><input id="ck_id_num0" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num0" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_final_date0" type="checkbox" checked="checked"/>审核日期</dt>
					<dd><input class="easyui-datebox" id="final_time10" value="<s:property value="model.final_time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:20px;">至</dt>
                     <dd><input class="easyui-datebox" id="final_time20" value="<s:property value="model.final_time2"/>" style="width:100px;height:26px;"/></dd>
				 	 <dd><a href="javascript:chaxun0();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
				 </dl>
			</div>
	 </fieldset>
	 <fieldset style="margin:5px;padding-right:0;">
	<legend><strong>体检人员列表</strong></legend>
	      <table id="getexamInfoList0">
	      </table>	
	 </fieldset>
 </div>
<div title="未复审人员列表" style="">
      <table id="getexamInfoList">
      </table>	
 </div>
<div title="已复审人员列表" style="">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:110px;"><input id="ck_exam_num" type="checkbox"/><s:text name="dahname"/>\<s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
			        <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:305px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:530px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
			        <%-- <dt style="height:26px;width:80px;"><input id=tijianleixin_f type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="tijianleixin"
					data-options="height:26,width:65,panelHeight:'auto'">
					<option value="G">个人</option>
					<option value="T">团体</option>
					</select></dd> --%>
                  </dl>
				<dl>
				   <%-- <dt style="height:26px;width:70px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>    --%>
                    <dt style="height:26px;width:110px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_final_date" type="checkbox" checked="checked"/>复审日期</dt>
					<dd><input class="easyui-datebox" id="final_time1" value="<s:property value="model.final_time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:20px;">至</dt>
                     <dd><input class="easyui-datebox" id="final_time2" value="<s:property value="model.final_time2"/>" style="width:100px;height:26px;"/></dd>
				 	 <%-- <dt style="height:26px;width:80px;"><input id="ck_customer_type" type="checkbox"/>人员类型</dt>					
					<dd><select class="easyui-combobox" id="customer_type"
					data-options="height:26,width:100,panelHeight:200">
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_tjlx" type="checkbox" />体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:100,panelHeight:'auto'"></select>
					</dd>	 --%>
					<dt style="height:26px;width:80px;"><input id="ck_exam_doctor" type="checkbox" checked="checked"/>复审医生</dt>					
					<dd><select class="easyui-combobox" id="exam_doctor"
					data-options="height:26,width:100,panelHeight:'auto'">
					</select></dd>
					<dt style="height:26px;width:60px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 </div>
 </div>
 </body>
 <div id="search" style="">
   <span style="margin-left:25px;">检索：</span><input id="sss" class="easyui-searchbox" searcher="searcher" prompt="请输入<s:text name="dahname"/>或<s:text name="tjhname"/>" style="width:150px;height:26px;ime-mode: disabled;"></input>
</div> 
 </html>