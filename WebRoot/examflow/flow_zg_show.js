/**
 * 回访
 */

$(document).ready(function () {
	chaxun();
	chaxun_report();

	$('#com_Name').bind('click', function() {
		select_com_list(this.value);
    }); 
	
	$('#com_Name').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').bind('blur', function() {  
		select_com_list_over();
    });
	
	$('#com_Name1').bind('click', function() {
		select_com_list1(this.value);
	}); 
	
	$('#com_Name1').bind('keyup', function() {
		select_com_list1(this.value);
	});
	
	$('#com_Name1').bind('blur', function() {  
		select_com_list_over1();
	});

	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	chaxun();
	    	chaxun_report();
	     }
	}
	
	zg_edit_row_init()
	
	setchebox();
});

//获取单位///////////////////////////////////////////////////////////
//-------------------------------------------单位信息显示-----------------------------------------------------
/**
* 模糊查询公司信息
*/
function select_com_list(com_Namess){
	var url='companychangshow.action';
	var data={"name":com_Namess};
	load_post(url,data,select_com_list_sess);
}

function select_com_list1(com_Namess){
	var url='companychangshow.action';
	var data={"name":com_Namess};
	load_post(url,data,select_com_list_sess1);
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
		if((data[index].attributes == null)||(data[index].attributes == '')){
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

function select_com_list_sess1(data){
	mydtree = new dTree('mydtree','../../images/img/','no','no');
	mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
	for(var index = 0,l = data.length;index<l;index++){
		if((data[index].attributes == null)||(data[index].attributes == '')){
			mydtree.add(data[index].id,
					0,
					data[index].text,
					"javascript:setvalue1("+data[index].id+",'"+data[index].text+"')",
					data[index].text
					,"_self",false);
		}else{
			mydtree.add(data[index].id,
					data[index].attributes,
					data[index].text,
					"javascript:setvalue1("+data[index].id+",'"+data[index].text+"')",
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
	$("#com_Name").val(name);
	$("#com_name_list_div").css("display","none");
}

function setvalue1(id,name){
	$("#com_Name1").val(name);
	$("#com_name_list_div1").css("display","none");
}
	
//单位失去焦点
var hc_mous_select=false;
function select_com_list_over(){
	if(!hc_mous_select){
		$("#com_name_list_div").css("display","none");
	}
}

function select_com_list_mover(){
	hc_mous_select=true;
}
function select_com_list_amover(){
	hc_mous_select=false;
}

var hc_mous_select1=false;
function select_com_list_over1(){
	if(!hc_mous_select1){
		$("#com_name_list_div1").css("display","none");
	}
}

function select_com_list_mover1(){
	hc_mous_select1=true;
}
function select_com_list_amover1(){
	hc_mous_select1=false;
}

function chaxun() {
	var company = undefined;
    if($("#ck_com_Name")[0].checked){
    	company =  $("#com_Name").val();
	}
	var exam_num = undefined;
	if($('#ck_exam_num_zg').attr('checked')){
		exam_num =  $("#exam_num_zg").val();
	}
	var time1 = undefined,time2 = undefined;
	if($('#ck_date').attr('checked')){
		time1 = $("#time1").datebox('getValue');
		time2 = $("#time2").datebox('getValue');
	}
	var username = undefined;
	 if($('#ck_username').attr('checked')){
		 username = $("#name").val();
	 }
	 var phone = undefined;
	 if($('#ck_phone').attr('checked')){
		 phone = $("#phone").val();
	 }
	 var vipflag = undefined;
	 if($('#ck_vipflag').attr('checked')){
		 vipflag = $("#vipflag").combobox('getValue');
	 }
	 var exam_type = undefined;
	 if($('#ck_exam_type').attr('checked')){
		 exam_type = $("#exam_type").combobox('getValue');
	 }
	 var h0 = undefined;
	 if($('#ck_h0').attr('checked')){
		 h0 = $("#h0").combobox('getValue');
	 }
	var model = {
		"exam_num" : exam_num,
		"time1" : time1,
		"time2" : time2,
		"username" : username,
		"phone" : phone,
		"vipflag" : vipflag,
		"exam_type" : exam_type,
		"h0" : h0,
		"company":company,
	};
	
	$("#flowexampListzg").datagrid({
		url : 'flowexampListzg.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		pageNumber : 1,
		pageSize : 15,// 每页显示的记录条数，默认为10
		pageList : [ 10, 15, 30, 50, 100 ],// 可以设置每页记录条数的列表
		pagination : true,
		toolbar : toolbar,
		remoteSort:false,
		collapsible : true,
		fitColumns : true,
		rownumbers:true,
		columns : [ [ 
			{field : 'ck',checkbox : true,hidden : false,},
			{align : 'center',field : 'exam_num',title : tjhname,sortable : true}, 
			{align : 'center',field : 'arch_num',title : dahname,sortable : true}, 
			{align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable : true}, 
			{align : 'center',field : 'exam_types',title : '体检类型',sortable : true}, 
			{align : 'center',field : 'user_name',title : '姓名',sortable : true}, 
			{align : 'center',field : 'sex',title : '性别',sortable : true},
			{align : 'center',field : 'is_marriage',title : '婚否',sortable : true}, 
			{align : 'center',field : 'age',title : '年龄',sortable : true}, 
			{align : 'center',field : 'join_date',title : '体检日期',sortable : true}, 
			{align : 'center',field : 'statuss',title : '体检状态',sortable : true}, 
			{align : 'center',field : 'phone',title : '手机号',sortable : true}, 
			{align : 'center',field : 'vipflag',title : '会员标识',sortable : true}, 
			{align : 'center',field : 'company',title : '单位',sortable : true},
			{align : 'center',field : 'strh0',title : '核收情况',sortable : true},
			{align : 'center',field : 'h0date',title : '核收时间',sortable : true},
		] ],
		onLoadSuccess : function(value) {
		},
		onClickRow : function(index, row) {
		},
		onDblClickRow  : function (index, row) {
			if(row.h0 == '0') {
				$.messager.confirm('提示信息','导检单未核收，是否继续？',function(r){
					if(r){
						$('#zg_edit_row').datagrid('loadData',{total:0,rows:[]});  
						$("#zg_edit_row").datagrid('appendRow',row);
					}
				});
			} else {
				$('#zg_edit_row').datagrid('loadData',{total:0,rows:[]});  
				$("#zg_edit_row").datagrid('appendRow',row);
			}
		}
	});
}

var toolbar = [ {
	text : '批量录结果',
	width : 150,
	iconCls : 'icon-add',
	handler : function() {
    	var row = $("#flowexampListzg").datagrid('getSelections');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '请先选中需要录入结果的人!','error');
    		return;
    	}
    	$("#dlg-edit-zg-result").dialog({
    		title:'录入结果',
    		href:'edit_zg_result.action'
    	});
    	$("#dlg-edit-zg-result").dialog("open");
    }
} ];

var exam_result_data = [{
	id:'Y',name:'合格'
	},{
		id:'R',name:'复查'
	},{
		id:'N',name:'不合格'
	},{
		id:'O',name:'其他'
	}
];

function zg_edit_row_init() {
	$("#zg_edit_row").datagrid({
		dataType : 'json',
		data : [],
		toolbar : '#toolbar',
		toolbar : toolbar_row,
		collapsible : true,
		fitColumns : true,
		columns : [ [ 
			{field : 'ck',checkbox : true,hidden : true,},
			{align : 'center',field : 'exam_num',title : tjhname,width : 110,sortable : true}, 
			{align : 'center',field : 'arch_num',title : dahname,width : 70,sortable : true}, 
			{align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable : true}, 
			{align : 'center',field : 'exam_types',title : '体检类型'}, 
			{align : 'center',field : 'user_name',title : '姓名',width : 50,sortable : true},
			{align : 'center',field : 'exam_result',title : '体检结果',"formatter":f_exam_result,sortable : true,editor:{
                type:'combobox',
                options:{
                    valueField:'id',
                    textField:'name',
                    data:exam_result_data,
            	 	panelHeight:'auto',
                },
            }},
            {align : 'center',field : 'exam_doctor',title : '检查医生',editor:{type:'text',},sortable : true},
            {align : 'center',field : 'remark',title : '备注',editor:{type:'text',},sortable : true},
			{align : 'center',field : 'sex',title : '性别',sortable : true},
			{align : 'center',field : 'is_marriage',title : '婚否'}, 
			{align : 'center',field : 'age',title : '年龄',sortable : true}, 
			{align : 'center',field : 'join_date',title : '体检日期',sortable : true}, 
			{align : 'center',field : 'statuss',title : '体检状态'}, 
			{align : 'center',field : 'phone',title : '手机号',sortable : true}, 
			{align : 'center',field : 'vipflag',title : '会员标识',sortable : true}, 
			{align : 'center',field : 'h0date',title : '核收时间',sortable:true},
		] ],
		onLoadSuccess : function(value) {
		},
		onClickRow : function(index, row) {
		},
		onDblClickRow  : function (rowIndex) {
//            if (lastIndex != rowIndex) {
//            	$("#flowexampListzg").datagrid('endEdit', lastIndex);
//            	$("#flowexampListzg").datagrid('beginEdit', rowIndex);
//            }  
//            lastIndex = rowIndex;
			$("#zg_edit_row").datagrid('endEdit', 0);
			$("#zg_edit_row").datagrid('beginEdit', 0);
		}
	});
}

function f_exam_result(val,row,index) {
	var show_val = '';
	for(var i=0;i<exam_result_data.length;i++){
		if(exam_result_data[i].id == val){
			show_val = exam_result_data[i].name;
		}
	}
	/*if(show_val == ''){
		row.disease_class = exam_result_data[0].id;
		show_val = exam_result_data[0].name;
	}*/
	return show_val;
}

var toolbar_row = [ {
	text : '保存',
	width : 75,
	iconCls : 'icon-save',
	handler : function() {
		$("#zg_edit_row").datagrid('endEdit', 0);
    	var row = $("#zg_edit_row").datagrid('getRows');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '没有数据!','error');
    		return;
    	}
    	var exam_num = new Array();
    	$.ajax({
 			url : 'saveZgExamResult.action',
 			type : 'post',
 			data : {
 				"exam_num" : row[0].exam_num,
 				"exam_result" : row[0].exam_result,
 				"exam_doctor" : row[0].exam_doctor,
 				"remark" : row[0].remark,
 			},
 			success : function(text) {
 				if (text.split("-")[1] == 'update') {
 					$.messager.alert('提示信息',text.split("-")[2]);
 					chaxun_report();
 				} else if (text.split("-")[1] == 'save') {
 					$.messager.alert('提示信息',text.split("-")[2]);
 					chaxun();
 					chaxun_report();
				} else {
					$.messager.alert("警告信息",text,"error");
					$("#zg_edit_row").datagrid('beginEdit', 0);
				}
 			},
 			error : function() {
 				$.messager.alert('提示信息', '操作失败！', 'error');
 				$("#zg_edit_row").datagrid('beginEdit', 0);
 			}
 		});
    }
} ];

function chaxun_report() {
	var exam_num = undefined;
	if($('#ck_exam_num_zg_report').attr('checked')){
		exam_num =  $("#exam_num_zg_report").val();
	}
	var time1 = undefined,time2 = undefined;
	if($('#ck_date_report').attr('checked')){
		time1 = $("#time1_report").datebox('getValue');
		time2 = $("#time2_report").datebox('getValue');
	}
	var username = undefined;
	 if($('#ck_username_report').attr('checked')){
		 username = $("#name_report").val();
	 }
	 var phone = undefined;
	 if($('#ck_phone_report').attr('checked')){
		 phone = $("#phone_report").val();
	 }
	 var vipflag = undefined;
	 if($('#ck_vipflag_report').attr('checked')){
		 vipflag = $("#vipflag_report").combobox('getValue');
	 }
	 var exam_type = undefined;
	 if($('#ck_exam_type_report').attr('checked')){
		 exam_type = $("#exam_type_report").combobox('getValue');
	 }
	 var receive_type = undefined;
	 if($('#ck_receive_type').attr('checked')){
		 receive_type = $("#receive_type").combobox('getValue');
	 }
	 var id_num = undefined;
	 if($('#ck_id_num').attr('checked')){
		 id_num = $("#id_num").val();
	 }
	 var com_Type = undefined;
	 if($('#ck_com_Type').attr('checked')){
		 com_Type = $("#com_Type").combobox('getValue');
	 }
	 var exam_result = undefined;
	 if($('#ck_exam_result').attr('checked')){
		 exam_result = $("#exam_result").combobox('getValue');
	 }
	 var company = undefined;
    if($("#ck_com_Name1")[0].checked){
    	company =  $("#com_Name1").val();
	}
	var model = {
		"exam_num" : exam_num,
		"time1" : time1,
		"time2" : time2,
		"username" : username,
		"phone" : phone,
		"vipflag" : vipflag,
		"exam_type" : exam_type,
		"receive_type" : receive_type,
		"id_num" : id_num,
		"com_Type" : com_Type,
		"exam_result" : exam_result,
		"company" : company,
	};
	
	$("#flowexampListzg_report").datagrid({
		url : 'flowexampListzg_report.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		pageNumber : 1,
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 10, 15, 30, 50, 100 ],// 可以设置每页记录条数的列表
		pagination : true,
		remoteSort:false,
		toolbar : toolbar_report,
		collapsible : true,
		fitColumns : true,
		rownumbers:true,
		columns : [ [ 
			{field : 'ck',checkbox : true,},
			{align : 'center',field : 'exam_num',title : tjhname,sortable : true}, 
			{align : 'center',field : 'arch_num',title : dahname,sortable : true}, 
			{align : 'center',field : 'user_name',title : '姓名',sortable : true}, 
			{align : 'center',field : 'exam_results',title : '体检结果',sortable : true},
			{align : 'center',field : 'exam_doctor',title : '检查医生',sortable : true},
			{align : 'center',field : 'receive_type_y',title : '领取方式',sortable : true},
			{align : 'center',field : 'exam_time',title : '结论日期',sortable : true},
			{align : 'center',field : 'exam_types',title : '体检类型',sortable : true}, 
			{align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable : true},
			{align : 'center',field : 'sex',title : '性别',sortable : true},
			{align : 'center',field : 'is_marriage',title : '婚否',sortable : true}, 
			{align : 'center',field : 'age',title : '年龄',sortable : true}, 
			{align : 'center',field : 'join_date',title : '体检日期',sortable : true}, 
			{align : 'center',field : 'remark',title : '招工备注',sortable : true},
			{align : 'center',field : 'remarke',title : '登记台备注',sortable : true},
			{align : 'center',field : 'statuss',title : '体检状态',sortable : true}, 
			{align : 'center',field : 'phone',title : '手机号',sortable : true}, 
			{align : 'center',field : 'vipflag',title : '会员标识',sortable : true}, 
			{align : 'center',field : 'h0date',title : '核收时间',sortable:true},
			{align : 'center',field : 'company',title : '单位',sortable:true},
			{align : 'center',field : 'f_xg',title : '操作',sortable:true,formatter : f_xg},
		] ],
		onLoadSuccess : function(value) {
		},
		rowStyler : function(index, row) {
			if (row.exam_result == 'N') {
				return 'color:red;';
			}
		},
		onClickRow : function(index, row) {
		},
		onDblClickRow  : function (index, row) {
			if (row.exam_result == 'R') {
	    		if(row.status=='Y'){
	    			$.messager.alert("操作提示", "预约体检人员，不能复检登记","error");
	    		}else{
	    			window.parent.addPanel_other("复查个人登记","regetDjtRegisterGEdit.action?exam_id="+row.id+"&ren_type=1","","1");
	    		}
	    	}
		}
	});
}

function f_xg(val, row) {
	return '<a href=\"javascript:reedit_zg_result(\''
			+ row.exam_num
			+ '\', \''
			+ row.user_name
			+ '\', \''
			+ row.exam_result
			+ '\', \''
			+ row.exam_doctor
			+ '\', \''
			+ row.remark
			+ '\')\"><img src=\"themes/default/images/blank.gif\" alt=\"重录结果\" />重录结果</a>';
}

function reedit_zg_result(exam_num, user_name, exam_result, exam_doctor,remark) {
	$("#dlg-reedit-zg-result").dialog({
		title : '重录招工体检结果',
		href : 'reedit_zg_result.action?exam_num=' + exam_num,
		onLoad : function() {
			$('#zg_exam_num').val(exam_num);
			$('#patient_name').val(user_name);
			$('#zg_exam_result').combobox('setValue', exam_result);
			$('#zg_exam_doctor').val(exam_doctor);
			$('#zg_remark').val(remark);
		},
	});
	$("#dlg-reedit-zg-result").dialog("open");
}

var toolbar_report = [ {
	text : '领取报告',
	width : 150,
	iconCls : 'icon-add',
	handler : function() {
    	var row = $("#flowexampListzg_report").datagrid('getSelections');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '请先选中需要领取报告的人!','error');
    		return;
    	}
    	var exam_num = new Array();
    	for(i=0;i<row.length;i++){
//    		if(row[i].receive_type == '1'){
//        		$.messager.alert('提示信息', '存在体检人员的报告已邮寄,不能再自取!','error');
//        		return;
//        	}else if(row[i].receive_type == '2'){
//        		$.messager.alert('提示信息', '存在体检人员的报告已自取,不能再自取!','error');
//        		return;
//        	}
    		exam_num.push(row[i].exam_num);
    	}
    	$.messager.confirm('提示信息','本次选中'+row.length+'份，是否继续？',function(r){
			if(r){
				$("#dlg-edit-thems").dialog({
					title:'报告领取',
					href:'getReportZg.action?exam_num='+exam_num.toString()
				});
				$("#dlg-edit-thems").dialog("open");
			}
    	});
    }
} ];

//批量分配
function report_receive() {
	if ($('#flowexampListzg_report').datagrid('getChecked').length > 0) {
//		$("#allot_dlg").dialog({
//			title : '回访分配',
//			href : 'batch_allot.action',
//		});
//		$("#allot_dlg").dialog("open");
	} else {
		$.messager.alert('提示信息', "请先在下方勾选分配对象");
	}
}

/*function addZgExamResultPage(row) {
	$("#zgExamResult_dlg").dialog({
		title : '录入招工体检结果',
		href : 'addZgExamResultPage.action',
		onLoad : function() {
			$("#examNum").val(row.exam_num);
			$("#archNum").val(row.arch_num);
			$('#patient_name').val(row.user_name);
		},
	});
	$("#zgExamResult_dlg").dialog("open");
}*/

/**
 ****搜索添加输入框根据是否为空自动判断chebox选中
 */
 function setchebox(inp){
 	$('#phone').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.ck_phone').attr('checked',false)
 			} else {
 				$('.ck_phone').attr('checked',true)
 			}
 		}
 	});
 
 	$('#name').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.ck_username').attr('checked',false)
 			} else {
 				$('.ck_username').attr('checked',true)
 			}
 		}
 	});
 }
 
 
