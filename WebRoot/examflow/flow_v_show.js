/**
 * 回访
 */

$(document).ready(function () {		
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	chaxun_visited();
	    	chaxun();
	     }
	}
	

	setchebox();
});
// 查询人员基本信息
function setcustomerInfo(row) {
	if (row) {
		$("#exam_num").val(row.exam_num);
		$("#arch_num").val(row.arch_num);
		$("#username").html(row.user_name);
		$("#sex").html(row.sex+'&nbsp;性');
		$("#age").html(row.age+'&nbsp;岁');
		$("#userphone").html(row.phone);
		$("#uservipflag").html(row.vipflag);
		$("#join_date").html(row.join_date);
		$("#company").html(row.company);
		$("#exam_type").html(row.exam_types);
		$("#receive_type").html(row.receive_type_y);
		$("#visit_btn").show();
	} else {
		$("#exam_num").val("");
		$("#arch_num").val("");
		$("#username").html("");
		$("#sex").html("");
		$("#age").html("");
		$("#userphone").html("");
		$("#uservipflag").html("");
		$("#join_date").html("");
		$("#company").html("");
		$("#exam_type").html("");
		$("#receive_type").html('');
		$("#visit_btn").hide();
	}
}

function getcircleGrid(){
	var model = {"done_flag": '',
			"startCheckDate": $("#time1").datebox('getValue'),
			"endCheckDate": $("#time2").datebox('getValue')};
	   $("#exam_circl").datagrid({
		url: 'criticalList.action',
		queryParams: model,
        pageSize: 15,//每页显示的记录条数，默认为10 
        pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		height:400,
        columns:[[
				  {align:"center",field:"id","title":"ID","hidden":true},
                  {align:'center',field:"arch_num","title":dahname,width:15},
        		  {align:"center",field:"exam_num","title":tjhname,"width":25},
        		  {align:"center",field:"user_name","title":"姓名","width":20},
        		  {align:"center",field:"dep_name","title":"科室","width":20},
        		  {align:"center",field:"item_name","title":"检查项目","width":20},
        		  {align:"center",field:"exam_result","title":"检查结果","width":30},
        		  {align:"center",field:"check_date","title":"体检日期","width":20},
        		  {align:"center",field:"disease_name","title":"阳性名称","width":20},
        		  {align:'center',field:"check_doctor","title":"处理医生","width":15},
        		  {align:'center',field:"done_date","title":"处理日期","width":15},
        		  {align:"center",field:"note","title":"处理内容","width":20},
        		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination:true,
        fitColumns:true,
      	fit:true,
	});
}
function getflowLoglist(exam_num) {//
	if(!exam_num) {
		return;
	}
	var model = {
		"exam_num" : exam_num
	};
	$("#flowloglist").datagrid({
		url : 'flowexamloglist.action',
		queryParams : model,
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		remoteSort : false,
		columns : [ [ {
			align : "center",
			field : "senddate",
			"title" : "时间",
			"width" : 28,
			sortable : 'true'
		}, {
			align : 'center',
			field : "flow_name",
			"title" : "事件",
			"width" : 22
		}, {
			align : 'center',
			field : "senduname",
			"title" : "发送者",
			"width" : 15,
			sortable : 'true'
		},/* {
			align : 'center',
			field : "accuname",
			"title" : "接收者",
			"width" : 15
		},*/ {
			align : "center",
			field : "flow_types",
			"title" : "状态",
			"width" : 10
		} ] ],
		onLoadSuccess : function(value) {
		},
		singleSelect : false,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		border : false,
		nowrap : false
	});
}

function chaxun(arg, callback) {
	var visit_leader = ($("#visit_leader").val()=='true');
	getcircleGrid();
	setcustomerInfo();//将左侧基本信息栏置空
	getflowLoglist($("#exam_num_v").val());
	var ptype=0;
	var exam_num = undefined;
	if($('#ck_exam_num_v').attr('checked')){
		exam_num =  $("#exam_num_v").val();
	}
	var vtcreater = undefined;
	if(visit_leader && $('#ck_vtcreater').attr('checked')){
		vtcreater =  $("#vtcreater").combobox('getValue');
		ptype=1;
	}else if(!visit_leader){
		ptype=1;
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
	var model = {
		"exam_num" : exam_num,
		"remark" : vtcreater,
		"visit_leader" : $("#visit_leader").val(),
		"time1" : time1,
		"time2" : time2,
		"username" : username,
		"phone" : phone,
		"vipflag" : vipflag,
		"ptype" : ptype
	};
	
	if(arg) {
		model = arg;
	}
	
	$("#flowexampListv").datagrid({
		url : 'flowexampListv.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		pageNumber : 1,
		pageSize : 15,// 每页显示的记录条数，默认为10
		pageList : [ 10, 15 ],// 可以设置每页记录条数的列表
		pagination : true,
		toolbar : visit_leader?toolbar:[],
		collapsible : true,
		fitColumns : true,
		rownumbers:true,
		columns : [ [ {
			field : 'ck',
			checkbox : true,
			hidden : !visit_leader
		},{
			align : 'center',
			field : 'vtcreater',
			title : '分配回访人',
			width : 17,
			sortable : true,
			hidden : !visit_leader
		},{
			align : 'center',
			field : 'exam_num',
			title : tjhname,
			width : 17,
			sortable : true
		}, {
			align : 'center',
			field : 'arch_num',
			title : dahname,
			width : 12,
			sortable : true
		}, {
			align : 'center',
			field : 'id_num',
			title : '身份证号',
			width : 25,
			sortable : true
		}, {
			align : 'center',
			field : 'exam_types',
			title : '体检类型',
			width : 12,
		}, {
			align : 'center',
			field : 'user_name',
			title : '姓名',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'sex',
			title : '性别',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'is_marriage',
			width : 10,
			title : '婚否'
		}, {
			align : 'center',
			field : 'age',
			title : '年龄',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'join_date',
			title : '体检日期',
			width : 17,
			sortable : true
		}, {
			align : 'center',
			field : 'statuss',
			title : '体检状态',
			width : 12,
		}, {
			align : 'center',
			field : 'phone',
			title : '手机号',
			width : 25,
			sortable : true
		}, {
			align : 'center',
			field : 'vipflag',
			title : '会员标识',
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'strh0',
			title : '核收状态',
			width : 12,
		}, {
			align : 'center',
			field : 'h0date',
			title : '核收时间',
			width : 20,
		}, ] ],
		onLoadSuccess : function(value) {
			samePhoneCheck = true;
			if(typeof callback == 'function') {
				callback();
			}
		},
		rowStyler : function(index, row) {
			if (row.h0 == 0) {
				return 'color:red;';
			}
		},
		onClickRow : function(index, row) {
		},
		onDblClickRow : function(index, row) {
			setcustomerInfo(row);
			getflowLoglist(row.exam_num);
			getexamallxmGrid(row.exam_num);
			getjcxxGrid(row.exam_num);
			getFinalSummary(row.exam_num);
			getExamDisease(row.exam_num);
			//$("#tt").tabs("select", "项目列表");
		}
	});
}

var toolbar = [ {
	text : '分配回访医生',
	width : 150,
	iconCls : 'icon-save',
	handler : function() {
		batch_allot();
	}
} ];

function chaxun_visited() {
	var exam_num = undefined;
	 if($("#ck_exam_num_v_visited")[0].checked){
		 exam_num =  $("#exam_num_v_visited").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date_visited")[0].checked){
		 time1 = $("#time1_visited").datebox('getValue');
		 time2 = $("#time2_visited").datebox('getValue');
	 }
	 var time3 = undefined,time4 = undefined;
	 if($("#ck_date_visited2")[0].checked){
		 time3 = $("#time3_visited").datebox('getValue');
		 time4 = $("#time4_visited").datebox('getValue');
	 }
	 var remark = undefined;
	 if($("#ck_screater")[0].checked){
		 remark = $("#screater").combobox('getValue');
	 }
	 var visit_type = undefined;
	 if($("#ck_visit_type")[0].checked){
		 visit_type = $("#visit_type_visited").combobox('getValue');
	 }
	 var username = undefined;
	 if($("#ck_username_visited")[0].checked){
		 username = $("#username_visited").val();
	 }
	 var phone = undefined;
	 if($("#ck_phone_visited")[0].checked){
		 phone = $("#phone_visited").val();
	 }
	 var vipflag = undefined;
	 if($("#ck_vipflag_visited")[0].checked){
		 vipflag = $("#vipflag_visited").combobox('getValue');
	 }
	 
	 var ptype=0;
	 if($("#ck_visitedtype")[0].checked){
		 ptype = $("#visitedtype").combobox('getValue');
	 }
	 
	var model = {
		"exam_num" : exam_num,
		"time1" : time1,
		"time2" : time2,
		"time3" : time3,
		"time4" : time4,
		"remark": remark,
		"visit_type" : visit_type,
		"username" : username,
		"phone" : phone,
		"vipflag" : vipflag,
		"ptype":ptype
	};
	var visit_leader = ($("#visit_leader").val()=='true');
	$("#flowexampListv_visited").datagrid({
		url : 'flowexampListv_visited.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		pageNumber : 1,
		pageSize : 15,// 每页显示的记录条数，默认为10
		pageList : [ 15, 30, 45, 60, 100 ],// 可以设置每页记录条数的列表
		fitColumns : true,
		pagination : true,
		rownumbers : true,
		remoteSort : false,
		columns : [ [
		//{field:'ck',checkbox:true },
		{
			align : 'center',
			field : 'exam_num',
			title : tjhname,
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'arch_num',
			title : dahname,
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'id_num',
			title : '身份证号',
			width : 28,
			sortable : true
		}, {
			align : 'center',
			field : 'exam_types',
			title : '体检类型',
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'user_name',
			title : '姓名',
			width : 15,
			sortable : true
		}, {
			align : 'center',
			field : 'sex',
			title : '性别',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'is_marriage',
			title : '婚否',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'age',
			title : '年龄',
			width : 10,
			sortable : true
		}, {
			align : 'center',
			field : 'phone',
			title : '手机号',
			width : 25,
			sortable : true
		}, {
			align : 'center',
			field : 'vipflag',
			title : '会员标识',
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'company',
			title : '单位',
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'join_date',
			title : '体检日期',
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'statuss',
			title : '体检状态',
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'v_name',
			title : '是否回访',
			width : 20,
			sortable : true
		}, {
			align : 'center',
			field : 'customer_feedback',
			title : '回访内容',
			width : 25,
			sortable : true
		}, {
			align : 'center',
			field : 'visit_result',
			title : '回访结果',
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'visit_type',
			title : '回访方式',
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'visit_doctor',
			title : '回访医生',
			width : 18,
			sortable : true
		}, {
			align : 'center',
			field : 'visit_date',
			title : '回访时间',
			width : 40,
			sortable : true
		}, {
			align : 'center',
			field : 'remark',
			title : '回访备注',
			width : 25,
			sortable : true
		}, /*{
			align : 'center',
			field : 'f_xg',
			title : '修改',
			width : 15,
			"formatter" : f_xg
		},*/ ] ],
		toolbar:toolbar1,
		onLoadSuccess : function(value) {
		},
		rowStyler : function(index, row) {
			if (row.h0 == 0) {
				return 'color:red;';
			}
			if (row.v == 0) {
				return 'color:red;';
			}
		},
		onClickRow : function(index, row) {
		},
		onDblClickRow : function(index, row) {
			var row = $('#flowexampListv_visited').datagrid('getRows')[index];
			if(row.vtcreater != $("#uname").val() && row.visit_doctor != $("#uname").val() && !visit_leader) {
				return;
			}
			//reVisit(row.exam_num, row.user_name);
			$('#tts').tabs('select','待回访');
		    $('#flowexampListv').datagrid('loadData',{total:0,rows:[]});  
			$("#flowexampListv").datagrid('appendRow',row);

			setcustomerInfo(row);
			getflowLoglist(row.exam_num);
			getexamallxmGrid(row.exam_num);
			getjcxxGrid(row.exam_num);
			getFinalSummary(row.exam_num);
			getExamDisease(row.exam_num);
		}
	});
}

var toolbar1 = [ {
	text : '数据导出',
	width : 90,
	iconCls : 'icon-check',
	handler : function() {
		var filed1 =$(".datagrid-sort-asc").parent().eq(0).attr('field');
		var filed2 = $(".datagrid-sort-desc").parent().eq(0).attr('field');
		var sort = undefined;order = undefined;
		if(filed1 != undefined){
			sort = filed1;
			order = 'asc';
		}
		if(filed2 != undefined){
			sort = filed2;
			order = 'desc';
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var exam_num = undefined;
		 if($("#ck_exam_num_v_visited")[0].checked){
			 exam_num =  $("#exam_num_v_visited").val();
		 }
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_date_visited")[0].checked){
			 time1 = $("#time1_visited").datebox('getValue');
			 time2 = $("#time2_visited").datebox('getValue');
		 }
		 var time3 = undefined,time4 = undefined;
		 if($("#ck_date_visited2")[0].checked){
			 time3 = $("#time3_visited").datebox('getValue');
			 time4 = $("#time4_visited").datebox('getValue');
		 }
		 var remark = undefined;
		 if($("#ck_screater")[0].checked){
			 remark = $("#screater").combobox('getValue');
		 }
		 var visit_type = undefined;
		 if($("#ck_visit_type")[0].checked){
			 visit_type = $("#visit_type_visited").combobox('getValue');
		 }
		 var username = undefined;
		 if($("#ck_username_visited")[0].checked){
			 username = $("#username_visited").val();
		 }
		 var phone = undefined;
		 if($("#ck_phone_visited")[0].checked){
			 phone = $("#phone_visited").val();
		 }
		 var vipflag = undefined;
		 if($("#ck_vipflag_visited")[0].checked){
			 vipflag = $("#vipflag_visited").combobox('getValue');
		 }
		 
		 var ptype=0;
		 if($("#ck_visitedtype")[0].checked){
			 ptype = $("#visitedtype").combobox('getValue');
		 }
		 
		var model = {
			"exam_num" : exam_num,
			"time1" : time1,
			"time2" : time2,
			"time3" : time3,
			"time4" : time4,
			"remark": remark,
			"visit_type" : visit_type,
			"username" : username,
			"phone" : phone,
			"vipflag" : vipflag,
			"ptype":ptype,
			"page":1,
			"rows":1000,
			"sort":sort,
			"order":order
		};
		$.ajax({
			url : 'flowexampListv_visited.action',
			data : model,
			type : "post",//数据发送方式   
			success : function(data) {
				var temp = eval('('+data+')');
				if(temp.rows.length == 0){
					$(".loading_div").remove();
					$.messager.alert("操作提示", "没有需要导出的回访人员信息!","error");
					return;
				}
		    	var filelist = new Array();
		    	var obj = new Object();
		    	obj.exam_num = tjhname;
		    	obj.arch_num = dahname;
		    	obj.id_num = "身份证号";
		    	obj.exam_types = "体检类型";
		    	obj.user_name = "姓名";
		    	obj.sex = "性别";
		    	obj.is_marriage = "婚否";
		    	obj.age = "年龄";
		    	obj.phone = "手机号";
		    	obj.vipflag = "会员标识";
		    	obj.company="单位";
		    	obj.set_name = "套餐";
		    	obj.join_date = "体检日期";
		    	obj.statuss = "体检状态";
		    	obj.customer_feedback = "回访内容";
		    	obj.visit_result = "回访结果";
		    	obj.visit_type = "回访方式";
		    	obj.visit_doctor = "回访医生";
		    	obj.visit_date = "回访时间";
		    	obj.remark = "回访备注";
		    	
		    	filelist.push(obj);
		    	
		    	for(i=0;i<temp.rows.length;i++){
		    		obj = new Object();
		    		obj.exam_num = temp.rows[i].exam_num;
			    	obj.arch_num = temp.rows[i].arch_num;
			    	obj.id_num = temp.rows[i].id_num;
			    	obj.exam_types = temp.rows[i].exam_types;
			    	obj.user_name = temp.rows[i].user_name;
			    	obj.sex = temp.rows[i].sex;
			    	obj.is_marriage = temp.rows[i].is_marriage;
			    	obj.age = temp.rows[i].age;
			    	obj.phone = temp.rows[i].phone;
			    	obj.vipflag = temp.rows[i].vipflag;
			    	obj.company = temp.rows[i].company;
			    	obj.set_name = temp.rows[i].set_name;
			    	obj.join_date = temp.rows[i].join_date;
			    	obj.statuss = temp.rows[i].statuss;
			    	obj.customer_feedback = temp.rows[i].customer_feedback;
			    	obj.visit_result = temp.rows[i].visit_result;
			    	obj.visit_type = temp.rows[i].visit_type;
			    	obj.visit_doctor = temp.rows[i].visit_doctor;
			    	obj.visit_date = temp.rows[i].visit_date;
			    	obj.remark = temp.rows[i].remark;
			    	filelist.push(obj);
		    	}
		    	$.ajax({
					url : 'saveDatagridData.action',
					data : {filelist:JSON.stringify(filelist)},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						var myDate = new Date();
						var month=myDate.getMonth()+1;
						var riqi= myDate.getFullYear()+'-'+month+'-'+myDate.getDate();
						window.location.href='datagridExportExcel.action?filename='+'回访管理'+riqi;
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
} ];

function f_xg(val, row) {
	var visit_leader = ($("#visit_leader").val()=='true');
	if(row.visit_doctor != $("#uname").val() && !visit_leader) {
		return '';
	}
	return '<a href=\"javascript:reVisit(\''
			+ row.exam_num
			+ '\', \''
			+ row.user_name
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"重新回访\" /></a>';
}

function reVisit(exam_num, name) {
	$("#visit_dlg").dialog({
		title : '修改回访信息',
		href : 'editVisitPage.action?exam_num=' + exam_num,
		onLoad : function() {
			$('#patient_name').val(name);
		},
	});
	$("#visit_dlg").dialog("open");
}

// 批量分配
function batch_allot() {
	if ($('#flowexampListv').datagrid('getChecked').length == 0) {
		$.messager.alert('提示信息', "请先在下方勾选分配对象");
		return;
	}
	$("#allot_dlg").dialog({
		title : '回访分配',
		href : 'batch_allot.action',
	});
	$("#allot_dlg").dialog("open");
}

// 回访
function addVisit() {
	if(samePhoneCheck) {
		$.ajax({
			url:'getExamInfoPhone.action?exam_num='+$('#exam_num').val(),
			type:'post',
			success:function(phone){
				if(phone.length>0){
					$.messager.confirm('确认','手机号有相同的人',function(r){    
						if (r){    
							chaxun({
								"phone" : phone,
								"ptype" : 1,
								"visit_leader":$("#visit_leader").val(),
							},function() {
								samePhoneCheck = false;
							});
						}    
					});  
				} else {
					addVisit_do();
				}
			},error:function(){
				$.messager.alert("提示信息","操作失败","error");
			}
		})
	} else {
		addVisit_do();
	}
}

function addVisit_do() {
	$("#visit_dlg").dialog({
		title : '回访登记',
		href : 'editVisitPage.action?exam_num=' + $('#exam_num').val(),
		onLoad : function() {
			$("#examNum").val($("#exam_num").val());
			$("#archNum").val($("#arch_num").val());
			$('#patient_name').val($("#username").html());
		},
	});
	$("#visit_dlg").dialog("open");
}

function allot_random() {
	$.ajax({
		url : 'allot_random.action',
		type : "post",
		success : function(data) {
			$.messager.alert('提示信息', data);
			chaxun({
				time1:$("#today").val(),
	    		time2:$("#today").val(),
	    		"visit_leader":$("#visit_leader").val(),
	    		ptype:1
			});
		},
		error : function() {
			$.messager.alert('提示信息', "用户操作失败！", function() {
			});
		}
	})
}

function getStatisticsData() {
	$.ajax({
		url : 'getStatisticsData.action',
		type : "get",
		success : function(data) {
			var obj = eval("(" + data + ")");
			$("#all").html(obj.all);
			$("#visited").html(obj.all - obj.notVisit);
			$("#notVisit").html(obj.notVisit);
			$("#notVisitVIP").html(obj.notVisitVIP);
			//$("#myself").html(obj.myself);
		},
		error : function() {
		}
	})
}

var selectexam_num;
function getexamallxmGrid(selectexam_num) {
	var model = {
		"exam_num" : selectexam_num
	};
	$("#wjxm").datagrid({
		url : 'flowEndRecoveryexam.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		columns : [ [ {
			align : 'center',
			field : 'item_code',
			title : '项目编码',
			width : 12
		}, {
			align : 'center',
			field : 'item_name',
			title : '项目名称',
			width : 15
		}, {
			align : 'center',
			field : 'dep_name',
			title : '科室名称',
			width : 12
		}, {
			align : 'center',
			field : 'exam_statuss',
			title : '状态',
			width : 12
		}, {
			align : 'center',
			field : 'sample_statuss',
			title : '采样状态',
			width : 12
		}, {
			align : 'center',
			field : 'create_time',
			title : '最晚检查日期',
			width : 15
		} ] ],
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true,
		fit : true,
		onLoadSuccess : function(data) {
			$("#exam_num_v_visited").select();
			$("#exam_num_v_visited").focus();
		},
		rowStyler : function(index, row) {
			if (row.exam_status == 'Y') {
				return 'color:blue;';
			} else if (row.exam_status == 'G') {
				return 'color:red;';
			} else if (row.exam_status == 'D') {
				return 'color:green;';
			}
		}
	});
}

function getjcxxGrid(selectexam_num) {//
	var model = {
		"exam_num" : selectexam_num
	};
	$("#item_result").datagrid({
		url : 'getAcceptanceItemResult.action',
		queryParams : model,
		rownumbers : false,
		pageSize : 15,// 每页显示的记录条数，默认为10
		pageList : [ 15, 30, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [ [ {
			align : "center",
			field : "dep_name",
			"title" : "收费项目",
			"width" : 10
		}, {
			align : '',
			field : "item_name",
			"title" : "检查项目",
			"width" : 15,
			"styler" : f_clolor
		}, {
			align : 'center',
			field : "exam_doctor",
			"title" : "检查医生",
			"width" : 10
		}, {
			align : 'center',
			field : "exam_date",
			"title" : "检查时间",
			"width" : 15
		}, {
			align : "",
			field : "exam_result",
			"title" : "检查结果",
			"width" : 25,
			"styler" : f_clolor,
			"formatter":f_flowor
		} ] ],
		onLoadSuccess : function(value) {
			MergeCells('item_result', 'dep_name,exam_doctor,exam_date');
		},
		singleSelect : true,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		striped:true,
		toolbar : "#toolbar",
		border : false,
		nowrap : false
	});
}

/**
* EasyUI DataGrid根据字段动态合并单元格
* @param fldList 要合并table的id
* @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
*/
function MergeCells(tableID, fldList) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 0; i--) {
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			} else {
				CurValue = dg.datagrid("getRows")[row][fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			} else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index : index,
					field : fldName,
					rowspan : span,
					colspan : null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}

function f_clolor(value, row, index) {
	if (row.dep_category == '17') {//一般检查（普通科室）
		if (row.health_level == 'Y') {
			return 'color:#F00;';//red
		} else if (row.health_level == 'W') {
			return 'color:#FF9B00;';//yellow
		}
		if (row.item_id == '0') {
			return 'background:#FEEAA8;color:#ff5100;';//背景淡黄，颜色橙
		}
	} else if (row.dep_category == '131') {//化验室
		if(row.health_level == 1){
			return 'color:#ff0000;';
		}else if(row.health_level == 2){
			return 'color:#0000ff;';
		}else if(row.health_level == 3){
			return 'color:#ff5100;';
		}else if(row.health_level == 4){
			return 'color:#FF9B00;';
		}
	}
}

function f_flowor(val,row){
	if(row.dep_category == '21'){
		if(row.exam_result == 'image_path'){
			return '<a href="javascript:void(0)" onclick = "show_picture('+row.item_id+')">查看图片</a>';
		}else{
			return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
		}
	}else if(row.dep_category == '131'){
		if(row.health_level == 1){
			return row.exam_result+' ↑';
		}else if(row.health_level == 2){
			return row.exam_result+' ↓';
		}else{
			return row.exam_result;
		}
	}else{
		if(row.exam_result!=null){
		    return row.exam_result.replace(/\n/g,'</br>');
		}
	}
}



//总检结论
function getFinalSummary(exam_num){
	$.ajax({
		url:'getFinalExamResult.action?exam_num='+exam_num+'&sug_flag=N',
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#zongjianjielun").val(obj.final_exam_result);
			}
		}
	});
}

function getExamDisease(exam_num){
	 var model={"exam_num":exam_num};
	$("#exam_disease").datagrid({
			 url:'getExamDiseaseResult.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
//		     pageSize: 15,//每页显示的记录条数，默认为10 
//		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20,"formatter":f_font_size}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
//			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}

function f_font_size(value,row,index){
	var count = row.suggest.indexOf("：")+1;
	if(count != -1){
		var tou = row.suggest.substring(0,count);
		var wei = row.suggest.substring(count,row.suggest.length-1);
		
		return '<span style="font-weight:bold;">'+tou+'</span>'+wei;
	}else{
		return row.suggest;
	}
}

/**
 ****搜索添加输入框根据是否为空自动判断chebox选中
 */
 function setchebox(inp){
 	$('#username_visited').textbox({  
 	    onChange: function(value) {
 	    	if(value==""){
 	    		$('.ck_username_visited').attr('checked',false)
 	    	} else {
 	    		$('.ck_username_visited').attr('checked',true)
 	    	}
 	    }
 	});
 	$('#exam_num_v_visited').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.ck_exam_num_v_visited').attr('checked',false)
 			} else {
 				$('.ck_exam_num_v_visited').attr('checked',true)
 			}
 		}
 	});
 
 	$('#phone_visited').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.ck_phone_visited').attr('checked',false)
 			} else {
 				$('.ck_phone_visited').attr('checked',true)
 			}
 		}
 	});
 	
 	$('#exam_num_v').textbox({  
 	    onChange: function(value) {
 	    	if(value==""){
 	    		$('.ck_username_visited').attr('checked',false)
 	    	} else {
 	    		$('.ck_exam_num_v').attr('checked',true)
 	    	}
 	    }
 	});
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
 
 
