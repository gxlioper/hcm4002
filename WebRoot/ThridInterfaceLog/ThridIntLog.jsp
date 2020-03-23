<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ThridInterfaceLog/ThirdIntLog.js?randomId=<%=Math.random()%>"></script>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="0">
<input type="hidden" id="reportexe">
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.djd_code_url"/>">
<input type="hidden" id="reportexeurl" value="<s:property value="model.report_url"/>">
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="batch_id" value="0">
<input type="hidden"  id="bodao_checkebox"  value ="<s:property value='model.bodao_checkebox'/>" />
 <fieldset style="margin:5px;padding-right:0">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
		<dl>
		    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="req_no"/>申请号</dt>					
			<dd><input class="easyui-textbox"  type="text" id="req_no" value="" style="height:26px;width:155px;"/></dd> 
			
			<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_no"/>体检编号</dt>					
			<dd><input class="textinput"  type="text" id="exam_no" value="" style="height:26px;width:155px;"/></dd> 
			
			<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="message_name"/>接口名称</dt>					
			<dd>
				<select class="easyui-combobox" id="message_name" name="message_name" data-options="height:26,width:120,panelHeight:'auto'"></select>
			</dd>
			  
			<%-- <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="message_type"/>消息类型</dt>					
			<dd><select id="message_type" class="easyui-combobox" data-options="height:26,width:100,panelHeight:'auto'">
					<option value="WebService">WebService</option>
					<option value="MQ">MQ</option>
					<option value="socket">socket</option>
				</select>
			</dd> --%>	
			
			<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="flag"/>处理状态</dt>					
			<dd><select id="flag" class="easyui-combobox" data-options="height:26,width:100,panelHeight:'auto'">
					<option value="0">正常</option>
					<option value="1">拒绝</option>
					<option value="2">错误</option>
				</select>
			</dd>
            </dl>
            <dl>
                  <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="message_date"/>开始时间</dt>
	        <dd><input class="easyui-datetimebox" type=text id="message_startDate" style="width:155px;height:26px;"></input>
                  <dt style="height:26px;width:80px;">结束时间</dt>
	        <dd><input class="easyui-datetimebox" type=text id="message_endDate" style="width:155px;height:26px;"></input></dd>
	         
	        <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="sender"/>发送者</dt>					
			<dd><input class="easyui-textbox"  type="text" id="sender" style="height:26px;width:120px;"/></dd>
			
			<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="receiver"/>接收者</dt>					
			<dd><input class="easyui-textbox"  type="text" id="receiver" value="" style="height:26px;width:100px;"/></dd>

			<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
              </dl>
	</div>		
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
 	  <input type="hidden"  id="click_id"  value="0"/>
      <legend><strong>第三方通信日志列表</strong></legend>
      	<table id="groupusershow"></table>	
      	<!-- <table id="messageLogShow"></table> -->
 </fieldset>
 
 <div id="til_viewLogDetail" class="easyui-dialog" data-options="width: 800,height: 550,closed: true,cache: false,modal: true,top:50"></div>
 <div id="til_viewDetailLogDetail" class="easyui-dialog" data-options="width: 800,height: 550,closed: true,cache: false,modal: true,top:50"></div>
  
</body>