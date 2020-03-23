<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/djtexambaodaoshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<input type="hidden" id="id_num" value="<s:property value="model.id_num"/>">

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检信息列表</strong></legend>
      <table id="djtexambaodaolistshow" class="easyui-datagrid" >
      </table>	
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addcustomerdo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close');">关闭</a>
	</div>
</div>