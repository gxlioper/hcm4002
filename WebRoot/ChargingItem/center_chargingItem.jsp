<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--不保存缓存  -->
<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
<meta HTTP-EQUIV="expires" CONTENT="0">

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ChargingItem/center_chargingItem.js"></script>
<body  class="easyui-layout"   style="height: 99%;">
<input typet="hidden"  id='web_Resource' value='<s:property value="web_Resource"/>' />
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>"/>
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>"/>
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>"/>
	<div  data-options="region:'west',title:'科室',split:true" style="width:200px;">
		<div id='b_some_tree' data-options="animate:true"></div>
	</div>   
	<div id="contDep" data-options="region:'center',title:'体检中心收费项目',split:true" style="width:300px;" >
		  <div id="b_getDepItemDetail" class="easyui-tabs" data-options="tabHeight:20" border="false"></div> 
	</div>  
	<!-- 项目库 -->
    <div data-options="region:'east',split:true,collapsed:true,fit:true" title="收费项目库"  >
    	 <div class="easyui-layout" data-options="fit:true">   
   	 		<div  data-options="region:'west',title:'科室',split:true" style="width:200px;">
				<div id='some_tree' data-options="animate:true"></div> 
			</div> 
			<div  data-options="region:'center',title:'收费项目',split:true" >
	 			 <div id=getDepItemDetail class="easyui-tabs" data-options="tabHeight:20"  border="false"></div> 
			</div>
		</div>    
    </div>   
 </body>
