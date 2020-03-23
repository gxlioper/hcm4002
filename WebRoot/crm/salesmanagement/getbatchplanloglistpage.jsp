<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/getbatchplanloglistpage.js?randomId=<%=Math.random()%>"></script>
 <input id="ids" value='<s:property value="id"/>' hidden="true">
  <input id="type" value='<s:property value="type"/>' hidden="true">
  <fieldset style="margin:5px;padding-right:0;">
<legend><strong>日志跟踪</strong></legend>

      <table id="batchplanloglist">
      </table>	
 </fieldset>

 <div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-show').dialog('close')">关闭</a>
	</div>
</div>


