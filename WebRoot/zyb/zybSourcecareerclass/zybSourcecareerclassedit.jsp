<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function () {
		getsource();
		$("#source").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#sc_classname").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		
		$("#sc_classcode").validatebox({
			required:true,
			validType:'maxLength[30]'
		});
		$("#sc_classcode").change(function(){
			$("#message").html('');
		});
		$("#sc_classcode").blur(function(){
			var flag=$("#sc_classcode").validatebox('isValid');
			if(flag){
				$.ajax({
					url:'volidate.action',
					data:{
						sc_classcode:$("#sc_classcode").val(),
						sc_classid:$("#sc_classid").val()
					},
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
	
	function getsource(){
		$("#source").combobox({
			url :'getSourcecareerclass.action',
			editable : false, //不可编辑状态
			panelHeight : 'auto',//自动高度适合 
			valueField : 'source_id',
			textField : 'source_name'
		})
		$("#source").combobox('select',"<s:property value='sourceid'/>");
		$("#source").combobox('setText',"<s:property value='source_name'/>");
	}
	function save(){
		 if ($("#source").combobox('getValue').trim()==''){
			 $('#source').combobox().next('span').find('input').focus();
				return;
		}else if (document.getElementById("sc_classname").value ==""){
			$("#sc_classname").focus();
			return;
		}else if(document.getElementById("sc_classcode").value ==""){
			$("#sc_classcode").focus();
			return;
		}else if($("#message").attr('value') == 'no'){
			$("#sc_classcode").focus();
			return;
		} 
		$.ajax({
			url:'saveSourcecareerclass.action', 
		    type: "post",
			data:{
				sc_classid:$("#sc_classid").val(),
				sc_classname:$("#sc_classname").val(),
				sc_classcode:$("#sc_classcode").val(),
				sourceid:$("#source").combobox('getValue').trim(),
			  },          
			success: function(data){
			  	 $.messager.alert('提示信息', data);
			  	 if(!$('#add').is(':checked')){
			  		 $("#edit").dialog("close");
			  	 }else{
			  		$("#sc_classname").val(''),
					$("#sc_classcode").val(''),
					$("#source").combobox('setValue','')
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
		 
		 if($('#add').is(':checked')){
			$('#add').attr('checked',false)
		}else{
			$('#add').attr('checked',true)
		} 
	} 
</script>
<fieldset style="margin:5px; height:80%;padding-top:20px;">
		<legend><strong>照射源分类信息编辑</strong></legend>
			<div class="formdiv">
				<dl>
	    	       <dt>照射源</dt>
	    	       <dd><input id="source" required='true'  ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>分类编码</dt>
	    	       <dd><input id="sc_classcode"  class="textinput" value='<s:property value='sc_classcode'/>' ></dd>
	    	       <dt style="position: absolute; height: 25px; width:100px; margin-left: 290px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
				<dl>
	    	       <dt><input type="hidden"  id="sc_classid" value="<s:property value='sc_classid'/>"   />
	    	       	分类名称</dt>
	    	       <dd><input id="sc_classname"  class="textinput" value="<s:property value='sc_classname'/>" ></dd>
	    	    </dl>
	    	</div>

<div class="dialog-button-box">
	<div class="inner-button-box">
		<s:if  test="sc_classid==null">
				<div style="float:left;padding-left:20px;font-size:16px;cursor:pointer;" onclick="continuityadd();"><input type="checkbox" onclick="continuityadd();" id="add" style="cursor:pointer;" />&nbsp;&nbsp;连续添加</div>
			</s:if>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="save();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#edit').dialog('close')">关闭</a>
	</div>
</div>
</fieldset> 