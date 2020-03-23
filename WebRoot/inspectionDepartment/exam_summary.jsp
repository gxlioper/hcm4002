<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>总检页面</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/datagrid-dnd.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/exam_summary.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.min.js"></script>
<script type="text/javascript" >
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
</script>
<style type="text/css">
.sub_btn{
	text-align:center;
	margin-top:10px;
	padding:0 30px;
}
.pop_up_box_lis{
	border:1px solid #ccc;
	background:#fff;
	padding:0 0px 10px;
	position:absolute;
	font-size:12px;
	left:35%;
	display:none;
}
.title{
	text-align:center;
	cursor:move;
	height:30px;
	line-height:30px;
	margin-bottom:15px;
	background:#359BCC;
	border-bottom:1px solid #ccc;
	color:#FFFFFF;
	font-size:16px;
}
</style>
</head>
<body>
<!--资源  -->
<input type="hidden" id="app_type" value="<s:property value="app_type"/>"/>
<input type="hidden"   id='webResource' value="<s:property value='webResource'/>"/>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden"   id='isExamSummaryEdit' value="<s:property value='isExamSummaryEdit'/>"/>
<input type="hidden"   id='isDiseaseMerge' value="<s:property value='isDiseaseMerge'/>"/>
<input type="hidden"   id='examResultStyle' value="<s:property value='examResultStyle'/>"/>
<input type="hidden"   id='isExamSuggest' value="<s:property value='isExamSuggest'/>"/>
<input type="hidden"   id='examSummaryPacsUrl' value="<s:property value='examSummaryPacsUrl'/>"/>
<input type="hidden"   id='isExamSummaryNewDiseasePageShow' value="<s:property value='isExamSummaryNewDiseasePageShow'/>"/>
<input type="hidden"   id='isExamSummaryNew' value="<s:property value='isExamSummaryNew'/>"/>
<input type="hidden"   id='operation_type' value="<s:property value='operation_type'/>"/>
<input type="hidden"   id='t_wbeResource' value="<s:property value='t_wbeResource'/>"/>
<input type="hidden"   id='cancel_wbeResource' value="<s:property value='cancel_wbeResource'/>"/>
<input type="hidden"   id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>"/>
<input type="hidden"   id='is_update_critical' value="<s:property value='model.is_update_critical'/>"/>
<input type="hidden"   id='userid' value="<s:property value='model.userid'/>"/>
<input type="hidden"   id='is_final_history_show' value="<s:property value='model.is_final_history_show'/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;">
	<div data-options="region:'north'" border="false" style="height:65px;">
    	<fieldset>
    		<legend><strong>基本信息</strong>
    			<span id="vipsigin" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">★★★★★</font></span>
    			</legend>
				<div class="user-query">
					<dl>
						<dt style="width:60px;"><s:text name="dahname"/>：<input type="hidden" name="id" id="id"/>
						<input type="hidden" name="exam_num" id="exam_num" value="<s:property value="exam_num"/>"/>
						<input type="hidden" name="approve_status" id="approve_status"/>
						<input type="hidden" id="censoring_status"/></dt><dt id="arch_num"></dt>
						<dt style="width:60px;"><s:text name="tjhname"/>：</dt><dt id="exam_num_x"></dt>
						<dt style="width:50px;">姓名：<input type="hidden" name="exam_id" id="exam_id"/></dt><dt id="user_name"></dt>
						<dt style="width:50px;">性别：</dt><dt id="sex" style="width: 30px;"></dt>
						<dt style="width:50px;">年龄：</dt><dt id="age" style="width: 30px;"></dt>
						<dt style="width:70px;">体检套餐：</dt><dt id="set_name" style="width:148px;"></dt>
						<dt style="width:50px;">单位：</dt><dt id="company" style="width:148px;"></dt>
						<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;height:27px;" onclick="javascript:getResultHistory();">历史结果对比</a></dd>
						<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" id="pacs_yp" style="width:100px;height:27px;display:none;" onclick="javascript:show_pace_url();">PACS阅片</a></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;height:27px;" onclick="javascript:changefontcss(0);">-字体</a></dd>
					<dd><a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;height:27px;" onclick="javascript:changefontcss(1);">+字体</a></dd>
					</dl>
				</div>
 			</fieldset>
	</div>
	<div data-options="region:'center'" border="false">
		<div class="easyui-layout" data-options="fit:true" border="false">
			<div data-options="region:'west'" style="width:35%;">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="结论" style="padding:5px;" >
						<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 100%;font-size:14px;line-height: 25px;" id="zongjianjielun"></textarea>
					</div>
					<div title="普通科" style="padding:5px;" >
						<table id="pt_itemResultList"></table>
					</div>
					<div title="影像科"   style="padding:5px;" >
						<table id="yx_itemResultList"></table>
					</div>
					<div title="检验科" style="padding:5px;" >
						<table id="hy_itemResultList"></table>
					</div>
				</div>
			</div>
			<div data-options="region:'center'" border="false"  style="position:relative">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="阳性发现列表" style="padding:5px;" >
						<table id="disease_sug_list"></table>
					</div>
					<div title="危急值列表" style="padding:5px;" >
						<table id="disease_critical_detail"></table>
					</div>
					<div title="健康宣教" style="padding:5px;" >
						<table id="disease_xuanjiao"></table>
					</div>
					<div title="体检综述" style="padding:5px;" >
						<textarea style="width: 99%;resize:none;height: 99%;font-size:14px;" id="exam_guidance" ondblclick="show_tjzs()"></textarea>
					</div>
					<div title="健康评估" style="padding:5px;" >
						<table id="disease_pinggu"></table>
					</div>
					<div title="导检单" style="padding:5px;" >
<!-- 						<img src="url" di="disease_tijianbaogao" /> -->
						<div id="disease_tijianbaogao" style="vertical-align: middle; text-align: center;"></div>
					</div>

				</div>
					<div style="width: 120px; height: 20px; position:absolute;top:0px;right:100px;" onclick="javascript:exam_suggestion();">
	       				<span class="easyui-linkbutton c6" style="width:120px;height:27px;" >报告预览意见</span>
	       				<div id="dlg-report" class="easyui-dialog" data-options="width: 850,height: 552,closed: true,cache: false,modal: true,top:50">
       					</div>
			   		</div>
<%-- 			   		<div style="width: 80px; height: 20px; position:absolute;top:0px;right:250px;" onclick="javascript:reportReject();">
	       				<span class="easyui-linkbutton c6" style="width:80px;height:27px;" >报告驳回</span>
	       				<div id="dlg-reject" class="easyui-dialog" data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50">
       				</div>
			  	 </div> --%>
			</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height:55px;">
		<div class="sub_btn">
			 <s:if test ="carPdf_button eq \"Y\"">
				<a href="javascript:void(0)" class="easyui-linkbutton c6"  id="pdfbuttion" style="display:none;" onclick="javascript:wenjuanpdf();">健康评测报告</a>
			</s:if>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;display:none;" id="scxvhao" onclick="javascript:create_num();">重新生成序号</a>
		  <!--  <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:addCrmVisitPlanPage();">新增健康计划</a>-->
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:addCrmPlanTacticsPage();">添加回访策略</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="jkz" onclick="javascript:jkz_page_show();">健康证</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:printreport();">报告预览</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:creat_new_jielun();">重新生成结论</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="baocun" onclick="javascript:save_examsummary('N');">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="zongjian" onclick="javascript:save_examsummary('Y');">确定终检</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_zongjian" onclick="javascript:qv_examsummary();">取消终检</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" id="sd_fucha" onclick="javascript:shedingfucha();">设定复查</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="shenghe" onclick="javascript:shengheorqv();">审核</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="bohui" onclick="javascript:zongjianbohui();">驳回</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_shenghe" onclick="javascript:shengheorqv();">取消审核</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="zhongsheng" onclick="javascript:zhongshengqv();">终审</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_zhongsheng" onclick="javascript:zhongshengqv();">取消终审</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;" onclick="javascript:close_page();">关闭</a>
		</div>
	</div>
</div>
<div id="toolbar">
		<div style="float:left;margin-left:20px">
		阳性发现/疾病搜索：<input type="text" style="width:150px;height:26px;" id="serch_disease" class="textinput"/>
		</div>
		<div style="margin-left:340px;">
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:new_adddisease();">新增阳性发现</a>
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:diseas_hebin();">合并阳性发现</a>
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:diseas_paixv();">阳性发现排序</a>
		</div>	
</div>
<div id="toolbar2">
		<div>
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:new_addcritical();">新增危急值</a>
		</div>	
</div>
<div id="dlg-add" class="easyui-dialog"  data-options="width: 800,height: 600,closed: true,cache: false,modal: true"></div>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 700,height: 400,closed: true,cache: false,modal: true"></div>
<div id="dlg-bohui" class="easyui-dialog"  data-options="width: 600,height: 300,closed: true,cache: false,modal: true"></div>
<div id="dlg-ls" class="easyui-dialog"  data-options="width: 1000,height: 650,closed: true,cache: false,modal: true"></div>
<div id="results_contrast" class="pop_up_box_lis">
	<div style="float:right; margin-top:2px; margin-right:5px;font-size: 30px;">
        <a href="javascript:void(0)" onclick="DivClose('results_contrast')" title="关闭">x</a>
    </div>
    <div id="ls_title" class="title"><span>历史结果对比</span></div>
    <div id="ls_graph" style="width:600px;height:200px; margin-left: 5px;margin-right: 5px;"></div>
    <table id="item_lishi_table_div">
    </table>
</div>
<div id="dlg-hb" class="easyui-dialog"  data-options="width: 680,height: 400,closed: true,cache: false,modal: true,title:'选择合并后的阳性发现'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dt>选择阳性发现：</dt>
				<dd><input type="text" style="width:350px;height:26px;" id="disease_hebin_name" class="textinput"/><strong class="red">*</strong></dd>
			</dl>
			<div  style="height:230px;width:450px;margin-left:80px;">
				<table id="disease_hebin_sug"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_suggestionhb();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-hb').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>

<div id="dlg-px" class="easyui-dialog"  data-options="width: 500,height: 650,closed: true,cache: false,modal: true,title:'阳性发现排序'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:550px;width:450px;margin-left:20px;">
				<table id="disease_paixv"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_diseasPaixv();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-px').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-tjzs" class="easyui-dialog"  data-options="width: 1200,height: 500,closed: true,cache: false,modal: true,title:'选择体检综述词'">
	<form id="add1Form">
		<div class="easyui-layout" style="height:420px;">
			<div data-options="region:'west',split:true" style="width:40%;">
				<div id="toolbar1">
	<label style="font-size: 15px;font-weight: bold;">搜索条件：</label>
	<label>&nbsp;&nbsp;&nbsp;&nbsp;体检综述名称&nbsp;&nbsp;&nbsp;</label><input type="text" id="ser_exam_guidance" value="" />
</div>
			<table id="exam_guidance_list"></table>
			</div>
			<div data-options="region:'center'">
				<div style="width:100%;height:33px;line-height:33px;background:#d9d9d9;">&nbsp;&nbsp;体检综述内容</div>
				<textarea style="width: 99%;resize:none;height: 90%;font-size:14px;" id="ls_exam_guidance"></textarea>
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
<div id="dlg-jkz" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,title:'健康证'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:20px;">
			<dl>
				<dd style="widht:80px;margin-left: 60px;" >是否允许发证：</dd>
				<dd><select style="width:150px;height:26px;" id="jkz_value" class="easyui-combobox" data-options="panelHeight:'auto'">
					<option value="1">允许</option>
					<option value="0">不允许</option>
				</select><strong class="red">*</strong></dd>
				<dd style="widht:80px;" >健康证号：</dd>
				<dd><select style="width:150px;height:26px;" id="health_no" class="easyui-combobox" data-options="panelHeight:'auto'">
					<option value="1" selected="selected">使用新健康证号</option>
					<option value="0">使用已存在健康证号</option>
				</select><strong class="red">*</strong></dd>
			</dl>
			<dl>
				<dd>本年度已存在健康证列表：</dd>
				<dd><table id="health_list"></table></dd>
			</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_jkz_yx();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-jkz').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="mm">  
</div>
</body>
</html>