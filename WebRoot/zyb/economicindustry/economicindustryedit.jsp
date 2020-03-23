<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var code=$("#industry_code").val();
var _parentId=$("#_parentId").val();
	$(document).ready(function () { 
		
		$("#industry_name").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#industry_code").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#industry_code").blur(function(){
			var NameCode=$("#industry_code").val();
			if(code==NameCode){
				$("#message").attr('value','ok');
				$("#message").html('');
				return false;
			}else{
				$.ajax({
					url:'getIndustrycode.action?industry_code='+$("#industry_code").val(),
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
			}
			
	});
		$("#industry_name_f").combobox({
			url :'getAllIndustry.action',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : '300',//自动高度适合
			valueField : 'industryID',
			textField : 'industry_name_f'
		});
		
	});
		
	
		
	function f_saveInd(){
		if (document.getElementById("industry_name").value ==""){
			$("#industry_name").focus();
			return;
		}else if(document.getElementById("industry_code").value ==""){
			$("#industry_code").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#industry_code").focus();
			return;
		} 
		if(_parentId==null || _parentId==''){
			$.ajax({
				url:'saveIndustry.action', 
			    type: "post",
				data:{
					industryID:$("#industryID").val(),
					industry_name:$("#industry_name").val(),
					industry_code:$("#industry_code").val(),
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
		}else if(_parentId!=null || _parentId!=''){
			$.ajax({
				url:'saveIndustry.action', 
			    type: "post",
				data:{
					industryID:$("#industryID").val(),
					parentID:$("#industry_name_f").combobox('getValue'),
					industry_name:$("#industry_name").val(),
					industry_code:$("#industry_code").val(),
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
		<legend><strong>经济行业信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt>行业名称</dt>
	    	       <dd><input id="industry_name" name="industry_name" value="<s:property value='industry_name'/>"  class="textinput"  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt><input type="hidden" name="industryID" id="industryID" value="<s:property value='industryID'/>"   />
	    	       		行业编码</dt>
	    	       <dd><input id="industry_code" name="industry_code" value="<s:property value='industry_code'/>"  class="textinput" ></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 260px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    <s:if test="_parentId!=null">
	    	    	<s:if test="_parentId!=''">
	    	    <dl>
	    	       <dt><input type="hidden" name="_parentId" id="_parentId" value="<s:property value='_parentId'/>"   />
	    	       	父级单位</dt>
	    	       <dd><input id="industry_name_f" name="industry_name_f" value="<s:property value='industry_name_f'/>"  class="easyui-combobox"  data-options="prompt:'请选择父级单位'"></dd>
	    	       <dt style="position: absolute; height: 25px; width: 150px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	    	</s:if>
	    	    </s:if>
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_saveInd();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>