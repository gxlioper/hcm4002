<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
//	$('#deadline').datebox({ editable:false });
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=KDJ',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			var card_level = '<s:property value="card_level"/>';
			for(var i=0;i<obj.length;i++){
				if(card_level == obj[i].id){
					str += '<option value="'+obj[i].id+'" selected="selected">'+obj[i].name+'</option>';
				}else{
					str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#card_level").html(str);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
});
$(function(){
	$("#card_num").validatebox({
		required: true,
		validType:'CHS'
	});
	$("#amount").validatebox({
		required:true,
		validType:'Number'
	});
	$("#sale_amount").validatebox({
		validType:'Number'
	});
	$("#limit_card_count").validatebox({
		validType:'Number'
	});
	$.extend($.fn.validatebox.defaults.rules,{
			
			Number:{
				validator:function(value){
					 var reg =/^[0-9]*$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			}
		});
	$("#card_num").blur(function(){
		var flag=$("#card_num").validatebox('isValid');
		if(flag){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
				$.ajax({
					url:'checkCardNum.action?card_num='+$("#card_num").val(),
					type:'post',
					success:function(data){
						$(".loading_div").remove();
						if(data=='ok'){
							$("#message_card").attr('value','ok');
							$("#message_card").html('');
							return false;
						}else{
							$("#message_card").attr('value','no');
							$("#message_card").html('该卡号已存在');
							return true;
						}
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
			}
	});
	
	
});

function viry_phy_num(phy_num){
	var data;
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getCardInfoByPhyNum.action?physical_num='+phy_num,
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			alert(data);
			data = data;
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
	return data;
}
function getfixcardnum(card_num){
	var v_card_num;
	$.ajax({
		url:'getAddPrefixCardNum.action?card_num='+card_num+'&card_level='+$("#card_level").val(),
		type:'post',
		async:false,//同步请求
		success:function(data){
			if(data.split('-')[0] == 'ok'){
				v_card_num = data.split('-')[1];
			}
		}
	});
	return v_card_num;
}
function f_saveCardInfo(){
	var reg =/^[0-9]*$/;
	if($("#card_num").val() == ''){
		$("#card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#card_num").val())){
		$("#card_num").focus();
		return;
	}else if($("#message_card").html() != ''){
		$("#card_num").focus();
		return;
	}else if($("#amount").val() == ''){
		$("#amount").focus();
		return;
	}else if(!reg.test($("#amount").val())){
		$("#amount").focus();
		return;
	}else if(!reg.test($("#sale_amount").val())){
		$("#sale_amount").focus();
		return;
	}else if($("#limit_card_count").val() != '' && !reg.test($("#limit_card_count").val())){
		$("#limit_card_count").focus();
		return;
	}
	var card_num = getfixcardnum($("#card_num").val());
	if(isCard_num(card_num) == false){
		$.messager.alert("操作提示", "该卡号已存在", "error");
		return;
	}
	if($("#is_start_card_reader").val() == 'Y'){
		var physical_num = get_phsical_num();//获取物理卡号
		if(physical_num.split("-")[0] == 'error'){
			$.messager.alert('提示信息', physical_num.split("-")[1],function(){});
			return;
		}
		
		if(isPhysical_num(physical_num.split("-")[1]) == false){//验证物理卡号
			$.messager.alert('提示信息', '该卡片已经开卡，不能重复开卡!',function(){});
			return;
		}
		var card_info = new Object();
		card_info.card_id = $("#card_id").val();
		card_info.physical_num = physical_num.split("-")[1];
		card_info.card_num = card_num;
	//	card_info.member_id = null;
		card_info.card_pwd = $("#card_pwd").val();
		card_info.card_type = $("#card_type").val();
		card_info.amount = $("#amount").val();
		card_info.limit_card_count = $("#limit_card_count").val();
		card_info.card_level = $("#card_level").val();
		card_info.deadline = $("#deadline").datebox('getValue');
		card_info.card_remark = $("#card_remark").val();
		card_info.discount = $("#discount").val();
		card_info.company = $("#company").val();
		card_info.sale_amount = $("#sale_amount").val();
		var status = make_card_vip(card_info);//写卡信息
		if(status.split("-")[0] == 'ok'){
			$.messager.alert('提示信息', status.split("-")[1],function(){});
	    	$("#dlg-edit").dialog("close");
	    	getGrid();
//			$("#card_num").val('');
//			$("#card_num").focus();
//			alert_autoClose("操作提示", status.split("-")[1],"");

		}else{
			$.messager.alert('提示信息', status.split("-")[1],function(){});
	    	$("#dlg-edit").dialog("close");
		}
	}else{
		$.ajax({
			url:'saveCardInfo.action?language='+$("#language").val(),  
	        data:{
	        	card_id:$("#card_id").val(),
//	        	physical_num:card_info.physical_num,
	        	card_num:card_num,
//	        	member_id:card_info.member_id,
	        	card_pwd:$("#card_pwd").val(),
	        	card_type:$("#card_type").val(),
	        	amount:$("#amount").val(),
	        	limit_card_count:$("#limit_card_count").val(),
	        	card_level:$("#card_level").val(),
	        	deadline:$("#deadline").datebox('getValue'),
	        	card_remark:$("#card_remark").val(),
	        	discount:$("#discount").val(),
	        	company:$("#company").val(),
	        	sale_amount : $("#sale_amount").val()
	        },          
	        type: "post",//数据发送方式 
	        success: function(data){
	        	$.messager.alert('提示信息', '制卡成功!','info');
		    	$("#dlg-edit").dialog("close");
		    	getGrid();
	        },
	        error:function(){
	        	$.messager.alert('提示信息', '制卡失败!','error');
		    	$("#dlg-edit").dialog("close");
	        }  
	    });
	}
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" name="card_id" id="card_id" value="<s:property value="card_id"/>"/>
				    <input type="hidden" id="is_start_card_reader" value="<s:property value="is_start_card_reader"/>"/>卡号：</dt>
				<dd><input type="text" id="card_num" name="card_num" value="<s:property value="card_num"/>" class="textinput"/><strong class="red">*</strong></dd>
				<dt style="width: 73px;" class="autoWidth"><span  id="message_card" class="red"></span></dt>
				<dt style="margin-left: -92px;">密码：</dt>
				<dd><input type="password" id="card_pwd" name="card_pwd" value="<s:property value="card_pwd"/>" class="textinput"/></dd>
			</dl>
			<dl style="display:none;">
				
				<dt>卡类型：</dt>
				<dd><select  id="card_type" name="card_type" style="width:143px" disabled="disabled">
				<option value="1">记名</option>
				<option value="2" selected="selected">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>面值：</dt>
				<dd><input type="text" id="amount" name="amount" value="<s:property value="amount"/>" class="textinput"/><strong class="red">*</strong></dd>
				<%-- <dt>卡状态：</dt>
				<dd><select  id="status" name="status" style="width:143px">
				<option value="1" selected="selected">正常</option>
				<option value="2">锁定</option>
				<option value="3">挂失</option>
				</select><strong class="red">*</strong></dd> --%>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline" name="deadline" class="easyui-datebox" style="height:27px;"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" id="limit_card_count" name="limit_card_count" value="<s:property value="limit_card_count"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_level" name="card_level" style="width:143px">
				<!-- <option value="1" selected="selected">普通</option>
				<option value="2">金卡</option>
				<option value="3">银卡</option> -->
				</select></dd>
			</dl>
			<dl>
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount" value="<s:property value="sale_amount"/>" class="textinput"/></dd>
				<dt>单位：</dt>
				<dd><input type="text" id="company" name="company" value="<s:property value="company"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount" name="discount" Style=" -webkit-appearance:none !important;" min="0" max="10" value="10" class="textinput"/></dd>
				<dt>备注：</dt>
				<dd><input type="text" id="card_remark" name="card_remark" value="<s:property value="card_remark"/>" class="textinput"/></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveCardInfo();">制卡</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
