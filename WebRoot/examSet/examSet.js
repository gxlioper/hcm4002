$(document).ready(function () {
	getgroupuserGrid();
	if($('#app_type').val()==2){
		$('#weiChat').hide();
	}
});

/**
 * 鼠标表格悬停，显示隐藏溢出内容
 */
function formatCellTooltip(value){  
    return "<span title='" + value + "'>" + value + "</span>";  
} 
/**
 * 显示体检套餐
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
		 url:'queryExamSet.action',
		 queryParams:{
			 set_name:$('#set_name').val(),
			 sex:$("input[name='Jsex']:checked").val(),
			 update_time:$('#update_time').datebox('getValue'),//修改开始时间
			 update_times:$('#update_times').datebox('getValue'),//修改结束时间
			 intss:$('#intss').val(),
			 startStop:chk_value  //是否显示停用/启用
		 },
		 toolbar:toolbar,
		 rownumbers:false,
		 height:510,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[
		        {field:'ck',checkbox:true},
		        {align:'center',field:'set_num',title:'编码',width:18},
	            {align:'center',field:'set_name',title:'套餐名称',width:28},
			 	{align:'center',field:'sex',title:'适用性别',width:12},
			 	{align:'center',field:'price',title:'金额',width:18},
			 	{align:'center',field:'set_amount',title:'折后金额',width:18},
			 	{align:'center',field:'set_discount',title:'折扣',width:18},
			 	{align:'center',field:'creater_name',title:'创建人',width:15},
			 	{align:'center',field:'create_time',title:'创建时间',width:20},
			 	{align:'center',field:'update_name',title:'修改人',width:15},
			 	{align:'center',field:'update_time',title:'修改时间',width:20},
			 	{align:'center',field:'isSynchro',title:'是否同步',width:12,"formatter":f_isSynch},
			 	{align:'center',field:'is_Active',title:'启（停）修改',width:10,"formatter":f_qt},
			 	{align:'center',field:'sss',title:'修改',width:10,"formatter":f_xg},
			 	{align:'center',field:'ss',width:10,title:'删除',"formatter":f_sc}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#groupusershow").datagrid("hideColumn", "set_amount"); // 设置隐藏列
					$("#groupusershow").datagrid("hideColumn", "set_discount"); // 设置隐藏列
				}
	    		$(".loading_div").remove();
	    	},
	    	//singleSelect:true,
	    	//checkOnSelect:false,
	    	//selectOnCheck:false,
    	    fitColumns:true,//自适应
	    	//singleSelect:true,
		    //collapsible:false,
			pagination:true,//分页控件
		    striped:true,
		    onDblClickRow:function(index, row){
	        	var row_id = $('#groupusershow').datagrid('getRows')[index].id;
	        	updateSampleDemo(row_id);
	        }
	       
	       // nowrap:false,//内容显示不下换行
	});
}
//是否同步
function f_isSynch(val,row){
	if(val==0){
		return "否";
	}else{
		return "是";
	}
}
 
//-------------------------------------修改体检项目---------------------
 function f_xg(val,row){	
	return '<a href=\"javascript:updateSampleDemo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
/**
 * 修改体检
 * @param id
 */
 function updateSampleDemo(id){
			$("#dlg-custedit").dialog({
		    //left:20,
			//top:0,
			title:'修改体检套餐',
			//width :1200,
			//height:590,
			href:'updateExamSetPage.action?id='+id
		});
		$("#dlg-custedit").dialog('open');
}
//------------------------------------删除体检套餐----------------------------------
function f_sc(val, row) {
	return '<a href=\"javascript:f_deluser(\''+ row.id+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-delete\" alt=\"删除\" /></a>';
}
/**
 * 删除体检套餐方法
 * @param id
 */
function f_deluser(id) {
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){ 
	    	$.ajax({
	    		url : 'deletexamSet.action?ids='+id,
	    		type : 'post',
	    		success : function(data) {
	    			$.messager.alert('提示信息',data);
	    			$('#groupusershow').datagrid('reload');
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
	text : '新增体检套餐',
	iconCls : 'icon-add',
	width : 120,
	handler : function() {
		$("#dlg-custedit").dialog({
			title : '新增体检套餐',
			//left:200,
			//top:0,
			//width :1200,
			//height:590,
			href :'addExamSet.action'
		});
		$("#dlg-custedit").dialog('open');
	}
},{
		text:'批量删除',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var ids = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		        ids.push(item.id);
		    }); 
		    f_deluser(ids.toString());
	    }
},{
	text:'微信同步',
	width:120,
	iconCls:'icon-check',
	id:'weiChat',
    handler:function(){
    	var ids = "";
    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	//alert(checkedItems.);
	    $.each(checkedItems, function(index, item){
	        ids=ids+","+(item.id);
	    });
	        if(ids.split(',').length<=1){
    		$.messager.alert("操作提示", "请选择套餐","error");
    	}else{
    		if(ids.length>1) ids=ids.substring(1,ids.length);
		 	  if(ids.length>=1){
		 		 setweixinadddo(ids);
		 	   	}else{
		 	   	  $.messager.alert('提示信息',"请选择套餐","error");
		 	   	}
    	}
	    
	   
    }
},{
	text:'数据导出',
	width:90,
	iconCls:'icon-check',
	handler:function(){
		/*var chk_value ="";    
		$('input[name = chkItem]:checked').each(function(){    
		   chk_value=chk_value+","+($(this).val());    
		}); 
		if(chk_value.length>1){
			chk_value=chk_value.substring(1,chk_value.length);
		}
		var statuss = new Array();
		var sta = $("#statusss").combobox('getValues');
		for(i=0;i<sta.length;i++){
			statuss.push("'"+sta[i]+"'");
		}*/
		
		var filed1 =$(".datagrid-sort-asc").parent().eq(0).attr('field');
		var filed2 = $(".datagrid-sort-desc").parent().eq(0).attr('field');
		var sort = undefined;order = undefined;
		if(filed1 != undefined){
			sort = filed1;
			order = 'asc';
		}
		if(filed2 != undefined){
			sort = filed2;
			order = 'desc';
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var model={
			 set_name:$('#set_name').val(),						//套餐名称
			 sex:$("input[name='Jsex']:checked").val(),			// 全部 男  女
			 update_time:$('#update_time').datebox('getValue'),//修改开始时间
			 update_times:$('#update_times').datebox('getValue'),//修改结束时间
			"page":1,
			"rows":1000,
			"sort":sort,
			"order":order
			
		};
		$.ajax({
			url : 'queryExamSet.action',
			data : model,
			type : "post",//数据发送方式   
			success : function(data) {
				var temp = eval('('+data+')');
				if(temp.rows.length == 0){
					$(".loading_div").remove();
					$.messager.alert("操作提示", "没有需要导出的人员信息!","error");
					return;
				}
		    	var filelist = new Array();
		    	var obj = new Object();
		    	
		    	obj.set_num = "编码";
		    	obj.set_name = "套餐名称";
		    	obj.sex = "适用性别";
		    	obj.price = "金额";
		    	obj.set_amount = "折后金额";
		    	obj.set_discount = "折扣";
		    	obj.creater_name = "创建人";
		    	obj.create_time = "创建时间";
		    	obj.update_name = "修改人";
		    	obj.update_time = "修改时间";
		    	obj.is_Active="启(停)修改";
		    	obj.sss="修改";
		    	obj.ss="删除";
		    	filelist.push(obj);
		    	
		    	for(i=0;i<temp.rows.length;i++){
		    		obj = new Object();
		    		obj.set_num = temp.rows[i].set_num;
			    	obj.set_name = temp.rows[i].set_name;
			    	obj.sex = temp.rows[i].sex;
			    	obj.price = temp.rows[i].price;
			    	obj.set_amount = temp.rows[i].set_amount;
			    	obj.set_discount = temp.rows[i].set_discount;
			    	obj.creater_name = temp.rows[i].creater_name;
			    	obj.create_time = temp.rows[i].create_time;
			    	obj.update_name = temp.rows[i].update_name;
			    	obj.update_time = temp.rows[i].update_time;
			    	if(temp.rows[i].is_Active=='Y'){
			    		obj.is_Active = '停用'
			    	}else{
			    		obj.is_Active = '启用'
			    	}
			    	obj.sss = temp.rows[i].sss;
			    	obj.ss = temp.rows[i].ss;
			    	filelist.push(obj);
		    	}
		    	$.ajax({
					url : 'saveDatagridData.action',
					data : {filelist:JSON.stringify(filelist)},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						window.location.href='datagridExportExcel.action';
					},
					error : function() {
						$.messager.alert("操作提示", "导出excel出错","error");
					}
				});
			},
			error : function() {
				$.messager.alert("操作提示", "导出excel出错","error");
			}
		});
    }
}];

var set_ids;
function setweixinadddo(set_ids){
	 $.messager.confirm('提示信息','执行此操作，将对所选套餐同步到微信端，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'setweixinadddo.action',
				data : {
					    ids:set_ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								$.messager.alert("操作提示", text);
								getgroupuserGrid();
							} else {
								$.messager.alert("操作提示", text, "error");
							}							
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
	   }
		 });
}



//启停修改
function f_qt(val,row){
var html='';
  if(val=="Y"){
    return '<a style="color:#1CC112;" href=\"javascript:f_startorblock(\''+row.id+'\',\'停用\')\">停用</a>';
  }else if(val=='N') {
     return '<a style="color:#f00;" href=\"javascript:f_startorblock(\''+row.id+'\',\'启用\')\">启用</a>';
   }
}


//启（停）修改
function f_startorblock(id,html){
	$.messager.confirm('提示信息','是否确认'+html+'该套餐？',function(r){
		if(r){
		$.ajax({
   	url:'getstartorblock.action?id='+id,
   	type:'post',
   	success:function(data){
   		var obj=eval("("+data+")");
   		if(obj=='success'){
   			$.messager.alert('提示信息',html+"该套餐成功！");
   			$('#groupusershow').datagrid('reload');
   		}else if(obj=='error'){
   			$.messager.alert('提示信息',html+"该套餐失败！",'error');
   		}else{
   			$.messager.alert('提示信息',obj);
   		}
   	},
   	error:function(){
   		$.messager.alert('提示信息','操作失败！','error');
   			}
			});
		}
	})
}
