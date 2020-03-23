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
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/zybUploadData/zybUploadData_ww.js?randomId=<%=Math.random()%>"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyin_dict_firstletter.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/pinYin/pinyinUtil.js?randomId=<%=Math.random()%>"></script>
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
<input type="hidden" id="userid" value="<s:property value="#session.username.userid"/>"/>
<input type="hidden" id="company_id" value="0">
<input type="hidden" id="batch_id" value="0">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:115px;"/></dd> 
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:70px;"/></dd>
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="customer_sex"/>性别</dt>
					<dd><select class="easyui-combobox" id="sex" 
						data-options="height:26,width:70,panelHeight:'auto'">
						<option value="">全部</option>
						<option value="男">男</option>
						<option value="女">女</option>
						</select>
					</dd>
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="register_date"/>体检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="start_date" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="end_date" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_type"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="data_source"/>数据来源</dt>					
					<dd><select class="easyui-combobox" id="data_source" name="data_source"
					data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:90px;"><input id="chkItem" name="chkItem" type="checkbox" value="djdstatuss"/>回收导检单</dt>
					<dd><select class="easyui-combobox" id="djdstatuss"
						data-options="height:26,width:80,panelHeight:'auto'">
						<option value="">不限</option>
						<option value="N">未回收</option>
						<option value="Y">已回收</option>
						</select>
					</dd>
					
				</dl>
				 
				<dl>
				   <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="arch_num"/><s:text name="dahname"/></dt>					
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:115px;"/></dd>   
					
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="com_Name"/>单位</dt>					
					<dd><input class="easyui-textbox"  type="text" id="com_Name" value="" style="height:26px;width:200px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:175px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                    </dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" checked="checked" type="checkbox" value="over_date"/>终检日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"></input></dd> 
			          <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="creater"/>创建人</dt>					
					 <dd><select id="creater" style="width:100px;height:26px;"></select></dd>
                    
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="status"/>检查状态</dt>	
                    <dd><select class="easyui-combobox" id="statusss" name="statusss"
						data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
                    
                    <dt style="height:26px;width:90px;"><input id="chkItem" name="chkItem" type="checkbox" value="conn_rylb"/>人员类别</dt>	
                    <dd><select class="easyui-combobox" id="conn_rylb" name="conn_rylb" data-options="height:26,width:80,panelHeight:'auto'"></select></dd>
                    
                </dl>
                <dl>
                	<dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="id_num"/>身份证</dt>	
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:135px;"/></dd>
					
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="batch_Name"/>任务</dt>					
					<dd>
						 <select class="easyui-combobox" id="batch_ids" name="batch_ids"	data-options="height:26,width:200,panelHeight:'auto'"></select>
                    </dd>
					
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="yuyue_date"/>预约日期</dt>
			         <dd><input class="easyui-datebox" type=text id="yuyue_date1" value="<s:property value="model.yuyue_date1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="yuyue_date2" value="<s:property value="model.yuyue_date2"/>" style="width:100px;height:26px;"></input></dd>
                    
                    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="levels"/>选择部门</dt>
					<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="tjlx"/>体检类别</dt>
					<dd><select class="easyui-combobox" id="tjlx" name="tjlx"
						data-options="height:26,width:100,panelHeight:'auto'"></select>
					</dd>
                    
					<dd><a href="javascript:getZybUserListGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:100px;">查询</a></dd>
                </dl>
			</div>		
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
  </body>