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
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termaccountoneshow.js?randomId=<%=Math.random()%>"></script>
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
    <legend><strong>基本信息</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:200px;" ><s:property value="model.comname"/></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:140px;" ><s:property value="model.batch_name"/>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				    <dd style="height:20px;width:80px;" >结算单号：</dd>
					<dd style="height:20px;width:100px;" ><s:property value="teamAccount.acc_num"/></dd>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <input type="button" name="Submit2" onclick="windowclosesss();" value="关闭窗口" />
				</dl>
				<dl>
					<dd style="height:20px;width:140px;">结算人 </dd>
					<dd style="height:20px;width:200px;" ><s:property value="teamAccount.acc_user"/></dd>
					 <dt style="height:26px;width:140px;">结算日期</dt>
		 	        <dd style="height:26px;width:140px;"><s:property value="teamAccount.acc_date"/></dd>
		 	         <dt style="height:26px;width:80px;">结算状态</dt>
		 	        <dd style="height:26px;width:100px;"><s:property value="teamAccount.acc_stautss"/></dd>
		 	    </dl>
		 	    <dl>
					<dd style="height:20px;width:140px;">结账人</dd>
					<dd style="height:20px;width:200px;" ><s:property value="teamAccount.balance_user"/></dd>
					<dt style="height:26px;width:140px;">结账日期</dt>
		 	        <dd style="height:26px;width:140px;"><s:property value="teamAccount.balance_date"/></dd>
		 	        <dt style="height:26px;width:80px;">结账状态</dt>
		 	        <dd style="height:26px;width:100px;"><s:property value="teamAccount.balance_statuss"/></dd>
		 	    </dl>
				<dl>
				    <dd style="height:20px;width:140px;" >审核人：</dd>
					<dd style="height:20px;width:200px;" ><s:property value="teamAccount.auditer_user"/></dd>
				    <dt style="height:26px;width:140px;">审核日期</dt>
		 	        <dd style="height:26px;width:140px;"><s:property value="teamAccount.balance_date"/></dd>
				</dl>
			</div>
 </fieldset>
  <fieldset style="margin:5px;padding-right:0;">
<legend><strong>结算金额</strong></legend>
&nbsp;&nbsp;&nbsp;&nbsp;总人次：<font id="zrc" style="font-weight:bold;font-style:italic;">0</font>(单位:人次)
			&nbsp;&nbsp;应收：<font id="ysje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
			&nbsp;&nbsp;实收：<font id="ssje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
			&nbsp;&nbsp;优惠：<font id="yhje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
    <legend><strong>发票维护</strong></legend>
			<div class="user-query">
			<dl>
			<dt>
				发票号<strong class="quest-color">*</strong>
			</dt>
			<dd><s:property value="teamAccount.invoice_no" /></dd>
			</dl>
			<dl>	
			<dt>
				发票抬头 <strong class="quest-color">*</strong>
			</dt>
			<dd><s:property value="teamAccount.invoice_name" />
			</dd>
			</dl>
			<dl>
			<dt>联系人</dt>
			<dd><s:property value="teamAccount.linker" /></dd>
			</dl>
			<dl>	
			<dt>
				联系电话
			</dt>
			<dd><s:property value="teamAccount.phone" />						
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