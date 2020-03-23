<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmsigntracking/addcrmsigntracking.js?randomId=<%=Math.random()%>"></script>

<fieldset style="margin: 20px;">
	<legend><strong>新增</strong></legend> 
	<div class="user-query" style=" margin-top: 10px" >
		<dl>
			<dt>
				签单计划：
			</dt>
			<dd>
				<input type="text" class="textinput"  id="addsign_num" style="width: 204px;" />
			</dd>
			<dt>
				跟踪时间 ：
			</dt>
			<dd>
			<input type="text" class="easyui-datetimebox" id="addtracking_date" value="<s:property value="tracking_date"/>" style="height:26px;width:205px;" data-options="disabled:true"/>
			</dd>
		</dl>
		<dl>
			<dt>
				联系人姓名 ：
			</dt>
			<dd>
				<input  class="textinput"  id="addscontact_name" style="width: 203px;"/>
			</dd>
			<dt>
				联系人电话 ：
				</dt> 
			<dd>
			<input class="textinput" id="addphone" style="width:200px;"/>
			</dd>
		</dl>	
		<dl style="height: 110px;">
		<dt>沟通内容： </dt>
			<dd>
			<textarea id="addtracking_content" class="textinput"	style="height: 100px; width: 520px;"></textarea>
			</dd>	
			</dl>
		<dl>		    
			<dt>备注：</dt>
			<dd>
				<textarea id="addremark" class="textinput"	style="height: 100px; width:520px;"></textarea>
			</dd>				
		</dl>	
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addCrmSignTracking();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
