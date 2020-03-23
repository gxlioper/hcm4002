<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js"></script>
<script type="text/javascript">
$(function(){
	$('#b-exam_num').validatebox({    
	    required: true
	});  

})
	function bindPersonSave(){
		if($('#b-exam_num').val()==""){
			$('#b-exam_num').focus()
			return;
		}
		$.ajax({
			url:'bindPersonUpdate.action',
			data:{exam_num:$('#b-exam_num').val(),customer_id:$('#c_customer_id').val()},
			type:'post',
			success:function(text){
				if(text==="无效体检号"){
					$.messager.alert("提示信息",text,"error");
				} else {
					$('#r_edit').dialog('close')
					$.messager.alert("提示信息",text,"info");
				}
			},
			error:function(){
				$.messager.alert("提示信息","绑定失败","error");
			}
		})
	}
</script>
	<input type="hidden" id="c_customer_id" value="<s:property value="customer_id"/>"  />
	<fieldset style="margin:5px;padding-right:20px;padding-left:30px;font-size:14px;">
	
	<div class="formdiv" style="margin: 20px 20px">
			体检号：<input type="text" class="textinput" id="b-exam_num" value=""   >
	</div>
	
</fieldset>
<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="bindPersonSave();">绑定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#r_edit').dialog('close')">关闭</a>
		</div>
</div>

