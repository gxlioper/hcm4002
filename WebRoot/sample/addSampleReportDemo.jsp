<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function (){
	$('#addname').validatebox({
		required : true,
	})
	$('#addnum').validatebox({
		required : true,
		validType:'CHS'
	})
	$("#addnum").blur(function() {
		addnum();
	})
	$('#addseq').validatebox({
			validType:'IsNumber',
	})
	$('#addseq').blur(function(){
		$('#addseq').validatebox({
			validType:'IsNumber',
		})
	})
	if($('#addid').val()!=0){
		$('#addnum').attr('type','hidden');
		$('#updata').text($('#addnum').val());
	}
	var a="<s:property value='isPrint_BarCode'/>";
	if(a=='0'){
		$('#isPrint_BarCodeN').attr("checked",true);
		$('#isPrint_BarCodeY').attr("checked",false);
	} 
})
</script>
 <fieldset style=" margin: 10px;">
	<legend><strong>报告样本编辑</strong></legend> 
	<div class="formDiv" style=" width:540px; margin-top: 15px">
		<dl>
			<dt>
				样本编码:
			</dt>
			<dd>
				<input type="hidden" id="addid" value="<s:property  value="id"/>"/>
				<span id="updata"></span>
				<input  type="text" id="addnum" maxlength="45" disabled="true"
					style="height: 26px; width: 244px;" class="textinput" 
					class="easyui-validatebox"   value="<s:property  value="demo_num"/>" />
					&nbsp;&nbsp;&nbsp;&nbsp;<font color="ff0000"><span  id="ssnum"></span></font>
			</dd>
		</dl>
		<dl >
			<dt>
				样本名称 :
			</dt>
			<dd>
				<input   type="text" id="addname"  maxlength="45" 
				 	value="<s:property value="demo_name"/>" 
					style="height: 26px; width: 244px;"  class="textinput"/>
			</dd>
		</dl>
		<dl>
			<dt>打印顺序:</dt>
			<dd>
				<input  type="text" class="textinput" maxlength="4" id="addseq"
					value="<s:property value="print_seq"/>" class="easyui-validatebox"  
					style="height: 26px; width: 244px;" />
			</dd>
		</dl>
		<dl >		    
			<dt>备注</dt>
			<dd>
				<input   type="text" maxlength="45" id="addremark"
				value="<s:property value="remark"/>"  class="textinput"	style="height: 26px; width: 244px;"/>
			</dd>			
		</dl>
	</div>
</fieldset>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addSampleReportDemo();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>