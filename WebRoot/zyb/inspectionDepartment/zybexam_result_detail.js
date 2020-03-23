$(document).ready(function () {
	var exam_num = $("#exam_num").val();
	gethyGrid(exam_num);
	gethyItemGrid(exam_num,'');
	getoccuhazardfactorsGrid();
	if($("#status").val() == '3'){
		var obj = $(".easyui-linkbutton");
		for(i=0;i<obj.length;i++){
			obj.eq(i).css('position','inherit');
			if(obj.eq(i).children().children().html() == '查看职业史'){
				obj.eq(i).children().css('margin-top','-2px');
			}else if(obj.eq(i).children().children().html() == '问卷'){
				obj.eq(i).css('display','none');
			}
		}
	}
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
});

function gethyGrid(exam_num){
	var model={"exam_num":exam_num,"app_type":$("#app_type").val()};
	$("#hy_sam_itemList").datagrid({
			 url:'getExamResultCharingItem.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
				{align:'center',field:'demo_name',title:'样本名称',width:20},
				{align:'center',field:'item_name',title:'收费项目',width:20,"styler":f_color_item},
				{align:'center',field:'exam_num',title:'检验关联码',width:20,"styler":f_color_item},
				{align:'center',field:"exam_doctor","title":"检查医生","width":20},
			    {align:'center',field:'exam_date',title:'检查时间',width:20},
				{align:'center',field:'inputters',title:'录入者',width:20}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('hy_sam_itemList', 'demo_name');
		    	},
		    	onClickRow:function(rowIndex, rowData){
		    		$('#hy_itemResultList').datagrid('load', {
		    			"exam_num":$("#exam_num").val(),"charging_item_code":rowData.item_code
		    		});
		    		if(rowData.inputter > 0){
		    			$('#inputter').combobox('setValue', rowData.inputter);
		    		}
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}

function f_color_item(value,row,index){
	if(row.exam_status != 'Y'){
		return 'color:#F00;';
	}
}

function f_color_hy(value,row,index){
	if(row.ref_indicator == 1 || row.ref_indicator == 2 || row.ref_indicator == 3|| row.ref_indicator == 5|| row.ref_indicator == 6){
		return 'color:#F00;';
	}else if(row.ref_indicator == 4){
		return 'color:#FF9B00;';
	}
}
function f_bs(val,row){
	if(row.ref_indicator == 1){
		return '↑';
	}else if(row.ref_indicator == 2){
		return '↓';
	}else if(row.ref_indicator == 5){
		return '↑↑';
	}else if(row.ref_indicator == 6){
		return '↓↓';
	}else{
		return '';
	}
}

function gethyItemGrid(exam_num,item_code){
	var lastIndex;
	var old_exam_result;
	var model={"exam_num":exam_num,"charging_item_code":item_code};
	$("#hy_itemResultList").datagrid({
			 url:'getExamResultExaminitionItem.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
			    {align:'center',field:'item_name',title:'检查项目',width:20},
			 	{align:'center',field:'exam_result',title:'检查结果',width:20,"styler":f_color_hy,editor:{type:'text',options:{}}},
			 	{align:'center',field:'bs',title:'标识',width:10,"formatter":f_bs,"styler":f_color_hy},
			 	{align:'center',field:'item_unit',title:'单位',width:10},
			 	{align:'center',field:'ref_value',title:'参考值',width:20,"formatter":f_ckz},
			 	{align:'center',field:'exam_num',title:'检验关联码',width:20}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false,
			    onClickRow:function(index,row){
			    	
			    	old_exam_result = row.exam_result;
			    	$(this).datagrid('endEdit', lastIndex);
			    	$(this).datagrid('beginEdit', index);
			    	
					var editors = $(this).datagrid('getEditor',{index:index,field:'exam_result'});
					$(editors.target).change(function(){
						$('#hy_itemResultList').datagrid('endEdit', index);
					});
					lastIndex = index;
			    },
			    onAfterEdit:function(rowIndex, row, changes){
			    	if(row.item_category == '数字型'){
			    		var res = /^(-?\d+)(\.\d+)?$/;
			    		if(res.test(row.exam_result)){
			    			if($("#sex").html() == '男'){
			    				if(row.ref_Mmin != '' && row.ref_Mmax != ''){
			    					if(Number(row.exam_result) < Number(row.ref_Mmin)){
			    						row.ref_indicator = 2;
			    					}else if(Number(row.exam_result) > Number(row.ref_Mmax)){
			    						row.ref_indicator = 1;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else if(row.ref_Mmin != '' && row.ref_Mmax == ''){
			    					if(Number(row.exam_result) < Number(row.ref_Mmin)){
			    						row.ref_indicator = 2;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else if(row.ref_Mmin == '' && row.ref_Mmax != ''){
			    					if(Number(row.exam_result) > Number(row.ref_Mmax)){
			    						row.ref_indicator = 1;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else{
			    					row.ref_indicator = 0;
			    				}
			    			}else{
			    				if(row.ref_Fmin != '' && row.ref_Fmax != ''){
			    					if(Number(row.exam_result) < Number(row.ref_Fmin)){
			    						row.ref_indicator = 2;
			    					}else if(Number(row.exam_result) > Number(row.ref_Fmax)){
			    						row.ref_indicator = 1;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else if(row.ref_Fmin != '' && row.ref_Fmax == ''){
			    					if(Number(row.exam_result) < Number(row.ref_Fmin)){
			    						row.ref_indicator = 2;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else if(row.ref_Fmin == '' && row.ref_Fmax != ''){
			    					if(Number(row.exam_result) > Number(row.ref_Fmax)){
			    						row.ref_indicator = 1;
			    					}else{
			    						row.ref_indicator = 0;
			    					}
			    				}else{
			    					row.ref_indicator = 0;
			    				}
			    			}
			    		}else{
			    			$.messager.alert('提示信息', '项目'+row.item_name+',检查结果必须为数字!','error');
			    			row.exam_result = old_exam_result;
			    		}
			    	}else{
			    		var flag = false;
			    		if(row.itemRefList.length == 0){
			    			flag = true;
			    		}
			    		for(i=0;i<row.itemRefList.length;i++){
							if(row.itemRefList[i].val_info == row.exam_result){
								flag = true;
							}
						}
			    		if(flag){
			    			row.ref_indicator = 0;
			    		}else{
			    			row.ref_indicator = 3;
			    		}
			    	}
			    	$(this).datagrid('refreshRow', rowIndex);
			    }
	});
}

function f_ckz(val,row){
	if(row.id == 0){
		if(row.item_category == '数字型'){
			if($("#sex").html() == '男'){
				if(row.ref_Mmin != '' && row.ref_Mmax != ''){
					row.ref_value=  row.ref_Mmin +'-'+row.ref_Mmax;
				}else if(row.ref_Mmin != '' && row.ref_Mmax == ''){
					row.ref_value= '>'+ row.ref_Mmin;
				}else if(row.ref_Mmin == '' && row.ref_Mmax != ''){
					row.ref_value= '<'+ row.ref_Mmax;
				}else{
					row.ref_value= '';
				}
			}else{
				if(row.ref_Fmin != '' && row.ref_Fmax != ''){
					row.ref_value=  row.ref_Fmin +'-'+row.ref_Fmax;
				}else if(row.ref_Fmin != '' && row.ref_Fmax == ''){
					row.ref_value= '>'+ row.ref_Fmin;
				}else if(row.ref_Fmin == '' && row.ref_Fmax != ''){
					row.ref_value= '<'+ row.ref_Fmax;
				}else{
					row.ref_value= '';
				}
			}
		}else{
			var ref = new Array();
			for(i=0;i<row.itemRefList.length;i++){
				ref.push(row.itemRefList[i].val_info);
			}
			row.ref_value=ref.toString();
		}
	}
	return row.ref_value;
	
}
function shezhiyisheng(){
	if($("#isExamResultDetailDoctorPageShow").val() == 'Y'){
		$("#shezhiyisheng").dialog({
		    center:"center",
			title:'设置检查审核医生',
			href:"getSetdoctor.action"
		});
		$("#shezhiyisheng").dialog('open');
	}else{
		saveExam_result();
	}
}
function saveExam_result(){
	var jc_text = 0,sh_text = 0;
	var jc_id = 0,sh_id=0;
	if($("#isExamResultDetailDoctorPageShow").val() == 'Y'){
		jc_text = $('#jc').combobox('getText');
		sh_text = $('#sh').combobox('getText');
		jc_id = $('#jc').combobox('getValue');
		sh_id = $('#sh').combobox('getValue');
		if(jc_text==""){
			$.messager.alert("提示信息","请选择检查医生","error");
			return;
		}
		if(sh_text==""){
			$.messager.alert("提示信息","请选择检审核医生","error");
			return;
		}
	}else{
		jc_text = $("#username").val();
		sh_text = $("#username").val();
	}
	
	var charging = $("#hy_sam_itemList").datagrid('getSelected');
	if(charging == null){
		$.messager.alert('提示信息', '请先选择收费项目','error');
		return;
	}
	var obj = $("#hy_itemResultList").datagrid('getRows');
	var item_result = new Array();
	for(i=0;i<obj.length;i++){
		if(obj[i].exam_result != ''){
			item_result.push({
				"id":obj[i].id,
				"exam_result":obj[i].exam_result,
				"ref_indicator":obj[i].ref_indicator,
				"item_unit":obj[i].item_unit,
				"exam_info_id":$("#exam_id").val(),
				"exam_item_id":obj[i].exam_item_id,
				"ref_value":obj[i].ref_value,
				"exam_doctor":jc_text,
				"exam_doctor_id":jc_id,
				"approver_id":sh_id,
				"approver":sh_text,
				"item_num":obj[i].item_num,
				"item_code":charging.item_code,
				"charging_item_id":charging.id
			});
		}
	}
	if(item_result.length == 0){
		$.messager.alert('提示信息', '未录入结果,不用保存!','error');
		return;
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'saveExamResultDetail.action',
		type:'post',
		data:{
			examinfo_id:$("#exam_id").val(),
			dep_id:$("#dep_id").val(),
			app_type:$("#app_type").val(),
			dep_num:$("#dep_num").val(),
			exam_num:$("#exam_num").val(),
			charging_id:charging.id,
			charging_item_code:charging.item_code,
			exam_status:charging.exam_status,
			examResulLists:JSON.stringify(item_result),
			inputter : $('#inputter').combobox('getValue')
		},
		success:function(data){
			$(".loading_div").remove();
			setCookie('inputter',$('#inputter').combobox('getValue'));
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$('#shezhiyisheng').dialog('close')
				$.messager.alert("操作提示",obj[1], "info");
				var exam_num = $("#exam_num").val();
				gethyGrid(exam_num);
				gethyItemGrid(exam_num,'');
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}

///////////////////////////////显示问卷/////////////////////////////////////
function shouSurver(){
	 var model={
			 exam_num_x:$('#exam_num').val()	 
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
			},
	  })

}
function wenjuanbuttoshow(){
	 var model={
			 exam_num_x:$('#tjh').html()	 
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
		_parentWin.getgroupuserGrid();
	}
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
                    }
                    else {
                        CurValue = dg.datagrid("getRows")[row][fldName];
                    }
                     if (PerValue == CurValue) {
                        span += 1;
                    }
                     else {
                        var index = row - span;
                         dg.datagrid('mergeCells', {
                            index: index,
                             field: fldName,
                             rowspan: span,
                             colspan: null
                         });
                         span = 1;
                         PerValue = CurValue;
                     }
                 }
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
//  	getcusthisGrid();
//  	$("#dlg-zys").dialog('open');
//  	$("#dlg-zys").dialog('center');
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
    
  