<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		
		$("#typeofwork_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#typeofwork_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#typeofwork_code").change(function(){
			$("#message").html('');
		});
		$("#typeofwork_code").blur(function(){
			var flag=$("#typeofwork_code").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'isUnique_O.action?typeofwork_code='+$("#typeofwork_code").val(),
					type:'post',
					success:function(data){
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
			});
			}	
		});

	});
	function savetow(){
		 if (document.getElementById("typeofwork_name").value ==""){
			$("#typeofwork_name").focus();
			return;
		}else if(document.getElementById("typeofwork_code").value ==""){
			$("#typeofwork_code").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#typeofwork_code").focus();
			return;
		} 
		$.ajax({
			url:'saveOccutypeofwork.action', 
		    type: "post",
			data:{
				typeofworkID:$("#typeofworkID").val(),
				typeofwork_name:$("#typeofwork_name").val(),
				typeofwork_code:$("#typeofwork_code").val(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 if(!$('#add').is(':checked')){
			  		 $("#edit").dialog("close");
			  	 }else{
			  		$("#typeofwork_name").val(''),
					$("#typeofwork_code").val('')
			  	 }
			  	 getData();
			    },
			 error:function(){
			    	$("#edit").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	};
	 function continuityadd(){
		 alert(123);
		 if($('#add').is(':checked')){
			$('#add').attr('checked',true)
		}else{
			$('#add').attr('checked',false)
		} 
	} 
</script>
<fieldset style="margin:5px; height:80%;padding-top:20px;">
		<legend><strong>工种信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt><input type="hidden" name="typeofworkID" id="typeofworkID" value="<s:property value='typeofworkID'/>"   />
	    	       	工种名称</dt>
	    	       <dd><input id="typeofwork_name" name="typeofwork_name"  class="textinput" value="<s:property value='typeofwork_name'/>" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>工种编码</dt>
	    	       <dd><input id="typeofwork_code" name="typeofwork_code"   class="textinput" value='<s:property value='typeofwork_code'/>' ></dd>
	    	       <dt style="position: absolute; height: 25px; width:100px; margin-left: 290px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	   
	    	    </div>

<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if  test="typeofworkID==null">
				<div style="float:left;padding-left:20px;font-size:16px;" ><input type="checkbox" onclick="continuityadd();" id="add" style="cursor:pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="savetow();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset> 