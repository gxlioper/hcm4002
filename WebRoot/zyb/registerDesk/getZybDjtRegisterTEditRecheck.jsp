<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main {	width: auto;	height: auto;}
#left { float: left;   width: 420px;	height: auto;}
#right {margin-right: 10px;height: auto;}

</style>

<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/sxtCutPic.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common_comboTree_box.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sfzCard.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jpgcam/webcam.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/sxtCutPic.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/registerDesk/getZybDjtRegisterTEditRecheck.js"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<body style="height:100%;">
<!-- 定义身份证设备 -->
<OBJECT ID='GT2ICROCX' width='0' height='0' <s:property value="sfz_div_ocx"/>></OBJECT>
<input type="hidden" id="sfz_div_code" value="<s:property value="sfz_div_code"/>">
<!-- 定义身份证设备结束 -->
<!-- 拍照 -->

<!--拍照-->
<div id="upload_poto_div" style="z-index: 89999;visibility:hidden ">
  <div style="float:right; top:2; margin-right:5px;"> <a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="Closeupload_poto_div()" title="关闭"> 关  闭 </a></div>
  <div id="title"><span>在线拍照</span></div>
  <div id="my_camera" style="width:150px;height:300px;float:left;"></div>
  <div id="img_upload" style="width:40%;float:right;"><img id="temppicshow" src="<%=request.getContextPath()%>/themes/default/images/user.png" width="200px" height="300px" /></div>
  <div style=" width:240px; margin:0 auto; margin-top:10px; overflow:hidden; text-align:center;">
      <a  href="javascript:void(0)" onClick="Webcam.unfreeze();"><img src="<%=request.getContextPath()%>/images/sxt.png" /><br>重拍</a>
      <a  href="javascript:void(0)" onClick="do_photo();"><img src="<%=request.getContextPath()%>/images/paizhao.png" /><br>拍照</a>
      <a  href="javascript:void(0)" onClick="do_upload()"><img src="<%=request.getContextPath()%>/images/shangchuan.png" /><br>确定</a>
      </div>
</div>
<input id="myphotodata" type="hidden" name="myphotodata" value=""/>
<!-- 拍照结束 -->

<!--数据上传-->
<div id="upload_photofile_div" style="left:550;top:100;z-index: 89999;visibility:hidden ">
  <div style="float:right; top:2; margin-right:5px;"><a style="font-size:20px; font-weight:bold; color:#FFF;" href="javascript:void(0)" onclick="closeuploadshow()" title="关闭"> 关  闭 </a> </div>
  <div id="title"><span>上传文件</span></div>
  <input type="file" id="customImageInfoImport" name="customImageInfoImport" />
  <input type="submit" onclick="wenjianshangchuan()" value="上传">
</div>
<!--数据上传结束-->
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="exam_num" value="<s:property value=""/>">
<input type="hidden" id="oldexam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="exam_num1" value="<s:property value=""/>">
<input type="hidden" id="customer_id" value="0">
<input type="hidden" id="batch_id" value="0">
<input type="hidden" id="company_id" value="0">
<input type="hidden" id="examstatus"  value="Y">
<input type="hidden" id="defauledate"  value="<s:property value="model.register_date"/>">
<input type="hidden" id="oldis_after_pay" value="<s:property value="model.is_after_pay"/>">
<input type="hidden" id="picture_Path" value="">
<input type="hidden" id="register_date" value="">
<input type="hidden" id="setimes" value="">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="exam_indicator" value="">
<input type="hidden" id="barexeurl" value="<s:property value="model.bar_code_url"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.djd_code_url"/>">
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="is_show_discount" value="<s:property value="model.is_show_discount"/>">
<input type="hidden" id="mc_no" value="">
<input type="hidden" id="patient_id" value="">
<input type="hidden" id="clinic_no" value="">
<input type="hidden" id="visit_no" value="">
<input type="hidden" id="visit_date" value="">
<input type="hidden" id="ren_type" value="<s:property value="model.ren_type"/>">
<input type="hidden" id="old_exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="ttog" value="<s:property value="model.ttog"/>">
<input type="hidden" id="gtot" value="<s:property value="model.gtot"/>">
<%-- <input type="hidden" id="employeeID" value="<s:property value="model.employeeID" />"> --%>
<input type="hidden" id="group_id" value=0>
	<div id="main">
	<div id="left">
	<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检人员基本信息</strong>
	</legend>
	<div class="user-query">
	   <dl style="height:130px;">
			<dt style="width:120px;"><img  id="exampic" style="height:120px;width:100px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /></dt>
			<dd style="width:200px;">
			
 <table width="240px" border="0" cellspacing="1" cellpadding="1">
  <tr>
    <td><a  href="javascript:void(0)" onClick="doload_photo_load()"><img style="height:25px;width:25px;" title="摄像头拍照" src="<%=request.getContextPath()%>/themes/default/images/sxt.png" /></a></td>
    <td colspan="2" ><div style="font-size:12px" id="examcount">您是第0次体检</div></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><a href="javascript:void(0)" onClick="upload_excel_btn()"><img style="height:25px;width:25px;" title="照片文件上传" src="<%=request.getContextPath()%>/themes/default/images/btndi.png" /></a></td>
    <td>&nbsp;</td>
    <td>&nbsp;<input id="newbutton"  style="height:26px;width:50px;" type="button" onclick="fnewbutton(0);" value="新增"></td>
    <td>&nbsp;<input id="yynewbutton"  style="height:26px;width:50px;" type="button" onclick="fnewbutton(1);"  value="预约"></td>
  </tr>
  <tr>
    <td><a  href="javascript:void(0)" onClick="djtreadcard_sfz();"><img title="身份证获取人员信息" style="height:25px;width:25px;" src="<%=request.getContextPath()%>/themes/default/images/shengfencod.png" /></a></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;<input id="yynewbutton"  style="height:26px;width:50px;" type="button" onclick="fnewjkwjdc();"  value="问卷"></td>

  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;<input id="savebutton"  style="height:26px;width:50px;" type="button" onclick="fnewbutton(2);" value="修改"></td>
    <td>&nbsp;<input id="clearbutton"  style="height:26px;width:50px;" type="button" onclick="clearcustomer();" value="清除"></td>
  </tr> 
</table>
	</dd>
		</dl>
		<dl>
			<dt style="height:26px;width:65px;">单位<strong class="quest-color">*</strong></dt>
			<dd><input type="text" id="com_Name" value="" style="height:23px;width:310px;"/>
					  <div id="com_name_list_div1" style="display:none;margin-left:0px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
			</dd>
		</dl>
		<dl>
			<dt style="height:26px;width:65px;">体检任务<strong class="quest-color">*</strong></dt>
			<dd>
			<input type="text" id="tjrw" value="" style="height:23px;width:310px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-20px;" 
					      onmouseover="select_batchcom_list_mover()" 
					      onmouseout="select_batchcom_list_amover()">
                      </div>
			</dd>
		</dl>
		

		<dl>
		<dt style="height:26px;width:50px;"><s:text name="dahname"/></dt>
			<dd>
				<input class="easyui-textbox" readonly  type="text" id="arch_num"
					style="height: 26px; width: 87px;" value="<s:property value="model.arch_num" />"/>
			</dd>
			
			<dt style="height:26px;width:73px;">身份证号 </dt>
			<dd>
				<input class="easyui-textbox" missingMessage="必须输入" invalidMessage="身份证必须18位"  type="text"  data-options="prompt:'查询条件',validType:'Length[18]',events:{blur:selectidnum}" id="id_num" 
					style="height: 26px; width: 150px;" value="<s:property value="model.id_num" />"/>
			</dd>
		</dl>
		<dl>
		<dt style="height:26px;width:50px;"><s:text name="tjhname"/></dt>
			<dd>
				<input class="easyui-textbox" missingMessage="可输入项" invalidMessage="最大长度不能超过20"  type="text" id="c_exam_num"
					style="height: 23px; width: 85px;" data-options="validType:'maxLength[20]'" value="<s:property value="model.exam_num" />"/>
			</dd>
			
			<dt style="height:26px;width:73px;">出生日期</dt>
			<dd><input class="easyui-datebox" maxlength="10"  value="<s:property value="model.birthday"/>" data-options="validType:'Length[10]',events:{blur:selectbirthday}"  type=text id="birthday" style="width:140px;height:26px;"></input>
			</dd>
		</dl>
		<dl>
		<dt style="height:26px;width:50px;">姓名<strong class="quest-color">*</strong></dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20"  id="custname"
					style="height: 26px; width: 80px;" data-options="prompt:'查询条件'" value="<s:property value="model.custname" />"/>
			</dd>
		
			
			<dt style="height:26px;width:80px;">人员类别</dt>
			<dd><select class="easyui-combobox" id="rylb" name="rylb"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>			
		</dl>
		<dl>
		<dt style="height:26px;width:50px;">年龄<strong class="quest-color">*</strong></dt>
			<dd>
				<input class="easyui-textbox" data-options="events:{blur:selectage}" maxlength="3" type="text" id="age"
					style="height: 26px; width: 80px;" value="<s:property value="model.age" />"/>
			</dd>
			
			
			<dt style="height:26px;width:80px;">体检类别</dt>
			<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>		
		</dl>
		<dl>		
		<dt style="height:26px;width:50px;">性别<strong class="quest-color">*</strong></dt>
			<dd>
				<select class="easyui-combobox" id="sex" name="sex"
					data-options="height:26,width:80,panelHeight:'auto'"></select>
			</dd>   
			
		    <dt style="height:26px;width:80px;">费别</dt>
			<dd>
				<select class="easyui-combobox" id="sftype" name="sftype"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
			
		</dl>
		<dl>
		
		<dt style="height:26px;width:50px;">婚否</dt>
			<dd>
				<select class="easyui-combobox" id="is_Marriage" name="is_Marriage"
					data-options="height:26,width:80,panelHeight:'auto'"></select>
			</dd>
			
		    <dt style="height:26px;width:80px;">身份类型</dt>
			<dd>
				<select class="easyui-combobox" id="customerType" name="customerType"
					data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
		</dl>
		<dl>
		   <dt style="height:26px;width:50px;">民族</dt>
			<dd><select class="easyui-combobox" id="minzhu" name="minzhu"					
					data-options="height:26,width:80,panelHeight:'auto'"></select>
			</dd>
		
		    <dt style="height:26px;width:80px;">职务</dt>
			<dd>
				<input class="easyui-textbox" id="addposition" name="addposition"
					style="height:26px;width:140px" />
			</dd>
		</dl>
		<dl>		    
			<dt style="height:26px;width:50px;">既往史</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="50"  id="past_medical_history"
					style="height: 26px; width: 105px;"  value="<s:property value="model.past_medical_history" />"/>
			</dd>	
			<dt style="height:26px;width:55px;">工号</dt>
			<dd>
				<input class="easyui-textbox" id="employeeID" name="employeeID"
					style="height:26px;width:140px" />
			</dd>		
		</dl>
		   <dl>
			<dt style="height:26px;width:50px;">电话</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20"  id="addtel" 
					style="height: 26px; width: 105px;" value="<s:property value="model.phone" />"/>
			</dd>
			
            <dd  style="height:26px;width:200px;">
			<input id="chkItem" name="is_after_pay" type="radio" value="N"/>前付费</dt>
			<input id="chkItem" name="is_after_pay"  checked="true" type="radio" value="Y"/>后付费</dt>
			</dd>	    	
		</dl>
        <dl>
			<dt style="height:26px;width:50px;">状态</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20" readonly  id="statuss" 
					style="height: 26px; width: 80px;" value="<s:property value="model.statuss" />"/>
			</dd>			
              <dt style="height:26px;width:80px;">预约日期</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20" readonly  id="register_dates" 
					style="height: 26px; width: 140px;" value=""/>
			</dd>	
		</dl>
		<dl>
			<dt style="height:26px;width:50px;">邮箱</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20" id="email"
					style="height: 26px; width: 80px;" value="<s:property value="model.email" />"/>
			</dd>	
			<dt style="height:26px;width:80px;">预约时间</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20" readonly  id="register_times" 
					style="height: 26px; width: 140px;" value=""/>
			</dd>
		</dl>
		<dl>
		    <dt style="height:26px;width:50px;">其他</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20"  id="others"
					style="height: 26px; width:80px;" value="<s:property value="model.others" />"/>
			</dd>
			
			<dt style="height:26px;width:80px;">报到时间</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20" readonly  id="join_dates" 
					style="height: 26px; width: 140px;" value=""/>
			</dd>	
		</dl>
		<dl>
			<dt style="height:26px;width:50px;">介绍人</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="20"  id="introducer"
					style="height: 26px; width:80px;" value="<s:property value="model.introducer" />"/>
			</dd>	
		    <dt style="height:26px;width:80px;">部门</dt>
			<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
		</dl>
		<dl>		    
			<dt style="height:26px;width:50px;">地址</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="50"  id="address"
					style="height: 26px; width: 310px;"  value="<s:property value="model.address" />"/>
			</dd>			
		</dl>
		<dl>		    
			<dt style="height:26px;width:50px;">备注</dt>
			<dd>
				<input class="easyui-textbox" type="text" maxlength="50"  id="remark"
					style="height: 26px; width: 80px;"  value="<s:property value="model.remark" />"/>
			</dd>
			<dt style="height:26px;width:80px;">套餐来源</dt>
			<dd>
				<input class="easyui-combobox" id="zyb_set_source" value="<s:property value="model.zyb_set_source" />"
						 data-options="panelHeight:'auto'" class="easyui-validatebox" style="height:26px; width: 140px;" />
			</dd>
		</dl>
	</div>
</fieldset>
</div>

<div id="right">
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检操作</strong>
	</legend>
			<div class="item-query">
	  
	  <dl>
	        <dt>&nbsp;&nbsp;<a href="javascript:examnumenterbd();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:70px;">报到</a></dt>
	        <dt>&nbsp;&nbsp;<a href="javascript:new_printdjd();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:70px;">导检单</a></dt>
			<dt>&nbsp;&nbsp;<a href="javascript:new_printdjdbar();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:90px;">导检单条码</a></dt>
			<dt>&nbsp;&nbsp;<a href="javascript:new_reprintdjd();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">补导检单</a></dt>
			<dt>&nbsp;&nbsp;<a href="javascript:printbarshow();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:70px;">打条码</a>
			&nbsp;<input name="isprintdah" type="checkbox" value="1"/><s:text name="dahname"/>  
			&nbsp;<input name="ishisflags" type="checkbox" value="1"/>自动发送收费申请
		    </dt>
	  </dl>
	</fieldset>
	<fieldset style="margin: 5px; padding-right: 0;">
			<legend>
				<strong>职业危害因素维护</strong>
			</legend>
			<div class="user-query">
			   <table id="zywhysset"></table>
			</div>
		</fieldset>
	<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检套餐</strong>
	</legend>	
	      <div id="djtGselectsetlist" style="width: 1030px;"></div>
	</fieldset>
	  <fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>体检项目</strong>
	</legend>

			&nbsp;&nbsp;&nbsp;&nbsp;收费项目数：<font id="countss" style="font-weight:bold;font-style:italic;">0</font>项
			&nbsp;&nbsp;团体应缴金额：<font id="tyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人应缴金额：<font id="gyjje" style="font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人实缴金额：<font id="gsjje" style="color:blue;font-weight:bold;font-style:italic;">0</font>元
			&nbsp;&nbsp;个人未缴金额：<font id="gwjje" style="color:red;font-weight:bold;font-style:italic;">0</font>元
	
			<div id="djtGselectItemlist" style="width: 1030px;"></div>
	</fieldset>
</div>

</div>

</div>
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 1000,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-zybocchisedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
  </body>
