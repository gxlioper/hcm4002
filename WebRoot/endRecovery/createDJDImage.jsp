<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>

	<head>
		<!--<meta http-equiv="Content-Type" content="text/html; charset=gb2312">-->
        <meta http-equiv="X-UA-Compatible" content="IE=10"/>
		<title>科密摄像头拍照</title>

<script language="javascript">
var CamID = -1;
var captureCount = 0;
var DeviceStatus = false;
var isOpenCamera = false;
var rotate = 0
//var t2 = window.setTimeout("show()", 3000); 
function show(op){
alert(op);
}

function ShowInfo(op) {
    var obj = document.getElementById("TextArea1");
    obj.value = obj.value  + " \n" + op;
}


function loadActiveX() {

    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)) {
        //IE浏览器加载控件
        document.getElementById("ActiveXDivOne").innerHTML = "<OBJECT id=\"axOcx\"  classid=\"clsid:D662BB12-95FC-4E4B-AD98-10AD44D6C1F0\" width=\"100%\" height=\"530px\" ></OBJECT>";      
    }
    else {
        //其他浏览器加载控件
        document.getElementById("ActiveXDivOne").innerHTML = "<OBJECT id=\"axOcx\" type=\"application/x-camera\"  clsid=\"{D662BB12-95FC-4E4B-AD98-10AD44D6C1F0}\"  width=\"100%\" height=\"530px\" ></OBJECT>";
    }
}


function InitLoad() {
    var devObj;
    devObj =document.getElementById("Device");
    var devCount = axOcx.GetMyDeviceCount(); //获取设备数目
    if (devCount == 0) ShowInfo("未发现相关设备");
    if (devCount == 1)
    {
          var objOption = document.createElement("option");
          objOption.text = "主摄像头";
          objOption.value = 0;
          devObj.options.add(objOption);
    }
    if (devCount == 2)
    {
        var objOption = document.createElement("option");
        objOption.text = "主摄像头";
        objOption.value = 0;
        devObj.options.add(objOption);

        objOption = document.createElement("option");
        objOption.text = "辅摄像头";
        objOption.value = 1;
        devObj.options.add(objOption);
    }

    if (devCount > 0) 
    {
        var resObj = document.getElementById("Resolution");
        CamID = devObj.selectedIndex;
        var ResolutionCount = axOcx.GetResolutionCount(CamID); //获取分辨率数目
        if (ResolutionCount > 0) 
        {
            for (var i = 0; i < ResolutionCount; i++) {
                var objOption = document.createElement("option");
                objOption.text = axOcx.GetResolution(i); //获取指定索引的分辨率
                objOption.value = i;
                resObj.options.add(objOption);
            }
        }
        
    }
    OpenDevice();
}


var meter1;
function countSecond() {
    alert("定时器");
    meter1 = setTimeout("countSecond()", 1000);
}


//开启摄像头
function OpenDevice() {
    var devObj = document.getElementById("Device");
    if (devObj.options.length > 0) {
        //var resObj = document.getElementById("Resolution");
        document.getElementById("Resolution").value = 3;
        //var ResolutionStr = resObj.value;
        var iRect = axOcx.OpenCamera(CamID, 3);
        if (iRect == 0)
        {
            isOpenCamera = true;
        }
        else 
        {
            axmw_cam_ocx1.CloseCamera();
            isOpenCamera = false;
        }
        axOcx.SetCutType(0);
        axOcx.SetFileType(1);
        axOcx.SetImageColorMode(0);
        axOcx.SetCamRotate(270);
       
    }

}

//关闭摄像头
function CloseDevice() {

    if (isOpenCamera) 
    {
        axOcx.CloseCamera()
        isOpenCamera = false;
        ShowInfo("关闭设备");
     }   
    window.close();
}



//抓图拍照
function Capture() {

    if (isOpenCamera == false) return;
    var exam_num = document.getElementById("exam_num").value.replace(/\s+/g,""); 
    if(exam_num==""){
    	 ShowInfo("无效体检编号");
    	return;
    }
    captureCount = captureCount + 1;
    var path = "D:\\djtpic\\" +exam_num;
    var suffix =".jpg";
    
    path = path + suffix;
    var base64Str=axOcx.CaptureImage(path);
    var imgobj1 = document.getElementById("img1");
    //imgobj1.src = path;
    imgobj1.src = "data:;base64," + base64Str; 
    var info = "拍照成功,图片路径:" + path;
    ShowInfo(info);
    HttpUploadFile();
    axOcx.DeleteFile(path);
}


//切换摄像头
function ChangeDevice() {

    var resObj = document.getElementById("Resolution");
    resObj.options.length = 0;  //清空

    var devObj;
    devObj = document.getElementById("Device");

    CamID = devObj.selectedIndex;
    var ResolutionCount = axOcx.GetResolutionCount(CamID); //获取分辨率数目
    if (ResolutionCount > 0) {
        for (var i = 0; i < ResolutionCount; i++) {
            var objOption = document.createElement("option");
            objOption.text = axOcx.GetResolution(i); //获取指定索引的分辨率
            objOption.value = i;
            resObj.options.add(objOption);
        }
        
    }

    var ResolutionStr = resObj.options[resObj.selectedIndex].text;
    var iRect = axOcx.OpenCamera(CamID, ResolutionStr);
    if (iRect == 0) {
        isOpenCamera = true;
        ShowInfo("成功切换摄像头");
    }
    else {
        axOcx.CloseCamera();
        isOpenCamera = false;
        ShowInfo("切换摄像头失败");
    }
}


//切换分辨率
function ChangeResolution() {
    if (isOpenCamera == true)
    {
        axOcx.CloseCamera();
        isOpenCamera = false;
        var resObj = document.getElementById("Resolution");
        var ResolutionStr = resObj.options[resObj.selectedIndex].text;
        ShowInfo(ResolutionStr);
        var iRect = axOcx.OpenCamera(CamID, ResolutionStr);
        if (iRect == 0)
        {
            isOpenCamera = true;
            ShowInfo("成功切换分辨率");
        }
        else
        {
            axOcx.CloseCamera();
            isOpenCamera = false;
            ShowInfo("切换分辨率失败");
        }
    }   
}

function change1(){
	document.getElementById("TextArea1").value="";
	 var imgobj1 = document.getElementById("img1");
	    imgobj1.src = ""; 
}
//Http上传测试
function HttpUploadFile() {
	var exam_num = document.getElementById("exam_num").value.replace(
				/\s+/g, "");
		if (exam_num == "") {
			ShowInfo("无效体检编号");
			return;
		}
		var path = "D:\\djtpic\\" + exam_num;
		var suffix = ".jpg";
		path = path + suffix;
		var userid = document.getElementById("userid").value;
		var basePath = GetUrlRelativePath();
		var urlpath = basePath + "/uploadDjd?exam_num=" + exam_num + "&userid="
				+ userid;
		var iRect = axOcx.UploadFile(urlpath, path);
		if (iRect == 0) {
			ShowInfo("上传成功");
			document.getElementById("exam_num").value="";
			document.getElementById("exam_num").focus();
		} else {
			ShowInfo("上传失败");
		}
	}

	function GetUrlRelativePath() {
		var url = document.location.toString();
		var arrUrl = url.split("//");
		var start = arrUrl[1].indexOf("/");
		var relUrl = arrUrl[1].substring(0, start);//stop省略，截取从start开始到结尾的所有字符
		arrUrl = arrUrl[0] + "//" + relUrl;
		return arrUrl;
	}

	//放大
	function ZoomIn() {
		if (isOpenCamera == false)
			return;
		axOcx.ZoomIn();
	}

	//缩小
	function ZoomOut() {
		if (isOpenCamera == false)
			return;
		axOcx.ZoomOut();
	}

	//适合大小
	function BestSize() {
		if (isOpenCamera == false)
			return;
		axOcx.BestSize();
	}

	//实际大小
	function TrueSize() {
		if (isOpenCamera == false)
			return;
		axOcx.TrueSize();
	}

	//对焦
	function ManualToFocus() {
		if (isOpenCamera == false)
			return;
		axOcx.ManualToFocus();
	}

	//设备属性
	function ShowImageSettingWindow() {
		if (isOpenCamera == false)
			return;
		axOcx.ShowImageSettingWindow();
	}

	function keyup_submit(e) {
		var evt = window.event || e;
		if (evt.keyCode == 13) {
			Capture();
			HttpUploadFile();
		}
	}
</script>

</head>
<body  onload="loadActiveX();InitLoad();">
<input type="hidden" id="picurl" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
        <div style="width:600px; height: 600px; border: 1px solid black; background:#359bcc;  float:left">
                <div  style=" width:100%; height:544px" id='ActiveXDivOne'>
		        </div>
                <div align="center" style=" width:100%; height:36px">   
                    <input type = "button" value = " 放大 " onclick = "ZoomIn();" style="height: 32px" />
                    <input type = "button" value = " 缩小 " onclick = "ZoomOut();" style="height: 32px" />
                    <input type = "button" value = " 适合大小 " onclick = "BestSize();" style="height: 32px" />
                    <input type = "button" value = " 1：1 " onclick = "TrueSize();" style="height: 32px" />
                    <input type = "button" value = " 对焦 " onclick = "ManualToFocus();" style="height: 32px" />
                    <input style="height: 32px" id="Button5" type="button" value="设备属性" onclick = "ShowImageSettingWindow();"/>
                </div>	

        </div>


        <div style="width:270px;height:600px;background:#359bcc; border: 1px solid black;float:left">

             <img id="img1" alt="" src="" style="MARGIN-TOP:0px; width: 200px; height: 270px" />

            <table style="width:100%;  height: 64px">
                <tr>               
                    <td > 
                        设 备 
                        <select id="Device" name="D1" onchange = "ChangeDevice()">                
                        </select>
                   
                        分辨率
                        <select style="width:80px"  id="Resolution" name="D2"onchange = "ChangeResolution()">    
                        </select>
                    </td>
                </tr>
            </table>


            <table style="width:100%;  height: 64px">
                <tr>
                    <td  >
                        <input style="width:80px;  height: 60px" id="Button1" type="button" value="打开" onclick = "OpenDevice();"/></td>
                    <td rowspan="2" >
                        <input style="width:80px;  height: 60px" id="Button3" type="button" value="拍照" onclick = "Capture();"/></td>
                    <td  >
                    <input style="width:80px;  height: 60px" id="Button2" type="button" value="关闭" onclick = "CloseDevice();"/></td>      
                </tr>

            </table>

            <table style="width:100%; height: 64px">
                <tr>
                    <td >
                       体检编号：<input type="text" id="exam_num" value="<s:property value="model.exam_num"/>"  onchange="change1()"   onkeydown="change1();">
                       
                    </td>
                 </tr>
            </table>

<!--             <table style="width: 100%; height: 32px"> -->
<!--                 <tr> -->
<!--                     <td> -->
<!--                           <input style="width: 100%; height: 30px" id="Button4" type="button" value="Http上传" onclick = "HttpUploadFile();"/> -->
<!--                     </td> -->
<!--                 </tr> -->

<!--             </table> -->

           
            <textarea style="width: 265px; height: 125px" id="TextArea1" rows="20" cols="20" ></textarea>
                            
        </div>
		
	</body>

</html>
