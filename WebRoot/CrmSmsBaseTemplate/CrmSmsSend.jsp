<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/CrmSmsBaseTemplate/CrmSmsSend.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname='<s:text name="tjhname"/>'; 
$(function(){
	$("input").attr("maxlength","45");
	$('#exam_num').css('ime-mode','disabled');
	$('#exam_num').focus();
})
</script>
<body >
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="baseurls" value="<%=request.getContextPath()%>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="exeurl" value="<s:property value="model.others"/>">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>短信记录查询</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="width:80px;"><input type="checkbox"  id="smstemp_cke" />短信模板</dt>
					<dd><select class="easyui-combobox" id="smsBaseTemplate"
										name="smsBaseTemplate"
										data-options="height:26,width:120,panelHeight:'auto'"></select></dd>
					<dt style="width:80px;"><input type="checkbox"  id="exam_num_cke" /><s:text name="tjhname"/></dt>
					<dd><input class="textinput"  type="text" id="exam_num"  style="height:23px;width:120px;"/></dd>
					<dt style="width:80px;"><input type="checkbox"  id="name_cke" />姓名</dt>
					<dd>
						<input class="textinput"  type="text" id="name"  style="height:23px;width:120px;"/>
					</dd>
					<dt style="width:80px;"><input type="checkbox"  id="phone_cke" />电话</dt>
					<dd>
						<input class="textinput"  type="text" id="phone"  style="height:23px;width:120px;"/>
					</dd>
					<dt style="width:80px;"><input type="checkbox"  id="user_type_cke" />用户类型</dt>
					<dd>
						<select id="user_type" class="easyui-combobox" name="dept"  data-options="panelHeight:'auto'" style="height:25px;width:120px;">   
						    <option value='1'>体检用户</option>   
						    <option value='0'>工作人员</option>   
						</select> 
					</dd>
				</dl>
				<dl>
					<dt style="width:80px;"><input type="checkbox"  id="sms_type_cke" />发送类型</dt>
					<dd>
						<select id="sms_type" class="easyui-combobox" data-options="panelHeight:'auto'" name="dept" style="height:25px;width:120px;">   
						    <option value='1'>立即发送</option>   
						    <option value='0'>延期发送</option>   
						</select> 
					</dd>
					<dt style="width: 80px;"><input type="checkbox"  id="sms_status_cke" />发送状态</dt>
					<dd>
						<select id="sms_status" class="easyui-combobox" data-options="panelHeight:'auto'"  style="height:25px;width:120px;">   
						    <option value='1'>发送成功</option>   
						    <option value='2'>发送失败</option>  
						      <option value='0'>未发送</option>     
						</select> 
					</dd>
					<dt style="width:80px;"><input type="checkbox"  checked="checked" id="sms_date_cke" />发送时间</dt>
					<dd><input class="easyui-datebox"  type="text" id="sms_date_q"  value="<s:property value='time1'/>"  style="height:23px;width:120px;"/>
					至
					<input class="easyui-datebox"  type="text" id="sms_date_j"  value="<s:property value='time2'/>"  style="height:23px;width:120px;"/></dd>
					
					<dd><a href="javascript:getgroupuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:80px;height: 25px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;height:80%" >
<legend><strong>短信记录列表</strong></legend> 
      <table id="groupusershow" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 400,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>