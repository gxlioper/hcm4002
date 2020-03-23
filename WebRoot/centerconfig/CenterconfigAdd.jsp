<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$("#center_type").combobox({
		url :'getDatadis.action?com_Type=XTPZLX',
		//editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'data_code_children',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				$('#center_type').combobox('setValue', data[0].data_code_children);
			}
		}
	})

});
	
	function savecenterconfig(){
		
		if($('#center_name').val() == ''){
			alert('体检中心名称不能为空!');
			return false;
		}else if($('#config_key').val() == ''){
			alert('配置KEY不能为空!');
			return false;
		}else if($('#config_value').val()  == ''){
			alert('配置VALUE不能为空!');
			return false;
		}else if($('#is_active').combobox('getValue') == ''){
			alert('是否生效不能为空!');
			return false;
		}else if($('#common').val() == ''){
			alert('配置说明不能为空!');
			return false;
		}else if($('#center_type').combobox('getValue') == ''){
			alert('配置类型不能为空!');
			return false;
		}
		
			 var model={
				center_name:$('#center_name').val(),//值
				config_key:$('#config_key').val(),
				config_value:$('#config_value').val(),
				is_active:$('#is_active').combobox('getValue'),
				common:$('#common').val(),
				center_type:$('#center_type').combobox('getValue')
			} 
					
			$.ajax({
				url:'savacenterconfig.action',
				data:model,
				type:'post',
				dataType:'text',
				success:function(text){
					if(text.split("-")[0]=='ok'){
		         		  $.messager.alert("操作提示", text);
		         		  $("#dlg-edit").dialog("close");
		         		 var _parentWin = window.opener;
							_parentWin.getGrid(); 
							window.close()
		         	  }else if(text.split("-")[0]=='error'){
		         		  $.messager.alert("操作提示", text,"error");
		         	  }
					
				}
			});
}
 	
</script>	
	<fieldset style="margin:5px;padding-right:20px;height:90%;">
	<legend><strong>新增系统配置</strong></legend>
<div class="formdiv">
			     <dl>
	    	       <dt>体检中心</dt>
	    	       <dd><input id="center_name" name="center_name" value="<s:property value="center_name"/>"  class="textinput"></dd>
	    	     </dl>
	    	     <dl>
	    	      	<dt>配置KEY</dt>
	    	      	<dd><input id="config_key" name="config_key" value="<s:property value="config_key"/>"  class="textinput"></dd>
	    	      </dl>
	    	      <dl>
	    	      	<dt>配置VALUE</dt>
	    	      	<dd><input id="config_value" name="config_value" value="<s:property value="config_value"/>"  class="textinput"></dd>
	    	      </dl>
	    	      <dl>
	    	       <dt>是否生效</dt>
	    	       <select id="is_active" class="easyui-combobox" name="is_active" style="width:145.267px;height:26.6px">   
					    <option value="Y">有效</option>   
					    <option value="N">无效</option>   
					</select>  
	    	     </dl>
	    	     
	    	     <dl>
	    	      <dt>配置说明</dt>
	    	       <dd><input id="common" name="common" value="<s:property value="common"/>"  class="textinput" ></dd>
	    	    </dl>
	    	
	    	    <dl>
	    	    	 <dt>配置类型</dt>
	    	       <select id="center_type" class="easyui-combobox" name="center_type" style="width:145.267px;height:26.6px" data-options="panelHeight:130">   
					  
					</select>  
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="savecenterconfig();" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

