var exam_page_show = false;
var teamAmountViewFlag;

$(document).ready(function () {
	teamAmountViewFlag = $("#teamAmountViewFlag").val();
	
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
//      	var model={"exam_num":$("#exam_num").val()};
//     	  	$("#groupusershow").datagrid('reload',model);
       	  	getgroupuserGrid();
        }
    });
		
	$('#exam_status').combobox({
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
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			
		}
	});
	$('#customer_type').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
		}
	});
	if($('#is_batch').val()=='Y'){
			toolbar.push({
				text:'批量审核',
				width:90,
				iconCls:'icon-edit',
			    handler:piliangshenhe
			},
                {
                    text:'批量复审',
                    width:90,
                    iconCls:'icon-edit',
                    handler:piliangfushen
                })
	}
	if($("#examSummaryCheckDefault").val() == 1){
		$("#ck_exam_num").attr('checked',true);
	}else if($("#examSummaryCheckDefault").val() == 2){
		$("#ck_date").attr('checked',true);
	}else{
		$("#ck_exam_num").attr('checked',true);
	}
	$('#tjlx').combobox({
        url : 'getOccuphyclass.action',
        editable : false, //不可编辑状态
        cache : false,
        panelHeight : 'auto',//自动高度适合
        valueField : 'occuphyexaclass_name',
        textField : 'occuphyexaclass_name'
    });
	getgroupuserGrid();	
	getexamInfoGrid();
//	getexamrejectGrid();
});
function piliangshenhe(){
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
	    	url:'getBatchAudit.action',
	    	data:{
	    		exam_num:piliang.toString()
	    	},
	    	type:'post',
	    	success:function(data){
	    		$(".loading_div").remove();
	    		if(data==""){
	    			$.messager.alert("提示信息","已审核","info");
	    			$('#groupusershow').datagrid('reload');
	    		} else {
	    			$.messager.alert("审核失败",data,"error");
	    		}
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    	}
	    })
	    
	    
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
	chaxun();
	chaxun2();
	chaxun3();
	$('#exam_num').textbox('textbox').focus();
	$('#exam_num').textbox('textbox').select();
}

function chaxun(){
	exam_page_show = false;
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
	 var exam_status = undefined;
	 if($("#ck_status")[0].checked){
		 exam_status = $("#exam_status").combobox('getValue');
	 }
	 var doctor_name = undefined;
	 if($("#ck_doctor_name")[0].checked){
		 doctor_name = $('#doctor_name').combobox('getValue');
	 }
	 var approve_status = undefined;
	 if($("#ck_appstatus")[0].checked){
		 approve_status = $('#appstatus').combobox('getValue');
	 }
	 var customer_type_id = undefined;
	 if($("#ck_customer_type")[0].checked){
		 customer_type_id = $('#customer_type').combobox('getValue');
	 }
	 var tijianleixin = undefined;
	 if($("#tijianleixin_f")[0].checked){
		 tijianleixin = $('#tijianleixin').combobox('getValue');
	 }
	 var is_guide_back = undefined;
	 if($("#ck_is_guide_back")[0].checked){
		 is_guide_back = $('#is_guide_back').combobox('getValue');
	 }
    var occuphyexaclass_name = undefined;
    if($("#ck_tjlx")[0].checked){
        occuphyexaclass_name = $('#tjlx').combobox('getValue');
    }
	 var model={
			 	 "app_type":$("#app_type").val(),
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,
				 "final_time1":final_time1,
				 "final_time2":final_time2,
				 "user_name":user_name,
				 "employeeID":'',
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "exam_status":exam_status,
				 "exam_doctor":doctor_name,
				 "approve_status":approve_status,
				 "customer_type_id":customer_type_id,
				 "tijianleixin":tijianleixin,
				 "is_guide_back":is_guide_back,
				 "occuphyexaclass_name":occuphyexaclass_name,
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
	 var exam_status = undefined;
	 if($("#ck_status")[0].checked){
		 exam_status = $("#exam_status").combobox('getValue');
	 }
	 var doctor_name = undefined;
	 if($("#ck_doctor_name")[0].checked){
		 doctor_name = $('#doctor_name').combobox('getValue');
	 }
	 var approve_status = undefined;
	 if($("#ck_appstatus")[0].checked){
		 approve_status = $('#appstatus').combobox('getValue');
	 }
	 var customer_type_id = undefined;
	 if($("#ck_customer_type")[0].checked){
		 customer_type_id = $('#customer_type').combobox('getValue');
	 }
	 var tijianleixin = undefined;
	
	 if($("#tijianleixin")[0].checked){
		 tijianleixin = $('#tijianleixin').combobox('getValue');
	 }
     var occuphyexaclass_name = undefined;
     if($("#ck_tjlx")[0].checked){
         occuphyexaclass_name = $('#tjlx').combobox('getValue');
     }
	 
		 var model={
				 "app_type":$("#app_type").val(),
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,
				 "final_time1":final_time1,
				 "final_time2":final_time2,
				 "user_name":user_name,
				 "employeeID":'',
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "exam_status":exam_status,
				 "exam_doctor":doctor_name,
				 "approve_status":approve_status,
                  "occuphyexaclass_name":occuphyexaclass_name,
				 "tijianleixin":tijianleixin,
				 "customer_type_id":customer_type_id
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamSummaryIndexList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:'体检号',width:90,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'arch_num',title:'档案号',width:100,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:160},
		    {align:'center',field:'exam_types',title:'体检类型',width:80},	
		 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100},
		 	{align:'center',field:'customer_type',title:'人员类型',width:80},	
		 	{align:'center',field:'join_date',title:'体检时间',width:150,sortable:true},
		 	{align:'center',field:'statuss',title:'体检状态',width:70},
		 	{align:'center',field:'item_num_s',title:'未检项目数量',width:80},
		 	{align:'center',field:'wuxuzongjian',title:'无需总检',width:80,"formatter":f_wuxuzongjian},
		 	{align:'center',field:'final_date',title:'(职业病)总检时间',width:140,sortable:true},
		 	{align:'center',field:'final_doctor',title:'(职业病)总检医生',width:80},
		 	{align:'center',field:'check_time',title:'审核时间',width:100},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:80},
		 	{align:'center',field:'zyb_final_time',title:'总检时间',width:150},
		 	{align:'center',field:'zyb_final_doctor',title:'总检医生',width:150},
		 	{align:'center',field:'remarke',title:'总检保存',width:100},
		 	{align:'center',field:'exam_times',title:'完成时间',width:140},
		 	{align:'center',field:'company',title:'单位',width:220,sortable:true},
		 	{align:'center',field:'receive_type',title:'邮寄/自取',width:80},
		 /*	{align:'center',field:'disease_name',title:'是否危机'},*/
		 	{align:'center',field:'set_name',title:'套餐',width:150,sortable:true},
		 	{align:'center',field:'personal_pay',title:'金额(元)',width:60,sortable:true,hidden : teamAmountViewFlag==0},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		if(exam_page_show){
		    		var obj = $("#groupusershow").datagrid("getRows");
		    		if(obj.length>0){
		    			if(obj[0].freeze == '1'){
							$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
							return;
						}
		    			f_examoneshow(obj[0].exam_num);
		    		}
	    		}
	    		$('.datagrid-cell-c1-item_num_s').tooltip({    
					position: 'right',   
					content: '<span style="color:#000000"></span>',   
					onShow: function(){       
						$(this).tooltip('tip').css({         
							backgroundColor: '#FFFFFF',          
	    	    			borderColor: '#00BFFF'          
						});    
					}
				});  
	    		 $('.datagrid-cell-c1-item_num_s').hover(function(){
	    			 var exam_num = $(this).parent().parent().find('[field="exam_num"]').children().children().text();
		    			$.ajax({
		    				url:'getNotExamCharingItemList.action?exam_num='+exam_num,
		    				type:'post',
		    				success:function(data){
		    					if(data !=null && data !=""){
		    						var obj = eval("("+data+")");
		    						var item_name = new Array();
		    						for(i=0;i<obj.length;i++){
		    							if(i % 3 == 0 && i!=0){
		    								item_name.push('<span style="color:#000000;font-size:16px">'+obj[i].item_name+'</span></br>');
		    							} else {
		    								item_name.push('<span style="color:#000000;font-size:16px">'+obj[i].item_name+'</span>');
		    							}
		    						}
		    					}
		    					$('.datagrid-cell-c1-item_num_s').tooltip({    
		    						position: 'right',   
		    						content: '<span style="color:#000000">'+item_name.toString()+'</span>',   
		    						onShow: function(){       
		    							$(this).tooltip('tip').css({         
		    								backgroundColor: '#FFFFFF',          
		    		    	    			borderColor: '#00BFFF'          
		    							});    
		    						}
		    					});  
		    					
		    				}
		    			})
	    		 },
	    		 function(){
	    			 
	    		 }) 

	    	},
	    	rowStyler:function(index,row){
	    		var color = "";
	    		if(row.receive_type=='邮寄'){
	    			color = 'font-weight:bold;';  
	    		} 
	    		if(row.disease_name=='★'){
	    			color+="color:#FF0000;";
	    		}
	    		if(row.is_print_report == "Y"){
	    			color = "color:blue";
	    		}
	    		return color;
	    		  
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
			fit:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:false,
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
// 	    $.each(checkedItems, function(index, item){
// 	        barids.push(item.exam_num);
// 	    });
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
		text:'职业病报告预览',
		width:150,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
// 	    $.each(checkedItems, function(index, item){
// 	        barids.push(item.exam_num);
// 	    });
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


   function f_examoneshow(id){
			$.ajax({
				url:'getNotExamCharingItemList.action?exam_num='+id,
				type:'post',
				success:function(data){
					if(data == 'null' || data == '[]'){
						open_exam_summary(id);
					}else{
						var obj = eval("("+data+")");
						var alertTable = "<table style='border:solid 1px #add9c0;' width='400px;' cellspacing='0'>";
						var item_name = new Array();
						for(i=0;i<obj.length-1;i+=2){
							//item_name.push("<br/>"+obj[i].item_name);
							alertTable+="<tr><th>"+obj[i].item_name+"</th><th>"+obj[i+1].item_name+"</th></tr>";
						}
						if(obj.length % 2 == 1) {
							alertTable+="<tr><th>"+obj[obj.length-1].item_name+"</th></tr>";
						}
						alertTable+= "</table>";
						//item_name.toString()
						$.messager.confirm('提示信息','<div style="margin-left: 40px;font-size: 14px;">未检查项目：</br>'+alertTable+' </br><span style="color: red;font-size: 20px;">是否强下总检？</span></div>',
						function(r){
							if(r){
								open_exam_summary(id);
							}
						}).panel({width:480});;
					}
				}
			});
   }
   
   function open_exam_summary(exam_num){
   	var wuxuzongjian;
	   	$.ajax({                                                //查询是否是无需总检
			url:'getCustomerInfo.action?exam_num='+exam_num,
			type:'post',
			async:false,
			success:function(data){
				if(data!="null" && eval("("+data+")").flag!='error'){
					var obj = eval("("+data+")");
					wuxuzongjian = obj.wuxuzongjian;
				}
			}
		});
   	
	   	if(wuxuzongjian == '1'){
	   		$.messager.confirm('提示信息','体检人员是无需总检人员，是否进入职业病主检？',function(r){
	   			if(r){
	   				url='getZybExamSummaryPage.action?exam_num='+exam_num;
					newwin = window.open("", "总检室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
					newwin.moveTo(0,0);
					newwin.resizeTo(screen.width,screen.height - 30);
					newwin.location = url;
					newwin.focus();
	   			}
	   		});
	   	}else{
	   		 $.messager.defaults = { ok: "职业病", cancel: "普通" };
			$.messager.confirm('提示信息','选择总检方式？',function(r){
				$.messager.defaults = { ok: "确定", cancel: "取消" };
				if(r){
					url='getZybExamSummaryPage.action?exam_num='+exam_num;
					newwin = window.open("", "总检室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
					newwin.moveTo(0,0);
					newwin.resizeTo(screen.width,screen.height - 30);
					newwin.location = url;
					newwin.focus();
				}else{
					var url='getExamSummaryPage.action?exam_num='+exam_num;
					newwin = window.open("", "总检室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
					newwin.moveTo(0,0);
					newwin.resizeTo(screen.width,screen.height - 30);
					newwin.location = url;
					newwin.focus();
				}
			});
			$.messager.defaults = { ok: "确定", cancel: "取消" };
	   	}
	   
   }
   
   function f_showexam(val,row){
	   if(row.freeze == '1'){
			return row.exam_num;
		}else{
			return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
		}
   }
   
   function f_wuxuzongjian(val,row){
	   	if(val == '1'){
	   		return "<font color='red'>无需总检</font>";
	   	}else{
	   		return "需要总检";
	   	}
   }
   
   function chaxun2(){
	   var model={
			   "app_type":$("#app_type").val(),
				"time1":$("#start_date1").datebox('getValue'),	
				"time2":$("#end_date1").datebox('getValue'),
				"user_name":$("#custname1").val(),
				"exam_num":$("#exam_num1").val()
		   };
	   $("#getexamInfoList").datagrid('reload',model);
   }
   
   function getexamInfoGrid(){
	   var model={
			   "app_type":$("#app_type").val(),
			"time1":$("#start_date1").datebox('getValue'),	
			"time2":$("#end_date1").datebox('getValue'),
			"user_name":$("#custname1").val(),
			"exam_num":$("#exam_num1").val()
	   };
	   $("#getexamInfoList").datagrid({
	   url:'getMayExamSummaryList.action',
	   dataType: 'json',
	   queryParams:model,
	   pageSize: 15,//每页显示的记录条数，默认为10 
	   pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
	   columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:'体检号',width:100,"formatter":f_showexam,sortable:true},
			    {align:'center',field:'arch_num',title:'档案号',width:100,sortable:true},
			 	{align:'center',field:'id_num',title:'身份证号',width:180},
			    {align:'center',field:'exam_types',title:'体检类型',width:80},	
			 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
			 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
			 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
			 	{align:'center',field:'phone',title:'电话',width:100},		
			 	{align:'center',field:'join_date',title:'体检日期',width:100},
			 	{align:'center',field:'statuss',title:'体检状态',width:60},
			 	{align:'center',field:'wuxuzongjian',title:'无需总检',width:80,"formatter":f_wuxuzongjian},
			 	{align:'center',field:'final_doctor',title:'总检医生',width:80},
			 	{align:'center',field:'check_doctor',title:'审核医生',width:80},
			 	{align:'center',field:'remarke',title:'总检保存',width:100},
			 	{align:'center',field:'company',title:'单位',width:220},
				{align:'center',field:'receive_type',title:'邮寄/自取',width:80},
			 	{align:'center',field:'set_name',title:'套餐',width:150}
//			 	{align:'center',field:'personal_pay',title:'金额(元)',width:15,sortable:true},
			 	]],	    	
	    onLoadSuccess:function(value){ 
		    $("#datatotal").val(value.total);
		},
		rowStyler:function(index,row){
    		var color = "";
    		if(row.receive_type=='邮寄'){
    			color = 'font-weight:bold;';  
    		} 
    		if(row.disease_name=='★'){
    			color+="color:#FF0000;";
    		}
    		if(row.is_print_report == "Y"){
    			color = "color:blue";
    		}
    		return color;
    		  
    	},
		onDblClickRow : function(index, row) {	
			if(row.freeze == '1'){
				$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
				return;
			}
		    f_examoneshow(row.exam_num);
		},
		height: window.screen.height-370,
		nowrap:true,
		fit:false,
		rownumbers:true,
		singleSelect:false,
		collapsible:true,
		pagination: true,
		fitColumns:false,
	    striped:true
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
				     "app_type":$("#app_type").val(),
					 "exam_num":exam_num,
					 "time1":time1,	
					 "time2":time2,
					 "user_name":user_name,
					 "exam_status":done_status
			 };
		     $("#getexamreject").datagrid({
			 url:'getExamSummaryRejectList.action',
			 dataType: 'json',
			 queryParams:model,
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
			 columns:[[
	            {field:'ck',checkbox:true },
			    {align:'center',field:'exam_num',title:'体检号',width:100,"formatter":f_showexam,sortable:true},
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
		    		if(exam_page_show){
			    		var obj = $("#getexamreject").datagrid("getRows");
			    		if(obj.length>0){
			    			if(obj[0].freeze == '1'){
								$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
								return;
							}
			    			f_examoneshow(obj[0].exam_num);
			    		}
		    		}
		    	},
		    	onDblClickRow : function(index, row) {	
		    		if(row.freeze == '1'){
						$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
						return;
					}
		    		f_examoneshow(row.exam_num);
				},
				height: window.screen.height-370,
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

function getcustomerInfo(exam_num){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+exam_num,
		type:'post',
		async:false,
		success:function(data){
			if(data!="null" && eval("("+data+")").flag!='error'){
				var obj = eval("("+data+")");
				alert(obj.wuxuzongjian);
			}
		}
	});
}

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