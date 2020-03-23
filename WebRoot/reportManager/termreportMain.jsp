<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE;" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#main {	width: auto;	height: 130px;}
#left {	width: 55%;	height: auto;}
#right {	width: 43%;	height: auto;	margin-left: 2px;}
#left, #right {	float: left;}
</style>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/reportManager/termreportMain.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript">
$(function(){
	$('#exam_num').textbox('textbox').css('ime-mode','disabled');
	$('#exam_num').textbox('textbox').focus();
	
	$("input").attr("maxlength","20");
})
</script>
<body style="height:500px;">
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="zyb_barcode_print_type" value="<s:property value="model.zyb_barcode_print_type"/>">
<input type="hidden" id="company_id" maxlength="20" value="<s:property value="model.company_id"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
					<dd style="height:20px;width:250px;">
					     选择单位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dd style="height:20px;width:250px;">
					     选择体检任务&nbsp;&nbsp;&nbsp;
					  <select class="easyui-combobox" id="batch_id" name="batch_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
                    </dd>
                    <dd style="height:20px;width:800px;">
					     <a
						href="javascript:reportyulan();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">团报预览</a>&nbsp; <a
						href="javascript:reportprint();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">团报打印</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						  <a
						href="javascript:zybreportprint(1);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">职业岗前</a>&nbsp; <a
						href="javascript:zybreportprint(2);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">职业岗中</a>
						&nbsp;
						 <a
						href="javascript:zybreportprint(3);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">职业离岗</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
						href="javascript:zybreportprint(4);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">放射岗前</a>
						&nbsp;
						 <a
						href="javascript:zybreportprint(5);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">放射岗中</a>&nbsp; <a
						href="javascript:zybreportprint(6);"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 76px;">放射离岗</a>
				     </dd>
				</dl>
			</div>
 </fieldset>
<div id="main">
		<div id="left">	
<fieldset style="margin: 5px; padding-right: 0;">
		<legend>
			<strong>普通体检查询</strong>
		</legend>
		<div class="user-query">
			<dl>
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="levels"/>选择部门</dt>
					<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
				 <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="tjlx"/>体检类别</dt>
			      <dd><select class="easyui-combobox" id="tjlx" name="tjlx" data-options="height:26,width:120,panelHeight:'auto'"></select>
			      </dd>	
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/>体检编号</dt>
				<dd>
					<input class="easyui-textbox" type=text id="exam_num"  style="width: 140px; height: 26px;"></input>
				</dd>
			   </dl>
			   
			   
               <dl>
               <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="group_id"/>任务分组</dt>					
				<dd>
				    <select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
			    </dd> 
			    
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_status"/>体检状态</dt>					
				<dd><select class="easyui-combobox" id="exam_status" name="exam_status"
					data-options="height:26,width:120,panelHeight:'auto'"></select>				    
			    </dd>				
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
				<dd>
					<input class="easyui-textbox" type=text id="custname"  style="width: 140px; height: 26px;"></input> 					
				</dd>
				  </dl>
				  
				  
               <dl>
               <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="set_id"/>分组套餐</dt>					
				<dd>
				    <select class="easyui-combobox" id="set_id" name="set_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
			    </dd>				
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="rylb"/>人员类别</dt>
			     <dd><select class="easyui-combobox" id="rylb" name="rylb" data-options="height:26,width:120,panelHeight:'auto'"></select>
                   <a
						href="javascript:getteamExamInfoShowGrid();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 50px;">查询</a>
			    </dd>
			</dl>
		</div>
	</fieldset>
	</div>
   <!--右边面版  -->
		 <div id="right">
  			<!--上  -->
  			<fieldset style="margin: 5px; padding-right: 0;">
		    <legend>
			<strong>职业病体检查询</strong>
		</legend>
		<div class="user-query">
			<dl>
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
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybexam_status"/>体检状态</dt>					
				<dd><select class="easyui-combobox" id="zybexam_status" name="zybexam_status"
					data-options="height:26,width:120,panelHeight:'auto'"></select>				    
			    </dd>	    				
				 <dt style="height: 26px; width: 50px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybexam_num"/>编号</dt>
				<dd>
					<input class="easyui-textbox" type=text id="zybexam_num"  style="width: 80px; height: 26px;"></input>
				</dd>
				 </dl>
               <dl>		
              	<dt style="height:26px;width:110px;"><input id="chkItem" name="chkItem" type="checkbox" value="zywhys"/>职业危害因素</dt>					
				<dd>
				    <select class="easyui-combobox" id="zywhys_id" name="zywhys_id"	data-options="height:26,width:140,panelHeight:'auto'"></select>
			    </dd>  
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="zybis_report_print"/>打印状态</dt>					
				<dd><select class="easyui-combobox" id="zybis_report_print" name="zybis_report_print"
					data-options="height:26,width:120,panelHeight:'auto'"></select>				    
			    </dd>	    				
				
			      <a href="javascript:getteamExamInfoShowGridzyb();" class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 50px;">查询</a>
				  <a href="javascript:queryEmpty();"  class="easyui-linkbutton" 
						style="height: 26px; width: 50px;">清空</a>
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
 </body>