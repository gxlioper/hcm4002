<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$("#invoice_num1,#invoice_name1,#invoice_type1").validatebox({
		required: true
	});
});
//获取需开发票申请单

//保存打印发票
function save_fapiao(){
		
	if($("#invoice_num1").val() == ''){
		$("#invoice_num1").focus();
		return;
	}else if($("#invoice_name1").val() == ''){
		$("#invoice_name1").focus();
		return;
	}else if($("#invoice_type1").val() == ''){
		$("#invoice_type1").focus();
		return;
	}	
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'updateSingleInviockTT.action',  
        data:{
        	batchid:$("#batchid").val(),
        	id:$("#id").val(),
            title_info:$("#invoice_name1").val(),
            invoice_type:$("#invoice_type1").val(),
            invoice_num:$("#invoice_num1").val(),
            amount2:$("#charges").val()
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	var models = eval("("+data+")");
        	$(".loading_div").remove();
        		if(models.value=='ok'){
        		   getcontractGrid();
        		   $.messager.alert('提示信息', "正在打印发票。",function(){});        		   
        		   if($("#invoiceprinttype").val()=='1'){
        			   fapiao_point($("#invoice_num1").val());
        		   }else{
        			   printreport_invoice(models.id,models.text);
        		   }
        		    gettjxmGrid($("#account_num").val());
	  				gettermaccountlistGrjlid($("#account_num").val());
        		   $('#dlg-custedit').dialog('close');
        		}else{
        			$.messager.alert('提示信息', models.text,function(){});
        			return;
        		}
        }
        });
}

//打印普通发票
function fapiao_point(a){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'fp_point_dw.cpt',
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
var fapuserid,fapaccountnum;
function printreport_invoice(fapuserid,fapaccountnum){
	//var exeurl =$("#reportexeurl").val() +" 0 "+barids; 
	var exeurl="invoiceService://1$"+fapaccountnum;
	 RunReportExe(exeurl);
 }

</script>
<input type="hidden" id="invoiceprinttype"  value="<s:property value="model.invoiceprinttype"/>">
<input type="hidden" id="batchid"  value="<s:property value="model.batchid"/>">
<input type="hidden" id="charges"  value="<s:property value="model.charges"/>">
<input type="hidden" id="id"  value="<s:property value="model.id"/>">
<input type="hidden" id="account_num"  value="<s:property value="model.account_num"/>">

<form id="add1Form">
	<div class="formdiv" style="height:30px;">
		<div id="singleInviockList"></div>
	</div>
	<div class="formdiv">
		<div class="formdiv">
			<dl>
				<dt>发票号：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_num1"  value="<s:property value="model.invoice_no"/>"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票抬头：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_name1"   value="<s:property value="model.comname"/>" /><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票内容：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_type1"  value="<s:property value="model.chargentities"/>元" /><strong class="red">*</strong></dd>
			</dl>
			
		</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fapiao();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-custedit').dialog('close')">关闭</a>
	</div>
</div>
</form>
