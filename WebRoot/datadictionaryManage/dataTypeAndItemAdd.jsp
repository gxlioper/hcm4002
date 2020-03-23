<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		
		$("#data_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
		$("#data_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#data_type").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
		$("#seq_code").validatebox({
			//required:true,
			validType:'IsNumber'
		});
		
		$("#data_type").change(function(){
			$("#data_type_message").html('');
		});
		
		$("#data_code").change(function(){
			$("#data_code_message").html('');
		});
		
		$("#data_code").blur(function(){
			var flag=$("#data_code").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'data_code_validate.action',
					data:{
						data_code:$("#data_code").val(),
					},
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#data_code_message").attr('value','no');
							$("#data_code_message").html('该数据编码已存在');
							return true;
						}else if(data=='ok'){
							$("#data_code_message").attr('value','ok');
							$("#data_code_message").html('');
							return false;
						}
					}
			});
			}	
		});
		
		$("#data_type").blur(function(){
			var flag=$("#data_type").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'data_type_validate.action',
					data:{
						data_type:$("#data_type").val(),
					},
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#data_type_message").attr('value','no');
							$("#data_type_message").html('该数据类型已存在');
							return true;
						}else if(data=='ok'){
							$("#data_type_message").attr('value','ok');
							$("#data_type_message").html('');
							return false;
						}
					}
			});
			}	
		});
	});

	function savedadt(){
		if (document.getElementById("data_code").value == ''){
			$("#data_code").focus();
			return;
		}else if(document.getElementById("data_name").value == ''){
			$("#data_name").focus();
			return;
		}else if(document.getElementById("data_type").value == ''){
			$("#data_type").focus();
			return;
		}else if(document.getElementById("seq_code").value == ''){
			$("#seq_code").focus();
			return;
		}else if($("#data_code_message").attr('value') == 'no'){
			$("#data_code").focus();
			return;
		}else if($("#data_type_message").attr('value') == 'no'){
			$("#data_type").focus();
			return;
		} 
		$.ajax({
		    url:'saveDaDt.action', 
		    type: "post",
			data:{
				id:$("#id").val(),
				data_code:$("#data_code").val(),
				data_name:$("#data_name").val(),
				data_type:$("#data_type").val(),
				seq_code:$("#seq_code").val(),
				remark:$("#remark").val(),
				data_code_children:$("#data_code_children").val(),
				data_class:$("#data_class").combobox('getValue')
			},          
			success: function(data){  
			  	 $.messager.alert('提示信息', data);
			  	 $("#edit_dlg").dialog("close");
			  	 shuxing($("#data_code").val());
			 },
			 error:function(){
			    	 $("#edit_dlg").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			 }  
		});
	}
</script>	
<fieldset style="margin:5px;padding-right:20px;height:90%;">
	<legend><strong>数据字典</strong></legend>
	<div class="formdiv">
		<input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	    <dl>
			<dt>数据类型</dt>
			<dd><input id="data_type" name="data_type" value="<s:property value="data_type"/>"  class="textinput"></dd>
			<dt  class="" style="position: absolute; height: 25px; width: 120px; margin-left: 310px;"><span  id="data_type_message" class="red"></span></dt>
		</dl>
		<dl>
			<dt>数据编码</dt>
	  		<dd><input id="data_code" name="data_code" value="<s:property value="data_code"/>"  class="textinput"></dd>
			<dt  class="" style="position: absolute; height: 25px; width: 120px; margin-left: 310px;"><span  id="data_code_message" class="red"></span></dt>
		</dl>
		<dl>
			<dt>数据细项编码</dt>
			<dd><input id="data_code_children" value="<s:property value="data_code_children"/>"  class="textinput"></dd>
		</dl>
		<dl>
			<dt>数据名称</dt>
			<dd><input id="data_name" name="data_name" value="<s:property value="data_name"/>"  class="textinput"></dd>
		</dl>
	 	     
		<dl>
			<dt>顺序码</dt>
			<dd><input id="seq_code" name="seq_code" value="<s:property value="seq_code"/>"  class="textinput" ></dd>
		</dl>
		<dl>
			<dt>适用系统</dt>
			<dd><select id="data_class" class="easyui-combobox" data-options="panelHeight:'auto',width:145,height:27,onLoadSuccess:function(){if('<s:property value="data_class"/>'!='' && '<s:property value="data_class"/>'!=null){$('#data_class').combobox('setValue','<s:property value="data_class"/>');}}">
				<option value="0">共用体检系统</option>
				<option value="1">健康体检系统</option>
				<option value="2">职业病体检系统</option>
			</select></dd>
		</dl>
		<dl>
			<dt>备注</dt>
			<dd><input id="remark" name="remark" value="<s:property value="remark"/>" style="width: 260px" class="textinput" ></dd>
		</dl>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="savedadt();" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
		</div>
	</div>
</fieldset>
