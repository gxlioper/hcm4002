<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>体检人员基本信息</title>
    <script type="text/javascript" src="../../html5/js/jquery.min.js"></script>
<script type="text/javascript" src="../../html5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/bootbox.js"></script>
<link rel='stylesheet' type='text/css' href='../../html5/css/bootstrap.min.css'>
</head>

<style type="text/css">
.col-lg-3 {
    width: 50%;
    float: left;
}
.btn-primary {
    color: #fff;
     background-color: #359bcc; 
     border-color: #359bcc; 
}
</style>
<script type="text/javascript">
var contralcustflag=0;
var data_source="";
//
//平台、设备和操作系统 
var system = { 
    win: false, 
    mac: false, 
    xll: false, 
    ipad:false 
}; 
//检测平台 
var p = navigator.platform; 
system.win = p.indexOf("Win") == 0; 
system.mac = p.indexOf("Mac") == 0; 
system.x11 = (p == "X11") || (p.indexOf("Linux") == 0); 
system.ipad = (navigator.userAgent.match(/iPad/i) != null)?true:false; 
//跳转语句，如果是手机访问就自动跳转到wap.baidu.com页面 
if (system.win || system.mac || system.xll||system.ipad) { 
} else { 
	data_source="003";
} 


 $(document).ready(function() {
	 showSel();
	 showSel2();
	 showSel3();
	 showSel4();
	 showSel5();
	 showSel6();
	 showSel7();
	 showSel8();
	//var exam_id= document.getElementById("exam_id").value
	var exam_num= document.getElementById("exam_num").value
	 f_findcustomerone(exam_num,0);
 });
 
 var selectexam_id;
	var brushtatle=0;
	function f_findcustomerone(selectexam_id,brushtatle){
		$.post("getDjtExamOneShow.action",{
			//"exam_id" : selectexam_id
			exam_num:selectexam_id
		}, function(jsonPost) {
			var customer = eval(jsonPost);			
			setCustomer(customer,brushtatle);
		}, "json");
	}
	function setCustomer(cumtomersd,brushtatle)
	{
// 		if(cumtomersd.exam_type=='T'){
// 			 $.messager.alert("操作提示","团检人员不能在此页编辑", "error");
// 		}else{
		
		$("#exam_id").val(cumtomersd.id);
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").val(cumtomersd.arch_num);	
		$("#exam_num").val(cumtomersd.exam_num);
		$("#c_exam_num").val(cumtomersd.exam_num);
		$('#idNum').val(cumtomersd.id_num);
		selectidnum(cumtomersd.id_num);
		$('#custname').val(cumtomersd.user_name);
		$('#age').val(cumtomersd.age);
		$('#email').val( cumtomersd.email);
// 		$('#addtel').val(cumtomersd.tel);
		
		$("input[name='is_after_pay'][value="+cumtomersd.is_after_pay+"]").attr("checked",true); 
		$('#others').val( cumtomersd.others);
		$('#statuss').val( cumtomersd.statuss);	
		$("#examstatus").val(cumtomersd.status)		  
		$('#register_date').val(cumtomersd.register_date);
		$('#exam_times').val(cumtomersd.exam_times);
		$('#remark').val(cumtomersd.remarke);
		$('#rylb').val(cumtomersd.customer_type_id);
		$('#tjlx').val(cumtomersd.customer_type);
		$('#sftype').val(cumtomersd.chargingType);
		$('#minzhu').val(cumtomersd.nation);
		$('#is_Marriage').val(cumtomersd.is_marriage);
		
		
// 		document.getElementById("examcount").innerHTML="您是第"+cumtomersd.examcount+"次体检";
		if(cumtomersd.vipsigin=="1")
	       {
	         $("#vipsigin").show();
	       }else{
	    	 $("#vipsigin").hide(); 
	       }
// 		$("#employeeID").textbox('setValue',cumtomersd.employeeID);		
// 		$("#visit_date").textbox('setValue',cumtomersd.visit_date);
// 		$("#visit_no").textbox('setValue',cumtomersd.visit_no);
// 		$("#clinic_no").textbox('setValue',cumtomersd.clinic_no);
// 		$("#introducer").textbox('setValue',cumtomersd.introducer);
		$("#patient_id").val(cumtomersd.patient_id);
		$("#mc_no").val(cumtomersd.mc_no);
		
// 		$("#register_dates").textbox('setValue',cumtomersd.register_date);
// 		$("#register_times").textbox('setValue',cumtomersd.exam_times);
// 		$("#join_dates").textbox('setValue',cumtomersd.join_date);
		
// 		$('#address').textbox('setValue', cumtomersd.address);
// 		$('#remark').textbox('setValue', cumtomersd.remarke);
// 	    $("#birthday").datebox('setValue',cumtomersd.birthday),
// 		$('#company').textbox('setValue', cumtomersd.company);
 		$('#past_medical_history').val(cumtomersd.past_medical_history);
		$("#picture_Path").val(cumtomersd.picture_Path);
// 		$("#reportingWay").combobox("setValue",cumtomersd.getReportWay);
// 		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+cumtomersd.picture_Path+"&"+new Date().getTime();
		
// 		$('[name="is_after_pay"]:radio').each(function() {
//             if (this.value == cumtomersd.is_after_pay){   
//                this.checked = true;   
//             }     
//          });   

//         //$('#exam_num').textbox('textbox').focus(); 
// 		//$("#exam_num").textbox('textbox').select();
// 		if(brushtatle==0){
// 		setselectListGrid();
// 		djtcustChangItemListGrid();
// 		gettotalinfo();
// 		}
	}
	//自动填充 生日、年龄、性别
	function selectidnum(idnum){
//		var idnum= $("#id_num").val();//取值 
		if(idnum.length==18){
			var ssidnum=idnum.substring(0,17);
			if (isSZ(ssidnum)) {	
			  $('#csrq').val(IdCard(idnum,1));
			  $('#age').val(IdCard(idnum,3));
			  $('#sex').val(IdCard(idnum,2));
			}
		}
	}

// 	民族
	function showSel(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=MZLX2",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#minzhu").append(opts); 
			}
		});  
	}
// 	性别
	function showSel2(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=XBLX2",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#sex").append(opts);
			}
		});  
	}
	function showSel3(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=SFLX2",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#customerType").append(opts);  
			}
		});  
	}
	
	function showSel4(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=TJLX",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#tjlx").append(opts);  
			}
		});  
	}	
		
	function showSel5(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=SFTYPE",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#sftype").append(opts);
			}
		});  
	}	
	function showSel6(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=HY",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#occusectorid").append(opts); 
			}
		});  
	}	
	function showSel7(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=RYLB",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#rylb").append(opts);  
			}
		});  
	}	
	function showSel8(){
		$.ajax({  
		        "type" : 'get',  
		        "url": "getDatadis.action?com_Type=HFLX",
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["name"]+"</option>";
				}	
				$("#is_Marriage").append(opts);  
			}
		});  
	}	
		
	//通过身份证号获取生日、年龄、性别
	function IdCard(UUserCard,num){
		   if(num==1){
		       //获取出生日期
		       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
		    return birth;
		   }
		   if(num==2){
		       //获取性别
		       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
		           //男
		     return "男";
		       } else {
		           //女
		     return "女";
		       }
		   }
		   if(num==3){
		        //获取年龄
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
		
		
	//输入生日计算年龄
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

	//输入年龄计算生日
	function selectage(){
		var age =$("#age").textbox('getValue');
		if(age.length>0){
				var myDate = new Date();
				var age1 = parseInt(myDate.getFullYear()) - parseInt(age);
				var birthday = age1 + "-" + (myDate.getMonth() + 1) + "-" + myDate.getDate();
				$("#birthday").datebox('setValue',birthday);
		}
	}	
	/**
	 * 保存修改
	 */
	function addcustomerdo(exam_num) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
			$.ajax({
				url : 'djteditcustomerdo.action',
				data : {
// 							customer_id: $("#customer_id").val(),
							custname :$("#custname").val(),
							id_num : document.getElementById("idNum").value,
							//exam_id :exam_id,
							exam_num : document.getElementById("exam_num").value,
// 							arch_num :$("#arch_num").val(),							
							age :$("#age").val(),
							batch_id:$("#batch_id").val(),
// 							tel :document.getElementById("addtel").value,
// 							past_medical_history :$("#past_medical_history").val(),
							remark :$("#remark").val(),
							is_after_pay:$("input[name='is_after_pay']:checked").val(),
							sex : $('#sex option:selected').val(),//选中的值,
							customer_type_id : $('#rylb option:selected').val(),//选中的值,
							customer_type : $('#tjlx option:selected').val(),//选中的值,
							is_marriage : $('#is_Marriage option:selected').val(),//选中的值,
							chargingType : $('#sftype option:selected').val(),//选中的值,
							nation : $('#minzhu option:selected').val(),//选中的值,
							customerType : $('#customerType option:selected').val(),//选中的值,
							data_source:"003",
							past_medical_history : document.getElementById("past_medical_history").value
// 							is_marriage : document.getElementsByName("is_Marriage")[0].value,
// 							birthday:$("#birthday").datebox('getValue'),	
// 							customer_type:document.getElementsByName("tjlx")[0].value,
// 							nation:document.getElementsByName("minzhu")[0].value,
// 							customer_type_id:document.getElementsByName("rylb")[0].value,
// 							chargingType:document.getElementsByName("sftype")[0].value,
// 							customerType:document.getElementsByName("customerType")[0].value,
// 							occusectorid:document.getElementsByName("occusectorid")[0].value,
// 							register_date:$("#register_date").val(),
// 							setimes:$("#setimes").val(),	 	
// 							tel : $("#addtel").val(),
// 							email : $("#email").val(),
// 							_level :'',
// 							position : $("#addposition").val(),
// 							others:$("#others").val(),
// 							remark:$("#remark").val(),
// 							address:$("#address").val(),
// 							examstatus:$("#examstatus").val(),
// 							company:$("#company").val(),
// 							getReportWay:$("#reportingWay").combobox("getValue"),
							
// 							employeeID:$("#employeeID").val(),
// 							mc_no:$("#mc_no").val(),//就诊卡号
// 							patient_id:$("#patient_id").val(),//病人id
// 							visit_date:$("#visit_date").val(),//就诊日期
// 							visit_no:$("#visit_no").val(),//就诊号
// 							clinic_no:$("#clinic_no").val(),//门诊号		
// // 							introducer:$("#introducer").combobox("getText"),
// 							past_medical_history:$("#past_medical_history").val(),
// 							is_after_pay:$('input[name="is_after_pay"]:checked').val(),
// 							picture_Path:$("#picture_Path").val(),
// 							flags:contralcustflag
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								bootbox.alert("操作成功！");
								console.log(text.split("-")[1]);
								 window.location.href="medical.html?exam_num="+text.split("-")[1];
// 								f_findcustomerone(,1,0);
								window.opener.location.reload(true); 
// 								$('#exam_num').textbox('textbox').focus(); 
// 								$("#exam_num").textbox('textbox').select();
							} else {
								bootbox.alert(text+"error");
							}
							
						},
						error : function() {
						}
					});
	}
	function isSZ(str){	
		return (/^[0-9]{1,20}$/.test(str));	
	}
	function fnewbutton(){
		if(checkinput()) {
			addcustomerdo("${model.exam_num}");
		}
	}
	function checkinput() {	
		if (document.getElementById("custname").value == '') {
			alert('姓名不能为空！');
			$("#custname").focus();
			return false;
		}else if ((document.getElementById("idNum").value.length!= '')&&(document.getElementById("idNum").value.length!= 18)) {
			alert('身份证号位数错误！');
			$("#idNum").focus();
			return false;
		} else if ((document.getElementById("idNum").value.length!= '')&&(!isSZ(document.getElementById("idNum").value.substring(0,17)))) {
			alert('身份证号格式错误！');
			$("#idNum").focus();
			return false;
		} else if (document.getElementById("idNum").value == '') {
			alert('身份证号格式错误！');
			$("#idNum").focus();
			return false;
		}
		if (!(isSZ(document.getElementById("age").value))) {
			alert('年龄格式错误！');
			$("#age").focus();
			return false;
		}  else if (Number(document.getElementById("age").value)>120) {
			alert('年龄不能超出120岁！');
			$("#age").focus();
			return false;
		} else if (Number(document.getElementById("age").value)<=0) {
			alert('年龄不能小于1岁！');
			$("#age").focus();
			return false;
		}else if ($('#minzhu sex').val()=='') {
			alert('性别不能为空！');
			$("#sex").focus();
			return false;
		}else /* if ($("#birthday").datebox('getValue')=='') {
			alert('出生日期不能为空！');
			return false;
		} */
		return true;
	}
	
	//通过身份证号获取生日、年龄、性别
	function IdCard(UUserCard,num){
		   if(num==1){
		       //获取出生日期
		       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
		    return birth;
		   }
		   if(num==2){
		       //获取性别
		       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
		           //男
		     return "男";
		       } else {
		           //女
		     return "女";
		       }
		   }
		   if(num==3){
		        //获取年龄
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
		
	function IdCard1(){
		 var  UUserCard= document.getElementById("idNum").value;
		 if(UUserCard.length==18){
		       //获取出生日期
		       birth=UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
		       $("#csrq").val(birth);
		       //获取性别
		       if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
		           //男
		    	   $("#sex").val("男");
		       } else {
		           //女
		    	   $("#sex").val("女");
		       }
		        //获取年龄
		        var myDate = new Date();
		        var month = myDate.getMonth() + 1;
		        var day = myDate.getDate();
		        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
		        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
		            age++;
		        }
		        $('#age').val(age);
		 }
		}		
</script>
<body>
  <nav class="navbar navbar-expand-lg navbar-dark fixed-top navbar-shrink" style="background-color: #359bcc" id="mainNav">
    <div class="container">
<!--       <a class="navbar-brand js-scroll-trigger" href="#page-top">火箭蛙  &nbsp; &nbsp; <span id="time" style="text-align:left;font-size:16px;"></span></a> -->
      <div class="collapse navbar-collapse" id="navbarResponsive">
      <div style="border:0px solid red;margin-top: 20px; float: left;margin-left: 0px;height: 50px;width: 200px;background-image: url(../img/logo.png);background-repeat:no-repeat;background-size:60%;">
  		
  	  </div>
        <div style="width: 20%; float: right; margin-top: 3%;"><a class="nav-link js-scroll-trigger" style="color: #000;;" href="padLogin.action">退出</a></div>
      </div>
    </div>
  </nav>
<input type="hidden" id="user_uuid"/>  
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">	
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">	

<!--     <form class="" role="form" > -->
    <form method="post" id="registerForm">
        <div class="container" style="">
            <div class="row" style="padding: 20px 0">
                <h3>体检人员基本信息</h3>
            </div>
            <div class="row">
                <!--前缀-->
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">体检编号</span>
                        <input class="form-control" name="c_exam_num" id="c_exam_num" readonly="readonly" type="text" style="ime-mode: disabled;">
                    </div>
                </div>
               <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">姓名<font color="red">*</font></span>
                        <input class="form-control" name="custname" maxlength="20" id="custname"  type="text">
                    </div>
                </div>
            </div>
            <div class="row">
               <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">身份证号<font color="red">*</font></span>
                        <input class="form-control" name="idNum" id="idNum"  onchange="IdCard1()" type="text">
                    </div>
                </div>
                
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">年龄<font color="red">*</font></span>
                        <input class="form-control" name="age" id="age" type="text" onKeyUp="if(this.value.length>2){this.value=this.value.substr(0,2)};this.value=this.value.replace(/[^\d]/g,'');">
                    </div>
                </div>
            </div>
            <div class="row">
             <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">出生日期</span>
                        <input class="form-control" readonly="readonly" type="text" id="csrq">
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">人员类别</span>
                        <select class="form-control" id="rylb"  name="rylb">
                        </select>
                    </div>
                </div>
                
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">性别<font color="red">*</font></span>
                        <select class="form-control" id="sex" name="sex">
                        </select>
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">体检类别</span>
                        <select class="form-control" id="tjlx" name="tjlx">
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">婚否</span>
                        <select class="form-control" id="is_Marriage" name="is_Marriage">
                        </select>
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">费别</span>
                        <select class="form-control" id="sftype" name="sftype">
                        </select>
                    </div>
                </div>
            </div>
             <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">民族</span>
                        <select class="form-control"  id="minzhu" name="minzhu"	>
                        </select>
                    </div>
                </div>
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group"> -->
<!--                         <span class="input-group-addon">身份类型</span> -->
<!--                         <select class="form-control" id="customerType" name="customerType"> -->
<!--                         </select> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
<!--              <div class="row"> -->
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group"> -->
<!--                         <span class="input-group-addon">电话</span> -->
<!--                         <input class="form-control" name="addtel"  id="addtel"  type="text"> -->
<!--                     </div> -->
<!--                 </div> -->
                <div class="form-group col-lg-3">
                    <input id="chkItem" name="is_after_pay" checked="true" type="radio" value="N"/>前付费
					<input id="chkItem" name="is_after_pay"  type="radio" value="Y"/>后付费
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">既往史</span>
                        <input class="form-control" maxlength="20" id="past_medical_history" type="text">
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">备注</span>
                        <input class="form-control" name="remark" maxlength="20" id="remark" type="text">
                    </div>
                </div>
            </div>
            <button type="button" id="btnRegister"  onclick="fnewbutton();" class="btn btn-primary">下一步</button>
	        <button class="btn btn-primary"  id="btn-search1" onclick="javascript:window.close();">关闭</button>
        	<button type="reset" class="btn btn-default" id="btnRegister">重置</button>
        </div>
        
    </form>
</body>
</html>
