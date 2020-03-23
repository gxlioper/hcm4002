$(document).ready(function () {

	$('#exam_num').textbox('textbox').focus();  
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });
	
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	//设置table的高度
        	document.getElementById("keShi_table").style.height= window.screen.height-380+"px";
        	table_page = false;
        	if($("#is_depinspect_checked").val() == 'Y'){
        		$.ajax({
        			url:'checkDepExaminfoItem.action',
        			data : {'exam_num':$('#exam_num').textbox('getValue')},
        			type : "post",//数据发送方式   
        			success:function(data){
        				if(data.split("-")[0] != 'ok'){
        					getExamInfoByNum();
        					setCookie('exam_num',$('#exam_num').textbox('getValue'));
        				}else{
        					$.messager.confirm('提示信息','体检人本科室项目已检查,是否再次进入修改？',function(r){    
        					    if (r){
        					    	getExamInfoByNum();
        					    	setCookie('exam_num',$('#exam_num').textbox('getValue'));
        					    }    
        					});
        				}
        			}
        		});
        	}else{
        		getExamInfoByNum();
        		setCookie('exam_num',$('#exam_num').textbox('getValue'));
        	}
        }
    });
	$('#exam_num').textbox('setValue',getCookie('exam_num'));
	if($('#exam_num').textbox('getValue') != ''){
		getExamInfoByNum(1);
	}
	$('#statusss').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	$('#doctor_name').combobox({
		url : 'getDepuser.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'chi_Name',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			
		}
	});
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'

	});
	
	//体检类型
	$('#exam_type').combobox({
		url : 'getDatadis.action?com_Type=EXAMTYPE',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	
	//收费项目
	$('#charging_item_id').combobox({
		url : 'getChargingItemListByDepId.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'item_code',
		textField : 'item_name',
		multiple : true,
		onLoadSuccess : function(data) {
			
		}
	});
	
	getgroupuserGrid();
});
//-------------------------------------------获取系统时间----------------------------------------------------
//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

//-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
 var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='companychangshow.action';
 	var data={"name":com_Namess};
 	load_post(url,data,select_com_list_sess);
 	}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
	function setvalue(id,name){
		$("#company_id").val(id);
		$("#com_Name").textbox('setValue',name);
		$("#com_name_list_div").css("display","none");		
	}

	
//单位失去焦点
var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_com_list_mover(){
	hc_mous_select1=true;
	}
function select_com_list_amover(){
	hc_mous_select1=false;
	}

//体检号报到
function examnumenter()// enter 键  
{
			if(($("#exam_num").val()!='')&&($("#exam_num").val().length>0)){
				getgroupuserGrid();
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
			 $.ajax({
					url : 'djtcheckexamInfoStatus.action',
					data : {
						    exam_num:$("#exam_num").val()
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {//一个体检信息
									   $("#exam_num").textbox('setValue',text.split("-")[1]);
										$.messager.confirm('提示信息','是否报到？',function(r){
										if(r){
											
											$("#exam_num").textbox('textbox').select();
											$('#exam_num').textbox('textbox').focus();
											 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
											 $("body").prepend(str);
											$.ajax({
												url : 'djtexamInfoStatusdo.action',
												data : {
													    exam_num:$("#exam_num").val()
														},
														type : "post",//数据发送方式   
														success : function(text) {
															$(".loading_div").remove();
															if (text.split("-")[0] == 'ok') {
																$("#exam_num").textbox('textbox').select();
																$('#exam_num').textbox('textbox').focus();
																getgroupuserGrid();
																 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
																	 printDJD_exe($("#exam_num").val());
																 }
															} else {
																$("#exam_num").textbox('textbox').select();
																$('#exam_num').textbox('textbox').focus();
																$.messager.alert("操作提示", text.split("-")[1], "error");
															}
															
														}
													});
	
										}
										});
									
								}  else {
									$("#exam_num").textbox('textbox').select();
									$('#exam_num').textbox('textbox').focus();
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
								
							}
						});
		 	}     
}

function exam_baodao(exam_num,id){
	$.messager.confirm('提示信息','是否报到？',function(r){
		if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'djtexamInfoStatusdo.action',
				data : {
					    exam_num:exam_num
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								f_examoneshow(id,exam_num);
							} else {
								$.messager.alert("操作提示", text.split("-")[1], "error");
							}
							
						}
					});

		}
		});
}


 //---------------------------------------显示人员------------------------------------------------------
var table_page = false;

function brushpagecharging(){
	if(table_page){
		getgroupuserGrid();
	}else{
		$("#groupusershow").datagrid('reload');
	}
}

function shouye_chaxun(){
	table_page = true;
	brushpagecharging();
}

function getExamInfoByNum(type){
	if($(".pagination-info").html() != undefined){
		getroupGridByNum();
	}
		var exam_num = undefined;
		if($("#ck_exam_num")[0].checked){
			exam_num =  $("#exam_num").val();
		}
		$.ajax({
			url:'getDepExamInfoUserLis.action?exam_num='+exam_num,
			success:function(data){
				var obj = $("#groupusershow").datagrid("getRows");
				var exam = eval('('+data+')');
				for(i=0;i<exam.rows.length;i++){
					var flag = true;
					for(j=0;j<obj.length;j++){
						if(exam.rows[i].exam_num == obj[j].exam_num){
							flag = false;
						}
					}
					if(flag){
						$("#groupusershow").datagrid("appendRow",exam.rows[i]);
					}
					if(type == 1 && exam.rows[i].weijian <= 0){
						return;
					}
					if(exam.rows[i].freeze == '1'){
						$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
						return;
					}
					if(exam.rows[i].status == 'Y'){
	    				exam_baodao(exam.rows[i].exam_num,exam.rows[i].id);
	    			}else{
	    				f_examoneshow(exam.rows[i].id,exam.rows[i].exam_num);
	    			}
				}
			}
		});
	
}

function getroupGridByNum(){
	var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
		 var model={
				 "exam_num":exam_num
		 };
		 
	     $("#groupusershow").datagrid({
		 url:'getDepExamInfoUserLis.action',
		 dataType: 'json',
		 queryParams:model,
		 columns:[[
           {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam},
		    {align:'center',field:'arch_num',title:dahname,width:18},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15,"formatter":f_customer_type},	
		 	{align:'center',field:'user_name',title:'姓名',width:20},
		 	{align:'center',field:'sex',title:'性别',width:10},
		 	{align:'center',field:'age',title:'年龄',width:10},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20},
		 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
				}
	    		$("#datatotal").val(value.total);
//	    		var obj = $("#groupusershow").datagrid("getRows");
//	    		if(obj.length>0){ 
//	    			if(obj[0].freeze == '1'){
//						$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
//						return;
//					}
//	    			if(obj[0].status == 'Y'){
//	    				exam_baodao(obj[0].exam_num,obj[0].id);
//	    			}else{
//	    				f_examoneshow(obj[0].id,obj[0].exam_num);
//	    			}
//	    		}
	    	},
	    	onDblClickRow : function(index,row) {
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		if(row.status == 'Y'){
    				exam_baodao(row.exam_num,row.id);
    			}else{
    				f_examoneshow(row.id,row.exam_num);
    			}
			},
			height: window.screen.height-400,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true,
		    rowStyler:function(index,row){   
		        if (row.weijian > 0){   
		            return 'color:red;';   
		        }   
		    },
		    toolbar:toolbar
	});
}

 /**
  * 
  */

 function getgroupuserGrid(valu){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var employeeID = undefined;
	 if($("#ck_searchemployeeID")[0].checked){
		 employeeID = $("#searchemployeeID").val();
	 }
	 var arch_num = undefined;
	 if($("#ck_arch_num")[0].checked){
		 arch_num = $("#arch_num").val();
	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	/* var exam_status = undefined;
	 if($("#ck_exam_status")[0].checked){
		 exam_status = $('#exam_status').combobox('getValue');
	 }*/
	 var doctor_name = undefined;
	 if($("#ck_doctor_name")[0].checked){
		 doctor_name = $('#doctor_name').combobox('getValue');
	 }
	 var exam_date1 = undefined,exam_date2 = undefined;
	 if($("#ck_exam_date")[0].checked){
		 exam_date1 = $("#exam_date1").datebox('getValue');
		 exam_date2 = $("#exam_date2").datebox('getValue');
	 }
	 var sex = undefined;
	 if($("#ck_exam_sex")[0].checked){
		 sex = $("#exam_sex").combobox('getValue');
	 }
	 var now_time = $("#nowtime").val();

	 var customer_type = undefined;
	 if($("#ck_tjlx")[0].checked){
		 customer_type = $('#tjlx').combobox('getValue');
	 }
	 
	 var exam_type = undefined;
	 if($("#ck_examType")[0].checked){
		 exam_type = document.getElementsByName("exam_type")[0].value;
	 }
	 
	 var charging_item_code = new Array();
	 if($("#ck_charging_item")[0].checked){
		 var charging_item_codes =	$("#charging_item_id").combobox('getValues');
		  for(i=0;i<charging_item_codes.length;i++){
			  charging_item_code.push("'"+charging_item_codes[i]+"'");
		  }
	 }
	 
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "employeeID":employeeID,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "exam_status":valu,
				 "doctor_name":doctor_name,
				 "exam_date1":exam_date1,
				 "exam_date2":exam_date2,
				 "customer_type":customer_type,
				 "sex":sex,
				 "exam_type":exam_type,  //体检类型
				 "charging_item_code":charging_item_code.toLocaleString()
		 };
		 if(valu != undefined && (valu=='y' || valu=='n')){
			 model = {
				"exam_status":valu.toUpperCase(),
				"time1": now_time,
				"time2": now_time
			 };
		 }
	     $("#groupusershow").datagrid({
		 url:'getDepExamInfoUserLis.action',
		 dataType: 'json',
		 queryParams:model,
		 //toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15,"formatter":f_customer_type},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20},
		 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
				}
	    		$("#datatotal").val(value.total);
	    		//加载一个查询数量的方法
	    		//alert(valu+"===="+value.total+"----");
	    		if(Number(value.total)>0 && (valu == undefined || valu=='y' || valu=='n')){
	    			$.ajax({
						url : 'queryCountTypeUser.action',
						data : model,
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == 'ok') {
								$("#yijian").html(text.split("-")[1]); $("#weijian").html(text.split("-")[2]);
								$("#jiancha").html(text.split("-")[3]); $("#qijian").html(text.split("-")[4]);
								$("#yanqi").html(text.split("-")[5]);
							} else {
								$.messager.alert("操作提示", text.split("-")[1], "error");
								$("#yijian").html(0); $("#weijian").html(0); $("#jiancha").html(0);
								$("#qijian").html(0); $("#yanqi").html(0);
							}
						}
					});
	    		}else if(Number(value.total)==0 && valu == undefined){
	    			$("#yijian").html(0); $("#weijian").html(0); $("#jiancha").html(0);
					$("#qijian").html(0); $("#yanqi").html(0);
	    		}
	    		
	    		
	    	},
	    	onDblClickRow : function(index,row) {
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		if(row.status == 'Y'){
    				exam_baodao(row.exam_num,row.id);
    			}else{
    				f_examoneshow(row.id,row.exam_num);
    			}
			},
			height: window.screen.height-400,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    rowStyler:function(index,row){   
		        if (row.weijian > 0){   
		            return 'color:red;';   
		        }   
		       /* if (row.exam_status == 'N'){   
		            return 'color:red;';   
		        }*/ 
		    },  
	        toolbar:toolbar	       
	});
	}
 

 
 function f_showexam(val,row){
	 if(row.freeze == '1'){
		 return row.exam_num;
	 }else if(row.status == 'Y'){
		 return '<a href=\"javascript:exam_baodao(\''+row.exam_num+'\',\''+row.id+'\')\">'+row.exam_num+'</a>';
	 }else{
		 return '<a href=\"javascript:f_examoneshow(\''+row.id+'\',\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
	 }
}
  //特殊人员
 function f_customer_type(val,row){
 	var is_custom_identification = $("#is_custom_identification").val();
 	if(is_custom_identification != '' && is_custom_identification != null){
 		var customer_types =  is_custom_identification.split(",")
 		for(var i = 0; i < customer_types.length ; i++){
 			if(row.customer_type_id == customer_types[i]){
 				return val+" <font size = '4' color='red'>★</font>";
 			}
 		}
 	}
 	return val;
 }

   function f_examoneshow(id,exam_num){
	   $.ajax({
			url : 'getExamItemAppType.action',
			data : {exam_num:exam_num},
			type : "post",//数据发送方式   
			success : function(text) {
				$("#exam_num").textbox("setValue", "");
				   var url='inspectshow_BSR.action?exam_num='+exam_num+'&app_type='+text;
				   newWindow = window.open(url, "电测听科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
				   newWindow.focus();
			}
		});
   }
   
 
   /**
	  * 定义工具栏
	  */
	 var toolbar=[{
			text:'普通报告预览',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	var checkedItems = $('#groupusershow').datagrid('getChecked');
//	    	    $.each(checkedItems, function(index, item){
//	    	        barids.push(item.exam_num);
//	    	    });
	    	    if(checkedItems.length != 1 ){
		    		$.messager.alert("操作提示", "请选择一个体检人员","error");
		    	}else{
		    		var item = checkedItems[0];
		    		if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
		    			printreport(item.exam_num);
		    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
		    	    	printreport4(item.exam_num);
		    	    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
		    	    	printreport5(item.id,item.exam_num,1);
		    	    }else{
		    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		    	    }
		    	}
		    }
		},{
			id:9,
			text:'职业病报告预览',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	var checkedItems = $('#groupusershow').datagrid('getChecked');
//	    	    $.each(checkedItems, function(index, item){
//	    	        barids.push(item.exam_num);
//	    	    });
	    	    if(checkedItems.length != 1 ){
		    		$.messager.alert("操作提示", "请选择一个体检人员","error");
		    	}else{
		    		var item = checkedItems[0];
		    		if($("#zyb_barcode_print_type").val() == '5'){//调用5.0职业病报告预览
		    	    	printreport5(item.id,item.exam_num,2);
		    	    }else{
		    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
		    	    }
		    	}
		    }
		}];
	 function printreport(barids){
		   var exeurl="reportServices://&0&"+barids+"&0";
		   location.href=exeurl;
	   }
   
	//新版本报告预览4.0
	 function printreport4(exam_num){
	 	var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
	 	location.href=exeurl;
	 }

	 function printreport5(id,exam_num,type){
		 if(type == 1){//预览普通报告
			var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
			location.href=exeurl;
		}else if(type == '2'){//预览职业病报告
			var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&opreport&"+exam_num;
			location.href=exeurl;
		}
	 }
   
	
	 
