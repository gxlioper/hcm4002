<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/diseaseLogic/examCriticalLogic.js"></script>
<body style="height:99%;" class="easyui-layout">
  <div data-options="region:'west',split:true" style="width:300px;">
   	<fieldset style="margin:5px;padding-right:0;height:95%;padding-left: 0;margin-top: 10px">
   	<legend><strong>查询条件</strong></legend>
			<ul id="ett" style="" ></ul>
 	 </fieldset>
  </div>   
  <div data-options="region:'center',fit:false" style="padding:5px;">
     	<fieldset style="margin:5px;padding-right:0;padding-left: 0;margin-top: 10px">
		   	<legend><strong>查询条件</strong></legend>
					<div class="user-query">
							&nbsp;&nbsp;疾病/阳性发现
							<input type="text" class="textinput" value='' name='disease_name' id="disease_name"  style="height: 26px;" />
							&nbsp;&nbsp;&nbsp;&nbsp;等级
							<input type="text" id="c_jb"  name="c_jb"  style="height: 26px;width:215px"/>
							&nbsp;&nbsp;&nbsp;&nbsp;启用
							<input type="checkbox" checked="checked" id="Y" onclick="cheboxCilke()"  name='Y' value='Y' />
							停用
							<input type="checkbox" id="N" checked="checked" onclick="cheboxCilke()"  name="N" value='N' />
							<a href="javascript:cheboxCilke();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a>
					</div>
 		 </fieldset>
   		 <fieldset style="margin:5px;padding-right:0;height:84%">
  			<legend><strong>列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="background-color:#FF0000;">&nbsp;&nbsp;&nbsp;</span>停用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="background-color:black">&nbsp;&nbsp;&nbsp;</span>正常</strong></legend> 
	   	  	  <table id="examCriticalLogicTable"  >
	      	</table>	
	 	</fieldset>
  </div>   
 
 <div id="dlg-edit" class="easyui-dialog"  data-options="width:1200,height: 400,closed: true,cache: false,modal: true,top:10"></div>
 
 </body>
  
  