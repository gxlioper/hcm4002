<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#deadline').datebox({ editable:false });
	});
function f_saveCardInfo(){
	var deadline = $("#deadline").datebox('getValue');
	var card_id = '<s:property value="card_id"/>';
	if(deadline == ''){
		$("#message").html("请选择延期日期");
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
        url:'cardInfoEdityanqi.action?language='+$("#language").val(),  
        data:{
          card_id:card_id,
          deadline:deadline
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        		$.messager.alert('提示信息', data,function(){});
            	$("#dlg-s").dialog("close");
            	getGrid();
            },
            error:function(){
            	$(".loading_div").remove();
            	$("#dlg-s").dialog("close");
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>延期日期：</dt>
				<dd><input type="text" id="deadline" name="deadline" class="easyui-datebox"/><strong class="red">*</strong></dd>
				<dt  class="autoWidth"><span  id="message" class="red"></span></dt>
			</dl>
	</div>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveCardInfo();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-s').dialog('close')">关闭</a>
	</div>
</div>
</form>
