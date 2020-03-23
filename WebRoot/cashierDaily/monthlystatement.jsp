<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财务部门月结</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/cashierDaily/monthlystatement.js?randomId=<%=Math.random()%>"></script>
</head>
<body>
<input type="hidden" id="user_ids" value="<s:property value="model.user_id"/>"/>
      	 <div class="easyui-layout" border="false" fit="true">
      	 	<div data-options="region:'north'" style="height:70px;">
      	 				<fieldset>
    						<legend><strong>信息检索</strong></legend>
    						<div class="user-query">
								<dl>
									<dd><input type="radio" name="datetype" value="0"/></dd>
									<dt style="width:70px;">日结年份：</dt>
									<dd><select id="year"  class="easyui-combobox" data-options="height:26,width:70,panelHeight:'300'"></select></dd>
									<dt style="width:70px;">日结月份：</dt>
									<dd><select id="month"  class="easyui-combobox" data-options="height:26,width:70,panelHeight:'auto'">
									<option value="01">一月</option>
									<option value="02">二月</option>
									<option value="03">三月</option>
									<option value="04">四月</option>
									<option value="05">五月</option>
									<option value="06">六月</option>
									<option value="07">七月</option>
									<option value="08">八月</option>
									<option value="09">九月</option>
									<option value="10">十月</option>
									<option value="11">十一月</option>
									<option value="12">十二月</option>
									</select></dd>
									<dd><input type="radio" name="datetype" checked="checked" value="1"/></dd>
									<dt style="width:70px;">日结日期：</dt>
									<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dt style="height:26px;width:20px;">至</dt>
                     				<dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"/></dd>
                     				<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
								</dl>
							</div>
    					</fieldset>
      	 			</div>
      	 	<div data-options="region:'west',title:'收费金额汇总列表'" style="width:35%;">
      	 			<table id="finacclist"></table>
      	 	</div>
      	 	<div data-options="region:'center',title:'财务部门日结列表'">
      	 				<table id="dailylist"></table>
      	 		</div>
      	 	</div>
      	 </div>
</body>
</html>