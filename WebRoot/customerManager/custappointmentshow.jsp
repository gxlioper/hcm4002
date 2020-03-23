<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>

<%@taglib prefix="s" uri="/struts-tags"%>
<title>菜单管理</title>
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/customerManager/custappointmentshow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
</head>
<body style="height:100%;">
<input type="hidden" id="barexeurl" value="<s:property value="model.time1"/>">
<input type="hidden" id="djdexeurl" value="<s:property value="model.time2"/>">
<input type="hidden" id="hansidjdflag" value="<s:property value="model.hansidjdflag"/>">
<input type="hidden" id="company_id" value="<s:property value="model.company_id"/>">
<input type="hidden" id="batch_id" value="0">
<div id="cont-page" class="include-page" >

<div class="easyui-layout" id="custappontshollist">
    <div data-options="region:'west',split:false" id="custappontshollistwest"  style="width:400px;">
    <fieldset style="margin:5px;padding-right:0;">
      <legend><strong>体检人员预约统计列表</strong></legend>
        <table id="custtimelist" >
        </table>	
      </fieldset>
    </div>
    <div  data-options="region:'center'">    

 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
				    <dt style="height:26px;width:60px;">选择单位</dt>					
					<dd><input type="text" id="com_Name" value="" style="height:20px;width:140px;"/>
						  <div id="com_name_list_div1" style="display:none;margin-left:10px;" 
						      onmouseover="select_com_list_mover()" 
						      onmouseout="select_com_list_amover()">
	                      </div>
	                 </dd>
                    <dt style="height:26px;width:60px;">选择分组</dt>
					<dd><select class="easyui-combobox" id="group_id" name="group_id"	data-options="height:26,width:100,panelHeight:'auto'"></select></dd>
					<dt style="height:26px;width:60px;">预约日期<strong class="quest-color">*</strong></dt>
			         <dd><input class="easyui-datebox" type=text id="register_date" style="width:100px;height:26px;"></input>
			         <dt style="height:26px;width:60px;">电话</dt>
					<dd><input class="easyui-textbox"  type="text" id="tel" value="" style="height:26px;width:100px;"/></dd>
					
				</dl>
				 
				<dl>
				<dt style="height:26px;width:60px;">体检任务</dt>
					<dd>
					<input type="text" id="tjrw" value="" style="height:20px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-20px;" 
					      onmouseover="select_batchcom_list_mover()" 
					      onmouseout="select_batchcom_list_amover()">
                      </div>
					</dd>   
				
				    <!-- dt>身份证号</dt>
					<dd><input class="easyui-textbox"  type="text" id="id_num" value="" style="height:26px;width:140px;"/></dd-->
					<dt style="height:26px;width:60px;"><s:text name="dahname"/></dt>
					<dd><input class="easyui-textbox"  type="text" id="arch_num" value="" style="height:26px;width:100px;"/></dd>
					<dt style="height:26px;width:60px;">姓名</dt>
					<dd><input class="easyui-textbox"  type="text" id="custname" value="" style="height:26px;width:100px;"/></dd>
					<dd><a href="javascript:gettimeuserGrid();"  class="easyui-linkbutton c6 l-btn l-btn-small" style="width:100px;">查询</a></dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>人员列表</strong></legend>
      <table id="groupusershow">
      </table>	
 </fieldset>
 <div id="dlg-custedit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-custshow" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-hsprintln" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  
    </div>
</div>

</div>


</body>
</html>