$(document).ready(function () {
	getjdtexamflow();
	
	$('#exam_num').textbox('textbox').focus();
	$('#exam_num').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
           insertexam();
        }
    });
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
});



function getjdtexamflow(){		
		     $("#selectexamflowlist").datagrid({
			 url:'#',
			 dataType: 'json',
		     pageSize: 50,//每页显示的记录条数，默认为10 
		     pageList:[20,30,45,50,80,100],//可以设置每页记录条数的列表 
			 columns:[[
	            {align:'center',field :'fxfzddd',title:'删除',width:7,'formatter':f_dellchargitem}, 
			    {align:'center',field:'exam_num',title:'体检号',width:20},
			 	{align:'center',field:'user_name',title:'姓名',width:20},
			 	{align:'center',field:'sex',title:'性别',width:10},
			 	{align:'center',field:'phone',title:'电话',width:20},		 	
			 	{align:'center',field:'join_date',title:'体检日期',width:15},
			 	{align:'center',field:'statusall',title:'状态',width:25,"formatter":f_showstatus},
			 	{align:'center',field:'remarke',title:'最后签收时间',width:25},
			 	{align:'center',field:'chi_name',title:'签收人',width:20}
			 	]],	    	
		    	onLoadSuccess:function(value){ 
		    		$("#datatotal").val(value.total);
		    	},
				height: window.screen.height-380,
				nowrap:false,
				rownumbers:true,
		    	singleSelect:false,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    striped:true       
		});
}

/**
 * 删除收费项目
 * 
 * @param val
 * @param row
 * @returns {String}
 */
function f_dellchargitem(val, row) {
		return '<a href=\"javascript:deletechargitemOne(\''
				+ row.exam_id
				+ '\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-del\" alt=\"删除\" /></a>';
}

/**
 * 套餐删除缴费项目表信息
 */
function deletechargitemOne(set_numsss) {
	var rows = $('#selectexamflowlist').datagrid('getRows');
	if (!rows.length == 0) {
		for (var i = rows.length - 1; i >= 0; i--) {
			if (set_numsss == rows[i].exam_id) {
				var index1 = $('#selectexamflowlist').datagrid(
						'getRowIndex', rows[i]);//获取指定索引
				$('#selectexamflowlist').datagrid('deleteRow', index1);//删除指定索引的行
			}
		}//j End    
	}
}


function deletechargitemall() {
	var rows = $('#selectexamflowlist').datagrid('getRows');
	if (!rows.length == 0) {
		for (var i = rows.length - 1; i >= 0; i--) {			
			$('#selectexamflowlist').datagrid('deleteRow', i);//删除指定索引的行
		}//j End    
	}
	$('#exam_num').textbox('setValue','');
	$('#exam_num').textbox('textbox').focus();
}

function f_showstatus(val,row){
	  if(row.status=='Y'){
		  return '<font color="red">'+row.statuss+'</font>';
	  }else if(row.status=='D'){
		  return '<font color="blue">'+row.statuss+'</font>';
	  }else if(row.status=='J'){
		  return '<font color="green">'+row.statuss+'</font>';
	  }else{
		  return row.statuss;
	  }
}

function flowcustadd() {
	var checkedItems = $('#selectexamflowlist').datagrid('getRows');
	var ids = new Array();
	var idsflag = 0;
	$.each(checkedItems, function(index, item) {
		ids.push(item.exam_num);
		idsflag = 1;
	});
	if (idsflag == 0) {
		$.messager.alert("操作提示", "无效体检信息", "error");
	} else {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
				+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url : 'examflowoutsave.action',
			data : {
				remark : $("#remark").val(),
				ids : ids.toString()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
					deletechargitemall();
				} else {
					$.messager.alert("操作提示", text.split("-")[1], "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");
			}
		});
	}
}

function insertexam() {
	if (($("#exam_num").val() != '') && ($("#exam_num").val().length > 0)) {
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
		var exam_num = $("#exam_num").val();
		var rowsLength = $('#selectexamflowlist').datagrid('getRows');
		var flag = true;// 不相等
		if (!rowsLength.length == 0) {
			for (var j = 0; j <= rowsLength.length - 1; j++)// 已选择
			{
				if (exam_num == rowsLength[j].exam_num) {
					flag = false;// 相等
					break;
				}
			}// j End
		}
		if (flag == true) {
			
			$.ajax({
				url : 'getexaminfoForNum.action',
				data : {
					exam_num : $("#exam_num").val(),
					types:'1'
				},
				type : "post",// 数据发送方式
				success : function(data) {
					$(".loading_div").remove();
					if (data.length > 10) {
						var customer = JSON.parse(data);
						$('#selectexamflowlist').datagrid("appendRow", {
							exam_num : customer.exam_num,
							user_name : customer.user_name,
							sex : customer.sex,
							is_marriage : customer.is_marriage,
							age : customer.age,
							phone : customer.phone,
							join_date : customer.join_date,
							status : customer.status,
							exam_id : customer.id,
							statuss : customer.statuss,
						    remarke:customer.remarke,
						    chi_name:customer.chi_name
						});
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");
				}
			});
		}else{
			$(".loading_div").remove();
		}
	}
	$("#exam_num").textbox('textbox').select();
	$('#exam_num').textbox('textbox').focus();
}