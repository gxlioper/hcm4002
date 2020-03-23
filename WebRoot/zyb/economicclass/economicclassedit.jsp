<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var parentID=$("#parentID").val();
var code=$("#economicclass_code").val();
	$(document).ready(function () { 
		
		$("#eName").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#economicclass_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#economicclass_code").blur(function(){
			var NameCode=$("#economicclass_code").val();
			if(code==NameCode){
				$("#message").attr('value','ok');
				$("#message").html('');
				return false;
			}else{
				$.ajax({
					url:'getBycode.action?economicclass_code='+$("#economicclass_code").val(),
					type:'post',
					success:function(data){
						var obj=eval("("+data+")");
						if(obj==null){
							$("#message").attr('value','ok');
							$("#message").html('');
							return false;
						}else if(obj!=null){
							$("#message").attr('value','no');
							$("#message").html('该编码已存在');
							return true;
						}
						
					}
				})
			}
				
		});
		
		$("#economicclass_name_e").combobox({
			url :'getAll.action',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : '200',//自动高度适合
			valueField : 'economicID',
			textField : 'economicclass_name',
				onLoadSuccess : function(data) {					
					for (var i = 0; i < data.length; i++) {
						if (data[i].economicID == $("#parentID").val()) {
							$('#economicclass_name_e').combobox('setValue', data[i].economicID);
							break;
						} else {
							$('#economicclass_name_e').combobox('setValue', data[0].economicID);
						}
					}
				}
		});
		
	});
		
	
		
	function f_saveEconomic(){
		
		if ($("#eName").val() ==""){
			$("#eName").focus();
			return;
		}else if($("#economicclass_code").val()==""){
			$("#economicclass_code").focus();
			return;
		}else if($("#message").attr('value')=='no'){
			$("#economicclass_code").focus();
			return;
		}
		if(parentID==null || parentID==''){
			$.ajax({
				url:'saveEcoclass_f.action', 
			    type: 'post',
				data:{
					economicID:$("#eID").val(),
					//parentID:$("#economicclass_name_e").combobox('getValue'),
					economicclass_name:$("#eName").val(),
					economicclass_code:$("#economicclass_code").val(),
				  },          
				success: function(data){
				  	 $.messager.alert('提示信息', data);
				  	 $("#edit_dlg").dialog("close");
				  	 getData();
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		}else if(parentID!=null && parentID !=''){
			$.ajax({
				url:'saveEcoclass_f.action', 
			    type: 'post',
				data:{
					economicID:$("#eID").val(),
					parentID:$("#economicclass_name_e").combobox('getValue'),
					economicclass_name:$("#eName").val(),
					economicclass_code:$("#economicclass_code").val(),
				  },          
				success: function(data){
				  	 $.messager.alert('提示信息', data);
				  	 $("#edit_dlg").dialog("close");
				  	 getData();
				    },
				 error:function(){
				    	$("#edit_dlg").dialog("close");
				    	$.messager.alert('提示信息', "用户操作失败！",function(){});
				    }
			})
		}			
	}
</script>
<fieldset style="margin:5px; height:90%;">
		<legend><strong>经济类型信息编辑</strong></legend>
			<div class="formdiv">
	    	    <dl>
	    	       <dt><input type="hidden"  id="eID" value="<s:property value='economicID'/>"   />
	    	       		类型编码</dt>
	    	       <dd>
	    	       <input id="economicclass_code"  value="<s:property value='economicclass_code'/>"  class="textinput" >
	    	       
	    	       </dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 260px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
				<dl>
	    	       <dt>类型名称</dt>
	    	       <dd><input id="eName"  value="<s:property value='economicclass_name'/>"  class="textinput"  ></dd>
	    	    </dl>
	    	    <s:if test="parentID!=null">
	    	    	<s:if test="parentID!=''">
	    	    <dl>
	    	       <dt><input type="hidden"  id="parentID" value="<s:property value='parentID'/>"   />
	    	       	父级单位</dt>
	    	       <dd><input  id="economicclass_name_e"  class="easyui-combobox" data-options="prompt:'请选择父级单位'"></dd>
	    	    </dl>
	    	    	</s:if>
	    	    </s:if>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_saveEconomic();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>