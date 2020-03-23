<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$("#center_num").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#center_num").change(function(){
			$("#message").html('');
		});
		$("#center_num").blur(function(){
			var flag=$("#center_num").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'isUniqueExam.action?center_num='+$("#center_num").val(),
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html('该体检中心编码已存在');
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
		
		$("#center_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#center_name").change(function(){
			$("#message").html('');
		});
		/*$("#seq_code").validatebox({
			//required:true,
			validType:'IsNumber'
		}); */
		$('#limit_count').validatebox({    
		    required: true,    
		});  
		var photo_function_status = '<s:property value="photo_function_status"/>';
		 if(photo_function_status == 0){
		 	$("#photo_function_status").find('option[value=0]').attr("selected",true);
		 }else if(photo_function_status == 1){
			$("#photo_function_status").find('option[value=1]').attr("selected",true);
		} 
	});
</script>	
	<fieldset style="margin:5px; height:90%;">
		<legend><strong>体检中心信息</strong></legend>
			<div class="formdiv">
	    	   
				<dl>
	    	       <dt>体检中心名称</dt>
	    	       <dd><input id="center_name" name="center_name" value="<s:property value='center_name'/>"  class="textinput"  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value='id'/>"   />
	    	       		体检中心编码</dt>
	    	       <dd><input id="center_num" name="center_num" value="<s:property value='center_num'/>"  class="textinput" ></dd>
	    	       <dt   style="position: absolute; height: 25px; width: 150px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    <dl>
	    	       <dt>体检人数上限</dt>
	    	       <dd>
	    	       		<input	class="textinput"  onkeyup="this.value=this.value.replace(/\D/g,'')"  maxlength="4" onafterpaste="this.value=this.value.replace(/\D/g,'')"	 value="<s:property value='limit_count_s'/>"  id="limit_count" />
	    	       </dd> 
	    	    </dl>
	    	    <dl>
	    	       <dt>拍照权限</dt>
 					<dd> 
		    	       <select style="width:150px;" id="photo_function_status" >
		    	       		<option  value="1">是</option>
		    	       		<option  value="0">否</option>
		    	       </select>
	    	       </dd>	 
	    	     </dl>
	    	  
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_Examsave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

