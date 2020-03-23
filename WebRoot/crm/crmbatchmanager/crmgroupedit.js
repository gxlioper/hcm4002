$(document).ready(function() {
	f_getDatadic();
	setselectGrid();
	setfzchareitemListGrid();
	setChangItemListGrid();
	$('#tclist').textbox('textbox').bind('click', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('blur', function() {
		select_com_list_over();
	});

	$('#itemname').bind('keyup', function() {
		setChangItemListGrid();
	});
	
	$("#discount").bind('keyup', function (event) {
	    var $amountInput = $(this);
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event || event;
	    if (event.keyCode == 37 | event.keyCode == 39) {
	        return;
	    }
	    //先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
	        //只允许一个小数点              
	        replace(/^\./g, "").replace(/\.{2,}/g, ".").
	        //只能输入小数点后两位
	        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));
	});
		
	$("#discount").on('blur', function () {
		if($('#discount').val()>10){
	    	$('#discount').val('10');
	    }
		if($("#discount").val()<0){
			$('#discount').val('10');
		}
	    var $amountInput = $(this);
	    //最后一位是小数点的话，移除
	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	    discount(this.value);
	});
	
	
	$('#amount').textbox('textbox').bind('blur', function() {
		disamout(this.value);
	});

});

//获取菜单
function f_getDatadic() {
	var sextype = $("#batchsex").val();
	$('#sex').combobox({
		url : 'getDatadis.action?com_Type=XBLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == sextype) {
					$('#sex').combobox('setValue', data[i].id);
					break;
				} else {
					$('#sex').combobox('setValue', data[0].id);
				}
			}
		},
		onChange: function (n,o) {
			$('#sex').combobox('setValue', n);
			setChangItemListGrid();
		}
	});
	
	$("#cust_type_id").combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		multiple:true,//是否允许多选
		valueField : 'id',
		textField : 'name',
		onSelect: function () {	
			
         },
         onLoadSuccess:function(){       	
			 var cust_type_ids= $("#cust_type_list").val();
			 if(cust_type_ids != ''){
				 $("#cust_type_id").combobox('setValues',cust_type_ids.split(','));
			 }
		}
	});
	
	var exam_indicator = $("#batchindicator").val();
	$('#exam_indicator').combobox({
		url : 'getDatadis.action?com_Type=FFFS',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == exam_indicator) {
					$('#exam_indicator').combobox('setValue', data[i].id);
					break;
				} else {
					$('#exam_indicator').combobox('setValue', data[0].id);
				}
			}
		}
	});

	var ismarriagess = $("#batchMarriage").val();
	$('#is_Marriage').combobox({
		url : 'getDatadis.action?com_Type=HFLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			for (var i = 0; i < data.length; i++) {
				if (data[i].id == ismarriagess) {
					$('#is_Marriage').combobox('setValue', data[i].id);
					break;
				} else {
					$('#is_Marriage').combobox('setValue', data[0].id);
				}
			}
		}
	});
}

function checkinput() {
	if (document.getElementById("group_name").value == '') {
		alert('分组名称不能为空！');
		document.getElementById("group_name").focus();
		return false;
	} else if ($('#discount').val() == '') {
		alert('折扣率不能为空！');
		document.getElementById("discount").focus();
		return false;
	} else if (!(isFloat($('#discount').val()))) {
		alert('折扣率格式错误！');
		document.getElementById("discount").focus();
		return false;
	} else if (document.getElementById("amount").value == '') {
		alert('折后金额不能为空！');
		document.getElementById("amount").focus();
		return false;
	} else if (!(isFloat(document.getElementById("amount").value))) {
		alert('折后金额格式错误！');
		document.getElementById("amount").focus();
		return false;
	} else if (document.getElementById("min_age").value != '') {
		if (!(isSZ(document.getElementById("min_age").value))) {
			alert('最小年龄格式错误！');
			document.getElementById("min_age").focus();
			return false;
		}
	} else if (document.getElementById("max_age").value != '') {
		if (!(isSZ(document.getElementById("max_age").value))) {
			alert('最大年龄格式错误！');
			document.getElementById("max_age").focus();
			return false;
		}
	}
	if(Number($("#discount").val())<Number($('#webResources').val())){
				$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
				return false;
	}
	return true;
}

//复制套餐和项目
function groupcopy(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	var itemrows = $('#selectchangitemlist').datagrid('getRows');  
    var itementities = "";
    for(i = 0;i < itemrows.length;i++)  
    {		  
    	itementities = itementities  + JSON.stringify(itemrows[i]);    
    } 
    
    var setrows = $('#selectctlist').datagrid('getRows');  
    var setentities = "";
    for(i = 0;i < setrows.length;i++)  
    {  
    	setentities = setentities  + JSON.stringify(setrows[i]);    
    } 	  		    
	$.ajax({
		url : 'groupcopydo.action',
		data : {
				itementities: itementities,
				setentities:setentities,
				},
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					if (text.split("-")[0] == 'ok') {
						alert_autoClose("操作提示", "添加成功！",text.split("-")[1]);
					} else {
						alert_autoClose("操作提示", text, "error");
					}
				}
			});
  }

//粘贴套餐和项目
function grouppost(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	    
	$.ajax({
		url : 'grouppostdo.action',
		data : {},
				type : "post",//数据发送方式   
				success : function(data) {
			$(".loading_div").remove();
			var datalist = eval("(" + data + ")");
			$('#selectctlist').datagrid('loadData', {
				total : 0,
				rows : []
			});
			for (var i = 0; i < datalist.listset.length; i++) {
				var userid = datalist.listset[i];
				var usersex = document.getElementsByName("sex")[0].value;
				var sexflag = false;
				if (usersex == '') {
					sexflag = true;
				} else if (userid.sex == '全部') {
					sexflag = true;
				} else if (usersex == userid.sex) {
					sexflag = true;
				}
				if (sexflag) {
					$('#selectctlist').datagrid("appendRow", {
						set_num : userid.set_num,
						set_name : userid.set_name,
						sex : userid.sex,
						set_discount : userid.set_discount,
						set_amount : userid.set_amount
					});
				} else {
					// alert("套餐性别冲突111，不能添加。");
				}
			}
			
			$('#selectchangitemlist').datagrid('loadData', {
				total : 0,
				rows : []
			});
			for (var j = 0; j < datalist.listitem.length; j++) {
				var useritem = datalist.listitem[j];
				$('#selectchangitemlist').datagrid("appendRow", {
					item_code : useritem.item_code,
					item_name : useritem.item_name,
					dep_name : useritem.dep_name,
					item_amount : useritem.item_amount,
					sex : useritem.sex,	
					itemnum:useritem.itemnum,
					discount : useritem.discount,
					set_num : useritem.set_num,
					amount : useritem.amount
				});
			}
			countamt();
			
		},error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");
		}
	});

}

/**
 * 保存修改
 */
function groupadd() {	
	if (checkinput()) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$("#group_num").val($("#group_name").val());
		var itemrows = $('#selectchangitemlist').datagrid('getRows');  
	    var itementities = "";
	    for(i = 0;i < itemrows.length;i++)  
	    {		  
	    	itementities = itementities  + JSON.stringify(itemrows[i]);    
	    } 
	    
	    var setrows = $('#selectctlist').datagrid('getRows');  
	    var setentities = "";
	    for(i = 0;i < setrows.length;i++)  
	    {  
	    	setentities = setentities  + JSON.stringify(setrows[i]);    
	    } 	  		    
		$.ajax({
			url : 'groupeditdo.action',
			data : {
						id : $("#id").val(),
						batch_id : $("#batch_id").val(),
						group_num : $("#group_num").val(),
						group_name : $("#group_name").val(),
						min_age : $("#min_age").val(),
						max_age : $("#max_age").val(),
						itementities: itementities,
						setentities:setentities,
						sex : document.getElementsByName("sex")[0].value,
						is_Marriage : document.getElementsByName("is_Marriage")[0].value,
						exam_indicator: document.getElementsByName("exam_indicator")[0].value,
						cust_type_id:$("#cust_type_id").combobox('getValues').join(','),
						posttion : $("#posttion").val(),
						group_index : $("#group_index").val(),
						discount : $("#discount").val(),
						amount : $("#amount").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == '0') {
							$.messager.alert("操作提示", text);
							var _parentWin =  window.opener ; 
							_parentWin.getgroupGrid(); // 主窗口getgroupGrid();刷新  
							window.close();
						} else {
							$.messager.alert("操作提示", text, "error");
						}
					}
				});
	}
	 
}

//-------------------------------------------单位信息显示-----------------------------------------------------
/**
 * 模糊查询公司信息
 */
var hc_set_list, set_Namess;
function select_com_list(set_Namess) {
	var url = 'crmsatlistshow.action';
	var data = {
		"setname" : set_Namess,
		"sex": document.getElementsByName("sex")[0].value,
		"apptype":$("#apptypesss").val(),
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
	var model = {
		"group_id" : $("#id").val()
	};
	$("#selectctlist").datagrid({
		url : 'groupsetlistshow.action',
		dataType : 'json',
		queryParams : model,
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
	return '<a href=\"javascript:deletetc(\''
			+ row.set_num
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
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
				break;
			} else {
				flag = true;
			}
		}//j End             
	}
	if (flag == true) {			
		var usersex = document.getElementsByName("sex")[0].value;
		var sexflag=false;
		if(usersex==''){
			sexflag=true;
		}else if(userid.sex=='全部'){
			sexflag=true;
		}else if(usersex==userid.sex){
			sexflag=true;
		}
		if(sexflag){
		   $('#selectctlist').datagrid("appendRow", {
			  set_num : userid.set_num,
			  set_name : userid.set_name,
			  sex : userid.sex,
			  set_discount : userid.set_discount,
			  set_amount : userid.set_amount
		   });
		   insertsettiem(userid.set_num);
		}else{
			//alert("套餐性别冲突111，不能添加。");
		}
	}
}

//----------------------------------------显示收费项目-------------------------------------------------
/**
 * 显示体检项目套餐信息
 */
function setChangItemListGrid() {
	var sex = $('#sex').combobox('getValue');
	var model = {
		"setname" : $("#itemname").val(),
		"sex": sex
	};
	$("#changitemlist").datagrid(
			{
				url : 'changitemlist.action',
				dataType : 'json',
				queryParams : model,
				rownumbers : false,
				pageSize: 30,//每页显示的记录条数，默认为10 
				pageList : [ 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
				columns : [[ {align : 'center',
					field : 'item_code',
					title : '项目编码',
					width : 20
				}, {
					align : 'center',
					field : 'item_name',
					title : '项目名称',
					width : 25
				}, {
					align : 'center',
					field : 'dep_name',
					title : '所属科室',
					width : 15
				}, {
					align : 'center',
					field : 'sex',
					title : '适用性别',
					width : 15
				}, {
					align : 'center',
					field : 'item_amount',
					title : '套餐金额',
					width : 15
				}]],
				onLoadSuccess : function(value) {
					$("#datatotal").val(value.total);
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
						row.discount = '10.00';
						row.amount = decimal(row.item_amount *row.itemnum
								* $("#discount").val() / 10, 2);
						insertitem(row);
					} else {
						row.discount ='10.00';
						row.amount = decimal(row.item_amount * row.itemnum
								* $("#discount").val() / 10, 2);
						insertitem(row);
					}
					$('#itemname').focus(); 
					$("#itemname").select();
				}
			});
	//document.getElementById("itemname").value = '';
	//document.getElementById("itemname").focus();
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
			}else if (($("#discount").val() == '10')
					|| ($("#discount").val() == '10.0')
					|| ($("#discount").val() == '10.00')) {	
				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
						* userid[i].discount / 10, 2);
				tcinsertitem(userid[i]);
			} else {
				userid[i].discount = $("#discount").val();
				userid[i].amount = decimal(userid[i].item_amount * userid[i].itemnum
						* $("#discount").val() / 10, 2);
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
	var selectitemcode="";
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;//相等
			}
			selectitemcode=selectitemcode+",'"+row.item_code+"'";
		}//j End             
	}
	if (flag == true) {		
		var usersex = document.getElementsByName("sex")[0].value;
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
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'checkitemflag.action',
				data : {
					checknotice : selectitemcode,
					exam_item : row.item_code						
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();
							if (text == 'ok') {
								$('#selectchangitemlist').datagrid("appendRow", {
									item_code : row.item_code,
									item_name : row.item_name,
									dep_name : row.dep_name,
									item_amount : row.item_amount,
									sex : row.sex,
									itemnum:row.itemnum,
									discount : row.discount,
									set_num : row.set_num,
									amount : row.amount
								});
								countamt();
								//$.messager.alert("操作提示", "添加成功！");
							} else {
								//$.messager.alert("操作提示", "项目冲突，不能添加！", "error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");					
						}
					});		
		}else{
			//$.messager.alert("操作提示", "性别冲突，不能添加！", "error");
		}

	}
}

/**
 * 增加分组项目
 */
function insertitem(row) {
	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var selectitemcode="";
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;//相等
			}
			selectitemcode=selectitemcode+",'"+rowsLength[j].item_code+"'";
		}//j End             
	}
	if (flag == true) {
		
		var usersex = document.getElementsByName("sex")[0].value;
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
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			$.ajax({
				url : 'checkitemflag.action',
				data : {
					checknotice : selectitemcode,
					exam_item : row.item_code						
						},
						type : "post",//数据发送方式   
						success : function(text) {
							 $(".loading_div").remove();
							if (text == 'ok') {
								$('#selectchangitemlist').datagrid("appendRow", {
									item_code : row.item_code,
									item_name : row.item_name,
									dep_name : row.dep_name,
									item_amount : row.item_amount,
									sex: row.sex,
							        itemnum:row.itemnum,
									discount : row.discount,
									set_num : row.set_num,
									amount : row.amount
								});
								countamt();
								alert_autoClose("操作提示", "添加成功！","");
							} else {
								alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
							}
							$('#itemname').focus(); 
							$("#itemname").select();
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("操作提示", "操作错误", "error");
							$('#itemname').focus(); 
							$("#itemname").select();
						}
					});		
		}else{
			alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
		}
	}else{
		alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
	}
}

//计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if(rows.length!='undefined'){
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = oldamt + Number(row.item_amount)*Number(row.itemnum);
			newamt = newamt + Number(row.amount);
		}//j End             
	}
	}
	var depositValue='';
	if(oldamt=='0'){
		depositValue='10.00';
	}else{
		depositValue=decimal((Number(newamt)/Number(oldamt)) * 10, 2);
	}
	$("#item_amount").textbox('setValue', oldamt+'');
	$("#amount").textbox('setValue', newamt+'');
    $("#discount").val(depositValue+'');
}

//修改折扣率 更新所有行
var disvar;
function discount(disvar) {
	if (!isFloat(disvar)) {
		$('#discount').textbox('setValue',10);
		$('#discount').textbox('textbox').focus();
		$.messager.alert("操作提示", "折扣率格式错误！", "error");
	} else if (Number(disvar) > 10) {
		$('#discount').textbox('setValue',10);
		$('#discount').textbox('textbox').focus();
		$.messager.alert("操作提示", "折扣率不能大于10！", "error");
	} else {
		if(Number(disvar)<Number($('#webResources').val())){
			$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
		}else{
			var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = disvar;
					row.amount = decimal(Number(row.item_amount) * Number(row.itemnum) * Number(disvar) / 10, 2);	
					//$('#selectchangitemlist').datagrid('updateRow', {index: j,row: row});
					//$('#selectchangitemlist').datagrid("endEdit", j);
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt();
		}
	}
}


//根据金额反算折扣
function disamout(disvar) {
	if (!isFloat(disvar)) {
		$('#amount').textbox('setValue', $("#item_amount").val());
		$('#amount').textbox('textbox').focus();
		$.messager.alert("操作提示", "金额格式错误！", "error");
	} else if (Number(disvar) > Number($("#item_amount").val())) {
		$('#amount').textbox('setValue', $("#item_amount").val());
		$('#amount').textbox('textbox').focus();
		$.messager.alert("操作提示", "金额不能大于！"
				+ document.getElementById("item_amount").value + "元", "error");
	} else {
		var newdiscont = decimal(Number(disvar)
				/ Number($("#item_amount").val()) * 10, 2);
		if(Number(newdiscont)<Number($('#webResources').val())){
			$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
		}else{
			$("#discount").val(newdiscont);
			var rows = $('#selectchangitemlist').datagrid('getRows');
			if (!rows.length == 0) {
				for (var j = 0; j <= rows.length - 1; j++)// 已选择
				{
					var row = rows[j];
					row.discount = newdiscont;
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum)
							* Number(newdiscont) / 10, 2);
					$('#selectchangitemlist').datagrid('refreshRow', j);
				}// j End
			}
			countamt2();
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
			oldamt = oldamt + Number(row.item_amount)*Number(row.itemnum);
			newamt = newamt + Number(row.amount);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").textbox('setValue', oldamt);
	//$("#amount").textbox('setValue', newamt);
}

//通过修改单个折扣率算金额
function calcAmt(rowIndex){
	var objGrid = $("#selectchangitemlist");        // 表格对象
	var discountEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'discount'});        // 折扣额对象
	var itemnumEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'itemnum'});        //数量
    var amountEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'amount'});  
 // 折扣率  绑定离开事件 
    $(discountEdt.target).bind("blur",function(){
        calcDeposit(rowIndex);        // 根据 折扣率变更后 计算 折扣额
    });
    
    // 金额 绑定离开事件 
    $(amountEdt.target).bind("blur",function(){
    	//alert(44);
        calcMoneyChange(rowIndex);    // 金额变更 后 重新计算  单价,折扣额,折扣率
    });
    
    // 数量 绑定离开事件 
    $(itemnumEdt.target).bind("blur",function(){
    	//alert(44);
        calcitemnumChange(rowIndex);    // 金额变更 后 重新计算  单价,折扣额,折扣率
    });
	
}

/**
 * 根据 折扣率变更后 计算 折扣额
 * @author 
 */
function calcMoneyChange(desindex){
    var objGrid = $("#selectchangitemlist");   
    var rows = objGrid.datagrid('getRows');
    var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
    var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
    var itemnumEdt = objGrid.datagrid('getEditor', {index:desindex,field:'itemnum'});    // 项目数量
    var discountValue = $(discountEdt.target).val();                // 折扣率 值
    var itemnumValue = $(itemnumEdt.target).val();// 数量    
    var invMoneyValue = rows[desindex].item_amount;            // 金额值
    var amountValue = $(depositEdt.target).val();    // 现有金额    
   
    var depositValue = decimal(Number(amountValue)/ (Number(invMoneyValue)* Number(itemnumValue)) * 10, 2);	
    if(depositValue<Number($('#webResources').val())){
    	$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
    }else{
    	if(Number(amountValue)>(Number(invMoneyValue)* Number(itemnumValue))){
        	depositValue=10;
        	amountValue= Number(invMoneyValue)* Number(itemnumValue);
        }
        rows[desindex].discount=depositValue; 
        rows[desindex].amount=amountValue;    // 折扣额  赋值
        rows[desindex].itemnum=itemnumValue;
        $('#selectchangitemlist').datagrid('refreshRow', desindex);
        $("#selectchangitemlist").datagrid('endEdit', desindex); 
        //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
        countamt();
    }
}

/**
 * 根据数量计算金额
 * @param desindex
 */
function calcitemnumChange(desindex){
    var objGrid = $("#selectchangitemlist");   
    var rows = objGrid.datagrid('getRows');
    var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
    var itemnumEdt = objGrid.datagrid('getEditor', {index:desindex,field:'itemnum'});    // 项目数量
    var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
    var invMoneyValue = rows[desindex].item_amount;
    var itemnumValue = $(itemnumEdt.target).val();// 数量
    var discountValue = $(discountEdt.target).val();// 折扣率 值
    if (!isSZZoo(itemnumValue)){
    	itemnumValue=1;
    }
    var amountValue = $(depositEdt.target).val();    // 现有金额        
    var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) /10, 2);	
    if(depositValue<Number($('#webResources').val())){
    	$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
    }else{
    	rows[desindex].itemnum=itemnumValue; 
	    rows[desindex].discount=discountValue; 
	    rows[desindex].amount=depositValue;    // 折扣额  赋值
	    $('#selectchangitemlist').datagrid('refreshRow', desindex);
	    $("#selectchangitemlist").datagrid('endEdit', desindex); 
	    //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
	    countamt();
    	
    }
   
}

 /**
 * 根据 折扣率变更后 计算 折扣额
 * @author yangm
 */
 var desindex;
function calcDeposit(desindex){
    var objGrid = $("#selectchangitemlist");   
    var rows = objGrid.datagrid('getRows');
    var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
    var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
    var itemnumEdt = objGrid.datagrid('getEditor', {index:desindex,field:'itemnum'});    // 项目数量
    var invMoneyValue = rows[desindex].item_amount;           // 金额值
    var discountValue = $(discountEdt.target).val();                // 折扣率 值
    var itemnumValue = $(itemnumEdt.target).val();// 数量    
    if(Number(discountValue)>10){
    	discountValue=10;
    }else if(Number(discountValue)<Number($('#webResources').val())){
    	$.messager.alert("操作提示", "折扣率最小不能低于"+$('#webResources').val(), "error");
    }else{
    	 var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) /10, 2);	
 	    rows[desindex].itemnum=itemnumValue; 
 	    rows[desindex].discount=discountValue; 
 	    rows[desindex].amount=depositValue;    // 折扣额  赋值
 	    $('#selectchangitemlist').datagrid('refreshRow', desindex);
 	    $("#selectchangitemlist").datagrid('endEdit', desindex); 
 	    //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
 	    countamt();	

    }
}

/**
 * 删除收费项目
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellchargitem(val, row) {
	return '<a href=\"javascript:deletechargitemOne(\''
			+ row.item_code
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
}
/**
 * 删除收费项目
 */
function deletechargitemOne(set_numsss) {
	$.messager.confirm('提示信息', '确定删除此收费项目吗？', function(r) {
		if (r) {
			var rows = $('#selectchangitemlist').datagrid('getRows');
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
	})
}
//----------------------------------------显示分组收费项目-------------------------------------------------
/**
 * 显示体检项目套餐信息
 */
 var lastIndex=-1; 
function setfzchareitemListGrid() {
	var model = {
		"group_id" : $("#id").val()
	};
	$("#selectchangitemlist").datagrid({
		url : 'groupchargitemlistshow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		//pageSize: 8,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
		columns : [[ {
			align : "center",
			field : "fxfzddd",
			title : "删除",
			width : 15,
			"formatter" : f_dellchargitem
		}, {
			align : 'center',
			field : 'item_code',
			title : '项目编码',
			width : 20
		}, {
			align : 'center',
			field : 'item_name',
			title : '项目名称',
			width : 25
		}, {
			align : 'center',
			field : 'set_num',
			title : '套餐编码',
			width : 25
		}, {
			align : 'center',
			field : 'dep_name',
			title : '科室',
			width : 25
		}, {
			align : 'center',
			field : 'item_amount',
			title : '原金额',
			width : 20
		}, {
			align : 'center',
			field : 'discount',
			title : '折扣率',
			width : 20,
			editor : {
				type : 'text'
			}
		}, {
			align : 'center',
			field : 'itemnum',
			title : '数量',
			width : 20,
			editor : {
				type : 'text'
			}
		}, {
			align : 'center',
			field : 'amount',
			title : '套餐金额',
			width : 20,
			editor : {
				type : 'text'
			}
		} ]],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
		},
		singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		pageNumber : 1,
		pageSize : 1000,
		onDblClickRow  : function (rowIndex) {			    
	            if (lastIndex != rowIndex){
	            	$("#selectchangitemlist").datagrid('beginEdit', rowIndex); 
	            	calcAmt(rowIndex);
	            }  
	            lastIndex = -1;  
        }
	});
}