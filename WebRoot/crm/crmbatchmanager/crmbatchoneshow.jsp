<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务信息</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:70px;">单位名称: </dt>
					<dd style="height:26px;width:240px;"><s:property value="model.comname"/></dd>
					<dt style="height:26px;width:70px;">方案名称: </dt>
					<dd style="height:26px;width:240px;"><s:property value="model.batch_name"/></dd>
				</dl>
				</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检任务明细</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:70px;">结算方式:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.pay_way"/></dd>
					<dt style="height:26px;width:70px;">创建人: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.creaters"/></dd>
					<dt style="height:26px;width:70px;">创建时间:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.create_time"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:70px;">状态: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.is_Active"/></dd>
					<dt style="height:26px;width:70px;">修改人: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.updaters"/></dd>
					<dt style="height:26px;width:70px;">修改时间 :</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.update_time"/></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:70px;">联系人:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.contact_name"/></dd>
					<dt style="height:26px;width:70px;">联系电话:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.phone"/></dd>
					<dt style="height:26px;width:70px;">收费类型: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.charge_type"/></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:70px;">计划人数:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.exam_number"/>(人)</dd>
					<dt style="height:26px;width:70px;">计划费用:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.exam_fee"/>(元)</dd>
					<dt style="height:26px;width:70px;">体检项目:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.exam_item"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:70px;">销售人员:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.sales_name"/></dd>
					<dt style="height:26px;width:70px;">介绍人:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.introducer_name"/></dd>
                    <dt style="height:26px;width:100px;">体检开始日期:</dt>
					<dd style="height:26px;width:110px;"><s:property value="model.exam_date"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:70px;">住宿安排:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.accommodation"/></dd>
				    <dt style="height:26px;width:70px;">用餐安排:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.dine"/></dd>
					<dt style="height:26px;width:100px;">体检结束日期:</dt>
					<dd style="height:26px;width:110px;"><s:property value="model.exam_date_end"/></dd>
				</dl>
				<dl>
					<dt  style="height:26px;width:70px;">发票抬头:</dt>
					<dd style="height:26px;width:500px;"><s:property value="model.invoice_title"/></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:70px;">邮寄地址:</dt>
					<dd style="height:26px;width:500px;"><s:property value="model.batch_address"/></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:70px;">备注:</dt>
					<dd style="height:26px;width:500px;"><s:property value="model.remark"/></dd>
				</dl>
				</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	   
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>

