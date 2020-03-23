$(document).ready(function () {
	//$("[name = chkItem]:checkbox").attr("checked", true);	
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
           examnumenter();
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
	
	$('#exam_type').combobox({
		url : 'getDatadis.action?com_Type=EXAMTYPE',
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
	 var chk_value ="";    
	  $('input[name = chkItem]:checked').each(function(){    
	   chk_value=chk_value+","+($(this).val());    
	  }); 
	  if(chk_value.length>1){
		  chk_value=chk_value.substring(1,chk_value.length);
	  }
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "exam_num":$("#exam_num").val(),
				 "time1":$("#start_date").datebox('getValue'),	
				 "time2":$("#end_date").datebox('getValue'),	
				 "time3":$("#time3").datebox('getValue'),	
				 "time4":$("#time4").datebox('getValue'),	
				 "custname":$("#custname").val(),
				 "chkItem":chk_value,
				 "exam_type":document.getElementsByName("exam_type")[0].value,
				 "create_time":$("#create_time").datebox('getValue'),
				 "status":document.getElementsByName("statusss")[0].value,	
				 "employeeID":'',
				 "djdstatuss":'',
				 "arch_num":$("#arch_num").val(),
				 "id_num":$("#id_num").val()
		 };
	     $("#groupusershow").datagrid({
		 url:'getbunkExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'fenpei',title:'分配',width:10,"formatter":f_fenpei},
		    {align:'center',field:'exam_num',title:tjhname,width:20,sortable:true},
		    {align:'center',field:'arch_num',title:dahname,width:20,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:40,sortable:true},
		 	{align:'center',field:'employeeID',title:'工号',width:18},
		    {align:'center',field:'exam_types',title:'体检类型',width:18},	
		 	{align:'center',field:'user_name',title:'姓名',width:25,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:10},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:25,sortable:true},		 	
		 	{align:'center',field:'set_name',title:'套餐',width:60},	
		 	{align:'center',field:'join_date',title:'体检日期',width:25,sortable:true},	 	
		 	{align:'center',field:'statusall',title:'体检状态',width:15,"formatter":f_showstatus},
		 	{align:'center',field:'bunk',title:'床位',width:15},
		 	{align:'center',field:'chi_name',title:'分配医生',width:15},
		 	{align:'center',field:'allocationdate',title:'分配时间',width:25},
		 	{align:'center',field:'final_doctor',title:'总检医生',width:18},
		 	{align:'center',field:'check_doctor',title:'审核医生',width:18}
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
			height: window.screen.height-380,
			nowrap:false,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true,
	        toolbar:toolbar	       
	});
	}
 

 function f_fenpei(val,row){
	  return '<a href=\"javascript:f_allocation(\''+row.exam_num+'\',\''+row.arch_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"分配\" /></a>';
 }
 
 function f_allocation(exam_num,arch_num){
	 $("#dlg-bunk").dialog({
 		title:'床位分配',
 		href:'bunkweihu.action?exam_num='+exam_num +"&arch_num="+arch_num,
 	});
 	$("#dlg-bunk").dialog("open");
	 
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

  

