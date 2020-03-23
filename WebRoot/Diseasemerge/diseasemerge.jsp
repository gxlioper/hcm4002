<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Diseasemerge/diseasemerge.js?randomId=<%=Math.random()%>"></script>
<body style="height:99%;">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>合并疾病查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width: 60px" >疾病名称</dt>
					<dd><input class="easyui-textbox"  type="text" id="disease_name"  style="height:26px;width:140px;"/>&nbsp;&nbsp;&nbsp;&nbsp;</dd>
					<dd><a href="javascript:diseasemerge();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height: 85%">
<legend><strong>合并疾病列表</strong></legend> 
      <table id="diseasemergetable" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" ></div>
  </body>