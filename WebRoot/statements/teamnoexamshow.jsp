<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.left1 {
	width: 49%;
	height: auto;
}

.right1 {
	width: 49%;
	height: auto;
	margin-left: 10px;
}

.left1, .right1 {
	float: left;
}

.pop_up_box_lis {
	border: 1px solid #ccc;
	background: #fff;
	padding: 0 0px 10px;
	position: absolute;
	font-size: 12px;
	display: none;
}

.title {
	text-align: center;
	cursor: move;
	height: 30px;
	line-height: 30px;
	margin-bottom: 15px;
	background: #359BCC;
	border-bottom: 1px solid #ccc;
	color: #FFFFFF;
	font-size: 16px;
}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/teamnoexamshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batchid"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:280px;" ><s:property value="model.comname"/></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:280px;" ><s:property value="model.batch_name"/>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				</dl>
				
				<dl>
				 <dd><input  checked="checked" name="decchargtype" onclick="gettermatotleElistGrid()" type="radio" value="E" /></dd>
				 <dt style="height:20px;width:140px;">未检人员</dt>
		         <dd><input  name="decchargtype" type="radio" onclick="gettermatotleClistGrid()" value="C" /></dd>
		         <dt style="height:20px;width:140px;">未检项目</dt>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input type="button" name="Submit2" onclick="javascript:window.close();" value="关闭窗口" />
				</dd>
				
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin: 5px; padding-right: 0;">									
						<div class="left1">
						<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>列表</strong>
						</legend>					
						<table id="teamAccountExamCEShow" class="easyui-datagrid">
						</table>
					</fieldset>
					</div>
                        <div class="right1">
                        <fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>细项</strong>
						</legend>
			             <div id="teamAccountItemList"></div>
					</fieldset></div>
			</fieldset>
<div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 </body>

