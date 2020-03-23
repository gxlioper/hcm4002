<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<script id="jqueryScript" type="text/javascript" src="<%=request.getContextPath()%>/sign/jquery/jquery-1.4.2.js"></script>
		<script id="jqueryScript" type="text/javascript" src="<%=request.getContextPath()%>/sign/jquery/jquery.json-2.3.js"></script>
		<script id="jqueryScript" type="text/javascript" src="<%=request.getContextPath()%>/sign/jquery/base64.js"></script>
		<script id="jqueryScript" type="text/javascript" src="<%=request.getContextPath()%>/sign/jquery/json2.js"></script>

		<style type='text/css'>
			div{border: 2px solid #C0C0C0;float:left;background-color:#dddddd;}
			div#ALL {float:left;width:1300px;border: 0;}
			div#div_ctrl {float:left;width:190px;height:300px;border:0px solid #B5B8C8}
			div#div_msg {float:left;width:320px;height:300px;border:0px solid #B5B8C8}
			div#div_sign_data {width:430px;height:300px;border:0px solid #B5B8C8}
			div#div_sign_value {width:300px;}
			div#div_sign_info {width:975;}
            table{border:0;}
			input[type="button"]{border: 1px solid #b7d2ff;background: #24748f;} 
			input[type="button"]{width:150px;height:30px;}
			input{font-size:17px;font-family: "Microsoft Yahei";}
			
			div#div_get_data input[type="text"]{width:270px;height:20px;}

			input[type="text"]{width:330px;height:30px;}
			select{width:100px;height:25px;}
			select{font-size:15px;font-family: "Microsoft Yahei";}			
			.display_none{	display:none;}
		</style>
		<script type="text/javascript"> 		
			function GetErrorMessage(ErrorCode)
			{
				var Message = "";
				switch (ErrorCode)
				{
					case 0:	Message = "成功";	break;
					case 1:	Message = "数据的json为空";	break;
					case 2:	Message = "输入的json解析失败";	break;
					case 3:	Message = "IDCard解析失败";	break;
					case 4:	Message = "IDCard不是object";	break;
					case 5:	Message = "证件类型不是字符串";	break;
					case 6:	Message = "证件号码不是字符串";	break;
					case 7:	Message = "指纹图片数据为空";	break;
					case 8:	Message = "手写轨迹图片为空";	break;
					case 9:	Message = "DeviceID为空";	break;
					case 10:	Message = "获取手写图片失败";	break;
					case 11:	Message = "获取指纹图片失败";	break;
					case 12:	Message = "加密后BIO_FEATURE数据为空";	break;
					case 13:	Message = "加密BIO_FEATURE数据失败";	break;
					case 14:	Message = "签名人不是字符串";	break;
					case 15:	Message = "init_XTX失败";	break;
					case 16:	Message = "获取的签名值为空";	break;
					case 17:	Message = "获取签名值失败";	break;
					case 18:	Message = "申请证书失败";	break;
					case 19:	Message = "时间戳为空";	break;
					case 20:	Message = "请求时间戳失败";	break;
					case 21:	Message = "打开安全签名板失败";	break;
					case 22:	Message = "事件证书为空";	break;
					case 23:	Message = "证书请求数据为空";	break;
					case 24:	Message = "Init AnySignClient失败";	break;
					case 25:	Message = "手写轨迹数据为空";	break;
					case 26:	Message = "签名包数据为空";	break;
					case 27:	Message = "对称解密失败";	break;
					case 28:	Message = "对称解密返回数据为空";	break;
					case 29:	Message = "解析BioFeature失败";	break;
					case 30:	Message = "输入的签名包为空";	break;
					case 31:	Message = "输入的原文为空";	break;
					case 32:	Message = "解析输入的签名包数据格式错误";	break;
					case 33:	Message = "签名值验证失败";	break;
					case 34:	Message = "获取证书内容数据失败";	break;
					case 35:	Message = "使用不支持的图片格式";	break;
					case 36:	Message = "设置笔迹宽度和颜色失败";	break;
					case 37:	Message = "设置signDevice失败";	break;
					case 38:	Message = "showdialog失败";	break;
					case 39:	Message = "获取手写指纹信息失败";	break;
					case 40:	Message = "设置adaptor对象失败";	break;
					case 41:	Message = "设置证件类型失败";	break;
					case 42:	Message = "设置证件号码失败";	break;
					case 43:	Message = "设置签名人姓名失败";	break;
					case 44:	Message = "设置原文数据失败";	break;
					case 45:	Message = "设置crypto对象失败";	break;
					case 46:	Message = "构造证书请求格式json失败";	break;
					case 47:	Message = "获取证书请求失败";	break;
					case 48:	Message = "签名包中EventCert为空";	break;
					case 49:	Message = "签名包中TSValue为空";	break;
					case 50:	Message = "输入的签名算法不是有效参数";	break;
					case 51:	Message = "获取签名包数据中指纹图片为空";	break;
					case 52:	Message = "获取签名包手写轨迹图片为空";	break;
					case 53:	Message = "计算Biofeature哈希失败";	break;
					case 54:	Message = "Biofeatur哈希值为空";	break;
					case 55:	Message = "设置bio_hash失败";	break;
					case 56:	Message = "获取证书中BIO_HASH失败";	break;
					case 57:	Message = "或者证书BIO_HASH内容为空";	break;
					case 58:	Message = "比较证书BIO_HASH失败";	break;
					case 59:	Message = "没有检测到签名设备";	break;
					case 60:	Message = "签名设备不是安全签名板,当前只支持安全签名板";	break;
					case 61:	Message = "用户取消签名";	break;
					case 62:	Message = "获取证书签名人失败";	break;
					case 63:	Message = "获取证书签名人为空";	break;
					case 64:	Message = "证件类型不正确";	break;
					case 65:	Message = "解析后的json不是json object";	break;
					case 66:	Message = "签名时间数据为空";	break;
					case 67:	Message = "创建签名句柄失败";	break;
					case 68:	Message = "输入的签名句柄为空";	break;
					case 69:	Message = "调用sign_begin接口失败";	break;
					case 70:	Message = "输入的文件名为空";	break;
					case 71:	Message = "base64解码失败";	break;
					case 72:	Message = "输入的文件太大，无法签名或者验证。";	break;
					case 73:	Message = "未输入签名人姓名";	break;
					case 74:	Message = "未输入签名人证件信息";	break;
					case 75:	Message = "输入的时间戳签名值为空";	break;
					case 76:	Message = "输入的时间戳原文为空";	break;
					case 77:	Message = "格式化时间戳时间失败";	break;
					case 78:	Message = "验证时间戳签名失败";	break;
					case 79:	Message = "读取文件内容失败";	break;
					case 80:	Message = "验证证书有效性失败（三级根配置不正确也会导致此错误）";	break;
					case 81:	Message = "获取时间戳原文为空";	break;
					case 82:	Message = "对图像进行缩放发生错误";	break;
					case 83:	Message = "对图像进行格式转换发生错误";	break;
					case 84:	Message = "未连接扩展屏";	break;
					case 85:	Message = "不支持的扩展屏型号";	break;
					case 86:	Message = "解析证书中的哈希值失败";	break;
					case 87:	Message = "比较证书中的哈希与手写笔迹哈希失败";	break;
					case 88:	Message = "设置签名对话框宽度不正确";	break;
					case 89:	Message = "加载UI的配置文件错误";	break;
					case 90:	Message = "获取照片失败";	break;
					case 91:	Message = "获取视频失败";	break;
					case 92:	Message = "打开摄像头失败";	break;
					case 93:	Message = "拍照失败";	break;
					case 94:	Message = "拍照路径确认失败";	break;
					case 95:	Message = "录音路径失败";	break;
					case 96:	Message = "录像存储路径获取失败";	break;
					case 97:	Message = "打开麦克风失败";	break;
					case 98:	Message = "未找到编码器xvid";	break;
					case 99:	Message = "签名板未打开";	break;
					case 100:	Message = "签名板已经打开";	break;
					case 101:	Message = "访问签名板失败";	break;
					case 102:	Message = "签名板服务程序未启动";	break;
					case 103:	Message = "签名板服务程序错误";	break;
					case 104:	Message = "签名板被移除";	break;
					case 105:	Message = "缺少证据信息";	break;
					default:
						Message = "未知错误" ;
						break;
				}
				Message += ",错误码：" + ErrorCode;
				ShowMsg(Message);
			}
		
			function build_plain_data()
			{
				var Name = document.getElementById('txt_signer').value;
				var cardtype = "";
				var cardNo = "";
				
				var idCard = 
				{
					Type: cardtype,
					Number: cardNo
				}
				
				var signinfo = 
				{
					Signer:Name,
					IDCard:idCard
				}
				var inputjson = $.toJSON(signinfo);
				
				return inputjson;
			}
		
			function sign()
			{
				var rv;
				//设置对话框显示位置：
				if(("" == document.getElementById("txt_dlg_left").value) && ("" == document.getElementById("txt_dlg_top").value)){
					ShowMsg("没有设置签名框位置，使用默认设置");
				}else{
					rv = anysign.set_dlg_pos(document.getElementById("txt_dlg_left").value, document.getElementById("txt_dlg_top").value);
					if(rv != 0){
						GetErrorMessage(rv);
					}else{
						ShowMsg("设置对话框位置成功");
					}
				}
				
				//签名
				var signer_info = build_plain_data();
				var plain = document.getElementById("txt_plain").value;				
				plain = anysign.Base64Encode(plain,"GBK");
				var sign_value = "";
				sign_value = anysign.sign_data_base64(signer_info, plain);
	
				if(sign_value == ""){
					GetErrorMessage(anysign.getLastError());
					return;
				}
				document.getElementById("txt_sign_value").value = sign_value;
				ShowMsg("签名成功");
				get_info();
			}
			
			function verify()
			{
				var plain = document.getElementById("txt_plain").value;
				plain = anysign.Base64Encode(plain,"GBK");
				var sign_value = document.getElementById("txt_sign_value").value;
				var rv;
				
				//验证数据签名
				rv = anysign.verify_sign_value_base64(sign_value, plain);				
				if(rv != 0){
					GetErrorMessage(rv);
					return;
				}
				
				ShowMsg("签名验证成功");
				
				//获取时间戳原文
				var tsPlain;
				tsPlain = anysign.get_ts_plaindata_base64(sign_value, plain);
				
				if(tsPlain == ""){
					GetErrorMessage(anysign.getLastError());
					return;
				}
				ShowMsg("获取时间戳原文成功");
				
				//获取时间戳
				var tsValue = anysign.get_ts_value(sign_value);
				if(tsValue == ""){
					GetErrorMessage(anysign.getLastError());
					return;
				}
				ShowMsg("获取时间戳成功");
				
				//验证时间戳
				var tsTime = anysign.verify_timestamp(tsValue, tsPlain);
				if(tsTime == ""){
					GetErrorMessage(anysign.getLastError());
					return;
				}
				document.getElementById("txt_ts_time").value = tsTime;
				ShowMsg("时间戳验证成功");
			
			}
			
			function get_info()
			{
				var sign_value = document.getElementById("txt_sign_value").value;
				//获取手写笔迹图片
				var scriptImage = anysign.get_sign_script(sign_value, 0);
				if("" == scriptImage){
					GetErrorMessage(anysign.getLastError());
				}else{
					ShowMsg("获取手写笔迹图片成功");
					document.getElementById("txt_script_image").value = scriptImage;
					var ImageData = document.getElementById("txt_script_image").value;
					var ResizeImage = anysign.resize_image(ImageData, 88, 0);
					if("" == ResizeImage){
						GetErrorMessage(anysign.getLastError());
					}else{
						ShowMsg("缩放手写笔迹图片成功");
						//document.getElementById("img_script").src ="data:image/gif;base64,"+ResizeImage;
						$.ajax({
							url : 'examsignupdate.action',
							data : {
								exam_num : $("#userid").val(),
								batch_name : sign_value,
								address : ResizeImage
							},
							type : "post",// 数据发送方式
							success : function(text) {
								if (text.split("-")[0] == 'ok') {
									ShowMsg(text.split("-")[2]);
									document.getElementById("img_script").src="getexamPhotosgin.action?exam_num="+text.split("-")[1]+"&"+new Date().getTime();
								} else {
									ShowMsg(text.split("-")[2]);
								}
							},
							error : function() {
								ShowMsg(text.split("-")[2]);				
							}
						});
					}					
				}
				
				
				//获取指纹图片
				var pfImage = anysign.get_sign_fingerprint(sign_value, 2);
				if("" == pfImage){
					GetErrorMessage(anysign.getLastError());
				}else{
					ShowMsg("获取指纹图片成功");
					document.getElementById("txt_fp_image").value = pfImage;
					var ImageData = document.getElementById("txt_fp_image").value;
					var ResizeImage = anysign.resize_image(ImageData, 88, 1);
					if("" == ResizeImage){
						GetErrorMessage(anysign.getLastError());
					}else{
						ShowMsg("缩放指纹图片成功");
						document.getElementById("img_fp").src ="data:image/jpg;base64,"+ResizeImage;
					}
				}
				
				
				//获取证据照片
				//var photoIndex = anysign.get_count_sign_photo(sign_value);
				//var photoImage = anysign.get_sign_photo(sign_value, photoIndex-1);
				//if("" == photoImage){
				///	GetErrorMessage(anysign.getLastError());
				//}else{
				//	ShowMsg("获取照片成功");
				//	document.getElementById("txt_photo_image").value = photoImage;
				//	document.getElementById("img_photo").src ="data:image/jpg;base64,"+photoImage;
				//	document.getElementById("img_photo").width="300";
				//	document.getElementById("img_photo").height="300";
				//}
				
				
				//获取签名时间
				var signTime = anysign.get_sign_time(sign_value);
				if("" == signTime){
					GetErrorMessage(anysign.getLastError());
				}else{
					ShowMsg("获取签名时间成功");
					document.getElementById("txt_sign_time").value = signTime;
				}

				
				//获取签名人
				var signer = anysign.get_cert_signer(sign_value);
				if("" == signer){
					GetErrorMessage(anysign.getLastError());
				}else{
					ShowMsg("获取签名人成功");
					document.getElementById("txt_cert_signer").value = signer;
				}
				
				
				//获取证书
				var signCert = anysign.get_sign_cert(sign_value);
				if("" == signCert){
					GetErrorMessage(anysign.getLastError());
				}else{
					ShowMsg("获取证书成功");
					document.getElementById("txt_sign_cert").value = signCert;
				}
				
			
			}
			
			function LoadOcx()
			{
				//创建控件AnySignApi
				try
				{
					var ocx = document.getElementById('anysign');
					if (ocx == null)
					{
						var el = document.createElement('AnySignInterfaceOcx');
						el.innerHTML = "<OBJECT classid=\"clsid:D3C5BDC4-CE65-48D8-8DE0-C3DB1DF84962\" id=\"anysign\"></OBJECT>";
						document.body.appendChild(el);
						
						var version = anysign.get_version();
						ShowMsg("当前AnySignInterface控件版本为" + version);
					}
					return anysign.getLastError();
				}
				catch (e)
				{
				   alert(e);
					alert("控件:AnySignInterface.ocx 可能没有注册成功！");
				}
			}
			
			function ShowMsg(msg)
			{
				document.getElementById('txt_show_msg').value += "\n";
				document.getElementById('txt_show_msg').value += msg;
				document.getElementById('txt_show_msg').scrollTop = document.getElementById('txt_show_msg').scrollHeight;
			}
			
            function get_colse(){
            	window.close();
            }
		</script>

	</head>	
	<body onLoad="LoadOcx()">	
	<input id="userid" type="hidden" value="<s:property value="exam_num"/>"/>
	<input id="txt_signer" type="hidden" value="<s:property value="custname"/>"  />
	<input id="txt_dlg_left" type="hidden" value="0"/>
    <input id="txt_dlg_top" type="hidden" value="0" />
    <input id="script_image_type"  type="hidden" value="1" />
	<input id="txt_plain" type="hidden" value="<s:property value="exam_num"/>"  /> 		
	<input id="txt_sign_value" type="hidden" value="<s:property value="itementities"/>" />
	
	<input  id="txt_script_image" type="hidden" /> 		
	<input  id="txt_fp_image" type="hidden"  /> 		
	<input  id="txt_photo_image" type="hidden"  /> 	
	
    <input id="txt_sign_cert" type="hidden"	/><!-- 签名值证书 -->
    <input id="txt_ts_time" type="hidden"	/><!-- 时间戳时间 -->
    <input id="txt_cert_signer" type="hidden"	/><!-- 证书中包含的签名人 -->
	<fieldset style="margin: 5px; padding-right: 0;">
		<legend>
			<strong>签名信息管理</strong>
		</legend>
		<div id="div_ctrl">
			<table>
				<tr>
					<td><input type="button" value="签名" onClick="sign()" /></td>
				</tr>
				<tr>
					<td><input type="button" value="验证" onClick="verify()" /></td>
				</tr>
				<tr>
					<td><input type="button" value="获取信息" onClick="get_info()" /></td>
				</tr>
               <tr>
					<td>&nbsp</td>
				</tr>
				<tr>
					<td><input type="button" value="关闭窗口" onClick="get_colse()" /></td>
				</tr>
			</table>
		</div>
		<div id="div_msg">
			<textarea id="txt_show_msg" title="" cols=37 rows=18></textarea>
		</div>
		<div id="div_sign_data">
			<table>
				<tr>
					<td>
						<div id="script_image"  title="此处显示最后获取到的签名图片"
							style="width: 200px; height: 200px">
							<img type="text" style="width:188px;height:188px;" id="img_script" src='getexamPhotosgin.action?exam_num=<s:property value="exam_num"/>'></img>
						</div>
					</td>
					<td>
						<div id="fp_image" title="此处显示最后获取到的签名图片"
							style="width: 200px; height: 200px">
							<img type="text" style="width:188px;height:188px;" id="img_fp" value=""></img>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">签名时间：<input type="text" id="txt_sign_time"
						value="" /></td>
				</tr>
			</table>

		</div>
	</fieldset>
</body>
</html>
