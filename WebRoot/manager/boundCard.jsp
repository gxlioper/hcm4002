<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#deadline').datebox({ disabled:true });
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
	$("#card_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			serchCardinfoByNum();
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
});
function serchCardinfoByNum(type){
	if(type == 1){
		clera_all();
		var card_num = read_card_vip();
		if(card_num.split("-")[0] == 'error'){
			$.messager.alert('提示信息', card_num.split("-")[1],function(){});
		}else{
			$("#card_num").val(card_num.split("-")[1]);
		}
	}
	if($("#card_num").val() == ''){
		$("#card_num").focus();
		return;
	}else if(/[\u0391-\uFFE5]/g.test($("#card_num").val())){
		$("#card_num").focus();
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getCardInfoByNum.action?card_num='+$("#card_num").val(),
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			clera_all(1);
			if(data == 'null'){
				$("#message_card").html('该卡号不存在!');
				$("#card_num").select();
				$("#card_num").focus();
			}else{
				var obj = eval("("+data+")");
				if(obj.card_type == '1'){
					$("#message_card").html('该卡已绑定!');
					$("#card_num").select();
					$("#card_num").focus();
					return;
				}
				$("#card_id").val(obj.id);
				$("#physical_num").val(obj.physical_num);
				$("#card_pwd").val(obj.card_pwd);
				$("#amount").val(obj.amount);
				$("#limit_card_count").val(obj.limit_card_count);
				$("#card_level").val(obj.card_level);
				$("#deadline").datebox('setValue',obj.deadline);
				$("#card_remark").val(obj.remark);
				$("#sale_amount").val(obj.sale_amount);
				$("#company").val(obj.company);
				$("#discount").val(obj.discount);
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}
function clera_all(type){
	if(type != 1){
		$("#card_num").val('');
	}
	$("#message_card").html('');
//	$("#member_id").val('');
//	$("#member_name").val('');
	$("#card_id").val('');
	$("#physical_num").val('');
	$("#card_pwd").val('');
	$("#amount").val('');
	$("#limit_card_count").val('');
	$("#card_level").val('');
	$("#deadline").datebox('setValue','');
	$("#card_remark").val('');
	$("#sale_amount").val('');
	$("#company").val('');
	$("#discount").val('');
}

function f_saveboundCard(){
	if($("#card_id").val() == ''){
		$.messager.alert("操作提示", "请先查询需要绑定的卡片!", "error");
		return;
	}
	$.ajax({
		url:'saveCardInfo.action',  
        data:{
        	card_id:$("#card_id").val(),
        	physical_num:$("#physical_num").val(),
        	card_num:$("#card_num").val(),
        	member_id:$("#member_id").val(),
        	card_pwd:$("#card_pwd").val(),
        	card_type:$("#card_type").val(),
        	amount:$("#amount").val(),
        	limit_card_count:$("#limit_card_count").val(),
        	card_level:$("#card_level").val(),
        	deadline:$("#deadline").datebox('getValue'),
        	card_remark:$("#card_remark").val(),
        	sale_amount:$("#sale_amount").val(),
        	company:$("#company").val(),
        	discount:$("#discount").val(),
        	arch_num:$("#arch_num").val()
        },          
        type: "post",//数据发送方式 
        success: function(data){
        	$.messager.alert('提示信息', '绑定卡片成功!','info');
        	$("#card-edit").dialog("close");
        	$("#cardinfolist").datagrid('reload');
        },
        error:function(){
        	$.messager.alert('提示信息', '绑定卡片失败!','error');
    		$("#card-edit").dialog("close");
        }  
    });
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt><input type="hidden" name="member_id" id="member_id" value="<s:property value="member_id"/>"/>
					<input type="hidden" name="arch_num" id="arch_num" value="<s:property value="arch_num"/>"/>姓名：</dt>
				<dd><input type="text" id="user_name" name="user_name" value="<s:property value="user_name"/>"  disabled="disabled" class="textinput"/><strong class="red">*</strong></dd>
				<dt><input type="hidden" name="card_id" id="card_id" value="<s:property value="card_id"/>"/><input type="hidden" name="physical_num" id="physical_num" value="<s:property value="physical_num"/>"/>卡号：</dt>
				<dd><input type="text" id="card_num" name="card_num" value="<s:property value="card_num"/>" class="textinput"/><strong class="red">*</strong></dd>
				<dt style="position: absolute; margin-left: 52%; margin-top: 25px;"><span  id="message_card" class="red"></span></dt>
				<dt style="position: absolute;margin-left: 78%; margin-top: -7px;"><a href="javascript:void(0)" onclick="serchCardinfoByNum(1)" class="button btn-readcard"></a></dt>
				
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd><input type="password" id="card_pwd"  disabled="disabled" value="<s:property value="card_pwd"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_type" name="card_type" style="width:143px" disabled="disabled">
				<option value="1" selected="selected">记名</option>
				<option value="2">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>面值：</dt>
				<dd><input type="text" id="amount" disabled="disabled" value="<s:property value="amount"/>" class="textinput"/><strong class="red">*</strong></dd>
				<%-- <dt>卡状态：</dt>
				<dd><select  id="status" name="status" style="width:143px">
				<option value="1" selected="selected">正常</option>
				<option value="2">锁定</option>
				<option value="3">挂失</option>
				</select><strong class="red">*</strong></dd> --%>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline" class="easyui-datebox"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" disabled="disabled" id="limit_card_count" name="limit_card_count" value="<s:property value="limit_card_count"/>" class="textinput"/></dd>
				<dt>卡等级：</dt>
				<dd><select  id="card_level" name="card_level" style="width:143px" disabled="disabled">
				<!-- <option value="1" selected="selected">普通</option>
				<option value="2">金卡</option>
				<option value="3">银卡</option> -->
				</select></dd>
			</dl>
			<dl>
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount" disabled="disabled" value="<s:property value="sale_amount"/>" class="textinput"/></dd>
				<dt>单位：</dt>
				<dd><input type="text" id="company" disabled="disabled" value="<s:property value="company"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount" disabled="disabled" Style=" -webkit-appearance:none !important;" min="0" max="10" value="10" class="textinput"/></dd>
				<dt>备注：</dt>
				<dd><input type="text"  id="card_remark" disabled="disabled" value="<s:property value="card_remark"/>" class="textinput"/></dd>
			</dl>
	</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_saveboundCard();">绑卡</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#card-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>
