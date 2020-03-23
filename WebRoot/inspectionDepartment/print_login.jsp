
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script>
$(document).ready(function () {
	$("#print_name").focus();
});
	$(function(){
		$("#print_name,#print_word").validatebox({
			required:true,
		});
	});
	function f_print_login(){
		if($("#print_name").val() == ''){
			$("#print_name").focus();
			return;
		}else if($("#print_word").val() == ''){
			$("#print_word").focus();
			return;
		}
		var data = $('#cyitemList').datagrid('getData');
		var checkitemlists = $('#cyitemList').datagrid('getChecked');
		var item_codes = new Array();
		if(checkitemlists.length == 0){
			$.messager.alert('提示信息', "请选择需要打印条码的样本!",'error');
			return;
		}
		var print_num = 0;
		for(j=0;j<checkitemlists.length;j++){
			if(checkitemlists[j].sample_barcode == ''){
				$.messager.alert('提示信息', "样本"+checkitemlists[j].demo_name+"未绑定条码，不能打印条码!",'error');
				return;
			}
			for(i=0;i<data.rows.length;i++){
				if(checkitemlists[j].sample_id == data.rows[i].sample_id){
					item_codes.push(data.rows[i].item_code);
				}
			}
			if(checkitemlists[j].print_num > print_num){
				print_num = checkitemlists[j].print_num;
			}
		}
		$.ajax({
	        url:'verifyUserPrintBarcode.action',  
	        data:{
	        	'user_name':$("#print_name").val(),
	        	'key_word':$("#print_word").val()
	        },          
	        type: "post",//数据发送方式   
	        success: function(data){
	        	nos = data.split('-')[0];
	        	if(data.split('-')[0] == 'ok'){
	        		if(data.split('-')[1] > print_num){
	        			print_code_now(item_codes.toString());
	        			$('#dlg-print').dialog('close');
	        		}else{
	        			$.messager.alert('提示信息','该用户的最大打印次数为'+data.split('-')[1]+'次，已超出打印次数限制，不能打印!','error',clera_print);
	        		}
	        	}else{
	        		$.messager.alert('提示信息', data.split('-')[1],'error',clera_print);
	        	}
	        },
	        error:function(data){
	        	$.messager.alert('提示信息', data,'error');
	        }
		});
	} 
	var nos;
	function clera_print(){
		if(nos == 'no1' || nos == 'no2'){//用户名不存在 或 用户已停用
			$("#print_name").val('');
    		$("#print_word").val('');
    		$("#print_name").focus();
		}else if(nos == 'no3'){//密码错误
    		$("#print_word").val('');
    		$("#print_word").focus();
		}else{
			$("#print_name").val('');
    		$("#print_word").val('');
    		$("#print_name").focus();
		}
	}

	function keyDown1(event){ 
		var inputs=$(".jre1");
	    var focus=document.activeElement;
	    var event=window.event||event;
	    var key=event.keyCode; 
	    for(var i=0; i<inputs.length; i++) { 
	        if(inputs[i]===focus) break; 
	    } 
	    switch(key) { 
	        case 37: //左
	            if(i>0) inputs[i-1].focus(); 
	            break; 
	        case 38: //上
	            if(i>0) inputs[i-1].focus();
	            break; 
	        case 39: //右
	            if(i<inputs.length-1) inputs[i+1].focus(); 
	            break; 
	        case 40: //下
	            if(i<inputs.length-1) inputs[i+1].focus();
	            break;
			case 13://回车 
				if(i<inputs.length-1) inputs[i+1].focus();
	            break;
	    }
	    if(key == 13 && i == inputs.length-1){
	    	$("#login_print").focus();
	    }
	} 

</script>
	<form id="addForm">
<div class="formdiv" onkeydown="keyDown1(event)">
	<br/>
			<dl>
				<dt style="width:100px;">用户名：</dt>
				<dd><input type="text" class="textinput jre1" style="width:200px;" id="print_name" /> <strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt style="width:100px;">密码：</dt>
				<dd><input type="password"  id="print_word" class="textinput jre1" style="width:200px;"/> <strong class="red">*</strong></dd>
			</dl>
	</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:f_print_login()" id="login_print" class="easyui-linkbutton c6" style="width:80px;" >确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-print').dialog('close')">关闭</a>
	</div>
	
</div>
</form>
