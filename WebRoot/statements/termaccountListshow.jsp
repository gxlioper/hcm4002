<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termaccountListshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body>
<input type="hidden" id="teamaccounturl" value="<s:property value="model.teamaccounturl"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batchid"/>">
<input type="hidden" id="acc_num" value="<s:property value="model.acc_num"/>">
<input type="hidden" id="feeresourceflag" value="<s:property value="feeresourceflag"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:200px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.comname"/></font></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:140px;" ><font id="batchname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.batch_name"/></font>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				    <dd style="height:20px;width:80px;" >结算单号：</dd>
					<dd style="height:20px;width:100px;" ><font id="accnum" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.acc_num"/></font></dd>
				    <dt style="height:26px;width:100px;">结算日期</dt>
		 	        <dd><s:property value="model.acc_date"/></dd>
		 	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input type="button" name="Submit2" onclick="windowcloses();" value="关闭窗口" />
				</dl>
				
			</div>
 </fieldset>
 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员结算明细</strong></legend>
<p>
&nbsp;&nbsp;&nbsp;&nbsp;总人次：<font id="zrc" style="font-weight:bold;font-style:italic;">0</font>(单位:人次)
			&nbsp;&nbsp;应收：<font id="ysje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
			&nbsp;&nbsp;实收：<font id="ssje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
			&nbsp;&nbsp;优惠：<font id="yhje" style="font-weight:bold;font-style:italic;">0</font>(单位:元)
      <table id="teamAccountExamShow">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>