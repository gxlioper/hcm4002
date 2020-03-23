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
<title>健康卡收费</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/health_card_charge.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:62%; top:110px; width:300px;height:100px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input{ width:180px;}
</style>
</head>
<body>
<input type="hidden" id="exam_num" value="<s:property value="exam_num"/>"/>
<input type="hidden" id="exam_id" value="<s:property value="exam_id"/>"/>
	<div class="easyui-layout" border="false" fit="true">
   		<div data-options="region:'west'" style="width:60%;">
   			<table id="witemlist">
      		</table>
		</div>
 		<div data-options="region:'center'" border="false">
        	<table id="cardinfolist"> 
			</table>
      	</div>
      	<div data-options="region:'south'" style="height:55px;">
		<div class="sub_btn">
			<dl style="font-size: 16px;margin-top: 10px;margin-left: 9%;"><dd style="width:150px;">原价：<font id="yuanjia">0</font>元&nbsp;&nbsp;&nbsp;</dd>
				<dd style="font-weight:bold;width:180px;">应收金额：<font id="yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
				<dd style="font-weight:bold;color:#f00;width:180px;">实收金额：<font id="shishou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
				<dd>折扣：</dd><dd><input style="width:40px;" type="text" id="zongzhekou" disabled="disabled" value="10"/>折</dd>
			</dl>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;margin-left: 10%;" onclick="javascript:savecollectFees();">收费</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;" onclick="javascript:close_page();">关闭</a>
		</div>
	</div>
	</div>
<div id="toolbar">
	<div class="user-query">
						<dl>
							<dd>卡号：<input type="text" maxlength="20" style="width:85px" id="ser_card_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;" onclick="javascript:getCardInfoBynum();">查询</a></dd>
						</dl>
					</div>
</div>
</body>
</html>