<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/padLogin/ipad/ipadcustomeritemaddshow.js?randomId=<%=Math.random()%>"></script>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 45%;	height: auto;}
#right {	width: 45%;	height: auto;	margin-left: 10px;}
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

.datagrid-wrap {
	width: 100% !important;
}

.datagrid-view {
	width: 100% !important;
}

.datagrid-view1 {
	width: 100% !important;
}

.datagrid-view2 {
	width: 100% !important;
}

.datagrid-header {
	width: 100% !important;
	border-top: 0px solid #34acd8 !important;
	font-weight: bold !important;
}
.datagrid-header .datagrid-cell span {
   font-weight: bold !important;
}
.datagrid-header-inner {
	width: 100% !important;
	background: #fff !important;
}

.datagrid-header-row.td {
	width: 10% !important;
}

@media screen and (max-width: 600px) {
	table td:before {
		content: attr(data-label);
		float: left;
		text-transform: uppercase;
		font-weight: bold;
	}
}

.panel datagrid easyui-fluid {
	width: 100% !important;
}

.datagrid-cell datagrid-cell-c10-item_code {
	width: 10% !important;
}

.datagrid-cell datagrid-cell-c10-item_category {
	width: 10% !important;
}

.datagrid-cell datagrid-cell-c10-item_name {
	width: 10% !important;
}

.datagrid-cell datagrid-cell-c10-dep_name {
	width: 10% !important;
}

.datagrid-cell datagrid-cell-c10-sex {
	width: 10% !important;
}

.datagrid-cell datagrid-cell-c10-item_amount {
	width: 10% !important;
}

span {
	color: #000 !important;
	font-size: 15px !important;
	word-wrap: break-word !important;
	height: auto !important;
}

#changitemlist>table {
	font-size: 16px;
	text-align: center;
	margin: 0 auto;
	border-collapse: separate;
	border-spacing: 0;
	border: 2px #000;
}

table thead tr, table tbody tr {
	height: 50px;
	line-height: 50px;
	/*background-color: pink;*/
}

table tr th:first-child, table tr td:first-child { /*设置table左边边框*/
	border-left: 1px solid #eaeaea;
}

table tr th:last-child, table tr td:last-child { /*设置table右边边框*/
	border-right: 1px solid #eaeaea;
}

table tr td:first-child, table tr td:nth-child(2), table tr td:nth-child(3),
	table tr td:last-child { /*设置table表格每列底部边框*/
	border-bottom: 1px solid #eaeaea;
}

}
table tr th {
	background: #eaeaea;
}

table tr:first-child th:first-child {
	border-top-left-radius: 12px;
}

table tr:first-child th:last-child {
	border-top-right-radius: 12px;
}

table tr:last-child td:first-child {
	border-bottom-left-radius: 12px;
}

table tr:last-child td:last-child {
	border-bottom-right-radius: 12px;
}

table-striped tbody tr:nth-child(even) td, table-striped tbody tr:nth-child(even) th
	{
	background-color: #D4FFFF;
}

table-striped tbody tr:nth-child(odd) td, table-striped tbody tr:nth-child(odd) th
	{
	background-color: #D4BFFF;
}

.title {
	background: #CCC;
}

.datagrid-row-alt {
	background: #fff !important;
}

.datagrid-row-selected {
	background: #f5f5f5 !important;
	color: #000 !important;
}

.datagrid-row-over, .datagrid-header td.datagrid-header-over {
	background: #f5f5f5 !important;
}
.c6, .c6:hover {
    background: #359bcc;
    }
</style>
<body  style="position:static">
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="custsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="a_customer_id" value="<s:property value="customer_id"/>">
<input type="hidden" id="isDefaultTen" value="<s:property value="model.is_default_ten"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<!--资源  -->
<input type="hidden"  id="web_Resource" value="<s:property value = 'web_Resource'/>" />
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员增项</strong>
	</legend>
	<div class="user-query">
		<div style="width: 20%;float: left;">
			原总额&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" readonly
				id="item_amount" value="0" style="height: 26px; width: 60%;" /><span>(元)</span>
		</div>
		<div id="zkl" style="width: 15%;float: left;">
			折扣率&nbsp;&nbsp;&nbsp;&nbsp;
			<input  type="text" id="discount" value="10" style="height: 26px; width: 50%;" />
		</div>
		<div id="zkhze" style="width: 18%;float: left;">
			折扣后总额&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="amount"   value="0" style="height: 26px; width: 50%;" />
				<span>(元)</span>
		</div>
		<div style="width: 37%;float: left;">
			<a href="javascript:djtcustadd();" class="easyui-linkbutton c6"
				style="width: 30%;">保存</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" style="width: 30%;"
				onclick="javascript:window.close();">关闭</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div><span id="z_zhi" style="width: 60%;"></span></div>
		</div>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择套餐</strong>
	</legend>

	<div id="main">

			套餐列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
				class="easyui-textbox" type="text" id="tclist" value="" />
			<div id="com_name_list_div" style="display: none"
				onmouseover="select_com_list_mover()"
				onmouseout="select_com_list_amover()"></div>

			已选套餐
			<div id="selectctlist" style="overflow-y: auto; overflow-x: auto; width: 100%;"></div>
	</div>
</fieldset>

<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检项目</strong>
	</legend>
		<div id="main">
			<div id="toolbar1">
		       <label style="font-size: 15px;font-weight: bold;">搜索条件：</label>
		       <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目名称&nbsp;&nbsp;&nbsp;</label><input type="text" id="itemname" name="itemname" value="" />
	        </div>
			<div id="changitemlist" style="overflow-y: auto; overflow-x: auto; width: 100%;"></div>
			
		</div>



</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<div style="height: 200px;">
		已选项目
		<div id="selectchangitemlist" style="height: 200px;"></div>
	</div>

</fieldset>
<div id="results_contrast" class="pop_up_box_lis">
    <div id="ls_title" class="title"><span>检查项目明细</span></div>
    <table id="examitem_div"></table>
</div>
 <div id="dlg-item-fuzhi" class="easyui-dialog"  data-options="width:800,height:510,closed: true,cache: false,modal: true,top:50"></div>
</body>