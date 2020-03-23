<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtreeck.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/findcustOneDingwei.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<input type="hidden" id="onecompany_id" value="<s:property value="model.company_id"/>">
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				<dt style="height:26px;width:60px;"><s:text name="dahname"/></dt>
					<dd><input type="text" id="onearch_num" value="" style="height:26px;width:100px;"/></dd>
				    <dt style="height:26px;width:60px;">姓名</dt>
					<dd><input type="text" id="onecustname" value="<s:property value="model.custname"/>" style="height:26px;width:145px;"/></dd>
					<dt style="height:26px;width:60px;">性别</dt>
					<dd><select class="easyui-combobox" id="onesex" name="onesex"
					data-options="height:26,width:60,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:40px;">电话</dt>
					<dd><input  type="text" id="onetel" value="<s:property value="model.tel"/>" style="height:26px;width:100px;"/></dd>
				</dl>
				 
				<dl>
				<dt style="height:26px;width:60px;"><s:text name="tjhname"/></dt>
					<dd><input type="text" class="easyui-textbox" id="oneexam_num" value="" style="height:26px;width:100px;ime-mode: disabled;"/></dd>
					<dt style="height:26px;width:60px;">身份证号</dt>
					<dd><input id="ondid_num" value="<s:property value="model.tel"/>" style="height:26px;width:145px;"/></dd>
				
				<dt style="height:26px;width:60px;">选择单位</dt>					
					<dd><input  type="text" id="onecom_Name" value="" style="height:26px;width:210px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:360px;" 
					      onmouseover="select_onecom_list_mover()" 
					      onmouseout="select_onecom_list_amover()">
                      </div>
                    </dd>   
                   	<dd><a href="javascript:setonefzchareitemListGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 
<fieldset style="margin: 5px; padding-right: 0;">
	<legend>
		<strong>查询结果列表</strong>
	</legend>
	<div id="coustomerDingweilist"></div>		
</fieldset>

