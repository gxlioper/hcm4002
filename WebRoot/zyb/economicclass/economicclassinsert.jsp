<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () { 
		
		$("#economicclass_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#economicclass_code_i").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#economicclass_code_i").blur(function(){
				$.ajax({
					url:'getBycode.action?economicclass_code='+$("#economicclass_code_i").val(),
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
			});
		});
		$("#economicclass_name_i").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#economicclass_name_i").combobox({
			url :'getAll.action',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : '200',//自动高度适合
			valueField : 'economicID',
			textField : 'economicclass_name'
		});
		
	});
		
	
		
	function saveEconomic(){
		if (document.getElementById("economicclass_name").value ==""){
			$("#economicclass_name").focus();
			return;
		}else if(document.getElementById("economicclass_code_i").value ==""){
			$("#economicclass_code_i").focus();
			return;
		}else if($("#message").attr('value')=='no'){
			$("#economicclass_code_i").focus();
			return;
		}else if($("#economicclass_name_i").combobox('getValue').trim()==''){
			//$("#economicclass_name_f").focus();
			$('#economicclass_name_f').combobox().next('span').find('input').focus();
			return;
		}
		
			$.ajax({
				url:'saveEcoclass_f.action', 
			    type: "post",
				data:{
					economicID:$("#economicID").val(),
					parentID:$("#economicclass_name_i").combobox('getValue'),
					economicclass_name:$("#economicclass_name").val(),
					economicclass_code:$("#economicclass_code_i").val(),
				  },          
				success: function(data){
				  	 $.messager.alert('提示信息', data);
				  	 $("#edit_insert").dialog("close");
				  	 getData();
				    },
				 error:function(){
				    	$("#edit_insert").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		
	}
</script>
<fieldset style="margin:5px; height:90%;">
		<legend><strong>经济类型信息编辑</strong></legend>
			<div class="formdiv">
	    	    <dl>
	    	       <dt><input type="hidden"  id="economicID"    />
	    	       	类型编码</dt>
	    	       <dd><input id="economicclass_code_i"   class="textinput" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left:260px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
				<dl>
	    	       <dt>类型名称</dt>
	    	       <dd><input id="economicclass_name"    class="textinput"  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>父级单位</dt>
	    	       <dd><input id="economicclass_name_i" required="true" data-options="prompt:'请选择父级单位'"></dd>
	    	    </dl>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="saveEconomic();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_insert').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>