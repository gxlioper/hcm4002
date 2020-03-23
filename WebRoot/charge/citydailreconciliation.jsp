<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>市医保对账</title>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 50%;	height: auto;}
#right {width: 49%;height: auto;margin-left: 8px;}
#left, #right {	float: left;}
}
</style>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/insurance.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/citydailreconciliation.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/healthCare.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden" id="user_ids" value="<s:property value="model.userId"/>"/>
<input type="hidden" id="userid"  value="<s:property value="#session.username.userid"/>"  />
<input type="hidden" id="userName" value="<s:property value="model.username"/>"/>
<input type="hidden" id="work_num" value="<s:property value="model.work_other_num"/>"/>
<div class="easyui-layout" border="false" fit="true">
		<fieldset>
    		<legend><strong>信息检索</strong></legend>
    		<div class="user-query">
				<dl>
					<dt style="width:70px;">收费日期：</dt>
					<dd><input class="easyui-datebox" id="daily_acc_date1" value="<s:property value="model.daily_acc_date1"/>" style="width:150px;height:26px;"/></dd>
                    <dt style="height:26px;width:20px;">至</dt>
                    <dd><input class="easyui-datebox" id="daily_acc_date2" value="<s:property value="model.daily_acc_date2"/>" style="width:150px;height:26px;"/></dd>
                    <dt style="width:70px;">收费方式：</dt>
                    <dd>
                    	<select class="easyui-combobox" id="type" 
						data-options="height:26,width:110,panelHeight:'auto',required:true">
						<option value="103" selected="selected">市医保</option>
						<option value="104">省医保</option>
						</select>
                    </dd>
                    <dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
					<dd><a href="javascript:ybdzsq();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:160px;">医保日对账申请</a></dd>

					<dd><a href="javascript:yb_sum_dzsq();"  class="easyui-linkbutton c5 l-btn l-btn-small" style="height:26px;width:160px;">医保总额对账申请</a></dd>

					<%--<dd><a href="javascript:print_insure_detail();"  class="easyui-linkbutton c4 l-btn l-btn-small" style="height:26px;width:150px;">打印医保收费明细</a></dd>--%>
					
					<dd><a href="javascript:print_jzd();"  class="easyui-linkbutton c4 l-btn l-btn-small" style="height:26px;width:150px;">结账单</a></dd>
					<%--<dd><a href="javascript:insure_qt();"  class="easyui-linkbutton c5 l-btn l-btn-small" style="height:26px;width:120px;">医保签退</a></dd>--%>
				</dl>
			</div>
    	</fieldset>
	<div id="main">
		<div id="left">
			<fieldset style="height:755px;">
		    		<legend><strong>个人收费</strong>
		    			<!-------  <font style="font-weight:bold;">合计 : </font>
		    			<font color="red" id="persioncount" size="4">0</font> 元-->
		    		</legend>
		    		<div id="tt" class="easyui-tabs" style="width:940px;height:740px;">   
					    <div title="收费" style="width:auto;">   
					        <table id="dailyacclist"></table>  
					    </div>  
					    <div title="退费" style="width:auto;">
					        <table id="refundlist"></table>
					    </div>
					</div>
		    </fieldset>
	    </div>
	    <div id="right">
	    	<fieldset style="height:auto;">
		    		<legend><strong>对账信息</strong></legend>
				      	<div class="user-query" style="background-color:#99BBE8">
							<dl>
								<dt style="height:26px;width:100px;">体检收费总额 : </dt>
								<dd style="height:26px;width:100px;"><font color="red" size="4" id="summaryamount"></font></dd>
							</dl>
							<dl>
								<dt style="height:26px;width:100px;">个人收费总额 : </dt>
								<dd style="height:26px;width:100px;"><font id="persionamount"></font></dd>
								<dt style="height:26px;width:110px;">个人收费总笔数 : </dt>
								<dd style="height:26px;width:100px;"><font id="persion_count"></font></dd>
								<dt style="height:26px;width:100px;">退费总额 : </dt>
								<dd style="height:26px;width:100px;"><font id="revoke_amount"></font></dd>
								<dt style="height:26px;width:100px;">退费总笔数 : </dt>
								<dd style="height:26px;width:100px;"><font id="revoke_count"></font></dd>
							</dl>
						</div>
		    </fieldset>
		    <div style="margin-top: 5px;;">
		    	<!--<a href="javascript:getmedicalInsuranceDetail();" class="easyui-linkbutton c5" id="qz"   style="width: 150px">获取医保交易明细</a>
		    	交易日期： <input class="easyui-datetimebox" id="start_time" value="<s:property value="model.daily_acc_date1"/>" style="width:150px;height:26px;"/>
    			至	<input class="easyui-datetimebox" id="end_time" value="<s:property value="model.daily_acc_date2"/>"  style="width:150px;height:26px;"/>-->
		    </div>
		    <fieldset style="height:620px;">
		    	<legend><strong>请求及响应日志</strong></legend>
                <div style="height: 49%;width: auto">
                    <textarea style="width: 99%;resize:none;height: 99%;font-size:14px;" readonly="readonly" id="yb_req" ></textarea>
                </div>
                <div style="height: 49%;width: auto">
                    <textarea style="width: 99%;resize:none;height: 99%;font-size:14px;" readonly="readonly" id="yu_reqs" ></textarea>
                </div>
		    </fieldset>
	    </div>
    </div>
</div>
<div id="dlg-psersion" class="easyui-dialog"  data-options="width: 1000,height: 550,closed: true,cache: false,modal: true,title:'消费明细'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
		<div style="height:450px;width:950px;margin-left:20px;">
			<div class="easyui-layout" border="false" fit="true">
				<div data-options="region:'center'" title="个人消费明细">
					<table id = "persioncitycharginglist"></table>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-psersion').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>

<div id="dlg-card" class="easyui-dialog"  data-options="width: 1000,height: 550,closed: true,cache: false,modal: true,title:'卡消费明细'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
		<div style="height:450px;width:950px;margin-left:20px;">
			<div class="easyui-layout" border="false" fit="true">
				<div data-options="region:'center'" title="会员卡消费明细">
					<table id = "cardcitycharginglist"></table>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-card').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>