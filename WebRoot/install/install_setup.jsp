<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站查询安装向导</title>
<link href="img/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>


<div class="regist-header box">
  <div class="rgheader box">
         <div class="brand fl">
            <h1 style="margin:0px;"><a href="${base}/"> ${site.name}</a></h1>
         </div>
     </div>
</div>
<div class="main">
  <table width="980" border="0" align="center" cellpadding="0" cellspacing="0" class="rg-tbg">
    <tr>
      <td height="76" align="center"><h2>4、系统安装完成</h2>
	  <span style="color:#016dd0;">安装系统安装完成，请重启TOMCAT服务。</span></td>
    </tr>
    <tr>
      <td>
	    <table width="600" border="0" align="center" cellpadding="0" cellspacing="0"
	style="border: #106DBA 1px solid; margin-top: 8%;">
          
          <tr>
            <td height="280" valign="top" align="left"
			style="padding: 10px; line-height: 1.7em;">恭喜您，系统已经安装成功！<br />
              系统自动重启TOMCAT服务。<font color="red">请等待30秒以后再访问！</font><br />
              如果系统没有自动启动TOMCAT服务，请手工启动，安装才能生效！<br />
             <!--   登录地址:<%=request.getAttribute("refreshLoginUrl")%><br />-->
             登录地址:http://ip:port<br />
</td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>


</body>
</html>