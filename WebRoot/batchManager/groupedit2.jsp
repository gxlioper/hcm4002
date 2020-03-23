<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 45%;	height: auto;}
#right {width: 45%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/batchManager/groupedit2.js?randomId=<%=Math.random()%>"></script>

<input type="hidden" id="id" value="<s:property value="model.id"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batch_id"/>">
<input type="hidden" id="batchMarriage" value="<s:property value="model.is_Marriage"/>">
<input type="hidden" id="batchindicator" value="<s:property value="model.exam_indicator"/>">
<input type="hidden" id="cust_type_list" value="<s:property value="model.cust_type_id"/>">
<input type="hidden" id="batchsex" value="<s:property value="model.sex"/>">
<input type="hidden" id="group_num" value="<s:property value="model.group_num"/>">
<input type="hidden" id="custsex" value="<s:property value=""/>">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>所选体检任务</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 140px;">单位名称</dd>
			<dd style="height: 20px; width: 280px;">
				<s:property value="model.comname" />
			</dd>
			<dd style="height: 20px; width: 140px;">体检任务名称：</dd>
			<dd style="height: 20px; width: 280px;">
				<s:property value="model.batch_name" />
			</dd>
			<dd>
			 <a href="javascript:groupcopy();" class="easyui-linkbutton c6" style="width:110px;">复制套餐项目</a>
	         <a href="javascript:grouppost();" class="easyui-linkbutton c6" style="width:100px;">使用复制项</a>
			 <a href="javascript:groupadd();" class="easyui-linkbutton c6" style="width:100px;">保存</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">关闭</a>
	        
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>分组基本信息</strong>
	</legend>
	<div class="user-query">
		<dl>
			
			<dt>
				分组名称 <strong class="quest-color">*</strong>
			</dt>
			<dd>
				<input class="easyui-textbox" maxlength="20" type="text" id="group_name"
					value="<s:property value="model.group_name"/>"
					style="height: 26px; width: 140px;" />
			</dd>
			<dt>职位</dt>
			<dd>
				<input type=textarea  maxlength="20" id="posttion"
					value="<s:property value="model.posttion"/>"
					style="width: 140px; height: 26px;"></input>
			</dd>
			<dt>付费方式</dt>
			<dd>
				<select class="easyui-combobox"  id="exam_indicator" name="exam_indicator"
					value="<s:property value="model.exam_indicator"/>"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>最小年龄(≥)</dt>
			<dd>
				<input maxlength="20" class="easyui-textbox" type="text" id="min_age"
					value="<s:property value="model.min_age"/>"
					style="height: 26px; width: 140px;" />
			</dd>

		</dl>
		<dl>
			<dt>性别</dt>
			<dd>
				<select class="easyui-combobox"  id="sex" name="sex"
					value="<s:property value="model.sex"/>"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			<dt>婚否</dt>
			<dd>
				<select class="easyui-combobox" id="is_Marriage" name="is_Marriage"
					value="<s:property value="model.is_Marriage"/>"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			  <dt>其他</dt>
			<dd>
				<input type=textarea maxlength="20" id="group_index"
					value="<s:property value="model.group_index"/>"
					style="width: 140px; height: 26px;"></input>
			</dd>
			<dt>最大年龄(&lt;)</dt>
			<dd>
				<input class="easyui-textbox" maxlength="20" type="text" id="max_age"
					value="<s:property value="model.max_age"/>"
					style="height: 26px; width: 140px;" />
			</dd>
		</dl>
		<dl>
		  <dt>人员类别</dt>
			<dd><input id="cust_type_id" name="cust_type_id" class="easyui-combobox" style="width:140px;height:30px;" data-options="
					panelHeight:'auto' ">				
			</dd>
			<dt>原总额</dt>
			<dd>
				<input class="easyui-textbox" maxlength="20" type="text" readonly id="item_amount" value="<s:property value="model.item_amount"/>"
					style="height: 26px; width: 115px;" />(元)
			</dd>
			<dt>折扣率</dt>
			<dd>
				<input maxlength="20" type="text" id="discount"
					value="<s:property value="model.discount"/>"
					style="height: 26px; width: 140px;" />
			</dd>
			<dt>折扣后总额</dt>
			<dd>
				<input class="easyui-textbox" maxlength="20" type="text" id="amount"
					value="<s:property value="model.amount"/>"
					style="height: 26px; width: 110px;" />(元)
			</dd>
		</dl>
	</div>
</fieldset>
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择套餐</strong>
	</legend>

	<div id="main">
			套餐列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="textinput" id="selecttclist"
					onclick="selectsetforsexTOG();" value="" style="height: 26px; width: 160px;" />

			已选套餐
			<div id="selectctlist"></div>
	</div>

</fieldset>
<%-- <fieldset style="margin: 5px; padding-right: 0;">
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

</fieldset> --%>
<div id="cc" class="easyui-layout" style="width:90%px;height:85%;">   
    <div data-options="region:'west',title:'科室'" style="width:200px;background:#eee;">
	   <!--  <fieldset style="margin: 5px; padding-right: 0;">
			<legend> -->
			<%-- 	<strong>科室</strong> --%>
			<!-- </legend> -->
		    	<ul id="depd_tree"   ></ul>
		    <!-- </fieldset> -->
    </div>   
     <div data-options="region:'center',title:'选择体检项目',width:500,tools:'#tt',toolPosition:'left'" style="padding:0px;background:#eee;">
     	
     	<!-- <fieldset style="margin: 5px; padding-right: 0;"> -->
				<div id ="itemshow"  ></div>
			<!-- </fieldset> -->
     </div>   
     <div id="tt"  style="width: 400px;" >
     	<div  style="float: left;margin-right: 10%;"  >
	     	<font>名称检索</font>
	     	<input type="text"  onkeyup="getItemShow(1)"     id ='c_item_name'  />
     	</div>
     	<div style="float: left;margin-right:50%;" ><input type="checkbox"  onclick="item_qihuan_yixuan()" id="btn-yxxm"/><label onclick="item_qihuan_yixuan(1)"><font size="4">已选项目</font></label></div>
     	<div style="width:1050px;"></div> 
	</div>
</div>  
<div id="dlg-selectsetforsexTOG" class="easyui-dialog" data-options="width: 1200,height: 590,closed: true,cache: false,modal: true"></div>