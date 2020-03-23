$(document).ready(function () {
	var exam_num = $("#exam_num").val();
	getcustomerInfo(exam_num);
	getExamItem(exam_num);
	wenjuanbuttoshow();
	shoubaogaopdfbuttion();
	
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
});
///////////////////////////////显示问卷/////////////////////////////////////
function shouSurver(){
	 var model={
			 exam_num_x:$('#exam_num_x').text()	 
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
						    href: "getSurverPage.action?s_num="+$('#c_id').val()+"&exam_num_x="+$('#exam_num_x').text() 
					  })
					
				}
			}
	  })

}//	$('#wenjuan').css("display:none");
function wenjuanbuttoshow(){
	 var model={
			 exam_num_x:$('#exam_num_x').text()	 
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
					 document.getElementById('wenjuan').style.display = "";
				}
			}
	  })
}
//查询人员基本信息
function getcustomerInfo(exam_num){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+exam_num,
		type:'post',
		 async:false,  
		success:function(data){
			var obj=eval("("+data+")");
			$("#arch_num").html(obj.arch_num);
			$("#exam_num_x").html(exam_num);
			$("#exam_id").val(obj.id);
			$("#user_name").html(obj.user_name);
			$("#sex").html(obj.sex);
			$("#age").html(obj.age);
			$("#set_name").html(obj.set_name);
			$("#company").html(obj.company);
			$("#past_medical_history").html(obj.past_medical_history);
			if(obj.picture_path != ''){
				document.getElementById("exampic").src="getdjtexamPhoto.action?others="+obj.picture_path+"&"+new Date().getTime();
			}
			if(obj.vipsigin=="1")
		       {
		         $("#vipsigin").show();
		       }else{
		    	 $("#vipsigin").hide(); 
		       }
			if(obj.exam_type_code != 'TJLXRZTJ'){
				weijizhi();
				yichangzhishu();
				getALL();
				
				$(".layout-panel-north .layout-button-up").click();
				$('.layout-expand-north .panel-title').html('全科会诊协作平台(单击查看)');
				$('.layout-expand-north .panel-tool').hide();
			}else{
				$('#div_dep').layout('remove','north');
			}
		}
	});
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
			url:'getViewExamImagePath.action?pacs_req_code='+obj[i].pacs_req_code+'&exam_num='+$("#exam_num").val(),
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
					'exam_num':$("#exam_num_x").html(),
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
	var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
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
function shoubaogaopdfbuttion(){
	$.ajax({
		url:'getDepShwoPinCeBaoGaoButtion.action?exam_num='+$('#exam_num_x').text(),
		success:function(data){
			if(data!=""){
			        $("#pdfbuttion").show()
			}
		}
	})
}
//+++++++++++++++++++++++调用问卷获取报告评测PDF++++++++++++++++
function wenjuanpdf(){
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	 $("body").prepend(str);
	$.ajax({
		url:'getYxCardPdf.action?exam_num='+$('#exam_num_x').text(),
		success:function(data){
			$(".loading_div").remove();	
			var re = eval('('+data+')');
			
			if(re.status=="error"){
				$.messager.alert("警告信息",re.result,"error");
			} else {
				newwin = window.open('getYxCardPdfPage.action','健康评测报告','fullscreen','top=0,left=0,directories=no,toolbar=no,menubar=no,alwaysRaised=yes,scrollbars=no, resizable=no,location=no, status=no');
				newwin.focus();
			}
		},
		error:function(){
			$(".loading_div").remove();
			$.messager.alert("警告信息","操作失败","error");
		}
	})
}

function show_last_result(i){
	var str = '';
	if(obj[i].inputter > 0){
		$("#inputter").combobox('setValue',obj[i].inputter);
	}
	if(obj[i].lastExamResult.length>0){
			str +='<dl><dd style="width:230px;color:#FF0000;font-size:15px;">上次'+obj[i].item_name+'检查结果</dd></dl>';
			//str +='<dl><dt style="width:25%">描述：</dt><dd style="width:50%">'+obj[i].lastExamResult[0].exam_desc+'</dd></dl>';
			str +='<dl><dt style="width:25%">结论：</dt><dd style="width:50%">'+obj[i].lastExamResult[0].exam_result+'</dd></dl>';
			str +='<dl><dt style="width:25%">医生：</dt><dd style="width:50%">'+obj[i].lastExamResult[0].exam_doctor+'</dd></dl>';
			str +='<dl id="item_old_result"></dl>';
		$("#result").html(str);
	}else{
		    str+='<dl><dt style="width:230px;color:#FF0000;font-size:15px;">上次'+obj[i].item_name+'检查结果</dt></dl>';
		    //str+='<dl><dt style="width:75%">描述：无<input id="old_exam_id" type="hidden" value=""></dt></dl>';
			str+='<dl><dt style="width:75%">结论：无</dt></dl>';
			str+='<dl><dt style="width:75%">医生：无</dt></dl>';
			str +='<dl id="item_old_result"></dl>';
		$("#result").html(str);
	}
}

function lishijieguoduibi(){
	for(i=0;i<obj.length;i++){
		$('#lishi_'+i).datagrid({    
		    data:obj[i].lastExamResult,    
		    columns:[[    
		        {field:'exam_date',title:'检查日期',width:20,align:'center'},
		        {field:'exam_doctor',title:'检查医生',width:10,align:'center'},
		        {field:'exam_desc',title:'描述',width:35,align:'center'},    
		        {field:'exam_result',title:'结论',width:35,align:'center'}    
		    ]],
		    pagination : false,
			fitColumns : true,
			fit : true,
			border : false,
			nowrap : false
		});
	}
	$('#dlg-history').dialog('open');
}

function weijizhi() {
	var model = {
		"exam_num" : $("#exam_num").val()
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
		"exam_num" : $('#exam_num').val()
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
		"exam_num" : $('#exam_num').val()
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

//跳转至危急值列表页
function tocriticalpage(){
	$("#dlg-list").dialog({
		title:'危急值列表',
		href:'depCriticalPage.action?exam_num='+$("#exam_num").val()
	});
	$("#dlg-list").dialog("open");
}
