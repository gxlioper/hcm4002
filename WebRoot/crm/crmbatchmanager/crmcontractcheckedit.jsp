<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmbatchmanager/crmcontractcheckedit.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="contract_num"
	value="<s:property value="model.contract_num"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选合同</strong>
	</legend>
	<div class="user-query">
		<dl>
		    <dd style="height: 20px; width: 100px;">合同编号：</dd>
			<dd style="height: 20px; width: 140px;">
				<s:property value="model.contract_num" />
			</dd>
			<dd style="height: 20px; width: 100px;">单位名称:</dd>
			<dd style="height: 20px; width: 280px;">
				<s:property value="model.comname" />
			</dd>
			
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>合同审核</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>审核状态</dt>
			<dd><select class="easyui-combobox" id="checktype" name="checktype"
					value="<s:property value="model.checktype"/>"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
			<dt>上次审核日期</dt>
			<dd><s:property value="model.checkdate" /></dd>
		</dl>
		<dl>
			<dt>审核说明</dt>
			<dd><input  type=textarea id="checknotice" value="<s:property value="model.checknotice"/>" style="width:630px;height:26px;"></input></dd>
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:batchcheckedit();" class="easyui-linkbutton c6" style="width:100px;">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>
