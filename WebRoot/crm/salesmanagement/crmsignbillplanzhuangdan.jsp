<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/crm/salesmanagement/crmsignbillplanzhuangdan.js?randomId=<%=Math.random()%>"></script>

   
 <input id="ids" name="ids" value="<s:property  value="id"/>" type="hidden"/>
  <input id="signname" name="signname" value="<s:property  value="sign_name"/>" type="hidden"/>
<input id="company_ids" name="company_ids" value="<s:property  value="company_id"/>" type="hidden"/>

<fieldset style="margin:5px;padding-right:0;">
<legend><strong>单位信息</strong></legend>
    <div class="user-query" style="padding-top:0px;">
	<dl>
		<dt style="width:70px;padding-left:20px;">单位名称：</dt>
		<dt style="width:230px;" id="com_names"></dt>
		<dt style="width:70px;">单位类型：</dt>
		<dt style="width:230px;" id="com_types"></dt>
		<dt style="width:70px;">经济类型：</dt>
		<dt style="width:230px;" id="economiccodes"></dt>
		<dt style="width:70px;">企业规模：</dt>
		<dt style="width:230px;" id="comsizecodes"></dt>
	</dl>
	<dl>
		<dt style="width:70px;padding-left:20px;">单位地址：</dt>
		<dt style="width:230px;" id="addresss"></dt>
		<dt style="width:70px;">行业类型：</dt>
		<dt style="width:230px;" id="industrycodes"></dt>
		<dt style="width:70px;">行政区划：</dt>
		<dt style="width:230px;" id="areacodes"></dt>
	</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>签单计划</strong></legend>		
	<div class="user-query" style="padding-top:0px;">
		<dl>
			<dt style="width:70px;padding-left:20px;">签单名称：</dt>
			<dt style="width:150px;" id="ck_sign_names"></dt>
			<dt style="width:70px;">助记码：</dt>
			<dt style="width:150px;" id="ck_sign_pingyings"></dt>
			<dt style="width:70px;">签单类型：</dt>
			<dt style="width:150px;" id="ck_sign_types"></dt>
			<dt style="width:70px;">签单编码：</dt>
			<dt id="ck_sign_nums"></dt>
		</dl>
		<dl>
			<dt style="width:70px;padding-left:20px;">签单数量：</dt>
			<dt style="width:150px;" id="ck_sign_counts"></dt>
			<dt style="width:70px;">年度：</dt>
			<dt style="width:150px;" id="ck_sign_years"></dt>
			<dt style="width:70px;">新旧分类：</dt>
			<dt style="width:150px;" id="ck_old_new_types"></dt>
			<dt style="width:70px;">客户分类：</dt>
			<dt style="width:170px;" id="ck_customer_types"></dt>
			<dt style="width:110px;">保护开始日期：</dt>
			<dt id="ck_protect_dates" style="width:120px;"></dt>
		</dl>
		<dl>
			<dt style="width:70px;padding-left:20px;">估算人数：</dt>
			<dt style="width:150px;" id="ck_sign_persions"></dt>
			<dt style="width:70px;">估算金额：</dt>
			<dt style="width:150px;" id="ck_sign_amounts"></dt>
			<dt style="width:70px;">签单日期：</dt>
			<dt style="width:150px;" id="ck_sign_dates"></dt>
			<dt style="width:110px;">体检结束日期：</dt>
			<dt style="width:130px;" id="ck_end_dates"></dt>
			<dt style="width:110px;">保护截止日期：</dt>
			<dt id="ck_abort_dates" style="width:120px;">
			</dt>
			<dt style="width:160px;" id="showdatesdt">
			<input id="showdates" hidden="true">
			</dt>
			<dt style="padding-right:20px;padding-left:20px;">
			<input id="xiugai" type="button" value="修改截止日期" style="width:100px" onclick="editabort_dates($('#xiugai').val()=='完成'?0:1)">
			</dt>		
		</dl>
		<dl>
			<dt style="width:70px;padding-left:20px;">签单状态：</dt>
			<dt style="width:150px;" id="ck_sign_statusss"></dt>
			<dt style="width:70px;">跟踪进度：</dt>
			<dt style="width:150px;" id="ck_track_progressss"></dt>
			<dt style="width:70px;">变化时间：</dt>
			<dt style="width:150px;" id="ck_track_times"></dt>
			<dt style="width:70px;">申请人：</dt>
			<dt style="width:170px;" id="ck_creaters"></dt>
			<dt style="width:70px;">申请时间：</dt>
			<dt style="width:150px;" id="ck_create_times"></dt>
		</dl>
	</div>
</fieldset>
<fieldset style="margin:5px;">
    <legend><strong>单位联系人</strong></legend>
    <table id="ck_contacts_lists"></table>
</fieldset>


<fieldset style="margin:5px;padding-right:0;">
<legend><strong>签单查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:100px;">签单计划名称</dt>
					<dd>
						<input  type="text" class="textinput" id="sign_name"  value='<s:property  value="sign_name"/>'   style="height:26px;width:140px;" />
					</dd>
					<dd><a href="javascript:getSignBillPlanZhuangDanList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
						<dd><a href="javascript:updateSignBillPlanZhuangDan();" class="easyui-linkbutton c6" style="width:80px;">撞单</a></dd>
							<dd><a href="javascript:updateSignBillPlanWeiZhuangDan();" class="easyui-linkbutton c6" style="width:80px;">排除撞单</a></dd>
				</dl>
			</div>
 </fieldset>
<fieldset style="margin:5px">
<legend><strong>计划列表</strong></legend>
<table id="crmsignbillplanzhuangdan"> 
</table>
</fieldset>

<div id="dlg-edit"></div>
