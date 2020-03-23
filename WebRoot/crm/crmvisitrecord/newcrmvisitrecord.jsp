 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/crm/crmvisitrecord/newcrmvisitrecord.js?randomId=<%=Math.random()%>"></script>
<!--表格内容溢出---显示省略号样式  -->
<style type="text/css">
.datagrid-btable .datagrid-cell{padding:6px 4px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;}  
</style>
<fieldset style="margin:5px;padding-right:0;">
<input id="visit_nums" name="id" value='<s:property value="visit_num"/>' type="hidden"/>
<input   id="visit_datelist"   hidden="true" value="<s:property value="visit_date"/>"/>
<legend><strong>回访记录查询</strong></legend>
			<div class="user-query" >
				<dl>
					<dt style="width:60px;">开始日期</dt>
					<dd>
					<input   type="text"  hidden="true" id="visit_num" 
				value="<s:property value="visit_num"/>"  class="textinput"	style="height: 16px; width: 232px;"/>
						<input  type="text" class="easyui-datebox" id="startTime"    style="height:23px;width:140px;" data-options="prompt:'请选择开始查询日期'" value="<s:property value="model.startTime"/>"/>
					</dd>
					<dt style="width:70px;">结束日期</dt>
					<dd>
						<input  type="text" class="easyui-datebox" id="endTime"    style="height:23px;width:140px;" data-options="prompt:'请选择结束查询日期'" value="<s:property value="model.endTime"/>"/>
					</dd>	
					<dd><a href="javascript:getCrmVisitRecordList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
 <legend><strong>回访记录列表</strong></legend> 
      <table id="CrmVisitRecordshow">
      </table>	
 </fieldset>

 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
