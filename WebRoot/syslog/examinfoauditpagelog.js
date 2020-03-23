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
//	 var employeeID = undefined;
//	 if($("#ck_searchemployeeID")[0].checked){
//		 employeeID = $("#searchemployeeID").val();
//	 }
//	 var arch_num = undefined;
//	 if($("#ck_arch_num")[0].checked){
//		 arch_num = $("#arch_num").val();
//	 }
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
//				 "employeeID":employeeID,
//				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamInfoAuditListLog.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'exam_num',title:tjhname,width:25,sortable:true},
		    {align:'center',field:'arch_num',title:'档案号',width:18,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:25,sortable:true},
		    {align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20,sortable:true},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:20,sortable:true},
		 	{align:'center',field:'ckjc',title:'审计检查结果',width:20,"formatter":f_ckjc},
		 	{align:'center',field:'ckzj',title:'审计总检结果',width:20,"formatter":f_ckzj},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		
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
function f_ckjc(val,row){
	return '<a href=\"javascript:ckjs(\''+row.exam_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"检查结果\" /></a>';
}
//审计检查结果
function ckjs(exam_num){
	$("#dlg-s").dialog({
		title: '审计检查结果',
		href:'getExamDepResultPageLog.action?exam_num='+exam_num,
		width:1200,
		height:500
	});
	$("#dlg-s").dialog('open');
}
function f_ckzj(val,row){
	return '<a href=\"javascript:ckzj(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"总检结果\" /></a>';
}
//审计总检结果
function ckzj(id){
	$("#dlg-s").dialog({
		title: '审计总检结果',
		href:'getExamSummaryPageLog.action?id='+id,
		width:1200,
		height:500
	});
	$("#dlg-s").dialog('open');
}

 function setchebox(inp){
	 	$('#custname').textbox({  
	 	    onChange: function(value) {
	 	    	if(value==""){
	 	    		$('#ck_custname').attr('checked',false)
	 	    	} else {
	 	    		$('#ck_custname').attr('checked',true)
	 	    	}
	 	    }
	 	});
	 	$('#exam_num').textbox({  
	 		onChange: function(value) {
	 			if(value==""){
	 				$('#ck_exam_num').attr('checked',false)
	 			} else {
	 				$('#ck_exam_num').attr('checked',true)
	 			}
	 		}
	 	});
	 	$('#id_num').textbox({  
	 		onChange: function(value) {
	 			if(value==""){
	 				$('#ck_id_num').attr('checked',false)
	 			} else {
	 				$('#ck_id_num').attr('checked',true)
	 			}
	 		}
	 	});
	 	$('#searchphone').textbox({  
	 		onChange: function(value) {
	 			if(value==""){
	 				$('#ck_searchphone').attr('checked',false)
	 			} else {
	 				$('#ck_searchphone').attr('checked',true)
	 			}
	 		}
	 	});
	 	
	 }
 

