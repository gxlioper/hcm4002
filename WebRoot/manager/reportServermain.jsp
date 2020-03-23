<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title>报表查看</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link href="<%=request.getContextPath()%>/themes/default/style.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/flexigrid.pack.js"></script>
<script type="text/javascript" src="scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var urls;
var names;
 function onclickok(urls,names){
	 window.parent.addPanel_other(names,urls,"","1");
 }
</script>

</head>
<body>
<!-- Header -->
<div id="header"></div>
<!-- End -->

<div id="content">
	<div class="content-panel">
		
		<div id="mainbox">
				<div class="homepage">			
					
					<ul class="custom-item">
					
					 <s:iterator value="reportls" status="index">
					<li><div class="item-a"><span></span></div><dl url="javascript:window.parent.addPanel_other('<s:property value="name"/>','<s:property value="other_url"/>','','1');"><dt><img class="<s:property value="icon_url"/>" src="<%=request.getContextPath()%>/themes/default/images/blank.gif"></dt><dd><h2><s:property value="name"/></h2><div class="desc"><s:property value="name"/></div></dd></dl></li>
					</s:iterator>					
					</ul>					
			</div>
		</div>
	</div>
</div>
<!-- Footer -->
	<div id="footer"></div>
<!-- End -->

</body>
</html>