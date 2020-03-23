var exam_page_show = false;
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
        	exam_page_show = true;
        	var model={"exam_num":$("#exam_num").val()};
       	  	$("#groupusershow").datagrid('load',model);
        }
    });
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
	$('#exam_num2').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num2').textbox('textbox').focus();
	
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
	if($("#examSummaryCheckDefault").val() == 1){
		$("#ck_exam_num").attr('checked',true);
	}else if($("#examSummaryCheckDefault").val() == 2){
		$("#ck_date").attr('checked',true);
	}else{
		$("#ck_exam_num").attr('checked',true);
	}
	$('#exam_doctor').combobox({
		url : 'getFinalDoctorList.action?operation_type=2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 300,//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple : false,
		onLoadSuccess : function(){
			$('#exam_doctor').combobox('setValue',$("#userid").val());
		}
	});
	if($("#is_exam_result_canfinal").val() == 3){
		$("#sh_getone").hide();
		$("#sh_getall").hide();
	}
	getgroupuserGrid();	
	getexamInfoGrid();
	getexamrejectGrid();
});
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
	$('#getexamInfoList').datagrid('reload');
	chaxun();
	chaxun3();
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
//	 var approve_status = undefined;
//	 if($("#ck_appstatus")[0].checked){
//		 approve_status = $('#appstatus').combobox('getValue');
//	 }
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
//				 "approve_status":approve_status,
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
//	 var approve_status = undefined;
//	 if($("#ck_appstatus")[0].checked){
//		 approve_status = $('#appstatus').combobox('getValue');
//	 }
	 
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
//				 "approve_status":approve_status,
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
		    striped:true,
		    toolbar:toolbar,
		    rowStyler:function(index,row){
	    		var color = "";
	    		if(row.disease_name=='★'){
	    			color ="color:#FF0000;";
	    		}
	    		return color;
	    	}
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
   
   function printreport(barids){
	   var exeurl="reportServices://&0&"+barids+"&0";
	   location.href=exeurl;
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
	   url:'getExaminedExamInfoList.action',
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
			 	{align:'center',field:'company',title:'单位',width:20},
			 	{align:'center',field:'set_name',title:'套餐',width:20},
			 	{align:'center',field:'join_date',title:'体检日期',width:15,sortable:true}
			 	]],	    	
	    onLoadSuccess:function(value){ 
		    $("#datatotal").val(value.total);
		    //将生成好的搜索框放入工具栏  (两种方法)  
            $('#search').appendTo('#tb.datagrid-toolbar'); 
            getfinalCount();
		},
		onDblClickRow : function(index, row) {	
			if(row.freeze == '1'){
				$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
				return;
			}
		    f_examoneshow(row.exam_num);
		},
		rowStyler:function(index,row){
    		var color = "";
    		if(row.disease_name=='★'){
    			color ="color:#FF0000;";
    		}
    		return color;
    	},
		height: window.screen.height-280,
		nowrap:true,
		rownumbers:false,
		singleSelect:false,
		collapsible:true,
		pagination: false,
		fitColumns:true,
	    striped:true,
	    toolbar:"#search"
	});
}
function getfinalCount(){
	$.ajax({
    	url:'getNotFinalCount.action?operation_type=2',
    	type:'post',
    	success:function(data){
    		var obj=eval("("+data+")");
    		$("#wz_count").html(obj.wz_count);
    		$("#yz_count").html(obj.yz_count);
    		$("#zj_count").html(obj.zj_count);
    		$("#t_count").html(obj.t);
    	},
    	error:function(){
    		$(".loading_div").remove();
    		$.messager.alert("警告信息","操作失败","error");
    	}
    });
}   
//获取单个审核
function huoqvzongjianone(){
	var obj = $("#getexamInfoList").datagrid('getRows');
  	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
  	$.ajax({
	    	url:'getExaminedExamInfo.action',
	    	data:{"id":obj.length},
	    	type:'post',
	    	success:function(data){
	    		$(".loading_div").remove();
	    		if(data.split('-')[0]=="ok"){
	    			$.messager.alert("提示信息",data.split('-')[1],"info");
	    			$('#getexamInfoList').datagrid('reload');
	    		} else {
	    			$.messager.alert("提示信息",data.split('-')[1],"error");
	    		}
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    	}
	    });
}
//获取全部审核
function huoqvzongjianall(){
	var obj = $("#getexamInfoList").datagrid('getRows');
  	if(obj.length > 0){
  		$.messager.alert("操作提示", '存在未审核人员，不能继续获取审核人员!', "error");
  		return;
  	}
  	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
  	$.ajax({
	    	url:'getExaminedExamInfo.action',
	    	data:{"status":'all'},
	    	type:'post',
	    	success:function(data){
	    		$(".loading_div").remove();
	    		if(data.split('-')[0]=="ok"){
	    			$.messager.alert("提示信息",data.split('-')[1],"info");
	    			$('#getexamInfoList').datagrid('reload');
	    		} else {
	    			$.messager.alert("提示信息",data.split('-')[1],"error");
	    		}
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    	}
	    });
}
function refrushwshlb(){
	$('#getexamInfoList').datagrid('reload');
}
//根据ID号和预约号获取总检
function searcher(value){
	value = value.replace(/(^\s*)|(\s*$)/g, ""); 
	if(value == ''){
		$.messager.alert("警告信息","请输入ID号或预约号!","error");
		$('#sss').searchbox('clear');
		return;
	}

		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
	  	$.ajax({
	    	url:'canExaminedExamInfoByExamNum.action',
	    	data:{"exam_num":value},
	    	type:'post',
	    	success:function(data){
	    		$(".loading_div").remove();
	    		if(data.split('-')[0]=="ok"){
	    			$.messager.alert("提示信息",data.split('-')[1],"info");
	    			$('#getexamInfoList').datagrid('reload');
	    			open_exam_summary(value);
	    		} else {
	    			$.messager.alert("提示信息",data.split('-')[1],"error");
	    		}
	    		$('#sss').searchbox('clear');
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    		$('#sss').searchbox('clear');
	    	}
	    });
}

function chaxun3(){
	var exam_num = undefined;
	 if($("#ck_exam_num2")[0].checked){
		 exam_num =  $("#exam_num2").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_data2")[0].checked){
		 time1 = $("#start_date2").datebox('getValue');
		 time2 = $("#end_date2").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname2")[0].checked){
		 user_name = $("#custname2").val();
	 }
	var done_status = $("#done_status").combobox('getValue');
	var model={
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,
				 "user_name":user_name,
				 "exam_status":done_status
		 };
	$("#getexamreject").datagrid('reload',model);
}
function getexamrejectGrid(){
		 var exam_num = undefined;
		 if($("#ck_exam_num2")[0].checked){
			 exam_num =  $("#exam_num2").val();
		 }
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_data2")[0].checked){
			 time1 = $("#start_date2").datebox('getValue');
			 time2 = $("#end_date2").datebox('getValue');
		 }
		 var user_name = undefined;
		 if($("#ck_custname2")[0].checked){
			 user_name = $("#custname2").val();
		 }
		var done_status = $("#done_status").combobox('getValue');
		var model={
					 "exam_num":exam_num,
					 "time1":time1,	
					 "time2":time2,
					 "user_name":user_name,
					 "exam_status":done_status
			 };
		     $("#getexamreject").datagrid({
			 url:'getAlreadyRejectList.action',
			 dataType: 'json',
			 queryParams:model,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
			 columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:'体检号',width:100,sortable:true},
			    {align:'center',field:'arch_num',title:'档案号',width:100,sortable:true},
			    {align:'center',field:'exam_types',title:'体检类型',width:60},	
			 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
			 	{align:'center',field:'join_date',title:'体检日期',width:100,sortable:true},
			 	{align:'center',field:'final_date',title:'总检日期',width:140,sortable:true},
			 	{align:'center',field:'final_doctor',title:'总检医生',width:80},
			 	{align:'center',field:'reject_doctor',title:'驳回医生',width:80},
			 	{align:'center',field:'reject_time',title:'驳回日期',width:140},
			 	{align:'center',field:'reject_context',title:'驳回原因',width:200},
			 	{align:'center',field:'done_statuss',title:'处理状态',width:80},
			 	{align:'center',field:'done_time',title:'处理日期',width:140},
			 	{align:'center',field:'company',title:'单位',width:220,sortable:true},
			 	{align:'center',field:'set_name',title:'套餐',width:150,sortable:true}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$("#datatotal").val(value.total);
		    	
		    	},
		    	onDblClickRow : function(index, row) {	
		    		
				},
				height: window.screen.height-380,
				nowrap:true,
				fit:false,
				rownumbers:false,
		    	singleSelect:false,
			    collapsible:true,
				pagination: true,
			    fitColumns:false,
			    striped:true
		});
	}
 var toolbar=[{
 	text:'批量复审',
	width:90,
	iconCls:'icon-edit',
    handler:piliangfushen
 }];
 
 //批量复审
function piliangfushen(){
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	if(checkedItems.length <=0){
    		$.messager.alert("操作提示", "请选择一个体检人员","error");
    		return;
    	}
    	var piliang= new Array();
	    $.each(checkedItems, function(index, item){
	    	piliang.push('\''+item.exam_num+'\'');
	    });
	    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	    $.ajax({
	    	url:'getBatchRetrial.action',
	    	data:{
	    		exam_num:piliang.toString()
	    	},
	    	type:'post',
	    	success:function(data){
	    		$(".loading_div").remove();
	    		if(data==""){
	    			$.messager.alert("提示信息","已复审","info");
	    			$('#groupusershow').datagrid('reload');
	    		} else {
	    			$.messager.alert("复审失败",data,"error");
	    		}
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    	}
	    })
}