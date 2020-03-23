/**
    * EasyUI DataGrid根据字段动态合并单元格
    * param tableID 要合并table的id
    * param colList 要合并的列,用逗号分隔(例如："name,department,office");
    * param mainColIndex 要合并的主列索引
    */
    function mergeCellsByField(tableID, colList, mainColIndex) {
        var ColArray = colList.split(",");
        var tTable = $('#' + tableID);
        var TableRowCnts = tTable.datagrid("getRows").length;
        var tmpA; 
        var tmpB;
        var PerTxt = "";
        var CurTxt = "";
        var alertStr = "";
        for (var i = 0; i <= TableRowCnts ; i++) {
            if (i == TableRowCnts) {
                CurTxt = "";
            }
            else {
                CurTxt = tTable.datagrid("getRows")[i][ColArray[mainColIndex]];
            }
            if (PerTxt == CurTxt) {
                tmpA += 1;
            }
            else {
                tmpB += tmpA;
                for (var j = 0; j < ColArray.length; j++) {
                    tTable.datagrid('mergeCells', {
                        index: i - tmpA,
                        field: ColArray[j],
                        rowspan: tmpA,
                        colspan: null
                    });
                }
                tmpA = 1;
            }
            PerTxt = CurTxt;
        }
    }

$(document).ready(function() {
	setpacsitemlistGrid();
	setlextitemlistGrid();
	});
/**
 * 显示pacsitemlist信息
 */
function setlextitemlistGrid() {
	var model = {
		"exam_num" : $("#exam_num").val()
	};
	$("#lextitemlist").datagrid({
		url : 'djtGlextitemlshow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {field:'ck',checkbox:true }, 
			         {align : 'center',field : 'contrstr',title : '操作',width : 10,"formatter" : f_contrsamp}, 
			         {align : 'center',field : 'demo_name',title : '样本',width : 15}, 
			         {align : 'center',field : 'sample_barcode',title : '条码编号',width : 15},
			         {align : 'center',field : 'item_code',title : '项目编码',width : 15}, 
			         {align : 'center',field : 'item_name',title : '项目名称',width : 25}, 
			         {align : 'center',field : 'statuss',title : '状态',width : 10}
			         ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			mergeCellsByField("lextitemlist", "ck,contrstr,demo_name,sample_barcode",3);
		},
		singleSelect : false,
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

//取消采样
function f_contrsamp(val, row){
		if(row.status=='Y'){
			 return '<a href=\"javascript:contrsamp(\''
				+ row.id
				+ '\')\">取消采样</a>';
		}else{
		    return '';
		}
}

//取消采样
function contrsamp(id){
	var item_ids = new Array();
	var row = $("#lextitemlist").datagrid('getRows');
	for(i=0;i<row.length;i++){
		if(row[i].id == id){
			item_ids.push(row[i].item_code);
		}
	}
	console.log(item_ids);
	 if($("#exam_num").val()!=''){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 $.ajax({
				url : 'djtGdellissamp.action',
				data : {
					exam_id: $("#exam_id").val(),
					exam_num: $("#exam_num").val(),
					id :id,
					ids:item_ids.toString()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						    $(".loading_div").remove();
							if (text.split("-")[0] =='ok') {
								setlextitemlistGrid();
								setpacsitemlistGrid();
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					}); 
	 }else{
		 $.messager.alert("操作提示","无效体检项目", "error");
	 }
}

/**
 * 显示pacsitemlist信息
 */
function setpacsitemlistGrid() {
	var model = {
		"exam_num" : $("#exam_num").val()
	};
	$("#pacsitemlist").datagrid({
		url : 'djtGpacsitemlshow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {field:'ck',checkbox:true }, 
			         {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			         {align : 'center',field : 'item_code',title : '项目编码',width : 15}, 
			         {align : 'center',field : 'item_name',title : '项目名称',width : 15}
			         ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		singleSelect : false,
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

var examids;
//取消采样
function getlisitem_code(ids){
	var itemssscode="";
	 if((ids!=undefined) && (ids.length>0)){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 $.ajax({
				url : 'djtGgetlisitemcode.action',
				data : {
					exam_id: $("#exam_id").val(),
					exam_num: $("#exam_num").val(),
					ids :ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						    $(".loading_div").remove();
							if (text.split("-")[0] =='ok') {
								itemssscode=text.split("-")[1];
								setlextitemlistGrid();
								setpacsitemlistGrid();
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					}); 
	 }
	 return itemssscode;
}


function printBar(){
	var ids = "";
	var lisids="";
	var lextitemlists = $('#lextitemlist').datagrid('getChecked');
    $.each(lextitemlists, function(index, item){
        ids=ids+","+(item.id);
        lisids=lisids+","+(item.id);
    });
    if(ids.split(',').length>1){
		 ids=ids.substring(1,ids.length);
		 lisids=lisids.substring(1,lisids.length);
    }
    
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 $.ajax({
				url : 'djtGgetlisitemcode.action',
				data : {
					exam_id: $("#exam_id").val(),
					exam_num:$('#exam_num').val(),
					ids :ids
					},
					type : "post",//数据发送方式   
					success : function(text) {
						    $(".loading_div").remove();
							if (text.split("-")[0] =='ok') {
								ids=text.split("-")[1];							
								var pacsitemlists = $('#pacsitemlist').datagrid('getChecked');
							    $.each(pacsitemlists, function(index, item){
							        ids=ids+","+(item.item_code);
							    });
							    debugger
							    if(ids.length>0) ids=ids.substring(1,ids.length);
							    if(ids.length>0){
								 if(($('[name=isprintdah_z]:checked').val()!=undefined)&&($('[name=isprintdah_z]:checked').val()=='1')){
									 var exeurl =$("#barexeurl").val() + " barcode "+$("#exam_num").val()+" "+ids+" *"; //打印档案号
								 }else{
									 var exeurl =$("#barexeurl").val() + " barcode "+$("#exam_num").val()+" "+ids; 
								 }
								 $.ajax({
										url : 'updateSampleExamEetaillis.action',
										data : {
											exam_id: $("#exam_id").val(),
											exam_num:$('#exam_num').val(),
											ids : ids
										    },
												type : "post",//数据发送方式   
												success : function(text) {
													$(".loading_div").remove();
													$.ajax({
														url : 'updateBarcodePrintStatus.action',
														data : {ids:"'"+$("#exam_num").val()+"'"},
														type : "post",//数据发送方式   
														success : function(text) {
															RunExe(exeurl);	
															setlextitemlistGrid();
															setpacsitemlistGrid();
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
							    }else{
							    	$.messager.alert("操作提示","无效样本", "error");
							    }
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					}); 
		 

}

function new_printBar(){
	if($("#barcode_print_type").val() == '1'){//调用旧打印程序
		printBar();
	}else if($("#barcode_print_type").val() == '2'){//调用新打印程序
		
		var lisids = new Array();
		var itemcode = new Array();
		
		var lextitemlists = $('#lextitemlist').datagrid('getChecked');
	    $.each(lextitemlists, function(index, item){
	    	//lisids.push(item.id);
	    	lisids.push(item.item.id);
	    	itemcode.push(item.item_code);
	    });
	    debugger
	    var pacsitemlists = $('#pacsitemlist').datagrid('getChecked');
	    $.each(pacsitemlists, function(index, item){
	    	itemcode.push(item.item_code);
	    });		
	    
	    if(itemcode.length == 0){
	    	$.messager.alert("操作提示", "请选择需要打印条码的项目!", "error");
	    	return ;
	    }
	    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url : 'updateSampleExamEetaillis.action',
			data : {
				exam_id: $("#exam_id").val(),
				exam_num:$('#exam_num').val(),
				ids : lisids.toString()
			    },
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						$.ajax({
							url : 'updateBarcodePrintStatus.action',
							data : {ids:"'"+$("#exam_num").val()+"'"},
							type : "post",//数据发送方式   
							success : function(text) {
								if(($('[name=isprintdah_z]:checked').val()!=undefined)&&($('[name=isprintdah_z]:checked').val()=='1')){
									 var exeurl ='GuidBarServices://&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&3&1'; //打印档案号
								 }else{
									 var exeurl ='GuidBarServices://&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&1'; 
								 }
								RunServerExe(exeurl);
								setlextitemlistGrid();
								setpacsitemlistGrid();
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
	}else if($("#barcode_print_type").val() == '3'){//调用4.0打印程序直接调用模式
		$.messager.alert("警告信息","未实现该打印程序调用类型，请选择其他类型配置-BARCODE_PRINT_TYPE","error");
	}else if($("#barcode_print_type").val() == '4' || $("#barcode_print_type").val() == '5'){//调用4.0打印程序中间表调用模式
		new_printBar4();
	}else{
		$.messager.alert("警告信息","未设置打印程序调用类型配置-BARCODE_PRINT_TYPE","error");
	}
}

//条码打印4.0
function new_printBar4(){
//	var lisids = new Array();
//	var itemcode = new Array();
//	var lextitemlists = $('#lextitemlist').datagrid('getChecked');
//    $.each(lextitemlists, function(index, item){
//    	lisids.push(item.id);
//    	itemcode.push(item.item_code);
//    });
//    var pacsitemlists = $('#pacsitemlist').datagrid('getChecked');
//    $.each(pacsitemlists, function(index, item){
//    	itemcode.push(item.item_code);
//    });	
//    
//    var url = '';
//    if(itemcode.length == 0){
//    	if($('[name=isprintdah_z]:checked').val()=='1'){//只打印档案号
//    		url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#exam_num").val()+'&&2&1';
//    	}else{
//    		$.messager.alert("操作提示", "请选择需要打印条码的项目!", "error");
//        	return ;
//    	}
//    }else{
//    	if($('[name=isprintdah_z]:checked').val()=='1'){//打印条码和档案号
//    		url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&3&1';
//    	}else{//打印条码
//    		url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&1&1';
//    	}
//    }
//       RunServerExe(url);
//    }
	
	var lisids = new Array();
	var itemcode = new Array();
	
	var lextitemlists = $('#lextitemlist').datagrid('getChecked');
    $.each(lextitemlists, function(index, item){
    	lisids.push(item.id);
    	itemcode.push(item.item_code);
    });
    var pacsitemlists = $('#pacsitemlist').datagrid('getChecked');
    $.each(pacsitemlists, function(index, item){
        lisids.push(item.charging_id);
    	itemcode.push(item.item_code);
    });		
    
    if(lisids.length == 0){
    	$.messager.alert("操作提示", "请选择需要打印条码的项目!", "error");
    	return ;
    }
    var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	console.log(itemcode);
	$.ajax({
		url : 'updateSampleExamEetaillis.action',
		data : {
			exam_id: $("#exam_id").val(),
			exam_num: $("#exam_num").val(),
			ids : lisids.toString()
		    },
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					$.ajax({
						url : 'updateBarcodePrintStatus.action',
						data : {ids:"'"+$("#exam_num").val()+"'"},
						type : "post",//数据发送方式   
						success : function(text) {
							if($('[name=isprintdah_z]:checked').val()=='1'){//打印条码和档案号
					    		url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&3&1';
					    	}else{//打印条码
					    		url = 'GuidBarServices://&P&0&'+$("#userid").val()+'&barcode&'+$("#exam_num").val()+'&'+itemcode.toString()+'&1&1';
					    	}
							RunServerExe(url);
							setlextitemlistGrid();
							setpacsitemlistGrid();
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


function RunServerExe(url){
	location.href=url;
}