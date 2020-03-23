<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<%
	application.setAttribute("name","application_James");

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>科室检查</title>
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/departAndFinaly.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/departInspect/departInspect_all_index.js?randomId=<%=Math.random()%>"></script>
	<style>
		.gene_head1{ overflow:hidden; height:25px;font-size:17px;}
		.gene_head1 li{ line-height:28px; border-bottom:1px solid #dfdfdf; width:24.8%;margin-left:-1px;}
		.gene_head1 li:hover{ border-top:2px solid #18A495; line-height:23px; background:#fff;}
		.gene_head1 li:frist-child:hover{ border:none;}
		.medical_class_title li {
			float: left;
			line-height: 24px;
			background: #DFDFDF;
			border-bottom: 1px solid #dfdfdf;
			border-left: 1px solid #fff;
			width: 20%;
			text-align: center;
		}
		.medical_class_title li:hover, .medical_class_title li.active {
			border-top: 2px solid #18A495;
			line-height: 23px;
		}
		.jre{}
		.test_box {
			width: 60%;
			min-height: 20px;
			_height: 20px;
			margin-left: auto;
			margin-right: auto;
			padding: 3px;
			outline: 0;
			border: 1px solid #a0b3d6;
			word-wrap: break-word;
			overflow-x: hidden;
			overflow-y: auto;
			_overflow-y: visible;
		}
		.hytest_box {
			width:30%;
			min-height: 20px;
			_height: 20px;
			margin-left: 5px;
			margin-right: auto;
			padding: 3px;
			outline: 0;
			border: 1px solid #a0b3d6;
			word-wrap: break-word;
			overflow-x: hidden;
			/*overflow-y: auto;*/
			_overflow-y: visible;
			float: left;
		}
		.image_depart_msg {
			font-size: 13px;
			padding:0px 70px;
		}
		.label_item{
			float: left;
			width:160px;
		}
		.label_area {
			float: left;
			width:40px;
		}
		.view {margin-left: 40px;}
		.in_conclusion{ margin-bottom:10px;}
		.in_conclusion textarea{ resize:none;}
		.B-ultrasound img.v_i_items{
			float: left;
			width: 150px;
			margin: 10px;
			height: 180px;
			background: #ccc;
		}
		.views{ overflow:hidden; width:100%;}
		.views li{ margin:10px 0; line-height:20px; float:left; width:49%;}
		.views li label{ width:170px; display:block; float:left; text-align:right;}

		.xscyc{min-height:50px;overflow-y:auto;max-height:330px;border:solid 1px #3399cc;position:absolute;z-index:10000;background:#ffffff;display:none}
		.xscycjl{min-height:50px;overflow-y:auto;max-height:330px;border:solid 1px #3399cc;position:absolute;z-index:10000;background:#ffffff;display:none}
		.xscyc ul{ width:auto !important; float:none;}
		.xscyc ul li{ margin:0 !important; display:block; line-height:22px;width:100%}
		.xscyc ul li:hover{background:#3399cc;color:#ffff;}
		.sub_btn{
			text-align:center;
			margin-top:10px;
			padding:0 30px;
		}
	</style>
</head>
<body >

<div class="easyui-layout" fit="true">
	<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
	<input type="hidden"     id='id' value="<s:property value='id'/>"/>
	<input type="hidden"     id='c_id' value="<s:property value='c_id'/>"/>
	<input type="hidden"     id='exam_num' value="<s:property value='exam_num'/>"/>
	<input type="hidden" id='isprint' value="<s:property value="#session.username.isPrint"/>"/>
	<input type="hidden" id="dep_id" value="<s:property value="#session.username.dep_id"/>"/>
	<input type="hidden" id="dep_num" value="<s:property value="dep_num"/>"/>
	<input type="hidden" id="username" value="<s:property value="#session.username.name"/>"/>
	<input type="hidden" id="isExamResultDetailDoctorPageShow"  value="<s:property value='isExamResultDetailDoctorPageShow'/>"/>
	<!--检查日期  -->
	<input type="hidden" id='exam_type_code' value="<s:property value='exam_type_code'/>"/>
	<input type="hidden" id='isCheckSensvitiveWord' value=""/>
	<input type="hidden" id='join_date' value="<s:property value='join_date'/>"/>
	<input type="hidden" id='picture_path' value="<s:property value='picture_path'/>"/>
	<input type="hidden" id="exam_result_summary" value="<s:property value='exam_result_summary'/>"/>
	<input type="hidden" id="moren_exam_result" value="<s:property value='exam_result'/>"/>
	<input type="hidden" id="suggestion"  value="<s:property value='suggestion'/>"/>
	<input type="hidden" id="is_departinspect_summary_edit"  value="<s:property value='is_departinspect_summary_edit'/>"/>
	<input type="hidden" id="is_dep_edit_questionnaire"  value="<s:property value='is_dep_edit_questionnaire'/>"/>
	<input type="hidden" id="app_type"  value="<s:property value='app_type'/>"/>
	<div  style="height:100%;width:22%" data-options="region:'west'">
		<fieldset class="customersize" style="margin:5px;margin-left :5px;padding-right:20px;height:50%;padding-left:30px;margin-left:3px;">
			<legend><strong>人员信息</strong>
				<s:if test="vipsigin==1"> <span id="vipsigin"  style="">&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">★★★★★</font></span></s:if>
				<s:else><span id="vipsigin"  style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;<font size="4" color="red">★★★★★</font></span></s:else>
			</legend>
			<div style="position: fixed;margin-left: 150px;margin-top: 10px;"><img id="exampic" style="height:120px;width:100px;" src="<%=request.getContextPath()%>/themes/default/images/user.png" /></div>
			<div class="user-query" style="margin-left:-20px;padding-right: 0px;">
				<dl>
					<dt style="width:60px;">档案号：</dt><dt style="width:100px;" id = 'arch_num'><s:property value='arch_num'/></dt>
				</dl>
				<dl>
					<dt style="width:60px;">体检号：</dt><dt style="width:100px;"  id="tjh"><s:property value='exam_num'/></dt>
				</dl>
				<dl>
					<dt style="width:60px;">姓名       </dt><dt style="width:100px;" id='u_name'><s:property value='user_name'/></dt>
				</dl>
				<dl>
					<dt style="width:60px;">性别：   </dt><dt id="sex" style="width:70px;"id = 'sex'><s:property value='sex'/></dt>
				</dl>
				<dl>
					<dt style="width:60px;">年龄：    </dt><dt style="width:70px;" id = 'ages'><s:property value='age'/></dt>
				</dl>
				<dl>
					<dt style="width:60px;">单位：     </dt><dt style="width:70%" id = 'company'><s:property value='company'/></dt>
				</dl>
				<dl>
					<dt style="width:90px;">人员类型:</dt><dt style="width:66%" id ='type_name'><s:property  value='type_name'/></dt>
				</dl>
				<%--<dl>--%>
					<%--<dt style="width:90px;">体检套餐:</dt><dt style="width:66%"><s:property  value='set_name'/></dt>--%>
				<%--</dl>--%>
				<%--<dl>--%>
					<%--<dt style="width:80px;">既往史:</dt><dt style="width:70%"><s:property value='past_medical_history'/></dt>--%>
				<%--</dl>--%>
				<dl>
					<dt style="width:90px;">检查医生:</dt><dt style="width:66%" id="exam_doctor_name"></dt>
				</dl>
				<dl>
					<dt style="width:90px;">检查时间:</dt><dt style="width:66%" id="exam_date"></dt>
				</dl>
			</div>
		</fieldset>
		<fieldset class="helthsize"  style="margin:5px;margin-left :0px;margin-left:3px;padding-right:0px;padding-left:0px;height: 45%;">
			<legend><strong>健康档案对比</strong></legend>
			<div style="padding-right: 5px;height: 30px;text-align: right;">
				<!--<a href="javascript:void(0)" onclick="lishijieguoduibi();" class="easyui-linkbutton c6" style="width:120px;height:26px;"/>历史结果对比</a>-->
			</div>
			<div  class="user-query"   id='result'></div>
		</fieldset>
	</div>



	<!--右侧展示-->
	<div style="height:50%;width:78%;" data-options="region:'east'" >
		<div>
			<fieldset style="margin:2px;padding-right:0;height: 50px;">
				<legend><strong>信息检索</strong></legend>
				<div class="user-query">
					<dl>
						<dt style="height:26px;width:80px;margin-left: 40px;">体检号</dt>
						<dd><input class="easyui-textbox"  type="text" id="t_exam_num" value="" style="height:26px;width:145px;"/></dd>
						<dd><a href="javascript:getcustomerInfo();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:120px;">查询</a></dd>
					</dl>
				</div>
			</fieldset>
		</div>

		<div id="div_dep"  style="height:90%;" class="easyui-layout" fit="true">
			<div style="height:80%;" data-options="region:'north',title:'全科会诊协作平台'">
				<div class="easyui-tabs" fit="true">
					<div title="危急值"  data-options="tabWidth:200,border:false">
						<div id="weijizhi_div"></div>
					</div>
					<div title="异常指数"  data-options="tabWidth:200,border:false">
						<div id="yichang"></div>
					</div>
					<div title="全部" data-options="tabWidth:200,border:false">
						<div id="all"></div>
					</div>
				</div>
			</div>
			<div  data-options="region:'center'">
			</div>

			<div   style="width:18%;margin-top: 10px;background-color:#C0E1F8" data-options="region:'west',height:300">
				<ul id="some_tree" style="margin-bottom: 100px;" ></ul>
			</div>
			<div  style="width:82%;margin-top: 10px;"  data-options="region:'east'">
				<ul class="gene_head1 medical_class_title" style="margin-left: 10%; margin-bottom: 20px;"></ul>

				<div style="margin-top: 20px;">
					<ul id='dt' class="views" onkeydown="keyDown(event)"></ul>
				</div>

				<div id="hydiv" style="margin-top: 20px;margin-bottom: 300px;">
					<ul id='hy' class="views" onkeydown="hykeyDown(event)"></ul>
					<div style="margin-left: 20px;margin-top: 50px;">
						<span style="margin-left:55px;">检查医生:</span>
						<select class="easyui-combobox" id="hy_inputter"data-options="height:26,width:100,panelHeight:'auto'"></select>
						<a href="javascript:shezhiyisheng()" class="easyui-linkbutton c6 " style="width:100px;">保存</a>
						<%--<a href="javascript:close_page();" class="easyui-linkbutton" style="width:100px;" onclick=" ">返回首页</a>--%>
					</div>
				</div>

				<div  data-options="region:'center'">
					<div class="xscyc" onmouseover="select_com_list_mover()" onmouseout="select_com_list_amover()"><ul></ul></div>
				</div>


				<div id = "yxdiv" style="margin-bottom: 200px;">
					<div style="padding: 10px 70px;text-align: right;margin-top: 10px;margin-right: 60px;"><span style="margin-left:55px;">检查医生:</span><select class="easyui-combobox" id="yx_inputter"data-options="height:26,width:100,panelHeight:'auto'"></select></div>
					<div id="yingxiangkeshi_jl" class="image_depart_msg" style="margin-top: 5px;">
					</div>
				</div>


				<div id = 'ptjljy'style="margin-top: 10px;padding-left: 10px">
					<dl>
						<dt style='text-align:right;width:14.6%;padding-right:10px'>结论&nbsp;&nbsp;</dt>
						<dd style='width:30%;padding-right: 0px;padding-left: 0px'>
	    	   				<textarea rows="" cols=""  value=""  id="jielun" style="width: 100%;height: 150px;resize:none;">
	    	   				</textarea>
						</dd>
						<dt style='text-align:right;width:18.6%;padding-right:10px;'>专科建议&nbsp;&nbsp;</dt>
						<dd style='width:30%'>
	    	   				<textarea id="zhuanjiaji" style="width: 100%;height: 150px;resize:none;" ondblclick="zhuankejianyi_db();">
	    	   				</textarea>
						</dd>
					</dl>
				</div>

				<div id= "ptksbc" style="margin-top: 250px;margin-left: 500px;margin-bottom: 100px;">
					<a href="javascript:jvjian()" class="easyui-linkbutton c6 " style="width:80px;">拒检</a>
					<a href="javascript:addexamDepResult();" class="easyui-linkbutton c6 jre" style="width:80px;" onclick="">确定保存</a>
					<%--<a href="javascript:close_page();" class="easyui-linkbutton" style="width:80px;" onclick=" ">返回首页</a>--%>
					<span style="margin-left:55px;">检查医生:</span>
					<select class="easyui-combobox" id="inputter"data-options="height:26,width:100,panelHeight:'auto'">
					</select>
				</div>
			</div>
		</div>
	</div>



	<div id="resultVocabulary"   style="z-index:10002" ></div>
	<div id="dlg-zkjy" class="easyui-dialog"  data-options="width: 800,height: 550,closed: true,cache: false,modal: true,title:'专科建议'">
		<form id="add1Form">
			<div class="formdiv">
				<div class="formdiv fomr3" style="padding-top:10px;">
					<div style="height:350px;width:750px;margin-left:20px;">
						<table id="zhuankejianyi_list"></table>
					</div>
					<div style="width:750px;margin-left:20px;">
						<div style="width:100%;height:25px;line-height:25px;background:#d9d9d9;margin-top: 5px">&nbsp;&nbsp;录入结果如下</div>
						<textarea id="zkjylr" style="width:99%;height:65px;resize:none;"/></textarea>
					</div>
				</div>
			</div>
			<div class="dialog-button-box">
				<div class="inner-button-box">
					<a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:f_save_depsug();">确定</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-zkjy').dialog('close')">关闭</a>
				</div>
			</div>
		</form>
	</div>

	<div id="dlg-edit" class="easyui-dialog"  data-options="width: 1050,height: 680,top:100, closed: true,cache: false,modal: false"></div>
	<div id="shezhiyisheng" class="easyui-dialog"  data-options="width: 500,height: 350,top:200, closed: true,cache: true,modal: false"></div>
</body>
</html>