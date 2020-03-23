<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		
		$("#type_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
		$("#type_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
   	});

 	
</script>	
	<fieldset style="margin:5px;padding-right:20px;height:90%;">
	<legend><strong>人员类型</strong></legend>
<div class="formdiv">
	    	    <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
	    	       		类型编码</dt>
	    	       <dd><input id="type_code" name="type_code" value="<s:property value="type_code"/>"  class="textinput" data-options="prompt:'輸入类型编码'"></dd>
	    	       <dt  class="" style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	      </dl>
	    	      <dl>
	    	       <dt>类型名称</dt>
	    	       <dd><input id="type_name" name="type_name" value="<s:property value="type_name"/>"  class="textinput" data-options="prompt:'輸入类型名称'" ></dd>
	    	     </dl>
	    	     <dl>
	    	       <dt>类型备注</dt>
	    	       <dd><input id="type_comment" name="type_comment" value="<s:property value="type_comment"/>" style="width: 260px" class="textinput" ></dd>
	    	     </dl>
	    	     </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="saveCtms();" >保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#ctms_edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

