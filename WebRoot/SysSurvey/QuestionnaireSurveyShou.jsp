<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/SysSurvey/QuestionnaireSurveyShou.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
</script>
<body   class="easyui-layout">
<!-- 体检用户ID -->
<input type="hidden"  value="<s:property value='customer_id'/>"   id="s_id"   /> 
	<div data-options="region:'north',title:'',split:true" style="height:70px;text-align: center;font-size:25px;padding-top: 15px">体检系统用户问卷调查</div>   
	<div  id="zuo_hyxx" data-options="region:'west',title:'会员信息',split:true" style="width:200px;padding-left:8px;"  class="formDiv">
		<dl  style="padding-left: 0px">
			<dt style="width: 30px;text-align: left;"  >姓名:</dt>
			<dd  style="width: 130px;"  id="s_name">
				<s:property value='s_user_name'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt style="width: 30px;text-align: left;"   >性别:</dt>
			<dd style="width: 130px;"  id="s_sex">
				<s:property value='s_sex'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt  style="width:30px;text-align: left;" >年龄:</dt>
			<dd  style="width: 130px;"  id="s_age">
				<s:property value='s_age'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt   style="width: 30px;text-align: left;">单位:</dt>
			<dd  style="width: 135px;">
				<s:property value='s_com_name'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt   style="width: 30px;text-align: left;">电话:</dt>
			<dd   style="width: 120px;">
				<s:property value='s_phone'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt  style="width: 55px;text-align: left;">人员类型:</dt>
			<dd  style="width: 110px;">
				<s:property value='s_data_name'/>
			</dd>
		</dl>
		<dl style="padding-left: 0px">
			<dt  style="width: 55px;text-align: left;">体检套餐:</dt>
			<dd  style="width: 110px;">
				<s:property value='s_set_name'/>
			</dd>
		</dl>
	</div>   
	<div data-options="region:'center',title:'健康问卷'"  id="wj_neirong" style="padding:5px;background:#eee;">
		<input type="hidden"  value="" />
	</div>  
	<div data-options="region:'south',split:true" style="height:60px;padding-top:10px;">
		<div class="inner-button-box"  style="text-align: center;">
		    <a href="javascript:savewenjuan();" class="easyui-linkbutton c6" style="width:100px;">提交问卷</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close();">退出</a>
		    
		</div>
	</div>   
</body>
</html>