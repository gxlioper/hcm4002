<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"   isErrorPage="true"%>
<%@taglib uri="/struts-tags"  prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误提示</title>
</head>
<body>
	<div align="center" style="margin-top: 17%">
		<table>
			<tr>
				<td><img src="<%=request.getContextPath()%>images/img/xitongcuowu.jpg" alt="" /></td>
				<td><p style="color: ff0000;"><s:property value="message"/></p></td>
			</tr>
		</table>
	</div>
</body>
</html>
