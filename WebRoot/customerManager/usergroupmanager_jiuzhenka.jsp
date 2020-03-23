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
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/usergroupmanager_jiuzhenka.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body style="height:100%;">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="barexeurl" value="<s:property value="model.time1"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.time2"/>">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="barcode_print_type" value="<s:property value="model.barcode_print_type"/>">
<input type="hidden" id="batch_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;">体检任务</dt>
					<dd>
					<input type="text" id="tjrw" value="" style="height:22px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-20px;" 
					      onmouseover="select_batchcom_list_mover()" 
					      onmouseout="select_batchcom_list_amover()">
                      </div>
                    </dd>   
					<dt style="height:26px;width:60px;">选择分组</dt>
					<dd><select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:60px;">体检状态</dt>
					<dd><select class="easyui-combobox" id="status" name="status"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:60px;">档案号</dt>
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:140px;"/></dd>
					<dt style="height:26px;width:60px;">婚否</dt>
					<dd><select class="easyui-combobox" id="is_Marriage" name="is_Marriage" value="<s:property value="model.is_Marriage"/>"
					data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:60px;">性别</dt>					
				    <dd>
				    <select class="easyui-combobox" id="sex" name="sex"	data-options="height:26,width:90,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '全部',  
                   value: '',  
                   selected:true},{  
                   label: '男',  
                   value: '男'},
                   {label: '女',  
                   value: '女'}]"></select>
			    </dd> 
				</dl>
				 
				<dl>
				<dt style="height:26px;width:60px;">选择单位</dt>					
					<dd><input  type="text" id="com_Name" value="" style="height:22px;width:140px;"/>
					  <div id="com_name_list_div1" style="display:none;margin-left:-20px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>   
                    <dt style="height:26px;width:60px;">选择部门</dt>
					<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
                    
										<!--dt>身份证号</dt>
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:140px;"/></dd-->
										<dt style="height:26px;width:60px;">姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:140px;"/></dd>
					<dt style="height:26px;width:60px;">预约日期</dt>
			         <dd><input class="easyui-datebox" type=text id="register_date" style="width:140px;height:26px;"></input>
					<dt style="height:26px;width:60px;">人员类别</dt>
					<dd><select class="easyui-combobox" id="rylb" name="rylb" data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:60px;">状态</dt>	
                    <dd><select class="easyui-combobox" id="djdstatuss" name="djdstatuss"	data-options="height:26,width:90,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '全部',  
                   value: ''},{  
                   label: '未打导检单',  
                   value: 'N'},
                   {label: '已打导检单',  
                   value: 'Y'}]"></select></dd>
			
				</dl>
				<dl>
					<dt style="height:26px;width:60px;">体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx" data-options="height:26,width:140,panelHeight:'auto'"></select></dd>	
						    <dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 920,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-printitem" class="easyui-dialog"  data-options="width: 600,height: 400,closed: true,cache: false,modal: true,top:50"></div>
  </body>