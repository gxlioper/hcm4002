/*--------------------------------------------------|
|火箭蛙社保交易  1.0.0                                   |
|---------------------------------------------------|
| yangm                                             |
|--------------------------------------------------*/
var inter_class;//消息类型01 银联pos 02 市医保，03 表示省医保
var trade_code;//医保交易类型
var inter_data;//医保交易数据
var hospital;
function insurance_send(inter_class,trade_code,inter_data,callback){
	insturance_log("req",inter_class,trade_code,inter_data);
	
	var xmlhttp;
	if(window.XMLHttpRequest){
		  xmlhttp=new XMLHttpRequest();
	  }else {
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  var url = "http://localhost:7999/sieaf/business_handle/";
	  xmlhttp.open("post", url, true);
	  var data = JSON.stringify({
	  "inputValue": inter_data,
	  "outputLen": 20000
	  });
	  xmlhttp.send(data);
	  xmlhttp.onreadystatechange=function(){
	  if(xmlhttp.readyState==4 && xmlhttp.status==200){
		  insturance_log("res",inter_class,trade_code,xmlhttp.responseText);
		  var obj = eval("("+xmlhttp.responseText+")");
	      callback(obj);
		  return obj;
		}
		//else if(xmlhttp.status!=200){
		//	$.messager.alert("操作提示",'操作——失败', "error");
		//}
	  }

//	var datastr={"flag":"0","cause":"医保发送请求失败！"};
//	$.ajax({
//		url:'http://localhost:7999/sieaf/business_handle/',
//		type:'post',
//		timeout:60000, //超时时间设置，单位毫秒
//		data : {
//            "inputValue": inter_data,
//            "outputLen": 20000
//		},
//		async: true,//将false改为true
//		dataType:'text',
//		success:function(data){
//			insturance_log("res",inter_class,trade_code,data);
//			var obj = eval("("+data+")");
//			callback(obj);
//			return obj;
//		},
//		error:function(data){
//			$(".loading_div").remove();
//			$.messager.alert("操作提示", '交易失败',"error");
//			return datastr;
//		},
//	});
 
	}
	
//保存医保日志
var req_res; //请求、应答
function insturance_log(req_res,inter_class,trade_code,inter_data){
	var ctr_type_name="未知消息类型";
    var logfilename="未知名称";
//    alert(trade_code)
	if("02"==inter_class){
		logfilename="市医保";
		if("yb04.10.01.16"==trade_code){
			ctr_type_name="身份认证"+req_res;
		}else if("yb04.07.01.01"==trade_code){
			ctr_type_name="通用登记-"+req_res;
		}else if("yb04.07.01.03"==trade_code){
			ctr_type_name="通用明细上传-"+req_res;
		}else if("yb04.07.01.04"==trade_code){
			ctr_type_name="通用收费-"+req_res;
		}else if("yb04.07.01.05"==trade_code){
			ctr_type_name="通用退费-"+req_res;
		}else if("yb04.07.03.04"==trade_code){
			ctr_type_name="冲正交易-"+req_res;
		}else if("yb04.07.03.07"==trade_code){
			ctr_type_name="日对账-"+req_res;
		}else if("yb04.07.03.05"==trade_code){
			ctr_type_name="交易对账-"+req_res;
		}
	}else if("03"==inter_class){
		logfilename="省医保";
		 if("9100"==trade_code){
			 ctr_type_name="签到-"+req_res;
		}else if("2100"==trade_code){
			ctr_type_name="读卡-"+req_res;
		}else if("2210"==trade_code){
			ctr_type_name="登记-"+req_res;
		}else if("2720"==trade_code){
			ctr_type_name="收费-"+req_res;
		}else if("2430"==trade_code){
			ctr_type_name="退费-"+req_res;
		}else if("1120"==trade_code){
			ctr_type_name="对帐-"+req_res;
		}else if("1110"==trade_code){
			ctr_type_name="对帐明细-"+req_res;
		}else if("2421"==trade_code){
			ctr_type_name="冲正-"+req_res;
		}
	}else if("01"==inter_class){
		logfilename="pos通";
		 if("00"==trade_code){
			 ctr_type_name="收费-"+req_res;
		}else if("02"==trade_code){
			 ctr_type_name="退费-"+req_res;
		}
	}
	
	$.ajax({
		url:'insertInsuranceLogCharge.action',
		data : {
			"logFileName":logfilename,
			"logData":ctr_type_name+"-"+inter_data,
		},
		type : 'post',
		success:function(data){},
		error:function(data){},
	});
  }


function translation(obj){
	var da=JSON.stringify(obj.data.outputValue);
	//var str =da.replace(/[\'\\\\/\b\f]/g);
	//var y=str.replace(/^(\s|")+|(\s|")+$/g,'');
	 var  str = da.replace(/[\'\\\\/\b\f\n\r\t]/g,'');
	 var y = str.replace(/^(\s|")+|(\s|")+$/g,'');
	var oj=eval("("+y+")");
	return oj;
}	
//测试方法
function sss(){
	insurance_send('dbgj','03','9100','{"inter_class":"02","trade_code":"9100","operator_id":"14","operator_code":"998889","operator_name":"杨明"}',function(objvalue) {
	    alert(objvalue.error_flag);
	})
}	