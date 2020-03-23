$(document).ready(function () {
	var exam_num = $("#tjh").html();
	getExamItem(exam_num);
	getoccuhazardfactorsGrid();
	wenjuanbuttoshow();
	if($("#picture_path").val() != ''){
		document.getElementById("exampic").src="getdjtexamPhoto.action?others="+$("#picture_path").val()+"&"+new Date().getTime();
	}
	var inputter = getCookie("inputter");
	$('#inputter').combobox({
		url : 'getSysLogUserList.action?oper_type=1',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			if(inputter == null){
				$('#inputter').combobox('setValue', $("#userid").val());
			}else{
				$('#inputter').combobox('setValue', inputter);
			}
		}
	});
	
	if($("#exam_type_code").val() != 'TJLXRZTJ'){
		weijizhi();
		yichangzhishu();
		getALL();
				
		$(".layout-panel-north .layout-button-up").click();
		$('.layout-expand-north .panel-title').html('全科会诊协作平台(单击查看)');
		$('.layout-expand-north .panel-tool').hide();
	}else{
		$('#div_dep').layout('remove','north');
	}
});
///////////////////////////////显示问卷/////////////////////////////////////
function shouSurver(){
	 var model={
			 exam_num_x:$('#tjh').text()	 
	 }
	  $.ajax({
			url:'getQuestionnaireSurveyPageYMShou.action',
			data:model,//通过体检号
			type:'post',
			success:function(data){
				var obj =  eval('(' + data + ')');
				var yh = obj.surverList;//已选择选项
				if(yh.length<1){
					$.messager.alert("提示信息","此用户未答问卷","info");
					return false;
				}else{
					  $('#shou_wenjuan').dialog({
					  		title: '问卷',    
						    width: 800,    
						    height:700,    
						    center:'center',
						    href: "getSurverPage.action?s_num="+$('#c_id').val()+"&exam_num_x="+$('#tjh').text() 
					  })
					
				}
			}
	  })

}
function wenjuanbuttoshow(){
	 var model={
			 exam_num_x:$('#tjh').text()	 
	 }
	  $.ajax({
			url:'getQuestionnaireSurveyPageYMShou.action',
			data:model,//通过体检号
			type:'post',
			 async:false,  
			success:function(data){
				var obj =  eval('(' + data + ')');
				var yh = obj.surverList;//已选择选项
				if(yh.length>0){
					$('#wenjuan').show();
				}
			}
	  })
}
var obj;
//查询检查项目信息
function getExamItem(exam_num){
	$.ajax({
		url:'getViewExamDetail.action?exam_num='+exam_num+'&app_type='+$("#app_type").val(),
		type:'post',
		success:function(data){
			if(data == 'null' || data == '[]'){
				
			}else{
				obj=eval("("+data+")");
				var str='';
				for(i=0;i<obj.length;i++){
					str+='<div class="B-ultrasound"><p>'+obj[i].item_name+'</p>';
					for(var j=0;j<obj[i].viewItem.length;j++){
						str += ' <div class="in_conclusion"> <span style="display:none">'+obj[i].viewItem[j].item_num+'</span>'+
						' <label class="label_item">'+obj[i].viewItem[j].item_name+':</label><input id="id_'+obj[i].viewItem[j].item_num+'" value="'+obj[i].viewItem[j].exam_result+'">'+
						' </div>';
					}
					str += '<div class="in_conclusion">'+
					' <label class="label_area">描述:</label><textarea id="miao_'+obj[i].sample_id+'" cols="100" rows="5">'+obj[i].exam_desc+'</textarea> '+
					'    </div>'+
			        '    <div class="in_conclusion">'+
					' <label class="label_area">结论:</label><textarea ondblclick="open_view_words('+obj[i].sample_id+')" onclick="show_last_result('+i+')" id="jielun_'+obj[i].sample_id+'" cols="100" rows="5">'+obj[i].exam_result+'</textarea> '+
			        '    </div>'+
			        '    <div class="view">'+
			        '    	<a href="#" title="查看图片" onclick="show_picture(\''+obj[i].pacs_req_code+'\')">查看图片<<</a>'+
			        '        <span style="margin-left: 70px;">检查医师：'+obj[i].exam_doctor+'</span>'+
			        '        <span style="margin-left: 50px;">检查时间：'+obj[i].exam_date+'</span>'+
			        '        <span style="margin-left: 50px;">录入者：'+obj[i].inputters+'</span>'+
			        '<a href="javascript:void(0)" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:60px;float: right;margin-right: 6%;" onclick="javascript:close_page();">'+
			        '<span class="l-btn-left" style="margin-top: 0px;"><span class="l-btn-text">关闭</span></span></a>'+
			        '<a href="javascript:void(0)" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:60px;float: right;" onclick="javascript:saveViewExamDetail('+i+');">'+
			        '<span class="l-btn-left" style="margin-top: 0px;"><span class="l-btn-text">保存</span></span></a>'+
			        '</div><div id="image_'+obj[i].pacs_req_code+'"></div></div>';
					
					
					$('#tt').tabs('add',{
						title: obj[i].item_name,
						selected: false,
						content:'<table id="lishi_'+i+'"></table>'
					});
				}
				$('#tt').tabs('select',0);
				$("#yingxiangkeshi_jl").html(str);
				show_last_result(0);
			}
			
			if($("#isViewExamImageShow").val() == 'Y'){
				viewImageShow();
			}
		}
	});
}
function viewImageShow(){
	var exam_id = 0;
	for(var i=0;i<obj.length;i++){
		exam_id = obj[i].id;
		$.ajax({
			url:'getViewExamImagePath.action?pacs_req_code='+obj[i].pacs_req_code+'&exam_num='+$("#tjh").html(),
			type:'post',
			async:false,
			success:function(data){
				if(data == '[]'){
					return;
				}
				var image_path = eval("("+data+")");
				var str = '';
				for(var j=0;j<image_path.length;j++){
					str += '<a href="#" onclick="show_picture_many(\''+obj[i].pacs_req_code+'\')"><img class="v_i_items" alt="" src="../../picture'+image_path[j].report_picture_path+'?tempid='+Math.random()+'" /></a>';
				}
				$("#image_"+exam_id).height(180);
				$("#image_"+exam_id).css('margin-left','30px');
				$("#image_"+exam_id).css('margin-top','20px');
				$("#image_"+exam_id).html(str);
			}
		});
	}
}

//保存影像科室检查结果
function saveViewExamDetail(i){
	if(obj == undefined || obj == null){
		$.messager.alert("操作提示",'页面加载有问题,请重新进入!', "error");
	}else{
		var viewExamDetail = new Array();
//		for(i=0;i<obj.length;i++){
			var viewItem = new Array();
			for(var j=0;j<obj[i].viewItem.length;j++){
				viewItem.push({
					'id':obj[i].viewItem[j].id,
					'exam_item_id':obj[i].viewItem[j].exam_item_id,
					'item_num':obj[i].viewItem[j].item_num,
					'exam_result':$("#id_"+obj[i].viewItem[j].item_num).val(),
				});
			}
			if($("#jielun_"+obj[i].sample_id).val() != ''){
				viewExamDetail.push({
					'id':obj[i].id,
					'exam_info_id':$("#exam_id").val(),
					'dep_id':$("#dep_id").val(),
					'app_type':$("#app_type").val(),
					'dep_num':$("#dep_num").val(),
					'exam_num':$("#tjh").html(),
					'pacs_id':obj[i].pacs_id,
					'pacs_req_code':obj[i].pacs_req_code,
					'exam_result':$("#jielun_"+obj[i].sample_id).val(),
					'exam_desc':$("#miao_"+obj[i].sample_id).val(),
					'viewItem':viewItem
				});
			}
//		}
		if(viewExamDetail.length == 0){
			$.messager.alert("操作提示",'未录入任何结论,不能保存!', "error");
			return ;
		}
		var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
		$.ajax({
			url:'saveViewExamDetail.action',
			type:'post',
			data:{'viewExams': JSON.stringify(viewExamDetail),inputter : $('#inputter').combobox('getValue')},
			success:function(data){
				$(".loading_div").remove();
				var objq = data.split("-");
				setCookie('inputter',$('#inputter').combobox('getValue'));
				if(objq[0] == 'ok'){
					if(obj.length == 1){
						$.messager.alert("操作提示",objq[1], "info",close_page);
					}else{
						$.messager.alert("操作提示",objq[1], "info",shuaxin_page);
					}
				}else{
					$.messager.alert("操作提示",objq[1], "error");
				}
			}
		});
	}
}
function shuaxin_page(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	window.location.reload();
}

function show_picture_many(id){//当有多张图片时调用此函数，实现点击哪个，点进去默认展示的就是哪个
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#tjh").html();
	newwin = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}

function open_view_words(id){
	$("#dlg-edit").dialog({
		title:'影像科室常用词',
		href:'getViewExamWordsPage.action?sample_id='+id
	});
	$("#dlg-edit").dialog("open");
}

//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	if($("#status").val() == '3'){
		window.parent.close_page();
	}else{
		var _parentWin =  window.opener;
		var userAgent = navigator.userAgent;
		window.opener = null;
		window.open('', '_self');
		window.close();
		_parentWin.shuaxing();
	}
}

function getoccuhazardfactorsGrid() {
		var model = {
			"exam_num" : $("#tjh").html()
		};
		$("#zywhysset").datagrid({
			url : 'examoccuhazardfactorslist.action',
			dataType : 'json',
			queryParams : model,
			toolbar : '#toolbar',
			rownumbers : false,
			pageSize : 5,// 每页显示的记录条数，默认为10
			pageList : [ 5, 10, 20 ],// 可以设置每页记录条数的列表
			columns : [ [ {
				align : 'center',
				field : 'hazard_name',
				title : '职业危害因素',
				width : 30
			}, {
				align : 'center',
				field : 'occuphyexaclass_name',
				title : '体检类别',
				width : 15
			}, {
				align : 'center',
				field : 'hazard_year',
				title : '危害年限',
				width : 15
			}] ],
			onLoadSuccess : function(value) {
				$("#datatotal").val(value.total);
			},
			singleSelect : false,
			collapsible : true,
			pagination : false,
			fitColumns : true,
			striped : true,
			fit:true
		});
	}
//显示职业史
function fn_show_zhiyeshi(){
//	getcusthisGrid();
//	$("#dlg-zys").dialog('open');
//	$("#dlg-zys").dialog('center');
	var url='getzhiyeshiPage.action?exam_num='+$('#tjh').text();
	newwin = window.open("", "职业史", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.moveTo(0,0);
	newwin.resizeTo(screen.width,screen.height-30);
	newwin.location = url;
	newwin.focus();
}
//////////////////////////////职业病历史处理////////////////////////////////////////
function getcusthisGrid(){	
		 var model={
				 "exam_num":$("#tjh").html()
		 };
	     $("#exam_zhiyeshi").datagrid({
		 url:'zybCustomerHislist.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:'#toolbar',
		 rownumbers:false,
	     pageSize: 5,//每页显示的记录条数，默认为10 
	     pageList:[5,10,20],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'company',title:'工作单位',width:18},	
		    {align:'center',field:'workshop',title:'车间部门',width:20},
		 	{align:'center',field:'worktype',title:'工种',width:20},
		 	{align:'center',field:'measure',title:'防护措施',width:30},
		 	{align:'center',field:'harmname',title:'有害因素名称',width:20},
		 	{align:'center',field:'concentrations',title:'有害因素浓度',width:20},
		 	{align:'center',field:'isradiation',title:'是否放射',width:15},
		 	{align:'center',field:'startdate',title:'开始时间',width:15},		 	
		 	{align:'center',field:'enddate',title:'结束时间',width:15}
		 	]],	    	
	    	onLoadSuccess:function(value){
	    		$("#datatotal").val(value.total);
	    	},
	    	singleSelect:false,
		    collapsible:true,
			pagination: false,
		    fitColumns:true,
		    striped:true
	});
}
function weijizhi() {
	var model = {
		"exam_num" : $("#tjh").html()
	};
	$("#weijizhi_div").datagrid({
		url : 'getWeijizhi.action',
		queryParams : model,
		rownumbers : false,
		columns : [ [ {
			align : "center",
			field : "dep_name",
			"title" : "收费项目",
			"width" : 20
		}, {
			align : 'center',
			field : "exam_doctor",
			"title" : "检查医生",
			"width" : 10
		}, {
			align : 'center',
			field : "exam_date",
			"title" : "检查时间",
			"width" : 15
		}, {
			align : '',
			field : "item_name",
			"title" : "检查项目",
			"width" : 15,
			"styler" : f_clolor
		}, {
			align : "",
			field : "exam_result",
			"title" : "检查结果",
			"width" : 45,
			"styler" : f_clolor,
			"formatter" : f_flowor
		} ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			MergeCells('weijizhi_div', 'dep_name,exam_doctor,exam_date');
		},
		singleSelect : true,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		border : false,
		nowrap : false
	});
}

function yichangzhishu() {
	var model = {
		"exam_num" : $("#tjh").html()
	};
	$("#yichang").datagrid({
		url : 'getyichang.action',
		queryParams : model,
		rownumbers : false,
		columns : [ [ {
			align : "center",
			field : "dep_name",
			"title" : "收费项目",
			"width" : 20
		}, {
			align : 'center',
			field : "exam_doctor",
			"title" : "检查医生",
			"width" : 10
		}, {
			align : 'center',
			field : "exam_date",
			"title" : "检查时间",
			"width" : 15
		}, {
			align : '',
			field : "item_name",
			"title" : "检查项目",
			"width" : 15,
			"styler" : f_clolor
		}, {
			align : "",
			field : "exam_result",
			"title" : "检查结果",
			"width" : 45,
			"styler" : f_clolor,
			"formatter" : f_flowor
		} ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			MergeCells('yichang', 'dep_name,exam_doctor,exam_date');
		},
		singleSelect : true,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		border : false,
		nowrap : false
	});
}

function getALL() {//
	var model = {
		"exam_num" : $("#tjh").html()
	};
	$("#all").datagrid({
		url : 'getaLL.action',
		queryParams : model,
		rownumbers : false,
		columns : [ [ {
			align : "center",
			field : "dep_name",
			"title" : "收费项目",
			"width" : 20
		}, {
			align : 'center',
			field : "exam_doctor",
			"title" : "检查医生",
			"width" : 10
		}, {
			align : 'center',
			field : "exam_date",
			"title" : "检查时间",
			"width" : 15
		}, {
			align : '',
			field : "item_name",
			"title" : "检查项目",
			"width" : 15,
			"styler" : f_clolor
		}, {
			align : "",
			field : "exam_result",
			"title" : "检查结果",
			"width" : 45,
			"styler" : f_clolor,
			"formatter" : f_flowor
		} ] ],
		onLoadSuccess : function(value) {
			$("#datatotal").val(value.total);
			MergeCells('all', 'dep_name,exam_doctor,exam_date');
		},
		singleSelect : true,
		collapsible : true,
		pagination : false,
		fitColumns : true,
		fit : true,
		border : false,
		nowrap : false
	});
}

function f_clolor(value, row, index) {
	if (row.dep_category == '17') {
		if (row.health_level == 'Y') {
			return 'color:#F00;';
		} else if (row.health_level == 'W') {
			return 'color:#FF9B00;';
		}
		if (row.item_id == '0') {
			return 'background:#FEEAA8;color:#ff5100;';
		}
	} else if (row.dep_category == '131') {
		if (row.health_level == 1 || row.health_level == 2
				|| row.health_level == 3|| row.health_level == 5|| row.health_level == 6) {
			return 'color:#F00;';
		} else if (row.health_level == 4) {
			return 'color:#FF9B00;';
		}
	}
}

function f_flowor(val, row) {
	if (row.dep_category == '21') {
		if (row.exam_result == 'image_path') {
			return '<a href="javascript:void(0)" onclick = "show_picture('
					+ row.item_id + ')">查看图片</a>';
		} else {
			return row.exam_result.replace(/\n/g, '</br>');
		}
	} else if (row.dep_category == '131') {
		if (row.health_level == 1) {
			return row.exam_result + ' ↑';
		} else if (row.health_level == 2) {
			return row.exam_result + ' ↓';
		}else if(row.health_level == 5){
			return row.exam_result+' ↑↑';
		}else if(row.health_level == 6){
			return row.exam_result+' ↓↓';
		} else {
			return row.exam_result;
		}
	} else {
		return row.exam_result.replace(/\n/g, '</br>');
	}
}
function show_picture(id) {
	var url = '/showViewExamImage.action?pacs_req_code=' + id+'&exam_num='+$("#exam_num").val();
	newwin = window.open(url, "查看图片","toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	newwin.focus();
}
/**
 2         * EasyUI DataGrid根据字段动态合并单元格
 3         * @param fldList 要合并table的id
 4         * @param fldList 要合并的列,用逗号分隔(例如："name,department,office");
 5         */
function MergeCells(tableID, fldList) {
	var Arr = fldList.split(",");
	var dg = $('#' + tableID);
	var fldName;
	var RowCount = dg.datagrid("getRows").length;
	var span;
	var PerValue = "";
	var CurValue = "";
	var length = Arr.length - 1;
	for (i = length; i >= 0; i--) {
		fldName = Arr[i];
		PerValue = "";
		span = 1;
		for (row = 0; row <= RowCount; row++) {
			if (row == RowCount) {
				CurValue = "";
			} else {
				CurValue = dg.datagrid("getRows")[row][fldName];
			}
			if (PerValue == CurValue) {
				span += 1;
			} else {
				var index = row - span;
				dg.datagrid('mergeCells', {
					index : index,
					field : fldName,
					rowspan : span,
					colspan : null
				});
				span = 1;
				PerValue = CurValue;
			}
		}
	}
}
