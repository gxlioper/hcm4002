<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/dtree.css"/> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zyb/customerManager/fsxzybimpusershow.js?randomId=<%=Math.random()%>"></script>
<script type="text/javascript">
var dahname ='<s:text name="dahname"/>';  
var tjhname ='<s:text name="tjhname"/>'; 
</script>
<body style="height:460px;">
<input type="hidden" id="company_id" value="">
<input type="hidden" id="batch_id" value="">
<input type="hidden" id="companybatch_id" value="">
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>信息检索</strong></legend>
			<div class="user-query">
				<dl>
                    <dd style="height:20px;">
					    单位部门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <input type="text" id="com_Name" value="" style="height:20px;width:140px;"/>
					  <div id="com_name_list_div1" style="display:none;margin-left:235px;" 
					      onmouseover="select_com_list_mover()" 
					      onmouseout="select_com_list_amover()">
                      </div>
                      </dd>
                      <dd style="height:20px;">
					     体检任务 
					  <input type="text" id="batchcom_Name" value="" style="height:20px;width:140px;"/>
					  <div id="com_name_list_div" style="display:none;margin-left:-20px;" 
					      onmouseover="select_batchcom_list_mover()" 
					      onmouseout="select_batchcom_list_amover()">
                      </div>
                    </dd>
                    <dd style="height:20px;">
					     <a href="javascript:getgroupGrid();" class="easyui-linkbutton c6" style="width:80px;">查询</a>
                    </dd>
				</dl>
			</div>
 </fieldset>
 <fieldset style="margin:5px;padding-right:0;">
<legend><strong>人员临时表信息</strong></legend>
      <table id="impusershow" class="easyui-datagrid" >
      
      </table>	
 </fieldset>
 
 <div id="dlg-edit" class="easyui-dialog"  data-options="width: 800,height: 380,closed: true,cache: false,modal: true,top:50"></div>
 <div id="dlg-show" class="easyui-dialog"  data-options="width: 750,height: 420,closed: true,cache: false,modal: true,top:50"></div>
  </body>