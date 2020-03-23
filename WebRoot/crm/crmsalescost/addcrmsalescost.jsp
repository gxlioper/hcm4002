<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmsalescost/addcrmsalescost.js?randomId=<%=Math.random()%>"></script>

<fieldset style="margin: 10px;">
	<legend><strong>新增</strong></legend> 
	<div class="user-query" style=" margin-top: 15px" >
		<dl>
			<dt>
				销售员:
			</dt>
			<dd>
				<input type="hidden" id="addid" value="<s:property  value="id"/>"/>
				<input type="hidden" id="addsales_ids" value="<s:property  value="sales_id"/>"/>
				<input  type="text" class="textinput"  id="addsales_ids" disabled="disabled"
					value="<s:property value="sales_name"/>" class="easyui-validatebox"  
					style="height: 15px; width: 200px;" />
			</dd>
			<dt>
				金额 :
			</dt>
			<dd>
				<input  class="textinput"  id="addcost_amount"  
					 class="easyui-validatebox"  value="<s:property  value="cost_amount"/>"
					style="height: 16px; width: 200px;"/>
			</dd>
		</dl>
		<dl>
		<dt>
				时间 :
			</dt>
		<dd>
		<input class="textinput" id="addcost_date"  value="<s:property value="cost_date"/>" disabled="disabled"
		style="width:200px;height:16px;" data-options="prompt:'请选择计划回访日期'"/>
		</dd>
		<dt>费用类型: </dt>
			<dd>
			<input type="hidden" id="addcost_types" value="<s:property  value="cost_type"/>"/>
			
				<input   type="text" maxlength="45" id="addcost_typename" disabled="disabled"
				value="<s:property value="cost_typename"/>"  class="textinput"	style="height: 16px; width: 200px;"/>
			</dd>	
		</dl>
		<dl>		    
			<dt>
				签单计划:
			</dt>
			<dd>
				<input  class="easyui-combobox"  id="addbatch_nums"  
					 class="easyui-validatebox"  value="<s:property  value="batch_num"/>"
					style="height:25px; width: 207px;"/>
			</dd>
			<dt>报销款项类型: </dt>
			<dd>
			<input  class="easyui-combobox"  id="addpayment_types"  
					 class="easyui-validatebox"  value="<s:property  value="payment_type"/>"  data-options="panelHeight:'auto'"
					style="height:25px; width: 207px;"/>
			</dd>			
		</dl>		
		<dl>		    
			<dt>备注：</dt>
			<dd>
				<textarea   id="addremark" class="textinput"	style="height: 100px; width:515px;"><s:property value="remark"/></textarea>
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
