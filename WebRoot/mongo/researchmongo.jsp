<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>科研数据页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/mongo/researchmongo.js?randomId=<%=Math.random()%>"></script>
</head> 
<style> 
table,td{
  border:1px solid #ccc; 
  border-collapse:collapse; 
} 
td{
  width:80px;
  height:30px;
}
.div-inline{ display:inline} 
    </style> 
<body>
<input type="hidden" id="pagesize" value="50">
<input type="hidden" id="pageno" value="1">
<div class="easyui-layout" fit="true">
 <div data-options="region:'north',border:false" style="height:105px;"> 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:70px;"><input id="ck_exam_num" type="checkbox"/>体检号</dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;ime-mode:disabled;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox" checked="checked"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:60px;"><input id="ck_age" type="checkbox"/>年龄</dt>
				 	<dd><input class="easyui-numberbox"  type="text" id="min_age" style="height:26px;width:40px;" data-options="min:0,precision:0"/></dd>
				 	<dt style="height:26px;width:20px;">至</dt>
				 	<dd><input class="easyui-numberbox"  type="text" id="max_age" style="height:26px;width:40px;" data-options="min:0,precision:0"/></dd>
				 	<dd><input type="radio" name="searchType" value="com" checked>普通查询</input> &nbsp;&nbsp;<input type="radio" name="searchType" value="zyb">职业病查询</input></dd>
				 	<dd><input type="radio" name="searchlimit" value="limit" checked>按选定项目显示</input> &nbsp;&nbsp;<input type="radio" name="searchlimit" value="all">显示所有</input></dd>
				</dl>
				<dl>
				   <dt style="height:26px;width:70px;"><input id="ck_examtype" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="examtype"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="G">个检</option>
						<option value="T">团检</option>
					</select></dd>
                    <dt style="height:26px;width:60px;"><input id="ck_sex" type="checkbox"/>性别</dt>					
					<dd><select class="easyui-combobox"  id="sex" data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:348px;position:fixed;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:60px;">查询</a></dd>
                    <dd><a href="javascript:mangoexport_data();" class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">导出excel</a>
                    &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<a href='javascript:mangoexport_save();'>保存组合条件</a>
                    </dd>
                    
                    <dd> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<font face="宋体" color="#000000" >
                                                共:<div id="counts" class="div-inline">0</div>条 &nbsp;&nbsp;共<div id="pagecount" class="div-inline">0</div>页&nbsp;&nbsp;当前第<div id="pagenum" class="div-inline"></div>页
                        &nbsp;&nbsp;<div id="propage" class="div-inline"></div>&nbsp;&nbsp;<div id="nextpage" class="div-inline"></div>
                    </font></td>  
                   </dd>
				 </dl>
			</div>
 </fieldset>
</div>
<div data-options="region:'west',title:'组合条件  ',split:true" style="width:600px;">
	<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="查询结果符合条件的项目" style="padding:0px;" >
		<div class="user-query" style="font-size:14">
		<dl style="background: #cccccc;">
		    <dt style="width:50px;text-align: center;">大条件</dt>
			<dt style="width:170px;text-align: center;">收费项目</dt>
			<dt style="width:150px;text-align: center;">检查项目</dt>
			<dt style="width:50px;text-align: center;">小条件</dt>
			<dt style="width:130px;text-align: center;">条件值</dt>
			<dt style="width:30px;text-align: left"><input type="button" value="+" onclick="add_value();" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>
		</dl></div>
		<div style="line-height: 30px;" id="zuhetj">
		</div>
	</div>
	<div title="需查询结果的项目" style="padding:0px;" >
		<div class="user-query" style="font-size:14">
		<dl style="background: #cccccc;">
			<dt style="width:265px;text-align: center;">收费项目</dt>
			<dt style="width:265px;text-align: center;">检查项目</dt>
			<dt style="width:30px;text-align: left"><input type="button" value="+" onclick="add_value1();" style="width:20px;text-align: center;background:#6fa8dc;color:#ffffff;cursor: pointer;"/></dt>
		</dl></div>
		<div style="line-height: 30px;" id="zuhetj1">
		</div>
	</div>
	<div title="查询条件列表" style="padding:0px;" >
		<div class="user-query" style="font-size:14">
			<table id="exam_item_list"></table>
		</div>
	</div>
	</div>
</div>
<div data-options="region:'center'">
	<div id="exam_item_list_table"></div>
</div>
 </div>
 </body>
 </html>