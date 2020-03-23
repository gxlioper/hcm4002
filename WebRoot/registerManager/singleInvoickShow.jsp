<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url : 'getMaxInvoiceNum.action',
		data : {},
		type : 'post',
		success:function(data){
			$("#invoice_num1").val(data);
		}
	});
	$("#invoice_name1").val($("#user_name").html());
	
	getsingleInviockGrid();
});
$(function(){
	$("#invoice_num1,#invoice_name1,#invoice_type1").validatebox({
		required: true
	});
});
//获取需开发票申请单
function getsingleInviockGrid(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#singleInviockList").datagrid({
		url: 'getChargingSingleInvoickList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[{align:'center',field:'fp_ck',checkbox:true},
		          {align:"center",field:"req_num","title":"申请单号","width":15},
		          {align:'center',field:"amount","title":"交易金额(元)","width":15}
//		          {align:"center",field:"cashier","title":"收费人","width":15},
//		          {align:"center",field:"cash_date","title":"收费日期","width":15}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	$('#singleInviockList').datagrid('checkAll');
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		onSelect:function(index,row){
			jineheji();
	    },
	    onUnselect:function(index,row){
	    	jineheji();
	    },
	    onCheckAll:function(rows){
	    	jineheji();
	    },
	    onUncheckAll:function(rows){
	    	jineheji();
	    }
	});
}

function jineheji(){
	var data = $('#singleInviockList').datagrid('getSelections');
	
	var amount2 = 0;
	for(i=0;i<data.length;i++){
		amount2 += Number(data[i].amount);
	}
	
	$("#invoice_type1").val(amount2 + '元');
}

//保存打印发票
function save_fapiao(){
	var data = $('#singleInviockList').datagrid('getSelections');
	if(data.length <= 0){
		$.messager.alert('提示信息', '请选择需要开发票的收费记录','error');
		return;
	}
	
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
	
	var amount2 = 0;
	var req_nums = new Array();
	for(i=0;i<data.length;i++){
		req_nums.push(data[i].req_num);
		amount2 += Number(data[i].amount);
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
        url:'saveSingleInviockBu.action?language='+$("#language").val(),  
        data:{
          exam_id:$("#exam_id").val(),
          req_nums:req_nums.toString(),
          title_info:$("#invoice_name1").val(),
          invoice_type:$("#invoice_type1").val(),
          invoice_num:$("#invoice_num1").val(),
          amount2:amount2
          },          
        type: "post",//数据发送方式   
        success: function(data){
        	$(".loading_div").remove();
        		var state = eval("("+data+")");
        		if(state.flag == 'error'){
        			$.messager.alert('提示信息', state.info,'error');
        			return;
        		}
        		$.messager.alert('提示信息', "正在打印发票。",'info');
        		if($("#invoiceprinttype").val()=='1'){
        			if($("input[name='invoice_l']:checked").val() == 'mx'){
    					fapiao_point_mx($("#invoice_num1").val());
    				}else{
    					fapiao_point($("#invoice_num1").val());
    				}
     		    }else{
     			   printreport_invoice(state.user_id,state.account_num);
     		    }
				$('#dlg-fapiao').dialog('close');
				getykfpListGrid();
				getyksjListGrid();
				
				$("#yintui").html(0);
				$("#yintui_sj").html(0);
        }
        });
}
</script>
<form id="add1Form">
	<div class="formdiv" style="height:200px;">
		<div id="singleInviockList"></div>
	</div>
	<div class="formdiv">
		<div class="formdiv">
			<dl>
				<dt>发票号：</dt>
				<dd><input style="width:290px;" type="text" readonly="readonly" id="invoice_num1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票抬头：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_name1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>发票内容：</dt>
				<dd><input style="width:290px;" type="text" id="invoice_type1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dd>
				<input name="invoice_l" type="radio" checked="checked" value="mx" style="margin-left: 220px;"/>详细发票
				<input style="margin-left: 30px;" name="invoice_l" type="radio" value="jd"/>简单发票
				<dd>
			</dl>
		</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_fapiao();">确定</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fapiao').dialog('close')">关闭</a>
	</div>
</div>
</form>
