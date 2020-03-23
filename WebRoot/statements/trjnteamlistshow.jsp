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
<script type="text/javascript" src="<%=request.getContextPath()%>/statements/trjnteamlistshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
$(function(){
	$("input").attr("maxlength","20");
})
</script>
<body>
<!-- 定义身份证设备结束 -->
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="<s:property value="model.batchid"/>">
<input type="hidden" id="acc_num" value="<s:property value="model.acc_num"/>">
<input type="hidden" id="batchcompany_id">

	<fieldset style="margin: 5px; padding-right: 0;">
		<legend>
			<strong>信息检索</strong>
		</legend>
		<div class="user-query">
			<dl>
			
			 <dt style="height:26px;width:70px;"><input id="chkItem" name="chkItem" type="checkbox" value="tjrw"/>体检任务</dt>
					<dd>
					<input type="text" id="tjrw" value="" style="height:22px;width:100px;"/>
					  <div id="com_name_list_div1" style="display: none; height: 420px; width: 300px;; overflow-y: scroll; margin-left: -20px;"
					      onmouseover="select_batchcom_list_mover()" 
					      onmouseout="select_batchcom_list_amover()">
                      </div>
                    </dd>   
			
				
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="levels"/>选择部门</dt>
					<dd><select class="easyui-combobox" id="levelss" name="levelss"	data-options="height:26,width:140,panelHeight:'auto'"></select></dd>
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="group_id"/>任务分组</dt>					
				<dd>
				    <select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:150,panelHeight:'auto'"></select>
			    </dd>  
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/>体检编号</dt>
				<dd>
					<input class="easyui-textbox" type=text id="exam_num"  style="width: 80px; height: 26px;"></input>
				</dd>
			   
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" checked type="checkbox" value="join_date"/>报道日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time3" value="<s:property value="model.time3"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time4" value="<s:property value="model.time4"/>" style="width:100px;height:26px;"></input></dd>
			         
			          <dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="sex"/>性别</dt>					
				<dd>
				    <select class="easyui-combobox" id="sex" name="sex"	data-options="height:26,width:50,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{
                   label: '男',  
                   value: '男',  
                   selected:true},
                   {label: '女',  
                   value: '女'}]"></select>
                   </dd>
			          
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="rylb"/>人员类别</dt>
			     <dd><select class="easyui-combobox" id="rylb" name="rylb"
					data-options="height:26,width:80,panelHeight:'auto'"></select>
				</dd>
		       </dl>
               <dl>
               <dt style="height: 26px; width: 70px;"><input id="chkItem" name="chkItem" type="checkbox" value="com_name"/>单位名称</dt>
				<dd>
					<input type="text" id="com_Name" value="" style="height:20px;width:100px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:0px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
				</dd>
				<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_status"/>体检状态</dt>					
				<dd>
				    <select class="easyui-combobox" id="exam_status" name="exam_status"	data-options="height:26,width:140,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '未报到',  
                   value: 'Y'},{  
                   label: '已登记',  
                   value: 'D'},
                   {label: '检查中',  
                   value: 'J'},
                   {label: '已终检',
                   selected:true, 
                   value: 'Z'}]"></select>    
			    </dd> 
			    <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="set_id"/>分组套餐</dt>					
				<dd>
				    <select class="easyui-combobox" id="set_id" name="set_id"	data-options="height:26,width:150,panelHeight:'auto'"></select>
			    </dd> 
				
				<dt style="height: 26px; width: 80px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
				<dd>
					<input class="easyui-textbox" type=text id="custname"  style="width: 80px; height: 26px;"></input>					
				</dd>
				 <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" checked type="checkbox" value="acc_date"/>结算日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:20px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd> 
			    
				 
			 <dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="isprint"/>打印</dt>					
				<dd>
				    <select class="easyui-combobox" id="isprint" name="isprint"	data-options="height:26,width:50,panelHeight:'auto' ,valueField: 'value',textField: 'label',data: [{  
                   label: '是',  
                   value: 'Y'},
                   {label: '否',  
                   value: 'N',  
                   selected:true}]"></select>
                   <a
						href="javascript:getteamExamInfoShowGrid();"
						class="easyui-linkbutton c6 l-btn l-btn-small"
						style="height: 26px; width: 50px;">查询</a>
					
			    </dd>
			</dl>
		</div>
	</fieldset>
	<fieldset style="margin:5px;padding-right:0;">
<legend><strong>已日结体检人员信息</strong></legend>
      <table id="teamExamInfoShow">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>