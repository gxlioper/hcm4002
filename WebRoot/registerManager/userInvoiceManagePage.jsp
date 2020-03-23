<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发票号段维护</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/userInvoiceManagePage.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden"  id="is_use_all_s" value="<s:property value="is_use_all"/>"  />
<input type="hidden"  id="user_invoice_num_length" value="<s:property value="user_invoice_num_length"/>"  />
	 <div class="easyui-layout" border="false" fit="true">
 		<div data-options="region:'center'" border="false" style="margin-top:10px;margin-bottom:10px;">
            <table id="userinvoicelist"></table>
      	</div>
      </div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 450,height: 392,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fplx" class="easyui-dialog"  data-options="width: 400,height: 180,closed: true,cache: false,modal: true,title:'启用发票类型'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 60px;" >发票类型：</dd>
				<dd><select style="width:150px;height:26px;" id="fplx_q" class="easyui-combobox" data-options="panelHeight:'auto'">
				</select><strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fplx();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fplx').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>