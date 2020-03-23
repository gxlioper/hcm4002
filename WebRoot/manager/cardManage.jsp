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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wk_rederCard.js"></script>
<script type="text/javascript">
var toolbar = [{
    text:'新增会员',
    iconCls:'icon-add',
    handler:function(){
    	$("#dlg-edit").dialog({
    		title:'新增会员',
    		href:'cardMemberEdit.action'
    	});
    	$("#dlg-edit").dialog("open");
    }
}];
$(document).ready(function () {
	var str = '<div class="loading_div" style=" position:fixed; background-image:url(../../images/bg_tm.png); left:0;top:0; overflow:hidden; width:100%; height:100%; display: block; z-index: 89999; ">' + '<div style="width:70px;  height:75px; margin-top:-38px; position:absolute; top:50%; left:50%; margin-left:-35px;"><img src="../../images/loading.gif" width="70" height="75" /></div></div>';
	    $("body").prepend(str);
	$.ajax({
		url:'getDatadis.action?com_Type=HYDJ',
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
	getGrid();
});

function getGrid(){
	var model = {"user_name":$("#ser_name").val(),"id_num":$("#ser_id_num").val(),"phone":$("#ser_phone").val(),"level":$("#ser_level").val()};
	   $("#cardmemerlist").datagrid({
		url: '<%=request.getContextPath()%>/cardMemberList.action',
		queryParams: model,
		rownumbers:true,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     toolbar: '#toolbar',
     sortName: 'cdate',
		sortOrder: 'desc',
     //height:390,
     columns:[[{align:"center",field:"arch_num","title":"档案号","width":15},
               {align:"center",field:"user_name","title":"姓名","width":15},
     		  {align:'center',field:"id_num","title":"身份证号","width":25},
     		  {align:"center",field:"phone","title":"电话","width":15},
     		  {align:"center",field:"sex","title":"性别","width":10},
     		  {align:"center",field:"birthday","title":"生日","width":15},
     		  {align:"center",field:"email","title":"Email","width":15},
     		  {align:"center",field:"address","title":"地址","width":20},
     		  {align:"center",field:"level","title":"会员等级","width":15},
     		  {align:"center",field:"integral","title":"会员积分","width":15},
     		 {align:"center",field:"totalamt","title":"累计消费金额","width":15},
     		{align:"center",field:"totaltimes","title":"累计体检次数","width":15},
     		{align:"center",field:"prelevel","title":"上次会员级别","width":15},
     		{align:"center",field:"preintegral","title":"上次会员积分","width":15},
     		{align:"center",field:"leveltime","title":"上次级别变动时间","width":15},
     		{align:"center",field:"integeraltime","title":"上次积分变动时间","width":15},
     		  {align:"center",field:"card","title":"卡信息","width":15,"formatter":f_ck},     //"formatter":  		  
     		  {align:"center",field:"xg","title":"修改","width":10,"formatter":f_xg}
     		 ]// {align:"center",field:"sc","title":"删除","width":10}
     		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		 pagination: true,
     fitColumns:true,
     striped:true,
     fit:true,
     toolbar:toolbar
	});
}
function f_xg(val,row){	
	return '<a href=\"javascript:f_xgcardmember(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"修改\" /></a>';
}
function f_xgcardmember(id){
		$("#dlg-edit").dialog({
			title:'修改会员',
			href:'getCardMemberOne.action?id='+id
		});
		$("#dlg-edit").dialog('open');
}
function f_ck(val,row){	
	return '<a href=\"javascript:f_ckcardinfo(\''+row.id+'\')\"><img src=\"themes/default/images/blank.gif\" class=\"icon ico-edit\" alt=\"查看卡片\" /></a>';
}
function f_ckcardinfo(id){
	$("#dlg-s").dialog({
		title:'会员卡信息',
		href:'cardmerkxx.action?id='+id
	});
	$("#dlg-s").dialog('open');
}
//回车查询
function serch_cls(dom){
	$(dom).unbind("keydown").keydown(function (e) {
	    if (e.which == 13) {
	    	$('#cardmemerlist').datagrid('load',{
	    		"user_name":$("#ser_name").val(),
	    		"id_num":$("#ser_id_num").val(),
	    		"phone":$("#ser_phone").val(),
	    		"level":$("#ser_level").val()
	    	});


	    }
    });
}
</script>
</head>
<body>
<div style="display:none;">
	<OBJECT id=MWRFATL <s:property value="card_reader_ocx"/>></OBJECT>
	<input type="hidden" id="card_reader_code" value="<s:property value="card_reader_code"/>"/>
</div>
<!-- <div id="cont-page" class="include-page" > -->
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:84px;">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>姓名：&nbsp;&nbsp;<input maxlength="20" type="text" id="ser_name" name="ser_name" onfocus="serch_cls(this);"  class="textinput"/></dd>
					<dd>身份证号：&nbsp;&nbsp;<input  maxlength="18" type="text" id="ser_id_num" name="ser_id_num" onfocus="serch_cls(this);"  class="textinput"/></dd>
					<dd>电话：&nbsp;&nbsp;<input maxlength="11" type="text" id="ser_phone" name="ser_phone" onfocus="serch_cls(this);"  class="textinput"/></dd>
					<dd>会员等级：&nbsp;&nbsp;<select id="ser_level" name="ser_level" style="width:143px;height:27px;" onchange="serch_cls(this);"></select></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 </div>
 <div  data-options="region:'center'">
<fieldset style="margin:5px;padding-right:0; height:95%;">
<legend><strong>会员列表</strong></legend>
<table id="cardmemerlist"> 
</table>
</fieldset>
</div>
</div>
<!-- </div> -->
<div id="dlg-edit" class="easyui-dialog" data-options="width: 780,height: 450,closed: true,cache: false,modal: true,top:50"></div>
<div id="card-edit" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
<div id="dlg-s" class="easyui-dialog" data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>