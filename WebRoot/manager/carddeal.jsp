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
<title>交易记录</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
$(document).ready(function () {
	//$('#deal_date').datebox({ readonly:false });
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();

	getGrid();
});

function getGrid(){
	var model = {"card_num":$("#ser_num").val(),"deal_type":$("#ser_type").val(),"creater":$("#creater").val(),
	"deal_date":$("#deal_date").datebox('getValue'),"user_name":$("#userName").val(),"exam_num":$("#exam_num").val()};
	  $("#cardinfolist").datagrid({
		url: '<%=request.getContextPath()%>/getCardDealList.action',
		queryParams: model,
		rownumbers:true,
     pageSize: 15,//每页显示的记录条数，默认为10 
     pageList:[15,30,45,60,75],//可以设置每页记录条数的列表 
     toolbar: '#toolbar',
     sortName: 'cdate',
		sortOrder: 'desc',
    // height:390,
     columns:[[{align:"center",field:"card_num","title":"卡号","width":20},
     		  {align:'center',field:"deal_type_y","title":"交易类型","width":10},
     		  {align:"center",field:"amount","title":"交易金额","width":10},
     		  {align:"center",field:"deal_date","title":"交易日期","width":15},
     		  {align:"center",field:"creater","title":"操作人","width":15},
     		  {align:"center",field:"card_count","title":"消费次数","width":12},
     		  {align:"center",field:"user_name","title":"消费人","width":10},
     		  {align:"center",field:"exam_num","title":"体检号","width":15},
     		  {align:"center",field:"remark","title":"备注","width":20}
     		  ]
     		  ],
	    onLoadSuccess:function(value){
	    	$("#datatotal").val(value.total);
	    },
	    singleSelect:true,
	    collapsible:true,
		 pagination: true,
     fitColumns:true,
     fit:true,
     striped:true,
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
</script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div  data-options="region:'north'" style="height:84px;">    
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd>卡号：&nbsp;&nbsp;<input type="text" maxlength="20" id="ser_num" name="ser_num" onfocus="serch_cls(this)"  class="textinput"/></dd>
					<dd>体检号：&nbsp;&nbsp;<input type="text" maxlength="20" id="exam_num" name="exam_num" onfocus="serch_cls(this)"  class="textinput"/></dd>
					<dd>消费人：&nbsp;&nbsp;<input maxlength="10" type="text" id="userName" name="user_name" onfocus="serch_cls(this)" class="textinput"/></dd>
					<dd>交易类型：&nbsp;&nbsp;<select id="ser_type" name="ser_type" onchange="serch_cls(this)" style="width:143px;height:27px;">
					<option value="">不限</option>
					<option value="1">恢复</option>
					<option value="2">锁定</option>
					<option value="3">挂失</option>
					<option value="4">作废</option>
					<option value="5">充值</option>
					<option value="6">消费</option>
					<option value="7">退费</option>
					</select></dd>
					<dd>操作人：&nbsp;&nbsp;<input maxlength="20" type="text" id="creater" name="creater" onfocus="serch_cls(this)" class="textinput"/></dd>
					<dd>操作日期：&nbsp;&nbsp;<input type="text" id="deal_date" name="deal_date" class="easyui-datebox" /></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:getGrid();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
</div>
<div  data-options="region:'center'">    
<fieldset style="margin:5px;padding-right:0;height:95%;">
<legend><strong>交易记录列表</strong></legend>
<table id="cardinfolist"> 
</table>
</fieldset>
</div>
</div>
</body>
</html>