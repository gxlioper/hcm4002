<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	jiechuxianzhi();
//	$('#deadline_w').datebox({ editable:false });
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=KDJ',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			for(var i=0;i<obj.length;i++){
				str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
			}
			$("#card_level_w").html(str);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
});
$(function(){
	$("#s_card_num").validatebox({
		required: true,
		validType:'CHS'
	});
	$("#e_card_num").validatebox({
		required: true,
		validType:'CHS'
	});
	$("#amount_w").validatebox({
		required: true,
		validType:'Number'
	});
	$("#limit_card_count_w").validatebox({
		validType:'Number1'
	});
	$("#sale_amount_w").validatebox({
		validType:'Number'
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
});

var timer = null;
function saveCardAmount(){
	var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	var reg1= /^[0-9]*$/;
	if($("#s_card_num").val() == ''){
		$("#s_card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#s_card_num").val())){
		$("#s_card_num").focus();
		return;
	}else if($("#e_card_num").val() == ''){
		$("#e_card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#e_card_num").val())){
		$("#e_card_num").focus();
		return;
	}else if($("#amount_w").val() == ''){
		$("#amount_w").focus();
		return;
	}else if(!reg.test($("#amount_w").val())){
		$("#amount_w").focus();
		return;
	}else if(!reg.test($("#sale_amount_w").val())){
		$("#sale_amount_w").focus();
		return;
	}else if($("#limit_card_count_w").val()!='' && !reg1.test($("#limit_card_count_w").val())){
		$("#limit_card_count_w").focus();
		return;
	}else if(/[0-9]$/.test($("#s_card_num").val()) == false){
		$.messager.alert('提示信息', "起始卡号不能以字母结束!",function(){});
		$("#s_card_num").focus();
		return;
	}else if(/[0-9]$/.test($("#e_card_num").val()) == false){
		$.messager.alert('提示信息', "结束卡号不能以字母结束!",function(){});
		$("#e_card_num").focus();
		return;
	}else if($("#s_card_num").val() > $("#e_card_num").val()){
		$.messager.alert('提示信息', "起始卡号不能大于结束卡号!",function(){});
		$("#s_card_num").focus();
		return;
	}else if($("#s_card_num").val().length != $("#e_card_num").val().length){
		$.messager.alert('提示信息', "起始卡号长度必须等于结束卡号长度!",function(){});
		$("#s_card_num").focus();
		return;
	}else if(wk_gd_w($("#s_card_num").val()) != wk_gd_w($("#e_card_num").val())){
		$.messager.alert('提示信息', "起始卡号与结束卡号的固定前几位必须相同!",function(){});
		$("#s_card_num").focus();
		return;
	}
	timer = window.setInterval("wk_make_Card_pl()",2000);
}

function getfixcardnum(card_num){
	var v_card_num;
	$.ajax({
		url:'getAddPrefixCardNum.action?card_num='+card_num+'&card_level='+$("#card_level_w").val(),
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

var status = true;
var card_count = 0;
var old_physical_num = null;
function wk_make_Card_pl(){//五康  批量制卡
	xianzhishuru();
	if(status){//制卡状态   确保每一次制卡成功再开始下一次制卡
		var card_num_b = str_addOne($("#s_card_num").val(),card_count);
		if(card_num_b > $("#e_card_num").val()){//批量制卡完成
			card_count = 0;
			$("#cnum").val('无');
			$("#z_cstatus").val("结束制卡!");
			$("#card_jg").val("批量制卡完成！");
			window.clearInterval(timer);
			getGrid();
			jiechuxianzhi();
			return;
		}
		
		var card_num = getfixcardnum(card_num_b);
		
		$("#cnum").val(card_num);
		$("#z_cstatus").val("开始制卡");
		status = false;
			
		var physical_num = get_phsical_num();//获取物理卡号
			
		if(physical_num.split("-")[0] == 'error'){
			$("#z_cstatus").val(physical_num.split("-")[1]);
			status = true;
			return;
		}
		var p_num = physical_num.split("-")[1];
		if(p_num == old_physical_num){ //根据物理卡号判断是否换了一张卡
			$("#z_cstatus").val("请放入卡片!");
			status = true;
			return;
		}
		
		$("#z_cstatus").val("正在制卡，请稍后!");
		old_physical_num = p_num;
		card_count ++;
		if(isPhysical_num(physical_num.split("-")[1]) == false){//验证物理卡号
			$("#card_jg").val("制卡卡号("+card_num+"),该卡片已经开卡，不能重复开卡！");
			$("#z_cstatus").val("结束制卡!");
			status = true;
			return;
		}
		
		if(isCard_num(card_num) == false){
			$("#card_jg").val("制卡卡号("+card_num+"),该卡号已存在,制卡失败!");
			$("#z_cstatus").val("结束制卡!");
			status = true;
			return;
		}
		
		var card_info = new Object();
		card_info.card_id = $("#card_id").val();
		card_info.physical_num = p_num;
		card_info.card_num = card_num;
//		card_info.member_id = null;
		card_info.card_pwd = $("#card_pwd_w").val();
		card_info.card_type = $("#card_type_w").val();
		card_info.amount = $("#amount_w").val();
		card_info.limit_card_count = $("#limit_card_count_w").val();
		card_info.card_level = $("#card_level_w").val();
		card_info.deadline = $("#deadline_w").datebox('getValue');
		card_info.card_remark = $("#card_remark_w").val();
		card_info.discount = $("#discount_w").val();
		card_info.company = $("#company_w").val();
		card_info.sale_amount = $("#sale_amount_w").val();
		var card_status = make_card_vip(card_info);//写卡信息
		if(card_status.split("-")[0] == 'ok'){
			$("#card_jg").val("制卡卡号("+card_num+"),制卡成功！");
        	$("#z_cstatus").val("结束制卡!");
        	status = true;
        	return;
		}else if(card_status.split("-")[0] == 'error'){
			$("#card_jg").val("制卡卡号("+card_num+"),"+ status.split("-")[1]);
			$("#z_cstatus").val("结束制卡!");
			status = true;
			return;
		}
	}
}
/**
 * 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。
 * @author zxcvbnmzb
 * @param testStr 要+1的字符串
 * @return +1后的字符串
 */
function str_addOne(testStr,count){
     var strs = testStr.split(/[^0-9]/);//根据不是数字的字符拆分字符串
     var numStr = strs[strs.length-1];//取出最后一组数字
     
     if(numStr != null && numStr.length>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
         var num = Number(numStr)+count;//将该数字加一
         num += "";
         //拼接字符串
         return testStr.substring(0, testStr.length-num.length)+num;
     }else{
         return null;
     }
 }

/**
 * 获取字符串最后一次出现的字母 前边的字符串
 * @param testStr
 */
function wk_gd_w(testStr){
	var strs = testStr.split(/[^0-9]/);//根据不是数字的字符拆分字符串
    var numStr = strs[strs.length-1];//取出最后一组数字
    return testStr.substring(0, testStr.length-numStr.length);
}

function xianzhishuru(){
	$("#pingbi").css("display","block");
}
function jiechuxianzhi(){
	$("#pingbi").css("display","none");
}
</script>
<form id="add1Form">
<div id="pingbi" style="height:280px;width:780px;z-index: 223;position:absolute;filter:alpha(opacity=1);opacity: 0.01;background:#000;">
</div>
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" name="card_id" id="card_id" value="<s:property value="card_id"/>"/>
				<input type="hidden" id="is_start_card_reader" value="<s:property value="is_start_card_reader"/>"/>起始卡号：</dt>
				<dd><input type="text" maxlength="20" id="s_card_num" name="s_card_num" class="textinput"/><strong class="red">*</strong></dd>
				<dt>结束卡号：</dt>
				<dd><input type="text" maxlength="20" id="e_card_num" name="e_card_num" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd><input type="password" id="card_pwd_w" name="card_pwd_w" value="<s:property value="card_pwd"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_type_w" name="card_type_w" style="width:143px" disabled="disabled">
				<option value="1">记名</option>
				<option value="2" selected="selected">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>面值：</dt>
				<dd><input type="text" id="amount_w" name="amount_w" value="<s:property value="amount"/>" class="textinput"/><strong class="red">*</strong></dd>
				<%-- <dt>卡状态：</dt>
				<dd><select  id="status" name="status" style="width:143px">
				<option value="1" selected="selected">正常</option>
				<option value="2">锁定</option>
				<option value="3">挂失</option>
				</select><strong class="red">*</strong></dd> --%>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline_w" name="deadline_w" class="easyui-datebox" style="height:27px;"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" id="limit_card_count_w" name="limit_card_count_w" value="<s:property value="limit_card_count"/>" class="textinput"/></dd>
				<dt>卡等级：</dt>
				<dd><select  id="card_level_w" name="card_level_w" style="width:143px">
				<!-- <option value="1" selected="selected">普通</option>
				<option value="2">金卡</option>
				<option value="3">银卡</option> -->
				</select></dd>
			</dl>
			<dl>
				
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount_w" value="<s:property value="sale_amount"/>" class="textinput"/></dd>
				<dt>单位：</dt>
				<dd><input type="text" id="company_w" value="<s:property value="company"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount_w" Style=" -webkit-appearance:none !important;" min="0" max="10" value="10" class="textinput"/></dd>
				<dt>备注：</dt>
				<dd><input type="text" id="card_remark_w" value="<s:property value="card_remark"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt style="font-size: 16px; color:#f00">当前制卡卡号:</dt>
				<dd><input type="text" id="cnum" style="height: 22px;border: 0px none; font-size: 16px; color:#f00" readonly="readonly"/></dd>
				<dt style="font-size: 16px; color:#f00">制卡状态：</dt>
				<dd><input type="text" id="z_cstatus" style="height: 22px;border: 0px none; font-size: 16px; color:#f00" readonly="readonly"/></dd>
			</dl>
			<dl style="margin-top: -20px;">
				<dt style="font-size: 16px; color:#f00">制卡结果:</dt>
				<dd><input type="text" id="card_jg" style="width:450px;height: 22px; border: 0px none; font-size: 16px; color:#f00;" readonly="readonly"/></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
		<div style="float: left;margin-left: 30px;width:60%; color:#666;">
			<dl>
				<dd>注：</dd>
				<dt>1.批量制卡其中一张卡失败,不影响下一张卡制作!</dt>
				
			</dl>
			<dl>
				<dt style="margin-left:5.2%;">2.当听到读卡器响一声,说明制卡成功,取下当前卡,放入下一张卡!</dt>
			</dl>
		</div>
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveCardAmount();">开始制卡</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-pl_card').dialog('close')">关闭</a>
	</div>
</div>
</form>