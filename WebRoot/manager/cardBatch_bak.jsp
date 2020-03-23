<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	$('#deadline_p').datebox({ editable:false });
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
			$("#card_level_p").html(str);
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
	$("#amount_p").validatebox({
		required: true,
		validType:'Number'
	});
	$("#sale_amount_p").validatebox({
		validType:'Number'
	});
	$("#limit_card_count_p").validatebox({
		validType:'Number1'
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
	}else if($("#amount_p").val() == ''){
		$("#amount_p").focus();
		return;
	}else if(!reg.test($("#amount_p").val())){
		$("#amount_p").focus();
		return;
	}else if(!reg.test($("#sale_amount_p").val())){
		$("#sale_amount_p").focus();
		return;
	}else if($("#limit_card_count_p").val()!='' && !reg1.test($("#limit_card_count_p").val())){
		$("#limit_card_count_p").focus();
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
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
        url:'saveCardBatch.action?language='+$("#language").val(),  
        data:{
          s_card_num:$("#s_card_num").val(),
          e_card_num:$("#e_card_num").val(),
          card_pwd: $("#card_pwd_p").val(),
          amount:$("#amount_p").val(),
          card_type:$("#card_type_p").val(),
          deadline:$("#deadline_p").datebox('getValue'),
          limit_card_count:$("#limit_card_count_p").val(),
          card_level:$("#card_level_p").val(),
          card_remark:$("#card_remark_p").val(),
          discount:$("#discount_p").val(),
          company:$("#company_p").val(),
          sale_amount:$("#sale_amount_p").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
        	  $(".loading_div").remove();
        		$.messager.alert('提示信息', data,'info');
        		$('#dlg-pl_card').dialog('close');
        		$("#cardinfolist").datagrid('reload');
            },
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			} 
    });
}
function wk_gd_w(testStr){
	var strs = testStr.split(/[^0-9]/);//根据不是数字的字符拆分字符串
    var numStr = strs[strs.length-1];//取出最后一组数字
    return testStr.substring(0, testStr.length-numStr.length);
}
</script>
<form id="add1Form">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>起始卡号：</dt>
				<dd><input type="text" maxlength="20" id="s_card_num" name="s_card_num" class="textinput"/><strong class="red">*</strong></dd>
				<dt>结束卡号：</dt>
				<dd><input type="text" maxlength="20" id="e_card_num" name="e_card_num" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>密码：</dt>
				<dd><input type="password" maxlength="20"  id="card_pwd_p" name="card_pwd_p" class="textinput"/></dd>
				<dt style="display:none;">卡类型：</dt>
				<dd style="display:none;"><select  id="card_type_p" name="card_type_p" style="width:143px" disabled="disabled">
				<option value="1">记名</option>
				<option value="2" selected="selected">不记名</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dt>面值：</dt>
				<dd><input type="text" id="amount_p" maxlength="20" name="amount_p" class="textinput"/><strong class="red">*</strong></dd>
				<%-- <dt>卡状态：</dt>
				<dd><select  id="status" name="status" style="width:143px">
				<option value="1" selected="selected">正常</option>
				<option value="2">锁定</option>
				<option value="3">挂失</option>
				</select><strong class="red">*</strong></dd> --%>
				<dt>有效日期：</dt>
				<dd><input type="text" id="deadline_p" name="deadline_p" class="easyui-datebox"/></dd>
			</dl>
			<dl>
				<dt>限制次数：</dt>
				<dd><input type="text" maxlength="20" id="limit_card_count_p" name="limit_card_count_p" value="<s:property value="limit_card_count"/>" class="textinput"/></dd>
				<dt>卡类型：</dt>
				<dd><select  id="card_level_p" name="card_level_p" style="width:143px">
				<!-- <option value="1" selected="selected">普通</option>
				<option value="2">金卡</option>
				<option value="3">银卡</option> -->
				</select></dd>
			</dl>
			<dl>
				
				<dt>售卡金额：</dt>
				<dd><input type="text" id="sale_amount_p" class="textinput"/></dd>
				<dt>单位：</dt>
				<dd><input type="text" id="company_p" value="<s:property value="company"/>" class="textinput"/></dd>
			</dl>
			<dl>
				<dt>折扣率：</dt>
				<dd><input type="number" id="discount_p" Style=" -webkit-appearance:none !important;" min="0" max="10" value="10" class="textinput"/></dd>
				<dt>备注：</dt>
				<dd><input type="text" id="card_remark_p" name="card_remark_p" class="textinput"/></dd>
			</dl>
		</div>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:saveCardAmount();">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-pl_card').dialog('close')">关闭</a>
	</div>
</div>
</form>
