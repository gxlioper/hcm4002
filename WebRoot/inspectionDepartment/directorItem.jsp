<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>项目完成情况页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/directorItem.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	$(function(){
		$("input").attr("maxlength","20");
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	})
</script>
</head> 
<body>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;"><input id="ck_exam_num" type="checkbox"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:30px;"><input id="ck_date" type="checkbox" checked="checked"/></dt>
					<dt style="height:26px;width:85px;margin-top:-4px;"><select class="easyui-combobox" id="date_type" data-options="height:26,width:80,panelHeight:'auto',editable:false">
							<option value="">体检日期</option>
							<option value="1">检查日期</option>
							</select>
					</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.s_join_date"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.e_join_date"/>" style="width:100px;height:26px;"/></dd>
			         <dt style="height:26px;width:80px;"><input id="ck_set_name" type="checkbox"/>体检套餐</dt>					
					 <dd><select class="easyui-combobox" id="set_name"
					data-options="height:26,width:140"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_exam_status" type="checkbox"/>检查状态</dt>					
					 <dd><select class="easyui-combobox" id="exam_status"
					data-options="height:26,width:80,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N">未检</option>
						<option value="Y">已检</option>
						<option value="C">登记</option>
						<option value="G">弃检</option>
						<option value="D">延期</option>
					</select></dd>  
				</dl>
				<dl>
				   <dt style="height:26px;width:60px;"><input id="ck_dep" type="checkbox"/>科室</dt>					
					<dd><select class="easyui-combobox" id="dep"
					data-options="height:26,width:100"></select></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_item" type="checkbox"/>收费项目</dt>					
					<dd><select class="easyui-combobox" id="item"
					data-options="height:26,width:135"></select></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:275px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:395px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>  
                     <dt style="height:26px;width:80px;"><input id="ck_pay_status" type="checkbox"/>收费状态</dt>					
					<dd><select class="easyui-combobox" id="pay_status"
					data-options="height:26,width:80,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N">未付费</option>
						<option value="Y">已付费</option>
						<option value="R">预付费</option>
					</select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_status" type="checkbox"/>体检状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:80,panelHeight:'auto'"></select></dd>                       
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员与项目列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 </body>
 </html>