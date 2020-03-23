$(document).ready(function () {
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	$('#start_date').datebox('setValue',getNowFormatDate());
	$('#end_date').datebox('setValue',getNowFormatDate());
	$('#exam_date1').datebox('setValue',getNowFormatDate());
	$('#exam_date2').datebox('setValue',getNowFormatDate());
	
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
	 var doctor_name = undefined;
	 if($("#ck_doctor_name")[0].checked){
		 doctor_name = $('#doctor_name').combobox('getValue');
	 }
	 var exam_date1 = undefined,exam_date2 = undefined;
	 if($("#ck_exam_date")[0].checked){
		 exam_date1 = $("#exam_date1").datebox('getValue');
		 exam_date2 = $("#exam_date2").datebox('getValue');
	 }
	 var exam_status = undefined;
	 if($("#ck_exam_status")[0].checked){
		 exam_status = $('#exam_status').combobox('getValue');
	 }
	 var customer_type = undefined;
	 if($("#ck_tjlx")[0].checked){
		 customer_type = $('#tjlx').combobox('getValue');
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
				 "exam_status":exam_status,
				 "doctor_name":doctor_name,
				 "exam_date1":exam_date1,
				 "exam_date2":exam_date2,
				 "dep_id":$("#dep_id").val(),
				 "customer_type":customer_type
		 };
		 if(valu == 'Y' || valu == 'N'){
			 model = {
				"exam_status":valu,
				"time1": $("#start_date").datebox('getValue'),
				"time2": $("#end_date").datebox('getValue'),
				"dep_id":$("#dep_id").val()
			 };
		 }
	     $("#groupusershow").datagrid({
		 url:'getExamResultDetailList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 100,//每页显示的记录条数，默认为10 
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
	    		f_examoneshow(row.exam_num);
			},
			height: window.screen.height-410,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
//		    toolbar:toolbar
	});
	}
 
 function getexaminfoByExamNum(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
		 var model={
				 "exam_num":exam_num,
				 "dep_id":$("#dep_id").val()
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamResultDetailList.action',
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
	    			f_examoneshow(obj[0].exam_num);
	    		}
	    	},
	    	onDblClickRow : function(index, row) {
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		f_examoneshow(row.exam_num);
			},
			height: window.screen.height-410,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
//		    toolbar:toolbar
	});
	}

   function f_examoneshow(id){
	   $("#exam_num").textbox("setValue", "");
	   var url='getExamResultDetailReaderPage.action?exam_num='+id;
	   newwin = window.open("", "检验科室检查", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height-30);
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
   
function f_showitem(val,row){
	return '<a href="javascript:void(0)" onclick = "showexamitem('+row.id+',this)">'+row.user_name+'</a>';
}
function showexamitem(id,ths){
	var height = window.screen.height-210 - $(ths).offset().top;
	if(height > 408){
		$("#results_contrast").css("top",$(ths).offset().top);
	}else{
		$("#results_contrast").css("top",$(ths).offset().top-(408-height));
	}
	
	$("#results_contrast").css("left","40%");
	$('#results_contrast').css('display','block');
	$("#examitem_div").datagrid({
		url:'getInfoItemByIdAndStatus.action',
		dataType: 'json',
		queryParams:{'examinfo_id':id,"exam_status":$("input[name='exam_status']:checked").val()},
		rownumbers:false,
		width:400,
		height:300,
		columns:[[
			 {align:'center',field:'item_code',title:'项目编码',width:40},
			 {align:'center',field:'item_name',title:'项目名称',width:60},
			 {align:'center',field:'exam_statuss',title:'检查状态',width:40},
			 {align:'center',field:'pay_statuss',title:'付费状态',width:40}
		]],
		singleSelect:true,
		collapsible:true,
		pagination: false,
		striped:true,
		nowrap:false,
		fitColumns:true
	});
}

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

function readAllResult(){
	var obj = $("#groupusershow").datagrid("getChecked");
	if(obj.length == 0){
		$.messager.alert("操作提示",'请选择需要读取结果的体检者!', "error");
		return;
	}
	var exam_nums = new Array();
	for(i=0;i<obj.length;i++){
		exam_nums.push(obj[i].exam_num);
	}
	
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'getExamResultDetailReader.action',
		type:'post',
		data:{
			exam_num:exam_nums.toString()
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert("操作提示",obj[1], "info");
				getgroupuserGrid();
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}

/**
 * 定义工具栏
 */
//var toolbar=[{
//		text:'报告预览',
//		width:90,
//		iconCls:'icon-print',
//	    handler:function(){
//	    	var barids= new Array();
//	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
//   	    $.each(checkedItems, function(index, item){
//   	        barids.push(item.exam_num);
//   	    });
//   	    if(barids.length <=0 ){
//	    		$.messager.alert("操作提示", "请选择一个体检人员","error");
//	    	}else{
//	    		printreport(barids.toString());
//	    	}
//   	    
//	    }
//	}];
//function printreport(barids){
//	   var exeurl="reportServices://&0&"+barids+"&0";
//	   location.href=exeurl;
//  }
