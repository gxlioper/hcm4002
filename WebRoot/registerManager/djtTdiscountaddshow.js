var deltiemflags = 0;
var delsetflags = 0;
var countitemrow = 0;
var arrayjftype;
var zzhekou="";
var web_Resource = "";
var teamAmountViewFlag;
$(document).ready(
		function() {
			teamAmountViewFlag = $("#teamAmountViewFlag").val();			
			setfzchareitemListGrid();		
			$('#itemname').keyup(function(event) {
				if(event.keyCode=='38'||event.keyCode=='40'){
					 var s = $('#changitemlist').datagrid("getRows"); 
					 if(s.length>0){
							 $('#changitemlist').datagrid("selectRow", 0);  //选择表格第一行
							 $('.datagrid-row-selected').attr('tabindex',"0");//获取焦点用
							 $('.datagrid-row-selected').focus();//获取焦
					 }
				} else {
					setChangItemListGridreload();
				}
			});

			$("#discount").bind("click",function(){
				zzhekou=$(this).val();
			});
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

//复制他人 项目
function teamGetCopyItem(){
	$('#dlg-item-copy').dialog({
		title:'复制项目',    
		href: 'getTeamCopyItemPage.action',   
	})
	$('#dlg-item-copy').dialog('open');  
}

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
 * 校验用户名密码是否正确
 */
function testuser(){
	var userName = $('#user_name').val();
	var user_password = $('#user_password').val();
	if(userName==null ||userName==""){
		$.messager.alert("提示信息","用户名不能为空","error");
		return;
	}
	if(user_password==null ||user_password==""){
		$.messager.alert("提示信息","密码不能为空","error");
		return;
	}
	$.ajax({
		url:'getUserByUserName.action?userName='+userName+'&user_password='+user_password,
		type:'post',
	    success:function(data){
	    	var dd = eval('('+data+')');
	    	if(dd[0]=="校验成功!"){
	    		$.messager.alert("提示信息",dd[0],"");
	    		if(dd[1]==null){
	    			$('#maxdiscount').text("最大打折权限：10折！");
	    		}else{
	    			$('#maxdiscount').text("最大打折权限："+dd[1]+"折！");
	    		}
		    	
	    	}else{
	    		$.messager.alert("提示信息",dd[0],"");
		    	$('#maxdiscount').text("");
	    	}
	    	
	    	//$('#discount').val(dd[1]);
	    	web_Resource=dd[1];
	    	//check(dd[1]);
		},error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
   });
}

/**
 * 显示树形结构
 * 
 * @param data
 * @returns
 */
function select_set_list_sess(data) {
	mydtree = new dTree('mydtree', '../../images/img/', 'no', 'no');
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



// 修改折扣率 更新所有行
var disvar;
function discount(disvar) {
	if(web_Resource=="" || web_Resource==undefined){
		$.messager.alert("提示信息","没有打折权限，请先输入用户名密码进行校验！","error");
		$('#discount').val(zzhekou);
		return;
	}
	
	if(Number(disvar)<web_Resource){
		$.messager.alert("提示信息","本操作员最大权限可以打"+web_Resource+"折！","error");
		$('#discount').val(web_Resource);
		return;
	}	
	if (!isFloat(disvar)) {
		document.getElementById("discount").value = 10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率格式错误！", "error");
	} else if (Number(disvar) > 10) {
		document.getElementById("discount").value = 10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率不能大于10！", "error");
	} else {
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j =0 ; j < rows.length; j++)// 已选择
			{
				var row = rows[j];
				var xmdiscount=row.item_discount;//收费项目表折扣率
				if(Number(disvar)<Number(xmdiscount)){
					$.messager.alert("操作提示", ""+row.item_name+"--折扣率小于收费项目折扣率，要按照收费项目折扣率执行！", "");
					if(row.personal_pay>0){
						row.discount=xmdiscount;
						row.team_pay=0;
						row.personal_pay=decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}else{
						row.personal_pay=0;
						row.discount=xmdiscount;
						row.team_pay=decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}
				}else{
					if(row.personal_pay>0){
						row.discount=disvar;
						row.team_pay=0;
						row.personal_pay=decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}else{
						row.personal_pay=0;
						row.discount=disvar;
						row.team_pay=decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}
				}
				
				$('#selectchangitemlist').datagrid('refreshRow', j);
							
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
		var tamtPay = 0, gamtPay = 0;
		if (!rows.length == 0) {
			for (var w = 0; w < rows.length; w++)//已选择
			{
				tamtPay = decimal(tamtPay + Number(rows[w].team_pay),2);
				gamtPay = decimal(gamtPay + Number(rows[w].personal_pay),2);
			}//j End 
		}
		//优惠金额
		
		//新折扣
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 4);
		if(web_Resource=="" || web_Resource==undefined){
			$.messager.alert("提示信息","没有打折权限，请先输入用户名密码进行校验！","error");
			//$('#amount').val(jine);
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
			
		 if(Number(newdiscont)<Number(web_Resource)){
				//资源
				$.messager.alert("提示信息","此用户最大权限只能打"+web_Resource+"折！","error");
				//$('#amount').val(jine);
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
		var teamPayAmou = 0;
		var personalPayAmou = 0;
		if (!rows.length == 0) {
			for (var j =0; j < rows.length; j++){
				var row = rows[j];
				//判断是团体结算  个人结算
				var xmdiscount=row.item_discount;//收费项目表折扣率
				if(Number(newdiscont)<Number(xmdiscount)){
					$.messager.alert("操作提示", ""+row.item_name+"--折扣率小于收费项目折扣率，要按照收费项目折扣率执行！", "");
					if("团体付费"==row.exam_indicators){
						row.discount = xmdiscount;
						row.team_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);	
						row.personal_pay = 0;
					}else if("个人付费"==row.exam_indicators){
						row.discount = xmdiscount;
						row.team_pay = 0;
						row.personal_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}
				}else{
					if("团体付费"==row.exam_indicators){
						row.discount = newdiscont;
						row.team_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);	
						row.personal_pay = 0;
					}else if("个人付费"==row.exam_indicators){
						row.discount = newdiscont;
						row.team_pay = 0;
						row.personal_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(row.discount) / 10, 2);
					}
				}
				
				$('#selectchangitemlist').datagrid('refreshRow', j);
				teamPayAmou  = decimal(teamPayAmou + Number(row.team_pay),2);
				personalPayAmou  = decimal(personalPayAmou + Number(row.personal_pay),2);
			}
			$("#team_pay").val(teamPayAmou);
			$("#personal_pay").val(personalPayAmou);
			$("#amount").val(Number(teamPayAmou+personalPayAmou));
		}
	}
}


// 计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var oldamt = 0, tamt = 0, gamt = 0;
	if (rows.length != 'undefined') {
		if (!rows.length == 0) {
			for (var j = rows.length-countitemrow; j < rows.length; j++)// 已选择
			{
				var row = rows[j];
				oldamt = decimal(oldamt +  Number(row.item_amount)*Number(row.itemnum),2);
			    tamt = decimal(tamt + Number(row.team_pay),2);
			    gamt = decimal(gamt + Number(row.personal_pay),2);
			}// j End
		}
	}	
	var newdiscount=decimal(decimal(tamt+gamt,2) * 10 / Number(oldamt), 4);
	$("#discount").val(newdiscount);
	$("#item_amount").val(oldamt);
	$("#team_pay").val(tamt);
	$("#personal_pay").val(gamt);
	$("#amount").val(decimal(tamt+gamt,2)); 
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
				    row: {
				    	id:row.id,
						item_code : row.item_code,
						item_name : row.item_name,
						dep_name : row.dep_name,
						item_category : row.item_category,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						itemnum:row.itemnum,
						item_type:row.item_type,
						team_pay : row.amount,
						exam_indicators:'T',
						personal_pay:0
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
								team_pay : row.amount,
								exam_indicators:'T',
								personal_pay:0
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

// 不修改折后总金额
function countamt3() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var tamt = 0, gamt = 0;
	var itemamout=0;
	if (!rows.length == 0) {
		for (var j = rows.length; j <= rows.length - 1; j++)// 已选择
		{   
			var row=rows[j];
			tamt = decimal(tamt + Number(row.team_pay),2);
			gamt = decimal(gamt + Number(row.personal_pay),2);
			itemamout = decimal(itemamout + Number(row.item_amount)*Number(row.itemnum),2);
		}
		/*for (var j = countitemrow; j < rows.length; j++)// 已选择
		{
			var row=rows[j];
			itemamout = decimal(itemamout + Number(row.item_amount)*Number($('#itemnum_input' + row.item_code).val()),2);
		}*/
	}
	
	var addTamt = 0;
	var addGamt = 0;
	var personal_pay_inputss=document.getElementsByName("personal_pay_input");
	var team_pay_inputss=document.getElementsByName("team_pay_input");
	var itemnum_input_is=document.getElementsByName("itemnum_input"); //数量变化
	if (personal_pay_inputss!= 'undefined') {
	for(var i=0;i<personal_pay_inputss.length;i++)
		{
		gamt = decimal(gamt + Number(personal_pay_inputss[i].value),2);
		tamt = decimal(tamt + Number(team_pay_inputss[i].value),2);
		addTamt = decimal(addTamt + Number(team_pay_inputss[i].value),2);//新增单位折扣付费
		addGamt = decimal(addGamt + Number(personal_pay_inputss[i].value),2);//新增个人折扣付费
		itemamout = decimal(itemamout + Number(rows[i].item_amount)*Number(itemnum_input_is[i].value),2);
		}
	}
	//console.log("==个人结算总额==="+gamt+"--单位结算总额---"+tamt+"==新增单位折扣付费==="+addTamt+"==新增个人折扣付费=="+addGamt+"==="+itemamout);
	// itemamout 原项目单位结算总额
	$("#team_pay").val(tamt);  //单位结算总额
	$("#personal_pay").val(gamt);  //个人结算总额
	$("#amount").val(decimal(tamt+gamt,2));  //折扣后金额
	$("#item_amount").val(itemamout); //原总额
	//计算折扣率
	if($("#isDefaultTen").val()=="Y"){
		$("#discount").val(10);
	}else{
		$("#discount").val(decimal(Number(tamt+gamt) / Number(itemamout) * 10, 4));
	}
}

//根据金额修改折扣
function countamt4() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;// 不相等
	var tamt = 0, gamt = 0;
	var itemamout=0;
	if (!rows.length == 0) {
		for (var j = rows.length-countitemrow; j <= rows.length - 1; j++)// 已选择
		{   
			var row=rows[j];
			tamt = decimal(tamt + Number(row.team_pay),2);
			gamt = decimal(gamt + Number(row.personal_pay),2);
			itemamout = decimal(itemamout + Number(row.item_amount)*Number(row.itemnum),2);
		}
	}
	
	var addTamt = 0;
	var addGamt = 0;
	var personal_pay_inputss=document.getElementsByName("personal_pay_input");
	var team_pay_inputss=document.getElementsByName("team_pay_input");
	var itemnum_input_is=document.getElementsByName("itemnum_input"); //数量变化
	if (personal_pay_inputss!= 'undefined') {
	for(var i=0;i<personal_pay_inputss.length;i++)
		{
		gamt = decimal(gamt + Number(personal_pay_inputss[i].value),2);
		tamt = decimal(tamt + Number(team_pay_inputss[i].value),2);
		addTamt = decimal(addTamt + Number(team_pay_inputss[i].value),2);//新增单位折扣付费
		addGamt = decimal(addGamt + Number(personal_pay_inputss[i].value),2);//新增个人折扣付费
		itemamout = decimal(itemamout + Number(rows[i].item_amount)*Number(itemnum_input_is[i].value),2);
		}
	}
	// itemamout 原项目单位结算总额
	$("#team_pay").val(tamt);  //单位结算总额
	$("#personal_pay").val(gamt);  //个人结算总额
	//$("#amount").val(decimal(tamt+gamt,2));  //折扣后金额
	$("#item_amount").val(itemamout); //原总额
	//计算折扣率
	$("#discount").val(decimal(Number(tamt+gamt) / Number(itemamout) * 10, 4));
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
		for (i = 0; i < itemrows.length; i++) {
			var itemobject=itemrows[i];

			var discountValue = $('#discount_input' + itemobject.item_code).val();
			itemobject.discount=discountValue;
			var personal_pay_input = $('#personal_pay' + itemobject.item_code).val();
			itemobject.personal_pay=personal_pay_input;
			var team_pay_input = $('#team_pay' + itemobject.item_code).val();
			itemobject.team_pay=team_pay_input;
			itemobject.itemnum= $('#itemnum_input' + itemobject.item_code).val();
			var exam_indicators_input=$('#exam_indicatorss' + itemobject.item_code).val();
			itemobject.exam_indicators=exam_indicators_input;
			itemobject.amount=Number(itemobject.personal_pay)+Number(itemobject.team_pay);
			itemobject.item_name = isIndexOfSpan(itemobject.item_name);
			itemobject.dep_name = isIndexOfSpan(itemobject.dep_name);
			itementities = itementities + JSON.stringify(itemobject);
		}
		//console.log(itementities);


		$.ajax({
			url : 'djtTTcustSaveDiscount.action',
			data : {
				exam_id : $("#exam_id").val(),
				itementities : itementities,
				discount : $("#discount").val(),
				amount : $("#amount").val(),
				item_amount : $("#item_amount").val(),
				exam_num :$("#exam_num").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
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
function setChangItemListGridreload(){
	var model = {
			"setname" : $("#itemname").val(),
			"sex" : $("#custsex").val(),
			"dep_id":$('#serch_dep_id').combobox('getValue')
		};
	$("#changitemlist").datagrid('reload',model);
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
						    row: {
						    	id:row.id,
						    	item_code : row.item_code,
								item_name : "<span style='color:blue;'>"+row.item_name+"</span>",
								dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
								item_category : row.item_category,
								item_amount :row.amount,
								sex : row.sex,
								discount :$("#discount").val(),
								set_num : row.set_num,
								item_type:row.item_type,
								itemnum:row.itemnum,
								team_pay :row.item_amount,
								exam_indicators:'T',
								personal_pay:0
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
										item_amount :row.amount,
										sex : row.sex,
										discount :$("#discount").val(),
										set_num : row.set_num,
										item_type:row.item_type,
										itemnum:row.itemnum,
										team_pay :row.item_amount,
										exam_indicators:'T',
										personal_pay:0
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
 * 增加分组项目
 */
function insertitem(row) {
	//console.log(row);
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
				    row: {
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
				    	itemnum:row.itemnum,
				    	team_pay : row.amount,
				    	exam_indicators:'T',
				    	personal_pay:0
				    }
				});
				 d_item.get_item_2(row.id)
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 $('#selectchangitemlist').datagrid("insertRow", {
							index: 0,  // 索引从0开始
						    row: {
						    	item_code : row.item_code,
						    	item_name :  "<span style='color:blue;'>"+row.item_name+"</span>",
						    	dep_name : "<span style='color:blue;'>"+row.dep_name+"</span>",
						    	item_category : row.item_category,
						    	item_amount : row.item_amount,
						    	sex : row.sex,
						    	discount : row.discount,
						    	set_num : row.set_num,
						    	item_type:row.item_type,
						    	itemnum:row.itemnum,
						    	team_pay : row.amount,
						    	exam_indicators:'T',
						    	personal_pay:0
						    }
						});
				    	 d_item.get_item_2(row.id)
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
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
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
								item_amount :  rows[i].item_amount,
								sex :  rows[i].sex,
								discount : rows[i].discount,
								itemnum: rows[i].itemnum,
								personal_pay:rows[i].personal_pay,
								exam_indicators:rows[i].exam_indicators,
								team_pay:rows[i].team_pay,
								item_type:rows[i].item_type,
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
		"exam_num" : $("#exam_num").val()
	};
	$("#selectchangitemlist").datagrid({
		url : 'custchangitemlist.action',
		dataType : 'json',
		queryParams : model,
		// pageSize: 8,//每页显示的记录条数，默认为10
		pageList : [ 10, 20, 30, 40, 10 ],// 可以设置每页记录条数的列表
		columns : [ [ 
		    /*{align : "center",field : "fxfzddd",title : "删除",width : 10,"formatter" : f_dellchargitem},*/ 
		    {align : 'left',field : 'item_code',title : '项目编码',width : 15}, 
		    {align : 'center',field : 'item_type',title : '项目类型',width : 20},
		    {align : 'center',field : 'item_category',title : '项目类型',width : 20,"formatter" : f_itemCategory ,hidden:true},
		    {align : 'left',field : 'item_name',title : '项目名称',width : 40}, 
		    {align : 'left',field : 'dep_name',title : '科室',width : 25}, 
		    {align : 'center',field : 'item_amount',title : '原金额',width : 10}, 
		    {align : 'center',field : 'id',title : 'ID',width : 10,hidden:true},
		    {align : 'center',field : 'itemnum',title : '数量',width : 15,"formatter" : f_itemnum},
		    {align : 'center',field : 'discount',title : '折扣率',width : 15,"formatter" : f_discount}, //刘翔
		    {align : 'center',field : 'exam_indicators',title : '付费方式',width : 15,	"formatter" : f_exam_indicators},
		    {align : 'center',field : 'team_pay',title : '单位金额',width : 15,"formatter" : f_team_pay}, 
		    {align : 'center',field : 'personal_pay',title : '个人金额',width : 16,"formatter" : f_personal_pay},
		    {align : 'center',hidden : true,field : 'item_discount',title : '收费项目折扣率',width : 10}
		    ] ],
		onLoadSuccess : function(value) {
			$("#selectchangitemlist").datagrid("hideColumn", "item_type"); // 设置隐藏列  
			$("#datatotal").val(value.total);
			var data = $('#selectchangitemlist').datagrid('getData');
			countitemrow = data.rows.length;
			countamt();

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
		return '<input type=\"text\" name=\"itemnum_input\" id=\"itemnum_input'
				+ row.item_code
				+ '\" maxlength=\"3\" size=\"5\"  onclick=\"z_shuliang(this)\" onblur=\"f_itemnum_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',\''
				+ row.exam_indicators
				+ '\',this.value);\" value=\"' + row.itemnum + '\" />';
}
function z_shuliang(value){
	shuliang=$(value).val();
	if (!isSZZoo(shuliang)){
		 shuliang=1;
	}
}

var row_itemnum;
var row_exam_indicators;
function f_itemnum_input(row_item_code, row_item_amt, row_exam_indicators,row_itemnum) {
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
	var exam_indicators=row_exam_indicators;
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
		
	//var exam_indicators_input=$('#exam_indicatorss' + row_item_code).val();
	var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
			* Number(discountValue) / 10, 2);
	if(exam_indicators=='团体付费'){
		$('#team_pay' + row_item_code).val(depositValue);
		$('#personal_pay' + row_item_code).val(0);
	}else if(exam_indicators=='个人付费'){
		$('#team_pay' + row_item_code).val(0);
		$('#personal_pay' + row_item_code).val(depositValue);
	}else{
		$('#team_pay' + row_item_code).val(depositValue);
		$('#personal_pay' + row_item_code).val(0);
	}
	
	 countamt3();
}

var xxzk="";
function f_discount(val, row) {
		return '<input type=\"text\" name=\"discount_input\" id=\"discount_input'
				+ row.item_code
				+ '\" maxlength=\"3\" size=\"5\"  onclick=\"clikezhekou(this.value);\" onblur=\"f_discount_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',\''
				+ row.exam_indicators
				+ '\',\''
				+ row.item_discount
				+ '\',this.value);\" value=\"' + row.discount + '\" />';
}
function clikezhekou(value){
	xxzk=value;
}
var row_item_code;
var row_item_amt;
var discount_input;
var row_exam_indicators;
var row_item_discount;
function f_discount_input(row_item_code, row_item_amt, row_exam_indicators,row_item_discount, discount_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var discountValue = discount_input; // 折扣率 值
	var exam_indicators = row_exam_indicators; //收费方式
	var item_discount = row_item_discount;//项目折扣
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
//alert("invMoneyValue"+invMoneyValue+"discountValue"+discountValue+"itemnumValue"+itemnumValue);
	if( web_Resource==""||web_Resource==undefined){
		$.messager.alert("提示信息","没有打折权限，请先输入用户名密码进行校验！！","error");
		$('#discount_input'+row_item_code).val(xxzk);
		return;
	}
	if((Number(discountValue))<web_Resource){
		$.messager.alert("提示信息","本操作员最大权限可打"+web_Resource+"折！","error");
			$('#discount_input'+row_item_code).val(web_Resource);
			return;
	}
	if (isNaN(Number(discountValue))) {
		discountValue = 10;
		$('#discount_input' + row_item_code).val(discountValue);
	}

	if ((Number(discountValue) > 10)||(Number(discountValue) < 0)) {
		discountValue = 10;
		depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue), 2);	
		$('#discount_input' + row_item_code).val(discountValue);
		if(exam_indicators=='团体付费'){
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}else if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
			$('#personal_pay' + row_item_code).val(depositValue);
		}else{
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}			
	}
	if(Number(discountValue)<Number(item_discount)){
		$.messager.alert("操作提示", ""+row_item_code+"--折扣率小于收费项目折扣率，要按照收费项目折扣率执行！", "");
		$('#discount_input' + row_item_code).val(item_discount);
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(item_discount) / 10, 2);
		if(exam_indicators=='团体付费'){
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}else if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
			$('#personal_pay' + row_item_code).val(depositValue);
		}else{
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}
	}else{
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
		if(exam_indicators=='团体付费'){
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}else if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
			$('#personal_pay' + row_item_code).val(depositValue);
		}else{
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}
	}
		
	//}
	 countamt3();
}

function f_exam_indicators(val, row) {
	if (deltiemflags == 0) {
		return row.exam_indicators;
	} else {
		
		var indsstr = '<select name=\"indicators_input\"  id=\"exam_indicatorss'+ row.item_code	+ '\"  onchange=\"f_exam_indicators_input(\''
		+ row.item_code + '\',' + row.item_amount
		+ ',this.value);\">';
		     if(row.exam_indicators=='G'){
		    	 indsstr =indsstr+'<option selected value =\"G\">个人付费</option>';
		     }else{
		    	 indsstr =indsstr+'<option value =\"G\">个人付费</option>';
		     }
		     
		     if(row.exam_indicators=='T'){
		    	 indsstr =indsstr+'<option selected value =\"T\">团体付费</option>';
		     }else{
		    	 indsstr =indsstr+'<option value =\"T\">团体付费</option>';
		     }
		     
		     if(row.exam_indicators=='GT'){
		    	 indsstr =indsstr+'<option selected value =\"GT\">混合付费</option>';
		     }else{
		    	 if($("#isShowHhPay").val()=="Y"){
		    		 indsstr =indsstr+'<option value =\"GT\">混合付费</option>';
		    	 }
		     }
		    
		     indsstr =indsstr+'</select>';
		return indsstr;
	}
}

var exam_indicators_input;
function f_exam_indicators_input(row_item_code, row_item_amt, exam_indicators_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
	 
	var discountValue = $('#discount_input' + row_item_code).val();
	personal_pay_input = $('#personal_pay' + row_item_code).val();
	team_pay_input = $('#team_pay' + row_item_code).val();	
	if (exam_indicators_input == 'G') {
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
		$('#team_pay' + row_item_code).val(0);
		$('#personal_pay' + row_item_code).val(depositValue);		
	}else if (exam_indicators_input == 'T') {
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
		$('#team_pay' + row_item_code).val(depositValue);
		$('#personal_pay' + row_item_code).val(0);
	}
	countamt3();
}

function f_team_pay(val, row) {
		return '<input type=\"text\" name=\"team_pay_input\" id=\"team_pay'
				+ row.item_code
				+ '\"  maxlength=\"10\" size=\"10\" onchange=\"f_team_pay_input(\''
				+ row.item_code + '\',' + row.item_amount
				+ ',\''
				+ row.exam_indicators
				+ '\',\''
				+ row.item_discount
				+ '\',this.value);\"  value=\"' + row.team_pay + '\" />';
}

var team_pay_input;
var row_exam_indicators;
var row_item_discount;
function f_team_pay_input(row_item_code, row_item_amt, row_exam_indicators, row_item_discount, team_pay_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var exam_indicators = row_exam_indicators; // 付费方式
	var item_discount = row_item_discount; // 项目折扣
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
	var discountValue = $('#discount_input' + row_item_code).val();
	personal_pay_input = $('#personal_pay' + row_item_code).val();
	//exam_indicators_input=$('#exam_indicatorss' + row_item_code).val();
	if( web_Resource==""||web_Resource==undefined){
		$.messager.alert("提示信息","没有打折权限，请先输入用户名密码进行校验！！","error");
		//$('#discount_input'+row_item_code).val(10);
		
		if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
		}else{
			$('#team_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) / 10, 2));
		}
		return;
	}

	if (isNaN(Number(team_pay_input))) {
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
		if(exam_indicators=='团体付费'){
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}else if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
			$('#personal_pay' + row_item_code).val(depositValue);
		}else{
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}
		
	} else {
		var zhekoulvqx =10;
		if( web_Resource!=undefined && web_Resource!=""){
			zhekoulvqx=decimal(Number(web_Resource), 4);
		}
		
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)* Number(discountValue) / 10, 2);
		if (Number(team_pay_input) > depositValue) {
			if(exam_indicators=='团体付费'){		
				var dzsize = decimal(Number(team_pay_input)/Number(invMoneyValue) * 10, 1);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					$('#team_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#personal_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(10);
				}else{
					$('#personal_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(dzsize);					
			    }				
			}else if(exam_indicators=='个人付费'){
				var dzsize = decimal(Number(team_pay_input)/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					//$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#team_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
				}else{
					//$('#personal_pay' + row_item_code).val(team_pay_input);
					$('#team_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
			    }				
			}else{//混合收费
				var dzsize = decimal((Number(team_pay_input)+Number(personal_pay_input))/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					$('#team_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#personal_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(10);
				}else{
					$('#personal_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(dzsize);					
			    }	
				
			}
		} else if (Number(team_pay_input) < depositValue) {
			if(exam_indicators=='团体付费'){
				var dzsize = decimal(Number(team_pay_input)/Number(itemnumValue)/Number(invMoneyValue) * 10, 4);
				if((Number(dzsize))<web_Resource){
					$.messager.alert("提示信息","本操作员最大权限可打"+web_Resource+"折！","error");
						//$('#discount_input'+row_item_code).val(10);
						//$('#team_pay' + row_item_code).val(depositValue);
						return;
				}
				if(Number(dzsize)<Number(item_discount)){
					$.messager.alert("操作提示", ""+row_item_code+"--折扣率小于收费项目折扣率，要按照收费项目折扣率执行！", "");
					if((zhekoulvqx>dzsize)||(dzsize>10)){
						$('#team_pay' + row_item_code).val(decimal(Number(Number(invMoneyValue)*Number(itemnumValue)
								* Number(item_discount) / 10, 2)));
						$('#personal_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(item_discount);
					}else{
						$('#personal_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(item_discount);
				    }	
				}else{
					if((zhekoulvqx>dzsize)||(dzsize>10)){
						$('#team_pay' + row_item_code).val(team_pay_input);
						$('#personal_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(dzsize);
					}else{
						$('#personal_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(dzsize);
				    }	
				}
					
				
			}else if(exam_indicators=='个人付费'){
				var dzsize = decimal(Number(team_pay_input)/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					//$('#personal_pay' + row_item_code).val(personal_pay_input);
					$('#team_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
				}else{
					//$('#personal_pay' + row_item_code).val(team_pay_input);
					$('#team_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
			    }	
			}else{//混合收费				
				var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)* Number(discountValue) / 10, 2);
				if(team_pay_input>depositValue)
				{				
					$('#team_pay' + row_item_code).val(depositValue);
					$('#personal_pay' + row_item_code).val(0);
				}else{
					$('#team_pay' + row_item_code).val(team_pay_input);
					var teampays=decimal(Number(depositValue)-Number(team_pay_input), 2);
					$('#personal_pay' + row_item_code).val(teampays);				
			    }	
			}
		}else{
			if(exam_indicators=='团体付费'){
				$('#team_pay' + row_item_code).val(depositValue);
				$('#personal_pay' + row_item_code).val(0);
			}else if(exam_indicators=='个人付费'){
				$('#team_pay' + row_item_code).val(0);
				$('#personal_pay' + row_item_code).val(depositValue);
			}else{
				$('#personal_pay' + row_item_code).val(decimal(depositValue-Number(team_pay_input),2));
			}
		}
	}
	countamt3();
}

function f_personal_pay(val, row) {
		return '<input type=\"text\" name=\"personal_pay_input\" id=\"personal_pay'
				+ row.item_code
				+ '\"  maxlength=\"10\" size=\"10\" onchange=\"f_personal_pay_input(\''
				+ row.item_code
				+ '\','
				+ row.item_amount
				+ ',\''
				+ row.exam_indicators
				+ '\',\''
				+ row.item_discount
				+ '\',this.value);\" value=\"' + row.personal_pay + '\" />';
}

var personal_pay_input;
var row_exam_indicators;
var row_item_discount;
function f_personal_pay_input(row_item_code, row_item_amt, row_exam_indicators, row_item_discount,  personal_pay_input) {
	var invMoneyValue = row_item_amt; // 金额值
	var exam_indicators = row_exam_indicators; // 付费方式
	var item_discount = row_item_discount; // 项目折扣
	var itemnumValue=$('#itemnum_input' + row_item_code).val(); //数量
	 if (!isSZZoo(itemnumValue)){
		 itemnumValue=1;
	    }
	var discountValue = $('#discount_input' + row_item_code).val();
	team_pay_input = $('#team_pay' + row_item_code).val();
	//exam_indicators_input=$('#exam_indicatorss' + row_item_code).val();
	if( web_Resource==""||web_Resource==undefined){
		$.messager.alert("提示信息","没有打折权限，请先输入用户名密码进行校验！！","error");
		//$('#discount_input'+row_item_code).val(10);
		//$('#team_pay' + row_item_code).val(0);
		//$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) / 10, 2));
		if(exam_indicators=='团体付费'){
			$('#personal_pay' + row_item_code).val(0);
		}else{
			$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(discountValue) / 10, 2));
		}
		return;
	}
	if (isNaN(Number(personal_pay_input))) {
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)
				* Number(discountValue) / 10, 2);
		if(exam_indicators=='团体付费'){
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}else if(exam_indicators=='个人付费'){
			$('#team_pay' + row_item_code).val(0);
			$('#personal_pay' + row_item_code).val(depositValue);
		}else{
			$('#team_pay' + row_item_code).val(depositValue);
			$('#personal_pay' + row_item_code).val(0);
		}
	}else{
		var zhekoulvqx =10;
		if( web_Resource!=undefined && web_Resource!=""){
			zhekoulvqx=decimal(Number(web_Resource),4);
		}
		
		var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)* Number(discountValue) / 10, 2);
		if (Number(personal_pay_input) > depositValue) {
			if(exam_indicators=='团体付费'){
				var dzsize = decimal(Number(personal_pay_input)/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					//$('#team_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#personal_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
				}else{
					$('#personal_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(dzsize);					
			    }			
			}else if(exam_indicators=='个人付费'){
				var dzsize = decimal(Number(personal_pay_input)/Number(itemnumValue)/Number(invMoneyValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#team_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(10);
				}else{
					$('#personal_pay' + row_item_code).val(personal_pay_input);
					$('#team_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(dzsize);
			    }	
			}else{
				var dzsize = decimal((Number(team_pay_input)+Number(personal_pay_input))/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#team_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(10);
				}else{
					$('#team_pay' + row_item_code).val(0);
					$('#discount_input' + row_item_code).val(dzsize);					
			    }	
			}
		} else if (Number(personal_pay_input) <depositValue) {
			if(exam_indicators=='团体付费'){
				var dzsize = decimal(Number(personal_pay_input)/Number(invMoneyValue)*Number(itemnumValue) * 10, 4);
				if((zhekoulvqx>dzsize)||(dzsize>10)){
					//$('#team_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue), 2));
					$('#personal_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(10);
				}else{
					$('#personal_pay' + row_item_code).val(0);
					//$('#discount_input' + row_item_code).val(dzsize);
			    }		
			}else if(exam_indicators=='个人付费'){
				var dzsize = decimal(Number(personal_pay_input)/Number(itemnumValue)/Number(invMoneyValue) * 10, 4);
				if((Number(dzsize))<web_Resource){
					$.messager.alert("提示信息","本操作员最大权限可打"+web_Resource+"折！","error");
						//$('#discount_input'+row_item_code).val(10);
						//$('#personal_pay' + row_item_code).val(depositValue);
						return;
				}
				if(Number(dzsize)<Number(item_discount)){
					$.messager.alert("操作提示", ""+row_item_code+"--折扣率小于收费项目折扣率，要按照收费项目折扣率执行！", "");
					if((zhekoulvqx>dzsize)||(dzsize>10)){
						$('#personal_pay' + row_item_code).val(decimal(Number(Number(invMoneyValue)*Number(itemnumValue)
								* Number(item_discount) / 10, 2)));
						$('#team_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(item_discount);
					}else{
						$('#personal_pay' + row_item_code).val(decimal(Number(invMoneyValue)*Number(itemnumValue) * Number(item_discount) / 10, 2));
						$('#discount_input' + row_item_code).val(item_discount);
				    }	
				}else{
					if((zhekoulvqx>dzsize)||(dzsize>10)){
						$('#personal_pay' + row_item_code).val(personal_pay_input);
						$('#team_pay' + row_item_code).val(0);
						$('#discount_input' + row_item_code).val(dzsize);
					}else{
						$('#personal_pay' + row_item_code).val(personal_pay_input);
						$('#discount_input' + row_item_code).val(dzsize);
				    }	
				}
			}else{//混合收费	
				var depositValue = decimal(Number(invMoneyValue)*Number(itemnumValue)* Number(discountValue) / 10, 2);
				if(personal_pay_input>depositValue)
				{				
					$('#personal_pay' + row_item_code).val(depositValue);
					$('#team_pay' + row_item_code).val(0);
				}else{
					$('#personal_pay' + row_item_code).val(personal_pay_input);
					var teampays=decimal(Number(depositValue)-Number(personal_pay_input), 2);
					$('#team_pay' + row_item_code).val(teampays);				
			    }	
			}
		}else{
			if(exam_indicators=='团体付费'){
				$('#team_pay' + row_item_code).val(depositValue);
				$('#personal_pay' + row_item_code).val(0);
			}else if(exam_indicators=='个人付费'){
				$('#team_pay' + row_item_code).val(0);
				$('#personal_pay' + row_item_code).val(depositValue);
			}else{
				$('#team_pay' + row_item_code).val(decimal(depositValue-Number(personal_pay_input),2));
			}
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
