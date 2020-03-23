$(document).ready(function () {
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
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 
		 var chk_value ="";    
		 $('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		 }); 
		 if(chk_value.length>1){
			  chk_value=chk_value.substring(1,chk_value.length);
		 }
		 
	     $("#groupusershow").datagrid({
		 url:'SampleReportlist.action',
		 queryParams:{
			 "demo_num":$('#num').val(),
			 "demo_name":$('#demo_name').val(),
			 "startStop":chk_value,  //是否显示停用/启用
		 },
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
		    {align:'center',field:'demo_num',title:'样本编码',width:15},	
		    {align:'center',field:'demo_name',title:'样本名称',width:15,},
		 	{align:'center',field:'remark',title:'备注',width:15},
		 	{align:'center',field:'print_seq',title:'打印顺序',width:15,},
		 	{align:'center',field:'chi_name',title:'记录创建者',width:15},
		 	{align:'center',field:'create_time',title:'创建时间',width:15},
		 	{align:'center',field:'updatename',title:'记录更新者',width:15},
		 	{align:'center',field:'update_time',title:'记录更新时间',width:15},
		 	{align:'center',field:'sss',title:'修改',width:15,"formatter":f_xg},
		 	//{align:'center',field:'ss',title:'删除',width:15,"formatter":f_sc},
		 	{align:"center",field:"is_Active","title":"启(停)修改","width":25,"formatter":f_qt}
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
	        toolbar:toolbar,
	        onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updataSampleReportDemoPage(row_id);
	        }
	});
}
 
 
//启停修改
 function f_qt(val,row){
  var html='';
     if(val=="N"){
       return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
     }else if(val=='Y') {
        return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
      }
 }


 //启（停）修改
 function f_startorblock(id,html){
 	$.messager.confirm('提示信息','是否确认'+html+'该样本？',function(r){
 		if(r){
 		$.ajax({
      	url:'updateSampleStopOrStart.action?ids='+id,
      	type:'post',
      	success:function(data){
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("提示信息",html+"该样本成功!");
				$('#groupusershow').datagrid('reload');
			} else {
				$.messager.alert("提示信息",data.split("-")[1],"error");
			}
      	},
      	error:function(){
      		$.messager.alert('提示信息','操作失败！','error');
      			}
 			});
 		}
 	})
 }


//-------------------------------------修改报告样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updataSampleReportDemoPage(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function updataSampleReportDemoPage(id){
			$("#dlg-custedit").dialog({
			title:'修改报告样本',
			width : 600,
			height : 320,
			href:'updataSampleReportDemoPage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}
//-------------------------------------新增报告样本&&修改报告样本----------------------------

/**
 * 验证报告编号是否可用
 */
function addnum() {
	$('#addnum').validatebox({
		required : true,
	});
	$.ajax({
		url : 'getSampleReportDemoBynum.action?demo_num=' + $("#addnum").val(),
		type : 'post',
		success : function(data) {
			if (data=='no') {
				$('#ssnum').text("编号已存在!")
				return false;
			} else if (data=='ok') {
				$('#ssnum').text("")
				return true;
			}
		}
	});
}
/**
 * 添加报告样本
 * 
 */
function addSampleReportDemo() {
	if($('#addnum').val()==''||/[\u0391-\uFFE5]/g.test(document.getElementById('addnum').value)){
		$('#addnum').focus();
		return;
	}
	
	if ($('#ssnum').text()!='') {
		return;
	}
	if($('#addname').val()==''){
		$('#addname').focus();
		return;
	}

	if(!/^[0-9]{1,20}$/.test(document.getElementById('addseq').value)&&
		document.getElementById('addseq').value!=''){
		$('#addseq').focus();
		return;
	}
	$.ajax({
		url : 'addSampleReportDemo.action',
		type : 'post',
		//dataType: "json", 
		data : {
			"id" : $('#addid').val(),
			"demo_name" : $('#addname').val(),
			"demo_num" : $('#addnum').val(),
			"print_seq" : $('#addseq').val(),
			"remark" : $('#addremark').val(),
			"isPrint_BarCode":$("input[name='isPrint_BarCode']:checked").val(),
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
//------------------------------------删除报告样本----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除报告样本提示信息
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '是否确定删除报告样本？', function(r) {
		if (r) {
			$.ajax({
				url : 'SampleReportDelet.action?id='+ id,
				type : 'post',
				success : function(data) {
					$.messager.alert('提示信息', data);
					getgroupuserGrid();
				},
				error : function() {
					$.messager.alert('提示信息', '操作失败！', 'error');
				}
			});
		}
	})
}
/**
 * 批量删除报告样本
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的报告样本!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除选中报告样本',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteSampleReportDemo.action',
				data : {ids:ids},
				type : "post",
				success : function(data) {
						$.messager.alert("操作提示",data);
						getgroupuserGrid();
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
	text : '新增报告样本',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增报告样本',
			width : 600,
			height : 320,
			href : 'addSampleReportDelet.action'
		});
		$("#dlg-custedit").dialog('open');
	}
}, {
	text:'批量删除',
	width:120,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
} ];
