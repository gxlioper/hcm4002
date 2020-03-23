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
        	getgroupuserGrid();
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
	if($("#is_out_reg").val() == 'Y'){
		toolbar = [{
		 	id:1,
			text:'体检车数据导出',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    	var barids = new Array();
		 	    $.each(checkedItems, function(index, item){
		 	        barids.push(item.exam_num);
		 	    });
		 	   	if(checkedItems.length == 0){
		 	   		$.messager.alert("提示信息","请选择需要导入到体检库的人员信息","error");
		 	   		return;
		 	   	}
		 	   	window.location.href='exportDataMigrationDb.action?exam_nums='+barids.toString()+"&is_out_reg="+$("#is_out_reg").val();
		    }
		},{
			id:2,
			text:'院内数据导入',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	upload_excel_btn();
		    }
		}];
	}else{
		toolbar = [{
			id:3,
			text:'院内数据导出',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    	var barids = new Array();
		 	    $.each(checkedItems, function(index, item){
		 	        barids.push(item.exam_num);
		 	    });
		 	   	if(checkedItems.length == 0){
		 	   		$.messager.alert("提示信息","请选择需要导入到体检库的人员信息","error");
		 	   		return;
		 	   	}
		 	   	window.location.href='exportDataMigrationDb.action?exam_nums='+barids.toString()+"&is_out_reg="+$("#is_out_reg").val();
		    }
		},{
			id:4,
			text:'体检车数据导入',
			width:150,
			iconCls:'icon-print',
		    handler:function(){
		    	upload_excel_btn();
		    }
		}];
	}
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
 function getgroupuserGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue') +' 00:00:00.000';
		 time2 = $("#end_date").datebox('getValue')+ ' 23:59:59.999';
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var searchphone = undefined;
	 if($("#ck_searchphone")[0].checked){
		 searchphone = $("#searchphone").val();
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
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
	 var exam_export = undefined;
	 if($("#ck_exam_export")[0].checked){
		 exam_export = $('#exam_export').combobox('getValue');
	 }
	 var exam_import = undefined;
	 if($("#ck_exam_import")[0].checked){
		 exam_import = $('#exam_import').combobox('getValue');
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "employeeID":employeeID,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "exam_export":exam_export,
				 "exam_import":exam_import
		 };
	     $("#groupusershow").datagrid({
		 url:'getDataMigrationList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:25,sortable:true},
		    {align:'center',field:'arch_num',title:'档案号',width:18},
		 	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20},
		 	{align:'center',field:'exam_export',title:'是否导出',width:20},
		 	{align:'center',field:'exam_import',title:'是否导入',width:20}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
//	    	onDblClickRow : function(index, row) {	
//	    		getExamitemList(row.exam_num);
//	    		getFinalSummary(row.exam_num);
//	    		getExamDisease(row.exam_num);
//			},
//			onClickRow :function(index, row){
//				
//			},
			//height: window.screen.height-400,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
		    toolbar:toolbar
	});
	}
 /**
  * 定义工具栏
  */
 var toolbar=[];
 function getExamitemList(examinfo_id){
	 var model={"exam_num":examinfo_id};
     $("#exam_item_list").datagrid({
	 url:'getDirectorItemStatus.action',
	 dataType: 'json',
	 queryParams:model,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
	 columns:[[
	    {align:'center',field:'dep_name',title:'科室',width:10},
	 	{align:'center',field:'item_name',title:'项目名称',width:25},
	    {align:'center',field:'exam_indicators',title:'计费类型',width:15},	
	 	{align:'center',field:'pay_statuss',title:'付费状态',width:15},
	 	{align:'center',field:'exam_statuss',title:'检查状态',width:20},
	 	{align:'center',field:'exam_date',title:'检查日期',width:20},
	 	{align:'center',field:'exam_doctor_name',title:'检查医生',width:20}	 	
	 	]],	    	
    	onLoadSuccess:function(value){ 
    		$("#datatotal").val(value.total);
    	},
    	onDblClickRow : function(index, row) {
    		if(row.exam_status == 'Y'){
	    		$("#dlg-edit").dialog({//width: 1200,height: 515,closed: true,cache: false,modal: true
	    			width: 700,
	    			height: 515,
	    			closed: true,
	    			cache: false,
	    			modal: true,
	    			title:row.item_name+'检查结果',
	    			href:'getDirectorItemResult.action?charging_item_ids='+row.item_code+'&exam_num='+examinfo_id+'&dep_category='+row.dep_category
	    		});
	    		$("#dlg-edit").dialog("open");
    		}
		},
		rownumbers:true,
    	singleSelect:true,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    fit:true,
	    striped:true
     });
 }
function upload_excel_btn() {
	document.getElementById("upload_photofile_div").style.visibility = "visible";//隐藏
}

function closeuploadshow() {
	document.getElementById("upload_photofile_div").style.visibility = "hidden";//隐藏
}

//文件上传按钮
function wenjianshangchuan() {
	if ($("#importDb").val() == '') {
		$.messager.alert("操作提示", "请选择需要上传的数据文件！", "error");
		return;
	}
	var srca = $("#importDb").val();
	var ext = [ '.db'];
	var s = srca.toLowerCase();
	var r = false;
	for (i = 0; i < ext.length; i++) {
		if (s.indexOf(ext[i]) > 0) {
			r = true;
			break;
		}
	}
	if (r) {
		ajaxFileUpload();
	} else {
		$.messager.alert("操作提示", "请上传正确的数据文件！","error");
	}
}

// 文件上传
function ajaxFileUpload(a, b) {
	$.ajaxFileUpload({
		url : 'importDataMigrationDb.action',
		fileElementId : 'importDb',
		dataType : 'json',
		success : function(data) {
			if (data.state == 'Y') {
				$.messager.alert("操作提示", data.msg, "info");
				closeuploadshow();
				getgroupuserGrid();
			} else {
				$.messager.alert("操作提示", data.msg, "error");
			}
		}
	});
}