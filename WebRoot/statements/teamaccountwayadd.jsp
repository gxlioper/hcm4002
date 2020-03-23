<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/teamaccountwayadd.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_idss" value="<s:property value="model.batchid"/>">
<input type="hidden" id="account_num" value="<s:property value="model.account_num"/>">
<input type="hidden" id="account_id" value="<s:property value="model.id"/>">
<input type="hidden" id="accountamt" value="<s:property value="model.charges"/>">
 <fieldset style="margin:5px;padding-right:0;">
    <legend><strong>支付方式维护</strong></legend>
<div class="datagrid-header-inner" style="display: block;">
<table id="titem" style="height: 40px;width:300px" border="0" cellspacing="0" cellpadding="0">

</table>
</div>
       <table id="totalamt">
       <tr>
          <td id="ykpamt">应开票金额：<s:property value="model.charges"/>￥</td>
          <td id="syamt">剩余金额：￥</td></tr>
      </table>	
 </fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:teamaccountwayadddo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custadd').dialog('close')">关闭</a>
	</div>
</div>