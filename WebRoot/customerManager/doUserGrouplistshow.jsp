<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/doUserGrouplistshow.js?randomId=<%=Math.random()%>"></script>
<script>

</script>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="fzids" value="<s:property value="model.ids"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>"> 

<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选体检任务</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 140px;">单位名称</dd>
			<dd style="height: 20px;">
				<s:property value="model.comname" />
			</dd>
			<dd style="height: 20px; width: 140px;">体检任务名称：</dd>
			<dd style="height: 20px; ">
				<s:property value="model.batch_name" />
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检任务下的分组</strong>
	</legend>
	<div class="user-query">
		 <table id="usergrouplist" class="easyui-datagrid" >
      </table>	
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custshow').dialog('close')">关闭</a>
	</div>
</div>