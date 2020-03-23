$(document).ready(function () {
	//回车查询事件
		 $("#exam_num_s").keydown(function(event){
			    event=document.all?window.event:event;
			    if((event.keyCode || event.which)==13){
			    	getcustomerInfo();
			    }
	    }); 
	    
	   $('#wjzlx').combobox({
		url : 'getDatadis.action?com_Type=WJZLX',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : 'auto',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#wjzlx').combobox('setValue', data[1].id);
			}
		}
	});
	
});

//查询人员基本信息
function getcustomerInfo(){
	$.ajax({
		url:'getCustomerInfo.action?exam_num='+$("#exam_num_s").val(),
		type:'post',
		async:false,
		success:function(data){
			if(data!="null" && eval("("+data+")").flag!='error'){
				var obj=eval("("+data+")");
				$("#exam_id").val(obj.id);
				$("#exam_num").val(obj.exam_num);
				$("#user_name").html(obj.user_name);
				$("#sex").html(obj.sex+'&nbsp;&nbsp;性');
				$("#age").html(obj.age+'&nbsp;&nbsp;岁');
				$("#userphone").html(obj.phone);
				$("#uservipflag").html(obj.vipflag);
				$("#join_date").html(obj.join_date);
				$("#company").html(obj.company);
				$("#exam_type").html(obj.exam_type);
				$('#getReportWayLine').show();
				$('#getReportWay').combobox('setValue', obj.getReportWay);
				$("#reportAddress").val(obj.reportAddress.trim());
				$("#emailAddress").val(obj.email.trim());
				if(obj.getReportWay == '1') {
					$("#reportAddressLine").show();
				} else {
					$("#reportAddressLine").hide();
				}
				if(obj.getReportWay == '3'){
					$("#email").show();
				}else{
					$("#email").hide();
				}
				if(obj.wuxuzongjian==1){
					$("#wuxuzongjian").html("无需总检");
					$("#zongjian").html("总检");
				}else{
					$("#wuxuzongjian").html("");
					$("#zongjian").html("无需总检");
				}
				
				if(obj.exam_dep.length > 0){
					$("#teshuxiangmu").show();
					var str = '';
					for(i=0;i<obj.exam_dep.length;i++){
						str +='<dl><dt>'+obj.exam_dep[i].item_name+'：</dt><dt>'+obj.exam_dep[i].exam_result+'</dt></dl>';
					}
					$("#teshu_div").html(str);
				}else{
					$("#teshuxiangmu").hide();
				}
				getGrid();
				getFinalSummary(obj.exam_num);
				getExamDisease(obj.exam_num);
				gettjbg(obj.exam_num,"");
				getwjlistGrid();
			}else{
				$("#exam_id").val("");
				$("#exam_num").val("");
				$("#user_name").html("");
				$("#sex").html("");
				$("#age").html("");
				$("#userphone").html("");
				$("#uservipflag").html("");
				$("#join_date").html("");
				$("#company").html("");
				$("#exam_type").html("");
				$('#getReportWayLine').hide();
				$('#email').hide();
				$("#reportAddress").val("");
				$("#reportAddressLine").hide();
				$("#wuxuzongjian").html("");
			}			
			$("#exam_num_s").select();
			$("#exam_num_s").focus();
			
		}
	});
	
}





function getGrid(){//
	var model = {"exam_num":$("#exam_num_s").val()};
	$("#item_result").datagrid({
		url: 'getAcceptanceItemResult.action',
		queryParams: model,
		rownumbers:false,
		pageSize: 15,//每页显示的记录条数，默认为10 
		pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		sortName: 'cdate',
		sortOrder: 'desc',
		columns:[[
		          {align:"center",field:"dep_name","title":"收费项目","width":10},
		          {align:'center',field:"exam_doctor","title":"检查医生","width":10},
		          {align:'center',field:"exam_date","title":"检查时间","width":15},
		          {align:'',field:"item_name","title":"检查项目","width":15,"styler":f_clolor},
		          {align:"",field:"exam_result","title":"检查结果","width":25,"styler":f_clolor,"formatter":f_flowor}
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	MergeCells('item_result', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		//striped:true,
		toolbar:"#toolbar",
		border:false,
		nowrap:false
	});
}

//总检结论
function getFinalSummary(exam_num){
	$.ajax({
		url:'getFinalExamResult.action?exam_num='+exam_num+'&sug_flag=N',
		type:'post',
		success:function(data){
			if(data != 'null'){
				var obj=eval("("+data+")");
				$("#zongjianjielun").val(obj.final_exam_result);
			}
		}
	});
}

function getExamDisease(exam_num){
	 var model={"exam_num":exam_num};
	$("#exam_disease").datagrid({
			 url:'getExamDiseaseResult.action',
			 dataType: 'json',
			 queryParams:model,
			 rownumbers:true,
//		     pageSize: 15,//每页显示的记录条数，默认为10 
//		     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
			 columns:[[
			    {align:'',field:'disease_name',title:'阳性/疾病发现',width:10},
			 	{align:'',field:'suggest',title:'阳性/疾病建议',width:20,"formatter":f_font_size}
			 	]],	    	
		    	onLoadSuccess:function(value){
		    		$("#datatotal").val(value.total);
		    	},
		    	singleSelect:true,
//			    collapsible:true,
				pagination: false,
			    fitColumns:true,
			    fit:true,
			    striped:true,
			    nowrap:false
	});
}

function f_font_size(value,row,index){
	var count = row.suggest.indexOf("：")+1;
	if(count != -1){
		var tou = row.suggest.substring(0,count);
		var wei = row.suggest.substring(count,row.suggest.length-1);
		
		return '<span style="font-weight:bold;">'+tou+'</span>'+wei;
	}else{
		return row.suggest;
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
      
      function gettjbg(exam_num,app_type){
    		var model = {exam_num:exam_num,app_type:app_type};
    		document.getElementById("disease_tijianbaogao").innerHTML="";
    		$.ajax({
    			url:'gettjbgList.action',
    			type:'post',
    			data:model,
    			success:function(data){
    				var obj = eval('('+data+')');
    				if(obj.DJD_path!='NULL'){
    					document.getElementById("disease_tijianbaogao").innerHTML="<img src='picture/"+obj.DJD_path+"' style='width:800px;height:1400px' />";
    				}
    				//			$('#miaoshu').textbox('setValue',obj.notice);
//    				$('#lizi').textbox('setValue',obj.example+obj.examplenote);
    			}
    		});
    	}
      function f_clolor(value,row,index){
    		if(row.dep_category == '17'){
    			if(row.health_level == 'Y'){
    				return 'color:#F00;';
    			}else if(row.health_level == 'W'){
    				return 'color:#FF9B00;';
    			}
    			if(row.item_id == '0'){
    				return 'background:#FEEAA8;color:#ff5100;';
    			}
    		}else if(row.dep_category == '131'){
    			if(row.health_level == 1){
    				return 'color:#ff0000;';
    			}else if(row.health_level == 2){
    				return 'color:#0000ff;';
    			}else if(row.health_level == 3){
    				return 'color:#ff5100;';
    			}else if(row.health_level == 4){
    				return 'color:#FF9B00;';
    			}
    		}
    	}

    	function f_flowor(val,row){
    		if(row.dep_category == '21'){
    			if(row.exam_result == 'image_path'){
    				return '<a href="javascript:void(0)" onclick = "show_picture(\''+row.req_id+'\')">查看图片</a>';
    			}else{
    				return row.exam_result.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'</br>');
    			}
    		}else if(row.dep_category == '131'){
    			if(row.health_level == 1){
    				return row.exam_result+' ↑';
    			}else if(row.health_level == 2){
    				return row.exam_result+' ↓';
    			}else{
    				return row.exam_result;
    			}
    		}else{
    			if(row.exam_result!=null){
    			    return row.exam_result.replace(/\n/g,'</br>');
    			}
    		}
    	}
    	
    	function show_picture(id){
    		var url='/showViewExamImage.action?pacs_req_code='+id+'&exam_num='+$("#exam_num").val();
    		newWindow = window.open(url, "查看图片", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
    		newWindow.focus();
    	}


function addCritical(){
	if($("#exam_num_s").val() == '' || $("#exam_num_s").val() == null){
		$.messager.alert('提示信息','体检号不能为空。。。。','info');
		return;
	}
	$("#note").val('');
	 $("#dlg-beizhu").dialog({
			title : '添加危急值',
		});
	$("#dlg-beizhu").dialog('open');
}
   
function f_Crisave(){
	var note = $("#note").val();
	if(note == '' || note == null){
		$.messager.alert('提示信息','请输入内容。。。。','info');
		return;
	}
	var type = $("#wjzlx").combobox('getValue');
	 var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		 $("body").prepend(str);
		 $.ajax({
			 url : 'addCriticalDBGJ.action',
			data : {'exam_num':$("#exam_num_s").val(),
					'note':note,
					'critical_type':type
			},
			type : "post",
			success : function(data) {
				$(".loading_div").remove();
				$.messager.alert("操作提示",data);
				getwjlistGrid();
				$('#dlg-beizhu').dialog('close');
				
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert('提示信息', '操作失败！', 'error');
			}
		 });
	
	
}
   
 function getwjlistGrid(){//
	var model = {"exam_num":$("#exam_num_s").val()};
	$("#wjlist").datagrid({
		url: 'getCriticalListByExamNum.action',
		queryParams: model,
		rownumbers:true,
		columns:[[
		          {align:"center",field:"exam_result","title":"内容","width":60},
		          {align:'center',field:"creater","title":"创建者","width":10},
		          {align:'center',field:"create_time","title":"创建时间","width":20},
		          {align:'center',field:"done_flag_s","title":"处理标识","width":12,"formatter":f_done_flag},
		          {align:'center',field:"done_date","title":"处理时间","width":20},
		          {align:'center',field:"note","title":"处理内容","width":40},
		          {align:'center',field:"critical_type_s","title":"类型","width":15}
		          
		        
		          ]],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
//	    	MergeCells('item_result', 'dep_name,exam_doctor,exam_date');
	    },
	    singleSelect:true,
	    collapsible:true,
		pagination: false,
		fitColumns:true,
		fit:true,
		striped:true,
		nowrap:false,
		fixed:true
	});
}

function f_done_flag(val,row){
	
	if(val == '未处理'){
		return '<font color="red">'+val+'</font>';
	}else{
		return val;
	}
}
