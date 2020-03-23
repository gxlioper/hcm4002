<%@ page contentType="text/html; charset=utf-8" language="java"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统参数设置-网站查询安装向导</title>
<link href="img/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
	function formSubmit() {
		document.getElementById('beforeSubmit').style.display = "none";
		document.getElementById('afterSubmit').style.display = "";
	}
</script>
</head>

<body>
<div class="regist-header box">
  <div class="rgheader box">
         <div class="brand fl">
            <h1 style="margin:0px;"></h1>
         </div>
     </div>
</div>
<form action="install_setup1.svl" method="post"
	onsubmit="return formSubmit();">
	
	<div class="main">
  <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="rg-tbg">
    <tr>
      <td height="76" align="center"><h2>3、通讯参数配置</h2>
	  <span style="color:#016dd0;">请设置系统相关参数</span></td>
    </tr>
    <tr>
      <td>
	  <table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
	
	<tr>
		<td align="center" valign="top">
		  <table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="border:1px solid #b5b5b5;">
			<tr>
				<td width="30%" height="50" align="right">钱包服务IP地址：</td>
				<td width="22%" align="left"><input  name="CENTERHOST"
					class="input" id="CENTERHOST" value="<%=request.getAttribute("CENTERHOST")%>"/></td>
				<td align="left" style="color:red">（*）</td>
			</tr>
			<tr>
				<td width="30%" height="50" align="right">钱包服务端口：</td>
				<td width="22%" align="left"><input  name="CENTERPORT"
					class="input" id="CENTERPORT" value="<%=request.getAttribute("CENTERPORT")%>"/></td>
				<td align="left" style="color:red">（*）</td>
			</tr>
			
			<tr>
				<td height="50" align="right">综合前置机IP：</td>
				<td align="left"><input  name="FRONTHOST"
					class="input" id="FRONTHOST" value="<%=request.getAttribute("FRONTHOST")%>"/></td>
				<td align="left" style="color:red">（*）</td>
			</tr>
			<tr>
				<td height="50" align="right">综合前置机端口：</td>
				<td align="left"><input  name="FRONTPORT"
					class="input" id="FRONTPORT" value="<%=request.getAttribute("FRONTPORT")%>"/></td>
				<td align="left" style="color:red">（*）</td>
			</tr>
			<tr>
				<td height="50" align="right">TOMCAT服务器IP：</td>
				<td align="left"><input  name="LOCALIP"
					class="input" id="LOCALIP" value="<%=request.getAttribute("LOCALIP")%>"/></td>
				<td align="left" style="color:red">（*）</td>
			</tr>
		</table>	  </td>
	</tr>
	<tr>
		<td height="50" align="center"><span
			id="beforeSubmit">
		  <input type="submit" class="regist-submit"
			value=" 提 交 " />
</span> <span id="afterSubmit"
			style="display: none; color: red;">安装大概需要几秒的时间，请您耐心等待...</span></td>
	</tr>
</table>
	  </td>
    </tr>
  </table>
</div>

</form>
</body>
</html>
