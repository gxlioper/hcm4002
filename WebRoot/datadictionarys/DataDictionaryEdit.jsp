<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		
		$("#data_code1").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
		$("#data_name1").validatebox({
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
		$("#data_type").combobox({
			url :'queryAllDaDt.action',
			//editable : false, //不可编辑状态
			cache : false,
			//panelHeight : 'auto',//自动高度适合
			valueField : 'data_type',
			textField : 'data_type',
			onSelect: function () {
				$.ajax({
					url:'getDataCode.action?data_type='+$("#data_type").combobox('getValue'),
					type:'post',
					success:function(data){
						var obj=eval("("+data+")");
						$("#data_code1").val(obj.data_code);
					}
				});
	         },
		})
		
	});

 	
</script>	
	<fieldset style="margin:5px;padding-right:20px;height:90%;">
	<legend><strong>数据字典</strong></legend>
<div class="formdiv">
			     <dl>
	    	       <dt>数据类型</dt>
	    	       <dd><input id="data_type" name="data_type" value="<s:property value="data_type"/>"  class="easyui-combobox" data-options="prompt:'请选择或输入数据类型'"></dd>
	    	     </dl>
	    	     <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>类型编码</dt>
	    	       <dd><input id="data_code1" name="data_code1" value="<s:property value="data_code"/>"  class="textinput"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	      </dl>
	    	      <dl>
	    	      	<dt>数据编码</dt>
	    	      	<dd><input id="data_code_children" value="<s:property value="data_code_children"/>"  class="textinput"></dd>
	    	      </dl>
	    	      <dl>
	    	       <dt>数据名称</dt>
	    	       <dd><input id="data_name1" name="data_name1" value="<s:property value="data_name"/>"  class="textinput"></dd>
	    	     </dl>
	    	     
	    	     <dl>
	    	      <dt>顺序码</dt>
	    	       <dd><input id="seq_code" name="seq_code" value="<s:property value="seq_code"/>"  class="textinput" ></dd>
	    	    </dl>
	    	    <dl>
	    	      <dt>适用系统</dt>
	    	       <dd><select id="data_class" class="easyui-combobox" data-options="panelHeight:'auto',width:145,height:27,onLoadSuccess:function(){$('#data_class').combobox('setValue','<s:property value="data_class"/>');}">
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
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

