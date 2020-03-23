<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
 <script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitrecord/addcrmvisitrecord.js?randomId=<%=Math.random()%>"></script>
 <script type="text/javascript">
 var dahname ='<s:text name="dahname"/>';  
 var tjhname ='<s:text name="tjhname"/>'; 
 </script>
 
<fieldset style="margin: 10px;">
	<legend><strong>健康计划编辑</strong></legend> 
	
	<form id="add1Form">
      <div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
	      <dl>
			<dt style="width:100px;">姓名:<input type="hidden" id="cvr_id"/></dt>
			<dd><input  type="text" class="textinput"  id="addusername" disabled="disabled" class="easyui-validatebox"  
					style="height: 18px;width:210px" />
			</dd>
			<dt style="width:100px;">健康计划编码 : <input type="hidden" id="addplanid"/></dt>
			<dd>
				<input   type="text" maxlength="45" id="addvisit_num" disabled="disabled"
				class="textinput"	style="height: 18px; width: 210px;"/>
			</dd>	
		</dl>
		<dl>
			<dt style="width:100px;"><s:text name="tjhname"/>:</dt>
			<dd><input  class="textinput"  id="addexam_num" disabled="disabled"
				class="easyui-validatebox" style="height: 18px; width: 210px;" />
			</dd>
			<dt style="width:100px;"><s:text name="dahname"/>:</dt>
			<dd><input  type="text" class="textinput"  id="addarch_num" disabled="disabled"
				 class="easyui-validatebox"  style="height: 18px;width:210px" />
			</dd>
		</dl>
		<dl>
		    <dt style="width:100px;">回访方式 :</dt>
			<dd><input  class="easyui-combobox"  id="addvisit_type"  data-options="panelHeight:'auto'"
			  class="easyui-validatebox" style="height: 26px; width: 215px;" />
			</dd>
			<dt style="width:100px;">电话:</dt>
			<dd><input  type="text" class="textinput"  id="phone" readonly="readonly" class="easyui-validatebox"  
					style="height: 18px;width:210px" />
			</dd>
		</dl>
		<dl>
		<dt style="width:100px;">客户反馈信息 : </dt>
			<dd>
				<textarea   id="addcustomer_feedback" class="textinput"	style="height: 60px; width: 550px;"></textarea>
			</dd>	
		</dl>	
		<dl>
		<dt style="width:100px;">健康建议 : </dt>
			<dd>
				<textarea   id="addhealth_suggest" class="textinput"	style="height: 60px; width: 550px;"></textarea>
			</dd>	
		</dl>		    
	</div>
	</div>
</form>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	<a href="javascript:shifang();" class="easyui-linkbutton c6" style="width:80px;">失访</a>
	    <a href="javascript:addCrmVisitRecord();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
