<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%  
        application.setAttribute("name","application_James");  
       
   %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员列表</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var toolbar = [{
    text:'单独制卡',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		height:450,
    		title:'单独制卡',
    		href:'cardinfosave.action'
    	});
    	$("#dlg-edit").dialog("open");
    }
},{
	text:'批量制卡',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-pl_card").dialog({
    		title:'批量制卡',
    		href:'cardBatch.action'
    	});
    	$("#dlg-pl_card").dialog("open");
    }
},{
	text:'批量删除',
    iconCls:'icon-remove',
    handler:function(){
    	var obj = $("#cardinfolist").datagrid('getSelections');
    	if(obj.length == 0){
    		$.messager.alert("操作提示", "请选择需要删除的卡信息!", "error");
    	}else{
    		var card_num = new Array();
    		for(i=0;i<obj.length;i++){
    			if(obj[i].card_count != 0){
    				$.messager.alert("操作提示", "卡号:"+obj[i].card_num+" 已使用，不能删除!", "error");
    				return;
    			}else if(obj[i].hair_card_status == 1){
    				$.messager.alert("操作提示", "卡号:"+obj[i].card_num+" 已发卡，不能删除!", "error");
    				return;
    			}else{
    				card_num.push("'"+obj[i].card_num+"'");
    			}
    		}
    		$.messager.confirm('确认对话框', '您确定要删除选中卡信息吗？', function(r){
    			if (r){
    				var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    	    	    $("body").prepend(str);
    		    	$.ajax({
    		    		url:'deleteCardInfo.action',
    		    		data:{card_num:card_num.toString()},
    		    		type:'post',
    		    		success:function(data){
    		    			$(".loading_div").remove();
    		    			$.messager.alert("操作提示", data, "info");
    		    			$("#cardinfolist").datagrid('reload');
    		    		},
    		    		error : function() {
    		    			$(".loading_div").remove();
    		    			$.messager.alert("操作提示", "操作错误", "error");					
    		    		}
    		    	});
    			}
    		});
    	}
    }
},{
	text:'读卡',
    iconCls:'icon-save',
    handler:function(){
    	$("#dlg-edit").dialog({
    		height:420,
    		title:'读卡',
    		href:'getReaderCard.action'
    	});
    	$("#dlg-edit").dialog("open");
    }
},{
	text:'查看已删除卡信息',
    iconCls:'icon-save',
    handler:function(){
    	$("#dlg-pl_card").dialog({
    		title:'已删除卡信息列表',
    		href:'getDeleteCardPage.action'
    	});
    	$("#dlg-pl_card").dialog("open");
    }
},{
	text:'批量发卡',
    iconCls:'icon-save',
    handler:function(){
    	var rows = $("#cardinfolist").datagrid('getSelections');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要发卡的卡信息!", "error");
    		return;
    	}
    	var card_num = new Array();
    	for(var i=0;i<rows.length;i++){
    		if(rows[i].hair_card_status == '1'){
    			$.messager.alert("操作提示", "卡号"+rows[i].card_num+"已发卡，不能再次发卡!", "error");
        		return;
    		}
    		card_num.push(rows[i].card_num);
    	}
    	if($("#isCardUserChoose").val() == 'Y'){
    		openCardUserChoose();
    	}else{
	    	$.messager.confirm('确认对话框', '您确定要发放选中的 '+rows.length+' 张卡信息吗？', function(r){
	    		if (r){
	    			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
		    	    $("body").prepend(str);
			    	$.ajax({
			    		url:'saveHairCardInfo.action',
			    		data:{card_num:card_num.toString()},
			    		type:'post',
			    		success:function(data){
			    			$(".loading_div").remove();
			    			$.messager.alert("操作提示", data, "info");
			    			$("#cardinfolist").datagrid('reload');
			    		},
			    		error : function() {
			    			$(".loading_div").remove();
			    			$.messager.alert("操作提示", "操作错误", "error");					
			    		}
			    	});
	    		}
	    	});
    	}
    }
},{
	text:'撤销发卡',
    iconCls:'icon-edit',
    handler:function(){
    	var rows = $("#cardinfolist").datagrid('getSelections');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要发卡的卡信息!", "error");
    		return;
    	}
    	var card_num = new Array();
    	for(var i=0;i<rows.length;i++){
    		if(rows[i].hair_card_status != '1'){
    			$.messager.alert("操作提示", "卡号"+rows[i].card_num+"未发卡，不能撤销发卡!", "error");
        		return;
    		}else if(rows[i].card_count != '0'){
    			$.messager.alert("操作提示", "卡号"+rows[i].card_num+"已使用，不能撤销发卡!", "error");
        		return;
    		}
    		
    		card_num.push(rows[i].card_num);
    	}
    	$.messager.confirm('确认对话框', '您确定要撤销选中的卡信息吗？', function(r){
    		if (r){
    			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    	    $("body").prepend(str);
		    	$.ajax({
		    		url:'delHairCardInfo.action',
		    		data:{card_num:card_num.toString()},
		    		type:'post',
		    		success:function(data){
		    			$(".loading_div").remove();
		    			$.messager.alert("操作提示", data, "info");
		    			$("#cardinfolist").datagrid('reload');
		    		},
		    		error : function() {
		    			$(".loading_div").remove();
		    			$.messager.alert("操作提示", "操作错误", "error");					
		    		}
		    	});
    		}
    	});
    }
},{
	text:'修改',
    iconCls:'icon-edit',
    handler:function(){
    	var rows = $("#cardinfolist").datagrid('getSelections');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要修改卡的卡信息!", "warning");
    	}else if (rows.length == 1){
    		var card_num = rows[0].card_num;
    		if(rows[0].hair_card_status == '1'){
    			$.messager.alert("操作提示", "该卡已发出不能修改!", "error");
    		}else{
    			xg_yemian(card_num);
    		}
    	}else{
    		$.messager.alert("操作提示", "只能修改一张卡的卡信息!", "warning");
    	}
    }
},{
	text:'批量修改',
    iconCls:'icon-edit',
    handler:function(){
    	var rows = $("#cardinfolist").datagrid('getSelections');
    	if(rows.length == 0){
    		$.messager.alert("操作提示", "请选择需要修改卡的卡信息!", "warning");
    	}else{ 
    		var card_num = new Array();
        	for(var i=0;i<rows.length;i++){
        		card_num.push(rows[i].card_num);
        	}
        	plxg_yemian(card_num.toString());
    	}
    }
}];
$(document).ready(function () {
	if($("#status").val() != 'Y'){
		toolbar = '';
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=KDJ',
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			var str = '<option value="">不限</option>';
			var obj=eval(data);
			for(var i=0;i<obj.length;i++){
				str += '<option value="'+obj[i].id+'">'+obj[i].name+'</option>';
			}
			$("#ser_level").html(str);
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
	$('#ser_hair_card_creater').combobox({
		url : 'getSysLogUserList.action',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			$('#ser_hair_card_creater').combobox('setValue',data[0].id);
		}
	});
	$('#card_users').combobox({
		url : 'getSysLogUserList.action?oper_type=1',
		editable : true, //不可编辑状态
		cache : false,
		panelHeight : '300',//自动高度适合
		valueField : 'id',
		textField : 'chi_Name',
		onLoadSuccess : function(data) {
			
		}
	});
	getGrid();
});

function openCardUserChoose(){
	$('#card_users').combobox('clear');
	$('#card_users').combobox('reload');
	$("#dlg-fkr").dialog({
		title:'选择发卡人信息',
		center:'center'
	});
	$("#dlg-fkr").dialog('open');
}

function save_cardChoose(){
	if($('#card_users').combobox('getValue') == '' || $('#card_users').combobox('getValue') == undefined){
		$.messager.alert("操作提示", "请选择发卡人!", "error");	
		return;
	}
	var rows = $("#cardinfolist").datagrid('getSelections');
	var card_num = new Array();
	for(var i=0;i<rows.length;i++){
		card_num.push(rows[i].card_num);
	}
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
    $("body").prepend(str);
	$.ajax({
		url:'saveHairCardInfo.action',
		data:{'card_num':card_num.toString(),'hair_card_creater':$('#card_users').combobox('getValue')},
		type:'post',
		success:function(data){
			$(".loading_div").remove();
			$("#dlg-fkr").dialog('close');
			$.messager.alert("操作提示", data, "info");
			$("#cardinfolist").datagrid('reload');
		},
		error : function() {
			$(".loading_div").remove();
			$.messager.alert("操作提示", "操作错误", "error");					
		}
	});
}

function getGrid(){
	var card_num = undefined;
	 if($("#ck_ser_num")[0].checked){
		 card_num =  $("#ser_num").val();
	 }
	 var status = undefined;
	 if($("#ck_ser_status")[0].checked){
		 status =  $("#ser_status").val();
	 }
	 var time1 = undefined,time2 = undefined;
	 if($("#ck_date")[0].checked){
		 time1 = $("#start_date").datebox('getValue');
		 time2 = $("#end_date").datebox('getValue');
	 }
	 var company = undefined;
	 if($("#ck_ser_company")[0].checked){
		 company =  $("#ser_company").val();
	 }
	 var card_level = undefined;
	 if($("#ck_ser_level")[0].checked){
		 card_level =  $("#ser_level").val();
	 }
	 var hair_card_status = -1;
	 if($("#ck_hair_card_status")[0].checked){
		 hair_card_status = $("#ser_hair_card_status").val();
	 }
	 var ser_hair_card_creater = -1;
	 if($("#ck_hair_card_creater")[0].checked){
		 ser_hair_card_creater = $("#ser_hair_card_creater").combobox('getValue');
	 }
	var model = {"card_num":card_num,
			"status":status,
			"time1":time1,
			"time2":time2,
			"company":company,
			"card_level":card_level,
			'hair_card_status':hair_card_status,
			'hair_card_creater':ser_hair_card_creater};
	   $("#cardinfolist").datagrid({
		url: '<%=request.getContextPath()%>/getCardInfoList.action',
		queryParams: model,
		rownumbers:false,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     toolbar: '#toolbar',
     sortName: 'cdate',
	 sortOrder: 'desc',
     height:390,
     columns:[[{align:'center',field:'ck',checkbox:true},
              {align:"center",field:"card_num","title":"卡号","width":20,sortable:true},
     		  {align:'center',field:"status_y","title":"卡状态","width":10},
//     		  {align:"center",field:"card_type_y","title":"卡类型","width":10},
     		  {align:"center",field:"amount","title":"卡余额(元)","width":10},
//     		  {align:"center",field:"member_name","title":"绑定人","width":15},
     		  {align:"center",field:"card_level","title":"卡类型","width":10},
     		  {align:"center",field:"deadline","title":"有效日期","width":15},
     		  {align:"center",field:"card_count","title":"消费次数","width":10},
     		  {align:"center",field:"limit_card_count","title":"限制次数","width":10},
     		  {align:"center",field:"remark","title":"备注","width":15},
 		      {align:"center",field:"face_amount","title":"面值","width":10},
//     		  {align:"center",field:"discount","title":"折扣率","width":20},
     		  {align:"center",field:"company","title":"单位","width":20},	
     		  {align:"center",field:"sale_amount","title":"售卡金额","width":15},	
     		  {align:"center",field:"creater","title":"制卡人","width":10},	
     		  {align:"center",field:"create_time","title":"制卡时间","width":20},	
     		  {align:"center",field:"hair_card_statuss","title":"发卡状态","width":10},	
     		  {align:"center",field:"hair_card_creater","title":"发卡人","width":10},	
     		  {align:"center",field:"hair_card_create_time","title":"发卡时间","width":20},	
//     		  {align:"center",field:"gs","title":"挂失/恢复","width":15,"formatter":f_gs},     //"formatter":  		  
//     		  {align:"center",field:"sd","title":"锁定/恢复","width":15,"formatter":f_sd},
    		  {align:"center",field:"zf","title":"作废/恢复","width":15,"formatter":f_zf},
//   		  {align:"center",field:"xg","title":"修改","width":15,"formatter":xg_fun}
     		  {align:"center",field:"xgyxq","title":"延期","width":10,"formatter":f_xgyxq},
     		 {align:"center",field:"jcbd","title":"解除绑定","width":10,"formatter":f_jcbd}
     		  ]
     		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    	if($("#status").val() != 'Y'){
				$('td[field="zf"]').hide();
				$('td[field="xgyxq"]').hide();
			}
	    },
	    rownumbers:true,
	    singleSelect:false,
	    collapsible:true,
		pagination: true,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    toolbar:toolbar
	});
}
function xg_fun(val,row){	
	return '<a href=\"javascript:xg_yemian(\''+row.card_num+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function xg_yemian(id){
	$("#dlg-xg").dialog({
		title:'修改卡信息',
		center:'center',
		href:'updateCardPage.action?card_num='+id
	});
	$("#dlg-xg").dialog('open');
}

//批量修改页面
function plxg_yemian(cardNumList){
   	var url='updCardExamSet.action?card_num='+cardNumList;
   	newWindow = window.open(url, "批量修改", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
   	newWindow.focus();
}
function f_huifu(id){
	$.messager.confirm('提示信息','确定恢复卡信息？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
		        url:'cardInfoUpdate.action?card_id='+id+'&status=1',  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(data){
		        	  $(".loading_div").remove();
		        		$.messager.alert('提示信息', data,function(){});
		        		getGrid();
		            },
		            error:function(){
		            	$(".loading_div").remove();
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
		}
	});
}
function f_gs(val,row){
	if(row.card_type == '1'){
		if(row.status == '1'){
			return '<a style="color:#f00" href=\"javascript:f_guashi(\''+row.id+'\')\">挂失</a>';
		}else if(row.status == '3'){
			return '<a href=\"javascript:f_huifu(\''+row.id+'\')\">恢复</a>';
		}
	}
	return '';
}
function f_guashi(id){
	$.messager.confirm('提示信息','确定挂失卡信息？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
		        url:'cardInfoUpdate.action?card_id='+id+'&status=3',  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(data){
		        	  $(".loading_div").remove();
		        		$.messager.alert('提示信息', data,function(){});
		        		getGrid();
		            },
					error : function() {
						$(".loading_div").remove();
						$.messager.alert("操作提示", "操作错误", "error");					
					}
		    });
		}
	});
}
function f_sd(val,row){
	if(row.card_type == '1'){
		if(row.status == '1'){
			return '<a style="color:#f00" href=\"javascript:f_suoding(\''+row.id+'\')\">锁定</a>';
		}else if(row.status == '2'){
			return '<a href=\"javascript:f_huifu(\''+row.id+'\')\">恢复</a>';
		}
	}
	return '';
}
function f_suoding(id){
	$.messager.confirm('提示信息','确定锁定卡信息？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
		        url:'cardInfoUpdate.action?card_id='+id+'&status=2',  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(data){
		        	  $(".loading_div").remove();
		        		$.messager.alert('提示信息', data,function(){});
		        		getGrid();
		            },
		            error:function(){
		            	$(".loading_div").remove();
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
		}
	});
}

function f_zf(val,row){
//	if(row.card_type == '1'){
		if(row.status == '1'){
			return '<a style="color:#f00" href=\"javascript:f_zuofei(\''+row.id+'\')\">作废</a>';
		}else if(row.status == '4'){
			return '<a href=\"javascript:f_huifu(\''+row.id+'\')\">恢复</a>';
		}
//	}
//	return '';
}
function f_zuofei(id){
	$.messager.confirm('提示信息','确定作废卡信息？',function(r){
		if(r){
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	   	    $("body").prepend(str);
			$.ajax({
		        url:'cardInfoUpdate.action?card_id='+id+'&status=4',  
		        data:{},          
		        type: "post",//数据发送方式   
		          success: function(data){
		        	  $(".loading_div").remove();
		        		$.messager.alert('提示信息', data,function(){});
		        		getGrid();
		            },
		            error:function(){
		            	$(".loading_div").remove();
		            	$.messager.alert('提示信息', "操作失败！",function(){});
		            }  
		    });
		}
	});
}
function f_xgyxq(val,row){
	if(row.card_type == '2'){
		return '<a href=\"javascript:f_yanqi(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"延期\" /></a>';
	}
	return '';
}
function f_yanqi(id){
	$.messager.confirm('提示信息','确定延期吗？',function(r){
		if(r){
			$("#dlg-s").dialog({
	    		title:'卡延期',
	    		href:'cardinfoyanqi.action?card_id='+id
	    	});
	    	$("#dlg-s").dialog("open");
		}
	});
}
function f_jcbd(val,row){
	if(row.card_type == '1'){
		return '<a href=\"javascript:f_jcd(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"解除绑定\" /></a>';
	}
	return '';
}
function f_jcd(id){
	$.messager.confirm('提示信息','确定解除本卡绑定的会员信息吗？',function(r){
		if(r){
			$.ajax({
		        url:'removeBinding.action?card_id='+id,  
		        type: "post",//数据发送方式   
		          success: function(data){
		        		$.messager.alert('提示信息', data,'info');
		        		getGrid();
		            },
		            error:function(){
		            	$.messager.alert('提示信息', "操作失败！",'error');
		            }  
		    });
		}
	});
}
//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	getGrid();
	    }
    });
}

function reapplylispacs(){
	$.messager.alert('提示信息', "操作成功！");
}
</script>
</head>
<body>
<div style="display:none;">
	<OBJECT id=MWRFATL <s:property value="card_reader_ocx"/>></OBJECT>
	<input type="hidden" id="card_reader_code" value="<s:property value="card_reader_code"/>"/> 
	<input type="hidden" id="status" value="<s:property value="model.status"/>"/>
	<input type="hidden" id="isCardUserChoose" value="<s:property value="model.isCardUserChoose"/>"/>
</div>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:110px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width: 70px;"><input id="ck_ser_num" type="checkbox" checked="checked"/>卡号：</dt>
					<dd><input type="text" id="ser_num" onfocus="serch_cls(this)"  class="textinput"/></dd>
					<dt style="width: 90px;"><input id="ck_ser_status" type="checkbox"/>卡状态：</dt>
					<dd><select id="ser_status" onchange="serch_cls(this)"  style="width:80px;height:27px;">
						<option value="">不限</option>
						<option value="1">正常</option>
						<option value="2">锁定</option>
						<option value="3">挂失</option>
						<option value="4">作废</option>
					</select></dd>
					<%-- <dt>卡类型：</dt><dd><select id="ser_type" name="ser_type" onchange="serch_cls(this)" style="width:143px;height:27px;">
					<option value="">不限</option>
					<option value="1">记名</option>
					<option value="2">不记名</option>
					</select></dd> --%>
					<dt style="height:26px;width:90px;"><input id="ck_date" type="checkbox" checked="checked"/>制卡时间：</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
					<dt style="width: 70px;"><input id="ck_ser_level" type="checkbox"/>卡等级：</dt>
					<dd><select id="ser_level" name="ser_level" onchange="serch_cls(this)" style="width:80px;height:27px;"></select></dd>
				</dl>
				<dl>
					<dt style="width: 70px;"><input id="ck_hair_card_creater" type="checkbox"/>发卡人：</dt>
					<dd><select id="ser_hair_card_creater"  style="width:145px;height:27px;"></select></dd>
					<dt style="width: 90px;"><input id="ck_hair_card_status" type="checkbox"/>发卡状态：</dt>
					<dd><select id="ser_hair_card_status" onchange="serch_cls(this)" style="width:80px;height:27px;">
					<option value="-1">不限</option>
					<option value="0">未发卡</option>
					<option value="1">已发卡</option>
					</select></dd>
					<dt style="width: 90px;"><input id="ck_ser_company" type="checkbox"/>单位：</dt>
					<dd><input type="text" id="ser_company" onfocus="serch_cls(this)"  class="textinput" style="width: 235px;"/></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0;height:95%;">
<legend><strong>卡列表</strong></legend>
<table id="cardinfolist"> 
</table>
</fieldset>
</div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 780,height: 380,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-pl_card" class="easyui-dialog" data-options="width: 820,height: 500,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 450,height: 170,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-xg" class="easyui-dialog" data-options="width: 780,height: 380,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-fkr" class="easyui-dialog" data-options="width: 400,height: 180,closed: true,cache: false,modal: true,top:50">
	<form id="add1Form">
	<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>选择发卡人：</dt>
				<dd><select id="card_users" style="height:26px;"></select><strong class="red">*</strong></dd>
			</dl>
		</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_cardChoose();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-fkr').dialog('close')">关闭</a>
		</div>
	</div>
	</form>
</div>
</body>
</html>