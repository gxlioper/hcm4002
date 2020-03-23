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

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/diseaseLogic/dep_disease_logic.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","45");
})
</script>
<body  class="easyui-layout"  style="height: 99%">
<input typet="hidden"  id='web_Resource' value='<s:property value="web_Resource"/>' />
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>"/>
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>"/>
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>"/>
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>"/>
<input type="hidden" id="web_Resource" value="<s:property value='webr'/>"/>
<div data-options="region:'west',title:'科室',split:true" style="width:15%;">
<div id='some_tree'></div>
</div>   
<div data-options="region:'center'" >
		 <fieldset style="margin:5px;">
		<legend><strong>疾病逻辑查询</strong></legend>
					<div class="user-query">
						<dl>
							<dt style="width:140px;text-align: right;margin-right: 5px">阳性指标/疾病名称</dt>
							<dd><input class="textinput"  type="text" id="c_logic_name"   style="height:26px;width:100%;"/></dd>
							<dt style="width:140px;text-align: right;margin-right: 5px">阳性指标/疾病编码</dt>
							<dd><input class="textinput"  type="text" id="c_logic_num"   style="height:26px;width:100%;"/></dd>
							<dt style="text-align: right;padding-right: 10px">疾病类型:</dt>
							<dd>
								<select id="c_logic_type" class="easyui-combobox"  style="width:100px;height:26px;"  data-options='panelHeight:80'>   
											    <option value=''>全部</option>   
											    <option value='Y'>阳性指标</option>   
											    <option value='N'>疾病</option>   
								</select>  
							</dd>
							<dd><a href="javascript:chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
						</dl>
					</div>
		 </fieldset>
		 <fieldset style="margin:5px;padding-right:0;height:85%">
		<legend><strong>疾病逻辑列表</strong></legend> 
		      <table id="groupusershow" class="easyui-datagrid"  >
		      </table>	
		 </fieldset>
		 <div id="dlg-custedit" class="easyui-dialog"  data-options="width:'60%',closed: true,cache: false,modal:true,top:50"  style="min-height:300px"></div>
		 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
		 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</div>  
  </body>