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
<script type="text/javascript" src="<%=request.getContextPath()%>/inspectionDepartment/exam_summary_new.js?randomId=<%=Math.random()%>"></script>
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
<input type="hidden"   id='webResource' value="<s:property value='webResource'/>"/>
<input type="hidden" id="app_type" value="<s:property value="model.app_type"/>"/>
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
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>"/>
<input type="hidden" id="is_exam_result_canfinal" value="<s:property value="model.is_exam_result_canfinal"/>"/>
<input type="hidden"   id='is_final_history_show' value="<s:property value='model.is_final_history_show'/>"/>
<div class="easyui-layout" fit="true" style="margin-left:5px;">
	<div data-options="region:'north'" border="false" style="height:95px;">
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
						<dt style="width:50px;">电话：</dt><dt id="phone" style="width: 100px;"></dt>
						<!-- <dt style="width:80px;">复查设定：<input type="hidden" id="review_id"/></dt><dt id="fcsd" style="width: 100px;"></dt> -->
						<dd><a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:150px;height:27px;" onclick="javascript:getResultHistory();">历史结果对比</a></dd>
						<dd><a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;height:27px;" onclick="javascript:changefontcss(0);">-字体</a></dd>
					    <dd><a href="javascript:void(0)" class="easyui-linkbutton" style="width:60px;height:27px;" onclick="javascript:changefontcss(1);">+字体</a></dd>
					</dl>
					<dl>
						<dt style="width:60px;">既往史：</dt><dd id="past_medical_history" style="width: 350px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;"></dd>
					    <dt style="width:70px;">体检次数：</dt><dt style="width: 50px;" id="exam_count" ></dt> 
					    <dt style="width:50px;">单位：</dt><dd id="company" style="width: 220px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;"></dd>
					    <dt style="width:70px;">总检医生：</dt><dt style="width: 60px;"><a id="exam_doctor"></a></dt>
						<dt style="width:70px;">审核医生：</dt><dt style="width: 60px;"><a id="app_doctor"></a></dt>
						<dt style="width:70px;">报告等级：</dt><dt style="width: 30px;color:red;font-size:16px;" id="report_dengji">0级</dt>
					</dl>
					</dl>
				</div>
 			</fieldset>
	</div>
	<div data-options="region:'center'" border="false">
		<div class="easyui-layout" data-options="fit:true" border="false">
			<div data-options="region:'west'" style="width:35%;">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="结论" style="padding:5px;" >
						<textarea readonly="readonly" style="width: 100%;resize:none;border: 0px;height: 100%;font-size:14px;" id="zongjianjielun"></textarea>
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
					<div title="复合阳性/疾病" style="padding:5px;" >
						<table id="disease_logic_composite_list"></table>
					</div>
				</div>
			</div>
			<div data-options="region:'center'" border="false">
				<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
					<div title="单项阳性发现列表" style="padding:5px;" >
						<table id="disease_sug_list"></table>
					</div>
					<div title="体检综述" style="padding:5px;" >
						<textarea style="width: 99%;resize:none;height: 99%;font-size:14px;" id="exam_guidance" ondblclick="show_tjzs()"></textarea>
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
		</div>
	</div>
	</div>
	<div data-options="region:'south'" style="height:55px;">
		<div class="sub_btn">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;display:none;" id="scxvhao" onclick="javascript:create_num();">重新生成序号</a>
			<!--<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:addCrmVisitPlanPage();">新增健康计划</a>-->
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:addCrmPlanTacticsPage();">添加回访策略</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="jkz" onclick="javascript:jkz_page_show();">健康证</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:100px;" onclick="javascript:checkCharingitem();">核对导检单</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:printreport();">报告预览</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;" onclick="javascript:creat_new_jielun();">重新生成结论</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="baocun" onclick="javascript:save_examsummary('N');">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="sh_baocun" onclick="javascript:save_examdisease('1');">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="fs_baocun" onclick="javascript:save_examdisease('2');">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="zongjian" onclick="javascript:save_examsummary('Y');">确定终检</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_zongjian" onclick="javascript:qv_examsummary();">取消终检</a>
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" id="sd_fucha" onclick="javascript:shedingfucha();">设定复查</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qxsd_fucha" onclick="javascript:qh_shedingfucha(4);">取消复查</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="hfsd_fucha" onclick="javascript:qh_shedingfucha(1);">恢复复查</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;display:none;" id="sd_teshu" onclick="javascript:shedingteshu(1);">设定特殊通知</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;display:none;" id="qv_teshu" onclick="javascript:shedingteshu(0);">取消特殊通知</a>-->
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" id="bgdj_butt" onclick="javascript:fn_sdbgdj();">报告等级</a> 
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="shenghe" onclick="javascript:querenshengheorqv();">审核</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="bohui" onclick="javascript:zongjianbohui();">驳回</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_shenghe" onclick="javascript:querenshengheorqv();">取消审核</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;display:none;" id="zhongsheng" onclick="javascript:zhongshengqv();">终审</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="qv_zhongsheng" onclick="javascript:zhongshengqv();">取消终审</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="yijian_qv" onclick="javascript:yijiancancel(1);">一键取消</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;display:none;" id="yijian_hui" onclick="javascript:yijiancancel(0);">一键恢复</a>
			<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:60px;" onclick="javascript:close_page();">关闭</a>
		</div>
	</div>
</div>
<div id="toolbar">
		<div style="float:left;margin-left:20px">
		阳性发现/疾病搜索：<input type="text" style="width:150px;height:26px;" id="serch_disease" class="textinput"/>
		</div>
		<div style="margin-left:340px;">
		<!--<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:new_adddisease();">新增阳性发现</a>-->
		<!--<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:diseas_hebin();">合并阳性发现</a>-->
		<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:120px;height:27px;" onclick="javascript:diseas_paixv();">阳性发现排序</a>
		</div>	
</div>

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
<div id="dlg-item" class="easyui-dialog"  data-options="width: 600,height: 650,closed: true,cache: false,modal: true,title:'查看科室项目列表'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:550px;width:550px;margin-left:20px;">
				<table id="exam_item_list"></table>
			</div>
	</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-item').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
<div id="dlg-bgdj" class="easyui-dialog"  data-options="width: 500,height: 200,closed: true,cache: false,modal: true,title:'设定报告等级'">
	<form id="add1Form">
		<div class="formdiv">
		<div class="formdiv fomr3" style="">
			<dl>
				<dt style="width:100px;">报告等级：<input type="hidden" id="report_summary_id"/></dt>
				<dd><select class="easyui-combobox" id="report_class"
					data-options="height:26,width:320,panelHeight:'auto'">
					<option value="0">0级</option>
					<option value="1">1级</option>
					<option value="2">2级</option>
					<option value="3">3级</option>
					</select></dd>
				</dl>
				<dl>
					<dt style="width:100px;">设定人：</dt><dd><input class="easyui-textbox" readonly="readonly" style="height:26px;width:100px;" id="report_class_user"/></dd>
					<dt style="width:100px;">设定时间：</dt><dd><input class="easyui-textbox" readonly="readonly" style="height:26px;width:100px;" id="report_class_date"/></dd>
				</dl>
		</div>
		</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:save_bgdj();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-bgdj').dialog('close')">关闭</a>
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
<div id="dlg-show_dis" class="easyui-dialog" title="建议" data-options="width: 700,height: 400,closed: true,cache: false,modal: true">
	<div class="easyui-layout" style="height:315px;">
		<table id="sug_list"></table>
	</div>
	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
</div>
</body>
</html>