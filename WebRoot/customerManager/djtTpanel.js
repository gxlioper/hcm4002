$(document).ready(function () { 
	reopen();
	//gettcGrid(0);
	djtcustChangItemListGrid("");
	gettjxmGrid(99999999);

	yddelChangItemListGrid("");
	getydcusGrid();
	$('#is_Marriage').combobox({
		url : 'getDatadis.action?com_Type=HFLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#is_Marriage').combobox('setValue', data[0].id);
		}
	});
	
	$('#rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB2',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			$('#rylb').combobox('setValue', data[0].id);
		}
	});
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		//editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name'
	});
	
	$('#status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple:true,
		onLoadSuccess : function(data) {
			
		}
	});
	
	getbatchGrid();
	getimpcusGrid();
	getgroupuserGrid();
	gettimeuserGrid();
	countExamInfotimeGrid();
	getpangroupGrid();
	
});

//加载单位信息
function reopen(){
	var url = 'companychangshow.action?remark=Y';
	var data = {"name":$("#com_name").val()};
	load_post(url,data,showcomtree);
}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function showcomtree(data){
			mydtree = new dTree('mydtree','../../images/img/','no','no');
			mydtree.add(0,-1,"单位","javascript:void(0)","根目录","_self",false);
			var l = data.length;
			for(var index = 0;index<l;index++){
				if((data[index].attributes == null)||(data[index].attributes == '')||(data[index].attributes == '0')){
					mydtree.add(data[index].id,
							0,
							data[index].text,
							"javascript:f_getCompanyOne("+data[index].id+",'"+data[index].text+"')",
							data[index].text
							,"_self",false);
				}else{
					mydtree.add(data[index].id,
							data[index].attributes,
							data[index].text,
							"javascript:f_getCompanyOne("+data[index].id+",'"+data[index].text+"')",
							data[index].text,"_self",false);
				}
			}
			$("#depttree").html(mydtree.toString());			
		}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */

	function f_getCompanyOne(id,comname){
		$("#company_id").val(id);
		$("#comname").val(comname);
		$("#batch_id").val('');
		getbatchGrid();	
		f_getdept();
		combatch();	
		//gettcGrid(0);
		djtcustChangItemListGrid("");
		gettjxmGrid(99999999);
		getpangroupGrid();
	}
	
	//方案获取分组
	var barbatchids;
	function combatch() {
		$('#selectbatch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == $("#batch_id").val()) {
						$('#selectbatch_id').combobox('setValue', data[i].id);
						break;
					} else {
						$('#selectbatch_id').combobox('setValue', data[0].id);
					}
				}
				batch_idchange(document.getElementsByName("selectbatch_id")[0].value);
				getgroupuserGrid();
				//加载分组
				getpangroupGrid();
			},
			onChange: function (n,o) {
				$('#selectbatch_id').combobox('setValue', n);
				batch_idchange(n);
				getgroupuserGrid();
			}
		});
		$('#impselectbatch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == $("#batch_id").val()) {
						$('#impselectbatch_id').combobox('setValue', data[i].id);
						break;
					} else {
						$('#impselectbatch_id').combobox('setValue', data[0].id);
					}
				}
				
				//异动管理  删除项目
				getydcusGrid();
			}
		});
		
		
		$('#ydselectbatch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == $("#batch_id").val()) {
						$('#ydselectbatch_id').combobox('setValue', data[i].id);
						break;
					} else {
						$('#ydselectbatch_id').combobox('setValue', data[0].id);
					}
				}
			}
		});
		
		
		$('#timebatch_id').combobox({
			url : 'getCompanForBatch.action?company_id='+$("#company_id").val(),
			editable : false, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'batch_name',
			onLoadSuccess : function(data) {
				for (var i = 0; i < data.length; i++) {
					if (data[i].id == $("#batch_id").val()) {
						$('#timebatch_id').combobox('setValue', data[i].id);
						break;
					} else {
						$('#timebatch_id').combobox('setValue', data[0].id);
					}
				}
				timebatch_idchange(document.getElementsByName("timebatch_id")[0].value);		
			},
			onChange: function (n,o) {
				$('#timebatch_id').combobox('setValue', n);
				timebatch_idchange(n);	
			}		
		});
	}

	//显示部门
	function f_getdept() {
		$('#levelss').combobox({
			url : 'getCompanForDept.action?company_id='+$("#company_id").val(),
			editable : true, //不可编辑状态
			cache : false,
			panelHeight : 'auto',//自动高度适合
			valueField : 'id',
			textField : 'dep_Name',
			multiple : true,
			onLoadSuccess : function(data) {
				//$('#levelss').combobox('setValue', data[0].id);				
			},
			filter: function(q, row){
				var opts = $(this).combobox('options');
				var text = row[opts.textField];//下拉的对应选项的汉字
				var pyjp = pinyinUtil.getFirstLetter(text).toLowerCase();
		 		if(row[opts.textField].indexOf(q) > -1 || pyjp.indexOf(q.toLowerCase()) > -1){
		 			return true;
		 		}	
			}
		});
	}
	
	
//----------------------------------------------方案管理----------------------------------------------------------------------
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
		 columns:[[           
		 	{align:'center',field:'batch_name',title:'体检任务名称',width:200,sortable:true},
		 	{align:'center',field:'exam_number',title:'计划人数',width:50},
		 	{align:'center',field:'exam_count',title:'实际人数',width:50},
		 	{align:'center',field:'creaters',title:'预约人',width:50},		
		 	{align:'center',field:'create_time',title:'预约时间',width:140},	
		 	{align:'center',field:'overflags',title:'结帐状态',width:80},
		 	{align:'center',field:'checktypes',title:'审核状态',width:80},	
		 	{align:'center',field:'checkuname',title:'审核人',width:80},	
		 	{align:'center',field:'checkdate',title:'审核时间',width:140},
		 	{align:"center",field:"xgddd",title:"修改",width:40,"formatter":f_xgbatch},
		 	{align:"center",field:"batchcopy",title:"复制",width:40,"formatter":f_copybatch},
		 	{align:'center',field:'ck',title:'查看',width:40,"formatter":f_showbatch},
		 	{align:"center",field:"sc",title:"删除",width:40,"formatter":f_scbatch},
		 	{align:'center',field:'contact_name',title:'联系人',width:60},
		 	{align:'center',field:'phone',title:'联系电话',width:80},
		 	{align:'center',field:'update_times',title:'修改时间',width:60,sortable:true},
		 	{align:'center',field:'accountflags',title:'锁定状态',width:80},	
		 	{align:'center',field:'accountuname',title:'锁定人',width:80},	
		 	{align:'center',field:'accountdate',title:'锁定时间',width:140},		
		 	{align:'center',field:'data_name',title:'结算方式',width:80,sortable:true}
		 	]],
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$("#fanganlist").datagrid("hideColumn", "contact_name"); // 设置隐藏列
	    		if($("#ischecktype").val()=='0'){
					 $("div.datagrid-toolbar [id ='3']").eq(0).hide();  
					 $("div.datagrid-toolbar [id ='4']").eq(0).hide();  
				}
	    		if($("#isovertype").val()=='0'){
					 $("div.datagrid-toolbar [id ='5']").eq(0).hide();  
					 $("div.datagrid-toolbar [id ='6']").eq(0).hide();  
				}
	    		if($("#isaccounttype").val()=='0'){
	    			$("div.datagrid-toolbar [id ='7']").eq(0).hide();
	    		}
	    		if($("#isunaccounttype").val()=='0'){
	    			$("div.datagrid-toolbar [id ='8']").eq(0).hide();
	    		}
	    		
	    	},onDblClickRow : function(index, row) {
	    		$("#batch_id").val(row.id);
	    		getpangroupGrid();
	    		combatch();
	    		//gettcGrid(0);
	    		gettjxmGrid(99999999);
	    		djtcustChangItemListGrid("");
				gettotalinfo("");
			},	    
			rownumbers : true,
		    singleSelect:false,
	    	fit:false,
		    pagination: false,
		    collapsible:true,
		    fitColumns:false,
			pageSize : 1000,
	        toolbar:toolbarbatch
	 	});
	 	}
	  
	  /**
	   * 定义工具栏
	   */
	  var toolbarbatch=[{
		    id:1,
	 		text:'新增体检任务',
	 		iconCls:'icon-add',
	 	    handler:function(){
	 	    	$("#dlg-edit-batch").dialog({
	 	    		title:'新增体检任务',
	 	    		href:'cusbatchedit.action?id=0&company_id='+$("#company_id").val()
	 	    	});
	 	    	if($("#company_id").val()>0){
	 	    	   $("#dlg-edit-batch").dialog("open");
	 	    	}else{
	 	    	  $.messager.alert('提示信息',"请选择单位名称","error");
	 	    	}
	 	    }
	 	},{
	 		id:2,
			text:'复制到',
			iconCls:'icon-save',
			width:90,
		    handler:function(){
		    	batchcopyto();
		    }
		},{
			id:3,
			text:'审核',
			iconCls:'icon-save',
			width:100,
		    handler:function(){
		    	var ids = "";
		    	var checkedItems = $('#fanganlist').datagrid('getChecked');
	    	    $.each(checkedItems, function(index, item){
	    	        ids=ids+","+(item.id);
	    	    });
	    	    if(ids.split(',').length<=1){
		    		$.messager.alert("操作提示", "请选择要审核的批次","error");
		    	}else if(ids.split(',').length>2){
		    		$.messager.alert("操作提示", "只能同时审核一个批次","error");
		    	}else{    	    	
		    		if(ids.length>1) ids=ids.substring(1,ids.length);
		    	    batchcheckdo(ids);
		    	}
		    }
		},{
			id:4,
			text:'取消审核',
			iconCls:'icon-save',
			width:100,
		    handler:function(){
		    	var ids = "";
		    	var checkedItems = $('#fanganlist').datagrid('getChecked');
	    	    $.each(checkedItems, function(index, item){
	    	        ids=ids+","+(item.id);
	    	    });
	    	    if(ids.split(',').length<=1){
		    		$.messager.alert("操作提示", "请选择要取消审核的批次","error");
		    	}else if(ids.split(',').length>2){
		    		$.messager.alert("操作提示", "只能同时取消审核一个批次","error");
		    	}else{    	    	
		    		if(ids.length>1) ids=ids.substring(1,ids.length);
		    	    batchcheckdoren(ids);
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
* 修改
* @param val
* @param row
* @returns {String}
*/
function f_xgbatch(val,row){	
	return '<a href=\"javascript:f_xguserbatch(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}


/**
 * 显示一条
 * @param val
 * @param row
 * @returns {String}
 */
 function f_showbatch(val,row){	
	  return '<a href=\"javascript:f_batchshow(\''+row.id+'\')\">查看</a>';
 } 
 
 function f_copybatch(val,row){	
	  return '<a href=\"javascript:batchcopyfrom(\''+row.id+'\')\">复制</a>';
 } 
 
 /**
  * 选择目标批次
  */
 function batchcopyfrom(userid){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	 $.ajax({
			url : 'docoptbatchfrom.action',
			data : {
				    batch_id : userid
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
							$.messager.alert("操作提示", text);
														
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
 
 /**
  * 批次复制到单位下
  */
 function batchcopyto(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)){
 		$.messager.alert("操作提示", "请选择单位","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否将批次复制到此单位下？',function(r){
	 if(r){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	 $.ajax({
			url : 'docoptbatchto.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id : document.getElementsByName("impselectbatch_id")[0].value
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							$.messager.alert("操作提示", text);
							getbatchGrid();	
							getpangroupGrid();
							f_getdept();
							combatch();							
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
 }
 
 /**
  * 执行封帐
  *//*
 function batchoverdo(userid) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
				.confirm(
						'提示信息',
						'是否将批次进行封帐？',
						function(r) {
							if (r) {
								var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
										+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
								$("body").prepend(str);
								$.ajax({
									url : 'dooverbatchto.action',
									data : {
										company_id : $("#company_id").val(),
										batch_id : userid
									},
									type : "post",// 数据发送方式
									success : function(text) {
										$(".loading_div").remove();
										if (text.split("-")[0] == 'ok') {
											$.messager.alert("操作提示", text);
											getbatchGrid();
										} else {
											$.messager.alert("操作提示", text,
													"error");
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
 
 *//**
  * 执行解除封帐
  *//*
 function batchoverdoren(userid) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
				.confirm(
						'提示信息',
						'是否将批次解除封帐？',
						function(r) {
							if (r) {
								var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
										+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
								$("body").prepend(str);
								$.ajax({
									url : 'dooverbatchtoren.action',
									data : {
										company_id : $("#company_id").val(),
										batch_id : userid
									},
									type : "post",// 数据发送方式
									success : function(text) {
										$(".loading_div").remove();
										if (text.split("-")[0] == 'ok') {
											$.messager.alert("操作提示", text);
											getbatchGrid();
										} else {
											$.messager.alert("操作提示", text,
													"error");
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
}*/
 /**
  * 执行锁定
  */
 function batchaccountdo(userid) {
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
					 	    		href:'doaccountbatchtoshow.action?company_id='+$("#company_id").val()+'&batch_id='+userid
					 	    	});
					 	    	$("#dlg-custedit-remarke").dialog("open");		
							}
						});
	}
}
 
 /**
  * 执行解除锁定
  */
 function batchaccountdoren(userid) {
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
										batch_id : document.getElementsByName("selectbatch_id")[0].value
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
  * 执行审核
  */
 function batchcheckdo(userid) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
				.confirm(
						'提示信息',
						'是否将批次进行审核？',
						function(r) {
							if (r) {
								var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
										+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
								$("body").prepend(str);
								$.ajax({
									url : 'docheckbatchto.action',
									data : {
										company_id : $("#company_id").val(),
										batch_id : userid
									},
									type : "post",// 数据发送方式
									success : function(text) {
										$(".loading_div").remove();
										if (text.split("-")[0] == 'ok') {
											$.messager.alert("操作提示", text);
											getbatchGrid();
										} else {
											$.messager.alert("操作提示", text,
													"error");
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
  * 执行解除审核
  */
 function batchcheckdoren(userid) {
	if (($("#company_id").val() == '') || (Number($("#company_id").val()) <= 0)) {
		$.messager.alert("操作提示", "请选择单位", "error");
	} else {
		$.messager
				.confirm(
						'提示信息',
						'是否将批次解除审核？',
						function(r) {
							if (r) {
								var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
										+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
								$("body").prepend(str);
								$.ajax({
									url : 'docheckbatchtoren.action',
									data : {
										company_id : $("#company_id").val(),
										batch_id : userid
									},
									type : "post",// 数据发送方式
									success : function(text) {
										$(".loading_div").remove();
										if (text.split("-")[0] == 'ok') {
											$.messager.alert("操作提示", text);
											getbatchGrid();
										} else {
											$.messager.alert("操作提示", text,
													"error");
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
 * 
 * 删除
 * @param val
 * @param row
 * @returns {String}
 */
function f_scbatch(val,row){
	return '<a href=\"javascript:f_deluserbatch(\''+row.id+'\')\">删除</a>';
}


function f_batchshow(userid){
	 	$("#dlg-show-batch").dialog({
	 		title:'单独查询体检任务',
	 		href:'batchoneshow.action?id='+userid+'&company_id='+$("#company_id").val()
	 	});
	 	$("#dlg-show-batch").dialog('open'); 
	 }

function f_xguserbatch(userid){
	$("#dlg-edit-batch").dialog({
		title:'修改体检任务',
		href:'cusbatchedit.action?id='+userid+'&company_id='+$("#company_id").val()
	});
	$("#dlg-edit-batch").dialog('open');
}

function f_deluserbatch(userid)
{
$.messager.confirm('提示信息','是否确定删除此体检任务？',function(r){
	if(r){
		$.ajax({
		url:'batchdelete.action?id='+userid,
		type:'post',
		success:function(text){
			if(text.split("-")[0]=='ok'){
        		  $.messager.alert("操作提示", text);
        		  $("#dlg-edit-batch").dialog("close");
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



//--------------------------------分组管理--------------------------------------------
function getpangroupGrid(){
	 var model={"batch_id":document.getElementsByName("selectbatch_id")[0].value};
    $("#grouplist").datagrid({
	 url:'grouplistshow.action',
	 dataType: 'json',
	 queryParams:model,
	 toolbar:'#toolbar',
	 rownumbers:true,
	 fit: true,
    pageSize: 15,//每页显示的记录条数，默认为10 
    pageList:[15],//可以设置每页记录条数的列表 
	 columns:[[ 
	    {align:'center',field:'status',title:'操作',width:10,"formatter":f_group_status},
	 	{align:'center',field:'group_name',title:'分组名称',width:25,sortable:true},
	 	{align:'center',field:'sex',title:'性别',width:15},
	 	{align:'center',field:'is_Marriage',title:'婚否',width:15},
	 	{align:'center',field:'old_amount',title:'原金额',width:15},
	 	{align:'center',field:'discount',title:'折扣率',width:15},
	 	{align:'center',field:'amount',title:'金额',width:15},
	 	{align:'center',field:'min_age',title:'最小年龄(≥)',width:20},
	 	{align:'center',field:'max_age',title:'最大年龄(<)',width:20},
	 	{align:'center',field:'exam_count',title:'总人数',width:15},
	 	{align:'center',field:'yexam_count',title:'预约人数',width:15},
	 	{align:'center',field:'jexam_count',title:'报到人数',width:15,"formatter":f_jexam_count},
	 	{align:'center',field:'group_index',title:'其他',width:15},	
	 	{align:'center',field:'exam_indicators',title:'结算方式',width:15,sortable:true},
	 	{align:'center',field:'tamt',title:'团体金额',width:15},
	 	{align:'center',field:'gamt',title:'个人金额',width:15},
	 	{align:'center',field:'type_name',title:'人员类型',width:15},
	 	{align:'center',field:'posttion',title:'职位',width:15},
	 	{align:"center",field:"xgddd",title:"修改",width:15,"formatter":f_xggroup},
	 	{align:'center',field:'ck',title:'查看',width:10,"formatter":f_showgroup},
	 	{align:"center",field:"sc",title:"删除",width:10,"formatter":f_scgroup} 	
	 	//{align:"center",field:"isActive","title":"启(停)修改","width":18,"formatter":f_qt}
	 	]],	    	
   	onLoadSuccess:function(value){
   		$("#datatotal").val(value.total);
   		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
		if(is_show_discount==0){
			$("#grouplist").datagrid("hideColumn", "tamt"); // 设置隐藏列
			$("#grouplist").datagrid("hideColumn", "gamt"); // 设置隐藏列
			$("#grouplist").datagrid("hideColumn", "amount"); // 设置隐藏列
			$("#grouplist").datagrid("hideColumn", "discount"); // 设置隐藏列
		}
		if($("#group_status").val() != '1'){
			$("#grouplist").datagrid("hideColumn", "status"); // 设置隐藏列     			
   		}
   	},
   	rowStyler: function(index,row){
		if (row.status == 'D'){
			return 'color:red;';
		}
	},
    singleSelect:true,
    collapsible:true,
	pagination: true,
    fitColumns:true,
    striped:true,
    fit:false,
    remoteSort:false,
    toolbar:toolbargroup,
		onDblClickRow : function(index, row) {
			//gettcGrid(row.id);
			gettjxmGrid(row.id);
			djtcustChangItemListGrid("");
			getGroupTotal(row.id);
			gettjxmGridonegroup(row.id);
			document.getElementById('geren').style.display='none';
			document.getElementById('group').style.display='';
			$("#groupje").html(row.amount);
		}
});
}
function f_jexam_count(val,row){
	return row.exam_count - row.yexam_count;
}

function f_group_status(val,row){
	if(val=="D"){
      return '<a style="color:#f00;" href=\"javascript:update_group_status(\''+row.id+'\',\'Y\')\">解冻</a>';
    }else if(val=='Y') {
       return '<a style="color:#1CC112;" href=\"javascript:update_group_status(\''+row.id+'\',\'D\')\">冻结</a>';
     }
}
function update_group_status(id,status){
	$.ajax({
			url:'updateGroupStatusById.action',
			data:{
				status:status,
				id:id
			},
			success:function(data){
				if (data.split("-")[0] == 'ok') {
					$("#grouplist").datagrid('reload');
					$.messager.alert("警告信息",data,"info");
				} else {
					$.messager.alert("警告信息",data,"error");
				}
				
			},
			error:function(){
				$.messager.alert("警告信息","操作失败","error");
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
	$.messager.confirm('提示信息','是否确认'+html+'该分组项目？',function(r){
		if(r){
		$.ajax({
     	url:'updateDjtPanelStopOrStart.action?ids='+id,
     	type:'post',
     	success:function(data){
			if(data.split("-")[0] == 'ok'){
				$.messager.alert("提示信息",html+"该分组项目成功!");
				$('#grouplist').datagrid('reload');
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

var newWindow;  
var timer; 

/**
 * 定义工具栏
 */
var toolbargroup=[{
		text:'新增分组',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	var url='/groupedit.action?id=0&company_id='+$("#company_id").val()+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value;
            if(document.getElementsByName("selectbatch_id")[0].value >0){
                $.ajax({
                    url : 'getBatchById.action',
                    data : {
                            batch_id : document.getElementsByName("impselectbatch_id")[0].value
                            },
                            type : "post",//数据发送方式
                            success : function(data) {
                              var obj = eval('(' + data + ')');
                              if(obj.checktype == 2){
                                $.messager.alert("操作提示", "体检任务: ("+obj.batch_name+")已审核不能新增分组", "error");
                              }else{
                                newWindow = window.open(url, "新增分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
                                newWindow.focus();
                                timer = setInterval("updateAfterClose()", 2000);
                              }
                            },
                            error : function() {
                                $.messager.alert("操作提示", "操作错误", "error");
                            }
                        });
            }else{
              $.messager.alert('提示信息',"请先选择体检任务","error");
            }
	    }
	},{
		text:'复制分组',
		iconCls:'icon-add',
		width:100,
	    handler:function(){
	    	var url='/copygroupmanager.action?id=0&company_id='+$("#company_id").val()+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value;
	    	if($("#is_batch_check").val() == '1'){
	    		newWindow = window.open(url, "复制分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	    		newWindow.focus();
	    	}else{
		    		if(document.getElementsByName("selectbatch_id")[0].value>0){
		    		$.ajax({
			 			url : 'getBatchById.action',
						data : {
							    batch_id : document.getElementsByName("impselectbatch_id")[0].value
								},
								type : "post",//数据发送方式   
								success : function(data) {
								  var obj = eval('(' + data + ')');
								  if(obj.checktype == 2){
								  	$.messager.alert("操作提示", "体检任务: ("+obj.batch_name+")已审核不能复制分组", "error");	
								  }else{
								  	newWindow = window.open(url, "复制分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
								  	newWindow.focus();
								  }
								},
								error : function() {
									$.messager.alert("操作提示", "操作错误", "error");					
								}
							});
		    		//timer = setInterval("updateAfterClose()", 2000);
		    	}else{
		    	  $.messager.alert('提示信息',"请先选择体检任务","error");
		    	}
	    	}
	    	
	    }
	}, '-', {
        text: '上移', 
        width:60,
        iconCls: 'icon-up',
        handler: function () {
        	var ids = "";
	    	var checkedItems = $('#grouplist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择一行移动","error");
	    	}else if(ids.split(',').length>2){
	    		$.messager.alert("操作提示", "只能选择一行移动","error");
	    	}else{
	    		MoveUp();
	    	}
        }
    }, '-', {
        text: '下移', 
        width:60,
        iconCls: 'icon-down',
        handler: function () {
            var ids = "";
	    	var checkedItems = $('#grouplist').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.id);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择一行移动","error");
	    	}else if(ids.split(',').length>2){
	    		$.messager.alert("操作提示", "只能选择一行移动","error");
	    	}else{
	    		MoveDown();
	    	}
        }
    }];


////////////////////////////////////////////分组移动//////////////////////////////////////////////////////////////////////////
function MoveUp() {
    var row = $("#grouplist").datagrid('getSelected');
    var index = $("#grouplist").datagrid('getRowIndex', row);
    mysort(index, 'up', 'grouplist');
     
}
//下移
function MoveDown() {
    var row = $("#grouplist").datagrid('getSelected');
    var index = $("#grouplist").datagrid('getRowIndex', row);
    mysort(index, 'down', 'grouplist');
     
}
 
 
function mysort(index, type, gridname) {
    if ("up" == type) {
        if (index != 0) {
            var toup = $('#' + gridname).datagrid('getData').rows[index];
            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
            $('#' + gridname).datagrid('getData').rows[index] = todown;
            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index - 1);
            $('#' + gridname).datagrid('selectRow', index - 1);
            saveGroupOrder();
        }
    } else if ("down" == type) {
        var rows = $('#' + gridname).datagrid('getRows').length;
        if (index != rows - 1) {
            var todown = $('#' + gridname).datagrid('getData').rows[index];
            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridname).datagrid('getData').rows[index] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index + 1);
            $('#' + gridname).datagrid('selectRow', index + 1);
            saveGroupOrder();
        }
    }
    
    
}

 function saveGroupOrder(){
	var data = new Array;
    var rows = $('#grouplist').datagrid('getRows');
    for(var i=0;i<rows.length;i++)
     {
    	data.push(rows[i].id);
      }

		$.ajax({
			url : 'saveGroupOrder.action',
			data : {ids:data.toString()},
			type : "post",//数据发送方式   
			success : function(text) {

			},error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
}
/////////////////////////////////////////////分组移动保存///////////////////////////////////////////////////////////////////////

/**
* 修改
* @param val
* @param row
* @returns {String}
*/
function f_xggroup(val,row){	
	if(row.status == 'D'){
		return'';
	}else{
		return '<a href=\"javascript:getBathByGroupid(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
	}
}


/**
* 显示一条方案
* @param val
* @param row
* @returns {String}
*/
function f_showgroup(val,row){	
 return '<a href=\"javascript:f_groupshow(\''+row.id+'\')\">查看</a>';
}

/**
 * 设置每隔2秒钟刷新父节点的表格
 */
function updateAfterClose() {  
    //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
    if(newWindow.closed == true) {  
    clearInterval(timer);  
    getpangroupGrid(); // 主窗口getgroupGrid();刷新  
    return;  
    }  
}  

/**
* 删除
* @param val
* @param row
* @returns {String}
*/
function f_scgroup(val,row){
  if(row.status == 'D'){
		return '';
	}else{
		return '<a href=\"javascript:getBathByGroupid1(\''+row.id+'\')\">删除</a>';
	}
}

function f_groupshow(userid){	 
$("#dlg-groupshow").dialog({
		title:'单独查询分组信息',
		href:'groupOneshow.action?id='+userid+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val()
	});
	$("#dlg-groupshow").dialog('open');
}



function f_xgusergroup(userid){
	var iframeurl='groupedit.action?id='+userid+'&company_id='+$("#company_id").val()+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value;
	newWindow=window.open(iframeurl, "新增分组", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newWindow.focus();
	timer = setInterval("updateAfterClose()", 1000);
}

function f_delusergroup(userid)
{
$.messager.confirm('提示信息','是否确定删除此分组？',function(r){
if(r){
	$.ajax({
	url:'groupdelete.action?id='+userid+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value,
	type:'post',
	success:function(text){
		if(text.split("-")[0]=='ok'){
    		  $.messager.alert("操作提示", text);
    		  $("#dlg-edit-group").dialog("close");
    		  getpangroupGrid();
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


function gettjxmGridonegroup(userid){
	 var model={
			 "company_id":$("#company_id").val(),
			 "batch_id":$("#batch_id").val(),
			 "group_id":userid
	 };
   $("#djtGselectItemlist").datagrid({
	 url:'groupchargitemlistshow.action',
	 dataType: 'json',
	 queryParams:model,
	 rownumbers:false,
    pageSize: 15,//每页显示的记录条数，默认为10 
    pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
    columns : [[ 
      {align : 'center',field : 'item_code',title : '项目编码',width : 20,sortable:true},
      {align : 'center',field : 'item_name',title : '项目名称',width : 25,sortable:true}, 
      {align : 'center',field : 'dep_name',title : '科室',width : 25,sortable:true}, 
      {align : 'center',field : 'item_amount',title : '原金额',width : 20},
      {align : 'center',field : 'discount',title : '折扣率',	width : 20},
      {align : 'center',field : 'itemnum',title : '数量',	width : 20},
      {align : 'center',field : 'amount',title : '套餐金额',width : 20,editor : {type : 'text'}}
    ]],
    onLoadSuccess : function(value) {
		$("#djtGselectItemlist").datagrid("hideColumn", "discount"); // 设置隐藏列   
		$("#djtGselectItemlist").datagrid("hideColumn", "amount"); // 设置隐藏列  
	},
    singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		pageNumber : 1,
		remoteSort:false,
		pageSize : 1000
});
}

//----------------------------------------显示套餐-------------------------------------------------
/**
 * 显示分组套餐信息
 */
function gettcGrid(userid){
		 var model={"group_id":userid};
	     $("#examsetlist").datagrid({
		 url:'groupsetlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'set_num',title:'套餐编码',width:20},
		 	{align:'center',field:'set_name',title:'套餐名称',width:25},
		 	{align:'center',field:'sex',title:'适用性别',width:15},
		 	{align:'center',field:'set_discount',title:'套餐折扣率',width:15},
		 	{align:'center',field:'set_amount',title:'套餐金额',width:15}
		 	]],	    	
		 	singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			pageSize : 1000
	});
	}
//----------------------------------------显示套餐-------------------------------------------------
/**
 * 显示体检项目套餐信息
 */
function gettjxmGrid(userid){
	    /* $("#chargitemlist").datagrid({
		 url:'groupchargitemlistshow.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:false,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
	     columns : [[ 
	       {align : 'center',field : 'item_code',title : '项目编码',width : 20,sortable:true},
	       {align : 'center',field : 'item_name',title : '项目名称',width : 25,sortable:true}, 
	       {align : 'center',field : 'dep_name',title : '科室',width : 25,sortable:true}, 
	       {align : 'center',field : 'item_amount',title : '原金额',width : 20},
	       {align : 'center',field : 'discount',title : '折扣率',	width : 20},
	       {align : 'center',field : 'itemnum',title : '数量',	width : 20},
	       {align : 'center',field : 'amount',title : '套餐金额',width : 20,editor : {type : 'text'}}
	     ]],	    	
	     singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			pageNumber : 1,
			remoteSort:false,
			pageSize : 1000
	});*/
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":document.getElementsByName("selectbatch_id")[0].value,
				 "group_id":userid
		 };
	     $("#chargitemlist").datagrid({
		 url:'getExamInfoUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,60,100,200,500],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'arch_num',title:dahname,width:60,sortable:true},	
		    {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
		 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:30,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
		 	{align:'center',field:'group_name',title:'分组名称',width:150,sortable:true},			 	
		 	{align:'center',field:'register_date',title:'预约日期',width:80,sortable:true},
		 	{align:'center',field:'swuxuzongjian',title:'是否总检',width:80,sortable:true},	
		 	{align:'center',field:'statuss',title:'体检状态',width:100},
		 	{align:'center',field:'dep_name',title:'部门',width:80,sortable:true},
		 	{align:'center',field:'billdep',title:'开票部门',width:200,sortable:true},
		 	{align:'center',field:'others',title:'其他',width:80,sortable:true},		 
		 	{align:'center',field:'position',title:'职务',width:80,sortable:true},		
		 	{align:'center',field:'lis',title:'体检类别',width:80,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:60}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},onDblClickRow : function(index, row) {
	    		gettotalinfo(row.exam_num);
				djtcustChangItemListGrid(row.exam_num);
				document.getElementById('geren').style.display='';
				document.getElementById('group').style.display='none';
			},
			rownumbers : true,
	    	singleSelect:false,
	    	fit:false,
	    	singleSelect : true,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false
	}); 
	}

var djtexam_id; 
function djtcustChangItemListGrid(djtexam_id) {
	var model = {"exam_num" :djtexam_id};
	$("#djtGselectItemlist").datagrid({
		url : 'djtcustchangitemlist.action',
		dataType : 'json',
		queryParams : model,
		toolbar:'#toolbar',
		columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 60},
		             {align : 'left',field : 'item_name',title : '项目名称',	width : 100},
		             {align : 'center',field : 'item_amount',title : '原金额',	width :60},
		             {align : 'center',field : 'calculation_amount',title : '利润',	width : 60},
		             {align : 'center',field : 'team_pay',title : '团体金额',	width : 60},
		             {align : 'center',field : 'personal_pay',title : '个人金额',	width : 60},	
		             {align : 'center',field : 'amount',title : '应收额',	width : 60},
		             {align : 'center',field : 'discount',title : '折扣率',	width : 40},
		             {align : 'center',field : 'itemnum',title : '数量',	width : 50},
		             {align : 'center',field : 'is_new_added',title : '次数',	width : 20},		            
		             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 80}, 		            		             
		             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 80}, 
		             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 80}, 
		             {align : 'center',field : 'is_applications',title : '接口标识',	width : 80},
		             {align : 'center',field : 'his_req_statuss',title : 'HIS接口',	width : 50}
		          ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			$("#djtGselectItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
			$("#djtGselectItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
			$("#djtGselectItemlist").datagrid("hideColumn", "itemnum"); // 设置隐藏列  
			$("#djtGselectItemlist").datagrid("hideColumn", "calculation_amount"); // 设置隐藏列  
			$("#djtGselectItemlist").datagrid("hideColumn", "his_req_statuss"); // 设置隐藏列  
		},
		rowStyler:function(index,row){    
	        if (row.is_new_added>0){    
	            return 'font-weight:bold;';    
	        }    
	    },
	    rownumbers : true,
	    singleSelect:false,
    	fit:false,
	    pagination: false,
	    collapsible:true,
	    fitColumns:false,
		pageSize : 1000	 
	});
}

function gettotalinfo(djtexam_id){
	$.post("djtGItemCount.action", 
			{
				"exam_num":djtexam_id,
				"exam_type":'T'
			}, function(jsonPost) {
				var customertotal = eval(jsonPost);
				document.getElementById("countss").firstChild.nodeValue=customertotal.counts;
				document.getElementById("tyjje").firstChild.nodeValue=customertotal.termAmt;
				document.getElementById("lrje").firstChild.nodeValue=customertotal.calculation_amount;
				document.getElementById("gyjje").firstChild.nodeValue=customertotal.personAmt;
				document.getElementById("gsjje").firstChild.nodeValue=customertotal.personYfAmt;
				document.getElementById("gwjje").firstChild.nodeValue=customertotal.qfAmt;
			}, "json");

}
function  getGroupTotal(group_id){//分组项目个数
	$.post("djtGItemCount.action", 
			{
				"group_id":group_id,
				"exam_type":'T'
			}, function(jsonPost) {
				var customertotal = eval(jsonPost);
				document.getElementById("groupcount").firstChild.nodeValue=customertotal.counts;
			}, "json");
}
//-----------------------------------------------人员处理-------------------------------------------------------------------------
//方案获取分组
var barbatchids;
function batch_idchange(barbatchids) {
	$('#selectgroup_id').combobox({
		url : 'getBatchForGroupTwo.action?batch_id='+barbatchids,
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'group_name',
		onLoadSuccess : function(data) {
			$('#selectgroup_id').combobox('setValue', data[0].id);				
		}
	});
}

function f_status() {
	$('#status').combobox({
		url : 'getDatadis.action?com_Type=EXAMSTATUS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		multiple:true,
		onLoadSuccess : function(data) {
			
		}
	});
}


 //---------------------------------------显示人员------------------------------------------------------
 /**
  * 
  */
 function getgroupuserGrid(){
	 var statuss = $("#status").combobox('getValues');
	 var status = new Array();
	 for(i=0;i<statuss.length;i++){
		 if(statuss[i] != ''){
			 status.push("'"+statuss[i]+"'"); 
		 }
	 }
	 
	 	 //alert($("#levelss").combobox('getValues').toString());
	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":document.getElementsByName("selectbatch_id")[0].value,
				 "group_id":document.getElementsByName("selectgroup_id")[0].value,
				 "dept_ids":$("#levelss").combobox('getValues').toString(),	
				 "status":status.toString(),	
				 "arch_num":$("#arch_num").val(),
				 "id_num":"",
				 "sex":document.getElementsByName("sex")[0].value,
				 "employeeID":"",
				 "birthday":$("#register_date").datebox('getValue'),				 
				 "custname":$("#custname").val(),
				 "position":$("#position").val(),
				 "djdstatuss":document.getElementsByName("djdstatuss")[0].value,
				 "tel":"",
				 "min_age" : $("#min_age").val(),
				 "max_age" : $("#max_age").val(),
				 "billdep":$("#billdep").val(),
				 "others":$("#others").val(),
				 "customer_type_id":document.getElementsByName("rylb")[0].value,
				 "is_marriage":document.getElementsByName("is_Marriage")[0].value,
				 "customer_type":$('#tjlx').combobox('getValue')
		 };
	     $("#groupusershow").datagrid({
		 url:'getExamInfoUserListDeptAll.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
		 columns:[[
		 	{field:'ck',checkbox:true },
            {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
            {align:'center',field:'arch_num',title:dahname,width:70,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:80},
		 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:50,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
		 	{align:'center',field:'group_name',title:'分组名称',width:150,sortable:true},			 	
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'team_pay',title:'团费',width:80},
		 	{align:'center',field:'personal_pay',title:'个费',width:80},
		 	{align:'center',field:'statuss',title:'体检状态',width:80},
		 	{align:'center',field:'join_date',title:'预约日期',width:100,sortable:true},		 	
		 	{align:'center',field:'position',title:'职务',width:100},		 
		 	{align:'center',field:'dep_name',title:'部门',width:150},		
		 	{align:'center',field:'employeeID',title:'工号',width:80},
		 	{align:'center',field:'lis',title:'检验(Y/N)',width:100},
		 	{align:'center',field:'pacs',title:'检查(Y/N)',width:100},		 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:180},
		 	{align:'center',field:'is_upload',title:'是否同步',width:100,"formatter":f_isUpload}
		 	]],	    
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		if($("#ttog").val()=='0'){
					 $("div.datagrid-toolbar [id ='18']").eq(0).hide();  
				}
				if($("#gtot").val()=='0'){
					 $("div.datagrid-toolbar [id ='19']").eq(0).hide();  
				}
	    	},             
	    	singleSelect:false,
	    	fit:false,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false,		    
		    toolbar:toolbarUserMsg
		    
	});
}
 
//是否同步
 function f_isUpload(val,row){
	 return val == 0 ? "否" : "是";
 }
 
 /**
  * 定义工具栏
  */
 var toolbarUserMsg=[{
	 	id:1,
		text:'自动分组',
		iconCls:'icon-save',
		width:90,
	    handler:function(){
            getBatchById(function(da){
                if(da){
                    doAllUserGroupuser();
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		id:2,
		text:'强制分组',
		iconCls:'icon-save',
		width:90,
	    handler:function(){
            getBatchById(function(da){
                if(da){
                    var checkedItems = $('#groupusershow').datagrid('getChecked');
                    var ids = "";
                    $.each(checkedItems, function(index, item){
                        ids=ids+","+(item.exam_num);
                    });
                    doUserGroupuser(ids);
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		id:3,
		text:'新增',
		iconCls:'icon-add',
		width:60,
	    handler:function(){
            getBatchById(function(da){
                if(da){
                    var url ='addcustomer.action?batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val();
                    newWindow = window.open(url, "新增体检人员", "height=800, width=800,toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
                    newWindow.focus();
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });

	    }
	},{
		id:4,
		text:'修改',
		width:60,
		iconCls:'icon-edit',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
    	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}else if(ids.split(',').length>2){
	    		$.messager.alert("操作提示", "只能同时修改一个体检人员信息","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	var url ='editcustomer.action?exam_num='+ids+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val();
			 	newWindow = window.open(url, "修改体检人员信息", "height=800, width=800,toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
			 	newWindow.focus();
	    	}
	    }
	},{
		id:5,
		text:'删除',
		width:60,
		iconCls:'icon-cancel',
	    handler:function(){
	    	getBatchById(function(da){
                if(da){
                	var ids = "";
        	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
            	    $.each(checkedItems, function(index, item){
            	        ids=ids+","+(item.exam_num);
            	    }); 	    	    
            	    deluserrowExam(ids);
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		id:6,
		text:'申请',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    });
	    	doSendApplyChargeItem(ids);
	    }
	},{
		id:7,
		text:'签到',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    }); 	    	 
	    	batchSign(ids);
	    }
	},{
		id:8,
		text:'导检单条码',
		iconCls:'icon-print',
		width:100,
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印导检单条码的体检人员","error");
	    		return;
	    	}
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='1';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());    			
    			setTimeout(function () { 
    				new_print_bar(barids,exam_nums.toString());
			     }, 1000);	
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			//new_reprintdjd4('GB');
    			new_reprintdjd4('G');
    			setTimeout('new_reprintdjd4("B")',1000);
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		id:9,
		text:'导检单',
		width:80,
		iconCls:'icon-print',
	    handler:function(){
	    	var ids = "";
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印导检单的体检人员","error");
	    		return;
	    	}
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(ids.length>1) ids=ids.substring(1,ids.length);
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	var falgs='0';
        	    daoyindan_point(ids.split(","),ids,barids,falgs,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_djd(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			new_reprintdjd4('G');
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		id:10,
		text:'条码',
		width:60,
		iconCls:'icon-print',
	    handler:function(){
	    	var barids="";
	    	var exam_nums = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印条码的体检人员","error");
	    		return;
	    	}
    	    $.each(checkedItems, function(index, item){
    	        barids=barids+"$"+(item.exam_num);
    	        exam_nums.push("'"+item.exam_num+"'");
    	    });
    	    if(barids.length>1) barids=barids.substring(1,barids.length);
    	    if($("#barcode_print_type").val() == '1'){//调用旧打印程序
    	    	printBar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
    			new_print_bar(barids,exam_nums.toString());
    		}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
    			$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
    		}else if($("#barcode_print_type").val() == '4'){//调用4.0打印程序中间表调用模式
    			new_reprintdjd4('B');
    		}else{
    			$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
    		}
	    }
	},{
		id:11,
		text:'<input name=\"isprintdah\" type=\"checkbox\" value=\"1\"/>打印档案号',
		width:100,
		iconCls:'',
		handler:function(){
	    }
	},{
		id:12,
		text:'打印单个条码',
		width:120,
		iconCls:'icon-print',
	    handler:function(){
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
	    	if(checkedItems.length == 0){
	    		$.messager.alert("操作提示", "请选择需要打印条码的体检人员","error");
	    		return;
	    	}
    	    $("#dlg-printitem").dialog({
        		title:'打印单个条码',
        		href:'getAllorintItemPage.action'
        	});
        	$("#dlg-printitem").dialog("open");
	    }
	},{
		id:13,
		text:'加项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	getBatchById(function(da){
                if(da){
                	var ids = "";
        	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
            	    $.each(checkedItems, function(index, item){
            	        ids=ids+","+(item.exam_num);
            	    });
            	    if(ids.split(',').length<=1){
        	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
        	    	}else{
        	    		if(ids.length>1) ids=ids.substring(1,ids.length);
        			 	    if(ids.length>=1){
        			 	    	var url='customeritemaddshow.action?ids='+ids+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val();
        			 	   		newWindow = window.open(url, "人员加项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
        			 	   		newWindow.focus();
        			 	    }else{
        			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
        			 	   	}
        	    	}
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		id:14,
		text:'减项',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	getBatchById(function(da){
                if(da){
                	var ids = "";
        	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
            	    $.each(checkedItems, function(index, item){
            	        ids=ids+","+(item.exam_num);
            	    });
            	    if(ids.split(',').length<=1){
        	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
        	    	}else{
        	    		if(ids.length>1) ids=ids.substring(1,ids.length);
        			 	  if(ids.length>=1){
        			 	    	var url='customeritemdelshow.action?ids='+ids+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val();
        			 	   		newWindow = window.open(url, "人员减项", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
        			 	   		newWindow.focus();
        			 	  }else{
        			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
        			 	   	}
        	    	}
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		id:15,
		text:'导出',
		width:60,
		iconCls:'icon-check',
	    handler:function(){
	    	if(document.getElementsByName("selectbatch_id")[0].value == '0'){
	    		$.messager.alert('提示信息',"请先选择体检任务","error");
	    		return;
	    	}
	    	if($("#company_id").val() == '0'){
	    		$.messager.alert('提示信息',"请先选择单位","error");
	    		return;
	    	}
	    	 var statuss = $("#status").combobox('getValues');
	    	 var status = new Array();
	    	 for(i=0;i<statuss.length;i++){
	    		 if(statuss[i] != ''){
	    			 status.push("'"+statuss[i]+"'"); 
	    		 }
	    	 }
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
			 var deptIds ='';
	    	 if(document.getElementsByName("levelss")[0]!=undefined){
	    		 //deptId = document.getElementsByName("levelss")[0].value;
	    		 deptIds=$("#levelss").combobox('getValues').toString();
	    		// alert($("#levelss").combobox('getValues').toString())
	    	 }
	    	 
	    	 alert_autoClose("操作提示", "正在导出信息，请等待。。。","");
	    	 
	    	 window.location.href='teamUserExportExcel.action?company_id='+$("#company_id").val()+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&group_id='+document.getElementsByName("selectgroup_id")[0].value+
	    	 		'&dept_ids='+deptIds+'&status='+status.toString()+'&arch_num='+$("#arch_num").val()+'&sex='+document.getElementsByName("sex")[0].value+'&birthday='+$("#register_date").datebox('getValue')+
	    	 		'&custname='+$("#custname").val()+'&position='+$("#position").val()+'&billdep='+$("#billdep").val()+'&others='+$("#others").val()+'&customer_type_id='+document.getElementsByName("rylb")[0].value+
	    	 		'&is_marriage='+document.getElementsByName("is_Marriage")[0].value+'&customer_type='+$('#tjlx').combobox('getValue');
	    		 /*var model={
	    				 "company_id":$("#company_id").val(),
	    				 "batch_id":document.getElementsByName("selectbatch_id")[0].value,
	    				 "group_id":document.getElementsByName("selectgroup_id")[0].value,
	    				 "dept_id":deptId,	
	    				 "status":status.toString(),	
	    				 "arch_num":$("#arch_num").val(),
	    				 "id_num":"",
	    				 "sex":document.getElementsByName("sex")[0].value,
	    				 "employeeID":"",
	    				 "birthday":$("#register_date").datebox('getValue'),				 
	    				 "custname":$("#custname").val(),
	    				 "position":$("#position").val(),
	    				 "tel":"",
	    				 "billdep":$("#billdep").val(),
	    				 "others":$("#others").val(),
	    				 "customer_type_id":document.getElementsByName("rylb")[0].value,
	    				 "is_marriage":document.getElementsByName("is_Marriage")[0].value,
	    				 "customer_type":$('#tjlx').combobox('getValue'),
	    				 "page":1,
	    				 "rows":1000,
	    				 "sort":sort,
	    				 "order":order
	    		 };*/
	    }
	},{
		id:16,
		text:'导pdf',
		width:70,
		iconCls:'icon-check',
	    handler:function(){
	    	if($("#batch_id").val() == '0'){
	    		$.messager.alert('提示信息',"请先选择体检任务","error");
	    		return;
	    	}
	    	if($("#company_id").val() == '0'){
	    		$.messager.alert('提示信息',"请先选择单位","error");
	    		return;
	    	}
	    	 var statuss = $("#status").combobox('getValues');
	    	 var status = new Array();
	    	 for(i=0;i<statuss.length;i++){
	    		 if(statuss[i] != ''){
	    			 status.push("'"+statuss[i]+"'"); 
	    		 }
	    	 }
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
						 "company_id":$("#company_id").val(),
						 "batch_id":document.getElementsByName("selectbatch_id")[0].value,
						 "group_id":document.getElementsByName("selectgroup_id")[0].value,
						 "dept_ids":$("#levelss").combobox('getValues').toString(),	
						 "status":status.toString(),	
						 "arch_num":$("#arch_num").val(),
						 "id_num":"",
						 "sex":document.getElementsByName("sex")[0].value,
						 "employeeID":"",
						 "birthday":$("#register_date").datebox('getValue'),				 
						 "custname":$("#custname").val(),
						 "position":$("#position").val(),
						 "djdstatuss":document.getElementsByName("djdstatuss")[0].value,
						 "tel":"",
						 "min_age" : $("#min_age").val(),
						 "max_age" : $("#max_age").val(),
						 "billdep":$("#billdep").val(),
						 "others":$("#others").val(),
						 "customer_type_id":document.getElementsByName("rylb")[0].value,
						 "is_marriage":document.getElementsByName("is_Marriage")[0].value,
						 "customer_type":$('#tjlx').combobox('getValue')
				 };
	    		 $.ajax({
	 				url : 'getExamInfoUserListDeptAll.action',
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
	 			    	for(i=0;i<temp.rows.length;i++){
	 			    		obj = new Object();
	 			    		obj.join_date = temp.rows[i].join_date;
	 			    		obj.dep_name = temp.rows[i].dep_name;
	 			    		obj.user_name = temp.rows[i].user_name;
	 			    		obj.exam_num = temp.rows[i].exam_num;
	 			    		obj.id_num = temp.rows[i].id_num;
	 				    	filelist.push(obj);
	 			    	}
	 			    	$.ajax({
	 						url : 'saveDatagridData.action',
	 						data : {filelist:JSON.stringify(filelist)},
	 						type : "post",//数据发送方式   
	 						success : function(text) {
	 							$(".loading_div").remove();
	 							window.location.href='exportPDF.action';
	 						},
	 						error : function() {
	 							$.messager.alert("操作提示", "导出pdf出错","error");
	 						}
	 					});
	 				},
	 				error : function() {
	 					$.messager.alert("操作提示", "导出pdf出错","error");
	 				}
	 			});
	    }
	},{
		id:17,
		text:'合并样本',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = new Array();
	    	var exam_name = new Array();
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	    	ids.push(item.id);
    	    	if(item.status != 'Y'){
    	    		exam_name.push(item.user_name);
    	    	}
    	    });
    	    if(ids.length == 0){
	    		$.messager.alert("操作提示", "请选择要合并样本的体检人员","error");
	    	}else if(exam_name.length > 0){
	    		$.messager.alert("操作提示", "体检人员"+exam_name.toString()+"不是预约状态,不能合并样本!","error");
	    	}else{
	    		$("#dlg-printitem").dialog({
	        		title:'批量合并样本',
	        		href:'getTeamSampleListPage.action'
	        	});
	        	$("#dlg-printitem").dialog("open");
	        	$("#dlg-printitem").dialog("center");
	    	}
	    }
	},{
		id:18,
		text:'批量同步',
		width:90,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
    	    $.each(checkedItems, function(index, item){
    	        ids=ids+","+(item.exam_num);
    	    }); 	 
    	    if(ids=="" || ids==null){
    	    	$.messager.alert("操作提示", "请先选择某一行", "error");
    	    }else{
    	    	isSynchro(ids);
    	    }
	    }
	},{
		id:20,
		text:'团转个',
		width:80,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
 	    $.each(checkedItems, function(index, item){
 	        ids=ids+","+(item.exam_num);
 	    });
 	   
 	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	    if(ids.length>=1){
			 	    	djtTpanelT2G(ids);
			 	   	}else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
			 	   	}
	    	}
	    }
	},{
		id:20,
		text:'个转团',
		width:80,
		iconCls:'icon-check',
	    handler:function(){
	    	var ids = "";
	    	var checkedItems = $('#groupusershow').datagrid('getChecked');
 	    $.each(checkedItems, function(index, item){
 	        ids=ids+","+(item.exam_num);
 	    });
 	   
 	    if(ids.split(',').length<=1){
	    		$.messager.alert("操作提示", "请选择要修改的体检人员","error");
	    	}else{
	    		if(ids.length>1) ids=ids.substring(1,ids.length);
			 	    if(ids.length>=1){
			 	    	djtTpanelG2T(ids);
			 	   	}else{
			 	   	  $.messager.alert('提示信息',"请先选择体检人员","error");
			 	   	}
	    	}
	    }
	}];
 
 /**
  * 执行自动分组
  */
 function doAllUserGroupuser(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 }else{	 
	 $.messager.confirm('提示信息','是否确定进行自动分组？',function(r){
	 if(r){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
	 $.ajax({
			url : 'doAllUserGroupuser.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:document.getElementsByName("selectbatch_id")[0].value
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
							$.messager.alert("操作提示", text);
														
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
 }
 
 
 /**
  * 强制分组
  */
 function doUserGroupuser(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else if(ids.length<=0){
 		$.messager.alert("操作提示", "请选择体检人员","error");	
 	}else{
 			$("#dlg-custshow").dialog({
		 		title:'选择分组',
		 		href:'doUserGrouplistshow.action?batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val()+'&ids='+ids
		 	});
		 	$("#dlg-custshow").dialog('open'); 			
 		}
 }
 
 /**
  * 登记台团体项目转个人项目
  * @param ids
  */
 function djtTpanelT2G(ids){
 	     $.messager.confirm('提示信息','是否确定要将所选择的体检信息团体项目转个人项目吗？',function(r){
 		 	if(r){
 		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
 				 $("body").prepend(str);
 	     $.ajax({
 			url : 'djtTpanelT2GItem.action',
 			data : {
 				    batch_id:document.getElementsByName("selectbatch_id")[0].value,
 		            ids:ids
 					},
 					type : "post",//数据发送方式   
 					success : function(text) {
 						$(".loading_div").remove();
 						if (text.split("-")[0] == 'ok') {
 							getgroupuserGrid();
 							$.messager.alert("操作提示", text);
// 							edit_kaidanren();
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

 /**
  * 登记台个人项目转团体项目
  * @param ids
  */
 function djtTpanelG2T(ids){
 	     $.messager.confirm('提示信息','是否确定要将所选择的体检信息个人项目转团体项目吗？',function(r){
 		 	if(r){
 		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
 				 $("body").prepend(str);
 	     $.ajax({
 			url : 'djtTpanelG2TItem.action',
 			data : {
 				 batch_id:document.getElementsByName("selectbatch_id")[0].value,
 		         ids:ids
 					},
 					type : "post",//数据发送方式   
 					success : function(text) {
 						$(".loading_div").remove();
 						if (text.split("-")[0] == 'ok') {
 							getgroupuserGrid();
 							$.messager.alert("操作提示", text);
// 							edit_kaidanren();
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
 
 /**
  * 批量删除
  */
 function deluserrowExam(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
			$.messager.alert("操作提示", "请选择单位和体检任务","error");
	}else{	 
	 $.messager.confirm('提示信息','是否确定删除选中用户？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'deluserrow.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:document.getElementsByName("selectbatch_id")[0].value,
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
							$.messager.alert("操作提示", text);
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
 }
 
 function  isSynchro(ids){
		$.messager.confirm('提示信息','是否进行批量同步？',function(r){
			 if(r){
				 $.ajax({
						url : 'isSynchroExamInfo.action',
						data : {ids:ids},
						type : "post",//数据发送方式   
						success : function(text) {
							if (text.split("-")[0] == ' ok') {
								getgroupuserGrid();
								$.messager.alert("操作提示", text);
							} else {
								$.messager.alert("操作提示", text, "error");
							}
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});
			 }
		})
	}


 /**
  * 显示一条
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showexam(val,row){	
	  return '<a href=\"javascript:f_examoneshow(\''+row.exam_num+'\')\">'+row.exam_num+'</a>';
  }
  
  /**
   * 显示
   * @param userid
   */
   function f_examoneshow(userid){	
	//   var url='/customeroneshow.action?id='+userid+'&batch_id='+document.getElementsByName("selectbatch_id")[0].value+'&company_id='+$("#company_id").val();
   //	if(userid>0){
   	//	window.open(url, "查看人员基本信息", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes")
   //	}else{
   //	  $.messager.alert('提示信息',"请先选择体检人员","error");
   //	}
	   window.parent.addPanel_other("体检团体登记","getDjtRegisterTList.action?exam_num="+userid+"","","1");
   }
 
 function printBar(barids,exam_nums){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" * *"; //打印档案号
	 }else{
		 var exeurl =$("#barexeurl").val() +" barcode "+barids+" *";  
	 }
	 $.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			    },
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						$.ajax({
							url : 'updateBarcodePrintStatus.action',
							data : {ids:exam_nums},
							type : "post",//数据发送方式   
							success : function(text) {
								RunExe(exeurl);	
							},
							error : function() {
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
					},
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});	
 }
 
 
 //新版本打印导检单
 function printDJD_exe(barids){
	 if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids+" *"; //打印档案号
	 }else{
		 var exeurl =$("#djdexeurl").val() + "  guid "+barids; 
	 }
	 RunExe(exeurl);
 }
 
 function doURLPDFPrint_ireport(idss){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
		$.ajax({
			url:'getDjDpdf.action',
			data:{
				ids:idss,
				frandom:Math.random()
				},
			type:"POST",
			complete:function(res,status){
				if(status=="success"){
					var resText=res.responseText;
					if(resText=="timeout"){
						alert("连接超时");
						}else{

							var filesurl=resText.split("-")[1];

							 var pdf = document.getElementById("createPDF");
							    if (pdf != undefined && pdf != null) {//判断pdf对象是否存在，如果存在就删除该对象
							        var parentNode = pdf.parentNode;
							        parentNode.removeChild(pdf);
							    }
							    var p = document.createElement("object");
							    p.id = "createPDF";
							    p.classid = "CLSID:CA8A9780-280D-11CF-A24D-444553540000";
							    p.width = 1;
							    p._Version=327680;
							    p._ExtentX=19315;
							    p._ExtentY=16034;
							    p._StockProps=0;
							    p.height = 1;
							    var objects=new Object();
							    objects.src=filesurl;
							    p.object=objects;  
							    document.body.appendChild(p);
							    
							    var pdfReader = document.getElementById("createPDF");  
							     pdfReader.setShowToolbar(false);  
							     pdfReader.setShowScrollbars(false);  
							     pdfReader.src=filesurl;  
							     setTimeout(function () { 
							    	 doprintln();
							     }, 5000);	
						}
				}
			}
		})
	}
 
 function doprintln(){
	 var pdfReader = document.getElementById("createPDF");   
	 pdfReader.gotoFirstPage();
     pdfReader.print();
     $(".loading_div").remove();
 }
 
 
 /**
  * 执行签到
  */
 function batchSign(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','是否进行批量签到？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'batchSigndo.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:document.getElementsByName("selectbatch_id")[0].value,
					    exam_nums:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupuserGrid();
								$.messager.alert("操作提示", text);
															
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
} 
 
 /**
  * 执行批量申请
  */
 function doSendApplyChargeItem(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	 	}else{	 
		 $.messager.confirm('提示信息','执行此操作，将对所选体检人员进行批量申请,请确定先进行人员分组，此操作比较慢，是否执行此操作？',function(r){
		 if(r){
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
		 $.ajax({
				url : 'doSendApplyChargeItem.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:document.getElementsByName("selectbatch_id")[0].value,
					    ids:ids
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getgroupuserGrid();
								$.messager.alert("操作提示", text);
													
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
 }
 
 /**
  * 批量删除
  */
 function deluserrow(ids){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("selectbatch_id")[0].value=='')||(Number(document.getElementsByName("selectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定删除选中用户？',function(r){
		 	if(r){
		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'deluserrow.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:document.getElementsByName("selectbatch_id")[0].value,
		            ids:ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getgroupuserGrid();
							$.messager.alert("操作提示", text);
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
 }
 
 function RunExe(strPath) {
		try {
			var objShell = new ActiveXObject('wscript.shell');
			objShell.Run(strPath);
			objShell = null;
		} catch (e) {
			$.messager.alert("操作提示",'找不到文件"'+strPath+'"(或它的组件之一)。请确定路径和文件名是否正确.', "error");	
		}
	}
 
 /**
  * 设置每隔2秒钟刷新父节点的表格
  */
 function djtupdateAfterClose() {  
     //父窗口去检测子窗口是否关闭，然后通过自我刷新，而不是子窗口去刷新父窗口  
     if(newWindow.closed == true) {
    	 getgroupuserGrid();
         return;  
     }  
}
 
 // 新打印条码
function new_print_bar(barids, exam_nums) {
	if(barids.length<=1){
 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
    }else{
		$.ajax({
			url : 'updateSampleExamEetail.action',
			data : {
				ids : barids
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.ajax({
						url : 'updateBarcodePrintStatus.action',
						data : {
							ids : exam_nums
						},
						type : "post",// 数据发送方式
						success : function(text) {
							var exeurl = '';
							if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&3&1'; //打印档案号
						  	 }else{
						  		 exeurl ='GuidBarServices://&barcode&'+barids+'&&1'; 
						  	 }
							RunReportExe(exeurl);
						},
						error : function() {
							$.messager.alert("操作提示", "操作错误", "error");
						}
					});
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
}
function RunReportExe(strPath) {
	location.href=strPath;
}

//---------------------------------------------------------人员导入管理----------------------------------------------------------------
function setcolorDatagrid(){	
	$('#impusershow').datagrid({   
	    rowStyler:function(index,row){ 
	        if (row.flags==1){
	            return 'color:red;';   
	        }   
	    }   
	});
}

/**
  * 
  */
var lastIndex;
 function getimpcusGrid(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	     lastIndex=undefined;
		 var model={"company_id":$("#company_id").val(),
		 "batch_id":document.getElementsByName("impselectbatch_id")[0].value,
		 "sex":document.getElementsByName("sex1")[0].value,
		 "is_marriage":document.getElementsByName("is_Marriage1")[0].value,
		 "id_num":$("#card_num").val(),
		 "custname":$("#name").val()
		 };
	     $("#impusershow").datagrid({
		 url:'impusershowlist.action',
		 dataType: 'json',
		 remoteSort: false,
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:true,
	     pageSize: 75,//每页显示的记录条数，默认为10 
	     pageList:[75,150,250,500,750,1000,2000],//可以设置每页记录条数的列表 
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'id',title:'编号',width:40},
            {align:'center',field:'sflags',title:'状态',width:80},            
            {align:'center',field:'id_num',title:'证件号',width:150,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'notices',title:'导入错误原因',width:300},
            {align:'center',field:'arch_num',title:dahname,width:50,editor:{type:'text'},sortable:true},	
		 	{align:'center',field:'custname',title:'姓名',width:50,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:50,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:40,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:40,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'birthday',title:'出生日期',width:100,editor:{type:'text'},sortable:true},	
		 	{align:'center',field:'tel',title:'电话',width:100,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'search',title:'定位',width:100,"formatter":f_showimp},
		 	{align:'center',field:'_level',title:'部门',width:250,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'billdep',title:'发票部门',width:100,editor:{type:'text'},sortable:true},	
		 	{align:'center',field:'exam_type',title:'体检类型',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'customer_type',title:'人员类型',width:80,editor:{type:'text'},sortable:true},		 	
		 	{align:'center',field:'position',title:'职务',width:100,editor:{type:'text'},sortable:true},		 	
		 	{align:'center',field:'remark',title:'备注',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'idtypename',title:'证件类型',width:100,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'nation',title:'民族',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'chargingType',title:'费别',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'medical_insurance_card',title:'社保卡号',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'visit_no',title:'客户卡号',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'address',title:'地址',width:200,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'degreeOfedu',title:'文化程度',width:80,editor:{type:'text'},sortable:true},
		 	{align:'center',field:'political_statuss',title:'政治面貌',width:80,editor:{type:'text'},sortable:true},
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		 $("#impusershow").datagrid("hideColumn", "id"); // 设置隐藏列
	    		 //$("#impusershow").datagrid("hideColumn", "employeeID"); // 设置隐藏列
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: true,
		    fitColumns:false,
		    striped:true,
		    fit:false,
	        toolbar:toolbarimpcust,
	        onDblClickRow  : function (rowIndex) {
	            if (lastIndex != rowIndex) {
	            	$("#impusershow").datagrid('endEdit', lastIndex);  
	            	$("#impusershow").datagrid('beginEdit', rowIndex); 
	            }  
	            lastIndex = rowIndex;  
        }
	});
	}
 
 /**
  * 定义工具栏
  */
 var toolbarimpcust=[{
		text:'保存行修改',
		width:100,
		iconCls:'icon-save',
	    handler:function(){
	    	var row = $('#impusershow').datagrid('getData').rows[lastIndex];
	    	if(row!=null){
	    		$("#impusershow").datagrid('endEdit', lastIndex);  
	    		savrowimp(row);
	    	}
	    }
	},{
		text:'删除选择项',
		width:120,
		iconCls:'icon-cancel',
	    handler:function(){
	    	var checkedItems = $('#impusershow').datagrid('getChecked');
	    	    var ids = "";
	    	    $("#impusershow").datagrid('endEdit', lastIndex); 
	    	    $.each(checkedItems, function(index, item){
	    	        ids=ids+","+(item.id);
	    	    }); 	    	    
	    	    delrowimp(ids);
	    }
	},{
		text:'选择项数据入正式库',
		width:180,
		iconCls:'icon-save',
	    handler:function(){
            getBatchById(function(da){
				 if(da){
                     var checkedItems = $('#impusershow').datagrid('getChecked');
                     var ids = "";
                     $("#impusershow").datagrid('endEdit', lastIndex);
                     $.each(checkedItems, function(index, item){
                         ids=ids+","+(item.id);
                     });
                     impuser(ids);
				 }else{
                     $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
				 	 return;
				 }
			});
	    }
	},{
		text:'整体数据入正式库',
		width:160,
		iconCls:'icon-check',
	    handler:function(){
            getBatchById(function(da){
                if(da){
                    var checkedItems = $('#impusershow').datagrid('getChecked');
                    impalluser();
                }else{
                    $.messager.alert("操作提示", "体检任务已经锁定，操作不能继续。","error");
                    return;
                }
            });
	    }
	},{
		text:'错误数据导出',
		width:140,
		iconCls:'icon-save',
	    handler:function(){
	    	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("impselectbatch_id")[0].value=='')||(Number(document.getElementsByName("impselectbatch_id")[0].value)<=0)){
	    	 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
	    	 		return;
	    	 	}else{
	    	 		window.location.href='saveimpData.action?batch_id='+document.getElementsByName("impselectbatch_id")[0].value+'&company_id='+$("#company_id").val();
	    	 	}	    	
	    }
	},{
		text:'下载模板',
		iconCls:'icon-check',
		handler:function(){
			window.location.href = "../../customerManager/template.xlsx";
	    }
	}];
 
 /**
  * 普通人员导入
  */
 function putonguserimp(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("impselectbatch_id")[0].value=='')||(Number(document.getElementsByName("impselectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{
 		
 		$.ajax({
 			url : 'getBatchById.action',
			data : {
				    batch_id : document.getElementsByName("impselectbatch_id")[0].value
					},
					type : "post",//数据发送方式   
					success : function(data) {
					  var obj = eval('(' + data + ')');
					  if(obj != null && obj.accountflag == '1' ){ // 0 正常 1 锁定
                          $.messager.alert("操作提示", "体检任务: ("+obj.batch_name+")已经锁定不能导名单", "error");
                          return;
                      }
					  if(obj.checktype == 2){
						  	$("#dlg-show-imp").dialog({
							 		title:'上传文件',
							 		href:'impuserfile.action?batch_id='+document.getElementsByName("impselectbatch_id")[0].value+'&company_id='+$("#company_id").val()
							 	});
						 	$("#dlg-show-imp").dialog('open');
					  }else{
					  	$.messager.alert("操作提示", "体检任务: ("+obj.batch_name+")未审核不能导名单", "error");	
					  }
					},
					error : function() {
						$.messager.alert("操作提示", "操作错误", "error");					
					}
				});
 		}
   		
 	}
 
 
 /**
  * 事业人员导入
  */
/* function shiyerenuserimp(){
	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("impselectbatch_id")[0].value=='')||(Number(document.getElementsByName("impselectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{
 		$("#dlg-show-imp").dialog({
	 		title:'上传文件',
	 		href:'impuserfile117.action?batch_id='+document.getElementsByName("impselectbatch_id")[0].value+'&company_id='+$("#company_id").val()
	 	});
	 	$("#dlg-show-imp").dialog('open');
 	}
 }*/
 
 /**
  * 军人信息导入
  */
 function junrenuserimp(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		 $.ajax({
				url : 'impjunrenuserToImp.action',
				data : {
					    company_id : $("#company_id").val(),
					    batch_id:document.getElementsByName("impselectbatch_id")[0].value
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text.split("-")[0] == 'ok') {
								getimpcusGrid();
								$.messager.alert("操作提示", text);							
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
 
 /**
  * 选择行整体导入数据库
  */
 function impuser(ids){
     if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("impselectbatch_id")[0].value=='')||(Number(document.getElementsByName("impselectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定将选择行导入人员库？',function(r){
		 	if(r){
		 		// alert($("#companybatch_id").val());
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	 $.ajax({
			url : 'impuserToExaminfodo.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:document.getElementsByName("impselectbatch_id")[0].value,
		            ids:ids,
		            companybatch_id:'0-0'
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getimpcusGrid();
							$.messager.alert("操作提示", text);							
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
 }
 
 /**
  * 整体导入数据库
  */
 function impalluser(){
     if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("impselectbatch_id")[0].value=='')||(Number(document.getElementsByName("impselectbatch_id")[0].value)<=0)){
 		$.messager.alert("操作提示", "请选择单位和体检任务","error");
 	}else{	 
	 $.messager.confirm('提示信息','是否确定执行整体数据导入人员库？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 		 $("body").prepend(str);
	 $.ajax({
			url : 'impuserAllToExaminfodo.action',
			data : {
				    company_id : $("#company_id").val(),
				    batch_id:document.getElementsByName("impselectbatch_id")[0].value,
		            companybatch_id:'0-0'
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {							
							getimpcusGrid();
							$.messager.alert("操作提示", text);							
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
 }
 
 
 document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0]; 
			if(e && e.keyCode==27){ // 按 Esc                 
			//要做的事情           
			}           
			if(e && e.keyCode==113){ // 按 F2                 
			  //要做的事情               
			}                         
			if(e && e.keyCode==13){ // enter 键                
			  //要做的事情
				
				  var row = $('#impusershow').datagrid('getData').rows[lastIndex];
				  if(row!='undefined'){
		    	  if(row!=null){
		    		  $("#impusershow").datagrid('endEdit', lastIndex);  
		    		  savrowimp(row);
		    	  }
			   }
			}        
	};
 
 /**
  * 编辑行，并保存
  * @param row
  */
 function savrowimp(row){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	 $.ajax({
			url : 'saveCustomerTmplist.action',
			data : {
						id : row.id,
						arch_num:row.arch_num,
						id_num:row.id_num,
						sex:row.sex,
						birthday:row.birthday,
						custname:row.custname,
						age:row.age,
						is_marriage:row.is_marriage,
						position:row.position,
						_level:row._level,
						tel:row.tel,
						exam_type:row.exam_type,
						remark:row.remark,
						customer_type:row.customer_type,
						//others:row.others,
						//employeeID:row.employeeID,
						notices:row.noticesrow,
						//visit_no:row.visit_no,
						//introducer:row.introducer,
						billdep:row.billdep,
						idtypename:row.idtypename,
						degreeOfedu:row.degreeOfedu,
						political_statuss:row.political_statuss,
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getimpcusGrid();
							//$.messager.alert("操作提示", text);							
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
 
 
 /**
  * 编辑行，并保存
  * @param row
  */
 function delrowimp(ids){
	 $.messager.confirm('提示信息','是否确定删除选中行数据？',function(r){
		 	if(r){
		 		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
	 $.ajax({
			url : 'delCustomerTmplist.action',
			data : {
						ids : ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == 'ok') {
							getimpcusGrid();
							$.messager.alert("操作提示", text);							
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
  
 
 /**
  * 姓名定位
  * @param val
  * @param row
  * @returns {String}
  */
  function f_showimp(val,row){	
	  return '<a href=\"javascript:f_dingweishow(\''+row.id+'\',\''+row.id_num+'\',\''+row.custname+'\')\">定位</a>';
  }

 var scustname,sid_num,sid;
 function f_dingweishow(sid,sid_num,scustname){ 
	 scustname = encodeURI(encodeURI(scustname));
	 if(lastIndex!=undefined){
	     $("#impusershow").datagrid('endEdit', lastIndex); 
	 }
	 var strurl='impuserusernamedw.action?id='+sid+'&id_num='+sid_num+'&custname='+scustname+'&company_id='+$("#company_id").val()+'&batch_id='+document.getElementsByName("impselectbatch_id")[0].value;
	 $("#dlg-show-imp").dialog({
	 		title:'姓名定位',
	 		href:strurl
	 	});
	 	$("#dlg-show-imp").dialog('open');
 }
 
 //----------------------------------------------人员预约日期设置----------------------------------------------------------------------------
 
 //方案获取分组
 var barbatchids
 function timebatch_idchange(barbatchids) {
 	$('#timegroup_id').combobox({
 		url : 'getBatchForGroupTwo.action?batch_id='+barbatchids,
 		editable : false, //不可编辑状态
 		cache : false,
 		panelHeight : 'auto',//自动高度适合
 		valueField : 'id',
 		textField : 'group_name',
 		onLoadSuccess : function(data) {
 			$('#timegroup_id').combobox('setValue', data[0].id);				
 		}
 	});
 }

 function countExamInfotimeGrid(){	
 	 var model={
 	   "batch_id":document.getElementsByName("timebatch_id")[0].value,
 	   "company_id":$("#company_id").val()
 	 };
     $("#custtimelist").datagrid({
 	 url:'countExamInfotime.action',
 	 dataType: 'json',
 	 queryParams:model,
 	 rownumbers:false,
 	 columns:[[
 	    {align:'center',field:'datetime',title:'日期',width:25},	
 	    {align:'center',field:'times',title:'时间',width:30},
 	    {align:'center',field:'nums',title:'人数',width:10},
 	 	{align:'center',field:'states',title:'说明',width:30},
 	 	{align : "center",field : "timesearch",title : "查询",width : 15,"formatter" : f_timesearch}
 	 	]],	    	
 	 	singleSelect : true,
 		collapsible : true,
 		pagination : true,
 		fitColumns : true,
 		autowidth : true,
 		striped : true,
 		pagination : false,
 		pageNumber : 1,
 		pageSize : 1000,    
 });
 }

 function f_timesearch(val, row) {
 	return '<a href=\"javascript:timesearch(\''+ row.datetime+ '\')\">查询</a>';
 }

 var seartime;
 function timesearch(seartime){
 	$("#impregister_date").datebox('setValue',seartime);
 	gettimeuserGrid();
 }

 /**
   * 
   */
  function gettimeuserGrid(){	
 	     var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
 		 $("body").prepend(str);
 		 var model={
 				 "company_id":$("#company_id").val(),
 				 "batch_id":document.getElementsByName("timebatch_id")[0].value,
 				 "group_id":document.getElementsByName("timegroup_id")[0].value,			 
 				 "arch_num":$("#earch_num").val(),
 				 "id_num":"",
 				 "birthday":$("#timeregister_date").datebox('getValue'),	
 				 "tel":$("#timetel").val(),
 				 "custname":$("#timecustname").val()
 		 };
 	     $("#timegroupusershow").datagrid({
 		 url:'getExamInfoUserList.action',
 		 dataType: 'json',
 		 queryParams:model,
 		 toolbar:'#toolbar',
 		 rownumbers:true,
 	     pageSize: 75,//每页显示的记录条数，默认为10 
 	     pageList:[75,150,300,500,1000],//可以设置每页记录条数的列表 
 		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'arch_num',title:dahname,width:60,sortable:true},	
 		    {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
 		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
 		 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
 		 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
 		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
 		 	{align:'center',field:'age',title:'年龄',width:30,sortable:true},
 		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
 		 	{align:'center',field:'group_name',title:'分组名称',width:150,sortable:true},			 	
 		 	{align:'center',field:'register_date',title:'预约日期',width:80,sortable:true},
 		 	{align:'center',field:'swuxuzongjian',title:'是否总检',width:80,sortable:true},	
 		 	{align:'center',field:'statuss',title:'体检状态',width:100},
 		 	{align:'center',field:'dep_name',title:'部门',width:80,sortable:true},
 		 	{align:'center',field:'billdep',title:'开票部门',width:200,sortable:true},
 		 	{align:'center',field:'others',title:'其他',width:80,sortable:true},		 
 		 	{align:'center',field:'position',title:'职务',width:80,sortable:true},		
 		 	{align:'center',field:'lis',title:'体检类别',width:80,sortable:true},
 		 	{align:'center',field:'customer_type_name',title:'人员类别',width:60}
 		 	]],	    	
 	    	onLoadSuccess:function(value){
 	    		$("#datatotal").val(value.total);
 	    		$(".loading_div").remove();
 	    		countExamInfotimeGrid();
 	    	},
 	    	singleSelect:false,
 		    collapsible:true,
 			pagination: true,
 		    fitColumns:true,
 		    striped:true,
 	        toolbar:toolbartime	       
 	});
 	}
  
  /**
   * 定义工具栏
   */
  var toolbartime=[{
 		text:'设置体检时间',
 		iconCls:'icon-save',
 	    handler:function(){
 	    	var checkedItems = $('#timegroupusershow').datagrid('getChecked');
     	    var ids = "";
     	    $.each(checkedItems, function(index, item){
     	        ids=ids+","+(item.exam_num);
     	    });
 	    	setcusttime(ids);
 	    }
 	},{
 		text:'删除体检时间',
 		iconCls:'icon-add',
 	    handler:function(){
 	    	var checkedItems = $('#timegroupusershow').datagrid('getChecked');
     	    var ids = "";
     	    $.each(checkedItems, function(index, item){
     	        ids=ids+","+(item.exam_num);
     	    });
 	    	 if(ids.length>1){
 	    	       ids=ids.substring(1,ids.length);	
 	    	    }
 	    	 deluserrow(ids);
 	    }
 	}];

  
  /**
   * 批量删除
   */
  function deluserrow(ids){
 	if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("timebatch_id")[0].value=='')||(Number(document.getElementsByName("timebatch_id")[0].value)<=0)){
  		$.messager.alert("操作提示", "请选择单位和体检任务","error");
  	}else if(ids.length<=0){
  		$.messager.alert("操作提示", "请选择体检人员","error");	
  	}else{	 
 	 $.messager.confirm('提示信息','是否确定删除选中人员的体检时间？',function(r){
 		 	if(r){
 		 		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
 				 $("body").prepend(str);
 	 $.ajax({
 			url : 'delcusttimedo.action',
 			data : {
 				    company_id : $("#company_id").val(),
 				    batch_id:document.getElementsByName("timebatch_id")[0].value,
 		            ids:ids
 					},
 					type : "post",//数据发送方式   
 					success : function(text) {
 						$(".loading_div").remove();
 						if (text.split("-")[0] == 'ok') {
 							gettimeuserGrid();
 							$.messager.alert("操作提示", text);
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
  }
  /**
   * 批量设置体检日期
   */
  function setcusttime(ids){
 	 if(($("#company_id").val()=='')||(Number($("#company_id").val())<=0)||(document.getElementsByName("timebatch_id")[0].value=='')||(Number(document.getElementsByName("timebatch_id")[0].value)<=0)){
  		$.messager.alert("操作提示", "请选择单位和体检任务","error");
  	}else if(ids.length<=0){
  		$.messager.alert("操作提示", "请选择体检人员","error");	
  	}else{
  			$("#dlg-custedit-time").dialog({
 		 		title:'设置体检时间段',
 		 		href:'setcusttime.action?batch_id='+document.getElementsByName("timebatch_id")[0].value+'&company_id='+$("#company_id").val()+'&ids='+ids
 		 	});
 		 	$("#dlg-custedit-time").dialog('open'); 			
  		}
  }
  
  
  //------------------------异动管理----------------------------------
  function getydcusGrid(){
	  
	  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":document.getElementsByName("ydselectbatch_id")[0].value
		 };
	     $("#ydexaminfolist").datagrid({
		 url:'getExamInfoYdUserList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,60,100,200,500],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'arch_num',title:dahname,width:60,sortable:true},	
		    {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:100},
		 	{align:'center',field:'user_name',title:'姓名',width:50,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:40,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
		 	{align:'center',field:'chi_name',title:'操作人',width:80},		
		 	{align:'center',field:'update_time',title:'操作时间',width:120},
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'register_date',title:'体检日期',width:80,sortable:true},			 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:100},
		 	{align:'center',field:'position',title:'职务',width:100},		
		 	{align:'center',field:'employeeID',title:'工号',width:50}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		yddelChangItemListGrid("");
	    	},onDblClickRow : function(index, row) {
	    		//alert(row);
				ydcustChangItemListGrid(row.exam_num);
			},rowStyler:function(index,row){		       
		        if (row.is_Actives!='Y'){    
		            return 'color:red;';    
		        }  
		    },
			rownumbers : true,
	    	singleSelect:false,
	    	fit:false,
	    	singleSelect : true,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false
	}); 
	}
  
  
  //------------------------异动管理----------------------------------
  function getydcusitemaddGrid(){
	  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":document.getElementsByName("ydselectbatch_id")[0].value
		 };
	     $("#ydexaminfolist").datagrid({
		 url:'getExamInfoYditemAddList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,60,100,200,500],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'arch_num',title:dahname,width:60,sortable:true},	
		    {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:100},
		 	{align:'center',field:'user_name',title:'姓名',width:50,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:40,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
		 	{align:'center',field:'is_Actives',title:'有效',width:80},		 	
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'register_date',title:'体检日期',width:80,sortable:true},			 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:100},
		 	{align:'center',field:'position',title:'职务',width:100},		
		 	{align:'center',field:'employeeID',title:'工号',width:50}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		yddelChangItemListGrid("");
	    	},onDblClickRow : function(index, row) {
	    		ydaddChangItemListGrid(row.exam_num);
			},rowStyler:function(index,row){		       
		        if (row.is_Actives!='Y'){    
		            return 'color:red;';    
		        }  
		    },
			rownumbers : true,
	    	singleSelect:false,
	    	fit:false,
	    	singleSelect : true,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false
	}); 
	}
  
  //------------------------异动管理----------------------------------
  function getydcusitemdelGrid(){
	  var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 var model={
				 "company_id":$("#company_id").val(),
				 "batch_id":document.getElementsByName("ydselectbatch_id")[0].value
		 };
	     $("#ydexaminfolist").datagrid({
		 url:'getExamInfoYditemdelList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,60,100,200,500],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'arch_num',title:dahname,width:60,sortable:true},	
		    {align:'center',field:'exam_num',title:tjhname,width:100,"formatter":f_showexam,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',width:180,sortable:true},
		 	{align:'center',field:'customer_type_name',title:'人员类别',width:100},
		 	{align:'center',field:'user_name',title:'姓名',width:50,sortable:true},
		 	{align:'center',field:'sex',title:'性别',width:40,sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否',width:50,sortable:true},
		 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
		 	{align:'center',field:'phone',title:'电话',width:100,sortable:true},
		 	{align:'center',field:'is_Actives',title:'有效',width:80},		 	
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'register_date',title:'体检日期',width:80,sortable:true},			 
		 	{align:'center',field:'remark1',title:'导检单/条码/报到',width:100},
		 	{align:'center',field:'position',title:'职务',width:100},		
		 	{align:'center',field:'employeeID',title:'工号',width:50}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		yddelChangItemListGrid("");
	    	},onDblClickRow : function(index, row) {
	    		yddelChangItemListGrid(row.exam_num);
			},rowStyler:function(index,row){		       
		        if (row.is_Actives!='Y'){    
		            return 'color:red;';    
		        }  
		    },
			rownumbers : true,
	    	singleSelect:false,
	    	fit:false,
	    	singleSelect : true,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false
	}); 
	}
  
  function ydcustChangItemListGrid(djtexam_num) {
		var model = {"exam_num" :djtexam_num};
		$("#ydexaminfoItemlist").datagrid({
			url : 'ydcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			toolbar:'#toolbar',
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 60},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 100},
			             {align : 'left',field : 'isActives',title : '有效',	width : 60},
			             {align : 'center',field : 'item_amount',title : '原金额',	width :60},
			             {align : 'center',field : 'calculation_amount',title : '利润',	width : 60},
			             {align : 'center',field : 'team_pay',title : '团体金额',	width : 60},
			             {align : 'center',field : 'personal_pay',title : '个人金额',	width : 60},	
			             {align : 'center',field : 'amount',title : '应收额',	width : 60},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 40},
			             {align : 'center',field : 'itemnum',title : '数量',	width : 50},
			             {align : 'center',field : 'is_new_added',title : '次数',	width : 20},		            
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 80}, 		            		             
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 80}, 
			             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 80}, 
			             {align : 'center',field : 'is_applications',title : '接口标识',	width : 80},
			             {align : 'center',field : 'his_req_statuss',title : 'HIS接口',	width : 50}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#ydexaminfoItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
				$("#ydexaminfoItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "itemnum"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "calculation_amount"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "his_req_statuss"); // 设置隐藏列  
			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }
		        if (row.isActive!='Y'){    
		            return 'color:red;';    
		        }  
		    },
		    singleSelect:false,
	    	fit:false,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false,
			pageSize : 1000	 
		});
	}
  
  function ydaddChangItemListGrid(djtexam_num) {
		var model = {"exam_num" :djtexam_num};
		$("#ydexaminfoItemlist").datagrid({
			url : 'ydcustaddchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			toolbar:'#toolbar',
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 60},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 100},
			             {align : 'left',field : 'chi_name',title : '操作人',	width : 80},
			             {align : 'left',field : 'update_time',title : '操作时间',	width : 120},
			             {align : 'center',field : 'item_amount',title : '原金额',	width :60},
			             {align : 'center',field : 'calculation_amount',title : '利润',	width : 60},
			             {align : 'center',field : 'team_pay',title : '团体金额',	width : 60},
			             {align : 'center',field : 'personal_pay',title : '个人金额',	width : 60},	
			             {align : 'center',field : 'amount',title : '应收额',	width : 60},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 40},
			             {align : 'center',field : 'itemnum',title : '数量',	width : 50},
			             {align : 'center',field : 'is_new_added',title : '次数',	width : 20},		            
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 80}, 		            		             
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 80}, 
			             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 80}, 
			             {align : 'center',field : 'is_applications',title : '接口标识',	width : 80},
			             {align : 'center',field : 'his_req_statuss',title : 'HIS接口',	width : 50}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#ydexaminfoItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
				$("#ydexaminfoItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "itemnum"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "calculation_amount"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "his_req_statuss"); // 设置隐藏列  
			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }
		        if (row.isActive!='Y'){    
		            return 'color:red;';    
		        }  
		    },
		    singleSelect:false,
	    	fit:false,
		    pagination: true,
		    collapsible:true,
		    fitColumns:false,
			pageSize : 1000	 
		});
	}
  
  function yddelChangItemListGrid(djtexam_num) {
		var model = {"exam_num" :djtexam_num};
		$("#ydexaminfoItemlist").datagrid({
			url : 'ydcustdelchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			toolbar:'#toolbar',
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ {align : 'left',field : 'item_code',title : '项目编码',	width : 60},
			             {align : 'left',field : 'item_name',title : '项目名称',	width : 100},
			             {align : 'left',field : 'chi_name',title : '操作人',	width : 80},
			             {align : 'left',field : 'update_time',title : '操作时间',	width : 120},
			             {align : 'center',field : 'item_amount',title : '原金额',	width :60},
			             {align : 'center',field : 'calculation_amount',title : '利润',	width : 60},
			             {align : 'center',field : 'team_pay',title : '团体金额',	width : 60},
			             {align : 'center',field : 'personal_pay',title : '个人金额',	width : 60},	
			             {align : 'center',field : 'amount',title : '应收额',	width : 60},
			             {align : 'center',field : 'discount',title : '折扣率',	width : 40},
			             {align : 'center',field : 'itemnum',title : '数量',	width : 50},
			             {align : 'center',field : 'is_new_added',title : '次数',	width : 20},		            
			             {align : 'center',field : 'pay_statuss',title : '结算状态',	width : 80}, 		            		             
			             {align : 'center',field : 'exam_indicators',title : '付费方式',	width : 80}, 
			             {align : 'center',field : 'exam_statuss',title : '检查状态',	width : 80}, 
			             {align : 'center',field : 'is_applications',title : '接口标识',	width : 80},
			             {align : 'center',field : 'his_req_statuss',title : 'HIS接口',	width : 50}
			          ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				$("#ydexaminfoItemlist").datagrid("hideColumn", "is_new_added"); // 设置隐藏列   
				$("#ydexaminfoItemlist").datagrid("hideColumn", "item_code"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "itemnum"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "calculation_amount"); // 设置隐藏列  
				$("#ydexaminfoItemlist").datagrid("hideColumn", "his_req_statuss"); // 设置隐藏列  

				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#ydexaminfoItemlist").datagrid("hideColumn", "personal_pay"); // 设置隐藏列
					$("#ydexaminfoItemlist").datagrid("hideColumn", "team_pay"); // 设置隐藏列
					$("#ydexaminfoItemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
					$("#ydexaminfoItemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
				}

			},
			rowStyler:function(index,row){    
		        if (row.is_new_added>0){    
		            return 'font-weight:bold;';    
		        }
		        if (row.isActive!='Y'){    
		            return 'color:red;';    
		        }  
		    },
		    singleSelect:false,
	    	fit:false,
		    pagination: false,
		    collapsible:true,
		    fitColumns:false,
			pageSize : 1000	 
		});
	}
  
  
//新补打导检单4.0
  var reprintflag;
  function new_reprintdjd4(reprintflag){
  	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
  	$("body").prepend(str);
  	var data = new Array;
  	var bar_calss = 1;
  	if(($('[name=isprintdah]:checked').val()!=undefined)&&($('[name=isprintdah]:checked').val()=='1')){
  		bar_calss=3;
  	 }
  	
  	var checkedItems = $('#groupusershow').datagrid('getChecked');	
      $.each(checkedItems, function(index, item){
          data.push({
  			exam_num:item.exam_num,
  			print_type:reprintflag,
  			charging_item_codes:'',
  			bar_calss:bar_calss,
  			arch_bar_num:1
  		});
      });		
  		$.ajax({
  			url : 'saveExamPrintTmp.action',
  			data : {examPrintTmpLists:JSON.stringify(data)},
  			type : "post",//数据发送方式   
  			success : function(text) {
  				$(".loading_div").remove();
  				//alert(text);
  				if (text.split("-")[0] == 'ok') {
  					var printno=text.split("-")[1];					
  					var url = 'GuidBarServices://&P&1&'+printno;
  			 		RunServerExe(url);										
  				} else{
  					
  					$.messager.alert("操作提示", text, "error");
  				}				
  			},
  			error : function() {
  				$(".loading_div").remove();
  				$.messager.alert("操作提示", "操作错误", "error");					
  			}
  		});
  	
  }
  
  //新打印导检
  function new_print_djd(barids,exam_nums){
 	 if(barids.length<=1){
 	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
 	 }else{
 		 $.ajax({
 				url : 'updateGuidePrintStatus.action',
 				data : {ids:exam_nums},
 				type : "post",//数据发送方式   
 				success : function(text) {
 					if (text.split("-")[0] == 'ok') {
 						var url = 'GuidBarServices://&guid&'+barids+'&&';
 						RunReportExe(url);											
 					} else {
 						$.messager.alert("操作提示", text, "error");
 					}			
 				},
 				error : function() {
 					$.messager.alert("操作提示", "操作错误", "error");					
 				}
 			});
 	 }
  }
  
  
//打印导检单和条码
  function daoyindan_point(idessss,idss,barids,falgs,exam_nums) {
 	 if(idss.length<=1){
 	 		$.messager.alert("操作提示", "请选择要打印的体检人员","error");
 	 }else{
 		 if($("#hansidjdflag").val()==1){
 			if(idss.split(",").length>10){
 				$.messager.alert("操作提示", "每次打印不能超过10个.","error");
 			}else{
 				$.ajax({
 					url : 'updateGuidePrintStatus.action',
 					data : {ids:exam_nums},
 					type : "post",//数据发送方式   
 					success : function(text) {
 						doURLPDFPrint_ireport(idss);	
 					},
 					error : function() {
 						$.messager.alert("操作提示", "操作错误", "error");					
 					}
 				});
 			}		 
 		 }else{
 			 $.ajax({
 					url : 'updateGuidePrintStatus.action',
 					data : {ids:exam_nums},
 					type : "post",//数据发送方式   
 					success : function(text) {
 						printDJD_exe(barids);//打印导检单	
 					},
 					error : function() {
 						$.messager.alert("操作提示", "操作错误", "error");					
 					}
 				});
 		 }
 		 if(falgs=='1'){
 			 printBar(barids,exam_nums);//打印条码
 		 }
 	 }
  }
  
function getBathByGroupid(id){
	if($("#is_batch_check").val() == '1'){
		f_xgusergroup(id);
	}else{
		$.ajax({
		url : 'getBathByGroupid.action',
 					data : {group_id:id},
 					type : "post",//数据发送方式   
 					success : function(data) {
 						 var obj = eval('(' + data + ')');
 						 if(obj.checktype == 2 ){
 						 	$.messager.alert("操作提示", "任务已审核不能修改!", "error");
 						 }else{
 						 	f_xgusergroup(id);
 						 }
 					},
 					error : function() {
 						$.messager.alert("操作提示", "操作错误", "error");	
 					}
		});
	}
	
}
function getBathByGroupid1(id){
	if($("#is_batch_check").val() == '1'){
		f_delusergroup(id);
	}else{
		$.ajax({
		url : 'getBathByGroupid.action',
 					data : {group_id:id},
 					type : "post",//数据发送方式   
 					success : function(data) {
 						 var obj = eval('(' + data + ')');
 						 if(obj.checktype == 2 ){
 						 	$.messager.alert("操作提示", "任务已审核不能删除!", "error");
 						 }else{
 						 	f_delusergroup(id);
 						 }
 					},
 					error : function() {
 						$.messager.alert("操作提示", "操作错误", "error");	
 					}
		});
	}
	
}

function getBatchById(callback){
    $.ajax({
        url : 'getBatchById.action',
        data : {
            batch_id : document.getElementsByName("impselectbatch_id")[0].value
        },
        type : "post",//数据发送方式
        success : function(data) {
            var obj = eval('(' + data + ')');
            if (obj != null && obj.accountflag == '1') { // 0 正常 1 锁定
                callback(false);
            }else{
                callback(true);
			}
        }
    });
  }
