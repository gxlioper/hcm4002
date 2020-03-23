<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>卡充值</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/layout1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/membership_card.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js"></script>
<style type="text/css">
#fapiao_msg{ position: absolute; font-weight:normal; margin-left:200px; top:330px; width:300px;height:130px; border:1px solid #666; background:#999;}
#fapiao_msg label{ width:100px; display:inline-block; text-align:left;}
#fapiao_msg input[type=text]{ width:180px;}
</style>
<script type="text/javascript">
$(function(){
	$("#c_amount").validatebox({
		required: true,
		validType:'Number'
	});
	$("#c_points").validatebox({
		validType:'Number1'
	});
	$("#ser_num").validatebox({
		required:true,
		validType:'CHS'
	});
	$.extend($.fn.validatebox.defaults.rules,{
			
			Number:{
				validator:function(value){
					 var reg =/^[0-9]+([.]{1}[0-9]+){0,1}$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			},
			Number1:{
				validator:function(value){
					 var reg =/^[0-9]*$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			}
		});
	$("#ser_card_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			serchCardinfoByNum();
		} 
	}); 
	getCharingType();
	
	
	$("#c_amount").numberbox({
	    "onChange":function(){
	        $("#s_amount").numberbox('setValue',$("#c_amount").numberbox('getValue'));
	    }
  });
	
   $("#s_amount").numberbox({
	    "onChange":function(){
	        $("#yingshou").html($("#s_amount").numberbox('getValue'));
	    }
  });
});

function serchCardinfoByNum(type){
	if(type == 1){
		clera_all();
		var card_num = read_card_vip();
		if(card_num.split("-")[0] == 'error'){
			$("#message_card").html(card_num.split("-")[1]);
		}else{
			$("#ser_card_num").val(card_num.split("-")[1]);
		}
	}
	if($("#ser_card_num").val() == ''){
		$("#ser_card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#ser_card_num").val())){
		$("#ser_card_num").focus();
		return;
	}
	$.ajax({
		url:'getCardInfoByNum.action?card_num='+$("#ser_card_num").val(),
		type:'post',
		success:function(data){
			clera_all(1);
			if(data == 'null'){
				$("#message_card").html('该卡号不存在!');
				//$("#ser_card_num").val('');
				$("#ser_card_num").select();
				$("#ser_card_num").focus();
			}else{
				var obj = eval("("+data+")");
				if(obj.card_type == '2' && $("#is_charge_bearer_card").val() != 'Y'){
					$("#message_card").html('该卡为不记名卡，不能充值!');
					$("#ser_card_num").val('');
					$("#ser_card_num").select();
					$("#ser_card_num").focus();
					return;
				}
				if(obj.status == '3'){
					$("#message_card").html('该卡号已挂失，不能充值!');
					//$("#ser_card_num").val('');
					$("#ser_card_num").select();
					$("#ser_card_num").focus();
					return;
				}else if(obj.status == '2'){
					$("#message_card").html('该卡号已锁定，不能充值!');
					//$("#ser_card_num").val('');
					$("#ser_card_num").select();
					$("#ser_card_num").focus();
					return;
				}else if(obj.status == '4'){
					$("#message_card").html('该卡号已作废，不能充值!');
					//$("#ser_card_num").val('');
					$("#ser_card_num").select();
					$("#ser_card_num").focus();
					return;
				}
				$("#member_id").val(obj.member_id);
				$("#member_name").val(obj.member_name);
				$("#card_id").val(obj.id);
				$("#status_y").val(obj.status_y);
				$("#card_type_y").val(obj.card_type_y);
				$("#amount").val(obj.amount);
				$("#card_level_y").val(obj.card_level_y);
				$("#card_count").val(obj.card_count);
				
				$("#c_amount").focus();
			}
		}
	});
}

function clera_all(type){
	if(type != 1){
		$("#ser_card_num").val('');
	}
	$("#message_card").html('');
	$("#member_id").val('');
	$("#member_name").val('');
	$("#card_id").val('');
	$("#status_y").val('');
	$("#card_type_y").val('');
	$("#amount").val('');
	$("#card_level_y").val('');
	$("#card_count").val('');
	$("#c_amount").val('');
	$("#c_points").val('');
	$("#remark").val('');
	$("#yingshou").html('0');
	$("#shishou").html('0');
	$("#c_amount").numberbox('setValue','');
	$("#s_amount").numberbox('setValue','');
	$("#invoice_name").val('');// 发票抬头
	$("#invoice_type").val('');// 发
	getCharingType();
	
}
function saveCardAmount(){

	var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	var reg1= /^[0-9]*$/;
	var yingshou = Number($("#yingshou").html());
	var shishou = Number($("#shishou").html());
	var cz_amount = $("#c_amount").numberbox('getValue');
	if($("#card_id").val() == ''){
		$("#ser_card_num").focus();
		return;
	}else if(cz_amount == ''){
		$("#c_amount").focus();
		$.messager.alert('提示信息', "请输入充值金额!","error");
		return;
//	}else if(!reg.test(cz_amount)){
//		$("#c_amount").focus();
//		return;
	}else if($("#c_points").val()!='' && !reg1.test($("#c_points").val())){
		$("#c_points").focus();
		return;
	}
	
	if(yingshou != shishou){
		$.messager.alert('提示信息', "应收不等于实收请先进行反算！","error");
		return;
	}
		var charingWay = new Array();
		var obj1 = $("input[name=sffs_box]:checked");
		if(obj1.length > 0){
			for(var i=0;i<obj1.length;i++){
				charingWay.push({"charging_way":$(obj1[i]).val(),"amount":$("#sffs_"+$(obj1[i]).val()).val()});
			}
		}else{
			$.messager.alert('提示信息', "请选择收费方式!",'error');
			return;
		}
		var titleInfo = $("#invoice_name").val();// 发票抬头
		var invoiceType = $("#invoice_type").val();// 发票内容
		var invoiceNum = $("#invoice_num").val();// 发票编码
		
		var isPrintRecepit = "N";// 是否打印发票
		if ($("#fapiao")[0].checked)
			isPrintRecepit = "Y";
		
		if(isPrintRecepit == 'Y'){
			if($("#invoice_num").val() == ''){
				$("#invoice_num").focus();
				return;
			}else if($("#invoice_name").val() == ''){
				$("#invoice_name").focus();
				return;
			}else if($("#invoice_type").val() == ''){
				$("#invoice_type").focus();
				return;
			}
		}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$.ajax({
        url:'saveCardPerpaid.action?language='+$("#language").val(),  
        data:{
          'card_id':$("#card_id").val(),
          'card_num':$("#ser_card_num").val(),
          'member_id': $("#member_id").val(),
          'amount':$("#amount").val(),
          'c_amount':cz_amount,//充值金额
          'c_points':$("#c_points").val(),
          'card_remark':$("#remark").val(),
          'charingWays':JSON.stringify(charingWay),
	      'isPrintRecepit':isPrintRecepit,
	      'title_info':$("#invoice_name").val(),
	      'invoice_type':$("#invoice_type").val(),
	      'invoice_num':$("#invoice_num").val(),
	      'sale_amount':shishou//实际金额
          },          
        type: "post",//数据发送方式   
          success: function(data){
          	$(".loading_div").remove();
				var state = eval("("+data+")");
	        		if(state.flag == 'error'){
	        			$.messager.alert('提示信息', state.info,'error');
	        			return;
	        		}
	        		serchCardinfoByNum();
	        		getCharingType();
	        		$("#yingshou").html('0');
					$("#shishou").html('0');
					$("#c_amount").numberbox('setValue','');
					$("#s_amount").numberbox('setValue','');
					
	        		$("#fapiao")[0].checked = false;
	        		$("#fapiao_msg").css("display", "none");
            		$("#invoice_name").val('');
            		$("#invoice_type").val('');
            		$("#invoice_num").val('');
	            		
	        		if (isPrintRecepit == "N") {
	    				$.messager.alert('提示信息', state.info,'info');
	    			} else {
	    				$.messager.alert('提示信息', "售卡成功！正在打印发票。",'info');
	    				if($("#invoiceprinttype").val()=='1'){
		    				if($("input[name='invoice_l']:checked").val() == 'mx'){
		    					fapiao_point_mx($("#invoice_num").val());
		    				}else{
		    					fapiao_point($("#invoice_num").val());
		    				}
	    				}else{
	    					printreport_invoice(state.user_id,state.account_num);
	    				}
	    			}
	        		if($("#isShowInvoicePage").val() == 'Y'){
	        			load_invoice();
	        		}else{
	        			
	        			$("#fapiao")[0].checked = false;
	            		load_invoice();
	            		$("#invoice_name").val('');
	            		$("#invoice_type").val('');
	            		$("#invoice_num").val('');
	        		}
	        		$("#dlg-qrsk").dialog('close');
        		
        		
            },
            error:function(){
            	$(".loading_div").remove();
            	$.messager.alert('提示信息', "操作失败！",function(){});
            }  
    });
}
$(function(){
	$('input').attr("maxlength","20");
	
});
function getCharingType(){//获取收费方式
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=SFFSLX',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			for(var i=0;i<6;i++){
				if(i< obj.length){
					if(obj[i].id == '122'){
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
			}
			$("#charingtype").html(str);
			
			if(obj.length > 6){
				str = '';
				for(var i=6;i<obj.length;i++){
					if(obj[i].id == '122'){
					}else{
						str += '<dl><dd><input name="sffs_box" onclick="click_sfbox(1,this)" style="margin-top:6px;" type="checkbox" style="" value="'+obj[i].id+'"/></dd><dt style="width:80px;">'+obj[i].name+'</dt><dd><input id="sffs_'+obj[i].id+'" onkeyup="keyup_sf(this)" onblur="blur_sf(this)" style="width:50px;" value="0" type="text"/>&nbsp;元</dd></dl>';
					}
				}
				$("#charingtype2").show();
				$("#charingtype2").html(str);
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
//收费方式金额限制输入数字
function keyup_sf(dom){
	$(dom).val($(dom).val().replace(/[^(\-?)\d.]/g, ''));
}
//收费方式金额失去焦点计算金额
function blur_sf(dom){
	if($(dom).val() == '' || $(dom).val() == 0){
		$(dom).val(0);
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = false;
	}else{
		$(dom).parent().parent().children('dd').eq(0).children('input')[0].checked = true;
	}
	click_sfbox();
}
//选择收费方式，计算实收金额与找零
function click_sfbox(type,ths){
	if(ths != undefined){
		if($(ths)[0].checked){
			var ys = Number($("#yingshou").html());
			var ss = Number($("#shishou").html());
			var sy = ys - ss;
			if(sy != 0){
				$("#sffs_"+$(ths).val()).val(sy);
			}
		}else{
			$("#sffs_"+$(ths).val()).val(0);
		}
	}
	
	var obj = $("input[name=sffs_box]:checked");
	var shishoujine = 0.0;
	if(obj.length > 0){
		for(var i=0;i<obj.length;i++){
			shishoujine += Number($("#sffs_"+$(obj[i]).val()).val());
		}
	}
	$("#shishou").html(decimal(shishoujine,2));
//	var yishou = Number($("#yingshou").html());
}

//获取发票最大票号
function load_invoice() {
	if ($("#fapiao")[0].checked) {
		$.ajax({
			url : 'getMaxInvoiceNum.action',
			data : {},
			type : 'post',
			success:function(data){
				$(".loading_div").remove();
				$("#invoice_num").val(data);
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
		$("#fapiao_msg").css("display", "block");
	} else {
		$("#fapiao_msg").css("display", "none");
	}
}

function printreport_invoice(user_id,acc_num){
	var exeurl="invoiceService://"+user_id+"$1$"+acc_num;
	RunReportExe(exeurl);
}
function RunReportExe(strPath) {
	 location.href=strPath;
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
</head>
<body> 
<input type="hidden"  id="isShowInvoicePage" value="<s:property value="isShowInvoicePage"/>"  />
<input type="hidden" id="invoiceprinttype"  value="<s:property value="invoiceprinttype"/>" />
<input type="hidden" id="is_charge_bearer_card"  value="<s:property value="model.is_charge_bearer_card"/>" />
<div style="display:none;">
	<OBJECT id=MWRFATL <s:property value="card_reader_ocx"/>></OBJECT>
	<input type="hidden" id="card_reader_code" value="<s:property value="card_reader_code"/>"/>
</div>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="margin-left:120px;">卡号：&nbsp;&nbsp;<input type="text" id="ser_card_num" name="ser_ser_num" class="textinput"/></dd>
					<!-- <dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;height:27px;" onclick="javascript:serchCardinfoByNum()">读卡(查询)</a></dd> -->
					<dd><a href="javascript:void(0)" onclick="serchCardinfoByNum(1)" class="button btn-readcard"></a></dd>
					<dt  class="autoWidth"><span  id="message_card" class="red"></span></dt>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
	<fieldset style="margin:5px;padding-right:0;height:95%">
	<legend><strong>充值信息</strong></legend>
		<form id="add1Form">
			<div class="formdiv">
				<div class="formdiv fomr3" style="padding-top:20px;">
					<dl>
						<dt><input type="hidden" name="member_id" id="member_id"/>姓名：</dt>
						<dd><input type="text" id="member_name" name="member_name" disabled="disabled" class="textinput"/></dd>
						<dt><input type="hidden" name="card_id" id="card_id"/>卡状态：</dt>
						<dd><input type="text" id="status_y" name="status_y" disabled="disabled" class="textinput"/></dd>
						<dt>卡类型：</dt>
						<dd><input type="text" id="card_type_y" name="card_type_y" disabled="disabled" class="textinput"/></dd>
					</dl>
					<dl>
						<dt>卡余额：</dt>
						<dd><input type="text" id="amount" name="amount" disabled="disabled" class="textinput"/></dd>
						<dt>卡等级：</dt>
						<dd><input type="text" id="card_level_y" name="card_level_y" disabled="disabled" class="textinput"/></dd>
						<dt>消费次数：</dt>
						<dd><input type="text" id="card_count" name="card_count" disabled="disabled" class="textinput"/></dd>
					</dl>
					<dl>
						<dt>充值金额：</dt>
						<dd><input type="text"   id="c_amount" name="c_amount" class="easyui-numberbox" /></dd>
						<dt>可得积分：</dt>
						<dd><input type="text" id="c_points" name="c_points" class="textinput" /></dd>
						<dt>备注：</dt>
						<dd><input type="text" id="remark" name="remark" class="textinput"/></dd>
					</dl>
					
					<dl>
						<dt>实收金额：</dt>
						<dd><input type="text"  id="s_amount" name="s_amount" class="easyui-numberbox" /></dd>
					</dl>
				</div>
			</div>
		</form>
		
		
		
		<fieldset style="margin:5px;padding-right:0;height:45%;width: 60%;">
	<legend><strong>选择收费方式</strong></legend>
		<input id="shoufeileixing" type="hidden"/>
		<input id="shoufeisale_trade_num" type="hidden"/>
		<form id="add1Form" style="margin-top: 10px;margin-left: 30px;">
			<span style="float: left; font-size: 16px; margin-top: 8px;">收费方式：</span>
						<div  id="charingtype" class="user-query" style="width: 200px; float: left;background:#ccc;height:180px;"></div>
						<div  id="charingtype2" class="user-query" style="width: 200px; float: left;display:none;background:#ccc;height:180px;"></div>
						<div class="user-query" style="font-size:16px;margin-top:3px;float: left;">
							<dl>
								<dd style="font-weight:bold;width:220px;">应收总金额：<font id="yingshou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							</dl>
							<dl>
								<dd style="font-weight:bold;width:220px;color:#f00;">实收总金额：<font id="shishou">0</font>元&nbsp;&nbsp;&nbsp;</dd>
							</dl>
							<dl><dd>票据打印：</dd><dd><input type="checkbox" id="fapiao" onclick="load_invoice()"/></dd><dd>打印发票</dd></dl>
								<div id="fapiao_msg" style="display: none">
									<div style="height: 30px; margin-top: 5px; margin-left: 5px;">
										<label>发票号：</label><input id="invoice_num" readonly="readonly" type="text" class="textinput"/>
									</div>
									<div style="height: 30px; margin-left: 5px;">
										<label>发票抬头：</label><input id="invoice_name" type="text" class="textinput"/>
									</div>
									<div style="height: 30px; margin-left: 5px;">
										<label>发票内容：</label><input id="invoice_type" type="text" class="textinput"/>
									</div>
									<div style="height: 30px; margin-left: 5px;text-align: center;margin-top: 7px;">
										<input name="invoice_l" type="radio"  value="mx"/>详细发票
										<input style="margin-left: 30px;" name="invoice_l" type="radio" value="jd" checked="checked"/>简单发票
									</div>
								</div>
						</div>
		</form>	
	</fieldset>
	
	<dl>
		<dd style="margin-left:445px;"><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveCardAmount();">保存</a></dd>
		
		<dd><a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:clera_all()">重置</a></dd>
	</dl>
	</fieldset>
</div>
</div>
</body>
</html>