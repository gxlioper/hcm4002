<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />


<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/queryInterfaceResult/sampleresultlist.js?randomId=<%=Math.random()%>"></script>
<style type="text/css">
.scolor{color:#fff;}
</style>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>';
</script>
 
<body>
<div class="easyui-layout" fit="true">
<div  data-options="region:'north'" style="height:100%;">
<input type="hidden" id="company_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;"><s:text name="tjhname"/></dt>
					<dd>
					<input  type="text" class="textinput" id="exam_num"    style="height:26px;width:100px;ime-mode: disabled;" />
					</dd> 
					<dt style="height:26px;width:60px;">姓名</dt>
					<dd>
					<input  type="text" class="textinput" id="user_name"    style="height:26px;width:100px;" />
					</dd>
					<dt style="height:26px;width:60px;">采集日期</dt>
			         <dd><input  type="text" class="easyui-datebox" id="sample_datestart"    style="height:26px;width:100px;" value="<s:property value="sample_datestart"/>"/></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input  type="text" class="easyui-datebox" id="sample_dateend"    style="height:26px;width:100px;" value="<s:property value="sample_dateend"/>"/>
			         </dd> 
			         <dt style="width:60px;">标本类型</dt>
		    <dd>
		       <select class="easyui-combobox" id="sample_type" style="width:120px;height:26px;"></select>
		    </dd>
		      <dd><a href="javascript:getSampleResultList();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="height:26px;width:80px;">清空</a></dd>
             
				</dl>
				
			</div>	
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:77%;">
<legend><strong>采集信息</strong></legend>
      <table id="sampleResultShow" >
      </table>	
 </fieldset>
 <div  class="user-query">
 	<dl>
	 	<dt style="text-align: right;">总人数:</dt><dd id = "zrs"  style="text-align: left"></dd>
	 	<dt style="text-align: right;">已采管数:</dt><dd id="yc" style="text-align: left"></dd>
	 	<dt style="text-align: right;">血样管数:</dt><dd id='xygs' style="text-align: left"></dd>
	 	<dt style="text-align: right;">血样人数:</dt><dd id="xyrs" style="text-align: left"></dd>
 	</dl>
 </div>
  </div>
  </div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>