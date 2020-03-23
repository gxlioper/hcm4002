$(document).ready(function () {
	getuser();
	
	$('#com_Name').textbox('textbox').bind('blur', function() { 
		select_com_list_over();
    });	
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {
		select_com_list_over();
    });	
	$('#conn_rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		//editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == customer_type_idtype) {
					$('#conn_rylb').combobox('setValue', data[i].id);
					break;
				} else {
					$('#conn_rylb').combobox('setValue', data[0].id);
				}
			}
		}
	});
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
});
/**
 * 清空查询
 */
function empty(){
	 $('#arch_num').val(""),
	 $('#exam_num').val(""),
	 $('#user_name').val(""),
	 $('#phone').val(""),
	 $('#id_num').val(""),
	 $('#com_Name').textbox('setValue',"")
	// getuser();
}
 //-------------------------------显示人员列表列表------------------------------------
/**
 * 显示人员列表
 */
 function getuser(){	
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 
		 var arch_num = undefined;
		 if($("#arch_num_chkItem")[0].checked){
			 arch_num = $("#arch_num").val();
		 }
		 var exam_num = undefined;
		 if($("#exam_num_chkItem")[0].checked){
			 exam_num = $("#exam_num").val();
		 }
		 var user_name = undefined;
		 if($("#user_name_chkItem")[0].checked){
			 user_name = $("#user_name").val();
		 }
		 var phone = undefined;
		 if($("#phone_chkItem")[0].checked){
			 phone = $("#phone").val();
		 }
		 var id_num = undefined;
		 if($("#id_num_chkItem")[0].checked){
			 id_num = $("#id_num").val();
		 }
		 var company = undefined;
		 if($("#com_Name_chkItem")[0].checked){
			 company = $("#company_id").val()
		 }
		 var ren_type = undefined;
		 if($("#conn_rylb_chkItem")[0].checked){
			 ren_type = $("#conn_rylb").combobox('getValue');
		 }
		 var is_Active = undefined;
		 if($("#conn_is_Active")[0].checked){
			 is_Active = $("#is_Active").combobox('getValue');
		 }
		 var start_date = "";
		 var end_date = "";
		 if($("#chkItem_date")[0].checked){
			 start_date = $("#start_date").datebox('getValue')
			 end_date = $("#end_date").datebox('getValue');
		 }
		
	     $("#groupusershow").datagrid({
		 url:'getexamInfo.action',
		 queryParams:{
			 arch_num:arch_num,
			 exam_num:exam_num,
			 user_name:user_name,
			 phone:phone,
			 id_num:id_num,
			 company:company,
			 ren_type:ren_type,
			 start_date:start_date,
			 end_date:end_date,
			 is_Active:is_Active
		 },
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	            {align:'center',field:'caozuo',title:'操作',width:15,"formatter":f_cz},	
	            {align:'center',field:'arch_num',title:dahname,width:15},	
	            {align:'center',field:'exam_num',title:tjhname,width:15},
			 	{align:'center',field:'user_name',title:'姓名',width:15},
			 	{align:'center',field:'sex',title:'性别',width:10},
			 	{align:'center',field:'age',title:'年龄',width:10},
			 	{align:'center',field:'id_num',title:'身份证',width:25},
			 	{align:'center',field:'phone',title:'电话',width:15},
			 	{align:'center',field:'exam_type_y',title:'个检/团检',width:10},
			 	{align:'center',field:'company',title:'单位',width:15},
			 	{align:'center',field:'c_name',title:'登记人',width:15},
			 	{align:'center',field:'register_date',title:'登记日期',width:15},
			 	{align:'center',field:'u_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改日期',width:15}
		 	]],   	
		 	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
		    fit:true
	});
}
 /**
  * 人员操作
  */
 function f_cz(val,row){
	 	var str="";
	 		if(row.is_Active=="Y"){
	 	str+='<a href=\"javascript:delexamInfo(\''+row.exam_num+'\')\"><font color="#ff0000">删除</font></a>'	
	 		}else{
 		str+='<a href=\"javascript:huifu(\''+row.exam_num+'\')\">恢复</a>'
	 		}
	 return str;
}
 /**
  * 删除人员
  * @param id
  */
function delexamInfo(exam_num){
	$.ajax({
		url:'delExamInfoCheck.action',
		data:{
			exam_num:exam_num
		},
		success:function(data){
			 $.messager.confirm('提示信息',data.split('-')[1]+' 您确认要删除吗？',function(r){
				  if(r){
						var is_Active="N";
						$.ajax({
							url:'updateExamInfo.action',
							data:{
								is_Active:is_Active,
								exam_num:exam_num
							},
							success:function(data){
								if (data.split("-")[0] == 'ok') {
									$("#groupusershow").datagrid('reload');
									$.messager.alert("警告信息",data,"info");
								} else {
									$.messager.alert("警告信息",data,"error");
								}
								
							},
							error:function(){
								$.messager.alert("警告信息","操作失败","error");
							}
						})
				   }
			   });
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})

}
/**
 * 恢复人员
 * @param id
 */
function huifu(id){
	var is_Active="Y";
	$.ajax({
		url:'updateExamInfo.action',
		data:{
			is_Active:is_Active,
			exam_num:id
		},
		success:function(data){
			
			if (data.split("-")[0] == 'ok') {
				$("#groupusershow").datagrid('reload');
				$.messager.alert("警告信息",data,"info");
			} else {
				$.messager.alert("警告信息",data,"error");
			}
		},
		error:function(){
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}
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
		$("#com_Name").textbox("setValue",name);
		$("#company_id").val(id);
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
