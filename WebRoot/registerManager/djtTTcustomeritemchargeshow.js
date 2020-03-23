var deltiemflags=0;
var delsetflags=0;
var countitemrow=0;
var arrayjftype;
$(document).ready(function() {
	
	setselectGrid();
	setfzchareitemListGrid();
	setChangItemListGrid();	
		
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
		if($("#discount").val()<1){
			$('#discount').val('10');
		}
	    var $amountInput = $(this);
	    //最后一位是小数点的话，移除
	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	    discount(this.value);
	});
	
	/*$('#amount').bind('blur', function() {
		disamout(this.value);
	});*/
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
	var model = {
			"exam_num":$("#exam_num").val(),
		    "ids":$("#ids").val(),
            "item_codes":$('#item_codes').val()
		};
		$("#selectoldchangitemlist").datagrid({
			url : 'djtTTcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			
			columns : [[ 
			   {align : 'center',field : 'item_code',title : '项目编码',width : 10}, 
			   {align : 'center',field : 'item_name',title : '项目名称',width : 25},
			   {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 15},
			   {align : 'center',field : 'exam_indicators',title: '付费方式', width: 15},
			   {align : 'center',field : 'pay_status',title: '个人结算状态', width: 15},
			   {align : 'center',field : 'team_pay',title : '单位金额', width : 15},
			   {align : 'center',field : 'personal_pay',title : '个人金额', width : 15} 
			   ]],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
				oldcountamt();
			},
			singleSelect : true,
			collapsible : true,
			pagination : true,
			fitColumns : true,
			autowidth : true,
			striped : true,
			pagination : false,
			beginEdit:true,
			rownumbers : false,			
			pageNumber : 1,
			pageSize : 1000
			
		});
}

//----------------------------------------显示套餐-------------------------------------------------


//修改折扣率 更新所有行
var disvar;
function discount(disvar) {
	if (!isFloat(disvar)) {
		document.getElementById("discount").value=10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率格式错误！", "error");
	} else if (Number(disvar) > 10) {
		document.getElementById("discount").value=10;
		document.getElementById("discount").focus();
		$.messager.alert("操作提示", "折扣率不能大于10！", "error");
	} else {
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = disvar;
					row.personal_pay = 0;	
					row.team_pay= decimal(Number(row.item_amount) * Number(disvar) / 10, 4);	
					$('#selectchangitemlist').datagrid('refreshRow', j);
					$("#selectchangitemlist").datagrid('endEdit', j); 
					$("#selectchangitemlist").datagrid('beginEdit', j); 
			}             
		}
		countamt();
		itemchagrecombo();
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
	}else{		
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 4);
		document.getElementById("discount").value=newdiscont;
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = newdiscont;
					row.personal_pay = decimal(Number(row.item_amount)* Number(newdiscont) /10, 2);
					$('#selectchangitemlist').datagrid('refreshRow', j);
					$("#selectchangitemlist").datagrid('beginEdit', j); 
			}//j End             
		}
		countamt2();
	}
}


function oldcountamt() {
	var rows = $('#selectoldchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, tamt = 0,gamt = 0;ygam=0;
	if(rows.length!='undefined'){
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = oldamt + Number(row.item_amount);
			tamt = tamt + Number(row.team_pay);			
			if(rows.pay_status=='Y'){
				ygam=ygam+Number(row.personal_pay);
			}else{
				gamt = gamt + Number(row.personal_pay);
			}
		}//j End             
	}
	}
	$("#olditem_amount").val(oldamt);	
	$("#oldteam_pay").val(tamt);
	$("#oldypersonal_pay").val(ygam);
	$("#oldpersonal_pay").val(gamt);
	$("#oldamount").val(tamt+gamt);
}

//计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, tamt = 0,gamt = 0;
	if(rows.length!='undefined'){
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = oldamt + Number(row.item_amount);
			tamt = tamt + Number(row.team_pay);
			gamt = gamt + Number(row.personal_pay);
		}//j End             
	}
	}
	$("#item_amount").val(oldamt);	
	$("#team_pay").val(tamt);
	$("#personal_pay").val(gamt);
	$("#amount").val(tamt+gamt);
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
									discount : row.discount,
									set_num : row.set_num,
									team_pay : row.amount,
									exam_indicators:'T',
									personal_pay:0
								});
								var rowss = $('#selectchangitemlist').datagrid('getRows');
								$("#selectchangitemlist").datagrid('beginEdit', rowss.length-1); 
								countamt();
								itemchagrecombo();
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

//不修改折后总金额
function countamt2() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = oldamt + Number(row.item_amount);		
		}            
	}
	$("#item_amount").val(oldamt)
}

//不修改折后总金额
function countamt3() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var tamt = 0, gamt = 0;
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			tamt = tamt + Number(row.team_pay);
			gamt = gamt + Number(row.personal_pay);
		}          
	}
	$("#team_pay").val(tamt)
	$("#personal_pay").val(gamt)
	$("#amount").val(tamt+gamt)
}

	/**
	 * 保存修改
	 */
	function djtcustadd() {
		closeedititem();
		var itemrows = $('#selectchangitemlist').datagrid('getRows');  
		if (itemrows.length>0) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			    var setrows = $('#selectoldchangitemlist').datagrid('getRows');  
			    var setentities = "";
			    for(i = 0;i < setrows.length;i++)  
			    {  
			    	setentities = setentities  + JSON.stringify(setrows[i]);    
			    } 	
		    var itementities = "";
		    for(i = 0;i < itemrows.length;i++)  
		    {
		    	itementities = itementities  + JSON.stringify(itemrows[i]);   
		    } 
			$.ajax({
				url : 'djtTTcustchangSaveItemSet.action',
				data : {
							exam_id : $("#exam_id").val(),
                            exam_num:$('#exam_num').val(),
							itementities: itementities,
							setentities:setentities,
							discount : $("#discount").val(),
							amount : $("#amount").val(),
							item_amount:$("#item_amount").val()
						},
						type : "post",//数据发送方式   
						success : function(text) {
							$(".loading_div").remove();		
							if (text.split("-")[0] == 'ok') {														
								$.messager.alert("操作提示", text.split("-")[1]);
								var _parentWin =  window.opener;
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
		}else{
			$.messager.alert("操作提示", "无效收费项目", "error");
		}
	}
	
	function closeedititem(){
    	var rows = $('#selectchangitemlist').datagrid('getRows');
    	var objGrid = $("#selectchangitemlist"); 
		if (!rows.length == 0) {
			for (var desindex = 0; desindex <= rows.length - 1; desindex++)//已选择
			{
				 var team_payEdt = objGrid.datagrid('getEditor', {index:desindex,field:'team_pay'});
			     var personal_payEdt = objGrid.datagrid('getEditor', {index:desindex, field:'personal_pay'});
			     rows[desindex].team_pay=$(team_payEdt.target).val();
			     rows[desindex].personal_pay=$(personal_payEdt.target).val();
			     $('#selectchangitemlist').datagrid('refreshRow', desindex);
			     $("#selectchangitemlist").datagrid('endEdit', desindex); 
				 $("#selectchangitemlist").datagrid('beginEdit', desindex); 
				 var exam_indicators = [
                                         {id:'T',name:'团体付费'},
						          	     {id:'G',name:'个人付费'},						          	     
						          	     {id:'GT',name:'混合付费'},
						          	     {id:'M',name:'免费'}
						          	     ];
						for(var m=0;m<exam_indicators.length;m++){
							exam_indicators[m].id=desindex+"-"+exam_indicators[m].id;
						}
						var examindi = rows[desindex].exam_indicators;
						var ed = $('#selectchangitemlist').datagrid('getEditor', { index: desindex, field: 'exam_indicators' });
						$(ed.target).combobox('clear');                                                                                     
						$(ed.target).combobox('loadData',exam_indicators);
						var s= $(ed.target).combobox('getData'); //获取控件中所有的值
						for (var j= 0; j < s.length; j++){
						  if(examindi==s[j].id.split("-")[1])//和控件中的valueField值进行对比
						  {
						    $(ed.target).combobox('setValue', s[j].id);
						  }				  
						} 
			}//j End             
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
	/**
	 * 显示体检项目套餐信息
	 */
	function setChangItemListGrid() {
		var model = {
			"setname" : $("#itemname").val(),
			"sex":$("#custsex").val()
		};
		$("#changitemlist").datagrid(
				{
					url : 'changitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					pageSize: 20,//每页显示的记录条数，默认为10 
					pageList : [ 20, 40, 60, 80, 100 ],//可以设置每页记录条数的列表 
					columns : [[ {align : 'center',	field : 'item_code',title : '项目编码',width : 20},
					         {align : 'center',field : 'item_name',title : '项目名称',	width : 25}, 
					         {align : 'center',field : 'dep_name',title : '所属科室',width : 15}, 
					         {align : 'center',field : 'sex',title : '适用性别',width : 15}, 
					         {align : 'center',field : 'item_amount',title : '金额',width : 15}
					         ]],
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
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 4);
							deltiemflags=1;
							insertitem(row);
						} else {
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 4);
							deltiemflags=1;
							insertitem(row);
						}
						//countamt();
					}
				});
		//document.getElementById("itemname").value = '';
		//document.getElementById("itemname").focus();
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
										discount : row.discount,
										set_num : row.set_num,
										team_pay : row.amount,
										exam_indicators:'T',
										personal_pay:0
									});
									var rowss = $('#selectchangitemlist').datagrid('getRows');
									//4选中并开启编辑状态 
									$('#selectchangitemlist').datagrid('selectRow',rowss.length-1); 
									$('#selectchangitemlist').datagrid('beginEdit', rowss.length-1); 
									
									countamt();
									itemchagrecombo();
									alert_autoClose("操作提示", "添加成功！","");
								} else {
									alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");					
							}
						});		
			}else{
				alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
			}

		}else{
			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		}
	}

    //通过修改单个折扣率算金额
    function calcAmt(rowIndex){
    	var objGrid = $("#selectchangitemlist");        // 表格对象
    	var discountEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'discount'});        // 折扣额对象
        var amountEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'amount'});  
     // 折扣率  绑定离开事件 
        $(discountEdt.target).bind("blur",function(){
            calcDeposit(rowIndex);        // 根据 折扣率变更后 计算 折扣额
        });
        
        // 金额 绑定离开事件 
        $(amountEdt.target).bind("blur",function(){
           calcMoneyChange(rowIndex);    // 金额变更 后 重新计算  单价,折扣额,折扣率
        });
    	
    }
	 /**
     * 根据 折扣率变更后 计算 折扣额
     * @author WUYF
     */
    var desindex;
    function calcMoneyChange(desindex){
        var objGrid = $("#selectchangitemlist");   
        var rows = objGrid.datagrid('getRows');
        var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;            // 金额值
        var amountValue = $(depositEdt.target).val();    // 现有金额    
        var depositValue = decimal(Number(amountValue) / Number(invMoneyValue) * 10, 4);	
        if(Number(amountValue)>Number(invMoneyValue)){
        	depositValue=10;
        	amountValue=invMoneyValue;
        }
        rows[desindex].discount=depositValue; 
        rows[desindex].amount=amountValue;    // 折扣额  赋值
        $('#selectchangitemlist').datagrid('refreshRow', desindex);
        $("#selectchangitemlist").datagrid('endEdit', desindex); 
        //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
        countamt3();
    }
    function calcDeposit(desindex){
        var objGrid = $("#selectchangitemlist");   
        var rows = objGrid.datagrid('getRows');
        var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;           // 金额值
        var discountValue = $(discountEdt.target).val();                // 折扣率 值
        var depositValue = decimal(Number(invMoneyValue) * Number(discountValue) /10, 4);	
        if(Number(discountValue)>10){
        	discountValue=10;
        	depositValue=invMoneyValue;
        }
        rows[desindex].discount=discountValue; 
        rows[desindex].amount=depositValue;    // 折扣额  赋值
        $('#selectchangitemlist').datagrid('refreshRow', desindex);
        $("#selectchangitemlist").datagrid('endEdit', desindex); 

        //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
        countamt3();
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
	 var exam_indicators = [
	     {id:'G',name:'个人付费'},
	     {id:'T',name:'团体付费'},
	     {id:'GT',name:'混合付费'},
	     {id:'M',name:'免费'}
	     ];
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var model = {"id":$("#exam_id").val()};
		$("#selectchangitemlist").datagrid({
			url : '#',
			dataType : 'json',
			queryParams : model,
			
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 15,	"formatter" : f_dellchargitem},
			   {align : 'center',field : 'item_code',title : '项目编码',width : 10}, 
			   {align : 'center',field : 'item_name',title : '项目名称',width : 25},
			   {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 15},
			   {field : 'exam_indicators',title: '付费方式', width: 15, sortable: true,
				   editor:{
					   type:'combobox',
					   options:{
						   editable: true,
						   panelHeight: 'auto',
						   valueField:'id',
						   textField:'name',
						   //data:exam_indicators,
						   required:true,
						   onSelect:function (data) {							   
                              var varSelect = $(this).combobox('getValue');
                              var row = $('#selectchangitemlist').datagrid('getSelected');
							  var rowIndex = varSelect.split("-")[0];
							  var comboboxvalue=varSelect.split("-")[1];
							  var row = $('#selectchangitemlist').datagrid('getData').rows[rowIndex];
							  if(comboboxvalue=='G'){								  
								  row.personal_pay=Number(row.team_pay)+Number(row.personal_pay);
								  row.team_pay= '0';
							  }							  
							  if(comboboxvalue=='T'){								  
								  row.team_pay=Number(row.team_pay)+Number(row.personal_pay);
								  row.personal_pay='0';
							  }
							  
							  if(comboboxvalue=='M'){								  
								  row.personal_pay='0';
								  row.team_pay= '0';
							  }
							  row.exam_indicators=comboboxvalue;
								$('#selectchangitemlist').datagrid('refreshRow', rowIndex);
								$("#selectchangitemlist").datagrid('endEdit', rowIndex); 
								$("#selectchangitemlist").datagrid('beginEdit', rowIndex); 
								itemchagrecombo();
                           }

               }}},
			   {align : 'center',field : 'team_pay',title : '单位金额',	width : 15,	editor : {type : 'text'}},
			   {align : 'center',field : 'personal_pay',title : '个人金额',	width : 15,	editor : {type : 'text'}} 
			   ]],
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
			beginEdit:true,
			rownumbers : false,			
			pageNumber : 1,
			pageSize : 1000
			
		});
	}
	
	
	function itemchagrecombo(){	
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var i=0;i<rows.length; i++) {
				var exam_indicators = [
				          	     {id:'G',name:'个人付费'},
				          	     {id:'T',name:'团体付费'},
				          	     {id:'GT',name:'混合付费'},
				          	     {id:'M',name:'免费'}
				          	     ];
				for(var m=0;m<exam_indicators.length;m++){
					exam_indicators[m].id=i+"-"+exam_indicators[m].id;
				}
				var row = rows[i];
				var examindi = row.exam_indicators;
				var ed = $('#selectchangitemlist').datagrid('getEditor', { index: i, field: 'exam_indicators' });
				$(ed.target).combobox('clear');                                                                                     
				$(ed.target).combobox('loadData',exam_indicators);

				var s= $(ed.target).combobox('getData'); //获取控件中所有的值
				for (var j= 0; j < s.length; j++){
				  if(examindi==s[j].id.split("-")[1])//和控件中的valueField值进行对比
				  {
				    $(ed.target).combobox('setValue', s[j].id);
				  }				  
				} 
			}//j End             
		}
	}