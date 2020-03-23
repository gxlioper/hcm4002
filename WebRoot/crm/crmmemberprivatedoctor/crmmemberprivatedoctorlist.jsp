 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmmemberprivatedoctor/crmmemberprivatedoctorlist.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
</head>
<body>
<input type="hidden" id="report_print_type" value="<s:property value="report_print_type"/>">
<input type="hidden" id="iszyb" value="<s:property value="#session.iszyb"/>">
<input type="hidden" id="zyb_report_print_type" value="<s:property value="zyb_report_print_type"/>">
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:98%;">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>私人医生查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="height:26px;width:80px;"><input id="ck_exam_num" type="checkbox"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:80px;"><input id="ck_searchphone" type="checkbox"/>电话</dt>					
					 <dd><input class="easyui-textbox"  type="text" id="searchphone" value="" style="height:26px;width:135px;"/></dd>  
			         <dt style="height:26px;width:50px;"><input id="ck_sex" type="checkbox"/>性别</dt>					
					 <dd><select class="easyui-combobox" id="searchsex"
					 data-options="height:26,width:135,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="男">男</option>
						<option value="女">女</option>
					 </select></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:80px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:415px;position:fixed;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:80px;"><input id="ck_examtype" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="examtype"
					data-options="height:26,width:135,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
					</select></dd>   
                     <dt style="height:26px;width:50px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:135,panelHeight:'auto'"></select></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:80px;"><input id="ck_allot" type="checkbox" checked="checked"/>分配状态</dt>
					<dd><select class="easyui-combobox" id="searchallot"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N" selected>未分配</option>
						<option value="Y"  >已分配</option>
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_setname" type="checkbox"/>体检套餐</dt>
					<dd><select class="easyui-combobox" id="searchsetname"
					data-options="height:26,width:135,panelHeight:'auto'">
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_amount" type="checkbox"/>体检金额</dt>
					<dd><input id="amount1" class="easyui-textbox" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input id="amount2" class="easyui-textbox" style="width:100px;height:26px;"/></dd>   
                     <dt style="height:26px;width:80px;"><input id="ck_isvisit" type="checkbox" checked="checked"/>是否回访</dt>
					<dd><select class="easyui-combobox" id="isvisit" data-options="height:26,width:135,panelHeight:'auto'">
						<option value="1" selected>需回访</option>
						<option value="0">无需回访</option>
					</select></dd>
                        
					<dd><a href="javascript:getCrmMemberPrivateDoctorList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
<fieldset style="margin:5px;padding-right:0; height:70%;">
 <legend><strong>客户列表</strong></legend> 
      <table id="CrmMemberPrivateDoctorshow">
      </table>	
 </fieldset>
 </div>
  </div>
 <div id="dlg-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>