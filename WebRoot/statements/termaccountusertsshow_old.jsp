<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
#main {	width: auto;	height: auto;}
#left {	width: 45%;	height: auto;}
#right {	width: 45%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/termaccountusertsshow.js?randomId=<%=Math.random()%>"></script>
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
<input type="hidden" id="batchcompany_id">
<fieldset style="margin:5px;padding-right:0;">
    <legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:140px;">合同单位名称 </dd>
					<dd style="height:20px;width:280px;" ><font id="companyname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.comname"/></font></dd>
					<dd style="height:20px;width:140px;" >体检任务名称：</dd>
					<dd style="height:20px;width:280px;" ><font id="batchname" style="color:red;font-weight:bold;font-style:italic;"><s:property value="model.batch_name"/></font>(<a href="javascript:f_batchshow()">查看</a>)</dd>
				</dl>
			</div>
 </fieldset>
	<fieldset style="margin: 5px; padding-right: 0;">
		<legend>
			<strong>信息检索</strong>
		</legend>
		<div class="user-query">
			<dl>
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" checked type="checkbox" value="com_Name"/>单位名称</dt>
				<dd>
					<input type="text" id="com_Name" value="" style="height:20px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:0px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
				</dd>
				
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="group_id"/>任务分组</dt>					
				<dd>
				    <select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
			    </dd>  
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="set_id"/>分组套餐</dt>					
				<dd>
				    <select class="easyui-combobox" id="set_id" name="set_id"	data-options="height:26,width:200,panelHeight:'auto'"></select>
			    </dd> 
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="sex"/>性别</dt>					
				<dd>
				    <select class="easyui-combobox" id="sex" name="sex"	data-options="height:26,width:60,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '全部',  
                   value: '',  
                   selected:true},{  
                   label: '男',  
                   value: '男'},
                   {label: '女',  
                   value: '女'}]"></select>
			    </dd> 
		       </dl>
               <dl>
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/>体检编号</dt>
				<dd>
					<input class="easyui-textbox" type=text id="exam_num"  style="width: 140px; height: 26px;"></input>
				</dd>
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
				<dd>
					<input class="easyui-textbox" type=text id="custname"  style="width: 140px; height: 26px;"></input> 
					
				</dd>
				
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_status"/>体检状态</dt>					
				<dd>
				    <select class="easyui-combobox" id="exam_status" name="exam_status"	data-options="height:26,width:120,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '未报到',  
                   value: 'Y',  
                   selected:true},{  
                   label: '已登记',  
                   value: 'D'},
                   {label: '检查中',  
                   value: 'J'},
                   {label: '已终检',  
                   value: 'Z'}]"></select>    
			    </dd> 
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="rylb" type="checkbox" value="rblb"/>人员类别</dt>
			     <dd><select class="easyui-combobox" id="rylb" name="rylb"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
					
					 <input id="chkItem" name="chkItem" type="checkbox" value="isnotpay"/>包含弃检项目
                   <a
						href="javascript:getteamExamInfoShowGrid();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 80px;">查询</a>
					<a
						href="javascript:termcustadd();"
						class="easyui-linkbutton"
						style="height: 26px; width: 80px;">保存</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input type="button" name="Submit2" onclick="javascript:window.close();" value="关闭窗口" />
			    </dd>
			</dl>
		</div>
	</fieldset>
	<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择套餐</strong>
	</legend>

	<div id="main">
			套餐列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
				 type="text" id="tclist" value="" />
			<div id=com_name_list_div1 style="display:none;height:220px;width:400px;;overflow-y:scroll;margin-left:0px;" 
				onmouseover="select_set_list_mover()"
				onmouseout="select_set_list_amover()"></div>
			已选套餐
			<div id="selectctlist"></div>
	</div>
</fieldset>

<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>选择体检项目</strong>
	</legend>
	<div id="main">
		<div id="left">
			项目列表<input type="text" id="itemname" name="itemname" value="" />
			<div id="changitemlist"></div>
		</div>
		<div id="right">
			已选项目
			<div id="selectchangitemlist"></div>
		</div>

	</div>

</fieldset>
	<fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员信息</strong></legend>
      <table id="teamExamInfoShow">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>