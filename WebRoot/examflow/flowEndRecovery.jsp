<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flowEndRecovery.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	getwjxmGrid();
	gethfqxGrid();
	if($("#isshowxx").val()=='Y'){
		shangchuanchaxun();
	}else{
		$("#tts").tabs('close',1);
	}
	getflowLoglist();
	getcustomerInfo();
	//回车查询事件
	 $("#exam_num_s").keydown(function(event){
		event=document.all?window.event:event;
		if((event.keyCode || event.which)==13){
			chaxun();
		}
    }); 
	
	 $('#screater').combobox({
		url : 'getflowUser.action?creater_state=h1',
		editable : true, //不可编辑状态
		cache : false,
		panelMinHeight : '100',//自动高度适合
		panelMaxHeight:'300',
		valueField : 'id',
		textField : 'username',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#screater').combobox('setValue', data[0].id);
			}
		}
	});
	 
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
	$('#exam_num_p').css('ime-mode','disabled');
	$('#exam_num_p').focus();
}); 


 	

/**
 * 回收导引单
 */
 function receiveExamInfo(){
  if($('#exam_num').val() != '' && $('#exam_num').val() != null){
		  $.ajax({
				url:'flowh0ExamInfo.action',
				data : {
  					exam_num : $("#exam_num").val()
  				},
				success:function(text){
					getflowLoglist();
					if (text.split("-")[0] == 'ok') {
						alert_autoClose("操作提示", text.split("-")[1],"");
						$("#exam_num_s").select();
					} else {
						//alert_autoClose("错误信息", text,"error");
						//$.messager.alert("错误信息",text,"error");
						$("#exam_num_s").select();
					}					
				},
				error:function(){
					$.messager.alert("提示","操作失败","error");
				}
			})
	 	
  }
}
/**
 * 撤销导引单
 */
function chexiao(){
		$.ajax({
			url:'flowrevocationDYD.action?exam_num='+$('#exam_num').val(),
			type:'post',
			success:function(text){	
				getflowLoglist();
				getcustomerInfo();
				shangchuanchaxun();
				if (text.split("-")[0] == 'ok') {					
					alert_autoClose("操作提示", text.split("-")[1],"");
					$("#exam_num_s").select();
				} else {
					alert_autoClose("操作提示", text, "error");
					$("#exam_num_s").select();
				}
			},
			error:function(){
				$.messager.alert("警告信息","操作失败","error");
			}
		})
}


function getlisStatus(){
	if ($("#exam_num_s").val() != '') {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
					+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$
					.ajax({
						url : 'getlisStatusres.action?exam_num='
								+ $('#exam_num_s').val(),
						type : 'post',
						success : function(text) {
							$(".loading_div").remove();
							getcustomerInfo();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", text.split("-")[1], "");
								$("#exam_num_s").select();
							} else {
								$.messager.alert("错误信息",text,"error");
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("警告信息", "操作失败", "error");
						}
					})
		} else {
			alert_autoClose("操作提示", "无效"+tjhname, "error");
		}
	}

function liuchenbeizhuPage(){
		$.ajax({
			url:'getNumExamInfo.action?exam_num='+$('#exam_num_s').val()+"&dep_id=1",
			type:'post',
			success:function(data){
				if(data==1){
					$("#dlg-beizhu").dialog({
						title : '流程备注',
						width :800,
						height :500,
						href : 'remakPage.action?exam_num='+$('#exam_num_s').val()+"&process=2"
					});
					$("#dlg-beizhu").dialog('open');  
				} else {
					$.messager.alert("提示信息",tjhname+"无效","error");
					$('#exam_num_s').focus();
					return;
				}
			},
			error:function(){
				$.messager.alert("提示信息","操作失败","error");
			}
		})
		
}
function liuchenbeizhuPage2(row){
		$.ajax({
			url:'getNumExamInfo.action?exam_num='+row.exam_num+"&dep_id=1",
			type:'post',
			success:function(data){
				if(data==1){
					$("#dlg-beizhu").dialog({
						title : '流程备注',
						width :800,
						height :500,
						href : 'remakPage.action?exam_num='+row.exam_num+"&process=2"
					});
					$("#dlg-beizhu").dialog('open');  
				} else {
					$.messager.alert("提示信息",tjhname+"无效","error");
					$('#exam_num_s').focus();
					return;
				}
			},
			error:function(){
				$.messager.alert("提示信息","操作失败","error");
			}
		})
		
}


//导引单核收  是否总检

	
	function shifouzongjian(){
	       if($("#exam_num").val()>0){
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
	      
	       }else{
	    	   $.messager.alert("操作提示", "无效信息", "error");
	       }
	}
	
	function edit_reportAddress() {
		$("#reportAddress").attr("readonly", false);
		$("#reportAddress").css("border", "1px solid #BBB");
	}
	function edit_emailAddress() {
		$("#emailAddress").attr("readonly", false);
		$("#emailAddress").css("border", "1px solid  black ");
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
function createImage(){
	if($("#exam_num_s").val() != null && $("#exam_num_s").val() != ""){
		var url="createDJDImage.action?exam_num="+$("#exam_num_s").val();
	   newwin = window.open("", "电子档", "toolbar=no,location=no,fullscreen=yes,scrollbars=yes");
	   newwin.moveTo(0,0);
	   newwin.resizeTo(screen.width,screen.height - 30);
	   newwin.location = url;
	   newwin.focus();
	}else{
		$.messager.alert("操作提示", "请输入体检号", "info");
	}
}

//操作列
function f_cz(val,row){
	 var str='';
	 if (row.exam_status=='D'){
		 str='<a href=\"javascript:recover(\''+row.id+'\')\">取消延期</a>';
	 }else  if (row.exam_status=='N')	{
		 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:yanqi(\''+row.id+'\')\">延期</a>'
	 		+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + '<a href=\"javascript:deleteExam(\''+row.id+'\',\''+row.sample_status+'\')\">放弃</a>';
	 }
	 		
	 return str;
}
	
 	function yanqi(rowid) {
		//判断是否弹出延期时间
		var yanqitime=$("#is_show_yanqitime").val();
		if(yanqitime=='Y'){
			$("#yq").dialog({
				title:'请选择延期时间',
				width : 300,
				height: 200,
				href:'getyanqi.action?ids='+rowid
			});
				$("#yq").dialog('open');
		}else{
			//延期操作
	 		$.ajax({
	 			url:'flowupdateyanqi.action',
	 			type:'post',
	 			data:{
	 			  id:rowid,
	 			 exam_num:$('#exam_num').val(),
	 			},
	 			success:function(text){
	 				if (text.split("-")[0] == 'ok') {
	 					getwjxmGrid();//未检项目
	 				} else {
	 					$.messager.alert("错误信息",text,"error");
	 				}
	 			},
	 			error:function(){
	 				$.messager.alert("警告","操作失败","error");
	 			}
	 		})
		}

 	}
//恢复弃项
function f_hf(val,row){
	 var str = '&nbsp;&nbsp;&nbsp;<a href=\"javascript:plhf(\''+row.id+'\')\">恢复</a>'
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
	  //判断是否弹出延期时间
	  
		var yanqitime=$("#is_show_yanqitime").val();
		if(yanqitime=='Y'){
			$("#yq").dialog({
				title:'请选择延期时间',
				width : 300,
				height: 200,
				href:'getyanqi.action?ids='+ids,
			});
				$("#yq").dialog('open');
		}else{
			//延期操作
	 		$.ajax({
	 			url:'updateyanqi.action?ids='+ids+'&date='+'',
	 			type:'post',
	 			success:function(data){
	 				getwjxmGrid();
	 			},
	 			error:function(){
	 				$.messager.alert("警告","操作失败","error");
	 			}
	 		})
		}

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
</script>
</head>
<body >
<input type="hidden"   id='tjhname'  value='<s:text name="tjhname"  />'  />
<input type="hidden"   id='dahname' value='<s:text name="dahname"  />'  />
<input type="hidden"   id='isshowxx' value='<s:property value="model.reportAddress" />' />
<input type="hidden"   id='upload_flow' value='<s:text name="upload_flow"  />'  />
<input type="hidden"   id='is_show_yanqitime' value='<s:property value="model.is_show_yanqitime" />' />
	<div id="tts" class="easyui-tabs"  style="width:100%; height: 520px;" data-options="fit:true,border:false,plain:true">
	<div title="导检单核收" style="padding:5px;" >
  <div id="main">
		<div id="left">
	
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query" style="font-weight: bold;font-size: 15px">
					<input type="hidden" name="exam_num" id="exam_num"/></dt>
						<dl><dt style="width:80px;">姓<font style="color:white;">占位</font>名：</dt><dt id="user_name"/><dt id="sex" style="width:60px;"/><dt id="age" style="width:60px;" class="autoWidth"/></dl>
						<dl><dt style="width:80px;">电<font style="color:white;">占位</font>话：</dt><dt id="userphone"></dt></dl>
						<dl><dt style="width:80px;">会员级别：</dt><dt id="uservipflag"></dt></dl>
						<dl><dt style="width:80px;">体检日期：</dt><dt id="join_date"></dt></dl>
						<dl><dt style="width:80px;">单<font style="color:white;">占位</font>位：</dt><dt id="company" style="width: 230px;"></dt></dl>
						<dl id="getReportWayLine"><dt style="width:80px;">是否邮寄：</dt><dt><select class="easyui-combobox" 
							id="getReportWay" data-options="height:26,width:80,panelHeight:'auto'"></select></dt></dl>
						<dl id="reportAddressLine"><dt style="width:80px;">邮寄地址：</dt><dt style="width: 230px;"><input id="reportAddress" style="width: 200px;border:0 solid white;"
							readonly="readonly" ondblclick="edit_reportAddress();" onblur="save_reportAddress();"/></dt></dl>
						<dl id="email"><dt style="width:80px;">邮箱地址：</dt><dt style="width: 230px;"><input id="emailAddress" style="width: 200px;border:0 solid white;"
						 readonly="readonly" ondblclick="edit_emailAddress();" onblur="save_emailAddress();"/></dt></dl>
							
						<dl><dt style="width:80px;">体检类型：</dt><dt id="exam_type"></dt></dl>
							
						<dl><dt style="width:80px;">提<font style="color:white;">占位</font>示：</dt><dt id="wuxuzongjian" 
							style="color: red"></dt><dt><button id="zongjian" onclick="shifouzongjian()"/></dt></dl>
					</div>
			</fieldset>
			<fieldset style="margin:5px;padding-right:0;" id = "jsxx_id">
<legend><strong>流程日志</strong></legend>
      <table  id="flowloglist" ></table>	
 </fieldset>
	</div>
   <!--右边面版  -->
		 <div id="right">
  			<!--上  -->
		    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 50px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:140px;ime-mode: disabled;" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
							<dd><a href="javascript:chaxun();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">回收导引单</a></dd>
							<dd><a href="javascript:chexiao();" class="easyui-linkbutton"   style="width: 90px">撤销导引单</a></dd>
							<dd><a href="javascript:getlisStatus();" class="easyui-linkbutton"   style="width: 120px">获取检验状态</a></dd>
							<dd><a href="javascript:liuchenbeizhuPage();" class="easyui-linkbutton"   style="width:80px">备注</a></dd>
							<dd id = "upload"><a href="javascript:examh1insert1();" class="easyui-linkbutton c6"   style="width:100px">上传整单</a></dd>	
							<dd><a href="javascript:createImage();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">电子存档</a></dd>
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 520px; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>						
					<div id="tt" class="easyui-tabs"  style="width: 905px; height: 520px;" data-options="fit:true,border:false,plain:true">
		      		<div title="未检项目" >   
		            		<table id="wjxm" ></table>
		        	</div>        	
		        	<div title="弃检项目"> 
	   					    <table id="hfqx" ></table>
		 			</div>
        		<s:if test='model.reportAddress=="Y"'>
       				<div title="检查细项" style="padding:5px;">
       					<table id="item_result"></table>
       				</div>
       				<div title="总检结论" style="padding:5px;" >
        				<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 100%;font-size:14px;" id="zongjianjielun"></textarea>
        			</div>
       				<div title="总检建议" style="padding:5px;">
       					<table id="exam_disease">
	      				</table>
       				</div>
       				<div title="导检单" style="padding:5px;">
       					<div id="disease_tijianbaogao"  style="vertical-align: middle; text-align: center;"></div>
       				</div>
       			</s:if>
       			</div>	
 			</fieldset> 			
		    </div>
		    
	</div>
	</div>
<!-- 导检单分发 -->
	<div title="导检单上传" style="padding:5px;">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 100px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
					<dt style="height:26px;width:110px;"><input id="ck_exam_num_p" class="ck_exam_num_p" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
					<dd><input type="text" style="width:115px;ime-mode: disabled;" id="exam_num_p" name="exam_num_p"  class="textinput"/></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_ptype" type="checkbox"/>上传状态</dt>
					<dd><select class="easyui-combobox" id="ptype" name="ptype" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="1" selected>未上传</option>
		    	       		<option  value="2">已上传</option>
		    	       		<option  value="0">全部</option>
		    	      </select>
		    	  	</dd>
					<dt style="height:26px;width:80px;"><input id="ck_username" class="ck_username" type="checkbox"/>姓名：</dt>
					<dd><input type="text" style="width:100px" id="username" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>查询日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                                              至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_screater" type="checkbox"/>查询人员</dt>	
                    <dd><select class="easyui-combobox" id="screater" name="screater" data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
						</dl>
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_com_Name" type="checkbox"/>单位：</dt>
					<dd style="height:26px;width:140;">
					  <input type="text" class="textinput" id="com_Name" value="" style="width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-50px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                   <dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select class="easyui-combobox" id="exam_typeh" name="exam_typeh" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="T">团体体检</option>
		    	       		<option  value="G">个人体检</option>
                     </select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_usersex" type="checkbox"/>性别：</dt>
					<dd><select class="easyui-combobox" id="usersex" data-options="height:26,width:100,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="M">男</option>
		    	       		<option  value="F">女</option>
		    	      </select></dd>
					<dd><a href="javascript:shangchuanchaxun();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        				<table id="flowexamlist" ></table>		
 			</fieldset> 
    </div>
	</div>
<div id="yq" class="fromDiv" style="padding-top: 50px;padding-left: 50px;z-index:-1;"></div>
<div id="dlg-message" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
</body>
</html>