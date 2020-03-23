<html>
   <head>
     <title>Please wait</title>
     <meta http-equiv="refresh" content="1;url=<@ww.url includeParams="all" />"/>
     <style>
	     	BODY
				{
					font-size: 9pt;
					margin: 0px;
					font-family: 宋体;
					bgcolor:#FFFFFF;
				}
     </style>
   </head>
   <body>
     <div><font color="green">正在处理您的请求，请耐心等待......</font></div>
     <#if percent!="0%">
				<div><font color="blue">目前处理进度：<@ww.property value="percent"/></font></div>	   
		 </#if>
   </body>
</html>
