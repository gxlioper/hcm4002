<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/datamigration/databaseconfig.js?randomId=<%=Math.random()%>"></script> 
<form id="add1Form">
<div class="formdiv" style="height:378px;width:1040px;">
	<table id="configList"></table>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 450,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</form>
