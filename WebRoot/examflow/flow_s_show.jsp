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
#right {	width: 72.5%;	height: auto;	margin-left: 10px;}
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
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%> "></script>

<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	getexamallxmGrid();
	shangchuanchaxun();
	baogaokeshangchuan();
	getflowLoglist();
	//回车查询事件
	 $("#exam_num_s").keydown(function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	chaxun();
		    }
    }); 
	 $('#screater').combobox({
			url : 'getflowUser.action?creater_state=s1',
			editable : true, //不可编辑状态
			cache : false,
			panelMinHeight :'100',//自动高度适合
			panelMaxHeight:'300',
			valueField : 'id',
			textField : 'username',
			onLoadSuccess : function(data) {
				if (data.length>0) {
					$('#screater').combobox('setValue', data[0].id);
				}
			}
		});
	 $('#exam_num_s').css('ime-mode','disabled');
	 $('#exam_num_s').focus();
});

//接收报告  可上传
  function jieshoukeshangchuan(){
		if ($('#exam_num').val() <= 0) {
			$.messager.alert("提示", "无效信息", "error");
		} else {
			  
			    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
						+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				     $("body").prepend(str);
			    	 $.ajax({
							url : 'jieshoukeshangchuan.action',
							data : {
								exam_num : $("#exam_num").val(),
							},
							type : "post",//数据发送方式   
							success : function(text) {
								$(".loading_div").remove();
								// && text.split("-")[1] == $("#exam_num_s").val()
								if (text.split("-")[0] == 'ok' ) {
									$.messager.alert("操作提示", text.split("-")[2]);
									$("#exam_num_s").select();
								} else {
									$.messager.alert("操作提示", "操作错误", "error");
									$("#exam_num_s").select();
								}
							},
							error : function() {
								$(".loading_div").remove();
								$.messager.alert("操作提示", "操作错误", "error");
							}
						});   
		 }

	}
	//取消报告上传
  function quxiaoshanngchuan(){
		if ($('#exam_num').val() <= 0) {
			$.messager.alert("提示", "无效信息", "error");
		} else {
			  
			    	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
						+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
				     $("body").prepend(str);
			    	 $.ajax({
							url : 'quxiaoshangchuan.action',
							data : {
								exam_num : $("#exam_num").val(),
								
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
			  
			
		 }

	}

	//项目列表
	function getexamallxmGrid() {
		var model = {
			"exam_num" : $("#exam_num_s").val()
		};
		$("#wjxm").datagrid({
			url : 'flowEndRecoveryexam.action',
			dataType : 'json',
			queryParams : model,
			rownumbers : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				align : 'center',
				field : 'item_code',
				title : '项目编码',
				width : 12
			}, {
				align : 'center',
				field : 'dep_name',
				title : '科室名称',
				width : 12
			}, {
				align : 'center',
				field : 'item_name',
				title : '项目名称',
				width : 15
			}, {
				align : 'center',
				field : 'exam_statuss',
				title : '状态',
				width : 12
			}, {
				align : 'center',
				field : 'sample_statuss',
				title : '采样状态',
				width : 12
			}, {
				align : 'center',
				field : 'create_time',
				title : '最晚检查日期',
				width : 15
			} ] ],
			collapsible : true,
			pagination : false,
			fitColumns : true,
			striped : true,
			fit : true,
			onLoadSuccess : function(data) {
				$("#exam_num_s").select();
				$("#exam_num_s").focus();
			},
			rowStyler : function(index, row) {
				if (row.exam_status == 'Y') {
					return 'color:blue;';
				} else if (row.exam_status == 'G') {
					return 'color:red;';
				} else if (row.exam_status == 'D') {
					return 'color:green;';
				}
			}
		});
	}

	/**
	 * 回收导引单
	 */
	function receiveExamInfo() {
		if ($('#exam_num').val() > 0) {
			$.ajax({
				url : 'flows0ExamInfo.action?exam_num=' + $('#exam_num').val(),
				success : function(text) {
					getflowLoglist();
					if (text.split("-")[0] == 'ok') {
						alert_autoClose("操作提示", text.split("-")[1], "");
						$("#exam_num_s").select();
					} else {
						//$.messager.alert("错误信息",text,"error");
						alert_autoClose("错误信息", text, "error");
						$("#exam_num_s").select();
					}
				},
				error : function() {
					$.messager.alert("提示", "操作失败", "error");
				}
			})
		}
	}
	/**
	 * 撤销导引单
	 */
	function huishou() {
		$.ajax({
			url : 'flows0ExamInfoun.action?exam_num=' + $('#exam_num').val(),
			type : 'post',
			success : function(text) {
				getflowLoglist();
				if (text.split("-")[0] == 'ok') {
					alert_autoClose("操作提示", text.split("-")[1], "");
					$("#exam_num_s").select();
				} else {
					$.messager.alert("错误信息", text, "error");
					$("#exam_num_s").select();
				}
			},
			error : function() {
				$.messager.alert("警告信息", "操作失败", "error");
			}
		})
	}

	function getlispacs() {
		if ($("#exam_num_s").val() != '') {
			var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">'
					+ '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
			$("body").prepend(str);
			$
					.ajax({
						url : 'getlispacsres.action?exam_num='
								+ $('#exam_num_s').val(),
						type : 'post',
						success : function(text) {
							$(".loading_div").remove();
							getcustomerInfo();
							if (text.split("-")[0] == 'ok') {
								alert_autoClose("操作提示", text.split("-")[1], "");
								$("#exam_num_s").select();
							} else {
								$.messager.alert("错误信息", text, "error");
								$("#exam_num_s").select();
							}
						},
						error : function() {
							$(".loading_div").remove();
							$.messager.alert("警告信息", "操作失败", "error");
						}
					})
		} else {
			alert_autoClose("操作提示", "无效体检编号", "error");
		}
	}
	function liuchenbeizhuPage() {
		$.ajax({
			url : 'getNumExamInfo.action?exam_num=' + $('#exam_num_s').val(),
			type : 'post',
			success : function(data) {
				if (data == 1) {
					$("#dlg-beizhu").dialog(
							{
								title : '流程备注',
								width : 800,
								height : 500,
								href : 'remakPage.action?exam_num='
										+ $('#exam_num_s').val() + "&process=3"//整单科室
							});
					$("#dlg-beizhu").dialog('open');
				} else {
					$.messager.alert("提示信息", tjhname + "无效", "error");
					$('#exam_num_s').focus();
					return;
				}
			},
			error : function() {
				$.messager.alert("提示信息", "操作失败", "error");
			}
		})

	}
	function liuchenbeizhuPage2(row) {
		$.ajax({
			url : 'getNumExamInfo.action?exam_num=' + row.exam_num
					+ "&dep_id=1",
			type : 'post',
			success : function(data) {
				if (data == 1) {
					$("#dlg-beizhu").dialog(
							{
								title : '流程备注',
								width : 800,
								height : 500,
								href : 'remakPage.action?exam_num='
										+ row.exam_num + "&process=2"
							});
					$("#dlg-beizhu").dialog('open');
				} else {
					$.messager.alert("提示信息", tjhname + "无效", "error");
					$('#exam_num_s').focus();
					return;
				}
			},
			error : function() {
				$.messager.alert("提示信息", "操作失败", "error");
			}
		})

	}
</script>
</head>
<body>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<div id="tts" class="easyui-tabs"  style="width:100%; height: 520px;" data-options="fit:true,border:false,plain:true">
<div title="报告接收" style="padding:5px;" >
  <div id="main">
		<div id="left">	
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query" style="font-weight: bold;font-size: 15px">
					<input type="hidden" name="exam_num" id="exam_num"/>
						<dl><dt style="width:80px;">姓<font style="color:white;">占位</font>名：</dt><dt id="user_name"/><dt id="sex" style="width:60px;"/><dt id="age" style="width:60px;" class="autoWidth"/></dl>
						<dl><dt style="width:80px;">电<font style="color:white;">占位</font>话：</dt><dt id="userphone"></dt></dl>
						<dl><dt style="width:80px;">会员级别：</dt><dt id="uservipflag"></dt></dl>
						<dl><dt style="width:80px;">体检日期：</dt><dt id="join_date"></dt></dl>
						<dl><dt style="width:80px;">单<font style="color:white;">占位</font>位：</dt><dt id="company" style="width: 230px;"></dt></dl>
						<dl><dt style="width:80px;">体检类型：</dt><dt id="exam_type"></dt></dl>
						<dl><dt style="width:80px;">是否邮寄：</dt><dt id="getReportWay"></dt></dl>
						<dl id="reportAddressLine"><dt style="width:80px;">邮寄地址：</dt><dt id="reportAddress"></dt></dl>
						<dl><dt style="width:80px;">提<font style="color:white;">占位</font>示：</dt><dt id="wuxuzongjian" style="color: red" class="autoWidth"></dt><dt  id="zongjian" class="autoWidth"></dt></dl>
						<!-- dl><dt>导引单状态：</dt><dt id="is_guide_back" ></dt></dl-->
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
		    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:140px;ime-mode: disabled;" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
							<dd><a href="javascript:chaxun();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">接收导引单</a></dd>
							<dd><a href="javascript:huishou();" class="easyui-linkbutton"   style="width: 90px">取消接收</a></dd>							
							<dd><a href="javascript:getlispacs();" class="easyui-linkbutton"   style="width: 140px">获取检查检验结果</a></dd>
							<dd><a href="javascript:liuchenbeizhuPage();" class="easyui-linkbutton"   style="width:80px">备注</a></dd>
							<dd><a href="javascript:jieshoukeshangchuan();" class="easyui-linkbutton c6" id="ksc" style="width: 90px">可上传</a></dd>
        					<dd><a href="javascript:quxiaoshanngchuan();" class="easyui-linkbutton"   style="width: 90px">取消上传</a></dd>	
							<!-- 
							<dd><a href="javascript:danganreport();" class="easyui-linkbutton"   style="width: 120px">胶片档案打印</a></dd>
							 -->
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 520px; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>							
					<div id="tt" class="easyui-tabs"  style="width: 905px; height: 520px;" data-options="fit:true,border:false,plain:true">
        			<div title="项目列表" style="padding:5px;" >
        				<table id="wjxm" ></table>		
        				</div>
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
       			</div>	
 			</fieldset> 			
		    </div>
		    
	</div>
	</div>
<!-- 导检单分发 -->
	<div title="报告上传" style="padding:5px;">
	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 110px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_com_Name" type="checkbox"/>单位：</dt>
					<dd style="height:26px;width:140;">
					  <input type="text" class="textinput" id="com_Name" value="" style="width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-50px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					<dt style="height:26px;width:80px;"><input id="ck_tjrw" type="checkbox"/>体检任务</dt>
					<dd>
						<input  class="easyui-combobox" type="text" id="tjrw" style="height:26px;width:140px;"/>
						  <div id="batch_list_div" style="display:none;margin-left:200px;" 
						      onmouseover="select_batchcom_list_mover()" 
						      onmouseout="select_batchcom_list_amover()">
	                      </div>
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_group_id" type="checkbox"/>分组：</dt>
					<dd><select class="easyui-combobox" id="group_id" name="group_id"
							data-options="valueField:'id', textField:'group_name',height:26,width:220,panelHeight:'auto'"></select>
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_set_id" type="checkbox"/>套餐：</dt>
					<dd><select class="easyui-combobox" id="set_id" name="set_id"	data-options="height:26,width:220,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_exam_type" type="checkbox"/>体检类型</dt>
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type" data-options="height:26,width:140,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="T">团体体检</option>
		    	       		<option  value="G">个人体检</option>
		    	      </select></dd>
		    	    	</dl>
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
							<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>查询日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                                              至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_username" class="ck_username" type="checkbox"/>姓名：</dt>
					<dd><input type="text" style="width:215px" id="username" class="textinput"/></dd>
		    	  	<dt style="height:26px;width:80px;"><input id="ck_screater" type="checkbox"/>查询人员</dt>	
                    <dd><select class="easyui-combobox" id="screater" name="screater" data-options="height:26,width:140"></select></dd>
                   		</dl>
						<dl>
					<dt style="height:26px;width:80px;"><input id="ck_usersex" type="checkbox"/>性别：</dt>
					<dd>
						<select class="easyui-combobox" id="usersex" data-options="height:26,width:145,panelHeight:'auto'">		    	       		
		    	       		<option  value="A" selected>不限</option>
		    	       		<option  value="M">男</option>
		    	       		<option  value="F">女</option>
		    	      	</select>
		    	     </dd>
					<dd><a href="javascript:shangchuanchaxun();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
					<dd><a href="javascript:baogaokeshangchuan();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">可上传</a></dd>
					
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>
        				<table id="flowexamlist" ></table>		
        				
 			</fieldset> 
    </div>
	</div>
<div id="transfer_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 500,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<input type="hidden" id="flow_code" value="s"/>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_s_show.js"></script>