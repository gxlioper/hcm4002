<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitplan/addcrmallvisitplan.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<fieldset style="margin: 10px;">
	<legend><strong>健康计划编辑</strong></legend> 
	<div style=" margin-top: 15px" >
		<dl style="height: 35px;padding: 10px 0 10px 30px;">
			<dt>
				<s:text name="dahname"/>:
			</dt>
			<dd style="padding:0 20px 0 20px;">
				<input type="hidden" id="addid" value="<s:property  value="id"/>"/>
				<input  type="text" class="textinput"  id="addarch_num" disabled="disabled"
					value="<s:property value="arch_num"/>" class="easyui-validatebox"  
					style="height: 15px; width: 244px;" />
			</dd>
			<dt>
				计划回访医生 :
			</dt>
			<dd style="padding:0 20px 0 20px;">
				<input  class="easyui-combobox"  id="addplan_doctor_id"  
					   value="<s:property  value="plan_doctor_id"/>"
					style="height: 26px; width: 244px;"/>
			</dd>
		</dl>
		<dl  style="height: 35px;padding: 10px 0 10px 30px;">
		<dt>
				计划回访时间 :
			</dt>
		<dd style="padding:0 20px 0 28px;">
		<input class="easyui-datebox" id="addplan_visit_date"  value="<s:property value="plan_visit_date"/>"
		style="width:205px;height:26px;" data-options="prompt:'请选择计划回访日期'"/>
		</dd>

		</dl>
		<dl style="height: 35px;padding: 20px 0 10px 32px;">		    
			<dt>回访内容</dt>
			<dd style="padding-left: 20px;">
				<textarea   id="addvisit_content" class="textinput"	style="height: 100px; width: 598px;"><s:property value="visit_content"/></textarea>
			</dd>			
		</dl>		
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addCrmVisitPlan();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
