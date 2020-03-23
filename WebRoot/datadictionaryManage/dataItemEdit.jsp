<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		
		$("#data_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#seq_code").validatebox({
			//required:true,
			validType:'IsNumber'
		});
		$("#data_name").change(function(){
			$("#message").html('');
		});
		$("#data_name").blur(function(){
			var flag=$("#data_name").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'data_name_validate.action',
					data:{
						id:$("#id").val(),
						data_type:$("#data_type").val(),
						data_code:$("#data_code").val(),
						data_name:$("#data_name").val(),
					},
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html('该数据名称已存在');
							return true;
						}else if(data=='ok'){
							$("#message").attr('value','ok');
							$("#message").html('');
							return false;
						}
					}
			});
			}	
		});
	});

	function savedadt(){
		if(document.getElementById("data_name").value == ''){
			$("#data_name").focus();
			return;
		}else if(document.getElementById("seq_code").value == ''){
			$("#seq_code").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#data_name").focus();
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
				getData($("#data_code").val());
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
			     <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>数据类型</dt>
	    	       <dd><input id="data_type" name="data_type" value="<s:property value="data_type"/>" readonly="readonly" class="textinput"></dd>
	    	     </dl>
	    	     <dl>
	    	       <dt>数据编码</dt>
	    	       <dd><input id="data_code" name="data_code" value="<s:property value="data_code"/>"  readonly="readonly" class="textinput"></dd>
	    	      </dl>
	    	      <dl>
	    	      	<dt>数据细项编码</dt>
	    	      	<dd><input id="data_code_children" value="<s:property value="data_code_children"/>"  class="textinput"></dd>
	    	      </dl>
	    	      <dl>
	    	       <dt>数据名称</dt>
	    	       <dd><input id="data_name" name="data_name" value="<s:property value="data_name"/>"  class="textinput"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 120px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
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

