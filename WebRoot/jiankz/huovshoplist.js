$(document).ready(function () {
	 $('#data_type').combobox({
		 	data:[{
		 		id:'1',value:'检验项目'
		 	},{
		 		id:'2',value:'检查项目'
		 	},{
		 		id:'3',value:'普通项目'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    panelHeight:'auto',
		    prompt:'请选择项目类别'
		    
	 });
	 getHopVsHuoList();
});

/**
 * 清空查询
 */
function empty(){
	 $('#hop_data_code').val("");
	 $('#hop_data_name').val("");
	 $('#huo_data_code').val("");
	 $('#huo_data_name').val("");
	 $('#data_type').combobox('setValue','');
	 getHopVsHuoList();
}


function getHopVsHuoList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $("#hopvshuolistshow").datagrid({
		 url:'getHuoVsHopList.action',
		 queryParams:{
			 hos_data_code:$('#hop_data_code').val(),
			 hos_data_name:$('#hop_data_name').val(),
			 huo_data_code:$('#huo_data_code').val(),
			 huo_data_name:$('#huo_data_name').val(),
			 data_type:$('#data_type').combobox('getValue')
		 },
		 toolbar:toolbar,
//		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {align:'center',field:'hos_data_code',title:'健康证项目编号',width:20},
		        {align:'center',field:'hos_data_name',title:'健康证项目名称',width:20},
		        {align:'center',field:'huo_data_code',title:'项目编号',width:10},
	            {align:'center',field:'huo_data_name',title:'项目名称',width:10},
			 	{align:'center',field:'creaters',title:'创建者',width:25},
			 	{align:'center',field:'create_time',title:'创建时间',width:20},
			 	{align:'center',field:'updaters',title:'修改者',width:15},
				{align:'center',field:'update_time',title:'修改时间',width:15},
			 	{align:'center',field:'data_type',title:'项目类型',width:15},
			 	{align:'center',field:'hos_piny',title:'项目拼音',width:15},
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}

function delhuorow(id){
			$.messager.confirm('提示信息','是否确定删除选中对照项目',function(r){
			 	if(r){
			 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
					 $.ajax({
						 url : 'deleteHuoVsHop.action',
						data : {id:id},
						type : "post",
						success : function(data) {
							$(".loading_div").remove();
							$.messager.alert("操作提示",data);
							$('#dlg-edit').dialog('close')
							getHopVsHuoList();
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
var toolbar = [{
	text:'添加对照项目',
	width:120,
	iconCls:'icon-save',
    handler:function(){
    	 $("#dlg-edit").dialog({
				title : '添加对照项目',
				width : 800,
				height :450,
				href : 'getAddHuoVsHosPage.action'
			});
			$("#dlg-edit").dialog('open');
    }
},
{
	text:'修改对照项目',
	width:120,
	iconCls:'icon-edit',
    handler:function(){
    	var id;
    	var item = $('#hopvshuolistshow').datagrid('getSelected');
    	if(item==null){
    		$.messager.alert("操作提示","请选中要修改的对照项目",'error');
    	}else{
    	id=item.id;
    	 $("#dlg-edit").dialog({
				title : '修改对照项目',
				width : 800,
				height :450,
				href : 'getUpdateHuoVsHosPage.action?id='+id
			});
			$("#dlg-edit").dialog('open');
    	}
    }
},
{
	text:'删除对照项目',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var id;
    	var item = $('#hopvshuolistshow').datagrid('getSelected');
    	if(item==null){
    		$.messager.alert("操作提示","请选中要删除的对照项目",'error');
    	}else{
    	id=item.id;
    	delhuorow(id);
    	}
    }
}
];
