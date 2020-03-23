<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title> 结束回收</title>
<style>
.main {	width: auto;	height: auto;}
.left {	width: 350px;	height: auto;}
.right {	width: 72.5%;	height: auto;	margin-left: 10px;}
.left, .right {	float: left;}
.left1 {	width: 20%;	height: auto;}
.right1 {	width: 78%;	height: auto;	margin-left: 10px;}
.left1, .right1 {	float: left;}
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_e_show.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	getPre_receive();
	getPre_receive_details();
	
	getexamallxmGrid();
	shangchuanchaxun();
	if($("#isshowxx").val()=='Y'){
		getflowLoglist();
	}
	//回车查询事件
	 $("#exam_num_s").keydown(function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	chaxun();
	    }
    });

	 $('#screater').combobox({
			url : 'getflowUser.action?creater_state=e1',
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
	 $('#vipflag').combobox({
		 	data:[{
		 		id:'1',value:'普通人员'
		 	},{
		 		id:'2',value:'一般vip'
		 	},{
		 		id:'3',value:'特级vip'
		 	}],
		    valueField:'id',    
		    textField:'value',
		    onLoadSuccess : function(data) {
				$('#vipflag').combobox('setValue', data[0].id);
		}
	});
	 $('#exam_num_s').css('ime-mode','disabled');
	 $('#exam_num_s').focus();
});

 	
	//项目列表
function getexamallxmGrid(){
		 var model={"exam_num":$("#exam_num_s").val(),"types":2};
	     $("#wjxm").datagrid({
		 url:'flowEndRecoveryexam.action',
		 dataType: 'json',
		 queryParams:model,
		 rownumbers:true,
		 columns:[[
		    {field:'ck',checkbox:true },
		    {align:'center',field:'item_code',title:'项目编码',width:12},
		 	{align:'center',field:'dep_name',title:'科室名称',width:12},
		 	{align:'center',field:'item_name',title:'项目名称',width:15},
		 	{align:'center',field:'exam_statuss',title:'状态',width:12},
		 	{align:'center',field:'sample_statuss',title:'采样状态',width:12},
		 	{align:'center',field:'create_time',title:'最晚检查日期',width:15}	
		 	]],	    	
	    collapsible:true,
		pagination: false,
	    fitColumns:true,
	    striped:true,
	    fit:true,
	    onLoadSuccess:function(data){
	    	$("#exam_num_s").select();
			$("#exam_num_s").focus();
	    },
	    rowStyler: function(index,row){
//			if (row.exam_status == 'Y'){
//				return 'color:blue;';
//			}else if (row.exam_status == 'G'){
//				return 'color:red;';
//			}else if (row.exam_status == 'D'){
//				return 'color:green;';
//			}
			if(row.dep_num == 'DR' || row.dep_num == "CT" || row.dep_num == 'MR'){
				return 'color:red;';
			}
		}

	});
}

/**
 * 回收导引单
 */
 function receiveExamInfo(){

	  $.ajax({
			url:'flowe0ExamInfo.action?exam_num='+$('#exam_num').val(),
			success:function(text){
				if($("#isshowxx").val()=='Y'){
					getflowLoglist();
				}
				if (text.split("-")[0] == 'ok') {
					alert_autoClose("操作提示", text.split("-")[1],"");
					$("#exam_num_s").select();
				} else {
					//$.messager.alert("错误信息",text,"error");
					alert_autoClose("错误信息", text,"error");
					$("#exam_num_s").select();
				}					
			},
			error:function(){
				$.messager.alert("提示","操作失败","error");
			}
		})
}
/**
 * 撤销导引单
 */
function huishou(){
		$.ajax({
			url:'flowe0ExamInfoun.action?exam_num='+$('#exam_num').val(),
			type:'post',
			success:function(text){				
				getflowLoglist();
				if (text.split("-")[0] == 'ok') {
					alert_autoClose("操作提示", text.split("-")[1],"");
					$("#exam_num_s").select();
				} else {
					$.messager.alert("错误信息",text,"error");
					$("#exam_num_s").select();
				}
			},
			error:function(){
				$.messager.alert("警告信息","操作失败","error");
			}
		})	
}

function liuchenbeizhuPage(){
	$.ajax({
		url:'getNumExamInfo.action?exam_num='+$('#exam_num_s').val(),
		type:'post',
		success:function(data){
			if(data==1){
				$("#dlg-beizhu").dialog({
					title : '流程备注',
					width :800,
					height :500,
					href : 'remakPage.action?exam_num='+$('#exam_num_s').val()+"&process=6"//整单科室
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

</script>
</head>
<body >
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0' <s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code" value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="report_print_type" value="<s:property value="model.report_print_type"/>">
	<input type="hidden"   id='isshowxx' value='<s:property value="model.reportAddress" />' />
	<div id="tts" class="easyui-tabs"  style="width:100%; height: 520px;" data-options="fit:true,border:false,plain:true">
	<div title="报告批量核收" style="padding:5px;" >
  <div class="main">
		<div class="left1">
			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 80px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
				<div class="user-query">
					<dl>
					<!-- 
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>查询日期</dt>
					 -->
			         <dd><input class="easyui-datebox" type=text id="time1_batch" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                                              至<input class="easyui-datebox" type=text id="time2_batch" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
					</dl>
					<dl>
					<dd><a href="javascript:getPre_receive();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
					</dl>
				</div>
 			</fieldset>
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>待核收</strong></legend>
				<table  id="pre_receive" ></table>
			</fieldset>
	</div>
   <!--右边面版  -->
		 <div class="right1">
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 520px; margin-right: 10px" >
    			<legend><strong>详细信息</strong></legend>							
				<table  id="pre_receive_details" ></table>
 			</fieldset> 			
		    </div>
		    
	</div>
	</div>
	<div title="报告单独接收" style="padding:5px;" >
  <div class="main">
		<div class="left">	
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query" style="font-weight: bold;font-size: 15px">
					<input type="hidden" name="exam_num" id="exam_num"/></dt>
						<dl><dt style="width:80px;">姓<font style="color:white;">占位</font>名：</dt><dt id="user_name"/><dt id="sex" style="width:60px;"/><dt id="age" style="width:60px;" class="autoWidth"/></dl>
						<dl><dt style="width:80px;">电<font style="color:white;">占位</font>话：</dt><dt id="userphone"></dt></dl>
						<dl><dt style="width:80px;">会员级别：</dt><dt id="uservipflag"></dt></dl>
						<dl><dt style="width:80px;">体检日期：</dt><dt id="join_date"></dt></dl>
						<dl><dt style="width:80px;">单<font style="color:white;">占位</font>位：</dt><dt id="company" style="width: 230px;"></dt></dl>
						<dl><dt style="width:80px;">体检类型：</dt><dt id="exam_type"></dt></dl>
						<dl><dt style="width:80px;">是否邮寄：</dt><dt id="getReportWay"></dt></dl>
						<dl id="reportAddressLine" style="width:80px;"><dt>邮寄地址：</dt><dt id="reportAddress"></dt></dl>
						<dl><dt style="width:80px;">提<font style="color:white;">占位</font>示：</dt><dt id="wuxuzongjian" style="color: red" class="autoWidth"></dt><dt  id="zongjian" class="autoWidth"></dt></dl>
					</div>
			</fieldset>
			<s:if test='model.reportAddress=="Y"'>
			 <fieldset style="margin:5px;padding-right:0;" id = "jsxx_id">
<legend><strong>流程日志</strong></legend>
      <table  id="flowloglist" ></table>	
 </fieldset>
 </s:if>
	</div>
   <!--右边面版  -->
		 <div class="right">
  			<!--上  -->
		    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:140px;ime-mode: disabled;" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
							<dd><a href="javascript:chaxun();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">接收报告</a></dd>
							<dd><a href="javascript:huishou();" class="easyui-linkbutton"   style="width: 90px">取消接收</a></dd>
							<dd><a href="javascript:yulanreport();" class="easyui-linkbutton"   style="width: 90px">预览报告</a></dd>
							<dd><a href="javascript:liuchenbeizhuPage();" class="easyui-linkbutton"   style="width: 90px">备注</a></dd>
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 520px; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>							
					<div id="tt" class="easyui-tabs"  style="width: 905px; height: 520px;" data-options="fit:true,border:false,plain:true">
        			<div title="项目列表" style="padding:5px;" >
        				<table id="wjxm" ></table>		
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
       				</s:if>
       				<div title="导检单" style="padding:5px;">
       					<div id="disease_tijianbaogao"  style="vertical-align: middle; text-align: center;"></div>
       				</div>
       			</div>	
 			</fieldset> 			
		    </div>
		    
	</div>
	</div>

	<div title="报告确认领取"  id="bgqrlq" value="报告确认领取"  style="padding:5px;">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 110px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_com_Name" type="checkbox"/>单位：</dt>
					<dd style="height:26px;width:200;">
					  <input type="text" class="textinput" id="com_Name" value="" style="width:210px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-50px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:80px;"><input id="ck_dep_id" type="checkbox"/>部门：</dt>
					<dd style="height:26px;width:140;">
					  <select class="easyui-combobox" id="levelss" name="levelss"
										data-options="height:26,width:140,panelHeight:'auto'"></select>
		    	   <dt style="height:26px;width:80px;"><input id="ck_ser_receive_date" type="checkbox"/>领取日期</dt>
					<dd><input style="width:100px;height:26px" type="text" id="ser_receive_date" class="easyui-datebox" /></dd>
					<dt style="height:26px;width:80px;"><input id="ck_vipflag" type="checkbox"/>会员标识</dt>
					<dd><input class="easyui-combobox" id="vipflag"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
					<dt style="height:26px;width:80px;"><input id="ck_report_class" class="ck_report_class"  type="checkbox"/>报告等级</dt>
					<dd><input type="text" style="height:26px;width:95px" id="report_class" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_screater" type="checkbox"/>查询人员</dt>	
                    <dd><select class="easyui-combobox" id="screater" name="screater" data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
		    	    	</dl>
						<dl>
							<dt style="height:26px;width:80px;"><input id="ck_exam_num_p" class="ck_exam_num_p" type="checkbox"/>快速查询</dt>
							<dd><input type="text" style="height:26px;width:185px" id="exam_num_p" name="exam_num_p"  class="textinput"/></dd>
							<dd><a href="javascript:void(0)" onClick="djtreadcard_sfz()"><img style="height:20px;width:20px;" title="身份证获取人员信息" src="<%=request.getContextPath()%>/themes/default/images/shengfencod.png" /></a></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_ptype" type="checkbox"/>上传状态</dt>
					<dd><select class="easyui-combobox" id="ptype" name="ptype" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="1" selected>未上传</option>
		    	       		<option  value="2">已上传</option>
		    	       		<option  value="0">全部</option>
		    	      </select>
		    	  	</dd>
					 <dt style="height:26px;width:80px;"><input id="ck_pre_getReportWay" type="checkbox"/>发送方式</dt>
					<dd><select class="easyui-combobox" id="pre_getReportWay" name="pre_getReportWay" data-options="height:26,width:100,panelHeight:'auto'">		    	       		
		    	       		<option value="1" selected>待邮寄</option>
		    	       		<option value="2">待自取</option>
		    	      </select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_ser_receive_type" type="checkbox"/>领取方式</dt>
					<dd><select class="easyui-combobox" id="ser_receive_type"
					data-options="height:26,width:100,panelHeight:'auto',editable:false">
						<option value="">全部</option>
						<option value="1&2">已领取</option>
						<option value="0">未领取</option>
						<option value="1">已邮寄</option>
						<option value="2">已自取</option>
					</select></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_ser_receive_name" type="checkbox"/>领取人：</dt>
					<dd><input type="text" style="height:26px;width:100px" id="ser_receive_name" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_usersex" type="checkbox"/>性别：</dt>
					<dd><select class="easyui-combobox" id="usersex" data-options="height:26,width:100,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="M">男</option>
		    	       		<option  value="F">女</option>
		    	      </select></dd>
                    	</dl>
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>查询日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                                              至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
					<dt style="height:26px;width:80px;"><input id="ck_tstz" type="checkbox"/>特殊通知</dt>
					<dd><select class="easyui-combobox" id="tstz" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="" selected>不限</option>
		    	       		<option  value="0">否</option>
		    	       		<option  value="1">是</option>
		    	      </select></dd>
		    	      <dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type" data-options="height:26,width:100,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="T">团体体检</option>
		    	       		<option  value="G">个人体检</option>
		    	      </select></dd>
						<dd><a href="javascript:shangchuanchaxun();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
						<dd><a href="javascript:quanliuchengchaxun();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 110px">全局查询</a></dd>
					</dl>
					<dl>
					</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        				<table id="flowexamlist" ></table>		
 			</fieldset> 
    </div>
</div>
<div id="dlg-edit" class="easyui-dialog" data-options="width: 780,height: 400,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-edit-thems" class="easyui-dialog" data-options="width: 780,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 600,height: 350,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
</body>
</html>