$(document).ready(function () {
	$('#com_Name').textbox('textbox').bind('click', function() {  
		select_com_list(this.value);
    }); 
	
	$('#com_Name').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
    });
	
	$('#com_Name').textbox('textbox').bind('blur', function() {  
		select_com_list_over();
    });	
	getbatchGrid();
});

 //-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
 var hc_com_list,com_Namess;
 function select_com_list(com_Namess){
 	var url='companychangshow.action';
 	var data={"name":com_Namess};
 	load_post(url,data,select_com_list_sess);
 	}
 
 /**
  * 显示树形结构
  * @param data
  * @returns
  */
 function select_com_list_sess(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			for(var index = 0,l = data.length;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:setvalue("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#com_name_list_div").html(mydtree.toString());
			$("#com_name_list_div").css("display","block");
			
		}
 
 /**
  * 点击树设置内容
  * @param id
  * @param name
  * @returns
  */
	function setvalue(id,name){
		$("#companyname").html(id+"-"+name);
		$("#company_id").val(id);
		$("#com_name_list_div").css("display","none");
		getbatchGrid();	
	}
 
//单位失去焦点
 var hc_mous_select1=false;
 function select_com_list_over(){
 	if(!hc_mous_select1){
 	$("#com_name_list_div").css("display","none");
 	}
 	}

 function select_com_list_mover(){
 	hc_mous_select1=true;
 	}
 function select_com_list_amover(){
 	hc_mous_select1=false;
 	}

 //---------------------------------------显示方案------------------------------------------------------
 /**
  * 
  */
 function getbatchGrid(){
		 var model={"company_id":$("#company_id").val()};
	     $("#fanganlist").datagrid({
		 url:'batchlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
            {align:"center",field:"sc",title:"删除",width:15,"formatter":f_sc},
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:25},
		 	{align:'center',field:'data_name',title:'结算方式',width:15},
		 	{align:'center',field:'exam_number',title:'预计人数',width:15},
		 	{align:'center',field:'exam_fee',title:'预算金额',width:15},
		 	{align:'center',field:'contact_name',title:'联系人',width:15},
		 	{align:'center',field:'phone',title:'联系电话',width:15},
			 {align:'center',field:'accountflags',title:'锁定状态',width:10},
			 {align:'center',field:'update_times',title:'修改时间',width:15},
		 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xg},
		 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_show}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
				if($("#isaccounttype").val()=='0'){
					$("div.datagrid-toolbar [id ='7']").eq(0).hide();
				}
				if($("#isunaccounttype").val()=='0'){
					$("div.datagrid-toolbar [id ='8']").eq(0).hide();
				}
	    	},
	    singleSelect:true,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true,
	    toolbar:toolbar
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
	    		href:'zybbatchedit.action?id=0&company_id='+$("#company_id").val()
	    	});
	    	if($("#company_id").val()>0){
	    	   $("#dlg-edit").dialog("open");
	    	}else{
	    	  $.messager.alert('提示信息',"请选择单位名称","error");
	    	}
	    }
	},{
	 id:7,
	 text:'锁定',
	 iconCls:'icon-save',
	 width:100,
	 handler:function(){
		 var ids = "";
		 var checkedItems = $('#fanganlist').datagrid('getChecked');
		 $.each(checkedItems, function(index, item){
			 ids=ids+","+(item.id);
		 });
		 if(ids.split(',').length<=1){
			 $.messager.alert("操作提示", "请选择要修改的批次","error");
		 }else if(ids.split(',').length>2){
			 $.messager.alert("操作提示", "只能同时修改一个批次","error");
		 }else{
			 if(ids.length>1) ids=ids.substring(1,ids.length);
			 batchaccountdo(ids);
		 }
	 }
 },{
	 id:8,
	 text:'解锁',
	 iconCls:'icon-save',
	 width:100,
	 handler:function(){
		 var ids = "";
		 var accountFlag = "";
		 var checkedItems = $('#fanganlist').datagrid('getChecked');
		 $.each(checkedItems, function(index, item){
			 ids=ids+","+(item.id);
			 accountFlag=accountFlag+","+(item.accountflag);
		 });
		 if(ids.split(',').length<=1){
			 $.messager.alert("操作提示", "请选择要修改的批次","error");
		 }else if(ids.split(',').length>2){
			 $.messager.alert("操作提示", "只能同时修改一个批次","error");
		 }else{
			 if(Number(accountFlag.substring(1,accountFlag.length))==0){
				 $.messager.alert("操作提示", "该批次未锁定","error");
			 }else{
				 if(ids.length>1) ids=ids.substring(1,ids.length);
				 batchaccountdoren(ids);
			 }
		 }
	 }
 }];

/**
 * 执行锁定
 */
function batchaccountdo(batch_id) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
			.confirm(
				'提示信息',
				'是否将批次进行锁定？',
				function(r) {
					if (r) {
						$("#dlg-custedit-remarke").dialog({
							title:'选择锁定日期',
							href:'doaccountbatchtoshow.action?company_id='+$("#company_id").val()+'&batch_id='+batch_id
						});
						$("#dlg-custedit-remarke").dialog("open");
					}
				});
	}
}

/**
 * 执行解除锁定
 */
function batchaccountdoren(batch_id) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
			.confirm(
				'提示信息',
				'是否将批次解除锁定？',
				function(r) {
					if (r) {
						var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
							+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
						$("body").prepend(str);
						//alert(document.getElementsByName("selectbatch_id")[0].value+"==="+userid);
						$.ajax({
							url : 'doaccountbatchtoren.action',
							data : {
								company_id : $("#company_id").val(),
								batch_id : batch_id
							},
							type : "post",// 数据发送方式
							success : function(text) {
								$(".loading_div").remove();
								if (text.split("-")[0] == 'ok') {
									$.messager.alert("操作提示", text);
									getbatchGrid();
								} else {
									$.messager.alert("操作提示", text,"error");
								}

							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误",
									"error");
							}
						});
					}
				});
	}
}

/**
 * 修改
 * @param val
 * @param row
 * @returns {String}
 */
 function f_xg(val,row){	
 	return '<a href=\"javascript:f_xguser(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
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
  * 
  * 删除
  * @param val
  * @param row
  * @returns {String}
  */
 function f_sc(val,row){
 	return '<a href=\"javascript:f_deluser(\''+row.id+'\')\">删除</a>';
 }
 
 
 function f_batchshow(userid){
	 	$("#dlg-show").dialog({
	 		title:'单独查询体检任务',
	 		href:'batchoneshow.action?id='+userid+'&company_id='+$("#company_id").val()
	 	});
	 	$("#dlg-show").dialog('open');
	 }
 
 function f_xguser(userid){
 	$("#dlg-edit").dialog({
 		title:'修改体检任务',
 		href:'zybbatchedit.action?id='+userid+'&company_id='+$("#company_id").val()
 	});
 	$("#dlg-edit").dialog('open');
 }

 function f_deluser(userid)
 {
 $.messager.confirm('提示信息','是否确定删除此体检任务？',function(r){
 	if(r){
 		$.ajax({
 		url:'zybbatchdelete.action?id='+userid,
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
 }