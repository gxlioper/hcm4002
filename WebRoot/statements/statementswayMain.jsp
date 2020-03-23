<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/statementswayMain.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<style>
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:15px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
</style>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="">
<input type="hidden" id="teamaccounturl" value="<s:property value="model.teamaccounturl"/>">
<input type="hidden" id="invoiceRepeatType"  value="<s:property value="invoiceRepeatType"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				
				<dt style="height:26px;width:80px;">结算单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>  
                    
                    <dt style="height:26px;width:80px;">体检任务</dt>					
					<dd><select class="easyui-combobox" id="batch_id" name="batch_id"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>  
				
				    <dd><a href="javascript:getcontractGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>合同信息</strong></legend>
      <table id="contractshow">
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>结帐信息</strong></legend>
      <table id="statementsinvoidshow">
      </table>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>结算单列表</strong></legend>
      <table id="statementsshow">
      </table>	
 </fieldset>
 
 
 <fieldset style="margin:5px;padding-right:0;">
	<legend><strong>结算方式列表</strong></legend>
	      <table id="statementswayshow">
	      </table>	
 </fieldset>
 
 
 
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 1000,height: 500,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custadd" class="easyui-dialog"  data-options="width: 400,height: 500,closed: true,cache: false,modal: true,top:50"></div>
  <div id="results_contrast" class="pop_up_box_lis">
	<div id="ls_title" class="title"><span>附加费用明细</span></div>
	<table id="fjfy_list"></table>
  </div>
  
  <div id="dlg-huazhang" title="团体划账" class="easyui-dialog"
		data-options="width: 300,height: 260,closed: true,cache: false,modal: true,top:60">
		
			<div class="formdiv">
				<dl>
					<dt style="width: 100px;height: 26px">商户余额 :</dt>
					<dd><input style="width:120px; height: 26px" type="text" id="sh_balance" class="textinput" readonly="readonly" /></dd>
				</dl>
				<dl>
					<dt style="width: 100px;height: 26px">已开发票金额 :</dt>
					<dd><input style="width:120px;height: 26px" type="text" id="ykfp_amount" class="textinput" readonly="readonly"/></dd>
				</dl>
				<dl>
					<dt style="width: 100px;height: 26px">结账金额:</dt>
					<dd><input style="width:120px;height: 26px" type="text" id="jz_amount" class="textinput" readonly="readonly"/></dd>
				</dl>
				<dl>
					<dt style="width: 100px;height: 26px;">划账金额:</dt>
					<dd><input style="width:120px;height: 26px" type="text" id="hz_amount" class="easyui-numberbox"  min="0.00"   precision="2"/></dd>
				</dl>
			</div>
		<div class="dialog-button-box">
			<div class="inner-button-box">
			    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_huazhang();">确定划账</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-huazhang').dialog('close')">关闭</a>
			</div>
		</div>
		
	</div>


</body>