$(document).ready(function () { 
	$('#exam_num_s').keydown(function (e) {
        if (e.keyCode == 13) {
        	zhujianchaxun();
        }
    });
	  //回车查询事件
    $("#exam_num_p").keydown(function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	shangchuanchaxun();
	    }
	});
    $('#exam_doc_id').combobox({
		url : 'getFinalDoctorList.action?operation_type=1',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			var inputter = getCookie("yulan_zongjian");
			if(inputter != undefined && inputter > 0){
				$('#exam_doc_id').combobox('setValue', inputter);
			}else if (data.length>0) {
				$('#exam_doc_id').combobox('setValue', data[0].id);
			}
		},
		onSelect : function(record){
			setCookie('yulan_zongjian',record.id);
		}
	});

    $('#exam_doc_search').combobox({
		url : 'getflowUser.action?creater_state=z',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight:200,
		valueField : 'id',
		textField : 'username',
		onLoadSuccess : function(data) {
			$('#exam_doc_search').combobox('setValue', $("#userid").val());
		}
	});
    zhujianchaxun();
	shangchuanchaxun();
	getexamrejectGrid();
	setchebox();
	$('#exam_num_s').css('ime-mode','disabled');
	$('#exam_num_s').focus();
	$('#exam_num_p').css('ime-mode','disabled');
	$('#exam_num_p').focus();
});
function shuaxing(){
	chaxunwyl();
	chaxunyyl();
	chaxun3();
}
function chaxunwyl(){
	var exam_doc_id = 0;
    if($("#ck_exam_doc_id")[0].checked){
   	 exam_doc_id =  $("#exam_doc_id").combobox('getValue');
    }
    
    var exam_num = "";
    if($("#ck_exam_num_s")[0].checked){
   	 exam_num =  $("#exam_num_s").val();
    }
    if(exam_num == '' && exam_doc_id == 0){
   	 exam_num = 'xxx';
    }
  var model={
	 "doctor_id":exam_doc_id,
	 "exam_num":exam_num
  };
  
    $("#flowexamlist").datagrid('load',model);
}

function chaxunyyl(){
	var exam_type = undefined;
    if($("#ck_exam_type")[0].checked){
   	 exam_type =  $("#exam_type").combobox('getValue');
    }
    var exam_num = undefined;
	 if($("#ck_exam_num_p")[0].checked){
		 exam_num =  $("#exam_num_p").val();
	 }
	 
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#time1").datebox('getValue');
		 time2 = $("#time2").datebox('getValue');
	 }
	 
	 var exam_doc_search = undefined;
	 if($("#ck_exam_doc_search")[0].checked){
	   	 exam_doc_search =  $("#exam_doc_search").combobox('getValue');
    }
	 var model={
			 "exam_num":exam_num,
			 "time1":time1,
			 "time2":time2,
			 "doctor_id":exam_doc_search,
			 "exam_type":exam_type
		   };
	 $("#yishangchuan").datagrid('load',model);
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

 ///////////////////////////////////////上传////////////////////////////////////////////////////////////////////
    	
   function shangchuanchaxun(){	    
	     var exam_type = undefined;
	     if($("#ck_exam_type")[0].checked){
	    	 exam_type =  $("#exam_type").combobox('getValue');
	     }
	     var exam_num = undefined;
		 if($("#ck_exam_num_p")[0].checked){
			 exam_num =  $("#exam_num_p").val();
		 }
		 
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_date")[0].checked){
			 time1 = $("#time1").datebox('getValue');
			 time2 = $("#time2").datebox('getValue');
		 }
		 
//		 var exam_doc_search = undefined;
//	     if($("#ck_exam_doc_search")[0].checked){
//	    	 exam_doc_search =  $("#exam_doc_search").combobox('getValue');
//	     }
	     
		 
	   var model={
		 "exam_num":exam_num,
		 "time1":time1,
		 "time2":time2,
		 "doctor_id":$("#userid").val(),
		 "exam_type":exam_type
	   };
	   
	     $("#yishangchuan").datagrid({
		 url:'flowexameListz_dbgj.action',
		 dataType: 'json',
		 queryParams:model,
		 remoteSort:false,
		 pageNumber:1,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,100,300,500],//可以设置每页记录条数的列表
	     pagination: true,
	     collapsible:true,
	     fitColumns:true,
	     rownumbers:true,
	     height: window.screen.height-380,
		 columns:[[
            {field:'ck',checkbox:true },
            {align:'center',field:'caozuo',title:'操作',"formatter":f_qxfs},
		    {align:'center',field:'exam_num',title:tjhname,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
		    {align:'center',field:'exam_types',title:'体检类型'},	
		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
		 	{align:'center',field:'sex',title:'性别',sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否'},
		 	{align:'center',field:'age',title:'年龄',sortable:true},
		 	{align:'center',field:'phone',title:'电话',sortable:true},
		 	{align:'center',field:'company',title:'单位',width:200},
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
		  	{align:'center',field:'statuss',title:'体检状态'},
		  	{align:'center',field:'final_date',title:'主检日期'},	 	
		  	{align:'center',field:'final_doctor',title:'主检医生'}
//		  	{align:'center',field:'huishou',title:'总检情况',width:300,"formatter":f_zhongjianc}		  	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    	},
	    	onDblClickRow : function(index, row) {
//	    		 var url='getAcceptanceItemDbgj.action?exam_num='+row.exam_num;
//				 newwin = window.open("", "预览室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
//				 newwin.moveTo(0,0);
//				 newwin.resizeTo(screen.width,screen.height-30);
//				 newwin.location = url;
//				 newwin.focus();
			},
			rowStyler:function(index,row){
	    		var color = "";
	    		if(row.disease_name=='★'){
	    			color ="color:#FF0000;";
	    		}
	    		return color;
	    	}
	});
   }
   function f_qxfs(val,row){
	   return '<a href=\"javascript:qxfs(\''+row.exam_num+'\')\">取消发送</a>';
   }
   function qxfs(exam_num){
	   var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		$("body").prepend(str);
	   $.ajax({
			url:'flowexambackchk.action', 
		    type: "post",
			data:{
				'exam_num':exam_num
			},
			success: function(text){
				$(".loading_div").remove();
			  	if (text.split("-")[0] == 'ok') {
					$.messager.alert("操作提示",text.split("-")[1],"");
				} else {
					$.messager.alert("警告信息",text,"error");
				}
			  	shuaxing();
			 },
			 error:function(){
				 $(".loading_div").remove();
			    	$.messager.alert('提示信息', "用户操作失败！",function(){});
			    }
		})
   }
   var toolbar=[{
		text:'批量发送给审核医生',
		width:200,
		iconCls:'icon-save',
	    handler:function(){
	    	examz1sendchk();
	    }
	}]
   
   
   function zhujianchaxun(){
	     var exam_doc_id = 0;
	     if($("#ck_exam_doc_id")[0].checked){
	    	 exam_doc_id =  $("#exam_doc_id").combobox('getValue');
	     }
	     
	     var exam_num = "";
	     if($("#ck_exam_num_s")[0].checked){
	    	 exam_num =  $("#exam_num_s").val();
	     }
	     if(exam_num == '' && exam_doc_id == 0){
	    	 exam_num = 'xxx';
	     }
	   var model={
		 "doctor_id":exam_doc_id,
		 "exam_num":exam_num
	   };
	   
	     $("#flowexamlist").datagrid({
		 url:'flowexameListzjbg.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:toolbar,
		 remoteSort:false,
	     pagination: false,
	     collapsible:true,
	     fitColumns:true,
	     rownumbers:true,
	     height: window.screen.height-380,
		 columns:[[
          {field:'ck',checkbox:true },
            {align:'center',field:'exam_num',title:tjhname,sortable:true},
		 	{align:'center',field:'id_num',title:'身份证号',sortable:true},
		    {align:'center',field:'exam_types',title:'体检类型'},	
		 	{align:'center',field:'user_name',title:'姓名',sortable:true},
		 	{align:'center',field:'sex',title:'性别',sortable:true},
		 	{align:'center',field:'is_marriage',title:'婚否'},
		 	{align:'center',field:'age',title:'年龄',sortable:true},
		 	{align:'center',field:'phone',title:'电话',sortable:true},
		 	{align:'center',field:'pay_amount',title:'金额'},
		 	{align:'center',field:'company',title:'单位',width:200},
		 	{align:'center',field:'set_name',title:'套餐',width:200},	
		 	{align:'center',field:'join_date',title:'体检日期',sortable:true},	 	
		  	{align:'center',field:'statuss',title:'体检状态'},
		  	{align:'center',field:'final_date',title:'主检日期'},	 	
		  	{align:'center',field:'final_doctor',title:'主检医生'}
//		  	{align:'center',field:'huishou',title:'总检情况',width:300,"formatter":f_zhongjianc}		  	
		 	]],	    	
	    	onLoadSuccess:function(value){ 
	    		$("#datatotal").val(value.total);
	    		$(".loading_div").remove();
	    		getfinalCount();
	    	},
	    	onDblClickRow : function(index, row) {	  
	    		 var url='getAcceptanceItemDbgj.action?exam_num='+row.exam_num;
				 newwin = window.open("", "预览室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
				 newwin.moveTo(0,0);
				 newwin.resizeTo(screen.width,screen.height-30);
				 newwin.location = url;
				 newwin.focus();
			},
			rowStyler:function(index,row){
	    		var color = "";
	    		if(row.disease_name=='★'){
	    			color ="color:#FF0000;";
	    		}
	    		return color;
	    	}
	});
 }
 
 var toolbar=[{
		text:'批量发送给审核医生',
		width:200,
		iconCls:'icon-save',
	    handler:function(){
	    	examz1sendchk();
	    }
	}]
  
 //批量发送给审核医生
   function examz1sendchk(){
	   var listh1= new Array();
	   var checkedItems = $('#flowexamlist').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        if(item.c==1){
	        	$.messager.alert("操作提示", "体检编号【"+item.exam_num+"】的报告已经审核，不能发送给审核医生","error");
	        	return false;
	        } else if(item.z1==0) {
	        	$.messager.alert("操作提示", "体检编号【"+item.exam_num+"】总检尚未完成，不能发送给审核医生","error");
	        	return false;
	        }else{
	        	listh1.push(item.exam_num);
	        }
	    });
	  if(listh1.length>0){
		  $("#sendchk_dlg").dialog({
				title : '批量发送审核',
				href : 'batch_sendchk.action',
			});
			$("#sendchk_dlg").dialog("open");
	  }
   }
 
   
   function f_reportshow(val,row){
	   return '<a href=\"javascript:printreport(\''+row.exam_num+'\')\">预览</a>';
   }
   
   function yulanreport(){
	   if($("#exam_num_s").val()!=''){
		   printreport($("#exam_num_s").val());
	   }
   }
   
   function f_zhongjianc(val,row){
	      var showstring="";
	      if(row.z0user.length>0){
	    	  showstring=showstring+"<br>"+row.z0date+"【"+row.z0user+"】  "+row.strz0;
	      }
	      if(row.z1user.length>0){
	    	  showstring=showstring+"<br><font color='blue'>"+row.z1date+"【"+row.z1user+"】  "+row.strz1+'</font>';
	      }

	      if(row.cuser.length>0){
	    	  showstring=showstring+"<br><font color='green'>"+row.cdate+"【"+row.cuser+"】  "+row.strc+'</font>';
	      }
	      if(row.fuser.length>0){
	    	  showstring=showstring+"<br><font color='red'>"+row.fdate+"【"+row.fuser+"】  "+row.strf+'</font>';
	      }
	      return showstring;
	  }
   function chaxun3(){
		var exam_num = undefined;
		 if($("#ck_exam_num2")[0].checked){
			 exam_num =  $("#exam_num2").val();
		 }
		 var time1 = undefined,time2 = undefined;
		 if($("#ck_data2")[0].checked){
			 time1 = $("#start_date2").datebox('getValue');
			 time2 = $("#end_date2").datebox('getValue');
		 }
		 var user_name = undefined;
		 if($("#ck_custname2")[0].checked){
			 user_name = $("#custname2").val();
		 }
		var done_status = $("#done_status").combobox('getValue');
		var model={
					 "exam_num":exam_num,
					 "time1":time1,	
					 "time2":time2,
					 "user_name":user_name,
					 "exam_status":done_status
			 };
		$("#getexamreject").datagrid('reload',model);
	}
	function getexamrejectGrid(){
			 var exam_num = undefined;
			 if($("#ck_exam_num2")[0].checked){
				 exam_num =  $("#exam_num2").val();
			 }
			 var time1 = undefined,time2 = undefined;
			 if($("#ck_data2")[0].checked){
				 time1 = $("#start_date2").datebox('getValue');
				 time2 = $("#end_date2").datebox('getValue');
			 }
			 var user_name = undefined;
			 if($("#ck_custname2")[0].checked){
				 user_name = $("#custname2").val();
			 }
			var done_status = $("#done_status").combobox('getValue');
			var model={
						 "exam_num":exam_num,
						 "time1":time1,	
						 "time2":time2,
						 "user_name":user_name,
						 "exam_status":done_status
				 };
			     $("#getexamreject").datagrid({
				 url:'getAlreadyRejectList.action',
				 dataType: 'json',
				 queryParams:model,
			     pageSize: 15,//每页显示的记录条数，默认为10 
			     pageList:[15,30,45,60,100],//可以设置每页记录条数的列表 
				 columns:[[
		            {field:'ck',checkbox:true },
				    {align:'center',field:'exam_num',title:'体检号',width:100,sortable:true},
				    {align:'center',field:'arch_num',title:'档案号',width:100,sortable:true},
				    {align:'center',field:'exam_types',title:'体检类型',width:60},	
				 	{align:'center',field:'user_name',title:'姓名',width:60,sortable:true},
				 	{align:'center',field:'sex',title:'性别',width:50,sortable:true},
				 	{align:'center',field:'age',title:'年龄',width:40,sortable:true},
				 	{align:'center',field:'join_date',title:'体检日期',width:100,sortable:true},
				 	{align:'center',field:'final_date',title:'总检日期',width:140,sortable:true},
				 	{align:'center',field:'final_doctor',title:'总检医生',width:80},
				 	{align:'center',field:'reject_doctor',title:'驳回医生',width:80},
				 	{align:'center',field:'reject_time',title:'驳回日期',width:140},
				 	{align:'center',field:'reject_context',title:'驳回原因',width:200},
				 	{align:'center',field:'done_statuss',title:'处理状态',width:80},
				 	{align:'center',field:'done_time',title:'处理日期',width:140},
				 	{align:'center',field:'company',title:'单位',width:220,sortable:true},
				 	{align:'center',field:'set_name',title:'套餐',width:150,sortable:true}
				 	]],	    	
			    	onLoadSuccess:function(value){ 
			    		$("#datatotal").val(value.total);
			    	
			    	},
			    	onDblClickRow : function(index, row) {
			    		if(row.done_status == 1){
			    			var url='getAcceptanceItemDbgj.action?exam_num='+row.exam_num;
							 newwin = window.open("", "预览室", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
							 newwin.moveTo(0,0);
							 newwin.resizeTo(screen.width,screen.height-30);
							 newwin.location = url;
							 newwin.focus();
			    		}else{
			    			$.messager.alert("警告信息","驳回未处理，不能预览","error");
			    		}
					},
					height: window.screen.height-380,
					nowrap:true,
					fit:false,
					rownumbers:false,
			    	singleSelect:false,
				    collapsible:true,
					pagination: true,
				    fitColumns:false,
				    striped:true
			});
		}   
	function getfinalCount(){
		$.ajax({
	    	url:'getFlowExamPreviewCount.action',
	    	type:'post',
	    	success:function(data){
	    		var obj=eval("("+data+")");
	    		$("#wz_count").html(obj.wz_count);
	    		$("#yz_count").html(obj.yz_count);
	    		$("#zj_count").html(obj.zj_count);
	    	},
	    	error:function(){
	    		$(".loading_div").remove();
	    		$.messager.alert("警告信息","操作失败","error");
	    	}
	    });
	}  	  
  /**
    ****搜索添加输入框根据是否为空自动判断chebox选中
    */
    function setchebox(inp){    	
    	$('#exam_num_p').change(function() {
    		if($(this).val()==""){
    			$('.ck_exam_num_p').attr('checked',false)
    		} else {
    			$('.ck_exam_num_p').attr('checked',true)
    		}
    	});    	
    }