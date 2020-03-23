<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/visitEdit.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	var visit_result = "<s:property value='visit_result'/>";
	$("input[name='visit_result'][value='"+visit_result+"']").attr("checked",true);
	
	var visit_type = "<s:property value='visit_type'/>";
	if(!visit_type) {
		visit_type = '5';
	}
	$('#visit_type').combobox("select", visit_type);
});
</script>

<input type="hidden" id="recordID" value="<s:property value="recordID"/>"/>
<fieldset style="margin: 10px;">
	<legend><strong>回访登记</strong></legend> 
	<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
		<dl>
			<dt style="width:100px;"><s:text name="tjhname"/></dt>
			<dd>
				<input  type="text" class="textinput" id="examNum" value="<s:property value='exam_num'/>" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;ime-mode: disabled;" />
			</dd>
			<dt style="width:100px;">姓名</dt>
			<dd>
				<input  type="text" class="textinput" id="patient_name" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
			</dd>
		</dl>
		<dl>
		    <dt style="width:100px;"><s:text name="dahname"/></dt>
		    <dd>
		       <input type="text" class="textinput" id="archNum" value="<s:property value='exam_num'/>" disabled="disabled" class="easyui-validatebox" style="height: 18px; width: 205px;" />
		    </dd>
		    <dt style="width:100px;">回访方式 </dt>
		    <dd>
		       <input id="visit_type" style="height: 26px; width: 215px;" />
		    </dd>
		</dl>
		<dl>
		    <dt style="width:100px;">回访结果</dt>
		    <dd>
		    	&nbsp;&nbsp;&nbsp;<input id="success" name="visit_result" type="radio" value="0" checked="checked"/>&nbsp;<label for="success">成功</label>
				&nbsp;&nbsp;&nbsp;<input id="not_success" name="visit_result" type="radio" value="1" />&nbsp;<label for="not_success">不成功</label>
		    </dd>
		</dl>
		<dl>
			<dt style="width:100px;">回访内容<br>（双击）</dt>
			<dd>
				<textarea id="customer_feedback" style="height: 100px; width: 210px;resize: none;"><s:property value="customer_feedback"/></textarea>
			</dd>
			<dt style="width:100px;text-align:center;line-height: 95px;font: bold;">&lt;&lt;</dt>
			<dd>
				<textarea id="HFNR" style="height: 100px; width: 210px;resize: none;" ondblclick="getSelectedText(this, 'customer_feedback');"></textarea>
				<!-- 
		       <select class="easyui-combobox" data-options="panelHeight:'auto'" class="easyui-validatebox"></select>
				 -->
		    </dd>
		</dl>
		<dl>
			<dt style="width:100px;">备注&nbsp;&nbsp;&nbsp;<br>（双击）</dt>
			<dd>
				<textarea id="remark" style="height: 100px; width: 210px;resize: none;"><s:property value="remark"/></textarea>
			</dd>
			<dt style="width:100px;text-align:center;line-height: 95px;font: bold;">&lt;&lt;</dt>
			<dd>
				<textarea id="HFNRBZ" style="height: 100px; width: 210px;resize: none;" ondblclick="getSelectedText(this, 'remark');"></textarea>
		    </dd>
		</dl>
	</div>
  </div>
</form>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:notNeedVisit();" class="easyui-linkbutton c2" style="width:80px;">无需回访</a>
	    <a href="javascript:saveVisit();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#visit_dlg').dialog('close')">关闭</a>
	</div>
</div>
