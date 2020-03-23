<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@taglib prefix="s" uri="/struts-tags"%>
<title>信息查看</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.toggle.js"></script>
<style>
.bg{

filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
-moz-background-size:100% 100%;
	background-size:100% 100%;
}
</style>
<script type="text/javascript">
$(function(){
var editfbxxid = $("#fbxxid").val();
f_getFbxx(editfbxxid);
function f_getFbxx(param) { 
	    $.post("geteditfbxx.action?fbxxid="+param,"ok",  
	        function (data) {
	            f_showFbxx(data);  
	         },"json");  
	}  
var editlmid
function f_showFbxx(obj) { 
  		var editfbxx = obj;
  		var xgsjstr = editfbxx.xgsj;
  		xgsjstr = xgsjstr.substring(0,4)+"-"+xgsjstr.substring(4,6)+"-"+xgsjstr.substring(6,8)+" "
  					+xgsjstr.substring(8,10)+":"+xgsjstr.substring(10,12)+":"+xgsjstr.substring(12,14);
  		$("#bt").html(editfbxx.bt);
  		$("#xxnr").html(editfbxx.snr);
  		$("#xgsj").html(xgsjstr);
  		$("#fbr").html(editfbxx.fbz);
  		var filelistfh = editfbxx.xxfjlist;
  		var filelist = $("#filelist");
  		filelist.empty();//
  		var filestr;
  		for(var i=0;i<filelistfh.length;i++){
  		
	  		filestr ='<li>'+filelistfh[i].fjmc+'  <span class="downloadlink"><a href="downloadfile.action?fjid='+filelistfh[i].fjid
	  				+'"><img class="icon ico-export" src="themes/default/images/blank.gif"/>下载</a></span></li>';
	  		filelist.append(filestr);
	  		
  		}
	}  
})
</script>

</head>
<body style="height:100%">
<div id="header" class="homehead">
	<div class="box">
		<div class="logo" style="padding:18px 0 0 0;"><a title="查询系统"><img src="themes/default/images/logo.png" /></a></div>
		<h1 class="system-title" style="padding-top:22px;"><img src="themes/default/images/logo2.png" /></h1>
	</div>
</div>
<div id="dBody" class="bg">
		<div class="content-home">
<div id="cont-page" class="include-page" style="height:500px;">
		<div class="location">
		<div class="repeat textbox-div"><img src="themes/default/images/blank.gif" class="icon-24 ico-10-1" alt="" />首页 <font face="simsun">&gt;</font> 查看新闻</div>
		<s class="sprite"></s><i class="sprite"></i>
		</div>
	<input type="hidden" id="fbxxid" name="fbxxid" value="<s:property value='fbxxid'/>" class="textinput" /> 
  	<div id="viewDescription">
  	<div class="title">
	<!--h1>下学期食堂补助发布通知</h1-->
	<h1><span id="bt"></span></h1>
		<span style="float:right;"><a href="javascript:window.location.href='index.html';">【返回】</a>  <a href="javascript:self.close();">【关闭窗口】</a></span>
		<span class="gray_low">发布时间:<strong id="xgsj"></strong>     发布人:<strong id="fbr"></strong></span>
	</div>
  <div id="xxnr"></div>
  <div class="annex"><strong>附件：</strong>
		<ul id="filelist">
			<!--li>四级英语复习资料.doc  <span class="downloadlink"><a href=""><img src="themes/default/images/page_white_word.png"/>下载</a></span></li>
			<li>四级英语复习资料.pdf  <span class="downloadlink"><a href=""><img src="themes/default/images/pdf.png"/> 下载</a></span></li>
			<li>四级英语复习资料.xls  <span class="downloadlink"><a href=""><img src="themes/default/images/page_excel.png"/>下载</a></span></li-->
		</ul>
	</div>
	<div id="footer">
	<div class="box">
		<div class="copyright">版权所有 &copy; 2012 哈尔滨新中新集团电子股份有限公司</div>
	</div>
</div>
  </div>
</div>

</body>
</html>