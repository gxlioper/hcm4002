$(document).ready(function () {
	var exam_num = $("#exam_num").val();
	getcustomerInfo(exam_num);
	gethyGrid(exam_num);
	gethyItemGrid(0,0);
	wenjuanbuttoshow();
});
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
		}
	});
}

function gethyGrid(exam_num){
	var model={"exam_num":exam_num,"dep_id":$("#dep_id").val()};
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
			    {align:'center',field:'exam_date',title:'检查时间',width:20}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    		MergeCells('hy_sam_itemList', 'demo_name');
		    	},
		    	onClickRow:function(rowIndex, rowData){
		    		$('#hy_itemResultList').datagrid('load', {
		    			"examinfo_id":$("#exam_id").val(),"charging_id":rowData.id
		    		});
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

function gethyItemGrid(id,item_id){
	var lastIndex;
	var old_exam_result;
	var model={"examinfo_id":id,"charging_id":item_id};
	$("#hy_itemResultList").datagrid({
			 url:'getExamResultExaminitionItem.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
			 columns:[[
			    {align:'center',field:'item_name',title:'检查项目',width:20},
			 	{align:'center',field:'exam_result',title:'检查结果',width:20,"styler":f_color_hy},
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
			    nowrap:false
	});
}

function f_ckz(val,row){
	if(row.exam_result == ''){
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

function readExam_result(){
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	$("body").prepend(str);
	$.ajax({
		url:'getExamResultDetailReader.action',
		type:'post',
		data:{
			exam_num:$("#exam_num").val()
		},
		success:function(data){
			$(".loading_div").remove();
			var obj = data.split("-");
			if(obj[0] == 'ok'){
				$.messager.alert("操作提示",obj[1], "info");
				var exam_num = $("#exam_num").val();
				gethyGrid(exam_num);
				gethyItemGrid(0,0);
			}else{
				$.messager.alert("操作提示",obj[1], "error");
			}
		}
	});
}

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
			},
	  })

}
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
//FF中需要修改配置window.close方法才能有作用，为了不需要用户去手动修改，所以用一个空白页面显示并且让后退按钮失效
//Opera浏览器旧版本(小于等于12.16版本)内核是Presto，window.close方法有作用，但页面不是关闭只是跳转到空白页面，后退按钮有效，也需要特殊处理
function close_page(){
	var _parentWin =  window.opener ;
	var userAgent = navigator.userAgent;
	  window.opener = null;
	  window.open('', '_self');
	  window.close();
	  _parentWin.getgroupuserGrid();
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