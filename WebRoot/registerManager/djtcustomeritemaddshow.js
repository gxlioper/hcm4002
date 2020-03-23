var deltiemflags=0;
var delsetflags=0;
var countitemrow=0;
var jine=""; 
var zhekou="";
var copy_item = "";
$(document).ready(function() {
	if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined ){
		$('#z_zhi').text("没有打折权限！");
	}else{
		$('#z_zhi').text("最大打折权限："+$('#web_Resource').val()+"折！");
	}
	var is_show_discount=$("#is_show_discount").val(); //是否显示折扣率及折扣后的金额   0不显示   1显示
	if(is_show_discount==0){
		$('#zkl').hide();// 设置隐藏列
		$('#zkhze').hide();// 设置隐藏列
		$('#discount').hide(); // 设置隐藏列
		$('#amount').hide();// 设置隐藏列
		$('#yuan').hide();// 设置隐藏列
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
					 $('#changitemlist').datagrid("selectRow", 0);  
					 $('.datagrid-row-selected').attr('tabindex',"0");
					 $('.datagrid-row-selected').focus();
			 }
		} else {
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
	
	$('#discount').bind('click',function(){
		zhekou = $(this).val();
	})
	/*$('#amount').bind('click',function(){
		jine = $(this).val();
	});*/
	
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
	var model = {
		"exam_num" : $("#exam_num").val()
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
			for (var j = 0; j <= rows.length-countitemrow-1; j++)//已选择
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
		var countAllMon = 0;
		var countMonAlready = 0;
		if (!rows.length == 0) {
			for (var j =0; j < rows.length-countitemrow; j++)//已选择
			{
				var row = rows[j];
				addCountMon+=Number(row.item_amount);
			} 
			//计算已有项目的优惠金额
			for (var k =rows.length-countitemrow; k < rows.length; k++)//已选择
			{
				var row = rows[k];
				countAllMon+=Number(row.item_amount);
				countMonAlready+=Number(row.amount);
			}
		}
		//重新优惠价格
		var amountCha = decimal(Number($("#item_amount").val()-disvar-(countAllMon-countMonAlready)),2);
		//alert(amountCha+"==="+addCountMon);
		if(addCountMon>0){ //只有当添加项目大于0时候 才有优惠
			if(amountCha>addCountMon){
				for (var j =0; j < rows.length-countitemrow; j++){
					var row = rows[j];
						row.discount = 10;
						row.amount = row.item_amount;	
						$('#selectchangitemlist').datagrid('refreshRow', j);
				}
				$.messager.alert("提示信息","折扣金额大于添加项目金额！","error");
				var amountOld = 0;
				//计算前折扣金额
				for (var m =0; m < rows.length; m++){
					    var row = rows[m];
						amountOld  = decimal(amountOld + Number(row.amount),2);
				}
				$("#amount").val(amountOld);
				
				return;
			}
		}else{
			$.messager.alert("提示信息","未添加项目不能有折扣金额！","error");
			var amountOld = 0;
			//计算前折扣金额
			for (var m =0; m < rows.length; m++){
				    var row = rows[m];
					amountOld  = decimal(amountOld + Number(row.amount),2);
			}
			$("#amount").val(amountOld);
			return;
		}
		
		
		var newdiscont=decimal(Number(disvar) / Number($("#item_amount").val()) * 10, 4);
		if($('#web_Resource').val()=="" || $('#web_Resource').val()==undefined){
			$.messager.alert("提示信息","没有打折权限！","error");
			//$('#amount').val(jine);
			var amountOld = 0;
			//计算前折扣金额
			for (var m =0; m < rows.length; m++){
				    var row = rows[m];
					amountOld  = decimal(amountOld + Number(row.amount),2);
			}
			$("#amount").val(amountOld);
			return;
		}
			
		 if(Number(newdiscont)<Number($('#web_Resource').val())){
				//资源
				$.messager.alert("提示信息","本操作人员最大权限只能打"+$('#web_Resource').val()+"折！","error");
				//$('#amount').val(jine);
				var amountOld = 0;
				//计算前折扣金额
				for (var m =0; m < rows.length; m++){
					    var row = rows[m];
						amountOld  = decimal(amountOld + Number(row.amount),2);
				}
				$("#amount").val(amountOld);
				return;
		}
		// newdiscont 为输入金额后改变的折扣率 为总折扣率
		document.getElementById("discount").value=newdiscont;
		
		//添加项目优惠的总金额
		var allAmount =  decimal(Number($("#item_amount").val())-Number(disvar)-Number(countAllMon-countMonAlready),2); 
		var addAmount = 0;
		if (!rows.length == 0) {
			for (var i =0; i < rows.length-countitemrow; i++){
				var row = rows[i];
				//所有添加项目的总额
				addAmount  = decimal(addAmount + Number(row.item_amount),2);
			}  
			var addDiscont = decimal(Number(addAmount-allAmount) / Number(addAmount) * 10, 4);
			for (var j =0; j < rows.length-countitemrow; j++){
				var row = rows[j];
				//所有添加项目的总额
				row.discount = addDiscont;
				row.amount = decimal(Number(row.item_amount)* Number(addAmount-allAmount) / Number(addAmount), 2);	
				$('#selectchangitemlist').datagrid('refreshRow', j);
			}             
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

//计算总金额
function countamt() {
	var rows = $('#selectchangitemlist').datagrid('getRows');
	var flag = true;//不相等
	var oldamt = 0, newamt = 0;
	if(rows.length!='undefined'){
		
		//计算原项目金额
		if (!rows.length == 0) {
			for (var j = rows.length-countitemrow; j <= rows.length - 1; j++)//已选择
			{
				var row = rows[j];
				oldamt = decimal(oldamt + Number(row.item_amount)*Number(row.itemnum),2);
				newamt = decimal(newamt + Number(row.amount),2);
			}//j End             
		}
		
		//计算新添加项目金额
		var personal_pay_count=document.getElementsByName("itemnum_input"); //数量
		var personal_pay_inputss=document.getElementsByName("personal_pay_input");
		for(var i=0;i<rows.length-countitemrow;i++){
			oldamt = decimal(oldamt + Number(rows[i].item_amount)*Number(personal_pay_count[i].value),2);
			newamt = decimal(newamt + Number(personal_pay_inputss[i].value),2); //新增个人付费
		}
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
			$("#discount").val(decimal(Number(newamt) / Number(oldamt) * 10, 4));
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
	function djtcustadd() {
		var itemrows = $('#selectchangitemlist').datagrid('getRows');
		if (itemrows.length>0) {
			 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			 $("body").prepend(str);
			var itemrows = $('#selectchangitemlist').datagrid('getRows');  
				
		    var itementities = "";
		    for(i = 0;i < itemrows.length-countitemrow;i++) {
		    	var itemobject=itemrows[i];
		    	itemobject.item_name = isIndexOfSpan(itemobject.item_name);
				itemobject.dep_name = isIndexOfSpan(itemobject.dep_name);
				//数量
				itemobject.itemnum= $('#itemnum_input' + itemobject.item_code).val();
				//折扣率
				var discountValue = $('#discount_input' + itemobject.item_code).val();
				itemobject.discount=discountValue;
				//金额
				var pay_amount = $('#personal_pay' + itemobject.item_code).val();
				itemobject.amount=pay_amount;
				
				itementities = itementities + JSON.stringify(itemobject);
		    	//itementities = itementities  + JSON.stringify(itemrows[i]);    
		    } 
		    //console.log(itementities)
		    var setrows = $('#selectctlist').datagrid('getRows');  
		    var setentities = "";
		    for(i = 0;i < setrows.length;i++)  
		    {  
		    	setentities = setentities  + JSON.stringify(setrows[i]);    
		    } 	  	
			$.ajax({
				url : 'djtGcustSaveItemSet.action',
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
		//$("#changitemlist").datagrid({}).datagrid("keyCtr");
		
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
		var model = {"exam_num":$("#exam_num").val()};
		$("#selectchangitemlist").datagrid({
			url : 'custchangitemlist.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : false,
			pageSize: 15,//每页显示的记录条数，默认为10 
			pageList : [ 15, 20, 30, 40, 10 ],//可以设置每页记录条数的列表 
			columns : [[ 
			   {align : "center",field : "fxfzddd",title : "删除",width : 10,	"formatter" : f_dellchargitem},
			   {align : 'left',field : 'item_code',title : '项目编码',width : 15}, 
			   {align : 'center',field : 'item_type',title : '项目类型',width : 20}, 
			   {align : 'center',field : 'item_category',title : '项目类型',width : 20,"formatter" : f_itemCategory,hidden:true},
			   {align : 'left',field : 'item_name',title : '项目名称',width :40},
			   {align : 'left',field : 'dep_name',title : '科室',width : 25}, 
			   {align : 'left',field : 'id',title : 'ID',width : 15,hidden:true},
			   {align : 'left',field : 'sex',title : '性别',width : 15,hidden:true},
			   {align : 'center',field : 'item_amount',title : '原金额',width : 10},
			   {align : 'center',field : 'itemnum',title : '数量',width : 10,"formatter" : f_itemnum}, 
			   {align : 'center',field : 'discount',title : '折扣率',width : 15,"formatter" : f_discount}, 
			   {align : 'center',field : 'amount',title : '金额',	width : 20,	"formatter" : f_personal_pay} 
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
			height:"550",
			/*pageNumber : 1,
			pageSize : 1000,*/
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
	
	function f_itemCategory(val,row){
		if(val=="" || val==null){
			return "";
		}else{
			return "<span id=\""+row.item_code+"_item_category\">"+val+"</span>";
		}
	}
	
	var shuliang="";
	//返回数量编辑列
	function f_itemnum(val, row) {
		if (deltiemflags == 0) {
			return row.itemnum;
		} else {
			return '<input type=\"text\" name=\"itemnum_input\" id=\"itemnum_input'
					+ row.item_code
					+ '\" maxlength=\"3\" size=\"5\"  onclick=\"z_shuliang(this)\" onblur=\"f_itemnum_input(\''
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
	//修改数量 改变金额
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
			    $("itemnum_input"+row_item_code).val(row_itemnum); //数量
				$('#personal_pay' + row_item_code).val(depositValue); //金额
		 countamt();
	}
	
	//返回折扣编辑列
	var xzhekou="";
	function f_discount(val, row) {
		if (deltiemflags == 0) {
			return row.discount;
		} else {
			return '<input type=\"text\" name=\"discount_input\" id=\"discount_input'
					+ row.item_code
					+ '\" maxlength=\"3\" size=\"5\"  onclick=\"z_zhekou(this)\" onblur=\"f_discount_input(\''
					+ row.item_code
					+ '\','
					+ row.item_amount
					+ ',this.value);\" value=\"' + row.discount + '\" />';
		}
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
		countamt();
	}
	
	function z_zhekou(value){
		xzhekou=$(value).val();
	}
	//返回金额编辑列
	var xjineFinal="";
	function f_personal_pay(val, row) {
		if (deltiemflags == 0) {
			return row.amount;
		} else {
			return '<input type=\"text\" name=\"personal_pay_input\" id=\"personal_pay'
					+ row.item_code
					+ '\"  maxlength=\"10\" size=\"10\" onclick=\"x_jineFinal(this)\" onchange=\"f_personal_pay_input(\''
					+ row.item_code
					+ '\','
					+ row.item_amount
					+ ',this.value);\" value=\"' + row.amount + '\" />';
		}
	}
	
	function x_jineFinal(value){
		xjineFinal=$(value).val();
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
				var depositValue = decimal(Number(personal_pay_input)/Number(invMoneyValue) *10, 4);
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
		countamt();
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
				//console.log(row);
				//复制项目
				for(var i = 0 ; i < item.length ; i++){
					tcinsertitem(item[i]);
					copy_item = "N";
				}
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