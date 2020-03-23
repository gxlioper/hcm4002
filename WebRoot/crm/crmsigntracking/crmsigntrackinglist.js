$(document).ready(function () {
	rili();
	getCrmSignTrackingList(getNowFormatDate(),getNowFormatDate());
});
function rili(){
	$.ajax({
		url : 'getCrmSignTrackingTime.action',
		type : 'post',
		//dataType: "json",
		success : function(data) {
			var strs=[];
			strs=data.split(',');
			$('#genzongrili').calendar({
				styler: function(date){
					for(var i=0;i<strs.length;i++){
					if((date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate())==strs[i]){
						return 'background-color:#ccc';
					}
					}
				},
				onSelect: function(date){
					var dtime = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
					$("#CrmSignTrackingshow").datagrid('reload',{start_date:dtime,end_date:dtime})
					
				}
			})
		}
	});
}

$(function () {
	$("#serch_sign_num").combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:23,
        width:140,
        mode:'remote',
        url:'getSignBillPlanByName.action',
        onSelect:function(record){
        	$("#addcontact_name").combobox('clear');
        	$("#addcontact_name").combobox('reload','getCompanyContactsList.action?company_id='+record.company_id);
        }
	});
	$("#addcontact_name").combobox({
		valueField: 'id',
        textField: 'contacts_name',
        hasDownArrow:true,
        height:23,
        width:80,
        url:'getCompanyContactsList.action?company_id=0'
	});
});


/**
 * 清空查询
 */
function empty(){
	 $("#serch_sign_num").combobox('clear');
	 $("#addcontact_name").combobox('clear');
	 $('#start_date').datebox('setValue','');
	 $('#end_date').datebox('setValue','');
}

function chaxun(){
	var data =  $('#serch_sign_num').combobox('getData');
	var temp = false;
	var sign_num = '';
	for(i=0;i<data.length;i++){
		if($('#serch_sign_num').combobox('getValue') == data[i].sign_num){
			temp = true;
		}
	}
	if(temp){
		sign_num = $('#serch_sign_num').combobox('getValue');
	}
	$("#CrmSignTrackingshow").datagrid('reload',{start_date:$('#start_date').datebox('getValue'),
												 end_date:$('#end_date').datebox('getValue'),
												 contact_name:$('#addcontact_name').combobox('getText'),
												 sign_num:sign_num})
}

function getCrmSignTrackingList(time,time1){
	var start_date = $('#start_date').datebox('getValue');
	var end_date = $('#end_date').datebox('getValue');
	if(time != undefined){
		start_date = time;
	}
	if(time1 != undefined){
		end_date = time1;
	}
	 $("#CrmSignTrackingshow").datagrid({
		 url:'getCrmSignTrackingList.action',
		 queryParams:{
			 start_date:start_date,
			 end_date:end_date
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
			 	{align:'center',field:'id',title:'id',hidden:true},
		        {align:'center',field:'sign_num',title:'签单计划编码',width:30},
		        {align:'center',field:'sign_name',title:'签单计划名称',width:30},
		        {align:'center',field:'tracking_date',title:'跟踪日期',width:40},
		        {align:'center',field:'contact_name',title:'目标联系人姓名',width:28},
	            {align:'center',field:'phone',title:'目标联系人电话',width:28},
			 	{align:'center',field:'tracking_content',title:'沟通内容',width:28},
			 	{align:'center',field:'remark',title:'备注',width:20},
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true
	});
}

//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'新增',
	width:120,
	iconCls:'icon-add',
	handler:function(){
		 $("#dlg-edit").dialog({
				title : '新增',
				width : 750,
				height :450,
				href : 'addCrmSignTrackingPage.action'
			});
			$("#dlg-edit").dialog('open');
}
}];

//-------------------------------------------获取系统时间----------------------------------------------------
//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
  var date = new Date();
  var seperator1 = "-";
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var strDate = date.getDate();
  if (month >= 1 && month <= 9) {
      month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate;
  return currentdate;
}