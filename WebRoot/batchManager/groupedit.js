$(document).ready(function() {
	f_getDatadic();
	setselectGrid();
	setfzchareitemListGrid();
	setChangItemListGrid();
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$("#zkl").hide(); // 设置隐藏列
		$("#zkhze").hide(); // 设置隐藏列
		$("#discount").hide(); // 设置隐藏列
		$("#amount").hide(); // 设置隐藏列
		$("#amount_yuan").hide(); // 设置隐藏列
	}
	$('#tclist').textbox('textbox').bind('click', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('keyup', function() {
		select_com_list(this.value);
	});

	$('#tclist').textbox('textbox').bind('blur', function() {
		select_com_list_over();
	});
	
	//初始化删除项目
	initDeleteItemList();
	
	//聚焦
	$("#itemname").focus();

	$("#itemname").bind('keyup', function (event) {
		if(event.keyCode=='38'||event.keyCode=='40'){
			 var s = $('#changitemlist').datagrid("getRows"); 
			 if(s.length>0){
				 $('#changitemlist').datagrid("selectRow", 0);  
				 $('.datagrid-row-selected').attr('tabindex',"0");
				 $('.datagrid-row-selected').focus();
			 }
		}else {
			setChangItemListGridreload();
		}
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
	
	
	$('#amount').bind('blur', function() {
		disamout(this.value);
	});

	var isgroupitemflag=getCookie("isgroupitemflag");
	if((isgroupitemflag!=null)||(isgroupitemflag!='')){
		if(isgroupitemflag=="false"){
			$("input[name='isgroupitemflag']").prop("checked", false);
		}else{
			$("input[name='isgroupitemflag']").prop("checked", true);
		}
	}
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
			//setChangItemListGrid();
			setChangItemListGridreload();
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
					amount : useritem.amount,
					item_discount:useritem.item_discount
				});
			}
			countamt();
			var newdiscont = decimal(Number($("#amount").val())/ Number($("#item_amount").val()) * 10, 4);
			$("#discount").val(newdiscont);
		},error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");
		}
	});

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
					console.log(rowsLength);
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
			var flag = true;// 不相等
			var selectitemcode = "";
			var itemtypeflag = true;
			if (!rowsLength.length == 0) {
				for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
				{
					if (row.item_code == rowsLength[j].item_code) {
						flag = false;// 相等
					}
					if ((row.item_type != '')
							&& (row.item_type == rowsLength[j].item_type)) {
						itemtypeflag = false;
					}
					selectitemcode = selectitemcode + ",'" + rowsLength[j].item_code
							+ "'";
				}// j End
			}
			if (flag == true) {

				var usersex = document.getElementsByName("sex")[0].value;
				var sexflag = false;
				// alert(usersex+"---"+row.sex);
				if (usersex == '') {
					sexflag = true;
				} else if (row.sex == '全部') {
					sexflag = true;
				} else if (usersex == row.sex) {
					sexflag = true;
				}
				if (sexflag) {
					if (itemtypeflag) {
						$('#selectchangitemlist').datagrid("insertRow", {
				    		index: 0,  // 索引从0开始
				    		row:{
				    			id:row.id,
								item_code : row.item_code,
								item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : row.dep_name,
								item_amount : row.item_amount,
								sex : row.sex,
								itemnum : row.itemnum,
								item_type:row.item_type,
								discount : row.discount,
								set_num : row.set_num,
								amount : row.amount,
								item_category:row.item_category,
								item_discount:row.item_discount
				    		}
						});
						
						countamt();
					} else {
						$.messager.confirm('提示信息', '[' + row.item_code + '-'
								+ row.item_name + ']冲突，是否添加？', function(r) {
							if (r) {
								$('#selectchangitemlist').datagrid("insertRow", {
						    		index: 0,  // 索引从0开始
						    		row:{
						    			id:row.id,
										item_code : row.item_code,
										item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
										dep_name : row.dep_name,
										item_amount : row.item_amount,
										sex : row.sex,
										itemnum : row.itemnum,
										item_type:row.item_type,
										discount : row.discount,
										set_num : row.set_num,
										amount : row.amount,
										item_category:row.item_category
						    		}
								});
								countamt();
							}
						});
					}
				} else {
					alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
				}
			}else {
				alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
			}
	   }				
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
	    //判断是否同步更新项目
	    var isgroupitemflag;
	    if(($('[name=isgroupitemflag]:checked').val()!=undefined)&&($('[name=isgroupitemflag]:checked').val()=='1')){
	    	isgroupitemflag=1;
	    }else{
	    	isgroupitemflag=0;
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
						amount : $("#amount").val(),
						maximum_amount:$("#maximum_amount").val(),  //分组最大金额限制
						amount : $("#amount").val(),
						isgroupitemflag:isgroupitemflag
					},
					type : "post",//数据发送方式   
					success : function(text) {
						$(".loading_div").remove();
						if (text.split("-")[0] == '0') {
							$.messager.alert("操作提示", text);
							var _parentWin =  window.opener ; 
							window.close();
							_parentWin.getgroupGrid(); // 主窗口getgroupGrid();刷新  
						} else {
							$.messager.alert("操作提示", text.split("-")[1], "error");
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
	var url = 'satlistshow.action';
	var data = {
		"setname" : set_Namess,
		"sex": document.getElementsByName("sex")[0].value
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
				$.messager.alert("操作提示", "套餐添加冲突！", "error");
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
function setChangItemListGridreload(){
	var model = {
			"setname" : $("#itemname").val(),
			"sex":$('#sex').combobox('getValue')
		};
	$("#changitemlist").datagrid('reload',model);
	$('#itemname').focus(); 
}


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
				pageSize: 10,//每页显示的记录条数，默认为10 
				pageList : [ 10, 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
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
					field : 'item_discount',
					title : '项目最大折扣',
					width : 18
				} ,{
					align : 'center',
					field : 'item_amount',
					title : '套餐金额',
					width : 15
				}]],
				onLoadSuccess : function(value) {
					$("#datatotal").val(value.total);
					var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
					if(is_show_discount==0){
						$("#changitemlist").datagrid("hideColumn", "item_discount"); // 设置隐藏列
						$("#changitemlist").datagrid("hideColumn", "item_amount"); // 设置隐藏列
					}
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
						row.discount = '10.0';
						
						if(Number(row.item_discount)>Number($("#discount").val())){
							row.discount = row.item_discount;
							row.amount = decimal(row.item_amount * row.itemnum
									* Number(row.item_discount) / 10, 2);
						}else{
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount * row.itemnum
									* $("#discount").val() / 10, 2);
						}
						
						insertitem(row);
					} else {
						
						if(Number(row.item_discount)>Number($("#discount").val())){
							row.discount = row.item_discount;
							row.amount = decimal(row.item_amount * row.itemnum
									* Number(row.item_discount) / 10, 2);
						}else{
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount * row.itemnum
									* $("#discount").val() / 10, 2);
						}
						insertitem(row);
					}
					$('#itemname').focus(); 
					$("#itemname").select();
				}
			});
	$("#changitemlist").datagrid({}).datagrid("keyCtr");
	//document.getElementById("itemname").value = '';
	document.getElementById("itemname").focus();
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
	var flag = true;// 不相等
	var selectitemcode = "";
	var itemtypeflag = true;
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;// 相等
			}
			if ((row.item_type != '')
					&& (row.item_type == rowsLength[j].item_type)) {
				itemtypeflag = false;
			}
			selectitemcode = selectitemcode + ",'" + row.item_code + "'";
		}// j End
	}
	if (flag == true) {
		var usersex = document.getElementsByName("sex")[0].value;
		var sexflag = false;
		// alert(usersex+"---"+row.sex);
		if (usersex == '') {
			sexflag = true;
		} else if (row.sex == '全部') {
			sexflag = true;
		} else if (usersex == row.sex) {
			sexflag = true;
		}
		if (sexflag) {
			if (itemtypeflag) {
				$('#selectchangitemlist').datagrid("appendRow", {
					item_code : row.item_code,
					item_name : row.item_name,
					dep_name : row.dep_name,
					item_amount : row.item_amount,
					sex : row.sex,
					itemnum : row.itemnum,
					discount : row.discount,
					set_num : row.set_num,
					amount : row.amount,
					item_category:row.item_category,
					item_discount:row.item_discount
				});
				countamt();
				//计算项目数量
				if("普通类型"==row.item_category){
					$("#commonType").html(parseInt($("#commonType").html())+1);
				}
				// $.messager.alert("操作提示", "添加成功！");
			} else {

				$.messager.confirm('提示信息', '[' + row.item_code + '-'
						+ row.item_name + ']冲突，是否添加？', function(r) {
					if (r) {
						$('#selectchangitemlist').datagrid("appendRow", {
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.item_amount,
							sex : row.sex,
							itemnum : row.itemnum,
							discount : row.discount,
							set_num : row.set_num,
							amount : row.amount,
							item_category:row.item_category
						});
						countamt();
						//计算项目数量
						if("普通类型"==row.item_category){
							$("#commonType").html(parseInt($("#commonType").html())+1);
						}
					}
				});

			}

		} else {
			// $.messager.alert("操作提示", "性别冲突，不能添加！", "error");
		}

	}
}

/**
 * 增加分组项目
 */
function insertitem(row) {
	console.log(row);
	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var selectitemcode = "";
	var itemtypeflag = true;
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;// 相等
			}
			if ((row.item_type != '')
					&& (row.item_type == rowsLength[j].item_type)) {
				itemtypeflag = false;
			}
			selectitemcode = selectitemcode + ",'" + rowsLength[j].item_code
					+ "'";
		}// j End
	}
	if (flag == true) {

		var usersex = document.getElementsByName("sex")[0].value;
		var sexflag = false;
		// alert(usersex+"---"+row.sex);
		if (usersex == '') {
			sexflag = true;
		} else if (row.sex == '全部') {
			sexflag = true;
		} else if (usersex == row.sex) {
			sexflag = true;
		}
		if (sexflag) {
			if (itemtypeflag) {
				$('#selectchangitemlist').datagrid("insertRow", {
		    		index: 0,  // 索引从0开始
		    		row:{
		    			id:row.id,
						item_code : row.item_code,
						item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
						dep_name : row.dep_name,
						item_amount : row.item_amount,
						sex : row.sex,
						itemnum : row.itemnum,
						item_type:row.item_type,
						discount : row.discount,
						set_num : row.set_num,
						amount : row.amount,
						item_category:row.item_category,
						item_discount:row.item_discount
		    		}
				});
				d_item.get_item_2(row.id);
				countamt();
				//计算项目数量
				if("普通类型"==row.item_category){
					$("#commonType").html(parseInt($("#commonType").html())+1);
				}
			} else {
				$.messager.confirm('提示信息', '[' + row.item_code + '-'
						+ row.item_name + ']冲突，是否添加？', function(r) {
					if (r) {
						$('#selectchangitemlist').datagrid("insertRow", {
				    		index: 0,  // 索引从0开始
				    		row:{
				    			id:row.id,
								item_code : row.item_code,
								item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : row.dep_name,
								item_amount : row.item_amount,
								sex : row.sex,
								itemnum : row.itemnum,
								item_type:row.item_type,
								discount : row.discount,
								set_num : row.set_num,
								amount : row.amount,
								item_category:row.item_category,
								item_discount:row.item_discount
				    		}
						});
						d_item.get_item_2(row.id);
						countamt();
						//计算项目数量
						if("普通类型"==row.item_category){
							$("#commonType").html(parseInt($("#commonType").html())+1);
						}
					}
				});
			}
		} else {
			alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
		}
	}else {
		alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
	}
}

// 计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if(rows.length!=undefined){
	if (rows.length != 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = decimal(Number(oldamt) + Number(row.item_amount)*Number(row.itemnum),2);			
			newamt = decimal(Number(newamt) + Number(row.amount),2);
		}//j End             
	}
	}
	$("#item_amount").textbox('setValue', oldamt+'');
	$("#amount").val(newamt);
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
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					//row.discount = disvar;
					if(Number(row.item_discount)>Number(disvar)){
						row.discount = row.item_discount;
						row.amount = decimal(Number(row.item_amount) * Number(row.itemnum) * Number(row.item_discount) / 10, 2);	
					}else{
						row.discount = disvar;
						row.amount = decimal(Number(row.item_amount) * Number(row.itemnum) * Number(disvar) / 10, 2);	
					}
					//$('#selectchangitemlist').datagrid('updateRow', {index: j,row: row});
					//$('#selectchangitemlist').datagrid("endEdit", j);
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt();
	}
}


//根据金额反算折扣
function disamout(disvar) {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var allDiscountAmount = 0; //项目总优惠额度
	var allAmount = 0; //总金额 
	//计算最大折扣金额 (添加数量的计算)
	if (rows.length != 0) {
		for (var w = 0; w <= rows.length - 1; w++){
			var row = rows[w];
			allDiscountAmount += decimal((10-Number(row.item_discount))/10*Number(row.item_amount)*Number(row.itemnum),2);
			allAmount +=decimal(Number(row.item_amount)*Number(row.itemnum),2);
		}
	}
	//alert("==最大优惠金额=="+allDiscountAmount+"==总金额=="+allAmount+"==项目优惠额度=="+Number(allAmount-disvar));
	if (!isFloat(disvar)) {
		$('#amount').textbox('setValue', $("#item_amount").val());
		$('#amount').textbox('textbox').focus();
		$.messager.alert("操作提示", "金额格式错误！", "error");
	} else if (Number(disvar) > Number($("#item_amount").val())) {
		$('#amount').textbox('setValue', $("#item_amount").val());
		$('#amount').textbox('textbox').focus();
		$.messager.alert("操作提示", "金额不能大于！"
				+ document.getElementById("item_amount").value + "元", "error");
	}else if (Number(disvar) < Number(allAmount-allDiscountAmount)) {
		//刷新金额
		$("#discount").val(10);
		discount(10);
		$.messager.alert("操作提示", "金额不能小于"+ Number(allAmount-allDiscountAmount) + "元", "error");
		return;
	} else {
		var newdiscont = decimal(Number(disvar)	/ Number($("#item_amount").val()) * 10, 4);
		$("#discount").val(newdiscont);
		
		var amounttotle=0;
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)// 已选择
			{
				var row = rows[j];
				//单项优惠金额
				var itemDiscount = Number(allAmount-disvar)/Number(allDiscountAmount)*Number(row.item_amount)*(10-Number(row.item_discount))/10;
//				row.discount = decimal((Number(row.item_amount)-Number(itemDiscount))/Number(row.item_amount)*10,2);
				row.discount = newdiscont;
				
				row.amount = decimal(Number(row.item_amount)-Number(itemDiscount),2);
				amounttotle=amounttotle+row.amount;
				$('#selectchangitemlist').datagrid('refreshRow', j);
			}// j End
			/*var trueamount= decimal(Number($("#amount").val()),2);
			var absmount=trueamount-amounttotle;
			if(absmount!=0){
				for (var j = 0; j <= rows.length - 1; j++)// 已选择
				{
					var row = rows[j];
					if(Math.abs(Number(absmount))<=Number(row.amount))
					{
						row.amount=	decimal(Number(row.amount)+Number(trueamount-amounttotle),2);
						$('#selectchangitemlist').datagrid('refreshRow', j);
						break;
					}
					
				}// j End
			}*/
		}

		var oldamount=$('#amount').val();
        var amounts=0;//总金额折后金额
    	
        var num=$('#selectchangitemlist').datagrid("getRows");//获取已添加的数据
        $.each(num,function(k,v){
            amounts+=v.amount;
        })
		var ceamt=decimal((oldamount-amounts),2);
        if(ceamt>0){
        	var indexk=-1;
        	var bdamt=100000;
        	 $.each(num,function(k,v){
        		 if(v.amount>1&&v.amount<bdamt){
        			 indexk=k;
        			 bdamt=v.amount;
					 //alert(bdamt);
        		 }
        	 })
        var fxamt=decimal($("#selectchangitemlist").datagrid("getRows")[indexk].amount,2);
		var newamt=decimal((fxamt+ceamt),2);
        $("#selectchangitemlist").datagrid("getRows")[indexk].amount=newamt;
		$('#selectchangitemlist').datagrid('refreshRow',indexk);
        }
		
		if(ceamt<0){
        	var indexk=-1;
        	var bdamt=0;
        	 $.each(num,function(k,v){
        		 if(v.amount>1&&v.amount>bdamt){
        			 indexk=k;
        			 bdamt=v.amount;
					 //alert(bdamt);
        		 }
        	 })
        var fxamt=decimal($("#selectchangitemlist").datagrid("getRows")[indexk].amount,2);
		var newamt=decimal((fxamt+ceamt),2);
        $("#selectchangitemlist").datagrid("getRows")[indexk].amount=newamt;
		$('#selectchangitemlist').datagrid('refreshRow',indexk);
        }
		
		//countamt2();
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
   
    var depositValue = decimal(Number(amountValue)/ (Number(invMoneyValue)* Number(itemnumValue)) * 10, 4);	
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
    
    rows[desindex].itemnum=itemnumValue; 
    rows[desindex].discount=discountValue; 
    rows[desindex].amount=depositValue;    // 折扣额  赋值
    $('#selectchangitemlist').datagrid('refreshRow', desindex);
    $("#selectchangitemlist").datagrid('endEdit', desindex); 
    //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
    countamt();
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
    }
    var depositValue = 0;
    //理论折扣大于实现折扣
    if(Number(rows[desindex].item_discount)>Number(discountValue)){
    	alert_autoClose("操作提示", "折扣金额不能小于理论金额", "error");
    	depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(rows[desindex].item_discount) /10, 2);	
    	rows[desindex].discount=rows[desindex].item_discount; 
    }else{
    	depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) /10, 2);	
    	rows[desindex].discount=discountValue; 
    }
    rows[desindex].itemnum=itemnumValue; 
    rows[desindex].amount=depositValue;    // 折扣额  赋值
    $('#selectchangitemlist').datagrid('refreshRow', desindex);
    $("#selectchangitemlist").datagrid('endEdit', desindex); 

    //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
    countamt();
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
							id:rows[i].id,
							item_code : rows[i].item_code,
							item_name :  isIndexOfSpan(rows[i].item_name),
							dep_name : rows[i].dep_name,
							item_amount : rows[i].item_amount,
							sex : rows[i].sex,
							itemnum : rows[i].itemnum,
							item_type:rows[i].item_type,
							discount : rows[i].discount,
							set_num : rows[i].set_num,
							amount : rows[i].amount,
							item_category:rows[i].item_category,
							item_discount:rows[i].item_discount
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
					if(rows[i].item_category=="普通类型"){
						var num = parseInt($("#commonType").html());
						if(num>0){
							$("#commonType").html(num-1);
						}
					}
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
		columns : [[
		 {
			align : "center",
			field : "",
			title : "ID",
			width : 15,
			hidden: true
		}, {
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
			field : 'item_discount',
			title : '理论折扣',
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
		},{
			align : 'center',
			field : 'item_category',
			title : '项目类型',
			width : 20,
			hidden: true
		}  ]],
		onLoadSuccess : function(value) {
			//var AllType = 0; //所有
			var commonType = 0; //普通类型
			for(var i=0;i<value.rows.length;i++){
				if("普通类型"==value.rows[i].item_category){
					commonType++;
				}
				//AllType++;
			}
			//$("#AllType").html(AllType);
			$("#commonType").html(commonType);
			$("#datatotal").val(value.total);
			
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#selectchangitemlist").datagrid("hideColumn", "item_discount"); // 设置隐藏列
				$("#selectchangitemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
				$("#selectchangitemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
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
		pageSize : 1000,
		height:'550',
		onDblClickRow  : function (rowIndex) {			    
	            if (lastIndex != rowIndex){
	            	$("#selectchangitemlist").datagrid('beginEdit', rowIndex); 
	            	calcAmt(rowIndex);
	            }  
	            lastIndex = -1;  
        }
	});
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

//初始化 删除项目
function initDeleteItemList(){
	$("#deleteItemList").datagrid({
		url : '',
		dataType : 'json',
		rownumbers : false,
		columns : [[ 
		{
			align : "center",
			field : "id",
			title : "ID",
			width : 15,
			hidden:true
		},{
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
			field : 'dep_name',
			title : '所属科室',
			width : 15,
			hidden:true
		}, {
			align : 'center',
			field : 'sex',
			title : '适用性别',
			width : 15,
			hidden:true
		},{
			align : 'center',
			field : 'item_type',
			title : '项目类型',
			width : 15,
			hidden:true
		}, {
			align : 'center',
			field : 'set_num',
			title : '套餐编码',
			width : 15
		}, {
			align : 'center',
			field : 'amount',
			title : '原金额',
			width : 15
		}, {
			align : 'center',
			field : 'item_discount',
			title : '理论折扣',
			width : 15
		},{
			align : 'center',
			field : 'discount',
			title : '折扣率',
			width : 15
		}, {
			align : 'center',
			field : 'itemnum',
			title : '数量',
			width : 15
		}, {
			align : 'center',
			field : 'item_amount',
			title : '套餐金额',
			width : 15
		}, {
			align : 'center',
			field : 'item_category',
			title : '项目类型',
			width : 15,
			hidden:true
		}]],
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
				row.discount = '10.0';
				if(Number(row.item_discount)>Number($("#discount").val())){
					row.discount = row.item_discount;
					row.amount = decimal(row.item_amount * row.itemnum
							* Number(row.item_discount) / 10, 2);
				}else{
					row.discount = $("#discount").val();
					row.amount = decimal(row.item_amount * row.itemnum
							* $("#discount").val() / 10, 2);
				}
				
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
				if(Number(row.item_discount)>Number($("#discount").val())){
					row.discount = row.item_discount;
					row.amount = decimal(row.item_amount * row.itemnum
							* Number(row.item_discount) / 10, 2);
				}else{
					row.discount = $("#discount").val();
					row.amount = decimal(row.item_amount * row.itemnum
							* $("#discount").val() / 10, 2);
				}
				
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
	});
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$("#deleteItemList").datagrid("hideColumn", "item_discount"); // 设置隐藏列
		$("#deleteItemList").datagrid("hideColumn", "discount"); // 设置隐藏列
		$("#deleteItemList").datagrid("hideColumn", "item_amount"); // 设置隐藏列
	}
}

function isgroupitemdo(){
	var isgroupitemflag=$("input[name='isgroupitemflag']").prop("checked");
	setCookie("isgroupitemflag",isgroupitemflag);
}
