<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>分组基本信息</strong></legend>
			<div class="user-query">
				<dl>
					
					<dt style="height:26px;width:70px;">批次名称: </dt>
					<dd style="height:26px;width:200px;"><s:property value="model.batch_name"/></dd>
					<dt style="height:26px;width:70px;">分组名称: </dt>
					<dd style="height:26px;width:200px;"><s:property value="model.group_name"/></dd>
				</dl>
							</div>
 </fieldset>
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>分组详细信息</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:70px;">性别: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.sex"/></dd>
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
					<dt style="height:26px;width:70px;">婚否：</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.is_Marriage"/></dd>
					<dt style="height:26px;width:70px;">最小年龄:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.min_age"/></dd>
					<dt style="height:26px;width:70px;">最大年龄:</dt>
					<dd style="height:26px;width:70px;"><s:property value="model.max_age"/></dd>
				</dl>
				<dl>
				    <dt style="height:26px;width:70px;">人员类型：</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.exam_item"/></dd>
					<dt  style="height:26px;width:70px;">职位:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.posttion"/></dd>
					<dt style="height:26px;width:70px;">分组标识:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.group_index"/></dd>
				</dl>
				<dl>
					
					<dt style="height:26px;width:70px;">实际费用:</dt>
					<dd style="height:26px;width:140px;"><s:property value="model.amount"/>(元)</dd>
					<dt style="height:26px;width:70px;">折扣率: </dt>
					<dd style="height:26px;width:140px;"><s:property value="model.discount"/></dd>
				</dl>			
			</div>
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	   
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-groupshow').dialog('close')">关闭</a>
	</div>
</div>

