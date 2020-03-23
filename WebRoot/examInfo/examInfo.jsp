<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtree.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/examInfo/examInfo.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<body style="height: 100%">

	<%-- <input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>"> --%>
	<input type="hidden" id="company_id">
	<input type="hidden" id="tatch_id">
	<fieldset style="margin: 5px; padding-right: 0; height: 80px;">
		<legend>
			<strong>人员查询</strong>
		</legend>
		<div class="user-query">
			<dl>
				<dt style="width: 80px">
					<input id="exam_num_chkItem" name="exam_num_chkItem"
						type="checkbox" /><s:text name="tjhname"/>
				</dt>
				<dd>
					<input class="textinput" type="text" id="exam_num" maxlength="45"
						style="height: 26px; width: 120px;ime-mode: disabled;" />
			
				
				<dt style="width:50px">
					<input id="user_name_chkItem" name="user_name_chkItem"
						type="checkbox" />姓名
				</dt>
				<dd>
					<input class="textinput" type="text" id="user_name" maxlength="50"
						style="height: 26px; width: 120px;" />
				</dd>
				<dt style="width:50px">
					<input id="phone_chkItem" name="phone_chkItem" type="checkbox" />手机
				</dt>
				<dd>
					<input class="textinput" type="text" id="phone" maxlength="45"
						style="height: 26px; width: 120px;" />
				</dd>
					<dt style="width:80px">
					<input id="id_num_chkItem" name="id_num_chkItem" type="checkbox" />身份证
				</dt>
				<dd>
					<input class="textinput" type="text" id="id_num" maxlength="45"
						style="height: 26px; width: 140px;" />
				</dd>
				
				<dt style="height:26px;width:80px;"><input id="chkItem_date" name="chkItem" type="checkbox" value="chkItem_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type="text" id="start_date" value="<s:property value="model.start_date"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type="text" id="end_date"   value="<s:property value="model.end_date"/>" style="width:100px;height:26px;"></input></dd>
				
			</dl>
			<dl>
				<dt style="width: 80px">
					<input id="arch_num_chkItem" name="arch_num_chkItem"
						type="checkbox" /><s:text name="dahname"/>
				</dt>
				<dd>
					<input class="textinput" type="text" id="arch_num" maxlength="45"   style="height: 26px; width: 120px;" />
				</dd>
				<dt style="width:50px">
					<input id="com_Name_chkItem" name="com_Name_chkItem"
						type="checkbox" />单位
				</dt>
				<dd>
					<input class="easyui-textbox" type="text" id="com_Name"
						maxlength="80" style="height: 26px; width:300px;" />
						<div id="com_name_list_div" style="display: none;margin-left:180px;"
						onmouseover="select_com_list_mover()"
						onmouseout="select_com_list_amover()"></div>
				</dd>
				<dt style="width:80px">
					<input id="conn_rylb_chkItem" name="conn_rylb_chkItem"
						type="checkbox" />人员类型
				</dt>
				<dd>
					<select class="easyui-combobox" id="conn_rylb" name="conn_rylb"
						style="height: 26px; width: 140px;"
						data-options="height:26,width:100,panelHeight:'auto'"></select>
				</dd>
				
				 <dt style="width:80px">
					<input id="conn_is_Active" name="conn_is_Active"
						type="checkbox" />人员状态
				</dt>
					<dd>
						<select class="easyui-combobox" id="is_Active" name="is_upload"	
						data-options="height:26,width:100,panelHeight:'auto'">
							<option value="Y">未删除</option>
							<option value="N">已删除</option>
						</select>
					</dd>
				<dd>
					<a href="javascript:getuser();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="width: 100px;">查询</a>
				</dd>
			</dl>
		</div>
	</fieldset>
	<fieldset style="margin: 5px; padding-right: 0; height: 80%">
		<legend>
			<strong>人员列表</strong>
		</legend>
		<table id="groupusershow">
		</table>
	</fieldset>
	<div id="dlg-custedit" class="easyui-dialog"
		data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-custshow" class="easyui-dialog"
		data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-hsprintln" class="easyui-dialog"
		data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
</body>