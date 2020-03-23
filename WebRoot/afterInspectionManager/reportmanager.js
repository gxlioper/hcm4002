$(document).ready(function () {
	getGrid();
	$("#ser_exam_num").keydown(function(e){ 
	    // 回车键事件 
		if(e.which == 13) { 
			confirm_print();
		} 
	});
	$('#ser_exam_num_c').keydown(function(e){
		  // 回车键事件 
		if(e.which == 13) { 
			huichetijianhao(this);
			$(this).select();
		} 
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
	$('#ser_exam_num_c').css('ime-mode','disabled');
	$('#ser_exam_num_c').focus();
});
/**
 * 体检号回车查询
 */
var tj_num = "";
function huichetijianhao(tj_num){
	var model = {"exam_num":$(tj_num).val()}
	$.ajax({
		url: 'getReportExamInfoList.action',
		data:model,
		type:'post',
		success:function(data){
			var dd = eval('('+data+')');
			var rows = $("#examinfolist").datagrid('getRows');
			for(i=0;i<rows.length;i++){
				if(dd.rows[0].exam_num == rows[i].exam_num){
					return;
				}
			}
			$("#examinfolist").datagrid('appendRow',dd.rows[0]);
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
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
		$("#com_Name").val(name);
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

var toolbar = [{
    text:'邮寄报告',
    iconCls:'icon-add',
    handler:function(){
    	var row = $("#examinfolist").datagrid('getSelections');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '请先选中需要邮寄报告的人!','error');
    		return;
    	}
    	var exam_num = new Array();
    	for(i=0;i<row.length;i++){
	    	if(row[i].is_report_tidy == 'N'){
	    		$.messager.alert('提示信息', '存在体检人员的报告未确认整理,不能邮寄!','error');
	    		return;
	    	}else if(row[i].receive_type == '1'){
	    		$.messager.alert('提示信息', '存在体检人员的报告已邮寄,不能再邮寄!','error');
	    		return;
	    	}else if(row[i].receive_type == '2'){
	    		$.messager.alert('提示信息', '存在体检人员的报告已自取,不能再邮寄!','error');
	    		return;
	    	}
	    	exam_num.push(row[i].exam_num);
    	}
    	$("#dlg-edit").dialog({
    		title:'邮寄报告',
    		href:'getReportMail.action?exam_num='+exam_num.toString()
    	});
    	$("#dlg-edit").dialog("open");
    }
},{
	text:'自己取报告',
    iconCls:'icon-add',
    handler:function(){
    	var row = $("#examinfolist").datagrid('getSelections');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '请先选中需要自己取报告的人!','error');
    		return;
    	}
    	var exam_num = new Array();
    	for(i=0;i<row.length;i++){
    		if(row[i].is_report_tidy == 'N'){
        		$.messager.alert('提示信息', '存在体检人员的报告未确认整理,不能自取!','error');
        		return;
        	}else if(row[i].receive_type == '1'){
        		$.messager.alert('提示信息', '存在体检人员的报告已邮寄,不能再自取!','error');
        		return;
        	}else if(row[i].receive_type == '2'){
        		$.messager.alert('提示信息', '存在体检人员的报告已自取,不能再自取!','error');
        		return;
        	}
    		
    		exam_num.push(row[i].exam_num);
    	}
    	$("#dlg-edit-thems").dialog({
    		title:'自己取报告',
    		href:'getReportThems.action?exam_num='+exam_num.toString()
    	});
    	$("#dlg-edit-thems").dialog("open");
    }
},{
    text:'批量取消邮寄/自取',
    iconCls:'icon-add',
    handler:function(){
    	var row = $("#examinfolist").datagrid('getSelections');
    	if(row.length == 0){
    		$.messager.alert('提示信息', '请先选中需要取消邮寄/自取报告的人!','error');
    		return;
    	}
    	var exam_num = new Array();
    	for(i=0;i<row.length;i++){
    		exam_num.push("'"+row[i].exam_num+"'");
    	}
    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    	$("body").prepend(str);
    	$.ajax({
    		url:'cancelReportMail.action',
    		data:{exam_num:exam_num.toString()},
    		type:'post',
    		success:function(data){
    			$(".loading_div").remove();
    			$.messager.alert('提示信息', '批量取消邮寄/自取报告成功!',function(){});
    	    	if($("#ser_exam_num").val() != ''){
    	    		getGrid($("#ser_exam_num").val());
    	    	}else{
    	    		getGrid();
    	    	}
    		},
    		error:function(data){
    			$(".loading_div").remove();
    			$.messager.alert('提示信息', '操作失败',function(){});
    		}
    	});
    }
},{
	text:'数据导出',
	width:90,
	iconCls:'icon-check',
	handler:function(){
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var com_name = '';
		if($("#com_Name").val() != ""){
			com_name = $("#company_id").val();
		}
		var model = {"arch_num":$("#ser_arch_num").val(),
				     "exam_num":$("#ser_exam_num_c").val(),
				     "user_name":$("#ser_name").val(),
				     "join_date":$("#join_date").datebox('getValue'),
				     "id_num":$("#ser_id_num").val(),
				     "phone":$("#ser_phone").val(),
				     'com_name':com_name,
				     "is_report_print":$("#is_report_print").combobox("getValue"),
				     "is_report_tidy":$("#is_report_tidy").combobox("getValue"),
				     "receive_type":$("#ser_receive_type").combobox("getValue"),
				     "create_time":$("#create_time").datebox('getValue'),
				      "join_date1":$("#join_date1").datebox('getValue'),
				     "report_tidy_time":$("#report_tidy_time").datebox('getValue'),
				     "report_tidy_time1":$("#report_tidy_time1").datebox('getValue'),
				     "create_time1":$("#create_time1").datebox('getValue'),
				     "page":1,
					 "rows":10000};
		$.ajax({
			url : 'getReportExamInfoList.action',
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
		    	obj.user_name = "姓名";
		    	obj.sex = "性别";
		    	obj.age = "年龄";
		    	obj.is_marriage = "婚否";
		    	obj.phone = "电话";
		    	obj.company = "单位";
		    	obj.exam_type_y = "体检类型";
		    	obj.join_date = "体检日期";
		    	obj.final_date = "总检医生/日期";
		    	obj.update_time = "审核医生/日期";
//		    	obj.print_doctor = "打印人";
//		    	obj.print_time = "打印日期";
		    	obj.is_report_tidy_y = "报告整理";
		    	obj.report_tidy_user = "整理医生/日期";
		    	obj.receive_type_y = "邮寄或自取报告";
		    	
		    	filelist.push(obj);
		    	
		    	for(i=0;i<temp.rows.length;i++){
		    		obj = new Object();
		    		obj.arch_num = temp.rows[i].arch_num;
		    		obj.exam_num = temp.rows[i].exam_num;
			    	obj.id_num = temp.rows[i].id_num;
			    	obj.user_name = temp.rows[i].user_name;
			    	obj.sex = temp.rows[i].sex;
			    	obj.age = temp.rows[i].age;
			    	obj.is_marriage = temp.rows[i].is_marriage;
			    	obj.phone = temp.rows[i].phone;
			    	obj.company = temp.rows[i].company;
			    	obj.exam_type_y = temp.rows[i].exam_type_y;
			    	obj.join_date = temp.rows[i].join_date;
			    	obj.final_date = temp.rows[i].final_date;
			    	obj.update_time = temp.rows[i].update_time;
//			    	obj.print_doctor = temp.rows[i].print_doctor;
//			    	obj.print_time = temp.rows[i].print_time;
			    	obj.is_report_tidy_y = temp.rows[i].is_report_tidy_y;
			    	obj.report_tidy_user = temp.rows[i].report_tidy_user;
			    	obj.receive_type_y = temp.rows[i].receive_type_y;
			    	
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
}];
function getGrid(exam_num){
	if(exam_num == null || exam_num == undefined){
		exam_num = $("#ser_exam_num_c").val();
	}
	var com_name = '';
	if($("#com_Name").val() != ""){
		com_name = $("#company_id").val();
	}
	var model = {"arch_num":$("#ser_arch_num").val(),
			     "exam_num":exam_num,
			     "user_name":$("#ser_name").val(),
			     "join_date":$("#join_date").datebox('getValue'),
			     "join_date1":$("#join_date1").datebox('getValue'),
			     "report_tidy_time":$("#report_tidy_time").datebox('getValue'),
			     "report_tidy_time1":$("#report_tidy_time1").datebox('getValue'),
			     "id_num":$("#ser_id_num").val(),
			     "phone":$("#ser_phone").val(),
			     'com_name':com_name,
			     "create_time":$("#create_time").datebox('getValue'),
			     "create_time1":$("#create_time1").datebox('getValue'),
			     "is_report_print":$("#is_report_print").combobox("getValue"),
			     "is_report_tidy":$("#is_report_tidy").combobox("getValue"),
			     "receive_type":$("#ser_receive_type").combobox("getValue")};
	$("#examinfolist").datagrid({
		url: 'getReportExamInfoList.action',
		queryParams: model,
		rownumbers:true,
		pageSize: 75,//每页显示的记录条数，默认为10 
		pageList:[75,300,500,800,1000],//可以设置每页记录条数的列表 
		toolbar: '#toolbar',
		columns:[[{field:'ck',checkbox:true },
				  {align:"center",field:"getReportWay","title":"领取方式","width":80,"formatter":f_getReportWay},
				  {align:'center',field:"exam_num","title":tjhname,"width":100,sortable:true},
		          {align:"center",field:"arch_num","title":dahname,"width":100,sortable:true},
		          {align:"center",field:"id_num","title":"身份证号","width":150,sortable:true},
		          {align:"center",field:"user_name","title":"姓名","width":80,sortable:true},
		          {align:"center",field:"sex","title":"性别","width":5,sortable:true},
		          {align:"center",field:"age","title":"年龄","width":80,sortable:true},
//		          {align:"center",field:"is_marriage","title":"婚否","width":80,sortable:true},
		          {align:"center",field:"phone","title":"电话","width":150},
		          {align:"center",field:"company","title":"单位","width":250},
		          {align:"center",field:"exam_type_y","title":"体检类型","width":80},
		          {align:"center",field:"join_date","title":"体检日期","width":150,sortable:true},
		           {align:"center",field:"rty","title":"邮寄或自取报告","width":120,"formatter":f_yz},
		          {align:"center",field:"final_date","title":"总检医生/日期","width":200},
		          {align:"center",field:"update_time","title":"审核医生/日期","width":200},
//		          {align:"center",field:"print_doctor","title":"打印人","width":15},
//		          {align:"center",field:"print_time","title":"打印日期","width":15,sortable:true},
		          {align:"center",field:"is_report_tidy_y","title":"报告整理","width":15,sortable:true},
		          {align:"center",field:"receive_date","title":"邮寄或自取/日期","width":20},
		          {align:"center",field:"report_tidy_user","title":"整理医生/日期","width":200}
		         
  		]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    	height: window.screen.height-400,
		    nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
			fitColumns:false,
			fit:false,
		    striped:true,
	        toolbar:toolbar	        
	});
}
//确定打印报告
function confirm_print(){
	var exam_num = $("#ser_exam_num").val();
	if(exam_num == ''){
		return;
	}
	$.ajax({
		url:'confirmReportPrint.action',
		data:{'exam_num':exam_num},
		type:'post',
		success:function(data){
			$("#message_examinfo").html(data);
//			getGrid(exam_num);
			$("#ser_exam_num").select();
			$.ajax({
				url:'getReportExamInfoList.action',
				data:{'exam_num':exam_num},
				type:'post',
				success:function(data){
					var dd = eval('('+data+')');
					var rows = $("#examinfolist").datagrid('getRows');
					for(i=0;i<rows.length;i++){
						if(dd.rows[0].exam_num == rows[i].exam_num){
							return;
						}
					}
					$("#examinfolist").datagrid('appendRow',dd.rows[0]);
				}
			});
		},
		error:function(){
			$("#message_examinfo").html('确认报告整理失败!');
		}
	});
}

function f_yz(val,row){
	if(row.receive_type == '0'){
		return row.receive_type_y;
	}else if(row.receive_type == '1'){
		return '<a href=\"javascript:f_showreport_m(\''+row.exam_num+'\')\">'+row.receive_type_y+'</a>'
	}else if(row.receive_type == '2'){
		return '<a href=\"javascript:f_showreport_t(\''+row.exam_num+'\')\">'+row.receive_type_y+'</a>'
	}
}

function f_showreport_m(exam_num){
	$("#dlg-s").dialog({
		title:'邮寄报告信息',
		href:'showReportMail.action?exam_num='+exam_num
	});
	$("#dlg-s").dialog("open");
}

function f_showreport_t(exam_num){
	$("#dlg-s").dialog({
		title:'领取报告信息',
		href:'showReportThems.action?exam_num='+exam_num
	});
	$("#dlg-s").dialog("open");
}
function  f_getReportWay(val,row){
	if(val == '1'){
		return "邮寄";
	}else if(val == '2'){
		return "自取";
	}else if(val == '3'){
		return "电子报告";
	}
		
}
function  delAll(){
	$("#ser_exam_num_c").val("");
    $("#ser_name").val("");
 	$("#join_date").datebox('setValue','');
 	$("#join_date1").datebox('setValue','');
 	$("#report_tidy_time").datebox('setValue','');
 	$("#report_tidy_time1").datebox('setValue','');
 	$("#ser_id_num").val('');
 	$("#ser_phone").val('');
	$("#create_time").datebox('setValue',''),
	$("#create_time1").datebox('setValue',''),
    $("#is_report_print").combobox('setValue',''),
	$("#is_report_tidy").combobox('setValue',''),
	$("#ser_receive_type").combobox('setValue','');
	$("#com_Name").val('');
}
