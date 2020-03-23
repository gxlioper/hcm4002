<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function () {
	var invoiceNum = "<s:property value='model.invoice_num'/>";
	if(invoiceNum!="" && invoiceNum!=null){
		//$("#invoice_num").val((parseInt(invoiceNum)+1));
		$("#print_invoice_num").val(stringToIntLength(invoiceNum));
	}
	$("#invoice_head").val($("#user_name").html());
});

//拼接前面丢失的0
function stringToIntLength(num){
	var leng = parseInt(num.length);
	var leng2 = parseInt((parseInt(num)+1).toString().length);
	var str = "";
	for(var i=0;i<leng-leng2;i++){
		str+="0";
	}
	return str+(parseInt(num)+1);
}

function print_fapiao_hao(){
	if($("#print_exam_num").val()=="" || $("#print_exam_num").val()==null){
		$.messager.alert('提示信息', "体检号为空",'error');
		return;
	}
	$.ajax({
		url : 'savePrintHeadNum.action',
		data : {
			exam_num:$("#print_exam_num").val(),
			invoice_head:$("#invoice_head").val(),
			invoice_num:$("#print_invoice_num").val()
		},
		type : 'post',
		success:function(data){
			$('#dlg-print-fapiao').dialog('close');
			$.messager.alert('提示信息', "收费成功！正在打印数据",'info');
			fapiao_point_mx($("#print_exam_num").val());
		},
		error : function() {
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
	
}

//打印明细发票
function fapiao_point_mx(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'shoujv.cpt',
		"a" : a
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
}
</script>
<form id="add1Form">
	<div class="formDiv">
	<input type="hidden" id="print_exam_num" value="<s:property value='model.exam_num'/>" />
	<div class="formdiv" style="margin-top:70px;">
		<dl>
			<dt style="width: 90px;">发票抬头</dt>
			<dd>
				<input type="text" style="width: 220px;"  class="textinput" id="invoice_head" value=''/>
				<font color="#ff0000">*</font>
			</dd>
		</dl><br/>
		<dl>
			<dt style="width: 90px;">票据号</dt>
			<dd>
				<input style="width: 220px;" type="text"  class="textinput" id="print_invoice_num"  value=''/>
				<font color="#ff0000">*</font>
			</dd>
		</dl>
		</div>
	</div>
<div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:print_fapiao_hao();" class="easyui-linkbutton" style="width:100px;">打印</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-print-fapiao').dialog('close')">关闭</a>
	</div>
</div>
</form>
