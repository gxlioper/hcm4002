var deltiemflags=0;
var delsetflags=0;
var countitemrow=0;
var jine=""; 
var zhekou="";
var copy_item = "";
$(document).ready(function() {
	setselectGrid();
	setfzchareitemListGrid();
	setChangItemListGrid();	
	
	//
	initDeleteItemList();
	
	$('#tclist').textbox('textbox').bind('click', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('blur', function() {
		select_com_list_over();
	});

	
	$('#itemname').keyup(function(event) {
		if(event.keyCode=='38'||event.keyCode=='40'){
			 var s = $('#changitemlist').datagrid("getRows"); 
			 if(s.length>0){
					 $('#changitemlist').datagrid("selectRow", 0);  
					 $('.datagrid-row-selected').attr('tabindex',"0");
					 $('.datagrid-row-selected').focus();
			 }
		} else {
			setChangItemListGridreload();
		}
	});

	
	$('#serch_dep_id').combobox({
		url : 'getDirectorDepList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'dep_name',
		onLoadSuccess : function(data) {
			
		},
		onSelect: function(rec){    
			setChangItemListGridreload();
        }
	});
	
	$('#results_contrast').mouseleave(function(){
		 $('#results_contrast').css('display', 'none');
	 });
});



/**
 * 模糊查询公司信息
 */
var hc_set_list, set_Namess;
function select_com_list(set_Namess) {
	var url = 'satlistshow.action';
	var data = {
		"setname" : set_Namess,
		"sex": $("#custsex").val()
	};
	load_post(url, data, select_set_list_sess);
}

/**
 * 显示树形结构
 * @param data
 * @returns
 */
function select_set_list_sess(data) {
	mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
	mydtree.add(0, -1, "套餐列表", "javascript:void(0)", "根目录", "_self", false);
	for (var index = 0, l = data.length; index < l; index++) {
		if ((data[index].attributes == null)
				|| (data[index].attributes == '')
				|| (data[index].attributes == '0')) {
			mydtree.add(data[index].id, 0, data[index].text,
					"javascript:setvalue(" + data[index].id + ",'"
							+ data[index].text + "')", data[index].text,
					"_self", false);
		} else {
			mydtree.add(data[index].id, data[index].attributes,
					data[index].text, "javascript:setvalue("
							+ data[index].id + ",'" + data[index].text
							+ "')", data[index].text, "_self", false);
		}
	}
	$("#com_name_list_div").html(mydtree.toString());
	$("#com_name_list_div").css("display", "block");

}

/**
 * 点击树设置内容
 * @param id
 * @param name
 * @returns
 */
function setvalue(id, name) {
	$.post("getExamOneShow.action", {
		"exam_set_id" : id
	}, function(jsonPost) {
		var userid = eval(jsonPost);
		delsetflags=1;
		inserttc(userid);

	}, "json");
	$("#com_name_list_div").css("display", "none");
}

//单位失去焦点
var hc_mous_select1 = false;
function select_com_list_over() {
	if (!hc_mous_select1) {
		$("#com_name_list_div").css("display", "none");
	}
}

function select_com_list_mover() {
	hc_mous_select1 = true;
}
function select_com_list_amover() {
	hc_mous_select1 = false;
}

//-----------------------------------------选择套餐---------------------------------------------------	
/**
 * 显示分组套餐信息
 */
function setselectGrid() {
	$("#selectctlist").datagrid({
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {
			align : "center",
			field : "fxfz",
			title : "删除",
			width : 15,
			"formatter" : f_dellset
		}, {
			align : 'center',
			field : 'set_num',
			title : '套餐编码',
			width : 15
		}, {
			align : 'center',
			field : 'set_name',
			title : '套餐名称',
			width : 45
		}, {
			align : 'center',
			field : 'sex',
			title : '适用性别',
			width : 20
		}, {
			align : 'center',
			field : 'set_discount',
			title : '套餐折扣率',
			width : 30
		}, {
			align : 'center',
			field : 'set_amount',
			title : '套餐金额',
			width : 20
		} ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#selectctlist").datagrid("hideColumn", "set_discount"); // 设置隐藏列
				$("#selectctlist").datagrid("hideColumn", "set_amount"); // 设置隐藏列
			}
		},
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

/**
 * 进行分组
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellset(val, row) {
	if(delsetflags==0){
		return '';
	}else{
	    return '<a href=\"javascript:deletetc(\''
			+ row.set_num
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}
}

//----------------------------------------显示套餐-------------------------------------------------

/**
 * 移除套餐行
 */
function deletetc(set_numsss) {
	$.messager
			.confirm(
					'提示信息',
					'确定删除此套餐吗？',
					function(r) {
						if (r) {								
							var rowsLength = $('#selectctlist').datagrid(
									'getRows');
							if (!rowsLength.length == 0) {
								var flag = true;//不相等
								for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
								{
									if (set_numsss == rowsLength[j].set_num) {
										deletechargItem(set_numsss);
										var index = $('#selectctlist')
												.datagrid('getRowIndex',
														rowsLength[j]);//获取指定索引
										$('#selectctlist').datagrid(
												'deleteRow', index);//删除指定索引的行
										break;
									}
								}//j End             
							}
							countamt();

						}
					})
}

/**
 * 套餐删除缴费项目表信息
 */
function deletechargItem(set_numsss) {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	if (!rows.length == 0) {
		for (var i = rows.length - 1; i >= 0; i--) {
			if (set_numsss == rows[i].set_num) {
				var index1 = $('#selectchangitemlist').datagrid(
						'getRowIndex', rows[i]);//获取指定索引
				$('#selectchangitemlist').datagrid('deleteRow', index1);//删除指定索引的行
			}
		}//j End    
		countamt();
	}
}

/**
 * 增加套餐
 */
function inserttc(userid) {
	
	var rowsLength = $('#selectctlist').datagrid('getRows');
	var flag = true;//不相等
	if (!rowsLength.length == 0) {
		var flag = true;//不相等
		for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
		{
			if (userid.set_num == rowsLength[j].set_num) {
				flag = false;//相等
				$.messager.alert("操作提示", "该套餐已添加", "error");
				break;
			} else {
				$('#selectctlist').datagrid('loadData',{total:0,rows:[]});
				deletechargItem(rowsLength[j].set_num);
				flag = true;
			}
		}//j End             
	}
	if (flag == true) {			
	   $('#selectctlist').datagrid("appendRow", {
		  set_num : userid.set_num,
		  set_name : userid.set_name,
		  sex : userid.sex,
		  set_discount : userid.set_discount,
		  set_amount : userid.set_amount
	   });
	   if(copy_item!='N'){
		   insertsettiem(userid.set_num);
	   } 
			 
	}
}


//计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if(rows.length!='undefined'){
		
		//计算原项目金额
		if (!rows.length == 0) {
			for (var j=0; j<rows.length; j++)//已选择
			{
				var row = rows[j];
				oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
				newamt = decimal(newamt + Number(row.amount),2);
			}//j End             
		}
		
		//计算新添加项目金额
	}
	
	//console.log(rows.length+"----"+countitemrow+"===oldamt===="+oldamt+"====newamt===="+newamt);
	$("#item_amount").val(oldamt);  //原总额
	$("#amount").val(newamt)  //折扣后金额
	if(oldamt==0){
		$("#discount").val(10);
	}else{
		if($("#isDefaultTen").val()=="Y"){
			$("#discount").val(10);
		}else{
			$("#discount").val(decimal(Number(newamt) / Number(oldamt) * 10, 2));
		}
	}
	//计算折扣率
}


//选择套餐插入收费项目
function insertsettiem(setnum) {
	$.post("setforchangitemlist.action", {
		"set_num" : setnum
	}, function(jsonPost) {
		var userid = eval(jsonPost);
		for (var i = 0; i < userid.length; i++) {
			if (!isFloat($("#discount").val())) {
				alert('折扣率格式错误！');
				document.getElementById("discount").focus();
			} else if (Number($("#discount").val()) > 10) {
				alert('折扣率不能大于10！');
				document.getElementById("discount").focus();
			} else if (($("#discount").val() == '10')
					|| ($("#discount").val() == '10.0')
					|| ($("#discount").val() == '10.00')) {
//				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
//						* userid[i].discount / 10, 2);
				deltiemflags=1;
				tcinsertitem(userid[i]);
			} else {
				//userid[i].discount = $("#discount").val();
//				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
//						* $("#discount").val() / 10, 2);
				deltiemflags=1;
				tcinsertitem(userid[i]);
			}
		}
		
	}, "json");
}

/**
 * 增加分组项目
 */
function tcinsertitem(row) {
	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var itemtypeflag=true;
	var selectitemcode="";
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;//相等
			}
			if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
				itemtypeflag=false;
			}
			selectitemcode=selectitemcode+",'"+row.item_code+"'";
		}//j End             
	}
	if (flag == true) {		
		var usersex = $("#custsex").val();
		var sexflag=false;
		//alert(usersex+"---"+row.sex);
		if(usersex==''){
			sexflag=true;
		}else if(row.sex=='全部'){
			sexflag=true;
		}else if(usersex==row.sex){
			sexflag=true;
		}
		if(sexflag){
			if(itemtypeflag){
				$('#selectchangitemlist').datagrid("insertRow", {
					index: 0,  // 索引从0开始
				    row: {
				    	id:row.id,
						item_code : row.item_code,
						item_name : row.item_name,
						item_category : row.item_category,
						dep_name : row.dep_name,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						itemnum:row.itemnum,
						item_type:row.item_type,
						amount : row.amount
				    }
				});
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 $('#selectchangitemlist').datagrid("insertRow", {
							index: 0,  // 索引从0开始
						    row: {
						    	id:row.id,
								item_code : row.item_code,
								item_name : "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount : row.item_amount,
								sex : row.sex,
								discount : row.discount,
								set_num : row.set_num,
								itemnum:row.itemnum,
								item_type:row.item_type,
								amount : row.amount
						    }
						});
						countamt();
					 }
				 });
			}
			
		}else{
			//$.messager.alert("操作提示", "性别冲突，不能添加！", "error");
		}

	}
}
/***
 * 项目带出项目
 * @returns {String}
 */
var d_item = {
		get_item:function(id){//项目带出其他项目数据获取
			var rowsLength = "";
			$.ajax({
				url:'getItemSampleDemoDai.action?id='+id,
				type:'post',
			    async: false,
			    success:function(data){
					rowsLength = eval('('+data+')');
					//alert(rowsLength);
					//console.log(rowsLength);
				},error:function(){
					$.messager.alert("提示信息","操作失败","error");
				}
		   })
		   return rowsLength;
		},
	   get_item_2:function(id){//执行添加项目到已选择项目列表流程
		  // alert("进来了");
		   var row = d_item.get_item(id);
		   for(var i = 0 ; i < row.length ; i ++){
			   row[i].item_amount = row[i].amount;
			   row[i].amount = decimal(row[i].item_amount* $("#discount").val() / 10, 2);
			   d_item.get_item_3(row[i]);
		   }
		
	   },
	   get_item_3:function(row){//添加到已选择项目列表
		   var rowsLength = $('#selectchangitemlist').datagrid('getRows');
			var flag = true;//不相等
			var selectitemcode="";
			var itemtypeflag=true;
			if (!rowsLength.length == 0) {
				for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
				{
					if (row.item_code == rowsLength[j].item_code) {
						flag = false;//相等
					}
					if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
						itemtypeflag=false;
					}
					selectitemcode=selectitemcode+",'"+rowsLength[j].item_code+"'";
				}//j End             
			}
			if (flag == true) {	
				var usersex = $("#custsex").val();
				var sexflag=false;
				if(usersex==''){
					sexflag=true;
				}else if(row.sex=='全部'){
					sexflag=true;
				}else if(usersex==row.sex){
					sexflag=true;
				}
				if(sexflag){			
					if(itemtypeflag){
						$('#selectchangitemlist').datagrid("insertRow", {
						 	index: 0,  // 索引从0开始
						    row: {
						    	id:row.id,
								item_code : row.item_code,
								item_name : "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount : row.item_amount,
								sex : row.sex,
								itemnum:row.itemnum,
								discount :$("#discount").val(),
								set_num : row.set_num,
								item_type:row.item_type,
								amount : row.amount
						    }
						});
						countamt();
					}else{
						 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
					     if(r){
					    	 $('#selectchangitemlist').datagrid("insertRow", {
								 	index: 0,  // 索引从0开始
								    row: {
								    	id:row.id,
										item_code : row.item_code,
										item_name : "<span style='color:blue;'>"+row.item_name+"</span>",
										dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
										item_category : row.item_category,
										item_amount : row.item_amount,
										sex : row.sex,
										itemnum:row.itemnum,
										discount :$("#discount").val(),
										set_num : row.set_num,
										item_type:row.item_type,
										amount : row.amount
								    }
								});
								countamt();
							 }
						 });
					}	
				}else{
					alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
					$('#itemname').focus(); 
					$("#itemname").select();
				}

			}else{
				alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
				$('#itemname').focus(); 
				$("#itemname").select();
			}
	   }
			
					
}


//不修改折后总金额
function countamt2() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt = decimal(newamt + Number(row.amount),2);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").val(oldamt)
	//$("#amount").val(newamt)
}

//不修改折后总金额
function countamt3() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt = decimal(newamt + Number(row.amount),2);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").val(oldamt)
	$("#amount").val(newamt)
}

	/**
	 * 保存修改
	 */
	function updCardExamSetItem() {
		var itemrows = $('#selectchangitemlist').datagrid('getRows');
		var setrows = $('#selectctlist').datagrid('getRows');
		if(setrows.length>0){
			if (itemrows.length>0) {
				 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				 $("body").prepend(str);
				var itemrows = $('#selectchangitemlist').datagrid('getRows');  
					
			    var itementities = "";
			    for(i = 0;i < itemrows.length-countitemrow;i++) {
			    	var itemobject=itemrows[i];
					
					itementities = itementities + JSON.stringify(itemobject);
			    } 
			      
			    var setentities = "";
			    for(i = 0;i < setrows.length;i++)  
			    {  
			    	setentities = setentities  + JSON.stringify(setrows[i]);    
			    } 	  	
				$.ajax({
					url : 'updCardExamSetItem.action',
					data : {
								card_num : $("#cardNumList").val(),
								itementities: itementities,
								setentities:setentities
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								if(text=="ok"){
											
									$.messager.alert("操作提示", "操作成功!");
									var _parentWin =  window.opener ; 
									_parentWin.reapplylispacs();
									window.close();
								}else{
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});
			}else{
				$.messager.alert("操作提示", "无效收费项目", "error");
			}
		}else{
			$.messager.alert("操作提示", "无效套餐", "error");
		}
		
	}

	/**
	 * 套餐删除缴费项目表信息
	 */
	function deletechargItem(set_numsss) {
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var i = rows.length - 1; i >= 0; i--) {
				if (set_numsss == rows[i].set_num) {
					var index1 = $('#selectchangitemlist').datagrid(
							'getRowIndex', rows[i]);//获取指定索引
					$('#selectchangitemlist').datagrid('deleteRow', index1);//删除指定索引的行
				}
			}//j End    
			countamt();
		}
	}

	//----------------------------------------显示收费项目-------------------------------------------------
	function setChangItemListGridreload(){
		var model = {
				"setname" : $("#itemname").val(),
				"sex":$("#custsex").val(),
				"dep_id":$('#serch_dep_id').combobox('getValue')
			};
		$("#changitemlist").datagrid('reload',model);
	}
	/**
	 * 显示体检项目套餐信息
	 */
	function setChangItemListGrid() {
		var model = {
			"setname" : $("#itemname").val(),
			"sex":$("#custsex").val(),
			"dep_id":$('#serch_dep_id').combobox('getValue')
		};
		$("#changitemlist").datagrid(
				{
					url : 'changitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					pageSize: 10,//每页显示的记录条数，默认为10 
					pageList : [ 10, 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
					columns : [[ {align : 'left',	field : 'item_code',title : '项目编码',width : 10},
					         {align : 'left',field : 'item_name',title : '项目名称',	width : 40,"formatter":f_showitem}, 
					         {align : 'left',field : 'dep_name',title : '所属科室',width : 22},
					         {align : 'left',field : 'item_category',title : '项目类型',width : 22,hidden:true},
					         {align : 'center',field : 'sex',title : '性别',width : 10}, 
					         {align : 'center',field : 'item_amount',title : '金额',width : 10}
					         ]],
					onLoadSuccess : function(value) {
						$("#datatotal").val(value.total);
						$("#itemname").focus();
					},
					singleSelect : true,
					collapsible : true,
					pagination : true,
					fitColumns : true,
					striped : true,
					onDblClickRow : function(index, row) {
						row.set_num = '0';
						row.itemnum=1;
						if (!isFloat($("#discount").val())) {
							alert('折扣率格式错误！');
							document.getElementById("discount").focus();
						} else if (Number($("#discount").val()) > 10) {
							alert('折扣率不能大于10！');
							document.getElementById("discount").focus();
						} else if (($("#discount").val() == '10')
								|| ($("#discount").val() == '10.0')
								|| ($("#discount").val() == '10.00')) {
							row.discount = '10';
							row.amount = decimal(row.item_amount*row.itemnum
									* $("#discount").val() / 10, 2);
							deltiemflags = 1;
							insertitem(row);
						} else {
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount*row.itemnum
									* $("#discount").val() / 10, 2);
							deltiemflags = 1;
							insertitem(row);
						}
						
						$('#itemname').focus(); 
						$("#itemname").select();

						// countamt();
					}
				});
	}
	
	function f_additem(val,row){
		return '<a href="javascript:void(0)" onclick = "additemcontrol(\''+row.item_code+'\')">增加此项</a>';
	}
	var selectitem_code;
	function additemcontrol(selectitem_code){
		var row;
		var rowboolean=false;
		var rows = $('#changitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var i = rows.length - 1; i >= 0; i--) {
				if (selectitem_code == rows[i].item_code) {
					row=rows[i];
					rowboolean=true;
					break;
				}
				}
		}
		if(rowboolean)
		{
		row.set_num = '0';
		if (!isFloat($("#discount").val())) {
			alert('折扣率格式错误！');
			document.getElementById("discount").focus();
		} else if (Number($("#discount").val()) > 10) {
			alert('折扣率不能大于10！');
			document.getElementById("discount").focus();
		} else if (($("#discount").val() == '10')
				|| ($("#discount").val() == '10.0')
				|| ($("#discount").val() == '10.00')) {
			row.discount = '10';
			row.itemnum=1;
			row.amount = decimal(row.item_amount
					* $("#discount").val() / 10, 2);							
			deltiemflags=1;
			insertitem(row);
		} else {
			row.discount = $("#discount").val();
			row.itemnum=1;
			row.amount = decimal(row.item_amount
					* $("#discount").val() / 10, 2);
			deltiemflags=1;
			insertitem(row);
		}
		}
		$('#itemname').focus(); 
		$("#itemname").select();	
	}

	/**
	 * 增加分组项目a1
	 */
	function insertitem(row) {
		var rowsLength = $('#selectchangitemlist').datagrid('getRows');
		var flag = true;//不相等
		var selectitemcode="";
		var itemtypeflag=true;
		if (!rowsLength.length == 0) {
			for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
			{
				if (row.item_code == rowsLength[j].item_code) {
					flag = false;//相等
				}
				/*if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
					itemtypeflag=false;
				}*/
				selectitemcode=selectitemcode+",'"+rowsLength[j].item_code+"'";
			}//j End             
		}
		if (flag == true) {		
			var usersex = $("#custsex").val();
			var sexflag=false;
			if(usersex==''){
				sexflag=true;
			}else if(row.sex=='全部'){
				sexflag=true;
			}else if(usersex==row.sex){
				sexflag=true;
			}
			if(sexflag){			
				if(itemtypeflag){
					$('#selectchangitemlist').datagrid('insertRow',{
					    index: 0,  // 索引从0开始
					    row: {
					    	id:row.id,
					    	item_code : row.item_code,
							item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
							dep_name :  "<span style='color:blue;'>"+row.dep_name+"</span>",
							item_category : row.item_category,
							item_amount :  row.item_amount,
							sex :  row.sex,
							itemnum: row.itemnum,
							discount :  row.discount,
							set_num :  row.set_num,
							item_type: row.item_type,
							amount :  row.amount
					    } 
					});
					d_item.get_item_2(row.id);
					countamt();
				}else{
					 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
				     if(r){
				    	 $('#selectchangitemlist').datagrid('insertRow',{
							    index: 0,  // 索引从0开始
							    row: {
							    	id:row.id,
							    	item_code : row.item_code,
									item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
									dep_name :  "<span style='color:blue;'>"+row.dep_name+"</span>",
									item_category : row.item_category,
									item_amount :  row.item_amount,
									sex :  row.sex,
									itemnum: row.itemnum,
									discount :  row.discount,
									set_num :  row.set_num,
									item_type: row.item_type,
									amount :  row.amount
							    } 
							});
					    	 d_item.get_item_2(row.id);
							countamt();
						 }
					 });
				}	
			}else{
				alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
				$('#itemname').focus(); 
				$("#itemname").select();
			}

		}else{
			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
			$('#itemname').focus(); 
			$("#itemname").select();
		}
	}
    
	/**
	 * 删除收费项目
	 * @param val
	 * @param row
	 * @returns {String}
	 */
	function f_dellchargitem(val, row) {	
		if (deltiemflags==0){
			return '';
		}else{
		   return '<a href=\"javascript:deletechargitemOne(\''
				+ row.item_code
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
		}
	}
	/**
	 * 删除收费项目
	 */
	function deletechargitemOne(set_numsss) {
		/*$.messager.confirm('提示信息', '确定删除此收费项目吗？', function(r) {
			if (r) {}
		})*/
		

		var rows = $('#selectchangitemlist').datagrid('getRows');
		//判断delete表中是否有数据
		var isDeleteFlag = false;
		var rowDelete = $('#deleteItemList').datagrid('getRows');
		for (var m = rowDelete.length - 1; m >= 0; m--) {
			if (set_numsss == rowDelete[m].item_code) {
				//有该条数据
				isDeleteFlag = true;
				break;
			}
		}
		if(!isDeleteFlag){
			//先插入  再删除
			for (var i = rows.length - 1; i >= 0; i--) {
				if (set_numsss == rows[i].item_code) {
					//console.log(rows[i]);
					$('#deleteItemList').datagrid('insertRow',{
						index: 0,  // 索引从0开始
						row: {
							id : rows[i].id,
							item_code : rows[i].item_code,
							item_name :  isIndexOfSpan(rows[i].item_name),
							dep_name :  isIndexOfSpan(rows[i].dep_name),
							item_category : rows[i].item_category,
							sex :  rows[i].sex,
							item_amount :  rows[i].item_amount,
							itemnum: rows[i].itemnum,
							discount : rows[i].discount,
							amount :  rows[i].amount
						} 
					});
					
					break;
				}
			}
		}else{
			$.messager.alert("提示信息","删除项目中已有该条信息","error");
		}
		
		if (!rows.length == 0) {
			for (var i = rows.length - 1; i >= 0; i--) {
				if (set_numsss == rows[i].item_code) {
					var index1 = $('#selectchangitemlist').datagrid(
							'getRowIndex', rows[i]);//获取指定索引
					$('#selectchangitemlist').datagrid('deleteRow',
							index1);//删除指定索引的行
					break;
				}
			}//j End             
		}
		countamt();
	
	}
	//----------------------------------------显示分组收费项目-------------------------------------------------
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		$("#selectchangitemlist").datagrid({
			rownumbers : false,
			pageSize: 15,//每页显示的记录条数，默认为10 
			pageList : [ 15, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 10,	"formatter" : f_dellchargitem},
			   {align : 'left',field : 'item_code',title : '项目编码',width : 15}, 
			   {align : 'left',field : 'item_name',title : '项目名称',width :30},
			   {align : 'left',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'left',field : 'id',title : 'ID',width : 15,hidden:true},
			   {align : 'left',field : 'sex',title : '性别',width : 15,hidden:true},
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10},
			   {align : 'center',field : 'itemnum',title : '数量',width : 10}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 15}, 
			   {align : 'center',field : 'amount',title : '金额',	width : 20} 
			   ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				var data=$('#selectchangitemlist').datagrid('getData');
				countitemrow=data.rows.length;
				countamt();
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			height:"550"
		});
	}
	
	function f_itemCategory(val,row){
		if(val=="" || val==null){
			return "";
		}else{
			return "<span id=\""+row.item_code+"_item_category\">"+val+"</span>";
		}
	}
	
	
	function f_showitem(val,row){
		return '<a href="javascript:void(0)" onclick = "showexamitem('+row.id+',this)">'+row.item_name+'</a>';
	}
	function showexamitem(id,ths){
		$("#results_contrast").css("top",$(ths).offset().top);
		 $("#results_contrast").css("left","80px");
		 $('#results_contrast').css('display','block');
		$("#examitem_div").datagrid({
			 url:'getChargingItemExamItem.action',
			 dataType: 'json',
			 queryParams:{'id':id},
			 rownumbers:false,
			 width:300,
			 height:350,
			 columns:[[
			           {align:'center',field:'item_num',title:'项目编码',width:40},
			           {align:'center',field:'item_name',title:'项目名称',width:60}
			 ]],
			 singleSelect:true,
			 collapsible:true,
			 pagination: false,
			 striped:true,
			 nowrap:false,
			 fitColumns:true
		 });
	}
//-------------------------------------复制套餐项目-----------------------
	//复制本人上次体检项目
//	function getcopyitemg(){
//		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
//		 $("body").prepend(str);
//		$.ajax({
//			url:'getcopaitem.action',
//			data:{
//				customer_id:$('#a_customer_id').val(),
//				copy_status:''
//				},
//			type:'post',
//			success:function(data){
//			
//				$(".loading_div").remove();
//				deltiemflags=1;
//				delsetflags=1;
//				var row = eval('('+data+')');
//				if(row.li.length == 0){
//					$.messager.alert('提示信息','无上次体检项目信息','error');
//					return;
//				}else{
//					$.messager.alert('提示信息','操作成功','info');
//				}
//				var item = row.li;
//				var set = row.se;
//				//console.log(row);
//				//复制项目
//				for(var i = 0 ; i < item.length ; i++){
//					tcinsertitem(item[i]);
//					copy_item = "N";
//				}
//				//复制套餐
//				for(var i = 0 ; i < set.length ; i++){
//					inserttc(set[i]);
//				}
//				copy_item = "Y";
//			},error:function(){
//				$(".loading_div").remove();
//				$.messager.alert('提示信息','操作失败','error');
//			}
//		})
//	}
//	//复制他人 项目
//	function getcopyitemg2(){
//		$('#dlg-item-fuzhi').dialog({
//			title:'复制项目',    
//			href: 'getcopaitemPage.action',   
//		})
//		$('#dlg-item-fuzhi').dialog('open');  
//	}
	
	//初始化删除项目信息
	function initDeleteItemList(){
		$("#deleteItemList").datagrid(
				{
					url : '',
					dataType : 'json',
					rownumbers : false,
					columns : 
						[[ 	 
						   {align : 'left',field : 'item_code',title : '项目编码',width : 10}, 
						   {align : 'left',field : 'item_name',title : '项目名称',width :30,"formatter":f_showitem},
						   {align : 'left',field : 'dep_name',title : '所属科室',width : 22}, 
						   {align : 'center',field : 'item_category',title : '项目类型',width : 10,hidden:true},
						   {align : 'left',field : 'sex',title : '性别',width : 10},
						   {align : 'center',field : 'item_amount',title : '原金额',width : 10},
						   {align : 'center',field : 'id',title : 'ID',width : 10,hidden:true},
						   {align : 'center',field : 'item_type',title : '类型',width : 10,hidden:true},
						   {align : 'center',field : 'itemnum',title : '数量',width : 10,hidden:true}, 
						   {align : 'center',field : 'discount',title : '折扣率',width : 10}, 
						   {align : 'center',field : 'amount',title : '折扣金额',	width : 15} 
					     ]],
					singleSelect : true,
					collapsible : true,
					fitColumns : true,
					striped : true,
					onDblClickRow : function(index, row) {
						row.set_num = '0';
						row.itemnum=1;
						if (!isFloat($("#discount").val())) {
							alert('折扣率格式错误！');
							document.getElementById("discount").focus();
						} else if (Number($("#discount").val()) > 10) {
							alert('折扣率不能大于10！');
							document.getElementById("discount").focus();
						} else if (($("#discount").val() == '10')
								|| ($("#discount").val() == '10.0')
								|| ($("#discount").val() == '10.00')) {
							row.discount = '10';
							row.amount = decimal(row.item_amount*row.itemnum
									* $("#discount").val() / 10, 2);
							deltiemflags = 1;
							
							var deleteItem = $('#deleteItemList').datagrid('getRows');
							for (var i = deleteItem.length - 1; i >= 0; i--) {
								if (row.item_code == deleteItem[i].item_code) {
									insertitem(deleteItem[i]);
								}
							}
							//删除该条记录
							if (!deleteItem.length == 0) {
								for (var i = deleteItem.length - 1; i >= 0; i--) {
									if (row.item_code == deleteItem[i].item_code) {
										var index1 = $('#deleteItemList').datagrid('getRowIndex', deleteItem[i]);//获取指定索引
										$('#deleteItemList').datagrid('deleteRow',index1);//删除指定索引的行
										break;
									}
								}//j End             
							}
							
						} else {
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount*row.itemnum
									* $("#discount").val() / 10, 2);
							deltiemflags = 1;
							
							var deleteItem = $('#deleteItemList').datagrid('getRows');
							for (var i = deleteItem.length - 1; i >= 0; i--) {
								if (row.item_code == deleteItem[i].item_code) {
									insertitem(deleteItem[i]);
								}
							}
							//删除该条记录
							if (!deleteItem.length == 0) {
								for (var i = deleteItem.length - 1; i >= 0; i--) {
									if (row.item_code == deleteItem[i].item_code) {
										var index1 = $('#deleteItemList').datagrid('getRowIndex', deleteItem[i]);//获取指定索引
										$('#deleteItemList').datagrid('deleteRow',index1);//删除指定索引的行
										break;
									}
								}//j End             
							}
							
						}
						
						$('#itemname').focus(); 
						$("#itemname").select();
						

					},
					rowStyler: function(index,row){
						return 'color:red;';
					}
				}
				

				);
		var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
		if(is_show_discount==0){
			$("#deleteItemList").datagrid("hideColumn", "discount"); // 设置隐藏列
			$("#deleteItemList").datagrid("hideColumn", "amount"); // 设置隐藏列
		}
	}

	//判断是否包含span
	function isIndexOfSpan(strIn){
		var strOut = "";
		if(strIn.indexOf("span") >-1){
			strOut = strIn.substring(strIn.indexOf('>')+1,strIn.indexOf('</span>'));
		}else{
			strOut = strIn;
		}
		return strOut;
	}