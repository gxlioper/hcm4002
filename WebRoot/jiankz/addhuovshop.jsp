<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/jiankz/addhuovshop.js?randomId=<%=Math.random()%>"></script>

<fieldset style="margin: 10px;">
	<legend><strong>添加项目</strong></legend> 
	<div class="user-query" style=" margin-top: 15px" >
		<dl>
			<dt>
				健康项目名称:
			</dt>
			<dd>
			<input  id="idaddq"  value="<s:property  value="id"/>" hidden="true"/>
				<input  type="text" class="textinput"  id="hos_data_nameadd" 
					value="<s:property value="hos_data_name"/>" class="easyui-validatebox"  
					style="height: 26px; width: 100px;" />
			</dd>
			<dt>
				健康项目拼音 :
			</dt>
			<dd>
				<input  class="textinput"  id="hos_piny"
					 class="easyui-validatebox"  value="<s:property  value="hos_piny"/>"
					style="height: 26px; width: 100px;" />
			</dd>
			<dt>
				项目类型:
			</dt>
			  <dd>
			  <input  id="data_type_code"  value="<s:property  value="data_type"/>" hidden="true"/>
		       <select class="easyui-combobox" id="data_type_name" style="width:120px;height:26px;"></select>
		    </dd>
		</dl>
		<dl>
			<dt>
				项目代码:
			</dt>
			<dd>
					<div id="huo_data_codeadd" style="height: 23px; width: 100px;"><s:property value="huo_data_code"/></div>
			</dd>
			<dt>
				项目名称 :
			</dt>
			<dd>
			<div id="huo_data_nameadd" style="height: 23px; width: 100px;"><s:property  value="huo_data_name"/></div>
			</dd>
		</dl>
	</div>
</fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>项目检索</strong></legend>
			<div class="user-query">
				<dl>
					<dt style="width:60px;">项目代码</dt>
					<dd>
						<input  type="text" class="textinput" id="hop_data_codeaddq"    style="height:26px;width:100px;" />
					</dd>
					<dt style="width:60px;">项目名称</dt>
					<dd>
						<input  type="text" class="textinput" id="hop_data_nameaddq"    style="height:26px;width:100px;" />
					</dd>
					<dt style="width:60px;">项目拼音</dt>
					<dd>
						<input  type="text" class="textinput" id="item_pinyinaddq"    style="height:26px;width:100px;" />
					</dd>
					<dd><a href="javascript:getItemProjectCheck();" class="easyui-linkbutton c6" style="width:100px;">查询</a></dd>
					<dd><a href="javascript:empty();"  class="easyui-linkbutton" style="width:100px;">清空</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:0 0 45px 0;height:145%">
<legend><strong>项目列表信息</strong></legend>
      <table id="huovshosshow" class="easyui-datagrid" >
      </table>	
 </fieldset>
 <div id="search-buttons"  class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:addHuoVsHos();" class="easyui-linkbutton c6" style="width:80px;">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#dlg-edit').dialog('close')">关闭</a>
	</div>
</div>
