<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收费</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/collectFees.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/healthCare.js?randomId=<%=Math.random()%>"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/aisino.js?randomId=<%=Math.random()%>"></script> --%>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
#shouju_msg{ position: absolute; font-weight:normal; margin-left:50.5%; top:110px; width:300px;height:130px; border:1px solid #666; background:#999;}
#shouju_msg label{ width:100px; display:inline-block; text-align:left;}
#shouju_msg input[type=text]{ width:180px;}

#fapiao_msg{ position: absolute; font-weight:normal; margin-left:50.5%; top:100px; width:700px;height:181px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg label1{ width:100px; display:inline-block; text-align:right;}
#fapiao_msg input[type=text]{ width:120px;}
</style>
</head>
<body>
<input type="hidden"  id="webResource" value="<s:property value="webResource_value"/>"  />
<input type="hidden"  id="isShowInvoicePage" value="<s:property value="isShowInvoicePage"/>"  />
<input type="hidden"  id="isChargingWayZero" value="<s:property value="isPrintRecepit"/>"  />
<input type="hidden" id="invoiceprinttype"  value="<s:property value="invoiceprinttype"/>">
<input type="hidden" id="isFeesBaodao"  value="<s:property value="isFeesBaodao"/>">
<input type="hidden" id="fees_mx_point"  value="<s:property value="fees_mx_point"/>">
<input type="hidden" id="is_fees_mx_point_checked"  value="<s:property value="is_fees_mx_point_checked"/>">
<input type="hidden" id="collect_fees_whole"  value="<s:property value="collect_fees_whole"/>">
<input type="hidden" id="invoiceRepeatType"  value="<s:property value="invoiceRepeatType"/>">
<input type="hidden" id="is_repeat_invoice"  value="<s:property value="is_repeat_invoice"/>">
<input type="hidden" id="username" value="<s:property value="#session.username.name"/>" />
<input type="hidden" id="userid"  value="<s:property value="#session.username.userid"/>"  />
<input type="hidden" id="work_other_num" value="<s:property value="work_other_num"/>">
<input type="hidden" id="req_num">
<input type="hidden" id="wayCharge">
<input type="hidden" id="inter_class">
<input type="hidden" id="data">
<input type="hidden" id="posAmount">
<input type="hidden" id="rcpt_print_flag1">
<input type="hidden" id="bill_type">
<input type="hidden" id="patNo">
<input type="hidden" id="ybname">
<input type="hidden" id="name"  value="<s:property value="name"/>">
<input type="hidden" id="center_id" value="<s:property value="center_id"/>" >

	    <div class="easyui-layout" border="false" fit="true">
   			<div data-options="region:'north'" border="false" style="height:282px;">
   					<fieldset>
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px;ime-mode: disabled;" id="ser_num" name="ser_num" value="<s:property value="model.exam_num"/>" class="textinput"/><input type="hidden" id="status"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
							<dt style="margin-left:20px;width:50px;">姓名：<input type="hidden" name="exam_id" id="exam_id" value="<s:property value="exam_id"/>"/></dt><dt style="width:80px;" id="user_name"></dt>
							<dt style="width:50px;">性别：</dt><dt style="width:25px;" id="sex"></dt>
							<dt style="width:50px;">年龄：</dt><dt style="width:30px;" id="age"></dt>
							<dt style="width:50px;">单位：</dt><dt id="company"></dt>
							<dt style="width:70px;">人员类型：</dt><dt id="customer_type"></dt>
							<dt style="width:70px;">体检套餐：</dt><dt id="set_name"></dt>
							<dt style="width:70px;">分组名称：</dt><dt id="group_name"></dt>
							<dt style="width:70px;">分组金额：</dt><dt style="width:60px;" id="amount"></dt>
						</dl>
					</div>
 			</fieldset>
   					
        	<fieldset style="padding-right:0;background:#ccc;height: 225px;">
				<span style="float: left; font-size: 16px; margin-top: 8px;">收费方式：</span>
					<div  id="charingtype" class="user-query" style="width: 200px; float: left;"></div>
					<div  id="charingtype2" class="user-query" style="width: 200px; float: left;display:none;"></div>
					<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
						<dl><dd style="width:150px;">原价：<font id="yuanjia">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;width:180px;">应收金额：<font id="yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;color:#f00;width:180px;">实收金额：<font id="shishou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd>折扣：</dd><dd><input style="width:40px;" type="text" id="zongzhekou" value="10"/>折</dd>
							<dd>找零：</dd><dd><input style="width:35px;" type="text" readonly="readonly" id="zhaoling" value="0.00"/>元</dd>
							<dd>选择处方医师：</dd><dd><select class="easyui-combobox" id="prescription" name="prescription"
							data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
						</dl>
						<dl><dd>票据打印：</dd><dd><input type="checkbox" id="shoufeimingxi"/></dd><dd>打印收费明细单</dd><dd><input type="checkbox" id="putongmingxi"/></dd><dd>打印普通收费单</dd></dl>
						<dl><dd style="margin-left: 90px;"><input type="checkbox" id="shouju" onclick="load_receiptis()"/></dd><dd>打印收据</dd><dd style="margin-left: 48px;"><input type="checkbox" id="fapiao" onclick="load_invoice()"/></dd><dd>打印发票</dd></dl>
							<div id="shouju_msg" style="display: none;margin-left:500px">
								<div style="height: 30px; margin-top: 5px; margin-left: 5px;">
									<label>收据号：</label><input id="invoice_num" readonly="readonly" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>收据抬头：</label><input id="invoice_name" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>收据内容：</label><input id="invoice_type" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;text-align: center;margin-top: 7px;">
									<input name="invoice_l" type="radio"  value="mx"/>详细收据
									<input style="margin-left: 30px;" name="invoice_l" type="radio" value="jd" checked="checked"/>简单收据
								</div>
							</div>
							<div id="fapiao_msg" style="display: none;margin-left:700px">
								<div style="height: 30px; margin-top: 5px; margin-left: 5px;">
									<label>发票号：</label><input id="fapiao_num" style="width:200px;" readonly="readonly" type="text" class="textinput"/>
								
									<label1>发票类型：</label1> 
									<select id='fplx' name="fplx">
									  <!-- option value="0">专用发票</option-->
									  <option value="2" selected>普通发票</option>
									 </select>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>购方名称：</label><input id="gfmc" type="text" style="width:204px;" class="textinput"/>
								
									<label1>购方税号：</label1><input id="gfsh" type="text" style="width:200px;" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>购方账号：</label><input id="gfkhh" type="text" style="width:200px;" class="textinput"/>
								
									<label1>地址电话：</label1><input id="gfdhdz" type="text" style="width:200px;"  class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>商品名称：</label><input id="spmc" type="text" style="width:200px;" class="textinput" value="体检费"/>
									<label1>复核人：</label1><select id="fhr" style="width:200px;" class="easyui-combobox" data-options="height:26,width:142,panelHeight:'auto'"></select>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>数量：</label><input id="spsl" type="text" style="width:200px;" class="textinput"/>
									<label1>单价：</label1><input id="spdj" type="text" style="width:200px;" class="textinput"/>
								</div>	
								<div style="height: 30px; margin-left: 5px;">
									<label>单位：</label><input id="dw" type="text" style="width:200px;" class="textinput"/>
									<label1>备注：</label1><input id="bz" type="text" style="width:200px;" class="textinput"/>
								</div>	
							</div>
						<dl style="margin-top:10px;margin-left:">
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:50px;" onclick="javascript:shoufeiBaocun();">收费</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:del_his()">撤销申请</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:60px;display:none;" onclick="javascript:updateAllAmount()">反算</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:50px;" onclick="javascript:moling()">凑整</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:cksfjl()">收费记录</a></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:fapiao_show();">补打发票</a></dd>
							<!--dd><a href="javascript:void(0)" id="repeatFapiao" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:fapiao_repeatInvoice();">重打发票</a></dd-->
							<!-- dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:weihu_fapiao();">作废发票</a></dd -->
							<dd id ='posanniu' style="display:none"><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:80px;" onclick="javascript:posanniu();">pos收费</a></dd>
					</dl>
					<dl style="margin-top:10px;margin-left:0px">
							 <dd style="margin-left: 40px;"><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:authorization();">医保授权</a></dd>
							 <dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:readCardym();">读卡</a></dd>
<!-- 							 <dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:readCardAccBalance('03','','');">读卡(省)</a></dd> -->
							 
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:shouju_show();">补打收据</a></dd>
							<!--dd><a id="repeatInvoice" href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:shouju_repeatInvoice();">重打收据</a></dd-->
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:75px;" onclick="javascript:weihu_shouju();">作废收据</a></dd>
						</dl>
						
					</div>
 			</fieldset>
 		</div>
 		<div data-options="region:'center'" border="false">
            <table id="witemlist"></table>
      	</div>
      </div>

<div id="dlg-tuifei" class="easyui-dialog" data-options="width: 450,height: 358,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 850,height: 452,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-shouju" class="easyui-dialog" data-options="width: 600,height: 458,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fapiao" class="easyui-dialog" data-options="width: 600,height: 740,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-show_sfjl" class="easyui-dialog"  data-options="width: 1200,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fapiaoCD" class="easyui-dialog" data-options="width: 600,height: 258,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>