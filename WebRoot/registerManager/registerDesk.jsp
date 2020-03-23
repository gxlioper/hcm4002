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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/registerDesk.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","20");
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
})
</script>
<body>
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0' <s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code" value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="0">
<input type="hidden" id="reportexe">
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.djd_code_url"/>">
<input type="hidden" id="reportexeurl" value="<s:property value="model.report_url"/>">
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<input type="hidden" id="report_print_type" value="<s:property value="model.report_print_type"/>">
<input type="hidden" id="zyb_report_print_type" value="<s:property value="model.zyb_report_print_type"/>">
<input type="hidden" id="customer_type_special" value="<s:property value="model.customer_type_special"/>">
<input type="hidden" id="customer_type_special_color" value="<s:property value="model.customer_type_special_color"/>">
<input type="hidden" id="freeze_color" value="<s:property value="model.freeze_color"/>">
<input type="hidden" id="ttog_exam" value="<s:property value="ttog_exam"/>">
<input type="hidden" id="gtot_exam" value="<s:property value="gtot_exam"/>">
<input type="hidden" id="batch_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:115px;"/></dd> 
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:70px;"/></dd>
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="customer_sex"/>性别</dt>
					<dd><select class="easyui-combobox" id="sex" 
						data-options="height:26,width:70,panelHeight:'auto'">
						<option value="">全部</option>
						<option value="男">男</option>
						<option value="女">女</option>
						</select>
					</dd>
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" class="join_cheke" type="checkbox" checked="checked" value="register_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_type"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="data_source"/>数据来源</dt>					
					<dd><select class="easyui-combobox" id="data_source" name="data_source"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dd><input name="wuxuzongjian" id="wuxuzongjian" type="checkbox" value="1"/>无需总检</dd>
					
					<dd>报到打:<input name="isprintdjd" type="checkbox" checked value="1"/> 导检单&nbsp;
					<input name="isprinttm" type="checkbox" checked value="1"/>条码&nbsp;
					<input name="isprintdah" type="checkbox" checked value="1" /><s:text name="dahname"/></dd>
				</dl>
				 
				<dl>
				   <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:115px;"/></dd>   
					
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="com_Name"/>单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:200px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:175px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="over_date"/>终检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"></input></dd> 
			          <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="creater"/>创建人</dt>					
					 <dd><select id="creater" style="width:100px;height:26px;"></select></dd>
                    
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="status"/>检查状态</dt>	
                    <dd><select class="easyui-combobox" id="statusss" name="statusss"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
                    
                    <dt style="height:26px;width:90px;"><input id="chkItem" name="chkItem" type="checkbox" value="conn_rylb"/>人员类别</dt>	
                    <dd><select class="easyui-combobox" id="conn_rylb" name="conn_rylb" data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
                    
					<dd><a href="javascript:dosfzbd();"  class="easyui-linkbutton" style="height:26px;width:100px;text-algin:center;">身份证报到</a></dd>
                    
                </dl>
                <dl>
                	<dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证</dt>	
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="batch_Name"/>任务</dt>					
					<dd>
						 <select class="easyui-combobox" id="batch_ids" name="batch_ids"	data-options="height:26,width:200,panelHeight:'auto'"></select>
                    </dd>
					
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="yuyue_date"/>预约日期</dt>
			         <dd><input class="easyui-datebox" type=text id="yuyue_date1" value="<s:property value="model.yuyue_date1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="yuyue_date2" value="<s:property value="model.yuyue_date2"/>" style="width:100px;height:26px;"></input></dd>
                    
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="levels"/>选择部门</dt>
					<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="tjlx"/>体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:100,panelHeight:'auto'"></select>
					</dd>
					<dt style="height:26px;width:90px;"><input id="chkItem" name="chkItem" type="checkbox" value="djdstatuss"/>回收导检单</dt>
					<dd><select class="easyui-combobox" id="djdstatuss"
						data-options="height:26,width:80,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N">未回收</option>
						<option value="Y">已回收</option>
						</select>
					</dd>
                    
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
                </dl>
			</div>		
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong>&nbsp;&nbsp;
	<span style="background-color:#9933FF;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">条码或导检单已经打印 &nbsp;&nbsp;</span>
    <span style="background-color:#FF0000;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">体检被冻结&nbsp;&nbsp;</span>
    <span style="font-size:14px;padding-left:70px" id="join_num">体检总人数:0人</span>
</legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  
  <div id="dlg-jkz" class="easyui-dialog"  data-options="width: 400,height: 200,closed: true,cache: false,modal: true,title:'打印档案号'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:30px;">
			<dl>
				<dt style="widht:100px;" >打印个数:</dt>
				<dd><input class="easyui-numberbox" type="text" id="print_nums" value="1" style="height:26px;width:100px;"/></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:new_print_arch();">确定打印</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-jkz').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>

<!-- 补做登记  -->
<div id="dlg-bzdj" class="easyui-dialog"  data-options="width:800,height: 450,closed: true,cache: false,modal: true,title:'选择补做项目',top:50">
	<table id="bzdjItemShow"></table>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:addBuZuoItem();">提交</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-bzdj').dialog('close')">关闭</a>
		</div>
	</div>
</div>
<!-- 个转团 -->
<div id="dlg-tjzs" class="easyui-dialog"  data-options="width: 800,height: 180,closed: true,cache: false,modal: true,title:'选择体检单位'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 60px;" >批次单位：</dd>
				<dd><select style="width:350px;height:26px;" id="com_batch" class="easyui-combobox" data-options="panelHeight:'250'">
					</select><strong class="red">*</strong><input type="hidden" id="com_batch_id"/><input type="hidden" id="comnames"/></dd>
				<dd style="widht:80px;" >分组：</dd>
				<dd><select style="width:150px;height:26px;" id="group" class="easyui-combobox" data-options="panelHeight:'250'">
				</select><strong class="red">*</strong></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_tjzs();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-tjzs').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
  </body>