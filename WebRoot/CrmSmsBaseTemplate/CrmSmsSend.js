$(document).ready(function () {
	$('#fenlei').combobox({
		url:'getDatadisKongGe.action?com_Type=DXMBLX',    
		valueField:'id',    
		textField:'name',
	   panelHeight:'auto'
			
	})
	
	$('#smsBaseTemplate').combobox({
		url:'queryCrmSmsBaseTemplateall.action',    
		valueField:'id',    
		textField:'sms_name',
	   panelHeight:'auto',
		onLoadSuccess : function(data) {
			$('#smsBaseTemplate').combobox('setValue', data[0].id);
		}
			
	})
	getgroupuserGrid();
});
/**
 * 清空查询
 */
function empty(){
	$('#num').textbox('setValue',"");
	$('#demo_name').textbox('setValue',"");
	getgroupuserGrid();
}
 //-------------------------------显示报告样本------------------------------------
/**
 * 显示报告样本列表
 */
 function getgroupuserGrid(){	 
	    var smsBaseTemplateid = "";
	 	if($('#smstemp_cke').is(":checked")){
	 		smsBaseTemplateid =document.getElementsByName("smsBaseTemplate")[0].value;
	 	}
	 	var name = "";
	 	if($('#name_cke').is(":checked")){
	 		name = $('#name').val();
	 	}
	 	var exam_num = "";
	 	if($('#exam_num_cke').is(":checked")){
	 		exam_num = $('#exam_num').val();
	 	}
	 	var phone = "";
	 	if($('#phone_cke').is(":checked")){
	 		phone=$('#phone').val();
	 	}
	 	var user_type = "";
	 	if($('#user_type_cke').is(":checked")){
	 		user_type = $('#user_type').combobox('getValue');
	 	}
	 	var sms_type = "";
	 	if($('#sms_type_cke').is(":checked")){
	 		sms_type = $('#sms_type').combobox('getValue');
	 	}
	 	var sms_status = "";
	 	if($('#sms_status_cke').is(":checked")){
	 		sms_status = $('#sms_status').combobox('getValue');
	 	}
	 	var sms_date = "";
	 	var sms_date_j ="";
	 	if($('#sms_date_cke').is(":checked")){
	 		sms_date = $('#sms_date_q').datebox('getValue');
	 		sms_date_j = $('#sms_date_j').datebox('getValue');
	 	}

	 	var model = {
	 			template_id:smsBaseTemplateid,
	 			name:name,
	 			exam_num:exam_num,
	 			phone:phone,
	 			user_type:user_type,
	 			sms_type:sms_type,
	 			sms_status:sms_status,
	 			time1:sms_date,
	 			sms_date_j:sms_date_j
	 	}
	 
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	     $("#groupusershow").datagrid({
		 url:'queryCrmSmsSendList.action',
		 queryParams:model,
		 rownumbers:true,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'exam_num',title:tjhname,width:15},	
		    {align:'center',field:'user_name',title:'姓名',width:10,},
		 	{align:'left',field:'sms_phone',title:'电话',width:10},
		 	{align:'center',field:'sms_note',title:'短信内容',width:50},
		 	{align:'center',field:'sms_status_s',title:'短信状态',width:10},
		 	{align:'center',field:'user_type_s',title:'用户类型',width:10},
		 	{align:'center',field:'sms_type_s',title:'短信类型',width:10},
		 	{align:'center',field:'chi_name',title:'发送者',width:10},
		 	{align:'center',field:'sms_date',title:'发送时间',width:10},
		 	{align:'center',field:'sss',title:'重新发送',width:10,"formatter":f_xg},
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
		    fit:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	/*var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updataSampleReportDemoPage(row_id);*/
	        }
	});
}


//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	 
	 if(row.sms_type_s=='立即发送'){
		 if(row.sms_status=='0' || row.sms_status=='2'){
			 return '<a href=\"javascript:chongxinffasong(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
		 }
	 }
	
}
/**
 * 
 * 
 */
 function chongxinffasong(id){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		$.ajax({
			url:'saveCrmSmsSendCHONGFA.action',
			data:{
				id:id
			},
			type:'post',
			success:function(data){
				$(".loading_div").remove();
				if (data.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", data.split("-")[1]);
				} else {
					$.messager.alert("操作提示", data.split("-")[1], "error");
				}
			},
			error:function(){
				$(".loading_div").remove();
				$.messager.alert("警告信息","操作失败","error");
			}
		})
}

/**
 * 手工发送短信
 * 
 */
 var smsids;
function handsmssend(smsids) {
	$.messager.confirm('提示信息', '确定要批量发送短信吗？', function(r) {
		if (r) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'handsmssend.action',
				type : 'post',
				// dataType: "json",
				data : {
					"ids" :smsids
				},
				success : function(data) {
					$(".loading_div").remove();
					
					if (data.split("-")[0] == 'ok') {
						$.messager.alert("操作提示", data.split("-")[1]);
					} else {
						$.messager.alert("操作提示", data.split("-")[1], "error");
					}
					/*if(data.indexOf("ok")>0){
						$.messager.alert("提示信息",data,"info");
					} else {
						$.messager.alert("警告信息",data,"error");
					}*/
					getgroupuserGrid();
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	});
}

//----------------------------------定义工具栏---------------------------
var toolbar = [ /*{
	text : '新增短信',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		 var ul='addCrmSmsSend.action'; 
		 window.open(ul,"短信编辑", "toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}
},*/{
	text:'手工发送短信',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    handsmssend(ids.toString());
    }
} ];
