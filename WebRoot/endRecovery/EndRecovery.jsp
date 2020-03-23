<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> 结束回收</title>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 350px;	height: auto;}
#right {width: 72.5%;height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:15px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
</style>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/endRecovery/EndRecovery.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	getwjxmGrid();
	gethfqxGrid();
	getcustomerInfo();
	//回车查询事件
	 $("#exam_num_s").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	chaxun();
		    	if($("#isDjdAutoRecover").val()=="Y"){
					receiveExamInfo();//回收导检单
				}
		    }
    }); 
	//回车查询事件
	
	$('#getReportWay').combobox({
		data:[{
	 		id:'1',value:'邮寄'
	 	},{
	 		id:'2',value:'自取'
	 	},{
	 		id:'3',value:'电子报告'
	 	},{
	 		id:'0',value:''
	 	}],
		valueField : 'id',
		textField : 'value',
	    onLoadSuccess : function(data) {
			$('#getReportWay').combobox('setValue', 0);
		},
		onSelect:function(record) {
			if(record.id == '1') {
				$("#reportAddressLine").show();
			} else {
				$("#reportAddressLine").hide();
			}
			if(record.id == '3'){
				$("#email").show();
			}else{
				$("#email").hide();
			}
			$.ajax({
				url : 'upd_getReportWay.action',
				data : {
					exam_num : $("#exam_num").val(),
					getReportWay : record.id,
				},
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					if (text.split("-")[0] == 'ok' ) {
						$.messager.alert("操作提示", "保存成功");
					} else {
						$.messager.alert("操作提示", "操作错误", "error");
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");
				}
			});
		}
	});
	$('#exam_num_s').css('ime-mode','disabled');
	$('#exam_num_s').focus();
});

 //操作列
	function f_cz(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:yanqi(\''+row.id+'\')\">延期</a>'
		 		+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deleteExam(\''+row.id+'\',\''+row.sample_status+'\')\">放弃</a>'
		 return str;
	}
 	//延期时间
 	function yanqi(rowid){
 		$("#yq").dialog({
			title:'请选择延期时间',
			width : 300,
			height: 200,
			href:'getyanqi.action?ids='+rowid
		});
 		$("#yq").dialog('open');
 	}
	//恢复弃项
	function f_hf(val,row){
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:recover(\''+row.id+'\')\">恢复</a>'
		 return str;
	}
	//未检项目列表
function getwjxmGrid(){
		 var model={"exam_num":$("#exam_num_s").val()};
	     $("#wjxm").datagrid({
		 url:'getWjxmExamList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:toolbar,
		 rownumbers:true,
		 columns:[[
		    {align:'center',field:'ck',checkbox:true},
		    {align:'center',field:'caozuo',title:'操作',width:250,"formatter":f_cz},
		    {align:'center',field:'item_code',title:'项目编码',width:100},
		 	{align:'center',field:'item_name',title:'项目名称',width:400},
		 	{align:'center',field:'dep_name',title:'科室名称',width:300},
		 	{align:'center',field:'exam_statuss',title:'状态',width:80},
		 	{align:'center',field:'sample_statuss',title:'采样状态',width:80},
		 	{align:'center',field:'create_time',title:'最晚检查日期',width:140}	
		 	]],	    	
		pagination: false,
	    striped:true,
	    fit:true,
		 nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			fitColumns:false,
			//fit:false,
	    onLoadSuccess:function(data){
	    	$("#exam_num_s").select();
			$("#exam_num_s").focus();
	    },
	    rowStyler: function(index,row){
			if (row.isActive == 'yes'){
				return 'color:#13e406;';
			}
		}

	});
}
	//弃项列表
function gethfqxGrid(){
		 var model={"exam_num":$("#exam_num_s").val()};
	     $("#hfqx").datagrid({
		 url:'getHfqxExamList.action',
		 dataType: 'json',
		 queryParams:model,
		 toolbar:too,
		 rownumbers:true,
	     pageSize: 15,//每页显示的记录条数，默认为10 
	     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
		 columns:[[
		    {align:'center',field:'ck',checkbox:true},
		    {align:'center',field:'caozuo',title:'操作',width:250,"formatter":f_hf},
		    {align:'center',field:'item_code',title:'项目编码',width:100},
		 	{align:'center',field:'item_name',title:'项目名称',width:400},
		 	{align:'center',field:'dep_name',title:'科室名称',width:300},
		 	]],	    	
	   pagination: false,
	    striped:true,
	    fit:true,
		 nowrap:false,
			rownumbers:true,
	    	singleSelect:false,
		    collapsible:true,
			fitColumns:false,
			//fit:false,
	    onLoadSuccess:function(data){
	    	$("#exam_num_s").select();
			$("#exam_num_s").focus();
	    }
	});
}
var	toolbar=[{
		text:'批量放弃',
		iconCls: 'icon-edit',
		handler: function(){
			var ids = new Array();
	    	var checkedItems = $('#wjxm').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		        ids.push(item.id);
		    });
		    if(ids.length == 0){
		    	$.messager.alert("提示","请选择需要放弃的项目!","error");
		    	return;
		    }
		    deluserrow(ids.toString());
		}
	},'-',{
		text:'批量延期',
		iconCls: 'icon-help',
		handler: function(){
			var ids = new Array();
	    	var checkedItems = $('#wjxm').datagrid('getChecked');
		    $.each(checkedItems, function(index, item){
		        ids.push(item.id);
		    });
		    if(ids.length == 0){
		    	$.messager.alert("提示","请选择需要延期的项目!","error");
		    	return;
		    }
			$("#yq").dialog({
				title:'请选择延期时间',
				width : 300,
				height: 200,
				href:'getyanqi.action?ids='+ids,
			});
	 		$("#yq").dialog('open');
	    }
	}]
var too=[{
	text:'批量恢复',
	iconCls: 'icon-edit',
	handler: function(){
		var ids = new Array();
    	var checkedItems = $('#hfqx').datagrid('getChecked');
	    $.each(checkedItems, function(index, item){
	        ids.push(item.id);
	    });
	    if(ids.length == 0){
	    	$.messager.alert("提示","请选择需要恢复的项目!","error");
	    	return;
	    }
	    plhf(ids.toString());
	}
}]
function plhf(ids){
	$.ajax({
		url:'plhf.action',
		data:{ids:ids},
		success:function(data){
			$.messager.alert("操作提示",data);
			getwjxmGrid();
			gethfqxGrid();
		},
		error:function(){
			$.messager.alert("警告","操作失败","error");
		}
	})
}
/**
 * 批量放弃
 */
function deluserrow(ids){
	$.ajax({
		url:'updateBatchDeletExamInfo.action',
		data:{ids:ids},
		success:function(data){
			$.messager.alert("操作提示",data);
			getwjxmGrid();
			gethfqxGrid();
		},
		error:function(){
			$.messager.alert("警告","操作失败","error");
		}
	})
}
/**
 * 回收导引单
 */
 function receiveExamInfo(){
	 if($('#is_guide_back').text()!="已回收"){
		  $.ajax({
				url:'receiveExamInfo.action?exam_num='+$('#exam_num').val(),
				success:function(data){
					/*$.messager.show({
						title:'提示信息',
						ok:"确定",
						msg:data,
						timeout:500,
						showType:'fade',
						style:{
							right:'',
							bottom:'',
							
						}
					});*/
					getcustomerInfo();
				},
				error:function(){
					$.messager.alert("提示","操作失败","error");
				}
			})
	  }else{
//		  $.messager.alert("提示信息","导引单已回收，不能重复操作","error");
//		  //alert("导引单已回收，不能重复操作");
		  return;
	  }
}
/**
 * 撤销导引单
 */
function huishou(){
	if($('#is_guide_back').text()=="已回收"){
		$.ajax({
			url:'revocationDYD.action?exam_num='+$('#exam_num').val(),
			type:'post',
			success:function(data){
				$.messager.show({
					title:'提示信息',
					ok:"确定",
					msg:data,
					timeout:1000,
					showType:'fade',
					style:{
						right:'',
						bottom:'',
						
					}
				});
				getcustomerInfo();
			},
			error:function(){
// 				$.messager.alert("警告信息","操作失败","error");
			}
		})
	}else{
		return;
	}
}

	function edit_reportAddress() {
		$("#reportAddress").attr("readonly", false);
		$("#reportAddress").css("border", "1px solid #BBB");
	}
	function edit_emailAddress() {
		$("#emailAddress").attr("readonly", false);
		$("#emailAddress").css("border", "1px solid  red ");
	}
	function save_reportAddress() {
		$("#reportAddress").attr("readonly", "readonly");
		$("#reportAddress").css("border", "0px solid white");
		$.ajax({
			url : 'upd_reportAddress.action',
			data : {
				exam_num : $("#exam_num").val(),
				reportAddress : $("#reportAddress").val(),
			},
			type : "post",//数据发送方式   
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok' ) {
					$.messager.alert("操作提示", "保存成功");
				} else {
					$.messager.alert("操作提示", "操作错误", "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");
			}
		});
	}
	function save_emailAddress() {
		$("#emailAddress").attr("readonly", "readonly");
		$("#emailAddress").css("border", "0px solid white");
		$.ajax({
			url : 'upd_emailAddress.action',
			data : {
				exam_num : $("#exam_num").val(),
				email : $("#emailAddress").val(),
			},
			type : "post",//数据发送方式   
			success : function(text) {
				$(".loading_div").remove();
				if (text.split("-")[0] == 'ok' ) {
					$.messager.alert("操作提示", "保存成功");
				} else {
					$.messager.alert("操作提示", "操作错误", "error");
				}
			},
			error : function() {
				$(".loading_div").remove();
				$.messager.alert("操作提示", "操作错误", "error");
			}
		});
	}
	//无需总检
	function shifouzongjian(){
		if($("#wuxuzongjian").html()==""){
	  		 $("#zongjian").html("总检");
   		  $.ajax({
   				url : 'guidewuxuzongjian.action',
   				data : {
   					exam_num : $("#exam_num").val()
   				},
   				type : "post",//数据发送方式   
   				success : function(text) {
   					$(".loading_div").remove();
   					if (text.split("-")[0] == 'ok' ) {
   						$.messager.alert("操作提示", text.split("-")[2]);
   					} else {
   						$.messager.alert("操作提示", "操作错误", "error");
   					}
   				},
   				error : function() {
   					$(".loading_div").remove();
   					$.messager.alert("操作提示", "操作错误", "error");
   				}
   			}
   		  );  
   		  getcustomerInfo();
	  }else{
		  $("#zongjian").html("无需总检");
		  $.ajax({
				url : 'guidexuyaozongjian.action',
				data : {
					exam_num : $("#exam_num").val()
				},
				type : "post",//数据发送方式   
				success : function(text) {
					$(".loading_div").remove();
					if (text.split("-")[0] == 'ok' ) {
						$.messager.alert("操作提示", text.split("-")[2]);
					} else {
						$.messager.alert("操作提示", "操作错误", "error");
					}
				},
				error : function() {
					$(".loading_div").remove();
					$.messager.alert("操作提示", "操作错误", "error");
				}
			}); 
		  getcustomerInfo();
	  }
}
</script>
</head>
<body >

<input type="hidden" id = 'IS_SAMPLING_DEL' value="<s:property value="model.order_id"/>">
<input type="hidden" id = 'isDjdAutoRecover' value="<s:property value="model.isDjdAutoRecover"/>">
  <div id="ssywjlistshow" class="easyui-layout" fit="true" style="padding: 5px;">
	<div data-options="region:'west'" style="width: 22%;">
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl><dt>姓名：<input type="hidden" name="exam_num" id="exam_num"/></dt><dt id="user_name"></dt></dl>
						<dl><dt>性别：</dt><dt id="sex"></dt></dl>
						<dl><dt>年龄：</dt><dt id="age"></dt></dl>
						<dl><dt>单位：</dt><dt id="company"></dt></dl>
						<dl><dt>人员类型：</dt><dt id="customer_type"></dt></dl>
						<dl><dt>体检套餐：</dt><dt id="set_name" style="width: 180px;" ></dt></dl>
						<dl><dt>导引单状态：</dt><dt id="is_guide_back" ></dt></dl>
						
						<dl id="getReportWayLine"><dt style="width:80px;">是否邮寄：</dt><dt><select class="easyui-combobox" 
							id="getReportWay" data-options="height:26,width:80,panelHeight:'auto'"></select></dt></dl>
						<dl id="reportAddressLine"><dt style="width:80px;">邮寄地址：</dt><dt style="width: 220px;"><input id="reportAddress" style="width: 200px;border:0 solid white;"
							readonly="readonly" ondblclick="edit_reportAddress();" onblur="save_reportAddress();"/></dt></dl>
						<dl id="email"><dt style="width:80px;">邮箱地址：</dt><dt style="width: 220px;"><input id="emailAddress"  style="width: 200px;border:0 solid white;"
						 readonly="readonly" ondblclick="edit_emailAddress();" onblur="save_emailAddress();"/></dt></dl>
						<dl><dt style="width:80px;">提<font style="color:white;">占位</font>示：</dt><dt id="wuxuzongjian" 
							style="color: red"></dt><dt><button id="zongjian" onclick="shifouzongjian()"/></dt></dl>
					</div>
			</fieldset>
			<fieldset id="teshuxiangmu" style="margin:5px;padding-right:0;display:none;">
    			<legend><strong>特殊项目</strong></legend>
					<div id="teshu_div" class="user-query">
						<dl><dt>腰围：</dt><dt>24.5</dt></dl>
					</div>
			</fieldset>
	</div>
	
   <!--右边面版  -->
		    <div data-options="region:'center',fit:true">
  			<!--上  -->
  			<div class="easyui-layout" data-options="fit:true,border:false">
		    <div data-options="region:'north',border:false" style="height: 90px;">
		    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 50px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:140px;ime-mode: disabled;" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
							<dd><a href="javascript:chaxun();" class="easyui-linkbutton"  style="width: 90px">查询</a></dd>
							<dd><a href="javascript:receiveExamInfo();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">回收导引单</a></dd>
							<dd><a href="javascript:createImage();" class="easyui-linkbutton"   style="width: 90px">扫描导引单</a></dd>
							<dd><a href="javascript:huishou();" class="easyui-linkbutton"   style="width: 90px">撤销导引单</a></dd>
							<!--<dd><a href="javascript:xuanzereporttype();" class="easyui-linkbutton c6"   style="width: 110px">选择报告类型</a></dd>-->
						</dl>
					</div>
 			</fieldset>
		    </div>
		    <div data-options="region:'center',border:false">
  				<!-- 选项卡 -->
		       <div id="tst" class="easyui-tabs" data-options="fit:true,border:false">
		      		<div title="未检项目" >   
		            		<table id="wjxm" ></table>
		        	</div>        	
		        	<div title="弃检项目"> 
	   					    <table id="hfqx" ></table>
		 			</div>
		 			<div title="导检单" style="padding:5px;">
       					<div id="disease_tijianbaogao"  style="vertical-align: middle; text-align: center;"></div>
       				</div>
		      </div>
			</div>
			</div>
		  </div>
	</div>
<div id="yq" class="fromDiv" style="padding-top: 50px;padding-left: 50px;z-index:-1;"  ></div>
<div id="dlg-report" class="easyui-dialog"  data-options="width: 400,height: 180,closed: true,cache: false,modal: true,title:'报告类型'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 60px;" >报告类型：</dd>
				<dd><select style="width:150px;height:26px;" id="report_type" class="easyui-combobox" data-options="panelHeight:'auto'">
					<option value="0">纸质报告</option>
					<option value="1">电子报告</option>
				</select><strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_report_type();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-report').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>