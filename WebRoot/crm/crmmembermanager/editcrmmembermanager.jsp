<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmmembermanager/addcrmmembermanager.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<fieldset style="margin: 30px;">
	<legend><strong>设置会员等级</strong></legend> 
	<div class="user-query" style=" margin-top: 15px" >
		<dl>
			<dt>
				<s:text name="dahname"/>:
			</dt>
			<dd>
				<input  type="text" class="textinput"  id="editarch_num" 
					value="<s:property value="arch_num"/>" class="easyui-validatebox"   disabled="true"
					style="height: 15px; width: 200px;" />
			</dd>
			<dt>
				姓名 :
			</dt>
			<dd>
				<input  class="textinput"  id="edduser_name"   disabled="true"
					 class="easyui-validatebox"  value="<s:property  value="user_name"/>"
					style="height: 16px; width: 203px;"/>
			</dd>
		</dl>
		<dl>
		<dt>
				会员等级 :
			</dt>
		<dd>
		<input  class="easyui-combobox"  id="editlevel"  
					 class="easyui-validatebox" value="<s:property  value="level_name"/>"
					style="height:25px; width: 207px;"/>
		</dd>
					
		</dl>	
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:editCrmMember();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
