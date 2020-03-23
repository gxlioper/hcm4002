<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统参数设置--圈存查询一体机安装向导</title>
<link href="img/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">

	function formSubmit() {
		if (document.getElementById('dbPassword').value == '') {
			if (!confirm("您没有填写数据库密码，您确定数据库密码为空吗？")) {
				return false;
			}
		}
		document.getElementById('beforeSubmit').style.display = "none";
		document.getElementById('afterSubmit').style.display = "";
	}
</script>
</head>

<body >
<div class="regist-header box">
  <div class="rgheader box">
         <div class="brand fl">
            <h1 style="margin:0px;"></h1>
         </div>
     </div>
</div>
<form action="install_setup.svl" method="post"
	onsubmit="return formSubmit();">
	
	<div class="main">
  <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="rg-tbg">
    <tr>
      <td height="76" align="center"><h2>2、数据库连接配置（环境要求：jdk1.5或以上、tomcat5.5或以上、oralce或以上）</h2>
	  <span style="color:#016dd0;">请设置系统相关参数</span></td>
    </tr>
    <tr>
      <td>
	  <table width="600" border="0" align="center" cellpadding="0" cellspacing="0"
	>
	
	<tr>
		<td align="center" valign="top">
		  <table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="border:1px solid #b5b5b5;">
			<tr>
				<td width="30%" height="50" align="right">圈存数据库URL：</td>
				<td width="22%" align="left"><textarea  name="dbHost"
					 id="dbHost" cols ="27" rows = "3" ><%=request.getAttribute("dbHost")%></textarea></td>
				<td align="left">只修改ip、端口、服务名称</td>
			</tr>
			
			<tr>
				<td height="50" align="right">数据库用户：</td>
				<td align="left"><input name="dbUser" type="text" class="input"
					id="dbUser" value="<%=request.getAttribute("dbUser")%>" /></td>
				<td align="left">&nbsp;</td>
			</tr>
			<tr>
				<td height="50" align="right">数据库密码：</td>
				<td align="left"><input name="dbPassword" type="text"
					class="input" id="dbPassword" value="<%=request.getAttribute("dbPassword")%>"/></td>
				<td align="left">安装数据库时输入的密码</td>
			</tr>
			<tr>
				<td height="50" align="right">数据库最大连接数：</td>
				<td align="left"><input name="maxPoolSize" type="text"
					class="input" id="maxPoolSize" maxlength="2" value="<%=request.getAttribute("maxPoolSize")%>"/></td>
				<td align="left">设置5-15之间的数字</td>
			</tr>
			<tr>
				<td height="50" align="right">数据库最小连接数：</td>
				<td align="left"><input type="text" name="minPoolSize" maxlength="2"
					class="input"  value="<%=request.getAttribute("minPoolSize")%>" id="minPoolSize"/></td>
				<td align="left">必须小于数据库连接数</td>
			</tr>
			<tr>
				<td height="50" align="right">部署路径：</td>
				<td align="left"><input name="cxtPath" readonly type="text"
					class="input" value="<%=request.getContextPath()%>" /></td>
				<td align="left">系统已经检测出您的部署路径</td>
			</tr>
			<tr>
				<td height="50" align="right">端口号：</td>
				<td align="left"><input name="port" type="text" readonly class="input"
					value="<%=request.getServerPort()%>" /></td>
				<td align="left">系统已经检测出您的端口号</td>
			</tr>
		</table>	  </td>
	</tr>
	<tr>
		<td height="50" align="center"><span
			id="beforeSubmit">
		  <input type="submit" class="regist-submit"
			value=" 提 交 " />
</span> <span id="afterSubmit"
			style="display: none; color: red;">安装大概需要10秒的时间，请您耐心等待...</span></td>
	</tr>
</table>
	  </td>
    </tr>
  </table>
</div>

</form>
</body>
</html>
