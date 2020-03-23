<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	if($("#is_use_all").val() == 'Y'){
		$("#is_use_all_y").val('共用发票号段');
	}else{
		$("#is_use_all_y").val('个人发票号段');
	}
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
	}else if($("#invoice_num_min").val() > $("#invoice_num_max").val()){
		$.messager.alert('提示信息', "最小发票号不能大于最大发票号!",function(){});
		$("#invoice_num_min").focus();
		return;
	}else if($("#invoice_num_min").val().length != $("#invoice_num_max").val().length){
		$.messager.alert('提示信息', "最小发票号长度必须等于最大发票号长度!",function(){});
		$("#s_card_num").focus();
		return;
	}
	$.messager.confirm('提示信息','保存之后,上一张打印发票号将清空,下一张发票号将从最小发票号开始,是否保存？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'saveUserInvoice.action',  
		        data:{
		          invoice_num_min:$("#invoice_num_min").val(),
		          invoice_num_max:$("#invoice_num_max").val()
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        		$.messager.alert('提示信息', data,'info');
		        		$('#dlg-tuifei').dialog('close');
		        		
		        }
		    });
		}
	});
}
//作废空白无用发票
function zuofei(){
	if($("#zuofei_invoice_num").val() > $("#invoice_num_max").val()){
		$.messager.alert('提示信息', "已使用发票号已超出最大发票号段，不能继续作废，请重新维护发票号段!",'error');
		return;
	}
	$.messager.confirm('提示信息','作废下一张发票号'+$('#zuofei_invoice_num').val(),function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$.ajax({
		        url:'getWuxaiofapiaozuifei.action',  
		        data:{
		        	invoice_num_used:$("#zuofei_invoice_num").val(),
		        	invoice_class : $("#invoice_class").val(),
		        	user_id : $("#user_id").val()
		          },          
		        type: "post",//数据发送方式   
		        success: function(data){
		        	$(".loading_div").remove();
		        	var result = eval('('+data+')');
		        	$('#zuofei_invoice_num').val(result.num_used);
		        	$('#s_num_used').val(result.s_num_used);
		        	load_invoice();
	        		$.messager.alert('提示信息', result.messager,'info');
		        },error:function(){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', "操作失败",'error');
		        }
		    });
		}
	})
}
</script>
<form id="add1Form">
	<div class="formDiv">
	<div class="formdiv" style="margin-top:10px;">
		<dl>
			<dt>使用号段模式</dt>
			<dd>
				<input type="hidden" id="is_use_all" value='<s:property value='model.is_use_all'/>'>
				<input type="hidden" id="user_id" value='<s:property value='model.user_id'/>'>
				<input type="text"  class="textinput" id="is_use_all_y" disabled="disabled"/>
			</dd>
		</dl>
		<dl>
			<dt>使用发票类型</dt>
			<dd>
				<input type="hidden" id="invoice_class" value='<s:property value='model.invoice_class'/>'>
				<input type="text"  class="textinput" disabled="disabled"  value='<s:property value='model.invoice_classs'/>'/>
			</dd>
		</dl>
		<dl>
			<dt>最小发票号</dt>
			<dd>
				<input type="text"  class="textinput" id="invoice_num_min" disabled="disabled"  value='<s:property value='model.invoice_num_min'/>'/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>最大发票号</dt>
			<dd>
				<input type="text"  class="textinput" id="invoice_num_max" disabled="disabled"  value='<s:property value='model.invoice_num_max'/>'/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		<dl>
			<dt>上一张打印票号</dt>
			<dd>
				<input type="text" id="s_num_used" class="textinput" disabled="disabled"  value='<s:property value='model.invoice_num_used'/>'/>
			</dd>
		</dl>
		<dl>
			<dt>下一张将打印票号</dt>
			<dd>
				<input type="text" id="zuofei_invoice_num" class="textinput" disabled="disabled" value='<s:property value='model.invoice_num'/>'/>
			</dd>
		</dl>
		</div>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <script type="text/javascript">
	    </script>
	    <s:if test="model.invoice_num_min != null">
	    	<a href="javascript:zuofei(0);" class="easyui-linkbutton" style="width:100px;">作废发票</a>
	    </s:if>
	    <!-- <a href="javascript:saveuserInvoice(0);" class="easyui-linkbutton c6" style="width:100px;">保存</a> -->
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-tuifei').dialog('close')">关闭</a>
	</div>
</div>
</form>
