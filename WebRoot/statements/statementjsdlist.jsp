<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/statements/statementjsdlist.js?randomId=<%=Math.random()%>"></script>

<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="">
<input type="hidden" id="batchid" value="<s:property value="model.batchid"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>选择结算单</strong></legend>
      <table id="statementslistshow">
      </table>	
 </fieldset>
 <fieldset style="margin: 5px; padding-right: 0;">
<div align="right">
	<div class="inner-button-box">
			<input type="checkbox" id="is_team_add"/>是否包含附加费用
	        <a href="javascript:void(0)" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:80px;" onclick="javascript:savelist();">确定</a>
	   	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close');">关闭</a>
	</div>
</div>
</fieldset>
