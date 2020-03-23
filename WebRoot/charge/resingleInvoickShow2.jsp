<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/insurance.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/aisino.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(document).ready(function () {
		
// 	getClientflag("dbgj",function(objvalue){
// 		if(objvalue.error_flag=='0'){			
// 			$("#rcpt_print_flag1").val(objvalue.rcpt_print_flag);
		$.ajax({
			url : 'getMaxInvoiceNumCharge.action',
			data : {
				bill_type:2
// 				user_id:objvalue.rcpt_print_flag
				},
			type : 'post',
			success:function(data){
				$("#fapiao_num1").val(data);
			},
			error : function() {
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
// 		}else{
// 			$.messager.alert("操作提示", objvalue.error_msg, "error");
// 		}
// 	});	
	
	$("#gfmc1").val($("#user_name").html());
	$('#fhr1').combobox({
		url : 'getCashierListCharge.action',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data){
//			for (var int = 0; int < data.length; int++) {
//				if($("#center_id").val()!=data[i].id){
					$('#fhr1').combobox('setValue', data[12].id);
//				}
//			}
			
		},
	});
	$("#gfmc1").combobox({
		url :'getInformationCharge.action',
		//editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'infoClientName',
		onSelect : function(){
			 var id = $("#gfmc1").combobox("getValue");
             $.ajax({  
    		        "type" : 'post',  
    		        "url": "getInformationCharge.action?infoClientName="+id,
    		        "dataType" : "json",  
    		        "success" : function(data) {
    					$("#gfsh1").val(data[0]["infoClientTaxCode"]);
    					$("#gfkhh1").val(data[0]["infoClientBankAccount"]);
    					$("#gfdhdz1").val(data[0]["infoClientAddressPhone"]);
    				}
            	}); 
        }  
	})
	getsingleInviockGrid();
});
$(function(){
	$("#fapiao_num1,#gfmc1,#spmc1").validatebox({
		required: true
	});
});
//获取需开发票申请单
function getsingleInviockGrid(){
	var model = {"exam_num":$("#ser_num").val()};
	$("#bdfpsingleInviockList").datagrid({
		url: 'getChargingSingleInvoickListCharge.action',
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
	    	$('#bdfpsingleInviockList').datagrid('checkAll');
	    },
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		onSelect:function(index,row){
			//jineheji();
	    },
	    onUnselect:function(index,row){
	    	//jineheji();
	    },
	    onCheckAll:function(rows){
	    	//jineheji();
	    },
	    onUncheckAll:function(rows){
	    	//jineheji();
	    }
	});
}

function jineheji(){
	var data = $('#bdfpsingleInviockList').datagrid('getSelections');
	
	var amount2 = 0;
	for(i=0;i<data.length;i++){
		amount2 += Number(data[i].amount);
	}
	
	$("#fpje").val(amount2);
}

//保存打印发票
function save_fapiao(){
	var data = $('#bdfpsingleInviockList').datagrid('getSelections');
	if(data.length <= 0){
		$.messager.alert('提示信息', '请选择需要开发票的收费记录','error');
		return;
	}	
	if($("#fapiao_num1").val() == ''){
		$("#fapiao_num1").focus();
		return;
	}else if($("#gfmc1").combobox("getText") == ''){
		$("#gfmc1").focus();
		return;
	}else if($("#spmc1").val() == ''){
		$("#spmc1").focus();
		return;
	}else if(($("#gfsh1").val() != '')&& $("#gfsh1").val().length<15){
		$("#gfsh1").focus();
		$.messager.alert('提示信息', "购方税号位数不对!","error");
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
        url:'saveSingleInviockBuCharge.action',  
        data:{
          exam_id:$("#exam_id").val(),
          req_nums:req_nums.toString(),
          title_info:$("#gfmc1").combobox("getText"),
          invoice_type:$("#spmc1").val(),
          invoice_num:$("#fapiao_num1").val(),
          rcpt_print_flag:$("#rcpt_print_flag1").val(),
          bill_type:2,
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
        		$.ajax({
    				url : 'getamtForCISAccountnumCharge.action',
    				data : {
    					account_num : state.account_num
    				    },
    						type : "post",//数据发送方式   
    						success : function(text) {
    							$(".loading_div").remove();
    							if (text!='error') {
    								var kaipiaodata={
    										yymc:'dbgj',
    										kprbm:$("#userid1").val(),
    										kprgh:$("#work_other_num1").val(),
    										fplx:$("#fplx1").val(),////发票类型 0 专票  2 普票
    										gfmc:$("#gfmc1").combobox("getText"),//购方名称
    										gfsh:$("#gfsh1").val(),//购方税号
    										gfkhh:$("#gfkhh1").val(),//购方开户行及账号
    										gfdhdz:$("#gfdhdz1").val(),//购方地址电话
    										jspsl:"6",//税率
    										spmc:$("#spmc1").val(),//商品名称
    										spje:text.split("#")[2],//商品金额
    										kpr:$("#username1").val(),//开票人
    										shuimu:"3070202",//税目
    										spgg:$("#dw1").val(),//商品规格
    										spsl:$("#spsl1").val(),//商品数量
    										spdj:$("#spdj1").val(),//商品单价
    										fhr:$('#fhr1').combobox('getText'),//复核人
    										skr:$("#username1").val(),//收费人
    										bz:$("#bz1").val()//备注
    								};
    								doFpInvoice(text.split("#")[1],kaipiaodata,function(objvalue) {
    								    if(objvalue.error_flag=='0'){
    								    	 $.messager.alert("操作提示", objvalue.error_msg, "");
    								    	 $("#gfmc1").val('');      		
    		    				        		$("#gfsh1").val('');        		
    		    				        		$("#gfkhh1").val('');
    		    				        		$("#gfdhdz1").val('');    
    		    				        		$("#bz1").val('');    
    		    				        		$("#fapiao_num1").val('');
    		    				        		$("#dlg-fapiao").dialog('close');
    		    				        		getykfpListGrid();
    		    								getyksjListGrid();	
    		    								
    		    								$("#yintui").html(0);
    		    								$("#yintui_sj").html(0);
    								    }else{
    								    	$.messager.alert("操作提示", objvalue.error_msg, "error");
    								    }
    								    
    								});
    								if(reqsdata.error_flag=='-1'){
    									$.messager.alert("操作提示", reqsdata.error_msg, "error");	
    								}
    				        		
    							}
    						},
    						error : function() {
    							$(".loading_div").remove();
    							$.messager.alert("操作提示", "操作错误", "error");	
    							$("#dlg-fapiao").dialog('close');
    						}
    					});
			
        },error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");	
			$("#dlg-fapiao").dialog('close');
		}
	});	
	
}
</script>

<input type="hidden" id="username1" value="<s:property value="#session.username.name"/>" />
<input type="hidden" id="work_other_num1"  value="<s:property value="#session.username.work_other_num"/>" />
<input type="hidden" id="userid1"  value="<s:property value="#session.username.userid"/>"  />
<input type="hidden" id="rcpt_print_flag1"  />

<form id="add1Form">
<input type="hidden" id="fpje" >
	<div class="formdiv" style="height:200px;">
		<div id="bdfpsingleInviockList"></div>
	</div>
	<div class="formdiv">
		<div class="formdiv">
			<dl style="height: 30px;">
				<dt>发票号：</dt>
				<dd ><input style="width:250px;" type="text"  readonly="readonly" id="fapiao_num1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>发票类型：</dt>
				<dd><select id='fplx1' style="width:250px;" name="fplx1">
									  <!-- option value="0">专用发票</option-->
									  <option value="2" selected>普通发票</option>
									 </select></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>购方名称：</dt>
				<dd><input style="width:250px;" type="text"  id="gfmc1" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>购方税号：</dt>
				<dd><input style="width:250px;" type="text"  id="gfsh1" class="textinput"/></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>购方开户行及账号：</dt>
				<dd><input style="width:250px;" type="text"  id="gfkhh1" class="textinput"/></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>购方地址电话：</dt>
				<dd><input style="width:250px;" type="text"  id="gfdhdz1" class="textinput"/></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>商品名称：</dt>
				<dd><input style="width:250px;" type="text"   id="spmc1" class="textinput" value="体检费"/><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>复核人：</dt>
				<dd><select id="fhr1" class="easyui-combobox" style="width:250px;"  data-options="height:26,width:142,panelHeight:'auto'"></select></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>数量：</dt>
				<dd><input style="width:250px;" type="text" id="spsl1" class="textinput" /><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>单价：</dt>
				<dd><input style="width:250px;" type="text" id="spdj1" class="textinput" /><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>单位：</dt>
				<dd><input style="width:250px;" type="text" id="dw1" class="textinput" /><strong class="red">*</strong></dd>
			</dl>
			<dl style="height: 30px;">
				<dt>备注：</dt>
				<dd><input style="width:250px;" type="text" id="bz1"  class="textinput"/></dd>
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
