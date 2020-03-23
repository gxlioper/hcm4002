<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#deadline').datebox({ disabled:true });
	$.ajax({
		url:'getDatadis.action?com_Type=KDJ',
		type:'post',
		success:function(data){
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
			$("#card_level_r").html(str);
		}
	});
});
$(function(){
	$("#card_num_r").validatebox({
		required: true,
		validType:'CHS'
	});
	$("#amount_r").validatebox({
		required:true,
		validType:'Number'
	});
	$("#limit_card_count_r").validatebox({
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
});
function serchCardinfoByNum(){
	clera_all();
	var card_num = read_card_vip();
	if(card_num.split("-")[0] == 'error'){
		$.messager.alert('提示信息', card_num.split("-")[1],function(){});
		return;
	}
	$("#card_num_r").val(card_num.split("-")[1]);
	$.ajax({
		url:'getCardInfoByNum.action?card_num='+card_num.split("-")[1],
		type:'post',
		success:function(data){
			if(data == 'null'){
				$.messager.alert('提示信息', '该卡号不存在!',function(){});
				return;
			}else{
				var obj = eval("("+data+")");
				$("#card_id_r").val(obj.id);
				$("#physical_num_r").val(obj.physical_num);
				$("#card_pwd_r").val(obj.card_pwd);
				$("#amount_r").val(obj.amount);
				$("#limit_card_count_r").val(obj.limit_card_count);
				$("#card_level_r").val(obj.card_level);
				$("#deadline_r").datebox('setValue',obj.deadline);
				$("#card_remark_r").val(obj.remark);
				$("#member_name_r").val(obj.member_name);
				$("#card_type_r").val(obj.card_type);
				$("#sale_amount_r").val(obj.sale_amount);
				$("#discount_r").val(obj.discount);
			}
		}
	});
}
function clera_all(type){
	$("#card_num_r").val('');
	$("#message_card_r").html('');
	$("#member_id_r").val('');
	$("#member_name_r").val('');
	$("#card_id_r").val('');
	$("#physical_num_r").val('');
	$("#card_pwd_r").val('');
	$("#amount_r").val('');
	$("#limit_card_count_r").val('');
	$("#card_level_r").val('');
	$("#deadline_r").datebox('setValue','');
	$("#card_remark_r").val('');
	$("#sale_amount_r").val('');
	$("#discount_r").val('');
}

</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" id="member_id_r" value="<s:property value="member_id"/>"/>姓名：</dt>
				<dd><input type="text" id="member_name_r" value="<s:property value="member_name"/>"  disabled="disabled" class="textinput"/><strong class="red">*</strong></dd>
				<dt><input type="hidden" id="card_id_r" value="<s:property value="card_id"/>"/><input type="hidden" name="physical_num" id="physical_num" value="<s:property value="physical_num"/>"/>卡号：</dt>
				<dd><input type="text" id="card_num_r" value="<s:property value="card_num"/>" disabled="disabled" class="textinput"/><strong class="red">*</strong></dd>
				
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd><input type="password" id="card_pwd_r" disabled="disabled" value="<s:property value="card_pwd"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_type_r" style="width:143px" disabled="disabled">
				<option value="1" selected="selected">记名</option>
				<option value="2">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>面值：</dt>
				<dd><input type="text" id="amount_r" disabled="disabled" value="<s:property value="amount"/>" class="textinput"/><strong class="red">*</strong></dd>
				<%-- <dt>卡状态：</dt>
				<dd><select  id="status" name="status" style="width:143px">
				<option value="1" selected="selected">正常</option>
				<option value="2">锁定</option>
				<option value="3">挂失</option>
				</select><strong class="red">*</strong></dd> --%>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline_r" style="height:27px;" class="easyui-datebox"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" disabled="disabled" id="limit_card_count_r" value="<s:property value="limit_card_count"/>" class="textinput"/></dd>
				<dt>卡等级：</dt>
				<dd><select  id="card_level_r" style="width:143px" disabled="disabled">
				<!-- <option value="1" selected="selected">普通</option>
				<option value="2">金卡</option>
				<option value="3">银卡</option> -->
				</select></dd>
			</dl>
			<dl>
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount_r" disabled="disabled" value="<s:property value="sale_amount"/>" class="textinput"/></dd>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount_r" disabled="disabled" Style=" -webkit-appearance:none !important;" min="0" max="10" value="<s:property value="discount"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><input type="text" id="card_remark_r" style="width:450px;" disabled="disabled" value="<s:property value="card_remark"/>" class="textinput"/></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:serchCardinfoByNum();">读卡</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
