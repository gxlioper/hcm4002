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

	 $('#parent_name').combotree({    
		    url: 'getDeptList.action',    
		    required: true ,
		    panelHeight : 'auto',//自动高度适合
		    height:26,
		    width:160,
		    onLoadSuccess:function(){
		    	$('#parent_name').combobox({
		    		required: true ,
				    panelHeight : 'auto',//自动高度适合
				    height:26,
				    width:160
		    	});
		    	$('#parent_name').combobox('setValue','<s:property value='parent_id'/>');
		    }
		});  
});

function f_Examsavedept(){
	if($('#parent_name').combobox('getValue') == ''){
		//$("#message").html('请选择父级部门');
		$('#parent_name').combobox().next('span').find('input').focus();
		return;
	}
	if ($("#center_name").val()==""){
		$("#center_name").focus();
		$("#message").html('请填写部门名称');
		return;
	}
	if($("#id").val()!=''){
		$.ajax({
		    url:'saveExamDept.action', 
		    type: "post",
			data:{
				id:$("#id").val(),
				center_num:$("#center_num").val(),
				center_name:$("#center_name").val(),
				parent_id:$('#parent_name').combobox('getValue')
			  },          
			success: function(data){
				 $(".loading_div").remove();
			  	 $.messager.alert('提示信息', data);
			  	 $("#dlg-edit").dialog("close");
			      	 getGrid();
			    },
			 error:function(){
				 	$(".loading_div").remove();
			    	$("#dlg-edit").dialog("close");
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }  
		});
	}else{
		var flag=$("#center_num").validatebox('isValid');
		if(flag){
			$.ajax({
				url:'isUniqueExam.action?center_num='+$("#center_num").val(),
				type:'post',
				success:function(data){
					if(data=='no'){
						$("#message").html('该体检中心编码已存在');
						return;
					}else if(data=='ok'){
						var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
						 $("body").prepend(str);
					$.ajax({
					    url:'saveExamDept.action', 
					    type: "post",
						data:{
							id:$("#id").val(),
							center_num:$("#center_num").val(),
							center_name:$("#center_name").val(),
							parent_id:$('#parent_name').combotree('tree').tree('getSelected').id	
						  },          
						success: function(data){
							 $(".loading_div").remove();
						  	 $.messager.alert('提示信息', data);
						  	 $("#dlg-edit").dialog("close");
						      	 getGrid();
						    },
						 error:function(){
							 	$(".loading_div").remove();
						    	$("#dlg-edit").dialog("close");
						    	$.messager.alert('提示信息', "用户操作失败！",function(){});
						    }  
					});
					}
				}
		});
		}else{
			$("#center_num").focus();
		}
	}
	
}
</script>	
	<fieldset style="margin:5px; height:90%;">
		<legend><strong>部门信息</strong></legend>
			<div class="formdiv">
	    	   <dl>
	    	       <dt>父级部门</dt>
	    	       <dd id="depname"><input id="parent_name" name="parent_name" /></dd>
	    	    </dl>
	    	 
				<dl>
	    	       <dt>部门名称</dt>
	    	       <dd>
	    	           <input id="id" name="id" value="<s:property value='id'/>" hidden="true"/>
	    	       <input id="center_name" name="center_name" value="<s:property value='center_name'/>"  class="textinput"  style="width:155px" ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt><input type="hidden" name="id" id="id" value="<s:property value='id'/>"   />
	    	       		部门编码</dt>
	    	       <dd><input id="center_num" name="center_num" value="<s:property value='center_num'/>"  class="textinput" style="width:155px"></dd>
	    	    </dl>
	    	     <dl>
	    	      <dt   style="position: absolute; height: 25px; width: 150px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	    	    </dl>
	    	  
	    	    </div>
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_Examsavedept();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

