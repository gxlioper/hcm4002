<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卡销售管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cardSaleManage/cardSale.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:250px; top:120px; width:300px;height:130px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input[type=text]{ width:180px;}
</style>
</head>
<body>
<input type="hidden"  id="isShowInvoicePage" value="<s:property value="isShowInvoicePage"/>"  />
<input type="hidden" id="invoiceprinttype"  value="<s:property value="invoiceprinttype"/>"  />
<input type="hidden" id="invoiceRepeatType"  value="<s:property value="invoiceRepeatType"/>"  />
<input type="hidden" id="saleListId"  value="cardSaleList"  />
<input type="hidden" id="saleDetailId"  value="skCardSaleDetail"  />
<input type="hidden" id="saleTradeNum"  value=""  />
<div id="tt" class="easyui-tabs" fit="true">
	<div title="未售卡信息列表" style="padding:5px;">
	   <div class="easyui-layout" border="false" fit="true">
	   		<div data-options="region:'north'" style="height:70px;">
      	 		<fieldset>
    				<legend><strong>信息检索</strong></legend>
    					<div class="user-query">
							<dl>
								<dt style="width:70px;"><input id="ck_card_num" type="checkbox"/>卡号：</dt>
								<dd><input class="easyui-textbox" id="card_num" style="width:100px;height:26px;"/></dd>
								<dt style="width:90px;"><input id="ck_card_num_se" type="checkbox" checked="checked"/>卡号号段：</dt>
								<dd><input class="easyui-textbox" id="card_num_s" style="width:100px;height:26px;"/></dd>
                     			<dt style="height:26px;width:20px;">至</dt>
                     			<dd><input class="easyui-textbox" id="card_num_e" style="width:100px;height:26px;"/></dd>
								<dt style="width:90px;"><input id="ck_date" type="checkbox"/>制卡时间：</dt>
								<dd><input class="easyui-datebox" id="start_date" value="<s:property value="start_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dt style="height:26px;width:20px;">至</dt>
                     			<dd><input class="easyui-datebox" id="end_date" value="<s:property value="end_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dt style="width:70px;"><input id="ck_company" type="checkbox"/>单位：</dt>
								<dd><input class="easyui-textbox" id="company" style="width:100px;height:26px;"/></dd>
                     			<dd><a href="javascript:chaxuncardinfo();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
							</dl>
						</div>
    			</fieldset>
      	 	</div>
	 		<div data-options="region:'center',title:'卡信息列表'" border="false">
		        <table id="cardinfolist"></table>
	      	</div>
	      	<div data-options="region:'east',title:'已选择售卡清单'" style="width:40%;">
	      		<table id="xycardsalelist"></table>
	      	</div>
      </div>
      </div>
      <div title="已售卡信息列表" style="padding:5px;"  onclick="setSaleDetailId('cardSaleList','skCardSaleDetail');">
      	 <div class="easyui-layout" border="false" fit="true">
      	 		<div data-options="region:'north'" style="height:70px;">
      	 			<fieldset>
    					<legend><strong>信息检索</strong></legend>
    					<div class="user-query">
							<dl>
								<dt style="width:70px;">售卡日期：</dt>
								<dd><input class="easyui-datebox" id="sk_start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dt style="height:26px;width:20px;">至</dt>
                     			<dd><input class="easyui-datebox" id="sk_end_date" value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dt style="width:90px;">是否开发票：</dt>
                     			<dd><select class="easyui-combobox" id="sk_kaipiao" style="width:100px;height:26px;">
                     				<option value="">不选择</option>
                     				<option value="N">未开发票</option>
                     				<option value="Y">已开发票</option>
                     			</select></dd>
                     			<dd><a href="javascript:shoukachaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
							</dl>
						</div>
    				</fieldset>
      	 		</div>
      	 	<div data-options="region:'center',title:'售卡交易记录列表'" border="false">
      	 		<table id="cardSaleList"></table>
      	 	</div>
      	 	<div data-options="region:'east',title:'售卡交易明细列表'" style="width:40%;">
	      		<table id="skCardSaleDetail"></table>
	      	</div>
      	 </div>
      </div>
      <div title="已预售卡信息列表" style="padding:5px;" onclick="setSaleDetailId('advCardSaleList','ysCardSaleDetail');">
      		<div class="easyui-layout" border="false" fit="true">
      	 		<div data-options="region:'north'" style="height:70px;">
      	 			<fieldset>
    					<legend><strong>信息检索</strong></legend>
    					<div class="user-query">
							<dl>
								<dt style="width:110px;"><input id="ck_saleTradeNum" type="checkbox"/>交易流水号：</dt>
								<dd><input class="easyui-textbox" id="ys_saleTradeNum" style="width:100px;height:26px;"/></dd>
								<dt style="width:110px;"><input id="ck_ysdate" type="checkbox"/>预售卡日期：</dt>
								<dd><input class="easyui-datebox" id="ys_start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dt style="height:26px;width:20px;">至</dt>
                     			<dd><input class="easyui-datebox" id="ys_end_date" value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"/></dd>
                     			<dd><a href="javascript:yskachaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
							</dl>
						</div>
    				</fieldset>
      	 		</div>
      	 	<div data-options="region:'center',title:'售卡交易记录列表'" border="false">
      	 		<table id="advCardSaleList"></table>
      	 	</div>
      	 	<div data-options="region:'east',title:'预售卡交易明细列表'" style="width:40%;">
	      		<table id="ysCardSaleDetail"></table>
	      	</div>
      	 </div>
      </div>
  </div>

<div id="dlg-sale-amount" class="easyui-dialog" data-options="width: 400,height: 180,closed: true,cache: false,modal: true,top:50,title:'修改售卡金额'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 90px;" >售卡金额：</dd>
				<dd><input class="easyui-numberbox" id="up_sale_amount" style="width:100px;height:26px;" data-options="min:0,precision:2"/><strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_up_sale_amount();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-sale-amount').dialog('close')">关闭</a>
		</div>
	</div>
	</form>
</div>
<div id="dlg-qrsk" class="easyui-dialog"  data-options="width: 1200,height: 300,closed: true,cache: false,modal: true,title:'确认售卡'">
	<input id="shoufeileixing" type="hidden"/>
	<input id="shoufeisale_trade_num" type="hidden"/>
	<form id="add1Form" style="margin-top: 10px;margin-left: 30px;">
		<span style="float: left; font-size: 16px; margin-top: 8px;">收费方式：</span>
					<div  id="charingtype" class="user-query" style="width: 200px; float: left;background:#ccc;height:180px;"></div>
					<div  id="charingtype2" class="user-query" style="width: 200px; float: left;display:none;background:#ccc;height:180px;"></div>
					<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
						<dl>
							<dd style="font-weight:bold;width:220px;">应收售卡总金额：<font id="yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;width:220px;">卡数量：<font id="shuliang">0</font>张&nbsp;&nbsp;&nbsp;</dd>
						</dl>
						<dl>
							<dd style="font-weight:bold;width:220px;color:#f00;">实收售卡总金额：<font id="shishou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;width:220px;">卡内总金额：<font id="knamount">0</font>元&nbsp;&nbsp;&nbsp;</dd>
						</dl>
						<dl><dd>票据打印：</dd><dd><input type="checkbox" id="fapiao" onclick="load_invoice()"/></dd><dd>打印发票</dd></dl>
							<div id="fapiao_msg" style="display: none">
								<div style="height: 30px; margin-top: 5px; margin-left: 5px;">
									<label>发票号：</label><input id="invoice_num" readonly="readonly" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>发票抬头：</label><input id="invoice_name" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>发票内容：</label><input id="invoice_type" type="text" class="textinput"/>
								</div>
								<div style="height: 30px; margin-left: 5px;text-align: center;margin-top: 7px;">
									<input name="invoice_l" type="radio"  value="mx"/>详细发票
									<input style="margin-left: 30px;" name="invoice_l" type="radio" value="jd" checked="checked"/>简单发票
								</div>
							</div>
					</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_card_sale();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-qrsk').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-bkfp" class="easyui-dialog" data-options="width: 500,height: 280,closed: true,cache: false,modal: true,top:50,title:'补开发票'">
	<form id="add1Form" style="margin-top: 10px;margin-left: 30px;">
		<input type="hidden" id="fapiao_sale_trade_num"></input>
	<div class="formdiv">
		<div class="formdiv">
			<dl>
				<dt>发票号：</dt>
				<dd><input style="width:150px;" type="text" readonly="readonly" id="invoice_num1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票抬头：</dt>
				<dd><input style="width:150px;" type="text" id="invoice_name1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票内容：</dt>
				<dd><input style="width:150px;" type="text" id="invoice_type1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dd>
				<input name="invoice_l1" type="radio" value="mx" style="margin-left: 120px;"/>详细发票
				<input style="margin-left: 30px;" name="invoice_l1" checked="checked" type="radio" value="jd"/>简单发票
				<dd>
			</dl>
		</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fapiao();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-bkfp').dialog('close')">关闭</a>
	</div>
</div>
</form>

<div id="up_bindCompany" class="easyui-dialog" data-options="width: 400,height: 200,closed: true,cache: false,modal: true,top:50,title:'绑定单位'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 10px;" >单位：</dd>
				<dd><input class="easyui-combobox" id="companyInfo" style="width:200px;height:26px;" data-options="min:0,precision:2"/><strong class="red">*</strong></dd>
			</dl>
		</div>
			<%-- <span><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
				  <div id="com_name_list_div" 
				      onmouseover="select_com_list_mover()" 
				      onmouseout="select_com_list_amover()">
                   </div>
	        </span> --%>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:bindCompany();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#up_bindCompany').dialog('close')">关闭</a>
		</div>
	</div>
	</form>
</div>

</div>
</body>
</html>