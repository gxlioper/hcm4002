$(document).ready(function () {
	$('#data_type_name').combobox({
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
	    prompt:'请选择项目类别',
	    onLoadSuccess:function(){
	    	if($('#data_type_code').val()!=''){
	    		var type=$('#data_type_code').val();
	    		$('#data_type_name').combobox('setValue',type);
	    	}
    	},
	    onSelect:function(rec){
	    	if(rec.id=='1'){
	    		getItemProject('getHuoLisProject.action');
	    	}else if(rec.id=='2'){
	    		getItemProject('getHuoPacsProject.action');
	    	}else if(rec.id=='3'){
	    		getItemProject('getHuoCommonProject.action');
	    	}
	    }	    
 });
});
function empty(){
	$('#hop_data_codeaddq').val('');
	$('#hop_data_nameaddq').val('');
	$('#item_pinyinaddq').val('');
}
function getItemProjectCheck(){
	var type=$('#data_type_name').combobox('getValue');
	if(type=='1'){
		getItemProject('getHuoLisProject.action');
	}else if(type=='2'){
		getItemProject('getHuoPacsProject.action');
	}else if(type=='3'){
		getItemProject('getHuoCommonProject.action');
	}else if(type==''){
		alert('请选择项目类型');
	}
}
function getItemProject(urls){
	 $("#huovshosshow").datagrid({
		 url:urls,
		 queryParams:{
			 hos_data_code:$('#hop_data_codeaddq').val(),
			 hos_data_name:$('#hop_data_nameaddq').val(),
			 item_pinyin:$('#item_pinyinaddq').val()
		 },
//		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {align:'center',field:'huo_data_code',title:'项目编号',width:20},
			 {align:'center',field:'huo_data_name',title:'项目名称',width:20},
			 {align:'center',field:'item_pinyin',title:'项目拼音',width:20},
		 	]],	   
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow:function(index,data){
	    		$('#huo_data_codeadd').text(data.huo_data_code);
	    		$('#huo_data_nameadd').text(data.huo_data_name);
	    	},
	    	singleSelect:true,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    striped:true
	});
}

function addHuoVsHos(){
	if($('#hos_data_nameadd').val()==''){
		$.messager.alert('提示信息','健康项目名称不能为空','error');
		return;
	}
	if($('#hos_piny').val()==''){
		$.messager.alert('提示信息','健康项目拼音不能为空','error');
		return;
	}
	if($('#data_type_name').combobox('getValue')==''){
		$.messager.alert('提示信息','请选择项目类型','error');
		return;
	}
	if($('#huo_data_codeadd').text()==''){
		$.messager.alert('提示信息','请选择项目','error');
		return;
	}
	$.messager.confirm('提示信息','请确认所填项目拼音为文档中项目，且均为小写字母',function(r){
		if(r){
			$.ajax({
				url : 'saveHuoVsHos.action',
				type : 'post',
				data : {
					"id":$('#idaddq').val(),
					"hos_data_name" : $('#hos_data_nameadd').val(),
					"hos_piny" : $('#hos_piny').val().toLowerCase(),
					"data_type" : $("#data_type_name").combobox('getValue'),
					"huo_data_code" : $('#huo_data_codeadd').text(),
					"huo_data_name" : $('#huo_data_nameadd').text()
				},
				success : function(data) {
					$.messager.alert('提示信息',data);
					$('#dlg-edit').dialog('close');
					getHopVsHuoList()
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
