<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	if($("#is_use_all_s").val() == 'Y'){
		$("#sfysrk").hide();
	}else {
		$('#user_id').combobox({
			url : 'getCashierList.action',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : '300px',//自动高度适合
			valueField : 'id',
			textField : 'chi_Name',
			onLoadSuccess : function(data){
				if(data.length > 0){
					$('#user_id').combobox('setValue', data[0].id);
					getinvoiceinfo();
				}
			},
			onSelect : function (){
				getinvoiceinfo();
			}
		});
	}
	
	$('#invoice_class').combobox({
		url : 'getDatadis.action?com_Type=FPLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data){
			if($("#old_invoice_class").val() != ''){
				$('#invoice_class').combobox('setValue', $("#old_invoice_class").val());
				getinvoiceinfo();
			}else{
				if(data.length > 0){
					$('#invoice_class').combobox('setValue', data[0].id);
					getinvoiceinfo();
				}
			}
		},
		onSelect : function (){
			getinvoiceinfo();
		}
	});
});
$(function(){
	$("#invoice_num_min,#invoice_num_max").validatebox({
		required: true
	});
});
function saveuserInvoice(){
	if($("#invoice_num_min").val() == ''){
		$("#invoice_num_min").focus();
		return;
	}else if($("#invoice_num_max").val() == ''){
		$("#invoice_num_max").focus();
		return;
	}else if($("#user_invoice_num_length").val() != '' && $("#invoice_num_min").val().length != $("#user_invoice_num_length").val()){
		$.messager.alert('提示信息', "最小发票号长度不是"+$("#user_invoice_num_length").val()+"位数!",'error');
		$("#invoice_num_min").focus();
		return;
	}else if($("#user_invoice_num_length").val() != '' && $("#invoice_num_max").val().length != $("#user_invoice_num_length").val()){
		$.messager.alert('提示信息', "最大发票号长度不是"+$("#user_invoice_num_length").val()+"位数!",'error');
		$("#invoice_num_max").focus();
		return;
	}else if($("#invoice_num_min").val() > $("#invoice_num_max").val()){
		$.messager.alert('提示信息', "最小发票号不能大于最大发票号!",'error');
		$("#invoice_num_min").focus();
		return;
	}else if($("#invoice_num_min").val().length != $("#invoice_num_max").val().length){
		$.messager.alert('提示信息', "最小发票号长度必须等于最大发票号长度!","error");
		$("#s_card_num").focus();
		return;
	}else if($("#invoice_num_max").val()> $("#shiyonghao")){
		$.messager.alert('提示信息', "最大发票号必须大于已维护最大发票号!","error");
		$("#s_card_num").focus();
		return;
	}else if($("#invoice_num_min").val() > $("#shiyonghao").val()){
		$.messager.alert('提示信息', "最小发票号必须大于已维护最大发票号!","error");
		$("#s_card_num").focus();
		return;
	}
	var is_active = 'N';
	if($("#old_invoice_class").val() == '' || $("#old_invoice_class").val() == $('#invoice_class').combobox('getValue')){
		is_active = 'Y';
	}
	$.messager.confirm('提示信息','保存之后,已使用发票号就会清空,是否保存？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'saveUserInvoiceInfo.action',  
		        data:{
		          invoice_num_min:$("#invoice_num_min").val(),
		          invoice_num_max:$("#invoice_num_max").val(),
		          is_use_all:$("#is_use_all_s").val(),
		          invoice_class : $('#invoice_class').combobox('getValue'),
		          is_active : is_active,
		          user_id : $('#user_id').combobox('getValue')
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', data,'info');
		        	$('#dlg-edit').dialog('close');
		        	$("#userinvoicelist").datagrid('reload');
		        }
		    });
		}
	});
}

function getinvoiceinfo(){
	$.ajax({
        url:'getUserInvoiceByUserIdAndClass.action',  
        data:{
          invoice_class : $('#invoice_class').combobox('getValue'),
          user_id : $('#user_id').combobox('getValue')
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	if(data == null || data == 'null'){
        		$("#zuixiaohao").val('无');
        		$("#zuidahao").val('无');
        		$("#shiyonghao").val('无');
        	}else{
        		var obj = eval('('+data+')');
        		$("#zuixiaohao").val(obj.invoice_num_min);
        		$("#zuidahao").val(obj.invoice_num_max);
        		$("#shiyonghao").val(obj.invoice_num_used);
        	}
        }
    });
}
</script>
<form id="add1Form">
	<div class="formDiv">
	<div class="formdiv" style="margin-top:10px;">
		<input type="hidden" id= "old_invoice_class" value='<s:property value='model.invoice_class'/>'/>
		<dl id = "sfysrk">
			<dt>收费员</dt>
			<dd>
				<select id="user_id" class="easyui-combobox" data-options="height:26,width:142,panelHeight:'auto'"></select>
			</dd>
		</dl>
		<dl>
			<dt>发票类型</dt>
			<dd>
				<select id="invoice_class" class="easyui-combobox" data-options="height:26,width:142,panelHeight:'auto'"></select>
			</dd>
		</dl>
		<dl>
			<dt>最小发票号</dt>
			<dd>
				<input type="text"  class="textinput" id="invoice_num_min"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>最大发票号</dt>
			<dd>
				<input type="text"  class="textinput" id="invoice_num_max"/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>已维护最小发票号</dt>
			<dd><input type="text"  class="textinput" id="zuixiaohao" disabled="disabled" value="无"/></dd>
		</dl>
		<dl>
			<dt>已维护最大发票号</dt>
			<dd><input type="text"  class="textinput" id="zuidahao" disabled="disabled" value="无"/></dd>
		</dl>
		<dl>
			<dt>已使用最大发票号</dt>
			<dd><input type="text"  class="textinput" id="shiyonghao" disabled="disabled" value="无"/></dd>
		</dl>
		</div>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:saveuserInvoice(0);" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
