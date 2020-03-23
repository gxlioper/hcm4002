<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main {	width: auto;	height: auto;}
#left { float: left;   width: 420px;	height: auto;}
#right {margin-right: 10px;height: auto;}
</style>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/sxtCutPic.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jpgcam/webcam1.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var  path='';
function doload_photo_load() {
	alert(1);
	doload_photo();
	document.getElementById("upload_poto_div").style.visibility="visible";//显示
	yipaizhao = false;
	$("#img_upload img").attr("src", '../../themes/default/images/user.png');
}

function Closeupload_poto_div() {
	 Webcam.reset();
	 document.getElementById("upload_poto_div").style.visibility="hidden";//隐藏
}
//拍照读取照片
function doload_photo() {
	Webcam.set({
		width: 200,
		height: 240,
		dest_width:320,
		flip_horiz: false,
		dest_height:400,
		image_format: 'jpeg',
		jpeg_quality: 90
	});
	Webcam.attach( '#my_camera' );
}
var imagedateurl;
function do_photo(){
  Webcam.snap( function(data_uri) {
	  // display results in page
	  imagedateurl=data_uri;
	  document.getElementById('img_upload').innerHTML = '<img src="'+data_uri+'"/>';
  });
}

//调用摄像头函数
var yipaizhao = false;
function do_upload() {
	var image_data = imagedateurl;   
	var raw_image_data = image_data.replace(/^data\:image\/\w+\;base64\,/, '');
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
    $.ajax({
		url : 'saveDJDimage.action',
		data : {
			exam_num : $("#exam_num").val(),
			others:'ok',
			sjson:raw_image_data
		    },
				type : "post",// 数据发送方式
				success : function(text) {
					$(".loading_div").remove();
					isPhotoUploaded(text);
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
   }

function isPhotoUploaded(text) {
	yipaizhao = true;
	if (text.split("&")[0] == 'ok') {
		Closeupload_poto_div();
		path = text.split("&")[1];
		document.getElementById("exampic").src="getDJDPhoto.action?others="+text.split("&")[1];
	}else{
		$.messager.alert("操作提示", text.split("&")[1], "error");	
		document.getElementById("exampic").src="../../themes/default/images/background.jpg";
	}
}



function upload_excel_btn() {
	//$("#upload_photofile_div").css("display", "block");
	document.getElementById("upload_photofile_div").style.visibility="visible";//隐藏
}

function closeuploadshow()
{
	//$("#upload_photofile_div").css("display", "none");
	document.getElementById("upload_photofile_div").style.visibility="hidden";//隐藏
}

//文件上传按钮
function wenjianshangchuan() {
	if ($("#customImageInfoImport").val() == '') {
		$.messager.alert("操作提示", "请选择上传的图片", "error");	
		return;
	}
	var srca = $("#customImageInfoImport").val();
	var ext = [ '.gif', '.jpg', '.jpeg', '.png' ];
	var s = srca.toLowerCase();
	var r = false;
	for (i = 0; i < ext.length; i++) {
		if (s.indexOf(ext[i]) > 0) {
			r = true;
			break;
		}
	}
	// return r;
	if (r) {
		var img = null;
		img = document.createElement("img");
		if (!+[ 1, ]) {// ie//alert("这是ie浏览器")
			document.body.insertAdjacentElement("beforeEnd", img); // firefox不行
			img.style.visibility = "hidden";
			img.src = srca;
			var imgwidth = img.offsetWidth;
			var imgheight = img.offsetHeight;
			if (imgwidth != 200 || imgheight != 240) {
				$.messager.alert("操作提示", "图的尺寸应该是" + 200 + "x" + 240, "error");	
				this.value = "";
				return false;
			}
			// return true;
			ajaxFileUpload();
		} else {// 非ie alert("这不是ie浏览器");
			ajaxFileUpload();
		}

	} else {
		$.messager.alert("操作提示", "请上传正确的图片文件，png、jpg、gif、jpeg。文件尺寸为200*240像素！", "error");	
	}
}

// 文件上传
function ajaxFileUpload(a, b) {
	$.ajaxFileUpload({
		url : 'djtcutsavePicture.action',
	    exam_id : $("#exam_id").val(),
	    others:'fileok',
		fileElementId : 'customImageInfoImport',
		dataType : 'json',
		success : function(data) {
			if (data.state == 'Y') {
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+data.msg;
			}else{
				$.messager.alert("操作提示", data.msg, "error");	
				document.getElementById("exampic").src="../../themes/default/images/user.png";
			}
			// select_person_list();
		}
	})
}
function saveDJDimage(){
 if(path==null || path ==""){
   $.messager.alert("操作提示", "请录入照片！！", "error");		
	 }else{
	 	$.ajax({
			url : 'addDJDimage.action',
			data : {
				exam_num : $("#exam_num").val(),
				userid:$("#userid").val(),
				DJD_path:path
			    },
					type : "post",// 数据发送方式
					success : function(text) {
						if(text > 0){
							$.messager.alert("操作提示", "保存成功", "info",close_page);
						}else{
							$.messager.alert("操作提示", "体检号信息错误！！", "error",close_page);
						}
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
	 }
	
}
function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.shuxing();
}
</script>
<body style="height:100%;">
<!--拍照-->
<div id="upload_poto_div" style="z-index: 89999;visibility:hidden;height: 500px;width: 600px;">
  <div style="float:right; top:2; margin-right:5px;"> <a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="Closeupload_poto_div()" title="关闭"> 关  闭 </a></div>
  <div id="title"><span>在线拍照</span></div>
  <div id="my_camera" style="width:200px;height:540px;float:left;"></div>
  <div id="img_upload" style="width:40%;float:left;margin-left:30px;"><img id="temppicshow" src="<%=request.getContextPath()%>/themes/default/images/user.png" width="320px" height="400px" /></div>
  <div style=" width:240px;  margin-top:10px; overflow:hidden; text-align:left;">
      <a  href="javascript:void(0)" onClick="Webcam.unfreeze();"><img src="<%=request.getContextPath()%>/images/sxt.png" /><br>重拍</a>
      <a  href="javascript:void(0)" onClick="do_photo();"><img src="<%=request.getContextPath()%>/images/paizhao.png" /><br>拍照</a>
      <a  href="javascript:void(0)" onClick="do_upload()"><img src="<%=request.getContextPath()%>/images/shangchuan.png" /><br>确定</a>
      </div>
</div>
<input id="myphotodata" type="hidden" name="myphotodata" value=""/>
<!-- 拍照结束 -->

<!--数据上传-->
<div id="upload_photofile_div" style="left:550;top:100;z-index: 89999;visibility:hidden ">
  <div style="float:right; top:2; margin-right:5px;"><a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="closeuploadshow()" title="关闭"> 关  闭 </a> </div>
  <div id="title"><span>上传文件</span></div>
  <input type="file" id="customImageInfoImport" name="customImageInfoImport" />
  <input type="submit" onclick="wenjianshangchuan()" value="上传">
</div>
<!--数据上传结束-->
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
	
	<center>
	<fieldset style="margin: 5px ,auto; padding-right: 10px; height: 700px; width: 700px;">
	<legend>
		<strong>导检单存档</strong>
	</legend>
	<div style="width:700px;height:700px ;"><img id="exampic" style="height:690px;width:700px;" src="<%=request.getContextPath()%>/themes/default/images/background.jpg" /></div>
	<div>	
		<dl>
		    <a href="javascript:void(0)" onClick="doload_photo_load()"><img style="height:25px;width:25px;" title="摄像头拍照" src="<%=request.getContextPath()%>/themes/default/images/sxt.png" /></a>
		    &nbsp; &nbsp; &nbsp;
		    <!--<a href="javascript:void(0)" onClick="upload_excel_btn()"><img style="height:25px;width:25px;" title="照片文件上传" src="<%=request.getContextPath()%>/themes/default/images/btndi.png" /></a>
		    &nbsp; &nbsp; &nbsp;-->
		    <a href="javascript:void(0)" onClick="saveDJDimage()"><img style="height:25px;width:25px;" title="保存" src="<%=request.getContextPath()%>/themes/default/images/save.png" /></a>
		     &nbsp; &nbsp; &nbsp;
		    <a href="javascript:void(0)" onClick="close_page();"><img style="height:25px;width:25px;" title="关闭" src="<%=request.getContextPath()%>/themes/default/images/close.jpg" /></a>
		</dl>     
 	</div>	
 </fieldset>
 </center>
</div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 1000,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 1000,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-jiuizhengka" class="easyui-dialog"  data-options="width:400,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-jiuizhengka_add" class="easyui-dialog"  data-options="width:300,height:200,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-show_sq" class="easyui-dialog"  data-options="width: 600,height: 420,closed: true,cache: false,modal: true,top:50"></div>

</body>
