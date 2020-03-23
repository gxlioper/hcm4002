var decchargtype="E";
$(document).ready(function () {
	decchargtype=$("input[name='decchargtype']:checked").val();
	if(decchargtype=="E"){//未检查人员
		gettermatotleElistGrid();
		nottermaCExamInfoList(0);
		nottermaEChargItemList("");
	}else{//未检查项目
		gettermatotleClistGrid();
		nottermaCExamInfoList(0);
		nottermaEChargItemList("");
	}
});

	 /**
	  * 未检项目列表
	  */
function gettermatotleClistGrid() {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
			+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	var model = {
			"company_id" : $("#company_id").val(),
			"batchid" : $("#batch_id").val()
	};
	$("#teamAccountExamCEShow").datagrid({
		url : 'nottermaCList.action',
		dataType : 'json',
		queryParams : model,
		rownumbers : true,
		pageSize : 100000,// 每页显示的记录条数，默认为10
		columns : [ [ 
		              {align : 'center',field : 'item_code',title : '项目编号',	width : 20,sortable:true},
		              {align : 'left',field : 'item_name',title : '项目名称',width : 25,sortable:true}    
		              ] ],
		onLoadSuccess : function(value) {
			//$("#datatotal").val(value.total);
			$(".loading_div").remove();
			nottermaCExamInfoList(0);
			nottermaEChargItemList("");
		},onDblClickRow : function(index, row) {
			nottermaCExamInfoList(row.id);
			nottermaCExamInfoList
		},
		remoteSort:false,
		singleSelect : false,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		striped : true
	});
}

/**
 * 未检人员列表
 */
function gettermatotleElistGrid() {
var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);
var model = {
	"company_id" : $("#company_id").val(),
	"batchid" : $("#batch_id").val()
};
$("#teamAccountExamCEShow").datagrid({
	url : 'nottermaEList.action',
	dataType : 'json',
	queryParams : model,
	rownumbers : true,
	pageSize : 100000,// 每页显示的记录条数，默认为10
	columns : [ [ 
	              {align : 'center',field : 'exam_num',title : tjhname,	width : 15,sortable:true},
	              {align : 'center',field : 'user_name',title : '姓名',	width : 20,sortable:true},
	              {align : 'left',field : 'id_num',title : '身份证号',width : 40,sortable:true},
	              {align : 'center',field : 'sex',title : '性别',	width : 10,sortable:true},
	              {align : 'left',field : 'age',title : '年龄',width : 15,sortable:true},
	              {align : 'left',field : 'com_name',title : '体检单位',width : 40}	             
	              ] ],
	onLoadSuccess : function(value) {
		//$("#datatotal").val(value.total);
		nottermaCExamInfoList(0);
		nottermaEChargItemList("");
		$(".loading_div").remove();
	},onDblClickRow : function(index, row) {
		nottermaEChargItemList(row.exam_num);
	},
	remoteSort:false,
	singleSelect : false,
	collapsible : true,
	pagination : false,
	fitColumns : true,
	striped : true
});
}
 

/**
 * 未检人员对应项目列表
 */
var exam_num;
function nottermaEChargItemList(exam_num) {
var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);
var model = {
	"company_id" : $("#company_id").val(),
	"batchid" : $("#batch_id").val(),
	"exam_num": exam_num
};
$("#teamAccountItemList").datagrid({
	url : 'nottermaEChargItemList.action',
	dataType : 'json',
	queryParams : model,
	rownumbers : true,
	pageSize : 100000,// 每页显示的记录条数，默认为10
	columns : [ [ 
	              {align : 'center',field : 'item_code',title : '项目编号',	width : 20,sortable:true},
	              {align : 'left',field : 'item_name',title : '项目名称',width : 25,sortable:true}     
	              ] ],
	onLoadSuccess : function(value) {
		//$("#datatotal").val(value.total);
		$(".loading_div").remove();
	},
	remoteSort:false,
	singleSelect : false,
	collapsible : true,
	pagination : false,
	fitColumns : true,
	striped : true
});
}


/**
 * 未检项目对应人员列表
 */
var id;
function nottermaCExamInfoList(id) {
var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
		+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
$("body").prepend(str);
var model = {
	"company_id" : $("#company_id").val(),
	"batchid" : $("#batch_id").val(),
	"chargitemid": id
};
$("#teamAccountItemList").datagrid({
	url : 'nottermaCExamInfoList.action',
	dataType : 'json',
	queryParams : model,
	rownumbers : true,
	pageSize : 100000,// 每页显示的记录条数，默认为10
	columns : [ [ 
	              {align : 'center',field : 'exam_num',title : tjhname,	width : 15,sortable:true},
	              {align : 'center',field : 'user_name',title : '姓名',	width : 20,sortable:true},
	              {align : 'left',field : 'id_num',title : '身份证号',width : 40,sortable:true},
	              {align : 'center',field : 'sex',title : '性别',	width : 10,sortable:true},
	              {align : 'left',field : 'age',title : '年龄',width : 15,sortable:true},
	              {align : 'left',field : 'com_name',title : '体检单位',width : 40}	             
	              ] ],
	onLoadSuccess : function(value) {
		//$("#datatotal").val(value.total);
		$(".loading_div").remove();
	},onDblClickRow : function(index, row) {
	},
	remoteSort:false,
	singleSelect : false,
	collapsible : true,
	pagination : false,
	fitColumns : true,
	striped : true
});
}
function f_batchshow(){
    $("#dlg-show").dialog({
        title:'单独查询体检任务',
        href:'batchoneshow.action?id='+$("#batch_id").val()+'&company_id='+$("#company_id").val()
    });
    $("#dlg-show").dialog('open');
}