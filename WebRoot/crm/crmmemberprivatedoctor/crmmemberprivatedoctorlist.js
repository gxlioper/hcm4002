$(document).ready(function () {
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
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
        	getCrmMemberPrivateDoctorList();
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
	
	$('#searchsetname').combobox({
		url : 'getDirectorExamSetList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			
		}
	});
	
	getCrmMemberPrivateDoctorList();
	
});

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
/**
 * 显示健康计划表
 */
function getCrmMemberPrivateDoctorList(){
	
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
	 var sex = undefined;
	 if($("#ck_sex")[0].checked){
		 sex = $("#searchsex").combobox('getValue');
	 }
	 
	 var isvisit=undefined;
	 if($("#ck_isvisit")[0].checked){
		 isvisit = $("#isvisit").combobox('getValue');
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
	 var allot_status = undefined;
	 if($("#ck_allot")[0].checked){
		 allot_status = $("#searchallot").combobox('getValue');
	 }
	 var set_id = undefined;
	 if($("#ck_setname")[0].checked){
		 set_id = $("#searchsetname").combobox('getValue');
	 }
	 var amount1 = undefined,amount2 = undefined;
	 if($("#ck_amount")[0].checked){
		 amount1 = $("#amount1").val();
		 amount2 = $("#amount2").val();
	 } 
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "s_join_date":time1,	
				 "e_join_date":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "sex":sex,
				 "isvisit":isvisit,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "phone":searchphone,
				 "exam_type":examtype,
				 "allot_status":allot_status,
				 "set_id":set_id,
				 "amount1":amount1,
				 "amount2":amount2
		 };
	
	 $("#CrmMemberPrivateDoctorshow").datagrid({
		 url:'getCrmMemberPrivateDoctorList.action',
		 queryParams:model,
		 toolbar:toolbar,
		 rownumbers:false,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
			 	{align:'center',field:'exam_num',title:tjhname,width:18},
		        {align:'center',field:'arch_num',title:dahname,width:15},
		        {align:'center',field:'user_name',title:'姓名',width:15},
		        {align:'center',field:'id_num',title:'身份证号',width:28},
	            {align:'center',field:'sex',title:'性别',width:10},
	            {align:'center',field:'age',title:'年龄',width:10},
	            {align:'center',field:'phone',title:'电话',width:18},
	            {align:'center',field:'exam_types',title:'体检类型',width:15},
	            {align:'center',field:'join_date',title:'体检日期',width:18},
	            {align:'center',field:'set_name',title:'体检套餐',width:22},
	            {align:'center',field:'personal_pay',title:'体检金额',width:15},
	            {align:'center',field:'statuss',title:'体检状态',width:15},
			 	{align:'center',field:'doctorname',title:'私人医生',width:15},
			 	{align:'center',field:'allot_person_name',title:'分配人',width:15},
			 	{align:'center',field:'allot_date',title:'分配时间',width:18}
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		if($("#iszyb").val()==''){
					 $("div.datagrid-toolbar [id ='9']").eq(0).hide();  
				}
	    	},
		    singleSelect:false,
		    collapsible:true,
			pagination: true,
			fitColumns:true,
			fit:true,
		    striped:true
	});
}


//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'更新分配医生',
	width:120,
	iconCls:'icon-edit',
    handler:function(){
    	var data = $('#CrmMemberPrivateDoctorshow').datagrid('getSelections');
    	if(data.length == 0){
    		$.messager.alert('提示信息','请选择需要分配的体检人员!','error');
    		return;
    	}
	    $("#dlg-edit").dialog({
			title : '分配私人医生',
			width : 800,
			height :450,
			href : 'getMergeCrmMemberPrivateDoctorPage.action'
		});
		$("#dlg-edit").dialog('open');
  }
},{
	text:'普通报告预览',
	width:150,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmMemberPrivateDoctorshow').datagrid('getSelected');
    	if(item!=null){
    		if($("#report_print_type").val() == '5'){
    			//预览普通报告
    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&0";//0代表体检号Y代表预览，2代表职业病
    			location.href=exeurl;
    	    }else{
    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-REPORT_PRINT_TYPE","error");
    	    }
	   	}else{
	   		$.messager.alert("操作提示", "请选择一个体检人员","error");
	   	}    
    }
},{
	id:9,
	text:'职业病报告预览',
	width:150,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmMemberPrivateDoctorshow').datagrid('getSelected');
    	if(item!=null){
    		if($("#zyb_report_print_type").val() == '5'){
    			//预览职业病报告
    			var exeurl="reportServices://&0&"+item.exam_num+"&Y&2";//0代表体检号Y代表预览，2代表职业病
    			location.href=exeurl;
    	    }else{
    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-ZYB_REPORT_PRINT_TYPE","error");
    	    }
	   	}else{
	   		$.messager.alert("操作提示", "请选择一个体检人员","error");
	   	}    
    }
},{
	text:'检查结果',
	width:120,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#CrmMemberPrivateDoctorshow').datagrid('getSelected');
    	if(item!=null){
   		 $("#dlg-edit").dialog({
   				title : '检查结果',
   				width : 800,
   				height :450,
   				href : 'getNewVisitPlanPageResult.action?exam_num='+item.exam_num+'&arch_num='+item.arch_num
   			});
   			$("#dlg-edit").dialog('open');
   	}else{
   		$.messager.alert("操作提示","请选择要查看结果的记录",'error');
   	}   
    }
}];