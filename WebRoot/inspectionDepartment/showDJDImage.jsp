<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>显示图片</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/jquery.rotate.1-1.js"></script>
<style type="text/css">
html{background-color:#E9E9E9; font-size:14px; color:#000; font-family:'微软雅黑'}
a,a:hover{ text-decoration:none;}
pre{font-family:'微软雅黑'}
.box{padding:20px; background-color:#fff; margin:35px 20px; border-radius:5px;}
.box a{padding-right:15px;}
.imgs img{width:100%;height:100%;padding:0 0px 0px;}

.fangda{margin-left:10px;margin-top:5px;display:inline-block;width:24px;height:24px;background:url('images/photo_button1.png') no-repeat 0 0;background-position:-257px 2px;}
.suoxiao{margin-left:10px;display:inline-block;width:24px;height:24px;background:url('images/photo_button1.png') no-repeat 0 0;background-position:-259px -52px;}
.zxiang{margin-left:10px;display:inline-block;width:20px;height:24px;background:url('images/photo_button1.png') no-repeat 0 0;background-position:-346px -38px;}
.close{margin-left:10px;display:inline-block;width:20px;height:22px;background:url('images/photo_button1.png') no-repeat 0 0;background-position:-357px -18px;}
.zuo{display:inline-block;width:15px;height:24px;background:url('images/photo_button2.png') no-repeat 0 0;background-position:-64px 0px;}
.you{display:inline-block;width:15px;height:24px;background:url('images/photo_button2.png') no-repeat 0 0;background-position:-78px 0px;}
.daohang{margin-left: 20px;background: #565656;width: 150px;position: fixed;margin-top: -35px;height:35px;}
.xvhao{padding: 3px 8px;background: #ccc;border: 1px solid #999;margin-right:5px;}
#daohang1{margin-left: 180px;position: fixed;margin-top: -27px;height:35px;}
.xuanzhong{color:#f00;}
</style>
<script type="text/javascript">
$(document).ready(function () {
	var DJD_path = '<s:property value="DJD_path"/>';
	isPhotoUploaded(DJD_path);
});
function isPhotoUploaded(text) {
	if (text != "" && text != null) {
		document.getElementById("Imgbox").src="getDJDPhoto.action?others="+text;
	}else{
		$.messager.alert("操作提示", text.split("&")[1], "error");	
		document.getElementById("Imgbox").src="../../themes/default/images/user.png";
	}
}


//放大缩小图片
function imgToSize(size) {
	var img = $("#Imgbox");
	var oWidth=img.width(); //取得图片的实际宽度
	var oHeight=img.height(); //取得图片的实际高度
	img.width(oWidth + size);
	img.height(oHeight + size/oWidth*oHeight);
}

function my_rotate(role){
	var img = $("#Imgbox");
   	var oWidth=img.width(); //取得图片的实际宽度
   	var oHeight=img.height(); //取得图片的实际高度

	img.rotateRight(role);
	var img1 = $("#Imgbox");
	img1.width(oHeight);
   	img1.height(oWidth);
}
function close_page(){
	window.close();
}
</script>
</head>
<body>
<div id="daohang" class="daohang">
	<!--<a href="javascript:void(0)" class="zuo" title="上一张" onclick="change_picture(-1)"></a>-->
	<a href="javascript:void(0)" class="fangda" title="放大" onclick="imgToSize(100);"></a>
	<a href="javascript:void(0)" class="suoxiao" title="缩小" onclick="imgToSize(-100);"></a>
	<a href="javascript:void(0)" class="zxiang" title="顺时针转向" onclick="my_rotate(-90)"></a>
	<a href="javascript:void(0)" class="close" title="关闭" onclick="close_page()"></a>
</div>
<div id="daohang1">
</div>
<center>
	<div class="box">
	<div id="imgs" class="imgs" style="width:700px;height:900px ;"><img id="Imgbox" style="height:900px;width:700px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /></div>
</div>
</center>
</body>
</html>