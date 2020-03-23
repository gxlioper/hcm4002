<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termaccountListwayshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batchid"/>">
<input type="hidden" id="acc_num" value="<s:property value="model.acc_num"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:200px;" ><s:property value="model.comname"/></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:140px;" ><s:property value="model.batch_name"/>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				    <dd style="height:20px;width:80px;" >结帐单号：</dd>
					<dd style="height:20px;width:100px;" ><s:property value="chargingInvoiceSingleTTDTO.account_num"/></dd>				   
				</dl>
				
			</div>
 </fieldset>
  <fieldset style="margin:5px;padding-right:0;">
<legend><strong>结算金额</strong></legend>
			&nbsp;&nbsp;实收合计：<s:property value="chargingInvoiceSingleTTDTO.invoice_amount"/>(单位:元)
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
    <legend><strong>结算方式维护</strong></legend>
			<div class="user-query">
			<dl>
			<dt>
				发票号<strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="invoice_no"
					style="height: 26px; width: 140px;" value="<s:property value="teamAccount.invoice_no" />"/>
			</dd>
			</dl>
			<dl>	
			<dt>
				发票抬头 <strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="invoice_name" 
					style="height: 26px; width: 280px;" value="<s:property value="teamAccount.invoice_name" />"/>
			</dd>
			</dl>
			<dl>
			<dt>联系人</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="linker"
					style="height: 26px; width: 140px;" value="<s:property value="teamAccount.linker" />"/>
			</dd>
			</dl>
			<dl>	
			<dt>
				联系电话
			</dt>
			<dd>
				<input class="easyui-textbox" type="text" id="phone" 
					style="height: 26px; width: 140px;" value="<s:property value="teamAccount.phone" />"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					 href="javascript:updateteamaccountdo();" 
					 class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">保存发票</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <input type="button" name="Submit2" onclick="windowclosesss();" value="关闭窗口" />
			</dd>
		</dl>				
			</div>
 </fieldset>
 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>结算方式</strong></legend>
      <table id="teamaccountwaylist">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 400,height: 170,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>