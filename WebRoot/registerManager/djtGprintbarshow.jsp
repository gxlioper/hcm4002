<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#mainbar {	width: auto;	height: auto;}
#leftbar {	width: 60%;	height: auto;}
#rightbar {	width: 35%;	height: auto;	margin-left: 10px;}
#leftbar, #rightbar {	float: left;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/djtGprintbarshow.js?randomId=<%=Math.random()%>"></script>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">

<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.djd_code_url"/>">
<fieldset style="margin: 5px; padding-right: 0;">
<div align="center">
	<div class="inner-button-box">
	    <input name="isprintdah_z" type="checkbox" value="1"/>打印档案号
	    <a href="javascript:new_printBar();" class="easyui-linkbutton" style="height:26px;width:50px;">打印</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="height:26px;width:50px;" onclick="javascript:$('#dlg-show').dialog('close');">关闭</a>
	</div>  
</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>打印条码</strong>
	</legend>
	<div id="mainbar">
		<div id="leftbar">
			<div style="width:100%;"  id="lextitemlist"></div>
		</div>

		<div id="rightbar">
			<div style="width:100%;"  id="pacsitemlist"></div>
		</div>

	</div>

</fieldset>
