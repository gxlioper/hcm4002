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
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termdecchargesshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batchid"/>">
<input type="hidden" id="acc_num" value="<s:property value="model.acc_num"/>">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:280px;" ><s:property value="model.comname"/></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:280px;" ><s:property value="model.batch_name"/>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				    <dd style="height:20px;width:140px;" >结算单号：</dd>
					<dd style="height:20px;width:280px;" ><s:property value="model.acc_num"/></dd>
				</dl>
				<dl>
					<dd style="height:20px;width:140px;">应收金额</dd>
					<dd style="height:20px;width:280px;" ><font id="ysje" style="font-weight:bold;font-style:italic;">0</font>(元)</dd>
					<dd style="height:20px;width:140px;" >实收金额</dd>
					<dd style="height:20px;width:280px;" ><font id="ssje" style="font-weight:bold;font-style:italic;">0</font>(元)</dd>
				    <dd style="height:20px;width:140px;" >已减免金额</dd>
					<dd style="height:20px;width:280px;" ><font id="yhje" style="font-weight:bold;font-style:italic;">0</font>(元)</dd>
				</dl>
				<dl>
				 <dd><input  checked="checked" name="decchargtype" onclick="gettermatotleClistGrid()" type="radio" value="C" /></dd>
				 <dt style="height:20px;width:140px;">按照缴费项目减免</dt>
		         <dd><input  name="decchargtype" type="radio" onclick="gettermatotleElistGrid()" value="E" /></dd>
		         <dt style="height:20px;width:140px;">按照体检人员减免</dt>
		          <dd><input  name="decchargtype" type="radio" onclick="gettermatotlePlistGrid()" value="P" /></dd>
		         <dt style="height:20px;width:140px;">按照人员平均减免</dt>
		         <dt style="height: 26px; width: 100px;" id="jmname">减免金额</dt>
				 <dd>
					<input class="easyui-textbox" type=text id="decamt"  style="width: 140px; height: 26px;">(元)</input>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="javascript:termdecamtdo();"
						class="easyui-linkbutton"
						style="height: 26px; width: 80px;">确定减免</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input type="button" name="Submit2" onclick="javascript:window.close();" value="关闭窗口" />
				</dd>
				</dl>
				<dl>
					<dt style="height: 26px; width: 80px;"><input id="ck_selectgroup_id" type="checkbox"/>选择分组</dt>
					<dd>
						<select class="easyui-combobox" id="selectgroup_id"
							name="selectgroup_id"
							data-options="height:26,width:240,panelHeight:'auto'"></select>
					</dd>
					<dt style="height:26px;width:80px;"><input id="ck_set_name" type="checkbox"/>体检套餐</dt>					
					 <dd><select class="easyui-combobox" id="set_name"
					data-options="height:26,width:200"></select></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_conn_rylb" type="checkbox" value="conn_rylb"/>人员类别</dt>	
                    <dd><select class="easyui-combobox" id="conn_rylb" name="conn_rylb"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:80px;"><input id="ck_tjlx" type="checkbox" value="tjlx"/>体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:100,panelHeight:'auto'"></select>
					</dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;height:27px;" onclick="javascript:chaxun();">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检结算减免列表</strong></legend>
      <table id="teamAccountExamCEShow">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>