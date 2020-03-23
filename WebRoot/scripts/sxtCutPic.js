/*--------------------------------------------------|
| 摄像头拍照 jpgcam 1.0.8                               |
|---------------------------------------------------|
| yangm                                             |
|--------------------------------------------------*/
function doload_photo_load() {
	doload_photo();
	document.getElementById("upload_poto_div").style.visibility="visible";//显示
	yipaizhao = false;
	$("#img_upload img").attr("src", '../../themes/default/images/user.png');
}

function Closeupload_poto_div() {
	// Webcam.off();
	 Webcam.reset();
	 document.getElementById("upload_poto_div").style.visibility="hidden";//隐藏
}
//拍照读取照片
function doload_photo() {
	Webcam.set({
		width: 320,
		height: 240,
//		dest_width:1440,
//		flip_horiz: true,
//		dest_height:1920,
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
	  document.getElementById('img_upload').innerHTML = '<img src="'+data_uri+'" width="200px" height="240px"/>';
  });
}

//调用摄像头函数
var yipaizhao = false;
function do_upload() {
	var url = 'djtcutsavePicture.action?exam_num='+$('exam_num').val()+'&exam_id='+$("#exam_id").val()+'&others=ok';
	var image_data = imagedateurl;    
	var raw_image_data = image_data.replace(/^data\:image\/\w+\;base64\,/, '');
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
    $.ajax({
		url : 'djtcutsavePicture.action',
		data : {
			exam_id : $("#exam_id").val(),
			exam_num:$('#exam_num').val(),
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
		$("#picture_Path").val(text.split("&")[1])
		//document.getElementById("temppicshow").src="getdjtexamPhoto.action?others="+text.split("&")[1];
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+text.split("&")[1];
	}else{
		$.messager.alert("操作提示", text.split("&")[1], "error");	
		$("#picture_Path").val('');
		document.getElementById("exampic").src="../../themes/default/images/user.png";
	}
}
//用户拍照
function do_upload_user() {
	var url = 'userCutSavePicture.action?others=ok';
	var image_data = imagedateurl;    
	var raw_image_data = image_data.replace(/^data\:image\/\w+\;base64\,/, '');
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
    $.ajax({
		url : 'userCutSavePicture.action',
		data : {
			//exam_id : $("#exam_id").val(),
			others:'ok',
			sjson:raw_image_data
		    },
				type : "post",// 数据发送方式
				success : function(text) {
					$(".loading_div").remove();
					isPhotoUploaded_user(text);
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
   }
//用户拍照显示
function isPhotoUploaded_user(text) {
	yipaizhao = true;
	if (text.split("&")[0] == 'ok') {
		Closeupload_poto_div();
		$("#user_pic_path").val(text.split("&")[1])
		//document.getElementById("temppicshow").src="getdjtexamPhoto.action?others="+text.split("&")[1];
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+text.split("&")[1];
	}else{
		$.messager.alert("操作提示", text.split("&")[1], "error");	
		$("#user_pic_path").val('');
		document.getElementById("exampic").src="../../themes/default/images/user.png";
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
			var imgwidth = img.offsetHeight;
			var imgheight = img.offsetWidth;
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
	    exam_num:$('#exam_num').val(),
	    others:'fileok',
		fileElementId : 'customImageInfoImport',
		dataType : 'json',
		success : function(data) {
			if (data.state == 'Y') {
				$("#picture_Path").val(data.msg)
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+data.msg;
			}else{
				$.messager.alert("操作提示", data.msg, "error");	
				$("#picture_Path").val('');
				document.getElementById("exampic").src="../../themes/default/images/user.png";
			}
			// select_person_list();
		}
	})
}

//用户文件上传按钮
function wenjianshangchuan_user() {
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
			var imgwidth = img.offsetHeight;
			var imgheight = img.offsetWidth;
			if (imgwidth != 200 || imgheight != 240) {
				$.messager.alert("操作提示", "图的尺寸应该是" + 200 + "x" + 240, "error");	
				this.value = "";
				return false;
			}
			// return true;
			ajaxFileUpload_user();
		} else {// 非ie alert("这不是ie浏览器");
			ajaxFileUpload_user();
		}

	} else {
		$.messager.alert("操作提示", "请上传正确的图片文件，png、jpg、gif、jpeg。文件尺寸为200*240像素！", "error");	
	}
}

// 用户文件上传
function ajaxFileUpload_user(a, b) {
	$.ajaxFileUpload({
		url : 'userCutSavePicture.action',
	    //exam_id : $("#exam_id").val(),
	    others:'fileok',
		fileElementId : 'customImageInfoImport',
		dataType : 'json',
		success : function(data) {
			if (data.state == 'Y') {
				$("#user_pic_path").val(data.msg)
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+data.msg;
			}else{
				$.messager.alert("操作提示", data.msg, "error");	
				$("#user_pic_path").val('');
				document.getElementById("exampic").src="../../themes/default/images/user.png";
			}
			// select_person_list();
		}
	})
}