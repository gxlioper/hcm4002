<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 
<script type="text/javascript" src="/scheduling/calendar.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="/scheduling/cal.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function() {
	//lookdetailsList(schedule_id);
	load_paiban_this();
});


</script>
	<div id="float-button" style="height:200px;padding-top:30px;" >
       			<div id="paiban_title" style="height:30px;width:50%;background:#d9d9d9;text-align:center;font-size:16px;line-height:30px;float:left;"></div>
       			<div id="now" style="height:30px;width:50%;background:#d9d9d9;text-align:center;font-size:16px;line-height:30px;float:right;">
       				<a  href="javascript:void(0)" onclick="load_paiban_this(-1)">上月 </a> &nbsp;&nbsp; <a href="javascript:void(0)" onclick="load_paiban_this(1)">下月</a></div>
       			<div id="paiban_table" style="height:220px;width:100%;background:#dbeaec;padding-top:20px;">
       				 <table border="0" cellpadding="1" cellspacing="9" id="paiban_table_list"  width="100%" style="text-align:center;font-size:16px;"></table>
       		</div>
       </div>
 