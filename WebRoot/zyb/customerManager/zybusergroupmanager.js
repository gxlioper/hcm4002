var companyidss;
$(document).ready(function () {
	 $('#tjrw').bind('click', function() {
			select_batchcom_list();
	    }); 
		
		$('#tjrw').bind('keyup', function() {
			select_batchcom_list();
	    });
		
		$('#tjrw').bind('blur', function() {
			select_batchcom_list_over();
	    });	
	
	
	$('#com_Name').bind('click', function() {  
		select_com_list(this.value);
     }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list(this.value);
    });

	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
 });
	
	$('#is_Marriage').combobox({
		url : 'getDatadis.action?com_Type=HFLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#is_Marriage').combobox('setValue', data[0].id);
		}
	});
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#rylb').combobox('setValue', data[0].id);
		}
	});
	
	getgroupuserGrid();
	f_status();
});



//获取批次任务/////////////////////////////////////////////////////////////////
function select_batchcom_list(){
	var url='getbatchcompanyshow.action';
	var data={"company_id":companyidss};
	load_post(url,data,select_batchcom_list_sess);
	}

/**
* 显示树形结构
* @param data
* @returns
*/
function select_batchcom_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"方案批次","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == companyidss)){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvaluebatch('"+data[index].id+"','"+data[index].text+"')",
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

function setvaluebatch(id,name){
	$("#batch_id").val(id);
	$("#tjrw").val(name);
	$("#com_name_list_div").css("display","none");
}

//单位失去焦点
var hc_mousbatch_select1=false;
function select_batchcom_list_over(){
	if(!hc_mousbatch_select1){
	$("#com_name_list_div").css("display","none");
	}
	}

function select_batchcom_list_mover(){
	hc_mousbatch_select1=true;
	}
function select_batchcom_list_amover(){
	hc_mousbatch_select1=false;
	}


//获取单位///////////////////////////////////////////////////////////
//-------------------------------------------单位信息显示-----------------------------------------------------
/**
* 模糊查询公司信息
*/
var hc_com_list,com_Namess;
function select_com_list(company_Namess){
	var url='companychangshow.action';
	var data={"name":company_Namess};
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
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue('"+data[index].id+"','"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div1").html(mydtree.toString());
			$("#com_name_list_div1").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
var resultId;
var companyidss;
	function setvalue(resultId,name){
		$("#tjrw").val();	
		$("#batch_id").val();		
		$("#company_id").val(resultId);
		$("#com_Name").val(name);
		companyidss=resultId;
		hc_mous_select1=false;
		select_com_list_over();
		f_getdept();		
	}

//单位失去焦点
var hc_mous_select1=false;
function select_com_list_over(){
	if(!hc_mous_select1){
	$("#com_name_list_div1").css("display","none");
	}
	}

function select_com_list_mover(){
	hc_mous_select1=true;
	}
function select_com_list_amover(){
	hc_mous_select1=false;
	}

//显示部门
function f_getdept() {
	$('#levelss').combobox({
		url : 'getCompanForDept.action?company_id='+$("#company_id").val(),
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			$('#levelss').combobox('setValue', data[0].id);				
		}
	});
}

function f_status() {
	$('#status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
}

var exam_num="";
function getExaminfoExtInfo(exam_num){	
	$.post("getoneExamExtinfo.action", 
			{
				"exam_num" : exam_num
			}, function(jsonPost) {
				var examinfo = eval(jsonPost);
				document.getElementById("gongzhong").innerHTML=examinfo.typeofwork_name;
				document.getElementById("gongzhongshow").innerHTML=examinfo.occutypeofwork;
				document.getElementById("hangye").innerHTML=examinfo.industry_name;
				document.getElementById("hangyeshow").innerHTML=examinfo.occusector;
				document.getElementById("gongling").innerHTML=examinfo.employeeage;
				document.getElementById("jhgongling").innerHTML=examinfo.damage;
				document.getElementById("jinchangrq").innerHTML=examinfo.joinDatetime;
			}, "json");
}

var exam_num="";
function getoccuhazardfactorsGrid(exam_num){	
     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 var model={
			 "exam_num":exam_num
	 };
     $("#zywhysset").datagrid({
	 url:'examoccuhazardfactorslist.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:false,
     pageSize: 5,//每页显示的记录条数，默认为10 
     pageList:[5,10,20],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'hazard_name',title:'职业危害因素',width:30},	
	    {align:'center',field:'occuphyexaclass_name',title:'体检类别',width:30},
	 	{align:'center',field:'hazard_year',title:'危害年限',width:10}
	 	]],	    	
    	onLoadSuccess:function(value){
    		$("#datatotal").val(value.total);
    		$(".loading_div").remove();
    	},
    	singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true      
});
}

var exam_ids="";
function zybcustChangItemListGrid(exam_ids) {
	var model = {"exam_id" :exam_ids};
	$("#zybGselectItemlist").datagrid({
		url : 'zybAllcustchangitemlist.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ 
                     {align : 'left',field : 'item_code',title : '项目编码',	width : 15},
		             {align : 'left',field : 'item_name',title : '项目名称',	width : 35},
		             {align : 'center',field : 'itemnum',title : '个数',	width : 10},		            
		             {align : 'center',field : 'app_typename',title : '类型',width : 10}
		          ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
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
		pageSize : 1000
	});
}

 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":$("#batch_id").val(),
				 "dept_id":document.getElementsByName("levelss")[0].value,	
				 "status":document.getElementsByName("status")[0].value,	
				 "arch_num":$("#arch_num").val(),
				 "id_num":"",
				 "sex":document.getElementsByName("sex")[0].value,
				 "employeeID":"",
				 "birthday":$("#register_date").datebox('getValue'),				 
				 "custname":$("#custname").val(),
				 "tel":$("#tel").val(),
				 "customer_type_id":document.getElementsByName("rylb")[0].value,
				 "is_marriage":document.getElementsByName("is_Marriage")[0].value
		 };
	     $("#groupusershow").datagrid({
		 url:'zybgetExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'exam_num',title:'体检号',width:90,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:160},
		 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:50},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50},
		 	{align:'center',field:'age',title:'年龄',width:50},		 	
		 	{align:'center',field:'data_name',title:'体检类别',width:100},
		 	{align:'center',field:'team_pay',title:'团费',width:80},
		 	{align:'center',field:'personal_pay',title:'个费',width:80},
		 	{align:'center',field:'register_date',title:'体检日期',width:90},	 			 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:130},
		 	{align:'center',field:'arch_num',title:'档案号',width:80,sortable:true},	
		 	{align:'center',field:'phone',title:'电话',width:100},		 	
		 	{align:'center',field:'employeeID',title:'工号',width:80}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {	
	    		getExaminfoExtInfo(row.exam_num);
	    		getoccuhazardfactorsGrid(row.exam_num);
	    		zybcustChangItemListGrid(row.id);
			},
			singleSelect:false,
	    	fit:false,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false,
	        toolbar:toolbar	       
	});
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增',
		iconCls:'icon-add',
		width:58,
	    handler:function(){
			getBatchById(function(da){
				if(da){
					//var url ='zybaddcustomer.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
					//window.open(url, "新增体检人员", "height=400, width=800,toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
					var url='zybaddcustomer.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
					newWindow = window.open(url, "新增职业病体检人员", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
					newWindow.focus();
				}else{
					$.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
					return;
				}
			});
	    }
	},{
		text:'修改',
		width:58,
		iconCls:'icon-edit',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}else if(ids.split(',').length>2){
	    		$.messager.alert("操作提示", "只能同时修改一个体检人员信息","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
	    		var url='zybaddcustomer.action?exam_num='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
	 	    	newWindow = window.open(url, "新增职业病体检人员", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	 	    	newWindow.focus();
	    	}
	    }
	},{
		text:'删除',
		width:58,
		iconCls:'icon-cancel',
	    handler:function(){
			getBatchById(function(da){
				if(da){
					var ids = "";
					var checkedItems = $('#groupusershow').datagrid('getChecked');
					$.each(checkedItems, function(index, item){
						ids=ids+","+(item.exam_num);
					});
					deluserrow(ids);
				}else{
					$.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
					return;
				}
			});
	    }
	},{
		text:'申请',
		width:58,
		iconCls:'icon-check',
	    handler:function(){
			getBatchById(function(da){
				if(da){
					var ids = "";
					var exam_nums = new Array();
					var checkedItems = $('#groupusershow').datagrid('getChecked');
					$.each(checkedItems, function(index, item){
						ids=ids+","+(item.exam_num);
					});
					doSendApplyChargeItem(ids);
				}else{
					$.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
					return;
				}
			});
	    }
	},{
		text:'签到',
		width:58,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
	    	batchSign(ids);
	    }
	},{
		text:'打印导检单条码',
		iconCls:'icon-print',
		width:130,
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='1';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());
    			new_print_bar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if(($("#barcode_print_type").val() == '4')||($("#barcode_print_type").val() == '5')){//调用4.0打印程序中间表调用模式
    			//new_reprintdjd4('GB');
    			new_reprintdjd4('G');
    			setTimeout('new_reprintdjd4("B")',1000);
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		text:'打印导检单',
		width:100,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='1';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());
    			new_print_bar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if(($("#barcode_print_type").val() == '4')||($("#barcode_print_type").val() == '5')){//调用4.0打印程序中间表调用模式
    			//new_reprintdjd4('GB');
    			new_reprintdjd4('G');
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
    	    
	    }
	},{
		text:'打印条码',
		width:90,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	printBar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_bar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if(($("#barcode_print_type").val() == '4')||($("#barcode_print_type").val() == '5')){//调用4.0打印程序中间表调用模式
    			new_reprintdjd4('B');
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		text:'<input name=\"isprintdah\" type=\"checkbox\" value=\"1\"/>打印档案号',
		width:100,
		iconCls:'',
		handler:function(){
	    }
	},{
		text:'加项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
			getBatchById(function(da){
				if(da){
					var ids = "";
					var checkedItems = $('#groupusershow').datagrid('getChecked');
					$.each(checkedItems, function(index, item){
						ids=ids+","+(item.exam_num);
					});
					if(ids.split(',').length<=1){
						$.messager.alert("操作提示", "请选择要修改的体检人员","error");
					}else{
						if(ids.length>1) ids=ids.substring(1,ids.length);
						if(ids.length>=1){
							var url='zybcustomerAllitemaddshow.action?ids='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
							newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
							newWindow.focus();
						}else{
							$.messager.alert('提示信息',"请先选择体检人员","error");
						}
					}
				}else{
					$.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
					return;
				}
			});
	    }
	},{
		text:'减项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
			getBatchById(function(da){
				if(da){
					var ids = "";
					var checkedItems = $('#groupusershow').datagrid('getChecked');
					$.each(checkedItems, function(index, item){
						ids=ids+","+(item.id);
					});
					if(ids.split(',').length<=1){
						$.messager.alert("操作提示", "请选择要修改的体检人员","error");
					}else{
						if(ids.length>1) ids=ids.substring(1,ids.length);
						if(ids.length>=1){
							var url='zybcustomerallitemdelshow.action?ids='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
							newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
							newWindow.focus();
						}else{
							$.messager.alert('提示信息',"请先选择体检人员","error");
						}
					}
				}else{
					$.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
					return;
				}
			});
	    }
	},{
		text:'导出',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	if($("#batch_id").val() == '0'){
	    		$.messager.alert('提示信息',"请先选择体检任务","error");
	    		return;
	    	}
	    	if($("#company_id").val() == '0'){
	    		$.messager.alert('提示信息',"请先选择单位","error");
	    		return;
	    	}
	    	window.location.href='exportExcel.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()+'&app_type=2';
	    }
	}];

function getBatchById(callback){
	$.ajax({
		url : 'getBatchById.action',
		data : {
			batch_id : $("#batch_id").val()
		},
		type : "post",//数据发送方式
		success : function(data) {
			var obj = eval('(' + data + ')');
			if (obj != null && obj.accountflag == '1') { // 0 正常 1 锁定
				callback(false);
			}else{
				callback(true);
			}
		}
	});
}
 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showexam(val,row){	
	  return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
  }
  
  /**
   * 显示
   * @param userid
   */
   function f_examoneshow(exam_num){	
	   var url='/customeroneshow.action?exam_num='+exam_num+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
   	if(exam_num!=""){
   		newWindow = window.open(url, "查看人员基本信息", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
   		newWindow.focus();
   	}else{
   	  $.messager.alert('提示信息',"请先选择体检人员","error");
   	}
   }
 
 function printBar(barids,exam_nums){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" * *"; //打印档案号
	 }else{
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" *"; 
	 }
	 $.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			    },
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						$.ajax({
							url : 'updateBarcodePrintStatus.action',
							data : {ids:exam_nums},
							type : "post",//数据发送方式   
							success : function(text) {
								RunExe(exeurl);	
							},
							error : function() {
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});	
 }
 
 //打印导检单和条码
 function daoyindan_point(idessss,idss,barids,falgs,exam_nums) {
	 if(idss.length<=1){
	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
	 }else{
		 if($("#hansidjdflag").val()==1){
			if(idss.split(",").length>10){
				$.messager.alert("操作提示", "每次打印不能超过10个.","error");
			}else{
				$.ajax({
					url : 'updateGuidePrintStatus.action',
					data : {ids:exam_nums},
					type : "post",//数据发送方式   
					success : function(text) {
						doURLPDFPrint_ireport(idss);	
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
			}		 
		 }else{
			 $.ajax({
					url : 'updateGuidePrintStatus.action',
					data : {ids:exam_nums},
					type : "post",//数据发送方式   
					success : function(text) {
						printDJD_exe(barids);//打印导检单	
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
		 }
		 if(falgs=='1'){
			 printBar(barids,exam_nums);//打印条码
		 }
	 }
 }
 
 //新版本打印导检单
 function printDJD_exe(barids){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids+" *"; //打印档案号
	 }else{
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids; 
	 }
	 RunExe(exeurl);
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
							    if (pdf != undefined && pdf != null) {//判断pdf对象是否存在，如果存在就删除该对象
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
 
 
 /**
  * 执行签到
  */
 function batchSign(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否进行批量签到？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'batchSigndo.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val(),
                    	exam_nums:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupuserGrid();
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
  * 执行批量申请
  */
 function doSendApplyChargeItem(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','执行此操作，将对所选体检人员进行批量申请,请确定先进行人员分组，此操作比较慢，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'doSendApplyChargeItem.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val(),
					    ids:ids,
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupuserGrid();
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
  * 批量删除
  */
 function deluserrow(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定删除选中用户？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'deluserrow.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:$("#batch_id").val(),
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
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
  * 强制分组
  */
 function doUserGroupuser(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else if(ids.length<=0){
 		$.messager.alert("操作提示", "请选择体检人员","error");	
 	}else{
 			$("#dlg-custshow").dialog({
		 		title:'选择分组',
		 		href:'doUserGrouplistshow.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()+'&ids='+ids
		 	});
		 	$("#dlg-custshow").dialog('open'); 			
 		}
 }
 /**
  * 执行自动分组
  */
 function doAllUserGroupuser(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定进行自动分组？',function(r){
	 if(r){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	 $.ajax({
			url : 'doAllUserGroupuser.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:$("#batch_id").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
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
 
 function RunExe(strPath) {
		try {
			var objShell = new ActiveXObject('wscript.shell');
			objShell.Run(strPath);
			objShell = null;
		} catch (e) {
			$.messager.alert("操作提示",'找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.', "error");	
		}
	}
 
//新打印导检
 function new_print_djd(barids,exam_nums){
	 if(barids.length<=1){
	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
	 }else{
		 $.ajax({
				url : 'updateGuidePrintStatus.action',
				data : {ids:exam_nums},
				type : "post",//数据发送方式   
				success : function(text) {
					if (text.split("-")[0] == 'ok') {
						var url = 'GuidBarServices://&guid&'+barids+'&&';
						RunReportExe(url);											
					} else {
						$.messager.alert("操作提示", text, "error");
					}			
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
	 }
 }
 // 新打印条码
function new_print_bar(barids, exam_nums) {
	if(barids.length<=1){
 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
    }else{
		$.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.ajax({
						url : 'updateBarcodePrintStatus.action',
						data : {
							ids : exam_nums
						},
						type : "post",// 数据发送方式
						success : function(text) {
							var exeurl = '';
							if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&3&1'; //打印档案号
						  	 }else{
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&1'; 
						  	 }
							RunReportExe(exeurl);
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
    }
}

//新补打导检单4.0
var reprintflag;
function new_reprintdjd4(reprintflag){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var data = new Array;
	var bar_calss = 1;
	if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		bar_calss=3;
	 }
	
	var checkedItems = $('#groupusershow').datagrid('getChecked');	
    $.each(checkedItems, function(index, item){
        data.push({
			exam_num:item.exam_num,
			print_type:reprintflag,
			charging_item_codes:'',
			bar_calss:bar_calss,
			arch_bar_num:1
		});
    });		
		$.ajax({
			url : 'saveExamPrintTmp.action',
			data : {examPrintTmpLists:JSON.stringify(data)},
			type : "post",//数据发送方式   
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					var printno=text.split("-")[1];					
					var url = 'GuidBarServices://&P&1&'+printno;
			 		RunServerExe(url);										
				} else{
					$.messager.alert("操作提示", text, "error");
				}				
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	}

function RunReportExe(strPath) {
	location.href=strPath;
}