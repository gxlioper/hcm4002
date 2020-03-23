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
<script type="text/javascript" src="<%=request.getContextPath()%>/departInspect/inderDep_BSR.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
	$(function(){
		
		$("input").attr("maxlength","20");
		$("input",$(".easyui-textbox").next("span")).focus(function(){
	        $(this).select();
		});
		$('#exam_num').textbox('textbox').css('ime-mode','disabled');
		$('#exam_num').textbox('textbox').focus();
	});
	
</script> 
<body>
<input type="hidden" id="iszyb" value="<s:property value="#session.iszyb"/>">
<input type="hidden" id="is_depinspect_checked" value="<s:property value="model.is_depinspect_checked"/>"/>
<input type="hidden" id="nowtime" value="<s:property value="model.time1"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<input type="hidden" id="customer_type_special" value="<s:property value="model.customer_type_special"/>">
<input type="hidden" id="customer_type_special_color" value="<s:property value="model.customer_type_special_color"/>">
<input type="hidden" id="is_custom_identification" value="<s:property value="model.is_custom_identification"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="ck_exam_num" type="checkbox" checked="checked"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd> 
					<dt style="height:26px;width:80px;"><input id="ck_custname" type="checkbox" />姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="ck_date" type="checkbox"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>"  style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input>
			         <dt style="height:26px;width:80px;"><input id="ck_searchemployeeID" type="checkbox"/>工号</dt>					
					 <dd><input class="easyui-textbox"  type="text" id="searchemployeeID" value="" style="height:26px;width:140px;"/></dd>
					 <dt style="height:26px;width:80px;"><input id="ck_tjlx" type="checkbox" />体检类别</dt>
					 <dd><select class="easyui-combobox" id="tjlx" name="tjlx"
							data-options="height:26,width:140,panelHeight:'auto'"></select>
					 </dd>	

				</dl>
				<dl>
				   <dt style="height:26px;width:80px;"><input id="ck_arch_num" type="checkbox"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_id_num" type="checkbox"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>   
                    <dt style="height:26px;width:80px;"><input id="ck_company_id" type="checkbox" />选择单位</dt>					
					<dd><input type="hidden" id="company_id"/><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:240px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:415px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>  
                     <dt style="height:26px;width:80px;"><input id="ck_status" type="checkbox"/>状态</dt>					
					<dd><select class="easyui-combobox" id="statusss" name="statusss"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					<dd><a href="javascript:getgroupuserGrid('n');"  class="easyui-linkbutton" style="height:26px;width:105px;">当天未检</a></dd>
					 <dd><a href="javascript:getgroupuserGrid('y');"  class="easyui-linkbutton" style="height:26px;width:105px;">当天已检</a></dd>
				 </dl>
				 <dl>
				 	<%-- <dt style="height:26px;width:80px;"><input id="ck_exam_status" type="checkbox"/>检查状态</dt>					
					 <dd><select class="easyui-combobox" id="exam_status"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="">全部人员</option>
						<option value="N">未检人员</option>
						<option value="Y">部分已检</option>
						<option value="M">全部已检</option>
					</select></dd> --%>
					
					<dt style="height:26px;width:80px;"><input id="ck_exam_sex" type="checkbox"/>性别</dt>					
					 <dd><select class="easyui-combobox" id="exam_sex"
					data-options="height:26,width:100,panelHeight:'auto'">
						<option value="0">女</option>
						<option value="1">男</option>
					</select></dd>
					
					<dt style="height:26px;width:80px;"><input id="ck_doctor_name" type="checkbox"/>检查医生</dt>					
					<dd><select class="easyui-combobox" id="doctor_name"
					data-options="height:26,width:135,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="ck_exam_date" type="checkbox"/>检查日期</dt>
			         <dd ><input class="easyui-datebox" type=text id="exam_date1" value="<s:property value="model.exam_date1"/>"  style="height:26px;width:100px;"/></dd>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd ><input class="easyui-datebox" type=text id="exam_date2" value="<s:property value="model.exam_date2"/>" style="height:26px;width:100px;" /></dd>
			         
			        <dt style="height:26px;width:80px;"><input id="ck_charging_item" type="checkbox"/>收费项目</dt>					
					<dd><select class="easyui-combobox" id="charging_item_id" name="charging_item_id"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
			         
					
					<dt style="height:26px;width:80px;"><input id="ck_examType" type="checkbox"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					
			         <dt style="height:26px;width:0px;"></dt>
			         <dd><a href="javascript:shouye_chaxun();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:105px;">查询</a></dd>
				 </dl>
			</div>
 </fieldset>
 <fieldset id="keShi_table" style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong>
	<%-- &nbsp;&nbsp;<span style="background-color:red;">&nbsp;&nbsp;&nbsp;</span>
    <span style="font-size:14px;">未检查&nbsp;&nbsp;</span> --%>
    ———————
    <span style="font-size:14px;"><a href="javascript:getgroupuserGrid('Y')">
    	<span style="background-color:#000;">&nbsp;&nbsp;&nbsp;</span> 已检人数：<span id="yijian">0</span>人</a></span>————
    <span style="font-size:14px;"><a href="javascript:getgroupuserGrid('N')" style="color:red;">
    	<span style="background-color:red;">&nbsp;&nbsp;&nbsp;</span> 未检人数：<span id="weijian">0</span>人</a></span>————
    <span style="font-size:14px;"><a href="javascript:getgroupuserGrid('Z')">检查中人数：<span id="jiancha">0</span>人</a></span>————
    <span style="font-size:14px;"><a href="javascript:getgroupuserGrid('G')">弃检项目（涉及人数）：<span id="qijian">0</span>人</a></span>————
    <span style="font-size:14px;"><a href="javascript:getgroupuserGrid('D')">延期项目（涉及人数）：<span id="yanqi">0</span>人</a></span>
</legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 </body>