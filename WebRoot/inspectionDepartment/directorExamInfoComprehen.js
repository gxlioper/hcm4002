var result_testno='';
$(document).ready(function () {
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {	    	
	    	$("#customer_id").val('1');
	    	getgroupuserGrid();
	     }
	}
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
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		if($('#com_Name').textbox('getValue')==""){
			$('.chk_com_Name').attr('checked',false)
		} else {
			$('.chk_com_Name').attr('checked',true)
		}
		select_com_list_over();		
    });
	
	$('#set_name').combobox({
		url : 'getDirectorExamSetList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			
		}
	});
	
	$('#dep').combobox({
		url : 'getDirectorDepList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'dep_name',
		onLoadSuccess : function(data) {
			
		},
		onSelect: function(rec){    
            var url = 'getDirectorItemList.action?dep_id='+rec.id;
            $('#item').combobox('clear');
            $('#item').combobox('reload', url);    
        }
	});
	$('#item').combobox({
		url : 'getDirectorItemList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'item_name',
		multiple : true,
		onLoadSuccess : function(data) {
			
		}
	});	
	$('#statusss').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS4',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple : true,
		onLoadSuccess : function(data) {
			
		}
	});	
	$("#customer_id").val('1');
	getgroupuserGrid();
	setchebox();
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
	 var date_type = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
		 date_type = $('#date_type').combobox('getValue');
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
		 var obj = $('#statusss').combobox('getValues');
		 status = obj.toString();
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
//	 var arch_num = undefined;
//	 if($("#ck_arch_num")[0].checked){
//		 arch_num = $("#arch_num").val();
//	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val();
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var set_name = undefined;
	 if($("#ck_set_name")[0].checked){
		 set_name = $('#set_name').combobox('getValue');
	 }
	 var item = undefined;
	 if($("#ck_item")[0].checked){
		 item = $('#item').combobox('getValues').toString();
	 }
	 var exam_status = undefined;
	 if($("#ck_exam_status")[0].checked){
		 exam_status = $('#exam_status').combobox('getValues').toString();
	 }
	 var pay_status = undefined;
	 if($("#ck_pay_status")[0].checked){
		 pay_status = $('#pay_status').combobox('getValues').toString();
	 }
	 var dep = undefined;
	 if($("#ck_dep")[0].checked){
		 dep = $('#dep').combobox('getValue');
	 }
	 var is_guide_back = undefined;
	 if($("#ck_is_guide_back")[0].checked){
		 is_guide_back = $('#is_guide_back').combobox('getValue');
	 }
	 var sex = undefined;
	 if($("#ck_sex")[0].checked){
		 sex = $('#sex').combobox('getValue');
	 }
	 var is_vip = undefined;
	 if($("#ck_is_vip")[0].checked){
		 is_vip = $('#is_vip').combobox('getValue');
	 }
	 var wxzj = undefined;
	 if($("#ck_wxzj")[0].checked){
		 wxzj = $('#wxzj').combobox('getValue');
	 }else{
		 wxzj=-1; 
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
//				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "dep_category":date_type,
				 "charging_item_ids":item,	
				 "exam_status":exam_status,
				 "pay_status":pay_status,
				 "dep_id":dep,
				 "set_id":set_name,
				 "arch_com_ids":$("#arch_com_ids").val(),
				 "is_guide_back":is_guide_back,
				 "sex":sex,
				 "wxzj":wxzj,
				 "isVip":is_vip,
				 "customer_id":$("#customer_id").val()		 
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamComprehenList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
		    {align:'right',field:'exam_num',halign: 'center',title:tjhname,width:128,sortable:true},
		    {align:'center',field:'arch_num',halign: 'center',title:dahname,width:125,sortable:true},
		 	{align:'right',field:'id_num',halign: 'center',title:'身份证号',width:165},
		 	{align:'center',field:'user_name',halign: 'center',title:'姓名',width:115,sortable:true},
		 	{align:'center',field:'sex',halign: 'center',title:'性别',width:110,sortable:true},
		 	{align:'center',field:'age',halign: 'center',title:'年龄',width:110,sortable:true},
		 	{align:'center',field:'phone',halign: 'center',title:'电话',width:150},
		 	{align:'center',field:'company',halign: 'center',title:'单位',width:180},
		 	{align:'center',field:'customer_type',title:'体检类别',width:120},
		 	{align:'center',field:'join_date',title:'体检日期',width:150},
		 	{align:'center',field:'statuss',title:'体检状态',width:115},
		 	{align:'center',field:'statuss1111111111',title:'已检查/总数',width:120,formatter:f_item_count},
//		 	{align:'center',field:'swuxuzongjian',title:'需总检',width:10},
//		 	{align:'center',field:'st',title:'特殊通知',width:10},
		 	{align:'center',field:'is_guide_backs',title:'回收导检单',width:115},
		 	{align:'center',field:'DJD_path',title:'导检单图片',width:115,formatter:f_image},
		 	{align:'center',field:'DJD_image_creater',title:'导检单图片上传者',width:135},
//		 	{align:'center',field:'print_count',title:'打印人数',width:15},
		 	{align:'center',field:'flow_name',title:'流程进度',width:150},
		 	{align:'center',field:'senddate',title:'流程时间',width:150},
			{align:'center',field:'final_doctor',title:'总检医生',width:115},
			{align:'center',field:'final_date',title:'总检时间',width:150},
			{align:'center',field:'check_name',title:'审核医生',width:115},
			{align:'center',field:'check_time',title:'审核时间',width:150},
			{align:'center',field:'fs_creater',title:'复审医生',width:115},
			{align:'center',field:'fs_date',title:'复审时间',width:150},
			{align:'center',field:'report_Print_UserName',title:'打印人',width:115},
			{align:'center',field:'report_Print_Date',title:'打印时间',width:150}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:false,
		    fit:true,
		    striped:true,
		    rownumbers:true,
		    onDblClickRow:function(rowIndex, row){
		    	f_lcck(row.id,row.exam_num);
		    },
		    toolbar:toolbar
	});
}
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'查看体检信息',
		width:130,
		iconCls:'icon-print',
	    handler:function(){
	    	var row = $('#groupusershow').datagrid('getSelected');
    	    if(row == null){
    	    	$.messager.alert("警告信息","请选择一个体检人员!","error");
    	    	return;
    	    }
    	    f_lcck(row.id,row.exam_num);
	    }
	}/*,{
		text:'报告预警',
		width:120,
		iconCls:'icon-cards',
	    handler:function(){
	    	smccf="名称重复"
	    	getgroupuserGrid();
	    }
	}*//*,{
		text:'PACS检查结果',
		width:130,
		iconCls:'icon-print',
	    handler:function(){
	    	var row = $('#groupusershow').datagrid('getSelected');
    	    if(row == null){
    	    	$.messager.alert("警告信息","请选择一个体检人员!","error");
    	    	return;
    	    }
    	    f_lchisck(row.exam_num,row.join_date);
	    }
	},{
		text:'LIS检查结果',
		width:130,
		iconCls:'icon-print',
	    handler:function(){
	    	var row = $('#groupusershow').datagrid('getSelected');
    	    if(row == null){
    	    	$.messager.alert("警告信息","请选择一个体检人员!","error");
    	    	return;
    	    }
    	    f_lcLisck(row.exam_num,row.join_date);
	    }
	},{
		text:'LIS检查结果2',
		width:130,
		iconCls:'icon-print',
	    handler:function(){
	    	var row = $('#groupusershow').datagrid('getSelected');
    	    if(row == null){
    	    	$.messager.alert("警告信息","请选择一个体检人员!","error");
    	    	return;
    	    }
    	    f_lcLisckTwo(row.arch_num,row.join_date);
	    }
	},{
		text:'手工获取检查检验结果',
		width:180,
		iconCls:'icon-print',
	    handler:function(){
	    	var row = $('#groupusershow').datagrid('getSelected');
    	    if(row == null){
    	    	$.messager.alert("警告信息","请选择一个体检人员!","error");
    	    	return;
    	    }
    	    getlispacs(row.exam_num);
	    }
	}*/];
 
function f_item_count(val, row) {
	return row.yijian + '/' + (row.yijian + row.weijian);
}
function f_image(val, row){
	if(row.DJD_path == null || row.DJD_path == ""){
		return "";
	}else{
		return '<a href="javascript:void(0)" onclick = "show_image('+"'"+row.DJD_path+"'"+')">查看图片</a>';
	}
}
function show_image(DJD_path){
	var url='/showDJDImage.action?DJD_path='+DJD_path;
	   newwin = window.open("", "图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height - 30);
	   newwin.location = url;
	   newwin.focus();
}
 
var lisexam_num;
	function getlispacs(lisexam_num) {
		if ($("#exam_num_s").val() != '') {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
					+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$
					.ajax({
						url : 'getlispacsres.action?exam_num='+lisexam_num,
						type : 'post',
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", text.split("-")[1], "");
							} else {
								$.messager.alert("错误信息", text, "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("警告信息", "操作失败", "error");
						}
					})
		} else {
			alert_autoClose("操作提示", "无效体检编号", "error");
		}
	}
	
function button_select_list(id){
	$("#customer_id").val(id);
	$("#arch_com_ids").val("notstz");	
	getgroupuserGrid();
}
	
function button_select_listts(id){
		$("#customer_id").val(id);
		$("#arch_com_ids").val("tstz");
		getgroupuserGrid();
	}

function fn_xm(val,row){
	return '<a href=\"javascript:f_xmck('+row.exam_num+');\">查看项目</a>';
}
function fn_jg(val,row){
	return '<a href=\"javascript:f_jgck('+row.exam_num+');\">查看结果</a>';
}
function fn_lc(val,row){
//	return '<a href=\"javascript:void(0);\">查看流程</a>';
	return '<a href=\"javascript:f_lcck(\''+row.id+'\',\''+row.exam_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"详细信息\" /></a>';
}
function fn_lchis(val,row){
	return '<a href=\"javascript:f_lchisck(\''+row.exam_num+'\',\''+row.join_date+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-info\" alt=\"查看HIS检查结果\" /></a>';
}

function f_lchisck(exam_num,join_date){
	$('#dlg-hisresult').dialog({    
	    title: '查看PACS检查结果',    
	    width: 1200,    
	    height: 500,    
	    closed: true,
	    cache: false,
	    modal: true,    
	    href: 'getHisExamResultPage.action?exam_num='+exam_num+'&s_join_date='+join_date,    
	});    
	$('#dlg-hisresult').dialog('open');
	$('#dlg-hisresult').dialog('center');
}
function f_lcLisck(exam_num,join_date){
	$('#dlg-hisresult').dialog({    
	    title: '查看LIS检查结果1',    
	    width: 1200,    
	    height: 500,    
	    closed: true,
	    cache: false,
	    modal: true,    
	    href: 'getPatLabSamplePage.action?exam_num='+exam_num+'&s_join_date='+join_date,       
	});    
	$('#dlg-hisresult').dialog('open');
	$('#dlg-hisresult').dialog('center');
}

function f_lcLisckTwo(arch_num,join_date){
	$('#dlg-hisresult').dialog({    
	    title: '查看LIS检查结果2',    
	    width: 1200,    
	    height: 500,    
	    closed: true,
	    cache: false,
	    modal: true,    
	    href: 'getPatLabSamplePageTwo.action?arch_num='+arch_num+'&s_join_date='+join_date,
	});    
	$('#dlg-hisresult').dialog('open');
	$('#dlg-hisresult').dialog('center');
}

function f_lcck(id,exam_num){
	var model = {'exam_num':exam_num};
	$("#exam_item_xx").datagrid({
		url : 'getDirectorItemStatus.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		columns : [ [ {align : 'center',field : 'dep_name',title : '科室',width : 15},
		              {align : 'center',field : 'item_name',title : '收费项目',width : 20},
		              {align : 'center',field : 'item_amount',title : '项目金额(元)',width : 15},
		              {align : 'center',field : 'exam_indicators',title : '付费类型',width : 15},
		              {align : 'center',field : 'pay_statuss',title : '付费状态',width : 15},
		              {align : 'center',field : 'exam_statuss',title : '检查状态',width : 10},
		              {align : 'center',field : 'exam_doctor_name',title : '检查医生',width : 10},
		              {align : 'center',field : 'exam_date',title : '检查时间',width : 25}
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			MergeCells('exam_item_xx', 'dep_name,dep_name',1);
		},
		singleSelect : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		striped : true,
		nowrap : false,
		rowStyler:function(index,row){
			if(row.exam_status == 'N'){
				return 'color:red;';
			}
		}
	});
	$("#item_result_xx").datagrid({
		url: 'getAcceptanceItemResult.action',
		queryParams: {'exam_num':exam_num},
		rownumbers:false,
		columns:[[
//		          {align:"center",field:"dep","title":"科室","width":10},
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":45,"styler":f_clolor,"formatter":f_flowor},
//		          {align:'center',field:"exam_status_y","title":"检查状态","width":10},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":10}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('item_result_xx', 'dep,dep_name,exam_doctor,exam_date,dep,exam_status_y',1);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false
	});
	$("#flowloglist").datagrid({
		url: 'flowexamloglist.action',
		queryParams: {"exam_num":exam_num},
		remoteSort:false,
		 	    columns:[[
				          {align:"center",field:"senddate","title":"时间","width":20,sortable:'true'},
				          {align:'center',field:"flow_name","title":"事件","width":20},
				          {align:'center',field:"senduname","title":"发送者","width":10,sortable:'true'},
				          {align:'center',field:"accuname","title":"接收者","width":10},
				          {align:"center",field:"flow_types","title":"状态","width":10}
				          ]],	    	
		onLoadSuccess:function(value){ 
			$("#datatotal").val(value.total);
		},
		singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		border:false,
		nowrap:false
	});
	$("#printflowlist").datagrid({
		url: 'printflowlist.action',
		queryParams: model,
		remoteSort:false,
		columns:[[
			{align:'center',field:"print_doctor","title":"打印者","width":10,sortable:'true'},
			{align:"center",field:"print_count","title":"次数","width":10}
			]],	    	
			onLoadSuccess:function(value){ 
				$("#datatotal").val(value.total);
			},
			singleSelect:true,
			collapsible:true,
			pagination: false,
			fitColumns:true,
			rownumbers:true,
			fit:true,
			border:false,
			nowrap:false
	});
	getFinalSummary(exam_num);
	getExamDisease(exam_num);
	$("#exam_num1").val(exam_num);
	$("#dlg-xxxx").dialog("open");
	$("#dlg-xxxx").dialog("center");
}

function f_xmck(id){
	var model = {'exam_num':id};
	$("#exam_item_list").datagrid({
		url : 'getDirectorItemStatus.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		columns : [ [ {align : 'center',field : 'dep_name',title : '科室',width : 15},
		              {align : 'center',field : 'item_name',title : '收费项目',width : 20},
		              {align : 'center',field : 'item_amount',title : '项目金额(元)',width : 15},
		              {align : 'center',field : 'exam_indicators',title : '付费类型',width : 15},
		              {align : 'center',field : 'pay_statuss',title : '付费状态',width : 15},
		              {align : 'center',field : 'exam_statuss',title : '检查状态',width : 10},
		              {align : 'center',field : 'exam_doctor_name',title : '检查医生',width : 10},
		              {align : 'center',field : 'exam_date',title : '检查时间',width : 25}
		              ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			MergeCells('exam_item_list', 'dep_name,dep_name',1);
		},
		singleSelect : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		striped : true,
		nowrap : false
	});
	$("#dlg-item").dialog("open");
	$("#dlg-item").dialog("center");
}
function f_jgck(id){
	var model={"exam_num":id};
	 $("#item_result").datagrid({
			url: 'getDirectorDiseaseList.action',
			queryParams: model,
			rownumbers:false,
			columns:[[
				{align:"center",field:"dep","title":"科室","width":10},
			          {align:"center",field:"dep_name","title":"收费项目","width":10},
			          {align:'center',field:"exam_status_y","title":"检查状态","width":15},
			          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
			          {align:'center',field:"exam_date","title":"检查时间","width":15},
			          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
			          {align:"",field:"exam_result","title":"检查结果","width":25,"styler":f_clolor,"formatter":f_flowor}
			          ]],
		    onLoadSuccess:function(value){
		    	$("#datatotal").val(value.total);
		    	MergeCells('item_result', 'dep,dep_name,exam_doctor,exam_date,dep,exam_status_y',1);
		    },
		    singleSelect:true,
		    collapsible:true,
			pagination: false,
			fitColumns:true,
			fit:true,
			border:false,
			nowrap:false
	});
	$("#dlg-result").dialog("open");
	$("#dlg-result").dialog("center");
}
function f_clolor(value,row,index){
	if(row.dep_category == '17'){
		if(row.health_level == 'Y'){
			return 'color:#F00;';
		}else if(row.health_level == 'W'){
			return 'color:#FF9B00;';
		}
		if(row.item_id == '0'){
			return 'background:#FEEAA8;color:#ff5100;';
		}
	}else if(row.dep_category == '131'){
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
			return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
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
function show_picture(id){
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num1").val();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}
//总检结论
 function getFinalSummary(eid){
 	$.ajax({
 		url:'getFinalSummaryResult.action',
 		data:{"exam_num":eid},
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
		     pageSize: 15,//每页显示的记录条数，默认为10 
		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}
 function MergeCells(tableID, fldList,zhuIndex) {
		var Arr = fldList.split(",");
		var dg = $('#' + tableID);
		var fldName;
		var RowCount = dg.datagrid("getRows").length;
		var span;
		var PerValue = "";
		var CurValue = "";
		var length = Arr.length - 1;
		for (i = length; i >= 1; i--) {
			z_fldName = Arr[zhuIndex];
			fldName = Arr[i];
			PerValue = "";
			span = 1;
			for (row = 0; row <= RowCount; row++) {
				if (row == RowCount) {
					CurValue = "";
				}else {
					CurValue = dg.datagrid("getRows")[row][z_fldName];
				}
				if (PerValue == CurValue) {
					span += 1;
				}else {
					var index = row - span;
					dg.datagrid('mergeCells', {
						index: index,
						field: fldName,
						rowspan: span,
						colspan: null
					});
					span = 1;
					PerValue = CurValue;
				}
			}
		}
	} 
 
 /**
 ****搜索添加输入框根据是否为空自动判断chebox选中
 */
 function setchebox(inp){
 	$('#custname').textbox({  
 	    onChange: function(value) {
 	    	if(value==""){
 	    		$('.chk_custname').attr('checked',false)
 	    	} else {
 	    		$('.chk_custname').attr('checked',true)
 	    	}
 	    }
 	});
 	$('#exam_num').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.chk_exam_num').attr('checked',false)
 			} else {
 				$('.chk_exam_num').attr('checked',true)
 			}
 		}
 	});
 	$('#id_num').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.chk_id_num').attr('checked',false)
 			} else {
 				$('.chk_id_num').attr('checked',true)
 			}
 		}
 	});
 	$('#searchphone').textbox({  
 		onChange: function(value) {
 			if(value==""){
 				$('.chk_searchphone').attr('checked',false)
 			} else {
 				$('.chk_searchphone').attr('checked',true)
 			}
 		}
 	});
 }
 
 
 function f_reportshow(val,row){
	   return '<a href=\"javascript:printreport(\''+row.exam_num+'\')\">预览</a>';
 }
 
 var varids;
 function printreport(barids) {
	if ($("#barcode_print_type").val() == '1') {// 调用旧打印程序
		var exeurl = "reportServices://&0&" + barids + "&0";
		RunServerExe(exeurl);
	} else if ($("#barcode_print_type").val() == '2') {// 调用旧打印程序
		var exeurl = "reportServices://&0&" + barids + "&0";
		RunServerExe(exeurl);
	} else if ($("#barcode_print_type").val() == '3') {// 调用4.0打印程序直接调用模式
		$.messager.alert("警告信息", "未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE",
				"error");
	} else if ($("#barcode_print_type").val() == '4') {// 调用4.0打印程序中间表调用模式
		var exeurl="GuidBarServices://&Y&0&"+$("#userid").val()+"&report&"+barids+"&&&";
		RunServerExe(exeurl);
		//new_reprintdjd4(barids);
	} else {
		$.messager.alert("警告信息", "未设置打印程序调用类型配置-BARCODE_PRINT_TYPE", "error");
	}
}
 
//新补打导检单4.0
 var reprintexamnum;
 function new_reprintdjd4(reprintexamnum) {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var data = new Array;
	var bar_calss = 1;
		data.push({
			exam_num : reprintexamnum,
			print_type : 'R',
			charging_item_codes : '',
			bar_calss : 1,
			arch_bar_num : 1
		});
	$.ajax({
		url : 'saveExamPrintTmp.action',
		data : {
			examPrintTmpLists : JSON.stringify(data)
		},
		type : "post",//数据发送方式   
		success : function(text) {
			$(".loading_div").remove();
			if (text.split("-")[0] == 'ok') {
				var printno = text.split("-")[1];
				var url = 'GuidBarServices://&Y&1&' + printno;
				RunServerExe(url);
			} else {
				$.messager.alert("错误信息",text,"error");
			}
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");
		}
	});
}
 
 var strPath;
 function RunServerExe(strPath) {
		location.href=strPath;
	}
 function getgroupuserGrid(){
	 var exam_num = undefined;
	 if($("#ck_exam_num")[0].checked){
		 exam_num =  $("#exam_num").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 var date_type = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
		 date_type = $('#date_type').combobox('getValue');
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
		 var obj = $('#statusss').combobox('getValues');
		 status = obj.toString();
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
//	 var arch_num = undefined;
//	 if($("#ck_arch_num")[0].checked){
//		 arch_num = $("#arch_num").val();
//	 }
	 var id_num = undefined;
	 if($("#ck_id_num")[0].checked){
		 id_num = $("#id_num").val();
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var set_name = undefined;
	 if($("#ck_set_name")[0].checked){
		 set_name = $('#set_name').combobox('getValue');
	 }
	 var item = undefined;
	 if($("#ck_item")[0].checked){
		 item = $('#item').combobox('getValues').toString();
	 }
	 var exam_status = undefined;
	 if($("#ck_exam_status")[0].checked){
		 exam_status = $('#exam_status').combobox('getValues').toString();
	 }
	 var pay_status = undefined;
	 if($("#ck_pay_status")[0].checked){
		 pay_status = $('#pay_status').combobox('getValues').toString();
	 }
	 var dep = undefined;
	 if($("#ck_dep")[0].checked){
		 dep = $('#dep').combobox('getValue');
	 }
	 var is_guide_back = undefined;
	 if($("#ck_is_guide_back")[0].checked){
		 is_guide_back = $('#is_guide_back').combobox('getValue');
	 }
	 var sex = undefined;
	 if($("#ck_sex")[0].checked){
		 sex = $('#sex').combobox('getValue');
	 }
	 var is_vip = undefined;
	 if($("#ck_is_vip")[0].checked){
		 is_vip = $('#is_vip').combobox('getValue');
	 }
	 var wxzj = undefined;
	 if($("#ck_wxzj")[0].checked){
		 wxzj = $('#wxzj').combobox('getValue');
	 }else{
		 wxzj=-1; 
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
//				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "dep_category":date_type,
				 "charging_item_ids":item,	
				 "exam_status":exam_status,
				 "pay_status":pay_status,
				 "dep_id":dep,
				 "set_id":set_name,
				 "arch_com_ids":$("#arch_com_ids").val(),
				 "is_guide_back":is_guide_back,
				 "sex":sex,
				 "wxzj":wxzj,
				 "isVip":is_vip,
				 "customer_id":$("#customer_id").val()		 
		 };
	     $("#groupusershow").datagrid({
		 url:'getReportWarningList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {align:'center',field:'examreport',title:'报告',"formatter":f_reportshow},
		    {align:'right',field:'exam_num',halign: 'center',title:tjhname,width:128,sortable:true},
		    {align:'center',field:'arch_num',halign: 'center',title:dahname,width:125,sortable:true},
		 	{align:'right',field:'id_num',halign: 'center',title:'身份证号',width:165},
		 	{align:'center',field:'user_name',halign: 'center',title:'姓名',width:115,sortable:true},
		 	{align:'center',field:'sex',halign: 'center',title:'性别',width:110,sortable:true},
		 	{align:'center',field:'age',halign: 'center',title:'年龄',width:110,sortable:true},
		 	{align:'center',field:'phone',halign: 'center',title:'电话',width:150},
		 	{align:'center',field:'company',halign: 'center',title:'单位',width:180},
		 	{align:'center',field:'customer_type',title:'体检类别',width:120},
		 	{align:'center',field:'join_date',title:'体检日期',width:150},
		 	{align:'center',field:'statuss',title:'体检状态',width:115},
		 	{align:'center',field:'statuss1111111111',title:'已检查/总数',width:120,formatter:f_item_count},
//		 	{align:'center',field:'swuxuzongjian',title:'需总检',width:10},
//		 	{align:'center',field:'st',title:'特殊通知',width:10},
		 	{align:'center',field:'is_guide_backs',title:'回收导检单',width:115},
		 	{align:'center',field:'DJD_path',title:'导检单图片',width:115,formatter:f_image},
		 	{align:'center',field:'DJD_image_creater',title:'导检单图片上传者',width:135},
//		 	{align:'center',field:'print_count',title:'打印人数',width:15},
		 	{align:'center',field:'flow_name',title:'流程进度',width:150},
		 	{align:'center',field:'senddate',title:'流程时间',width:150},
			{align:'center',field:'final_doctor',title:'总检医生',width:115},
			{align:'center',field:'final_date',title:'总检时间',width:150},
			{align:'center',field:'check_name',title:'审核医生',width:115},
			{align:'center',field:'check_time',title:'审核时间',width:150},
			{align:'center',field:'fs_creater',title:'复审医生',width:115},
			{align:'center',field:'fs_date',title:'复审时间',width:150},
			{align:'center',field:'report_Print_UserName',title:'打印人',width:115},
			{align:'center',field:'report_Print_Date',title:'打印时间',width:150}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:false,
		    fit:true,
		    striped:true,
		    rownumbers:true,
		    onDblClickRow:function(rowIndex, row){
		    	f_lcck(row.id,row.exam_num);
		    },
		    toolbar:toolbar
	});
}