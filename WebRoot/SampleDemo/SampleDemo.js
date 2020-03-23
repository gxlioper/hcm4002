$(document).ready(function () {
	//getgroupuserGrid();
	yblx();
	dep_shu();
});


/**
 * 科室树
 */
function dep_shu(zhi){
	
	$("#some_tree").tree({
		
		 url:"getDatadis.action?com_Type="+"YBFL",
		 /*queryParams:{
			 web_Resource:$('#web_Resource').val()
		 },*/
		 type:'post',
		 dataType:'json',
		 loadFilter :function(data,parent){
			 if(zhi!=""&&zhi!=undefined){
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].name});
				 }
				return newData;
			 }else{
				 var obj = data;
				 var newData = new Array();
				 for(var i=0;i<obj.length;i++){
					 newData.push({id:obj[i].id,text:obj[i].name});
				 }
				 var newDate2 = [{id:-1,text:'所有类别',children:newData}];
				 return newDate2;
			 }
		 },
		 onLoadSuccess:function(node,data){  
	           $("#some_tree li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	           var n = $("#some_tree").tree("getSelected");  
	           if(n!=null){  
	                $("#some_tree").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	           }  
        },
        onSelect:function(node){
        	var obj =  $(this).tree('getChildren',node.target);
        	//getgroupuserGrid(node.id);
        	$("#demo_type").val(node.id);
        	getgroupuserGrid();
        }
	});
}

/**
 * 清空查询
 */
function empty(){
	$('#num').textbox('setValue',"");
	$('#demo_name').textbox('setValue',"");
	getgroupuserGrid();
}
function yblx(){
	$('#yblx').combobox({
		url:"getDatadis.action?com_Type="+"YBLX",
	    valueField:'id',
	    textField:'name'
	})
}
 //-------------------------------显示检验样本------------------------------------
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
		 url:'querySampleDemo.action',
		 queryParams:{
			 "demo_num":$('#num').val(),
			 "demo_name":$('#demo_name').val(),
			 "demo_category":$('#yblx').combobox('getValue'),//样本类型
			 "demo_type":$("#demo_type").val(),
			 "startStop":chk_value,  //是否显示停用/启用
		 },
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize:15,//
	     pageList:[15,30,50,60,80],//可以设置每页记录条数的列表 
		 columns:[[
	           	{field:'ck',checkbox:true },
	            {align:'center',field:'demo_num',title:'样本编码',width:15,'formatter':guanxi},	
			    {align:'center',field:'demo_name',title:'样本名称',width:15,},
			 	{align:'center',field:'dataname',title:'标本类型',width:15},
			 	{align:'center',field:'demo_indicator',title:'样本标志',width:15},
			 	{align:'center',field:'fd',title:'样本颜色',width:15,"styler":f_color},
			 	{align:'center',field:'print_seq',title:'打印顺序',width:15},
			 	{align:'center',field:'print_copy',title:'打印份数',width:15},
			 	{align:'center',field:'isPrint_BarCodes',title:'是否打印条码',width:15},
			 	{align:'center',field:'barCode_Classs',title:'条码类型',width:15},
			 	{align:'center',field:'remark',title:'备注',width:15},
			 	{align:'center',field:'creatername',title:'记录创建者',width:15},
			 	{align:'center',field:'create_time',title:'创建时间',width:20},
			 	{align:'center',field:'updataname',title:'记录更新者',width:15},
			 	{align:'center',field:'update_time',title:'记录更新时间',width:20},
			 	{align:'center',field:'sss',title:'修改',width:15,"formatter":f_xg},
			 	//{align:'center',field:'ss',title:'删除',width:15,"formatter":f_sc},
			 	{align:"center",field:"isActive","title":"启(停)修改","width":25,"formatter":f_qt}
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
	        	updateSampleDemo(row_id);
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
      	url:'updateYanSampleStopOrStart.action?ids='+id,
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
 
function f_color(value,row,index){
		var color=row.demo_color;
		var s = color.substring(1,color.length);
		return 'background:#'+s+';';
}
//------------------关系----------------
function guanxi(value,row,index){
	return '<a href=\"javascript:guanxi_s(\''+row.id+'\')\">'+row.demo_num+'</a>';
}
function guanxi_s(id){
	$("#dlg_show").dialog({
		title:'样本项目关系',
		href:'getSampleDemoChargingItemPage.action?id='+id
	});
	$("#dlg_show").dialog('open');
}
function f_color(value,row,index){
		var color=row.demo_color;
		var s = color.substring(1,color.length);
		return 'background:#'+s+';';
}
//-------------------------------------修改检验样本页面---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改报告样本页面
 * 
 */
 function updateSampleDemo(id){
			$("#dlg-custedit").dialog({
			title:'修改检验样本',
			width : 880,
			height: 420,
			href:'updateSampleDemo.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
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
    if(!/^[0-9]{1,20}$/.test(document.getElementById('print_seq').value)&&document.getElementById('print_seq').value!=''){
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
			barCode:$('#barCode').val(),
			print_dep:$('#print_dep').combobox('getValue'),
			demo_type : $('#demo_type2').combobox('getValue'), //所属类别
			isPrint_req:$('input:radio[name="isPrint_req"]:checked').val(),
			req_print_num:$('#req_print_num').val()
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
//------------------------------------删除检验样本----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除检验样本提示信息
 * 
 */
function f_deluser(id) {
	$.messager.confirm('提示信息', '是否确定删除检验样本？', function(r) {
		if (r) {
			$.ajax({
				url : 'deleteSampleDemo.action?ids='+ id,
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
 * 批量删除检验样本
 * 
 */
function deluserrow(ids){
	if(ids==""){
		$.messager.alert('提示信息','请选择要删除的检验样本!')
		return;
	}
	$.messager.confirm('提示信息','是否确定删除选中检验样本',function(r){
	 	if(r){
	 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			 $.ajax({
				 url : 'deleteSampleDemo.action',
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
	text : '新增检验样本',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
	    debugger
        var n = $("#some_tree").tree("getSelected");
        $("#dlg-custedit").dialog({
			title : '新增检验样本',
			width : 880,
			height: 420,
			href : 'addSampleDemo.action?demo_type='+n.id
		});
		$("#dlg-custedit").dialog('open');
	}
},{
	text : '批量修改分类',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		
		var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	if(checkedItems==""){
    		$.messager.alert("提示信息","请先选择样本！","error");
    		return;
    	}
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
		
		$("#dlg_demo_type").dialog({
			title : '批量修改分类',
			width : 600,
			height: 400,
			href : 'addDemoTypePage.action?ids='+ids.toString()
		});
		$("#dlg_demo_type").dialog('open');
	}
}/*, {
	text:'批量删除',
	width:100,
	iconCls:'icon-cancel',
    handler:function(){
    	var ids = new Array();
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    }); 
	    deluserrow(ids.toString());
    }
}*/];
