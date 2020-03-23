<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/batcheckedit.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="id" value="<s:property value="model.id"/>">
<input type="hidden" id="company_id"
	value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id"
	value="<s:property value="model.batch_id"/>">
	<input type="hidden" id="check_statuss"
	value="<s:property value="model.check_status"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选体检任务</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 80px;">单位名称</dd>
			<dd style="height: 20px; width: 200px;">
				<s:property value="model.comname" />
			</dd>
			<dd style="height: 20px; width: 100px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 200px;">
				<s:property value="model.batch_name" />
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检任务审核</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>审核状态<input type="hidden" id="check_type" value="<s:property value="model.check_type"/>" /></dt>
			<dd><select class="easyui-combobox" id="check_status" 
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
			<dt>上次审核日期</dt>
			<dd><s:property value="model.checkdate" /></dd>
		</dl>
		<dl>
			<dt>审核说明</dt>
			<dd><input type="text" id="checknotice" value="<s:property value="model.checknotice"/>" style="width:630px;height:26px;"></input></dd>
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:batchcheckedit();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>
