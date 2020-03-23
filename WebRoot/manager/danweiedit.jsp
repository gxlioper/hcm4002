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
   
   $('#dep_Name').validatebox({   
   		 	required: true
    	
	});
})
function f_menusave(){
	if($("#dep_Name").val()==''){
		alert('部门名称不能为空！');
		$("#dep_Name").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		 url:'danweieditdo.action',  
        data:{id:$("#bmid").val(),company_Id:$("#company_Id").val(),dep_Name:$("#dep_Name").val()},          
        type: "post",//数据发送方式   
          success: function(text){ 
        	  $(".loading_div").remove();
             	if(text.split("-")[0]=='ok'){
          		  $.messager.alert("操作提示", text);
          		  $("#dlg-edit").dialog("close");
              	  getGrid();
              	window.parent.reopen();
          	  }else if(text.split("-")[0]=='error'){
          		  $.messager.alert("操作提示", text,"error");
          	  }
          	 
            },
            error:function(){
            	$(".loading_div").remove();
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
				<dt>单位名称：</dt>
				<dd><input type="hidden" name="company_Id" id="company_Id" value="<s:property value="company_Id"/>"/><s:property value="com_Name"/></dd>
				
			</dl>
			<dl>
				<dt><input type="hidden" name="bmid" id="bmid" value="<s:property value="id"/>"/>部门名称：</dt>
				<dd><input type="text" class="textinput"  name="dep_Name" id="dep_Name" value="<s:property value="dep_Name"/>" style="width:244px;"/> <strong class="red">*</strong></dd>
				
			</dl>
			
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_menusave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
	
</div>