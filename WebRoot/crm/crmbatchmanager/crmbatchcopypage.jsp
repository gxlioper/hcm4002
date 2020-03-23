<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmbatchmanager/crmbatchcopypage.js?randomId=<%=Math.random()%>"></script>
<input  id="sign_numes" value="<s:property value="sign_num"/>" hidden="true"/>
<fieldset style="margin:5px;">
    <legend><strong>签单计划信息</strong></legend>
    <table id="company_info_list"></table>
</fieldset>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:savePlan();">确定选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
<div id="sousuo_input">
	签单计划名称:<input class="textinput" onkeyup="pcompanyReload()" id="serch_comname" style="width:150px;height:26">
	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:26px;" onclick="javascript:pcompanyReload();">查询</a>
</div>

