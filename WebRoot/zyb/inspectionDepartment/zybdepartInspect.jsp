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
<title>职业病科室检查</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/departAndFinaly.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/inspectionDepartment/zybdepartInspect.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>';
</script>
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
	width: 15%;
	text-align: center;
}
.medical_class_title li:hover, .medical_class_title li.active {
	border-top: 2px solid #18A495;
	line-height: 23px;
}
.jre{}
.test_box {
    width: 60%; 
    height: 25px; 
    line-height: 1.5;
    border: 1px solid #a0b3d6; 
    margin-left:10px;
    resize:none;
}

.views{ overflow:hidden; width:100%;}
.views li{ margin:5px 0; line-height:20px; float:left; width:49%;}
.views li label{ width:150px; display:block; float:left; text-align:right;}

.xscyc{min-height:50px;overflow-y:auto;max-height:330px;border:solid 1px #3399cc;position:absolute;z-index:10000;background:#ffffff;display:none}
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
<input type="hidden"  id='dep_id' value="<s:property value="#session.username.dep_id"/>"/>
<input type="hidden"  id='dep_num' value="<s:property value="#session.username.deptCode"/>"/>
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden"  id='id' value="<s:property value='id'/>"/>
<input type="hidden"  id='exam_num' value="<s:property value='exam_num'/>"/>
<input type="hidden"  id='c_id' value="<s:property value='c_id'/>"/>
<input type="hidden"  id='app_type' value="<s:property value='app_type'/>"/>
<input type="hidden"  id='status' value="<s:property value='status'/>"/>
<input type="hidden" id='isprint' value="<s:property value="#session.username.isPrint"/>"/>
<!--检查日期  -->
<input type="hidden" id='exam_type_code' value="<s:property value='exam_type_code'/>"/>
<input type="hidden" id='isCheckSensvitiveWord' value="<s:property value='isCheckSensvitiveWord'/>"/>
<input type="hidden" id='join_date' value="<s:property value='join_date'/>"/>
<input type="hidden" id='picture_path' value="<s:property value='picture_path'/>"/>
<input type="hidden" id="exam_result_summary" value="<s:property value='exam_result_summary'/>"/>
<input type="hidden" id="moren_exam_result" value="<s:property value='exam_result'/>"/>
<input type="hidden" id="suggestion"  value="<s:property value='suggestion'/>"/>
<input type="hidden" id="is_departinspect_summary_edit"  value="<s:property value='is_departinspect_summary_edit'/>"/>
	<div style="width:25%" data-options="region:'west'">
				<fieldset style="margin:5px;">
	    			<legend><strong>基本信息</strong></legend>
				<div class="user-query">
						<img id="exampic" style="height:120px;width:100px;float:right;" src="<%=request.getContextPath()%>/themes/default/images/user.png" />
						<dl style="width: 160px;">
							<dt style="width:60px;"><s:text name="dahname"/>：</dt><dt style="width:80px;"><s:property value='arch_num'/></dt>
						</dl>
						<dl style="width: 160px;">
							<dt style="width:60px;"><s:text name="tjhname"/>：</dt><dt style="width:85px;"  id="tjh"><s:property value='exam_num'/></dt>
						</dl>
						<dl style="width: 160px;">
							<dt style="width:55px;">姓名：</dt><dt style="width:80px;"><s:property value='user_name'/></dt>
						</dl>
						<dl style="width: 160px;">
							<dt style="width:45px;">性别：</dt><dt id="sex" style="width:35px;"><s:property value='sex'/></dt>
							<dt style="width:45px;">年龄：</dt><dt style="width:30px;"><s:property value='age'/></dt>
						</dl>
						<dl>
							<dt style="width:70px;">人员类型：</dt><dt style="width:200px;"><s:property  value='type_name'/></dt>
						</dl>
						<dl>
							<dt style="width:70px;">体检套餐：</dt><dt style="width:200px"><s:property  value='set_name'/></dt>
						</dl>
						<dl>
							<dt style="width:55px;">单位：</dt><dt style="width:200px;"><s:property value='company'/></dt>
						</dl>
						<dl>
							<dt style="width:60px;">既往史：</dt><dt style="width:200px;"><s:property value='past_medical_history'/></dt>
						</dl>
						<dl>
							<dt style="width:70px;">检查医生：</dt><dt style="width:200px;" id="exam_doctor_name"></dt>
						</dl>
						<dl>
							<dt style="width:70px;">检查时间：</dt><dt style="width:200px;" id="exam_date"></dt>
						</dl>
					   </div>
					</fieldset>
					<fieldset style="margin:5px;">
	    			<legend><strong>从业信息</strong></legend>
	    				<div class="user-query">
	    					<dl>
	    						<dt style="width:70px;">行业：</dt><dt style="width:200px;"><s:property  value='occusectorid'/></dt>
	    						
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">从业行业：</dt><dt style="width:200px;"><s:property  value='occusector'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">工种：</dt><dt style="width:200px;"><s:property  value='occutypeofworkid'/></dt>
	    						
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">从业工种：</dt><dt style="width:200px;"><s:property  value='occutypeofwork'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">工龄(年)：</dt><dt style="width:30px;"><s:property  value='employeeage'/></dt>
	    						<dt style="width:100px;">接害工龄(年)：</dt><dt style="width:30px;"><s:property  value='damage'/></dt>
	    					</dl>
	    					<dl>
	    						<dt style="width:70px;">进厂日期：</dt><dt style="width:100px;"><s:property  value='joinDatetime'/></dt>
	    						<dt><a href="javascript:void(0)" onclick="fn_show_zhiyeshi();" class="easyui-linkbutton c6" style="width:100px;height:26px;"/>查看职业史</a></dt>
	    					</dl>
	    				</div>
	    			</fieldset>
	    			<div style="height:140px;margin:5px;">
	    				<table id="zywhysset"></table>
	    			</div>
	    			<fieldset style="margin:5px;">
	    			<legend><strong>健康档案对比</strong></legend>
	    				<div style="padding-right: 5px;height: 30px;text-align: right;">
		    				<a href="javascript:void(0)" onclick="lishijieguoduibi();" class="easyui-linkbutton c6" style="width:120px;height:26px;"/>历史结果对比</a>
						</div>
						<div  class="user-query" id='result'></div>
	    			</fieldset>
	</div>
	<div style="height:100%;width:75%" data-options="region:'east'">
		<div id="div_dep" class="easyui-layout" fit="true">
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
 			<div data-options="region:'center'">
				<div class="xscyc" onmouseover="select_com_list_mover()" onmouseout="select_com_list_amover()"><ul></ul></div>
				<div style="margin: 10px;">
					<ul class="gene_head1 medical_class_title" style="margin-left: 10%; margin-bottom: 20px;"></ul>
				</div>
				<div>
  					<ul id='dt' class="views" onkeydown="keyDown(event)"></ul>
  				</div>
	    	   	<div style="margin-top: 10px;">
	    	   		<ul class="views">
	    	   			<li>
	    	   				<label>结论</label>
	    	   				<textarea  id="jielun" style="width: 60%;height: 200px;resize:none;margin-left: 10px;">
		    	   			</textarea>
	    	   			</li>
	    	   			<li>
	    	   				<label>专科建议</label>
	    	   				<textarea id="zhuanjiaji" style="width: 60%;height: 200px;resize:none;margin-left: 10px;" ondblclick="zhuankejianyi_db();">
		    	   			</textarea>
	    	   			</li>
	    	   		</ul>
	    	   	</div>
	 	  </div>
	 	</div>
    </div>
    <div data-options="region:'south'" style="height:55px;">
    	 <div class="sub_btn">
    	 	<a href="javascript:void(0)"  onclick="shouSurver();"  class="easyui-linkbutton c6"  id="wenjuan"    style="width:100px;display:none;"/>问卷</a>
    	 	<s:if test ="carPdf_button eq \"Y\"">
				<a href="javascript:void(0)" class="easyui-linkbutton c6"  id="pdfbuttion" style="display:none;" onclick="javascript:wenjuanpdf();">健康评测报告</a>
			</s:if>
    	 	<a href="javascript:void(0)"  onclick="zhuankejianyi();"  class="easyui-linkbutton c6" style="width:100px;"/>生成建议</a>
			<a href="javascript:weijizhitanchuan();" class="easyui-linkbutton c6" style="width:100px;" onclick="">危机/危急值</a>
			<a href="javascript:jvjian()" class="easyui-linkbutton c6 " style="width:80px;">拒检</a>
			<a href="javascript:addexamDepResult();" class="easyui-linkbutton c6 jre" style="width:80px;" onclick="">确定保存</a>
			<a href="javascript:qinchujieguo();" class="easyui-linkbutton  " style="width:80px;display:none;" onclick="">清除结果</a>
			<a href="javascript:close_page();" class="easyui-linkbutton" style="width:80px;" onclick=" ">返回首页</a>
			<span style="margin-left:55px;">录入者:</span>					
			<select class="easyui-combobox" id="inputter"data-options="height:26,width:100,panelHeight:'auto'">
			</select>
		</div>
    </div>
    </div>
    <div id="resultVocabulary"   style="z-index:10002" ></div>
    <div id="history" class="easyui-dialog" data-options="width: 600,height:300,closed: true,cache: false,modal: true,top:100" ></div>
    <div id="weijizhil" class="easyui-dialog" data-options="width: 600,height:300,closed: true,cache: false,modal: true,top:100" ></div>
    <div id="shou_wenjuan" class="easyui-dialog" data-options="width: 600,height:300,closed: true,cache: false,modal: true,top:100" ></div>
    <div id="dlg-item" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'科室采样'">
		<form id="add1Form">
			<div class="formdiv">
			<div class="formdiv fomr3" style="padding-top:0px;">
				<div style="height:350px;width:890px;margin-left:0px;" onkeydown="keyDown(event);">
					<table id="exam_item_list"></table>
				</div>
		</div>
		</div>
		<div class="dialog-button-box">
			<div class="inner-button-box">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" id="kscyqr" style="width:80px;" onclick="javascript:save_sampling_barcode();">确定采样</a>
			    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-item').dialog('close')">关闭</a>
			</div>
		</div>
		</form>	
	</div>
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
<div id="dlg-zys" class="easyui-dialog"  data-options="width: 900,height: 450,closed: true,cache: false,modal: true,title:'职业史'">
	<form id="add1Form">
	<div class="formdiv">
		<div class="formdiv fomr3" style="padding-top:10px;">
			<div style="height:350px;width:850px;margin-left:20px;">
				<table id="exam_zhiyeshi"></table>
			</div>
		</div>
	</div>
	<div class="dialog-button-box">
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-zys').dialog('close')">关闭</a>
		</div>
	</div>
	</form>	
</div>
</body>
</html>