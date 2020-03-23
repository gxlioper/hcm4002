<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收费员日结</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/insurance.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/posdaily.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/aisino.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden" id="userid"  value="<s:property value="#session.username.userid"/>"  />
<div id="tt" class="easyui-tabs" fit="true">
	<div title="pos未日结列表" style="padding:5px;">
	   <div class="easyui-layout" border="false" fit="true">
		   	<div data-options="region:'north'" style="height:35%;">
		   		<div >
		   			&nbsp;&nbsp;<font size="4"> 收费交易金额：</font><font id="sf_amount" style="font-weight:900;font-style:normal;">0</font>元
		   			&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" size="4"> 退费交易金额：</font><font id="tf_amount" style="font-weight:900;font-style:normal;">0</font>元
		   			&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue" size="4"> 日结金额：</font><font id="rj_amount" style="font-weight:900;font-style:normal;">0</font>元
		   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:130px;" onclick="javascript:shuaxin(0)">刷&nbsp;&nbsp;新</a>
		   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c4" style="width:130px;" onclick="javascript:shuaxin(1)">结&nbsp;&nbsp;算</a>
		   		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c5" style="width:150px;" onclick="javascript:cdfp()">重打pos结算单</a>
		   		</div>
		   		<table id="poshuizongjine" border="false"></table>
		   	</div>
	 		<div data-options="region:'center'" border="false">
	 			<div id="aa" class="easyui-accordion" fit="true">
		 			<div title="pos收费明细列表">
		            	<table id="getposDailyDetailList"></table>
		           </div>
	            </div>
	      	</div>
        </div>
      </div>
      
      <!--<div title="pos已日结列表" style="padding:5px;">
      	<fieldset style="margin:5px;padding-right:0;">
			<legend><strong>信息检索</strong>&nbsp;&nbsp;
			</legend>
		      <div class="user-query">
		      	<dl>
					<dt style="width:120px;">  结账时间：</dt>
					<dd><input class="easyui-datebox" id="daily_acc_date1" value="<s:property value="daily_acc_date1"/>" style="width:150px;height:26px;"/></dd>
         			<dt style="height:26px;width:20px;">至</dt>
         			<dd><input class="easyui-datebox" id="daily_acc_date2" value="<s:property value="daily_acc_date2"/>" style="width:150px;height:26px;"/></dd>
         			<dd><a href="javascript:getendposdailydetail();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:120px;">查&nbsp;&nbsp;询</a></dd>
						
		      	</dl>
		      </div>
		 </fieldset>
      	
      	 <fieldset style="margin:5px;padding-right:0;height: 85%;">
			<legend><strong>信息列表</strong>&nbsp;&nbsp;
			</legend>
		      <table id="yj_posdaily">
		      </table>	
		 </fieldset>
      </div>
      -->
      
      
      <div title="pos已日结列表" style="padding:5px;">
		   <div class="easyui-layout" border="false" fit="true">
		   		<div data-options="region:'north'" style="height:70px;">
	      	 		<fieldset>
	    				<legend><strong>信息检索</strong></legend>
	    					<div class="user-query">
								<dl>
									<dt style="width:120px;">  结账时间：</dt>
									<dd><input class="easyui-datebox" id="daily_acc_date1" value="<s:property value="daily_acc_date1"/>" style="width:150px;height:26px;"/></dd>
				         			<dt style="height:26px;width:20px;">至</dt>
				         			<dd><input class="easyui-datebox" id="daily_acc_date2" value="<s:property value="daily_acc_date2"/>" style="width:150px;height:26px;"/></dd>
				         			<dd><a href="javascript:getendposdailySummary();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:120px;">查&nbsp;&nbsp;询</a></dd>
								</dl>
							</div>
	    			</fieldset>
	      	 	</div>
		 		<div data-options="region:'center',title:'已结账列表'" border="false">
			        <table id="yjmx_posdaily"></table>
		      	</div>
		      	<div data-options="region:'east',title:'流水明细'" style="width:70%;">
		      		<table id="yj_posdaily"></table>
		      	</div>
	      </div>
      </div>

</body>
</html>