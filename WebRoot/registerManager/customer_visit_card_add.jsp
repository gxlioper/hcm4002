<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<script type="text/javascript">
$(document).ready(function() {
	getvalidatejiuzhengka();
	$('#vilit_no').validatebox({    
	    required: true,    
	    validType: 'vilit_no_num'   
	}); 
})
function addjiuzhengka(){
	if($('#vilit_no').val()==""){
		$('#vilit_no').focus();
		return;
	}
	if(!getvalidatevilit($('#vilit_no').val())){
		$('#vilit_no').focus();
		return;
	}
	$.ajax({
		url:'savejiuzhenka.action',
		type:'post',
		data:{
			visit_no:$('#vilit_no').val(),
			customer_id:$('#add_cid').val()
		},
		success:function(data){
			$('#dlg-jiuizhengka_add').dialog('close')
				$('#shou_customer_visit_card').datagrid('reload');
			$.messager.alert("提示信息",data,"info");
			
		},error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
}
function getvalidatejiuzhengka(){
	$.extend($.fn.validatebox.defaults.rules, {    
		vilit_no_num: {    
	        validator: function(value,param){    
	            return getvalidatevilit(value);    
	        },    
	        message:"必填项"  
	    }    
	});
}
var val = "";
function getvalidatevilit(val){
	var fla = true;
	$.ajax({
		url:'getvalidatevilit.action',
		data:{
			visit_no:val,
			customer_id:$('#add_cid').val()
		},
		type:'post',
		async:false,
		success:function(data){
			if(data=="ok"){
				
			} else {
				$.fn.validatebox.defaults.rules.vilit_no_num.message = "就诊卡号冲突！";
				fla = false;
			}
		},error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
	})
	return fla;
}
</script>
<input type="hidden"  id="add_cid"   value="<s:property value='customer_id'/>"  />
<div class = "formDiv"  style="margin-top: 40px;">
	<dl>
		<dt style="width: 100px;">卡号</dt>
		<dd><input type="text" id = "vilit_no" class = "textinput"  style="height: 26px;width: 100px;" /></dd>
	</dl>
</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
		<a href="javascript:addjiuzhengka();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-jiuizhengka_add').dialog('close')">关闭</a>
	</div>
</div>
