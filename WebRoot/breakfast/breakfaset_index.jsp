<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<title>早餐科室首页</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/breakfast/breakfaset_index.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	$(function(){
		$("input").attr("maxlength","20");
		$("input",$(".easyui-textbox").next("span")).focus(function(){
	        $(this).select();
		});
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	})
	
</script>
</head> 
<body>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="ck_exam_num" type="checkbox" checked="checked"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox"/>体检日期</dt>
					<dd><input class="easyui-datebox" id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
                     <dd><input class="easyui-datebox" id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"/></dd>
			         <dt style="height:26px;width:50px;"><input id="ck_searchemployeeID" type="checkbox"/>工号</dt>					
					 <dd><input class="easyui-textbox"  type="text" id="searchemployeeID" value="" style="height:26px;width:135px;"/></dd> 
					 <dt style="height:26px;width:80px;"><input id="ck_exam_status" type="checkbox"/>检查状态</dt>					
					 <dd><select class="easyui-combobox" id="exam_status"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">全部</option>
						<option value="N">未就餐</option>
						<option value="Y">已就餐</option>
					</select></dd>   
				</dl>
				<dl>
				   <dt style="height:26px;width:80px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id" /><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:415px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>  
                     <dt style="height:26px;width:50px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>                      
					<dd><a href="javascript:shouye_chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
				 </dl>
				<!--  <dl>
				 <dd><input  checked="checked" name="exam_status" onclick="getgroupuserGrid()" type="radio" value="N" /></dd>
				 <dt>未吃早餐人员</dt>
		         <dd><input  name="exam_status" type="radio" onclick="getgroupuserGrid()" value="Y" /></dd>
		         <dt>已吃早餐人员</dt>
				</dl> -->
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 </body>
 </html>