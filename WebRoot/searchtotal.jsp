<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>健康体检系统</title>
<style type="text/css">
.div_obj {
	float: left;
	width: 289px;
	height: 289px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj1 {
	float: left;
	width: 550px;
	height: 300px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj2 {
	float: left;
	width: 694px;
	height: 400px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj3 {
	float: left;
	width: 764px;
	height: 400px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj4{
	float: left;
	width: 544px;
	height: 140px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj5{
	float: left;
	width: 690px;
	height: 197px;
	margin: 2px;
	background-color: #FFFFF4;
}
.div_obj6{
	float: left;
	width: 1460px;
	height: 197px;
	margin: 2px;
	background-color: #FFFFF4;
}
.div_obj7{
	float: left;
	width: 1460px;
	height: 300px;
	margin: 2px;
	background-color: #FFFFF4;
}
.div_obj8 {
	float: left;
	width: 694px;
	height: 300px;
	margin: 2px;
	background-color: #FFFFF4;
}

.div_obj9 {
	float: left;
	width: 764px;
	height: 300px;
	margin: 2px;
	background-color: #FFFFF4;
}
.in_one_line {
	float: left
}
</style>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="echarts/echarts.min.js"></script>
<script type="text/javascript"
	src="searchtotal.js?randomId=<%=Math.random()%>"></script>

</head>
<body bgcolor="#C4E1FF">
	<div class="in_one_line">
		<div class="div_obj" id="showtotla"></div>
		<div class="div_obj" id="container"></div>
		<div class="div_obj" id="sql_conn_count"></div>
		<div class="div_obj" id="showChart"></div>
		<div class="div_obj" id="exam_gride_statistics"></div>
	</div>
	<br>
	<div class="in_one_line">
			<div class="div_obj8" id="flow_trend_chart"></div>
			<div class="div_obj9" id="flow_sql_conn_count"></div>
		</div>
		<br>
		<div class="in_one_line">
			<div class="div_obj2" id="showbar1"></div>
			<div class="div_obj3">
			 <div class="div_obj5" id="statistics_personnel_times"></div>
		    <div class="div_obj5" id="count_number_checks"></div>
			</div>
		</div>
		<br>
		<div class="in_one_line">
			<div class="div_obj2">
			   <div class="div_obj5" id="annual_unit_growth"></div>
		       <div class="div_obj5" id="annual_personnel_growth"></div>
			</div>			
			<div class="div_obj3">
		    <div class="div_obj5" id="showgradient"></div>
		    <div class="div_obj5" id="showgradient4"></div>
		</div>	
		</div>
		<br>
		<div class="div_obj7" id="age_group_disease"></div>		
</body>
</body>
</html>