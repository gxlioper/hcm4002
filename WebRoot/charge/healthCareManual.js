//授权
function authorization(){
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在授权中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }	else{// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function(){
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","yb04.07.01.01",xmlhttp.responseText);
			var state = eval("("+xmlhttp.responseText+")");
			$(".loading_div").remove();
			if(state.flag=="1"){
				$.messager.alert("操作提示","授权成功"+state.cause, "info");
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
//		alert(xmlhttp.responseText);
//		document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
		}else{
			$(".loading_div").remove();
			//$.messager.alert("操作提示",'授权失败', "error");
		}
	  }
	var url = "http://localhost:7999/sieaf/authorization/";
	xmlhttp.open("post", url, true);
	//xmlhttp.open("GET","demo_get.asp",true);
	var user = "350603010835060300202";
	var pwd = "123";
	var data = JSON.stringify({
	"usr": user,
	"pwd": pwd
	});
	insturance_log("req","02","yb04.07.01.01",data);
	xmlhttp.send(data);
}

//读取卡号
function readCard(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:300px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><font style="font-weight:bold;font-size:15px" >正在读卡中，请稍后。。。</font></div></div>';
	 $("body").prepend(str);
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }	else{
		  // code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function(){
		  $(".loading_div").remove();
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","dk",xmlhttp.responseText);
			  var obj = eval("("+xmlhttp.responseText+")");
			  $.messager.alert("操作提示","卡余额为"+obj.data.cardNum, "info");
		}
	  }
	var url = "http://localhost:7999/sieaf/read_cardnum/";
	xmlhttp.open("post", url, true);
	var data = JSON.stringify({});
	insturance_log("req","02","dk",data);
	xmlhttp.send(data);
}
//作废明细
function invalidSubsidiary(){
	var bae016=$("#bae016").val();
	var bke241=$("#bke241").val();
	var medical_req_num=$("#akc190").val();
	var	medical_mx_req_num=$("#bke297").val();
   	var inputValueStr = JSON.stringify({
   		 "data": {
   			"akc190":medical_req_num,//	医保流水号
   			"bke297":medical_mx_req_num,//	医保明细流水号
   			"bae016":bae016,//	作废原因
   			"bke241":bke241 //	数据来源
   		 },
   		 "usr": "350603010835060300202",
   		 "pwd": "123",
   		 "funid": "yb04.07.01.06"
   		});
   	
   	insurance_send("02","yb04.07.01.06",inputValueStr,function(obj){
    	var oj= translation(obj);
    	var str = JSON.stringify(oj.data);
			if(oj.flag=="1"){
					putRefund(req_num,examInfoCharingItem,str);
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
	});
}
//退费
function medicareRefund(){
	var patNo=$("#aaz500").val();
	var ybname=$("#aac003").val();
	var req_num=$("#bke298").val();
	var medical_req_num=$("#akc190").val();
	var	medical_charge_req_num=$("#aae072").val();
	var	medical_way=$("#aka078").val();
	var examInfoCharingItem = new Array();
	examInfoCharingItem.length = 0;
   	var inputValueStr = JSON.stringify({
   		 "data": {
   			 "aaz500":patNo,//		社会保障卡号
   			 "aac002":"",//		证件号码（社会保障号）
   			 "aac003":ybname,//		姓名
   			 "aka078":"60",//		医疗就诊方式
   			 "akc190":medical_req_num,//		医保流水号
   			 "aae072":medical_charge_req_num,//		医保收费流水号
   			 "bke298":req_num,//		his收费流水号
   			 "bke241":"01",//	数据来源
   		 },
   		 "usr": "350603010835060300202",
   		 "pwd": "123",
   		 "funid": "yb04.07.01.05"
   		});
   	
   	insurance_send("02","yb04.07.01.05",inputValueStr,function(obj){
    	var oj= translation(obj);
    	var str = JSON.stringify(oj.data);
			if(oj.flag=="1"){
					putRefund(req_num,examInfoCharingItem,str);
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
	});
}
//--------------冲正交易--------
function checkCorrect(){
	var req=$("#bke298").val();
	var medical_req_num=$("#akc190").val();
	var	medical_charge_req_num=$("#aae072").val();
	var	medical_way=$("#aka078").val();
	if(req == null || req == ''){
		$.messager.alert("操作提示", "体检申请号错误！", "info");		
		return ;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
  	$.ajax({
		url : 'checkCorrectTransactionCharge.action', //校验交易是否能冲正
		data : {
			    "req_num":req
				},
				type : "post",//数据发送方式   
				success : function(data) {
					if("ok"==data.split("-")[0]){  // 可以冲正操作
						var inputValueStr = JSON.stringify({
							 "data": {
								 "funid":"yb04.07.01.04",	//原交易功能代码
								 "aka078":medical_way,	//医疗就诊方式
								 "akc190":medical_req_num,	//医保流水号
								 "aae072":medical_charge_req_num	//医保收费流水号
							 },
							 "usr": "350603010835060300202",
							 "pwd": "123",
							 "funid": "yb04.07.03.04"
							});
						insurance_send("02","yb04.07.03.04",inputValueStr,function(obj){
							var oj= translation(obj);
							if(oj.flag=="1"){
								$(".loading_div").remove();
								$.messager.alert("操作提示","冲正成功", "error");
							}else{
								$.messager.alert("操作提示",oj.cause, "error");
							}
						});
					}else{
						$(".loading_div").remove();
						$.messager.alert("操作提示", data.split("-")[1], "error");		
						return;
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");		
					return;
				}
			});
  
}
function putRefund(req_num,examInfoCharingItem,str){
	$.ajax({
	    url:'putRefundCharge.action',  
	    data:{
	      req_nums:req_num,
	      examInfoCharingItems:JSON.stringify(examInfoCharingItem),
	      singleRefund:"2",
	      insureAccount:str,
	      medical_type:"02"
	      },          
	    type: "post",//数据发送方式   
	      success: function(text){
	    	  if (text.split("-")[0] == 'ok') {
	    		  $(".loading_div").remove();
				  $.messager.alert('提示信息', "退费成功！",'info');
	    	  }else{
	    		  $(".loading_div").remove();
	    		  $.messager.alert('提示信息', text.split("-")[1],'error');
	    	  }
	        },
	        error:function(){
	        	$(".loading_div").remove();
	        	$.messager.alert('提示信息', "操作失败！",'error');
	        }  
	});
}