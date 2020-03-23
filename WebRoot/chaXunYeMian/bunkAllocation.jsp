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
<script type="text/javascript" src="<%=request.getContextPath()%>/chaXunYeMian/bunkAllocation.js?randomId=<%=Math.random()%>"></script>
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

<input type="hidden" id="batch_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="register_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_type"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
				</dl>
				 
				<dl>
				   <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>   
                    <dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证</dt>					
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="over_date"/>终检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"></input></dd> 
			          <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="create_time"/>创建日期</dt>					
					 <dd><input class="easyui-datebox" type=text id="create_time" value="<s:property value="model.create_time"/>" style="width:100px;height:26px;"></input></dd>
                </dl>
                <dl>
                	<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="com_Name"/>选择单位</dt>					
					<dd><input type="hidden" id="company_id"/><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:305px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:1px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="status"/>检查状态</dt>	
                    <dd><select class="easyui-combobox" id="statusss" name="statusss"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
				    
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
                </dl>
			</div>		
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-bunk" class="easyui-dialog"  data-options="width: 600,height: 300,closed: true,cache: false,modal: true,top:50"></div>
  </body>