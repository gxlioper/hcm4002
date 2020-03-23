var decchargtype="C";
$(document).ready(function () {
	getteamtotalinfo();
	chaxun();
	
	$('#selectgroup_id').combobox({
		url : 'getBatchForGroupTwo.action?batch_id='+$("#batch_id").val(),
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'group_name',
		onLoadSuccess : function(data) {
			$('#selectgroup_id').combobox('setValue', data[0].id);				
		}
	});
	
	$('#set_name').combobox({
		url : 'getDirectorExamSetList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'set_name',
		onLoadSuccess : function(data) {
			
		}
	});
	
	$('#tjlx').combobox({
		url : 'getDatadis.action?com_Type=TJLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#tjlx').combobox('setValue', data[0].id);
			}
		}
	});
	
	$('#conn_rylb').combobox({
		url : 'getDatadis.action?com_Type=RYLB',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			
		}
	});
	
});

function chaxun() {
	decchargtype=$("input[name='decchargtype']:checked").val();
	if(decchargtype=="C"){//按照收费项目减免
		document.getElementById('jmname').innerHTML='减免金额';
		gettermatotleClistGrid();
	}else if(decchargtype=="E"){//按照收费项目减免
		document.getElementById('jmname').innerHTML='减免金额';
		gettermatotleElistGrid();
	}else{//按照人员减免
		document.getElementById('jmname').innerHTML='每人减免金额';
		gettermatotlePlistGrid();
	}
}

	 /**
	  * 
	  */
function gettermatotleClistGrid() {
	document.getElementById('jmname').innerHTML='减免金额';
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var model = {
		"acc_num" : $("#acc_num").val()
	};
	$("#teamAccountExamCEShow").datagrid({
		url : 'gettermatotleClistGrid.action',
		dataType : 'json',
		queryParams : model,
		toolbar : '#toolbar',
		rownumbers : true,
		pageSize : 100000,// 每页显示的记录条数，默认为10
		pageList : [ 10, 20, 45, 60, 75 ],// 可以设置每页记录条数的列表
		columns : [ [ {field:'checkss',checkbox:true },
		              {align : 'center',field : 'item_code',title : '项目编号',	width : 20,sortable:true}, 
		              {align : 'left',field : 'item_name',title : '项目名称',width : 25,sortable:true}, 
		              {align : 'center',field : 'chargingcount',title : '体检项目数量',width : 40,sortable:true}, 
		              {align : 'center',field : 'amount',title : '项目总金额',width : 25,sortable:true}		             
		              ] ],
		onLoadSuccess : function(value) {
			//$("#datatotal").val(value.total);
			$(".loading_div").remove();
		},
		singleSelect : false,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true,
		toolbar : toolbar
	});
}

function gettermatotlePlistGrid(){
	document.getElementById('jmname').innerHTML='每人减免金额';
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);

var group_id = undefined;
if($("#ck_selectgroup_id")[0].checked){
	group_id = $('#selectgroup_id').combobox('getValue');
}
var set_name = undefined;
if($("#ck_set_name")[0].checked){
	set_name = $('#set_name').combobox('getValue');
}
var conn_rylb = undefined;
if($("#ck_conn_rylb")[0].checked){
	conn_rylb = $('#conn_rylb').combobox('getValue');
}
var tjlx = undefined;
if($("#ck_tjlx")[0].checked){
	tjlx = $('#tjlx').combobox('getValue');
}

var model = {
	"acc_num":$("#acc_num").val(),
	"set_id":set_name,
	"group_id":group_id,
	"ren_type":conn_rylb,
	 "customer_type":tjlx,
};
$("#teamAccountExamCEShow").datagrid({
	url : 'gettermatotleElistGrid.action',
	dataType : 'json',
	queryParams : model,
	toolbar : '#toolbar',
	rownumbers : true,
	pageSize : 200,// 每页显示的记录条数，默认为10
	pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
	columns : [ [ {field:'checkss',checkbox:true },
	              {align : 'center',field : 'exam_num',title : '体检编号',	width : 15,sortable:true}, 
	              {align : 'center',field : 'user_name',title : '姓名',	width : 20,sortable:true}, 
	              {align : 'left',field : 'id_num',title : '身份证号',width : 40,sortable:true}, 
	              {align : 'center',field : 'sex',title : '性别',	width : 10,sortable:true}, 
	              {align : 'left',field : 'age',title : '年龄',width : 10,sortable:true},
	              {align : 'center',field : 'chargingcount',title : '体检项目数量',width : 15,sortable:true}, 
	              {align : 'center',field : 'amount',title : '项目总金额',width : 15,sortable:true},	             
	              {align : 'center',field : 'group_name',title : '分组',width : 25,sortable:true},
	              {align : 'center',field : 'set_name',title : '套餐',width : 25,sortable:true},
	              {align : 'center',field : 'customer_type_id',title : '人员类别',width : 15,sortable:true},
	              {align : 'center',field : 'customer_type',title : '体检类别',width : 15,sortable:true},
	              ] ],
	onLoadSuccess : function(value) {
		$("#datatotal").val(value.total);
		$(".loading_div").remove();
	},
	singleSelect:false,
    collapsible:true,
	pagination: true,
    fitColumns:true,
    striped:true,    
	toolbar : toolbarp
});
}
 
 /**
  * 定义工具栏
  */
 var toolbarp=[{
		text:'按照人员平均减免',
		width:160,
		iconCls:'icon-check',
	    handler:function(){
	    	termdecamtdo();
	    }
	}];
/**
 * 
 */
function gettermatotleElistGrid() {
	document.getElementById('jmname').innerHTML='减免金额';
var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);
var group_id = undefined;
if($("#ck_selectgroup_id")[0].checked){
	group_id = $('#selectgroup_id').combobox('getValue');
}
var set_name = undefined;
if($("#ck_set_name")[0].checked){
	set_name = $('#set_name').combobox('getValue');
}
var conn_rylb = undefined;
if($("#ck_conn_rylb")[0].checked){
	conn_rylb = $('#conn_rylb').combobox('getValue');
}
var tjlx = undefined;
if($("#ck_tjlx")[0].checked){
	tjlx = $('#tjlx').combobox('getValue');
}

var model = {
	"acc_num":$("#acc_num").val(),
	"set_id":set_name,
	"group_id":group_id,
	"ren_type":conn_rylb,
	 "customer_type":tjlx,
};
$("#teamAccountExamCEShow").datagrid({
	url : 'gettermatotleElistGrid.action',
	dataType : 'json',
	queryParams : model,
	toolbar : '#toolbar',
	rownumbers : true,
	pageSize : 200,// 每页显示的记录条数，默认为10
	pageList : [ 200,300,500 ],// 可以设置每页记录条数的列表
	columns : [ [ {field:'checkss',checkbox:true },
	              {align : 'center',field : 'exam_num',title : '体检编号',	width : 15,sortable:true}, 
	              {align : 'center',field : 'user_name',title : '姓名',	width : 20,sortable:true}, 
	              {align : 'left',field : 'id_num',title : '身份证号',width : 40,sortable:true}, 
	              {align : 'center',field : 'sex',title : '性别',	width : 10,sortable:true}, 
	              {align : 'left',field : 'age',title : '年龄',width : 10,sortable:true},
	              {align : 'center',field : 'chargingcount',title : '体检项目数量',width : 15,sortable:true}, 
	              {align : 'center',field : 'amount',title : '项目总金额',width : 15,sortable:true},
	              {align : 'center',field : 'group_name',title : '分组',width : 25,sortable:true},
	              {align : 'center',field : 'set_name',title : '套餐',width : 25,sortable:true},
	              {align : 'center',field : 'customer_type_id',title : '人员类别',width : 15,sortable:true},
	              {align : 'center',field : 'customer_type',title : '体检类别',width : 15,sortable:true},
	              ] ],
	onLoadSuccess : function(value) {
		$("#datatotal").val(value.total);
		$(".loading_div").remove();
	},
	singleSelect:false,
    collapsible:true,
	pagination: true,
    fitColumns:true,
    striped:true,    
	toolbar : toolbare
});
}
 
 /**
  * 定义工具栏
  */
 var toolbar=[{
		text:'按照体检项目减免',
		width:160,
		iconCls:'icon-check',
	    handler:function(){
	    	termdecamtdo();
	    }
	}];
 
 var toolbare=[{
		text:'按照体检人员减免',
		width:160,
		iconCls:'icon-check',
	    handler:function(){
	    	termdecamtdo();
	    }
	}];
 
 function termdecamtdo(){
	 decchargtype=$("input[name='decchargtype']:checked").val();
    if (($("#acc_num").val()=='')||($("#acc_num").val().length<=0)) {
		 $.messager.alert("操作提示", "结算单号无效","error");		
	 }else if (($("#decamt").val()=='')||(isNaN(Number($("#decamt").val())))) {
		 $.messager.alert("操作提示", "减免金额无效","error");		
	 }else{
		 $.messager.confirm('提示信息','是否确定执行减免操作？',function(r){
			if(r){
				termdecamtCEdo();
			 }
		 });
	 }    
  }

 /**
  * 批量删除
  */
 function termdecamtCEdo(){
	     var itemrows = $('#teamAccountExamCEShow').datagrid('getChecked');
		if (itemrows.length > 0) {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
					+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			var setentities = "";
			for (i = 0; i < itemrows.length; i++) {
				setentities = setentities + JSON.stringify(itemrows[i]);
			}
			$.ajax({
				url : 'termdecamtCEdo.action',
				data : {
					acc_num : $("#acc_num").val(),
					setentities : setentities,
					deccharges : $("#decamt").val(),
					batchid:$("#batch_id").val(),
					exam_status:decchargtype
				},
				type : "post",// 数据发送方式
				success : function(text) {
					$(".loading_div").remove();
					if (text.split("-")[0] == 'ok') {
						$.messager.alert("操作提示", text.split("-")[1]);
						var _parentWin =  window.opener ; 
						_parentWin.gettermaccountlistGrid();
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
			$.messager.alert("操作提示", "无效体检人员", "error");
		}
 }
 
 function getteamnumdo(){
	 if($("#acc_num").val()!=''){
	 $.ajax({
			url : 'setTeamAccountcountdo.action',
			data : {
				acc_num : $("#acc_num").val(),
				prices:$("#prices").val(),
				charges:$("#charges").val(),
				deccharges:$("#deccharges").val(),
				batchid: $("#batch_id").val()
			},
			type : "post",// 数据发送方式
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示", text.split("-")[1]);
					var _parentWin =  window.opener ; 
					_parentWin.getTeamAccountForBatch();
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
		 $.messager.alert("操作提示", "无效结算单号 ", "error");
	 }
 }
 
 function getteamtotalinfo() {
		$.post(
			"teamItemCount.action",
			{
				"acc_num" : $("#acc_num").val()
			},
			function(jsonPost) {
			    var customertotal = eval(jsonPost);
			    document.getElementById("ysje").firstChild.nodeValue = customertotal.totalAmt;
				document.getElementById("ssje").firstChild.nodeValue = customertotal.termAmt;
				document.getElementById("yhje").firstChild.nodeValue = customertotal.personAmt;
			}, "json");
	}

 