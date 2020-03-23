<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jiankz/huovshoplist.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:100%;">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:80px;">健康项目代码</dt>
					<dd>
						<input  type="text" class="textinput" id="hop_data_code"    style="height:26px;width:120px;" />
					</dd>
					<dt style="width:80px;">健康项目名称</dt>
					<dd>
						<input  type="text" class="textinput" id="hop_data_name"    style="height:26px;width:120px;" />
					</dd>
					<dt style="width:60px;">项目代码</dt>
					<dd>
						<input  type="text" class="textinput" id="huo_data_code"    style="height:26px;width:120px;" />
					</dd>
					<dt style="width:60px;">项目名称</dt>
					<dd>
						<input  type="text" class="textinput" id="huo_data_name"    style="height:26px;width:120px;" />
					</dd>
					<dt style="width:60px;">项目类别</dt>
		    <dd>
		       <select class="easyui-combobox" id="data_type" style="width:120px;height:26px;"></select>
		    </dd>
					<dd><a href="javascript:getHopVsHuoList();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:80%;">
<legend><strong>项目列表信息</strong></legend>
      <table id="hopvshuolistshow" class="easyui-datagrid" >
      </table>	
 </fieldset>
  </div>
  </div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>