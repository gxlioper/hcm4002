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
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/healthCareManual.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/healthCare.js?randomId=<%=Math.random()%>"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/aisino.js?randomId=<%=Math.random()%>"></script> --%>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
#shouju_msg label{ width:100px; display:inline-block; text-align:left;}
#shouju_msg input[type=text]{ width:180px;}

#medical_msg label{ width:180px; display:inline-block; text-align:left;}
#medical_msg label1{ width:100px; display:inline-block; text-align:right;}
#medical_msg input[type=text]{ width:120px;}
</style>
</head>
<body>

<!-- 	    <div class="easyui-layout" border="false" fit="true"> -->
<!--    			<div data-options="region:'north'" border="false" style="height:282px;"> -->
						<fieldset style="padding-right:0;background:#ccc;height:600px;width:49%;float:left">
							<legend><strong><h2>医保</h2></strong></legend>
							<div id="medical_msg" style="margin-left:100px;margin-top:20px">
<!-- 								<div style="height: 30px; margin-left: 5px;"> -->
<!-- 									<label>原交易功能代码：</label><input id="funid" type="text" style="width:200px;" class="textinput"/>冲正 -->
								
<!-- 								</div> -->
								<div style="height: 30px; margin-left: 5px;">
									<label>社会保障卡号：</label><input id="aaz500" type="text" style="width:200px;" class="textinput"/>退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>证件号码（社会保障号）：</label><input id="aac002" type="text" style="width:200px;" class="textinput"/>退费  可不填
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>姓名：</label><input id="aac003" type="text" style="width:200px;" class="textinput"/>退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保流水号：</label><input id="akc190" type="text" style="width:200px;" class="textinput"/>都用
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保明细流水号：</label><input id="bke297" type="text" style="width:200px;" class="textinput"/>作废明细
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保收费流水号：</label><input id="aae072" type="text" style="width:200px;" class="textinput"/>冲正  退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>his收费流水号：</label><input id="bke298" type="text" style="width:200px;" class="textinput"/>冲正  退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>作废原因：</label><input id="bae016" type="text" style="width:200px;" class="textinput" />作废明细
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>数据来源：</label><input id="bke241" type="text" style="width:200px;" value="01" class="textinput"/>退费  作废明细
								</div>	
								<div style="height: 30px; margin-left: 5px;">
									<label>医疗就诊方式：</label><input id="aka078" type="text" style="width:200px;" class="textinput"/>冲正 退费
								</div>	
							</div>
							<dl style="margin-top:10px;margin-left:">
								<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:70px;margin-left:100px;" onclick="javascript:authorization();">授权</a></dd>
								<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:70px;margin-left:20px;" onclick="readCard();">读卡</a></dd>
								<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:90px;margin-left:20px;" onclick="javascript:invalidSubsidiary();">作废明细</a></dd>
								<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:70px;margin-left:20px;" onclick="javascript:medicareRefund()">退费</a></dd>
								<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="height: 30px;width:70px;margin-left:20px;" onclick="javascript:checkCorrect()">冲正</a></dd>
							</dl>
						</fieldset>
						<fieldset style="padding-right:0;background:#ccc;height:600px;width:49%;float:right">
							<legend><strong><h2>POS</h2></strong></legend>
							<div id="medical_msg" style="margin-left:100px;margin-top:20px">
								<div style="height: 30px; margin-left: 5px;">
									<label>原交易功能代码：</label><input id="funid" type="text" style="width:200px;" class="textinput"/>冲正
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>社会保障卡号：</label><input id="aaz500" type="text" style="width:200px;" class="textinput"/>退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>证件号码（社会保障号）：</label><input id="aac002" type="text" style="width:200px;" class="textinput"/>退费  可不填
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>姓名：</label><input id="aac003" type="text" style="width:200px;" class="textinput"/>退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保流水号：</label><input id="akc190" type="text" style="width:200px;" class="textinput"/>都用
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保明细流水号：</label><input id="bke297" type="text" style="width:200px;" class="textinput"/>作废明细
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>医保收费流水号：</label><input id="aae072" type="text" style="width:200px;" class="textinput"/>冲正  退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>his收费流水号：</label><input id="bke298" type="text" style="width:200px;" class="textinput"/>冲正  退费
								
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>作废原因：</label><input id="bae016" type="text" style="width:200px;" class="textinput" />作废明细
								</div>
								<div style="height: 30px; margin-left: 5px;">
									<label>数据来源：</label><input id="bke241" type="text" style="width:200px;" value="01" class="textinput"/>退费  作废明细
								</div>	
								<div style="height: 30px; margin-left: 5px;">
									<label>医疗就诊方式：</label><input id="aka078" type="text" style="width:200px;" class="textinput"/>冲正 退费
								</div>	
							</div>
						</fieldset>
</body>
</html>