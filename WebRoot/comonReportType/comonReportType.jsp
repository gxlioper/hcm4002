<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/comonReportType/comonReportType.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body style="height:500px;">
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<input type="hidden" id="company_id" maxlength="20" value="<s:property value="model.company_id"/>">
<div id="main">
   <!--右边面版  -->
		 <div id="right">
  			<!--上  -->
  			<fieldset style="margin: 5px; padding-right: 0;">
		    <legend>
			<strong>信息检索</strong>
		</legend>
		<div class="user-query">
				<dl>
					<dt style="height:26px;width:110px;">
					  <input id="chkItem" name="chkItem" type="checkbox" value="company_id"/>
					     选择单位</dt>
					<dd style="height:20px;">
					     <input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none; left: 135px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:120px;">
					  <input id="chkItem" name="chkItem" type="checkbox" value="batchid"/>
					     选择体检任务</dt>
                    <dd style="height:20px;">
					  <select class="easyui-combobox" id="batch_id" name="batch_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
                    </dd>
                    <dt style="height:26px;width:110px;"><input id="chkItem" name="chkItem" type="checkbox" value="zyblevels"/>选择部门</dt>
					<dd><select class="easyui-combobox" id="zyblevelss" name="zyblevelss"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>		
				
					
					 <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybtjlx"/>体检类别</dt>
				      <dd><select class="easyui-combobox" id="zybtjlx" name="zybtjlx" data-options="height:26,width:120,panelHeight:'auto'"></select>
				      </dd>	
				      	
			       <dt style="height: 26px; width: 50px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybcustname"/>姓名</dt>
					<dd>
						<input class="easyui-textbox" type=text id="zybcustname"  style="width: 80px; height: 26px;"></input> 
					</dd>
						
				</dl>

               <dl>	
	               <dt style="height:26px;width:110px;"><input id="chkItem" name="chkItem" type="checkbox" value="zytjlx"/>职业体检类型</dt>
					<dd><select class="easyui-combobox" id="zytjlx_id" name="zytjlx_id"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>	
				    <dt style="height:26px;width:120px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybexam_status"/>体检状态</dt>					
					<dd><select class="easyui-combobox" id="zybexam_status" name="zybexam_status"
						data-options="height:26,width:140,panelHeight:'auto'"></select>				    
				    </dd>	    				
					 
					<dt style="height:26px;width:110px;"><input id="chkItem" name="chkItem" type="checkbox" value="zywhys"/>职业危害因素</dt>					
					<dd>
					    <select class="easyui-combobox" id="zywhys_id" name="zywhys_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
				    </dd>  
				    
				    
				    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybis_report_print"/>打印状态</dt>					
					<dd><select class="easyui-combobox" id="zybis_report_print" name="zybis_report_print"
						data-options="height:26,width:120,panelHeight:'auto'"></select>				    
				    </dd>	
				    <dt style="height: 26px; width: 50px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybexam_num"/>编号</dt>
					<dd>
						<input class="easyui-textbox" type=text id="zybexam_num"  style="width: 80px; height: 26px;"></input>
					</dd>    				
					
				      <a href="javascript:getteamExamInfoShowGridzyb();" class="easyui-linkbutton c6 l-btn l-btn-small"
							style="height: 26px; width: 50px;">查询</a>
					  <!-- <a href="javascript:queryEmpty();"  class="easyui-linkbutton" 
							style="height: 26px; width: 50px;">清空</a> -->
				    </dd>
				 </dl>
		</div>
	</fieldset>
</div>
</div>
	<fieldset style="margin:5px;padding-right:0;">
      <legend><strong>体检人员信息</strong></legend>
      <table id="teamExamInfoShow"></table>	
 </fieldset>
 
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 
 <div id="dlg-reportEdit" class="easyui-dialog"  data-options="width: 400,height: 200,closed: true,cache: false,modal: true,title:'修改报告类别'">
		<div class="formdiv fomr3" style="padding-top:30px;">
			<dt style="height:26px;width:90px;">报告类型</dt>
				<dd><select class="easyui-combobox" id="commonReportType" name="commonReportType" data-options="height:26,width:140,panelHeight:'auto'"></select>
			</dd>
		</div>
	<div class="dialog-button-box">
		<input type="text" value="" id="itemIdMsg" style="display: none;"/>
		<div class="inner-button-box">
		    <a href="javascript:void(0)" class="easyui-linkbutton c6" style="width:80px;" onclick="javascript:updateReportEdit();">确定</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-reportEdit').dialog('close')">关闭</a>
		</div>
	</div>
</div>
 </body>