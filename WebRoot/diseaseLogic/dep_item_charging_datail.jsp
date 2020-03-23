<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/diseaseLogic/dep_item_charging_datail.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<!-- 普通类型显示 -->
<div id="common" style="margin: 0 auto; width: 98.5%;">
	<div  style="display: none;">
		<input type="text" id="chargitem_id" value="" />
		<input type="text" id="limit_count" value="" />
		<input type="text" id="dept_id" value="" />
	</div>
	<fieldset style="margin:5px;padding-right:0;height: 80%;width: 98.5%">
		<legend style="margin-top: 6px; margin-bottom: 10px;"><strong><span id="title_item" style="color:#0b90da;"></span> --- 检查项目清单</strong></legend> 
		<div style="float: left; width: 49.5%;">
			<span style="font-size: 16px;">检查项目名称</span> 
			<input type="text" class="textinput" id="item_num" style="width: 150px; height: 30px; padding-bottom: 6px;margin-bottom:2px;"/>
			<a href="javascript:getExaminationItem();" class="easyui-linkbutton c6" style="width: 95px;margin-left: 10px;">查询</a>
			<!-- <a href="javascript:getExamItemAddSave();" class="easyui-linkbutton" style="width: 95px; margin-left: 120px;">保存</a> -->
			<table id="examinationItem"></table>
		</div>
		<div style="float: right; width: 49.5%; margin-top: 2px">
			<div style="height: 29px">
				<span style="font-size: 16px;">以下是已选择检查项目清单</span>
			</div>
			<table id="selectedExaminationItem" class="easyui-datagrid"></table>
		</div>
	 </fieldset>
</div>
<div id="item_msg" style="display: none; margin-top: 30px;">
	<p align="center" style="font-size: 19px;">耗材类型没有项目细项</p>
</div>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,left:20"></div>
 <div id="dlg-itemView" class="easyui-dialog"  data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50,left:120"></div>