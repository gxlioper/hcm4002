<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
	#view_detail dl dt{
		font-weight: 600;
		text-align:right;
	}
</style>
	<input type="hidden" id="id" value="<s:property value="model.id"/>">
	<fieldset style="margin: 5px; padding-right: 0;">
		<legend>
			<strong>日志详情</strong>
		</legend>
		<div id="view_detail" class="user-query">
			<dl>
				<dt>日志时间：</dt>
				<dd><s:property value="model.ldate"/></dd>			
			</dl>
			<br/>
			<dl>
				<dt>日志详情：</dt>
				<dd>&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="model.lmessage"/></dd>			
			</dl>
			<br/>
		</div>
	</fieldset>
	
	<div id="search-buttons"  class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:expTxtDetailContent(<s:property value="model.id"/>);" class="easyui-linkbutton c6" style="width:100px;">导出txt文档</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#til_viewDetailLogDetail').dialog('close')">关闭</a>
		</div>
	</div>

