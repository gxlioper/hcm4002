<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitplan/addcrmvisitplan.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<input type="hidden" id="updddvisit_important" value="<s:property value="visit_important"/>"/>
<input type="hidden" id="upfujianflag_im" value="<s:property value="fujianflag"/>"/>
<input type="hidden" id="upid" value="<s:property value="id"/>"/>
<input type="hidden" id="cvr_id" value="<s:property value="cvr_id"/>"/>
<fieldset style="margin: 10px;">
	<legend><strong>健康计划编辑</strong></legend> 
	<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:100px;">
				<s:text name="tjhname"/>
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="upexam_num"  disabled="disabled" class="easyui-validatebox" value="<s:property value="exam_num"/>" style="height: 18px; width: 205px;" />
			</dd>
			<dt style="width:100px;">
				姓名 
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="upusername" disabled="disabled" value="<s:property value="personname"/>" class="easyui-validatebox" style="height: 18px; width: 205px;" />
			</dd>
		</dl>
		<dl>
		    <dt style="width:100px;">重要级别 </dt>
		    <dd>
		       <select class="easyui-combobox" id="upimportant" style="width:210px;height:26px;"></select>
		    </dd>
		    <dt style="width:100px;"><s:text name="tjhname"/></dt>
		    <dd>
		       <input  type="text" class="textinput"  id="uparch_num" disabled="disabled" value="<s:property value="arch_num"/>" class="easyui-validatebox" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">计划回访时间 </dt>
		    <dd>
		       <input class="easyui-datebox" id="upplan_visit_date" style="width:210px;height:26px;" value="<s:property value="plan_visit_date"/>"  data-options="prompt:'请选择计划回访日期'"/>
		    </dd>
		    <dt style="width:100px;">计划回访医生</dt>
		    <dd>
		       <input  type="text" class="textinput"  id="updoctor" disabled="disabled" value="<s:property value="doctorName"/>" style="height: 18px; width: 205px;" />
		    </dd>
		</dl>
		<dl>
		   <dt style="width:100px;">是否需要复检</dt>
		    <dd>
		       <select class="easyui-combobox" id="upfujianflag" style="width:210px;height:26px;"></select>
		    </dd>
		</dl>
		<dl>		    
			<dt style="width:100px;">回访内容</dt>
			<dd>
				<textarea   id="upvisit_content" class="textinput"	style="height: 100px; width: 535px;"><s:property value="visit_content"/></textarea>
			</dd>			
		</dl>		
	</div>
  </div>
</form>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addCrmVisitPlan();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
