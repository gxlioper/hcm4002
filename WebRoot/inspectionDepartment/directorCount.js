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
	$('#zongjianyisheng').combobox({
		url : 'getZongjianyisheng.action',
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
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var user_name = undefined;
	 if($("#ck_custname")[0].checked){
		 user_name = $("#custname").val();
	 }
	 var zongjianyisheng = undefined;
	 if($("#ck_zongjianyisheng")[0].checked){
		 zongjianyisheng = $("#zongjianyisheng").combobox('getValue');
	 }
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var examtype = undefined;
	 if($("#ck_examtype")[0].checked){
		 examtype = $('#examtype').combobox('getValue');
	 }
	 var zongjiandate = undefined;
	 if($("#ck_zongjiandate")[0].checked){
		 zongjiandate = $("#zongjiandate").datebox('getValue');
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
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "zongjianyisheng":zongjianyisheng,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "zongjiandate":zongjiandate,
				 "exam_type":examtype
		 };
	     $("#groupusershow").datagrid({
		 url:'getDirectorCountExamInfoList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'exam_num',title:tjhname,width:25,sortable:true},
		  //{align:'center',field:'arch_num',title:'档案号',width:18},
		 //	{align:'center',field:'id_num',title:'身份证号',width:25},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	//{align:'center',field:'phone',title:'电话',width:20},		 	
		 	//{align:'center',field:'company',title:'单位',width:20},
		// 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		if(value.total=='0'){
		    		getCountExamitemList(0);
	    		}else{
	    			$('#groupusershow').datagrid('selectRow',0);
		    		var row = $('#groupusershow').datagrid('getSelected');  
		    		getCountExamitemList(row.exam_num);
	    		}
	    	},
	    	onDblClickRow : function(index, row) {	
	    		getCountExamitemList(row.exam_num);
			},
			onClickRow :function(index, row){
				
			},
			//height: window.screen.height-400,
			rownumbers:true,
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true
	});
	}

 function getCountExamitemList(examinfo_id){
	 
	 var model={"examinfo_id":examinfo_id
	 };
	 $("#item_result").datagrid({
			url: 'getDirectorDiseaseList.action',
			queryParams: model,
			rownumbers:false,
			pageSize: 15,//每页显示的记录条数，默认为10 
			pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			sortName: 'cdate',
			sortOrder: 'desc',
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
			//striped:true,
			toolbar:"#toolbar",
			border:false,
			nowrap:false
		});
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
			if(row.health_level == 1 || row.health_level == 2 || row.health_level == 3|| row.health_level == 5|| row.health_level == 6){
				return 'color:#F00;';
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
			}else if(row.health_level == 5){
				return row.exam_result+' ↑↑';
			}else if(row.health_level == 6){
				return row.exam_result+' ↓↓';
			}else{
				return row.exam_result;
			}
		}else{
			return row.exam_result.replace(/\n/g,'</br>');
		}
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
 
 
