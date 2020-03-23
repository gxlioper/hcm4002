var companyidss;
var newWindow;  
var timer; 
$(document).ready(function () {
	
	 $('#tjrw').bind('click', function() { 
			select_batchcom_list(this.value);
	    }); 
		
		$('#tjrw').bind('keyup', function() {
			select_batchcom_list(this.value);
	    });
		
		$('#tjrw').bind('blur', function() {
			select_batchcom_list_over();
	    });	
	
	
	$('#com_Name').bind('click', function() {  
		select_com_list();
 }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list();
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
function select_batchcom_list(batchcom_Namess){
	var url='getComByBatchList.action';
	var data={"comname":batchcom_Namess};
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
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
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
var partnerId;
var companyidss;
	function setvaluebatch(partnerId,name){
		var batcoms=partnerId.split('-');
		$("#company_id").val(batcoms[0]);
		$("#tjrw").val(name);	
		$("#com_Name").val('');
		$("#batch_id").val(batcoms[1]);
		batch_idchange(batcoms[1]);
		companyidss=batcoms[0];
		hc_mousbatch_select1=false;
		select_batchcom_list_over();
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
function select_com_list(){
	var url='getbatchcompanyshow.action';
	var data={"company_id":companyidss};	
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
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].id == companyidss)){
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
			$("#com_name_list_div1").html(mydtree.toString());
			$("#com_name_list_div1").css("display","block");
			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
	function setvalue(id,name){
		$("#company_id").val(id);
		$("#com_Name").val(name);
		$("#com_name_list_div1").css("display","none");
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
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			$('#levelss').combobox('setValue', data[0].id);				
		}
	});
}

//方案获取分组
var barbatchids
function batch_idchange(barbatchids) {
	$('#group_id').combobox({
		url : 'getBatchForGroup.action?batch_id='+barbatchids,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'group_name',
		onLoadSuccess : function(data) {
			$('#group_id').combobox('setValue', data[0].id);				
		}
	});
}

function f_status() {
	$('#status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
}


 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){
	 var statuss = $("#status").combobox('getValues');
	 var status = new Array();
	 for(i=0;i<statuss.length;i++){
		 if(statuss[i] != ''){
			 status.push(statuss[i]); 
		 }
	 }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":$("#batch_id").val(),
				 "group_id":document.getElementsByName("group_id")[0].value,
				 "dept_id":document.getElementsByName("levelss")[0].value,	
				 "status":status.toString(),	
				 "arch_num":$("#arch_num").val(),
				 "id_num":"",
				 "sex":document.getElementsByName("sex")[0].value,
				 "employeeID":"",
				 "startDate":$("#start_date").datebox('getValue'), //体检开始日期
				 "endDate":$("#end_date").datebox('getValue'), //体检结束日期
				 "custname":$("#custname").val(),
				 "djdstatuss":document.getElementsByName("djdstatuss")[0].value,
				 "tel":"",
				 "customer_type_id":document.getElementsByName("rylb")[0].value,
				 "is_marriage":document.getElementsByName("is_Marriage")[0].value
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},	
		    {align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:15},
		 	{align:'center',field:'employeeID',title:'工号',width:15},
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20,sortable:true},
		 	{align:'center',field:'group_name',title:'分组名称',width:15,sortable:true},			 	
		 	{align:'center',field:'set_name',title:'套餐',width:20},	
		 	{align:'center',field:'is_upload',title:'是否同步',width:15,"formatter":f_isUpload},
		 	{align:'center',field:'is_report_upload',title:'是否上传报告',width:15,"formatter":f_isUploadReport},
		 	{align:'center',field:'register_date',title:'体检日期',width:15,sortable:true},
		 	{align:'center',field:'position',title:'职务',width:15},		 	
		 	{align:'center',field:'lis',title:'检验(Y/N)',width:15},
		 	{align:'center',field:'pacs',title:'检查(Y/N)',width:15},		 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:25}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar	       
	});
}
 
 
 //是否同步
 function f_isUpload(val,row){
	 return val == 0 ? "否" : "是";
 }
 
//是否上传报告
 function f_isUploadReport(val,row){
	 return val == 0 ? "否" : "是";
 }
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'自动分组',
		iconCls:'icon-save',
		width:90,
	    handler:function(){
	    	doAllUserGroupuser();
	    }
	},{
		text:'强制分组',
		iconCls:'icon-save',
		width:90,
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    var ids = "";
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
    	    doUserGroupuser(ids);
	    }
	},{
		text:'新增人员',
		iconCls:'icon-add',
		width:90,
	    handler:function(){
	    	var url ='addcustomer.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
	    	newWindow = window.open(url, "新增体检人员", "height=800, width=800,toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    	newWindow.focus();
	    }
	},{
		text:'修改人员',
		width:90,
		iconCls:'icon-edit',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}else if(ids.split(',').length>2){
	    		$.messager.alert("操作提示", "只能同时修改一个体检人员信息","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	var url ='editcustomer.action?id='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
			 	newWindow = window.open(url, "修改体检人员信息", "height=800, width=800,toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
			 	newWindow.focus();
	    	}
	    }
	},{
		text:'批量删除',
		width:90,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	    	    
    	    deluserrow(ids);
	    }
	},{
		text:'批量申请',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
	    	doSendApplyChargeItem(ids);
	    }
	},{
		text:'批量签到',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
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
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印导检单条码的体检人员","error");
	    		return;
	    	}
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
    			new_reprintdjd4('B');
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
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印导检单的体检人员","error");
	    		return;
	    	}
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='0';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if(($("#barcode_print_type").val() == '4')||($("#barcode_print_type").val() == '5')){//调用4.0打印程序中间表调用模式
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
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印条码的体检人员","error");
	    		return;
	    	}
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
    			new_reprintdjd4('G');
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
		text:'打印单个条码',
		width:120,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印条码的体检人员","error");
	    		return;
	    	}
    	    $("#dlg-printitem").dialog({
        		title:'打印单个条码',
        		href:'getAllorintItemPage.action'
        	});
        	$("#dlg-printitem").dialog("open");
	    }
	},{
		text:'加项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
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
			 	    	var url='customeritemaddshow.action?ids='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
			 	   		newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
			 	   		newWindow.focus();
			 	    }else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
			 	   	}
	    	}
	    }
	},{
		text:'减项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
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
			 	    	var url='customeritemdelshow.action?ids='+ids+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
			 	   		newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
			 	   		newWindow.focus();
			 	  }else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
			 	   	}
	    	}
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
	    	
	    	 var statuss = $("#status").combobox('getValues');
	    	 var status = new Array();
	    	 for(i=0;i<statuss.length;i++){
	    		 if(statuss[i] != ''){
	    			 status.push(statuss[i]); 
	    		 }
	    	 }
	    	//window.location.href='exportExcel.action?batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
	    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 var model={
					 "company_id":$("#company_id").val(),
					 "batch_id":$("#batch_id").val(),
					 "group_id":document.getElementsByName("group_id")[0].value,
					 "dept_id":document.getElementsByName("levelss")[0].value,	
					 "status":status.toString(),	
					 "arch_num":$("#arch_num").val(),
					 "id_num":"",
					 "sex":document.getElementsByName("sex")[0].value,
					 "employeeID":"",
					 "startDate":$("#start_date").datebox('getValue'), //体检开始日期
					 "endDate":$("#end_date").datebox('getValue'), //体检结束日期
					 "custname":$("#custname").val(),
					 "djdstatuss":document.getElementsByName("djdstatuss")[0].value,
					 "tel":"",
					 "customer_type_id":document.getElementsByName("rylb")[0].value,
					 "is_marriage":document.getElementsByName("is_Marriage")[0].value,
					 "page":1,
					 "rows":100000
			 };
			 
			 $.ajax({
					url : 'getExamInfoUserList.action',
					data : model,
					type : "post",//数据发送方式   
					success : function(data) {
						var temp = eval('('+data+')');
						if(temp.rows.length == 0){
							$(".loading_div").remove();
							$.messager.alert("操作提示", "没有需要导出的人员信息!","error");
							return;
						}
						
						var filelist = new Array();
				    	var obj = new Object();
				    	obj.arch_num = "档案号";
				    	obj.exam_num = "体检号";
				    	obj.id_num = "身份证号";
				    	obj.customer_type_name = "人员类别";
				    	obj.employeeID = "工号";
				    	//obj.exam_types = "体检类型";
				    	obj.user_name = "姓名";
				    	obj.sex = "性别";
				    	obj.is_marriage = "婚否";
				    	obj.age = "年龄";
				    	obj.phone = "电话";
				    	obj.group_name = "分组名称",
				    	obj.set_name = "套餐";
				    	//obj.is_upload = "是否同步";
				    	obj.join_date = "体检日期";
				    	obj.dep_name = "部门"; //部门
				    	obj.position = "职务"; 
				    	obj.others = "其他";
				    	//obj.statusall = "体检状态";
				    	filelist.push(obj);
				    	
				    	for(i=0;i<temp.rows.length;i++){
				    		obj = new Object();
				    		obj.arch_num = temp.rows[i].arch_num;
				    		obj.exam_num = temp.rows[i].exam_num;
					    	obj.id_num = temp.rows[i].id_num;
					    	obj.customer_type_name = temp.rows[i].customer_type_name;
					    	obj.employeeID = temp.rows[i].employeeID; //工号
					    	//obj.exam_types = temp.rows[i].exam_types;
					    	obj.user_name = temp.rows[i].user_name;
					    	obj.sex = temp.rows[i].sex;
					    	obj.is_marriage = temp.rows[i].is_marriage;
					    	obj.age = temp.rows[i].age;
					    	obj.phone = temp.rows[i].phone;
					    	obj.group_name = temp.rows[i].group_name; //分组名称
					    	obj.set_name = temp.rows[i].set_name;  //套餐
					    	//obj.is_upload = temp.row[i].is_upload;
					    	obj.join_date = temp.rows[i].join_date;
					    	obj.dep_name = temp.rows[i].dep_name; //部门
					    	obj.position = temp.rows[i].position; //职务
					    	obj.others = temp.rows[i].others; //其他
					    	filelist.push(obj);
				    	}
				    	$.ajax({
							url : 'saveDatagridData.action',
							data : {filelist:JSON.stringify(filelist)},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								window.location.href='datagridExportExcel.action';
							},
							error : function() {
								$.messager.alert("操作提示", "导出excel出错","error");
							}
						});
						
						
					},
					error : function() {
						$.messager.alert("操作提示", "导出excel出错","error");
					}
			 });
	    	
	    }
	},{
		text:'批量同步',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	isSynchro(ids);
    	    }
	    }
	},{
		text:'报告上传',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	reportUpload(ids);
    	    }
	    }
	}];
 
 //批量同步信息
 function  isSynchro(ids){
		$.messager.confirm('提示信息','是否进行批量同步？',function(r){
			 if(r){
				 $.ajax({
						url : 'isSynchroExamInfo.action',
						data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == ' ok') {
								getgroupuserGrid();
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
			 }
		})
	}
 
 function  reportUpload(ids){
		$.messager.confirm('提示信息','是否进行批量上传报告？',function(r){
			 if(r){
				 $.ajax({
						url : 'reportUploadExamInfo.action',
						data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == ' ok') {
								getgroupuserGrid();
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
			 }
		})
	}

 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showexam(val,row){	
	  return '<a href=\"javascript:f_examoneshow(\''+row.id+'\')\">'+row.exam_num+'</a>';
  }
  
  /**
   * 显示
   * @param userid
   */
   function f_examoneshow(userid){	
	   var url='/customeroneshow.action?id='+userid+'&batch_id='+$("#batch_id").val()+'&company_id='+$("#company_id").val();
   	if(userid>0){
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
  * 批量设置行业
  */
 function getocchangye(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||($("#batch_id").val()=='')||(Number($("#batch_id").val())<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'doAllUserhangye.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:$("#batch_id").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
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
 
 /**
  * 设置每隔2秒钟刷新父节点的表格
  */
 function djtupdateAfterClose() {  
     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
     if(newWindow.closed == true) {
    	 getgroupuserGrid();
         return;  
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