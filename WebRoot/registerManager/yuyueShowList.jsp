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
<script type="text/javascript" src="<%=request.getContextPath()%>/registerManager/yuyueShowList.js?randomId=<%=Math.random()%>"></script>
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
<fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
	<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="orderid"/>订单号</dt>
					<dd><input class="easyui-textbox"  type="text" id="orderid" value="" style="height:26px;width:140px;"/></dd> 
				    <dt style="height:26px;width:60px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_num"/><s:text name="tjhname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="exam_num" value="" style="height:26px;width:100px;"/></dd> 
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="custname"/>姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:135px;"/></dd>
					<dt style="height:26px;width:50px;"><input id="chkItem" name="chkItem" type="checkbox" value="phone"/>电话</dt>					
					<dd><input class="easyui-textbox"  type="text" id="phone" value="" style="height:26px; width:100px;"/></dd>   
					<dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="order_date"/>订单日期</dt>
			         <dd><input class="easyui-datebox" type=text id="time1" value="<s:property value="model.time1"/>" style="width:100px;height:26px;"></input>
                     <dt style="height:26px;width:30px;">至</dt>
			         <dd><input class="easyui-datebox" type=text id="time2" value="<s:property value="model.time2"/>" style="width:100px;height:26px;"></input></dd>
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="status"/>订单状态</dt>	
                    <dd><select class="easyui-combobox" id="statusss" name="statusss"
						data-options="height:26,width:100,panelHeight:'auto'">
						<option value="1">预约订单</option>
					    <option value="2">订单取消</option>
					    <option value="3">订单支付</option>
						</select></dd>
			         <dt style="height:26px;width:80px;"><input id="chkItem" name="chkItem" type="checkbox" value="exam_type"/>体检类型</dt>					
					<dd><select class="easyui-combobox" id="exam_type" name="exam_type"
					data-options="height:26,width:100,panelHeight:'auto'">
					<option value="G">个人</option>
					<option value="T">团体</option>                    
                  	</select></dd>
					<dd><a href="javascript:getyuyueGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="height:26px;width:80px;">查询</a></dd>
                </dl>
			</div>		
 </fieldset>
 
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>体检人员结算明细</strong></legend>
      <table id="yuyueShowList">
      </table>	
 </fieldset>
<div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 460,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-groupshow" class="easyui-dialog"  data-options="width: 750,height: 380,closed: true,cache: false,modal: true,top:50"></div>  </body>