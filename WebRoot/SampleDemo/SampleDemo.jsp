<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/scripts/spectrum-master/spectrum.css"/> 

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/spectrum-master/spectrum.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/SampleDemo/SampleDemo.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","45");
})
</script>
<body class="easyui-layout" style="height: 99%">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
<input type="hidden" id="demo_type" value="">

<div data-options="region:'west',title:'样本分类',split:true" style="width:15%;">
	<div id='some_tree' data-options="animate:true"></div>
</div>
<div  data-options="region:'center'">
	<fieldset style="margin:5px;padding-right:0;">
	   <legend><strong>检验样本查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width: 60px" >样本编码</dt>
					<dd><input class="easyui-textbox"  type="text" id="num"  style="height:26px;width:140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</dd>
					<dt style="width: 60px" >样本名称</dt>
					<dd><input class="easyui-textbox"  type="text" id="demo_name"   style="height:26px;width:140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</dd>
					<dt style="width: 60px" >标本类型</dt>
					<dd><input type="text" id="yblx" class="easyui-combobox"/></dd>
					
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" checked="checked" value="Y"/>启用</dt>		
					<dt style="height:26px;width:65px;"><input id="chkItem" name="chkItem" type="checkbox" value="N"/>停用</dt>	
					
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
					<!-- <dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd> -->
				</dl>
			</div>
	 </fieldset>
	 <fieldset style="margin:5px;padding-right:0;">
	 <legend><strong>检验样本列表</strong></legend> 
	      <table id="groupusershow" class="easyui-datagrid" >
	      </table>	
	 </fieldset>
	 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg_show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	 <div id="dlg_demo_type" class="easyui-dialog"  data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50"></div>
</div>  
</body>