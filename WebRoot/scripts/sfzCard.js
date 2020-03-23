/*--------------------------------------------------|
|火箭蛙身份证读卡  1.0.0                                   |
|---------------------------------------------------|
| yangm                                             |
|--------------------------------------------------*/
var comtype;
function readCardSFZ(comtype){
	if(comtype=="d000"){
		return readCardSFZ_HTTP();
	}else if(comtype=="d001"){
		return readCardSFZ_DH();
	}else if(comtype=="d002"){
		return readCardSFZ_COMM();
	}else if(comtype=="d003"){//浙江二院
		return readCardZJTY_COMM();
	}else if(comtype=="d004"){//新中新dkq-A16D 身份证读卡器
		return readCardSYN_COMM();
	}else if(comtype=="d005"){//神思身份证读卡器 ss628(100)
		return readCardSS_COMM();
	}else if(comtype=="d006"){//180tj
		return readCard180_COMM();
	}else if(comtype=="d007"){//华大多合一
		return readCardHD_COMM();
	}else if(comtype=="d008"){//德卡读卡器
		return readCardDK_COMM();
	}else if(comtype=="d009"){//神盾BS
		return readCardSD_COMM();
	}
}

//东北国际 大华四合一读卡器 读取身份证
function readCardSFZ_DH(){
	var data;
	var cardconn = GT2ICROCX.GetIDCardInfo();
	cardconn =cardconn.replace(/[\r\n]/g, "");
	data=JSON.parse(cardconn);
	return data;
}

//东北国际 大华四合一读卡器 读就诊卡号
function readCardJZK_DH(){
	var data;
	var cardconn = GT2ICROCX.GetVisitCardNo();
	return cardconn;
}

//火箭蛙 固定读卡器获取身份证正信息
function readCardSFZ_COMM(){
	//火箭蛙 固定读卡器获取身份证正信息
	document.write("<OBJECT id='GT2ICROCX' Name='GT2ICROCX'  width='0' height='0'  CLASSID='CLSID:5A381625-B14C-4ACD-BD3B-8D2BA0B5C7DB' CODEBASE='XX_IdrOcx.dll#version=2,1,0,0'></OBJECT>");
	var data;
	var ret = GT2ICROCX.ReadCard();
	if (ret != "0"){
		data.error_flag="-1";
		data.error_msg="读身份证失败";
	}else{
		data.error_flag="0";
		data.error_msg="ok";
		data.certno=GT2ICROCX.CardNo;
		data.name=GT2ICROCX.Name;
		data.sex=GT2ICROCX.SexL;
		data.birth=GT2ICROCX.Born;
		data.address=GT2ICROCX.Address;
		data.bmpFileData=GT2ICROCX.Base64Jpg;
	}
	return data;
}

//神盾读卡器
function readCardSD_COMM() {
	var datastr = {
		error_flag : "-1",
		error_msg : "无效数据",
		certno : "",
		name : "",
		sex : "",
		birth : "",
		address : "",
		bmpFileData : ""
	};
	var i = 0;
	var flag = 0;

	if (GT2ICROCX.OpenComm(1001) == 1) {
		flag = 1;
		// alert(i);
	} else {
		for (i = 1; i < 3; i++) {
			if (aaa.OpenComm(i) == 1) {
				flag = 1;
				// alert(i);
				break;
			}
			if (flag != 1) {
				datastr = {
					error_flag : "-1",
					error_msg : "打开端口失败",
					certno : "",
					name : "",
					sex : "",
					birth : "",
					address : "",
					bmpFileData : ""
				};
			}
		}
	}

	if (flag == 1) {
		if (GT2ICROCX.Authen() == 1) {
			ret = GT2ICROCX.ReadCardPath("c:\\", 1);
			if (ret == 1 || ret == 3) {
				datastr.error_flag="0";
				datastr.error_msg="ok";
				datastr.name = GT2ICROCX.sName;
				datastr.sex = GT2ICROCX.sSex;
				if(datastr.sex=="1"){
					datastr.sex="男";
				}else if(datastr.sex=="2"){
					datastr.sex="女";
				}else{
					datastr.sex="";	
				}
				// myform.national.value=GT2ICROCX.sNation;
				datastr.birth = GT2ICROCX.sBornDate;
				// datastr.address=GT2ICROCX.sAddress;
				datastr.certno = GT2ICROCX.sIDNo;
				// myform.qfjg.value=GT2ICROCX.sSignGov;
				// myform.yxqstart.value=GT2ICROCX.sStartDate;
				// myform.yxqend.value=GT2ICROCX.sEndDate;
				GT2ICROCX.ReadCard(3)
				datastr.address = GT2ICROCX.sNewAddress;
				datastr.bmpFileData = GT2ICROCX.PhotoBuffer;
				// myform.fp.value=GT2ICROCX.sFpState;
			} else {
				datastr = {
					error_flag : "-1",
					error_msg : "读卡错误！",
					certno : "",
					name : "",
					sex : "",
					birth : "",
					address : "",
					bmpFileData : ""
				};
			}
		} else {
			datastr = {
				error_flag : "-1",
				error_msg : "找卡错误,请重新放卡",
				certno : "",
				name : "",
				sex : "",
				birth : "",
				address : "",
				bmpFileData : ""
			};
		}
	} else {
		datastr = {
			error_flag : "-1",
			error_msg : "打开端口失败",
			certno : "",
			name : "",
			sex : "",
			birth : "",
			address : "",
			bmpFileData : ""
		};
	}
	GT2ICROCX.EndComm();
	return datastr;
}

//浙江二院
function readCardZJTY_COMM(){
	//浙江二院
	//document.write("<OBJECT id='CVR_IDCard' Name='CVR_IDCard'  width='0' height='0'  CLASSID='CLSID:10946843-7507-44FE-ACE8-2B3483D179B7'></OBJECT>");
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",address:"",bmpFileData:""};
	var ret= GT2ICROCX.ReadCard();
	if (ret != "0"){
		datastr.error_flag="-1";
		datastr.error_msg=ret;
	}else{
		datastr.error_flag="0";
		datastr.error_msg="ok";
		datastr.certno=GT2ICROCX.CardNo;
		datastr.name=GT2ICROCX.Name;
		datastr.sex=GT2ICROCX.Sex;
		var borns = GT2ICROCX.Born.replace("年","-");
		borns = borns.replace("月","-");
		borns = borns.replace("日","");
		datastr.birth=borns;
		datastr.address=GT2ICROCX.Address;
		datastr.bmpFileData=GT2ICROCX.Picture;
	}
	return datastr;
}

//神思身份证读卡器 ss628(100)
function readCardSS_COMM(){
	var data;
	var cardconn = GT2ICROCX.GetIDCardInfo();
	cardconn =cardconn.replace(/[\r\n]/g, "");
	data=JSON.parse(cardconn);
	return data;
}


function readCardSYN_COMM(){
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",address:"",bmpFileData:""};
	var str = GT2ICROCX.FindReader();
  	if (str > 0)
  	{
  		var nRet;		
  	  	nRet = GT2ICROCX.ReadCardMsg();
  	  	if(nRet==0)
  	  	{
  		datastr.error_flag="0";
		datastr.error_msg="ok";
		datastr.certno=GT2ICROCX.CardNo;
		datastr.name=GT2ICROCX.NameA.trim();
		var sexcode=GT2ICROCX.Sex;
		if(sexcode==1){
			datastr.sex="男";
		}else if(sexcode==2){
			datastr.sex="女";
		}else{
			datastr.sex="";
		}
		
		var bornsmm = GT2ICROCX.Born;
		var borns = bornsmm.substring(0,4)+"-";
		borns = borns+bornsmm.substring(4,6)+"-";
		datastr.birth=borns+bornsmm.substring(6,8);
		datastr.address=GT2ICROCX.Address;
		datastr.bmpFileData=GT2ICROCX.Base64Photo;
  	  	}else{
  	  	  datastr.error_flag="-1";
		  datastr.error_msg="没有找到身份证";  
  	  	}
  	}else{
  		datastr.error_flag="-1";
		datastr.error_msg="没有找到读卡器";  		
  	}
	return datastr;
}

function readCard180_COMM(){
	
	var cardconn = GT2ICROCX.GetIDCardInfo();
	cardconn =cardconn.replace(/[\r\n]/g, "");
	cardconn = Trim(cardconn,"g");
	var data = JSON.parse(cardconn);	
	return data;
}

function Trim(str,is_global){
    var result;
    result = str.replace(/(^\s+)|(\s+$)/g,"");
    if(is_global.toLowerCase()=="g")
    {
        result = result.replace(/\s/g,"");
     }
    return result;
}

function readCardHD_COMM(){
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",address:"",bmpFileData:""};
	var t_PIC = 'D:\\zp.jpg';
	var ret = document.getElementById("GT2ICROCX").iReaderIDCard(t_PIC);
	if (ret == 0){
		var cardinfo = document.getElementById("GT2ICROCX").pOutInfo;
		var base64 = document.getElementById("GT2ICROCX").base64Data;
		var xinxis = cardinfo.split("|");
		datastr.error_flag="0";
		datastr.error_msg="ok";
		datastr.certno=xinxis[5];
		datastr.name=xinxis[0];
		datastr.sex=xinxis[1];
		var bornsmm = xinxis[3];
		var borns = bornsmm.substring(0,4)+"-"+bornsmm.substring(4,6)+"-"+bornsmm.substring(6,8);
		datastr.birth=borns;
		datastr.address=xinxis[4];
		datastr.bmpFileData = base64;
	}else{
		datastr.error_flag="-1";
		datastr.error_msg=document.getElementById("GT2ICROCX").pMsgErr;
	}
	return datastr;
}

function readCardDK_COMM(){
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",address:"",bmpFileData:""};
	var st; //主要用于返回值
	var lSnr; //本用于取序列号，但在javascript只是当成dc_card函数的一个临时变量
	var rlen; //用于取一些返回值长度，但在javascript只是当成dc_card函数的一个临时变量
	var i,m,n;
	GT2ICROCX.dc_exit();
	st = GT2ICROCX.dc_init(100, 115200);
	if(st <= 0){
		datastr.error_flag="-1";
		datastr.error_msg="打开读卡器出错!";
		return datastr;
	}
	st = GT2ICROCX.DC_start_i_d();
	if (st < 0){
		datastr.error_flag="-1";
		datastr.error_msg="读取身份证信息失败!";
		return datastr;
	}
	GT2ICROCX.dc_beep(5);
	datastr.error_flag="0";
	datastr.error_msg="ok";
	datastr.name = GT2ICROCX.DC_i_d_query_name();
	datastr.sex = GT2ICROCX.DC_i_d_query_sex();
//	datastr.nation = GT2ICROCX.DC_i_d_query_nation();
	datastr.birth = GT2ICROCX.DC_i_d_query_birth();
	datastr.address = GT2ICROCX.DC_i_d_query_address();
	datastr.certno = GT2ICROCX.DC_i_d_query_id_number();
//	datastr.department = GT2ICROCX.DC_i_d_query_department();
//	datastr.expire = GT2ICROCX.DC_i_d_query_expire_day();
	var st=GT2ICROCX.DC_i_d_query_photo_bmp_buffer();
	datastr.bmpFileData=sha1_to_base64(GT2ICROCX.get_bstrRBuffer_asc);
//	GT2ICROCX.put_bstrSBuffer = "d:\\me.jpg";
//	st = GT2ICROCX.DC_i_d_query_photo_file();
	GT2ICROCX.DC_end_i_d();
	GT2ICROCX.dc_exit();

	return datastr;
}
/************16进制数据转为64进制数据**************/
function sha1_to_base64(sha1) {
	var digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var base64_rep = "";
	var cnt = 0;
	var bit_arr = 0;
	var bit_num = 0;
	for (var n = 0; n < sha1.length; ++n) {
		if (sha1[n] >= 'A' && sha1[n] <= 'Z') {
			ascv = sha1.charCodeAt(n) - 55;
		} else if (sha1[n] >= 'a' && sha1[n] <= 'z') {
			ascv = sha1.charCodeAt(n) - 87;
		} else {
			ascv = sha1.charCodeAt(n) - 48;
		}
		bit_arr = (bit_arr << 4) | ascv;
		bit_num += 4;
		if (bit_num >= 6) {
			bit_num -= 6;

			base64_rep += digits[bit_arr >>> bit_num];
			bit_arr &= ~(-1 << bit_num);
		}
	}
	if (bit_num > 0) {
		bit_arr <<= 6 - bit_num;
		base64_rep += digits[bit_arr];
	}
	var padding = base64_rep.length % 4;
	if (padding > 0) {
		for (var n = 0; n < 4 - padding; ++n) {
			base64_rep += "=";
		}
	}
	return base64_rep;
}

/*******************************************************************************
 * 社保卡读取
 * 
 ******************************************************************************/
function read_card_sbk(comtype){
	if(comtype=="d007"){// 华大多合一
		return read_card_sbk_hd();
	}else{
		return {error_flag:"-1",error_msg:"该读卡器未开通社保卡读取!"};
	}
}
function read_card_sbk_hd(){
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",nation:"",sbno:""};
	var slot = '17';
	var ret = document.getElementById("GT2ICROCX").iReadSicard(slot);
	if (ret == 0){
		var cardinfo = document.getElementById("GT2ICROCX").pOutInfo;
		var xinxis = cardinfo.split("|");
		datastr.error_flag="0";
		datastr.error_msg="ok";
		datastr.certno=xinxis[1];
        datastr.sbno=xinxis[0];
		datastr.name=xinxis[2];
		datastr.sex=xinxis[3];
		var bornsmm = xinxis[5];
		var borns = datastr.certno.substring(6,10)+"-"+datastr.certno.substring(10,12)+"-"+datastr.certno.substring(12,14);
		datastr.birth=borns;
		datastr.nation=xinxis[4];
	}else{
		datastr.error_flag="-1";
		datastr.error_msg=document.getElementById("GT2ICROCX").pMsgErr;
	}
	return datastr;
}

function readCardSFZ_HTTP(){
	var datastr={error_flag:"-1",error_msg:"无效数据",certno:"",name:"",sex:"",birth:"",address:"",bmpFileData:""};
	$.ajax({
		url:'http://localhost:8088/IDCard',
		type:'get',
		async:false,
		success:function(data){
			var cardinfo = JSON.parse(data);
			if(cardinfo.rcode == '0') {
				datastr.error_msg = 'ok';
				datastr.error_flag="0";
				if(cardinfo.photo != '') {
					datastr.bmpFileData = cardinfo.photo;
	        	}
				datastr.name = cardinfo.name;
	        	datastr.sex = cardinfo.sex;
	        	datastr.birth = cardinfo.birthday;
	        	datastr.certno = cardinfo.certno;
	        	datastr.nation = cardinfo.nation;
	        	datastr.address = cardinfo.address;
			} else {
				datastr.error_flag="-1";
				datastr.error_msg = data.msg;
			}
		},
		error:function(data){
			datastr.error_flag="-1";
			datastr.error_msg = data.statusText;
		},
	});
	return datastr;
}