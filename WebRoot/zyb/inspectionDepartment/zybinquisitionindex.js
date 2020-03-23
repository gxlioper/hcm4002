$(document).ready(function () {
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
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
        	getexaminfoByExamNum();
        }
    });
		
	$('#statusss').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
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
	$('#results_contrast').mouseleave(function(){
		 $('#results_contrast').css('display', 'none');
	});
	getgroupuserGrid();	
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
function shuaxing(){
	getgroupuserGrid();
} 
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
	 var doctor_name = undefined;
	 if($("#ck_doctor_name")[0].checked){
		 doctor_name = $('#doctor_name').combobox('getValue');
	 }
	 var exam_date1 = undefined,exam_date2 = undefined;
	 if($("#ck_exam_date")[0].checked){
		 exam_date1 = $("#exam_date1").datebox('getValue');
		 exam_date2 = $("#exam_date2").datebox('getValue');
	 }
	 var customer = undefined;
	 if($("#ck_tjlx")[0].checked){
		 customer = $('#tjlx').combobox('getValue');
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
				 "exam_status":$("input[name='exam_status']:checked").val(),
				 "doctor_name":doctor_name,
				 "exam_date1":exam_date1,
				 "exam_date2":exam_date2,
				 "customer_type":customer
		 };
	     $("#groupusershow").datagrid({
		 url:'zybInquisitionIndexList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:15},
		 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
	    	onDblClickRow : function(index, row) {	 
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		f_examoneshow(row.exam_num,row.apptype);
			},
			height: window.screen.height-400,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    toolbar:toolbar
	});
	}

   function f_examoneshow(exam_num,apptype){
	   $.ajax({
			url:'queryIsTiJianType.action',
			type:'post',
			data: {
				exam_num:exam_num
			},
			success:function(data){
				var message=eval("("+data+")");
				isOpenWenZhen(message,exam_num);
			},
			error:function(){
				$.messager.alert('提示信息','查询问题失败！','error');
			}
		});
   }
 
   function f_showexam(val,row){
	   if(row.freeze == '1'){
		   return row.exam_num;
	   }else{
		  return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\','+row.apptype+')\">'+row.exam_num+'</a>';
	   }
   }
 //判断是否打开问诊页面
   function isOpenWenZhen(pra,exam_num){
   	var dep_id = $("#dep_id").val();
   	var url="";
   	var code = "";
   	var mess = "";
   	if(pra == 'ZYJKJC'){
   		url='brainShowPage.action?quest_sub_code=QS06&exam_num='+exam_num+"&dep_id="+dep_id;
   		var code = "QS06";
   		mess = "非放射性问诊";
   	}else if(pra == 'FSJKJC'){
   		url='brainShowPage.action?quest_sub_code=QS07&exam_num='+exam_num+"&dep_id="+dep_id;
   		var code = "QS07";
   		mess = "放射性问诊";
   	}
   	$.ajax({
   		url:'queryIsNeiWaiAns.action',
   		type:'post',
   		data: {
   			peId:exam_num,
   			quest_sub_code:code
   		},
   		success:function(data){
   			var message=eval("("+data+")");
   			if(message!="false"){
   				if(dep_id=='173' && message.substring(0,1) == "0"){
   					newwin = window.open(url, mess, "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
   					newwin.focus();
   				}else if(dep_id=='168' && message.substring(2,3) == "0"){
   					newwin = window.open(url, mess, "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
   					newwin.focus();
   				}else{
   					newwin = window.open(url, mess, "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
   	   				newwin.focus();
   				}
   			}else if(message=="false"){
   				newwin = window.open(url, mess, "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
   				newwin.focus();
   			}
   		},
   		error:function(){
   			$.messager.alert('提示信息','加载问题失败！','error');
   		}
   	});
   }
   function getexaminfoByExamNum(){
		 
		 var exam_num = undefined;
		 if($("#ck_exam_num")[0].checked){
			 exam_num =  $("#exam_num").val();
		 }
			 var model={
					 "exam_num":exam_num
					
			 };
		     $("#groupusershow").datagrid({
			 url:'zybInquisitionIndexList.action',
			 dataType: 'json',
			 queryParams:model,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
			 columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
			    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:25},
			    {align:'center',field:'exam_types',title:'体检类型',width:15},	
			 	{align:'center',field:'user_name',title:'姓名',width:20,"formatter":f_showitem,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
			 	{align:'center',field:'phone',title:'电话',width:20},		 	
			 	{align:'center',field:'company',title:'单位',width:20},
			 	{align:'center',field:'set_name',title:'套餐',width:20},
			 	{align:'center',field:'join_date',title:'体检日期',width:15},
			 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
			 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
			 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$("#datatotal").val(value.total);
		    		var obj = $("#groupusershow").datagrid("getRows");
		    		if(obj.length>0){
		    			if(obj[0].freeze == '1'){
							$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
							return;
						}
		    			f_examoneshow(obj[0].id,obj[0].exam_num);
		    		}
		    	},
		    	onDblClickRow : function(index, row) {
		    		if(row.freeze == '1'){
						$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
						return;
					}
		    		f_examoneshow(row.id,row.exam_num);
				},
				height: window.screen.height-400,
				rownumbers:false,
		    	singleSelect:false,
			    collapsible:true,
				pagination: true,
			    fitColumns:true,
			    striped:true,
			    toolbar:toolbar
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
