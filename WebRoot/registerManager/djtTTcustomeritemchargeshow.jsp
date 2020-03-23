<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 30%;	height: auto;}
#right {	width: 60%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/djtTTcustomeritemchargeshow.js?randomId=<%=Math.random()%>"></script>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="custsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="ids" value="<s:property value="model.ids"/>">
<input type="hidden" id="item_codes" value="<s:property value="model.item_codes"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检项目原金额</strong>
	</legend>
	<div class="user-query">
		<dl>
		  
			<dt>原总额</dt>
			<dd>
				<input  type="text" readonly id="olditem_amount" value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			
			<dt>折扣后总额</dt>
			<dd>
				<input type="text" id="oldamount" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>单位结算总额</dt>
			<dd>
				<input type="text" id="oldteam_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>个人未结算总额</dt>
			<dd>
				<input type="text" id="oldpersonal_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>个人已结算总额</dt>
			<dd>
				<input type="text" id="oldypersonal_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>原缴费项目</strong>
	</legend>
	  <div id="selectoldchangitemlist"></div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
<legend>
		<strong>体检项目新金额</strong>
	</legend>
	<div class="user-query">
		<dl>
		  
			<dt>原总额</dt>
			<dd>
				<input  type="text" readonly id="item_amount" value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>折扣率</dt>
			<dd>
				<input  type="text" id="discount"
					value="10"
					style="height: 26px; width: 70px;" />
			</dd>
			<dt>折扣后总额</dt>
			<dd>
				<input type="text" id="amount" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>单位结算总额</dt>
			<dd>
				<input type="text" id="team_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
			<dt>个人结算总额</dt>
			<dd>
				<input type="text" id="personal_pay" readonly
					value="0"
					style="height: 26px; width: 70px;" />(元)
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择新缴费项目</strong>
	</legend>
	<div id="main">
		<div id="left">
			项目列表<input type="text" id="itemname" name="itemname" value="" />
			<div id="changitemlist"></div>
		</div>

		<div id="right">
			已选项目
			<div id="selectchangitemlist"></div>
		</div>
	</div>

</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">

<div align="right">
	<div class="inner-button-box">
	    <a href="javascript:djtcustadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	</div>
</div>
</fieldset>
