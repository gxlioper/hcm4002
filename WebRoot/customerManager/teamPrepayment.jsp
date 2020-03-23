<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.main {
	width: auto;
	height: auto;
}

.left {
	width: 15%;
	height: auto;
}

.right {
	width: 84%;
	height: auto;
	margin-left: 10px;
}

.left, .right {
	float: left;
}

.left1 {
	width: 49%;
	height: auto;
}

.right1 {
	width: 49%;
	height: auto;
	margin-left: 10px;
}

.left1, .right1 {
	float: left;
}

.pop_up_box_lis {
	border: 1px solid #ccc;
	background: #fff;
	padding: 0 0px 10px;
	position: absolute;
	font-size: 12px;
	display: none;
}

.title {
	text-align: center;
	cursor: move;
	height: 30px;
	line-height: 30px;
	margin-bottom: 15px;
	background: #359BCC;
	border-bottom: 1px solid #ccc;
	color: #FFFFFF;
	font-size: 16px;
}
</style>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtree.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	var dahname = '<s:text name="dahname"/>';
	var tjhname = '<s:text name="tjhname"/>';
</script>
</head>
<body>
	<input type="hidden" id="barexeurl"value="<s:property value="model.time1"/>"/> 
	<input type="hidden" id="djdexeurl" value="<s:property value="model.time2"/>"/>
	<input type="hidden" id="rechargeResources" value="<s:property value="model.rechargeResources"/>"/>
	<input type="hidden" id="frozenResources" value="<s:property value="model.frozenResources"/>"/>
	<input type="hidden" id="invoiceRepeatType"  value="<s:property value="model.invoiceRepeatType"/>">
	<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>" />
	<input type="hidden" id="company_id" />
	<input type="hidden" id="batch_id" />
	<input type="hidden" id="group_id" />
	<input type="hidden" id="comname" />
	<input type="hidden" id="com_t" value=""/>
	<input type="hidden" id="com_id" value=""/>
	<div class="main">
			<div  class="easyui-layout" style="height: 640px">
				<div data-options="region:'west',title:'单位信息 ',split:true" style="width:200px;">
					<div style="margin-left: 7px;">
					    <input id="com_name" onkeyup="reopen();"  maxlength="20" style="background-position:right; background-repeat:no-repeat;height:20px; width: 160px;" />
					    <!-- <a href="javascript:void(0)" class="easyui-linkbutton c6" style="height:20px;">单位查询</a>  --> 
					    <div id="append" style="position: absolute; background-color: white;"></div>  
					    	 <div id="depttree"></div>
					</div>
				    	 
				</div>
				<div data-options="region:'center',title:''" style="padding:5px;">
				    <div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="商户开户/充值" style="padding: 5px;">
						<fieldset style="margin: 5px; padding-right: 0;">
							<legend><strong>商户信息</strong>&nbsp;&nbsp;
							</legend>
							<div class="formdiv" >
								<dl>
									<dt>单位编号：</dt>
									<dd><input type="text" id="com_num" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
							
								<dl>
									<dt>账户余额：</dt>
									<dd><input type="text" id="balance" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
								<dl>
									<dt>商户状态：</dt>
									<dd><input type="text" id="com_type" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
								<dl>
									<dt>创建人：</dt>
									<dd><input type="text" id="creater" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
								<dl>
									<dt>创建时间：</dt>
									<dd><input type="text" id="create_date" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
								<dl>
									<dt>修改人：</dt>
									<dd><input type="text" id="updater" disabled="disabled" value="" class="textinput"/></dd>
								</dl>
								<dl>
									<dt>修改时间：</dt>
									<dd><input type="text" id="update_date" disabled="disabled" value="" class="textinput"/></dd>
									<dd><a href="javascript:recharge();" class="easyui-linkbutton c6 l-btn l-btn-small" id='chongzhi' style="width:100px; height: 26; margin-left: 120px;">充&nbsp;&nbsp;&nbsp;值</a></dd>
									<dd><a href="javascript:editMercacc('1');" class="easyui-linkbutton c5 l-btn l-btn-small" id='dongjie'  style="width:100px; height: 26; margin-left: 60px;">
										冻&nbsp;&nbsp;&nbsp;结</a></dd>
									<dd><a href="javascript:editMercacc('0');" class="easyui-linkbutton c4 l-btn l-btn-small" id='jiedong' style="width:100px; height: 26; margin-left: 60px;">
										解&nbsp;&nbsp;&nbsp;冻</a></dd>
								</dl>
								
							</div>
						</fieldset>
						<div style="margin-left: 60px;" ><font color="red">注: 商户充值后不支持退费,请确认信息无误后进行充值操作 ！！</font> </div>
		         	 </div>
		          
		          	<div title="商户流水" style="padding: 5px;">
		          		<fieldset style="margin: 5px; padding-right: 0;height: 580px;">
							<legend><strong>流水信息</strong>&nbsp;&nbsp;
							</legend>
							<table id = 'transactionFlow'></table>
						</fieldset>
					</div>
		
					<div title="发票信息" style="padding: 5px;">
							<table id = 'invoiceInfo'></table>
					</div>
					
				</div>
			</div>
	</div>
			    
	<div id="dlg-chongzhi" title="充值" class="easyui-dialog"
		data-options="width: 400,height: 380,closed: true,cache: false,modal: true,top:50">
		<div id = 'toolbar_t'>
			&nbsp;&nbsp;&nbsp;&nbsp;充值金额：<input class="easyui-numberbox"  type="text" id="amount" value="" style="height:26px;width:120px;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;" onclick="saveRecharge()">确认充值</a>
	    </div>
	    <table id = 'getChargingMethod' ></table>
	</div>	
	
	<div id="dlg-fapiao" title="开发票" class="easyui-dialog"
		data-options="width: 400,height: 240,closed: true,cache: false,modal: true,top:50">
		
		<div style="background-color: #A4BED4;width: 100%;height: 80%;margin-top: 0px;">
			<div class="formdiv">
				<dl>
					<dt style="width: 80px;">发票号：</dt>
					<dd><input style="width:220px;" type="text" readonly="readonly" id="invoice_num" class="textinput"/><strong class="red">*</strong></dd>
				</dl>
				<dl>
					<dt style="width: 80px;">发票抬头：</dt>
					<dd><input style="width:220px;" type="text" id="invoice_name" class="textinput"/><strong class="red">*</strong></dd>
				</dl>
				<dl>
					<dt style="width: 80px;">发票金额：</dt>
					<dd><input style="width:220px;" type="text" id="invoice_type" class="textinput" readonly="readonly"/><strong class="red">*</strong></dd>
				</dl>
			</div>
		</div>	
		
		<div class="dialog-button-box">
			<div class="inner-button-box">
			    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fapiao();">确定</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fapiao').dialog('close')">关闭</a>
			</div>
		</div>
		
	</div>
	   
	</div>	
	
	
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/teamPrepayment.js?randomId=<%=Math.random()%>"></script>