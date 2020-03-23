var serch_status = false;zyb_serch_status = false;
$(document).ready(function () { 
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });	
	
	$('#exam_status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS3',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
        onLoadSuccess:function(data){ 	
        	if((data!=undefined)&&(data.length>0)){
				$('#exam_status').combobox('setValue', data[0].id);	
				}
		}
	});
	
	$('#zybexam_status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS3',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
        onLoadSuccess:function(data){ 	
        	if((data!=undefined)&&(data.length>0)){
				$('#zybexam_status').combobox('setValue', data[0].id);	
				}
		}
	});
	
	$('#zybis_report_print').combobox({
		data : [{
			'id':'N',
			'name':'未打印',
		},{
			'id':'Y',
			'name':'已打印',
		},],
		editable : false, //不可编辑状态
		cache : false,
		multiple:false,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess:function(data){ 	
			if((data!=undefined)&&(data.length>0)){
				$('#zybis_report_print').combobox('setValue', data[0].id);	
			}
		}
	});
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,//是否允许多选
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
				$('#rylb').combobox('setValue', data[0].id);	
				}
		}
	});

	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLXCOMM',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
				$('#tjlx').combobox('setValue', data[0].id);	
				}
		}
	});
	
	$('#zybtjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLXZYB',
		editable : false, //不可编辑状态
		cache : false,
		multiple:true,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#zybtjlx').combobox('setValue', data[0].id);	
			}
		}
	});
	$('#zybexam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getExamInfoByNumzyb();
        }
    });
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	getExamInfoByNum();
        }
    });
	getteamExamInfoShowGrid();
	//getteamExamInfoShowGridzyb()
});

function getExamInfoByNum(){
	var model = {
			"exam_num":$("#exam_num").val(),
			"chkItem":"exam_num,serch"
		};
	if(!serch_status){
		zyb_serch_status = false;
		serch_status = true;
		$("#teamExamInfoShow").datagrid({
			url:'termReportUserList.action',
			dataType : 'json',
			queryParams : model,
			remoteSort:false,
			rownumbers : true,
			pageSize : 200,// 每页显示的记录条数，默认为10
			pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
			columns : [[  {field:'ck',checkbox:true },
			              {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
			              {align : 'center',field : 'exam_num',title : '体检编号',width : 18,"formatter":f_showexam,sortable:true}, 
			              {align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable:true}, 
			              {align : 'center',field : 'user_name',title : '姓名',width : 15,sortable:true}, 
			              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
			              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
			              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true}, 	
			              {align : 'center',field : 'tjlx',title : '体检类型',width :15,sortable:true},		
			              {align : 'center',field : 'statussss',title : '体检状态',width :15,"formatter":f_showstatus,sortable:true},		                            
			              {align : 'center',field : 'group_name',title : '分组',width : 20,sortable:true}, 
			              {align : 'center',field : 'set_name',title : '套餐',width : 47,sortable:true}
			              ]],
			onLoadSuccess : function(value) {
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
	}else{
		$.ajax({
			url:'termReportUserList.action',
			data:model,
			success:function(data){
				var obj = $("#teamExamInfoShow").datagrid("getRows");
				var exam = eval('('+data+')');
				for(i=0;i<exam.rows.length;i++){
					var flag = true;
					for(j=0;j<obj.length;j++){
						if(exam.rows[i].exam_num == obj[j].exam_num){
							flag = false;
						}
					}
					if(flag){
						$("#teamExamInfoShow").datagrid("appendRow",exam.rows[i]);
					}
				}
			}
		});
	}
}

function getExamInfoByNumzyb(){
	 var chk_value ="";    
	 $('input[name = chkItem]:checked').each(function(){    
	  chk_value=chk_value+","+($(this).val());    
	 }); 
	 if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	 }
	 
	 var levels = $("#zyblevelss").combobox('getValues');
		 var levelss = new Array();
		 for(i=0;i<levels.length;i++){
			 if(levels[i] != ''){
				 levelss.push("'"+levels[i]+"'"); 
			 }
		 }
		 
	var tjlxs = $("#zybtjlx").combobox('getValues');
	var tjlxss = new Array();
	for(i=0;i<tjlxs.length;i++){
		if(tjlxs[i] != ''){
		   tjlxss.push("'"+tjlxs[i]+"'"); 
	   }
	 }
	
	var zytjlx_ids = $("#zytjlx_id").combobox('getValues');
	var zytjlx_idss = new Array();
	for(i=0;i<zytjlx_ids.length;i++){
		if(zytjlx_ids[i] != ''){
			zytjlx_idss.push("'"+zytjlx_ids[i]+"'"); 
	   }
	 }
	
	
	var zywhys_ids = $("#zywhys_id").combobox('getValues');
	var zywhys_idss = new Array();
	for(i=0;i<zywhys_ids.length;i++){
		if(zywhys_ids[i] != ''){
			zywhys_idss.push("'"+zywhys_ids[i]+"'"); 
	   }
	 }
		 
	var model = {
		"exam_num":$("#zybexam_num").val(),
		"custname":$("#zybcustname").val(),
		"zytjlx":zytjlx_idss.toString(),
		"zywhys":zywhys_idss.toString(),
		"exam_status":$('#zybexam_status').combobox('getValues').join(','),
		"is_report_print":$('#zybis_report_print').combobox('getValue'),
		"levels":levelss.toString(),
		"tjlxs":tjlxss.toString(),
		"chkItem":chk_value+',zybexam_num,serch'
	};
	if(!zyb_serch_status){
		zyb_serch_status = true;
		serch_status = false;
			$("#teamExamInfoShow").datagrid({
				url:'termReportUserZybList.action',
				dataType : 'json',
				queryParams : model,
				remoteSort:false,
				toolbar:'#toolbar',
				rownumbers : true,
				pageSize : 200,// 每页显示的记录条数，默认为10
				pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
				columns : [[  {field:'ck',checkbox:true },
				              {align:'center',field:'examreport',title:'报告',"formatter":f_reportshowzyb},
				              {align : 'center',field : 'exam_num',title : '体检编号',width : 18,"formatter":f_showexam,sortable:true}, 
				              {align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable:true}, 
				              {align : 'center',field : 'user_name',title : '姓名',width : 15,sortable:true}, 
				              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
				              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
				              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true}, 	
				              {align : 'center',field : 'tjlx',title : '体检类型',width :15,sortable:true},		
				              {align : 'center',field : 'statussss',title : '体检状态',width :15,"formatter":f_showstatus,sortable:true},
				              {align : 'center',field : 'is_report_print_y',title : '打印状态',width :15,sortable:true}
//				              {align : 'center',field : 'zytjlxname',title : '职业体检',width : 20,sortable:true}, 
//				              {align : 'center',field : 'zywhysname',title : '职业危害',width : 47,sortable:true}
				              ]],
				onLoadSuccess : function(value) {
					$("#datatotal").val(value.total);
					$(".loading_div").remove();
					$("#zybexam_num").next().children("input:first-child").select();
				},
				singleSelect:false,
			   collapsible:true,
				pagination: true,
			   fitColumns:true,
			   striped:true,
			   toolbar:toolbazybr
			});
	}else{
		$.ajax({
			url:'termReportUserZybList.action',
			data:model,
			success:function(data){
				var obj = $("#teamExamInfoShow").datagrid("getRows");
				var exam = eval('('+data+')');
				for(i=0;i<exam.rows.length;i++){
					var flag = true;
					for(j=0;j<obj.length;j++){
						if(exam.rows[i].exam_num == obj[j].exam_num){
							flag = false;
						}
					}
					if(flag){
						$("#teamExamInfoShow").datagrid("appendRow",exam.rows[i]);
					}
				}
				$("#zybexam_num").next().children("input:first-child").select();
			}
		});
	}
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
		f_getDatadic(id);	
		f_getdept(id);
		f_getdeptzyb(id);
	}


	//获取菜单
	var company_id;
	function f_getDatadic(company_id) {
		$('#batch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+company_id,
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				if((data!=undefined)&&(data.length>0)){
				$('#batch_id').combobox('setValue', data[0].id);	

				}
			},onChange:function(n,o){
				f_getGroup(n);
				f_getzytjlx(n);
		        f_getzywhys(n)
		     }
		});
	}
	
	var batchtogroup_id;
	function f_getGroup(batchtogroup_id){
		$('#group_id').combobox({
			url : 'termgrouplistshow.action?batchid='+batchtogroup_id,
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'group_name',
			onLoadSuccess : function(data) {
				if((data!=undefined)&&(data.length>0)){
					$('#group_id').combobox('setValue', data[0].id);
			
				}
			},
			onChange:function(n,o){
		         getSetFrogroupId(n);
		     }
		});
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

//显示部门
function f_getdept(company_id) {
	$('#levelss').combobox({
		url : 'getCompanForDept2.action?company_id='+company_id,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#levelss').combobox('setValue', data[0].id);	
			}
		}
	});
}


function f_getzytjlx(batchtogroup_id) {
	$('#zytjlx_id').combobox({
		url : 'termGetzocforcomBatch.action?company_id='+$("#company_id").val()+'&batchid='+batchtogroup_id,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#zytjlx_id').combobox('setValue', data[0].id);	
			}
		}
	});
}

function f_getzywhys(batchtogroup_id) {
	$('#zywhys_id').combobox({
		url : 'termGetzofforcomBatch.action?company_id='+$("#company_id").val()+'&batchid='+batchtogroup_id,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#zywhys_id').combobox('setValue', data[0].id);	
			}
		}
	});
}

//显示部门
function f_getdeptzyb(company_id) {
	$('#zyblevelss').combobox({
		url : 'getCompanForDept2.action?company_id='+company_id,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		multiple:true,
		textField : 'dep_Name',
		onLoadSuccess : function(data) {
			if((data!=undefined)&&(data.length>0)){
			$('#zyblevelss').combobox('setValue', data[0].id);	
			}
		}
	});
}

var groupset_id;
function getSetFrogroupId(groupset_id) {
	$('#set_id').combobox({
		url : 'termSetlistshow.action?group_id=' + groupset_id,
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : 'auto',// 自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				$('#set_id').combobox('setValue', data[0].id);
				break;
			}
		}
	});
}

//--------------------------------------职业危害查询--------------------------------------------

function getteamExamInfoShowGridzyb() {
	zyb_serch_status = false;
	serch_status = false;
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var chk_value ="";    
	 $('input[name = chkItem]:checked').each(function(){    
	  chk_value=chk_value+","+($(this).val());    
	 }); 
	 if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	 }
	 
	 var levels = $("#zyblevelss").combobox('getValues');
		 var levelss = new Array();
		 for(i=0;i<levels.length;i++){
			 if(levels[i] != ''){
				 levelss.push("'"+levels[i]+"'"); 
			 }
		 }
		 
	var tjlxs = $("#zybtjlx").combobox('getValues');
	var tjlxss = new Array();
	for(i=0;i<tjlxs.length;i++){
		if(tjlxs[i] != ''){
		   tjlxss.push("'"+tjlxs[i]+"'"); 
	   }
	 }
	
	var zytjlx_ids = $("#zytjlx_id").combobox('getValues');
	var zytjlx_idss = new Array();
	for(i=0;i<zytjlx_ids.length;i++){
		if(zytjlx_ids[i] != ''){
			zytjlx_idss.push("'"+zytjlx_ids[i]+"'"); 
	   }
	 }
	
	
	var zywhys_ids = $("#zywhys_id").combobox('getValues');
	var zywhys_idss = new Array();
	for(i=0;i<zywhys_ids.length;i++){
		if(zywhys_ids[i] != ''){
			zywhys_idss.push("'"+zywhys_ids[i]+"'"); 
	   }
	 }
		 
	var model = {
		"company_id":$("#company_id").val(),
		"batchid":document.getElementsByName("batch_id")[0].value,
		"sex":'',
		"exam_num":$("#zybexam_num").val(),
		"custname":$("#zybcustname").val(),
		"zytjlx":zytjlx_idss.toString(),
		"zywhys":zywhys_idss.toString(),
		"exam_status":$('#zybexam_status').combobox('getValues').join(','),
		"is_report_print":$('#zybis_report_print').combobox('getValue'),
		"levels":levelss.toString(),
		"tjlxs":tjlxss.toString(),
		"chkItem":chk_value
	};
	$("#teamExamInfoShow").datagrid({
		url:'termReportUserZybList.action',
		dataType : 'json',
		queryParams : model,
		remoteSort:false,
		toolbar:'#toolbar',
		rownumbers : true,
		pageSize : 200,// 每页显示的记录条数，默认为10
		pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
		columns : [[  {field:'ck',checkbox:true },
		              {align:'center',field:'examreport',title:'报告',"formatter":f_reportshowzyb},
		              {align : 'center',field : 'exam_num',title : '体检编号',width : 18,"formatter":f_showexam,sortable:true}, 
		              {align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable:true}, 
		              {align : 'center',field : 'user_name',title : '姓名',width : 15,sortable:true}, 
		              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
		              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
		              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true}, 	
		              {align : 'center',field : 'tjlx',title : '体检类型',width :15,sortable:true},		
		              {align : 'center',field : 'statussss',title : '体检状态',width :15,"formatter":f_showstatus,sortable:true},
		              {align : 'center',field : 'is_report_print_y',title : '打印状态',width :15,sortable:true}
//		              {align : 'center',field : 'zytjlxname',title : '职业体检',width : 20,sortable:true}, 
//		              {align : 'center',field : 'zywhysname',title : '职业危害',width : 47,sortable:true}
		              ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$(".loading_div").remove();
		},
		singleSelect:false,
	   collapsible:true,
		pagination: true,
	   fitColumns:true,
	   striped:true,
	   toolbar:toolbazybr
	});
	}

	/**
	 * 定义工具栏
	 */
	var toolbazybr=[{
			text:'批量打印职业病个人报告',
			width:200,
			iconCls:'icon-print',
			handler:function(){
				if($("#zyb_barcode_print_type").val() == '5'){
					reportprintlnallzyb();
				}else{
					$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
				}
		    }
		},{
			text:'<input name=\"isprintdah\" type=\"checkbox\" value=\"1\"/>同时打印普通报告',
			width:140,
			iconCls:'',
			handler:function(){
		    }
		}];
	function f_reportshowzyb(val,row){
		   return '<a href=\"javascript:printreportzyb(\''+row.exam_num+'\')\">预览</a>';
	}

	function printreportzyb(barids) {
		if ($("#zyb_barcode_print_type").val() == '5') {// 调用4.0打印程序中间表调用模式
			var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&opreport&"+barids+"&&&";
			RunServerExe(exeurl);
		} else {
			$.messager.alert("警告信息", "未设置打印程序调用类型配置-BARCODE_PRINT_TYPE", "error");
		}
	}	
	function reportprintlnallzyb(){
		var commbgtype=false;
		if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
			commbgtype=true;
		 }
		   var listh1= new Array();
		   var checkedItems = $('#teamExamInfoShow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		    	listh1.push({
					exam_num : item.exam_num,
					print_type : 'P',
					charging_item_codes : '',
					bar_calss : 1,
					arch_bar_num : 1
				});
		    	if(commbgtype){
			    	listh1.push({
						exam_num : item.exam_num,
						print_type : 'R',
						charging_item_codes : '',
						bar_calss : 1,
						arch_bar_num : 1
					});
		    	}
		    });
		  if(listh1.length>0){
			  $.messager.confirm('提示信息','是否确定打印所选记录报告？',function(r){
					if(r){
						$.ajax({
							url : 'saveExamPrintTmp.action',
							data : {
								examPrintTmpLists : JSON.stringify(listh1)
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {
									var printno = text.split("-")[1];
									var url = 'GuidBarServices://&P&1&' + printno;
									RunServerExe(url);
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
				})  
		  }
	}
 //---------------------------------------显示方案------------------------------------------------------
/**
 * 
 */
function getteamExamInfoShowGrid() {
	zyb_serch_status = false;
	serch_status = false;
var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);
var chk_value ="";    
 $('input[name = chkItem]:checked').each(function(){    
  chk_value=chk_value+","+($(this).val());    
 }); 
 if(chk_value.length>1){
	  chk_value=chk_value.substring(1,chk_value.length);
 }
 
 var levels = $("#levelss").combobox('getValues');
	 var levelss = new Array();
	 for(i=0;i<levels.length;i++){
		 if(levels[i] != ''){
			 levelss.push("'"+levels[i]+"'"); 
		 }
	 }
	 
var tjlxs = $("#tjlx").combobox('getValues');
var tjlxss = new Array();
for(i=0;i<tjlxs.length;i++){
	if(tjlxs[i] != ''){
	   tjlxss.push("'"+tjlxs[i]+"'"); 
   }
 }
	 
var model = {
	"company_id":$("#company_id").val(),
	"batchid":document.getElementsByName("batch_id")[0].value,
	"sex":'',
	"group_id":document.getElementsByName("group_id")[0].value,
	"set_id":document.getElementsByName("set_id")[0].value,
	"exam_num":$("#exam_num").val(),
	"custname":$("#custname").val(),
	"customer_type_id":$('#rylb').combobox('getValues').join(','),
	"exam_status":$('#exam_status').combobox('getValues').join(','),
	"levels":levelss.toString(),
	"tjlxs":tjlxss.toString(),
	"chkItem":chk_value
};
$("#teamExamInfoShow").datagrid({
	url:'termReportUserList.action',
	dataType : 'json',
	queryParams : model,
	remoteSort:false,
	toolbar:'#toolbar',
	rownumbers : true,
	pageSize : 200,// 每页显示的记录条数，默认为10
	pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
	columns : [[  {field:'ck',checkbox:true },
	              {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
	              {align : 'center',field : 'exam_num',title : '体检编号',width : 18,"formatter":f_showexam,sortable:true}, 
	              {align : 'center',field : 'id_num',title : '身份证号',width : 25,sortable:true}, 
	              {align : 'center',field : 'user_name',title : '姓名',width : 15,sortable:true}, 
	              {align : 'center',field : 'sex',title : '性别',width : 10,sortable:true}, 
	              {align : 'center',field : 'age',title : '年龄',width : 10,sortable:true}, 
	              {align : 'center',field : 'phone',title : '电话',width : 15,sortable:true}, 	
	              {align : 'center',field : 'tjlx',title : '体检类型',width :15,sortable:true},		
	              {align : 'center',field : 'statussss',title : '体检状态',width :15,"formatter":f_showstatus,sortable:true},		                            
	              {align : 'center',field : 'group_name',title : '分组',width : 20,sortable:true}, 
	              {align : 'center',field : 'set_name',title : '套餐',width : 47,sortable:true}
	              ]],
	onLoadSuccess : function(value) {
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

/**
 * 定义工具栏
 */
var toolbar=[{
		text:'批量打印报告',
		width:120,
		iconCls:'icon-print',
		handler:function(){
			if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
				$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			reportprintlnall();
    	    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
    	    	reportprintlnall();
    	    }else{
    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    	    }
	    }
	}];

//批量上传


function f_showexam(val,row){
 return '<a href=\"javascript:f_showchargingitemshow('+row.id+')\">'+row.exam_num+'</a>';
} 

var termexamid;
function f_showchargingitemshow(termexamid){
var url='termexamchargingitem.action?exam_id='+termexamid;
  newWindow = window.open(url, "体检人员缴费项目明细", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
  newWindow.focus();
}

function f_showstatus(val,row){
 if(row.status=='Y'){
	  return '<font color="red">'+row.statuss+'</font>';
 }else if(row.status=='D'){
	  return '<font color="blue">'+row.statuss+'</font>';
 }else if(row.status=='J'){
	  return '<font color="green">'+row.statuss+'</font>';
 }else{
	  return row.statuss;
 }
}

function f_reportshow(val,row){
	   return '<a href=\"javascript:printreport(\''+row.exam_num+'\')\">预览</a>';
}

function printreport(barids) {
	if ($("#barcode_print_type").val() == '1') {// 调用旧打印程序
		var exeurl = "reportServices://&0&" + barids + "&0";
		RunServerExe(exeurl);
	} else if ($("#barcode_print_type").val() == '2') {// 调用旧打印程序
		var exeurl = "reportServices://&0&" + barids + "&0";
		RunServerExe(exeurl);
	} else if ($("#barcode_print_type").val() == '3') {// 调用4.0打印程序直接调用模式
		$.messager.alert("警告信息", "未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	} else if (($("#barcode_print_type").val() == '4')||($("#barcode_print_type").val() == '5')) {// 调用4.0打印程序中间表调用模式
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+barids+"&&&";
		RunServerExe(exeurl);
	} else {
		$.messager.alert("警告信息", "未设置打印程序调用类型配置-BARCODE_PRINT_TYPE", "error");
	}
}

function reportprintlnall(){
	   var listh1= new Array();
	   var checkedItems = $('#teamExamInfoShow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	    	listh1.push({
				exam_num : item.exam_num,
				print_type : 'R',
				charging_item_codes : '',
				bar_calss : 1,
				arch_bar_num : 1
			});
	    });
	  if(listh1.length>0){
		  $.messager.confirm('提示信息','是否确定打印所选记录报告？',function(r){
				if(r){
					$.ajax({
						url : 'saveExamPrintTmp.action',
						data : {
							examPrintTmpLists : JSON.stringify(listh1)
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								var printno = text.split("-")[1];
								var url = 'GuidBarServices://&P&1&' + printno;
								RunServerExe(url);
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
			})  
	  }
}

/**
 * 团报打印
 */
function reportprint() {
	var comid=$("#company_id").val();
	var batchssid=document.getElementsByName("batch_id")[0].value;
	if((comid!='')||(batchssid!='')){
	$.messager.confirm('提示信息', '是否确定打印普通团体报告？', function(r) {
		if (r) {
			var userid = $("#userid").val();			
			var url = 'termreportServices://&P&'+userid+'&pereport&'+comid+'&' + batchssid;
			RunServerExe(url);
		}
	})
	}else{
		$.messager.alert("操作提示", "无限单位和批次", "error");
	}
}


/**
 * 职业病团报打印
 */
var zybtbtype;
function zybreportprint(zybtbtype) {
	var comid=$("#company_id").val();
	var batchssid=document.getElementsByName("batch_id")[0].value;
	if((comid!='')||(batchssid!='')){
	$.messager.confirm('提示信息', '是否确定打印职业病团体报告？', function(r) {
		if (r) {
			var userid = $("#userid").val();			
			var url = 'termreportServices://&P&'+userid+'&opereport&'+comid+'&' + batchssid;
			RunServerExe(url);
		}
	})
	}else{
		$.messager.alert("操作提示", "无效单位和批次", "error");
	}
}

/**
 * 团报预览
 */
function reportyulan() {
	var comid=$("#company_id").val();
	var batchssid=document.getElementsByName("batch_id")[0].value;
	if((comid!='')||(batchssid!='')){
	$.messager.confirm('提示信息', '是否预览普通团体报告？', function(r) {
		if (r) {
			var userid = $("#userid").val();			
			var url = 'termreportServices://&Y&'+userid+'&pereport&'+comid+'&' + batchssid;
			RunServerExe(url);
		}
	})
	}else{
		$.messager.alert("操作提示", "无效单位和批次", "error");
	}
}

var strPath;
function RunServerExe(strPath) {
		location.href=strPath;
	}

//清空表格数据
function queryEmpty(){
	$('#teamExamInfoShow').datagrid('loadData', { total: 0, rows: [] }); 
}