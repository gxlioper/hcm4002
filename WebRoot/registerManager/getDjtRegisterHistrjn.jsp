<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
/* #main {	width: auto;	height: auto;}
#left { float: left;   width: 410px;	height: auto;}
#right {margin-right: 10px;height: auto;} */
</style>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/getDjtRegisterHistrjn.js?randomId=<%=Math.random()%>"></script>
<body style="height:99.5%;width: 100%">
<input type="hidden" id="exam_id" value="<s:property value = 'id'/>"/>
<fieldset style="width:30.7%;height:97%;float: left;border:1px solid #ff9900;margin-left:1%;margin-top:8px;"  >
  <legend  style="color:#ff9900;"><strong>最近一次体检</strong></legend> 
	 <div id="shou_1"    class="formDiv">
		<dl  style="heigth:10px;"> 
			<dt  style="width:35px;">姓名:</dt>
			<dd  id='user_name_0'></dd>
		</dl>
			<dl  style="heigth:10px;">
			<dt   style="width:35px;">性别:</dt>
			<dd  id='sex_0'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:35px;">年龄:</dt>
			<dd  id='age_0'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检次数:</dt>
			<dd  id='cishu_0'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检时间:</dt>
			<dd  id='tj_time_0'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt   style="width:65px;;">体检套餐:</dt>
			<dd  id='tj_exam_set_0'></dd>
		</dl>
		<dl	style="heigth:10px;">
			<dt   style="width:65px;">体检金额:</dt>
			<dd id="amount_0"></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt	  style="width:65px;;">阳性发现:</dt>
			<dd  id='yxzb_0'></dd>
		</dl>
    </div>
</fieldset>
<fieldset style="width:30.7%;height:97%;float: left;border:1px solid #ff9900;margin-left:1%;margin-top:8px;"  >
  <legend   style="color:#ff9900 "><strong>上一次体检</strong></legend> 
    <div id="shou_2"		   class="formDiv">
    		<dl  style="heigth:10px;"> 
			<dt  style="width:35px;">姓名:</dt>
			<dd  id='user_name_1'></dd>
		</dl>
			<dl  style="heigth:10px;">
			<dt   style="width:35px;">性别:</dt>
			<dd  id='sex_1'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:35px;">年龄:</dt>
			<dd  id='age_1'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检次数:</dt>
			<dd  id='cishu_1'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检时间:</dt>
			<dd  id='tj_time_1'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt   style="width:65px;;">体检套餐:</dt>
			<dd  id='tj_exam_set_1'></dd>
		</dl>
		<dl	style="heigth:10px;">
			<dt   style="width:65px;">体检金额:</dt>
			<dd id="amount_1"></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt	  style="width:65px;;">阳性发现:</dt>
			<dd  id='yxzb_1'></dd>
		</dl>
    </div>
 </fieldset>
 <fieldset style="width:30.5%;height:97%;float: left;border:1px solid #ff9900;margin-left:1%;margin-top:8px;"  >
  <legend	 style="color:#ff9900 "	><strong>上上次体检</strong></legend> 
    <div id="shou_3"	   class="formDiv">
    		<dl  style="heigth:10px;"> 
			<dt  style="width:35px;">姓名:</dt>
			<dd  id='user_name_2'></dd>
		</dl>
			<dl  style="heigth:10px;">
			<dt   style="width:35px;">性别:</dt>
			<dd  id='sex_2'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:35px;">年龄:</dt>
			<dd  id='age_2'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检次数:</dt>
			<dd  id='cishu_2'></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt  style="width:65px;;">体检时间:</dt>
			<dd  id='tj_time_2'></dd>
		</dl>
		
		<dl  style="heigth:10px;">
			<dt   style="width:65px;">体检套餐:</dt>
			<dd  id='tj_exam_set_2'></dd>
		</dl>
		<dl	style="heigth:10px;">
			<dt   style="width:65px;">体检金额:</dt>
			<dd id="amount_2"></dd>
		</dl>
		<dl  style="heigth:10px;">
			<dt	  style="width:65px;">阳性发现:</dt>
			<dd  id='yxzb_2'></dd>
		</dl>
    </div>
   </fieldset>
</body>
