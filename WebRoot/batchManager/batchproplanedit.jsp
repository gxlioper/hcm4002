<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/batchManager/batchproplanedit.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="id" value="<s:property value="model.id"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="exam_number" value="<s:property value="model.exam_number"/>">
<div class="easyui-layout" fit="true">
<div data-options="region:'center'" style ="height:320px;width:750px;">	
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选体检任务</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 70px;">单位名称</dd>
			<dd style="height: 20px; width: 200px;"><s:property value="model.comname" /></dd>
			<dd style="height: 20px; width: 90px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 200px;"><s:property value="model.batch_name" /></dd>
			<dd style="height: 20px; width: 100px;">体检任务总人数：</dd>
			<dd style="height: 20px; width: 40px;"><s:property value="model.exam_number" /></dd>
			
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>排期基本条件</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>计划开始日期</dt>
			<dd><input class="easyui-datebox"   type=text id="startdate" style="width:100px;height:26px;"></input></dd>
			<dt>计划结束日期</dt>
			<dd><input class="easyui-datebox"   type=text id="enddate" style="width:100px;height:26px;"></input></dd>
			<dt>日体检人数</dt>
			<dd><input class="easyui-textbox" type="text" id="daynum" value="20" style="height: 26px; width: 50px;" /></dd>
			<dd style="height: 20px; width: 50px;"><a href="javascript:paiqistart();" class="easyui-linkbutton c6" style="width:100px;">排期</a></dd>			
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>排期列表</strong>
	</legend>
	<div id="batchproplanlist"></div>
	
</fieldset>
</div>
<div data-options="region:'south'" style="height:50px;">
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:paiqiadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-editpro').dialog('close')">关闭</a>
	</div>
</div>
</div>
</div>
