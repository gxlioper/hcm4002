<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 45%;	height: auto;}
#right {	width: 45%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/customerManager/zybcustomerAllitemaddshow.js"></script>
<input type="hidden" id="ids" value="<s:property value="model.ids"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="batchsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<script type="text/javascript">
$(function(){
	$('input').attr("maxlength","20");
})
</script>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员增项</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 280px;">所选人员为：<s:property value="model.age"/>人</dd>
			<dd style="height: 20px; width: 280px;">人员性别为：<s:property value="model.sex"/>性</dd>
		</dl>
		<dl>
		  
			<dt>原总额</dt>
			<dd>
				<input  type="text" readonly id="item_amount" value="0"
					style="height: 26px; width: 115px;" /><span>(元)</span>
			</dd>
			<dt id="zx_zkl">折扣率</dt>
			<dd>
				<input  type="text" id="discount"
					value="10"
					style="height: 26px; width: 100px;" />
			</dd>
			<dt id="zx_zkhze">折扣后总额</dt>
			<dd>
				<input type="text" id="amount"
					value="0"
					style="height: 26px; width: 110px;" /><span id="zx_yuan">(元)</span>
			</dd>
			<dd>
			    <label><input name="exam_indicator"  type="radio" checked value="Z" />职业病加项 </label> 
			    <label><input name="exam_indicator"  type="radio" value="T" />团体加项 </label> 
				<label><input name="exam_indicator"  type="radio" value="G" />个人加项 </label>                
			</dd>
		</dl>
	</div>
</fieldset>

<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检项目</strong>
	</legend>
	<div id="main">
		<div id="left">
			项目列表<input type="text" id="itemname" name="itemname" value="" />
			<div id="changitemlist"></div>
		</div>

		<div id="right">
			已选项目
			<div id="selectchangitemlist"></div>
		</div>

	</div>

</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">

<div align="right">
	<div class="inner-button-box">
	    <a href="javascript:groupadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	</div>
</div>
</fieldset>
