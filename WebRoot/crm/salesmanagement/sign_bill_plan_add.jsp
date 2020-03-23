<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/sign_bill_plan_add.js?randomId=<%=Math.random()%>"></script>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>单位信息</strong></legend>
    <div class="user-query" style="padding-top:0px;">
	<dl>
		<dt style="width:70px;"><input type="hidden" id="company_id" value="<s:property value="company_id"/>"/>单位名称：</dt>
		<dd><input type="text" id="com_name"/><strong class="quest-color">*</strong></dd>
		<dt style="width:70px;">单位类型：</dt>
		<dd><select id="com_type"></select><strong class="quest-color">*</strong></dd>
		<dt style="width:70px;">经济类型：</dt>
		<dd><select id="economiccode"></select><strong class="scolor">*</strong></dd>
		<dt style="width:70px;">企业规模：</dt>
		<dd><select id="comsizecode"></select><strong class="scolor">*</strong></dd>
	</dl>
	<dl>
		<dt style="width:70px;">单位地址：</dt>
		<dd><input type="text" id="address" value="<s:property value="address"/>" style="width:365px;" class="textinput"/><strong class="quest-color">*</strong></dd>
		<dt style="width:70px;">行业类型：</dt>
		<dd><select id="industrycode"></select><strong class="scolor">*</strong></dd>
		<dt style="width:70px;">行政区划：</dt>
		<dd><select id="areacodees"></select><strong class="scolor">*</strong></dd>
	</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>签单计划</strong></legend>		
	<div class="user-query" style="padding-top:0px;">
		<dl>
			<dt style="width:70px;">签单名称：</dt>
			<dd><input style="width:280px;" type="text" id="sign_name" onkeyup="pingyingsc()" value="<s:property value="sign_name"/>" class="textinput"/><strong class="quest-color">*</strong></dd>
			<dt style="width:90px;">助记码：</dt>
			<dd><input type="text" id="sign_pingying" value="<s:property value="sign_pingying"/>" class="textinput"/><strong class="quest-color">*</strong></dd>
			<dt style="width:90px;">签单类型：</dt>
			<dd><select id="sign_type"></select><strong class="quest-color">*</strong></dd>
		</dl>
		<dl>
			<dt style="width:70px;">签单数量：</dt>
			<dd><input style="width:94px;" type="text" id="sign_count" value="1" class="textinput"/><strong class="quest-color">*</strong></dd>
			<dt style="width:70px;">年度：</dt>
			<dd><input style="width:94px;" type="text" id="sign_year" value="<s:property value="sign_year"/>" class="textinput"/><strong class="quest-color">*</strong></dd>
			<dt style="width:90px;">新旧分类：</dt>
			<dd><select id="old_new_type"></select><strong class="quest-color">*</strong></dd>
			<dt style="width:90px;">客户分类：</dt>
			<dd><select id="customer_type"></select><strong class="quest-color">*</strong></dd>
		</dl>
		<dl>
			<dt style="width:70px;">估算人数：</dt>
			<dd><input style="width:94px;" type="text" id="sign_persion" value="" class="textinput"/><strong class="scolor">*</strong></dd>
			<dt style="width:70px;">估算金额：</dt>
			<dd><input style="width:94px;" type="text" id="sign_amount" value="" class="textinput"/><strong class="scolor">*</strong></dd>
			<dt style="width:90px;">签单日期：</dt>
			<dd><input type="text" id="sign_date" class="easyui-datebox" style="height:26px;"/><strong class="scolor">*</strong></dd>
			<dt style="width:90px;">体检结束日期：</dt>
			<dd><input type="text" id="end_date" class="easyui-datebox" style="height:26px;width:143px;"/><strong class="scolor">*</strong></dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;">
    <legend><strong>单位联系人</strong></legend>
    <table id="company_contacts_list"></table>
</fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveSignBillPlan();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>

<div id="toobor_contacts">
	<a href="javascript:contacts_edit()" class="l-btn l-btn-small l-btn-plain" group="" id="">
		<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">新增联系人</span>
		<span class="l-btn-icon icon-add">&nbsp;</span></span>
	</a>
</div>
<div id="contacts_edit"></div>
