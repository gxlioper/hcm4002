var deltiemflags=0;
var delsetflags=0;
var countitemrow=0;
var jine="";
var zhekou="";
var copy_item = "";
$(document).ready(function() {
	$('#c_item_name').focus();
	if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined ){
		$('#z_zhi').text("没有打折权限！");
	}else{
		$('#z_zhi').text("最大打折权限："+$('#web_Resource').val()+"折！");
	}
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$('#ttzkl1').hide();// 设置隐藏列
		$('#ttzkhze1').hide();// 设置隐藏列
		$('#discount').hide(); // 设置隐藏列
		$('#amount').hide();// 设置隐藏列
		$('#ttyuan1').hide();// 设置隐藏列
	}
	setselectGrid();

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
	
//	$('#amount').bind('blur', function() {
//		disamout(this.value);
//	});
	
	$('#discount').bind('click',function(){
		zhekou = $(this).val();
	})
	
	getsaveChargingItem();
	dep_tree();
});

function getsaveChargingItem(){
	$.ajax({
		url:'getExaminfoChargingItemadd.action',
		data:{"id":$("#exam_id").val(),"exam_num":$('#exam_num').val()},
		type:'post',
		async:false, 
		success:function(data){
			var item = eval('('+data+')');
			for(var i = 0 ; i < item.length ; i++){
				var row = item[i];
				item_id.push({
					item_code : row.item_code,
					item_name : row.item_name,
					dep_name : row.dep_name,
					item_amount : row.item_amount,
					sex : row.sex,
					itemnum:row.itemnum,
					discount : row.discount,
					set_num : row.set_num,
					item_type:row.item_type,
					amount : row.amount,
					yjx : 1
				});
			}
			countamt();
		},
		error:function(){
			$.messager.alert("提示信息","操作失败","error");
		}
		
	});
}

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
					"javascript:setvalueselectset(" + data[index].id + ",'"
							+ data[index].text + "')", data[index].text,
					"_self", false);
		} else {
			mydtree.add(data[index].id, data[index].attributes,
					data[index].text, "javascript:setvalueselectset("
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
function setvalueselectset(id, name) {
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
		"exam_id" : $("#exam_id").val(),
		"exam_num":$("#exam_num").val()
	};
	$("#selectctlist").datagrid({
		url : 'exam_tclistshow.action',
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
	if (!item_id.length == 0) {
		for (var i = 0; i<item_id.length ; i++) {
			if (set_numsss == item_id[i].set_num) {
				item_id.splice(i,1);
				i--;
			}
		}//j End
		item_qihuan_yixuan();
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
		   if(copy_item!='N'){
			   insertsettiem(userid.set_num);
		   } 
		}else{
			//alert("套餐性别冲突111，不能添加。");
		}
	}
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
				deltiemflags=1;
				tcinsertitem(userid[i]);
			} else {
				deltiemflags=1;
				tcinsertitem(userid[i]);
			}
		}
		item_qihuan_yixuan();
	}, "json");
}

/**
 * 增加分组项目
 */
function tcinsertitem(row) {
	var rowsLength = item_id;
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
				item_id.push({
					item_id :row.id,
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
					personal_pay:0,
					amount : row.amount
				});
				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 item_id.push({
			    		 	item_id :row.id,
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.item_amount,
							sex : row.sex,
							discount : row.discount,
							itemnum:row.itemnum,
							set_num : row.set_num,
							item_type:row.item_type,
							team_pay : row.amount,
							exam_indicators:'T',
							personal_pay:0,
							amount : row.amount
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
		
		var rows = item_id;
		if (!rows.length == 0) {
			for (var j = countitemrow; j <= rows.length - 1; j++)//已选择
			{
				if(rows[j].yjx != 1){
				var row = rows[j];
					row.discount = disvar;
					row.amount = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 2);	
					row.team_pay = decimal(Number(row.item_amount)*Number(row.itemnum) * Number(disvar) / 10, 2);
				}
			}//j End             
		}
		countamt();
		item_qihuan_yixuan();
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
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 4);
		
		
		if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			$('#amount').val(jine);
			return;
		}
			
		 if(Number(newdiscont)<Number($('#web_Resource').val())){
				//资源
				$.messager.alert("提示信息","本操作人员最大权限只能打"+$('#web_Resource').val()+"折！","error");
				$('#amount').val(jine);
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
	var rows = item_id;
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
//	if(oldamt==0){
//		$("#discount").val(10);
//	}else{
//		$("#discount").val(decimal(Number(newamt) / Number(oldamt) * 10, 2));
//	}
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
		   var rowsLength = item_id;
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
						item_id.push({
							item_id :row.id,
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.item_amount,
							sex : row.sex,
							itemnum:row.itemnum,
							discount :$("#discount").val(),
							set_num : row.set_num,
							item_type:row.item_type,
							team_pay : row.amount,
							exam_indicators:'T',
							personal_pay:0,
							amount : row.amount
						});
						countamt();
					}else{
						 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
					     if(r){
					    	 item_id.push({
					    		 	item_id :row.id,
									item_code : row.item_code,
									item_name : row.item_name,
									dep_name : row.dep_name,
									item_amount : row.item_amount,
									sex : row.sex,
									itemnum:row.itemnum,
									discount :$("#discount").val(),
									set_num : row.set_num,
									item_type:row.item_type,
									team_pay : row.amount,
									exam_indicators:'T',
									personal_pay:0,
									amount : row.amount
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
	var rows = item_id;
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
	function djtcustadd() {
		var itemrows = item_id;
		if (itemrows.length>0) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			var itemrows = item_id;  
		    var itementities = "";
		    for(i = countitemrow;i < itemrows.length;i++)  
		    {	if(itemrows[i].yjx != 1){
//			    	var discountValue = $('#discount_input' + itemobject.item_code).val();
//					itemobject.discount=discountValue;
//					var personal_pay_input = $('#personal_pay' + itemobject.item_code).val();
//					itemobject.personal_pay=personal_pay_input;
//					var team_pay_input = $('#team_pay' + itemobject.item_code).val();
//					itemobject.team_pay=team_pay_input;
//					itemobject.itemnum= $('#itemnum_input' + itemobject.item_code).val();
//					var exam_indicators_input=$('#exam_indicatorss' + itemobject.item_code).val();
//					itemobject.exam_indicators=exam_indicators_input;
//		    		itemrows[i].amount=Number(itemrows[i].personal_pay)+Number(itemrows[i].team_pay);
		    	 	itementities = itementities  + JSON.stringify(itemrows[i]); 
		    	}	  
		    } 
		    
		    var setrows = $('#selectctlist').datagrid('getRows');  
		    var setentities = "";
		    for(i = 0;i < setrows.length;i++)  
		    {
				var setobject = setrows[i];
				setobject.exam_indicators='T';	
				setentities = setentities + JSON.stringify(setobject);
			} 	  	
		    		    
			$.ajax({
				url : 'djtTTcustSaveItemSet.action',
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
							if(text=="ok"){
								$.messager.alert("本操作员最大权限打"+$('#web_Resource').val()+"折,项目折扣超出权限！");
							}else{
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

	//----------------------------------------显示收费项目-------------------------------------------------
	function setChangItemListGridreload(){
		var listh1= new Array();
		 var checkedItems = $('#selectchangitemlist').datagrid('getRows');
		    $.each(checkedItems, function(index, item){
		        	listh1.push(item.item_code);
		    });
		var model = {
				"setname" : $("#itemname").val(),
				"sex":$("#custsex").val(),
				"itementities":listh1.toString(),
				"dep_id":$('#depd_tree').tree('getSelected').id
			};
		$("#changitemlist").datagrid('reload',model);
	}
	/**
	 * 显示体检项目套餐信息
	 */
	function setChangItemListGrid() {
		 var listh1= new Array();
		 var checkedItems = $('#selectchangitemlist').datagrid('getRows');
		    $.each(checkedItems, function(index, item){
		        	listh1.push(item.item_code);
		    });
		var model = {
			"setname" : $("#itemname").val(),
			"sex":$("#custsex").val(),
			"itementities":listh1.toString()
			/*"dep_id":$('#serch_dep_id').combobox('getValue')*/
		};
		$("#changitemlist").datagrid(
				{
					url : 'djtchangitemlist.action',
					dataType : 'json',
					queryParams : model,
					rownumbers : false,
					pageSize: 30,//每页显示的记录条数，默认为10 
					pageList : [ 30, 60, 90, 120, 150 ],//可以设置每页记录条数的列表 
					columns : [[ {align : 'left',	field : 'item_code',title : '项目编码',width : 10},
					         {align : 'left',field : 'item_name',title : '项目名称',	width : 40,"formatter":f_showitem}, 
					         {align : 'left',field : 'dep_name',title : '所属科室',width : 22}, 
					         {align : 'center',field : 'sex',title : '性别',width : 10}, 
					         {align : 'center',field : 'item_amount',title : '金额',width : 10}
					         ]],
					onLoadSuccess : function(value) {
						$("#datatotal").val(value.total);
						$("#itemname").focus();
					},
					rowStyler:function(index,row){    
						 if (row.limit_count==1){  
					            return 'background-color:#ddceb2;';    
					        }   
				    },
					singleSelect : true,
					collapsible : true,
					pagination : true,
					fitColumns : true,
					striped : true,
					onDblClickRow : function(index, row) {
						if(row.limit_count==0){
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
						$('#itemname').focus(); 
						$("#itemname").select();
					}
					}
				});
		$("#changitemlist").datagrid({}).datagrid("keyCtr");
		//document.getElementById("itemname").value = '';
		//document.getElementById("itemname").focus();
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

function biaojishixiao(row){
	var index = $('#changitemlist').datagrid('getRowIndex',row);
	row.limit_count = 1;
	$('#changitemlist').datagrid('updateRow',{index:index,row:row});
}	
	var x_zhekou="";
	var x_jine= "";
	var x_shuliang="";
    //通过修改单个折扣率算金额
    function calcAmt(rowIndex){
    	var objGrid = $("#selectchangitemlist");        // 表格对象
    	var discountEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'discount'});        // 折扣额对象
        var amountEdt = objGrid.datagrid('getEditor', {index:rowIndex, field:'amount'}); 
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
        var depositEdt = objGrid.datagrid('getEditor', {index:desindex, field:'amount'});        // 折扣率对象
        var invMoneyValue = rows[desindex].item_amount;            // 金额值        
        var amountValue = $(depositEdt.target).val();    // 现有金额    
        var itemnumValue=rows[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var depositValue = decimal(Number(amountValue) / (Number(invMoneyValue)*Number(itemnumValue)) * 10, 4);	
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
        	rows[desindex].amount=x_jine;
        	 $('#selectchangitemlist').datagrid('refreshRow', desindex);
        	 $("#selectchangitemlist").datagrid('endEdit', desindex); 
        	return;
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
    
    function calcDeposit(obj,id){
    	var $amountInput = $(obj);
    	//先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
	        //只允许一个小数点              
	        replace(/^\./g, "").replace(/\.{2,}/g, ".").
	        //只能输入小数点后两位
	        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3'));
	    //最后一位是小数点的话，移除
	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
    	var rows = item_id;
    	var desindex;
    	for(var i=0;i<item_id.length;i++){
			if(item_id[i].item_id == id){
				desindex = i;
			}
		}
        var discountValue = $(obj).val();                // 折扣率 值
        var itemnumValue=item_id[desindex].itemnum; 
        if (!isSZZoo(itemnumValue)){
   		 itemnumValue=1;
   	    }
        var invMoneyValue = decimal(item_id[desindex].item_amount*Number(itemnumValue),2);           // 金额值
        var depositValue = decimal(Number(item_id[desindex].item_amount)*Number(itemnumValue) * Number(discountValue) /10, 2);	
        if(Number(discountValue)>10){
        	discountValue=10;
        	depositValue=invMoneyValue;
        }
        //资源
        if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
        	$.messager.alert("提示信息","没有打折权限！","error");
        	show_item_yi();
        	return;
        }
        if(discountValue<$('#web_Resource').val() && discountValue != item_id[desindex].discount ){
        	$.messager.alert("提示信息","本操作员最大权限只能打"+$('#web_Resource').val()+"折！","error");
        	show_item_yi();
        	return;
        }
        
        item_id[desindex].discount=discountValue; 
        item_id[desindex].amount=depositValue;    // 折扣额  赋值    
        item_id[desindex].team_pay=depositValue;    // 折扣额  赋值   
        countamt3();
        show_item_yi();
    }
	//----------------------------------------显示分组收费项目-------------------------------------------------
	/**
	 * 显示体检项目套餐信息
	 */
	 var lastIndex; 
	function setfzchareitemListGrid() {
		var model = {"id":$("#exam_id").val(),"exam_num":$("#exam_num").val()};
		$("#selectchangitemlist").datagrid({
			url : 'custchangitemlist.action',
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
			   {align : 'center',field : 'itemnum',title : '数量',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 10,editor : {type : 'text'}}, 
			   {align : 'center',field : 'amount',title : '金额',	width : 10,	editor : {type : 'text'}} 
			   ]],
			onLoadSuccess : function(value) {
				$("#selectchangitemlist").datagrid("hideColumn", "item_type"); // 设置隐藏列  
				$("#datatotal").val(value.total);
				var data=$('#selectchangitemlist').datagrid('getData');
				countitemrow=data.rows.length;
				countamt();
				var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
				if(is_show_discount==0){
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
	
//-------------------------------------复制套餐项目-----------------------
	//复制本人上次体检项目
	function getcopyitemg(){
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		$.ajax({
			url:'getcopaitem.action',
			data:{
				customer_id:$('#a_customer_id').val(),
				copy_status:''
				},
			type:'post',
			success:function(data){
				$(".loading_div").remove();
				deltiemflags=1;
				delsetflags=1;
				var row = eval('('+data+')');
				if(row.li.length == 0){
					$.messager.alert('提示信息','无上次体检项目信息','error');
					return;
				}else{
					$.messager.alert('提示信息','操作成功','info');
				}
				var item = row.li;
				var set = row.se;
				//复制项目
				for(var i = 0 ; i < item.length ; i++){
					tcinsertitem(item[i]);
					copy_item = "N";
				}
				getItemShow();
				//复制套餐
				for(var i = 0 ; i < set.length ; i++){
					inserttc(set[i]);
				}
				copy_item = "Y";
			},error:function(){
				$(".loading_div").remove();
				$.messager.alert('提示信息','操作失败','error');
			}
		})
	}
	//复制他人 项目
	function getcopyitemg2(){
		$('#dlg-item-fuzhi').dialog({
			title:'复制项目',    
			href: 'getcopaitemPage.action',   
		})
		$('#dlg-item-fuzhi').dialog('open');  
	}
/************************************************新模式加项*******************************************************/	
	/**
	 * 科室树
	 */
	function dep_tree(){
		$('#depd_tree').tree({    
			url:'depTree.action',
		    onLoadSuccess:function(node,data){
		    	 var n = $('#depd_tree').tree('find', data[0].id);
		         $('#depd_tree').tree('select', n.target);
		    },
		    onSelect:function(node){
		    	$('#btn-yxxm').attr('checked',false);
		    	getItemShow();
			},
			onBeforeLoad: function (node, param) {
				param.sex = $('#custsex').val();
			}
			
		});
	}
	var item_id = new Array();//项目id
	var all_item_list;
	function getItemShow(type){
		var dep_id = $('#depd_tree').tree('getSelected').id;
		var dep_c = $('#depd_tree').tree('getSelected').depttype;
		var exam_indicator = 'T';
		var exam_id = $('#exam_id').val();
		var item_name = '';
		var sex = $("#custsex").val(); 
		if(type == 1 && $('#c_item_name').val() != ''){
			dep_c = 0;
			item_name = $('#c_item_name').val();
		}
			$.ajax({
				url:'getDJTItem.action',
				type:'post',
			    datatype: 'json',
				data:{
					dep_id:dep_id,
					dep_c:dep_c,
					exam_indicator:exam_indicator,
					exam_id:exam_id,
					item_name:item_name,
					sex:sex
				},
				async:false,
				success:function(data){
					all_item_list = eval('('+data+')');
					show_item();
				},
				error:function(){
				}
			});
	}
		
	function show_item(){
			var item = "";
			var da = all_item_list;
			var item_item = item_id; 
			item="<table>"
			for(var i = 0 ; i < da.length ; i++){
				item +="<tr><td colspan='4'><font style='font-weight:bold'  color='#ff9900'>"+da[i].dep_name+"</font></td></tr>"
				var li = da[i].li;
				if(li.length>0){
					for(var j = 0 ; j < li.length ; j++){
						item +="<tr><td>" ;//onclick='baocunitem_id("+li[j].id+",1)'
								var item_fal = 0;
								var item_fal1 = 0;
								var item_fal2 = 0;
								var item_fal3 = 0;
								//查询之前加项以保存的项目---2查询以前的
								for(var k = 0 ; k < item_item.length ; k++){
									if(li[j].item_code == item_item[k].item_code&&item_item[k].yjx==1){
										item_fal = 2;
									} 
								}
								if(isitemid(li[j].item_code)==1 && item_fal!=2){
									item+="<input type='checkbox' id='check_"+li[j].id+"' checked='checked' onclick='baocunitem_id("+li[j].id+")'/>" //data=1是已选选项目不能操作标示
									item+="<label onclick='baocunitem_id("+li[j].id+",1)'><font color='#e69138' id='name_"+li[j].id+"'>"+li[j].item_name+'--'+li[j].amount+"元</font></label></td>"
								} else if(item_fal == 2){
									item+="<input type='checkbox' id='check_"+li[j].id+"' checked='checked' disabled='disabled'/>"
									item+="<font color='#e69138' id='name_"+li[j].id+"' style='font-weight:bold'>"+li[j].item_name+'--'+li[j].amount+"元</font></td>"
								} else {
									item+="<input type='checkbox' id='check_"+li[j].id+"' onclick='baocunitem_id("+li[j].id+")'/>"
									item+="<label onclick='baocunitem_id("+li[j].id+",1)'><font color='' id='name_"+li[j].id+"'>"+li[j].item_name+'--'+li[j].amount+"元</font></label></td>"
								}
								if(j+1<li.length){
									item+="<td>"
									for(var k = 0 ; k < item_item.length ; k++){
										if(li[j+1].item_code == item_item[k].item_code&&item_item[k].yjx==1){
											item_fal1 = 2;
										} 
									}
									if(isitemid(li[j+1].item_code)==1&&item_fal1!=2){
										item+="<input type='checkbox' id='check_"+li[j+1].id+"'  checked='checked'  onclick='baocunitem_id("+li[j+1].id+")'/>"
										item+="<label onclick='baocunitem_id("+li[j+1].id+",1)'><font color='#e69138' id='name_"+li[j+1].id+"'>"+li[j+1].item_name+'--'+li[j+1].amount+"元</font></label></td>"
									} else if(item_fal1 == 2){
										item+="<input type='checkbox' id='check_"+li[j+1].id+"' checked='checked' disabled='disabled'  />"
										item+="<font color='#e69138' style='font-weight:bold' id='name_"+li[j+1].id+"'>"+li[j+1].item_name+'--'+li[j+1].amount+"元</font></td>"
									} else {
										item+="<input type='checkbox' id='check_"+li[j+1].id+"' onclick='baocunitem_id("+li[j+1].id+")'   />"
										item+="<label onclick='baocunitem_id("+li[j+1].id+",1)'><font color='' id='name_"+li[j+1].id+"'>"+li[j+1].item_name+'--'+li[j+1].amount+"元</font></label></td>"
									}
								} 
								if(j+2<li.length){
									item +="<td>"
									for(var k = 0 ; k < item_item.length ; k++){
										if(li[j+2].item_code == item_item[k].item_code&&item_item[k].yjx==1){
											item_fal2 = 2;
										} 
									}
									if(isitemid(li[j+2].item_code)==1&&item_fal2!=2){
										item+="<input type='checkbox' id='check_"+li[j+2].id+"' checked='checked'  onclick='baocunitem_id("+li[j+2].id+")'/>"
										item+="<label onclick='baocunitem_id("+li[j+2].id+",1)'><font  color='#e69138' id='name_"+li[j+2].id+"'>"+li[j+2].item_name+'--'+li[j+2].amount+"元</font></label></td>"
									} else if(item_fal2 == 2){
										item+="<input type='checkbox' id='check_"+li[j+2].id+"' checked='checked' disabled='disabled'/>"
										item+="<font color='#e69138' style='font-weight:bold' id='name_"+li[j+2].id+"'>"+li[j+2].item_name+'--'+li[j+2].amount+"元</font></td>"
									} else {
										item+="<input type='checkbox' id='check_"+li[j+2].id+"' onclick='baocunitem_id("+li[j+2].id+")'/>"
										item+="<label onclick='baocunitem_id("+li[j+2].id+",1)'><font color='' id='name_"+li[j+2].id+"'>"+li[j+2].item_name+'--'+li[j+2].amount+"元</font></label></td>"
									}
								} 
								if(j+3<li.length){
									item+="<td>"
									for(var k = 0 ; k < item_item.length ; k++){
										if(li[j+3].item_code == item_item[k].item_code&&item_item[k].yjx==1){
											item_fal3 = 2;
										} 
									}
									if(isitemid(li[j+3].item_code)==1&&item_fal3!=2){
										item+="<input type='checkbox' id='check_"+li[j+3].id+"' checked='checked'  onclick='baocunitem_id("+li[j+3].id+")'/>"
										item+="<label onclick='baocunitem_id("+li[j+3].id+",1)'><font color='#e69138' id='name_"+li[j+3].id+"'>"+li[j+3].item_name+'--'+li[j+3].amount+"元</font></label></td>"
									} else if(item_fal3 == 2){
										item+="<input type='checkbox' id='check_"+li[j+3].id+"' checked='checked' disabled='disabled'  />"
										item+="<font color='#e69138' style='font-weight:bold' id='name_"+li[j+3].id+"'>"+li[j+3].item_name+'--'+li[j+3].amount+"元</font></td>"
									} else {
										item+="<input type='checkbox' id='check_"+li[j+3].id+"'  onclick='baocunitem_id("+li[j+3].id+")'   />"
										item+="<label onclick='baocunitem_id("+li[j+3].id+",1)'><font color='' id='name_"+li[j+3].id+"'>"+li[j+3].item_name+'--'+li[j+3].amount+"元</font></label></td>"
									}
								} 
								item+="</td></tr>";
								if(j+3<li.length || j+2<li.length || j+1<li.length){
									j = j + 3 ;
								}
					}
				}
			}
			item+="<table/>";
		document.getElementById("itemshow").innerHTML = item;
	}

	var quanbuitem;
	function allitem_li(){
		$.ajax({
			url:'getDJTItem.action',
			type:'post',
		    datatype: 'json',
			data:{
				dep_id:0,
				dep_c:9,
				exam_indicator:'T',
				exam_id:$('#exam_id').val(),
				exam_num:$('#exam_num').val(),
				sex:$("#custsex").val()
			},
			async:false,
			success:function(data){
				quanbuitem = eval('('+data+')');
			},
			error:function(){
			}
		});
	}

function item_qihuan_yixuan(type){
	allitem_li();
	if(type == 1){
		if($("#btn-yxxm").is(":checked")){
			$("#btn-yxxm").attr('checked',false);
		}else{
			$("#btn-yxxm").attr('checked',true);
		}
	}
	if($("#btn-yxxm").is(":checked")){
		show_item_yi();
	}else{
		show_item();
	}
}
function show_item_yi(){
	var item = "";
	var da = quanbuitem;
	var item_item = item_id;
	var yixuan_item = new Array();
	for(var i = 0 ; i < da.length ; i++){
		var li = da[i].li;
		var xiangmu = new Array();
		for(var m = 0 ; m < li.length ; m++){
			for(var n=0;n < item_item.length; n++){
				if(li[m].item_code == item_item[n].item_code){
					xiangmu.push(item_item[n]);
				}
			}
		}
		if(xiangmu.length > 0){
			da[i].li = xiangmu;
			yixuan_item.push(da[i]);
		}
	}
	da = yixuan_item;
	item="<table>"
	for(var i = 0 ; i < da.length ; i++){
		item +="<tr><td colspan='4'><font style='font-weight:bold'  color='#ff9900'>"+da[i].dep_name+"</font></td></tr>"
		var li = da[i].li;
		if(li.length>0){
			for(var j = 0 ; j < li.length ; j++){
				item +="<tr><td>";
						var item_fal = 0;
						var item_fal1 = 0;
						var item_fal2 = 0;
						var item_fal3 = 0;
						//查询之前加项以保存的项目---2查询以前的
						for(var k = 0 ; k < item_item.length ; k++){
							if(li[j].item_code == item_item[k].item_code&&item_item[k].yjx==1){
								item_fal = 2;
							} 
						}
						if(isitemid(li[j].item_code)==1 && item_fal!=2){
							item+="<a href='#' onclick='deleteitem("+li[j].item_id+")'><img src=\"themes/default/images/blank.gif\"  data-options='width:50,height:50' class=\"icon ico-delete\" alt=\"删除\" /></a>" //data=1是已选选项目不能操作标示
							item+="<label id='item_name_"+li[j].item_id+"' ondblclick='calmante("+li[j].item_id+")' ><font id='name_"+li[j].item_id+"'>"+li[j].item_name+'--金额:'+li[j].amount+"元，"+li[j].discount+"折</font></label></td>"
						} else if(item_fal == 2){
							item+="<div style='width:20px;height:10px;float: left;'></div>"
							item+="<font id='name_"+li[j].item_id+"' style='font-weight:bold'>"+li[j].item_name+'--金额:'+li[j].amount+"元，"+li[j].discount+"折</font></td>"
						}
						if(j+1<li.length){
							item+="<td>"
							for(var k = 0 ; k < item_item.length ; k++){
								if(li[j+1].item_code == item_item[k].item_code&&item_item[k].yjx==1){
									item_fal1 = 2;
								} 
							}
							if(isitemid(li[j+1].item_code)==1&&item_fal1!=2){
								item+="<a href='#' onclick='deleteitem("+li[j+1].item_id+")'><img src=\"themes/default/images/blank.gif\"  data-options='width:50,height:50' class=\"icon ico-delete\" alt=\"删除\" /></a>"
								item+="<label id='item_name_"+li[j+1].item_id+"' ondblclick='calmante("+li[j+1].item_id+")'><font id='name_"+li[j+1].item_id+"'>"+li[j+1].item_name+'--金额:'+li[j+1].amount+"元，"+li[j+1].discount+"折</font></label></td>"
							} else if(item_fal1 == 2){
								item+="<div style='width:20px;height:10px;float: left;'></div>"
								item+="<font style='font-weight:bold' id='name_"+li[j+1].item_id+"'>"+li[j+1].item_name+'--金额:'+li[j+1].amount+"元，"+li[j+1].discount+"折</font></td>"
							}
						} 
						if(j+2<li.length){
							item +="<td>"
							for(var k = 0 ; k < item_item.length ; k++){
								if(li[j+2].item_code == item_item[k].item_code&&item_item[k].yjx==1){
									item_fal2 = 2;
								} 
							}
							if(isitemid(li[j+2].item_code)==1&&item_fal2!=2){
								item+="<a href='#' onclick='deleteitem("+li[j+2].item_id+")'><img src=\"themes/default/images/blank.gif\"  data-options='width:50,height:50' class=\"icon ico-delete\" alt=\"删除\" /></a>"
								item+="<label id='item_name_"+li[j+2].item_id+"' ondblclick='calmante("+li[j+2].item_id+")'><font id='name_"+li[j+2].item_id+"'>"+li[j+2].item_name+'--金额:'+li[j+2].amount+"元，"+li[j+2].discount+"折</font></label></td>"
							} else if(item_fal2 == 2){
								item+="<div style='width:20px;height:10px;float: left;'></div>"
								item+="<font style='font-weight:bold' id='name_"+li[j+2].item_id+"'>"+li[j+2].item_name+'--金额:'+li[j+2].amount+"元，"+li[j+2].discount+"折</font></td>"
							}
						} 
						if(j+3<li.length){
							item+="<td>"
							for(var k = 0 ; k < item_item.length ; k++){
								if(li[j+3].item_code == item_item[k].item_code&&item_item[k].yjx==1){
									item_fal3 = 2;
								} 
							}
							if(isitemid(li[j+3].item_code)==1&&item_fal3!=2){
								item+="<a href='#' onclick='deleteitem("+li[j+3].item_id+")'><img src=\"themes/default/images/blank.gif\"  data-options='width:50,height:50' class=\"icon ico-delete\" alt=\"删除\" /></a>"
								item+="<label id='item_name_"+li[j+3].item_id+"' ondblclick='calmante("+li[j+3].item_id+")'><font id='name_"+li[j+3].item_id+"'>"+li[j+3].item_name+'--金额:'+li[j+3].amount+"元，"+li[j+3].discount+"折</font></label></td>"
							} else if(item_fal3 == 2){
								item+="<div style='width:20px;height:10px;float: left;'></div>"
								item+="<font style='font-weight:bold' id='name_"+li[j+3].item_id+"'>"+li[j+3].item_name+'--金额:'+li[j+3].amount+"元，"+li[j+3].discount+"折</font></td>"
							}
						} 
						item+="</td></tr>";
						if(j+3<li.length || j+2<li.length || j+1<li.length){
							j = j + 3 ;
						}
			}
		}
	}
	item+="<table/>";
    document.getElementById("itemshow").innerHTML = item;
}
	function isitemid(h_id){
		for(var i = 0 ; i < item_id.length ; i++){
			if(item_id[i].item_code==h_id){
				return 1;
			} 
		}
	}
function baocunitem_id(id,type){
	if(!$("#check_"+id).attr('disabled')){
		if(type == 1){
			if($("#check_"+id).is(":checked")){
				$("#check_"+id).attr('checked',false);
				$("#name_"+id).attr('color','');
			}else{
				$("#check_"+id).attr('checked',true);
				$("#name_"+id).attr('color','#e69138');
			}
		}
		if($("#check_"+id).is(":checked")){
			for(var i=0;i<all_item_list.length;i++){
				var li = all_item_list[i].li;
				for(j=0;j<li.length;j++){
					if(id == li[j].id){
						var row = li[j];
						row.discount = $("#discount").val();
						row.itemnum=1;
						insertitem(row);
						countamt();
					}
				}
			}
		}else{
			for(var i=0;i<item_id.length;i++){
				if(item_id[i].item_id == id){
					item_id.splice(i,1);
					countamt();
				}
			}
		}
	}
	show_item();
	$('#c_item_name').val('');
	$('#c_item_name').focus();
}

function calmante(id){
	show_item_yi();
	for(var i=0;i<item_id.length;i++){
		if(item_id[i].item_id == id){
			$("#item_name_"+id).html("<font id='name_"+item_id[i].item_id+"'>"+item_id[i].item_name+'--金额:'+item_id[i].amount+"元，<input style='width:30px;' onblur='calcDeposit(this,"+item_id[i].item_id+")' value='"+item_id[i].discount+"'/>折</font>");
			$("#item_name_"+id).children('font').children('input').select();
			$("#item_name_"+id).children('font').children('input').focus();
		}
	}
}
/**
 * 增加分组项目a1
 */
function insertitem(row) {
	var rowsLength = item_id;
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
				item_id.push({
					item_id : row.id,
					item_code : row.item_code,
					item_name : row.item_name,
					dep_name : row.dep_name,
					item_amount : row.amount,
					sex : row.sex,
					itemnum:row.itemnum,
					discount : row.discount,
					set_num : row.set_num,
					item_type:row.item_type,
					team_pay : row.amount,
					exam_indicators:'T',
					personal_pay:0,
					amount : row.amount
				});
				 d_item.get_item_2(row.id);
//				 biaojishixiao(row);
//				countamt();
			}else{
				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
			     if(r){
			    	 item_id.push({
			    		 	item_id :row.id,
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.amount,
							sex : row.sex,
							itemnum:row.itemnum,
							discount : row.discount,
							set_num : row.set_num,
							item_type:row.item_type,
							team_pay : row.amount,
							exam_indicators:'T',
							personal_pay:0,
							amount : row.amount
						});
				    	 d_item.get_item_2(row.id);
				    	 countamt();
//				    	 biaojishixiao(row);
				    	 $("#check_"+row.id).attr('checked',true);
						 $("#name_"+row.id).attr('color','#e69138');
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

/**
 * 已选项目删除
 */
function deleteitem(id){
	var item_name;
	for(var i=0;i<item_id.length;i++){
		if(item_id[i].item_id == id){
			item_name = item_id[i].item_name;
		}
	}
	$.messager.confirm('确认','删除项目--'+item_name+'',function(r){    
	    if (r){ 
	    	for(var i=0;i<item_id.length;i++){
				if(item_id[i].item_id == id){
					item_id.splice(i,1);
					countamt();
					show_item_yi();
				}
			}
	    }
    });    
}

function selectsetforsexTOG() {
    var sexencode=encodeURIComponent(encodeURIComponent($("#custsex").val()));
	$('#dlg-selectsetforsexTOG').dialog(
			{
				title : '选择套餐',
				href : 'getExamSetTreeSelect.action?sex='+sexencode+'&exam_type=G',
			});
	$('#dlg-selectsetforsexTOG').dialog('open');
}