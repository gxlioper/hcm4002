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
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
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
	 var set_name = undefined;
	 if($("#ck_set_name")[0].checked){
		 set_name = $('#set_name').combobox('getValue');
	 }
	 var comid = 0;
	 if($("#com_Name").textbox('getValue') != '' && $("#ck_company_id")[0].checked){
		 comid = $("#company_id").val();
	 }
	 var item = undefined;
	 if($("#ck_item")[0].checked){
		 item = $('#item').combobox('getValues').toString();
	 }
	 var exam_status = undefined;
	 if($("#ck_exam_status")[0].checked){
		 exam_status = $('#exam_status').combobox('getValue');
	 }
	 var pay_status = undefined;
	 if($("#ck_pay_status")[0].checked){
		 pay_status = $('#pay_status').combobox('getValue');
	 }
	 var dep = undefined;
	 if($("#ck_dep")[0].checked){
		 dep = $('#dep').combobox('getValue');
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "charging_item_ids":item,	
				 "exam_status":exam_status,
				 "pay_status":pay_status,
				 "dep_id":dep,
				 "set_id":set_name,
				 "status":status,
				 "dep_category":date_type
		 };
	     $("#groupusershow").datagrid({
		 url:'getDirectorExamItemList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 30,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'join_date',title:'体检日期',width:25},
		    {align:'center',field:'exam_num',title:tjhname,width:15},
		 	{align:'center',field:'user_name',title:'姓名',width:10},
		 	{align:'center',field:'sex',title:'性别',width:10},
		 	{align:'center',field:'age',title:'年龄',width:10},
		 	{align:'center',field:'phone',title:'电话',width:15},		 	
		 	{align:'center',field:'company',title:'单位',width:10},
		 	{align:'center',field:'set_name',title:'套餐',width:10},
		 	{align:'center',field:'statuss',title:'体检状态',width:10},
		 	{align:'center',field:'item_name',title:'项目名称',width:25},
		 	{align:'center',field:'pay_statuss',title:'收费状态',width:15},
		 	{align:'center',field:'exam_statuss',title:'检查状态',width:15},
		 	{align:'center',field:'exam_date',title:'检查时间',width:15},
		 	{align:'center',field:'exam_doctor_name',title:'检查医生',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		MergeCells("groupusershow","id,exam_num,join_date,user_name,sex,age,phone,set_name,company,statuss",0);
	    	},
	    	onDblClickRow : function(index, row) {	
	    		
			},
			height: window.screen.height-380,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
	}

 /**
 2         * EasyUI DataGrid根据字段动态合并单元格
 3         * @param fldList 要合并table的id
 4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
 5         */
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