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
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/pricing.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:20%; top:100px; width:300px;height:130px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input[type=text]{ width:180px;}
</style>
</head>
<body>
<input type="hidden"  id="webResource" value="<s:property value="webResource_value"/>"  />
<input type="hidden"  id="isShowInvoicePage" value="<s:property value="isShowInvoicePage"/>"  />
<input type="hidden"  id="isChargingWayZero" value="<s:property value="isPrintRecepit"/>"  />
<input type="hidden" id="invoiceprinttype"  value="<s:property value="invoiceprinttype"/>">
<input type="hidden" id="isFeesBaodao"  value="<s:property value="isFeesBaodao"/>">
	    <div class="easyui-layout" border="false" fit="true">
   			<div data-options="region:'north'" border="false" style="height:103px;">
   					<fieldset>
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px" id="ser_num" name="ser_num" class="textinput"/><input type="hidden" id="status"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
							<dt style="margin-left:20px;width:50px;">姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt style="width:80px;" id="user_name"></dt>
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
   					
        	<fieldset style="padding-right:0;height:25px;border="false"">
					<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
						<dl><dd style="width:150px;">原价：<font id="yuanjia">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd style="font-weight:bold;width:180px;">应收金额：<font id="yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							<dd>折扣：</dd><dd><input style="width:40px;" type="text" id="zongzhekou" value="10"/>折</dd>
						</dl>
					</div>
 			</fieldset>
 		</div>
 		<div data-options="region:'center'" border="false">
            <table id="witemlist"></table>
      	</div>
      </div>

    <div id="dlg-huajia" class="easyui-dialog" title="划价" style="width:800px;height:480px" 
    	data-options="width: 900,height: 540,top:50, closed: true,cache: false,modal: true">
        <div class="easyui-tabs" id="tab_ss" style="height:390px;width:780px;margin-left:5px;">
				 <div title="按项目划价" id = "item" style="padding:0px;">
					<table id = "itme_pricing"></table>
    			 </div>
    			<div title="按项目总额划价" id= "amount" style="padding:0px;" >
    				<div>
    					<dl>
    					<dd style="font-weight:bold;width:180px;">应收金额：<font id="yingshouje">0</font>元&nbsp;&nbsp;&nbsp;</dd>
    					
					    <dd>体检收费金额：<input class="easyui-numberbox"  type="text" id="tj_amount" precision = "2"   style="height:26px;width:100px;" /></dd> &nbsp;&nbsp;&nbsp;
    					
					    <dd>his收费金额：<input class="easyui-numberbox"  type="text" id="his_amount" precision = "2"  style="height:26px;width:100px;" /></dd> 
    					</dl>
    				</div>
    				<table id = "teamItme_pricing"></table>
    			</div>
    	</div>
    	<div class="dialog-button-box">
				<div class="inner-button-box">
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;"  id='tj'  onclick="save_amount(0)">体检收费</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c5" style="width:100px;"  id='his' onclick="save_amount(1)">his收费</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;"  id='save_pricing'  onclick="save_amount(2)">确定</a>&nbsp;&nbsp;&nbsp;
				    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:$('#dlg-huajia').dialog('close')">关闭</a>
				</div>
		</div>
    </div>
    
    
    <div id="dlg-shoufei" class="easyui-dialog" title="收费" style="width:1000px;height:550px" 
    	data-options="width: 1100,height: 640,top:40, closed: true,cache: false,modal: true">
    	
    	<fieldset style="padding-right:0;background:#C4CDD6;height: 185px;">
				<span style="float: left; font-size: 16px; margin-top: 8px;">收费方式：</span>
					<div  id="charingtype" class="user-query" style="width: 200px; float: left;"></div>
					<div  id="charingtype2" class="user-query" style="width: 200px; float: left;display:none;"></div>
					<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
						<dl>
							<dd style="font-weight:bold;width:180px;">体检应收金额：<font id="tj_yingshou">0</font>元</dd>
							<dd style="font-weight:bold;width:180px;">his应收金额：<font id="his_yingshou">0</font>元</dd>
						</dl>
						<dl>
							<dd style="font-weight:bold;color:#f00;width:180px;">体检实收金额：<font id="tj_shishou">0</font>元</dd>
							<dd style="font-weight:bold;color:#f00;width:180px;">his实收金额：<font id="his_shishou">0</font>元</dd>
						</dl>
						<dl><dd>票据打印：</dd><dd><input type="checkbox" id="shoufeimingxi"/></dd><dd>打印收费明细单</dd></dl>
										<dl><dd style="margin-left: 90px;"><input type="checkbox" id="putongmingxi"/></dd><dd>打印普通收费单</dd></dl>
						<dl><dd style="margin-left: 90px;"><input type="checkbox" id="fapiao" onclick="load_invoice()"/></dd><dd>打印发票</dd></dl>
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
									<input name="invoice_l" type="radio" checked="checked" value="mx"/>详细发票
									<input style="margin-left: 30px;" name="invoice_l" type="radio" value="jd"/>简单发票
								</div>
							</div>
						<dl style="margin-top:3px;margin-left:">
							<dd>找零：</dd><dd><input style="width:35px;" type="text" readonly="readonly" id="zhaoling" value="0.00"/>元</dd>
						</dl>
					</div>
 			</fieldset>
    	<fieldset style="margin:5px;padding-right:0;height: 250px;">
			<legend><strong>收费项目列表</strong>
			</legend>
	      <table id="chargeItemList">
	      </table>	
 		</fieldset>
 		
 		<div class="dialog-button-box">
				<div class="inner-button-box">
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;" onclick="javascript:shoufeiBaodao();">收费</a>&nbsp;&nbsp;&nbsp;
					<!--<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:del_his()">撤销申请</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:fapiao_show();">补打发票</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:110px;" onclick="javascript:weihu_fapiao();">作废空白发票</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:print_fapiao();">收据打印</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;display:none;" onclick="javascript:updateAllAmount()">反算</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;display:none;" onclick="javascript:void(0)">抹零</a>&nbsp;&nbsp;&nbsp;-->
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:$('#dlg-shoufei').dialog('close')">关闭</a>
				</div>
		</div>
    <div/>
    
    <div id="dlg-refund" class="easyui-dialog" title="退费" style="width:800px;height:480px" 
    	data-options="width: 1100,height: 640,top:40, closed: true,cache: false,modal: true">
    	<div data-options="region:'center'">
     			<div id="tt" class="easyui-tabs" style="height:400px;width:780px;margin-left:5px;">
     				<div title="已收费未开发票项目列表" style="padding:5px;">
   						<table id="yksjList">
      					</table>
        			</div>
        			<div title="已开发票收据项目列表" style="padding:5px;" >
            			<table id="ykfpList">
      					</table>
        			</div>
    		</div>
		</div>
		
		<div class="dialog-button-box">
				<div class="inner-button-box">
				    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:90px;" onclick="javascript:$('#dlg-refund').dialog('close')">关闭</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
		</div>
		<div id="toolbar">
			<div>
				<dl>
				<dd style="font-weight:bold;width:280px;font-size: 18px;">未开发票总额：<font id="yksj_amount">0</font>元&nbsp;&nbsp;&nbsp;</dd>
			    <dd style="font-weight:bold;color: red; width:280px;font-size: 18px;"> 应退金额：<font id="yksj_tui">0</font>元&nbsp;&nbsp;&nbsp;</dd>
				</dl>
			</div>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;"  id='tj'  onclick="f_tuifei()">退费</a>&nbsp;&nbsp;&nbsp;
		</div>
		<div id="toolbar1">
			<div>
				<dl>
				<dd style="font-weight:bold;width:280px;font-size: 18px;">已开发票总额：<font id="ykfp_amount">0</font>元&nbsp;&nbsp;&nbsp;</dd>
			    <dd style="font-weight:bold;color: red; width:280px;font-size: 18px;"> 应退金额：<font id="ykfp_tui">0</font>元&nbsp;&nbsp;&nbsp;</dd>
				</dl>
			</div>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;"  id='tj'  onclick="f_tuifei(1)">退费</a>&nbsp;&nbsp;&nbsp;
		</div>
    </div>
    
    <div id="dlg-tuifei" class="easyui-dialog" title="退费" style="width:550px;height:420px" 
    	data-options="width: 550,height: 420,top:50, closed: true,cache: false,modal: true">
    	<div id = "toolbar_t">
			<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; margin: 5px;">应退金额：<font id="yishou_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
			<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; color: #f00; margin-top: 5px;">实退金额：<font id="shitui_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
			<div><input type="hidden" id="isPrintRecepit" value="<s:property value="isPrintRecepit"/>"/>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;" onclick="javascript:tuifeiClick();">确认退费</a></div>
		</div>
    	<table id="tuifei_fees">
      	</table>
    </div>
    
<div id="dlg-edit" class="easyui-dialog" data-options="width: 850,height: 452,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fapiao" class="easyui-dialog" data-options="width: 600,height: 458,closed: true,cache: false,modal: true,top:50"></div>

</body>
</html>