var contralcustflag=0;  // 0 标识新增，1标识预约新增，2标识修改
// var dingweitype=0; //0 标识新增和预约新增，1标识修改
$(document).ready(function() {
	huiche();
		
	 var height = window.screen.height-260;  
	stView_layout = $('#djtregisterglistshow').layout({
	height: height 
	});
	f_getDatadic();
	setselectListGrid();
    var oldisafterpay=$("#oldis_after_pay").val();
	$('[name="is_after_pay"]:radio').each(function() {
        if (this.value == oldisafterpay){   
           this.checked = true;   
        }     
     });   
	
	f_findcustomerone($("#exam_num").val(),0);
	   $("#birthday").datebox({
	        onSelect: function(date){  
	        	selectbirthday();  
	        }  
	    }); 
	 
		$('#exam_num').keydown(function (e) {
		   if (e.keyCode == 13) {
		      findcustomer_examnum();
		   }
		 });
		
		$('#id_num').keydown(function (e) {
		   if (e.keyCode == 13) {
			  findcustomer_idnum();
		   }
		});
		
		$('#custname').keydown(function (e) {
		   if (e.keyCode == 13) {
		      findcustomer_custname();
		   }
		});
		
		$('#introducer').combobox({
			url:'queryIntroducerList.action',
			valueField:'name',    
			textField:'name',
			panelHeight:'200',
		});
		
		$('#exam_num').css('ime-mode','disabled');
		$('#exam_num').focus();
		
	});

var vcontralcustflag;
function fnewbutton(vcontralcustflag){
	contralcustflag=vcontralcustflag;
	if((contralcustflag==0)&&$('#exam_num').val()!=""){
		$.messager.alert("操作提示", "记录存在，不能新增", "error");
	}else if((contralcustflag==1)&&$('#exam_num').val()!=""){
		$.messager.alert("操作提示", "记录存在，不能预约", "error");
	}else if((contralcustflag==2)&&$('#exam_num').val()==""){
		$.messager.alert("操作提示", "记录不存在，不能修改", "error");
	}else{
	   if (checkinput()) {
		   if(contralcustflag=='1'){
			   $("#dlg-edit").dialog({
			 		title:'体检人员预约',
			 		href:'getdjtregisterdataShow.action'
			 	});
			 	$("#dlg-edit").dialog('open');	
		   }else{
		    addcustomerdo();
		   }
	   }
	}
 }

// 自动填充 生日、年龄、性别
function selectidnum(){
	if($('#s_idtype').combobox('getText').trim()!="身份证"){
		return;
	}
	var idnum= $("#id_num").val();// 取值
	if(idnum.length==18){
		var ssidnum=idnum.substring(0,17);
		if (isSZ(ssidnum)) {		
		  $('#birthday').datebox('setValue',IdCard(idnum,1));
		  $('#age').textbox('setValue',IdCard(idnum,3));
		  $('#sex').combobox('setValue',IdCard(idnum,2));
		} else {
			$.messager.alert("提示信息","身份证不存在","error");
		}
		
	} else if(idnum.length==15 ){
		//15位身份证验证
		var idca= idCardNoUtil.checkIdCardNo(idnum)
		if(idca){
				//性别&&身份证
			  	var getIdCardInfo = idCardNoUtil.getIdCardInfo(idnum);
			  
			  	//计算年龄
			    var myDate = new Date();
		        var month = myDate.getMonth() + 1;
		        var day = myDate.getDate();
		        var age = myDate.getFullYear() - Number("19"+idnum.substring(6,8)) - 1;
		        if (idnum.substring(8,10) < month || idnum.substring(8,10) == month && idnum.substring(10,12) >= day) {
		            age++;
		        }
			  
		        $('#birthday').datebox('setValue',getIdCardInfo.birthday);//出生日期
				$('#age').textbox('setValue',age);//年龄
				$('#sex').combobox('setValue',getIdCardInfo.gender);//性别
		} else {
			$.messager.alert("提示信息","身份证不存在","error");
		}
	} else if(idnum!="" && idnum.length!=15 && idnum.length!=18 ){
		$.messager.alert("提示信息","身份证不存在","error");
	}
}

// -------------------------------------------zr15位身份证验证---------------------------/////////////

var idCardNoUtil = {
		 /*省,直辖市代码表*/
		 provinceAndCitys: {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",
		 31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",
		 45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
		 65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"},
		 
		 /*每位加权因子*/
		 powers: ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"],
		  
		 /*第18位校检码*/
		 parityBit: ["1","0","X","9","8","7","6","5","4","3","2"],
		 
		 /*性别*/
		 genders: {male:"男",female:"女"},
		 
		 /*校验地址码*/
		 checkAddressCode: function(addressCode){
		   var check = /^[1-9]\d{5}$/.test(addressCode);
		   if(!check) return false;
		   if(idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0,2))]){
		    return true;
		   }else{
		    return false;
		   }
		 },
		 
		 /*校验日期码*/
		 checkBirthDayCode: function(birDayCode){
		   var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
		   if(!check) return false;  
		   var yyyy = parseInt(birDayCode.substring(0,4),10);
		   var mm = parseInt(birDayCode.substring(4,6),10);
		   var dd = parseInt(birDayCode.substring(6),10);
		  var xdata = new Date(yyyy,mm-1,dd);
		  if(xdata > new Date()){
		   return false;//生日不能大于当前日期
		  }else if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) ){
		   return true;
		  }else{
		   return false;
		  }
		 },
		  
		 /*计算校检码*/
		 getParityBit: function(idCardNo){
		  var id17 = idCardNo.substring(0,17);  
		   /*加权 */
		   var power = 0;
		   for(var i=0;i<17;i++){
		    power += parseInt(id17.charAt(i),10) * parseInt(idCardNoUtil.powers[i]);
		   }       
		   /*取模*/
		   var mod = power % 11;
		   return idCardNoUtil.parityBit[mod];
		 },
		  
		 /*验证校检码*/
		 checkParityBit: function(idCardNo){
		   var parityBit = idCardNo.charAt(17).toUpperCase();
		   if(idCardNoUtil.getParityBit(idCardNo) == parityBit){
		     return true;
		   }else{
		     return false;
		   }
		 },
		 
		 /*校验15位或18位的身份证号码*/
		 checkIdCardNo: function(idCardNo){
		  //15位和18位身份证号码的基本校验
		  var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
		  if(!check) return false;
		  //判断长度为15位或18位 
		  if(idCardNo.length==15){
		    return idCardNoUtil.check15IdCardNo(idCardNo);
		  }else if(idCardNo.length==18){
		    return idCardNoUtil.check18IdCardNo(idCardNo);
		  }else{
		    return false;
		  }
		 },
		 
		 //校验15位的身份证号码
		 check15IdCardNo: function(idCardNo){
		   //15位身份证号码的基本校验
		   var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);  
		   if(!check) return false;
		   //校验地址码
		   var addressCode = idCardNo.substring(0,6);
		   check = idCardNoUtil.checkAddressCode(addressCode);
		   if(!check) return false;
		   var birDayCode = '19' + idCardNo.substring(6,12);
		   //校验日期码
		   return idCardNoUtil.checkBirthDayCode(birDayCode);
		 },
		 
		 //校验18位的身份证号码
		 check18IdCardNo: function(idCardNo){
		   //18位身份证号码的基本格式校验
		   var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
		   if(!check) return false;
		   //校验地址码
		   var addressCode = idCardNo.substring(0,6);
		   check = idCardNoUtil.checkAddressCode(addressCode);
		   if(!check) return false;
		   //校验日期码
		   var birDayCode = idCardNo.substring(6,14);
		   check = idCardNoUtil.checkBirthDayCode(birDayCode);
		   if(!check) return false;
		   //验证校检码  
		   return idCardNoUtil.checkParityBit(idCardNo);  
		 },
		 
		 formateDateCN: function(day){
		    var yyyy =day.substring(0,4);
		    var mm = day.substring(4,6);
		    var dd = day.substring(6);
		    return yyyy + '-' + mm +'-' + dd;
		 },
		 
		 //获取信息
		 getIdCardInfo: function(idCardNo){
		   var idCardInfo = {
		    gender:"",  //性别
		    birthday:"" // 出生日期(yyyy-mm-dd)
		   };
		  if(idCardNo.length==15){
		    var aday = '19' + idCardNo.substring(6,12);
		    idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
		    if(parseInt(idCardNo.charAt(14))%2==0){
		      idCardInfo.gender=idCardNoUtil.genders.female;
		    }else{
		      idCardInfo.gender=idCardNoUtil.genders.male;
		    }   
		  }else if(idCardNo.length==18){
		    var aday = idCardNo.substring(6,14);
		    idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
		     if(parseInt(idCardNo.charAt(16))%2==0){
		      idCardInfo.gender=idCardNoUtil.genders.female;
		    }else{
		      idCardInfo.gender=idCardNoUtil.genders.male;
		    } 
		     
		  }
		  return idCardInfo;
		 },
		  
		 /*18位转15位*/
		 getId15: function(idCardNo){
		  if(idCardNo.length==15){
		    return idCardNo;
		  }else if(idCardNo.length==18){
		    return idCardNo.substring(0,6) + idCardNo.substring(8,17); 
		  }else{
		  return null;
		  }
		 },
		  
		 /*15位转18位*/
		 getId18: function(idCardNo){
		  if(idCardNo.length==15){
		    var id17 = idCardNo.substring(0,6) + '19' + idCardNo.substring(6);
		    var parityBit = idCardNoUtil.getParityBit(id17);
		    return id17 + parityBit;
		  }else if(idCardNo.length==18){
		    return idCardNo;
		  }else{
		  return null;
		  }
		 }
};
		 
//		//身份证号码验证  
//		jQuery.validator.addMethod("idCardNo", function(value, element) { 
//		  return this.optional(element) || idCardNoUtil.checkIdCardNo(value); 
//		}, "Please specify a valid ID number."); 
//		 
//		//获取身份证信息 
//		var idCardInfo = idCardNoUtil.getIdCardInfo(idCardNo); 
//		alert(idCardInfo.gender + "|" + idCardInfo.birthday); 
		 
//		注：录入并判断数据库中是否已存在同样的身份证时 
//		(1) 若输入的是15位的身份证：先查找15位的ID是否存在，若不存在还需要将15位的身份证转成18位的身份证，仍不存在的话才可录入系统。 
//		(2) 若输入的是18位的身份证：先查找18位的ID是否存在，若不存在还需要将18位的身份证转成15位的身份证，仍不存在的话才可录入系统。 
//		如果找到对应的15位身份证，需要将15位的更新到18位。

// -------------------------------------------身份证计算END---------------------------/////////////
function isSZ(str){	
	return (/^[0-9]{1,20}$/.test(str));	
}

// 通过身份证号获取生日、年龄、性别
function IdCard(UUserCard,num){
	   if(num==1){
	       // 获取出生日期
	       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
	    return birth;
	   }
	   if(num==2){
	       // 获取性别
	       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
	           // 男
	     return "男";
	       } else {
	           // 女
	     return "女";
	       }
	   }
	   if(num==3){
	        // 获取年龄
	        var myDate = new Date();
	        var month = myDate.getMonth() + 1;
	        var day = myDate.getDate();
	        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
	        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
	            age++;
	        }
	  return age;
	 }
	}
	
	
// 输入生日计算年龄
function selectbirthday(){
	var birthday =$("#birthday").datebox('getValue');
	if(birthday.length==10){
			 var myDate = new Date();
		     var month = myDate.getMonth() + 1;
		     var day = myDate.getDate();
		     var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
		     if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
		        age++;
		     }
		     $('#age').textbox('setValue',age);
	}
}

// 输入年龄计算生日
function selectage(){
	var age =$("#age").textbox('getValue');
	if(age.length>0){
			var myDate = new Date();
			var age1 = parseInt(myDate.getFullYear()) - parseInt(age);
			var birthday = age1 + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate();
			$("#birthday").datebox('setValue',birthday);
	}
}

function setbuttondisable(){
	if(contralcustflag==0){
		$("#newbutton").attr("disabled", false);
		$("#editbutton").attr("disabled", true);
		$("#yynewbutton").attr("disabled", true);
		$('#exam_num').attr('readonly',true); 
		// dingweitype=0;
	}else if(contralcustflag==1){
		$("#newbutton").attr("disabled", true);
		$("#editbutton").attr("disabled", true);
		$("#yynewbutton").attr("disabled", false);
		$('#exam_num').attr('readonly',true); 
		// dingweitype=0;
	}else if(contralcustflag==2){
		$("#newbutton").attr("disabled", true);
		$("#editbutton").attr("disabled", false);
		$("#yynewbutton").attr("disabled", true);
		$('#exam_num').attr('readonly',false); 
		// $('#exam_num').textbox('textbox').focus();
		// $("#exam_num").textbox('textbox').select();
		// dingweitype=1;
	}	
	$("#savebutton").attr("disabled", false);
	$("#clearbutton").attr("disabled", false);
}

function gettotalinfo(){
	$.post("djtGItemCount.action", 
			{
				"exam_id":$("#exam_id").val(),
				"exam_num":$("#exam_num").val(),
				"exam_type":'G'
			}, function(jsonPost) {
				var customertotal = eval(jsonPost);
				document.getElementById("countss").firstChild.nodeValue=customertotal.counts;
				document.getElementById("yjje").firstChild.nodeValue=customertotal.totalAmt;
				document.getElementById("sjje").firstChild.nodeValue=customertotal.personAmt;
				document.getElementById("wjje").firstChild.nodeValue=customertotal.qfAmt;
			}, "json");
}


// 读取身份证
	var conreadcard=0;
	function djtreadcard_sfz() {
	    if(conreadcard==0){
	    	var sfz_div_code = $("#sfz_div_code").val();
	    	var data=readCardSFZ(sfz_div_code);    	
	        if(data.error_flag=="0"){
	        	var certno=data.certno;
	        	if(certno.length==18){
	        		if($("#exam_id").val()<=0)
	        		{	        		
	        		setexaminfoall(data,certno);
	        		conreadcard=0;
	        	    }else{
	        		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			   	    $("body").prepend(str);
				    $.ajax({
						url : 'djtexamInfoforIdNum.action',
						data : {
							    exam_id:$("#exam_id").val(),
							    exam_num:$('#exam_num').val(),
							    id_num:certno,
				                customer_id:$("#customer_id").val()
								},
								type : "post",// 数据发送方式
								success : function(text) {
									$(".loading_div").remove();
									conreadcard=0;
									if (text == '1') {
										// 可以正常覆盖
										if(data.sex!=document.getElementsByName("sex")[0].value){
											$.messager.confirm('提示信息','身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',function(r){
											 	if(r){
													setexaminfoall(data,certno);
											 	}
										 });
										}else{
											setexaminfoall(data,certno);
										}
									  // 可以正常覆盖---------------------------结束--------------------------------------
									}else if (text == '2'){
										// 提示是否需要覆盖
										 $.messager.confirm('提示信息','系统里存在相同身份证号，你确定要用此身份证信息覆盖吗？',function(r){
											 	if(r){
											 		if(data.sex!=document.getElementsByName("sex")[0].value){
														$.messager.confirm('提示信息','身份证和当前录入人员性别不一致，强制修改可能引起缴费项目混乱，你确定要用此身份证信息覆盖吗？',function(r){
														 	if(r){
																setexaminfoall(data,certno);
														 	}
													 });
													}else{
														setexaminfoall(data,certno);
													}
											 	}
										 });
									}								
								},
								error : function() {
									conreadcard=0;
									$(".loading_div").remove();
									$.messager.alert("操作提示", "操作错误", "error");					
								}
							});
	        	}	
	        	}else{
	        		$.messager.alert("操作提示", "读取身份证号码错误", "error");	
	        	}
	        }else{
//	        	$.messager.alert("操作提示", "读取身份证失败", "error");	
	        	$.messager.alert("操作提示", data.error_msg, "error");
	        }
	        
	    }
}

	var data,certno;
	function setexaminfoall(data,certno){
		$('#id_num').val(certno);
		$("#custname").val(data.name);
		$("#sex").textbox('setValue',data.sex);
		$("#address").textbox('setValue',data.address);
		$("#picture_Path").val("");
		var object_minzhu = $('#minzhu').combobox('getData');
        for(var i=0;i<object_minzhu.length;i++) {
        	if (object_minzhu[i].name.trim() == data.nation) {
        		$('#minzhu').combobox('setValue', object_minzhu[i].id);
        		break;
        	}
        }
		var bords = data.birth;
		if(bords.length==8){
			var birthday= bords.substring(0,4)+"-"+bords.substring(4,6)+"-"+bords.substring(6,8);
			$('#birthday').datebox('setValue',birthday);						
				 var myDate = new Date();
			     var month = myDate.getMonth() + 1;
			     var day = myDate.getDate();
			     var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
			     if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
			        age++;
			     }
			     $('#age').textbox('setValue',age);
		}else if(bords.length==10){
			var birthday= bords;
			$('#birthday').datebox('setValue',birthday);						
				 var myDate = new Date();
			     var month = myDate.getMonth() + 1;
			     var day = myDate.getDate();
			     var age = myDate.getFullYear() - birthday.substring(0, 4) - 1;
			     if (birthday.substring(5, 7) < month || birthday.substring(5, 7) == month && birthday.substring(8, 10) <= day) {
			        age++;
			     }
			     $('#age').textbox('setValue',age);
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
	    $.ajax({
			url : 'djtuploadPicexamInfo.action',
			data : {
				    others:data.bmpFileData
					},
					type : "post",// 数据发送方式
					success : function(text) {
						$(".loading_div").remove();
						conreadcard=0;
						if (text.split("&")[0] == 'ok') {
							$("#picture_Path").val(text.split("&")[1]);
							document.getElementById("exampic").src="getdjtexamPhoto.action?others="+text.split("&")[1]+"&"+new Date().getTime();
						}									
					},
					error : function() {
						conreadcard=0;
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
	}
	
	// 获取菜单
	function f_getDatadic() {
		var sextype = '<s:property value="model.sex"/>';
		$('#sex').combobox({
			url : 'getDatadis.action?com_Type=XBLX2',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == sextype) {
						$('#sex').combobox('setValue', data[i].id);
						break;
					} else {
						$('#sex').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		  
		var nationtype = '<s:property value="model.nation"/>';
		$('#minzhu').combobox({
			url :'getDatadis.action?com_Type=MZLX2',
			editable : true, //不可编辑状态
			cache : false,
			panelHeight:200,
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == nationtype) {
						$('#minzhu').combobox('setValue', data[i].id);
						break;
					} else {
						$('#minzhu').combobox('setValue', data[0].id);
					}
				}	
			}
		});
		
		
		var customerTypetype = '<s:property value="model.customerType"/>';
		$('#customerType').combobox({
			url : 'getDatadis.action?com_Type=SFLX2',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == customerTypetype) {
						$('#customerType').combobox('setValue', data[i].id);
						break;
					} else {
						$('#customerType').combobox('setValue', data[0].id);
					}
				}
			}
		});
		var occusectoridtype = '<s:property value="model.occusectorid"/>';
		$('#occusectorid').combobox({
			url : 'getDatadis.action?com_Type=HY',
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == occusectoridtype) {
						$('#occusectorid').combobox('setValue', data[i].id);
						break;
					} else {
						$('#occusectorid').combobox('setValue', data[0].id);
					}
				}
			}
		});
		var customer_typetype = '<s:property value="model.customer_type"/>';
		$('#tjlx').combobox({
			url : 'getDatadis.action?com_Type=TJLX',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == customer_typetype) {
						$('#tjlx').combobox('setValue', data[i].id);
						break;
					} else {
						$('#tjlx').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		// 费用类别
		var chargingTypetype = '<s:property value="model.chargingType"/>';
		$('#sftype').combobox({
			url : 'getDatadis.action?com_Type=SFTYPE',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == chargingTypetype) {
						$('#sftype').combobox('setValue', data[i].id);
						break;
					} else {
						$('#sftype').combobox('setValue', data[0].id);
					}
				}
			}
		});

		var customer_type_idtype = '<s:property value="model.customer_type_id"/>';
		$('#rylb').combobox({
			url : 'getDatadis.action?com_Type=RYLB',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == customer_type_idtype) {
						$('#rylb').combobox('setValue', data[i].id);
						break;
					} else {
						$('#rylb').combobox('setValue', data[0].id);
					}
				}
			}
		});

		var is_Marriagetype = '<s:property value="model.is_Marriage"/>';
		$('#is_Marriage').combobox({
			url : 'getDatadis.action?com_Type=HFLX',
			editable : false, // 不可编辑状态
			cache : false,
			panelHeight : 'auto',// 自动高度适合
			valueField : 'id',
			textField : 'name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == is_Marriagetype) {
						$('#is_Marriage').combobox('setValue', data[i].id);
						break;
					} else {
						$('#is_Marriage').combobox('setValue', data[0].id);
					}
				}
			}
		});
	}

	function checkinput() {	
		/*if(!jiuzhengkayanzhen.getvalidatevilit() && $("#visit_no_h").val()!=$("#visit_no_j").val()){
			$.messager.alert("提示信息","就诊卡冲突!","error");
			return;
		}*/
		if (document.getElementById("custname").value == '') {
			alert('姓名不能为空！');
			return false;
		}else if (($('#s_idtype').combobox('getText').trim()=="身份证")&&(document.getElementById("id_num").value.length!= '')&&(document.getElementById("id_num").value.length!= 18&&document.getElementById("id_num").value.length!= 15)) {
			alert('身份证号位数错误！');
			return false;
		} /*else if (($('#s_idtype').combobox('getText').trim()=="身份证")&&(document.getElementById("id_num").value.length!= '')&&(!isSZ(document.getElementById("id_num").value.substring(0,17)))) {
			alert('身份证号格式错误！');
			return false;
		} */else if (document.getElementById("age").value == '') {
			alert('年龄不能为空！');
			return false;
		}if (!(isSZ(document.getElementById("age").value))) {
			alert('年龄格式错误！');
			return false;
		}  else if (Number(document.getElementById("age").value)>120) {
			alert('年龄不能超出120岁！');
			return false;
		} else if (Number(document.getElementById("age").value)<=0) {
			alert('年龄不能小于1岁！');
			return false;
		}else if (document.getElementsByName("sex")[0].value=='') {
			alert('性别不能为空！');
			return false;
		}else if ($("#birthday").datebox('getValue')=='') {
			alert('出生日期不能为空！');
			return false;
		}
		return true;
	}

	/**
	 * 保存修改
	 */
	 var contratype;
	function addcustomerdo() {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'djteditcustomerdo.action',
				data : {
							customer_id: $("#customer_id").val(),
							custname :document.getElementById("custname").value,
							id_num : document.getElementById("id_num").value,
							exam_id :$("#exam_id").val(),
							exam_num : document.getElementById("exam_num").value,
							arch_num :document.getElementById("arch_num").value,							
							age :document.getElementById("age").value,
							sex : document.getElementsByName("sex")[0].value,
							is_marriage : document.getElementsByName("is_Marriage")[0].value,
							birthday:$("#birthday").datebox('getValue'),	
							customer_type:document.getElementsByName("tjlx")[0].value,
							nation:document.getElementsByName("minzhu")[0].value,
							customer_type_id:document.getElementsByName("rylb")[0].value,
							chargingType:document.getElementsByName("sftype")[0].value,
							customerType:document.getElementsByName("customerType")[0].value,
							occusectorid:document.getElementsByName("occusectorid")[0].value,
							register_date:$("#register_date").val(),
							setimes:$("#setimes").val(),	 	
							tel : $("#addtel").val(),
							email : $("#email").val(),
							_level :'',
							position : $("#addposition").val(),
							others:$("#others").val(),
							remark:$("#remark").val(),
							address:$("#address").val(),
							examstatus:$("#examstatus").val(),
							company:$("#company").val(),
							introducer:$("#introducer").combobox("getText"),
							employeeID:$("#employeeID").val(),
							
							mc_no:$("#mc_no").val(),// 就诊卡号
							patient_id:$("#patient_id").val(),// 病人id
							visit_date:$("#visit_date").val(),// 就诊日期
							visit_no:$("#visit_no_j").val(),// 就诊号
							clinic_no:$("#clinic_no").val(),// 门诊号
														
							past_medical_history:$("#past_medical_history").val(),
							is_after_pay:$('input[name="is_after_pay"]:checked').val(),
							picture_Path:$("#picture_Path").val(),
							flags:contralcustflag,
						    idtype:$('#s_idtype').combobox('getValue'),//证件类型
						    idtypename:$('#s_idtype').combobox('getText')//证件类型名称
						},
						type : "post",// 数据发送方式
						success : function(text) {
							 $(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", "操作成功！","");
								f_findcustomerone(text.split("-")[1],0);
								// $('#exam_num').textbox('textbox').focus();
								// $("#exam_num").textbox('textbox').select();
							} else {
								if(document.getElementById("custname").value.indexOf("*")!=-1   || document.getElementById("id_num").value.indexOf("*")!=-1){
									$.messager.alert("操作提示", "此客户安全级别较高,不允许修改", "error");
								}else{
									$.messager.alert("操作提示", text, "error");
								}
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	}
	
	// 手工挂号
	function customerguahao(){
		 if($("#exam_id").val()>0){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
					url : 'djtGcustomerguahao.action',
					data : {
						exam_id: $("#exam_id").val(),
						exam_num:$('#exam_num').val()
						},
						type : "post",// 数据发送方式
						success : function(text) {
							    $(".loading_div").remove();
								if (text.split("-")[0] =='ok') {
									f_findcustomerone(text.split("-")[1],1);
								}else {
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						}); 
		 }else{
			 alert("操作提示","无效体检信息", "error");
		 }
	}
	
	function findcustomer_custname(){
		if($("#custname").val()!=''){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
					url : 'countcustomer.action',
					data : {
						custname : $("#custname").val()
						},
						type : "post",// 数据发送方式
						success : function(text) {
							    $(".loading_div").remove();
								if (Number(text.split("-")[0]) ==1) {
									f_findcustomerone(text.split("-")[1],0);
								}else if (Number(text.split("-")[0]) >1) {
									f_custdingweishow();
								}  else {
									$.messager.alert("操作提示","无效查询条件", "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						}); 
		 }else{
			 clearcustomer();
			 alert("无效姓名");
		 }
	}
	
	function findcustomer_idnum(){
		if($("#id_num").val().length==18){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
					url : 'countcustomerForidnum.action',
					data : {
						id_num : $("#id_num").val()
						},
						type : "post",// 数据发送方式
						success : function(text) {
							    $(".loading_div").remove();
								if (Number(text.split("-")[0])>0) {
									f_findcustomeroneidnum(text.split("-")[1],0);
								} else {
									$.messager.alert("操作提示","无效查询条件", "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						}); 
		 }else{
			 clearcustomer();
			 alert("无效身份编号");
		 }
	}
	
	function findcustomer_examnum(){
		 if($("#exam_num").val()!=''){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
					url : 'countcustomer.action',
					data : {
						exam_num: $("#exam_num").val()
						},
						type : "post",// 数据发送方式
						success : function(text) {
							    $(".loading_div").remove();
								if (Number(text.split("-")[0]) ==1) {
									f_findcustomerone(text.split("-")[1],0);
								}else if (Number(text.split("-")[0]) >1) {
									f_custdingweishow();
								}  else {
									$.messager.alert("操作提示","无效查询条件", "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						}); 
		 }else{
			 clearcustomer();
			 alert("无效体检编号");
		 }
	}

	// 查询出多条时候弹出窗口选择
	function f_custdingweishow(){ 
		 var scustname=$("#custname").val();
		 scustname = encodeURI(encodeURI(scustname));
		 var strurl='findcustOneDingwei.action?dingweitype='+contralcustflag+'&exam_num='+$("#exam_num").val()+'&custname='+scustname+'&id_num='+$("#id_num").val();
		 $("#dlg-show").dialog({
		 		title:'条件定位',
		 		href:strurl
		 	});
		 	$("#dlg-show").dialog('open');
	 }
	
	
	var selectexam_id;
	var brushtatle=0;
	function f_findcustomerone(selectexam_id,brushtatle){
		$.post("getDjtExamOneShow.action", 
		{
			"exam_num" : selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);			
			setCustomer(customer,brushtatle);
		}, "json");
	}
	
	function f_findcustomeroneidnum(selectexam_id,brushtatle){
		$.post("getDjtExamOneShowidnum.action", 
		{
			"customer_id" : selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);			
			setCustomeridnum(customer,brushtatle);
		}, "json");
	}
	
	function f_findcustomerone_select(selectexam_id){
		$.post("getDjtExamOneShow.action", 
		{
			"exam_num" : selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);			
			setCustomer_select(customer);
		}, "json");
	}
	
	function setCustomer_select(cumtomersd)
	{
		// if(cumtomersd.exam_type=='T'){
		// $.messager.alert("操作提示","团检人员不能在此页编辑", "error");
		// }else{
		//$('$s_idtype').val('value',cumtomersd.idtype);
		
		  var val = $('#s_idtype').combobox('getData');
			 
		  
		  $('#s_idtype').combobox({
		 		panelHeight:'auto',
		 		url:"getDatadis.action?com_Type="+"ZJLX",
		 		valueField:'id',
		 		textField:'name',
		 		onLoadSuccess : function(){//下拉框默认选择
				 	       var val = $('#s_idtype').combobox('getData');
				     	      for(var i = 0 ; i < val.length ; i++ ){
				     	    	  if(val[i].id==cumtomersd.idtype){
				     	    		 $("#s_idtype").combobox('setValue',val[i].id);
				     	    	  } else {
				     	    		 $("#s_idtype").combobox('setValue',val[0].id);
				     	    	  }
				     	      }
 	             }
		})
	//$('#s_idtype').combobox('setValue',cumtomersd.idtype);
	$('#visit_no_j').val(cumtomersd.visit_no);
		  $('#visit_no_h').val(cumtomersd.visit_no);
		$("#exam_id").val(0);
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").textbox('setValue',cumtomersd.arch_num);	
		// $("#exam_num").textbox('setValue',cumtomersd.exam_num);
		$('#id_num').val(cumtomersd.id_num);
		$('#custname').val(cumtomersd.user_name);
		$('#age').textbox('setValue', cumtomersd.age);
		$('#email').textbox('setValue', cumtomersd.email);
		$('#addtel').textbox('setValue', cumtomersd.addtel);
		$('#others').textbox('setValue', cumtomersd.others);
		// $('#statuss').textbox('setValue', cumtomersd.statuss);
		// $("#examstatus").val(cumtomersd.status)
		$('#addtel').textbox('setValue', cumtomersd.phone);	
		// $('#register_date').val(cumtomersd.register_date);
		// $('#exam_times').val(cumtomersd.exam_times);

		// $("#register_dates").textbox('setValue',cumtomersd.register_date);
		// $("#register_times").textbox('setValue',cumtomersd.exam_times);
		// $("#join_dates").textbox('setValue',cumtomersd.join_date);
		
		$('#address').textbox('setValue', cumtomersd.address);
		$('#remark').textbox('setValue', cumtomersd.remarke);
		$('#introducer').textbox('setValue', cumtomersd.introducer);
	    $("#birthday").datebox('setValue',cumtomersd.birthday);
		// $('#company').textbox('setValue', cumtomersd.company);
		// $('#past_medical_history').textbox('setValue',
		// cumtomersd.past_medical_history);
		// $("#picture_Path").val(cumtomersd.picture_Path);

		 for(var i=0;i<objectsex.length;i++) {
	        	if (objectsex[i].id == cumtomersd.sex) {
					$('#sex').combobox('setValue', objectsex[i].id);
					break;
				}
	        }  

        for(var i=0;i<objectminzhu.length;i++) {
        	if (objectminzhu[i].id == cumtomersd.nation) {
				$('#minzhu').combobox('setValue', objectminzhu[i].id);
				break;
			}
        } 

        for(var i=0;i<objectcustomer_type.length;i++) {
        	if (objectcustomer_type[i].id == cumtomersd.customer_type) {
				$('#tjlx').combobox('setValue', objectcustomer_type[i].id);
				break;
			}
        } 

        for(var i=0;i<objectchargingType.length;i++) {
        	if (objectchargingType[i].id == cumtomersd.chargingType) {
				$('#sftype').combobox('setValue', objectchargingType[i].id);
				break;
			}
        } 

        for(var i=0;i<objectcustomerType.length;i++) {
        	if (objectcustomerType[i].id == cumtomersd.customerType) {
				$('#customerType').combobox('setValue', objectcustomerType[i].id);
				break;
			}
        } 
        var objectoccusectorid = $('#occusectorid').combobox('getData');
        for(var i=0;i<objectoccusectorid.length;i++) {
        	if (objectoccusectorid[i].id == cumtomersd.occusectorid) {
				$('#occusectorid').combobox('setValue', objectoccusectorid[i].id);
				break;
			}
        } 

        for(var i=0;i<objectcustomer_type_id.length;i++) {
        	if (objectcustomer_type_id[i].id == cumtomersd.customer_type_id) {
				$('#rylb').combobox('setValue', objectcustomer_type_id[i].id);
				break;
			}
        } 

        for(var i=0;i<objectis_Marriage.length;i++) {
        	if (objectis_Marriage[i].id == cumtomersd.is_marriage) {
				$('#is_Marriage').combobox('setValue', objectis_Marriage[i].id);
				break;
			}
        }
		// }
        // $('#exam_num').textbox('textbox').focus();
		// $("#exam_num").textbox('textbox').select();
		setselectListGrid();
		djtcustChangItemListGrid();
		gettotalinfo();
	}
	
	var cumtomersd;
	
	function setCustomer(cumtomersd,brushtatle)
	{
		if(cumtomersd.exam_type=='T'){
			 $.messager.alert("操作提示","团检人员不能在此页编辑", "error");
		}else{		
			  var val = $('#s_idtype').combobox('getData');
			 
			  
			  $('#s_idtype').combobox({
			 		panelHeight:'auto',
			 		url:"getDatadis.action?com_Type="+"ZJLX",
			 		valueField:'id',
			 		textField:'name',
			 		onLoadSuccess : function(){//下拉框默认选择
					 	       var val = $('#s_idtype').combobox('getData');
					     	      for(var i = 0 ; i < val.length ; i++ ){
					     	    	  if(val[i].id==cumtomersd.idtype){
					     	    		 $("#s_idtype").combobox('setValue',val[i].id);
					     	    	  } else {
					     	    		 $("#s_idtype").combobox('setValue',val[0].id);
					     	    	  }
					     	      }
	 	             }
			})
		//$('#s_idtype').combobox('setValue',cumtomersd.idtype);
		$('#visit_no_j').val(cumtomersd.visit_no);
			  $('#visit_no_h').val(cumtomersd.visit_no);
		$("#exam_id").val(cumtomersd.id);
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").textbox('setValue',cumtomersd.arch_num);	
		$("#exam_num").val(cumtomersd.exam_num);
		$("#c_exam_num").val(cumtomersd.exam_num);
		$('#id_num').val(cumtomersd.id_num);
		$('#custname').val(cumtomersd.user_name);
		$('#age').textbox('setValue', cumtomersd.age);
		$('#email').textbox('setValue', cumtomersd.email);
		$('#addtel').textbox('setValue', cumtomersd.addtel);
		$('#others').textbox('setValue', cumtomersd.others);
		$('#statuss').textbox('setValue', cumtomersd.statuss);	
		$("#examstatus").val(cumtomersd.status)		  
		$('#addtel').textbox('setValue', cumtomersd.phone);	
		$('#register_date').val(cumtomersd.register_date);
		$('#exam_times').val(cumtomersd.exam_times);
		document.getElementById("examcount").innerHTML="您是第"+cumtomersd.examcount+"次体检";
		
		$("#employeeID").val(cumtomersd.employeeID);		
		if(cumtomersd.vipsigin=="1")
	       {
	         $("#vipsigin").show();
	       }else{
	    	 $("#vipsigin").hide(); 
	       }
		$("#register_dates").textbox('setValue',cumtomersd.register_date);
		$("#register_times").textbox('setValue',cumtomersd.exam_times);
		$("#join_dates").textbox('setValue',cumtomersd.join_date);
		
		$("#visit_date").val(cumtomersd.visit_date);
		$("#visit_no_j").val(cumtomersd.visit_no);
		$("#visit_no_h").val(cumtomersd.visit_no);
		$("#clinic_no").val(cumtomersd.clinic_no);
		$("#patient_id").val(cumtomersd.patient_id);
		$("#mc_no").val(cumtomersd.mc_no);
		
		$('#address').textbox('setValue', cumtomersd.address);
		$('#remark').textbox('setValue', cumtomersd.remarke);
		$('#introducer').textbox('setValue', cumtomersd.introducer);
	    $("#birthday").datebox('setValue',cumtomersd.birthday),
		$('#company').textbox('setValue', cumtomersd.company);
		$('#past_medical_history').textbox('setValue', cumtomersd.past_medical_history);
		$("#picture_Path").val(cumtomersd.picture_Path);
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+cumtomersd.picture_Path+"&"+new Date().getTime();
		
		$('[name="is_after_pay"]:radio').each(function() {
            if (this.value == cumtomersd.is_after_pay){   
               this.checked = true;   
            }     
         });   

		var objectsex = $('#sex').combobox('getData');
		 for(var i=0;i<objectsex.length;i++) {
	        	if (objectsex[i].id == cumtomersd.sex) {
					$('#sex').combobox('setValue', objectsex[i].id);
					break;
				}
	        }  

		var objectminzhu = $('#minzhu').combobox('getData');
        for(var i=0;i<objectminzhu.length;i++) {
        	if (objectminzhu[i].id == cumtomersd.nation) {
				$('#minzhu').combobox('setValue', objectminzhu[i].id);
				break;
			}
        } 

        var objectcustomer_type = $('#tjlx').combobox('getData');
        for(var i=0;i<objectcustomer_type.length;i++) {
        	if (objectcustomer_type[i].id == cumtomersd.customer_type) {
				$('#tjlx').combobox('setValue', objectcustomer_type[i].id);
				break;
			}
        } 
        
        var objectchargingType = $('#sftype').combobox('getData');
        for(var i=0;i<objectchargingType.length;i++) {
        	if (objectchargingType[i].id == cumtomersd.chargingType) {
				$('#sftype').combobox('setValue', objectchargingType[i].id);
				break;
			}
        }         
        
        var objectcustomerType = $('#customerType').combobox('getData');
        for(var i=0;i<objectcustomerType.length;i++) {
        	if (objectcustomerType[i].id == cumtomersd.customerType) {
				$('#customerType').combobox('setValue', objectcustomerType[i].id);
				break;
			}
        } 
		
        var objectcustomer_type_id = $('#rylb').combobox('getData');
        for(var i=0;i<objectcustomer_type_id.length;i++) {
        	if (objectcustomer_type_id[i].id == cumtomersd.customer_type_id) {
				$('#rylb').combobox('setValue', objectcustomer_type_id[i].id);
				break;
			}
        } 
        var objectoccusectorid = $('#occusectorid').combobox('getData');
        for(var i=0;i<objectoccusectorid.length;i++) {
        	if (objectoccusectorid[i].id == cumtomersd.occusectorid) {
				$('#occusectorid').combobox('setValue', objectoccusectorid[i].id);
				break;
			}
        }

        var objectis_Marriage = $('#is_Marriage').combobox('getData');
        for(var i=0;i<objectis_Marriage.length;i++) {
        	if (objectis_Marriage[i].id == cumtomersd.is_marriage) {
				$('#is_Marriage').combobox('setValue', objectis_Marriage[i].id);
				break;
			}
        }
		 }
        // $('#exam_num').textbox('textbox').focus();
		// $("#exam_num").textbox('textbox').select();
		if(brushtatle==0){
		setselectListGrid();
		djtcustChangItemListGrid();
		gettotalinfo();
		}
	}
	
	
	function setCustomeridnum(cumtomersd,brushtatle)
	{
		clearcustomer();
		$('#visit_no_j').val(cumtomersd.visit_no);
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").textbox('setValue',cumtomersd.arch_num);
		$('#id_num').val(cumtomersd.id_num);
		$('#custname').val(cumtomersd.user_name);		
		$('#address').textbox('setValue', cumtomersd.address);		
	    $("#birthday").datebox('setValue',cumtomersd.birthday);
		

		var objectsex = $('#sex').combobox('getData');
		 for(var i=0;i<objectsex.length;i++) {
	        	if (objectsex[i].id == cumtomersd.sex) {
					$('#sex').combobox('setValue', objectsex[i].id);
					break;
				}
	        }  

	}
	
	function clearcustomer(){
		
		$('#visit_no_j').val('');
		$('#exam_num').val('');
		$('#c_exam_num').val('');
		$('#id_num').val('');
		$('#custname').val('');
		$('#age').textbox('setValue','');
		$('#email').textbox('setValue','');
		$('#addtel').textbox('setValue','');
		$('#others').textbox('setValue','');
		$('#statuss').textbox('setValue','');	
		$('#addtel').textbox('setValue', '');	
		$("#birthday").datebox('setValue','');
		document.getElementById("examcount").innerHTML="您是第0次体检";
		$("#register_dates").textbox('setValue','');
		$("#register_times").textbox('setValue','');
		$("#join_dates").textbox('setValue','');	
		$('#exam_times').val("");
		$('#register_date').val("");		
		$("#examstatus").val('Y');
		$("#exam_id").val('0');
		$("#arch_num").textbox('setValue','');	
		$("#customer_id").val('0');			
	    	 $("#vipsigin").hide(); 
		$("#employeeID").val("");
		$("#mc_no").val('');
		$("#patient_id").val('');
		$("#visit_date").val('');
		$("#visit_no").val('');
		$("#clinic_no").val('');		
		$("#visit_no_j").val('');		
		$("#visit_no_h").val('');		
		$('#address').textbox('setValue','');
		$('#remark').textbox('setValue','');
		$('#introducer').textbox('setValue','');
		$('#company').textbox('setValue','');
		$('#past_medical_history').textbox('setValue', '');
		$("#picture_Path").val('');
		document.getElementById("exampic").src="../../themes/default/images/user.png";
		 var oldisafterpay=$("#oldis_after_pay").val();
			$('[name="is_after_pay"]:radio').each(function() {
		        if (this.value == oldisafterpay){   
		           this.checked = true;   
		        }     
		     });  
		
		 var datasex = $('#sex').combobox('getData');
         if (datasex.length > 0) {
             $('#sex').combobox('select', datasex[0].id);
         } 

		var objectminzhu = $('#minzhu').combobox('getData');
		if (objectminzhu.length > 0) {
            $('#minzhu').combobox('select', objectminzhu[0].id);
        } 

        var objectcustomer_type = $('#tjlx').combobox('getData');
        if (objectcustomer_type.length > 0) {
            $('#tjlx').combobox('select', objectcustomer_type[0].id);
        } 
		
        var objectchargingType = $('#sftype').combobox('getData');
        if (objectchargingType.length > 0) {
            $('#sftype').combobox('select', objectchargingType[0].id);
        } 
        
        var objectcustomerType = $('#customerType').combobox('getData');
        if (objectcustomerType.length > 0) {
            $('#customerType').combobox('select', objectcustomerType[0].id);
        } 
		
        var objectcustomer_type_id = $('#rylb').combobox('getData');
        if (objectcustomer_type_id.length > 0) {
            $('#rylb').combobox('select', objectcustomer_type_id[0].id);
        } 
        var objectoccusectorid = $('#occusectorid').combobox('getData');
        if (objectoccusectorid.length > 0) {
            $('#occusectorid').combobox('select', objectoccusectorid[0].id);
        } 
        var objectis_Marriage = $('#is_Marriage').combobox('getData');
        if (objectis_Marriage.length > 0) {
            $('#is_Marriage').combobox('select', objectis_Marriage[0].id);
        }         
        setselectListGrid();
        djtcustChangItemListGrid();
        gettotalinfo();
	}
	
	
	/**
	 * 显示分组套餐信息
	 */
	function setselectListGrid() {
		var model = {
			"exam_id" :document.getElementById("exam_id").value,
			"exam_num":document.getElementById("exam_num").value	
		};
		$("#djtGselectsetlist").datagrid({
			url : 'exam_tclistshow.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			
			// pageSize: 8,//每页显示的记录条数，默认为10
			pageList : [ 10, 20, 30, 40, 10 ],// 可以设置每页记录条数的列表
			columns : [[ {
				align : "center",
				field : "fxfz",
				title : "删除",
				width : 15,
				"formatter" : f_djtdellset
			}, {
				align : 'center',
				field : 'set_num',
				title : '套餐编码',
				width : 15
			}, {
				align : 'center',
				field : 'set_name',
				title : '套餐名称',
				width : 45
			}, {
				align : 'center',
				field : 'sex',
				title : '适用性别',
				width : 20
			}, {
				align : 'center',
				field : 'set_discount',
				title : '套餐折扣率',
				width : 30
			}, {
				align : 'center',
				field : 'set_amount',
				title : '套餐金额',
				width : 20
			} ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#djtGselectsetlist").datagrid("hideColumn", "set_discount"); // 设置隐藏列
					$("#djtGselectsetlist").datagrid("hideColumn", "set_amount"); // 设置隐藏列
				}
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
		});
	}

	/**
	 * 删除套餐信息
	 * 
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	function f_djtdellset(val, row) {
		    return '<a href=\"javascript:djtdeletetc(\''
				+ row.set_num
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}

	/**
	 * 
	 */
	function djtdeletetc(userid){
	    if($("#exam_num").val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else if((userid=='')||(userid.length<=0)){
	 		$.messager.alert("操作提示", "选择项目无效","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否确定删除选中套餐？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'djtcustomersetdelshow.action',
				data : {
					exam_id : $("#exam_id").val(),
					exam_num:$('#exam_num').val(),
			        ids:userid
				    },
						type : "post",// 数据发送方式
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								setselectListGrid();
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
	 	}
	}

	/**
	 * 显示体检项目套餐信息
	 */
	var djtexam_id; 
	function djtcustChangItemListGrid() {
		var model = {"exam_id" :$("#exam_id").val(),"exam_num":$("#exam_num").val()};
		$("#djtGselectItemlist").datagrid({
			url : 'djtcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			toolbar:'#toolbar',
			// pageSize: 8,//每页显示的记录条数，默认为10
			pageList : [ 10, 20, 30, 40, 10 ],// 可以设置每页记录条数的列表
			columns : [[ {field:'ck',checkbox:true }, 
                         {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
			             {align : 'center',field : 'item_amount',title : '原金额',	width : 10},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 10},
			             {align : 'center',field : 'itemnum',title : '数量',	width : 10},
			             {align : 'center',field : 'is_new_added',title : '次数',	width : 10},
			             {align : 'center',field : 'amount',title : '应收额',	width : 10},
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 15}, 
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 15}, 
			             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 15}, 
			             {align : 'center',field : 'is_applications',title : '接口标识',	width : 15},
			             {align : 'center',field : 'his_req_statuss',title : '缴费申请',	width : 10}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#djtGselectItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列
				$("#djtGselectItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#djtGselectItemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
					$("#djtGselectItemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
				}
			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }    
		    },
		    singleSelect : false,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000,
			toolbar:toolbar
		});
	}
	
	/**
	 * 定义工具栏
	 */
	 var toolbar=[{
			text:'加项',
			width:60,
			height:20,
			iconCls:'icon-add',
		    handler:function(){
		    	djtaddcusItem();
		    }
		},{
			text:'减项',
			width:60,
			height:20,
			iconCls:'icon-cancel',
		    handler:function(){
		    	djtdelcusItem();
		    }
		},{
			text:'发(lis/pacs)申请',
			width:140,
			height:20,
			iconCls:'icon-check',
			handler:function(){
				reApplydjd();
			}
		},{
			text:'撤销申请',
			width:90,
			height:20,
			iconCls:'icon-cancel',
			handler:function(){
				delApplydjd();
		    }
		},{
			text:'收费请求',
			width:90,
			height:20,
			iconCls:'icon-check',
			handler:function(){
				rehisdjd();
		    }
		},{
			text:'刷新',
			width:70,
			height:20,
			iconCls:'icon-check',
			handler:function(){
				djtcustChangItemListGrid();
				gettotalinfo();
		    }
		}
// ,{
// text:'健康卡缴费',
// width:100,
// height:20,
// iconCls:'icon-check',
// handler:function(){
// if($("#exam_num").val() == '' || $("#exam_id").val() == ''){
// $.messager.alert('提示信息', "请先登记或查询体检人员!",'error');
// return;
// }
// var
// url='getHealthCardChargePage.action?exam_num='+$("#exam_num").val()+'&exam_id='+$("#exam_id").val();
// newwin = window.open("", "健康卡缴费",
// "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
// newwin.moveTo(0,0);
// newwin.resizeTo(screen.width,screen.height-30);
// newwin.location = url;
// }
// }
		];
	
	//
	function printdjdbar(){
		if($("#exam_num").val().length>0){
			var ids =$("#exam_num").val();
			var falgs='1';
    	    daoyindan_point(ids,falgs);
		}else{
			$.messager.alert("操作提示", "体检人员无效","error");
		}		
	}
	
	function reprintdjd(){
		if($("#exam_num").val().length>0){
			var ids = "";
	    	var checkedItems = $('#djtGselectItemlist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.item_code);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请先选择体检人员对应的缴费项目","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	  if(ids.length>=1){
			 		 printDJD_exe($("#exam_num").val()+" "+ids);
			 	   	}else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员的收费项目","error");
			 	   	}
	    	}
		}else{
			$.messager.alert("操作提示", "体检人员无效","error");
		}	
	}
	
	function printdjd(){
		if($("#exam_num").val().length>0){
			var ids =$("#exam_num").val();
			var falgs='0';
			daoyindan_point(ids);
			// clearcustomer();
		}else{
			$.messager.alert("操作提示", "体检人员无效","error");
		}	
		
	}
	
	function printbarall(){
		if($("#exam_num").val().length>0){
			var ids =$("#exam_num").val();
			if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
				 var exeurl =$("#barexeurl").val() + " barcode "+ids+" * *"; // 打印档案号
			 }else{
				 var exeurl =$("#barexeurl").val() + " barcode "+ids+" *"; 
			 }
			 $.ajax({
					url : 'updateSampleExamEetail.action',
					data : {
						ids : ids
					    },
							type : "post",// 数据发送方式
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {
									$.ajax({
										url : 'updateBarcodePrintStatus.action',
										data : {ids:"'"+ids+"'"},
										type : "post",// 数据发送方式
										success : function(text) {
											RunExe(exeurl);	
										},
										error : function() {
											$.messager.alert("操作提示", "操作错误", "error");					
										}
									});
								} else {
									$.messager.alert("操作提示", text, "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});		
		}else{
			$.messager.alert("操作提示", "体检人员无效","error");
		}
	}
	
	function RunExe(strPath) {
		try {
			var objShell = new ActiveXObject('wscript.shell');
			objShell.Run(strPath);
			objShell = null;
		} catch (e) {
			$.messager.alert("操作提示", '找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.',"error");
		}
	}
	
	 function daoyindan_point(barids,falgs) {
		 if($("#hansidjdflag").val()==1){
			doURLPDFPrint_ireport(barids);				 
		 }else{
			 printDJD_exe(barids);// 打印条码
		 }
		 if(falgs=='1'){
			 printbarall();// 打印条码
		 }
		 clearcustomer();
	 }
	 
	// 新版本打印导检单
	 function printDJD_exe(barids){
	     var exeurl =$("#djdexeurl").val() + " guid "+barids+"";
	     $.ajax({
				url : 'updateGuidePrintStatus.action',
				data : {ids:"'"+barids+"'"},
				type : "post",// 数据发送方式
				success : function(text) {
					RunExe(exeurl);
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
	 }
	
	 function doURLPDFPrint_ireport(idss){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
			$.ajax({
				url:'getDjDpdf.action',
				data:{
					ids:idss,
					frandom:Math.random()
					},
				type:"POST",
				complete:function(res,status){
					if(status=="success"){
						var resText=res.responseText;
						if(resText=="timeout"){
							alert("连接超时");
							}else{
								var filesurl=resText.split("-")[1];
								 var pdf = document.getElementById("createPDF");
								    if (pdf != undefined && pdf != null) {// 判断pdf对象是否存在，如果存在就删除该对象
								        var parentNode = pdf.parentNode;
								        parentNode.removeChild(pdf);
								    }
								    var p = document.createElement("object");
								    p.id = "createPDF";
								    p.classid = "CLSID:CA8A9780-280D-11CF-A24D-444553540000";
								    p.width = 1;
								    p._Version=327680;
								    p._ExtentX=19315;
								    p._ExtentY=16034;
								    p._StockProps=0;
								    p.height = 1;
								    var objects=new Object();
								    objects.src=filesurl;
								    p.object=objects;  
								    document.body.appendChild(p);
								    
								    var pdfReader = document.getElementById("createPDF");  
								     pdfReader.setShowToolbar(false);  
								     pdfReader.setShowScrollbars(false);  
								     pdfReader.src=filesurl;  
								     setTimeout(function () { 
								    	 doprintln();
								     }, 5000);	
							}
					}
				}
			})
		}
	 
	 function doprintln(){
		 var pdfReader = document.getElementById("createPDF");   
		 pdfReader.gotoFirstPage();
	     pdfReader.print();
	     $(".loading_div").remove();
	 }
	
	// 登记台减项
	function djtdelcusItem(){
		var checkedItems = $('#djtGselectItemlist').datagrid('getChecked');
	    var ids =  new Array();	
	    var delflags=0;
	    var delflagstext="";
	    var delids=new Array();
	    $.each(checkedItems, function(index, item){	        
	        if(item.pay_status=='Y'){
	        	delflags=1;
	        	delflagstext=item.item_code+"项目已经付费，不能删除！"
	        }else if((item.exam_status=='Y')||(item.exam_status=='C')){
	        	delflags=1;
	        	delflagstext=item.item_code+"项目已检或已登记，不能删除！"
	        }else if(item.is_application=='Y'){
	        	delflags=1;
	        	delflagstext=item.item_code+"项目已发申请，不能删除！"
	        }else{
	        	ids.push(item.item_code);
	        	delids.push(item.item_code);
	        }
	    });
	    if(delflags==1){
	    	$.messager.alert("操作提示", delflagstext,"error");	
	    }else{
       
	    if($('#exam_num').val()==""){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else if((ids=='')||(ids.length<=0)){
	 		$.messager.alert("操作提示", "选择项目无效","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否确定删除选中项目？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'djtcustomeritemdelshow.action',
				data : {
					exam_id : $("#exam_id").val(),
					exam_num:$('#exam_num').val(),
					batch_id:0,
			        ids:ids.toString(),
			        others:delids.toString()
				    },
						type : "post",// 数据发送方式
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								djtcustChangItemListGrid();
								gettotalinfo();
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
	 	}
	    }
	}
	
	
	 var newWindow;  
	 var timer; 
	function djtaddcusItem(){
		if($("#examstatus").val() == 'Z'){
			$.messager.alert('提示信息',"体检人员已总检，不能加项!","error");
			return;
		}
		var scustsex = encodeURI(encodeURI(document.getElementsByName("sex")[0].value));
		if($('#exam_num').val()!=""){
 	    	var url='djtcustomeritemaddshow.action?exam_num='+$('#exam_num').val()+'&customer_id='+$("#customer_id").val()+'&exam_id='+$("#exam_id").val()+'&sex='+scustsex+'';
 	    	newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
 	    	newWindow.focus();
 	    	// timer = setInterval("djtupdateAfterClose()", 1000);
 	   	}else{
 	   	  $.messager.alert('提示信息',"请先确定体检人员","error");
 	   	}
	}
	
	/**
	 * 设置每隔2秒钟刷新父节点的表格
	 */
	 function djtupdateAfterClose() {  
	     // 父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口
	     if(newWindow.closed == true) {
	     clearInterval(timer);  
	     setselectListGrid();
	     gettotalinfo();
	     djtcustChangItemListGrid();
	     return;  
	     }  
	}  
	 
	// 打印条码
	function printbarshow(){
		if($("#exam_num").val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else{
		$("#dlg-show").dialog({
	 		title:'打印导检单',
	 		href:'djtGprintbarshow.action?exam_id='+$("#exam_id").val()+'&exam_num'+$("#exam_num").val()
	 	});
	 	$("#dlg-show").dialog('open');
	 	}
	}
	
// 补发申请操作
  function reApplydjd(){
	    if($("#exam_num").val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else{
		 $.messager.confirm('提示信息','是否确定补发申请？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'djtreApplydo.action',
				data : {
					exam_id : $("#exam_id").val(),
					exam_num:$('#exam_num').val()
				    },
						type : "post",// 数据发送方式
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								djtcustChangItemListGrid();
								gettotalinfo();
								$.messager.alert("操作提示", text.split("-")[1]);
							} else {
								$.messager.alert("操作提示", text.split("-")[1], "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
	 	}
  }
  
  /**
	 * 删除申请
	 */
  function delApplydjd(){
	  var data = $('#djtGselectItemlist').datagrid('getSelections');		
		if(data.length <= 0){
			$.messager.alert('提示信息', '请选择需要撤销申请的项目',function(){});
			return;
		}else{
			for(i=0;i<data.length;i++){
				if(data[i].exam_status == 'Y' || data[i].exam_status == 'C'){
					$.messager.alert('提示信息', '收费项目'+data[i].item_name+'已检查，不能撤销申请!',function(){});
					return;
				}
				if(data[i].is_application == 'N'){
					$.messager.alert('提示信息', '收费项目'+data[i].item_name+'未发送申请，不需要撤销申请!',function(){});
					return;
				}
			}
		}
		
		// 调用取消申请接口
		var chargingids = new Array();
		for(var i=0;i<data.length;i++){
			chargingids.push(data[i].item_code);
		}
		var exam_num = $("#exam_num").val();
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
   	    $("body").prepend(str);
		$.ajax({
			url : 'delLisAndPacsApplication.action',
			data : {exam_num:exam_num,examInfoCharingItems:chargingids.toString()},
			type : 'post',
			success:function(data){
				$(".loading_div").remove();
				$.messager.alert('提示信息', data,function(){});
				djtcustChangItemListGrid();
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
  }
  
  //
	function reapplydjtlispacs(){
		if(($('[name=ishisflags]:checked').val()!=undefined)&&($('[name=ishisflags]:checked').val()=='1')){
			rehisdjdpacs();
		}else{
			djtcustChangItemListGrid();
			gettotalinfo();
		}
	}
	
	// 补发收费申请
	  function rehisdjdpacs(){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			 $.ajax({
					url : 'djtrehisdo.action',
					data : {
						exam_id : $("#exam_id").val(),
						exam_num:$('#exam_num').val()
					    },
							type : "post",// 数据发送方式
							success : function(text) {
								djtcustChangItemListGrid();
								gettotalinfo();
								$(".loading_div").remove();
								/*
								 * if (text.split("-")[0] == 'ok') {
								 * $.messager.alert("操作提示", text.split("-")[1]); }
								 * else { $.messager.alert("操作提示",
								 * text.split("-")[1], "error"); }
								 */
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
		   
	  }

  // 补发收费申请
  function rehisdjd(){
	    if($('#exam_num').val()==''){
	 		$.messager.alert("操作提示", "人员无效","error");
	 	}else{
		 $.messager.confirm('提示信息','是否确定补发申请？',function(r){
			 	if(r){
			 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
		 $.ajax({
				url : 'djtrehisdo.action',
				data : {
					exam_id : $("#exam_id").val(),
					exam_num:$('#exam_num').val()
				    },
						type : "post",// 数据发送方式
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								djtcustChangItemListGrid();
								$.messager.alert("操作提示", text.split("-")[1]);
							} else {
								$.messager.alert("操作提示", text.split("-")[1], "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
	 	}
  }
  
  function jzkread_cardno(){
	  var mc_no=readCardJZK_DH();
	  if(mc_no>0){
		  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
             $.ajax({
		         url : 'djtjzkinfodo.action',
		         data : {
		        	 mc_no:mc_no
		         },
				 type : "post",// 数据发送方式
				 success : function(text) {
					$(".loading_div").remove();
					if(text.indexOf("error")==0){
						$.messager.alert("操作提示", text.split("-")[1], "error");
					}else if(text.indexOf("errorexam")==0){
  						$("#exam_num").val(text.split("-")[1]);
  						findcustomer_examnum();
  					}else{
						var cumtomersddata=JSON.parse(text);
						setjzkCustomer_select(cumtomersddata);
					}					
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			 });
	  }else{
		  $.messager.alert("操作提示","读取就诊卡错误！", "error");  
	  }
  }
  
  // 身份证获取挂号信息
  function sfzread_cardno(){
	  var sfz_div_code = $("#sfz_div_code").val();
	  var data=readCardSFZ(sfz_div_code);    	
      if(data.error_flag=="0"){
      	var certno=data.certno;
      	if(certno.length==18){
  		  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
  			 $("body").prepend(str);
               $.ajax({
  		         url : 'djtjzkinfodo.action',
  		         data : {
  		        	 mc_no:certno
  		         },
  				 type : "post",// 数据发送方式
  				 success : function(text) {
  					$(".loading_div").remove();
  					if(text.indexOf("error")==0){
  						$.messager.alert("操作提示", text.split("-")[1], "error");
  					}else if(text.indexOf("errorexam")==0){
  						$("#exam_num").val(text.split("-")[1]);
  						findcustomer_examnum();
  					}else{
  						var cumtomersddata=JSON.parse(text);
  						setjzkCustomer_select(cumtomersddata);
  					}					
  				},
  				error : function() {
  					$(".loading_div").remove();
  					$.messager.alert("操作提示", "操作错误", "error");					
  				}
  			 });
      	}else{
      		$.messager.alert("操作提示","读取身份证号错误！", "error");  
      	}
      }
	  
  }
  
  // 就诊卡号获取人员信息
  function setjzkCustomer_select(cumtomersd)
	{
		$("#exam_id").val(0);
		$("#customer_id").val('');	
		$("#arch_num").textbox('setValue','');	
		$('#id_num').val(cumtomersd.ID_NO);
		$('#custname').val(cumtomersd.NAME);
		$('#age').textbox('setValue', cumtomersd.AGE);
		$('#email').textbox('setValue', '');
		// $('#addtel').textbox('setValue', cumtomersd.addtel);
		$('#others').textbox('setValue', '');	  
		$('#addtel').textbox('setValue', cumtomersd.PHONE_NUMBER_HOME);	
		$('#address').textbox('setValue', cumtomersd.MAILING_ADDRESS);
		$('#remark').textbox('setValue', '');
		$('#introducer').textbox('setValue', '');
	    $("#birthday").datebox('setValue',cumtomersd.DATE_OF_BIRTH);
	    $("#past_medical_history").textbox('setValue',cumtomersd.ALERGY);
	    
	    $("#employeeID").val("");	
	    $("#visit_date").val(cumtomersd.VISIT_DATE);
		$("#visit_no_j").val(cumtomersd.VISIT_NO);
		$("#clinic_no").val(cumtomersd.CLINIC_NO);
		$("#patient_id").val(cumtomersd.PATIENT_ID);
		$("#mc_no").val(cumtomersd.CARD_NO);
		
		var objectsex = $('#sex').combobox('getData');
	    for(var i=0;i<objectsex.length;i++) {
	        	if (objectsex[i].id == cumtomersd.SEX) {
					$('#sex').combobox('setValue', objectsex[i].id);
					break;
				}
	        }  

      /*
		 * for(var i=0;i<objectminzhu.length;i++) { if (objectminzhu[i].id ==
		 * cumtomersd.nation) { $('#minzhu').combobox('setValue',
		 * objectminzhu[i].id); break; } }
		 */

     /*
		 * for(var i=0;i<objectcustomer_type.length;i++) { if
		 * (objectcustomer_type[i].id == cumtomersd.customer_type) {
		 * $('#tjlx').combobox('setValue', objectcustomer_type[i].id); break; } }
		 */

      /*
		 * for(var i=0;i<objectchargingType.length;i++) { if
		 * (objectchargingType[i].id == cumtomersd.chargingType) {
		 * $('#sftype').combobox('setValue', objectchargingType[i].id); break; } }
		 */

      /*
		 * for(var i=0;i<objectcustomerType.length;i++) { if
		 * (objectcustomerType[i].id == cumtomersd.customerType) {
		 * $('#customerType').combobox('setValue', objectcustomerType[i].id);
		 * break; } }
		 */

     /*
		 * for(var i=0;i<objectcustomer_type_id.length;i++) { if
		 * (objectcustomer_type_id[i].id == cumtomersd.customer_type_id) {
		 * $('#rylb').combobox('setValue', objectcustomer_type_id[i].id); break; } }
		 */
	    var objectis_Marriage = $('#is_Marriage').combobox('getData');
      for(var i=0;i<objectis_Marriage.length;i++) {
      	if (objectis_Marriage[i].id == cumtomersd.MARITAL_STATUS) {
				$('#is_Marriage').combobox('setValue', objectis_Marriage[i].id);
				break;
			}
      }
		// }
        // $('#exam_num').textbox('textbox').focus();
		// $("#exam_num").textbox('textbox').select();
		setselectListGrid();
		djtcustChangItemListGrid();
		gettotalinfo();
	}
  
// 挂号补充人员挂号信息
  function setjzkCustomer_selectreg(cumtomersd)
	{
	   $("#visit_date").val(cumtomersd.VISIT_DATE);
		$("#visit_no").val(cumtomersd.VISIT_NO);
		$("#clinic_no").val(cumtomersd.CLINIC_NO);
		$("#patient_id").val(cumtomersd.PATIENT_ID);
		$("#mc_no").val(cumtomersd.CARD_NO);
	}
  
  function examnumenterbd()// enter 键
  {
  			if(($("#exam_num").val()!='')&&($("#exam_num").val().length>0)){
  				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
  				 $("body").prepend(str);
  			 $.ajax({
  					url : 'djtcheckexamInfoStatus.action',
  					data : {
  						    exam_num:$("#exam_num").val()
  							},
  							type : "post",// 数据发送方式
  							success : function(text) {
  								$(".loading_div").remove();
  								if (text.split("-")[0] == 'ok') {// 一个体检信息
  									   $("#exam_num").textbox('setValue',text.split("-")[1]);
  										$.messager.confirm('提示信息','是否报到？',function(r){
  										if(r){
  											 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
  											 $("body").prepend(str);
  											$.ajax({
  												url : 'djtexamInfoStatusdo.action',
  												data : {
  													    exam_num:$("#exam_num").val()
  														},
  														type : "post",// 数据发送方式
  														success : function(text) {
  															$(".loading_div").remove();
  															if (text.split("-")[0] == 'ok') {
  																$("#examstatus").val('D');
  															} else {
  																$.messager.alert("操作提示", text.split("-")[1], "error");
  															}
  															
  														},
  														error : function() {
  															$(".loading_div").remove();
  															$.messager.alert("操作提示", "操作错误", "error");					
  														}
  													});
  	
  										}
  										});
  									
  								}  else {
  									// $("#exam_num").textbox('textbox').select();
  									// $('#exam_num').textbox('textbox').focus();
  									$.messager.alert("操作提示", text.split("-")[1], "error");
  								}
  								
  							},
  							error : function() {
  								$(".loading_div").remove();
  								$.messager.alert("操作提示", "操作错误", "error");					
  							}
  						});
  		 	}else{
  		 		$.messager.alert("操作提示", "体检编号无效", "error");	
  		 	}  
  }
  

// 打印发票
function fapiao_point(){
	if($('#exam_num').val()!=""){
	var reportlets = new Array();
	var sa = {
		"reportlet" : 'mx_point.cpt',
		"a" : $("#exam_num").val()
	};
	reportlets.push(sa);
	var printurl = "../../ReportServer";
	var config = {
		url : printurl,
		isPopUp : true,
		data : {
			reportlets : reportlets
		}
	}
	FR.doURLPDFPrint(config);
	
	}else{
		$.messager.alert("操作提示", "体检编号无效", "error");	
	}
}

// 签名
function fnewsigng(){
	 if($('#exam_num').val()==""){
		$.messager.alert("操作提示", "记录存在，不能新增", "error");
	 }else{
		var url='examinfosignshow.action?exam_num='+$('#exam_num').val()+'&exam_id='+$("#exam_id").val();
		 var name='设置签名';                            // 网页名称，可为空;
         var iWidth=1024                          // 弹出窗口的宽度;
         var iHeight=420;                         // 弹出窗口的高度;
         // 获得窗口的垂直位置
         var iTop = (window.screen.availHeight - 30 - iHeight) / 2+100;
         // 获得窗口的水平位置
         var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
         newWindow = window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no'); 
         newWindow.focus();
	    // newWindow = window.open(url, "设置签名",
		// "toolbar=yes,location=yes,fullscreen=no,scrollbars=yes");
	 }
 }

// 问卷调查
function fnewjkwjdc(){
	 if(Number($("#exam_num").val())<=0){
		$.messager.alert("操作提示", "记录存在，不能新增", "error");
	 }else{
		 $.ajax({
				url:'getQuestionnaireSurveyPageShow.action?s_num='+$('#exam_num').val(),
				type:'post',
			    success:function(data){
			    	if(data=="youxiao"){
			    		var url="getQuestionnaireSurveyPageYM.action?s_num="+$("#exam_num").val();
			    	 	newWindow = window.open (url,'fullscreen = yes , height=100%, width=100%, top=5, left=5,toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no','_self') 
			    	 	newWindow.focus();
			    	}else{
				    	$.messager.alert("警告信息","体检号不存在","error");
			    	}
			    },
			    error:function(){
			    	$.messager.alert("警告信息","操作失败","error");
			    }				
			})
	 }
 }

// 空函数，有用 问卷
function qk(){}

function RunServerExe(url){
	location.href=url;
}

function new_print_barcode(){
	if($("#exam_num").val().length>0){
		var ids =$("#exam_num").val();
		 $.ajax({
				url : 'updateSampleExamEetail.action',
				data : {
					ids : ids
				    },
						type : "post",// 数据发送方式
						success : function(text) {
							if (text.split("-")[0] == 'ok') {
								$.ajax({
									url : 'updateBarcodePrintStatus.action',
									data : {ids:"'"+ids+"'"},
									type : "post",// 数据发送方式
									success : function(text) {
										if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
											 var exeurl ='GuidBarServices://&guidBarcode&'+ids+'&&3&1'; // 打印档案号
										 }else{
											 var exeurl ='GuidBarServices://&guidBarcode&'+ids+'&&1'; 
										 }
										RunServerExe(exeurl);
									},
									error : function() {
										$.messager.alert("操作提示", "操作错误", "error");					
									}
								});
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});		
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}

// 新打导检单
function new_printdjd(){
	if($("#barcode_print_type").val() == '1'){// 调用旧打印程序
		printdjd();
	}else if($("#barcode_print_type").val() == '2'){// 调用新打印程序
		var url = 'GuidBarServices://&guid&'+$("#exam_num").val()+'&&';
		RunServerExe(url);
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
		new_printdjd4();
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}
// 新打导检单条码
function new_printdjdbar(){
	if("Y" == $("#examstatus").val()){
		$.messager.confirm('提示信息','是否报到？',function(r){
			if(r){
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				$.ajax({
					url : 'djtexamInfoStatusdo.action',
					data : {
						    exam_num:$("#exam_num").val()
							},
							type : "post",// 数据发送方式
							success : function(text) {
								$(".loading_div").remove();
								if($("#barcode_print_type").val() == '1'){// 调用旧打印程序
									printdjdbar();
								}else if($("#barcode_print_type").val() == '2'){// 调用新打印程序
									var url = 'GuidBarServices://&guid&'+$("#exam_num").val()+'&&';
									// RunServerExe(url);//打导检单
									new_print_barcode();
								}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
									$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
								}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
									new_printdjdbar4();
								}else{
									$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});	
			     }
			});
	}else{
		if($("#barcode_print_type").val() == '1'){// 调用旧打印程序
			printdjdbar();
		}else if($("#barcode_print_type").val() == '2'){// 调用新打印程序
			var url = 'GuidBarServices://&guid&'+$("#exam_num").val()+'&&';
			// RunServerExe(url);//打导检单
			new_print_barcode();
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
			new_printdjdbar4();
		}else{
			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		}
	}
}
// 新补打导检单
function new_reprintdjd(){
	if($("#barcode_print_type").val() == '1'){// 调用旧打印程序
		reprintdjd();
	}else if($("#barcode_print_type").val() == '2'){// 调用新打印程序
		if($("#exam_num").val().length>0){
			var ids = "";
	    	var checkedItems = $('#djtGselectItemlist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.item_code);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请先选择体检人员对应的缴费项目","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	  if(ids.length>=1){
			 		 var url = 'GuidBarServices://&guid&'+$("#exam_num").val()+'&'+ids+'&';
			 		RunServerExe(url);// 打导检单
			 	   	}else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员的收费项目","error");
			 	   	}
	    	}
		}else{
			$.messager.alert("操作提示", "体检人员无效","error");
		}	
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
		new_reprintdjd4();
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}

//新打导检单 4.0
function new_printdjd4(){
	if($("#exam_num").val().length>0){
		var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&guid&'+$("#exam_num").val()+'&&';
		RunServerExe(url);
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}
//新打导检单条码4.0
function new_printdjdbar4(){
	if($("#exam_num").val().length>0){
		var bar_calss = 1;
		if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
			bar_calss = 3;
		}
		$.ajax({
			url : 'updateSampleExamEetail.action',
			data : {ids : $("#exam_num").val()},
			type : "post",//数据发送方式   
			success : function(text) {
				if (text.split("-")[0] == 'ok') {
					$(".loading_div").remove();
					var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&guidbar&'+$("#exam_num").val()+'&&'+bar_calss+'&1';
					RunServerExe(url);
				} else {
					$(".loading_div").remove();
					$.messager.alert("操作提示", text, "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}
//新补打导检单4.0
function new_reprintdjd4(){
	if($("#exam_num").val().length>0){
		var ids = new Array;
    	var checkedItems = $('#djtGselectItemlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	    	ids.push(item.item_code);
	    });
	    if(ids.length<=0){
    		$.messager.alert("操作提示", "请选择需要补打的项目","error");
    		return;
    	}
	    var url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&guid&'+$("#exam_num").val()+'&'+ids.toString()+'&';
		RunServerExe(url);
	}else{
		$.messager.alert("操作提示", "体检人员无效","error");
	}
}
// ------------------------------就诊卡----------------------------
function jiuzhengkaPage(){
	if($('#customer_id').val()<=0){
		$.messager.alert("提示信息","人员信息无效","error");
		return;
	}
	$('#dlg-jiuizhengka').dialog({
		 title: '就诊卡维护',    
	     href: 'getjiuzhenkapage.action?customer_id='+$('#customer_id').val()
	})
	$('#dlg-jiuizhengka').dialog('open');
}
//-------------------------修改程序就诊卡-------------------------


//就诊卡验证
var jiuzhengkayanzhen = {
		getvalidatevilit:function(){
			var fla = true;
			if($("#visit_no_h").val()!=$("#visit_no_j").val()){
				$.ajax({
					url:'getvalidatevilit.action',
					data:{
						visit_no:$("#visit_no_j").val(),
						customer_id:$('#customer_id').val()
					},
					type:'post',
					async:false,
					success:function(data){
						if(data=="ok"){
							
						} else {
							fla = false;
						}
					},error:function(){
						$.messager.alert("提示信息","操作失败","error");
					}
				})
			}
			return fla;
		},
		/*alertyanzheng:function(){
			if(!jiuzhengkayanzhen.getvalidatevilit() && $("#visit_no_h").val()!=$("#visit_no_j").val()){
				$.messager.alert("提示信息","就诊卡冲突！","error");
				return;
			}
		}*/
		
}
//---读取就诊卡----
function duqujiuzhneka(){
	var data = readjiuzhenka($("#Read_jiuzhenka").val());
	$('#visit_no_j').val(data.card_no);
	gtjiuzhenkaInfo(data.card_no)
}

//黄岗---就诊卡获取信息人员
function gtjiuzhenkaInfo(card){
	$.ajax({
		url:'getJiuzhenkaInfo.action',
		data:{
			jiuzhenka:card
		},
		type:'post',
		success:function(da){
			var data = eval('('+da+')')
			$('#custname').val(data.NAME);
			if(data.SEX=="1"){
				$('#sex').textbox('setValue',"男");//性别
			} else {
				$('#sex').textbox('setValue',"女");//性别
				
			}
			 $("#birthday").datebox('setValue',data.BIRTH);//出生日期
		    $('#age').textbox('setValue', data.AGE);//年龄
		    $("#address").textbox('setValue',data.ADDRESS);//地址
		    $("#addtel").textbox('setValue',data.LXFS);//电话
		    $("#id_num").val(data.IDCARDINFO);//身份证
		},error:function(){
			$.messager.alert("提示信息","操作失败!","error");
		}
	})
}
function huiche(){
	$('#visit_no_j').bind('keydown',function(event){  
		  if(event.keyCode == "13"){  
		    huichegtjiuzhenkaInfo();
		  }  
	}); 
}
//回车就诊卡
function huichegtjiuzhenkaInfo(){
	$.ajax({
		url:'getJiuzhenkaInfo.action',
		data:{
			jiuzhenka:$('#visit_no_j').val()
		},
		type:'post',
		success:function(da){
			var data = eval('('+da+')')
			$('#custname').val(data.NAME);
			if(data.SEX=="1"){
				$('#sex').textbox('setValue',"男");//性别
			} else {
				$('#sex').textbox('setValue',"女");//性别
				
			}
		    $("#birthday").datebox('setValue',data.BIRTH);//出生日期
		    $('#age').textbox('setValue', data.AGE);//年龄
		    $("#address").textbox('setValue',data.ADDRESS);//地址
		    $("#addtel").textbox('setValue',data.LXFS);//电话
		    $("#id_num").val(data.IDCARDINFO);//身份证
		
		},error:function(){
			$.messager.alert("提示信息","操作失败!","error");
		}
	})
}

