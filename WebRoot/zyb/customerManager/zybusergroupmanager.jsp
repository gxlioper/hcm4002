<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main {	width: auto;	height: auto;}
#left {	width: 67%;	height: auto;}
#right {	width: 30%;	height: auto;	margin-left: 10px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/>  

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/customerManager/zybusergroupmanager.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body style="height:100%;">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="barexeurl" value="<s:property value="model.time1"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.time2"/>">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="batch_id" value="0">
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
	<div id="main">
		<div id="left">

			<fieldset style="margin: 5px; padding-right: 0;">
				<legend>
					<strong>信息检索</strong>
				</legend>
				<div class="user-query">
					<dl>
						<dt style="height: 26px; width: 60px;">选择单位</dt>
						<dd>
							<input type="text" id="com_Name" value=""
								style="height: 22px; width: 140px;" />
							<div id="com_name_list_div1"
								style="display: none; margin-left: -20px;"
								onmouseover="select_com_list_mover()"
								onmouseout="select_com_list_amover()"></div>
						</dd>
						<dt style="height: 26px; width: 60px;">体检状态</dt>
						<dd>
							<select class="easyui-combobox" id="status" name="status"
								data-options="height:26,width:100,panelHeight:'auto'"></select>
						</dd>
						<dt style="height: 26px; width: 50px;"><s:text name="dahname"/></dt>
						<dd>
							<input class="easyui-textbox" type="text" id="arch_num" value=""
								style="height: 26px; width: 100px;" />
						</dd>
						<dt style="height: 26px; width: 45px;">婚否</dt>
						<dd>
							<select class="easyui-combobox" id="is_Marriage"
								name="is_Marriage"
								value="<s:property value="model.is_Marriage"/>"
								data-options="height:26,width:60,panelHeight:'auto'"></select>
						</dd>
						<dt style="height: 26px; width: 60px;">预约日期</dt>
						<dd>
							<input class="easyui-datebox" type=text id="register_date"
								style="width: 100px; height: 26px;"></input>
					</dl>

					<dl>
						<dt style="height: 26px; width: 60px;">体检任务</dt>
						<dd>
							<input type="text" id="tjrw" value=""
								style="height: 22px; width: 140px;" />
							<div id="com_name_list_div"
								style="display: none; margin-left: -20px;"
								onmouseover="select_batchcom_list_mover()"
								onmouseout="select_batchcom_list_amover()"></div>
						</dd>
						
						<dt style="height: 26px; width: 60px;">选择部门</dt>
						<dd>
							<select class="easyui-combobox" id="levelss" name="levelss"
								data-options="height:26,width:100,panelHeight:'auto'"></select>
						</dd>

						<!--dt>身份证号</dt>
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:140px;"/></dd-->
						<dt style="height: 26px; width: 50px;">姓名</dt>
						<dd>
							<input class="easyui-textbox" type="text" id="custname" value=""
								style="height: 26px; width: 100px;" />
						</dd>
						<dt style="height: 26px; width: 45px;">性别</dt>
						<dd>
							<select class="easyui-combobox" id="sex" name="sex"
								data-options="height:26,width:60,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '全部',  
                   value: '',  
                   selected:true},{  
                   label: '男',  
                   value: '男'},
                   {label: '女',  
                   value: '女'}]"></select>
						</dd>
						<dt style="height: 26px; width: 60px;">人员类别</dt>
						<dd>
							<select class="easyui-combobox" id="rylb" name="rylb"
								data-options="height:26,width:60,panelHeight:'auto'"></select>
						</dd>
						<dd>
							<a href="javascript:getgroupuserGrid();"
								class="easyui-linkbutton c6 l-btn l-btn-small"
								style="width: 100px;">查询</a>
						</dd>
					</dl>
				</div>
			</fieldset>
			<fieldset style="margin: 5px; padding-right: 0;">
				<legend>
					<strong>体检人员列表</strong>
				</legend>
				<table id="groupusershow">
				</table>
			</fieldset>
		</div>

		<div id="right"  class="formDiv">
			<fieldset style="margin: 5px; padding-right: 0;">
				<legend>
					<strong>从业信息</strong>
				</legend>
				<dl>
					<dt style="height: 26px; width: 60px;">行业:</dt>
					<dd style="height: 26px; width: 120px;" id="hangye"></dd>
					<dt style="height: 26px; width: 60px;">从业行业:</dt>
					<dd style="height: 26px; width: 120px;" id="hangyeshow"></dd>				
				</dl>
				<dl>
				    <dt style="height: 26px; width: 60px;">工种:</dt>
					<dd style="height: 26px; width: 120px;" id="gongzhong"></dd>
					<dt style="height: 26px; width: 60px;">从业工种:</dt>
					<dd style="height: 26px; width: 120px;" id="gongzhongshow" ></dd>								
				</dl>
				<dl>							
					<dt style="height: 26px; width: 60px;">工龄(年):</dt>
					<dd style="height: 26px; width: 120px;" id="gongling"></dd>
					<dt style="height: 26px; width: 90px;">接害工龄(年):</dt>
					<dd style="height: 26px; width: 60px;" id="jhgongling"></dd>
				</dl>
				<dl>
					<dt style="height: 26px; width: 60px;"></dt>
					<dd style="height: 26px; width: 120px;" ></dd>	
					<dt style="height: 26px; width: 60px;"></dt>
					<dd style="height: 26px; width: 120px;"  ></dd>							
				</dl>
			</fieldset>
			
			<fieldset style="margin: 5px; padding-right: 0;">
				<legend>
					<strong>职业危害因素</strong>
				</legend>
				<table id="zywhysset">
				</table>
			</fieldset>
			
			<fieldset style="margin: 5px; padding-right: 0;">
				<legend>
					<strong>体检项目</strong>
				</legend>
				<table id="zybGselectItemlist">
				</table>
			</fieldset>
		</div>
	</div>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 920,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-printitem" class="easyui-dialog"  data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50"></div>
  </body>