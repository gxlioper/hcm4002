$.fn.extend({  
    
    resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {  
        var height = $(document.body).height() - heightMargin;  
        var width = $(document.body).width() - widthMargin;  
  
        height = height < minHeight ? minHeight : height;  
        width = width < minWidth ? minWidth : width;  
  
        $(this).datagrid('resize', {  
//            height : height,  
            width : width  
        });  
    }  
});  
$(function() {  
    // 当窗口大小发生变化时，调整DataGrid的大小  
    $(window).resize(function() {  
        $('#selectctlist').resizeDataGrid(20, 20, 300, 600);  
    });  
    $(window).resize(function() {  
        $('#changitemlist').resizeDataGrid(20, 20, 300, 600);  
    });  
    $(window).resize(function() {  
        $('#selectchangitemlist').resizeDataGrid(20, 20, 300, 600);  
    });  
});  
$(document).ready(function(){
});
var width=window.screen.availWidth;//(包括边线的宽)
var deltiemflags = 0;
var delsetflags = 0;
var countitemrow = 0;
var arrayjftype;
var zzhekou="";
var teamAmountViewFlag;
$(document).ready(
		function() {
			teamAmountViewFlag = $("#teamAmountViewFlag").val();
			
			if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
				$('#z_zhi').text("没有打折权限！");
			}else{
				$('#z_zhi').text("最大打折权限："+$('#web_Resource').val()+"折！");
			}
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$('#ttzkl').hide();// 设置隐藏列
				$('#ttzkhze').hide();// 设置隐藏列
				$('#discount').hide(); // 设置隐藏列
				$('#amount').hide();// 设置隐藏列
				$('#ttyuan').hide();// 设置隐藏列
			}
			
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
							 $('#changitemlist').datagrid("selectRow", 0);  //选择表格第一行
							 $('.datagrid-row-selected').attr('tabindex',"0");//获取焦点用
							 $('.datagrid-row-selected').focus();//获取焦
					 }
				} else {
					setChangIteListGridreload();
				}
			});
			$("#discount").bind('click',function(){
				zzhekou=$(this).val()
			})
			$("#discount").bind(
					'keyup',
					function(event) {
						var $amountInput = $(this);
						// 响应鼠标事件，允许左右方向键移动
						event = window.event || event;
						if (event.keyCode == 37 | event.keyCode == 39) {
							return;
						}
						// 先把非数字的都替换掉，除了数字和.
						$amountInput
								.val($amountInput.val().replace(/[^\d.]/g, "").
								// 只允许一个小数点
								replace(/^\./g, "").replace(/\.{2,}/g, ".").
								// 只能输入小数点后两位
								replace(".", "$#$").replace(/\./g, "").replace(
										"$#$", ".").replace(
										/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));
					});

			$("#discount").on('blur', function() {
				if ($('#discount').val() > 10) {
					$('#discount').val('10');
				}
				if ($("#discount").val() < 0) {
					$('#discount').val('10');
				}
				var $amountInput = $(this);
				// 最后一位是小数点的话，移除
				$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
				discount(this.value);
			});

			
			 $('#amount').bind('blur', function() { disamout(this.value); });
			 
			 
//			$('#serch_dep_id').combobox({
//				url : 'getDirectorDepList.action',
//				editable : true, //不可编辑状态
//				cache : false,
//				panelHeight : '300',//自动高度适合
//				valueField : 'id',
//				textField : 'dep_name',
//				onLoadSuccess : function(data) {
//					
//				},
//				onSelect: function(rec){    
//					setChangItemListGrid();
//		        }
//			});
			
			$('#results_contrast').mouseleave(function(){
				 $('#results_contrast').css('display', 'none');
			 });
		});

//复制他人 项目
//function teamGetCopyItem(){
//	$('#dlg-item-copy').dialog({
//		title:'复制项目',    
//		href: 'getTeamCopyItemPage.action',   
//	})
//	$('#dlg-item-copy').dialog('open');  
//}

/**
 * 模糊查询公司信息
 */
var hc_set_list, set_Namess;
function select_com_list(set_Namess) {
	var url = 'satlistshow.action';
	var data = {
		"setname" : set_Namess,
		"sex" : $("#custsex").val()
	};
	load_post(url, data, select_set_list_sess);
}

/**
 * 显示树形结构
 * 
 * @param data
 * @returns
 */
function select_set_list_sess(data) {
	mydtree = new dTree('mydtree', '../../../images/img/', 'no', 'no');
	mydtree.add(0, -1, "套餐列表", "javascript:void(0)", "根目录", "_self", false);
	for (var index = 0, l = data.length; index < l; index++) {
		if ((data[index].attributes == null) || (data[index].attributes == '')
				|| (data[index].attributes == '0')) {
			mydtree.add(data[index].id, 0, data[index].text,
					"javascript:setvalue(" + data[index].id + ",'"
							+ data[index].text + "')", data[index].text,
					"_self", false);
		} else {
			mydtree.add(data[index].id, data[index].attributes,
					data[index].text, "javascript:setvalue(" + data[index].id
							+ ",'" + data[index].text + "')", data[index].text,
					"_self", false);
		}
	}
	$("#com_name_list_div").html(mydtree.toString());
	$("#com_name_list_div").css("display", "block");
}

/**
 * 点击树设置内容
 * 
 * @param id
 * @param name
 * @returns
 */
function setvalue(id, name) {
	$.post("getExamOneShow.action", {
		"exam_set_id" : id
	}, function(jsonPost) {
		var userid = eval(jsonPost);
		delsetflags = 1;
		inserttc(userid);

	}, "json");
	$("#com_name_list_div").css("display", "none");
}

// 单位失去焦点
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

// -----------------------------------------选择套餐---------------------------------------------------
/**
 * 显示分组套餐信息
 */
function setselectGrid() {
	var model = {
		"exam_id" : $("#exam_id").val(),
		"exam_num":$('#exam_num').val()
	};
	$("#selectctlist").datagrid({
		url : 'exam_tclistshow.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : false,
		// pageSize: 8,//每页显示的记录条数，默认为10
		pageList : [ 10, 20, 30, 40, 10 ],// 可以设置每页记录条数的列表
		columns : [ [ {
			align : "center",
			field : "fxfz",
			title : "删除",
			width : 15,
			resizable:false,
			"formatter" : f_dellset
		}, {
			align : 'center',
			field : 'set_num',
			title : '套餐编码',
			resizable:false,
			width : 15
		}, {
			align : 'center',
			field : 'set_name',
			title : '套餐名称',
			resizable:false,
			width : 45
		}, {
			align : 'center',
			field : 'sex',
			title : '适用性别',
			resizable:false,
			width : 20
		}, {
			align : 'center',
			field : 'set_discount',
			resizable:false,
			title : '套餐折扣率',
			width : 30
		}, {
			align : 'center',
			field : 'set_amount',
			resizable:false,
			title : '套餐金额',
			width : 20
		} ] ],
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
 * 
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellset(val, row) {
	if (delsetflags == 0) {
		return '';
	} else {
		return '<a href=\"javascript:deletetc(\''
				+ row.set_num
				+ '\')\"><img src=\"../img/del.jpg\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}
}

// ----------------------------------------显示套餐-------------------------------------------------

/**
 * 移除套餐行
 */
function deletetc(set_numsss) {
	$.messager.confirm('提示信息', '确定删除此套餐吗？', function(r) {
		if (r) {
			var rowsLength = $('#selectctlist').datagrid('getRows');
			if (!rowsLength.length == 0) {
				var flag = true;// 不相等
				for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
				{
					if (set_numsss == rowsLength[j].set_num) {
						deletechargItem(set_numsss);
						var index = $('#selectctlist').datagrid('getRowIndex',
								rowsLength[j]);// 获取指定索引
						$('#selectctlist').datagrid('deleteRow', index);// 删除指定索引的行
						break;
					}
				}// j End
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
				var index1 = $('#selectchangitemlist').datagrid('getRowIndex',
						rows[i]);// 获取指定索引
				$('#selectchangitemlist').datagrid('deleteRow', index1);// 删除指定索引的行
			}
		}// j End
		countamt();
	}
}

/**
 * 增加套餐
 */
function inserttc(userid) {
	var rowsLength = $('#selectctlist').datagrid('getRows');
	var flag = true;// 不相等
	if (!rowsLength.length == 0) {
		var flag = true;// 不相等
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (userid.set_num == rowsLength[j].set_num) {
				flag = false;// 相等
				$.messager.alert("操作提示", "该套餐已添加", "error");
				break;
			} else {
				flag = true;
			}
		}// j End
	}
	if (flag == true) {
		var usersex = $("#custsex").val();
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
			insertsettiem(userid.set_num);
		} else {
			// alert("套餐性别冲突111，不能添加。");
		}
	}
}

// 修改折扣率 更新所有行
var disvar;
function discount(disvar) {
	if (!isFloat(disvar)) {
		document.getElementById("discount").value = 10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率格式错误！", "error");
	} else if (Number(disvar) > 10) {
		document.getElementById("discount").value = 10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率不能大于10！", "error");
	} else {
		if($('#web_Resource').val()==""|| $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			$('#discount').val(zzhekou);
			return;
		}
		
		if(Number(disvar)<$('#web_Resource').val() && Number(disvar) != zzhekou){
			$.messager.alert("提示信息","本操作员最大权限可打"+$('#web_Resource').val()+"折！","error");
			$('#discount').val(zzhekou);
			return;
		}
		
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j < rows.length-countitemrow; j++)// 已选择
			{
				var row = rows[j];
				$('#discount_input' + row.item_code).val(disvar);; //数量
				$('#personal_pay' + row.item_code).val(decimal(Number(row.item_amount)*Number($('#itemnum_input' + row.item_code).val())
						* Number(disvar) / 10, 2));			
			}
		}
		countamt();
	}
}


//根据金额反算折扣
function disamout(disvar) {
	if (!isFloat(disvar)) {
		document.getElementById("amount").value=document.getElementById("item_amount").value;
		document.getElementById("amount").focus();
		$.messager.alert("操作提示", "金额格式错误！", "error");
	}else if (Number(disvar) > Number(document.getElementById("item_amount").value)) {
		document.getElementById("amount").value=document.getElementById("item_amount").value;
		document.getElementById("amount").focus();
		$.messager.alert("操作提示", "金额不能大于！"+document.getElementById("item_amount").value+"元", "error");
	}else if(Number(disvar)<=0){
		
	}else{		
		//计算折扣后金额 不能小于添加的金额
		var rows = $('#selectchangitemlist').datagrid('getRows');
		var addCountMon = 0;
		//数量变化
		var itemnum_input_is=document.getElementsByName("itemnum_input");
		
		var tamtPay = 0, gamtPay = 0;
		if (!rows.length == 0) {
			for (var j =0; j < rows.length-countitemrow; j++)//已选择
			{
				var row = rows[j];
				addCountMon = decimal(addCountMon + Number(row.item_amount)*Number(itemnum_input_is[j].value),2);
			}//j End       
			
			for (var w = rows.length-countitemrow; w < rows.length; w++)//已选择
			{
				tamtPay = decimal(tamtPay + Number(rows[w].team_pay),2);
				gamtPay = decimal(gamtPay + Number(rows[w].personal_pay),2);
				
			}//j End 
		}
		var amountCha = decimal(Number(gamtPay+tamtPay+addCountMon-disvar),2);
		if(amountCha>addCountMon){
			for (var j =0; j < rows.length-countitemrow; j++){
				//个人分项金额
				var row = rows[j];
					row.discount = 10;
					row.personal_pay = row.item_amount;	
					row.itemnum = itemnum_input_is[j].value;
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}
			//计算 
			var teamPayAmou = 0;
			var personalPayAmou = 0;
			for (var m =0; m < rows.length; m++){
				//个人分项金额
					var row = rows[m];
					teamPayAmou  = decimal(teamPayAmou + Number(row.team_pay),2);
					personalPayAmou  = decimal(personalPayAmou + Number(row.personal_pay),2);
			}
			$.messager.alert("提示信息","折扣金额大于添加项目金额！","error");
			$("#amount").val(Number(teamPayAmou+personalPayAmou));
			//计算个人结算金额
			$("#personal_pay").val(Number(personalPayAmou));
			return;
		}
		
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 2);
		
		if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			//计算 
			var teamPayAmou = 0;
			var personalPayAmou = 0;
			for (var m =0; m < rows.length; m++){
				//个人分项金额
					var row = rows[m];
					teamPayAmou  = decimal(teamPayAmou + Number(row.team_pay),2);
					personalPayAmou  = decimal(personalPayAmou + Number(row.personal_pay),2);
			}
			$("#amount").val(Number(teamPayAmou+personalPayAmou));
			//$('#amount').val(jine);
			return;
		}
			
		 if(Number(newdiscont)<Number($('#web_Resource').val())){
				//资源
				$.messager.alert("提示信息","本操作人员最大权限只能打"+$('#web_Resource').val()+"折！","error");
				//$('#amount').val(jine);
				//计算 
				var teamPayAmou = 0;
				var personalPayAmou = 0;
				for (var m =0; m < rows.length; m++){
					//个人分项金额
						var row = rows[m];
						teamPayAmou  = decimal(teamPayAmou + Number(row.team_pay),2);
						personalPayAmou  = decimal(personalPayAmou + Number(row.personal_pay),2);
				}
				$("#amount").val(Number(teamPayAmou+personalPayAmou));
				return;
		}
		// newdiscont 为输入金额后改变的折扣率 为总折扣率
		document.getElementById("discount").value=newdiscont;
		
		//计算添加项目的折扣率
		var allAmount =  decimal(Number($("#item_amount").val())-Number(disvar),2); //减掉的总金额
		var addAmount = 0;
		if (!rows.length == 0) {
			for (var i =0; i < rows.length-countitemrow; i++){
				var row = rows[i];
				//所有添加项目的总额
				addAmount  = decimal(addAmount + Number(itemnum_input_is[i].value)*Number(row.item_amount),2);
			}  
			//添加分项 项目的折扣率
			var addDiscont = decimal(Number(addAmount-amountCha) / Number(addAmount) * 10, 2);
			//alert(Number(addAmount-amountCha) / Number(addAmount));
			var allPerAmount = 0;
			for (var j =0; j < rows.length-countitemrow; j++){
				var row = rows[j];
				//所有添加项目的总额
				row.discount = addDiscont;
				row.itemnum = itemnum_input_is[j].value;
				row.personal_pay = decimal(Number(itemnum_input_is[j].value)*Number(row.item_amount)* Number(addAmount-amountCha) / Number(addAmount), 2);	
				allPerAmount = decimal(Number(allPerAmount+Number(row.personal_pay)),2);
				$('#selectchangitemlist').datagrid('refreshRow', j);
			}   
			
			//个人结算总额
			$("#personal_pay").val(allPerAmount);
			
		}
	}
}


//计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var oldamt = 0, tamt = 0, gamt = 0;
	if (rows.length != 'undefined') {
		if (!rows.length == 0) {
			for (var j = rows.length-countitemrow; j < rows.length; j++)// 已选择
			{
				var row = rows[j];				
				oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum), 2);				
				tamt = decimal(tamt + Number(row.team_pay),2);
				gamt = decimal(gamt +Number(row.personal_pay),2);
			}// j End
		}
	}	
	//console.log("======="+gamt+"-------"+tamt);
	var itemnum_inputss =document.getElementsByName("itemnum_input");  //数量输入
	var personal_pay_inputss=document.getElementsByName("personal_pay_input");
	//var team_pay_inputss=document.getElementsByName("team_pay_input");
	if(rows.length-countitemrow>0){
		for(var i=0;i<rows.length-countitemrow;i++){
			oldamt = decimal(oldamt +  Number(rows[i].item_amount)*Number(itemnum_inputss[i].value),2);
			gamt = decimal(gamt + Number(personal_pay_inputss[i].value),2);
			//tamt = decimal(tamt + Number(team_pay_inputss[i].value),2);
			//console.log("==222总共费用==="+tamtInput+"==团体单项费用=="+Number(team_pay_inputss[i].value)+"===个人总共费用==="+gamtInput+"==个人单项费用=="+Number(personal_pay_inputss[i].value));
			//console.log("======="+gamt+"-------"+oldamt);
		}
	}
	/*var personal_pay_inputss=document.getElementsByName("personal_pay_input");
	if (personal_pay_inputss!= 'undefined') {
	for(var i=0;i<personal_pay_inputss.length;i++)
		{
		gamt = decimal(gamt + Number(personal_pay_inputss[i].value),2);
		}
	}*/

	$("#item_amount").val(oldamt); //原总额
	$("#personal_pay").val(gamt);  // 个人结算总额
	$("#amount").val(decimal(tamt + gamt,2));  //折扣后总额
	
}

// 选择套餐插入收费项目
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
				deltiemflags = 1;
				tcinsertitem(userid[i]);
			} else {
				userid[i].discount = $("#discount").val();
//				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
//						* $("#discount").val() / 10, 2);
				deltiemflags = 1;
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
	var itemtypeflag=true;
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;// 相等
			}
			if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
				itemtypeflag=false;
			}
			selectitemcode = selectitemcode + ",'" + row.item_code + "'";
		}// j End
	}
	if (flag == true) {
		var usersex = $("#custsex").val();
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
			if(itemtypeflag){
				$('#selectchangitemlist').datagrid("insertRow", {
					index: 0,  // 索引从0开始
		    		row:{
		    			id:row.id,
						item_code : row.item_code,
						item_name : row.item_name,
						dep_name :  row.dep_name,
						item_category : row.item_category,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						item_type:row.item_type,
						team_pay :0,
						itemnum:row.itemnum,
						exam_indicators:'G',
						personal_pay:row.amount
		    		}
				});
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 $('#selectchangitemlist').datagrid("insertRow", {
							index: 0,  // 索引从0开始
				    		row:{
				    			id:row.id,
								item_code : row.item_code,
								item_name : "<span style='color:blue;'>"+ row.item_name+"</span>",
								dep_name :  "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount : row.item_amount,
								sex : row.sex,
								discount : row.discount,
								set_num : row.set_num,
								item_type:row.item_type,
								team_pay :0,
								itemnum:row.itemnum,
								exam_indicators:'G',
								personal_pay:row.amount
				    		}
						});
						countamt();
					 }
				 });
			}
		} else {
			// $.messager.alert("操作提示", "性别冲突，不能添加！", "error");
		}

	}
}

//不修改折后总金额
function countamt3() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var tamt = 0, gamt = 0;
	var itemamout=0;
	if (!rows.length == 0) {
		for (var j = rows.length-countitemrow; j <= rows.length - 1; j++)// 已选择
		{
			var row=rows[j];
			
			tamt = decimal(tamt + Number(row.team_pay),2);
			gamt = decimal(gamt + Number(row.personal_pay), 2);
			itemamout = decimal(itemamout+Number(row.item_amount)*Number(row.itemnum), 2);
			/*console.log("=====row.team_pay======="+row.team_pay+"==row.personal_pay=="+row.personal_pay+
					"--------row.item_amount----"+row.item_amount+"=======row.itemnum======="+row.itemnum);*/
		}
		
		/*for (var j = countitemrow; j < rows.length; j++)// 已选择
		{
			var row=rows[j];
			itemamout = decimal(itemamout +Number(row.item_amount)*Number($('#itemnum_input' + row.item_code).val()), 2);
		}*/
	}
	
	//console.log("=====原总额======="+itemamout+"==个人付费=="+gamt+"--------团体付费----"+tamt)
		
	var addGamt = 0;
	var yuanGamont = 0; //新加项原金额
	var personal_pay_inputss=document.getElementsByName("personal_pay_input");
	var personal_pay_itemnum=document.getElementsByName("itemnum_input");
	if (personal_pay_inputss!= 'undefined') {
	for(var i=0;i<personal_pay_inputss.length;i++)
		{
		gamt = decimal(gamt + Number(personal_pay_inputss[i].value),2);
		addGamt = decimal(addGamt + Number(personal_pay_inputss[i].value),2); //新增个人付费
		yuanGamont = decimal(yuanGamont + Number(personal_pay_itemnum[i].value)*Number(rows[i].item_amount),2); 
		}
	}
	//console.log("=====原总额======="+itemamout+"==个人付费=="+gamt+"--------新增个人付费----"+addGamt+"=====原添加金额===="+yuanGamont)
	$("#personal_pay").val(gamt);
	$("#amount").val(decimal(tamt+gamt,2)); //折扣后总金额
	$("#item_amount").val(decimal(yuanGamont+itemamout,2));
	//计算折扣率
	if($("#isDefaultTen").val()=="Y"){
		$("#discount").val(10);
	}else{
		$("#discount").val(decimal(Number(tamt+gamt) / Number(yuanGamont+itemamout) * 10, 2));
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
			var itemtypeflag=true;
			if (!rowsLength.length == 0) {
				for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
				{
					if (row.item_code == rowsLength[j].item_code) {
						flag = false;// 相等
					}
					if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
						itemtypeflag=false;
					}
					selectitemcode = selectitemcode + ",'" + rowsLength[j].item_code
							+ "'";
				}// j End
			}
			if (flag == true) {
				var usersex = $("#custsex").val();
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
					if(itemtypeflag){
						$('#selectchangitemlist').datagrid("insertRow", {
							index: 0,  // 索引从0开始
				    		row:{
				    			id:row.id,
								item_code : row.item_code,
								item_name : "<span style='color:blue;'>"+ row.item_name+"</span>",
								dep_name :  "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount : row.amount,
								sex : row.sex,
								discount : $("#discount").val(),
								set_num : row.set_num,
								item_type:row.item_type,
								team_pay :0,
								itemnum:row.itemnum,
								exam_indicators:'G',
								personal_pay:row.item_amount
				    		}
						});
						countamt();
					}else{
						 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
					     if(r){
					    	 $('#selectchangitemlist').datagrid("insertRow", {
									index: 0,  // 索引从0开始
						    		row:{
						    			id:row.id,
										item_code : row.item_code,
										item_name : "<span style='color:blue;'>"+ row.item_name+"</span>",
										dep_name :  "<span style='color:blue;'>"+row.dep_name+"</span>",
										item_category : row.item_category,
										item_amount : row.amount,
										sex : row.sex,
										discount : $("#discount").val(),
										set_num : row.set_num,
										item_type:row.item_type,
										team_pay :0,
										itemnum:row.itemnum,
										exam_indicators:'G',
										personal_pay:row.item_amount
						    		}
								});
								countamt();
							 }
						 });
					}	
				} else {
					alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
					$('#itemname').focus(); 
					$("#itemname").select();

				}
			} else {
				alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
				$('#itemname').focus(); 
				$("#itemname").select();

			}
	   }
}
/**
 * 保存修改
 */
function djtcustadd() {
	var itemrows = $('#selectchangitemlist').datagrid('getRows');
	if (itemrows.length > 0) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var itemrows = $('#selectchangitemlist').datagrid('getRows');
		var itementities = "";
		for (i = 0; i < itemrows.length-countitemrow; i++) {
			var itemobject=itemrows[i];

			var discountValue = $('#discount_input' + itemobject.item_code).val();
			itemobject.discount=discountValue;
			var personal_pay_input = $('#personal_pay' + itemobject.item_code).val();
			itemobject.personal_pay=personal_pay_input;
			itemobject.team_pay=0;
			itemobject.itemnum= $('#itemnum_input' + itemobject.item_code).val();
			itemobject.exam_indicators='G';		
			itemobject.amount=Number(itemobject.personal_pay)+Number(itemobject.team_pay);
			itemobject.item_name = isIndexOfSpan(itemobject.item_name);
			itemobject.dep_name = isIndexOfSpan(itemobject.dep_name);
			itementities = itementities + JSON.stringify(itemobject);
		}

		//console.log(itementities);
		var setrows = $('#selectctlist').datagrid('getRows');
		var setentities = "";
		for (i = 0; i < setrows.length; i++) {
			setentities = setentities + JSON.stringify(setrows[i]);
		}

		$.ajax({
			url : 'djtTGcustSaveItemSet.action',
			data : {
				exam_id : $("#exam_id").val(),
				exam_num:$('#exam_num').val(),
				itementities : itementities,
				setentities : setentities,
				discount : $("#discount").val(),
				amount : $("#amount").val(),
				item_amount : $("#item_amount").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
					window.opener.location.reload(true); 
					window.close();
					var _parentWin =  window.opener ; 
					_parentWin.setselectListGrid();
					_parentWin.reapplydjtlispacs();
					window.close();
				} else {
					$.messager.alert("操作提示", text.split("-")[1], "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");					
			}
		});
	} else {
		$.messager.alert("操作提示", "无效收费项目", "error");
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
				var index1 = $('#selectchangitemlist').datagrid('getRowIndex',
						rows[i]);// 获取指定索引
				$('#selectchangitemlist').datagrid('deleteRow', index1);// 删除指定索引的行
			}
		}// j End
		countamt();
	}
}

// ----------------------------------------显示收费项目-------------------------------------------------
function setChangIteListGridreload(){
	var model = {
			"setname" : $("#itemname").val(),
			"sex" : $("#custsex").val()
//			"dep_id":$('#serch_dep_id').combobox('getValue')
		};
	$("#changitemlist").datagrid('reload',model);
}
/**
 * 显示体检项目套餐信息
 */
function setChangItemListGrid() {
	var model = {
		"setname" : $("#itemname").val(),
		"sex" : $("#custsex").val()
//		"dep_id":$('#serch_dep_id').combobox('getValue')
	};
	$("#changitemlist").datagrid(
			{
				url : 'changitemlist.action',
				dataType : 'json',
				queryParams : model,
				rownumbers : false,
				pageSize : 10,// 每页显示的记录条数，默认为10
				pageList : [ 10, 30, 60, 90, 120, 150 ],// 可以设置每页记录条数的列表
				columns : [ [ {
					align : 'left',
					field : 'item_code',
					title : '项目编码',
					resizable:false,
					width : 15
				}, {
					align : 'left',
					field : 'item_category',
					title : '项目类型',
					resizable:false,
					width : 15,
					hidden:true
				},{
					align : 'left',
					field : 'item_name',
					title : '项目名称',
					resizable:false,
					width : 40,
					"formatter":f_showitem
				}, {
					align : 'left',
					field : 'dep_name',
					title : '所属科室',
					resizable:false,
					width : 25
				}, {
					align : 'center',
					field : 'sex',
					title : '性别',
					resizable:false,
					width : 10
				}, {
					align : 'center',
					field : 'item_amount',
					title : '金额',
					resizable:false,
					width : 10
				} ,
				{align : 'center',field : 'id',title : '操作',width : 10,"formatter":operate}] ],
				onLoadSuccess : function(value) {
					$("#datatotal").val(value.total);
					$('#itemname').focus();
				},
				
				singleSelect : true,
				collapsible : true,
				pagination : true,
				fitColumns : true,
				striped : true
//				onDblClickRow : function(index, row) {
//					row.set_num = '0';
//					row.itemnum=1;
//					if (!isFloat($("#discount").val())) {
//						alert('折扣率格式错误！');
//						document.getElementById("discount").focus();
//					} else if (Number($("#discount").val()) > 10) {
//						alert('折扣率不能大于10！');
//						document.getElementById("discount").focus();
//					} else if (($("#discount").val() == '10')
//							|| ($("#discount").val() == '10.0')
//							|| ($("#discount").val() == '10.00')) {
//						row.discount = '10';
//						row.amount = decimal(row.item_amount*row.itemnum
//								* $("#discount").val() / 10, 2);
//						deltiemflags = 1;
//						insertitem(row);
//					} else {
//						row.discount = $("#discount").val();
//						row.amount = decimal(row.item_amount*row.itemnum
//								* $("#discount").val() / 10, 2);
//						deltiemflags = 1;
//						insertitem(row);
//					}
//					
//					$('#itemname').focus(); 
//					$("#itemname").select();
//
//					// countamt();
//				}
			});
	
	//		$("#changitemlist").datagrid({}).datagrid("keyCtr");
	// document.getElementById("itemname").value = '';
	// document.getElementById("itemname").focus();
}
function operate(value, row, index) {
	 return ['<button type="button" class="RoleOfedita btn btn-primary" style="margin-right:15px;background: #359bcc;" onclick = "editUser('+index+');">加项</button>'
            
	            ].join('')
}
function editUser( index) {
	 $('#changitemlist').datagrid('selectRow',index);
	 var row = $('#changitemlist').datagrid('getSelected');
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

/**
 * 增加分组项目
 */
function insertitem(row) {
	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var selectitemcode = "";
	var itemtypeflag=true;
	if (!rowsLength.length == 0) {
		for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
		{
			if (row.item_code == rowsLength[j].item_code) {
				flag = false;// 相等
			}
			if((row.item_type!='')&&(row.item_type==rowsLength[j].item_type)){
				itemtypeflag=false;
			}
			selectitemcode = selectitemcode + ",'" + rowsLength[j].item_code
					+ "'";
		}// j End
	}
	if (flag == true) {
		var usersex = $("#custsex").val();
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
			if(itemtypeflag){
				$('#selectchangitemlist').datagrid("insertRow", {
		    		 index: 0,  // 索引从0开始
		    		 row:{
		    			id:row.id,
		    			item_code : row.item_code,
						item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
						dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
						item_category : row.item_category,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						item_type:row.item_type,
						team_pay :0,
						itemnum:row.itemnum,
						exam_indicators:'G',
						personal_pay:row.amount
		    		 }
					});
				d_item.get_item_2(row.id);
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 $('#selectchangitemlist').datagrid("insertRow", {
			    		 index: 0,  // 索引从0开始
			    		 row:{
			    			 	id:row.id,
			    			 	item_code : row.item_code,
								item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount : row.item_amount,
								sex : row.sex,
								discount : row.discount,
								set_num : row.set_num,
								item_type:row.item_type,
								team_pay :0,
								itemnum:row.itemnum,
								exam_indicators:'G',
								personal_pay:row.amount
			    		 }
						});
			    	 	d_item.get_item_2(row.id);
						countamt();
					 }
				 });
			}	
		} else {
			alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
			$('#itemname').focus(); 
			$("#itemname").select();

		}
	} else {
		alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		$('#itemname').focus(); 
		$("#itemname").select();

	}
}


/**
 * 删除收费项目
 * 
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellchargitem(val, row) {
	if (deltiemflags == 0) {
		return '';
	} else {
		return '<a href=\"javascript:deletechargitemOne(\''
				+ row.item_code
				+ '\')\"><img src=\"../img/del.jpg\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}
}
/**
 * 删除收费项目
 */
function deletechargitemOne(set_numsss) {
	/*$.messager.confirm('提示信息', '确定删除此收费项目吗？',function(r) {
				if (r) {}
			})*/
			var rows = $('#selectchangitemlist').datagrid('getRows');
//			
//			//判断delete表中是否有数据
//			var isDeleteFlag = false;
//			var rowDelete = $('#deleteItemList').datagrid('getRows');
//			for (var m = rowDelete.length - 1; m >= 0; m--) {
//				if (set_numsss == rowDelete[m].item_code) {
//					//有该条数据
//					isDeleteFlag = true;
//					break;
//				}
//			}
			
//			if(!isDeleteFlag){
//				//先插入  再删除
//				for (var i = rows.length - 1; i >= 0; i--) {
//					if (set_numsss == rows[i].item_code) {
//						$('#deleteItemList').datagrid('insertRow',{
//							index: 0,  // 索引从0开始
//							row: {
//								id : rows[i].id,
//								item_code : rows[i].item_code,
//								item_name :  isIndexOfSpan(rows[i].item_name),
//								dep_name :  isIndexOfSpan(rows[i].dep_name),
//								item_category : rows[i].item_category,
//								item_amount :  rows[i].item_amount,
//								sex :  rows[i].sex,
//								discount : rows[i].discount,
//								itemnum: rows[i].itemnum,
//								personal_pay:rows[i].personal_pay,
//								exam_indicators:rows[i].exam_indicators,
//								team_pay:rows[i].team_pay,
//								item_type:rows[i].item_type,
//								amount :  rows[i].amount
//								
//							} 
//						});
//						
//						break;
//					}
//				}
//			}else{
//				$.messager.alert("提示信息","删除项目中已有该条信息","error");
//			}
			
			if (!rows.length == 0) {
				for (var i = rows.length - 1; i >= 0; i--) {
					if (set_numsss == rows[i].item_code) {
						var index1 = $('#selectchangitemlist')
								.datagrid('getRowIndex', rows[i]);// 获取指定索引
						$('#selectchangitemlist').datagrid('deleteRow',
								index1);// 删除指定索引的行
						break;
					}
				}// j End
			}
			countamt();
		
}
/**
 * 显示体检项目套餐信息
 */
var lastIndex;
function setfzchareitemListGrid() {
	var model = {
		"id" : $("#exam_id").val(),
		"exam_num":$('#exam_num').val()
	};
	$("#selectchangitemlist").datagrid({
		url : 'custchangitemlist.action',
		dataType : 'json',
		queryParams : model,
		// pageSize: 8,//每页显示的记录条数，默认为10
		pageList : [ 10, 20, 30, 40, 10 ],// 可以设置每页记录条数的列表
		columns : [ [ 
		          {align : "center",field : "fxfzddd",title : "删除",	width : 15,resizable:false,"formatter" : f_dellchargitem}, 
		          {align : 'left',field : 'item_code',title : '项目编码',width : 26,resizable:false}, 
		          {align : 'center',field : 'item_type',title : '项目类型',width : 20,resizable:false},
		          {align : 'center',field : 'item_category',title : '项目类型',width : 20,resizable:false,"formatter" : f_itemCategory,hidden:true},
		          {align : 'left',field : 'item_name',title : '项目名称',width : 35,resizable:false},
//		          {align : 'left',field : 'dep_name',title : '科室',width : 25,resizable:false}, 
		          {align : 'center',field : 'item_amount',title : '原金额',width : 20,resizable:false}, 
		          {align : 'center',field : 'id',title : 'ID',width : 10,resizable:false,hidden:true}, 
		          {align : 'center',field : 'itemnum',title : '数量',width : 15,resizable:false,"formatter" : f_itemnum}, 
		          {align : 'center',field : 'discount',title : '折扣率',width : 20,resizable:false,"formatter" : f_discount}, 
		          {align : 'center',field : 'exam_indicators',title : '付费方式',resizable:false,width : 25,"formatter" : f_exam_indicators}, 
		          {align : 'center',field : 'team_pay',title : '团体付费',resizable:false,width : 25,"formatter" : f_team_pay}, 
		          {align : 'center',field : 'personal_pay',title : '个人金额',	resizable:false,width : 26,"formatter" : f_personal_pay}
		          ] ],
		onLoadSuccess : function(value) {
			$("#selectchangitemlist").datagrid("hideColumn", "item_type"); // 设置隐藏列
			$("#datatotal").val(value.total);
			var data = $('#selectchangitemlist').datagrid('getData');
			countitemrow = data.rows.length;
			countamt();
			var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
			if(is_show_discount==0){
				$("#selectchangitemlist").datagrid("hideColumn", "dep_name"); // 设置隐藏列
				$("#selectchangitemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
				$("#selectchangitemlist").datagrid("hideColumn", "team_pay"); // 设置隐藏列
				$("#selectchangitemlist").datagrid("hideColumn", "personal_pay"); // 设置隐藏列
			}
		},
		singleSelect : true,
		collapsible : true,
		pagination : true,
		fitColumns : true,
		autowidth : true,
		striped : true,
		pagination : false,
		beginEdit : true,
		rownumbers : false,
		height:'550',
		pageNumber : 1,
		pageSize : 1000

	});
}

function f_itemCategory(val,row){
	if(val=="" || val==null){
		return "";
	}else{
		return "<span id=\""+row.item_code+"_item_category\">"+val+"</span>";
	}
}

var shuliang="";
function f_itemnum(val, row) {
	if (deltiemflags == 0) {
		return row.itemnum;
	} else {
		return '<input type=\"text\" name=\"itemnum_input\" id=\"itemnum_input'
				+ row.item_code
				+ '\" maxlength=\"3\" size=\"3\"  onclick=\"z_shuliang(this)\" onblur=\"f_itemnum_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',this.value);\" value=\"' + row.itemnum + '\" />';
	}
}
function z_shuliang(value){
	shuliang=$(value).val();
	if (!isSZZoo(shuliang)){
		 shuliang=1;
	}
}

var row_itemnum;
function f_itemnum_input(row_item_code, row_item_amt,row_itemnum) {
	//获得该项目的类型
	var itemCategory = $('#'+row_item_code+'_item_category' ).html();
	if("普通类型"==itemCategory){
		if(row_itemnum==0 || row_itemnum>1){
			$.messager.alert("提示信息","普通类型数量只能为1","error");
			$("#itemnum_input"+row_item_code).val(1);
			return;
		}
	}else if("耗材类型"==itemCategory){
		if(row_itemnum==0){
			$.messager.alert("提示信息","耗材类型数量至少为1","error");
			$("#itemnum_input"+row_item_code).val(1);
			return;
		}
	}
	var invMoneyValue = row_item_amt; // 金额值
	var discountValue = $('#discount_input' + row_item_code).val(); // 折扣率 值
	var itemnumValue=row_itemnum;
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
		
	exam_indicators_input=$('#exam_indicatorss' + row_item_code).val();	

		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
			$('#personal_pay' + row_item_code).val(depositValue);
	 countamt3();
}

var xzhekou="";
function f_discount(val, row) {
	if (deltiemflags == 0) {
		if(teamAmountViewFlag==1 || row.exam_indicator=='G') {
			return row.discount;
		} else {
			return "*";
		}
		//return row.discount;
	} else {
		return '<input type=\"text\" name=\"discount_input\" id=\"discount_input'
				+ row.item_code
				+ '\" maxlength=\"3\" size=\"3\"  onclick=\"z_zhekou(this)\" onblur=\"f_discount_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',this.value);\" value=\"' + row.discount + '\" />';
	}
}
function z_zhekou(value){
	xzhekou=$(value).val();
}
var row_item_code;
var row_item_amt;
var discount_input;
function f_discount_input(row_item_code, row_item_amt,discount_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var discountValue = discount_input; // 折扣率 值
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }

	if($('#web_Resource').val()=="" ||  $('#web_Resource').val()==undefined){
		$.messager.alert("提示信息","没有打折权限！","error");
		$('#discount_input'+row_item_code).val(xzhekou);
		discountValue=xzhekou;
	}
	//资源
	if(Number(discountValue) < $('#web_Resource').val() && Number(discountValue) != xzhekou){
		$.messager.alert("提示信息","本操作员最大权限可打"+$('#web_Resource').val()+"折！","error");
		$('#discount_input'+row_item_code).val(xzhekou);
		discountValue=xzhekou;
	}
	
	exam_indicators_input=$('#exam_indicatorss' + row_item_code).val();
	invMoneyValue=decimal(Number(invMoneyValue)*Number(itemnumValue), 2);
	if (isNaN(Number(discountValue))) {
		discountValue = 10;
		$('#discount_input' + row_item_code).val(discountValue);
	}
	if ((Number(discountValue) > 10)||(Number(discountValue)<0)) {
		discountValue = 10;
		depositValue = decimal(Number(invMoneyValue)
				* Number(discountValue)/10, 2);		
		$('#discount_input' + row_item_code).val(discountValue);		
		$('#personal_pay' + row_item_code).val(depositValue);		
	} else {
		var depositValue = decimal(Number(invMoneyValue)
				* Number(discountValue) / 10, 2);
			$('#personal_pay' + row_item_code).val(depositValue);
	}
	 countamt3();
}

function f_exam_indicators(val, row) {
	if (deltiemflags == 0) {
		return row.exam_indicators;
	} else {
		return '个人加项';		
	}
}

function f_team_pay(val, row) {
	if (deltiemflags == 0) {
		if(teamAmountViewFlag==1 || row.exam_indicator=='G') {
			return row.team_pay;
		} else {
			return "*";
		}
	} else {
		return row.team_pay;
	}
}

var xjine="";
function f_personal_pay(val, row) {
	if (deltiemflags == 0) {
		return row.personal_pay;
	} else {
		return '<input type=\"text\" name=\"personal_pay_input\" id=\"personal_pay'
				+ row.item_code
				+ '\"  maxlength=\"10\" size=\"5\" onclick=\"x_jine(this)\" onchange=\"f_personal_pay_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',this.value);\" value=\"' + row.personal_pay + '\" />';
	}
}
function x_jine(value){
	xjine=$(value).val();
}
var personal_pay_input;
function f_personal_pay_input(row_item_code, row_item_amt,personal_pay_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }	
	var discountValue = $('#discount_input' + row_item_code).val();
	invMoneyValue=decimal(Number(invMoneyValue)*Number(itemnumValue), 2);
	if (isNaN(Number(personal_pay_input))) {
		$('#personal_pay' + row_item_code).val(invMoneyValue);
		$('#discount_input' + row_item_code).val(10);
	} else {		
		if (Number(personal_pay_input) > invMoneyValue) {	
			$('#personal_pay' + row_item_code).val(invMoneyValue);
			$('#discount_input' + row_item_code).val(10);
		} else if (Number(personal_pay_input) < 0) {
			$('#personal_pay' + row_item_code).val(invMoneyValue);
			$('#discount_input' + row_item_code).val(10);
		}else{
			var depositValue = decimal(Number(personal_pay_input)/Number(invMoneyValue) *10, 1);
			if($('#web_Resource').val()=="" ||  $('#web_Resource').val()==undefined){
				$.messager.alert("提示信息","没有打折权限！","error");
				$('#personal_pay'+row_item_code).val(xjine);
				return;
			}
			//资源
			if(depositValue < $('#web_Resource').val() && $('#personal_pay'+row_item_code).val() != xjine){
				$.messager.alert("提示信息","本操作员最大权限可打"+$('#web_Resource').val()+"折！","error");
				$('#personal_pay'+row_item_code).val(xjine);
				return;
			}
			
			$('#discount_input' + row_item_code).val(depositValue);
		}
	}
	countamt3();
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
					   {align : 'left',field : 'item_name',title : '项目名称',width :40,"formatter":f_showitem},
					   {align : 'left',field : 'dep_name',title : '所属科室',width : 22}, 
					   {align : 'left',field : 'sex',title : '性别',width : 10},
					   {align : 'center',field : 'item_amount',title : '原金额',width : 10},
					   {align : 'center',field : 'id',title : 'ID',width : 10,hidden:true},
					   {align : 'center',field : 'itemnum',title : '数量',width : 10,hidden:true}, 
					   {align : 'center',field : 'item_type',title : '类型',width : 10,hidden:true},
					   {align : 'center',field : 'item_category',title : '项目类型',width : 20,hidden:true},
					   {align : 'center',field : 'discount',title : '折扣率',width : 10}, //
					   {align : 'center',field : 'exam_indicators',title : '付费方式',width : 15,hidden:true}, 
					   {align : 'center',field : 'personal_pay',title : '折扣金额',	width : 16}
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
		$("#deleteItemList").datagrid("hideColumn", "personal_pay"); // 设置隐藏列
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
