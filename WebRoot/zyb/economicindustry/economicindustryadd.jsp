<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$("#industry_name_a").validatebox({
		required:true,
		validType:'maxLength[30]'
	});
	$("#industry_code_a").validatebox({
		required:true,
		validType:'maxLength[30]'
	});
	$("#industry_code_a").blur(function(){
			$.ajax({
				url:'getIndustrycode.action?industry_code='+$("#industry_code_a").val(),
				type:'post',
				success:function(data){
					// var obj=eval('('+data+')');
					if(data=='no'){
						$("#message").attr('value','no');
						$("#message").html('该编码已存在');
						return true;
					}else if(data=='ok'){
						$("#message").attr('value','ok');
						$("#message").html('');
						return false;
					}
				}
			})
	});
	function saveIndustry_f(){
		 if (document.getElementById("industry_name_a").value ==""){
			$("#industry_name").focus();
			return;
		}else if(document.getElementById("industry_code_a").value ==""){
			$("#industry_code_a").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#industry_code_a").focus();
			return;
		}
		$.ajax({
			url:'saveIndustry_f.action', 
		    type: "post",
			data:{
				industry_name:$("#industry_name_a").val(),
				industry_code:$("#industry_code_a").val(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 $("#edit_add").dialog("close");
			  	 getData();
			    },
			 error:function(){
			    	$("#edit_add").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	}
</script>
<fieldset style="margin:5px; height:90%;">
		<legend><strong>经济行业信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt>行业名称</dt>
	    	       <dd><input id="industry_name_a" name="industry_name_a"  class="textinput"  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>行业编码</dt>
	    	       <dd><input id="industry_code_a" name="industry_code_a"   class="textinput" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 260px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	   
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="saveIndustry_f();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_add').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>