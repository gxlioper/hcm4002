var contralcustflag=0;
var exam_id="";
var comname2="";
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

$(function(){
	//exam_id= document.getElementById("exam_id").value
	exam_num = document.getElementById("exam_num").value
	f_findcustomerone(exam_num,0);
	
})
//自动填充 生日、年龄、性别
function selectidnum(idnum){
//	var idnum= $("#id_num").val();//取值 
	if(idnum.length==18){
		var ssidnum=idnum.substring(0,17);
		if (isSZ(ssidnum)) {	
		  $('#csrq').val(IdCard(idnum,1));
		  $('#age').val(IdCard(idnum,3));
		  $('#sex').val(IdCard(idnum,2));
		}
	}
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
	 comname();
	
	
 });
 $('#group_id').next().children(':first').click(function () {
	  getgroups();      
   });
 var selectexam_id;
	var brushtatle=0;
	function f_findcustomerone(selectexam_id,brushtatle){
		$.post("getDjtExamOneShow.action",{
			//"exam_id" : selectexam_id,
			"exam_num":selectexam_id
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
//		alert(cumtomersd.company);
//		$("#comname1").find("option[text='"+cumtomersd.comname+"']").attr("selected",true);
//		$("#company_id").find("option[text='"+cumtomersd.company_id+"']").attr("selected",true);
		$("#batch_id").val(cumtomersd.batch_id);
		$("#comname1").val(cumtomersd.batch_name);
		$("#comname3").val(cumtomersd.comname);
		  company_id(cumtomersd.company_id);
//		$("#company_id").val(cumtomersd.company_id);
		$("#exam_id").val(cumtomersd.id);
		$("#customer_id").val(cumtomersd.customer_id);	
		$("#arch_num").val(cumtomersd.arch_num);	
		$("#exam_num").val(cumtomersd.exam_num);
		$('#idNum').val(cumtomersd.id_num);
		selectidnum(cumtomersd.id_num);
		$("input[name='is_after_pay'][value="+cumtomersd.is_after_pay+"]").attr("checked",true); 
		$("#rylb").val(cumtomersd.customer_type_id);
		$("#tjlx").val(cumtomersd.customer_type);	
		$("#is_Marriage").val(cumtomersd.is_marriage);	
		$("#sftype").val(cumtomersd.chargingType);
		$('#minzhu').val(cumtomersd.nation);
		
		$('#custname').val(cumtomersd.user_name);
		$('#age').val(cumtomersd.age);
		$('#email').val( cumtomersd.email);
//		$('#addtel').val(cumtomersd.addtel);
		$('#others').val( cumtomersd.others);
		$('#statuss').val( cumtomersd.statuss);	
		$("#examstatus").val(cumtomersd.status)		  
// 		$('#addtel').textbox('setValue', cumtomersd.phone);	
		$('#register_date').val(cumtomersd.register_date);
		$('#exam_times').val(cumtomersd.exam_times);
		$("#exam_indicator").val(cumtomersd.exam_indicator);	
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
 		$('#remark').val(cumtomersd.remarke);
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

	function comname(){
		var  comname= document.getElementById("comname1").value;
		$.ajax({
    		url:"getComByBatchIpadList.action?comname="+comname,
    		type:'post',
    		success:function(data){
    			var list=eval("("+data+")");
    			var opts = "";
				for (var i = 0; i < list.length; i++) {
						opts += "<option value='"+ list[i]["id"]+"'>"+ list[i]["text"]+"</option>";
				}	
				$("#comname1").append(opts);
    		},
    		error:function(){
    			
    		}
    	});
    	 
	}	
	function gradeChange(){
		var options=$("#comname1 option:selected");
		company_id(options.val().split("-")[0]);
		$("#batch_id").val(options.val().split("-")[1]);
	}	
	function company_id(company_id){
		$("#company_id").html("");
		$.ajax({  
		        type : 'post',  
		        url: "getbatchcompanyshow.action?company_id="+company_id,
		        dataType : "json",  
		        success : function(data) {
					var opts = "";
					for (var i = 0; i < data.length; i++) {
							opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["text"]+"</option>";
					}		
				$("#company_id").append(opts);  
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
	function addcustomerdo(exam_id) {
		var sex=$('#sex option:selected').val();
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		var group_id=$('#group_id option:selected').val();
			$.ajax({
				url : 'djteditTcustomerdo.action',
				data : {
// 							customer_id: $("#customer_id").val(),
							comname:$("#comname3").val(),
							custname :$("#custname").val(),
//							addtel:	$("#addtel").val(),
							id_num : document.getElementById("idNum").value,
							exam_id :exam_id,
							exam_num : document.getElementById("exam_num").value,
							company_id:$("#company_id option:selected").val(),
// 							arch_num :$("#arch_num").val(),		
							batch_id:$("#batch_id").val(),
							age :$("#age").val(),
//							addtel :$("#addtel").val(),
							past_medical_history :$("#past_medical_history").val(),
							remark :$("#remark").val(),
							is_after_pay:$("input[name='is_after_pay']:checked").val(),
							sex : $('#sex option:selected').val(),//选中的值,
							customer_type_id : $('#rylb option:selected').val(),//选中的值,
							customer_type : $('#tjlx option:selected').val(),//选中的值,
							is_marriage : $('#is_Marriage option:selected').val(),//选中的值,
							chargingType : $('#sftype option:selected').val(),//选中的值,
							nation : $('#minzhu option:selected').val(),//选中的值,
							customerType : $('#customerType option:selected').val(),//选中的值,
							group_id : group_id,//选中的值,
							data_source:data_source//数据来源
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
								var scustsex = encodeURI(encodeURI($('#sex option:selected').val()));
								 window.location.href="medicalGroup.html?exam_id="+text.split("-")[1]+"&sex="+scustsex+'&exam_indicator='+$("#exam_indicator").val()+'';
								window.opener.location.reload(true); 
//								 f_findcustomerone(,1,0);
// 								$('#exam_num').textbox('textbox').focus(); 
// 								$("#exam_num").textbox('textbox').select();
							} else {
								bootbox.alert(text+"error");
							}
							
						},
						error : function() {
							alert("错误");
						}
					});
	}
	function isSZ(str){	
		return (/^[0-9]{1,20}$/.test(str));	
	}
	function fnewbutton(){
		if(checkinput()) {
			addcustomerdo(exam_id);
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
		}else if ($('#sex').val()=='') {
			alert('性别不能为空！');
			$("#sex").focus();
			return false;
		}else /* if ($("#birthday").datebox('getValue')=='') {
			alert('出生日期不能为空！');
			return false;
		} */
		return true;
	}
//弹框体检任务
	 // update表单
    function update_info(id)
    {
    	getgroupuserGrid()
    	  var columns  = document.getElementsByClassName("columns ");//获取到的是一个类数组
        for(var i=0;i<columns .length;i++){
         columns [i].style.display = "none";
      }  
        

    }
    function getgroupuserGrid() { 
    	
    	
		$('#reportTable').bootstrapTable({  
			  method: 'get',  
			  url: 'getComByBatchIpadList.action',  
			  dataType: "json",  
			  striped: true,     //使表格带有条纹  
			  pagination: true, //在表格底部显示分页工具栏  
//				  pageSize: 10,  
//				  pageNumber: 1,  
			  pageList: [10, 20, 50],  
			  idField: "exam_num",  //标识哪个字段为id主键  
			  showToggle: false,   //名片格式  
			  cardView: false,//设置为True时显示名片（card）布局  
			  showColumns: true, //显示隐藏列    
			  showRefresh: true,  //显示刷新按钮  
			  singleSelect: true,//复选框只能选择一条记录  
			  search: false,//是否显示右上角的搜索框  
			  queryParams: function queryParams(params){
		            var param = {    
		                    pageNumber: params.pageNumber,    
		                    pageSize: params.pageSize,
		                    sortName: params.sortName,
		                    sortOrder: params.sortOrder,
		                    comname1:comname2
		                };   
		                return param;    
		           }, //参数 
			  clickToSelect: true,//点击行即可选中单选/复选框  
//			  sidePagination: "server",//表格分页的位置  
			  sidePagination : "client",// 表格分页的位置
			  queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
			  toolbar: "#toolbar", //设置工具栏的Id或者class  
			  columns: [/*{
 					field: "ck",
					checkbox:true,
				},*/{
 					field: 'id',
					title: '任务编号',
					switchable: true
				}, 
				{
 					field: 'text',
					title: '体检任务',
					switchable: true
				}, ],
				  onClickRow:function (row) {
					  $("#myModal").modal('hide');
					  $("#comname1").val(row.text);
					  $("#comname3").val(row.id);
					  company_id(row.id.split("-")[0]);
					  $("#batch_id").val(row.id.split("-")[1]);
					  
					  
		           }
			}); 
	}
	function reload(){
		comname2=encodeURI(encodeURI($("#comname2").val()));
		var opt = {
	        url: "getComByBatchIpadList.action?format=json&comname="+comname2,
	        silent: true,
	        query:{
//	        	comname:comname2
	        }
	    };
//带参数 刷新
		$("#reportTable").bootstrapTable('refresh', opt);
	}
	 
      function showcolumns (){
          var columns  = document.getElementsByClassName("columns ");//获取到的是一个类数组
          for(var i=0;i<columns .length;i++){
                    columns [i].style.display = "none";        //隐藏
          }
       }
      
      
 	 // 民族
      function update(id)
      {
      	getgroupuserGrid1()
      	  var columns  = document.getElementsByClassName("columns ");//获取到的是一个类数组
          for(var i=0;i<columns .length;i++){
           columns [i].style.display = "none";
        }  
          

      }
      function getgroupuserGrid1() { 
      	
      	
  		$('#reportTable').bootstrapTable({  
  			  method: 'get',  
  			  url: 'getDatadis.action?com_Type=MZLX2',  
  			  dataType: "json",  
  			  striped: true,     //使表格带有条纹  
  			  pagination: true, //在表格底部显示分页工具栏  
//  				  pageSize: 10,  
//  				  pageNumber: 1,  
  			  pageList: [10, 20, 50],  
  			  idField: "exam_num",  //标识哪个字段为id主键  
  			  showToggle: false,   //名片格式  
  			  cardView: false,//设置为True时显示名片（card）布局  
  			  showColumns: true, //显示隐藏列    
  			  showRefresh: true,  //显示刷新按钮  
  			  singleSelect: true,//复选框只能选择一条记录  
  			  search: false,//是否显示右上角的搜索框  
  			  queryParams: function queryParams(params){
  		            var param = {    
  		                    pageNumber: params.pageNumber,    
  		                    pageSize: params.pageSize,
  		                    sortName: params.sortName,
  		                    sortOrder: params.sortOrder,
  		                    comname1:comname2
  		                };   
  		                return param;    
  		           }, //参数 
  			  clickToSelect: true,//点击行即可选中单选/复选框  
//  			  sidePagination: "server",//表格分页的位置  
  			  sidePagination : "client",// 表格分页的位置
  			  queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求  
  			  toolbar: "#toolbar", //设置工具栏的Id或者class  
  			  columns: [/*{
   					field: "ck",
  					checkbox:true,
  				},*/{
   					field: 'id',
  					title: '任务编号',
  					switchable: true
  				}, 
  				{
   					field: 'text',
  					title: '体检任务',
  					switchable: true
  				}, ],
  				  onClickRow:function (row) {
  					  $("#myModal").modal('hide');
  					  $("#comname1").val(row.text);
  					  $("#comname3").val(row.id);
  					  company_id(row.id.split("-")[0]);
  					  $("#batch_id").val(row.id.split("-")[1]);
  					  
  					  
  		           }
  			}); 
  	}
  
  //获取分组信息列表
	function getgroups(){
		if(Number($("#company_id").val())<=0){
			bootbox.alert("单位不能为空");
//			$.messager.alert("操作提示", "单位不能为空", "error");
		}else if(Number($("#batch_id").val())<=0){
			bootbox.alert("体检任务不能为空");
//			$.messager.alert("操作提示", "体检任务不能为空", "error");
		}else{
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
		 $.ajax({
				url : 'getdjtTgroupInfoDo.action',
				data : {	
							age :$("#age").val(),
							sex :$("#sex option:selected").val(),
							is_marriage : $("#is_Marriage option:selected").val(),
							company_id:$("#company_id option:selected").val(),
							batch_id:$("#batch_id").val(),
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							$("#group_id").empty();
							 var data = $.parseJSON(text);
							 var opts = "";
								for (var i = 0; i < data.length; i++) {
										opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["group_name"]+"</option>";
								}	
								$("#group_id").append(opts);  
						},
						error : function() {
							$(".loading_div").remove();
							bootbox.alert("操作错误");
						}
					});
	     }
	} 