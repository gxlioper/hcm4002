$(document).ready(function () {
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	getgroupuserGrid();	
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
        	//getexaminfoByExamNum();// 体检号回车事件
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
	/*$('#doctor_name').combobox({
		url : 'getDepuser.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'chi_Name',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			
		}
	});*/
	
	$('#dep_name').combobox({
		url :'getViewdepartment.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'dep_name',
		onLoadSuccess : function(data) {
			var val = $(this).combobox('getData');  
            for (var item in val[0]) {  
                if (item == 'id') {  
                    $(this).combobox('select', val[0][item]);  
                }  
            }
		}
	});
	/*$('#results_contrast').mouseleave(function(){
		 $('#results_contrast').css('display', 'none');
	});*/
	
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
function shuaxing(){
	getgroupuserGrid();
} 
/**
  * 
  */
var s_id=new Array();
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
	 var status = undefined;
	 if($("#ck_status")[0].checked){
		 status = $('#statusss').combobox('getValue');
	 }
	 var employeeID = undefined;
	 if($("#ck_searchemployeeID")[0].checked){
		 employeeID = $("#searchemployeeID").val();
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
	 var dep_name = undefined;
	 //if($("#ck_dep_name")[0].checked){
		 dep_name = $('#dep_name').combobox('getValue');
	 //}
	 var positive_find = undefined;
	 if($("#ck_positive_find")[0].checked){
		 positive_find = $('#positive_find').combobox('getValue');
	 }
	 var exam_date1 = undefined,exam_date2 = undefined;
	 if($("#ck_exam_date")[0].checked){
		 exam_date1 = $("#exam_date1").datebox('getValue');
		 exam_date2 = $("#exam_date2").datebox('getValue');
	 }
	 var customer_type = undefined;
	 if($("#ck_tjlx")[0].checked){
		 customer_type = $("#tjlx").combobox('getValue');
	 }
		 var model={
				 "company_id":comid,
				 "exam_num":exam_num,
				 "time1":time1,	
				 "time2":time2,	
				 "user_name":user_name,
				 "status":status,	
				 "employeeID":employeeID,
				 "arch_num":arch_num,
				 "id_num":id_num,
				 "dep_name":dep_name,
				 "positive_find":positive_find,
				 "exam_date1":exam_date1,
				 "exam_date2":exam_date2,
				 "customer_type":customer_type
		 };
	     $("#groupusershow").datagrid({
		 url:'getAllViewExamList.action',
		 dataType: 'json',
		 queryParams:model,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    //{align:'center',field:'exam_num',title:tjhname,width:20,"formatter":f_showexam,sortable:true},
		    {align:'center',field:'exam_num',title:'体检号',width:20},
		    {align:'center',field:'arch_num',title:dahname,width:18,sortable:true},
		 	//{align:'center',field:'id_num',title:'身份证号',width:25},
		    //{align:'center',field:'exam_types',title:'体检类型',width:15},	
		 	{align:'center',field:'user_name',title:'姓名',width:20,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:10,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:10,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:20},		 	
		 	{align:'center',field:'company',title:'单位',width:20},
		 	{align:'center',field:'set_name',title:'套餐',width:20},
		 	{align:'center',field:'join_date',title:'体检日期',width:15},
		 	{align:'center',field:'item_name',title:'收费名称',width:20},
		 	{align:'center',field:'exam_desc',title:'检查描述',width:30},
		 	{align:'center',field:'exam_result',title:'检查结果',width:20},
		 	{align:'center',field:'data_name',title:'阳性标记',width:20},
		 	{align:'center',field:'marker',title:'标记人',width:15},
		 	{align:'center',field:'mark_time',title:'标记时间',width:15},
		 	//{align:'center',field:'exam_times',title:'检查医生/日期',width:25},
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    	},
	    	/*onDblClickRow : function(index, row) {	 
	    		if(row.freeze == '1'){
					$.messager.alert("操作提示", '该体检人已冻结,不能检查!', "error");
					return;
				}
	    		f_examoneshow(row.exam_num);
			},*/
			height: window.screen.height-400,
			rownumbers:false,
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    nowrap:false,
		    striped:true,
		    toolbar:[{
				iconCls: 'icon-edit',
				text:"阳性标记",
				handler: function(){
					//var s_id=new Array();
					var rows= $("#groupusershow").datagrid('getChecked');
					if(rows.length<=0){
						$.messager.alert("提示信息","请选择要标记的行","error");
						return;
					}else{
						for(var i=0;i<rows.length;i++){
							s_id.push(rows[i].id);
						};
						
					}
					$("#dlg_edit").dialog({
			    		title:'阳性标记',
			    		href:'positiveEditpage.action'
			    	});
			    	$("#dlg_edit").dialog("open");
					}
			}]
	});
	}

 function savepositive_find(){
		$.ajax({
		url:'savePositivefind.action?ids='+s_id.toString(),
		data:{
			positive_find:$("#data_name").combobox('getValue'),
		},
		success:function(){
			$.messager.alert("提示信息","标记成功");
			$("#groupusershow").datagrid('reload');
			$("#dlg_edit").dialog("close");
		},
		error:function(){
			$.messager.alert("提示信息","标记失败","error");
		}
	});

}
 
  /* function f_examoneshow(id){
	   $("#exam_num").textbox("setValue", "");
	   var url='imagingResultsDetail.action?exam_num='+id;
	   newwin = window.open("", "影像科室检查结果", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height-30);
	   newwin.location = url;
	   newwin.focus();
   }*/
 
   /*function f_showexam(val,row){
	   if(row.freeze == '1'){
		   return row.exam_num;
	   }else{
		  return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
	   }
   }*/
   
  // function getexaminfoByExamNum(){}
   
  

