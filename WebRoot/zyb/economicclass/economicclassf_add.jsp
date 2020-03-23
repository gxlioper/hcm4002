<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$("#economicclass_code_f").validatebox({
		required:true,
		validType:'maxLength[30]'
	});
	$("#economicclass_code_f").blur(function(){
		var flag=$("#economicclass_code_f").validatebox('isValid');
		if(flag){
			$.ajax({
				url:'getBycode.action?economicclass_code='+$("#economicclass_code_f").val(),
				type:'post',
				success:function(data){
					var obj=eval('('+data+')');
					if(obj!=null){
						$("#message").attr('value','no');
						$("#message").html('该编码已存在');
						return true;
					}else if(obj==null){
						$("#message").attr('value','ok');
						$("#message").html('');
						return false;
					}
				}
			})
		}
	});
	$("#economicclass_name_f").validatebox({
		required:true,
		validType:'maxLength[30]'
	});
	function saveEcoclass_f(){
		 if (document.getElementById("economicclass_code_f").value ==""){
			$("#economicclass_code_f").focus();
			return;
		}else if($("#message").attr('value')=='no'){
			$("#economicclass_code_f").focus();
			return;
		}else if(document.getElementById("economicclass_name_f").value ==""){
			$("#economicclass_name_f").focus();
			return;
		} 
		$.ajax({
			url:'saveEcoclass_f.action', 
		    type: "post",
			data:{
				economicclass_name:$("#economicclass_name_f").val(),
				economicclass_code:$("#economicclass_code_f").val(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 $("#editf_add").dialog("close");
			  	 getData();
			    },
			 error:function(){
			    	$("#editf_add").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	}
</script>
<fieldset style="margin:5px; height:90%;">
		<legend><strong>经济类型信息编辑</strong></legend>
			<div class="formdiv">
	    	    <dl>
	    	       <dt>类型编码</dt>
	    	       <dd><input id="economicclass_code_f"  class="textinput" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 240px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
				<dl>
	    	       <dt>类型名称</dt>
	    	       <dd><input id="economicclass_name_f"   class="textinput"  ></dd>
	    	    </dl>
	    	   
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="saveEcoclass_f();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#editf_add').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>