<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	getresultList();
});
</script>
<div id="ls_tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-ls').dialog('close')">关闭</a>
	</div>
</div>