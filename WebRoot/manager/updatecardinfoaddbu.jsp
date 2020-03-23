<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#deadline_g').datebox({ editable:false });
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	 $.ajax({
		url:'getDatadis.action?com_Type=KDJ',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '';
			var obj=eval(data);
			var card_level = '<s:property value="model.card_level"/>';
			console.log(card_level);
			for(var i=0;i<obj.length;i++){
				if(card_level == obj[i].id){
					str += '<option value="'+obj[i].id+'" selected="selected">'+obj[i].name+'</option>';
				}else{
					str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#card_level_g").html(str);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	}); 
});
$(function(){
	$("#limit_card_count_g").validatebox({
		validType:'Number1'
	});
	$("#sale_amount_g").validatebox({
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
function xg_saveCardInfo(){
		$.ajax({
			url:'updateCard.action',  
	        data:{
//	        	card_id:$("#card_id").val(),
//	        	physical_num:card_info.physical_num,
	        	card_num:$("#card_num_g").val(),
//	        	member_id:card_info.member_id,
//	        	card_pwd:$("#card_pwd").val(),
//	        	card_type:$("#card_type").val(),
//	        	amount:$("#amount").val(),
	        	limit_card_count:$("#limit_card_count_g").val(),//限制次数
//	        	card_level:$("#card_level").val(),
	        	deadline:$("#deadline_g").datebox('getValue'),
	        	card_remark:$("#card_remark_g").val(),
	        	discount:$("#discount_g").val(),
	        	company:$("#company_g").val(),
	        	sale_amount:$("#sale_amount_g").val()
	        },          
	        type: "post",//数据发送方式 
	        success: function(data){
	        	$.messager.alert('提示信息',data);
	        	$("#cardinfolist").datagrid('reload');
		    	$("#dlg-xg").dialog("close");
	        },
	        error:function(){
	        	$.messager.alert('提示信息', '操作失败','error');
	        }  
	    });
}
</script>
<style>
</style>
<div class="formdiv"  style="margin-top: 50px">
			<dl>
				<dt>卡号：</dt>
				<dd><input type="text" id="card_num_g" disabled="disabled" value="<s:property value="model.card_num"/>" class="textinput"/></dd>
				<dt >密码：</dt>
				<dd><input type="password" id="card_pwd_g" disabled="disabled" value="<s:property value="model.card_pwd"/>" class="textinput"/></dd>
			</dl>
			<dl style="display:none;">
				<dt>卡类型：</dt>
				<dd><select  id="card_type_g" style="width:143px" disabled="disabled">
				<option value="1">记名</option>
				<option value="2" selected="selected">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>卡余额：</dt>
				<dd><input type="text" id="amount_g" disabled="disabled" value="<s:property value="model.amount"/>" class="textinput"/></dd>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline_g" style="height: 26px;"  value="<s:property value="model.deadline"/>"  class="easyui-datebox"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" id="limit_card_count_g" value="<s:property value="model.limit_card_count"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_level_g" disabled="disabled" style="width:143px"></select></dd>
			</dl>
			<dl>
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount_g" value="<s:property value="sale_amount"/>" class="textinput"/></dd>
				<dt>单位：</dt>
				<dd><input type="text" id="company_g" style="width:140px;" value="<s:property value="model.company"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount_g" Style=" -webkit-appearance:none !important;width: 140px;" min="0" max="10" value="<s:property value="model.discount"/>" class="textinput"/></dd>
				<dt>备注：</dt>
				<dd><input type="text"  id="card_remark_g" value="<s:property value="model.card_remark"/>" class="textinput"/></dd>
			</dl>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:xg_saveCardInfo();">修改</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-xg').dialog('close')">关闭</a>
	</div>
</div>
