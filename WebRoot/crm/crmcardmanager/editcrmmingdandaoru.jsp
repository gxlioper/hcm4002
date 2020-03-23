<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

$(document).ready(function () {
	$('#birthday').datebox({ editable:false });
	var sex = '<s:property value="sex"/>';
	if( sex=='男'){
		$("input[name=sex]").eq(0).attr("checked","checked");
	}else{
		$("input[name=sex]").eq(1).attr("checked","checked");
	}
	getNationAndLevel();
	});
function getNationAndLevel(){
	$.ajax({
		url:'getDatadis.action?com_Type=MZLX',
		type:'post',
		success:function(data){
			var str = '';
			var obj=eval(data);
			var nation = '<s:property value="nation"/>';
			for(var i=0;i<obj.length;i++){
				if(nation == obj[i].id){
					str += '<option value="'+obj[i].id+'" selected="selected">'+obj[i].name+'</option>';
				}else{
					str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
			}
			$("#nation").html(str);
		}
	});
	$.ajax({
		url:'getDatadis.action?com_Type=HYJB',
		type:'post',
		success:function(data){
			var str = '';
			var obj=eval(data);
			var member_level = '<s:property value="level"/>';
			for(var i=0;i<obj.length;i++){
				if(member_level == obj[i].id){
					str += '<option value="'+obj[i].id+'" selected="selected">'+obj[i].name+'</option>';
				}else{
					str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
				}
				
			}
			$("#level").html(str);
		}
	});
}
$(function(){
	$("#user_name,#id_number").validatebox({
		required: true
	});
	$("#phone").validatebox({
		validType:'phone'
	});
	$("#integral").validatebox({
		validType:'Number'
	});
	$("#id_number").validatebox({
		validType:'id_num'
	});
	$.extend($.fn.validatebox.defaults.rules,{
			Number:{
				validator:function(value){
					 var reg =/^[0-9]*$/;          
					  return reg.test(value);
				},
				message:'只能输入数字'
			},
			phone:{
				validator:function(value){
					 var reg =/[1][3,4,5,7,8][0-9]{9}$/;          
					  return reg.test(value);
				},
				message:'电话格式错误'
			},
			id_num:{
				validator:function(value){
					 var reg =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|x|X)$/;
					 var reg1 =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
					 return (reg.test(value) || reg1.test(value));
				},
				message:'身份证格式错误'
			}
		});
	$("#id_number").blur(function(){
		var flag=$("#id_number").validatebox('isValid');
		if(flag){
			if(get_sexByIdNum($("#id_number").val()) ==1){
				$("input[name=sex]").eq(0).attr("checked","checked");
			}else{
				$("input[name=sex]").eq(1).attr("checked","checked");
			}
			$('#birthday').datebox('setValue',get_birthyByIdNum($("#id_number").val()));
				$.ajax({
					url:'checkCardMemberIdNum.action?id_num='+$("#id_number").val()+'&id='+$("#id").val(),
					type:'post',
					success:function(data){
						if(data=='no'){
							$("#message").attr('value','no');
							$("#message").html('该身份证已注册');
							return true;
						}else if(data=='ok'){
							$("#message").attr('value','ok');
							$("#message").html('');
							return false;
						}
					}
				});
			}
	});
});
function f_cardMembersave(){
	var reg =/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|x|X)$/;
	var reg1 =/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	var reg2 =/[1][3,4,5,7,8][0-9]{9}$/;
	var reg3 =/^[0-9]*$/;
	if($("#user_name").val() == ''){
		$("#user_name").focus();
		return;
	}else if($("#id_number").val() == ''){
		$("#id_number").focus();
		return;
	}else if(!reg.test($("#id_number").val()) && !reg1.test($("#id_number").val())){
		$("#id_number").focus();
		return;
	}else if($("#message").html() != ''){
		$("#id_number").focus();
		return;
	}else if($("#phone").val() != '' && !reg2.test($("#phone").val())){
		$("#phone").focus();
		return;
	}
//	else if($("#points").val() != '' && !reg3.test($("#points").val())){
//		$("#points").focus();
//		return;
//	}
	$.ajax({
        url:'updateTemporaryCustomerInfo.action',  
        data:{
          id:$("#id").val(),
          user_name:$("#user_name").val(),
          id_num:$("#id_number").val(),
          birthday: $("#birthday").datebox('getValue'),
          sex:$("input[name=sex]:checked").val(),
          phone:$("#phone").val(),
          email:$("#email").val(),
          level:$("#level").val(),
          integral:$("#integral").val(),
//        remark:$("#remark").val(),
          nation:$("#nation").val(),
          address:$("#address").val()
//          customer_id:$("#customer_id").val()
          },          
        type: "post",//数据发送方式   
          success: function(data){
   
        		$.messager.alert('提示信息', data.split("-")[0],function(){});
            	 	$("#dlg-edit").dialog("close");
            	 	getTemporaryCustomerInfoList();
            },
            error:function(data){
            	 $("#dlg-edit").dialog("close");
            	$.messager.alert('提示信息', "操作失败！",data);
            }  
    });
}
/**
 * 通过身份证号计算年龄
 * 
 * @return
 */
function get_ageByIdNum(idNum) {
	var myDate = new Date();
	if (idNum.length == 18) {
		var age = parseInt(myDate.getFullYear()) - parseInt(idNum.substring(6, 10));
		return age;
	} else if (idNum.length == 15) {
		var dd = '19' + idNum.substring(6, 8);
		var age = parseInt(myDate.getFullYear()) - parseInt(dd);
		return age;
	}
}
/**
 * 通过身份证号计算出生日期
 * 
 * @param idNum
 * @return
 */
function get_birthyByIdNum(idNum) {
	if (idNum.length == 18) {
		var date = idNum.substring(6, 10) + "-" + idNum.substring(10, 12) + "-" + idNum.substring(12, 14);
		return date;
	} else if (idNum.length == 15) {
		var date = "19" + idNum.substring(6, 8) + "-" + idNum.substring(8, 10) + "-" + idNum.substring(10, 12);
		return date;
	}
}
/**
 * 通过身份证号计算性别
 * 
 * @param idNum
 * @return
 */
function get_sexByIdNum(idNum) {
	if (idNum.length == 18) {
		var sex = parseInt(idNum.substring(16, 17)) % 2;
		return sex;
	} else if (idNum.length == 15) {
		var sex = parseInt(idNum.substring(14, 15)) % 2;
		return sex;
	}
}
$(function(){
	$('input').attr("maxlength","20");
})
</script>
<form id="addForm">
<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl class="member">
				<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>" style="height: 25px;"/>姓名：</dt>
				<dd><input type="text" id="user_name" name="user_name" value="<s:property value="user_name"/>" class="textinput" style="height: 25px;"/><strong class="red">*</strong></dd>
				<dt><input type="hidden" name="customer_id" id="customer_id" value="<s:property value="customer_id"/>"  style="height: 25px;"/>身份证号：</dt>
				<dd><input type="text" id="id_number" name="id_number" value="<s:property value="id_num"/>" class="textinput"  style="height: 25px;"/><strong class="red">*</strong></dd>
				<dt  class="autoWidth"><span  id="message" class="red"></span></dt>
			</dl>
			<dl>
				<dt>生日：</dt>
				<dd><input type="text" id="birthday" name="birthday" value="<s:property value="birthday"/>" class="easyui-datebox"/><strong class="red">*</strong></dd>
				<dt>性别：</dt>
				<dd><input type="radio" id="sex" name="sex" value="男" checked="checked" style="margin-top:4px;" />男
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="sex" name="sex" value="女" />女
				</dd>
			</dl>
			<dl>
				<dt>电话：</dt>
				<dd><input type="text" id="phone" name="phone" value="<s:property value="phone"/>" class="textinput"  style="height: 25px;"/></dd>
				<dt>邮箱：</dt>
				<dd><input type="text" id="email" name="email" value="<s:property value="email"/>" class="textinput"  style="height: 25px;"/></dd>
			</dl>
			<dl>
				<dt>民族：</dt>
				<dd><select id="nation" name="nation" style="width:143px">
				<!-- <option value="1" selected="selected">汉族</option>
				<option value="2">回族</option> -->
				</select></dd>
				<dt>地址：</dt>
				<dd><input type="text" id="address" name="address" value="<s:property value="address"/>" class="textinput"  style="height: 25px;"/></dd>
			</dl>
			<dl class="member">
				<dt>会员等级：</dt>
				<dd><select id="level" name="level" style="width:143px">
				<!-- <option value="1" selected="selected">初级会员</option>
				<option value="2">会员</option> -->
				</select></dd>
				<dt>会员积分：</dt>
				<dd><input type="text" id="integral" name="integral" disabled="disabled" value="<s:property value="integral" />" class="textinput"  style="height: 25px;"/></dd>
			</dl>
			<!--  <dl class="member">
				<dt>备注：</dt>
				<dd><textarea id="remark" name="remark" style="height:40px;width:455px;resize:none;"><s:property value="remark"/></textarea></dd>
				<dt></dt>
				<dd></dd>
			</dl>-->
	</div>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_cardMembersave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</form>