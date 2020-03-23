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
<title>退费</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/refundFees.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:650px; top:40px; width:300px;height:100px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input{ width:180px;}
</style>
</head>
<body>
<input type="hidden" id="invoiceprinttype"  value="<s:property value="invoiceprinttype"/>">
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',split:true" style="width:20%;">
		<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:85px" id="ser_num" name="ser_num" class="textinput"/></dd>
							<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
						</dl>
					</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt>姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name"></dt></dl>
						<dl><dt>性别：</dt><dt id="sex"></dt></dl>
						<dl><dt>年龄：</dt><dt id="age"></dt></dl>
						<dl><dt>单位：</dt><dt id="company"></dt></dl>
						<dl><dt>人员类型：</dt><dt id="customer_type"></dt></dl>
						<dl><dt>体检套餐：</dt><dt id="set_name"></dt></dl>
					</div>
			</fieldset>
	</div>
    <div data-options="region:'center'">
     			<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        			<div title="已开发票收据项目列表" style="padding:5px;" >
            			<table id="ykfpList">
      					</table>
        			</div>
       				<div title="已收费未开发票项目列表" style="padding:5px;">
   						<table id="yksjList">
      					</table>
        			</div>
    	</div>
	</div>
</div>
<div id="toolbar">
		<div style="width: 250px; float: left; font-size: 18px;font-weight:bold; margin: 5px;">已开发票总金额：<font id="yishou">0</font>元&nbsp;&nbsp;&nbsp;</div>
		<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; color: #f00; margin-top: 5px;">应退金额：<font id="yintui">0</font>元&nbsp;&nbsp;&nbsp;</div>
		<div><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;" onclick="javascript:tuifei_show(1);">退费</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;" onclick="javascript:qvxiaoshenqing('ykfpList');">撤销LIS/PACS申请</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:jiaofeishengqi();">缴费申请</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:zuofeifapiao();">退费发票</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:chexiaoshoufei(1);">撤销收费</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:90px;" onclick="javascript:cksfjl()">收费记录</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:130px;" onclick="javascript:pt_point()">补打普通收费单</a>
		</div>
</div>
<div id="toolbar1">
		<div style="width: 250px; float: left; font-size: 18px;font-weight:bold; margin: 5px;">已收费总金额：<font id="yishou_sj">0</font>元&nbsp;&nbsp;&nbsp;</div>
		<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; color: #f00; margin-top: 5px;">应退金额：<font id="yintui_sj">0</font>元&nbsp;&nbsp;&nbsp;</div>
		<div><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:70px;" onclick="javascript:tuifei_show();">退费</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;" onclick="javascript:qvxiaoshenqing('yksjList');">撤销LIS/PACS申请</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:jiaofeishengqi();">缴费申请</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:90px;" onclick="javascript:fapiao_show();">补打发票</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:bd_mx_point();">补打收费明细</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:chexiaoshoufei();">撤销收费</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:90px;" onclick="javascript:cksfjl()">收费记录</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:130px;" onclick="javascript:pt_point()">补打普通收费单</a>
			 </div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 900,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="detail" class="easyui-dialog" data-options="width: 900,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-tuifei" class="easyui-dialog" data-options="width: 600,height: 420,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fapiao" class="easyui-dialog" data-options="width: 600,height: 458,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-show_sfjl" class="easyui-dialog"  data-options="width: 1200,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>