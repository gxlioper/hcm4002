$(document).ready(function () {
	$('#com_Namejkz').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Namejkz').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Namejkz').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });
	 $('#examflags').combobox({
		 	data:[{
		 		id:'1',value:'已上传'
		 	},{
		 		id:'0',value:'未上传'
		 	},{
		 		id:'2',value:'上传失败'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    panelHeight:'auto',
		    prompt:'请选择是否上传'
		    
	 });
	 $('#sfdy').combobox({
		 data:[{
			 id:'1',value:'已打印'
		 },{
			 id:'0',value:'未打印'
		 }],
		 valueField:'id',    
		 textField:'value',
		 panelHeight:'auto',
		 prompt:'选择打印状态'
			 
	 });
	getJKZList(0);
	
	$('#exam_numjkz').css('ime-mode','disabled');
	$('#exam_numjkz').focus();
	
});

/**
 * 清空查询
 */
function empty(){
	 $('#user_namejkz').val("");
	 $('#exam_numjkz').val("");
	 $('#id_numjkz').val("");
	 $('#join_datejkzstart').datebox('setValue','');
	 $('#join_datejkzend').datebox('setValue','');
	 $('#examflags').combobox('setValue','');
	 $('#company_id').val('');
	 $('#arch_numjkz').val('');
	 $('#id_numjkz').val('');
	 $('#sfdy').combobox('setValue','');
	 getJKZList(1);
}
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
		$("#com_Namejkz").textbox('setValue',name);
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

function getJKZList(pra){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 
	 $("body").prepend(str);
	 $("#jkzshow").datagrid({
		 url:'getJKZList.action',
		 queryParams:{
			 exam_num:$('#exam_numjkz').val(),
			 chi_name:$('#user_namejkz').val(),
			 joinstart_date:$('#join_datejkzstart').datebox('getValue'),
			 joinend_date:$('#join_datejkzend').datebox('getValue'),
			 examflag:$('#examflags').combobox('getValue'),
			 company_id:$('#company_id').val(),
			 arch_num:$('#arch_numjkz').val(),
			 sfzh:$('#id_numjkz').val(),
			 sfdy:$("#sfdy").combobox('getValue'),
			 pra:pra
		 },
		 toolbar:toolbar,
//		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {field:'ck',checkbox:true },
			 {align:'center',field:'bh',title:'健康证编号',width:20},
			 {align:'center',field:'exam_num',title:tjhname,width:20},
		        {align:'center',field:'xm',title:'姓名',width:18},
		        {align:'center',field:'xb',title:'性别',width:10},
	            {align:'center',field:'nl',title:'年龄',width:10},
			 	{align:'center',field:'dw',title:'工作单位',width:25},
			 	{align:'center',field:'hy',title:'行业',width:20},
			 	{align:'center',field:'gz',title:'工种',width:15},
				{align:'center',field:'rq',title:'体检日期',width:15},
			 	{align:'center',field:'hjg',title:'培训结果',width:15},
			 	{align:'center',field:'fzrq',title:'发证日期',width:15},
			 	{align:'center',field:'djczy',title:'登记操作员',width:15},
			 	{align:'center',field:'examflag',title:'是否上传',width:15},
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}

function impuser(ids){
	  if(ids!=null&&ids.length>0){
	 $.messager.confirm('提示信息','是否确定将选择行上传？',function(r){
		 	if(r){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	 $.ajax({
		  
			url : 'shangChuanjkz.action',
			data : {
				exam_nums:ids,
					},
					type : "post",//数据发送方式   
					success : function(data) {
						$(".loading_div").remove();
							$.messager.alert("操作提示", data);							
					},
					error : function(data) {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", data);					
					}
				});
  }
	 });
	  }else{
		  $.messager.alert("操作提示", "请选择需要上传的信息");	
	  }
	
}

function impalluser(){
	
	 $.messager.confirm('提示信息','是否确定将所有信息上传？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 		 $("body").prepend(str);
	 $.ajax({
			url : 'shangChuanAlljkz.action',
			data : {
				 exam_num:$('#exam_numjkz').val(),
				 chi_name:$('#user_namejkz').val(),
				 joinstart_date:$('#join_datejkzstart').datebox('getValue'),
				 joinend_date:$('#join_datejkzend').datebox('getValue'),
				 examflag:$('#examflags').combobox('getValue'),
				 company_id:$('#company_id').val(),
				 arch_num:$('#arch_numjkz').val(),
				 sfzh:$('#id_numjkz').val()
				},
					type : "post",//数据发送方式   
					success : function(data) {
						$(".loading_div").remove();
							$.messager.alert("操作提示", data);							
					
					},
					error : function(data) {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", data);					
					}
				});
  }
	 });
	
}
//contype 0:预览; 1:打印
function healthreport5(barids,contype){
	var type = 'Y';
	if(contype == 1){
		type = "P";
	}
	var exeurl="GuidBarServices://&"+type+"&0&"+$("#userid").val()+"&healthcert&"+barids;
	RunReportExe(exeurl);
}
//contype 0:预览; 1:打印
function healthreport(barids,contype){
		var exeurl="reportServices://&0&"+barids+"&"+contype+"&1";
		 RunReportExe(exeurl);
}
function RunReportExe(strPath) {
	 location.href=strPath;
}
//----------------------------------定义工具栏---------------------------
var toolbar = [{
	text:'选择数据上传',
	width:180,
	iconCls:'icon-save',
    handler:function(){
    	var checkedItems = $('#jkzshow').datagrid('getChecked');
    	var ids = "";
    	var strs=[];
	    $.each(checkedItems, function(index, item){
	       strs.push(item.exam_num);
	    }); 	   
	    ids=strs.join(',');
	    impuser(ids);
    }
},{
	text:'整体数据上传',
	width:160,
	iconCls:'icon-check',
    handler:function(){
    	var checkedItems = $('#jkzshow').datagrid('getChecked');
    	impalluser();
    }
},{
	text:'查看详细信息',
	width:120,
	iconCls:'icon-print',
    handler:function(){
    	var item = $('#jkzshow').datagrid('getSelected');
    	if(item!=null){
   		 $("#dlg-edit").dialog({
   				title : '上传',
   				width : 800,
   				height :450,
   				href : 'getJianKangZhengDetailPage.action?exam_num='+item.exam_num
   			});
   			$("#dlg-edit").dialog('open');
   	}else{
   		$.messager.alert("操作提示","请选择要查看结果的记录",'error');
   	}   
    }
},{
	text:'健康证预览',
	width:120,
	iconCls:'icon-print',
	handler:function(){
    	var ids = "";
    	var checkedItems = $('#jkzshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+"$"+(item.exam_num);
	    });
	    if(ids.length>1) ids=ids.substring(1,ids.length);
	    if((ids=="")||(ids.split(',').length!=1)){
    		$.messager.alert("操作提示", "请选择一个体检人员","error");
    	}else{
    		if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
    			healthreport(ids,0);
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			healthreport(ids,0);
    	    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
    	    	healthreport5(ids,0);
    	    }else{
    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    	    }
    	}    
    }
},{
	text:'健康证打印',
	width:120,
	iconCls:'icon-print',
	handler:function(){
    	var ids = "";
    	var checkedItems = $('#jkzshow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids=ids+"$"+(item.exam_num);
	    });
	    if(ids.length>1) ids=ids.substring(1,ids.length);
	    if((ids=="")||(ids.split(',').length!=1)){
    		$.messager.alert("操作提示", "请选择一个体检人员","error");
    	}else{
    		if($("#barcode_print_type").val() == '1' || $("#barcode_print_type").val() == '2'){//调用旧预览程序
    			healthreport(ids,1);
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			healthreport(ids,1);
    	    }else if($("#barcode_print_type").val() == '5'){//调用5.0打印
    	    	healthreport5(ids,1);
    	    }else{
    	    	$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    	    }
    	}    
    }
}
];
