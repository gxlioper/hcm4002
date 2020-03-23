<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>回访管理</title>
<style>
.main {	width: auto; height: auto;}
.left {	width: 350px;;	height: auto;}
.right { width: 72.5%; height: auto;	margin-left: 10px;}
.left, .right {	float: left;}
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
<s:if test="visit_leader">
	$('#screater').combobox({
		url : 'getflowUser.action?creater_state=v',
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
</s:if>
<s:else>
	$('#screater').combobox({
	 	data:[{
	 		id:'<s:property value="#session.username.userid"/>',username:'自己'
	 	}],
	    valueField:'id',    
	    textField:'username',
	    editable:false,
	    onLoadSuccess : function(data) {
	    	$("#ck_screater").attr("checked", true);
			$("#ck_screater").attr("disabled", "disabled");
			$('#screater').combobox("select", data[0].id);
		}
	});
</s:else>
	
	getStatisticsData();
	chaxun();
	chaxun_visited();
	//回车查询事件
	$("#exam_num_v").keydown(function(event){
		event=document.all?window.event:event;
		if((event.keyCode || event.which)==13){
			chaxun();
		}
    });
	$("#exam_num_v_visited").keydown(function(event){
		event=document.all?window.event:event;
		if((event.keyCode || event.which)==13){
			chaxun_visited();
		}
    });
	
	$('#visit_type_visited').combobox({
		data:[{
	 		id:'5',value:'电话'
	 	},{
	 		id:'6',value:'排查'
	 	}],
		valueField : 'id',
		textField : 'value',
	    onLoadSuccess : function(data) {
			$('#visit_type_visited').combobox('setValue', data[0].id);
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
	$('#vipflag_visited').combobox({
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
			$('#vipflag_visited').combobox('setValue', data[0].id);
	}
	});
	$('#visitedtype').combobox({
	 	data:[{
	 		id:'0',value:'所有'
	 	},{
	 		id:'1',value:'未回访'
	 	},{
	 		id:'2',value:'已回访'
	 	}],
	    valueField:'id',    
	    textField:'value',
	    onLoadSuccess : function(data) {
				$('#visitedtype').combobox('setValue', data[0].id);
		}
	});
	
	$('#vtcreater').combobox({
		url : 'getDatadis.action?com_Type=USERS&flow_code=v',
		editable : false, //不可编辑状态
		cache : false,
		panelHeight : '200',//自动高度适合
		valueField : 'id',
		textField : 'name',
		onLoadSuccess : function(data) {
			if (data.length>0) {
				$('#vtcreater').combobox('setValue', data[0].id);
			}
		}
	});
	$('#exam_num_v').css('ime-mode','disabled');
	$('#exam_num_v').focus();
	$('#exam_num_v_visited').css('ime-mode','disabled');
	$('#exam_num_v_visited').focus();
});

</script>
</head>
<body >
<div id="tts" class="easyui-tabs"  style="width:100%; height: 520px;" data-options="fit:true,border:false,plain:true">
	<div title="待回访" style="padding:5px;" >
		<div  class="easyui-layout" style="height: 99%">
			<div data-options="region:'west',title:'单人信息',split:true" style="width:300px;">
				<fieldset style="margin:5px;padding-right:0;">
		    		<legend><strong>基本信息</strong></legend>
					<div class="user-query" style="font-weight: bold;font-size: 15px">
						<input type="hidden" id="exam_num"/>
						<input type="hidden" id="arch_num"/>
						<dl><dt style="width:80px;">姓<font style="color:white;">占位</font>名：</dt><dt id="username" style="width:60px;"/><dt id="sex" style="width:50px;"/><dt id="age" style="width:40px;" class="autoWidth"/></dl>
						<dl><dt style="width:80px;">电<font style="color:white;">占位</font>话：</dt><dt id="userphone"></dt></dl>
						<dl><dt style="width:80px;">会员级别：</dt><dt id="uservipflag"></dt></dl>
						<dl><dt style="width:80px;">体检日期：</dt><dt id="join_date"></dt></dl>
						<dl><dt style="width:80px;">领取方式：</dt><dt id="receive_type"></dt></dl>
						<dl><dt style="width:80px;">体检类型：</dt><dt id="exam_type"></dt></dl>
						<dl><dt style="width:80px;">单<font style="color:white;">占位</font>位：</dt><dt id="company" style="width: 230px;"></dt></dl>
						<dl><dt></dt><dt><a id="visit_btn" href="javascript:addVisit();" class="easyui-linkbutton"  style="width: 90px;display: none">回访</a></dt></dl>
					</div>
				</fieldset>
				<fieldset style="margin:5px;padding-right:0;" id = "jsxx_id">
					<legend><strong>流程日志</strong></legend>
					<table id="flowloglist" ></table>	
				</fieldset>
			</div>
			<div data-options="region:'center',title:'人员列表'" style="padding:5px;">
				<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 70px; margin-right: 10px" >
					<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dt style="height:26px;width:120px;"><input id="ck_exam_num_v" class="ck_exam_num_v" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
							<dd><input type="text" style="width:95px;ime-mode: disabled;" id="exam_num_v" class="textinput"/></dd>
							<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>查询日期</dt>
							<dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
							至<input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
							<dt style="height:26px;width:100px;"><input id="ck_phone" class="ck_phone" type="checkbox"/>手机号：</dt>
							<dd><input type="text" style="width:100px" id="phone" class="textinput"/></dd>
							</dl>
						<dl>
							<dt style="height:26px;width:120px;"><input id="ck_vipflag" type="checkbox"/>会员标识</dt>
							<dd><input class="easyui-combobox" id="vipflag"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 100px;" /></dd>
							<dt style="height:26px;width:80px;"><input id="ck_username" class="ck_username"  type="checkbox"/>姓名：</dt>
							<dd><input type="text" style="width:215px" id="name" class="textinput"/></dd>
							<s:if test="visit_leader">
								<dt style="height:26px;width:100px;"><input id="ck_vtcreater" type="checkbox"/>分配回访人</dt>
	                    		<dd><select class="easyui-combobox" id="vtcreater" data-options="height:26,width:105,panelHeight:'auto'"></select></dd>
	                    	</s:if>
	                    
							<dd>
								<a href="javascript:chaxun();" class="easyui-linkbutton c6 l-btn l-btn-small" style="width: 90px">查询</a>
								<a href="javascript:allot_random();" class="easyui-linkbutton" style="width: 90px">随机分配</a>
							</dd>
						</dl>
					</div>
		 		</fieldset>
		 		<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 50px; height: 720px; margin-right: 5px" >
					<legend><strong>信息显示</strong></legend>
					<div style="margin:5px;padding-right:0; padding-top: 3px; padding-bottom: 3px; height: 7px; margin-right: 3px">
						<font>今日应回访共<span id="all"></span>人，已回访<span id="visited"></span>人，未回访<span id="notVisit"></span>人
						（含贵宾<span id="notVisitVIP"></span>人）</font>
					</div>
					<div id="tt" class="easyui-tabs"  style="width: 905px; height: 720px;" data-options="fit:true,border:false,plain:true">
	        			<div title="人员信息" style="padding:5px;" >
							<table id="flowexampListv" ></table>
	        			</div>
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
       				<div title="危机值" style="padding:5px;">
       					<table id="exam_circl">
	      				</table>
       				</div>
	       			</div>
		        </fieldset> 			
			</div>
		</div>
	</div>
	<div title="已回访" style="padding:5px;">
		<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 80px; margin-right: 10px" >
		 	<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="height:26px;width:120px;"><input id="ck_exam_num_v_visited" class="ck_exam_num_v_visited" type="checkbox"/><s:text name="tjhname"/>(<s:text name="dahname"/>)：</dt>
					<dd><input type="text" style="width:100px;ime-mode: disabled;" id="exam_num_v_visited" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date_visited" type="checkbox" checked="checked"/>查询日期</dt>
					<dd><input class="easyui-datebox" type=text id="time1_visited" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
					至<input class="easyui-datebox" type=text id="time2_visited" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
					<dt style="height:26px;width:80px;"><input id="ck_vipflag_visited" type="checkbox"/>会员标识</dt>
					<dd><input class="easyui-combobox" id="vipflag_visited"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 120px;" /></dd>
					<dt style="height:26px;width:80px;"><input id="ck_username_visited" class="ck_username_visited" type="checkbox"/>姓名：</dt>
					<dd><input type="text" style="width:100px" id="username_visited" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_visitedtype" type="checkbox"/>回访状态</dt>	
                    <dd><select class="easyui-combobox" id="visitedtype" name="visitedtype" data-options="height:26,width:105"></select></dd>
				</dl>
				<dl>
					<dt style="height:26px;width:120px;"><input id="ck_phone_visited" class="ck_phone_visited" type="checkbox"/>手机号：</dt>
					<dd><input type="text" style="width:100px" id="phone_visited" class="textinput"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date_visited2" type="checkbox"/>回访日期</dt>
					<dd><input class="easyui-datebox" type=text id="time3_visited" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
					至<input class="easyui-datebox" type=text id="time4_visited" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
					<dt style="height:26px;width:80px;"><input id="ck_visit_type" type="checkbox"/>回访方式</dt>
					<dd><input class="easyui-combobox" id="visit_type_visited"  data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 120px;" /></dd>
					<dt style="height:26px;width:80px;"><input id="ck_screater" type="checkbox"/>查询人员</dt>	
                    <dd><select class="easyui-combobox" id="screater" name="screater" data-options="height:26,width:105"></select></dd>
                    					
					
					 
					<dd><a href="javascript:chaxun_visited();" class="easyui-linkbutton c6 l-btn l-btn-small"  style="width: 90px">查询</a></dd>
				</dl>
			</div>
		</fieldset>
		<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: auto; margin-right: 10px" >
			<legend><strong>信息显示</strong></legend>
		   	<table id="flowexampListv_visited" ></table>		
		</fieldset>
    </div>
</div>
<div id="allot_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 500,height: 300,closed: true,cache: false,modal: true,top:50"></div>
<div id="visit_dlg" style="padding-top:50px;" class="easyui-dialog"  data-options="width: 800,height: 600,closed: true,cache: false,modal: true,top:50"></div>
<input type="hidden" id="visit_leader" value="<s:property value='model.visit_leader'/>"/>
<input type="hidden" id="today" value="<s:property value="model.time1"/>"/>
<input type="hidden" id="uname" value="<s:property value="#session.username.name"/>"/>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/examflow/flow_v_show.js"></script>