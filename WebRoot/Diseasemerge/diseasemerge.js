$(function(){
	$('#disease_name').textbox('textbox').focus();
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			diseasemerge();
		}
	}
	diseasemerge();
})
 //-------------------------------显示 合并疾病------------------------------------
/**
 * 显示合并疾病列表
 */
 function diseasemerge(){	
	     $("#diseasemergetable").datagrid({
		 url:'querytMergePage.action',
		 queryParams:{
			 disease_name:$('#disease_name').textbox('getValue')
		 },
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	{field:'ck',checkbox:true },
	          //  {align:'center',field:'disease_num',title:'疾病编码',width:10},	
	            {align:'left',field:'disease_name',title:'疾病名称',width:20},	
	            {align:'left',field:'name',title:'合并疾病名称',width:30},	
			    {align:'center',field:'creater_name',title:'创建人',width:10},
			 	{align:'center',field:'create_time',title:'创建时间',width:10},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',title:'删除',width:10,"formatter":f_sc},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:true,
		    fit:true,
		    striped:true,
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#diseasemergetable').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	});
}
function f_color(value,row,index){
		var color=row.demo_color;
		var s = color.substring(1,color.length);
		return 'background:#'+s+';';
}

//-------------------------------------修改检验样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:update_disese(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function update_disese(id){
			$("#dlg-custedit").dialog({
			title:'修改合并疾病',
			width :600,
			height:600,
			center:'center',
			href:'updateMergePage.action?id='+id
		});
}
//-------------------------------------新增检验样本&&修改检验样本----------------------------
 

/**
 * 添加报告样本
 * 
 */
function addSampleDemo() {
	if($('#demo_num').val()==''||/[\u0391-\uFFE5]/g.test(document.getElementById('demo_num').value)){
		$('#demo_num').focus();
		return;
	}
	if ($('#ssnum').text()!='') {
		return;
	}
	if($('#adddemo_name').val()==''){
		$('#adddemo_name').focus();
		return;
	}
    if(!/^[0-9]{1,20}$/.test(document.getElementById('print_seq').value)&&
	   document.getElementById('print_seq').value!=''){
    	$('#print_seq').focus();
    	return;
    }
	if($('#print_copy').val()==''||!/^[0-9]{1,20}$/.test(document.getElementById('print_copy').value)){
		$('#print_copy').focus();
		return;
	}
	$.ajax({
		url : 'addSampleDemoMethod.action',
		type : 'post',
		data : {
			 id : $('#demo_id').val(),
			demo_num : $('#demo_num').val(),
			demo_name: $('#adddemo_name').val(),
			demo_category : $('#demo_category').combobox('getValue'),
			demo_indicator: $('#demo_indicator').val(),
			demo_color:$('#demo_color').val().toString(),
			print_seq: $('#print_seq').val(),
			print_copy: $('#print_copy').val(),
			remark: $('#remark').val(),
			isPrint_BarCode:$("input[name=isPrint_BarCode]:checked").val(),
			barCode:$('#barCode').val()
		},
		success : function(data) {
			$.messager.alert('添加成功',data);
			$('#dlg-custedit').dialog('close')
			getgroupuserGrid();
		},
		error : function() {
			$.messager.alert('提示信息', '操作失败！', 'error');
		}
	});
}
//------------------------------------删除合并疾病----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除合并疾病
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '是否删除本条记录？', function(r) {
		if (r) {
			$.ajax({
				url : 'deleteMerge.action?ids='+ id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					$('#diseasemergetable').datagrid("reload");
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 批量删除检验样本
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的记录!')
		return;
	}
	$.messager.confirm('提示信息','是否删除选中记录',function(r){
	 	if(r){
			 $.ajax({
				 url : 'deleteMerge.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						$('#diseasemergetable').datagrid("reload");
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			 });
	 	 }
	 });
}
//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text : '新增合并疾病',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增合并疾病',
			width :600,
			height:600,
			center:'center',
			href : 'addMergePage.action'
		});
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#diseasemergetable').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
}];
