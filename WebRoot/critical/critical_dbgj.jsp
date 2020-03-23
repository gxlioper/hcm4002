<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title> </title>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 350px;	height: auto;}
#right {width: 72.5%;height: auto;	margin-left: 10px;}
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
<script type="text/javascript"	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/critical/critical_dbgj.js?randomId=<%=Math.random()%>"></script>
</head>
<body >
<input type="hidden"   id='tjhname'  value='<s:text name="tjhname"  />'  />
<input type="hidden"   id='dahname' value='<s:text name="dahname"  />'  />
  <div id="main">
		<div id="left">
 			<fieldset style="margin:5px;padding-right:0;">
    			<legend><strong>基本信息</strong></legend>
					<div class="user-query" style="font-weight: bold;font-size: 15px">
					<input type="hidden" name="exam_id" id="exam_id"/><input type="hidden" name="exam_num" id="exam_num"/></dt>
						<dl><dt style="width:80px;">姓<font style="color:white;">占位</font>名：</dt><dt id="user_name"/><dt id="sex" style="width:60px;"/><dt id="age" style="width:60px;" class="autoWidth"/></dl>
						<dl><dt style="width:80px;">电<font style="color:white;">占位</font>话：</dt><dt id="userphone"></dt></dl>
						<dl><dt style="width:80px;">会员级别：</dt><dt id="uservipflag"></dt></dl>
						<dl><dt style="width:80px;">体检日期：</dt><dt id="join_date"></dt></dl>
						<dl><dt style="width:80px;">单<font style="color:white;">占位</font>位：</dt><dt id="company" style="width: 230px;"></dt></dl>
						<dl><dt style="width:80px;">体检类型：</dt><dt id="exam_type"></dt></dl>
					</div>
			</fieldset>
	</div>
   <!--右边面版  -->
		 <div id="right">
  			<!--上  -->
		    	<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 50px; margin-right: 10px" >
    			<legend><strong>信息检索</strong></legend>
					<div class="user-query">
						<dl>
							<dd><s:text name="tjhname"/>：<input type="text" style="width:140px" id="exam_num_s" name="exam_num_s"  class="textinput"/></dd>
							<dd><a href="javascript:getcustomerInfo();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">查询</a></dd>
							<dd><a href="javascript:addCritical();" class="easyui-linkbutton c6" id="add"   style="width: 90px">添加危机</a></dd>
							<!--<dd><a href="javascript:chexiao();" class="easyui-linkbutton"   style="width: 90px">撤销导引单</a></dd>
							<dd><a href="javascript:getlisStatus();" class="easyui-linkbutton"   style="width: 120px">获取检验状态</a></dd>
							<dd><a href="javascript:liuchenbeizhuPage();" class="easyui-linkbutton"   style="width:80px">备注</a></dd>
							<dd id = "upload"><a href="javascript:examh1insert1();" class="easyui-linkbutton c6"   style="width:100px">上传整单</a></dd>	
							<dd><a href="javascript:createImage();" class="easyui-linkbutton c6" id="qz"   style="width: 90px">电子存档</a></dd>-->
						</dl>
					</div>
 			</fieldset>
 			
 			<fieldset style="margin:5px;padding-right:0; padding-top: 5px; padding-bottom: 20px; height: 520px; margin-right: 10px" >
    			<legend><strong>信息显示</strong></legend>						
					<div id="tt" class="easyui-tabs"  style="width: 905px; height: 520px;" data-options="fit:true,border:false,plain:true">
        			<div title="历史记录" style="padding:5px;" >
        				<table id="wjlist" ></table>		
        			</div>
       				<div title="总检结论" style="padding:5px;" >
        				<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 100%;font-size:14px;" id="zongjianjielun"></textarea>
        			</div>
       				<div title="总检建议" style="padding:5px;">
       					<table id="exam_disease">
	      				</table>
       				</div>
       				<div title="检查细项" style="padding:5px;">
       					<table id="item_result"></table>
       				</div>
       				<div title="导检单" style="padding:5px;">
       					<div id="disease_tijianbaogao"  style="vertical-align: middle; text-align: center;"></div>
       				</div>
       			</div>	
 			</fieldset> 			
		    </div>
		    
	</div>
	
	</div>
<div id="yq" class="fromDiv" style="padding-top: 50px;padding-left: 50px;z-index:-1;"></div>
<div id="dlg-message" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true"></div>
<div id="dlg-beizhu" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50,resizable:true">
	<fieldset style="margin:10px;padding-top:20px;height:80%;">
			<legend><strong>记录危机内容</strong></legend>	
	    <div class="formdiv">
			<dl>
		    	<dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>危机内容</dt>
		    	<dd><textarea style="width:90%;resize:none;" cols="55" rows="6" name="note" id="note" ></textarea>
				</dd>
				<dt>所属类型</dt>
		    	<dd>
		    		<select class="easyui-combobox" id="wjzlx" name="wjzlx"
						data-options="height:30,width:180,panelHeight:'auto'"></select>
				</dd>
		    </dl>
		</div>
		<div class="dialog-button-box">
			<div class="inner-button-box">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="f_Crisave()">保存</a> &nbsp;&nbsp;&nbsp;
			    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-beizhu').dialog('close')">关闭</a>
			</div>
		</div>
	</fieldset>
</div>
</body>
</html>