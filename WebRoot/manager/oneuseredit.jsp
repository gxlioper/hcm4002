<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息修改</title>
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/main.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/tree.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/admin.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/default/ecard/style/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/default/ecard/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>


<script type="text/javascript">
$(document).ready(function(){

   $(this).keypress( function(e) {
   var key = window.event ? e.keyCode : e.which;
   if(key.toString() == "13"){
      return false;
     }
   });
 
});

   
function f_usersave(){
			if (document.getElementById("passwd").value == ''){
		  		alert('密码不能为空！');
				document.getElementById("passwd").focus();
				return;
			}else if (document.getElementById("passwd").value !=  document.getElementById("identify1").value){
		  		alert('两次输入的密码不一致！');
				document.getElementById("passwd").focus();
				return;
			}else if (document.getElementById("name").value == ''){
		  		alert('姓名不能为空！');
				document.getElementById("name").focus();
				return;
			}else if (document.getElementById("tel1").value == ''){
		  		alert('电话不能为空！');
				document.getElementById("tel1").focus();
				return;
			}else if (document.getElementById("email").value != ''){
			    if (!isValidMail(document.getElementById("email").value))
			    {
		  		alert('email地址格式错误！');
				document.getElementById("email").focus();
				return;
				}
			}
			
	 $.ajax({
        url:'oneusereditdo.action?language='+$("#language").val(),  
        data:{
          id:$("#id").val(),
          username:$("#username").val(),
          passwd:$("#passwd").val(),
          name:$("#name").val(),
          pid:$("#pid").val(),
          tel1:$("#tel1").val(),
          sex:$("#sex").val(),
          tel2:$("#tel2").val(),
          email:$("#email").val(),
          usertype:$("#usertype").val()
          },          
        type: "post",//数据发送方式   
          success: function(text){  
              document.getElementById("mes").innerHTML='<s:text name="opermsg"/>:\n'+text;
            }     
    });              
}

function isValidMail(sText) {
var reMail = /^(?:[a-z\d]+[_\-\+\.]?)*[a-z\d]+@(?:([a-z\d]+\-?)*[a-z\d]+\.)+([a-z]{2,})+$/i;
 return reMail.test(sText)
}
</script>
</head>
<body>
<!--div class="head white">
<div>用户信息修改</div>
</div-->
<div class="head white"><div><span class="head-em"><span>用户信息修改</span></span></div></div>
<div class="formWidget">
	<div class="up"><div></div></div>
	<div class="box">
		<div class="form rechargeForm">
		<br/>
		<dl>
	      <dt>登录名称：</dt>
		  <dd><s:property value="username"/></dd>
		</dl>
		<!--dl>
	      <dt>用户类型：</dt>
		  <dd><s:property value="susertype"/></dd>
		</dl>
		<dl>
	      <dt>证件号码：</dt>
		  <dd><s:property value="pid"/></dd>
		</dl-->
		<dl>
	      <dt>姓名：</dt>
		  <dd><input type="text" name="name" id="name" value="<s:property value="name"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>性别：</dt>
		  <dd><select id="sex" name="sex" style="width:180px">
				        <option value="1"  <s:if test="sex==1">selected</s:if> >男</option>
				        <option value="2"  <s:if test="sex==2">selected</s:if> >女</option>
				     </select><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>登录密码：</dt>
		  <dd><input type="password" name="passwd" id="passwd" value="<s:property value="passwd"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>密码确认：</dt>
		  <dd><input type="password" name="identify1" id="identify1" value="<s:property value="passwd"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>联系电话1：</dt>
		  <dd><input type="text" name="tel1" id="tel1" value="<s:property value="tel1"/>"  maxlength="25" size="20" style="width:176px;" /><span class="red_low">*</span></dd>
		</dl>
		<dl>
	      <dt>联系电话2：</dt>
		  <dd><input type="text" name="tel2" id="tel2" value="<s:property value="tel2"/>"  maxlength="25" size="20" style="width:176px;" /></dd>
		</dl>
		<dl>
	      <dt>Email地址：</dt>
		  <dd><input type="text" name="email" id="email" value="<s:property value="email"/>"  maxlength="25" size="20" style="width:176px;" /></dd>
		</dl>		
			<br />
			<dl>
				<dt></dt>
				<dd><font id="messcolor" color="red"><span id="mes"></span></font></dd>
			</dl>
				<dl>
					<dt></dt>
					<dd>
					    <a href='javascript:f_usersave()'><span class="btn_green_s"><s:text name="save"/><span class="btn_green_s_right"></span></span></a>
					    <a href="javascript:history.go(-1);"><span class="btn_green_s"><s:text name="fh"/><span class="btn_green_s_right"></span></span></a>
					</dd>
				</dl>
				<br/>
		</div>
	</div>
	<div class="down"><div></div></div>
</div>
</body>
</html>
