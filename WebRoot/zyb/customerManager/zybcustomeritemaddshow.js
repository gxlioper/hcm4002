var deltiemflags=0;
var delsetflags=0;
var countitemrow=0;
var jine="";
var zhekou="";
$(document).ready(function() {
		
	if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined ){
		$('#z_zhi').text("本操作员:没有打折权限！");
	}else{
		$('#z_zhi').text("本操作员最大权限可以打："+$('#web_Resource').val()+"折！");
	}
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$('#zytj_zkl').hide();// 设置隐藏列
		$('#zytj_zkhze').hide();// 设置隐藏列
		$('#discount').hide(); // 设置隐藏列
		$('#amount').hide();// 设置隐藏列
		$('#zytj_yuan').hide();// 设置隐藏列
	}
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
	
	
	$('#amount').bind('blur', function() {
		disamout(this.value);
	});
	
	$('#discount').bind('click',function(){
		zhekou = $(this).val();
	})
	$('#amount').bind('click',function(){
		jine = $(this).val();
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
			setChangItemListGrid();
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
	var url = 'zybsatlistshow.action';
	var data = {
		"setname" : set_Namess,
		"sex": $("#custsex").val(),
		"app_type":'2'
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
	$.post("getZybExamOneShow.action", {
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
		"exam_num" : $("#exam_num").val(),
		"app_type":'2'
	};
	$("#selectctlist").datagrid({
		url : 'zybexam_tclistshow.action',
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
				break;
			} else {
				flag = true;
			}
		}//j End             
	}
	if (flag == true) {			
		var usersex = $("#custsex").val();
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
		
		//资源
		if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			$('#discount').val(zhekou);
			return;
		} 
		
		if( Number(disvar)<Number($('#web_Resource').val()) ){
			$.messager.alert("提示信息","本操作人员最大权限只能打"+$('#web_Resource').val()+"折！","error");
			$('#discount').val(zhekou);
			return;
		}
		
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = countitemrow; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = disvar;
					row.team_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 2);	
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt();
	}
}

//根据金额反算折扣
function disamout(disvar) {
	if (!isFloat(disvar)) {
		document.getElementById("team_pay").value=document.getElementById("item_amount").value;
		document.getElementById("team_pay").focus();
		$.messager.alert("操作提示", "金额格式错误！", "error");
	}else if (Number(disvar) > Number(document.getElementById("item_amount").value)) {
		document.getElementById("team_pay").value=document.getElementById("item_amount").value;
		document.getElementById("team_pay").focus();
		$.messager.alert("操作提示", "金额不能大于！"+document.getElementById("item_amount").value+"元", "error");
	}else if(Number(disvar)<=0){
		
	}else{	
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 2);
		
		
		if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			$('#team_pay').val(jine);
			return;
		}
			
		 if(  Number(newdiscont)<Number($('#web_Resource').val())){
				//资源
				$.messager.alert("提示信息","本操作人员最大权限只能打"+$('#web_Resource').val()+"折！","error");
				$('#team_pay').val(jine);
				return;
		}
		
		document.getElementById("discount").value=newdiscont;
		var rows = $('#selectchangitemlist').datagrid('getRows');
		if (!rows.length == 0) {
			for (var j = countitemrow; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
					row.discount = newdiscont;
					row.amount = decimal(Number(row.item_amount)*Number($('#itemnum_input' + row.item_code).val())* Number(newdiscont) /10, 2);	
					$('#selectchangitemlist').datagrid('refreshRow', j);
			}//j End             
		}
		countamt2();
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
			newamt = newamt + Number(row.team_pay);
		}//j End             
	}
	}
	$("#item_amount").val(oldamt)
	$("#amount").val(newamt)
}


//选择套餐插入收费项目
function insertsettiem(setnum) {
	$.post("setzybforchangitemlist.action", {
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
				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
						* userid[i].discount / 10, 2);
				deltiemflags=1;
				tcinsertitem(userid[i]);
			} else {
				userid[i].discount = $("#discount").val();
				userid[i].amount = decimal(userid[i].item_amount*userid[i].itemnum
						* $("#discount").val() / 10, 2);
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
				$('#selectchangitemlist').datagrid("appendRow", {
					item_code : row.item_code,
					item_name : row.item_name,
					dep_name : row.dep_name,
					item_amount : row.item_amount,
					sex : row.sex,
					discount : row.discount,
					set_num : row.set_num,
					itemnum:row.itemnum,
					item_type:row.item_type,
					team_pay : row.amount,
					exam_indicators:'T',
					personal_pay:0
				});
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 $('#selectchangitemlist').datagrid("appendRow", {
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.item_amount,
							sex : row.sex,
							discount : row.discount,
							set_num : row.set_num,
							item_type:row.item_type,
							itemnum:row.itemnum,
							team_pay : row.amount,
							exam_indicators:'T',
							personal_pay:0
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

//不修改折后总金额
function countamt2() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if (!rows.length == 0) {
		for (var j = 0; j <= rows.length - 1; j++)//已选择
		{
			var row = rows[j];
			oldamt = oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
			newamt = newamt + Number(row.amount);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").val(oldamt)
	//$("#team_pay").val(newamt)
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
			newamt = newamt + Number(row.team_pay);
			//var index = $('#selectchangitemlist').datagrid('getRowIndex',row);//获取指定索引
			
		}//j End             
	}
	$("#item_amount").val(oldamt)
	$("#amount").val(newamt)
}

	/**
	 * 保存修改
	 */
	function djtcustadd() {
		var itemrows = $('#selectchangitemlist').datagrid('getRows');
		if (itemrows.length>0) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			var itemrows = $('#selectchangitemlist').datagrid('getRows');  
		    var itementities = "";
		    for(i = countitemrow;i < itemrows.length;i++)  
		    {	
		    	var itemobject=itemrows[i];
				itemobject.amount=itemobject.team_pay;
		    	itementities = itementities  + JSON.stringify(itemobject);    
		    } 
		    
		    var setrows = $('#selectctlist').datagrid('getRows');  
		    var setentities = "";
		    for(i = 0;i < setrows.length;i++)  
		    {  
		    	setentities = setentities  + JSON.stringify(setrows[i]);    
		    } 	  	
		    		    
			$.ajax({
				url : 'zybcustGSaveItemSet.action',
				data : {
							exam_id : $("#exam_id").val(),
							exam_num:$('#exam_num').val(),
							itementities: itementities,
							setentities:setentities,
							discount : $("#discount").val(),
							amount : $("#amount").val(),
							item_amount:$("#item_amount").val(),
							app_type:'2'
						},
						type : "post",//数据发送方式   
						success : function(text) {
							if(text=="ok"){
								$.messager.alert("本操作员最大权限打"+$('#web_Resource').val()+"折,项目折扣超出权限！");
							}else{
								$(".loading_div").remove();		
								if (text.split("-")[0] == 'ok') {														
									$.messager.alert("操作提示", text.split("-")[1]);
									var _parentWin =  window.opener ; 
									_parentWin.zybsetselectListGrid();
									_parentWin.gettotalinfo();
									_parentWin.zybcustChangItemListGrid();
									window.close();
								} else {
									$.messager.alert("操作提示", text.split("-")[1], "error");
								}
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
			"sex":$("#custsex").val(),
			"dep_id":$('#serch_dep_id').combobox('getValue')
		};
		$("#changitemlist").datagrid(
				{
					url : 'zybchangitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					pageSize: 30,//每页显示的记录条数，默认为10 
					pageList : [ 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
					columns : [[ {align : 'left',	field : 'item_code',title : '项目编码',width : 10},
					         {align : 'left',field : 'item_name',title : '项目名称',	width : 40,"formatter":f_showitem}, 
					         {align : 'left',field : 'dep_name',title : '所属科室',width : 25}, 
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
							row.itemnum=1;
							row.discount = $("#discount").val();
							row.amount = decimal(row.item_amount
									* $("#discount").val() / 10, 2);
							deltiemflags=1;
							insertitem(row);
						}
						$('#itemname').focus(); 
						$("#itemname").select();
						
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
					$('#selectchangitemlist').datagrid("appendRow", {
						item_code : row.item_code,
						item_name : row.item_name,
						dep_name : row.dep_name,
						item_amount : row.item_amount,
						sex : row.sex,
						discount : row.discount,
						set_num : row.set_num,
						item_type:row.item_type,
						team_pay : row.amount,
						itemnum:row.itemnum,
						exam_indicators:'T',
						personal_pay:0
					});
					countamt();
				}else{
					 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
				     if(r){
				    	 $('#selectchangitemlist').datagrid("appendRow", {
								item_code : row.item_code,
								item_name : row.item_name,
								dep_name : row.dep_name,
								item_amount : row.item_amount,
								sex : row.sex,
								discount : row.discount,
								set_num : row.set_num,
								item_type:row.item_type,
								itemnum:row.itemnum,
								team_pay : row.amount,
								exam_indicators:'T',
								personal_pay:0
							});
							countamt();
						 }
					 });
				}	
			}else{
				alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
			}

		}else{
			alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		}
	}
	var x_zhekou="";
	var x_jine= "";
	var x_shuliang="";
    //通过修改单个折扣率算金额
    function calcAmt(rowIndex){
    	var objGrid = $("#selectchangitemlist");        // 表格对象
    	var discountEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'discount'});        // 折扣额对象
        var amountEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'team_pay'}); 
        var itemnumEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'itemnum'});
        x_zhekou = $(discountEdt.target).val();
        x_jine =  $(amountEdt.target).val();
        x_shuliang = $(itemnumEdt.target).val();
     // 折扣率  绑定离开事件 
        $(discountEdt.target).bind("blur",function(){
            calcDeposit(rowIndex);        // 根据 折扣率变更后 计算 折扣额
        });
        
        $(discountEdt.target).keydown(function (e) {
            if (e.keyCode == 13) {
            	calcDeposit(rowIndex);
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
        // 金额 绑定离开事件 
        $(amountEdt.target).bind("blur",function(){
           calcMoneyChange(rowIndex);    // 金额变更 后 重新计算  单价,折扣额,折扣率
        });
        
        $(amountEdt.target).keydown(function (e) {
            if (e.keyCode == 13) {
            	calcMoneyChange(rowIndex);
            }
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
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'team_pay'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;            // 金额值
        var amountValue = $(depositEdt.target).val();    // 现有金额    
        var itemnumValue=rows[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var depositValue = decimal(Number(amountValue) / (Number(invMoneyValue)*Number(itemnumValue)) * 10, 1);	
        if(Number(amountValue)>Number(invMoneyValue)*Number(itemnumValue)){
        	depositValue=10;
        	amountValue=decimal(Number(invMoneyValue)*Number(itemnumValue),2);
        }
        
        //资源-金额
      // alert("折扣1"+depositValue+"折扣66"+$('#web_Resource').val()+"金额1"+amountValue+"金额2"+x_jine);
        if($('#web_Resource').val()=='' || $('#web_Resource').val()==undefined){
        	$.messager.alert("提示信息","没有打折权限！","error");
        	$("#selectchangitemlist").datagrid('cancelEdit', desindex); 
        	return;
        }
        
        if(depositValue<$('#web_Resource').val() && amountValue != x_jine && depositValue!=0){
        	$.messager.alert("提示信息","本操作员最大权限只能打"+$('#web_Resource').val()+"折！","error");
        	rows[desindex].team_pay=x_jine;
        	 $('#selectchangitemlist').datagrid('refreshRow', desindex);
        	 $("#selectchangitemlist").datagrid('endEdit', desindex); 
        	return;
        }
        
        rows[desindex].discount=depositValue; 
        rows[desindex].team_pay=amountValue;    // 折扣额  赋值
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

        rows[desindex].team_pay=depositValue;    // 折扣额  赋值
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
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'team_pay'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;           // 金额值
        var discountValue = $(discountEdt.target).val();                // 折扣率 值
        var itemnumValue=rows[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var invMoneyValue = decimal(rows[desindex].item_amount*Number(itemnumValue),2);           // 金额值
        var depositValue = decimal(Number(rows[desindex].item_amount)*Number(itemnumValue) * Number(discountValue) /10, 2);	
        if(Number(discountValue)>10){
        	discountValue=10;
        	depositValue=invMoneyValue;
        }
        
        //资源
        if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
        	$.messager.alert("提示信息","没有打折权限！","error");
        	 $("#selectchangitemlist").datagrid('cancelEdit', desindex); 
        	return;
        }
        //alert("xx折扣"+discountValue+"折扣66"+$('#web_Resource').val()+"折扣扣HEH "+discountValue+"折扣LLE"+x_zhekou);
        if(discountValue<$('#web_Resource').val() && discountValue != x_zhekou ){
        	$.messager.alert("提示信息","本操作员最大权限只能打"+$('#web_Resource').val()+"折！","error");
        	rows[desindex].discount=x_zhekou;
        	 $('#selectchangitemlist').datagrid('refreshRow', desindex);
        	 $("#selectchangitemlist").datagrid('endEdit', desindex); 
        	return;
        }
        
        rows[desindex].discount=discountValue; 
        rows[desindex].team_pay=depositValue;    // 折扣额  赋值
        
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
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var model = {"exam_num":$("#exam_num").val(),
				"app_type":'2'
				};
		$("#selectchangitemlist").datagrid({
			url : 'zybcustchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			//pageSize: 8,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 10,	"formatter" : f_dellchargitem},
			   {align : 'left',field : 'item_code',title : '项目编码',width : 15}, 
			   {align : 'center',field : 'item_type',title : '项目类型',width : 20}, 
			   {align : 'left',field : 'item_name',title : '项目名称',width :40},
			   {align : 'left',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10},
			   {align : 'center',field : 'amount',title : '个人缴费',width : 10},
			   {align : 'center',field : 'itemnum',title : '数量',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'team_pay',title : '金额',	width : 10,	editor : {type : 'text'}} 
			   ]],
			onLoadSuccess : function(value) {
				$("#selectchangitemlist").datagrid("hideColumn", "item_type"); // 设置隐藏列  
				$("#selectchangitemlist").datagrid("hideColumn", "amount"); // 设置隐藏列  
				$("#datatotal").val(value.total);
				var data=$('#selectchangitemlist').datagrid('getData');
				countitemrow=data.rows.length;
				countamt();
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
					$("#selectchangitemlist").datagrid("hideColumn", "discount"); // 设置隐藏列
					$("#selectchangitemlist").datagrid("hideColumn", "amount"); // 设置隐藏列
					$("#selectchangitemlist").datagrid("hideColumn", "team_pay"); // 设置隐藏列
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
			onDblClickRow  : function (rowIndex) {
				if(countitemrow<=rowIndex){
		            if (lastIndex != rowIndex) {
		            	//$("#selectchangitemlist").datagrid('endEdit', lastIndex);  
		            	$("#selectchangitemlist").datagrid('beginEdit', rowIndex); 
		            	calcAmt(rowIndex);
		            }  
		            lastIndex = -1;  
				}
	        }
		});
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
