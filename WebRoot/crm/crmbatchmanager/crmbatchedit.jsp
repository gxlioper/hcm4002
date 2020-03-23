<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
 <script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmbatchmanager/crmbatchedit.js?randomId=<%=Math.random()%>"></script>
<input type="hidden" id="company_ids" value='<s:property value="model.company_ids"/>'>
<input type="hidden" id="id" value='<s:property value="model.id"/>'>
<input type="hidden" id="batch_num" value='<s:property value="model.batch_num"/>'>
<input type="hidden" id="sign_nums" value='<s:property value="model.sign_nums"/>'>
<input type="hidden" id="check_types" value='<s:property value="model.check_type"/>'>
<input type="hidden" id="tijiantypess" value='<s:property value="model.apptype"/>'>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务基本信息</strong></legend>
			<div class="user-query">
				<dl>
				    <dt>单位名称 </dt>
					<dd><input class="easyui-textbox"  maxlength="20" readonly type="text" id="comname" value="<s:property value="model.comname"/>" style="height:26px;width:390px;"/></dd>
					<dt>体检任务名称 <strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" maxlength="20" type="text" id="batch_name"  value="<s:property value="model.batch_name"/>" style="height:26px;width:140px;"/></dd>
				</dl>
				<dl>
					<dt>联系人</dt>
					<dd><input class="easyui-textbox" type="text" id="contact_name" maxlength="20" value="<s:property value="model.contact_name"/>" style="width:140px;height:26px;"></input></dd>
					<dt>联系电话</dt>
					<dd><input class="easyui-textbox" type="text" id="phone" maxlength="20" value="<s:property value="model.phone"/>" style="width:140px;height:26px;"></input></dd>
					<dt>结算方式 <strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="pay_way" name="pay_way" value="<s:property value="model.pay_way"/>" style="height:26px;width:140px;"></select></dd>
				</dl>
				<dl>
					<dt>计划人数<strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" type="text"  id="exam_number" value="<s:property value="model.exam_number"/>" style="height:26px;width:140px;"/></dd>
					<dt>计划费用 <strong class="quest-color">*</strong></dt>
					<dd><input class="easyui-textbox" type="text" id="exam_fee" maxlength="20"  value="<s:property value="model.exam_fee"/>" style="height:26px;width:140px;"/></dd>
					<dt>收费类型 <strong class="quest-color">*</strong></dt>
					<dd><select class="easyui-combobox" id="charge_type" name="charge_type" value="<s:property value="model.charge_type"/>" style="height:26px;width:140px;"/></select></dd>
				</dl>
				
				<dl>
				    <dt>销售人员</dt>
					<dd><input class="easyui-textbox" type="text" id="sales_name" maxlength="20" value="<s:property value="model.sales_name"/>" style="width:140px;height:26px;"></input></dd>
					<dt>介绍人</dt>
					<dd><input class="easyui-textbox" type="text" id="introducer_name" maxlength="20" value="<s:property value="model.introducer_name"/>" style="width:140px;height:26px;"></input></dd>
                    <dt>体检项目</dt>
					<dd><input class="easyui-textbox"   type=text id="exam_item" maxlength="100" value="<s:property value="model.exam_item"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
				    <dt>住宿安排</dt>
					<dd><input class="easyui-textbox" type="text" maxlength="100" id="accommodation" value="<s:property value="model.accommodation"/>" style="width:140px;height:26px;"></input></dd>
				    <dt>用餐安排</dt>
					<dd><input class="easyui-textbox"  maxlength="100"  type=text id="dine" value="<s:property value="model.dine"/>" style="width:140px;height:26px;"></input></dd>
					<dt>体检日期</dt>
					<dd><input  class="easyui-datebox"   type=text id="exam_date" value="<s:property value="model.exam_date"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
					<dt>发票抬头</dt>
					<dd><input  type=textarea id="invoice_title" maxlength="100" value="<s:property value="model.invoice_title"/>" style="width:390px;height:26px;"></input></dd>
					<dt>体检结束日期</dt>
					<dd><input  class="easyui-datebox"   type=text id="exam_date_end" value="<s:property value="model.end_date"/>" style="width:140px;height:26px;"></input></dd>
				</dl>
				<dl>
					<dt>邮寄地址</dt>
					<dd><input  type=textarea id="batch_address" maxlength="100" value="<s:property value="model.batch_address"/>" style="width:390px;height:26px;"></input></dd>
						<dt>审核类型 :</dt>
					<dd><input  id="check_typess"   style="height: 26px; width: 140px;"/></dd>
				
				</dl>
				<dl>
					<dt>备注</dt>
					<dd><input  type=textarea id="remark" maxlength="200" value="<s:property value="model.remark"/>" style="width:390px;height:26px;"></input></dd>
					<dt>体检类型:</dt>
					<dd><input  id="examtypes"   style="height: 26px; width: 140px;"/></dd>
				</dl>
				</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:fanganadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>

