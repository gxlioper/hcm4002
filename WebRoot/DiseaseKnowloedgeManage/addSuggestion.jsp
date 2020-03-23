<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('#sex,#disease_suggestion_d,#default_value').validatebox({
			required : true
		})
		$("#minAge,#maxAge").validatebox({
			//required:true,
			validType:'IsNumber'
		});
		$("#sex").combobox({
			data:[{'id':'全部','text':'全部'},{'id':'男','text':'男'},{'id':'女','text':'女'}],
			editable : true, //不可编辑状态
			cache : false,
			height:27,
			width:143,
			panelHeight:'auto',
			valueField : 'id',
			textField : 'text',	
	        onLoadSuccess : function(data){
	        	 var sex= '<s:property value="sex"/>';
				 if(sex != ''){				
					 $('#sex').combobox('setValue', sex);	
				 }else{
					 $('#sex').combobox('setValue', data[0].id);
				 }
			}
		});
		$("#default_value").combobox({
			data:[{'id':'N','text':'否'},{'id':'Y','text':'是'}],
			editable : true, //不可编辑状态
			cache : false,
			height:27,
			width:143,
			panelHeight:'auto',
			valueField : 'id',
			textField : 'text',	
	        onLoadSuccess : function(data){
	        	 var default_value= '<s:property value="default_value"/>';
				 if(default_value != ''){				
					 $('#default_value').combobox('setValue', default_value);	
				 }else{
					 $('#default_value').combobox('setValue', data[0].id);
				 }
			}
		});
	});

	//建议表数据保存
	function f_saveSuggestion(){
		if($("#disease_suggestion_d").val() == ''){
			$("#disease_suggestion_d").focus();
			return;
		}
	$.ajax({
		url:'saveSuggestion.action',  
		data:{
			sug_id:$("#sug_id").val(),
			disease_id:$("#disease_id").val(),
			disease_num:$("#sug_disease_num").val(),
			sex:$("#sex").combobox('getValue'),
			minAge:$("#minAge").val(),
			maxAge:$("#maxAge").val(),
			default_value:$("#default_value").combobox('getValue'),
			disease_suggestion:$("#disease_suggestion_d").val()
		  },          
		type: "post",  
		  success: function(data){  
		  	 $.messager.alert('提示信息', data);
		  	 $("#dlg-add").dialog("close");
		  	getsuggestionGrid($("#disease_id").val());
		    },
		    error:function(){
		    	 $("#dlg-add").dialog("close");
		    	$.messager.alert('提示信息', "用户操作失败！",function(){});
		    }  
	});
}
		
</script>
	<fieldset style="margin:5px;padding-right:20px;">
	<legend><strong>健康建议维护</strong></legend>
  		<div class="formdiv">
  				<input type="hidden" id="disease_id" value="<s:property value="disease_id" />"/>
  				<input type="hidden" id="sug_id" value="<s:property value="sug_id"/>"/>
  				<dl>
  					<dt>疾病编码</dt>
  					<dd><input id="sug_disease_num" value="<s:property value="disease_num"/>"  class="textinput" disabled="disabled"></dd>
  					<dt>疾病名称</dt>
  					<dd><input id="sug_disease_name" value="<s:property value="disease_name"/>"  class="textinput" disabled="disabled"></dd>
  				</dl>
  				<dl>
  					<dt>最小年龄</dt>
  					<dd><input id="minAge" value="<s:property value="minAge" />" class="textinput" ></dd>
  					<dt>最大年龄</dt>
  					<dd><input id="maxAge" value="<s:property value="maxAge" />" class="textinput" ></dd>
  				</dl>
	    	    <dl>
	    	       <dt>适用性别</dt>
	    	       <dd ><select id="sex"></select></dd>
	    	       <dt>默认值</dt>
	    	       <dd><select id="default_value"></select></dd> 
	    	    </dl>
	    	     <dl>
	    	    	<dt>健康建议</dt>
	    	       <dd>
	    	       	<textarea style="width:80%;resize:none;" cols="66" rows="4" id="disease_suggestion_d" ><s:property value="disease_suggestion"/></textarea>
	    	       </dd>
	    	    </dl>
	    	   </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box" >
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="f_saveSuggestion();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-add').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

