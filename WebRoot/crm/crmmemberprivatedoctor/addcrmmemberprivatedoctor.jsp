<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmmemberprivatedoctor/addcrmmemberprivatedoctor.js?randomId=<%=Math.random()%>"></script>

<fieldset style="margin: 10px;">
	<legend><strong>分配私人医生</strong></legend> 
	<div style=" margin-top: 15px" >
		<dl style="height: 35px;padding: 10px 0 10px 30px;">
			<dt>
				为如下体检者分配私人医生:
			</dt>
			<dd style="padding:0 20px 0 20px;" id="show_exam_name">
			</dd>
		</dl>
		<dl  style="height: 35px;padding: 10px 0 10px 30px;">
		<dt>
			选择私人医生 :
			</dt>
			<dd style="padding:0 20px 0 20px;">
				<input  class="easyui-combobox"  id="adddoctor_id"
					 class="easyui-validatebox"  value="<s:property  value="doctor_id"/>"
					style="height: 26px; width: 244px;" />
			</dd>
		</dl>
			
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addCrmMemberPrivateDoctor();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
