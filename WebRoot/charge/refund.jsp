<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function () {
	gettuifei_fees();
	tuifeiheji();
// 	yibaoqiandao();
});
// function yibaoqiandao(){
// 	citySignInSend("02");
// 	  setTimeout(function(){
// 		  citySignInSend("03");
// 	    },2000);//延时2秒后执行 
	
// }
var oldamount_t;
function gettuifei_fees(){
	var req_nums = "<s:property value='req_nums'/>";
	var model = {"exam_num":$("#ser_num").val(),"req_nums":req_nums};
	$("#tuifei_fees").datagrid({
		url: 'getChargingWayByExamNumCharge.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',  
		columns:[[{align:'center',field:'sf_ck_t',checkbox:true},
		          {align:"center",field:"charging_way","title":"收费方式","width":10},
		          {align:'center',field:"amount","title":"收费金额(元)","width":15},
// 		          {align:'center',field:"amount","title":"应退金额(元)","width":15},
		          {align:"center",field:"amount_t","title":"应退金额(元)","width":15,editor:{type:'numberbox',options:{precision:2}}},
		          {align:'center',field:"data_code_children","title":"编码","width":15,"formatter" : f_djtdellset},
		     	]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	if('<s:property value="isPrintRecepit"/>' == 'Y'){
	    		$("#yishou_t").html($("#yintui").html());
	    	}else{
	    		$("#yishou_t").html($("#yintui_sj").html());	
	    	}
	    	$.each(value.rows, function(index, item){
	    		item.amount_t = 0.0;
	    		$('#tuifei_fees').datagrid('refreshRow', index);
	    	});
// 	    	 $("#tuifei_fees").datagrid("checkAll"); // 获取所有选中的行
// 	    	 var editors = $('#tuifei_fees').datagrid('getEditor',{index:index,field:'amount_t'});
// 				$(editors.target).numberbox({onChange:function(){
// 						$('#tuifei_fees').datagrid('endEdit', index);
// 						tuifeiheji();
// 					}
// 				});

	    },
	    
	    singleSelect:false,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		toolbar:"#toolbar_t",
		checkOnSelect:false,
		onClickCell:function(index, field, row){
	    	//$('#tuifei_fees').datagrid('clearSelections', index);
			$('#tuifei_fees').datagrid('beginEdit', index);
			var editors = $('#tuifei_fees').datagrid('getEditor',{index:index,field:'amount_t'});
			$(editors.target).numberbox({onChange:function(){
					$('#tuifei_fees').datagrid('endEdit', index);
					tuifeiheji();
				}
			});
	    },
	    onBeforeEdit:function(rowIndex, rowData){
	    	oldamount_t = rowData.amount_t;
	    },
	    onAfterEdit:function(rowIndex, rowData, changes){
	    	if(changes.amount_t != undefined){ //输入应退金额
	    		if(Number(changes.amount_t) > rowData.amount || Number(changes.amount_t) < 0){
	    			$.messager.alert('提示信息', '应退金额不能大于收费金额或小于0',function(){});
	    			rowData.amount_t = oldamount_t;
	        		$('#tuifei_fees').datagrid('refreshRow', rowIndex);
	    		}
	    	}
	    },
	    onSelect:function(index,row){
	    	if(row.amount == 0){
	    		$('#tuifei_fees').datagrid('unselectRow', index);
	    	}else{
	    		$("input[name=sf_ck_t]").eq(index).attr("checked",true);
		    	tuifeiheji();
	    	}
	    },
	    onUnselect:function(index,row){
	    	$("input[name=sf_ck_t]").eq(index).attr("checked",false);
	    	tuifeiheji();
	    },
	    onCheckAll:function(rows){
	    	tuifeiheji();
	    },
	    onUncheckAll:function(rows){
	    	tuifeiheji();
	    }
	});
}
function f_djtdellset(val, row){
	if("115"==row.data_code_children){
		$("#pzh").attr("style","display:none;");//隐藏div
	}else if("101"==row.data_code_children||"102"==row.data_code_children){
		$("#pzh").attr("style","display:block;");//隐藏div
	}
}
function tuifeiheji(){
	var obj = $("input[name=sf_ck_t]");
	var shitui = 0.0;
	var data = $('#tuifei_fees').datagrid('getData');
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked == true){
			shitui += Number(data.rows[i].amount_t);
		}
	}
	$("#shitui_t").html(decimal(shitui,2));
}

function tuifeiClick(){
	var inter_class=$("#inter_class").val();
	if(inter_class=="02"||inter_class=="03"){
		readCardAccBalance(inter_class);
	}else{
		var isPrintRecepit = $("#isPrintRecepit").val();
		if(isPrintRecepit == 'Y'){
			$.messager.confirm('提示信息','确定同时作废所开发票吗？',function(r){
				if(r){
					save_tuifei('ykfpList');
				}
			});
		}else{
			save_tuifei('yksjList');
		}
	}
}

//保存退费信息
function save_tuifei(id){
	var yintui = Number($("#yishou_t").html());
	var shitui_t = Number($("#shitui_t").html());
	
	if(yintui != shitui_t){
		$.messager.alert('提示信息', "应退金额不等于实退金额，不能退费！",function(){});
		return;
	}
	var examInfoCharingItem = new Array();
	var data = $('#'+id).datagrid('getSelections');
	examInfoCharingItem.length = 0;
	
	var account_num = data[0].account_num;
	var req_nums = data[0].req_num;
	for(var i=0;i<data.length;i++){
		examInfoCharingItem.push({"id":data[i].id,
			  "item_id":data[i].item_id,
			  "item_amount":data[i].item_amount,
			  "amount":data[i].amount,
			  "personal_pay":data[i].personal_pay,
			  "discount":data[i].discount
			  });
	}
// 	return;
	var cash=false;	
	var charingWay = new Array();
	var obj1 = $('#tuifei_fees').datagrid('getData');
	var input = $("input[name=sf_ck_t]");
	charingWay.length = 0;
	var falg=false;
	var chargeType="01";
	var trade_class="";
	var chk_value =[]; 
	if(input.length > 0){
		for(var i=0;i<input.length;i++){
// 			if(input[i].checked == true){
				chk_value.push(obj1.rows[i].data_code_children); 
				if(obj1.rows[i].id==119){
					cash=true;	
				}
				charingWay.push({"charging_way":obj1.rows[i].id,"amount":obj1.rows[i].amount,"data_code_children":obj1.rows[i].data_code_children});
// 			}
		}
	}else{
		$.messager.alert('提示信息', "请选择退费方式!",function(){});
		return;
	}
// 	var shitui_t="";
// 	for (var j = 0; j < charingWay.length; j++) {
// 		 if(charingWay[j].charging_way==121){
// 			 shitui_t =charingWay[j].charging_way.amount;
// 		 }else if(charingWay[j].charging_way==234){
// 			 shitui_t =charingWay[j].charging_way.amount;
// 		 }else if(charingWay[j].charging_way==228){
// 			 shitui_t =charingWay[j].charging_way.amount;
// 		 }
// 		}
// 	alert(shitui_t)
// 	return;
	if(chk_value.indexOf("101") != -1&&chk_value.length==1){
		falg=true;
		trade_class="01";
		chargeType="00";
	}else if(chk_value.indexOf("102") != -1&&chk_value.length==1){
		falg=true;
		trade_class="01";
		chargeType="00";
	}else if(chk_value.indexOf("115")  != -1&&chk_value.length==1){
		falg=true;
		trade_class="00";
		chargeType="00";
	}else if(chk_value.indexOf("103") != -1){
		falg=true;
		trade_class="02";
		chargeType="01";
	} else if(chk_value.indexOf("104") != -1){
		falg=true;
		trade_class="03";
		chargeType="01";
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	//包含医保一退全退
	if(chargeType=="01"){
		$("#singleRefund").val("2");
		$.ajax({
		    url:'save_tuifeiCharge.action?language='+$("#language").val(),  
		    data:{
		      exam_id:$("#exam_id").val(),
		      exam_num:$("#ser_num").val(),
		      chargeType:chargeType,
		      amount1:yintui,
		      amount2:shitui_t,
		      discount: 10,
		      charingWays:JSON.stringify(charingWay),
		      examInfoCharingItems:JSON.stringify(examInfoCharingItem),
		      isPrintRecepit:$("#isPrintRecepit").val(),
		      account_num:account_num,
		      inter_class:trade_class,
		      req_nums:req_nums
		      //cardInfos:JSON.stringify(card_xiaofei)
		      },          
		    type: "post",//数据发送方式   
		      success: function(text){
		    	  if (text.split("-")[0] == 'ok') {
// 		    		  alert(text);
			    	  if(falg){
	// 		    		  	if(trade_class=="02"||trade_class=="03"){
			    		  		medicareRefund(trade_class,req_nums,trade_class,shitui_t,account_num,account_num,examInfoCharingItem,charingWay,cash,chk_value,obj1,id);
		// 	    		  		webSocketRegistration(trade_class,req_nums,trade_class,shitui_t,account_num,id,data,examInfoCharingItem);
	// 		    			}else{
	// 	// 	    				cerate_socket(req_nums,"",trade_class,shitui_t,account_num,id,data,charingWay);	
	// 		    				 WebSocketPos(req_nums,trade_class,shitui_t,account_num,account_num,charingWay,chk_value,obj1,examInfoCharingItem)
	// 		    			}
			    			
			    		}else{
			    			save_tuifeitz(account_num);
			    		}
		    	  }else{
		    		  $.messager.alert('提示信息', "操作失败！",'error');
		    	  }
		        },
		        error:function(){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', "操作失败！",'error');
		        }  
		});
	//单个退费   主要包含pos和现金流
	}else{
		$("#singleRefund").val("1");
		var charingWay = new Array();
		charingWay.length = 0;
		for(var i=0;i<input.length;i++){
			if(input[i].checked == true){
				charingWay.push({"charging_way":obj1.rows[i].id,"amount":obj1.rows[i].amount_t,"data_code_children":obj1.rows[i].data_code_children});
			}
		}
		$.ajax({
		    url:'save_tuifeiDXCharge.action?language='+$("#language").val(),  
		    data:{
		      exam_id:$("#exam_id").val(),
		      exam_num:$("#ser_num").val(),
		      chargeType:chargeType,
		      amount1:yintui,
		      amount2:shitui_t,
		      discount: 10,
		      charingWays:JSON.stringify(charingWay),
		      examInfoCharingItems:JSON.stringify(examInfoCharingItem),
		      isPrintRecepit:$("#isPrintRecepit").val(),
		      account_num:account_num,
		      inter_class:trade_class,
		      req_nums:req_nums
		      //cardInfos:JSON.stringify(card_xiaofei)
		      },          
		    type: "post",//数据发送方式   
		      success: function(text){
		    		medicareRefund(trade_class,req_nums,trade_class,shitui_t,account_num,account_num,examInfoCharingItem,charingWay,cash,chk_value,obj1,id);
// 		    	  if (text.split("-")[0] == 'ok') {
// 			    	  if(falg){
// 			    			WebSocketPos(req_nums,trade_class,shitui_t,account_num,account_num,charingWay,chk_value,obj1,examInfoCharingItem)
// 			    		}else{
// 			    			save_tuifeitz(account_num);
// 			    		}
// 		    	  }else{
// 		    		  $.messager.alert('提示信息', "操作失败！",'error');
// 		    	  }
		        },
		        error:function(){
		        	$(".loading_div").remove();
		        	$.messager.alert('提示信息', "操作失败！",'error');
		        }  
		});	
	}
	
}
function save_tuifeitz(account_num){
	 $(".loading_div").remove();
		alert_autoClose("操作提示", "退费成功",'info');
		$('#dlg-tuifei').dialog('close');
		chaxun();
// 	$.ajax({
// 		url : 'getamtForCISAccountnumCharge.action',
// 		data : {
// 			account_num : account_num
// 		    },
// 				type : "post",//数据发送方式   
// 				success : function(text) {
// 					if ((text!='error')&&(text.split("#")[5]=='2')) {
// 						var zuofeifapiaodata={
// 								yymc:"dbgj",
// 								fplx:"2",////发票类型 0 专票  2 普票
// 								fpdm:text.split("#")[4],//发票代码
// 								fphm:text.split("#")[3]//发票号码
// 						};
// 						var reqsdata=FpCancelInv(zuofeifapiaodata,function(objvalue) {
// 						    if(objvalue.error_flag=='0'){
// 						    	 $.messager.alert("操作提示", "发票【"+objvalue.fphm+"】作废成功", "");
// 						    }else{
// 						    	$.messager.alert("操作提示", objvalue.error_msg, "error");
// 						    }
						    
// 						});
						
// 					}
// 				},
// 				error : function() {
// 					$.messager.alert("操作提示", "操作错误", "error");					
// 				}
// 			});
	

//     		if(id == 'ykfpList'){
//     			$.messager.confirm('提示信息','是否打印发票？',function(r){
//     				if(r){
//     					fapiao_show();
//     				}
//     			});
//     		}
}





//回车事件
document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0]; 
			if(e && e.keyCode==27){ // 按 Esc                 
			//要做的事情           
			}           
			if(e && e.keyCode==113){ // 按 F2                 
			  //要做的事情               
			}                         
			if(e && e.keyCode==13){ // enter 键                
			  //要做的事情
// 				tuifeiClick();
			}        
	};
	//退费
	function WebSocketPos(req_num,trade_class,shitui_t,account_num,id,charingWay,chk_value,obj1,examInfoCharingItem){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 if(chk_value.indexOf("101") != -1||chk_value.indexOf("102") != -1){
				trade_class="01";
			}else if(chk_value.indexOf("115") != -1){
				trade_class="00";
			}
		var pay_way=charingWay[0].charging_way;
		 for (var j = 0; j < charingWay.length; j++) {
			 if(charingWay[j].charging_way==121){
				 shitui_t =charingWay[j].amount;
				 pay_way=charingWay[j].charging_way;
			 }else if(charingWay[j].charging_way==234){
				 shitui_t =charingWay[j].amount;
				 pay_way=charingWay[j].charging_way;
			 }else if(charingWay[j].charging_way==228){
				 shitui_t =charingWay[j].amount;
				 pay_way=charingWay[j].charging_way;
			 }
			}
// 		 alert(shitui_t);
// 		 charingWay[]
// 		 obj1.rows[i].id,"amount":obj1.rows[i].amount_t
// 		 if(obj1.rows[i].id==121){
// 			 shitui_t 
// 		 }
		 var voucher_no=$("#voucher_no").val();
		 var trade_no=$("#trade_no").val();
		 var nowDate = new Date();
		  var year = nowDate.getFullYear();
		  var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
		  var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
		  var dateStr = $("#trade_date").val();
		 insurance_send('dbgj','01','02',
				 '{"inter_class":"01",'
			 +'"trade_class":"'+trade_class+'",'
			 +'"trade_op_code":"02",'
			 +'"pay_class":"01",'
			 +'"peis_trade_code":"'+req_num+'",'
			 +'"amount":"'+shitui_t+'",'
			 +'"operator_id":"'+$("#center_id").val()+'",'
			 +'"operator_code":"'+$("#work_other_num").val()+'",'
			 +'"trade_date":"'+dateStr+'",'
			 +'"trade_no":"'+$("#trade_no").val()+'",'  //原交易参考号
			 +'"voucher_no":"'+$("#voucher_no").val()+'"}',function(objvalue) {
			 $(".loading_div").remove(); 
			 $('#dlg-fapiao').dialog('close')
				if(objvalue.error_flag=="1"){
					var obj=eval("("+objvalue.jsondata+")");
					if(obj.erro_code=="0"){
						$(".loading_div").remove();
// 						save_tuifeitz(id,data);
 						
						$.ajax({
							url : 'saveDailyCharge.action',
							async:false,
							data : {
								pay_way:pay_way,  
								amount:shitui_t,
								peis_trade_code:req_num,   
								trans_code:"02",   //00 收费 02 退费
								voucher_no:obj.voucher_no,
								trade_no:obj.trans_no
								},
									type : "post",//数据发送方式   
									success : function(data) {
										putRefund(req_num,id,examInfoCharingItem,"");
									}
								});
						
						
					}else{
// 						deleteTuifei(examInfoCharingItem,account_num,req_num);
						$(".loading_div").remove();
						$.messager.alert('提示信息', "退费失败！"+obj.error_msg,'error');
					}
				}else{
					$(".loading_div").remove();
					$.messager.alert('提示信息', "退费失败！",'error');
				}
			})
	}


function deleteTuifei(examInfoCharingItem,account_num,req_num){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
	    url:'deleteTuifeiCharge.action',  
	    data:{
	      examInfoCharingItems:JSON.stringify(examInfoCharingItem),
	      isPrintRecepit:$("#isPrintRecepit").val(),
	      account_num:account_num,
	      req_nums:req_num
	      },          
	    type: "post",//数据发送方式   
	      success: function(text){
	    	  if (text.split("-")[0] == 'ok') {
	    		  $(".loading_div").remove();
	    		  $.messager.alert('提示信息', "操作失败，删除数据成功！",'info');
	    	  }
	        },
	        error:function(){
	        	$(".loading_div").remove();
	        	$.messager.alert('提示信息', "操作失败！",'error');
	        }  
	});
}
function putRefund(req_num,id,examInfoCharingItem,str){
	var  singleRefund=$("#singleRefund").val();
	$.ajax({
	    url:'putRefundCharge.action',  
	    data:{
	      req_nums:req_num,
	      examInfoCharingItems:JSON.stringify(examInfoCharingItem),
	      singleRefund:singleRefund,
	      insureAccount:str,
	      medical_type:"02"
	      },          
	    type: "post",//数据发送方式   
	      success: function(text){
	    	  if (text.split("-")[0] == 'ok') {
	    		  save_tuifeitz(id);
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
//读卡
function readCardAccBalance(inter_class){
	
	
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
		if (xmlhttp.readyState==4 && xmlhttp.status==200){
			insturance_log("res","02","dk",xmlhttp.responseText);
			  var obj = eval("("+xmlhttp.responseText+")");
// 			  certification(c,inter_class,type_class,type)
			  var patNo=$("#patNo").val();
				if(obj.data.cardNum==patNo){
					$(".loading_div").remove();
					var isPrintRecepit = $("#isPrintRecepit").val();
					if(isPrintRecepit == 'Y'){
						$.messager.confirm('提示信息','确定同时作废所开发票吗？',function(r){
							if(r){
								save_tuifei('ykfpList');
							}
						});
					}else{
						save_tuifei('yksjList');
					}
				}else{
					$(".loading_div").remove();
					$.messager.alert('提示信息', "收费医保编号与退费医保编号不匹配。",'error');	
				}
		}
	  }
	var url = "http://localhost:7999/sieaf/read_cardnum/";
	xmlhttp.open("post", url, true);
	var data = JSON.stringify({});
	insturance_log("req","02","dk",data);
	xmlhttp.send(data);
	
	
	
	
	
}
//退费
function medicareRefund(trade_class,req_num,trade_class,shitui_t,account_num,account_num,examInfoCharingItem,charingWay,cash,chk_value,obj1,id){
	var patNo=$("#patNo").val();
	var ybname=$("#ybname").val();
	var medical_charge_req_num=$("#medical_charge_req_num").val();
	var medical_req_num=$("#medical_req_num").val();
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
   		 "usr": "sy_lzq",
   		 "pwd": "000000",
   		 "funid": "yb04.07.01.05"
   		});
   	
   	insurance_send(inter_class,"yb04.07.01.05",inputValueStr,function(obj){
    	var oj= translation(obj);
    	var str = JSON.stringify(oj.data);
			if(oj.flag=="1"){
				$(".loading_div").remove();
				if(charingWay.length>1){
					if(cash){
						putRefund(req_num,id,examInfoCharingItem,str);
					}else{
						$.ajax({
						    url:'putRefundCharge.action',  
						    data:{
						      req_nums:req_num,
						      examInfoCharingItems:JSON.stringify(examInfoCharingItem)
						      },          
						    type: "post",//数据发送方式   
						      success: function(text){
						    	  }
						});
						WebSocketPos(req_num,trade_class,shitui_t,account_num,id,charingWay,chk_value,obj1,examInfoCharingItem);
					}
				}else{
					putRefund(req_num,id,examInfoCharingItem,str);
				}
			}else{
				$.messager.alert("操作提示",state.cause, "error");
			}
	});
}
</script>
<input type="hidden" id="patNo"  value="<s:property value="patNo"/>">
<input type="hidden" id="inter_class"  value="<s:property value="inter_class"/>">
<input type="hidden" id="name"  value="<s:property value="name"/>">
<input type="hidden" id="work_other_num" value="<s:property value="work_other_num"/>">
<input type="hidden" id="center_id" value="<s:property value="center_id"/>" >
<input type="hidden" id="trade_no" value="<s:property value="trade_no"/>" >
<input type="hidden" id="voucher_no" value="<s:property value="voucher_no"/>" >
<input type="hidden" id="trade_date" value="<s:property value="trade_date"/>" >
<input type="hidden" id="medical_req_num" value="<s:property value="medical_req_num"/>" >
<input type="hidden" id="medical_charge_req_num" value="<s:property value="medical_charge_req_num"/>" >
<input type="hidden" id="req_nums" value="<s:property value="req_nums"/>" >
<input type="hidden" id="singleRefund">
<div class="easyui-layout" fit="true">
<div data-options="region:'north'" style="height:328px;">
<table id="tuifei_fees"></table>

</div>
</div>
<div id = "toolbar_t">
	<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; margin: 5px;">应退金额：<font id="yishou_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
	<div style="width: 180px; float: left; font-size: 18px;font-weight:bold; color: #f00; margin-top: 5px;">实退金额：<font id="shitui_t">0</font>元&nbsp;&nbsp;&nbsp;</div>
	
	<div><input type="hidden" id="isPrintRecepit" value="<s:property value="isPrintRecepit"/>"/>
	<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;" onclick="javascript:tuifeiClick();">确认退费</a></div>
<!-- 	<div id="pzh" style="display:none">原交易参考号：<input type="text" id="trade_no" /> -->
<!-- 	原凭证号：<input type="text" id="voucher_no" /></div> -->
</div>