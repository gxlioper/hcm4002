<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		
		$("#phyexaclass_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#phyexaclass_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#phyexaclass_code").blur(function(){
			if($("#phyexaclass_code").validatebox('isValid')){
				$.ajax({
					url:'isvalidatephyexa.action?phyexaclass_code='+$("#phyexaclass_code").val(),
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html("该编码已存在");
							return true;
						}else if(data=='ok'){
							$("#message").attr('value','ok');
							$("#message").html('');
							return false;
						}
					}
				})
			}
		})
		shifou();
	});
	 function shifou(){
		var isprintcard = '<s:property value="isprintcard"/>';
		 	$("#isprintcard").val(isprintcard);
		var isupload = '<s:property value="isupload"/>';
		    $("#isupload").val(isupload); 
		
	};
	function savephyexacla(){
		 if (document.getElementById("phyexaclass_name").value ==""){
			$("#phyexaclass_name").focus();
			return;
		}else if(document.getElementById("phyexaclass_code").value ==""){
			$("#phyexaclass_code").focus();
			return;
		}else if($("#message").attr('value')=='no'){
			$("#phyexaclass_code").focus();
			return;
		} 
		$.ajax({
			url:'savePhyExaCla.action', 
		    type: "post",
			data:{
				phyexaclassID:$("#phyexaclassID").val(),
				phyexaclass_name:$("#phyexaclass_name").val(),
				phyexaclass_code:$("#phyexaclass_code").val(),
				isprintcard:$("#isprintcard").val(),
				isupload:$("#isupload").val(),
				remark:$("#remark").val(),
				center_num:$("#center_num").val(),
				showorder:$("#showorder").val(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 $("#edit").dialog("close");
			  	 getData();
			    },
			 error:function(){
			    	$("#edit").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
	}
</script>
<fieldset style="margin:5px; height:80%;padding-top:20px;">
		<legend><strong>体检类别信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt><input type="hidden" name="phyexaclassID" id="phyexaclassID" value="<s:property value='phyexaclassID'/>"   />
	    	       	体检类别名称</dt>
	    	       <dd><input id="phyexaclass_name" name="phyexaclass_name"  class="textinput" value="<s:property value='phyexaclass_name'/>" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>体检类别编码</dt>
	    	       <dd><input id="phyexaclass_code" name="phyexaclass_code"   class="textinput" value='<s:property value='phyexaclass_code'/>' ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 240px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    <dl>
	    	       <dt>体检中心编码</dt>
	    	       <dd><input id="center_num" name="center_num"   class="textinput" value='<s:property value='center_num'/>' ></dd>
	    	    </dl>
	    	     <dl>
	    	       <dt>显示顺序</dt>
	    	       <dd><input id="showorder" name="showorder"   class="textinput" value='<s:property value='showorder'/>' ></dd>
	    	    </dl>
	    	     <dl>
	    	     	 <dt>是否打卡</dt>
	    	       <dd> 
		    	       <select style="width:150px;" id="isprintcard" >
		    	       		<option  value="0">是</option>
		    	       		<option  value="1">否</option>
		    	       </select>
	    	       </dd>
	    	    </dl>
	    	     <dl>
	    	     	 <dt>是否上传</dt>
	    	       <dd> 
		    	       <select style="width:150px;" id="isupload" >
		    	       		<option  value="0">是</option>
		    	       		<option  value="1">否</option>
		    	       </select>
	    	       </dd>
	    	    </dl>
	    	   <dl>
	    	       <dt>备注</dt>
	    	       <dd><input id="remark" name="remark"   class="textinput" value='<s:property value='remark'/>' ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    </div>

<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="savephyexacla();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset> 