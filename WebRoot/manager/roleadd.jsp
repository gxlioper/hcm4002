<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	 $(this).keypress( function(e) {
   var key = window.event ? e.keyCode : e.which;
   if(key.toString() == "13"){
      return false;
     }
   });
   
   $('#rolename').validatebox({   
   		 	required: true
    	
	});
})
function f_menusave(){
	if($("#rolename").val()==''){
		//alert('角色名称不能为空！');
		$("#rolename").focus();
		return;
	}
	$.ajax({
		 url:'roleeditdo.action?language='+$("#language").val(),  
        data:{id:$("#id").val(),rolename:$("#rolename").val()},          
        type: "post",//数据发送方式   
          success: function(data){  
             	var obj=eval("("+data+")");
          		//alert(obj);
          		$.messager.alert('提示信息', obj,function(){});
          	 $("#dlg-edit").dialog("close");
              	getGrid();
            },
            error:function(){
            	 $("#dlg-edit").dialog("close");
            	//alert("用户操作失败！");
            	$.messager.alert('提示信息', "用户操作失败！",function(){});
            }
                 
	});
}
$(function(){
	$('input').attr("maxlength","20");
})
</script>
<div class="formdiv">
	<br/>
			<dl>
				<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>角色名称：</dt>
				<dd><input type="text" class="textinput"  name="rolename" id="rolename" value="<s:property value="rolename"/>" style="width:244px;"/> <strong class="red">*</strong></dd>
				
			</dl>
			
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_menusave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
	
</div>