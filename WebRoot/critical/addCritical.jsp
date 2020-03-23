<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 45%;	height: auto;}
#right {	width: 45%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/critical/addCritical.js?randomId=<%=Math.random()%>"></script>
<body>
<fieldset style="margin: 3px; padding-right: 0;">
	<legend>
		<strong>信息检索</strong>
	</legend>
	<div class="user-query">
		<dl>
			<dd style="height: 20px; width: 280px;">
				体检号   <input type="text" style="width:145px ; height: 20px;" id="ser_num" name="ser_num" class="textinput"/>
			</dd>
			<dd>
			 <a href="javascript:chaxun();" class="easyui-linkbutton c6" style="width:110px;">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <a href="javascript:window.close();" class="easyui-linkbutton " style="width:110px;">关闭</a>
			</dd>
			
		</dl>
	</div>
</fieldset>

<div class="easyui-layout" fit="true">
	<div data-options="region:'west'" style="width:50%;"> 
		<fieldset style="height: 88%;">
    			<legend><strong>检查信息</strong></legend>
    			<table id="item_result"></table>
    	</fieldset>
		
	</div>
    <div data-options="region:'center'">
    	<div class="easyui-layout" data-options="fit:true" border="false" style="margin-left:10px;">
    		<div data-options="region:'north'" border="false" style="height:54px;">
    			<fieldset>
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query">
						<dl>
							<dt style="width:50px;">姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name" style="width:70px;"></dt>
							<dt style="width:50px;">性别：</dt><dt id="sex" style="width:30px;"></dt>
							<dt style="width:50px;">年龄：</dt><dt id="age" style="width:30px;"></dt>
							<dt style="width:70px;">体检套餐：</dt><dt id="set_name"></dt>
							<dt style="width:70px;">体检日期：</dt><dt id="join_date"></dt>
							<dt style="width:70px;">单位：</dt><dt id="company"></dt>
						</dl>
					</div>
 				</fieldset>
			</div>
			<div data-options="region:'center'">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        			<div title="总检结论" style="padding:5px;" >
        				<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 90%;font-size:14px;" id="zongjianjielun"></textarea>
        			</div>
       				<div title="总检建议" style="padding:5px;">
       					<fieldset style="height: 88%;">
				    			<legend><strong>总检建议</strong></legend>
				    			<table id="exam_disease" style="width: 100%;height: 80%;">
				    	</fieldset>
       					
	      				</table>
       				</div>
       			</div>
			</div>
    	</div>
	</div>
</div>
</body>



