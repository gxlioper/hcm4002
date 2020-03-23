var exam_id = "";
var countitemrow=0;
var deltiemflags=0;
$(function() {
	exam_id = $("#exam_id").val();

	getgroupuserGrid1();
	getgroupuserGrid2();
	getgroupuserGrid3();
	getgroupuserGrid4();
	select_button();
	
	var columns = document.getElementsByClassName("columns ");// 获取到的是一个类数组
	for (var i = 0; i < columns.length; i++) {
		columns[i].style.display = "none";
	}
});
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
						$('#selectchangitemlist').datagrid("appendRow", {
							item_code : row.item_code,
							item_name : row.item_name,
							dep_name : row.dep_name,
							item_amount : row.amount,
							sex : row.sex,
							discount : $("#discount").val(),
							set_num : row.set_num,
							item_type:row.item_type,
							team_pay :0,
							itemnum:row.itemnum,
							exam_indicators:'G',
							personal_pay:row.item_amount
						});
						countamt();
					}else{
						 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
					     if(r){
					    	 $('#selectchangitemlist').datagrid("appendRow", {
									item_code : row.item_code,
									item_name : row.item_name,
									dep_name : row.dep_name,
									item_amount : row.amount,
									sex : row.sex,
									discount :$("#discount").val(),
									set_num : row.set_num,
									item_type:row.item_type,
									team_pay :0,
									itemnum:row.itemnum,
									exam_indicators:'G',
									personal_pay:row.item_amount
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
  $(window).on('load', function () {  
	                $('.selectpicker').selectpicker({  
	                    'selectedText': 'cat'
	                });  
	                // $('.selectpicker').selectpicker('hide');  
	               });  
	function select_button(){
		var sex=$("#custsex").val();
		$.ajax({  
		        "type" : 'get',  
		        "url": "satlistshow.action?sex="+sex,
		        "dataType" : "json",  
		        "success" : function(data) {
				var opts = "";
				for (var i = 0; i < data.length; i++) {
						opts += "<option value='"+ data[i]["id"]+"'>"+ data[i]["text"]+"</option>";
				}	
				$("#tclist").append(opts);
			}
		});  
	}	
$('#itemname').keyup(function(event) {
	if (event.keyCode == '38' || event.keyCode == '40') {
		// var s = $('#changitemlist').datagrid("getRows");
		// if(s.length>0){
		// $('#changitemlist').datagrid("selectRow", 0);
		// $('.datagrid-row-selected').attr('tabindex',"0");
		// $('.datagrid-row-selected').focus();
		// }
		reload();
	} else {
		setChangItemListGridreload();
	}
});
function djtsearch() {
	var itemname = $("#itemname").val();
	var opt = {
		url : "changitemlist.action?format=json&itemname="+itemname,
		silent : true,
		query : {
//			itemname : itemname
		}
	};
	$("#changitemlist").bootstrapTable('refresh', opt);
}
function showcolumns() {
	var columns = document.getElementsByClassName("columns ");// 获取到的是一个类数组
	for (var i = 0; i < columns.length; i++) {
		columns[i].style.display = "none"; // 隐藏
	}
}
function getgroupuserGrid1() {
	deleteItemList
	$('#changitemlist').bootstrapTable({
		method : 'get',
		url : 'changitemlist.action',
		dataType : "json",
		striped : true, // 使表格带有条纹
		pagination : true, // 在表格底部显示分页工具栏
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 50 ],
		idField : "exam_id", // 标识哪个字段为id主键
		showToggle : false, // 名片格式
		cardView : false,// 设置为True时显示名片（card）布局
		showColumns : true, // 显示隐藏列
		showRefresh : true, // 显示刷新按钮
		singleSelect : true,// 复选框只能选择一条记录
		search : false,// 是否显示右上角的搜索框
		queryParams : function queryParams(params) {
			var param = {
				exam_id : exam_id,
				setname : $("#itemname").val(),
				sex : $("#custsex").val()
			};
			return param;
		}, // 参数
		clickToSelect : true,// 点击行即可选中单选/复选框
//		sidePagination : "client",// 表格分页的位置
		 sidePagination: "server",//表格分页的位置
//		queryParamsType : "limit", // 参数格式,发送标准的RESTFul类型的参数请求
		toolbar : "#toolbar", // 设置工具栏的Id或者class
		columns : [ {
			align : 'left',
			field : 'item_code',
			title : '项目编码',
			width : 10
		}, {
			align : 'left',
			field : 'item_name',
			title : '项目名称',
			width : 40
		}, {
			align : 'left',
			field : 'dep_name',
			title : '所属科室',
			width : 22
		}, {
			align : 'left',
			field : 'item_category',
			title : '项目类型',
			width : 22,
			hidden : true
		}, {
			align : 'center',
			field : 'sex',
			title : '性别',
			width : 10
		}, {
			align : 'center',
			field : 'item_amount',
			title : '金额',
			width : 10
		} ],
		onDblClickRow : function(row) {
			row.set_num = '0';
			row.itemnum=1;
			/*if (!isFloat($("#discount").val())) {
				alert('折扣率格式错误！');
				document.getElementById("discount").focus();
			} else */if (Number($("#discount").val()) > 10) {
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
//			addRow(row);
		}

	});
}

// 新增一行表格
function addRow(row) {
	var count = $('#selectchangitemlist').bootstrapTable('getData').length;
	// newFlag == 1的数据为新规的数据
	$('#selectchangitemlist').bootstrapTable('insertRow', {
		index : 0,
		row : {
			item_code : row.item_code,
			item_name : row.item_name,
			dep_name : row.dep_name,
			// sex:row.sex,
			item_amount : row.item_amount,
			itemnum : "1",
			discount : row.discount,
			amount : row.amount
		}
	});
}
/**
 * 增加分组项目a1
 */
function insertitem(row) {
	var rowsLength = $('#selectchangitemlist').bootstrapTable('getData');
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
				var count = $('#selectchangitemlist').bootstrapTable('getData').length;
				// newFlag == 1的数据为新规的数据
				$('#selectchangitemlist').bootstrapTable('insertRow', {
					index : 0,
//				$('#selectchangitemlist').datagrid('insertRow',{
//				    index: 0,  // 索引从0开始
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
				bootbox.confirm('['+row.item_code+'-'+row.item_name+']冲突，是否添加？', function(r){
//				 $.messager.confirm('提示信息','['+row.item_code+'-'+row.item_name+']冲突，是否添加？',function(r){
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
//			alert_autoClose("操作提示", "性别冲突，不能添加！", "error");
			bootbox.alert("性别冲突，不能添加！");
			$('#itemname').focus(); 
			$("#itemname").select();
		}

	}else{
//		alert_autoClose("操作提示", "项目冲突，不能添加！", "error");
		bootbox.alert("性别冲突，不能添加！");
		$('#itemname').focus(); 
		$("#itemname").select();
	}
}
//弹出框自动关闭
function alert_autoClose(title,msg,icon){  
	 var interval;  
	 var time=500;  
	 var x=1;    //设置时间2s
		bootbox.alert(title,msg,icon,function(){});
//	$.messager.alert(title,msg,icon,function(){});  
	 interval=setInterval(fun,time);  
	        function fun(){  
	      --x;  
	      if(x==0){  
	          clearInterval(interval);  
	  $(".messager-body").window('close');    
	       }  
	}; 
	}
// * 显示分组套餐信息
function getgroupuserGrid2() {
	$('#selectctlist').bootstrapTable({
		method : 'post',
		url : 'exam_tclistshow.action',
		dataType : "json",
		editable : true,// 开启编辑模式
		striped : true, // 使表格带有条纹
		pagination : true, // 在表格底部显示分页工具栏
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 50 ],
		idField : "exam_id", // 标识哪个字段为id主键
		showToggle : false, // 名片格式
		cardView : false,// 设置为True时显示名片（card）布局
		showColumns : true, // 显示隐藏列
		showRefresh : true, // 显示刷新按钮
		singleSelect : true,// 复选框只能选择一条记录
		search : false,// 是否显示右上角的搜索框
		queryParams : function queryParams(params) {
			var param = {
				exam_id : exam_id
			};
			return param;
		}, // 参数
		clickToSelect : true,// 点击行即可选中单选/复选框
		sidePagination : "client",// 表格分页的位置
//		sidePagination : "server",// 表格分页的位置
		// queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		toolbar : "#toolbar", // 设置工具栏的Id或者class
		columns : [{
		    title: "操作",
		    align: 'center',
		    valign: 'middle',
		    width: 15, // 定义列的宽度，单位为像素px
		    formatter: function (value, row, index) {   //传入数据
		        return '<button class="btn btn-primary btn-sm" onclick="f_dellset(\'' + row.set_num + '\')">删除</button>';
		    }
		} ,{
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
		} ],

	});
}
//删除套餐
function f_dellset(set_num) {
//	var index = $('#selectctlist').bootstrapTable('getData').length;
    $('#selectctlist').bootstrapTable('remove', {
        field: "set_num",   //此处的 “id”对应的是字段名
        values: [parseInt(set_num)]
    });
}
// 初始化删除项目信息
function getgroupuserGrid3() {
	$('#deleteItemList').bootstrapTable({
		method : 'post',
		url : 'exam_tclistshow.action',
		dataType : "json",
		striped : true, // 使表格带有条纹
		pagination : true, // 在表格底部显示分页工具栏
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 50 ],
		idField : "exam_id", // 标识哪个字段为id主键
		showToggle : false, // 名片格式
		cardView : false,// 设置为True时显示名片（card）布局
		showColumns : true, // 显示隐藏列
		showRefresh : true, // 显示刷新按钮
		singleSelect : true,// 复选框只能选择一条记录
		search : false,// 是否显示右上角的搜索框
		queryParams : function queryParams(params) {
			var param = {
				exam_id : exam_id
			};
			return param;
		}, // 参数
		clickToSelect : true,// 点击行即可选中单选/复选框
		sidePagination : "client",// 表格分页的位置
//		sidePagination : "server",// 表格分页的位置
		// queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		toolbar : "#toolbar", // 设置工具栏的Id或者class
		columns : [ {
			align : 'left',
			field : 'item_code',
			title : '项目编码',
			width : 10
		}, {
			align : 'left',
			field : 'item_name',
			title : '项目名称',
			width : 30
		}, {
			align : 'left',
			field : 'dep_name',
			title : '所属科室',
			width : 22
		},
		// {align : 'center',field : 'item_category',title : '项目类型',width :
		// 10,hidden:true},
		{
			align : 'left',
			field : 'sex',
			title : '性别',
			width : 10
		}, {
			align : 'center',
			field : 'item_amount',
			title : '原金额',
			width : 10
		},
		// {align : 'center',field : 'id',title : 'ID',width : 10,hidden:true},
		// {align : 'center',field : 'item_type',title : '类型',width :
		// 10,hidden:true},
		// {align : 'center',field : 'itemnum',title : '数量',width :
		// 10,hidden:true},
		{
			align : 'center',
			field : 'discount',
			title : '折扣率',
			width : 10
		}, {
			align : 'center',
			field : 'amount',
			title : '折扣金额',
			width : 15
		} ],

	});
}
function getgroupuserGrid4() {
	$('#selectchangitemlist').bootstrapTable({
		method : 'get',
		url : 'custchangitemlist.action',
		dataType : "json",
		striped : true, // 使表格带有条纹
		pagination : true, // 在表格底部显示分页工具栏
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 50 ],
		idField : "exam_id", // 标识哪个字段为id主键
		showToggle : false, // 名片格式
		cardView : false,// 设置为True时显示名片（card）布局
		showColumns : true, // 显示隐藏列
		showRefresh : true, // 显示刷新按钮
		singleSelect : true,// 复选框只能选择一条记录
		search : false,// 是否显示右上角的搜索框
		queryParams : function queryParams(params) {
			var param = {
				id : exam_id,
			};
			return param;
		}, // 参数
		clickToSelect : true,// 点击行即可选中单选/复选框
		sidePagination : "client",// 表格分页的位置
//		sidePagination : "server",// 表格分页的位置
		// queryParamsType: "limit", //参数格式,发送标准的RESTFul类型的参数请求
		toolbar : "#toolbar", // 设置工具栏的Id或者class
		columns : [{
		    title: "操作",
		    align: 'center',
		    valign: 'middle',
		    width: 15, // 定义列的宽度，单位为像素px
		    formatter: function (value, row, index) {   //传入数据
		        return '<button class="btn btn-primary btn-sm" onclick="del(\'' + row.id + '\', \'' + row.item_code + '\',\'' + row.item_name + '\',\'' + row.dep_name + '\',\'' + row.sex + '\',\'' + row.item_amount + '\',\'' + row.discount + '\',\'' + row.amount + '\')">删除</button>';
//		        item_code, item_name, dep_name, sex, item_amount, discount, amount
		    }
		} ,{
			align : 'left',
			field : 'item_code',
			title : '项目编码',
			width : 15
		},
		// {align : 'center',field : 'item_type',title : '项目类型',width : 20},
		// {align : 'center',field : 'item_category',title : '项目类型',width : 20},
		{
			align : 'left',
			field : 'item_name',
			title : '项目名称',
			width : 40
		}, {
			align : 'left',
			field : 'dep_name',
			title : '科室',
			width : 25
		},
		// {align : 'left',field : 'id',title : 'ID',width : 15,hidden:true},
		// {align : 'left',field : 'sex',title : '性别',width : 15,hidden:true},
		{
			align : 'center',
			field : 'item_amount',
			title : '原金额',
			width : 10
		}, {
			align : 'center',
			field : 'itemnum',
			title : '数量',
			width : 10
		}, {
			align : 'center',
			field : 'discount',
			title : '折扣率',
			width : 15
		}, {
			align : 'center',
			field : 'amount',
			title : '金额',
			width : 20
		} ],
		
	});
}
function del(id,item_code, item_name, dep_name, sex, item_amount, discount, amount) {
    var index = $('#selectchangitemlist').bootstrapTable('getData').length;
    $('#selectchangitemlist').bootstrapTable('remove', {
        field: "id",   //此处的 “id”对应的是字段名
        values: [parseInt(id)]
    });
//    djtdeletetc();
    djtdeletetc(item_code, item_name, dep_name, sex, item_amount, discount, amount);
}


function f_dellchargitem(item_code) {	
	if (deltiemflags==0){
		return '';
	}else{
	   return '<a href=\"javascript:deletechargitemOne(\''
			+ item_code
			+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
	}
}

//删除事件
window.delEvents ={
    "click #del_btn":function(e,value,row,index)
    {
        console.log(row);
	     bootbox.confirm({
			size: "small",
			message: "您确定删除"+row.no+"行吗？",
			buttons: {
				confirm: {
					label: '是',
					className: 'btn-success'
				},
				cancel: {
					label: '否',
					className: 'btn-danger'
				}
			},
			callback: function(result) {
				if(result) {
					 $("#tab").bootstrapTable('refresh');
					$.ajax({
			          method:"post",
			          url:'/list?datatype=jsonp',
			          data:{
			          	no:row.no,
			          },
			          dataType : "jsonp",
			          async:true,
			          success:function (res) {			          	 
			          	
			          }
			        }); 
				}
			}
		});	
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
				$('#deleteItemList').bootstrapTable('insertRow', {
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
//	countamt();

}
function operateFormatter(value, row, index) {
	return [ '<button type="button" class="btn btn-primary" id="del_btn">删除</button>'

	].join('')
}
//操作栏的格式化
 function actionFormatter(value, row, index) {
     var id = value;
     var result = "";
     result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"DeleteByIds('" +
         (index+1) + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
     return result;
 }
 
 function DeleteByIds(index) {
	
     $('#selectchangitemlist').bootstrapTable('remove', {
         filed: 'Num',
         value: [parseInt(index)]
     }); 
//     $("#selectchangitemlist").bootstrapTable('refresh');
 }
//function operateFormatter(value, row, index) {
//	if (deltiemflags==0){
//		return '';
//	}else{
//	return [ '<button type="button" class="RoleOfedita btn btn-primary" style="margin-right:15px;" onclick = "djtdeletetc(\''
//			+ row.item_code
//			+ '\',\''
//			+ row.item_name
//			+ '\',\''
//			+ row.dep_name
//			+ '\',\''
//			+ row.sex
//			+ '\',\''
//			+ row.item_amount
//			+ '\',\''
//			+ row.discount + '\',\'' + row.amount + '\');">删除</button>'
//
//	].join('')
//	}
//}
function djtdeletetc(item_code, item_name, dep_name, sex, item_amount,
		discount, amount) {
//	removeRow();
	var count = $('#deleteItemList').bootstrapTable('getData').length;
	// newFlag == 1的数据为新规的数据
	$('#deleteItemList').bootstrapTable('insertRow', {
		index : count,
		row : {
			item_code : item_code,
			item_name : item_name,
			dep_name : dep_name,
			sex : sex,
			item_amount : item_amount,
			discount : discount,
			amount : amount
		}
	});
}
function removeRow() {
	var count = $('#selectchangitemlist').bootstrapTable('getData').length;
	// var length = $('#bootstrap-table').bootstrapTable('getData').length;
	// if(length>1){//保留一行数据
	$('#selectchangitemlist').bootstrapTable('remove', {
		field : count,
		values : [ parseInt(count) - 1 ]
	})
	// }

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
 * 移除套餐行
 */
function deletetc(set_numsss) {
	
				bootbox.confirm("确定删除此套餐吗？", function(r){
						if (r) {								
							var rowsLength = $('#selectctlist').bootstrapTable('getData');
							if (!rowsLength.length == 0) {
								var flag = true;//不相等
								for (var j = 0; j <= rowsLength.length - 1; j++)//已选择
								{
									if (set_numsss == rowsLength[j].set_num) {
										deletechargItem(set_numsss);
										var index = $element.parent().data('index');
//										var index = $('#selectctlist')
//												.datagrid('getRowIndex',
//														rowsLength[j]);//获取指定索引
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
	var rows = $('#selectchangitemlist').bootstrapTable('getData');
	if (!rows.length == 0) {
		for (var i = rows.length - 1; i >= 0; i--) {
			if (set_numsss == rows[i].set_num) {
//				var index1 = $('#selectchangitemlist').datagrid(
//						'getRowIndex', rows[i]);//获取指定索引
				var index1 =$('#selectchangitemlist').bootstrapTable("check",i);
				$table.bootstrapTable('removeByIndex',1);//删除序号为0的数据
//				$('#selectchangitemlist').datagrid('deleteRow', index1);//删除指定索引的行
			}
		}//j End    
		countamt();
	}
}

/**
 * 增加套餐
 */
var copy_item = "";
function inserttc(userid) {
	var rowsLength = $('#selectctlist').bootstrapTable('getData');
//	var rowsLength = $('#selectctlist').datagrid('getRows');
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
			var count = $('#selectctlist').bootstrapTable('getData').length;
			$('#selectctlist').bootstrapTable('insertRow', {
				index : count,
				row : {
					set_num : userid.set_num,
					  set_name : userid.set_name,
					  sex : userid.sex,
					  set_discount : userid.set_discount,
					  set_amount : userid.set_amount
				}
			});
		  /* $('#selectctlist').datagrid("appendRow", {
			  set_num : userid.set_num,
			  set_name : userid.set_name,
			  sex : userid.sex,
			  set_discount : userid.set_discount,
			  set_amount : userid.set_amount
		   });*/
		   if(copy_item!='N'){
			   insertsettiem(userid.set_num);
		   } 
			 
		}else{
			//alert("套餐性别冲突111，不能添加。");
		}
	}
}
/**
 * 点击下拉列表
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
function gradeChange(){
	var options=$("#tclist option:selected");
	setvalue(options.val(), options.text())
}
//选择套餐插入收费项目
function insertsettiem(setnum) {
	$.post("setforchangitemlist.action", {
		"set_num" : setnum
	}, function(jsonPost) {
		var userid = eval(jsonPost);
		for (var i = 0; i < userid.length; i++) {
			/*if (!isFloat($("#discount").val())) {
				alert('折扣率格式错误！');
				document.getElementById("discount").focus();
			} else*/ if (Number($("#discount").val()) > 10) {
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
	var rowsLength = $('#selectchangitemlist').bootstrapTable('getData');
//	var rowsLength = $('#selectchangitemlist').datagrid('getRows');
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
				 var count = $('#selectchangitemlist').bootstrapTable('getData').length;
		 			$('#selectchangitemlist').bootstrapTable('insertRow', {
		 				index : 0,
//				$('#selectchangitemlist').datagrid("insertRow", {
//					index: 0,  // 索引从0开始
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
//			    	 $('#selectchangitemlist').datagrid("insertRow", {
			    		 var count = $('#selectchangitemlist').bootstrapTable('getData').length;
			 			$('#selectchangitemlist').bootstrapTable('insertRow', {
			 				index : count,
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
//计算总金额

function countamt() {
	var rows = $('#selectchangitemlist').bootstrapTable('getData');
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
//		var personal_pay_count=document.getElementsByName("itemnum_input"); //数量
//		var personal_pay_inputss=document.getElementsByName("personal_pay_input");
//		for(var i=0;i<rows.length-countitemrow;i++){
//			oldamt = decimal(oldamt + Number(rows[i].item_amount)*Number(personal_pay_count[i].value),2);
//			newamt = decimal(newamt + Number(personal_pay_inputss[i].value),2); //新增个人付费
//		}
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

/**
 * 保存修改
 */
function djtcustadd() {
	var itemrows = $('#selectchangitemlist').bootstrapTable('getData');
	if (itemrows.length>0) {
		 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		var itemrows = $('#selectchangitemlist').bootstrapTable('getData');	
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
	    var setrows = $('#selectchangitemlist').bootstrapTable('getData');
	    var setentities = "";
	    for(i = 0;i < setrows.length;i++)  
	    {  
	    	setentities = setentities  + JSON.stringify(setrows[i]);    
	    } 	
		$.ajax({
			url : 'djtGcustSaveItemSet.action',
			data : {
						exam_id :exam_id,
						itementities: itementities,
						setentities:setentities,
						discount : $("#discount").val(),
						amount : $("#amount").val(),
						item_amount:$("#item_amount").val()
					},
					type : "post",//数据发送方式   
					success : function(text) {
						if(text=="ok"){
							bootbox.alert("本操作员最大权限打"+$('#web_Resource').val()+"折,项目折扣超出权限！");
						}else{
							$(".loading_div").remove();		
							if (text.split("-")[0] == 'ok') {														
								bootbox.alert(text.split("-")[1]);
//								var _parentWin =  window.opener ; 
//								_parentWin.setselectListGrid();
//								_parentWin.reapplydjtlispacs();
//								window.close();
								window.location.reload();
							} else {
								bootbox.alert(text.split("-")[1]);
							}
						}
					},
					error : function() {
						$(".loading_div").remove();
						bootbox.alert( "操作错误");
					}
				});
	}else{
		bootbox.alert( "无效收费项目");
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
//num表示要四舍五入的数,v表示要保留的小数位数。  
function decimal(num,v)  
{  
    var vv = Math.pow(10,v);  
    return Math.round(num*vv)/vv;  
}   