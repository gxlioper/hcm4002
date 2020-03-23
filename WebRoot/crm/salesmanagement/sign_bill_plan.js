var toolbar = [{
    text:'申请签单计划',
    iconCls:'icon-add',
    handler:function(){
    	$('#dlg-edit').dialog({    
    	    title: '申请签单计划', 
    	    href: 'getCrmSignBillPlanAddPage.action',
    	    width: 1000,    
    	    height: 500, 
    	    closed: true,
    	    cache: false,
    	    modal:true
    	});
    	$('#dlg-edit').dialog('open');
    }
},{
    text:'排除撞单',
    iconCls:'icon-edit',
    handler:function(){
    	var item = $('#sign_bill_span_list').datagrid('getSelected');
	if(item==null){
		$.messager.alert("操作提示","请选中要排除撞单的签单计划","error");
	}else{
	id =item.id;
	sign_name=item.sign_name;
	company_id=item.company_id;
	var url='/getCrmSignBillPlanZhuangdanPage.action?ids='+id+'&sign_names='+sign_name+'&company_id='+company_id;
	newWindow = window.open(url, "排除撞单", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
	}
    }
},{
    text:'修改负责人',
    iconCls:'icon-edit',
    handler:function(){
    	var item = $('#sign_bill_span_list').datagrid('getSelected');
	if(item==null){
		$.messager.alert("操作提示","请选中要修改负责人的签单计划","error");
	}else{
	id =item.id;
	sign_name=item.sign_name;
	com_name=item.com_name;
    	$('#dlg-show').dialog({   
    	    title: '修改负责人', 
    	    href: 'getCrmSignBillPlanEditCreaterPage.action?ids='+id+'&sign_name='+sign_name+'&com_name='+com_name,
    	    width: 500,    
    	    height: 300, 
    	    closed: true,
    	    cache: false,
    	    modal:true
    	});
    	$('#dlg-show').dialog('open');
    }
    }
}, {
	text:'删除签单计划',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var sign_num;
    	var item = $('#sign_bill_span_list').datagrid('getSelected');
    	if(item==null){
    		$.messager.alert("操作提示","请选中要删除的签单计划","error");
    	}else{
    		sign_num =item.sign_num;
    	delsign(sign_num);
    	}
    }
},{
    text:'签单计划跟踪',
    iconCls:'icon-edit',
    handler:function(){
    	var item = $('#sign_bill_span_list').datagrid('getSelected');
	if(item==null){
		$.messager.alert("操作提示","请选中要跟踪的签单计划","error");
	}else{
	id =item.sign_num;
	$('#dlg-show').dialog({   
	    title: '签单计划跟踪', 
	    href: 'getBatchPlanLogListPage.action?ids='+id+'&type=1',
	    width: 1000,    
	    height: 450, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#dlg-show').dialog('open');
    }
    }
}];
function delsign(sign_num){
		$.messager.confirm('提示信息','是否确定删除选中签单计划',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				 $.ajax({
					 url : 'deleteSignBillPlan.action',
					data : {sign_num:sign_num},
					type : "post",
					success : function(data) {
						$(".loading_div").remove();
						if(data.indexOf('失败')==-1){
							$.messager.alert("操作提示",data);
						}else{
							$.messager.alert('提示信息', data, 'error');
						}
						$('#dlg-edit').dialog('close')
						getSignBillPlanList();
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert('提示信息', '操作失败！', 'error');
					}
				 });
		 	 }
		 });
	}
$(document).ready(function () {
	$('#ser_creater').combobox({
		url : 'getCrmUserCenterList.action',
		editable : true, //不可编辑状态
		cache : false,
		height:26,
		width:100,
		panelHeight : 'auto',//自动高度适合
		valueField : 'userid',
		textField : 'name',
		onLoadSuccess : function(data) {
		}
	});
	
	getSignBillPlanList();
});

function chaxunsingn(){
	$("#sign_bill_span_list").datagrid('load',{"sign_name":$("#ser_sign_name").val(),"sign_year":$("#ser_sign_year").val(),"userid":$('#ser_creater').combobox('getValue')});
}

function getSignBillPlanList(){
	var model = {"sign_name":$("#ser_sign_name").val(),"sign_year":$("#ser_sign_year").val(),"userid":$('#ser_creater').combobox('getValue')};
	$("#sign_bill_span_list").datagrid({
		url: 'getCrmSignBillPlanList.action',
		queryParams: model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		toolbar: '#toolbar',
		columns:[[{align:'center',field:'ck',checkbox:true},
			{align:"center",field:"id","title":"id",hidden:true},
		      {align:"center",field:"track_progresss","title":"跟踪进度","width":15},
		      {align:"center",field:"track_time","title":"变化时间","width":25},
		      {align:"center",field:"sign_statuss","title":"状态","width":10},
              {align:"center",field:"sign_num","title":"编号","width":15},
     		  {align:'center',field:"sign_name","title":"名称","width":20},
     		  {align:"center",field:"com_name","title":"单位","width":25},
     		 {align:"center",field:"company_id","title":"单位",hidden:true},
     		  {align:"center",field:"sign_year","title":"年度","width":10},
     		  {align:"center",field:"sign_type","title":"签单类型","width":12},
     		 {align:"center",field:"sign_persion","title":"估算人数","width":12},
     		{align:"center",field:"sign_amount","title":"估算金额","width":12},
     		{align:"center",field:"creater","title":"负责人","width":10},
     		  {align:"center",field:"f_ck","title":"任务","width":10,"formatter":f_ck}
     		  ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
		fitColumns:true,
		striped:true,
		fit:true,
		toolbar:toolbar,
		onDblClickRow:function(index, row){
        	var row = $('#sign_bill_span_list').datagrid('getRows')[index];
        	f_ck_sign(index,row.company_id);
	    }
	});
}

function f_ck(value,row,index){
		var str = '<a href=\"javascript:f_renwu_sign(\''+row.sign_num+'\',\''+row.sign_statuss+'\',\''+row.track_progresss+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-export\" alt=\"任务\" /></a>';
		return str;	
}
function f_renwu_sign(sign_num,sign_statuss,track_progresss){
	if(sign_statuss=='正式稿'){
			window.parent.addPanel_other('体检任务','getCrmBatchManagerPage.action?sign_num='+sign_num,'"+wx.getIcon_url()+"','1');	
	}else{
		$.messager.alert("操作提示","该签单计划无法进行体检任务维护","error");
	}
}
var row_index = 0;
function f_ck_sign(index,company_id){
	row_index = index;
	$('#dlg-edit').dialog({    
	    title: '查看签单计划', 
	    href: 'getSignBillPlanLookPage.action?company_id='+company_id,
	    width: 1100,    
	    height: 500, 
	    closed: true,
	    cache: false,
	    modal:true
	});
	$('#dlg-edit').dialog('open');
}