<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%application.setAttribute("name","application_James");  %>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>医保项目管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/charge/item_bind_insurance.js?randomId=<%=Math.random()%>"></script>
</head>
<body >   
		<a href="getmedicalCharge.action"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:160px;">医保项目价表关系</a>
			 		<fieldset>
						<legend><strong>信息检索</strong></legend>
							<div class="user-query">
								<dl>
								    <dt style="height:26px;width:70px;">项目编号</dt>
									<dd><input class="easyui-textbox"  type="text" id="item_code" value="" style="height:26px;width:100px;"/></dd> 
									<dt style="height:26px;width:60px;">项目名称</dt>
									<dd><input class="easyui-textbox"  type="text" id="item_name" value="" style="height:26px;width:135px;"/></dd>
									<dt style="height:26px;width:40px;padding-left:10px;">科室</dt>					
									<dd><input  class="easyui-combobox"   id="c_dep" style="height: 26px"  data-options="valueField:'id',textField:'dep_name',url:'getDepartmentListCharge.action'"/></dd>
									<!-- <dt style="height:26px;width:110px;padding-left:10px;">是否关联省医保</dt>	 -->				
								<%-- 	<dd><select class="easyui-combobox" id="is_bind_prov" data-options="height:26,width:100,panelHeight:'auto'">
									<option value="">全部</option>
									<option value="Y">是</option>
									<option value="N">否</option>
									</select></dd> --%>
									<dd><a href="javascript:getChargItemList_prov();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">查询</a></dd>
									
			                  </dl>
								
							</div>
			</fieldset>
		 	<div style="width: 58%;float: left;">
			 	<fieldset>
					<legend><strong>收费项目列表</strong></legend>
		        		<table id="chargItemList"></table>
		        </fieldset>
	      	</div>
	      	<div  style="width:41%;float: right;">
	      		<fieldset>
					<legend><strong>已关联价表列表</strong></legend>
	      				<table id="binded_prov"></table>
      		    </fieldset>
	      	</div>
	      	<div id="clinic_prov_v" class="easyui-dialog" data-options="width: 1000,height: 500,closed: true,cache: false,modal: true,top:50"></div>
</body>
</html>