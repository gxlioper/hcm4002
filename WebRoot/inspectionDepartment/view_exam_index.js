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
	//回车事件
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
	 var sex = undefined;
	 if($("#ck_exam_sex")[0].checked){
		 sex = $("#exam_sex").combobox('getValue');
	 }
//	 var exam_status = undefined;
//	 if($("#ck_exam_status")[0].checked){
//		 exam_status = $('#exam_status').combobox('getValue');
//	 }
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
				 "exam_status":$("input[name='exam_status']:checked").val(),
				 "doctor_name":doctor_name,
				 "exam_date1":exam_date1,
				 "exam_date2":exam_date2,
				 "customer_type":customer,
				 "sex":sex,
				 "exam_type":exam_type,   //体检类型
				 "charging_item_code":charging_item_code.toLocaleString()
		 };
	     $("#groupusershow").datagrid({
		 url:'getViewExamIndexList.action',
		 dataType: 'json',
		 queryParams:model,
	     //pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:'体检号',width:20,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:'档案号',width:18,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15,"formatter":f_customer_type},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,"formatter":f_showitem,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:15},
		 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
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
		    toolbar:toolbar,
		    rowStyler:function(index,row){   
		        if (row.weijian > 0){   
		            return 'color:red;';   
		        }   
		    },
	});
	}

   function f_examoneshow(id,exam_num){
	   $.ajax({
			url : 'getExamItemAppType.action',
			data : {exam_num:exam_num},
			type : "post",//数据发送方式   
			success : function(text) {
					if (text == '1') {//健康体检
						 $("#exam_num").textbox("setValue", "");
						 var url='getViewExamPage.action?exam_num='+exam_num;
						 newwin = window.open("", "健康体检影像科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
						 newwin.moveTo(0,0);
						 newwin.resizeTo(screen.width,screen.height-30);
						 newwin.location = url;
						 newwin.focus();
					}else if(text == '2' || text == '3'){//职业病 或健康体检和职业病
						$("#exam_num").textbox("setValue", "");
						 var url='getZybViewExamPage.action?exam_num='+exam_num;
						 newwin = window.open("", "职业病体检影像科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
						 newwin.moveTo(0,0);
						 newwin.resizeTo(screen.width,screen.height-30);
						 newwin.location = url;
						 newwin.focus();
//					}else if(text == '3'){//健康体检和职业病
//						$("#exam_num").textbox("setValue", "");
//						var url='getViewExamAllPage.action?examinfo_id='+id+'&exam_num='+exam_num;
//						 newwin = window.open("", "健康体检和职业病体检影像科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
//						 newwin.moveTo(0,0);
//						 newwin.resizeTo(screen.width,screen.height-30);
//						 newwin.location = url;
//						 newwin.focus();
					}else {
						$.messager.alert("操作提示","本科室未发现体检项目!", "error");
					}
				}
			});
	   
   }
 
   function f_showexam(val,row){
	   if(row.canExam == 'N') {//
		   return '<label title="批次已封帐且该体检者尚未结算">'+row.exam_num+'</label>';
	   } else if(row.freeze == '1'){
		   return row.exam_num;
	   }else{
		  return '<a href=\"javascript:f_examoneshow('+row.id+',\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
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
   
   function getexaminfoByExamNum(){
		 
		 var exam_num = undefined;
		 if($("#ck_exam_num")[0].checked){
			 exam_num =  $("#exam_num").val();
		 }
			 var model={
					 "exam_num":exam_num
					
			 };
		     $("#groupusershow").datagrid({
			 url:'getViewExamIndexList.action',
			 dataType: 'json',
			 queryParams:model,
		     //pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
			 columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:'体检号',width:20,"formatter":f_showexam,sortable:true},
			    {align:'center',field:'arch_num',title:'档案号',width:18,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:25},
			    {align:'center',field:'exam_types',title:'体检类型',width:15,"formatter":f_customer_type},	
			 	{align:'center',field:'user_name',title:'姓名',width:20,"formatter":f_showitem,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
			 	{align:'center',field:'phone',title:'电话',width:20},		 	
			 	{align:'center',field:'company',title:'单位',width:20,sortable:true},
			 	{align:'center',field:'set_name',title:'套餐',width:20},
			 	{align:'center',field:'join_date',title:'体检日期',width:15},
			 	{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
			 	{align:'center',field:'final_doctor',title:'总检医生',width:15},
			 	{align:'center',field:'check_doctor',title:'审核医生',width:15}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		if($("#iszyb").val()==''){
						 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
					}
		    		$("#datatotal").val(value.total);
		    		var obj = $("#groupusershow").datagrid("getRows");
		    		if(obj.length==0){
		    			//查询体检人在该科室下的项目
		    			$.ajax({
		    				url:'queryDeptCharingItemMsg.action',
		    				data:model,
		    				success:function(text){
		    					if (text.split("-")[0] == 'ok' && text.split("-")[1] == 'NoItem') {
		    						$.messager.alert("操作提示","该科室没有体检项目！", "error");
		    						return;
		    					}else if(text.split("-")[0] == 'ok' && text.split("-")[1] == 'NoPayCharing'){
		    						$.messager.alert("操作提示","该科室下体检项目项目未付费，不能检查！", "error");
		    						return;
		    					}
		    				},
		    				error:function(){
		    					$.messager.alert("警告","后台程序错误","error");
		    				}
		    			})
		    		}
		    		if(obj.length>0){
		    			if(obj[0].freeze == '1'){
							$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
							return;
						}
						if(obj[0].canExam == 'N'){
							$.messager.alert("操作提示", '批次已封帐且该体检者尚未结算,不能检查!', "error");
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
			    toolbar:toolbar,
			    rowStyler:function(index,row){   
			        if (row.weijian > 0){   
			            return 'color:red;';   
			        }   
			    },
		});
		}
   
   function f_showitem(val,row){
		return '<a href="javascript:void(0)" onclick = "showexamitem('+row.exam_num+',\''+row.exam_status+'\',this)">'+row.user_name+'</a>';
	}
	function showexamitem(exam_num,exam_status,ths){
		var height = window.screen.height-210 - $(ths).offset().top;
		if(height > 238){
			$("#results_contrast").css("top",$(ths).offset().top);
		}else{
			$("#results_contrast").css("top",$(ths).offset().top-(238-height));
		}
		
		$("#results_contrast").css("left","36%");
		$('#results_contrast').css('display','block');
		$("#examitem_div").datagrid({
			url:'getInfoItemByIdAndStatus.action',
			dataType: 'json',
			queryParams:{'exam_num':exam_num,"exam_status":exam_status},
			rownumbers:false,
			width:500,
			height:130,
			columns:[[
				 {align:'center',field:'item_code',title:'项目编码',width:40},
				 {align:'center',field:'item_name',title:'项目名称',width:60},
				 {align:'center',field:'exam_statuss',title:'检查状态',width:40},
				 {align:'center',field:'pay_statuss',title:'付费状态',width:40},
				 {align:'center',field:'picture',title:'图片',width:40,"formatter":f_flowor}
			]],
			singleSelect:true,
			collapsible:true,
			pagination: false,
			striped:true,
			nowrap:false,
			fitColumns:true
		});
	}
	function f_flowor(val,row){
		if(row.dep_num == '' || row.dep_num == 'null'){
			return '无';
		}else{
			return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.dep_num+'\',\''+row.dep_name+'\')">查看图片</a>';
		}
	}

	function show_picture(id,exam_num){
		var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+exam_num;
		newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
		newwin.focus();
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
		    		if($("#report_print_type").val() == '5'){
		    			//预览普通报告
		    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&0";//0代表体检号Y代表预览，2代表职业病
		    			location.href=exeurl;
		    	    }else{
		    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-REPORT_PRINT_TYPE","error");
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
		    		if($("#zyb_report_print_type").val() == '5'){
		    			//预览职业病报告
		    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&2";//0代表体检号Y代表预览，2代表职业病
		    			location.href=exeurl;
		    	    }else{
		    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-ZYB_REPORT_PRINT_TYPE","error");
		    	    }
		    	}
		    }
		}];
	
