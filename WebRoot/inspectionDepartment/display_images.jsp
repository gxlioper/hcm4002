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
.box{padding:20px; background-color:#fff; margin:35px 30px; border-radius:5px;}
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
#daohang1{position: fixed;margin-top: 15px;height:35px;}
.xuanzhong{color:#f00;}

#daohang_dep{margin-left: 180px;position: fixed;margin-top: -27px;height:35px;}
.xuanzhong_dep{color:#f00;}
.xvhao_dep{padding: 3px 8px;background: #ccc;border: 1px solid #999;margin-right:5px;}
</style>
<script type="text/javascript">
$(document).ready(function () {
	var id = '<s:property value="pacs_req_code"/>';
	var exam_num = '<s:property value="exam_num"/>';
	getViewExamImagePath(id,exam_num);
});
var imagePaths;
function getViewExamImagePath(id,exam_num){
	$.ajax({
		url:'getViewExamImagePath.action?pacs_req_code='+id+'&exam_num='+exam_num,
		type:'post',
		success:function(data){
			if(data == '[]'){
				$("#imgs").html("没有可查看图片");
				return;
			}
			var obj=eval("("+data+")");
			imagePaths = obj;
			var dep_id = new Array();
			var str = '';
			var id = 0;
			for(i=0;i<obj.length;i++){
				if(dep_id.indexOf(obj[i].dep_id) == -1){
					if(obj[i].id > 0){
						id = obj[i].id;
					}
					dep_id.push(obj[i].dep_id);
					str += '<a href="javascript:void(0)" id="dep_'+obj[i].dep_id+'" data="'+obj[i].dep_id+'" onclick="change_dep('+obj[i].dep_id+')" class="xvhao_dep" title="'+obj[i].dep_name+'" >'+obj[i].dep_name+'</a>';
				}
			}
			$("#daohang_dep").html(str);
			if(id == 0){
				change_dep($(".xvhao_dep").eq(0).attr('data'));
			}else{
				change_dep(id);
			}
		}
	});
}
//切换科室
function change_dep(dep_id){
	if($("#dep_"+dep_id).html() == $(".xuanzhong_dep").eq(0).html()){
		//change_picture(0,dep_id);
		return;
	}
	$(".xuanzhong_dep").eq(0).removeClass("xuanzhong_dep");
	$("#dep_"+dep_id).addClass("xuanzhong_dep");
	var str = '';
	var j = 0;
	var item_i = 0;
	for(var i=0;i<imagePaths.length;i++){
		if(dep_id == imagePaths[i].dep_id){
			if(imagePaths[i].pacs_req_code == imagePaths[i].item_code && item_i == 0){
				item_i = j;
			}
			str += '<li style="list-style:none outside none"><a href="javascript:void(0)" data="'+imagePaths[i].report_picture_path+'" onclick="change_picture('+j+','+dep_id+')" class="xvhao" title="'+(j+1)+'" >'+(j+1)+'</a></li>';
			j++;
		}
	}
	$("#itemxvhao").html(str);
	change_picture(item_i,dep_id);
}
//切换图片
function change_picture(i,dep_id){
	if((i+1) == $(".xuanzhong").eq(0).html()){
		return;
	}
	$(".xuanzhong").eq(0).removeClass("xuanzhong");
	$(".xvhao").eq(i).addClass("xuanzhong");
	var str = '<img id="Imgbox" src="../../picture'+$(".xvhao").eq(i).attr('data')+'?tempid='+Math.random()+'"/>';
	$("#imgs").html(str);
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
<div></div>
<div id="daohang" class="daohang">
	<!--<a href="javascript:void(0)" class="zuo" title="上一张" onclick="change_picture(-1)"></a>-->
	<a href="javascript:void(0)" class="fangda" title="放大" onclick="imgToSize(100);"></a>
	<a href="javascript:void(0)" class="suoxiao" title="缩小" onclick="imgToSize(-100);"></a>
	<a href="javascript:void(0)" class="zxiang" title="顺时针转向" onclick="my_rotate(-90)"></a>
	<a href="javascript:void(0)" class="close" title="关闭" onclick="close_page()"></a>
</div>
<div id="daohang_dep">
</div>
<div id="daohang1">
	<ul id="itemxvhao" style="padding: 0px;margin: 0px;border: none;line-height: 32px;"></ul>
</div>
<div class="box">
    <div id="imgs" class="imgs">
        <img id="Imgbox" src=""/>
    </div>
</div>
</body>
</html>