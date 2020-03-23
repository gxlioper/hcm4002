$(document).ready(function() {
	setfzchareitemListGrid();
	setChangItemListGrid();		
	$('#itemname').bind('keyup', function() {
		setChangItemListGrid();
	});
	
	$('#discount').bind('blur', function() {
		discount(this.value);
	});
	
	$('#amount').bind('blur', function() {
		disamout(this.value);
	});

	});

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
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 2);	
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt();
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
			oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt = decimal(newamt + Number(row.amount),2);
		}//j End             
	}
	}
	$("#item_amount").val(oldamt)
	$("#amount").val(newamt)
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
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10,1);
		document.getElementById("discount").value=newdiscont;
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = 0; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = newdiscont;
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum)* Number(newdiscont) /10, 2);	
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt2();
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
			oldamt =  decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt =  decimal(newamt + Number(row.amount),2);
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
			oldamt =  decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt =  decimal(newamt + Number(row.amount),2);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").val(oldamt)
	$("#amount").val(newamt)
}

	function checkinput() {
		if (document.getElementById("ids").value == '') {
			alert('所选体检人员无效！');
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 保存修改
	 */
	function groupadd() {
	if (checkinput()) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		var itemrows = $('#selectchangitemlist').datagrid('getRows');
		var itementities = "";
		for (i = 0; i < itemrows.length; i++) {
			itementities = itementities + JSON.stringify(itemrows[i]);
		}

		$.ajax({
			url : 'customeritemadddo.action',
			data : {
                ids : $("#ids").val(),
				exam_nums : $("#ids").val(),
				batch_id : $("#batch_id").val(),
				company_id : $("#company_id").val(),
				itementities : itementities,
				sex : $("#sex").val(),
				exam_indicator : $("input[name='exam_indicator']:checked")
						.val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text);
					window.close();
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
			"sex":$("#batchsex").val()
		};
		$("#changitemlist").datagrid(
				{
					url : 'changitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					//pageSize: 8,//每页显示的记录条数，默认为10 
					pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
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
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 2);
							insertitem(row);
						} else {
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 2);
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
			var usersex = $("#batchsex").val();
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
        var itemnumEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'itemnum'});
     // 折扣率  绑定离开事件 
        $(discountEdt.target).bind("blur",function(){
            calcDeposit(rowIndex);        // 根据 折扣率变更后 计算 折扣额
        });
        
        $(discountEdt.target).keydown(function (e) {
            if (e.keyCode == 13) {
            	calcDeposit(rowIndex);
            }
        });
        
        // 金额 绑定离开事件 
        $(amountEdt.target).bind("blur",function(){
            calcMoneyChange(rowIndex);    // 金额变更 后 重新计算  单价,折扣额,折扣率
        });
        
        $(amountEdt.target).keydown(function (e) {
            if (e.keyCode == 13) {
            	calcMoneyChange(rowIndex);
            }
        });
        
        // 数量  绑定离开事件 
        $(itemnumEdt.target).bind("blur",function(){
            calcitemnum(rowIndex);        
        });
        
        $(itemnumEdt.target).keydown(function (e) {
            if (e.keyCode == 13) {
            	calcitemnum(rowIndex);
            }
        });
    	
    }
    
    /**
     * 根据 折扣率变更后 计算 折扣额
     * @author 
     */
    var desindex;
    function calcMoneyChange(desindex){
        var objGrid = $("#selectchangitemlist");   
        var rows = objGrid.datagrid('getRows');
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;            // 金额值
        var amountValue = $(depositEdt.target).val();    // 现有金额    
        var itemnumValue=rows[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var depositValue = decimal(Number(amountValue) /(Number(invMoneyValue)*Number(itemnumValue)) * 10, 1);	
        if(Number(amountValue)>Number(invMoneyValue)*Number(itemnumValue)){
        	depositValue=10;
        	amountValue=decimal(Number(invMoneyValue)*Number(itemnumValue),2);;
        }
        rows[desindex].discount=depositValue; 
        rows[desindex].amount=amountValue;    // 折扣额  赋值
        $('#selectchangitemlist').datagrid('refreshRow', desindex);
        $("#selectchangitemlist").datagrid('endEdit', desindex); 
        //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
        countamt3();
    }
    
    function calcitemnum(desindex){
        var objGrid = $("#selectchangitemlist");   
        var rows = objGrid.datagrid('getRows');
        var itemnumEdt = objGrid.datagrid('getEditor', {index:desindex,field:'itemnum'});    // 折扣额对象
        var itemnumValue=$(itemnumEdt.target).val(); 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var invMoneyValue = decimal(rows[desindex].item_amount*Number(itemnumValue),2);           // 金额值
        var discountValue = rows[desindex].discount;                // 折扣率 值
        var depositValue = decimal(Number(rows[desindex].item_amount)*Number(itemnumValue) * Number(discountValue) /10, 2);	
        if(Number(discountValue)>10){
        	discountValue=10;
        	depositValue=invMoneyValue;
        }

        rows[desindex].amount=depositValue;    // 折扣额  赋值
        rows[desindex].itemnum=itemnumValue;
        $('#selectchangitemlist').datagrid('refreshRow', desindex);
        $("#selectchangitemlist").datagrid('endEdit', desindex); 
        //$("#selectchangitemlist").datagrid('beginEdit', desindex); 
        countamt3();
    }
    
    function calcDeposit(desindex){
        var objGrid = $("#selectchangitemlist");   
        var rows = objGrid.datagrid('getRows');
        var discountEdt = objGrid.datagrid('getEditor', {index:desindex,field:'discount'});    // 折扣额对象
        var discountValue = $(discountEdt.target).val();                // 折扣率 值
        var itemnumValue=rows[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var invMoneyValue = decimal(rows[desindex].item_amount*Number(itemnumValue),2);           // 金额值
        var depositValue = decimal(Number(rows[desindex].item_amount)*Number(itemnumValue) * Number(discountValue) /10,2);	
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
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var model = {"ids" : $("#ids").val()};
		$("#selectchangitemlist").datagrid({
			url : '#',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 15,	"formatter" : f_dellchargitem},
			   {align : 'center',field : 'item_code',title : '项目编码',width : 20}, 
			   {align : 'center',field : 'item_name',title : '项目名称',width : 25},
			   {align : 'center',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'center',field : 'item_amount',title : '原金额',width : 20}, 
			   {align : 'center',field : 'itemnum',title : '数量',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 20,editor : {type : 'text'}}, 
			   {align : 'center',field : 'amount',title : '金额',	width : 20,	editor : {type : 'text'}} 
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
			pageNumber : 1,
			pageSize : 1000,
			onDblClickRow  : function (rowIndex) {
		            if (lastIndex != rowIndex) {
		            	//$("#selectchangitemlist").datagrid('endEdit', lastIndex);  
		            	$("#selectchangitemlist").datagrid('beginEdit', rowIndex); 
		            	calcAmt(rowIndex);
		            }  
		            lastIndex = -1;  
	        }
		});
	}