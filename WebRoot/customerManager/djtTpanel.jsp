<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.main {
	width: auto;
	height: auto;
}

.left {
	width: 15%;
	height: auto;
}

.right {
	width: 84%;
	height: auto;
	margin-left: 10px;
}

.left, .right {
	float: left;
}

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
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/dtree.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
	var dahname = '<s:text name="dahname"/>';
	var tjhname = '<s:text name="tjhname"/>';
</script>
</head>
<body>
	<input type="hidden" id="barexeurl"value="<s:property value="model.time1"/>"/> 
	<input type="hidden" id="djdexeurl" value="<s:property value="model.time2"/>"/>
	<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>" />
	<input type="hidden" id="isovertype" value="<s:property value="model.isovertype"/>" />
	<input type="hidden" id="ischecktype" value="<s:property value="model.ischecktype"/>" />
	<input type="hidden" id="isaccounttype" value="<s:property value="model.isaccounttype"/>" />
	<input type="hidden" id="isunaccounttype" value="<s:property value="model.isunaccounttype"/>" />
	<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>" />
	<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>" />
	<input type="hidden" id="is_batch_check"value="<s:property value="model.status"/>"/>
	<input type="hidden" id="ttog" value="<s:property value="model.ttog"/>">
    <input type="hidden" id="gtot" value="<s:property value="model.gtot"/>"> 
    <input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>"> 
    <input type="hidden" id="group_status" value="<s:property value="model.grouptot"/>"> 
	<input type="hidden" id="company_id" />
	<input type="hidden" id="batch_id" />
	<input type="hidden" id="group_id" />
	<input type="hidden" id="comname" />
	<div class="main">
			<div  class="easyui-layout" style="height: 640px">
				<div data-options="region:'west',title:'单位信息 ',split:true" style="width:200px;">
					<div style="margin-left: 7px;">
					    <input id="com_name" onkeyup="reopen();"  maxlength="20" style="background-position:right; background-repeat:no-repeat;height:20px; width: 160px;" />
					    <!-- <a href="javascript:void(0)" class="easyui-linkbutton c6" style="height:20px;">单位查询</a>  --> 
					    <div id="append" style="position: absolute; background-color: white;"></div>  
					    	 <div id="depttree"></div>
					</div>
				    	 
				</div>
				<div data-options="region:'center',title:'人员列表'" style="padding:5px;">
			    <div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="体检任务管理" style="padding: 5px;">
					<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>体检任务</strong>
						</legend>
						<table id="fanganlist"></table>
					</fieldset>
					<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>分组列表</strong>
						</legend>
						<table id="grouplist" class="easyui-datagrid">
						</table>
					</fieldset>
					<%-- <fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>分组对应套餐</strong>
						</legend>
						<table id="examsetlist" class="easyui-datagrid">
						</table>
					</fieldset> --%>
					<fieldset style="margin: 5px; padding-right: 0;">									
						<div class="left1">
						<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>分组对应体检人员</strong>
						</legend>					
						<table id="chargitemlist" class="easyui-datagrid">
						</table>
					</fieldset>
					</div>
                        <div class="right1">
                        <fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>分组对应体检项目</strong>
						</legend>
						<div id = 'geren' style="display:none;" >
						&nbsp;&nbsp;收费项目数：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>项
						&nbsp;&nbsp;团体应缴金额：<font id="tyjje" style="font-weight:bold;font-style:italic;">0</font>元
						&nbsp;&nbsp;个人应缴金额：<font id="gyjje" style="font-weight:bold;font-style:italic;">0</font>元
						<br>&nbsp;&nbsp;利润金额：<font id="lrje" style="font-weight:bold;font-style:italic;">0</font>元
						&nbsp;&nbsp;个人实缴金额：<font id="gsjje" style="color:blue;font-weight:bold;font-style:italic;">0</font>元
						&nbsp;&nbsp;个人未缴金额：<font id="gwjje" style="color:red;font-weight:bold;font-style:italic;">0</font>元
						</div>
						<div id = 'group' >
						&nbsp;&nbsp;收费项目数：<font id="groupcount" style="font-weight:bold;font-style:italic;">0</font>项
						&nbsp;&nbsp;分组金额：<font id="groupje" style="font-weight:bold;font-style:italic;">0</font>元
						</div>
			<div id="djtGselectItemlist"></div>
			</fieldset></div>
		      </fieldset>
	          </div>
	          
	          
	          <div title="人员导入管理" style="padding: 5px;">
					<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>信息检索</strong>
						</legend>
						<div class="user-query">
							<dl>
								<dd style="height: 20px;">
									体检任务 <select class="easyui-combobox" id="impselectbatch_id"
										name="impselectbatch_id"
										data-options="height:26,width:140,panelHeight:'auto'"></select>
								</dd>
								<dd style="height: 20px;">
									证件号  <input class="easyui-textbox"  type="text" id="card_num" value="" style="height:26px;width:140px;"/>
								</dd>
								<dd style="height: 20px;">
									姓名  <input class="easyui-textbox"  type="text" id="name" value="" style="height:26px;width:70px;"/>
								</dd>
								<dt style="height: 20px; width: 35px;">性别</dt>
								<dd>
									<select class="easyui-combobox" id="sex" name="sex1"
										data-options="height:26,width:60,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
						                   label: '全部',  
						                   value: '',  
						                   selected:true},{  
						                   label: '男',  
						                   value: '男'},
						                   {label: '女',  
						                   value: '女'}]"></select>
								</dd>
								
								<dt style="height: 20px; width: 35px;">婚否</dt>
								<dd>
									<select class="easyui-combobox" id="is_Marriage1" name="is_Marriage1"
										data-options="height:26,width:80,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
						                   label: '全部',  
						                   value: '',  
						                   selected:true},{  
						                   label: '已婚',  
						                   value: '已婚'},
						                   {label: '未婚',  
						                   value: '未婚'}]"></select>
								</dd>
								
								
									<a href="javascript:getimpcusGrid();"
										class="easyui-linkbutton c6" style="width: 60px;">查询</a>
										
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:putonguserimp();"
										class="easyui-linkbutton" style="width: 110px;">普通人员导入</a>
										
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
											href="javascript:shiyerenuserimp();"
											class="easyui-linkbutton" style="width: 110px;">市属事业单位</a> -->
											
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
											href="javascript:jiguanuserimp();"
											class="easyui-linkbutton" style="width: 110px;">市直机关人员</a> -->
											
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
											href="javascript:lixiuuserimp();"
											class="easyui-linkbutton" style="width: 110px;">市本离休干部</a> -->
											
												
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
											href="javascript:gaizhiuserimp();"
											class="easyui-linkbutton" style="width: 110px;">市属改制单位</a> -->
										
								</dd>
							</dl>
						</div>
					</fieldset>
					<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>人员临时表信息</strong>
						</legend>
						<table id="impusershow" class="easyui-datagrid">

						</table>
					</fieldset>
				</div>

					<div title="团体人员管理" style="padding: 5px;">
					<fieldset style="margin: 5px; padding-right: 0; padding-top: 5px; padding-bottom: 20px; margin-right: 0px">
						<legend>
							<strong>信息检索</strong>
						</legend>
						<div class="user-query">
							<dl>
								<dt style="height: 26px; width: 60px;">体检任务</dt>
								<dd>
									<select class="easyui-combobox" id="selectbatch_id"
										name="selectbatch_id"
										data-options="height:26,width:140,panelHeight:'auto'"></select>
								</dd>
								<dt style="height: 26px; width: 60px;">选择部门</dt>
								<dd>
									<select class="easyui-combobox" id="levelss" name="levelss"
										data-options="height:26,width:100,panelHeight:'auto'"></select>
								</dd>
								<dt style="height: 26px; width: 60px;">
									<s:text name='dahname' />
								</dt>
								<dd>
									<input class="easyui-textbox" type="text" id="arch_num"
										value="" style="height: 26px; width: 100px;" />
								</dd>
								<dt style="height: 26px; width: 60px;">体检状态</dt>
								<dd>
									<select class="easyui-combobox" id="status" name="status"
										data-options="height:26,width:100,panelHeight:'auto'"></select>
								</dd>

								<dt style="height: 26px; width: 35px;">婚否</dt>
								<dd>
									<select class="easyui-combobox" id="is_Marriage"
										name="is_Marriage"
										value="<s:property value="model.is_Marriage"/>"
										data-options="height:26,width:60,panelHeight:'auto'"></select>
								</dd>
								<dt style="height: 26px; width: 60px;">人员类别</dt>
									<dd>
										<select class="easyui-combobox" id="rylb" name="rylb"
											data-options="height:26,width:90,panelHeight:'auto'"></select>
									</dd>
									
								<dt style="height:26px;width:35px;">状态</dt>	
			                    <dd><select class="easyui-combobox" id="djdstatuss" name="djdstatuss"	data-options="height:26,width:100,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
			                   label: '全部',  
			                   value: ''},{  
			                   label: '未打导检单',  
			                   value: 'N'},
			                   {label: '已打导检单',  
			                   value: 'Y'}]"></select></dd>
								<!-- <dt style="height: 26px; width: 35px;">其他</dt>
								<dd>
									<input class="easyui-textbox" type="text" id="others"
										value="" style="height: 26px; width: 80px;" />									
								</dd> -->
							</dl>

							<dl>
								<dt style="height: 26px; width: 60px;">选择分组</dt>
								<dd>
									<select class="easyui-combobox" id="selectgroup_id"
										name="selectgroup_id"
										data-options="height:26,width:140,panelHeight:'auto'"></select>
								</dd>
								<dt style="height: 26px; width: 60px;">体检类别</dt>
								<dd>
									<select class="easyui-combobox" id="tjlx" name="tjlx"
										data-options="height:26,width:100,panelHeight:'auto'"></select>
								</dd>

								<!--dt>身份证号</dt>
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:140px;"/></dd-->
								<dt style="height: 26px; width: 60px;">姓名</dt>
								<dd>
									<input class="easyui-textbox" type="text" id="custname"
										value="" style="height: 26px; width: 100px;" />
								</dd>
								<dt style="height: 26px; width: 60px;">预约日期</dt>
								<dd>
									<input class="easyui-datebox" type=text id="register_date"
										style="width: 100px; height: 26px;"></input>
								</dd>
								
								<dt style="height: 26px; width: 35px;">性别</dt>
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
									<!-- <dt style="height: 26px; width: 60px;">开票部门</dt>
								<dd>
									<input class="easyui-textbox" type="text" id="billdep"
										value="" style="height: 26px; width: 90px;" />
								</dd>
									<dt style="height: 26px; width: 35px;">职务</dt>
									<dd>
										<input class="easyui-textbox" type="text" id="position"
										value="" style="height: 26px; width: 80px;" />
									</dd> -->
									
									<dt  style="height:26px;width:60px;">年龄段</dt>
								<dd>
									<input maxlength="20" class="easyui-textbox" type="text" id="min_age"
										value="0"
										style="height: 26px; width: 65px;" /> -			
									<input class="easyui-textbox" maxlength="20" type="text" id="max_age"
										value="100"
										style="height: 26px; width: 65px;" />
								</dd>	
								<dd><a href="javascript:getgroupuserGrid();" class="easyui-linkbutton c6 l-btn l-btn-small" style="width:63px; height: 26; margin-left: 20px;">查询</a></dd>
							
							</dl>
							
						</div>
					</fieldset>
					<fieldset style="margin: 5px; padding-right: 0;height: 520px;width: 1600px;">
						<legend><strong>人员信息</strong></legend>
						<table id="groupusershow"  style="height: 520px"></table>
					</fieldset>
				</div>
				
				
				<div title="人员预约管理" style="padding: 5px;">
							<fieldset style="margin: 5px; padding-right: 0;">
								<legend>
									<strong>体检人员预约统计列表</strong>
								</legend>
								<table id="custtimelist">
								</table>
							</fieldset>
							<fieldset style="margin: 5px; padding-right: 0;">
								<legend>
									<strong>信息检索</strong>
								</legend>
								<div class="user-query">
									<dl>
										<dt style="height: 26px; width: 60px;">体检任务</dt>
										<dd>
											<select class="easyui-combobox" id="timebatch_id"
												name="timebatch_id"
												data-options="height:26,width:140,panelHeight:'auto'"></select>
										</dd>
										<dt style="height: 26px; width: 60px;">选择分组</dt>
										<dd>
											<select class="easyui-combobox" id="timegroup_id"
												name="timegroup_id"
												data-options="height:26,width:160,panelHeight:'auto'"></select>
										</dd>
										<dt style="height: 26px; width: 70px;">
											预约日期
										</dt>
										<dd>
											<input class="easyui-datebox" type=text
												id="timeregister_date" style="width: 100px; height: 26px;"></input>
											<dt style="height: 26px; width: 40px;">电话</dt>
											<dd>
												<input class="easyui-textbox" type="text" id="timetel"
													value="" style="height: 26px; width: 90px;" />
											</dd>

										<!-- dt>身份证号</dt>
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:140px;"/></dd-->
										<dt style="height: 26px; width: 60px;">
											<s:text name='dahname' />
										</dt>
										<dd>
											<input class="easyui-textbox" type="text" id="timearch_num"
												value="" style="height: 26px; width: 100px;" />
										</dd>
										<dt style="height: 26px; width: 60px;">姓名</dt>
										<dd>
											<input class="easyui-textbox" type="text" id="timecustname"
												value="" style="height: 26px; width: 80px;" />
										</dd>
										<dd>
											<a href="javascript:gettimeuserGrid();"
												class="easyui-linkbutton c6 l-btn l-btn-small"
												style="width: 60px;">查询</a>
										</dd>
									</dl>
								</div>
							</fieldset>
							<fieldset style="margin: 5px; padding-right: 0;">
								<legend>
									<strong>人员列表</strong>
								</legend>
								<table id="timegroupusershow">
								</table>
							</fieldset>
				</div>
				
				
				<div title="人员异动管理" style="padding: 5px;">
					<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>信息检索</strong>
						</legend>
						<div class="user-query">
							<dl>
								<dd style="height: 20px;">
									体检任务 <select class="easyui-combobox" id="ydselectbatch_id"
										name="ydselectbatch_id"
										data-options="height:26,width:140,panelHeight:'auto'"></select>
								</dd>
								<dd style="height: 20px;">
									<a href="javascript:getydcusGrid();"
										class="easyui-linkbutton c6" style="width: 80px;">删除体检</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:getydcusitemaddGrid();"
										class="easyui-linkbutton c6" style="width: 80px;">新增项目</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:getydcusitemdelGrid();"
										class="easyui-linkbutton c6" style="width: 80px;">删除项目</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
								</dd>
							</dl>
						</div>
					</fieldset>
					<fieldset style="margin: 5px; padding-right: 0;">									
						<div class="left1">
						<fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>体检人员</strong>
						</legend>					
						<table id="ydexaminfolist" class="easyui-datagrid">
						</table>
					</fieldset>
					</div>
                        <div class="right1">
                        <fieldset style="margin: 5px; padding-right: 0;">
						<legend>
							<strong>对应体检项目</strong>
						</legend>
			              <div id="ydexaminfoItemlist"></div>
					</fieldset></div>
					</fieldset>
				</div>

			</div>
			</div>
			</div>
			    
			</div>
			

	<div id="dlg-edit-batch" class="easyui-dialog" style="z-index: 2000;"
		data-options="width: 820,height: 410,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-show-batch" class="easyui-dialog" style="z-index: 2000;"
		data-options="width: 950,height: 450,closed: true,cache: false,modal: true,top:50"></div>

	<div id="dlg-custshow" class="easyui-dialog"
		data-options="width: 920,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-edit-group" class="easyui-dialog"
		data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-show-group" class="easyui-dialog"
		data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-groupshow" class="easyui-dialog"
		data-options="width: 750,height: 500,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-custedit" class="easyui-dialog"
		data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-hsprintln" class="easyui-dialog"
		data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-printitem" class="easyui-dialog"
		data-options="width: 700,height: 500,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-edit-imp" class="easyui-dialog"
		data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-show-imp" class="easyui-dialog"
		data-options="width: 900,height: 420,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-custedit-time" class="easyui-dialog"
		data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
	<div id="dlg-custedit-remarke" class="easyui-dialog"
		data-options="width: 400,height: 380,closed: true,cache: false,modal: true,top:50"></div>	
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/djtTpanel.js?randomId=<%=Math.random()%>"></script>