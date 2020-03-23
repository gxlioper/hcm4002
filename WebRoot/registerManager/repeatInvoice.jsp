<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {

});

//重打发票
function repeatInvoice(){
	var invoice_num=Number($("#invoice").val());
	alert(invoice_num+"^^^^^"+$("input[name='invoice_2']:checked").val());
	if($("input[name='invoice_2']:checked").val() == 'mx'){
		fapiao_point_mx(invoice_num);
	}else{
		fapiao_point(invoice_num);
	}
	
}

//打印普通发票
function fapiao_point(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point.cpt',
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
//打印明细发票
function fapiao_point_mx(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point_mx.cpt',
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
<input type="hidden" id="invoice" value="<s:property value="model.invoice_num"/>">
<form id="add1Form">
	<div class="formdiv">
		<div class="formdiv">
			<dl>
				<dt>发票号：</dt>
				<dd><input style="width:290px;" type="text" readonly="readonly" id="invoice_num" value="<s:property value="model.invoice_num"/>" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
<%-- 			<dl>
				<dt>发票抬头：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_name1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票内容：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_type1" class="textinput"/><strong class="red">*</strong></dd>
			</dl> --%>
			<dl>
				<dd>
				<input name="invoice_2" type="radio" checked="checked" value="mx" style="margin-left: 220px;"/>详细发票
				<input style="margin-left: 30px;" name="invoice_2" type="radio" value="jd"/>简单发票
				</dd>
			</dl>
		</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:repeatInvoice();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fapiaoCD').dialog('close')">关闭</a>
	</div>
</div>
</form>
