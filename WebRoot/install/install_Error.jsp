<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站查询一体机安装向导</title>
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
      <td height="76" align="center"><h2>错误提示：<%=request.getAttribute("errortype")%></h2>
	  <span style="color:#016dd0;">请你<a href="javascript:history.go(-1)">点击返回</a>，重新设置！</span></td>
    </tr>
    <tr>
      <td>
	    <table width="600" border="0" align="center" cellpadding="0" cellspacing="0"
	style="border: #106DBA 1px solid; margin-top: 8%;">
          
          <tr>
            <td  align="left"><%=request.getAttribute("errorstr")%></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>


</body>
</html>