$(document).ready(function () {
	getTemporaryCustomerInfoList();
});

/**
 * 清空查询
 */
function empty(){
	 $('#user_names').val("");
	 getTemporaryCustomerInfoList();
}

/**
 * 显示健康计划表
 */
var lastIndex;

function getTemporaryCustomerInfoList(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 lastIndex=undefined;
	 $("#membershow").datagrid({
		 url:'getTemporaryCustomerInfoList.action',
		 queryParams:{
			 user_name:$('#user_names').val(),
		 },
		 toolbar:toolbar,
		 dataType: 'json',
		 rownumbers:false,
		 fit:true,
	     pageSize:15,
	     pageList:[15,20,30,40,50],//可以设置每页记录条数的列表 
		 columns:[[	
			 {field:'ck',checkbox:true },
			 	{align:'center',field:'id',title:'id',hidden:true},
		        {align:'center',field:'user_name',title:'姓名',editor:'textbox'},
		        {align:'center',field:'id_num',title:'身份证',editor:'textbox',required:true,width:25},
	            {align:'center',field:'sex',title:'性别',editor:{type:'textbox'}},
			 	{align:'center',field:'birthday',title:'出生日期',editor:'textbox'},
			 	{align:'center',width:25,field:'nation_name',title:'民族',editor:{type:'textbox'}},
				{align:'center',field:'phone',width:20,title:'电话',editor:'textbox'},
			 	{align:'center',field:'address',width:30,title:'地址',editor:'textbox'},
			 	{align:'center',field:'email',title:'邮箱',editor:'textbox'},
				{align:'center',field:'flag',title:'标志',hidden:true},
				{align:'center',field:'notices',title:'说明'},
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
		    onClickRow  : function (rowIndex) {
	            if (lastIndex != rowIndex) {
	            	$("#membershow").datagrid('endEdit', lastIndex);  
					$('#membershow').datagrid('beginEdit', rowIndex);
	            }  
	            lastIndex = rowIndex;  
	            $('.datagrid-editable .textbox,.datagrid-editable .datagrid-editable-input,.datagrid-editable .textbox-text').bind('keydown', function(e){
	            	var code = e.keyCode || e.which;
	            	if(code == 13){
	            	    //保存更改 第一次编辑可能不会改变值
	            	    $('#eDatagrid').datagrid('acceptChanges');
	            	    $('#eDatagrid').datagrid('endEdit', lastIndex);
	            	    //do something
	            	    var row = $('#membershow').datagrid('getData').rows[lastIndex];
	                	if(row!=null){
	                		$("#membershow").datagrid('endEdit', lastIndex);  
	                		savrow(row);
	                	}
	            	}
	            	});
        }
	});
}

function deluserrow(id){
			$.messager.confirm('提示信息','是否确定删除选中的会员',function(r){
			 	if(r){
			 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
					 $("body").prepend(str);
					 $.ajax({
						 url : 'deleteTemporaryCustomerInfo.action',
						data : {ids:id},
						type : "post",
						success : function(data) {
								$.messager.alert("操作提示",data);
								$('#dlg-edit').dialog('close');
								getTemporaryCustomerInfoList();
								$(".loading_div").remove();
						},
						error : function() {
							$.messager.alert('提示信息', '操作失败！', 'error');
							$(".loading_div").remove();
						}
					 });
			 	 }
			 });
}

function impuser(ids){
	  if(ids!=null&&ids.length>0){
	 $.messager.confirm('提示信息','是否确定将选择行导入人员库？',function(r){
		 	if(r){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	 $.ajax({
		  
			url : 'impuserToCustomerinfodo.action',
			data : {
		            ids:ids,
					},
					type : "post",//数据发送方式   
					success : function(data) {
						$(".loading_div").remove();
						getTemporaryCustomerInfoList();
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
		  $.messager.alert("操作提示", "请选择需要导入的会员信息");	
	  }
	
}

function impalluser(){
	
	 $.messager.confirm('提示信息','是否确定执行整体数据导入人员库？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 		 $("body").prepend(str);
	 $.ajax({
			url : 'alluserToCustomerinfodo.action',
					type : "post",//数据发送方式   
					success : function(data) {
						$(".loading_div").remove();
												
						getTemporaryCustomerInfoList();
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


function savrow(row){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
			url : 'updateTemporaryCustomerInfo.action',
			data : {
						id : row.id,
						user_name:row.user_name,
						id_num:row.id_num,
						sex:row.sex,
						birthday:row.birthday,
						nation_name:row.nation_name,
						phone:row.phone,
						address:row.address,
						email:row.email
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
							getTemporaryCustomerInfoList();
							$.messager.alert("操作提示", text);
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
}

//----------------------------------定义工具栏---------------------------
var toolbar = [ {
	text:'上传文件',
	width:120,
	iconCls:'icon-add',
    handler:function(){
    	$("#dlg-show").dialog({
			title : '上传文件',
			width : 800,
			height :450,
			href : 'impMemberFile.action'
		});
		$("#dlg-show").dialog('open');
    }
},{
	text:'保存行修改',
	width:100,
	iconCls:'icon-save',
    handler:function(){
    	var row = $('#membershow').datagrid('getData').rows[lastIndex];
    	if(row!=null){
    		$("#membershow").datagrid('endEdit', lastIndex);  
    		savrow(row);
    	}
    }
},{
	text : '删除',
	iconCls : 'icon-cancel',
	width : 120,
	handler :  function() {
    	var id;
    	var checkedItems = $('#membershow').datagrid('getChecked');
    	var ids = "";
    	var strs=[];
	    $.each(checkedItems, function(index, item){
	       strs.push("'"+item.id+"'");
	    }); 	   
	    ids=strs.join(',');
	    deluserrow(ids);
	    
}
},{
	text:'选择项数据入正式库',
	width:180,
	iconCls:'icon-save',
    handler:function(){
    	var checkedItems = $('#membershow').datagrid('getChecked');
    	var ids = "";
    	var strs=[];
	    $.each(checkedItems, function(index, item){
	       strs.push(item.id);
	    }); 	   
	    ids=strs.join(',');
	    impuser(ids);
    }
},{
	text:'整体数据入正式库',
	width:160,
	iconCls:'icon-check',
    handler:function(){
    	var checkedItems = $('#membershow').datagrid('getChecked');
    	impalluser();
    }
},{
	text:'<a href=\"../../crm/crmcardmanager/templateMember.xlsx\">下载模板</a>',
	iconCls:'icon-check',
	handler:function(){
    }
}
];
