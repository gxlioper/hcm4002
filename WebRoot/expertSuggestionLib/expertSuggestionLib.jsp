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
<script type="text/javascript" src="<%=request.getContextPath()%>/expertSuggestionLib/expertSuggestionLib.js?randomId=<%=Math.random()%>"></script>
<body>
 <fieldset style="margin:5px;padding-right:0;height: 50px;">
<legend><strong>专科建议查询</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:30px">科室</dt>
					<dd>
						<input class="easyui-combobox" id="dep_id" maxlength="80"   style="height:26px;width:140px;"
							data-options="valueField:'id',textField:'text'"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</dd>
					<dt style="width:60px">建议名词</dt>
					<dd><input class="textinput"   type="text" id="item_name" maxlength="45"  style="height:26px;width:140px;"/>&nbsp;&nbsp;
					</dd>
					<dd><a href="javascript:getItemResultLib();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>专科建议列表</strong></legend> 
      <table id="itemResultLibShow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>