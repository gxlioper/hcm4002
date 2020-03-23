<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健康体检管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/plugins/easyui.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/icon.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/brainExamin/brainExaminPage.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/brainExamin/brainExaminPage.js?randomId=<%=Math.random()%>"></script>
</head>
<body>

 <div class="easyui-layout" fit="true" style="margin:5px;">
    <div data-options="region:'west',split:true" title="体检列表管理" style="width:200px;">
    	<div id="titleList">
    	</div>
    </div>
    <div  data-options="region:'center',title:''">
    
    	<div style="display: none;">
    		<input  id="title_ji" type="text" value="0"/>
	    	<input  id="title_lei" type="text" value=""/>
	    	<input  id="sup_item_id" type="text" value="0"/>
	    	<input  id="add_title_id" type="text" value="0"/>
	    	<input  id="title_type" type="text" value=""/>
    	</div>
    	
    	<p>
		 	<h3 style="margin:16px 0px 12px 43px;">当前位置  &nbsp;  >> &nbsp;  
		 		<span id="titleText"></span>
		 	</h3>
		 </p>
    	
    	<div class="titlelist" style="display:none;">
    		<fieldset style="margin:5px;padding-right:0;">
				<div class="user-query" style="margin: 10px;">
					<dl>
						<dt style="text-align: center; ">标题关键字：</dt>
						<dd><input class="easyui-textbox"  type="text" id="title_name1" value=""  style="height:30px;width:180px;"/></dd>
						<dd><a href="javascript:queryBrainTitleMsg();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; margin-left: 15%; border-radius: 5px">查询</a></dd>
						<dd><a href="javascript:queryEmpty();"  class="easyui-linkbutton" style="width:100px; margin-left: 8%;border-radius: 5px">清空</a></dd>
					</dl>
				</div>
			 </fieldset>
			 
			 <div style="margin:5px;padding-right:0;">
				<table id="titlelist" style="height:525px; width: 100%;"></table>	
	    	</div >
			 
    	</div>
    	
    	
    	<div class="projectlist" style="display:none;">
    		<fieldset style="margin:5px;padding-right:0;">
				<%-- <legend><strong>体检标题查询</strong></legend> --%>
				<div class="user-query" style="margin: 10px;">
					<dl>
						<dt style="text-align: center; ">标题关键字：</dt>
						<dd><input class="easyui-textbox"  type="text" id="title_name2" value=""  style="height:30px;width:180px;"/></dd>
						<dd><a href="javascript:queryProjectGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; margin-left: 15%; border-radius: 5px">查询</a></dd>
						<dd><a href="javascript:queryEmpty();"  class="easyui-linkbutton" style="width:100px; margin-left: 8%;border-radius: 5px">清空</a></dd>
					</dl>
				</div>
			 </fieldset>
			<div id="questionlist2"  style="margin:5px;padding-right:0;">
				 <table id="questionlist" style="height:510px; width: 100%;"></table>	
	    	</div>
			<div id="projectlist2" style="margin:5px;padding-right:0;">
				 <table id="projectlist" style="height:510px;width: 100%;"></table>	
	    	</div>
			
    	</div>
    	
    	<div class="modulelist" style="display:none;">
    		<fieldset style="margin:5px;padding-right:0;">
				<%-- <legend><strong>体检标题查询</strong></legend> --%>
				<div class="user-query" style="margin: 10px;">
					<dl>
						<dt style="text-align: center; ">标题关键字：</dt>
						<dd><input class="easyui-textbox"  type="text" id="title_name3" value=""  style="height:30px;width:180px;"/></dd>
						<dd><a href="javascript:getModuleGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; margin-left: 15%; border-radius: 5px">查询</a></dd>
						<dd><a href="javascript:queryEmpty();"  class="easyui-linkbutton" style="width:100px; margin-left: 8%;border-radius: 5px">清空</a></dd>
					</dl>
				</div>
			 </fieldset>
			 
	    	<div  style="margin:5px;padding-right:0;">
				 <table id="modulelist" style="height:525px;width: 100%;"></table>
	    	</div>
			 
    	</div>
    	
    	<div class="stylelist" style="display:none;">
    		<fieldset style="margin:5px;padding-right:0;">
				<%-- <legend><strong>体检标题查询</strong></legend> --%>
				<div class="user-query" style="margin: 10px;">
					<dl>
						<dt style="text-align: center; ">标题关键字：</dt>
						<dd><input class="easyui-textbox"  type="text" id="title_name4" value=""  style="height:30px;width:180px;"/></dd>
						<dd><a href="javascript:getStyleGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px; margin-left: 15%; border-radius: 5px">查询</a></dd>
						<dd><a href="javascript:queryEmpty();"  class="easyui-linkbutton" style="width:100px; margin-left: 8%;border-radius: 5px">清空</a></dd>
					</dl>
				</div>
			 </fieldset>
			 
	    	<div style="margin:5px;padding-right:0;">
				 <table id="stylelist" style="height:525px;width: 100%;"></table>
	    	</div>
			 
    	</div>
    	
    	<div id="dlg-addEdit" class="easyui-dialog" data-options="width: 560,height: 410,closed: true,cache: false,modal: true,top:50"></div>
    	<div id="pro-addEdit" class="easyui-dialog" data-options="width: 600,height: 510,closed: true,cache: false,modal: true,top:35"></div>
    	
    </div>
</div>

</body>
</html>