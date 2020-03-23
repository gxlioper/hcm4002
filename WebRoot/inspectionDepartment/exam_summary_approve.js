$(document).ready(function () {
//	$('#serch_exam_num').textbox('textbox').focus();  
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
        	var model={"exam_num":$("#exam_num").val()};
       	  	$("#groupusershow").datagrid('load',model);
        }
    });
	$('#serch_exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#serch_exam_num').textbox('textbox').focus();
//	$('#customer_type').combobox({
//		url : 'getDatadis.action?com_Type=RYLB',
//		editable : false, //不可编辑状态
//		cache : false,
//		panelHeight : 200,//自动高度适合
//		valueField : 'id',
//		textField : 'name',
//		onLoadSuccess : function(data) {
//		}
//	});
//	$('#tjlx').combobox({
//		url : 'getDatadis.action?com_Type=TJLX',
//		editable : false, //不可编辑状态
//		cache : false,
//		panelHeight : 'auto',//自动高度适合
//		valueField : 'id',
//		textField : 'name'
//
//	});
	$('#exam_doctor').combobox({
		url : 'getFinalDoctorList.action?operation_type=2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 300,//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple : true,
		onLoadSuccess : function(){
			$('#exam_doctor').combobox('setValue',$("#userid").val());
		}
	});
	$('#serch_exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getapproveinfo();
        	$('#serch_exam_num').textbox('textbox').val('');
        }
    });
	getgroupuserGrid();	
	getexamInfoGrid();
	getfinalCount();
});

function getfinalCount(){
	$.ajax({
    	url:'getNotFinalCount.action?operation_type=2',
    	type:'post',
    	success:function(data){
    		var obj=eval("("+data+")");
    		$("#wz_count").html(obj.wz_count);
    		$("#yz_count").html(obj.yz_count);
    		$("#zj_count").html(obj.zj_count);
    	},
    	error:function(){
    		$(".loading_div").remove();
    		$.messager.alert("警告信息","操作失败","error");
    	}
    });
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
 //---------------------------------------显示人员------------------------------------------------------

function shuxing(){
	$("#getexamInfoList").datagrid('reload');
	chaxun();
//	getfinalCount();
}

function chaxun(){
	var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var final_time1 = undefined,final_time2 = undefined;
	 if($("#ck_final_date")[0].checked){
		 final_time1 = $("#final_time1").datebox('getValue');
		 final_time2 = $("#final_time2").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
//	 var arch_num = undefined;
//	 if($("#ck_arch_num")[0].checked){
//		 arch_num = $("#arch_num").val();
//	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var exam_doctor = undefined;
	 if($("#ck_exam_doctor")[0].checked){
		 var doctor_obj = $('#exam_doctor').combobox('getValues');
		 var flag = false; 
		 for(i=0;i<doctor_obj.length;i++){
			 if(doctor_obj[i] == 0){
				 flag = true;
			 }
		 }
		 if(flag){
			 exam_doctor = '';
		 }else{
			 exam_doctor = doctor_obj.toString();
		 }
	 }
//	 var customer_type_id = undefined;
//	 if($("#ck_customer_type")[0].checked){
//		 customer_type_id = $('#customer_type').combobox('getValue');
//	 }
//	 var tijianleixin = undefined;
//	 if($("#tijianleixin_f")[0].checked){
//		 tijianleixin = $('#tijianleixin').combobox('getValue');
//	 }
//	 var customer_type = undefined;
//	 if($("#ck_tjlx")[0].checked){
//		 customer_type = $('#tjlx').combobox('getValue');
//	 }
	 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,
				 "final_time1":final_time1,
				 "final_time2":final_time2,
				 "user_name":user_name,
//				 "arch_num":arch_num,
				 "id_num":id_num,
				 "exam_doctor":exam_doctor
//				 "customer_type_id":customer_type_id,
//				 "tijianleixin":tijianleixin,
//				 "customer_type":customer_type
		 };
	 $("#groupusershow").datagrid('reload',model);
}
//+++++++++++++++提示详细未检查项目*-鼠标悬停--------------

 /**
  * 
  */
 function getgroupuserGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var final_time1 = undefined,final_time2 = undefined;
	 if($("#ck_final_date")[0].checked){
		 final_time1 = $("#final_time1").datebox('getValue');
		 final_time2 = $("#final_time2").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
//	 var arch_num = undefined;
//	 if($("#ck_arch_num")[0].checked){
//		 arch_num = $("#arch_num").val();
//	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val()
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var exam_doctor = undefined;
	 if($("#ck_exam_doctor")[0].checked){
		 var doctor_obj = $('#exam_doctor').combobox('getValues');
		 var flag = false; 
		 for(i=0;i<doctor_obj.length;i++){
			 if(doctor_obj[i] == 0){
				 flag = true;
			 }
		 }
		 if(flag){
			 exam_doctor = '';
		 }else{
			 exam_doctor = doctor_obj.toString();
		 }
	 }
//	 var customer_type_id = undefined;
//	 if($("#ck_customer_type")[0].checked){
//		 customer_type_id = $('#customer_type').combobox('getValue');
//	 }
//	 var tijianleixin = undefined;
//	 if($("#tijianleixin")[0].checked){
//		 tijianleixin = $('#tijianleixin').combobox('getValue');
//	 }
//	 var customer_type = undefined;
//	 if($("#ck_tjlx")[0].checked){
//		 customer_type = $('#tjlx').combobox('getValue');
//	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,
				 "final_time1":final_time1,
				 "final_time2":final_time2,
				 "user_name":user_name,
//				 "arch_num":arch_num,
				 "id_num":id_num,
				 "exam_doctor":exam_doctor
//				 "customer_type":customer_type,
//				 "tijianleixin":tijianleixin,
//				 "customer_type_id":customer_type_id
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamSummaryApproveList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},
		 	{align:'center',field:'customer_type',title:'人员类型',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
		 	{align:'center',field:'set_name',title:'套餐',width:20,sortable:true},
		 	{align:'center',field:'join_date',title:'体检日期',width:15,sortable:true},
		 	{align:'center',field:'final_date',title:'总检日期',width:15,sortable:true},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:15},
		 	{align:'center',field:'yl',title:'预览报告',width:20,"formatter":f_showreport}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
//		    	var obj = $("#groupusershow").datagrid("getRows");
//		    	if(obj.length>0){
//		    		if(obj[0].freeze == '1'){
//						$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
//						return;
//					}
//		    		f_examoneshow(obj[0].exam_num);
//		    	}

	    	},
	    	onDblClickRow : function(index, row) {	
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		f_examoneshow(row.exam_num);
			},
			height: window.screen.height-430,
			nowrap:true,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}

 function f_showreport(val,row){
		return '<a href=\"javascript:showreport(\''+row.exam_num+'\')\">预览</a>';
	}
	function showreport(exam_num){
		if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
			printreport(exam_num);
		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
	    	printreport4(exam_num);
		}else if($("#barcode_print_type").val() == '5'){
			var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
			location.href=exeurl;
	    }else{
	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	    }
		
	}
	function printreport(exam_num){
		var exeurl="reportServices://&0&"+exam_num+"&0";
		location.href=exeurl;
	}
	//新版本报告预览4.0
	function printreport4(exam_num){
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+exam_num;
		location.href=exeurl;
	}

   function f_examoneshow(id){
			$.ajax({
				url:'getNotExamCharingItemList.action?exam_num='+id,
				type:'post',
				success:function(data){
					if(data == 'null' || data == '[]'){
						open_exam_summary(id);
					}else{
						var obj = eval("("+data+")");
						var item_name = new Array();
						for(i=0;i<obj.length;i++){
							item_name.push(obj[i].item_name);
						}
						
						$.messager.confirm('提示信息','<div style="margin-left: 40px;font-size: 14px;">未检查项目：</br>'+item_name.toString()+' ！</br><span style="color: red;font-size: 20px;">是否强下总检？</span></div>',function(r){
							if(r){
								open_exam_summary(id);
							}
						});
					}
				}
			});
   }
   
   function open_exam_summary(exam_num){
	   var url='getExamSummaryPage.action?exam_num='+exam_num+'&operation_type=2';
	   newwin = window.open("", "审核室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height - 30);
	   newwin.location = url;
	   newwin.focus();
   }
   
   function f_showexam(val,row){
	   if(row.freeze == '1'){
			return row.exam_num;
		}else{
			return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
		}
   }
 function getapproveinfo(){
	 $.ajax({
			url:'getExamSummaryApproveOrCensoring.action',
			data:{'exam_num':$("#serch_exam_num").val().replace(/(^\s*)|(\s*$)/g, ""),'operation_type':2},
			type:'post',
			success:function(data){
				$("#error").html('');
				if(data.split('-')[0] == 'ok'){
					open_exam_summary($("#serch_exam_num").val().replace(/(^\s*)|(\s*$)/g, ""));
					getfinalCount();
				}else{
					$("#serch_exam_num").select();
					$("#serch_exam_num").focus();
					$("#error").html(data.split('-')[1]);
				}
			}
		});
 }

 function getexamInfoGrid(){
	   $("#getexamInfoList").datagrid({
	   url:'getCanExamSummaryApproveList.action',
	   dataType: 'json',
	   pageSize: 15,//每页显示的记录条数，默认为10 
	   pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
	   columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
			    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:25},
			    {align:'center',field:'exam_types',title:'体检类型',width:15},	
			 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
			 	{align:'center',field:'phone',title:'电话',width:20},
			 	{align:'center',field:'customer_type',title:'人员类型',width:20},		 	
			 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
			 	{align:'center',field:'set_name',title:'套餐',width:20,sortable:true},
			 	{align:'center',field:'join_date',title:'体检日期',width:15,sortable:true},
			 	{align:'center',field:'final_date',title:'总检日期',width:15,sortable:true},
			 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
			 	{align:'center',field:'check_doctor',title:'审核医生',width:15},
			 	{align:'center',field:'yl',title:'预览报告',width:20,"formatter":f_showreport}
			 	]],	    	
	    onLoadSuccess:function(value){ 
		    $("#datatotal").val(value.total);
		},
		onDblClickRow : function(index, row) {	
			if(row.freeze == '1'){
				$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
				return;
			}
		    f_examoneshow(row.exam_num);
		},
		height: window.screen.height-280,
		nowrap:true,
		rownumbers:false,
		singleSelect:false,
		collapsible:true,
		pagination: false,
		fitColumns:true,
	    striped:true
	});
}
