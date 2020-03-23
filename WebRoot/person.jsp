<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
<title>个人信息</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="themes/default/style.css" />
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript">

function f_updateUser(){
	var id="<s:property value="#session.userinfo.id"/>";
	$("#dlg-edit").dialog({
		title:'修改个人信息',
		href:'updatePerson.action?id='+id
	});
	$("#dlg-edit").dialog('open');
}

function f_updatepasswd(){
	$("#dlg-edit").dialog({
		title:'修改密码',
		href:'updatePasswd.action'
	});
	$("#dlg-edit").dialog('open');
}
</script>

</head>
<body>

<div id="cont-page" class="include-page">
<!--  	<div class="location">
		<div class="repeat textbox-div"><img src="themes/default/images/blank.gif" class="icon-24 ico-6-24" alt="" /><a href="card.html">首页</a> <font face="simsun">&gt;</font> 个人信息</div>
		<s class="sprite"></s><i class="sprite"></i>
	</div>-->
	<div class="formdiv">
	
		<div class="mainlrofile">
			
			<h6>基本信息</h6>
			<dl>
				<div class="side-profile" align="right" >
				<ul>
					<%-- <li><a class="button btn-save" style='margin-right:100px;' type="button" title="确定" onclick="javascript:void(0);"><span>
					<img src="themes/default/images/blank.gif" class="icon ico-edit" alt="修改个人信息" />修改个人信息</span></a> &nbsp; 
					<a class="button btn-save" style='margin-right:100px;' type="button" title="确定" onclick="javascript:void(0);"><span>
					<img src="themes/default/images/blank.gif" class="icon ico-edit" alt="修改密码" />修改密码</span></a></li> --%>
				</ul>
			</div>
			</dl>
			
		
			<dl>
				<dt>登    录    名</dt>
				<dd>：&nbsp;<s:property value="#session.username.username"/></dd>
			</dl>
			<dl>
				<dt>用户类型</dt>
				<dd>：&nbsp;
				<s:if test="#session.username.usertype==1">
					超级管理员
				</s:if>
				<s:else> 
					普通人员员
				</s:else> 
				
				</dd>
			</dl>
			
			<dl>
				<dt>姓            名  </dt>
				<dd>：&nbsp;<s:property value="#session.username.name"/></dd>
			</dl>
			<dl>
				<dt>&nbsp;</dt>
				<dd>&nbsp;				
			</dl>
			<br/>
			<h6>联系信息</h6>
			<dl>
				<dt>联系电话</dt>
				<dd>：&nbsp;<s:property value="#session.username.tel"/></dd>
			</dl>
			<dl>
				<dt>Email地址</dt>
				<dd>：&nbsp;<s:property value="#session.userinfo.email"/></dd>
			</dl>
			<dl>
				<dt>&nbsp;</dt>
				<dd>&nbsp;</dd>
			</dl>
			
		</div>
		
		<div class="clearfix"></div>
	</div>
	
	<div class="arc-bottom"><s class="sprite"></s><p></p><i class="sprite"></i></div>
</div>
				
			</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 500,height:300,closed: true,cache: false,modal: true,top:5"></div>
</body>
</html>