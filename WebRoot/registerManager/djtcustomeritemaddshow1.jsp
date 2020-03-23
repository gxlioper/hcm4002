<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/djtcustomeritemaddshow1.js?randomId=<%=Math.random()%>"></script>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="custsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="a_customer_id" value="<s:property value="customer_id"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<!--资源  -->
<input type="hidden"  id="web_Resource" value="<s:property value = 'web_Resource'/>" />
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员增项</strong>
	</legend>
	<div class="user-query">
		<dl>
		  
			<dt>原总额</dt>
			<dd>
				<input  type="text" readonly id="item_amount" value="0" readonly
					style="height: 26px; width: 115px;" /><span>(元)</span>
			</dd>
			<dt id = "zkl">折扣率</dt>
			<dd>
				<input  type="text" id="discount" value="10" style="height: 26px; width: 100px;" />
			</dd>
			<dt  id = "zkhze">折扣后总额</dt>
			<dd>
				<input type="text" id="amount" 
					value="0"
					style="height: 26px; width: 110px;" /><span id = "yuan">(元)</span>
			</dd>
			<dd>
			 <a href="javascript:djtcustadd();" class="easyui-linkbutton c6" style="width:70px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;" onclick="javascript:window.close();">关闭</a>
	    	<a href="javascript:getcopyitemg();" class="easyui-linkbutton "   style="width:140px;">复制上次体检项目</a>
				 <a href="javascript:getcopyitemg2();" class="easyui-linkbutton " style="width:140px;">选择人复制项目</a>
			&nbsp;&nbsp;&nbsp;&nbsp;<span  id="z_zhi"></span>
			</dd>
		</dl>
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
			<div id="selectctlist"></div>
	</div>
</fieldset>

<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检项目</strong>
	</legend>
	<div id="main">
		<div id="left">
		<div id="toolbar1">
	       <label style="font-size: 15px;font-weight: bold;">搜索条件：</label>
	       <label>科室&nbsp;&nbsp;&nbsp;</label><select class="easyui-combobox" id="serch_dep_id"
					data-options="height:24,width:100"></select>
	       <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目名称&nbsp;&nbsp;&nbsp;</label><input type="text" id="itemname" name="itemname" value="" />
        </div>
			<div id="changitemlist"></div>
		</div>

		<div id="right">
			已选项目
			<div id="selectchangitemlist"></div>
		</div>

	</div>

</fieldset>
<div id="results_contrast" class="pop_up_box_lis">
    <div id="ls_title" class="title"><span>检查项目明细</span></div>
    <table id="examitem_div"></table>
</div>
 <div id="dlg-item-fuzhi" class="easyui-dialog"  data-options="width:800,height:510,closed: true,cache: false,modal: true,top:50"></div>
