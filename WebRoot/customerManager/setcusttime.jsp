<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/setcusttime.js?randomId=<%=Math.random()%>"></script>
<script>

</script>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="addbatch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="ids" value="<s:property value="model.ids"/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员基本信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dt>
				体检日期
			</dt>
			<dd><input class="easyui-datebox" type=text id="datetime" style="width:100px;height:26px;"></input></dd>		
		</dl>
		<dl>
			<dt>
				开始时间
			</dt>
			<dd><input class="easyui-textbox" type="text" id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input></dd>	
			<dt>
				结束时间
			</dt>
			<dd><input  class="easyui-textbox" type="text" id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>			
		</dl>
		<dl>
			<dt style="width:400px;height:26px;">
				注意：1、开始时间和结束时间输入格式如 10:00表示10点00分。
			</dt>
					
		</dl>
	</div>
</fieldset>

<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addcustomerdo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit-time').dialog('close')">关闭</a>
	</div>
</div>