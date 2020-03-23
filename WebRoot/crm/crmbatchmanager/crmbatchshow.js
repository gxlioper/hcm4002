$(document).ready(function () { 
	$('#tijiantype').combobox({
		 data:[{
		 		id:'1',value:'健康体检'
		 	},{
		 		id:'2',value:'职业病体检'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    prompt:'请选择体检方式',
		   // multiple:true,
		    panelHeight:'auto',
	})
	
	$('#qiandan').combobox({
		valueField: 'sign_num',
        textField: 'sign_name',
        hasDownArrow:true,
        height:24,
        width:240,
        mode:'remote',
        url:'getSignBillPlanByName.action',
        onSelect:function(){
        	getbatchGrid();
        }
	 })
	if($('#batch_sign_nums').val()!=''){
		$('#qiandan').combobox('setValue',$('#batch_sign_nums').val());
	}
	getbatchGrid();
});

 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getbatchGrid(){
		 var model={sign_num:$('#qiandan').combobox('getValue'),apptype:$('#tijiantype').combobox('getValue')};
	     $("#fanganlist").datagrid({
		 url:'crmbatchlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {align:"center",field:"sc",title:"删除",width:15,"formatter":f_sc},
            {align:'center',field:'id',title:'体检任务编码',width:25,hidden:true},
            {align:'center',field:'sign_num',title:'签单计划编码',width:25,hidden:true},
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:25},
		 	{align:'center',field:'data_name',title:'结算方式',width:15},
		 	{align:'center',field:'exam_number',title:'预计人数',width:15},
		 	{align:'center',field:'exam_fee',title:'预算金额',width:15},
		 	{align:'center',field:'contact_name',title:'联系人',width:15},
		 	{align:'center',field:'phone',title:'联系电话',width:15},
		 	{align:'center',field:'update_times',title:'修改时间',width:15},
		 	{align:'center',field:'is_Actives',title:'提交审核状态',width:15},	
		 	{align:'center',field:'cwcheckstatuss',title:'财务状态',width:15},	
		 	{align:'center',field:'yscheckstatuss',title:'医生状态',width:15},	
		 	{align:'center',field:'sjcheckstatuss',title:'上级部门状态',width:25},	
		 	{align:'center',field:'apptypes',title:'体检类型',width:25},
		 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xg},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show},
		 	{align:"center",field:"fxfz",title:"进行分组",width:15,"formatter":f_fz},
		 	{align:"center",field:"ryjh",title:"人员计划",width:15,"formatter":f_ryjh}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true,
	    toolbar:toolbar
	});
	}
 function tijiaoshenhe(id,sign_num,apptype){
	 
	 $.ajax({
 		url : 'tijiaoBatch.action',
 		type : 'post',
 		data : {
 			"id" : id,
 			"sign_num":sign_num,
 			"apptype":apptype
 		},
 		success : function(data) {
 			if(data.indexOf('失败')==-1){
 				$.messager.alert('提示信息',data);
 			}else{
 				$.messager.alert('提示信息',data,"error");
 			}
 			getbatchGrid();
 		},
 		error : function() {
 			$.messager.alert('提示信息', '操作失败！', 'error');
 		}
 	});
 }
 function quxiaoshenhe(id){
	 $.ajax({
 		url : 'quxiaoBatch.action',
 		type : 'post',
 		data : {
 			"id" : id
 		},
 		success : function(data) {
 			if(data.indexOf('失败')==-1){
 				$.messager.alert('提示信息',data);	
 				
 			}else{
 				$.messager.alert('提示信息',data,'error');	
 			}
 			getbatchGrid();
 		},
 		error : function() {
 			$.messager.alert('提示信息', '操作失败！', 'error');
 		}
 	});
 }
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'新增体检任务',
		iconCls:'icon-add',
	    handler:function(){
	    	$("#dlg-edit").dialog({
	    		title:'新增体检任务',
	    		href:'crmbatchedit.action?id=0&sign_num='+$('#qiandan').combobox('getValue')
	    	});
		    		if($("#qiandan").combobox('getValue')!=null&&$("#qiandan").combobox('getValue').length>0){
		    			 $("#dlg-edit").dialog("open");
		    		}else{
		    			  $.messager.alert('提示信息',"请选择签单计划","error");
		    		}
	    }
	},{
		text:'复制体检任务',
		iconCls:'icon-edit',
	    handler:function(){
	    	$('#dlg-edit').dialog({    
			    title: '选择已存在的单位', 
			    href: 'crmbatcheditpage.action?sign_num='+$('#qiandan').combobox('getValue'),
			    width: 900,    
			    height: 400, 
			    closed: true,
			    cache: false,
			    modal:true
			});
	    	if($("#qiandan").combobox('getValue')!=null&&$("#qiandan").combobox('getValue').length>0){
	    		$('#dlg-edit').dialog('open');
   		}else{
   			  $.messager.alert('提示信息',"请选择签单计划","error");
   		}
	    }
	},{
		text:'提交体检审核',
		iconCls:'icon-add',
	    handler:function(){
	    	var row=$('#fanganlist').datagrid('getSelected');
	    	if(row==null){
				$.messager.alert("操作提示","请选择体检任务",'error');
			}else{
				$.messager.confirm('提示信息','确定将体检任务'+row.batch_name+'提交审核',function(r){
					if(r){
						tijiaoshenhe(row.id,row.sign_num,row.apptype);
					}
				})
			}
	    }
	},{
		text:'撤销体检审核',
		iconCls:'icon-cancel',
	    handler:function(){
	    	var row=$('#fanganlist').datagrid('getSelected');
	    	if(row==null){
				$.messager.alert("操作提示","请选择体检任务",'error');
			}else{
				$.messager.confirm('提示信息','确定将体检任务'+row.batch_name+'撤销审核',function(r){
					if(r){
						quxiaoshenhe(row.id);
					}
				})
			}
	    }
	},{
	    text:'体检任务跟踪',
	    iconCls:'icon-edit',
	    handler:function(){
	    	var item = $('#fanganlist').datagrid('getSelected');
		if(item==null){
			$.messager.alert("操作提示","请选中要跟踪的签单计划");
		}else{
		id =item.id;
		$('#dlg-show').dialog({   
		    title: '签单计划跟踪', 
		    href: 'getBatchPlanLogListPage.action?ids='+id+'&type=2',
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
 
/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){
		 return '<a href=\"javascript:f_xguser(\''+row.id+'\',\''+row.is_Actives+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
 	
 }
 
 
 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_show(val,row){	
	  return '<a href=\"javascript:f_batchshow(\''+row.id+'\')\">查看</a>';
  }
  
  
  /**
   * 进行分组
   * @param val
   * @param row
   * @returns {String}
   */
   function f_fz(val,row){	
		   return '<a href=\"javascript:f_fzctro(\''+row.id+'\',\''+row.is_Actives+'\')\">体检任务分组</a>';

   }

   
   function f_ryjh(val,row){	
		   return '<a href=\"javascript:f_ryjhctro(\''+row.id+'\',\''+row.is_Actives+'\')\">人员计划</a>';
	   }
 /**
  * 
  * 删除
  * @param val
  * @param row
  * @returns {String}
  */
 function f_sc(val,row){
 	return '<a href=\"javascript:f_deluser(\''+row.id+'\',\''+row.is_Actives+'\')\">删除</a>';
 }
 
 
 function f_batchshow(userid){
	 	$("#dlg-show").dialog({
	 		title:'单独查询体检任务',
	 		href:'crmbatchoneshow.action?id='+userid+'&sign_num='+$('#qiandan').combobox('getValue')
	 	});
	 	$("#dlg-show").dialog('open');
	 }
 
function f_fzctro(userid,is_Actives){
	   if(is_Actives=='未提交审核'){
			var reqUrl2 = 'crmgroupmanager.action?sign_num='+$('#qiandan').combobox('getValue')+'&batch_id='+userid;
			window.parent.showOnetab(2,reqUrl2);
		 }else{
			 $.messager.alert("操作提示","该体检任务"+is_Actives+"，无法进行任务分组","error");
		 }
}


function f_ryjhctro(userid,is_Actives){
	  if(is_Actives=='未提交审核'){
		  var reqUrl3 = 'crmbatchproshow.action?sign_num='+$('#qiandan').combobox('getValue')+'&batch_id='+userid;
			window.parent.showOnetab(3,reqUrl3);
		 }else{
			 $.messager.alert("操作提示","该体检任务"+is_Actives+"，无法进行人员计划","error");
		 }
}

 function f_xguser(userid,is_Actives){
	 if(is_Actives=='未提交审核'){
		 	$("#dlg-edit").dialog({
		 		title:'修改体检任务',
		 		href:'crmbatchedit.action?id='+userid+'&sign_num='+$('#qiandan').combobox('getValue')
		 	});
		 	$("#dlg-edit").dialog('open');
	 }else{
		 $.messager.alert("操作提示","该体检任务"+is_Actives+"，无法修改","error");
	 }
 }

 function f_deluser(userid,is_Actives)
 {
	 if(is_Actives=='未提交审核'){
		 $.messager.confirm('提示信息','是否确定删除此体检任务？',function(r){
			 	if(r){
			 		$.ajax({
			 		url:'batchdelete.action?id='+userid,
			 		type:'post',
			 		success:function(text){
			 			if(text.split("-")[0]=='ok'){
			         		  $.messager.alert("操作提示", text);
			         		  $("#dlg-edit").dialog("close");
			         		  getbatchGrid();
			         	  }else if(text.split("-")[0]=='error'){
			         		  $.messager.alert("操作提示", text,"error");
			         	  }
			 		},
			 		error:function(){
			 			$.messager.alert('提示信息','操作失败！','error');
			 		}
			 		});
			 	}
			 });
			 }else{
				 $.messager.alert("操作提示","该体检任务"+is_Actives+"，无法进行删除","error");
			 }

 }